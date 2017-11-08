package com.sinosoft.lis.cbcheck;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMElement;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LMDutyPayAddFeeDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LMDutyPayAddFeeSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BQAddPremCalBL {
private static Logger logger = Logger.getLogger(BQAddPremCalBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String theDate = "";
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private List tBomList = new ArrayList();
	// 险种加费表
	private LMDutyPayAddFeeSchema mLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	// 保单表
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	// 被保险人信息表
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	// 被保险人信息表
	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
	// 保费项表
	private LPPremSchema mLPPremSchema = new LPPremSchema();
	/** 保全重算后的标准保费  */
	private String mNonAddFeePrem = "";
	// 责任表
	private LPDutySchema mLPDutySchema = new LPDutySchema();
	private String mUWFlag = "";
	private CalBase mCalBase = new CalBase();

	// 时间
	private FDate fDate = new FDate();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	String tStr = "";
	private String mCalCode;// 计算编码
	double mValue = 0.0;
	private String mInsuredNo;
	private String mSex;//被保人性别
	private String mOccupationType;
	/** 加费类型*/
	private String mPayPlanType;
	/** 加费方式*/
	private String mAddFeedirect;
	/** 加费评点*/
	private double mSuppriskScore;
	private String ifAddFeeCalCode="1";//该险种是否有加费算法

	public BQAddPremCalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		logger.debug(tStr);
		mTransferData.setNameAndValue("mValue", tStr);
		mResult.add(mTransferData);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		double FirstScore = mLPPremSchema.getSuppRiskScore();
		double SecondScore = mLPPremSchema.getSecInsuAddPoint();
		LMDutyPayAddFeeDB tLMDutyPayAddFeeDB = new LMDutyPayAddFeeDB();
		tLMDutyPayAddFeeDB.setSchema(this.mLMDutyPayAddFeeSchema);
		if(!tLMDutyPayAddFeeDB.getInfo()){
			logger.debug("该险种没有加费算法！");
			ifAddFeeCalCode="0";
		}
		//???此处需要业管讨论完毕后,确认是否需要校验核保师的加费评点!!!
		//先注释掉~
		/*
		double tAddPoint = tLDUWUserDB.getAddPoint();
		if ((FirstScore > tAddPoint) || (SecondScore > tAddPoint)) {
			CError.buildErr(this,"加费评点过高，超过该核保师的权限范围!");
			return false;

		}
		*/

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLMDutyPayAddFeeSchema = (LMDutyPayAddFeeSchema) cInputData
				.getObjectByObjectName("LMDutyPayAddFeeSchema", 0);
		mLPPolSchema = (LPPolSchema) cInputData.getObjectByObjectName(
				"LPPolSchema", 0);
		mLPPremSchema = (LPPremSchema) cInputData.getObjectByObjectName(
				"LPPremSchema", 0);
		mLPDutySchema = (LPDutySchema) cInputData.getObjectByObjectName(
				"LPDutySchema", 0);
		mLPInsuredSchema = (LPInsuredSchema) cInputData.getObjectByObjectName(
				"LPInsuredSchema", 0);
		TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);//区分人工核保和其他调用该程序的标志，人工核保调用不为空
		if(tTransferData == null)
			mUWFlag = "";
		else
			mUWFlag = (String)tTransferData.getValueByName("UWFlag");
			
		mNonAddFeePrem = (String) cInputData.getObjectByObjectName("String", 0);
		mPayPlanType = mLPPremSchema.getPayPlanType();
		mAddFeedirect = mLPPremSchema.getAddFeeDirect();
		mSuppriskScore = mLPPremSchema.getSuppRiskScore();
		mInsuredNo = mLPPolSchema.getInsuredNo();

		mInputData = cInputData;
		if (mInsuredNo == null || mInsuredNo.equals("")) {
			CError.buildErr(this,"前台传输被保险人mInsuredNo失败!");
			return false;

		}
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据mOperator失败!");
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据mOperator失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		ExeSQL ttExeSQL = new ExeSQL();
		mPayPlanType = mLPPremSchema.getPayPlanType();
		mAddFeedirect = mLPPremSchema.getAddFeeDirect();
		mSuppriskScore = mLPPremSchema.getSuppRiskScore();
		//如果如果lmdutypayaddfee表中没有该险种加费的公式则算C表中的加费比例算出加费
		//按照 "C表加费/C表标准保费*保全重算后的标准保费=加费"  来计算
		if("0".equals(ifAddFeeCalCode)){
//			LCPremDB tLCPremDB = new LCPremDB();
			//获得C表标准保费
			String tSumPremSql="select sum(prem) from lcprem where polno='"+"?polno?"
							+"' and dutycode='"+"?dutycode?"
							+"' and payplancode not like '000000%' and payplantype='0'";
			//获得C表加费
			String tOldAddPremSql="select sum(prem) from lcprem where polno='"+"?polno?"
							+"' and dutycode='"+"?dutycode?"
							+"' and payplancode like '000000%' and payplantype='"+"?payplantype?"+"'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSumPremSql);
			sqlbv.put("polno", mLPPremSchema.getPolNo());
			sqlbv.put("dutycode", mLPPremSchema.getDutyCode());
			Double tDouble = new Double(ttExeSQL.getOneValue(sqlbv));
			
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tOldAddPremSql);
			sqlbv1.put("polno", mLPPremSchema.getPolNo());
			sqlbv1.put("dutycode", mLPPremSchema.getDutyCode());
			sqlbv1.put("payplantype", mPayPlanType);
			Double ttDouble = new Double(ttExeSQL.getOneValue(sqlbv1));
			double tOldAddPrem = ttDouble.doubleValue();//C表加费
			double tSumPrem = tDouble.doubleValue();//C表标准保费
//			double tPrem = mLPPremSchema.getPrem();//重算后的标准保费
			double tPrem = Double.valueOf((mNonAddFeePrem)).doubleValue();//重算后的标准保费
			//计算加费
			tStr = String.valueOf((tOldAddPrem/tSumPrem)*tPrem);
		}else{
			if (!prepareCalBase())
				return false;
	
			if (!prepareCalculate())
				return false;
		}
		return true;
	}

	private boolean prepareCalBase() {
		// 准备保单险种表的信息
		//2009-04-28 如从外面传入数据，不用重查，防止别的程序报错
		LPDutyDB tLPDutyDB = new LPDutyDB();
		if(!mUWFlag.equals("") && mUWFlag.equals("UW"))
		{
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setPolNo(mLPPolSchema.getPolNo());
			tLPPolDB.setEdorType(mLPPolSchema.getEdorType());
			tLPPolDB.setEdorNo(mLPPolSchema.getEdorNo());
			if (!tLPPolDB.getInfo()) {
				CError.buildErr(this,"查询LCPol表失败!");
				return false;
			}
			mLPPolSchema = tLPPolDB.getSchema();

			
			tLPDutyDB.setPolNo(mLPPolSchema.getPolNo());
			tLPDutyDB.setDutyCode(mLPDutySchema.getDutyCode());
			tLPDutyDB.setEdorType(mLPDutySchema.getEdorType());
			tLPDutyDB.setEdorNo(mLPDutySchema.getEdorNo());
			if (!tLPDutyDB.getInfo()) {
				CError.buildErr(this,"查询LCDuty表失败!");
				return false;
			}
			mLPDutySchema = tLPDutyDB.getSchema();
		}
		
		// 准备被保险人表的信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(mLPPolSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPPolSchema.getEdorType());
		tLPInsuredDB.setContNo(mLPPolSchema.getContNo());
		tLPInsuredDB.setInsuredNo(mInsuredNo);
		if(!tLPInsuredDB.getInfo()){
			tLCInsuredDB.setContNo(mLPPolSchema.getContNo());
			logger.debug("*************************");
			logger.debug("mInsuredNo==" + mInsuredNo);
	
			//如果保全没有被保人信息，先查看传入的lpinsuredschema中性别和职业类别是否为空，为空再去查承保的被保人信息
			if(mLPInsuredSchema.getSex()!=null && !mLPInsuredSchema.getSex().equals("") 
				&&	mLPInsuredSchema.getOccupationType()!=null && !mLPInsuredSchema.getOccupationType().equals(""))
			{
				mSex=mLPInsuredSchema.getSex();
				mOccupationType=mLPInsuredSchema.getOccupationType();
			}
			else
			{
				tLCInsuredDB.setInsuredNo(mInsuredNo);
				if (!tLCInsuredDB.getInfo()) {
					CError.buildErr(this,"查询LCInsured表失败!");
					return false;
				}
				mLCInsuredSchema = tLCInsuredDB.getSchema();
				mSex=tLCInsuredDB.getSex();
				mOccupationType=tLCInsuredDB.getOccupationType();
			}
		}else{
			mSex=tLPInsuredDB.getSex();
			mOccupationType=tLPInsuredDB.getOccupationType();
		}
		//tongmeng 2008-10-15 modify
		//先注释掉VPU
		double dVPU = getVPU(mLPDutySchema.getDutyCode());
//		准备计算信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPPolSchema.getContNo());
		if(!tLPContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
		}
		
		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setContNo(mLPPolSchema.getContNo());
		if(!tLPAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLPPolSchema.getAgentCode());
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
		}
		
//		准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLPContDB.getSchema());
		
		//准备被保人BOMAppnt数据
		BOMAppnt appnt = new BOMAppnt();		
		appnt = DealBOMAppnt(tLPAppntDB.getSchema(),tLPContDB.getSchema());
		
		//准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		agent = DealBOMAgent(tLAAgentDB.getSchema());
		
		//准备被保人BOMInsured数据
		BOMInsured insured = new BOMInsured();
		insured = DealBOMInsured(tLPInsuredDB.getSchema(),mLPPremSchema);
		
		//准备险种BOMPol数据
		BOMPol pol = new BOMPol();//一个险种
		pol = DealBOMPol(mLPPolSchema,mLPDutySchema);
		
		//准备受益人BOMBnf数据
		BOMElement element = new BOMElement();
		tBomList.add(cont);
		tBomList.add(appnt);
		tBomList.add(agent);
		tBomList.add(insured);
		tBomList.add(pol);
		// 准备基本要素信息
		mCalBase = new CalBase();
		mCalBase.setAppAge(mLPPolSchema.getInsuredAppAge());
		mCalBase.setAppAg2(getAppAg2(mLPPolSchema.getPolNo(), mLPPolSchema
				.getInsuredNo()));
		mCalBase.setSex(mSex);
		mCalBase.setPrem(mLPPolSchema.getStandPrem());
		mCalBase.setPayEndYear(mLPPolSchema.getPayEndYear());
		mCalBase.setPayEndYearFlag(mLPPolSchema.getPayEndYearFlag());
		mCalBase.setSuppRiskScore(mLPPremSchema.getSuppRiskScore());
		mCalBase.setContNo(mLPPolSchema.getContNo());
		mCalBase.setGet(mLPPolSchema.getAmnt());
		mCalBase.setDutyCode(mLPDutySchema.getDutyCode());
		mCalBase.setPayIntv(mLPPolSchema.getPayIntv());
		mCalBase.setPolNo(mLPPolSchema.getPolNo());
		mCalBase.setInsuYear(mLPPolSchema.getInsuYear());
		mCalBase.setInsuYearFlag(mLPPolSchema.getInsuYearFlag());
		mCalBase.setGetYear(mLPPolSchema.getGetYear());
		mCalBase.setFirstScore(mLPPremSchema.getSuppRiskScore());
		//mCalBase.setSecondScore(mLCPremSchema.getSecInsuAddPoint());
		mCalBase.setMult(mLPDutySchema.getMult());
		mCalBase.setGetLimit(mLPDutySchema.getGetLimit());

		// 关于职业加费类别
		mCalBase.setJob(mOccupationType);
		mCalBase.setVPU(String.valueOf(dVPU));
		mCalBase.setStandbyFlag1(mLPPolSchema.getStandbyFlag1());
		mCalBase.setCValiDate(mLPPolSchema.getCValiDate());
		mCalBase.setOccupation(tLPInsuredDB.getOccupationCode());
		logger.debug("mLCPolSchema.getInsuredSex()=="
				+ mLPPolSchema.getInsuredSex());
		// 针对险种144
		if (mLPPolSchema.getInsuredSex() != null
				&& mLPPolSchema.getInsuredSex().equals("0")) {
			mCalBase.setHusbandScore(mLPPremSchema.getSuppRiskScore());
			mCalBase.setWifeScore(mLPPremSchema.getSecInsuAddPoint());
		} else {
			mCalBase.setHusbandScore(mLPPremSchema.getSecInsuAddPoint());
			mCalBase.setWifeScore(mLPPremSchema.getSuppRiskScore());

		}

		return true;
	}

	private boolean prepareCalculate() {
		// 从LMDutyPayAddFee表中获取calcode字段值
		LMDutyPayAddFeeDB tLMDutyPayAddFeeDB = new LMDutyPayAddFeeDB();
		tLMDutyPayAddFeeDB.setSchema(this.mLMDutyPayAddFeeSchema);
//		tLMDutyPayAddFeeDB
//				.setAddFeeType(mLMDutyPayAddFeeSchema.getAddFeeType());
//		tLMDutyPayAddFeeDB.setRiskCode(mLCPolSchema.getRiskCode());
//		tLMDutyPayAddFeeDB.setDutyCode(mLCDutySchema.getDutyCode());
		// 如果加费对象为投保人,那么加费对象算法为01
		/*
		if (mLMDutyPayAddFeeSchema.getAddFeeObject().equals("01")) {
			tLMDutyPayAddFeeDB.setAddFeeObject("01");
		} else {
			String tSql = " select AddFeeObject from LMDutyPayAddFee where 1=1 "
					+ " and RiskCode = '"
					+ mLCPolSchema.getRiskCode()
					+ "'"
					+ " and DutyCode = '"
					+ mLCDutySchema.getDutyCode()
					+ "'"
					+ " and AddFeeType = '"
					+ mLMDutyPayAddFeeSchema.getAddFeeType()
					+ "'"
					+ " and AddFeeObject <> '01'";
			ExeSQL tExeSQl = new ExeSQL();
			String tSqlResult = tExeSQl.getOneValue(tSql);
			if (tSqlResult.equals("0") || tSqlResult.trim().equals("")
					|| tSqlResult.equals("null")) {
				CError tError = new CError();
				tError.moduleName = "AddPremCalBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "此险种无需进行加费，或者该险种没有此加费类型!";
				this.mErrors.addOneError(tError);
				return false;

			}

			tLMDutyPayAddFeeDB.setAddFeeObject(tSqlResult);

		}
*/
		if (!tLMDutyPayAddFeeDB.getInfo()) {
			CError.buildErr(this,"界面录入信息有误!请检查代码合法性!");
			return false;

		}
		mCalCode = tLMDutyPayAddFeeDB.getAddFeeCalCode();

		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("InsuredNo", mInsuredNo);
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase.getPayEndYearFlag());
		mCalculator
				.addBasicFactor("SuppRiskScore", mCalBase.getSuppRiskScore());
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("DutyCode", mCalBase.getDutyCode());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("FirstScore", mCalBase.getFirstScore());
		//mCalculator.addBasicFactor("SecondScore", mCalBase.getSecondScore());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("StandByFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("CValidate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("AppAg2", mCalBase.getAppAg2());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());
		mCalculator.addBasicFactor("OccupationCode", mCalBase.getOccupation());
		// 针对险种144
		mCalculator.addBasicFactor("HusbandScore", mCalBase.getHusbandScore());
		mCalculator.addBasicFactor("WifeScore", mCalBase.getWifeScore());
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("")) {
			tStr = "0";
		}
		else
			tStr = String.valueOf(Float.parseFloat(mCalculator.calculate()));
		return true;
	}
	

	private BOMCont DealBOMCont(LPContSchema tLPContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLPContSchema.getSellType());//出单方式
			if(!(tLPContSchema.getCValiDate()==null||"".equals(tLPContSchema.getCValiDate()))){
				theDate=tLPContSchema.getCValiDate()+" 00:00:00";
				cont.setCvalidate(sdf.parse(theDate));
			}
			cont.setPrem(tLPContSchema.getPrem());
			cont.setMult(tLPContSchema.getMult());
			if(!((tLPContSchema.getCValiDate()==null))||"".equals(tLPContSchema.getCValiDate())){
				theDate=tLPContSchema.getCValiDate()+" 00:00:00";
				cont.setCvalidate(sdf.parse(theDate));
			}
			
			cont.setAutoPayFlag(tLPContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLPContSchema.getBankAccNo());
			cont.setBankCode(tLPContSchema.getBankCode());
			String tPayIntv =String.valueOf(tLPContSchema.getPayIntv()) ;
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				cont.setPayIntv("0");
			}
			else
			{
				cont.setPayIntv(tPayIntv);
			}
			if(!((tLPContSchema.getMakeDate()==null)||"".equals(tLPContSchema.getMakeDate()))){
				theDate = tLPContSchema.getMakeDate()+" "+tLPContSchema.getMakeTime();
				cont.setMakeDate(sdf.parse(theDate));
			}
			String tNewPayMode = tLPContSchema.getNewPayMode();
			String tPayMode = tLPContSchema.getPayLocation();
			String tAccName = "";
			if(tNewPayMode!=null&&tNewPayMode.equals("0")){
				cont.setPayMode("0");
				tAccName = tLPContSchema.getNewAccName();
			}else if(tPayMode!=null&&tPayMode.equals("0")) {
				 cont.setPayMode("0");
				 tAccName = tLPContSchema.getAccName();
			}
			else
			{
				cont.setPayMode(tLPContSchema.getPayMode());
			}
			cont.setManageCom(tLPContSchema.getManageCom());
			cont.setAmnt(new Double(tLPContSchema.getAmnt()));
			cont.setAutoPayFlag(tLPContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLPContSchema.getBankAccNo());
			cont.setBankCode(tLPContSchema.getBankCode());
			cont.setCardFlag(tLPContSchema.getCardFlag());
			cont.setCardFlag(tLPContSchema.getCardFlag());
			cont.setContNo(tLPContSchema.getContNo());			
			cont.setManageCom(tLPContSchema.getManageCom());			
			cont.setOutPayFlag(tLPContSchema.getOutPayFlag());			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LPAppntSchema tLPAppntSchema,LPContSchema tPContSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLPAppntSchema.getAppntName());
			appnt.setAppntNo(tLPAppntSchema.getAppntNo());
			appnt.setAppntSex(tLPAppntSchema.getAppntSex());		
			appnt.setNationality(tLPAppntSchema.getNationality());
			appnt.setNativePlace(tLPAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLPAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLPAppntSchema.getOccupationType());
			appnt.setPosition(tLPAppntSchema.getPosition());
			appnt.setRelationToInsured(tLPAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLPAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLPAppntSchema.getSalary()));
			int tAppAge = PubFun.calInterval2(tLPAppntSchema.getAppntBirthday(), tPContSchema.getPolApplyDate(), "Y");
			appnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLPAppntSchema.getAppntBirthday()==null))||"".equals(tLPAppntSchema.getAppntBirthday())){
				theDate=tLPAppntSchema.getAppntBirthday()+" 00:00:00";
				appnt.setAppntBirthday(sdf.parse(theDate));
			}
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?agentcode?"+"'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tBlackList);
		sqlbv2.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv2);
		agent.setAgentBlankFlag(tBlackFlag);//黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());		
		return agent;
	}
	
	private BOMInsured DealBOMInsured(LPInsuredSchema tLPInsuredSchema,LPPremSchema mLPPremSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			//参考AutoUWCheckBL.DealBOMInsured
			Insured.setInsuredNo(tLPInsuredSchema.getInsuredNo());	
			String polApplyDateSql = "select PolApplyDate from lccont where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(polApplyDateSql);
			sqlbv3.put("contno", tLPInsuredSchema.getContNo());
			String tpolApplyDate = tempExeSQL.getOneValue(sqlbv3);
			int tInsAge = PubFun.calInterval(tLPInsuredSchema.getBirthday(), tpolApplyDate, "Y");
			Insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
			Insured.setOccupationType(tLPInsuredSchema.getOccupationType());
			Insured.setOccupationCode(tLPInsuredSchema.getOccupationCode());
			Insured.setInsuredStat(tLPInsuredSchema.getInsuredStat());
			Insured.setMarriage(tLPInsuredSchema.getMarriage());
			Insured.setRelationToAppnt(tLPInsuredSchema.getRelationToAppnt());
			Insured.setSalary(Double.valueOf(String.valueOf(tLPInsuredSchema.getSalary())));
			Insured.setSex(tLPInsuredSchema.getSex());
			Insured.setSecInsuAddPoint(mLPPremSchema.getSecInsuAddPoint());
			// 针对险种144
			if (mLPPolSchema.getInsuredSex() != null
					&& mLPPolSchema.getInsuredSex().equals("0")) {
				Insured.setEM(mLPPremSchema.getSuppRiskScore());
				Insured.setSecInsuAddPoint(mLPPremSchema.getSecInsuAddPoint());
			} else {
				Insured.setEM(mLPPremSchema.getSecInsuAddPoint());
				Insured.setSecInsuAddPoint(mLPPremSchema.getSuppRiskScore());

			}
						
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LPPolSchema tLPPolSchema,LPDutySchema tLPDutySchema){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			Pol.setAmnt(Double.valueOf(String.valueOf(tLPPolSchema.getAmnt())));
			Pol.setUWFlag(tLPPolSchema.getUWFlag());
			Pol.setPrem(new Double(tLPPolSchema.getPrem()));
			Pol.setInsuredNo(tLPPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLPPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLPPolSchema.getMult()));
			Pol.setPayYears(new Double(tLPPolSchema.getPayYears()));
			Pol.setPolNo(tLPPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLPPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLPPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLPPolSchema.getCurrency());
			Pol.setLiveGetMode(tLPPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLPPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLPPolSchema.getRiskCode());
			Pol.setGetLimit(tLPDutySchema.getGetLimit());
			double dVPU = getVPU(tLPDutySchema.getDutyCode());
			Pol.setVPU(Double.valueOf(dVPU));
			Pol.setPayEndYear(new Double(tLPPolSchema.getPayEndYear()));
			Pol.setPayEndYearFlag(tLPPolSchema.getPayEndYearFlag());
			Pol.setGetYear(new Double(tLPPolSchema.getGetYear()));
			Pol.setGetYearFlag(tLPPolSchema.getGetYearFlag());
			if(!(tLPPolSchema.getCValiDate()==null||"".equals(tLPPolSchema.getCValiDate()))){
				theDate=tLPPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setFloatRate(new Double(tLPPolSchema.getFloatRate()));
			Pol.setStandbyFlag1(tLPPolSchema.getStandbyFlag1());
			Pol.setStandbyFlag2(tLPPolSchema.getStandbyFlag2());
			Pol.setStandbyFlag3(tLPPolSchema.getStandbyFlag3());
			if(!((tLPPolSchema.getCValiDate()==null))||"".equals(tLPPolSchema.getCValiDate())){
				theDate=tLPPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setGetLimit(tLPDutySchema.getGetLimit());
			Pol.setGetYear(Double.valueOf(tLPPolSchema.getGetYear()));
			Pol.setDutyCode(tLPDutySchema.getDutyCode());			
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}
	

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 查询VPU
	 * 
	 * @param sDutyCode
	 * @return String
	 */
	private double getVPU(String sDutyCode) {
		double dVPU = 0.0;
		String sql = " select VPU from lmduty " + " where dutycode = '"
				+ "?dutycode?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("dutycode", sDutyCode);
		ExeSQL tExeSQL = new ExeSQL();
		String sVPU = tExeSQL.getOneValue(sqlbv4);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询VPU失败!");
			return -1;
		}
		if (sVPU == null || sVPU.trim().equals("")) {
			CError.buildErr(this, "VPU为空!");
			return -1;
		}
		try {
			dVPU = Double.parseDouble(sVPU);
		} catch (Exception e) {
			CError.buildErr(this, "VPU查询结果错误!" + "错误结果：" + sVPU);
			return -1;
		}

		return dVPU;
	}

	private String getAppAg2(String tPolNo, String tInsuredNo) {
		String AppAg2 = "";
		String tSql = " select Birthday from LCInsuredRelated where 1=1 "
				+ " and polno = '" + "?polno?" + "'" + " and MainCustomerNo = '"
				+ "?insuredno?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("polno", tPolNo);
		sqlbv5.put("insuredno", tInsuredNo);
		ExeSQL tExeSQL = new ExeSQL();
		AppAg2 = tExeSQL.getOneValue(sqlbv5);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询AppAg2失败!");
			return "";
		}
		if (AppAg2 == null || AppAg2.trim().equals("")) {
			return "";
		}
		return AppAg2;

	}

}
