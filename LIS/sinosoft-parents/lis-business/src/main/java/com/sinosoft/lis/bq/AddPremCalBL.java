package com.sinosoft.lis.bq;
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
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LMDutyPayAddFeeDB;
import com.sinosoft.lis.f1print.BqNameFun;
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
import com.sinosoft.lis.schema.LMDutyPayAddFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 计算加费金额
 * </p>
 * <p>
 * Description: 计算被保险人健康加费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class AddPremCalBL {
private static Logger logger = Logger.getLogger(AddPremCalBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String theDate = "";
	private List tBomList = new ArrayList();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 险种加费表
	private LMDutyPayAddFeeSchema mLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();

	// 保单表
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	// 被保险人信息表
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();

	// 保费项表
	private LCPremSchema mLCPremSchema = new LCPremSchema();

	// 责任表
	private LCDutySchema mLCDutySchema = new LCDutySchema();

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

	public AddPremCalBL() {
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

	public double getAddPrem() {
		return Double.parseDouble(BqNameFun.getRound(tStr));
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(mOperator);
		tLDUWUserDB.setUWType("1");
		if (!tLDUWUserDB.getInfo()) {
			CError.buildErr(this, "查询LDUWUser表失败!");
			return false;
		}
		// ???此处需要业管讨论完毕后,确认是否需要校验核保师的加费评点!!!
		/*
		 * double FirstScore = mLCPremSchema.getSuppRiskScore(); double
		 * SecondScore = mLCPremSchema.getSecInsuAddPoint(); double tAddPoint =
		 * tLDUWUserDB.getAddPoint(); if ((FirstScore > tAddPoint) ||
		 * (SecondScore > tAddPoint)) {
		 * CError.buildErr(this,"加费评点过高，超过该核保师的权限范围!"); return false; }
		 */
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象,获得全局公共数据
		mInputData = cInputData;
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mLCDutySchema = (LCDutySchema) cInputData.getObjectByObjectName(
				"LCDutySchema", 0);
		mLCPremSchema = (LCPremSchema) cInputData.getObjectByObjectName(
				"LCPremSchema", 0);
		mLCInsuredSchema = (LCInsuredSchema) cInputData.getObjectByObjectName(
				"LCInsuredSchema", 0);
		mLMDutyPayAddFeeSchema = (LMDutyPayAddFeeSchema) cInputData
				.getObjectByObjectName("LMDutyPayAddFeeSchema", 0);

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据mGlobalInput失败!");
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据mGlobalInput失败!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!prepareCalBase())
			return false;

		if (!prepareCalculate())
			return false;

		return true;
	}

	private boolean prepareCalBase() {
		// 准备保单险种表的信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLCPolSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "查询LCPol表失败!");
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		tLCDutyDB.setDutyCode(mLCDutySchema.getDutyCode());
		if (!tLCDutyDB.getInfo()) {
			CError.buildErr(this, "查询LCDuty表失败!");
			return false;
		}
		mLCDutySchema = tLCDutyDB.getSchema();

		// tongmeng 2008-10-15 modify
		// 先注释掉VPU
		double dVPU = getVPU(mLCDutySchema.getDutyCode());

//		准备计算信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCPolSchema.getContNo());
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
		}
		
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCPolSchema.getContNo());
		if(!tLCAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCPolSchema.getAgentCode());
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
		}
//		 准备被保险人表的信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLCPolSchema.getContNo());

		logger.debug("*************************");
		logger.debug("mInsuredNo==" + mInsuredNo);

		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			CError.buildErr(this,"查询LCInsured表失败!");
			return false;
		}
		mLCInsuredSchema = tLCInsuredDB.getSchema();
//		准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLCContDB.getSchema());
		
		//准备被保人BOMAppnt数据
		BOMAppnt appnt = new BOMAppnt();		
		appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContDB.getSchema());
		
		//准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		agent = DealBOMAgent(tLAAgentDB.getSchema());
		
		//准备被保人BOMInsured数据
		BOMInsured insured = new BOMInsured();
		insured = DealBOMInsured(tLCInsuredDB.getSchema(),mLCPremSchema);
		
		//准备险种BOMPol数据
		BOMPol pol = new BOMPol();//一个险种
		pol = DealBOMPol(mLCPolSchema,tLCDutyDB.getSchema());
		
		//准备受益人BOMBnf数据
		BOMElement element = new BOMElement();
		tBomList.add(cont);
		tBomList.add(appnt);
		tBomList.add(agent);
		tBomList.add(insured);
		tBomList.add(pol);

		// 准备基本要素信息
		mCalBase = new CalBase();
		mCalBase.setAppAge(mLCPolSchema.getInsuredAppAge());
		mCalBase.setAppAg2(getAppAg2(mLCPolSchema.getPolNo(), mLCPolSchema
				.getInsuredNo()));
		mCalBase.setSex(mLCInsuredSchema.getSex());
		mCalBase.setPrem(mLCPolSchema.getStandPrem());
		mCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
		mCalBase.setPayEndYearFlag(mLCPolSchema.getPayEndYearFlag());
		mCalBase.setSuppRiskScore(mLCPremSchema.getSuppRiskScore());
		mCalBase.setContNo(mLCPolSchema.getContNo());
		mCalBase.setGet(mLCPolSchema.getAmnt());
		mCalBase.setDutyCode(mLCDutySchema.getDutyCode());
		mCalBase.setPayIntv(mLCPolSchema.getPayIntv());
		mCalBase.setPolNo(mLCPolSchema.getPolNo());
		mCalBase.setInsuYear(mLCPolSchema.getInsuYear());
		mCalBase.setInsuYearFlag(mLCPolSchema.getInsuYearFlag());
		mCalBase.setGetYear(mLCPolSchema.getGetYear());
		mCalBase.setFirstScore(mLCPremSchema.getSuppRiskScore());
		// mCalBase.setSecondScore(mLCPremSchema.getSecInsuAddPoint());
		mCalBase.setMult(mLCDutySchema.getMult());
		mCalBase.setGetLimit(mLCDutySchema.getGetLimit());

		// 关于职业加费类别
		mCalBase.setJob(mLCInsuredSchema.getOccupationType());
		mCalBase.setVPU(String.valueOf(dVPU));
		mCalBase.setStandbyFlag1(mLCPolSchema.getStandbyFlag1());
		mCalBase.setCValiDate(mLCPolSchema.getCValiDate());

		logger.debug("mLCInsuredSchema.getOccupationType()="
				+ mLCInsuredSchema.getOccupationType());
		logger.debug("mLCPolSchema.getInsuredSex()=="
				+ mLCPolSchema.getInsuredSex());

		// 针对险种144
		if (mLCPolSchema.getInsuredSex() != null
				&& mLCPolSchema.getInsuredSex().equals("0")) {
			mCalBase.setHusbandScore(mLCPremSchema.getSuppRiskScore());
			mCalBase.setWifeScore(mLCPremSchema.getSecInsuAddPoint());
		} else {
			mCalBase.setHusbandScore(mLCPremSchema.getSecInsuAddPoint());
			mCalBase.setWifeScore(mLCPremSchema.getSuppRiskScore());
		}

		return true;
	}
	
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLCContSchema.getSellType());//出单方式
			if(!(tLCContSchema.getCValiDate()==null||"".equals(tLCContSchema.getCValiDate()))){
				theDate=tLCContSchema.getCValiDate()+" 00:00:00";
				cont.setCvalidate(sdf.parse(theDate));
			}
			cont.setPrem(tLCContSchema.getPrem());
			cont.setMult(tLCContSchema.getMult());
			if(!((tLCContSchema.getCValiDate()==null))||"".equals(tLCContSchema.getCValiDate())){
				theDate=tLCContSchema.getCValiDate()+" 00:00:00";
				cont.setCvalidate(sdf.parse(theDate));
			}
			
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			String tPayIntv =String.valueOf(mLCDutySchema.getPayIntv()) ;
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				cont.setPayIntv("0");
			}
			else
			{
				cont.setPayIntv(tPayIntv);
			}
			if(!((tLCContSchema.getMakeDate()==null)||"".equals(tLCContSchema.getMakeDate()))){
				theDate = tLCContSchema.getMakeDate()+" "+tLCContSchema.getMakeTime();
				cont.setMakeDate(sdf.parse(theDate));
			}
			String tNewPayMode = tLCContSchema.getNewPayMode();
			String tPayMode = tLCContSchema.getPayLocation();
			String tAccName = "";
			if(tNewPayMode!=null&&tNewPayMode.equals("0")){
				cont.setPayMode("0");
				tAccName = tLCContSchema.getNewAccName();
			}else if(tPayMode!=null&&tPayMode.equals("0")) {
				 cont.setPayMode("0");
				 tAccName = tLCContSchema.getAccName();
			}
			else
			{
				cont.setPayMode(tLCContSchema.getPayMode());
			}
			cont.setManageCom(tLCContSchema.getManageCom());
			cont.setAmnt(new Double(tLCContSchema.getAmnt()));
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());			
			cont.setManageCom(tLCContSchema.getManageCom());			
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema,LCContSchema tLCContSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());		
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			int tAppAge = PubFun.calInterval2(tLCAppntSchema.getAppntBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			appnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLCAppntSchema.getAppntBirthday()==null))||"".equals(tLCAppntSchema.getAppntBirthday())){
				theDate=tLCAppntSchema.getAppntBirthday()+" 00:00:00";
				appnt.setAppntBirthday(sdf.parse(theDate));
			}
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?agentcode?"+"'";
		sqlbv.sql(tBlackList);
		sqlbv.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv);
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
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema,LCPremSchema tLCPremSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		try{
			//参考AutoUWCheckBL.DealBOMInsured
			Insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());	
			String polApplyDateSql = "select PolApplyDate from lccont where contno='"+"?contno?"+"'";
			sqlbv.sql(polApplyDateSql);
			sqlbv.put("contno", tLCInsuredSchema.getContNo());
			String tpolApplyDate = tempExeSQL.getOneValue(sqlbv);
			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tpolApplyDate, "Y");
			Insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
			Insured.setOccupationType(tLCInsuredSchema.getOccupationType());
			Insured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
			Insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
			Insured.setMarriage(tLCInsuredSchema.getMarriage());
			Insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			Insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			Insured.setSex(tLCInsuredSchema.getSex());
			Insured.setSecInsuAddPoint(tLCPremSchema.getSecInsuAddPoint());
			// 针对险种144
			if (mLCPolSchema.getInsuredSex() != null
					&& mLCPolSchema.getInsuredSex().equals("0")) {
				Insured.setEM(mLCPremSchema.getSuppRiskScore());
				Insured.setSecInsuAddPoint(mLCPremSchema.getSecInsuAddPoint());
			} else {
				Insured.setEM(mLCPremSchema.getSecInsuAddPoint());
				Insured.setSecInsuAddPoint(mLCPremSchema.getSuppRiskScore());

			}
						
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema,LCDutySchema tLCDutySchema){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			Pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema.getAmnt())));
			Pol.setUWFlag(tLCPolSchema.getUWFlag());
			Pol.setPrem(new Double(tLCPolSchema.getPrem()));
			Pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLCDutySchema.getMult()));
			Pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			Pol.setPolNo(tLCPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLCPolSchema.getCurrency());
			Pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLCPolSchema.getRiskCode());
			Pol.setGetLimit(tLCDutySchema.getGetLimit());
			double dVPU = getVPU(mLCDutySchema.getDutyCode());
			Pol.setVPU(Double.valueOf(dVPU));
			Pol.setPayEndYear(new Double(tLCPolSchema.getPayEndYear()));
			Pol.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			Pol.setGetYear(new Double(tLCPolSchema.getGetYear()));
			Pol.setGetYearFlag(tLCPolSchema.getGetYearFlag());
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
			Pol.setStandbyFlag1(mLCPolSchema.getStandbyFlag1());
			Pol.setStandbyFlag2(mLCPolSchema.getStandbyFlag2());
			Pol.setStandbyFlag3(mLCPolSchema.getStandbyFlag3());
			if(!((mLCPolSchema.getCValiDate()==null))||"".equals(mLCPolSchema.getCValiDate())){
				theDate=mLCPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setGetLimit(mLCDutySchema.getGetLimit());
			Pol.setGetYear(Double.valueOf(mLCPolSchema.getGetYear()));
			Pol.setDutyCode(mLCDutySchema.getDutyCode());			
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}

	private boolean prepareCalculate() {
		// 从LMDutyPayAddFee表中获取calcode字段值
		LMDutyPayAddFeeDB tLMDutyPayAddFeeDB = new LMDutyPayAddFeeDB();
		tLMDutyPayAddFeeDB.setSchema(this.mLMDutyPayAddFeeSchema);
		// tLMDutyPayAddFeeDB
		// .setAddFeeType(mLMDutyPayAddFeeSchema.getAddFeeType());
		// tLMDutyPayAddFeeDB.setRiskCode(mLCPolSchema.getRiskCode());
		// tLMDutyPayAddFeeDB.setDutyCode(mLCDutySchema.getDutyCode());
		// 如果加费对象为投保人,那么加费对象算法为01
		/*
		 * if (mLMDutyPayAddFeeSchema.getAddFeeObject().equals("01")) {
		 * tLMDutyPayAddFeeDB.setAddFeeObject("01"); } else { String tSql = "
		 * select AddFeeObject from LMDutyPayAddFee where 1=1 " + " and RiskCode = '" +
		 * mLCPolSchema.getRiskCode() + "'" + " and DutyCode = '" +
		 * mLCDutySchema.getDutyCode() + "'" + " and AddFeeType = '" +
		 * mLMDutyPayAddFeeSchema.getAddFeeType() + "'" + " and AddFeeObject <>
		 * '01'"; ExeSQL tExeSQl = new ExeSQL(); String tSqlResult =
		 * tExeSQl.getOneValue(tSql); if (tSqlResult.equals("0") ||
		 * tSqlResult.trim().equals("") || tSqlResult.equals("null")) { CError
		 * tError = new CError(); tError.moduleName = "AddPremCalBL";
		 * tError.functionName = "getInputData"; tError.errorMessage =
		 * "此险种无需进行加费，或者该险种没有此加费类型!"; this.mErrors.addOneError(tError); return
		 * false; }
		 * 
		 * tLMDutyPayAddFeeDB.setAddFeeObject(tSqlResult); }
		 */
		if (!tLMDutyPayAddFeeDB.getInfo()) {
			CError.buildErr(this, "界面录入信息有误!请检查代码合法性!");
			return false;

		}
		mCalCode = tLMDutyPayAddFeeDB.getAddFeeCalCode();

		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
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
		// mCalculator.addBasicFactor("SecondScore", mCalBase.getSecondScore());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("StandByFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("CValidate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("AppAg2", mCalBase.getAppAg2());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());

		// 针对险种144
		mCalculator.addBasicFactor("HusbandScore", mCalBase.getHusbandScore());
		mCalculator.addBasicFactor("WifeScore", mCalBase.getWifeScore());
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("")) {
			tStr = "0";
		}
		return true;
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
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = " select VPU from lmduty " + " where dutycode = '"
				+ "?sDutyCode?" + "'";
		sqlbv.sql(sql);
		sqlbv.put("sDutyCode", sDutyCode);
		ExeSQL tExeSQL = new ExeSQL();
		String sVPU = tExeSQL.getOneValue(sqlbv);
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
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tSql = " select Birthday from LCInsuredRelated where 1=1 "
				+ " and polno = '" + "?tPolNo?" + "'" + " and MainCustomerNo = '"
				+ "?tInsuredNo?" + "'";
		sqlbv.sql(tSql);
		sqlbv.put("tPolNo", tPolNo);
		sqlbv.put("tInsuredNo", tInsuredNo);
		ExeSQL tExeSQL = new ExeSQL();
		AppAg2 = tExeSQL.getOneValue(sqlbv);
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
