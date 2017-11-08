package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:投连账户追加保费
 * </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx
 * @version 1.0
 */
public class PEdorIPDetailBL implements BusinessService{
private static Logger logger = Logger.getLogger(PEdorIPDetailBL.class);

    public CErrors mErrors = new CErrors();
    private VData mInputData;
    private VData mResult = new VData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private MMap map = new MMap();
    private ExeSQL mExeSQL = new ExeSQL();
    private String mOperate;
    public String CurrentDate = PubFun.getCurrentDate();
    public String CurrentTime = PubFun.getCurrentTime();
    
    private Reflections mRef = new Reflections();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet ();
    private LPInsureAccFeeTraceSet mLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet ();
    private LPPerInvestPlanSet mLPPerInvestPlanSet = new LPPerInvestPlanSet();
    private LPPerInvestPlanSet sLPPerInvestPlanSet = new LPPerInvestPlanSet();





    public boolean submitData(VData cInputData, String cOperate) {
    	//将操作数据拷贝到本类中
    	mInputData = (VData) cInputData.clone();
    	this.mOperate = cOperate;

    	if(!getInputData(cInputData)){
    		return false;
    	}
    	if (!checkData())
    	{
    		return false;
    	}
    	if (!dealData())
    	{
    		return false;
    	}
    	
        mResult.clear();
        mResult.add(map);
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "GEdorDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
    	return true;
    }
    

    private boolean getInputData(VData cInputData) {

    	 try
         {
             mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
             mLPEdorItemSchema = (LPEdorItemSchema)mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
             mLPPerInvestPlanSet = (LPPerInvestPlanSet)mInputData.getObjectByObjectName("LPPerInvestPlanSet", 0);
         }
         catch (Exception e)
         {
             CError.buildErr(this, "接收前台数据失败!");
             return false;
         }

         
         return true;
    }


    private boolean checkData() {
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setSchema(mLPEdorItemSchema);
        tLPEdorItemSet = tLPEdorItemDB.query();
        if (tLPEdorItemSet.size() == 0) {
            CError.buildErr(this, "无保全项目数据!");
            return false;
        }
        mLPEdorItemSchema = tLPEdorItemSet.get(1);
        if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
            CError.buildErr(this, "该保全项目已经申请确认不能修改!");
            return false;
        }
        return true;
    }

    private boolean dealData() {
    	
		String tDelSql = "delete from LJSPayPerson where GetNoticeNo = '?GetNoticeNo?' ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tDelSql);
		sbv1.put("GetNoticeNo", mLPEdorItemSchema.getEdorNo());
		map.put(sbv1, "DELETE");
		tDelSql = "delete from LJSGetEndorse where EndorsementNo = '?EndorsementNo?' ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tDelSql);
		sbv2.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		map.put(sbv2, "DELETE");
		tDelSql = "delete from LPInsureAccTrace where EdorNo = '?EndorsementNo?' ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(tDelSql);
		sbv3.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		map.put(sbv3, "DELETE");
		tDelSql = "delete from LPInsureAccFeeTrace where EdorNo = '?EndorsementNo?' ";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(tDelSql);
		sbv4.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		map.put(sbv4, "DELETE");
		
    	if(mOperate.equals("EDORITEM|INPUT")){
    		if(!dealInputData()){
    			return false;
    		}
    	}else if(mOperate.equals("EDORITEM|DELETE")){
    		if(!dealDeleteData()){
    			return false;
    		}
    	}else if(mOperate.equals("EDORITEM|SAVE")){
    		if(!dealSaveData()){
    			return false;
    		}
    	}else{
			CError.buildErr(this, "不识别的操作类型！");
			return false;
    	}
    	if(!mOperate.equals("EDORITEM|SAVE")){  		
    		mLPEdorItemSchema.setEdorState("3");
    		map.put(mLPEdorItemSchema, "UPDATE");
    	}else{
    		mLPEdorItemSchema.setEdorState("1");
    		map.put(mLPEdorItemSchema, "UPDATE");
    	}
    	 return true;
    }
    
    

    public VData getResult() {
        return mResult;
    }
    
    private boolean dealInputData(){
    	LCPerInvestPlanDB tLCPerInvestPlanDB = null;
    	LCPerInvestPlanSchema tLCPerInvestPlanSchema = null;
    	LPPerInvestPlanSchema tLPPerInvestPlanSchema = null;
    	LPPerInvestPlanSchema sLPPerInvestPlanSchema = null;
    	for(int i=1;i<=mLPPerInvestPlanSet.size();i++)
    	{
    		tLPPerInvestPlanSchema = mLPPerInvestPlanSet.get(i);
    		tLCPerInvestPlanDB = new LCPerInvestPlanDB();
    		tLCPerInvestPlanDB.setPolNo(tLPPerInvestPlanSchema.getPolNo());
    		tLCPerInvestPlanDB.setInsuAccNo(tLPPerInvestPlanSchema.getInsuAccNo());
    		tLCPerInvestPlanDB.setPayPlanCode(tLPPerInvestPlanSchema.getPayPlanCode());
    		if(!tLCPerInvestPlanDB.getInfo()){
    			CError.buildErr(this, "查询账户信息失败");
    			return false;
    		}
    		tLCPerInvestPlanSchema = tLCPerInvestPlanDB.getSchema();
    		sLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
    		
    		mRef.transFields(sLPPerInvestPlanSchema, tLCPerInvestPlanSchema);
    		sLPPerInvestPlanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		sLPPerInvestPlanSchema.setEdorType(mLPEdorItemSchema.getEdorType());
    		sLPPerInvestPlanSchema.setInvestMoney(tLPPerInvestPlanSchema.getInvestMoney());
    		sLPPerInvestPlanSchema.setModifyOperator(mGlobalInput.Operator);
    		sLPPerInvestPlanSchema.setModifyDate(CurrentDate);
    		sLPPerInvestPlanSchema.setModifyTime(CurrentTime);
    		sLPPerInvestPlanSet.add(sLPPerInvestPlanSchema);
    	}
    	map.put(sLPPerInvestPlanSet, "DELETE&INSERT");
    	 tLCPerInvestPlanDB = null;
    	tLCPerInvestPlanSchema = null;
    	tLPPerInvestPlanSchema = null;
    	sLPPerInvestPlanSchema = null;
    	return true;
    }
    
    private boolean dealSaveData(){
    	String tSql = "Select distinct PolNo from LPPerInvestPlan where ContNo = '?ContNo?' and EdorNo = '?EdorNo?' and EdorType = '?EdorType?'";
    	SQLwithBindVariables sbv1=new SQLwithBindVariables();
    	sbv1.sql(tSql);
    	sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
    	sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
    	sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
    	SSRS tPolNoSet = mExeSQL.execSQL(sbv1);
    	if(tPolNoSet== null ||tPolNoSet.getMaxRow()<1){
    		CError.buildErr(this, "没有追加记录！请先追加！");
    		return false;
    	}
    	for(int i=1;i<=tPolNoSet.getMaxRow();i++){
    		String tPolNo = tPolNoSet.GetText(i, 1);
    		LCPolDB tLCPolDB = new LCPolDB();
    		tLCPolDB.setPolNo(tPolNo);
    		if(!tLCPolDB.getInfo()){
    			CError.buildErr(this, "查询险种信息失败！");
    			return false;
    		}
    		mLCPolSchema = tLCPolDB.getSchema();
    		//1、生成LJSPayPerson,LJSGetEndorse
    		String tSQL = "select a.dutycode, b.payplancode, b.currency,sum(b.investmoney) from lcprem a, LPPerInvestPlan b where a.polno = b.polno "
    					+ " and a.payplancode = b.payplancode and b.polno = '?tPolNo?'"
    					+ " and b.edorno = '?edorno?'"
    					+ " group by a.dutycode, b.payplancode, b.currency";
    		SQLwithBindVariables sbv2=new SQLwithBindVariables();
    		sbv2.sql(tSQL);
    		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
    		sbv2.put("tPolNo", tPolNo);
    		SSRS tSSRS = mExeSQL.execSQL(sbv2);
        	if(tSSRS== null ||tSSRS.getMaxRow()<1){
        		CError.buildErr(this, "查询追加记录失败！");
        		return false;
        	}
    		for(int n=1;n<=tSSRS.getMaxRow();n++){
    			String tDutyCode = tSSRS.GetText(n, 1);
    			String tPayPlanCode = tSSRS.GetText(n, 2);
    			String tCurrency = tSSRS.GetText(n, 3);
    			String tAddMoney = tSSRS.GetText(n, 4);
    			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();;
    		    tLJSPayPersonSchema.setPolNo(mLCPolSchema.getPolNo());
    		    
    		    LJAPayPersonDB lzLJAPayDB = new LJAPayPersonDB();
    		    String SQL = "select * from LJAPayPerson where polno = '?tPolNo?' and dutycode = '?tDutyCode?' and payplancode = '?tPayPlanCode?' order by paycount DESC";	    
    		    SQLwithBindVariables sbv3=new SQLwithBindVariables();
    		    sbv3.sql(SQL);
    		    sbv3.put("tPolNo", tPolNo);
    		    sbv3.put("tDutyCode", tDutyCode);
    		    sbv3.put("tPayPlanCode", tPayPlanCode);
    		    tLJSPayPersonSchema.setPayCount(lzLJAPayDB.executeQuery(sbv3).get(1).getPayCount()+1);//暂时,PK
    		    tLJSPayPersonSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
    		    tLJSPayPersonSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
    		    tLJSPayPersonSchema.setContNo(mLCPolSchema.getContNo());
    		    tLJSPayPersonSchema.setManageCom(mLCPolSchema.getManageCom());
    		    tLJSPayPersonSchema.setAgentCom(mLCPolSchema.getAgentCom());
    		    tLJSPayPersonSchema.setAgentType(mLCPolSchema.getAgentType());
    		    tLJSPayPersonSchema.setRiskCode(mLCPolSchema.getRiskCode());
    		    tLJSPayPersonSchema.setAgentCode(mLCPolSchema.getAgentCode());
    		    tLJSPayPersonSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
    		    tLJSPayPersonSchema.setCurrency(tCurrency);
    		    tLJSPayPersonSchema.setPayTypeFlag("2");//暂时
    		    tLJSPayPersonSchema.setAppntNo(mLCPolSchema.getAppntNo());
    		    tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
    		    tLJSPayPersonSchema.setPayAimClass("1");//暂时,PK
    		    tLJSPayPersonSchema.setDutyCode(tDutyCode);//暂时,PK
    		    tLJSPayPersonSchema.setPayPlanCode(tPayPlanCode);//暂时,PK
    		    tLJSPayPersonSchema.setSumDuePayMoney(tAddMoney);
    		    tLJSPayPersonSchema.setPayType("IP");
    			tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
    			tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
    			tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
    			tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
    			tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
    			tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
    			tLJSPayPersonSchema.setBankAccNo("");
    			tLJSPayPersonSchema.setBankCode("");
    			tLJSPayPersonSchema.setPayIntv("-1");
    			tLJSPayPersonSchema.setMainPolYear(PubFun.calInterval(mLCPolSchema.getCValiDate(), mLPEdorItemSchema.getEdorValiDate(), "Y")+1);
    			tLJSPayPersonSchema.setSumActuPayMoney(tAddMoney);
    			tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
    			tLJSPayPersonSchema.setCurPayToDate(mLCPolSchema.getPaytoDate());
    			//营改增 add zhangyingfeng 2016-07-13
    			//价税分离 计算器
    			TaxCalculator.calBySchema(tLJSPayPersonSchema);
    			//end zhangyingfeng 2016-07-13
    			mLJSPayPersonSet.add(tLJSPayPersonSchema);
    			
    			LJSGetEndorseSchema tLJSGetEndorseSchema = initLJSGetEndorse(tLJSPayPersonSchema,"BF");
    			//营改增 add zhangyingfeng 2016-07-13
    			//价税分离 计算器
    			TaxCalculator.calBySchema(tLJSGetEndorseSchema);
    			//end zhangyingfeng 2016-07-13
    			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
    		}
    		//2、生成LPInsureacctrace,LPInsureAccFeeTrace
    		LPPerInvestPlanDB tLPPerInvestPlanDB =  new LPPerInvestPlanDB();
    		tLPPerInvestPlanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		tLPPerInvestPlanDB.setEdorType(mLPEdorItemSchema.getEdorType());
    		tLPPerInvestPlanDB.setPolNo(tPolNo);
    		LPPerInvestPlanSet tLPPerInvestPlanSet = tLPPerInvestPlanDB.query();
    		if(tLPPerInvestPlanSet == null || tLPPerInvestPlanSet.size()<1){
    			CError.buildErr(this, "查询追加记录失败！");
    			return false;
    		}
    		LPPerInvestPlanSchema tLPPerInvestPlanSchema = null;
    		LMRiskFeeDB tLMRiskFeeDB = null;
    		LMRiskFeeSchema tLMRiskFeeSchema = null;
    		LPInsureAccTraceSchema tLPInsureAccTraceSchema = null;
    		for(int n = 1;n<=tLPPerInvestPlanSet.size();n++){
    			tLPPerInvestPlanSchema = tLPPerInvestPlanSet.get(n);
    			double dAddMoney = tLPPerInvestPlanSchema.getInvestMoney();
    			tLMRiskFeeDB = new LMRiskFeeDB();
    			tLMRiskFeeDB.setInsuAccNo(tLPPerInvestPlanSchema.getInsuAccNo());
    			tLMRiskFeeDB.setPayPlanCode(tLPPerInvestPlanSchema.getPayPlanCode());
    			tLMRiskFeeDB.setFeeTakePlace("01");//保费初始费用
    			tLMRiskFeeSchema = tLMRiskFeeDB.query().get(1);
    			if(tLMRiskFeeSchema == null){
    	            CError.buildErr(this, "没有查到退保区分类型计算代码!");
    	            return false;
    			}
    			String tCalCode = tLMRiskFeeSchema.getFeeCalCode();
    			Calculator tCalculator = new Calculator();
    	        tCalculator.setCalCode(tCalCode);
    	        tCalculator.addBasicFactor("SumPrem",String.valueOf(dAddMoney));
    	        String tManageFee = tCalculator.calculate();
    	        double dManageFee = 0.0;
    	        if(tManageFee!=null && !tManageFee.equals("")){
    	        	dManageFee = Double.parseDouble(tManageFee);
    	        }
    	        double actuAddMoney = dAddMoney -dManageFee;
    	        
    	        String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
    	        String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
    	        
    	        tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
    	        tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    	        tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
    	        tLPInsureAccTraceSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
    	        tLPInsureAccTraceSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
    	        tLPInsureAccTraceSchema.setContNo(mLCPolSchema.getContNo());
    	        tLPInsureAccTraceSchema.setPolNo(mLCPolSchema.getPolNo());
    	        tLPInsureAccTraceSchema.setSerialNo(tSerialNo);//待定
    	        tLPInsureAccTraceSchema.setInsuAccNo(tLPPerInvestPlanSchema.getInsuAccNo());
    	        tLPInsureAccTraceSchema.setRiskCode(mLCPolSchema.getRiskCode());
    	        tLPInsureAccTraceSchema.setPayPlanCode(tLPPerInvestPlanSchema.getPayPlanCode());
    	        tLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo()); //待定
    	        tLPInsureAccTraceSchema.setOtherType("2");//待定
    	        tLPInsureAccTraceSchema.setAccAscription("1");
    	        tLPInsureAccTraceSchema.setMoneyType("BF");
    	        tLPInsureAccTraceSchema.setMoney(actuAddMoney);
    	        tLPInsureAccTraceSchema.setState("0");
    	        tLPInsureAccTraceSchema.setManageCom(mLCPolSchema.getManageCom());
    	        tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
    	        tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
    	        tLPInsureAccTraceSchema.setMakeDate(CurrentDate);
    	        tLPInsureAccTraceSchema.setMakeTime(CurrentTime);
    	        tLPInsureAccTraceSchema.setModifyDate(CurrentDate);
    	        tLPInsureAccTraceSchema.setModifyTime(CurrentTime);
    	        tLPInsureAccTraceSchema.setFeeCode("000000");
    	        tLPInsureAccTraceSchema.setBusyType(mLPEdorItemSchema.getEdorType()); //账户转换
    	        tLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo());
    	        tLPInsureAccTraceSchema.setAccAlterType("3");
    	        tLPInsureAccTraceSchema.setBusyType("IP");
    	        tLPInsureAccTraceSchema.setCurrency(tLPPerInvestPlanSchema.getCurrency());
    	        mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
    	        if(dManageFee>0){
    	        	LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
    	        	mRef.transFields(tLPInsureAccFeeTraceSchema, tLPInsureAccTraceSchema);
    	        	tLPInsureAccFeeTraceSchema.setFee(dManageFee);
    	        	tLPInsureAccFeeTraceSchema.setMoneyType("GL");
    	        	tLPInsureAccFeeTraceSchema.setFeeCode(tLMRiskFeeSchema.getFeeCode());
    	        	tLPInsureAccFeeTraceSchema.setState("0");
    	        	mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
    	        }
    		}
    	}
    	
    	map.put(mLJSPayPersonSet, "INSERT");
    	map.put(mLJSGetEndorseSet, "INSERT");
    	map.put(mLPInsureAccTraceSet, "INSERT");
		//add zhangyingfeng 2016-08-03
		//营改增 价税分离计算器
		TaxCalculator.calBySchemaSet(mLPInsureAccFeeTraceSet);
		//end zhangyingfeng 2016-08-03
    	map.put(mLPInsureAccFeeTraceSet, "INSERT");
    	return true;
    }
    
    private boolean dealDeleteData(){
    	LPPerInvestPlanDB tLPPerInvestPlanDB = null;
    	LPPerInvestPlanSchema tLPPerInvestPlanSchema = null;
    	for(int i=1;i<=mLPPerInvestPlanSet.size();i++)
    	{
    		tLPPerInvestPlanSchema = mLPPerInvestPlanSet.get(i);
    		tLPPerInvestPlanDB = new LPPerInvestPlanDB();
    		tLPPerInvestPlanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		tLPPerInvestPlanDB.setEdorType(mLPEdorItemSchema.getEdorType());
    		tLPPerInvestPlanDB.setPolNo(tLPPerInvestPlanSchema.getPolNo());
    		tLPPerInvestPlanDB.setInsuAccNo(tLPPerInvestPlanSchema.getInsuAccNo());
    		tLPPerInvestPlanDB.setPayPlanCode(tLPPerInvestPlanSchema.getPayPlanCode());
    		if(!tLPPerInvestPlanDB.getInfo()){
    			CError.buildErr(this, "查询账户信息失败");
    			return false;
    		}
    		tLPPerInvestPlanSchema = tLPPerInvestPlanDB.getSchema();
    		sLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);
    	}
    	map.put(sLPPerInvestPlanSet, "DELETE");
    	tLPPerInvestPlanDB = null;
    	tLPPerInvestPlanSchema = null;
    	return true;
    }

    
    private LJSGetEndorseSchema initLJSGetEndorse(LJSPayPersonSchema tLJSPayPersonSchema,String strfinType){
    	LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
    	tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
    	String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), strfinType, mLPEdorItemSchema.getPolNo());
    	mRef.transFields(tLJSGetEndorseSchema, mLCPolSchema);
    	mRef.transFields(tLJSGetEndorseSchema, tLJSPayPersonSchema);
    	tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
    	tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
    	if (finType==null || finType.equals("")){
    		CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
    		return null;
    	}
    	tLJSGetEndorseSchema.setFeeFinaType(finType);
    	tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
    	tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
    	tLJSGetEndorseSchema.setOtherNo(mLCPolSchema.getPolNo());
    	tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
    	tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
    	tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
    	tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
    	tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
    	tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
    	tLJSGetEndorseSchema.setGetMoney(tLJSPayPersonSchema.getSumDuePayMoney());
    	tLJSGetEndorseSchema.setGetFlag("0");
    	tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem + String.valueOf(mLJSPayPersonSet.get(1).getPayCount()));
    	return tLJSGetEndorseSchema;
    }

	public CErrors getErrors() {
		return mErrors;
	}
 
    

}


