package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LLCasePolicyDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LPAccMoveDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPAccMoveSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LLCasePolicySet;
import com.sinosoft.lis.vschema.LPAccMoveSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;



/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全集体下个人功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx 2009-1-12 
 * @version 1.0
 */
public class PGrpEdorAZDetailBL implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorAZDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    
    private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
    private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
    private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private LPContStateSet saveLPContStateSet = new LPContStateSet();
    private LPGrpContStateSet saveLPGrpContStateSet = new LPGrpContStateSet();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections ref = new Reflections();
    private TransferData mTransferData = new TransferData();

    private GrpEdorCalZT mGrpEdorCalZT = new GrpEdorCalZT();
    private String currDate = PubFun.getCurrentDate();
    private String currTime = PubFun.getCurrentTime();
    private MMap map = new MMap();
    public PGrpEdorAZDetailBL()
    {
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据
        if(!getInputData())
            return false;
        logger.debug("---End getInputData---");

        //数据校验操作
        if (!checkData())
            return false;
        logger.debug("---End checkData---");

        //数据准备操作
        if (mOperate.equals("INSERT||EDOR")) {
            if (!dealData()) {
              return false;
            }
        }
        //数据准备操作
        if (mOperate.equals("DELETE||EDOR"))
        {
            if (!deleteData())
                return false;
        }
        
        if (mOperate.equals("INSERT||SAVE")) {
            if (!saveData()) {
              return false;
            }
        }

        //　数据提交、保存
        PubSubmit tPubSubmit = new PubSubmit();

        if (!tPubSubmit.submitData(mResult, mOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);

            CError tError = new CError();
            tError.moduleName = "ContBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";

            this.mErrors.addOneError(tError);
            return false;
        }
        //支持EJB改造
        mResult.clear();
        mResult.add("success");
        
        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData()
    {
        try
        {
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPGrpEdorItemSchema",
                    0);
            mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
                    "LPEdorItemSet", 0);
            

            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
            mTransferData = (TransferData) mInputData.getObjectByObjectName(
                    "TransferData", 0);

        }
        catch (Exception e)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorXTDetailBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    /**
     * 校验传入的数据的合法性
     * @return
     */
    private boolean checkData()
    {
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
        if (!tLPGrpEdorItemDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "无保全申请数据!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }

        //将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

        if (tLPGrpEdorItemDB.getEdorState().trim().equals("2"))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean dealData(){

    	/**返还被保人标志*/
    	String tInsuGetFlag = (String)mTransferData.getValueByName("InsuGetFlag");
    	/**转入公共帐户标志*/
    	String tPubGetFlag = (String) mTransferData.getValueByName("PubGetFlag");
    	/**返还被保人现金*/
    	String tReInsuCash = (String) mTransferData.getValueByName("ReInsuCash");
    	/**转入公共帐户金额 */
    	String tRePubCash = (String) mTransferData.getValueByName("RePubCash");
    	/**个人交费部分现金价值*/
    	String tInsuGet = (String) mTransferData.getValueByName("InsuGet");
    	/**集体交费部分现金价值*/
    	String tPubMoney = (String) mTransferData.getValueByName("PubMoney");
    	/**是否公共帐户标记*/
    	String tPubFlag = (String) mTransferData.getValueByName("PubFlag");
    	/**退保帐户号*/
    	String tInsuAccNo = (String) mTransferData.getValueByName("InsuAccNo");

    	logger.debug("返还被保人标志:"+tInsuGetFlag);
    	logger.debug("转入公共帐户标志"+tPubGetFlag);
    	logger.debug("返还被保人现金"+tReInsuCash);
    	logger.debug("转入公共帐户金额"+tRePubCash);
    	logger.debug("个人交费部分现金价值"+tInsuGet);
    	logger.debug("集体交费部分现金价值"+tPubMoney);
    	logger.debug("是否公共帐户标记"+tPubFlag);
    	LPEdorItemSchema tLPEdorItemSchema = mLPEdorItemSet.get(1);//一个一个退,HOHO,只会有一个
    	LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
    	double tAZMoney = tLPEdorItemSchema.getGetMoney();
    	logger.debug("该被保人退费的金额为:"+tAZMoney);


    	ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
    	ref.transFields(tLPEdorMainSchema, tLPEdorItemSchema);
    	//判断保单是否理赔,如果有理赔,不能作保全项目
    	LLCasePolicyDB tLLCasePolicyDB = new LLCasePolicyDB();
    	tLLCasePolicyDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    	tLLCasePolicyDB.setContNo(tLPEdorMainSchema.getContNo());
    	LLCasePolicySet tLLCasePolicySet = tLLCasePolicyDB.query();
    	if (tLLCasePolicySet.size() > 0) {
    		CError.buildErr(this,"该保单发生过理赔，不能申请减人!");
    		return false;
    	}
    	//end

    	//----------------合同数据的处理 start----------------
    	LPContSchema tLPContSchema = null;//合同
    	LCContSchema tLCContSchema = null;
    	LCContDB tLCContDB = new LCContDB();
    	tLCContDB.setContNo(tLPEdorMainSchema.getContNo());
    	if(!tLCContDB.getInfo())
    	{
    		CError.buildErr(this, "查询被保人合同信息失败!");
    		return false; 
    	}
    	tLCContSchema = tLCContDB.getSchema();
    	tLPContSchema = new LPContSchema();
    	ref.transFields(tLPContSchema, tLCContSchema);
    	tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    	tLPContSchema.setAppFlag("4");//终止状态
    	tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    	tLPContSchema.setModifyDate(PubFun.getCurrentDate());
    	tLPContSchema.setModifyTime(PubFun.getCurrentTime());
    	
    	tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
    	tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
    	tLPEdorMainSchema.setUWState("0");
    	tLPEdorMainSchema.setMakeDate(currDate);
    	tLPEdorMainSchema.setMakeTime(currTime);
    	tLPEdorMainSchema.setModifyDate(currDate);
    	tLPEdorMainSchema.setModifyTime(currTime);
    	saveLPEdorMainSet.add(tLPEdorMainSchema);
    	//----------------合同数据的处理 end----------------
    	

    	
    	LPPolSchema tLPPolSchema = null;//险种保单
    	LCPolSchema tLCPolSchema = null;

    	LCPolDB tLCPolDB = new LCPolDB();
    	tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
    	if(!tLCPolDB.getInfo())
    	{
    		CError.buildErr(this, "查询被保人险种信息失败!");
    		return false; 
    	}
    	tLCPolSchema = tLCPolDB.getSchema();

    	tLPPolSchema = new LPPolSchema();
    	ref.transFields(tLPPolSchema, tLCPolSchema);
    	tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    	tLPPolSchema.setAppFlag("4");//终止状态
    	tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    	tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
    	tLPPolSchema.setModifyTime(PubFun.getCurrentTime());

    	if(tAZMoney > 0){
    		String sFeeType = BqCalBL.getFinType(tLPPolSchema.getEdorType(), "TB",tLCPolSchema.getPolNo());
    		LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
    		tLJSGetEndorseSchemaNew.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
    		tLJSGetEndorseSchemaNew.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
    		tLJSGetEndorseSchemaNew.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
    		tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeType);
    		tLJSGetEndorseSchemaNew.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    		tLJSGetEndorseSchemaNew.setGrpPolNo(tLPPolSchema.getGrpPolNo());
    		tLJSGetEndorseSchemaNew.setContNo(tLPEdorItemSchema.getContNo());
    		tLJSGetEndorseSchemaNew.setPolNo(tLPPolSchema.getPolNo());
    		tLJSGetEndorseSchemaNew.setRiskCode(tLPPolSchema.getRiskCode());
    		tLJSGetEndorseSchemaNew.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
    		tLJSGetEndorseSchemaNew.setOtherNoType("3");
    		tLJSGetEndorseSchemaNew.setDutyCode("000000");
    		tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
    		tLJSGetEndorseSchemaNew.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
    		tLJSGetEndorseSchemaNew.setGetMoney(tAZMoney);
    		tLJSGetEndorseSchemaNew.setAgentCode(tLPPolSchema.getAgentCode());
    		tLJSGetEndorseSchemaNew.setAgentCom(tLPPolSchema.getAgentCom());
    		tLJSGetEndorseSchemaNew.setAgentGroup(tLPPolSchema.getAgentGroup());
    		tLJSGetEndorseSchemaNew.setAgentType(tLPPolSchema.getAgentType());
    		tLJSGetEndorseSchemaNew.setInsuredNo(tLPPolSchema.getInsuredNo());
    		tLJSGetEndorseSchemaNew.setKindCode(tLPPolSchema.getKindCode());
    		tLJSGetEndorseSchemaNew.setAppntNo(tLPPolSchema.getAppntNo());
    		tLJSGetEndorseSchemaNew.setRiskVersion(tLPPolSchema.getRiskVersion());
    		tLJSGetEndorseSchemaNew.setHandler(tLPPolSchema.getHandler());
    		tLJSGetEndorseSchemaNew.setApproveCode(tLPPolSchema.getApproveCode());
    		tLJSGetEndorseSchemaNew.setApproveDate(tLPPolSchema.getApproveDate());
    		tLJSGetEndorseSchemaNew.setApproveTime(tLPPolSchema.getApproveTime());
    		tLJSGetEndorseSchemaNew.setManageCom(tLPPolSchema.getManageCom());
    		tLJSGetEndorseSchemaNew.setCurrency(tLPPolSchema.getCurrency());
    		tLJSGetEndorseSchemaNew.setPolType("1");
    		tLJSGetEndorseSchemaNew.setGetFlag("1");
    		tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
    		tLJSGetEndorseSchemaNew.setMakeDate(PubFun.getCurrentDate());
    		tLJSGetEndorseSchemaNew.setMakeTime(PubFun.getCurrentTime());
    		tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
    		tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
    		tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_Prem);
    		mLJSGetEndorseSet.add(tLJSGetEndorseSchemaNew);
    	}
    	tLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());
    	tLPEdorItemSchema.setGetMoney(tAZMoney);
    	tLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
    	tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
    	tLPEdorItemSchema.setUWFlag("0");
    	tLPEdorItemSchema.setEdorState("1");
    	tLPEdorItemSchema.setMakeDate(currDate);
    	tLPEdorItemSchema.setMakeTime(currTime);
    	tLPEdorItemSchema.setModifyDate(currDate);
    	tLPEdorItemSchema.setModifyTime(currTime);
    	saveLPEdorItemSet.add(tLPEdorItemSchema);
    	saveLPContStateSet.add(mGrpEdorCalZT.creatContState(tLPEdorItemSchema));
    	//--------------------开始处理帐户------------------
    	LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
    	tLCInsureAccDB.setPolNo(tLPPolSchema.getPolNo());
    	tLCInsureAccDB.setInsuAccNo(tInsuAccNo);
    	if(!tLCInsureAccDB.getInfo())
    	{
    		CError.buildErr(this, "查询帐户信息失败!");
    		return false; 
    	}
    	LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccDB.getSchema();
    	LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
    	ref.transFields(tLPInsureAccSchema, tLCInsureAccSchema);
    	tLPInsureAccSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    	tLPInsureAccSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    	tLPInsureAccSchema.setState("4");//退保失效
    	tLPInsureAccSchema.setInsuAccBala(0);
    	tLPInsureAccSchema.setBalaDate(mLPGrpEdorItemSchema.getEdorValiDate());
    	tLPInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
    	tLPInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
    	
    	LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
    	tLCInsureAccClassDB.setPolNo(tLPPolSchema.getPolNo());
    	tLCInsureAccClassDB.setInsuAccNo(tInsuAccNo);
    	LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
    	if(tLCInsureAccClassSet == null || tLCInsureAccClassSet.size()<1)
    	{
    		CError.buildErr(this, "查询帐户分类信息失败!");
    		return false; 
    	}
    	
    	LPAccMoveSet tLPAccMoveSet = new LPAccMoveSet();
		LPInsureAccClassSchema tLPInsureAccClassSchema = null;
		LCInsureAccClassSchema tLCInsureAccClassSchema = null;
		LCInsureAccClassSchema tPubLCInsureAccClassSchema = null;
		//集体交费帐户分离
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		for(int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);
			tLPInsureAccClassSchema = new LPInsureAccClassSchema();
			ref.transFields(tLPInsureAccClassSchema, tLCInsureAccClassSchema);
			tLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setState("4");//退保失效
			tLPInsureAccClassSchema.setInsuAccBala(0);
			tLPInsureAccClassSchema.setBalaDate(mLPGrpEdorItemSchema.getEdorValiDate());
			tLPInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
			tLPInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
			tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
			
			String tPayPlanCode = tLCInsureAccClassSchema.getPayPlanCode();
			LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
			tLMDutyPayDB.setPayPlanCode(tPayPlanCode);
			if (!tLMDutyPayDB.getInfo()) {
				CError.buildErr(this, "查询责任缴费信息失败");
				return false; 
			}						
			String tPayAimClass = tLMDutyPayDB.getPayAimClass();
			if("2".equals(tPayAimClass)){
				
				//集体交费部分
				tPubLCInsureAccClassSchema = tLCInsureAccClassSchema;
				//处理集体交费部分返还被保人的部分,转入公共帐户的转移至外层统一处理,原因是公共帐户的交费目的是和个人交费目的一致的
				if("1".equals(tInsuGetFlag))
				{
					LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
					tLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
					tLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
					tLPAccMoveSchema.setPolNo(tLCInsureAccClassSchema.getPolNo());
					tLPAccMoveSchema.setInsuAccNo(tLCInsureAccClassSchema.getInsuAccNo());
					tLPAccMoveSchema.setPayPlanCode(tLCInsureAccClassSchema.getPayPlanCode());
					tLPAccMoveSchema.setRiskCode(tLCInsureAccClassSchema.getRiskCode());
					tLPAccMoveSchema.setAccType(tLCInsureAccClassSchema.getAccType());
					tLPAccMoveSchema.setAccMoveType("C");//转为现金的标识为C 转到公共帐户的标识为P
					tLPAccMoveSchema.setOtherNo(tLCInsureAccClassSchema.getPolNo());
					tLPAccMoveSchema.setOtherType(tLCInsureAccClassSchema.getOtherType());
					tLPAccMoveSchema.setAccAscription(tLCInsureAccClassSchema.getAccAscription());
					tLPAccMoveSchema.setAccMoveNo("000000");//转出现金
					tLPAccMoveSchema.setAccMoveCode("000000");
					tLPAccMoveSchema.setAccMoveBala(tReInsuCash);//集体帐户返给被保人的金额
					tLPAccMoveSchema.setSerialNoOut("000000");
					tLPAccMoveSchema.setSerialNoIn("000000");
					tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
					tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
					tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
					tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
					tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
					tLPAccMoveSet.add(tLPAccMoveSchema);
				}
				
			}else {
				//个人交费部分
				//生成个人交费部份的帐户的转移记录
				LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
				tLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tLPAccMoveSchema.setPolNo(tLCInsureAccClassSchema.getPolNo());
				tLPAccMoveSchema.setInsuAccNo(tLCInsureAccClassSchema.getInsuAccNo());
				tLPAccMoveSchema.setPayPlanCode(tLCInsureAccClassSchema.getPayPlanCode());
				tLPAccMoveSchema.setRiskCode(tLCInsureAccClassSchema.getRiskCode());
				tLPAccMoveSchema.setAccType(tLCInsureAccClassSchema.getAccType());
				tLPAccMoveSchema.setAccMoveType("C");//转为现金的标识为C 转到公共帐户的标识为P
				tLPAccMoveSchema.setOtherNo(tLCInsureAccClassSchema.getPolNo());
				tLPAccMoveSchema.setOtherType(tLCInsureAccClassSchema.getOtherType());
				tLPAccMoveSchema.setAccAscription(tLCInsureAccClassSchema.getAccAscription());
				tLPAccMoveSchema.setAccMoveNo("000000");//转出现金
				tLPAccMoveSchema.setAccMoveCode("000000");
				tLPAccMoveSchema.setAccMoveBala(tInsuGet);//个人帐户的现金价值,公共帐户的亦是如此
				tLPAccMoveSchema.setSerialNoOut("000000");
				tLPAccMoveSchema.setSerialNoIn("000000");
				tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
				tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
				tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
				tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
				tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
				tLPAccMoveSet.add(tLPAccMoveSchema);
				
			}
			
		}

		if(tPubGetFlag.equals("1"))
		{
			//转入公共帐户处理 前台控制住,不允许公共帐户勾选此标志,进入此流程的只能是个人单
			LCPolDB tComLCPolDB = new LCPolDB();
			tComLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tComLCPolDB.setPolTypeFlag("2");
			LCPolSchema tComLCPolSchema = tComLCPolDB.query().get(1);
			LCInsureAccClassDB tComLCInsureAccClassDB = new LCInsureAccClassDB();
			tComLCInsureAccClassDB.setPolNo(tComLCPolSchema.getPolNo());
			//普天有多个公共账户,SB们提出的SB解决方案
			if("86330020040220000454".equals(mLPGrpEdorItemSchema.getGrpContNo())){
				tComLCInsureAccClassDB.setPolNo("86330020040210024638");
			}

			LCInsureAccClassSchema tComLCInsureAccClassSchema = tComLCInsureAccClassDB.query().get(1);//公共帐户的分类明细只有一个
			LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
			tLPAccMoveSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPAccMoveSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPAccMoveSchema.setPolNo(tPubLCInsureAccClassSchema.getPolNo());
			tLPAccMoveSchema.setInsuAccNo(tPubLCInsureAccClassSchema.getInsuAccNo());
			tLPAccMoveSchema.setPayPlanCode(tPubLCInsureAccClassSchema.getPayPlanCode());
			tLPAccMoveSchema.setRiskCode(tPubLCInsureAccClassSchema.getRiskCode());
			tLPAccMoveSchema.setAccType(tPubLCInsureAccClassSchema.getAccType());
			tLPAccMoveSchema.setAccMoveType("P");//转至公共帐户
			tLPAccMoveSchema.setOtherNo(tComLCInsureAccClassSchema.getPolNo());
			tLPAccMoveSchema.setOtherType(tComLCInsureAccClassSchema.getOtherType());
			tLPAccMoveSchema.setAccAscription(tComLCInsureAccClassSchema.getAccAscription());
			tLPAccMoveSchema.setAccMoveNo(tComLCInsureAccClassSchema.getInsuAccNo());
			tLPAccMoveSchema.setAccMoveCode(tComLCInsureAccClassSchema.getPayPlanCode());
			tLPAccMoveSchema.setAccMoveBala(tRePubCash);
			tLPAccMoveSchema.setSerialNoOut("000000");
			tLPAccMoveSchema.setSerialNoIn("000000");
			tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
			tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
			tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
			tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
			tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
			tLPAccMoveSet.add(tLPAccMoveSchema);
		}
		
		map.put(tLPAccMoveSet, "DELETE&INSERT");
    	map.put(tLPContSchema, "DELETE&INSERT");
    	map.put(tLPPolSchema, "DELETE&INSERT");
    	map.put(tLPInsureAccSchema, "DELETE&INSERT");
    	map.put(tLPInsureAccClassSet, "DELETE&INSERT");
    	map.put(saveLPEdorItemSet, "DELETE&INSERT");
    	map.put(saveLPContStateSet, "DELETE&INSERT");
    	map.put(saveLPEdorMainSet, "DELETE&INSERT");
    	map.put(mLJSGetEndorseSet, "DELETE&INSERT");

    	mLPGrpEdorItemSchema.setEdorState("3");
    	mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
    	mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

    	map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    	mResult.clear();
    	mResult.add(map);

    	return true;
    }
    
    private boolean saveData() {
    	
  	  map.put("delete from lpgrppol where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
  	  map.put("delete from lpgrpcont where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
  	  map.put("delete from lpinsureacctrace where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"'", "DELETE");
  	  map.put("delete from lpgrpcontstate where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
  	  //准备团单数据
  	  LCGrpContDB tLCGrpContDB = new LCGrpContDB();
  	  tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  	  LCGrpContSchema tLCGrpContSchema = null;
  	  if(!tLCGrpContDB.getInfo())
  	  {
  		  CError.buildErr(this, "查询团单信息失败!");
  		  return false;
  	  }
  	  tLCGrpContSchema = tLCGrpContDB.getSchema();
  	  LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
  	  ref.transFields(tLPGrpContSchema, tLCGrpContSchema);
  	  tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	  tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	  tLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
  	  tLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());
  	  //准备团单险种数据GrpPol
  	  LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
  	  LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
  	  tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  	  tLCGrpPolDB.setAppFlag("1");
  	  LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
  	  LCGrpPolSchema tLCGrpPolSchema = null;
  	  LPGrpPolSchema tLPGrpPolSchema = null;
  	  if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1)
  	  {
  		  CError.buildErr(this, "查询团单险种信息失败!");
  		  return false;
  	  }
  	  for(int i = 1; i<=tLCGrpPolSet.size();i++)
  	  {
  		  tLCGrpPolSchema = tLCGrpPolSet.get(i);
  		  tLPGrpPolSchema = new LPGrpPolSchema();
  		  ref.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
  		  tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  		  tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  		  tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
  		  tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());

  		  int oldPeoples = 0;

  		  LCPolDB tLCPolDB = new LCPolDB();
  		  tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  		  tLCPolDB.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
  		  tLCPolDB.setAppFlag("1");
  		  LCPolSet tLCPolSet = tLCPolDB.query();
  		  oldPeoples = tLCPolSet.size();

  		  if(tLCPolSet == null || tLCPolSet.size()<1){
  			  //空险种
  			  continue;
  		  }
  		  int ZTPeoples = 0;

  		  LPPolDB tLPPolDB = new LPPolDB();
  		  tLPPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  		  tLPPolDB.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
  		  tLPPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  		  tLPPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  		  LPPolSet tLPPolSet = tLPPolDB.query();
  		  ZTPeoples = tLPPolSet.size();
  		  if(tLPPolSet == null || tLPPolSet.size()<1)
  		  {
  			  //险种没有退保的,跳过
  			  continue;
  		  }
  		  int newPeoples = oldPeoples-ZTPeoples;
  		  logger.debug("该险种退保后在保的人数为:"+newPeoples);
  		  tLPGrpPolSchema.setPeoples2(newPeoples);
  		  if(newPeoples == 0)
  		  {
  			tLPGrpPolSchema.setAppFlag("4");//都退保了,保单置为失效状态
  		  }
  		  tLPGrpPolSet.add(tLPGrpPolSchema);
  	  }
  	  int OldContPeoples = 0;//合同下在保人数
  	  int ZTContPeoples = 0;

  	  LCContDB tLCContDB = new LCContDB();
  	  tLCContDB.setAppFlag("1");
  	  tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  	  LCContSet tLCContSet = tLCContDB.query();
  	  if(tLCContSet == null || tLCContSet.size()<1)
  	  {
  		  CError.buildErr(this, "查询团单下个人有效合同信息失败!");
  		  return false;
  	  }
  	  OldContPeoples = tLCContSet.size();
  	  LPContDB tLPContDB = new LPContDB();

  	  tLPContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	  tLPContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	  tLPContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  	  LPContSet tLPContSet = tLPContDB.query();
  	  if(tLPContSet == null || tLPContSet.size()<1)
  	  {
  		  ZTContPeoples = 0;
  	  }else 
  	  {
  		  ZTContPeoples = tLPContSet.size();
  	  }

  	  int newContPeoples = OldContPeoples-ZTContPeoples;
  	  logger.debug("该团体保单减人后在保的人数为:"+newContPeoples);
  	  tLPGrpContSchema.setPeoples(newContPeoples);
  	  if(newContPeoples == 0)
  	  {
  		tLPGrpContSchema.setAppFlag("4");
  		saveLPGrpContStateSet.add(mGrpEdorCalZT.creatGrpContState(mLPGrpEdorItemSchema));
  		map.put(saveLPGrpContStateSet, "DELETE&INSERT");
  	  }
  	  //-----------------处理帐户转移信息,生成Trace -----------------
  	  //1先判断退保的数据里有无公共帐户
  	  //分离出公共帐户
  	  LCPolDB tComLCPolDB = new LCPolDB();
  	  tComLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  	  tComLCPolDB.setPolTypeFlag("2");
  	  LCPolSchema tComLCPolSchema = tComLCPolDB.query().get(1);
  	  LCInsureAccClassDB tComLCInsureAccClassDB = new LCInsureAccClassDB();
  	  tComLCInsureAccClassDB.setPolNo(tComLCPolSchema.getPolNo());
  	  if("86330020040220000454".equals(mLPGrpEdorItemSchema.getGrpContNo())){
  		  tComLCInsureAccClassDB.setPolNo("86330020040210024638");
  	  }
  	  
  	  LCInsureAccClassSchema tComLCInsureAccClassSchema = tComLCInsureAccClassDB.query().get(1);//公共帐户的分类明细只有一个
  	  LPInsureAccClassSchema tComLPInsureAccClassSchema = new LPInsureAccClassSchema();
  	  ref.transFields(tComLPInsureAccClassSchema, tComLCInsureAccClassSchema);
  	  tComLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	  tComLPInsureAccClassSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	  
  	  boolean tPubTBFlag = false;
  	  LPPolDB tPubLPPolDB = new LPPolDB();
  	  tPubLPPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	  tPubLPPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	  tPubLPPolDB.setPolTypeFlag("2");
  	  if("86330020040220000454".equals(mLPGrpEdorItemSchema.getGrpContNo())){
  		tPubLPPolDB.setPolNo("86330020040210024638");
  	  }
  	  LPPolSet tPubLPPolSet = tPubLPPolDB.query();
  	  if(tPubLPPolSet != null && tPubLPPolSet.size()>0)
  	  {
  		tPubTBFlag = true;
  	  }
  	  
  	  LPAccMoveSet saveLPAccMoveSet = new LPAccMoveSet();
  	  LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
  	  LPAccMoveDB tLPAccMoveDB = new LPAccMoveDB();
  	  tLPAccMoveDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	  tLPAccMoveDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	  LPAccMoveSet tLPAccMoveSet = tLPAccMoveDB.query();
  	  LPAccMoveSchema tLPAccMoveSchema = new LPAccMoveSchema();
  	  LPAccMoveSchema tComLPAccMoveSchema = new LPAccMoveSchema();
  	  double dToAccMoney = 0.0;
  	  for(int i = 1;i<=tLPAccMoveSet.size();i++)
  	  {
  		  tLPAccMoveSchema = tLPAccMoveSet.get(i);
  		  if("C".equals(tLPAccMoveSchema.getAccMoveType()))
  		  {
  			  if(tLPAccMoveSchema.getPolNo().equals(tComLCPolSchema.getPolNo()))
  			  {
  				  tComLPAccMoveSchema = tLPAccMoveSchema;//公共帐户的后面单独处理
  				  continue;
  			  }
  			  //转为现金的记录
  			  LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
  			  tLPInsureAccClassDB.setEdorNo(tLPAccMoveSchema.getEdorNo());
  			  tLPInsureAccClassDB.setEdorType(tLPAccMoveSchema.getEdorType());
  			  tLPInsureAccClassDB.setPolNo(tLPAccMoveSchema.getPolNo());
  			  tLPInsureAccClassDB.setInsuAccNo(tLPAccMoveSchema.getInsuAccNo());
  			  tLPInsureAccClassDB.setPayPlanCode(tLPAccMoveSchema.getPayPlanCode());
  			  LPInsureAccClassSchema tLPInsureAccClassSchema = tLPInsureAccClassDB.query().get(1);
  			  LPInsureAccTraceSchema tLPInsureAccTraceSchema = initLPInsureAccTrace(tLPInsureAccClassSchema, -(tLPAccMoveSchema.getAccMoveBala()));
  			  tLPInsureAccTraceSchema.setMoneyType("AC");//帐户转现金标识
  			  tLPAccMoveSchema.setSerialNoOut(tLPInsureAccTraceSchema.getSerialNo());
  			  saveLPAccMoveSet.add(tLPAccMoveSchema);
  			  tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
  			  
  		  } else if("P".equals(tLPAccMoveSchema.getAccMoveType())){
  			  //转入公共帐户的
  			  LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
  			  tLPInsureAccClassDB.setEdorNo(tLPAccMoveSchema.getEdorNo());
  			  tLPInsureAccClassDB.setEdorType(tLPAccMoveSchema.getEdorType());
  			  tLPInsureAccClassDB.setPolNo(tLPAccMoveSchema.getPolNo());
  			  tLPInsureAccClassDB.setInsuAccNo(tLPAccMoveSchema.getInsuAccNo());
  			  tLPInsureAccClassDB.setPayPlanCode(tLPAccMoveSchema.getPayPlanCode());
  			  LPInsureAccClassSchema tLPInsureAccClassSchema = tLPInsureAccClassDB.query().get(1);
  			  LPInsureAccTraceSchema outLPInsureAccTraceSchema = initLPInsureAccTrace(tLPInsureAccClassSchema, -(tLPAccMoveSchema.getAccMoveBala()));
  			  outLPInsureAccTraceSchema.setMoneyType("AP");
  			  //生成公共帐户的转入的Trace
  			  LPInsureAccTraceSchema inLPInsureAccTraceSchema = initLPInsureAccTrace(tComLPInsureAccClassSchema, tLPAccMoveSchema.getAccMoveBala());
  			  inLPInsureAccTraceSchema.setMoneyType("AI");
  			  tLPAccMoveSchema.setSerialNoOut(outLPInsureAccTraceSchema.getSerialNo());
  			  tLPAccMoveSchema.setSerialNoIn(inLPInsureAccTraceSchema.getSerialNo());
  			  tLPInsureAccTraceSet.add(outLPInsureAccTraceSchema);
  			  tLPInsureAccTraceSet.add(inLPInsureAccTraceSchema);
  			  saveLPAccMoveSet.add(tLPAccMoveSchema);
  			  //转入公共帐户的进行统计
  			  dToAccMoney+=tLPAccMoveSchema.getAccMoveBala();
  		  }
  	  }
  	  if(tPubTBFlag)
  	  {
  		  LPEdorItemDB tComLPEdorItemDB = new LPEdorItemDB();
  		  tComLPEdorItemDB.setEdorNo(tLPAccMoveSchema.getEdorNo());
  		  tComLPEdorItemDB.setEdorType(tLPAccMoveSchema.getEdorType());
  		  tComLPEdorItemDB.setPolNo(tLPAccMoveSchema.getPolNo());
  		  LPEdorItemSchema tComLPEdorItemSchema = tComLPEdorItemDB.query().get(1);
  		  //更新补退费记录
  		  LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
  		  tLJSGetEndorseDB.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
  		  tLJSGetEndorseDB.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
  		  tLJSGetEndorseDB.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
  		  tLJSGetEndorseDB.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
  		  tLJSGetEndorseDB.setPolNo(tComLCPolSchema.getPolNo());
  		  LJSGetEndorseSchema tComLJSGetEndorseSchema = tLJSGetEndorseDB.query().get(1);
  		  double selfMoney = tComLPEdorItemSchema.getGetMoney();
  		  tComLJSGetEndorseSchema.setGetMoney(selfMoney+dToAccMoney);
  		  map.put(tComLJSGetEndorseSchema, "DELETE&INSERT");

  		  //更新公共帐户转出现金ACCMOVE,转移的金额为个人帐户转入公共帐户的+自身原有的
  		  tComLPAccMoveSchema.setAccMoveBala(selfMoney+dToAccMoney);
  		  //还要生成trace
  		  LPInsureAccTraceSchema tLPInsureAccTraceSchema = initLPInsureAccTrace(tComLPInsureAccClassSchema, -tComLPAccMoveSchema.getAccMoveBala());
  		  tLPInsureAccTraceSchema.setMoneyType("AC");//帐户转现金标识
  		  tComLPAccMoveSchema.setSerialNoOut(tLPInsureAccTraceSchema.getSerialNo());
  		  saveLPAccMoveSet.add(tLPAccMoveSchema);
  		  tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);


  	  }
  	  map.put(tLPGrpContSchema, "DELETE&INSERT");
  	  map.put(tLPGrpPolSet, "DELETE&INSERT");
  	  map.put(saveLPAccMoveSet, "DELETE&INSERT");
  	  map.put(tLPInsureAccTraceSet, "DELETE&INSERT");
  	  mLPGrpEdorItemSchema.setEdorState("1");
  	  mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
  	  mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

  	  map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");

  	  mResult.clear();
  	  mResult.add(map);

  	  return true;
    }

    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean deleteData()
    {
        //按个人保全主表进行处理
    	String edorno = mLPGrpEdorItemSchema.getEdorNo();
    	String edortype = mLPGrpEdorItemSchema.getEdorType();
    	String sqlWhere = " edorno = '" + edorno + "' and edortype = '" + edortype + "'";
        for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
        	
    		map.put("delete from LPEdorMain where contno = "+mLPEdorItemSet.get(i).getContNo()+" and edorno = '" + edorno + "' ","DELETE");
    		map.put("delete from LPEdorItem where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from lppol where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from lpcont where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from lpinsured where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPInsureAcc where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPInsureAccClass where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPInsureAccTrace where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPContState where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LJSGetEndorse " + " where EndorsementNo='" + edorno + "' and FeeOperationType='" + edortype + "' and  contno = '"+mLPEdorItemSet.get(i).getContNo()+"'", "DELETE");
    		map.put("delete from LPAccMove where polno in (select polno from lcpol where contno = '"+mLPEdorItemSet.get(i).getContNo()+"') and " + sqlWhere, "DELETE");
        }
        
    	mLPGrpEdorItemSchema.setEdorState("3");
    	mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
    	mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
    	  
    	map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    	
        mResult.clear();
        mResult.add(map);

        return true;
    }
    
    private LPInsureAccTraceSchema initLPInsureAccTrace(LPInsureAccClassSchema aLPInsureAccClassSchema, double aAccBalance)
    {
    	LPInsureAccTraceSchema aLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
    	ref.transFields(aLPInsureAccTraceSchema, aLPInsureAccClassSchema);
    	aLPInsureAccTraceSchema.setMakeDate(mLPGrpEdorItemSchema.getEdorValiDate());
    	aLPInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
    	aLPInsureAccTraceSchema.setManageCom(mGlobalInput.ManageCom);
    	aLPInsureAccTraceSchema.setModifyDate(mLPGrpEdorItemSchema.getEdorValiDate());
    	aLPInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
    	aLPInsureAccTraceSchema.setMoney(aAccBalance);
    	aLPInsureAccTraceSchema.setMoneyType("TF");
    	aLPInsureAccTraceSchema.setFeeCode("000000");
    	aLPInsureAccTraceSchema.setPayDate(mLPGrpEdorItemSchema.getEdorValiDate());
    	aLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
    	aLPInsureAccTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
    			PubFun.getNoLimit(mGlobalInput.ManageCom)));
    	aLPInsureAccTraceSchema.setState("");
    	aLPInsureAccTraceSchema.setUnitCount(0);
    	return aLPInsureAccTraceSchema;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
