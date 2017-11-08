package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:保险期间恢复
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Sinosoft</p>
 * @author 孙少贤 2009-04-19
 * @version 1.0
 */
public class PGrpEdorBRDetailBL
{
private static Logger logger = Logger.getLogger(PGrpEdorBRDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();


    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;

    private Reflections mRef = new Reflections();

    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();

    /** 传入数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private TransferData mTransferData = new TransferData();
    private String mReStatDate = "";
    private String mGrpContNo = "";
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mRiskCode = "";

    //恢复后的终止日期
    String mNewGrpEndDate = "";//团单险种新的终止日期
    String mNewPolEndDate = "";//个单新的终止日期

    /** 修改数据 */
    private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
    private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPGetSet mLPGetSet = new LPGetSet();
    private LPContSet mLPContSet = new  LPContSet();
    private LPContStateSet mLPContStateSet = new LPContStateSet();
    private LPGrpContStateSet mLPGrpContStateSet = new LPGrpContStateSet();

    //记录个人合同号
    private String mContNo = "";

    /**
     * Constructor
     **/
    public PGrpEdorBRDetailBL()
    {}

    /**
     *
     **/
    public VData getResult()
    {
        return mResult;
    }

    /**
     *
     **/
    public static void main(String[] args)
    {
        PGrpEdorBRDetailBL tPGrpEdorAADetailUI = new PGrpEdorBRDetailBL();
        TransferData tTransferData = new TransferData();
        String Operator = "INSERT";
        GlobalInput tG = new GlobalInput();
        tG.Operator = "tk";
        tG.ManageCom = "0101";
        LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        tLCGrpPolSchema.setGrpPolNo("270101000162");
        tLCGrpPolSchema.setGrpContNo("280101000395");
        tLCGrpPolSchema.setRiskCode("132");
        tLCGrpPolSchema.setCValiDate("2006-02-01");
        tLCGrpPolSchema.setPayEndDate("2006-05-01");
        tLCGrpPolSchema.setGrpName("大篷车");
        tLCGrpPolSchema.setPeoples2("123");
        tLCGrpPolSet.add(tLCGrpPolSchema);
        tTransferData.setNameAndValue("StatDate",   "2006-10-01");
        tTransferData.setNameAndValue("EdorNo",    "460101000297");
        tTransferData.setNameAndValue("EdorType",  "BR");
        tTransferData.setNameAndValue("GrpContNo", "280101000395");

        try
        {
            VData tVData = new VData();
            tVData.add(tG);
            tVData.add(tLCGrpPolSet);
            tVData.add(tTransferData);
            logger.debug("start UI...."+tLCGrpPolSet.size());

            tPGrpEdorAADetailUI.submitData(tVData, Operator);
        }
        catch (Exception ex)
        {
            logger.debug("SHIBAI....");
        }

    }
    /**
     * 数据提交的公共方法
     * @param: cInputData 传入的数据
     * @param: cOperate 数据操作字符串
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "=====This is PGrpEdorBRDetailBL->submitData=====\n");

        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }

        //数据校验
        if (!checkData())
        {
            return false;
        }

        //数据准备操作
        if (!dealData())
        {
            return false;
        }
        mInputData.clear();
        mInputData.add(mMap);
        PubSubmit tPubSubmit = new  PubSubmit();
        if(!tPubSubmit.submitData(mInputData,mOperate))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "提交数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData.clear();
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData()
    {
        logger.debug("=====This is PGrpEdorBRDetailBL->getInputData=====\n");
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mLCGrpPolSet = (LCGrpPolSet) mInputData.getObjectByObjectName(
                "LCGrpPolSet", 0);
        if (mGlobalInput == null || mGlobalInput.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "GlobalInput数据为空!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if (mTransferData == null || mTransferData.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "TransferData 数据为空!!";
            this.mErrors.addOneError(tError);

            return false;
        }

        mReStatDate = (String) mTransferData.getValueByName("StatDate");
        mEdorNo = (String) mTransferData.getValueByName("EdorNo");
        mEdorType = (String) mTransferData.getValueByName("EdorType");
        mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");

        return true;
    }

    /**
     *
     **/
    private boolean checkData()
    {
    	logger.debug("=====This is PGrpEdorBRDetailBL->checkData=====\n");
    	LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    	tLPGrpEdorItemDB.setEdorNo(mEdorNo);
    	tLPGrpEdorItemDB.setEdorType(mEdorType);
    	tLPGrpEdorItemDB.setGrpContNo(mGrpContNo);
    	LPGrpEdorItemSet tLPGrpEdorItemSet = null;
    	try {
    		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
    	} catch (Exception ex){
    		CError.buildErr(this, ex.toString());
    		ex.printStackTrace();
    		return false;
    	}
    	if (tLPGrpEdorItemDB.mErrors.needDealError()){
    		mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
    		return false;
    	}
    	if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1)
    	{
    		CError tError = new CError();
    		tError.moduleName = "PGrpEdorCTDetailBL";
    		tError.functionName = "checkData";
    		tError.errorMessage = "无保全项目数据!";
    		this.mErrors.addOneError(tError);

    		return false;
    	}
    	if (tLPGrpEdorItemSet.get(1).getEdorState().equals("2"))
    	{
    		CError tError = new CError();
    		tError.moduleName = "PGrpEdorCTDetailBL";
    		tError.functionName = "checkData";
    		tError.errorMessage = "该保全项目已经申请确认不能修改!";
    		this.mErrors.addOneError(tError);

    		return false;
    	}
    	mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1).getSchema());
    	int intv = PubFun.calInterval(mReStatDate, mLPGrpEdorItemSchema.getEdorAppDate(), "D");
    	if(intv>0){
    		CError.buildErr(this, "恢复日期不可早于保全申请日期!");
    		return false;
    	}
    	if (!deleteData()){
    		return false;
    	}

    	return true;
    }

    /**
     *
     **/


    /**
     *
     **/
    private boolean deleteData(){

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->deleteStopEdorState=====\n");
        String tSql =
            "delete from LPGrpContState where EdorNo = '" + mEdorNo +
            "' and GrpContNo = '" + mGrpContNo + "'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPGrpPol where EdorNo = '" + mEdorNo + "' and EdorType = '"+mEdorType+"'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPPol where EdorNo = '" + mEdorNo +
            "' and EdorType = '"+mEdorType+"'";
        
        mMap.put(tSql, "DELETE");
        
        tSql =
            "delete from LPContState where EdorNo = '" + mEdorNo + "' and EdorType = '"+mEdorType+"'";
        mMap.put(tSql, "DELETE");
        
        tSql =
            "delete from LPDuty where EdorNo = '" + mEdorNo +
            "' and EdorType = '"+mEdorType+"'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPGet where EdorNo = '" + mEdorNo +
            "' and EdorType = '"+mEdorType+"'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPPrem where EdorNo = '" + mEdorNo +
            "' and EdorType = '"+mEdorType+"'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPCont where EdorNo = '" + mEdorNo +
            "' and EdorType = '"+mEdorType+"'";
        mMap.put(tSql, "DELETE");

        return true;
    }

    /**
     * TK加保业务处理
     * @return boolean
     */
    private boolean dealData()
    {
    	logger.debug("=====This is PGrpEdorBRDetailBL->dealData=====\n");
    	String tSql = "";

    	/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    	 * No.1 更新LPGrpEdorItem表
    	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    	 */
    	if (!dealGrpEdorItem()){
    		return false;
    	}
    	
    	List reContNo = new ArrayList();
    	LCGrpPolSchema tLCGrpPolSchema = null;
    	LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
    	tLCGrpPolDB.setGrpContNo(mGrpContNo);
    	tLCGrpPolDB.setAppFlag("1");
    	mLCGrpPolSet = tLCGrpPolDB.query();
    	
    	for (int n = 1; n <= mLCGrpPolSet.size(); n++)
    	{
    		tLCGrpPolSchema = mLCGrpPolSet.get(n);
    		String tGrpPolNo = tLCGrpPolSchema.getGrpPolNo();

    		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 * No.2 插入lcgrpcontstate表
    		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 */
    		if (!InsertGrpReStateData(tLCGrpPolSchema)){
    			return false;
    		}
    		mRiskCode = mLCGrpPolSet.get(n).getRiskCode();

    		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 * No.3 更新LPGrpPol表
    		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 */
    		if (!InsertLPGrpPolData(tLCGrpPolSchema)){
    			return false;
    		}

    		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 * No.4 插入LPPol表
    		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 */
    		LCPolDB tLCPolDB = new LCPolDB();
    		LCPolSet tLCPolSet = null;
    		tSql = "select * from LCPol where GrpContNo = '" + mGrpContNo +
    		"' and GrpPolNo = '" + tGrpPolNo + "' and appflag = '1' and exists (select 'X' from lccontstate where statetype = 'Available' and state = '1' and enddate is null and contno = LCPol.ContNo and PolNo = LCPol.PolNo)";
    		try {
    			tLCPolSet = tLCPolDB.executeQuery(tSql);
    		} catch (Exception ex){
    			CError.buildErr(this, ex.toString());
    			ex.printStackTrace();
    			return false;
    		}
    		if (tLCPolDB.mErrors.needDealError()) {

    			mErrors.copyAllErrors(tLCPolDB.mErrors);
    			return false;
    		}
    		
    		logger.debug("有"+tLCPolSet.size()+"条处于保单中止状态的个人险种");
    		LCPolSchema tLCPolSchema = null;
    		String tPolNo = "";
    		String tContNo = "";
    		for (int i = 1; i <= tLCPolSet.size(); i++){
    			tLCPolSchema = tLCPolSet.get(i);
    			tPolNo = tLCPolSchema.getPolNo();
    			tContNo = tLCPolSchema.getContNo();
    			
        		if (!InsertPolReStateData(tLCPolSchema)) {
        			return false;
        		}
        		
    			if (!InsertLPPolData(tLCPolSchema)){
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.5 插入LPDuty表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			if (!InsertLPDutyData(tPolNo)){
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.6 插入LPPrem表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			if (!InsertLPPremData(tPolNo)){
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.7 插入LCGet表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			if (!InsertLPGetData(tPolNo)){
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.8 插入LCCont表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
                if(!reContNo.contains(tContNo))
                {
                	if (!InsertLPContData(tContNo)){
                		return false;
                	}
                	reContNo.add(tContNo);
                }

    		}
    	}
    	mMap.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    	mMap.put(mLPGrpContStateSet, "DELETE&INSERT");
    	mMap.put(mLPContStateSet, "DELETE&INSERT");
    	mMap.put(mLPGrpPolSet, "DELETE&INSERT");
    	mMap.put(mLPPolSet, "DELETE&INSERT");
    	mMap.put(mLPGetSet, "DELETE&INSERT");
    	mMap.put(mLPDutySet, "DELETE&INSERT");
    	mMap.put(mLPPremSet, "DELETE&INSERT");
    	mMap.put(mLPContSet, "DELETE&INSERT");

    	return true;
    }

    /**
     *
     **/
    private boolean dealGrpEdorItem(){

        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setEdorNo(mEdorNo);
        tLPGrpEdorItemDB.setEdorType(mEdorType);
        tLPGrpEdorItemDB.setGrpContNo(mGrpContNo);
        tLPGrpEdorItemDB.setEdorType(mEdorType);
        LPGrpEdorItemSet tLPGrpEdorItemSet = null;
        try {
            tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLPGrpEdorItemDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
            return false;
        }
        if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1)
        {
            mErrors.addOneError("查询LPGrpEdorItem表失败!");
            return false;
        }
        mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
        mLPGrpEdorItemSchema.setEdorState("1");
        mLPGrpEdorItemSchema.setModifyDate(mCurrentDate);
        mLPGrpEdorItemSchema.setModifyTime(mCurrentTime);

        return true;
    }

    /**
     *
     **/
private boolean InsertGrpReStateData(LCGrpPolSchema grpPolSchema) {
    	
    	String tOldEndDate = PubFun.calDate(mReStatDate, -1,"D", null);//上一状态结束日期
    	String strSql = "select * from lcgrpcontstate where statetype = 'Available' "
			+ "and state = '1' and enddate is null and grpcontno = '" + grpPolSchema.getGrpContNo() + "' and grppolno = '"+grpPolSchema.getGrpPolNo()+"'";
    	//先查询是否存在state='1'的这条中断记录 必须存在
    	LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
    	LPGrpContStateSchema tLPGrpContStateSchema = null;
    	LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(strSql);
    	if (tLCGrpContStateSet.size() > 0) {
    		//如果存在,先结束中断状态
    		tLPGrpContStateSchema = new LPGrpContStateSchema();
    		mRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(1));
    		
			if (tLPGrpContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
				tLPGrpContStateSchema.setEndDate(tLPGrpContStateSchema.getStartDate());
			else 
				tLPGrpContStateSchema.setEndDate(tOldEndDate);
			
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			
			int intv = PubFun.calInterval(tLPGrpContStateSchema.getStartDate(), tLPGrpContStateSchema.getEndDate(), "D");//计算出该团单险种中断了几天
			String tOldGrpEndDate = tLPGrpContStateSchema.getRemark();//上一次申请中断前的保单中止日期
			mNewGrpEndDate = PubFun.newCalDate(tOldGrpEndDate,"D",intv+1);
			
			tLPGrpContStateSchema.setRemark(String.valueOf(intv+1));//结束中断日期后将中断天数保存到备注
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			
			tLPGrpContStateSchema = new LPGrpContStateSchema();
			//先置上旧状态
			mRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(1));
			tLPGrpContStateSchema.setState("0");
			if (tLPGrpContStateSchema.getStartDate().compareTo(mReStatDate) == 0)
				tLPGrpContStateSchema.setStartDate(PubFun.calDate(mReStatDate,1,"D",null));
			else
				tLPGrpContStateSchema.setStartDate(mReStatDate);
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			tLPGrpContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
			//tLPGrpContStateSchema.setStateReason(mStateReason);
			tLPGrpContStateSchema.setRemark(String.valueOf(intv));//申请失效复效时存放险种中断了多少天
			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			
    	}else{
    		//如果不存在肯定是出错的
    		 mErrors.addOneError("查询没有查询到保单的中断状态!");
             return false;
    	}
		return true;
	}

    /**
     *
     **/
    private boolean InsertLPGrpPolData(LCGrpPolSchema tLCGrpPolSchema){

        logger.debug(
            "=====This is PGrpEdorBRDetailBL->InsertLPGrpPolData=====\n");
        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
        LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
        LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
        tLCGrpPolDB.setSchema(tLCGrpPolSchema);
        mLCGrpPolSchema=tLCGrpPolDB.query().get(1);
        Reflections mRef = new Reflections();
        mRef.transFields(mLPGrpPolSchema, mLCGrpPolSchema);
        mLPGrpPolSchema.setEdorNo(mEdorNo);
        mLPGrpPolSchema.setEdorType(mEdorType);
        mLPGrpPolSchema.setPayEndDate(mNewGrpEndDate);
        mLPGrpPolSchema.setPaytoDate(mNewGrpEndDate);
        mLPGrpPolSchema.setModifyDate(mCurrentDate);
        mLPGrpPolSchema.setModifyTime(mCurrentTime);
        mLPGrpPolSet.add(mLPGrpPolSchema);

        return true;
    }

    /**
     *
     **/
    private boolean InsertLPPolData(LCPolSchema tLCPolSchema){

        logger.debug(
            "=====This is PGrpEdorBRDetailBL->InsertLPPolData=====\n");
        LPPolSchema tLPPolSchema = new LPPolSchema();
        mRef.transFields(tLPPolSchema, tLCPolSchema);
        tLPPolSchema.setEdorNo(mEdorNo);
        tLPPolSchema.setEdorType(mEdorType);
        tLPPolSchema.setRiskCode(mRiskCode);
        tLPPolSchema.setModifyDate(mCurrentDate);
        tLPPolSchema.setModifyTime(mCurrentTime);
        tLPPolSchema.setPayEndDate(mNewPolEndDate);
        tLPPolSchema.setPaytoDate(mNewPolEndDate);
        tLPPolSchema.setEndDate(mNewPolEndDate);
        tLPPolSchema.setAcciEndDate(mNewPolEndDate);
        mLPPolSet.add(tLPPolSchema);

        return true;
    }

    /**
     *
     **/
    private boolean InsertLPDutyData(String tPolNo) {

        logger.debug(
            "=====This is PGrpEdorBRDetailBL->InsertLPDutyData=====\n");
        LCDutyDB tLCDutyDB = new LCDutyDB();
        LCDutySet tLCDutySet = null;
        String tSql = "select * from LCDuty where PolNo = '" + tPolNo + "'";
        try {
            tLCDutySet = tLCDutyDB.executeQuery(tSql);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCDutyDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLCDutyDB.mErrors);
            return false;
        }
        if (tLCDutySet == null || tLCDutySet.size() < 1) {
            mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCDuty表失败！");
            return false;
        }
        LCDutySchema tLCDutySchema = null;
        LPDutySchema tLPDutySchema = null;
        for (int t = 1; t <= tLCDutySet.size(); t++) {

            tLCDutySchema = new LCDutySchema();
            tLCDutySchema.setSchema(tLCDutySet.get(t).getSchema());
            tLPDutySchema = new LPDutySchema();
            mRef.transFields(tLPDutySchema, tLCDutySchema);
            tLPDutySchema.setPayEndDate(mNewPolEndDate);
            tLPDutySchema.setPaytoDate(mNewPolEndDate);
            tLPDutySchema.setModifyDate(mCurrentDate);
            tLPDutySchema.setModifyTime(mCurrentTime);
            tLPDutySchema.setEdorNo(mEdorNo);
            tLPDutySchema.setEdorType(mEdorType);
            mLPDutySet.add(tLPDutySchema);
        }

        return true;
    }

    /**
     *
     **/
    private boolean InsertLPPremData(String tPolNo) {

        logger.debug(
            "=====This is PGrpEdorBRDetailBL->InsertLPPremData=====\n");
        LCPremDB tLCPremDB = new LCPremDB();
        LCPremSet tLCPremSet = null;
        String tSql = "select * from LCPrem where PolNo ='" + tPolNo + "'";
        try {
            tLCPremSet = tLCPremDB.executeQuery(tSql);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCPremDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLCPremDB.mErrors);
            return false;
        }
        if (tLCPremSet == null || tLCPremSet.size() < 1) {
            mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCPrem表失败！");
            return false;
        }
        LCPremSchema tLCPremSchema = new LCPremSchema();
        LPPremSchema tLPPremSchema = new LPPremSchema();
        for (int t = 1; t <= tLCPremSet.size(); t++) {

            tLCPremSchema = new LCPremSchema();
            tLCPremSchema.setSchema(tLCPremSet.get(t).getSchema());
            tLPPremSchema = new LPPremSchema();
            mRef.transFields(tLPPremSchema, tLCPremSchema);
            tLPPremSchema.setPayEndDate(mNewPolEndDate);
            tLPPremSchema.setPaytoDate(mNewPolEndDate);
            tLPPremSchema.setModifyDate(mCurrentDate);
            tLPPremSchema.setModifyTime(mCurrentTime);
            tLPPremSchema.setEdorNo(mEdorNo);
            tLPPremSchema.setEdorType(mEdorType);
            mLPPremSet.add(tLPPremSchema);
        }

        return true;
    }

    /**
     *
     **/
    private boolean InsertLPGetData(String tPolNo) {

        logger.debug(
            "=====This is PGrpEdorBRDetailBL->InsertLPGetData=====\n");
        LCGetDB tLCGetDB = new LCGetDB();
        LCGetSet tLCGetSet = new LCGetSet();
        String tSql = "select * from LCGet where PolNo = '" + tPolNo + "'";
        try {
            tLCGetSet = tLCGetDB.executeQuery(tSql);
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCGetDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLCGetDB.mErrors);
            return false;
        }
        if (tLCGetSet == null || tLCGetSet.size() < 1) {

                mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCGet表失败！");
                return false;
            }
            LCGetSchema tLCGetSchema = new LCGetSchema();
            LPGetSchema tLPGetSchema = new LPGetSchema();
            for (int t = 1; t <= tLCGetSet.size(); t++) {

                tLCGetSchema = new LCGetSchema();
                tLCGetSchema.setSchema(tLCGetSet.get(t).getSchema());
                tLPGetSchema = new LPGetSchema();
                mRef.transFields(tLPGetSchema, tLCGetSchema);
                tLPGetSchema.setGetEndDate(mNewPolEndDate);
                tLPGetSchema.setModifyDate(mCurrentDate);
                tLPGetSchema.setModifyTime(mCurrentTime);
                tLPGetSchema.setEdorNo(mEdorNo);
                tLPGetSchema.setEdorType(mEdorType);
                mLPGetSet.add(tLPGetSchema);
            }

            return true;
        }

        /**
         *
         **/
        private boolean InsertLPContData(String tContNo){

            logger.debug(
            "=====This is PGrpEdorBRDetailBL->InsertLPContData=====\n");
            LCContDB tLCContDB = new LCContDB();
            LCContSet tLCContSet = new LCContSet();
            String tSql = "select * from LCCont where ContNo = '" + tContNo + "'";
            try {
                tLCContSet = tLCContDB.executeQuery(tSql);
            } catch (Exception ex) {
                CError.buildErr(this, ex.toString());
                ex.printStackTrace();
                return false;
            }
            if (tLCContDB.mErrors.needDealError()) {
                mErrors.copyAllErrors(tLCContDB.mErrors);
                return false;
            }
        if (tLCContSet == null || tLCContSet.size() < 1) {
                mErrors.addOneError("查询个人保单号[" + tContNo + "]的LCCont表失败！");
                return false;
            }
            LCContSchema tLCContSchema = new LCContSchema();
            LPContSchema tLPContSchema = new LPContSchema();
            for (int t = 1; t <= tLCContSet.size(); t++) {

                tLCContSchema = new LCContSchema();
                tLCContSchema.setSchema(tLCContSet.get(t).getSchema());
                mContNo = tLCContSchema.getContNo();
                tLPContSchema = new LPContSchema();
                mRef.transFields(tLPContSchema, tLCContSchema);
                tLPContSchema.setPaytoDate(mNewPolEndDate);
                tLPContSchema.setModifyDate(mCurrentDate);
                tLPContSchema.setModifyTime(mCurrentTime);
                tLPContSchema.setEdorNo(mEdorNo);
                tLPContSchema.setEdorType(mEdorType);
                mLPContSet.add(tLPContSchema);
            }

            return true;
        }
        private boolean InsertPolReStateData(LCPolSchema tPolSchema) {
        	
        	String tOldEndDate = PubFun.calDate(mReStatDate, -1,"D", null);//上一状态结束日期
        	String strSql = "select * from lccontstate where statetype = 'Available' "
    			+ "and state = '1' and enddate is null and contno = '" + tPolSchema.getContNo() + "' and polno = '"+tPolSchema.getPolNo()+"'";
        	//先查询是否存在state='1'的这条记录 必须存在
        	LCContStateDB tLCContStateDB = new LCContStateDB();
        	LPContStateSchema tLPContStateSchema = null;
        	LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(strSql);
        	if (tLCContStateSet.size() > 0) {
        		//如果存在,先结束上一状态
        		tLPContStateSchema = new LPContStateSchema();
        		mRef.transFields(tLPContStateSchema, tLCContStateSet.get(1));
        		
    			if (tLPContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
    				tLPContStateSchema.setEndDate(tLPContStateSchema.getStartDate());
    			else 
    				tLPContStateSchema.setEndDate(tOldEndDate);
    			
    			tLPContStateSchema.setEdorNo(mEdorNo);
    			tLPContStateSchema.setEdorType(mEdorType);
    			
    			int intv = PubFun.calInterval(tLPContStateSchema.getStartDate(), tLPContStateSchema.getEndDate(), "D");//计算出该个单险种中断了几天
    			String tOldPolEndDate = tLPContStateSchema.getRemark();//上一次申请中断前的保单中止日期
    			mNewPolEndDate = PubFun.newCalDate(tOldPolEndDate,"D",intv);
    			
    			tLPContStateSchema.setRemark(String.valueOf(intv));//结束中断日期后将中断天数保存到备注
    			
    			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
    			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
    			mLPContStateSet.add(tLPContStateSchema);
    			
    			tLPContStateSchema = new LPContStateSchema();
    			//先置上旧状态
    			mRef.transFields(tLPContStateSchema, tLCContStateSet.get(1));
    			tLPContStateSchema.setState("0");
    			if (tLPContStateSchema.getStartDate().compareTo(mReStatDate) == 0)
    				tLPContStateSchema.setStartDate(PubFun.calDate(mReStatDate,1,"D",null));
    			else
    				tLPContStateSchema.setStartDate(mReStatDate);
    			tLPContStateSchema.setEdorNo(mEdorNo);
    			tLPContStateSchema.setEdorType(mEdorType);
    			tLPContStateSchema.setMakeDate(PubFun.getCurrentDate());
    			tLPContStateSchema.setMakeTime(PubFun.getCurrentTime());
    			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
    			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
    			//tLPGrpContStateSchema.setStateReason(mStateReason);
    			tLPContStateSchema.setRemark(String.valueOf(intv));//申请失效复效时存放险种中断了多少天
    			mLPContStateSet.add(tLPContStateSchema);
    			
        	}else{
        		//如果不存在,生成新的
        		mErrors.addOneError("查询没有查询到保单的中断状态!");
        		return false;

        	}
    		return true;
    	}

}
