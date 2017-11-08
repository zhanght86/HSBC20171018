package com.sinosoft.lis.list;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.ebusiness.ActivateBL;
import com.sinosoft.lis.tb.ContBL;
import com.sinosoft.lis.tb.ContInsuredBL;
import com.sinosoft.lis.tb.LCContSignBL;
import com.sinosoft.lis.tb.ProposalBL;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * SinApproveBL 卡单的核销程序：包括承保和签单 2008-3-10 zy
 */

public class SinApproveBL
{
private static Logger logger = Logger.getLogger(SinApproveBL.class);


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private GlobalInput tG = new GlobalInput();	
	private MMap map = new MMap();
	private VData mDVData;
	public CardErrLogSchema mCardErrLogSchema;
	private double prem = 0; //获取卡单保费
	private String certifycode="";
	private String mPrtno="";//卡单的印刷号
	private LJTempFeeSchema mLJTempFeeSchema ;//传递的暂收费信息
	private boolean misRiskPlan = false;//判断是否为产品组合

	/**
	 * 卡单核销：产生保单信息，核销保费收入
	 */

	public boolean submitData(VData cInputData, String cOperate)
	{
		try
		{
			if (!getInputData(cInputData))
				return false;

			if (!checkData())
				return false;

			if (!dealData())
			{
				CError.buildErr(this, "业务逻辑处理失败！");
				return false;
			}
			if (!prepareOutputData())
				return false;

		} catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		logger.debug("**********success**********");
		return true;
	}

	/**
	 * 获取数据
	 */
	public boolean getInputData(VData mInputData)
	{	
		if(dealGlobalInput())
		{
			logger.debug("置完全局变量！");
		}
		
		mLJTempFeeSchema = new LJTempFeeSchema();
		mLJTempFeeSchema = (LJTempFeeSchema)mInputData.getObjectByObjectName("LJTempFeeSchema", 0);
		logger.debug("卡单号码为：  "+mLJTempFeeSchema.getTempFeeNo());
		if(mLJTempFeeSchema == null || mLJTempFeeSchema.equals(""))
		{
			CError.buildErr(this, "传暂收费信息失败！");
			return false;
		}
		return true;
	}	
	
	public boolean dealGlobalInput()
	{
		tG.Operator="KD";
		tG.ComCode="86";
		tG.ManageCom="86";
		return true;
	}

	/**
	 * 校验数据
	 */
	public boolean checkData()
	{
		return true;
	}

	/**
	 * 业务处理
	 */
	public boolean dealData()
	{		
		//承保处理
		if(!Proposal(mLJTempFeeSchema))
		{
			CError.buildErr(this, "承保处理失败！");
			return false;
		}
		
		mPrtno = mLJTempFeeSchema.getTempFeeNo();
		logger.debug("印刷号为----"+mPrtno);
		//签单处理
		if(!Sign(mPrtno))
		{
			CError.buildErr(this, "签单处理失败！");
			return false;
		}
		return true;
	}
	

	/**
	 * 准备投保数据
	 */

	private boolean prepareproposal(LJTempFeeSchema mFeeSchema)
	{		
		double cPrem = getPrem(mFeeSchema);
		if(cPrem<=0)
		{
			this.mErrors.clearErrors();
			CError.buildErr(this, "单证描述的保费有问题，请核实！");
			return false;
		}
		
		//从费率表中根据保费和险种编码取得insuyear和insuyearflag
		RateCardDB tRateCardDB=new RateCardDB();
		logger.debug("卡单"+certifycode+":查询费率表的RiskCode:"+mFeeSchema.getRiskCode());
		logger.debug("卡单"+certifycode+":查询费率表的Prem:"+cPrem);
		tRateCardDB.setRiskCode(mFeeSchema.getRiskCode());
		tRateCardDB.setPrem(cPrem);
		if (!tRateCardDB.getInfo()) 
		{
			//插入错误记录
			InserErr("07", mFeeSchema.getTempFeeNo(), "查询费率表失败"+mErrors.getFirstError());
        }
		
		logger.debug("卡单"+certifycode+":查询费率表的Insuyear:"+tRateCardDB.getInsuYear());
		logger.debug("卡单"+certifycode+":查询费率表的InsuyearFlag:"+tRateCardDB.getInsuYearFlag());
		
		//准备需要提交给ContBL的数据
		LCContSchema mLCContSchema = new LCContSchema();
		mLCContSchema.setGrpContNo("00000000000000000000");
		mLCContSchema.setProposalContNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setPrtNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setContNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setManageCom(mFeeSchema.getPolicyCom());
		mLCContSchema.setPayIntv(0);	
		mLCContSchema.setMult("1");
		mLCContSchema.setPrem(cPrem);		
		mLCContSchema.setTempFeeNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setAgentCode(mFeeSchema.getAgentCode());
		mLCContSchema.setFirstPayDate("");
		mLCContSchema.setSaleChnl(mFeeSchema.getSaleChnl());
		mLCContSchema.setSellType("13");//销售方式--自助卡单
		mLCContSchema.setPolType("0");
		mLCContSchema.setContType("1");
		mLCContSchema.setCardFlag("3");//卡单
		mLCContSchema.setPolApplyDate(PubFun.getCurrentDate());
		//准备综拓专员和助理信息
		//2010-5-14 综拓渠道改为12
		if(mFeeSchema.getSaleChnl()!=null && mFeeSchema.getSaleChnl().equals("12"))
		{
			String tSql = "select a.upagentcode,a.agentcode from laxagent a,laagent b where b.managecom=a.managecom and b.agentcode='?agentcode?'";
			logger.debug("查询综拓专员和助理信息" + tSql);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("agentcode", mFeeSchema.getAgentCode());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				logger.debug("综拓专员和助理信息查询失败！");
				mLCContSchema.setAgentCodeOper("0000000000");
				mLCContSchema.setAgentCodeAssi("0000000000");
			}
			else
			{
				if(tSSRS.GetText(1, 1) == null || tSSRS.GetText(1, 1).equals(""))
					mLCContSchema.setAgentCodeOper("0000000000");
				else
					mLCContSchema.setAgentCodeOper(tSSRS.GetText(1, 1));
				if(tSSRS.GetText(1, 2) == null || tSSRS.GetText(1, 2).equals(""))
					mLCContSchema.setAgentCodeAssi("0000000000");
				else
					mLCContSchema.setAgentCodeAssi(tSSRS.GetText(1, 2));
			}			
		}

		// 投保人信息
		LCAppntSchema mLCAppntSchema = new LCAppntSchema();
		mLCAppntSchema.setPrtNo(mFeeSchema.getTempFeeNo());
		mLCAppntSchema.setAppntName("无名单");
		mLCAppntSchema.setAppntSex("0");
		mLCAppntSchema.setAppntBirthday("1990-1-1");//与当前时间差值需要大于险种描述中MinInsuredAge，小于MaxInsuredAge
		
		LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		LCAccountSchema mLCAccountSchema = new LCAccountSchema();
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
		LDPersonSchema mLDPersonSchema = new LDPersonSchema();
		
		mLDPersonSchema.setName(mLCAppntSchema.getAppntName());
		mLDPersonSchema.setSex(mLCAppntSchema.getAppntSex());
		mLDPersonSchema.setBirthday(mLCAppntSchema.getAppntBirthday());
		
		TransferData transferData = new TransferData();
		transferData.setNameAndValue("KDCheckFlag", "1");
		
		VData mInputData = new VData();
		mInputData.add(tG);
		mInputData.add(mLCContSchema);
		mInputData.add(mLCAppntSchema);
		mInputData.add(mLCAddressSchema);
		mInputData.add(mLCAccountSchema);
		mInputData.add(mLCCustomerImpartSet);
		mInputData.add(mLDPersonSchema);
		mInputData.add(transferData);
		
//		 提交保单和投保人信息进入核心业务系统
		ContBL mContBL = new ContBL();
		if (!mContBL.submitData(mInputData, "INSERT||CONT")) 
		{
			this.mErrors.copyAllErrors(mContBL.mErrors);
			return false;
		}
		
		LCContDB mLCContDB = new LCContDB();
		LCContSchema aLCContSchema = new LCContSchema();
		aLCContSchema.setContNo(mLCContSchema.getContNo());
		mLCContDB.setSchema(aLCContSchema);		
		if(mLCContDB.getInfo())
		{
			mLCContSchema.setSchema(mLCContDB.getSchema());
		}			
		
//		 准备需要提交给ContInsuredBL的数据
		LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
		mLDPersonSchema = new LDPersonSchema();
		LLAccountSchema mLLAccountSchema = new LLAccountSchema();

		mLCInsuredSchema.setName("无名单");
		mLCInsuredSchema.setSex("0");
		mLCInsuredSchema.setBirthday("1990-1-1");
		mLCInsuredSchema.setContPlanCode("");
		
		mLDPersonSchema.setName(mLCInsuredSchema.getName());
		mLDPersonSchema.setSex(mLCInsuredSchema.getSex());
		mLDPersonSchema.setBirthday(mLCInsuredSchema.getBirthday());
		
		mInputData.clear();
		mInputData.add(tG);
		mInputData.add(mLCContSchema);
		mInputData.add(mLCInsuredSchema);
		mInputData.add(mLCAddressSchema);
		mInputData.add(mLDPersonSchema);
		mInputData.add(mLLAccountSchema);

		transferData = new TransferData();
		transferData.setNameAndValue("FamilyType", "0");
		transferData.setNameAndValue("PolTypeFlag", "0");
		transferData.setNameAndValue("SequenceNo", "1");
		transferData.setNameAndValue("ContType", "1");
		transferData.setNameAndValue("SavePolType","0");
		transferData.setNameAndValue("KDCheckFlag", "1");
        mInputData.add(transferData);
        
        ContInsuredBL mContInsuredBL = new ContInsuredBL();
		if (!mContInsuredBL.submitData(mInputData, "INSERT||CONTINSURED")) 
		{
			this.mErrors.copyAllErrors(mContInsuredBL.mErrors);
			return false;
		}
		
		// 对lcpolshema进行赋值
		LCPolSchema mLCPolSchema = new LCPolSchema();
		mLCPolSchema.setGrpContNo("00000000000000000000");
		mLCPolSchema.setGrpPolNo("00000000000000000000");
		mLCPolSchema.setProposalNo(mFeeSchema.getTempFeeNo());
		mLCPolSchema.setPrtNo(mFeeSchema.getTempFeeNo());
		mLCPolSchema.setContType("1");
		mLCPolSchema.setManageCom(mFeeSchema.getPolicyCom());
		mLCPolSchema.setRiskCode(mFeeSchema.getRiskCode());
		mLCPolSchema.setAgentCode(mFeeSchema.getAgentCode());
		mLCPolSchema.setSaleChnl(mFeeSchema.getSaleChnl());
		mLCPolSchema.setSpecifyValiDate("Y");//不重算生效日
		mLCPolSchema.setCValiDate(PubFun.getCurrentDate());
		mLCPolSchema.setPrem(cPrem);
		mLCPolSchema.setPayLocation("");
		mLCPolSchema.setInsuYear(tRateCardDB.getInsuYear());
		mLCPolSchema.setInsuYearFlag(tRateCardDB.getInsuYearFlag());
		mLCPolSchema.setMult("1");
		mLCPolSchema.setPolApplyDate(PubFun.getCurrentDate());
		
		//保险责任表
		LCDutySchema mLCDutySchema = new LCDutySchema();
		mLCDutySchema.setPayIntv(0);
		mLCDutySchema.setInsuYear(tRateCardDB.getInsuYear());
		mLCDutySchema.setInsuYearFlag(tRateCardDB.getInsuYearFlag());
		
		LCBnfSet mLCBnfSet = new LCBnfSet();
		//得到处理后的投被保人信息
		mLCContDB = new LCContDB();
		aLCContSchema = new LCContSchema();
		aLCContSchema.setContNo(mLCContSchema.getContNo());
		mLCContDB.setSchema(aLCContSchema);		
		if(mLCContDB.getInfo())
		{
			mLCContSchema.setSchema(mLCContDB.getSchema());
		}
		else
		{
			logger.debug("查询合同信息失败！");
			return false;
		}
		
		LCInsuredDB mLCInsuredDB = new LCInsuredDB();
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql("select * from LCInsured where contno='?contno?'");
		sqlbv1.put("contno", mLCContSchema.getContNo());
		mLCInsuredSchema = mLCInsuredDB.executeQuery(sqlbv1).get(1);
		
		LCAppntDB mLCAppntDB = new LCAppntDB();
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql("select * from LCAppnt where contno='?contno?'");
		sqlbv2.put("contno", mLCContSchema.getContNo());
		mLCAppntSchema = mLCAppntDB.executeQuery(sqlbv2).get(1);
		
		transferData = new TransferData();
		transferData.setNameAndValue("ChangePlanFlag", "1");
		transferData.setNameAndValue("KDCheckFlag", "1");
		
		mInputData.clear();
		mInputData.add(tG);
		mInputData.add(mLCContSchema);
		mInputData.add(mLCInsuredSchema);
		mInputData.add(transferData);
		mInputData.add(mLCPolSchema);
		mInputData.add(mLCAppntSchema);
		mInputData.add(mLCDutySchema);
		mInputData.add(mLCBnfSet);
		
		ProposalBL mProposalBL = new ProposalBL();		
		if (!mProposalBL.submitData(mInputData, "INSERT||PROPOSAL")) 
		{
			this.mErrors.copyAllErrors(mProposalBL.mErrors);
			return false;
		}
		return true;
	}
	
	/**
	 * 准备投保数据
	 */

	private boolean prepareproposalPlan(LJTempFeeSchema mFeeSchema)
	{		
		String riskplancode = mFeeSchema.getRiskCode();//产品代码
		int insuNum = 1;//被保人数
		
		//确定产品组合信息：被保人数，被保人所占比例		
		LDPlanDB tLDPlanDB = new LDPlanDB();
		LDPlanSchema tLDPlanSchema = new LDPlanSchema();
		LDPlanSet tLDPlanSet = new LDPlanSet();
		tLDPlanDB.setContPlanCode(riskplancode);
		tLDPlanDB.setPlanType("0");//??是0?
		tLDPlanSet = tLDPlanDB.query();
		tLDPlanSchema = tLDPlanSet.get(1);
		insuNum = tLDPlanSchema.getPeoples3();
		String srateInsu = tLDPlanSchema.getPlanKind1();			
		
		double[] rateInsu = new double[insuNum];//被保人所占比例
		double tSumPrem = 0;//合同总保费
		double tSumAmnt = 0;//合同总保额		
		int indexInsu =1;
		while(indexInsu<=insuNum)
		{				
			rateInsu[indexInsu-1] = 0;//初始化
			
			if(srateInsu.indexOf("/")==-1)
			{
				logger.debug("产品组合"+riskplancode+"没有定义被保人"+indexInsu+"比例");
				return false;
			}				
			try{rateInsu[indexInsu-1] = Double.parseDouble(srateInsu.substring(0,srateInsu.indexOf("/")));}
			catch (Exception ex) {
				logger.debug("获得被保人"+indexInsu+"比例时出错:"+ex.toString());
				ex.printStackTrace();
				return false;
			}			
			indexInsu++;
			srateInsu = srateInsu.substring(srateInsu.indexOf("/")+1);
			if(indexInsu==insuNum)
				srateInsu = srateInsu + "/";
		}
		
		//查询产品计划所有险种信息
		LDPlanRiskDB tLDPlanRiskDB = new LDPlanRiskDB();
		LDPlanRiskSet tLDPlanRiskSet = new LDPlanRiskSet();
		String tSql = "select * from LDPlanRisk"
	        + " where ContPlanCode='?ContPlanCode?'"
	        + " and PlanType='?PlanType?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("ContPlanCode", tLDPlanSchema.getContPlanCode());
		sqlbv3.put("PlanType", tLDPlanSchema.getPlanType());
		tLDPlanRiskSet = tLDPlanRiskDB.executeQuery(sqlbv3);
		if(tLDPlanRiskSet==null || tLDPlanRiskSet.size()<0)
		{
			logger.debug("获得产品计划"+tLDPlanSchema.getContPlanCode()+"险种信息时出错");
			return false;
		}
		
		// 对保险险种计划排序，确保主险在前面
		LCPolSet mainPolSet = new LCPolSet();
		LCPolSet subPolSet = new LCPolSet();
		LCDutySet mainDutySet = new LCDutySet();
		LCDutySet subDutySet = new LCDutySet();
		for (int i = 1; i <= tLDPlanRiskSet.size(); i++) 
		{
			LDPlanRiskSchema tLDPlanRiskSchema = tLDPlanRiskSet.get(i);
			String tPrem = "";
			String tAmnt = "";
			String tInsuYear = "";
			String tInsuYearFlag = "";
			String tPayEndYear = "";
			String tPayEndYearFlag = "";
			String tMult = "";//暂时不让录
			String tCalRule = "1";//一定要为约定费率	
			double tFloatRate = 0;//费率
			double formatFloatRate = 0;//格式化后费率
			
			//查询产品计划某个险种详细信息：保费、保额、保险期间、份数
			tSql = "select CalFactor,CalFactorValue"
						+ " from LDPlanDutyParam"
				        + " where ContPlanCode='?ContPlanCode?'"
				        + " and PlanType='?PlanType?'"
				        + " and RiskCode='?RiskCode?'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("ContPlanCode", tLDPlanSchema.getContPlanCode());
			sqlbv4.put("PlanType", tLDPlanSchema.getPlanType());
			sqlbv4.put("RiskCode", tLDPlanRiskSchema.getRiskCode());
			logger.debug("查询产品组合详细信息：" + tSql);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				logger.debug("产品组合（"+tLDPlanSchema.getContPlanCode()+"）详细配置信息查询失败！");					
				return false;
			}
			for (int t = 1; t <= tSSRS.getMaxRow(); t++) 
			{
				if(tSSRS.GetText(t, 1).equals("Prem"))
					tPrem = tSSRS.GetText(t, 2);
				else if(tSSRS.GetText(t, 1).equals("Amnt"))
					tAmnt = tSSRS.GetText(t, 2);
				else if(tSSRS.GetText(t, 1).equals("InsuYear"))
					tInsuYear = tSSRS.GetText(t, 2);
				else if(tSSRS.GetText(t, 1).equals("InsuYearFlag"))
					tInsuYearFlag = tSSRS.GetText(t, 2);
				else if(tSSRS.GetText(t, 1).equals("PayEndYear"))
					tPayEndYear = tSSRS.GetText(t, 2);
				else if(tSSRS.GetText(t, 1).equals("PayEndYearFlag"))
					tPayEndYearFlag = tSSRS.GetText(t, 2);				
			}
			
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCDutySchema tLCDutySchema = new LCDutySchema();
			
			// 对lcpolshema进行赋值
			tLCPolSchema.setGrpContNo("00000000000000000000");
			tLCPolSchema.setGrpPolNo("00000000000000000000");
			tLCPolSchema.setProposalNo(mFeeSchema.getTempFeeNo());
			tLCPolSchema.setPrtNo(mFeeSchema.getTempFeeNo());
			tLCPolSchema.setContType("1");
			tLCPolSchema.setManageCom(mFeeSchema.getPolicyCom());
			tLCPolSchema.setRiskCode(tLDPlanRiskSchema.getRiskCode());
			tLCPolSchema.setAgentCode(mFeeSchema.getAgentCode());
			tLCPolSchema.setSaleChnl(mFeeSchema.getSaleChnl());
			tLCPolSchema.setSpecifyValiDate("Y");//不重算生效日
			tLCPolSchema.setCValiDate(PubFun.getCurrentDate());
			tLCPolSchema.setPrem(tPrem);//该险种保费
			tLCPolSchema.setAmnt(tAmnt);
			tLCPolSchema.setPayLocation("");
			tLCPolSchema.setInsuYear(tInsuYear);
			tLCPolSchema.setInsuYearFlag(tInsuYearFlag);
			tLCPolSchema.setPayEndYear(tPayEndYear);
			tLCPolSchema.setPayEndYearFlag(tPayEndYearFlag);
			tLCPolSchema.setMult(tMult);
			tLCPolSchema.setPolApplyDate(PubFun.getCurrentDate());

			//计算费率
			try{
				if(Double.parseDouble(tPrem)>0 && Double.parseDouble(tAmnt)>0)
					tFloatRate = Double.parseDouble(tPrem)/Double.parseDouble(tAmnt);
				formatFloatRate = PubFun.round(tFloatRate,10);
			}
			catch(Exception e)
			{
				logger.debug("计算费率出错......");
				logger.debug(e.toString());
			}
			
			//保险责任表					
			tLCDutySchema.setPayIntv(0);
			tLCDutySchema.setInsuYear(tLCPolSchema.getInsuYear());
			tLCDutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			tLCDutySchema.setPayEndYear(tLCPolSchema.getPayEndYear()); // 交费年期
			tLCDutySchema.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			tLCDutySchema.setCalRule(tCalRule);
			tLCDutySchema.setFloatRate(formatFloatRate);// 存储转换后的浮动费率精度
			
			//计算总保费、总保额
			tSumPrem = tSumPrem + Double.parseDouble(tPrem);
			tSumAmnt = tSumAmnt + Double.parseDouble(tAmnt);
			
			if (tLDPlanRiskSchema.getRiskCode().equals(
					tLDPlanRiskSchema.getMainRiskCode())) {						
				mainPolSet.add(tLCPolSchema);//主险险种信息
				mainDutySet.add(tLCDutySchema);//主险责任信息
			} else {
				subPolSet.add(tLCPolSchema);//附加险险种信息
				subDutySet.add(tLCDutySchema);//附加险责任信息
			}
		}			
		
		mainPolSet.add(subPolSet);//所有险种信息
		mainDutySet.add(subDutySet);//所有责任信息
		
		//准备需要提交给ContBL的数据
		LCContSchema mLCContSchema = new LCContSchema();
		mLCContSchema.setGrpContNo("00000000000000000000");
		mLCContSchema.setProposalContNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setPrtNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setContNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setManageCom(mFeeSchema.getPolicyCom());
		mLCContSchema.setPayIntv(0);	
		mLCContSchema.setMult("1");				
		mLCContSchema.setTempFeeNo(mFeeSchema.getTempFeeNo());
		mLCContSchema.setAgentCode(mFeeSchema.getAgentCode());
		mLCContSchema.setFirstPayDate("");
		mLCContSchema.setSaleChnl(mFeeSchema.getSaleChnl());
		mLCContSchema.setSellType("13");//销售方式--自助卡单
		mLCContSchema.setPolType("0");
		mLCContSchema.setContType("1");
		mLCContSchema.setCardFlag("3");//卡单
		mLCContSchema.setPolApplyDate(PubFun.getCurrentDate());
		//准备综拓专员和助理信息
		//2010-5-14 综拓渠道改为12
		if(mFeeSchema.getSaleChnl()!=null && mFeeSchema.getSaleChnl().equals("12"))
		{
			tSql = "select a.upagentcode,a.agentcode from laxagent a,laagent b where b.managecom=a.managecom and b.agentcode='?agentcode?'";
			logger.debug("查询综拓专员和助理信息" + tSql);
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("agentcode", mFeeSchema.getAgentCode());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv5);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				logger.debug("综拓专员和助理信息查询失败！");
				mLCContSchema.setAgentCodeOper("0000000000");
				mLCContSchema.setAgentCodeAssi("0000000000");
			}
			else
			{
				if(tSSRS.GetText(1, 1) == null || tSSRS.GetText(1, 1).equals(""))
					mLCContSchema.setAgentCodeOper("0000000000");
				else
					mLCContSchema.setAgentCodeOper(tSSRS.GetText(1, 1));
				if(tSSRS.GetText(1, 2) == null || tSSRS.GetText(1, 2).equals(""))
					mLCContSchema.setAgentCodeAssi("0000000000");
				else
					mLCContSchema.setAgentCodeAssi(tSSRS.GetText(1, 2));
			}			
		}
		mLCContSchema.setFamilyID(mFeeSchema.getTempFeeNo());//家庭单
		mLCContSchema.setPeoples(0);//初始化为0，后面会修改该值
		mLCContSchema.setPrem(tSumPrem);
		mLCContSchema.setAmnt(tSumAmnt);

		// 投保人信息
		LCAppntSchema mLCAppntSchema = new LCAppntSchema();
		mLCAppntSchema.setPrtNo(mFeeSchema.getTempFeeNo());
		mLCAppntSchema.setAppntName("无名单");
		mLCAppntSchema.setAppntSex("0");
		mLCAppntSchema.setAppntBirthday("1990-1-1");//与当前时间差值需要大于险种描述中MinInsuredAge，小于MaxInsuredAge
		
		LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		LCAccountSchema mLCAccountSchema = new LCAccountSchema();
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
		LDPersonSchema mLDPersonSchema = new LDPersonSchema();
		
		mLDPersonSchema.setName(mLCAppntSchema.getAppntName());
		mLDPersonSchema.setSex(mLCAppntSchema.getAppntSex());
		mLDPersonSchema.setBirthday(mLCAppntSchema.getAppntBirthday());
		
		TransferData transferData = new TransferData();
		transferData.setNameAndValue("KDCheckFlag", "1");					
		
		VData mInputData = new VData();
		mInputData.add(tG);
		mInputData.add(mLCContSchema);
		mInputData.add(mLCAppntSchema);
		mInputData.add(mLCAddressSchema);
		mInputData.add(mLCAccountSchema);
		mInputData.add(mLCCustomerImpartSet);
		mInputData.add(mLDPersonSchema);
		mInputData.add(transferData);
		
//		 提交保单和投保人信息进入核心业务系统
		ContBL mContBL = new ContBL();
		if (!mContBL.submitData(mInputData, "INSERT||CONT")) 
		{
			this.mErrors.copyAllErrors(mContBL.mErrors);
			return false;
		}
		
		LCContDB mLCContDB = new LCContDB();
		LCContSchema aLCContSchema = new LCContSchema();
		aLCContSchema.setContNo(mLCContSchema.getContNo());
		mLCContDB.setSchema(aLCContSchema);		
		if(mLCContDB.getInfo())
		{
			mLCContSchema.setSchema(mLCContDB.getSchema());
		}		
			
		//保存被保人信息
		for(int i=1;i<=insuNum;i++)
		{
//			 准备需要提交给ContInsuredBL的数据
			LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
			mLDPersonSchema = new LDPersonSchema();
			LLAccountSchema mLLAccountSchema = new LLAccountSchema();

			mLCInsuredSchema.setName("无名单"+i);
			mLCInsuredSchema.setSex("0");
			mLCInsuredSchema.setBirthday("1990-1-1");
			mLCInsuredSchema.setContPlanCode("");
			mLCInsuredSchema.setSequenceNo(""+i);
			
			mLDPersonSchema.setName(mLCInsuredSchema.getName());
			mLDPersonSchema.setSex(mLCInsuredSchema.getSex());
			mLDPersonSchema.setBirthday(mLCInsuredSchema.getBirthday());
			
			mInputData.clear();
			mInputData.add(tG);
			mInputData.add(mLCContSchema);
			mInputData.add(mLCInsuredSchema);
			mInputData.add(mLCAddressSchema);
			mInputData.add(mLDPersonSchema);
			mInputData.add(mLLAccountSchema);

			transferData = new TransferData();
			transferData.setNameAndValue("FamilyType", "0");
			transferData.setNameAndValue("PolTypeFlag", "0");
			transferData.setNameAndValue("ContType", "1");
			transferData.setNameAndValue("SavePolType","0");
			transferData.setNameAndValue("KDCheckFlag", "0");//投被保人客户号不同
			transferData.setNameAndValue("SequenceNo", String
					.valueOf(mLCInsuredSchema.getSequenceNo())); // 内部客户号
	        mInputData.add(transferData);
	        
	        ContInsuredBL mContInsuredBL = new ContInsuredBL();
			if (!mContInsuredBL.submitData(mInputData, "INSERT||CONTINSURED")) 
			{
				this.mErrors.copyAllErrors(mContInsuredBL.mErrors);
				return false;
			}
			
			//开始保存产品组合险种信息				
			LCPolSchema tReturnLCPolSchema = new LCPolSchema();//主险保单信息
			for (int j = 1; j <= mainPolSet.size(); j++) 
			{		
				//获取lcpolschema					
				LCPolSchema mLCPolSchema = new LCPolSchema();
				mLCPolSchema.setSchema(mainPolSet.get(j));				
				mLCPolSchema.setPrem(PubFun.round(mLCPolSchema.getPrem()*rateInsu[i-1],2));//该险种实际保费=险种保费*所占比例
				mLCPolSchema.setAmnt(PubFun.round(mLCPolSchema.getAmnt()*rateInsu[i-1],2));//该险种实际保费=险种保费*所占比例
				if(j!=1)
					mLCPolSchema.setMainPolNo(tReturnLCPolSchema.getPolNo());//传入主险保单号
				
				//获取lcdutyschema					
				LCDutySchema mLCDutySchema = new LCDutySchema();
				mLCDutySchema = mainDutySet.get(j);
				
				LCBnfSet mLCBnfSet = new LCBnfSet();
				
				//得到处理后的投被保人信息
				mLCContDB = new LCContDB();
				aLCContSchema = new LCContSchema();
				aLCContSchema.setContNo(mLCContSchema.getContNo());
				mLCContDB.setSchema(aLCContSchema);		
				if(mLCContDB.getInfo())
				{
					mLCContSchema.setSchema(mLCContDB.getSchema());
				}
				else
				{
					logger.debug("查询合同信息失败！");
					return false;
				}
				
				LCInsuredDB mLCInsuredDB = new LCInsuredDB();
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql("select * from LCInsured where contno='?contno?' and sequenceno='?sequenceno?'");
				sqlbv6.put("contno", mLCContSchema.getContNo());
				sqlbv6.put("sequenceno", i);
				mLCInsuredSchema = mLCInsuredDB.executeQuery(sqlbv6).get(1);
				SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
				sqlbv7.sql("select * from LCAppnt where contno='?contno?'");
				sqlbv7.put("contno", mLCContSchema.getContNo());
				sqlbv7.put("sequenceno", i);
				LCAppntDB mLCAppntDB = new LCAppntDB();
				mLCAppntSchema = mLCAppntDB.executeQuery(sqlbv7).get(1);
				
				transferData = new TransferData();
				transferData.setNameAndValue("ChangePlanFlag", "1");
				transferData.setNameAndValue("KDCheckFlag", "1");
				
				mInputData.clear();
				mInputData.add(tG);
				mInputData.add(mLCContSchema);
				mInputData.add(mLCInsuredSchema);
				mInputData.add(transferData);
				mInputData.add(mLCPolSchema);
				mInputData.add(mLCAppntSchema);
				mInputData.add(mLCDutySchema);
				mInputData.add(mLCBnfSet);
				
				ProposalBL mProposalBL = new ProposalBL();		
				if (!mProposalBL.submitData(mInputData, "INSERT||PROPOSAL")) 
				{
					this.mErrors.copyAllErrors(mProposalBL.mErrors);
					return false;
				}
				
				if(j==1)
				{
					VData newVData = mProposalBL.getResult();

					// 主险保单信息
					tReturnLCPolSchema = ((LCPolSchema) newVData
							.getObjectByObjectName("LCPolSchema", 0));
				}					
			}				
		}
		
		return true;
	}

	private boolean prepareSign(LCContSet sLCContSet,String scontno)
	{
		VData sVData = new VData();
		sVData.add(tG);
		TransferData mTransferData = new TransferData();
		if(!misRiskPlan)//不是产品组合
			mTransferData.setNameAndValue("KDCheckFlag", "1");
		sVData.add(mTransferData);
		sVData.add(sLCContSet);
		
		// 调用核心的签单逻辑
		LCContSignBL mLCContSignBL = new LCContSignBL();
		if (!mLCContSignBL.submitData(sVData, ""))
		{
			this.mErrors.copyAllErrors(mLCContSignBL.mErrors);
			return false;
		}
		map = new MMap();
		map.add((MMap) mLCContSignBL.getResult().getObjectByObjectName("MMap", 0));
		if(!misRiskPlan){//不是产品组合
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		    sqlbv8.sql("update lccont set printcount = 1,CustomGetPolDate='' where contno = '?scontno?'");
		    sqlbv8.put("scontno", scontno);
			map.put(sqlbv8, "UPDATE");
		}
		else
		{
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			sqlbv9.sql("update lccont set printcount = 1,CustomGetPolDate='' where familyid = '?familyid?'");
			sqlbv9.put("familyid", scontno);
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql("update ljtempfee set otherno = '?scontno?' where tempfeeno = '?scontno?'");
			sqlbv10.put("scontno", scontno);
			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
			sqlbv11.sql("update ljtempfeeclass set otherno = '?scontno?' where tempfeeno = '?scontno?'");
			sqlbv11.put("scontno", scontno);
			map.put(sqlbv9, "UPDATE");
			map.put(sqlbv10, "UPDATE");//otherno重新置为卡号
			map.put(sqlbv11, "UPDATE");
		}			
		VData tInputData = new VData();
		tInputData.add(map);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tInputData))
		{
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			return false;
		}
		return true;
	}
	
	public boolean Proposal(LJTempFeeSchema mFeeSchema)
	{
		//置产品组合标记
		if(setisRiskPlan(mFeeSchema.getTempFeeNo()))
		{
			logger.debug("置产品组合标记");
		}
		
		//置全局变量
		if(dealGlobalInput())
		{
			logger.debug("置完全局变量");
		}		
		
		if(misRiskPlan)//是产品组合
		{
			logger.debug("开始产品组合承保处理...........");
			if (!prepareproposalPlan(mFeeSchema))
			{
				// 插入错误记录
				InserErr("02", mFeeSchema.getTempFeeNo(), "承保处理失败"+mErrors.getFirstError());
				return false;
			}
			logger.debug("结束产品组合承保处理...........");
		}
		else
		{
			if (!prepareproposal(mFeeSchema))
			{
				// 插入错误记录
				InserErr("02", mFeeSchema.getTempFeeNo(), "承保处理失败"+mErrors.getFirstError());
				return false;
			}
		}		
		 
		// 修改复核、自核状态
		String mContNo = mFeeSchema.getTempFeeNo();
		if (!setState(mContNo))
		{
			// 插入错误记录
			InserErr("03", mFeeSchema.getTempFeeNo(), "修改保单复核状态出错！");
			return false;
		} 
		else
		{
			PubSubmit mPubSubmit = new PubSubmit();
			if (!mPubSubmit.submitData(mDVData))
			{
				// 插入错误记录
				InserErr("04", mFeeSchema.getTempFeeNo(), "复核处理过程中出错！");
				return false;
				
			}
		}		
		return true;		
	}
	
/**
 * 签单处理
 **/	
	public boolean Sign(String spolno)
	{
		//置产品组合标记
		if(setisRiskPlan(spolno))
		{
			logger.debug("置产品组合标记");
		}
		
		//置全局变量
		if(dealGlobalInput())
		{
			logger.debug("置完全局变量");
		}
		
		LCContSet sLCContSet = new LCContSet();
		LCContDB sLCContDB = new LCContDB();
		String pSql = "select * from lccont where proposalcontno='?spolno?'";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(pSql);
		sqlbv12.put("spolno", spolno);
		if(sLCContDB.executeQuery(sqlbv12).size()<=0)
		{
			this.mErrors.clearErrors();
			CError.buildErr(this, "查询保单号为" + spolno + "的信息失败！");
			return false;			
		}
			
		sLCContSet = sLCContDB.executeQuery(sqlbv12);

		if ("1".equals(sLCContSet.get(1).getAppFlag()))
		{
			this.mErrors.clearErrors();
			CError.buildErr(this, "该单已经签单，请核实！");
			return false;
		}
		
		if (!prepareSign(sLCContSet,spolno))
		{
			// 插入错误记录
			InserErr("05",spolno, "签单处理失败"+mErrors.getFirstError());
			return false;
		}
		return true;
	}

	/**
	 * 卡单核销错误信息记录日志
	 */
	public boolean InserErr(String aErrType, String aPolNo, String aRemark)
	{
		
		CardErrLogDB mCardErrLogDB = new CardErrLogDB();
		mCardErrLogDB.setSerialNo(PubFun1.CreateMaxNo("SERIALNO", 20));
		mCardErrLogDB.setErrType(aErrType);
		mCardErrLogDB.setPolNo(aPolNo);
		mCardErrLogDB.setRemark(aRemark);
		mCardErrLogDB.setManageCom(tG.ManageCom);
		mCardErrLogDB.setOperator(tG.Operator);
		mCardErrLogDB.setMakeDate(PubFun.getCurrentDate());
		mCardErrLogDB.setMakeTime(PubFun.getCurrentTime());
		if (!mCardErrLogDB.insert())
			logger.debug("插入错误信息失败！");
		return true;

	}	
	
	/**
	 * 获取卡单的保费及对交费金额与保费是否一致的校验，钱交多了对卡单是不行的！
	 **/
	private double getPrem(LJTempFeeSchema mFeeSchema) {		
		//获取卡单的单证类型
		ActivateBL tActivateBL = new ActivateBL();
		certifycode = tActivateBL.GetCertifyType(mFeeSchema.getTempFeeNo());
		LMCardRiskDB tLMCardRiskDB = new LMCardRiskDB();
		tLMCardRiskDB.setRiskCode(mFeeSchema.getRiskCode());
		tLMCardRiskDB.setCertifyCode(certifycode);	
		prem = tLMCardRiskDB.query().get(1).getPrem();


		return prem;
	}
	
	/**
	 * 准备数据
	 */
	public boolean prepareOutputData()
	{
		return true;
	}

	private boolean setState(String contno)
	{
		mDVData = new VData();
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql("update LCPol set ApproveFlag='9',ApproveCode = '?ApproveCode?',ApproveDate ='?ApproveDate?',ApproveTime='?ApproveTime?',UWFlag='9' where ContNo = '?contno?'");
		sqlbv13.put("ApproveCode", tG.Operator);
		sqlbv13.put("ApproveDate", PubFun.getCurrentDate());
		sqlbv13.put("ApproveTime", PubFun.getCurrentTime());
		sqlbv13.put("contno", contno);
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		sqlbv14.sql("update LCCont set Approveflag='9',Approvecode='?ApproveCode?',ApproveDate='?ApproveDate?',ApproveTime='?ApproveTime?' ,UWFlag='9' where contno='?contno?'");
		sqlbv14.put("ApproveCode", tG.Operator);
		sqlbv14.put("ApproveDate", PubFun.getCurrentDate());
		sqlbv14.put("ApproveTime", PubFun.getCurrentTime());
		sqlbv14.put("contno", contno);
		map.put(sqlbv13, "UPDATE");
		map.put(sqlbv14, "UPDATE");
		mDVData.add(map);
		return true;
	}
	
	private boolean setisRiskPlan(String tTempFeeNo)
	{
		if(tTempFeeNo.length()==20 && tTempFeeNo.substring(2, 3).equals("7"))
			misRiskPlan = true;
		else
			misRiskPlan = false;
		return true;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "KD";
		mGlobalInput.ManageCom = "86";
		mGlobalInput.ComCode = "86";
		
		LJTempFeeSchema tempFeeSchema = new LJTempFeeSchema();
		LJTempFeeDB tempFeeDB = new LJTempFeeDB();
		tempFeeDB.setTempFeeNo("86240018091200000042");
		tempFeeDB.setRiskCode("141815");
		tempFeeDB.setTempFeeType("1");
		if (!tempFeeDB.getInfo())
		{
			logger.debug("未找到暂收费信息");
		}

		// 获取卡单的暂收费记录信息
		tempFeeSchema =tempFeeDB.query().get(1);
	    VData mData = new VData();
	    mData.addElement(tempFeeSchema);
		SinApproveBL tApproveBL =  new SinApproveBL();
		if(tApproveBL.submitData(mData, "INSERT"))
		{
			logger.debug("**********success**********");
		}
		else
			logger.debug("**********success**********");

	}

}
