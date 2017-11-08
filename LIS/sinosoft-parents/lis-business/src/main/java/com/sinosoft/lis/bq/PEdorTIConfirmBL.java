package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.acc.AccAndClassBak;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全账户转换确认处理类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PEdorTIConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorTIConfirmBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;
    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();

    /**  */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new
            LCInsureAccTraceSet();
    
    LPInsuAccOutSet mLPInsuAccOutSet = new LPInsuAccOutSet();
    LPInsuAccOutDB mLPInsuAccOutDB = new LPInsuAccOutDB();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private MMap map = new MMap();
    boolean newaddrFlag = false;

    public PEdorTIConfirmBL() {}


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }
        logger.debug("---End getInputData---");

        //数据准备操作
        if (!dealData()) {
            return false;
        }
        logger.debug("---End prepareData---");
        if (!prepareOutputData()) {
            return false;
        }

        return true;
    }

    public VData getResult() {
        return mResult;
    }

    private boolean prepareOutputData() {

        mResult.clear();
        mResult.add(map);

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData() {
        try {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPEdorItemSchema",
                    0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
        } catch (Exception e) {
            CError.buildErr(this, "接收数据失败");
            return false;
        }

        return true;
    }


    /**
     * 准备需要保存的数据
     */
    private boolean dealData() {
        String tContNo = mLPEdorItemSchema.getContNo();
        String tEdorNo = mLPEdorItemSchema.getEdorNo();
        String tEdorType = mLPEdorItemSchema.getEdorType();

        mLPInsuAccOutDB.setEdorNo(tEdorNo);
        mLPInsuAccOutDB.setEdorType(tEdorType);
        mLPInsuAccOutDB.setContNo(tContNo);
        mLPInsuAccOutSet = mLPInsuAccOutDB.query();
        //处理feetrace表
        if (!dealInsuAccTraceData()) {
            return false;
        }
        //处理后续处理接口表
        LOPolAfterDealSchema mLOPolAfterDealSchema=new LOPolAfterDealSchema();
        mLOPolAfterDealSchema.setAccAlterNo(tEdorNo);
        mLOPolAfterDealSchema.setAccAlterType("3");
        mLOPolAfterDealSchema.setBusyType(tEdorType);
        mLOPolAfterDealSchema.setManageCom(mLPEdorItemSchema.getManageCom());
        mLOPolAfterDealSchema.setState("0");
        mLOPolAfterDealSchema.setContNo(mLPEdorItemSchema.getContNo());
        mLOPolAfterDealSchema.setConfDate(CurrentDate);
        mLOPolAfterDealSchema.setMakeDate(CurrentDate);
        mLOPolAfterDealSchema.setMakeTime(CurrentTime);
        mLOPolAfterDealSchema.setModifyDate(CurrentDate);
        mLOPolAfterDealSchema.setModifyTime(CurrentTime);

        if (mLCInsureAccTraceSet.size() > 0) {
            map.put(mLCInsureAccTraceSet, "INSERT");
        }
        map.put(mLOPolAfterDealSchema, "INSERT");

        if(!accandclassbak())
        {
        	return false;
        }


        for (int i = 1; i <= mLPInsuAccOutSet.size(); i++) {
            mLPInsuAccOutSet.get(i).setState("2");
        }
        if (mLPInsuAccOutSet.size() > 0) {
            map.put(mLPInsuAccOutSet, "UPDATE");
        }

        LPInsuAccInSet mLPInsuAccInSet = new LPInsuAccInSet();
        LPInsuAccInDB mLPInsuAccInDB = new LPInsuAccInDB();
        mLPInsuAccInDB.setEdorNo(tEdorNo);
        mLPInsuAccInDB.setEdorType(tEdorType);
        mLPInsuAccInDB.setContNo(tContNo);
        mLPInsuAccInSet = mLPInsuAccInDB.query();
        for (int i = 1; i <= mLPInsuAccInSet.size(); i++) {
            mLPInsuAccInSet.get(i).setState("0");
        }
        if (mLPInsuAccInSet.size() > 0) {
            map.put(mLPInsuAccInSet, "UPDATE");
        }

        //mLPEdorItemSchema.setEdorValiDate(CurrentDate);
        //map.put(mLPEdorItemSchema, "UPDATE");
        return true;
    }
    
    
   private boolean accandclassbak() {
    	
   	AccAndClassBak tAccAndClassBak = new AccAndClassBak();
	tAccAndClassBak.setEdorNo(mLPEdorItemSchema.getEdorNo());
	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	sqlbv1.sql("delete from LPInsureAcc where edorno = '?edorno?'");
	sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	sqlbv2.sql("delete from LPInsureAccClass where edorno = '?edorno?'");
	sqlbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		map.put(sqlbv1, "DELETE");
		map.put(sqlbv2, "DELETE");
		map.put(tAccAndClassBak.getLPInsureAccSet() , "DELETE&INSERT");
		map.put(tAccAndClassBak.getLPInsureAccClassSet() , "DELETE&INSERT");
    	
    	
    	return true;
    }

    private boolean dealInsuAccTraceData() {
        logger.debug(
                "=====This is PEdorTIConfirmBL->dealInsuAccTraceData====\n");
        String tOperator = mGlobalInput.Operator;
//////////////////////////////////鲁哲，改trace的机构为lcpol的机构\\\\\\\\\\\\\\\\\\\\
        String strPolno = mLPInsuAccOutSet.get(1).getOutPolNo();
        logger.debug("POLNO:::::::::::::"+strPolno);
        LCPolDB lzLCPolDB = new LCPolDB();
        lzLCPolDB.setPolNo(strPolno);
        String tManageCom = lzLCPolDB.query().get(1).getManageCom();
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\END//////////////////////////////////////////////
        LPInsureAccTraceDB tLPInsureAccTraceDB = new
                                                 LPInsureAccTraceDB();
        tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
        LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB.
                query();
        if (tLPInsureAccTraceSet.size() > 0) {
            LCInsureAccTraceSchema tLCInsureAccTraceSchema;
            for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++) {
                tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                Reflections ref = new Reflections();
                ref.transFields(tLCInsureAccTraceSchema,
                                tLPInsureAccTraceSet.get(i));
                tLCInsureAccTraceSchema.setOperator(tOperator);
                tLCInsureAccTraceSchema.setManageCom(tManageCom);
                tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
                tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
                mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
            }
        }

        return true;
    }

    public TransferData getReturnTransferData() {
        return null;
    }

    public CErrors getErrors() {
        return mErrors;
    }

}
