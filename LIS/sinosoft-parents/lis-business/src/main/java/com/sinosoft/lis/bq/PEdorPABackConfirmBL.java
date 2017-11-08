package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
/**
 * <p>Title: 保全回退处理 </p>
 *
 * <p>Description: 投资计划变更保全回退处理确认类 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author ck
 * @CreateDate 2005-10-18
 * @version 1.0
 */
public class PEdorPABackConfirmBL implements EdorBack{
private static Logger logger = Logger.getLogger(PEdorPABackConfirmBL.class);
    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    private MMap map = new MMap();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    private String CurrDate = PubFun.getCurrentDate();
    private String CurrTime = PubFun.getCurrentTime();
    //需要回退的保全项目
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    public PEdorPABackConfirmBL() {
    }

    /**
     * 数据提交的公共方法
     * @param cInputData 传入的数据对象
     * @param cOperate 数据操作字符串
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        if (!getInputData()) {
            return false;
        }

        //进行业务处理
        if (!dealData()) {
            return false;
        }

        //准备往后台的数据
        if (!prepareData()) {
            return false;
        }

        return true;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @return boolean
     */
    private boolean getInputData() {
        try {
            mGlobalInput =
                    (GlobalInput)
                    mInputData.getObjectByObjectName("GlobalInput", 0);
            mLPEdorItemSchema = //需要回退的保全项目
                    (LPEdorItemSchema)
                    mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        } catch (Exception e) {
            e.printStackTrace();
            CError.buildErr(this, "接收前台数据失败!");
            return false;
        }
        if (mGlobalInput == null || mLPEdorItemSchema == null) {
            CError.buildErr(this, "传入数据有误!");
            return false;
        }

        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return boolean
     */
    private boolean dealData() {
        logger.debug("=== 投资计划变更回退确认生效 ===");
        Reflections tRef = new Reflections();

        //InvestPlan
        LCPerInvestPlanSet tLCPerInvestPlanSet=new LCPerInvestPlanSet();
        LPPerInvestPlanSet tLPPerInvestPlanSet=new LPPerInvestPlanSet();
        LPPerInvestPlanSet mLPPerInvestPlanSet=new LPPerInvestPlanSet();

        LPPerInvestPlanDB tLPPerInvestPlanDB = new LPPerInvestPlanDB();
        tLPPerInvestPlanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPPerInvestPlanDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPPerInvestPlanDB.setContNo(mLPEdorItemSchema.getContNo());

        mLPPerInvestPlanSet=tLPPerInvestPlanDB.query();

        for(int i=1;i<=mLPPerInvestPlanSet.size();i++)
        {
            LCPerInvestPlanSchema tLCPerInvestPlanSchema = new
                    LCPerInvestPlanSchema();
            tRef.transFields(tLCPerInvestPlanSchema, mLPPerInvestPlanSet.get(i));
            tLCPerInvestPlanSchema.setModifyDate(CurrDate);
            tLCPerInvestPlanSchema.setModifyTime(CurrTime);
            tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);

            LPPerInvestPlanSchema tLPPerInvestPlanSchema=new LPPerInvestPlanSchema();
            LCPerInvestPlanDB tLCPerInvestPlanDB=new LCPerInvestPlanDB();
            tLCPerInvestPlanDB.setPolNo(tLCPerInvestPlanSchema.getPolNo());
            tLCPerInvestPlanDB.setInsuAccNo(tLCPerInvestPlanSchema.getInsuAccNo());
            tLCPerInvestPlanDB.setPayPlanCode(tLCPerInvestPlanSchema.getPayPlanCode());
            if(tLCPerInvestPlanDB.getInfo())
            {
                tRef.transFields(tLPPerInvestPlanSchema , tLCPerInvestPlanDB.getSchema());
                tLPPerInvestPlanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPPerInvestPlanSchema.setEdorType(mLPEdorItemSchema.
                        getEdorType());
                tLPPerInvestPlanSchema.setModifyDate(CurrDate);
                tLPPerInvestPlanSchema.setModifyTime(CurrTime);
            }else
            {
                CError.buildErr(this, "查询投资计划信息失败!");
                return false;
            }
            tLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);
        }
        map.put(tLCPerInvestPlanSet, "DELETE&INSERT");
        map.put(tLPPerInvestPlanSet, "DELETE&INSERT");

        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * @return boolean
     */
    private boolean prepareData() {
        mResult.clear();
        mResult.add(map);
        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return VData
     */
    public VData getResult() {
        return mResult;
    }

    public CErrors getErrors() {
        return mErrors;
    }

    public static void main(String[] args) {
        PEdorPABackConfirmBL PEdorPABackConfirmBL = new PEdorPABackConfirmBL();
    }
}
