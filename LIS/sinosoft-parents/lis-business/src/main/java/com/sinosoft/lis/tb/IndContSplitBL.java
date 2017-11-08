package com.sinosoft.lis.tb;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

//import com.ibm.db2.jcc.c.w;
import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LACommisionDetailDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCCustomerImpartDB;
import com.sinosoft.lis.db.LCCustomerImpartParamsDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPerInvestPlanDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPerInvestPlanSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 核心业务系统
 * </p>
 * <p>
 * Description: 合同签单保单拆分类
 * </p>
 * 针对个险保单多主险和家庭单,进行保单拆分逻辑.将一个大合同按照主险的数量拆分成多个小合同
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.0
 */

public class IndContSplitBL {
private static Logger logger = Logger.getLogger(IndContSplitBL.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		VData tVData = new VData();
//		LCPolSet tLCPolSet = new LCPolSet();
//		tVData.add(tLCPolSet);
//		LCPolSet t1LCPolSet = (LCPolSet) tVData.getObjectByObjectName(
//				"LCPolSet", 0);
//		LCPolSchema tLCPolSchema = new LCPolSchema();
//		tLCPolSchema.setPolNo("111");
//		t1LCPolSet.add(tLCPolSchema);
		double sum1 = new Double(1306).doubleValue() +new Double(14).doubleValue() + new Double(9.14).doubleValue();
		double sum2 = Double.parseDouble("1333.66");
		
		logger.debug("sum1:"+sum1 + ":"+"sum2:"+sum2);
		logger.debug("sum1:"+PubFun.round(sum1, 2) + ":"+"sum2:"+PubFun.round(sum2, 2));
		double dif1 = sum2-sum1;
		double dif2= PubFun.round(sum2, 2)-PubFun.round(sum1, 2);
		logger.debug("dif1:"+dif1 + ":"+"dif2:"+dif2);

		
	}

	// 存放所有保单.为了豁免险拆分时均匀保费使用
	private LCPolSet mAllPolSet = new LCPolSet();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private DecimalFormat mDecimalFormat = new DecimalFormat("0.00"); // 数字转换对象
	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private String mFamilyContNo = "00000000000000000000";// 家庭单合同号
	private String mFamilyType = "0";// 家庭单类型
	// 投保人豁免险缓存
	private Hashtable mFreeAppntRiskTable = null;
	// 被保人豁免险缓存
	private Hashtable mFreeInsuredRiskTable = null;
	private String mFreeRiskFlag = "";
	private LCAppntSet mLCAppntSet = new LCAppntSet();
	// 险种其他表
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	//合同特约表
	private LCCSpecSet mLCCSpecSet = new LCCSpecSet();
	// 险种相关表
	private LCContSet mLCContSet = new LCContSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCGetSet mLCGetSet = new LCGetSet();
	// 帐户相关表
	private LCPremToAccSet mLCPremToAccSet = new LCPremToAccSet();
	private LCGetToAccSet mLCGetToAccSet = new LCGetToAccSet();
	private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
	private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
	private LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
	private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
	private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	//合同状态
	private LCContStateSet mLCContStateSet = new LCContStateSet();
	//财务相关表
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	//财务-客户账户新插入的数据
	private LJTempFeeSet mInsertLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mInsertLJTempFeeClassSet = new LJTempFeeClassSet();
	private String mTempfeeSerialNo = "";
	private LJFIGetSet mInsertLJFIGetSet = new LJFIGetSet();
	
	//其他表复制
	private LACommisionDetailSet mLACommisionDetailSet = new LACommisionDetailSet();
	//告知表
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	//告知参数表
	private LCCustomerImpartParamsSet mLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();

	//折扣表数据
	private LCDiscountSet mLCDiscountSet = new LCDiscountSet();
	//实收表数据
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	//余额集合
	private LJAPayPersonSet mLJAPayPersonYETSet = new LJAPayPersonSet();
	//溢交退费处理
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet();
	//溢交退费通知书
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	
	//投资计划表
	private LCPerInvestPlanSet mLCPerInvestPlanSet = new LCPerInvestPlanSet();
	
	private int mMaxSize = 0;
	private MMap mMMap = new MMap();
	private LCContSchema mOldLCContSchema = null;
	private VData mResult = new VData();
	private FDate fDate = new FDate();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mOperator = "";
	private Date mFirstPayDate = null;
	private Date maxPayDate = null;
	private Date maxEnterAccDate = null;
	private String mPaySerialNo = "";
	private String mSellType = "";//selltype='08' 银保通的单子
	private String mContNo ="";//卡单时 查询有些问题 单独查询处合同号后再进行其他查询
	private String mOrganizeDate = "";//合同成立日期
	private String mOrganizeTime = "";//合同成立时间
	//第一被保人,第一主险信息
	private LCPolSchema mOldLCPolSchema = null;
	private LCPremSet mOldLCPremSet = null;
	
	
	//tongmeng 2009-03-31 modify
	//合同下的最小的被保人序列号!
	private String mInsuredSeq = "";
	
	private String mMark = "";
	private String MRCurrency = "";//主险的币种信息
	
	//tongmeng 2010-12-02 modify
	LDExch mLDExch = new LDExch();
	
	public IndContSplitBL() {
	}

	/**
	 * 合同复制
	 * 
	 * @param tVData
	 * @return
	 */
	private boolean copyLCCont(VData tVData, int t) {
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tVData.getObjectByObjectName("LCPolSet", 0) == null ? (new LCPolSet())
				: (LCPolSet) tVData.getObjectByObjectName("LCPolSet", 0);
		if (tLCPolSet.size() <= 0) {
			return false;
		}

		// 取出主险的LCPol,累计份数,保费,保额
		double sumPrem = 0;
		double sumAmnt = 0;
		double sumMult = 0;
		//首期缴费时间
		Date tPayToDate = null;
		LCPolSchema tMainLCPolSchema = new LCPolSchema();
		//tongmeng 2010-12-02 modify
		//合同的汇总金额折算到主险的币种下
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			if (tLCPolSet.get(i).getMainPolNo().equals(
					tLCPolSet.get(i).getPolNo())) {
				tMainLCPolSchema.setSchema(tLCPolSet.get(i));
				break;
			}
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			/*if (tLCPolSet.get(i).getMainPolNo().equals(
					tLCPolSet.get(i).getPolNo())) {
				tMainLCPolSchema.setSchema(tLCPolSet.get(i));
				// break;
			}*/
		
			
			double newSumPrem = 0;
			/*
			 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
			 * 如果返回值小于0，则有错误数据
			 * orgitype 传入的币种
			 * destype 需要转换的币种
			 * transdate 转换日期
			 * amnt 转换金额
			 */
			String tCurrency = tLCPolSet.get(i).getCurrency();
			//if()
			newSumPrem = this.mLDExch.toOtherCur(tCurrency, tMainLCPolSchema.getCurrency(), tMainLCPolSchema.getCValiDate(), tLCPolSet.get(i).getPrem());

			double newSumAmnt = 0;
			
			newSumAmnt = this.mLDExch.toOtherCur(tCurrency, tMainLCPolSchema.getCurrency(), tMainLCPolSchema.getCValiDate(), tLCPolSet.get(i).getAmnt());
			sumPrem = PubFun.round(sumPrem,2) + PubFun.round(newSumPrem, 2);
			sumAmnt = PubFun.round(sumAmnt,2) + PubFun.round(newSumAmnt, 2);
			sumMult = sumMult + tLCPolSet.get(i).getMult();
			Date paytodate = fDate.getDate(tLCPolSet.get(i).getPaytoDate());
			if (tPayToDate == null || tPayToDate.after(paytodate)) {
				tPayToDate = paytodate;
			}
		}
		
		LCContSchema tLCContSchema = new LCContSchema();
		if (mOldLCContSchema == null) {
			LCContDB tLCContDB = new LCContDB();
			if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
			{
				tLCContDB.setContNo(tMainLCPolSchema.getContNo());
			}
			else
			{
				//tLCContDB.setContNo(tMainLCPolSchema.getPrtNo());
				tLCContDB.setContNo(mContNo);
			}			
			if (!tLCContDB.getInfo()) {
				// 查询合同 信息 失败
				return false;
			}
			mOldLCContSchema = new LCContSchema();
			mOldLCContSchema.setSchema(tLCContDB.getSchema());
		}
		// make LCCont
		// 合同信息以主险信息为主.
		tLCContSchema = makeLCCont(tMainLCPolSchema);
		// 设置其他信息
		// 家庭单号和家庭单类型在prepareUpdataData()方法中统一设置
		tLCContSchema.setMult(sumMult);
		tLCContSchema.setSumPrem(sumPrem);
		tLCContSchema.setPrem(sumPrem);
		tLCContSchema.setAmnt(sumAmnt);
		tLCContSchema.setAppFlag("1");
		tLCContSchema.setPaytoDate(tPayToDate);
		tLCContSchema.setModifyDate(this.mCurrentDate);
		tLCContSchema.setModifyTime(this.mCurrentTime);
		tLCContSchema.setFirstPayDate(this.mFirstPayDate);
		mLCContSet.add(tLCContSchema);
		
		//LACommisionDetail复制 
		LACommisionDetailDB tLACommisionDetailDB = new LACommisionDetailDB();
		LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
		tLACommisionDetailDB.setAgentCode(tLCContSchema.getAgentCode());
		if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
		{
			tLACommisionDetailDB.setGrpContNo(tLCContSchema.getContNo());
		}
		else
		{
			tLACommisionDetailDB.setGrpContNo(tLCContSchema.getPrtNo());
		}		
		if(!tLACommisionDetailDB.getInfo())
		{
			//如果没有查询出来就不处理了~~~
			return true;
		}
		tLACommisionDetailSchema.setSchema(tLACommisionDetailDB.getSchema());
		tLACommisionDetailSchema.setGrpContNo(tLCContSchema.getContNo());
		this.mLACommisionDetailSet.add(tLACommisionDetailSchema);
		
		return true;
	}

	/**
	 * 处理投保人豁免险
	 * 
	 * @param tRiskData
	 * @param tFreeRiskData
	 * @return
	 */
	private boolean dealAppntFreeRisk(VData tRiskData, VData tFreeRiskData,
			String tRiskCode) {
		// 取出所有可以豁免的险种
		if (this.mFreeAppntRiskTable == null) {
			mFreeAppntRiskTable = new Hashtable();
			SSRS tSSRS = new SSRS();
			String tSQL = "select code1 from ldcode1 where codetype='freerisk' and code='"
					+ "?code?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("code", tRiskCode);
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			for (int n = 1; n <= tSSRS.getMaxRow(); n++) {
				mFreeAppntRiskTable.put(tSSRS.GetText(n, 1), tRiskCode);
			}
		}

		
		// 指向引用,直接修改数据
		// 正常险种数据集合
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = (LCPolSet) tRiskData.getObjectByObjectName("LCPolSet", 0);
		LCDutySet tLCDutySet = (LCDutySet) tRiskData.getObjectByObjectName(
				"LCDutySet", 0);
		LCPremSet tLCPremSet = (LCPremSet) tRiskData.getObjectByObjectName(
				"LCPremSet", 0);
		LCGetSet tLCGetSet = (LCGetSet) tRiskData.getObjectByObjectName(
				"LCGetSet", 0);
		LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet) tRiskData.getObjectByObjectName(
				"LJSPayPersonSet", 0);
		//tongmeng 2008-11-13 add
		//增加对投保人豁免险的处理
		//如果当前的被保人和投保人是一个人的话，那么，不添加豁免险！

		//MSRS的个险投保单在签单做保单拆分后，一定一个保单里只有一个被保人数据！(如果有连带被保人的话以后再处理)


		// 豁免险种数据集合
		LCPolSet tFreeLCPolSet = (LCPolSet) tFreeRiskData
				.getObjectByObjectName("LCPolSet", 0);
		LCDutySet tFreeLCDutySet = (LCDutySet) tFreeRiskData
				.getObjectByObjectName("LCDutySet", 0);
		LCPremSet tFreeLCPremSet = (LCPremSet) tFreeRiskData
				.getObjectByObjectName("LCPremSet", 0);
		LCGetSet tFreeLCGetSet = (LCGetSet) tFreeRiskData
				.getObjectByObjectName("LCGetSet", 0);
		LJSPayPersonSet tFreeLJSPayPersonSet = (LJSPayPersonSet) tFreeRiskData
				.getObjectByObjectName("LJSPayPersonSet", 0);

		// 获取豁免险种数据
		LCPolSchema tFreeLCPolSchema = tFreeLCPolSet.get(1);
		// 投保人豁免累计所有可以豁免的险种的保费
		double allPrem = 0;
		for (int m = 1; m <= this.mAllPolSet.size(); m++) {
			String tempRiskCode = mAllPolSet.get(m).getRiskCode();
			if (this.mFreeAppntRiskTable.containsKey(tempRiskCode)) {
				//tongmeng 2009-12-23 modify 
				//先格式化之后再加和
				allPrem = PubFun.round(allPrem,2) + PubFun.round(mAllPolSet.get(m).getPrem(),2);
			}
		}
		logger.debug("累计总豁免的保费:" + allPrem);
		if (allPrem == 0) {
			// 没有可以豁免的保费
			CError.buildErr(this, "该合同下没有需要豁免的险种,不需要投保豁免险!","01","023");
			return false;
		}
		LCPolSchema tMainPolSchema = new LCPolSchema();
		// 累计该合同下可以豁免的保费
		double currPrem = 0;
		double currAmnt = 0;
		boolean AppntFlag = false ;
		for (int m = 1; m <= tLCPolSet.size(); m++) {
			if(tLCPolSet.get(m).getInsuredNo().equals(tLCPolSet.get(m).getAppntNo()))
			{
				//如果投被保人是一个人。
				AppntFlag = true;
				break;
			}
			String tempRiskCode = tLCPolSet.get(m).getRiskCode();
			if (this.mFreeAppntRiskTable.containsKey(tempRiskCode)) {
				//tongmeng 2009-12-23 modify 
				//先格式化之后再加和
				currPrem = PubFun.round(currPrem,2) + PubFun.round(tLCPolSet.get(m).getPrem(),2);
				// currAmnt = currAmnt +
			}
			if (tLCPolSet.get(m).getPolNo().equals(
					tLCPolSet.get(m).getMainPolNo())) {
				tMainPolSchema.setSchema(tLCPolSet.get(m).getSchema());
			}
		}
		if(AppntFlag)
		{
			logger.debug("投被保人是同一个人！不做豁免险处理！");
			return true;
		}
		logger.debug("累计可豁免的保费:" + currPrem+"总保费:"+allPrem);
		if (currPrem == 0) {
			// 该合同不需要均分
			return true;
		}
		// 计算需要均分的保费
		double tSplitPrem = 0;
		//tongmeng 2009-12-23 modify
		//如果currPrem == allPrem 就不再进行折算。防止出现折算后少1分钱。
		if(PubFun.round(currPrem,2)==PubFun.round(allPrem,2))
		{
			tSplitPrem = tFreeLCPolSchema.getPrem();
		}
		else
		{
			tSplitPrem = (currPrem * tFreeLCPolSchema.getPrem()) / allPrem;
		}
		// 格式化数据
		tSplitPrem = PubFun.round(tSplitPrem,2);
		// 需要新生成保单号
		String tPolNo = "";
		// 生成险种保单号
		String tLimit = PubFun.getNoLimit(tMainPolSchema.getManageCom());
		tPolNo = PubFun1.CreateMaxNo("POLNO", tLimit);

		if (StrTool.cTrim(tPolNo).equals("")) {
			// @@错误处理
			CError.buildErr(this, "险种保单号生成失败!","01","024");
			return false;
		}
		// 新增缴费项
		for (int m = 1; m <= tFreeLCPremSet.size(); m++) {
			LCPremSchema tempLCPremSchema = new LCPremSchema();
			tempLCPremSchema.setSchema(tFreeLCPremSet.get(m));
			tempLCPremSchema.setContNo(tMainPolSchema.getContNo());
			tempLCPremSchema.setPolNo(tPolNo);
			tempLCPremSchema.setStandPrem(tSplitPrem);
			tempLCPremSchema.setPrem(tSplitPrem);
			tempLCPremSchema.setSumPrem(tSplitPrem);
			tempLCPremSchema.setSumPrem(tSplitPrem);
			// 加到保费集合中
			tLCPremSet.add(tempLCPremSchema);
		}
		// 新增领取项
		for (int m = 1; m <= tFreeLCGetSet.size(); m++) {
			LCGetSchema tempLCGetSchema = new LCGetSchema();
			tempLCGetSchema.setSchema(tFreeLCGetSet.get(m));
			tempLCGetSchema.setContNo(tMainPolSchema.getContNo());
			tempLCGetSchema.setPolNo(tPolNo);
			tempLCGetSchema.setInsuredNo(tMainPolSchema.getInsuredNo());
			// 领取项,设置的是保费
			tempLCGetSchema.setStandMoney(currPrem);
			tempLCGetSchema.setActuGet(currPrem);
			// 加到领取集合中
			tLCGetSet.add(tempLCGetSchema);
		}
		// 新增责任项
		for (int m = 1; m <= tFreeLCDutySet.size(); m++) {
			LCDutySchema tempLCDutySchema = new LCDutySchema();
			tempLCDutySchema.setSchema(tFreeLCDutySet.get(m));
			tempLCDutySchema.setContNo(tMainPolSchema.getContNo());
			tempLCDutySchema.setPolNo(tPolNo);
			tempLCDutySchema.setStandPrem(tSplitPrem);
			tempLCDutySchema.setPrem(tSplitPrem);
			tempLCDutySchema.setSumPrem(tSplitPrem);
			tempLCDutySchema.setAmnt(currPrem);
			tempLCDutySchema.setRiskAmnt(currPrem);
			// 加到责任集合中
			tLCDutySet.add(tempLCDutySchema);
		}
		// 新增险种项
		for (int m = 1; m <= tFreeLCPolSet.size(); m++) {
			LCPolSchema tempLCPolSchema = new LCPolSchema();
			tempLCPolSchema.setSchema(tFreeLCPolSet.get(m));
			tempLCPolSchema.setContNo(tMainPolSchema.getContNo());
			tempLCPolSchema.setMainPolNo(tMainPolSchema.getMainPolNo());
			tempLCPolSchema.setPolNo(tPolNo);
			tempLCPolSchema.setInsuredNo(tMainPolSchema.getInsuredNo());
			tempLCPolSchema.setInsuredName(tMainPolSchema.getInsuredName());
			tempLCPolSchema.setInsuredSex(tMainPolSchema.getInsuredSex());
			tempLCPolSchema.setInsuredBirthday(tMainPolSchema
					.getInsuredBirthday());
			tempLCPolSchema.setInsuredAppAge(tMainPolSchema.getInsuredAppAge());
			// proposalno 没有必要设置
			tempLCPolSchema.setStandPrem(tSplitPrem);
			tempLCPolSchema.setPrem(tSplitPrem);
			tempLCPolSchema.setSumPrem(tSplitPrem);
			tempLCPolSchema.setAmnt(currPrem);
			tempLCPolSchema.setRiskAmnt(currPrem);
			// 加到险种集合中
			tLCPolSet.add(tempLCPolSchema);
		}
		// 新增应收子表项
		for (int m = 1; m <= tFreeLJSPayPersonSet.size(); m++) {
			LJSPayPersonSchema tempLJSPayPersonSchema = new LJSPayPersonSchema();
			tempLJSPayPersonSchema.setSchema(tFreeLJSPayPersonSet.get(m));
			tempLJSPayPersonSchema.setContNo(tMainPolSchema.getContNo());
			tempLJSPayPersonSchema.setPolNo(tPolNo);
			tempLJSPayPersonSchema.setSumDuePayMoney(tSplitPrem);
			tempLJSPayPersonSchema.setSumActuPayMoney(tSplitPrem);
			// 加到集合中
			tLJSPayPersonSet.add(tempLJSPayPersonSchema);
		}

		return true;
	}

	/**
	 * 处理实收保费
	 * 目前只生成实收总表
	 * @return
	 */
	private boolean dealJAPay() {
		//
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		tLJAPayPersonSet.set(this.mLJAPayPersonSet);
		double sumpay = 0;
		//tongmeng 2010-11-08 modify
		//按照币种汇总ljapay
		Hashtable tCurrencyMap = new Hashtable();
		for(int i=1;i<=tLJAPayPersonSet.size();i++)
		{
//			//tongmeng 2010-11-08 modify
//			//按照币种汇总
//			
//			String tCurrency = tLJAPayPersonSet.get(i).getCurrency();
//			if(!tCurrencyMap.containsKey(tCurrency))
//			{
//				tCurrencyMap.put(tCurrency, tLJAPayPersonSet.get(i).getSumActuPayMoney());
//			}
//			else
//			{
//				double tempSumpay = PubFun.round((Double)tCurrencyMap.get(tCurrency), 2) + PubFun.round(tLJAPayPersonSet.get(i).getSumActuPayMoney(),2);
//				tCurrencyMap.put(tCurrency, tempSumpay);
//			}
//			//tongmeng 2009-12-23 modify
//			//先格式化后再加和
//			//sumpay = PubFun.round(sumpay,2) + PubFun.round(tLJAPayPersonSet.get(i).getSumActuPayMoney(),2);
			
			//营改增  add zhangyingfeng 2016-07-06
			//注释掉原来的，以后需要可以改回
			//需要汇总 总额 总净额  总税额
			String tCurrency = tLJAPayPersonSet.get(i).getCurrency();			
			if(!tCurrencyMap.containsKey(tCurrency))
			{
				Hashtable tSumLjaPayMap = new Hashtable<String, Double>();  //放置  总净额  总税额  总额
				tSumLjaPayMap.put(tCurrency+"Sum", tLJAPayPersonSet.get(i).getSumActuPayMoney());
				tSumLjaPayMap.put(tCurrency+"NetAm", tLJAPayPersonSet.get(i).getNetAmount());
				tSumLjaPayMap.put(tCurrency+"TaxAm", tLJAPayPersonSet.get(i).getTaxAmount());
				tSumLjaPayMap.put(tCurrency+"Tax", tLJAPayPersonSet.get(i).getTax());   //税率暂时取第一条， 系统后续具体业务类型可能税率不同，汇总不能统一
				tCurrencyMap.put(tCurrency, tSumLjaPayMap);
			}
			else
			{
				double tempSumpay = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"Sum"), 2) + PubFun.round(tLJAPayPersonSet.get(i).getSumActuPayMoney(),2);
				double tempSumpayNetAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"NetAm"), 2) + PubFun.round(tLJAPayPersonSet.get(i).getNetAmount(),2);
				double tempSumpayTaxAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"TaxAm"), 2) + PubFun.round(tLJAPayPersonSet.get(i).getTaxAmount(),2);
				double tempSumpayTax=(Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"Tax");
				Hashtable tSumLjaPayMap = new Hashtable<String, Double>();  //放置  总净额  总税额  总额
				tSumLjaPayMap.put(tCurrency+"Sum", tempSumpay);
				tSumLjaPayMap.put(tCurrency+"NetAm", tempSumpayNetAm);
				tSumLjaPayMap.put(tCurrency+"TaxAm", tempSumpayTaxAm);
				tSumLjaPayMap.put(tCurrency+"Tax",tempSumpayTax);
				tCurrencyMap.put(tCurrency, tSumLjaPayMap);
			}
			//营改增  end zhangyingfeng 2016-07-06
		}
		//如果有余额的话,需要累计余额数据
		for(int i=1;i<=this.mLJAPayPersonYETSet.size();i++)
		{
//			String tCurrency = mLJAPayPersonYETSet.get(i).getCurrency();
//			if(!tCurrencyMap.containsKey(tCurrency))
//			{
//				tCurrencyMap.put(tCurrency, mLJAPayPersonYETSet.get(i).getSumActuPayMoney());
//			}
//			else
//			{
//				double tempSumpay = PubFun.round((Double)tCurrencyMap.get(tCurrency), 2) + PubFun.round(mLJAPayPersonYETSet.get(i).getSumActuPayMoney(),2);
//				tCurrencyMap.put(tCurrency, tempSumpay);
//			}
//			
//			//tongmeng 2009-12-23 modify
//			//先格式化后再加和
//			//sumpay = PubFun.round(sumpay,2) + PubFun.round(mLJAPayPersonYETSet.get(i).getSumActuPayMoney(),2);
		
			//营改增  add zhangyingfeng 2016-07-06
			//注释掉原来的，以后需要可以改回
			//需要汇总 总额 总净额  总税额
			String tCurrency = mLJAPayPersonYETSet.get(i).getCurrency();
			if(!tCurrencyMap.containsKey(tCurrency))
			{
				Hashtable tSumLjaPayMap = new Hashtable<String, Double>();  //放置  总净额  总税额  总额
				//没有对应实收，只算余额，其余置0
				tSumLjaPayMap.put(tCurrency+"Sum", mLJAPayPersonYETSet.get(i).getSumActuPayMoney());
				tSumLjaPayMap.put(tCurrency+"NetAm", 0);
				tSumLjaPayMap.put(tCurrency+"TaxAm", 0);
				tSumLjaPayMap.put(tCurrency+"Tax", 0);   
				tCurrencyMap.put(tCurrency, tSumLjaPayMap);
			}
			else
			{
				double tempSumpay = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"Sum"), 2) + PubFun.round(mLJAPayPersonYETSet.get(i).getSumActuPayMoney(),2);
				double tempSumpayNetAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"NetAm"), 2);
				double tempSumpayTaxAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"TaxAm"), 2);
				double tempSumpayTax=(Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"TaxAm");
				Hashtable tSumLjaPayMap = new Hashtable<String, Double>();  //放置  总净额  总税额  总额
				tSumLjaPayMap.put(tCurrency+"Sum", tempSumpay);
				tSumLjaPayMap.put(tCurrency+"NetAm", tempSumpayNetAm);
				tSumLjaPayMap.put(tCurrency+"TaxAm", tempSumpayTaxAm);
				tSumLjaPayMap.put(tCurrency+"Tax",tempSumpayTax);
				tCurrencyMap.put(tCurrency, tSumLjaPayMap);
			}
			//营改增  end zhangyingfeng 2016-07-06
		}
		//tongmeng 2010-11-08 modify
		//如果有溢交退费的话,累计溢交退费的数据
		
		//2012-01-31  溢交退费的数据不累计到ljapay中
		/*
		for(int i=1;i<=this.mLJAGetSet.size();i++)
		{
			String tCurrency = mLJAGetSet.get(i).getCurrency();
			if(!tCurrencyMap.containsKey(tCurrency))
			{
				tCurrencyMap.put(tCurrency, mLJAGetSet.get(i).getSumGetMoney());
			}
			else
			{
				double tempSumpay = PubFun.round((Double)tCurrencyMap.get(tCurrency), 2) + PubFun.round(mLJAGetSet.get(i).getSumGetMoney(),2);
				tCurrencyMap.put(tCurrency, tempSumpay);
			}
		}
		*/
		
		Enumeration eKey=tCurrencyMap.keys(); 
		while(eKey.hasMoreElements()) 
		{ 
			String key=(String)eKey.nextElement();
//			double tValue = PubFun.round(((Double)tCurrencyMap.get(key)),2);
			//营改增 add zhangyingfeng  2016-07-06
			//需要增加净额 税额 税率   取值集合原来的单一值改为map
			double tempSumpay = PubFun.round((Double)((Hashtable)tCurrencyMap.get(key)).get(key+"Sum"), 2);
			double tempSumpayNetAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(key)).get(key+"NetAm"), 2);
			double tempSumpayTaxAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(key)).get(key+"TaxAm"), 2);
			double tempSumpayTax=(Double)((Hashtable)tCurrencyMap.get(key)).get(key+"Tax");
			//营改增  end zhangyingfeng 2016-07-06
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			tLJAPaySchema.setPayNo(this.mPaySerialNo);
			//IncomeNo 家庭单和多主险设置成印刷号 incometype
			String tIncomeNo = mFamilyContNo;//多主险和家庭单放家庭单合同号
			String tIncomeNoType = "4";//多主险和家庭单放4
			if(this.mFamilyType.equals("0"))
			{
				//正常单,设置成合同号
				tIncomeNo = this.mLCContSet.get(1).getContNo();
				tIncomeNoType = "2";
			}
			tLJAPaySchema.setIncomeNo(tIncomeNo);
			tLJAPaySchema.setIncomeType(tIncomeNoType);

			tLJAPaySchema.setAppntNo(this.mOldLCContSchema.getAppntNo());
//			tLJAPaySchema.setSumActuPayMoney(tValue);
			//营改增 add zhangyingfeng  2016-07-06
			//需要增加净额 税额 税率  
			tLJAPaySchema.setSumActuPayMoney(tempSumpay);  //总额
			tLJAPaySchema.setTaxAmount(tempSumpayTaxAm);  //税额
			tLJAPaySchema.setNetAmount(tempSumpayNetAm); //净额
			tLJAPaySchema.setTax(tempSumpayTax); //税率
			//营改增  end zhangyingfeng 2016-07-06
			tLJAPaySchema.setPayDate(maxPayDate);
			tLJAPaySchema.setEnterAccDate(maxEnterAccDate);
			tLJAPaySchema.setConfDate(this.mCurrentDate);
			tLJAPaySchema.setSerialNo(mPaySerialNo);
			tLJAPaySchema.setOperator(mOperator);
			tLJAPaySchema.setMakeDate(this.mCurrentDate);
			tLJAPaySchema.setMakeTime(this.mCurrentTime);
			tLJAPaySchema.setModifyDate(this.mCurrentDate);
			tLJAPaySchema.setModifyTime(this.mCurrentTime);

			tLJAPaySchema.setManageCom(this.mOldLCContSchema.getManageCom());
			tLJAPaySchema.setAgentCom(this.mOldLCContSchema.getAgentCom());
			tLJAPaySchema.setAgentType(this.mOldLCContSchema.getAgentType());
			tLJAPaySchema.setOtherNo(this.mOldLCContSchema.getProposalContNo());
			tLJAPaySchema.setOtherNoType("6"); // 个人投保单号
			if (this.mOldLCContSchema.getSaleChnl() != null && 
					this.mOldLCContSchema.getSaleChnl().equals("03")) {
				tLJAPaySchema.setOtherNoType("7");
			}
			tLJAPaySchema.setCurrency(key); // 币种信息
			
			LJAPaySet tLJAPaySet = new LJAPaySet();
			tLJAPaySet.add(tLJAPaySchema);
			this.mLJAPaySet.add(tLJAPaySet);
		}
		sumpay = PubFun.round(sumpay, 2);
		
		return true;
	}
	

	/**
	 * 处理帐户信息
	 * 
	 * @param tVData
	 * @param i
	 * @return
	 */
	private boolean dealInsuAcc(VData tVData, int i) {
		//取出帐户相关表
		LCInsureAccSet tLCInsureAccSet = tVData.getObjectByObjectName(
				"LCInsureAccSet", 0) == null ? (new LCInsureAccSet())
				: (LCInsureAccSet) tVData.getObjectByObjectName(
						"LCInsureAccSet", 0);
		
		LCInsureAccTraceSet tLCInsureAccTraceSet = tVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0) == null ? (new LCInsureAccTraceSet())
				: (LCInsureAccTraceSet) tVData.getObjectByObjectName(
						"LCInsureAccTraceSet", 0);
		
		LCInsureAccClassSet tLCInsureAccClassSet = tVData
				.getObjectByObjectName("LCInsureAccClassSet", 0) == null ? (new LCInsureAccClassSet())
				: (LCInsureAccClassSet) tVData.getObjectByObjectName(
						"LCInsureAccClassSet", 0);

		LCInsureAccFeeSet tLCInsureAccFeeSet = tVData.getObjectByObjectName(
				"LCInsureAccFeeSet", 0) == null ? (new LCInsureAccFeeSet())
				: (LCInsureAccFeeSet) tVData.getObjectByObjectName(
						"LCInsureAccFeeSet", 0);

		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = tVData
				.getObjectByObjectName("LCInsureAccFeeTraceSet", 0) == null ? (new LCInsureAccFeeTraceSet())
				: (LCInsureAccFeeTraceSet) tVData.getObjectByObjectName(
						"LCInsureAccFeeTraceSet", 0);

		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = tVData
				.getObjectByObjectName("LCInsureAccClassFeeSet", 0) == null ? (new LCInsureAccClassFeeSet())
				: (LCInsureAccClassFeeSet) tVData.getObjectByObjectName(
						"LCInsureAccClassFeeSet", 0);
	
				
		if (tLCInsureAccSet.size() > 0) {
				this.mLCInsureAccSet.add(tLCInsureAccSet);
		}
		if (tLCInsureAccTraceSet.size() > 0) {
					this.mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
		}

		if (tLCInsureAccClassSet.size() > 0) {
					this.mLCInsureAccClassSet.add(tLCInsureAccClassSet);
		}	
		if (tLCInsureAccFeeSet.size() > 0) {
			this.mLCInsureAccFeeSet.add(tLCInsureAccFeeSet);
		}
		if (tLCInsureAccFeeTraceSet.size() > 0) {
			this.mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSet);
		}
		if (tLCInsureAccClassFeeSet.size() > 0) {
			this.mLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSet);
		}
		return true;
	}

	/**
	 * 处理被保人豁免险
	 * 
	 * @param tRiskData
	 * @param tFreeRiskData
	 * @return
	 */
	private boolean dealInsuredFreeRisk(VData tRiskData, VData tFreeRiskData,
			String tRiskCode) {
		// 取出所有可以豁免的险种
		if (this.mFreeInsuredRiskTable == null) {
			mFreeInsuredRiskTable = new Hashtable();
			SSRS tSSRS = new SSRS();
			String tSQL = "select code1 from ldcode1 where codetype='freerisk' and code='"
					+ "?code?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("code", tRiskCode);
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			for (int n = 1; n <= tSSRS.getMaxRow(); n++) {
				mFreeInsuredRiskTable.put(tSSRS.GetText(n, 1), tRiskCode);
			}
		}

		// 指向引用,直接修改数据
		// 正常险种数据集合
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = (LCPolSet) tRiskData.getObjectByObjectName("LCPolSet", 0);
		LCDutySet tLCDutySet = (LCDutySet) tRiskData.getObjectByObjectName(
				"LCDutySet", 0);
		LCPremSet tLCPremSet = (LCPremSet) tRiskData.getObjectByObjectName(
				"LCPremSet", 0);
		LCGetSet tLCGetSet = (LCGetSet) tRiskData.getObjectByObjectName(
				"LCGetSet", 0);

		// 豁免险种数据集合
		LCPolSet tFreeLCPolSet = (LCPolSet) tFreeRiskData
				.getObjectByObjectName("LCPolSet", 0);
		LCDutySet tFreeLCDutySet = (LCDutySet) tFreeRiskData
				.getObjectByObjectName("LCDutySet", 0);
		LCPremSet tFreeLCPremSet = (LCPremSet) tFreeRiskData
				.getObjectByObjectName("LCPremSet", 0);
		LCGetSet tFreeLCGetSet = (LCGetSet) tFreeRiskData
				.getObjectByObjectName("LCGetSet", 0);

		LCPolSchema tMainPolSchema = new LCPolSchema();

		// 获取豁免险种数据
		LCPolSchema tFreeLCPolSchema = tFreeLCPolSet.get(1);
		// 被保人豁免累计所有可以豁免的险种的保费
		double allPrem = 0;
		for (int m = 1; m <= this.mAllPolSet.size(); m++) { 
			String tempRiskCode = mAllPolSet.get(m).getRiskCode();
			if (this.mFreeInsuredRiskTable.containsKey(tempRiskCode)
					&& mAllPolSet.get(m).getInsuredNo().equals(
							tFreeLCPolSchema.getInsuredNo())) {
				//tongmeng 2009-12-23 modify
				//先格式化后再加和
				allPrem = PubFun.round(allPrem,2) + PubFun.round(mAllPolSet.get(m).getPrem(),2);
			}
		}
		logger.debug("累计总豁免的保费:" + allPrem);
		if (allPrem == 0) {
			// 没有可以豁免的保费
			// CError.buildErr(this,"改合同下没有需要豁免的险种,不需要投保豁免险!");
			return true;
		}
		// 累计该合同下可以豁免的保费
		double currPrem = 0;
		double currAmnt = 0;
		for (int m = 1; m <= tLCPolSet.size(); m++) {
			String tempRiskCode = tLCPolSet.get(m).getRiskCode();
			if (this.mFreeInsuredRiskTable.containsKey(tempRiskCode)
					&& tLCPolSet.get(m).getInsuredNo().equals(
							tFreeLCPolSchema.getInsuredNo())) {
				//tongmeng 2009-12-23 modify
				//先格式化后再加和
				currPrem = PubFun.round(currPrem,2) + PubFun.round(tLCPolSet.get(m).getPrem(),2);
				// currAmnt = currAmnt +
			}
			if (tLCPolSet.get(m).getPolNo().equals(
					tLCPolSet.get(m).getMainPolNo())) {
				tMainPolSchema.setSchema(tLCPolSet.get(m).getSchema());
			}
		}
		logger.debug("累计可豁免的保费:" + currPrem);
		if (currPrem == 0) {
			// 该合同不需要均分
			return true;
		}
		// 计算需要均分的保费
		double tSplitPrem = 0;
		//tongmeng 2009-12-23 modify
		//如果currPrem==allPrem 就不再进行折算了。
		if(PubFun.round(currPrem, 2)==PubFun.round(allPrem, 2))
		{
			tSplitPrem = tFreeLCPolSchema.getPrem();
		}
		else
		{
			tSplitPrem = (currPrem * tFreeLCPolSchema.getPrem()) / allPrem;
		}
		
		// 格式化数据
		tSplitPrem = PubFun.round(tSplitPrem,2);
		// 需要新生成保单号
		String tPolNo = "";
		// 生成险种保单号
		String tLimit = PubFun.getNoLimit(tMainPolSchema.getManageCom());
		tPolNo = PubFun1.CreateMaxNo("POLNO", tLimit);

		if (StrTool.cTrim(tPolNo).equals("")) {
			// @@错误处理
			CError.buildErr(this, "险种保单号生成失败!","01","024");
			return false;
		}
		// 新增缴费项
		for (int m = 1; m <= tFreeLCPremSet.size(); m++) {
			LCPremSchema tempLCPremSchema = new LCPremSchema();
			tempLCPremSchema.setSchema(tFreeLCPremSet.get(m));
			tempLCPremSchema.setContNo(tMainPolSchema.getContNo());
			tempLCPremSchema.setPolNo(tPolNo);
			tempLCPremSchema.setStandPrem(tSplitPrem);
			tempLCPremSchema.setPrem(tSplitPrem);
			tempLCPremSchema.setSumPrem(tSplitPrem);
			tempLCPremSchema.setSumPrem(tSplitPrem);
			// 加到保费集合中
			tLCPremSet.add(tempLCPremSchema);
		}
		// 新增领取项
		for (int m = 1; m <= tFreeLCGetSet.size(); m++) {
			LCGetSchema tempLCGetSchema = new LCGetSchema();
			tempLCGetSchema.setSchema(tFreeLCGetSet.get(m));
			tempLCGetSchema.setContNo(tMainPolSchema.getContNo());
			tempLCGetSchema.setPolNo(tPolNo);
			tempLCGetSchema.setInsuredNo(tMainPolSchema.getInsuredNo());
			// 领取项,设置的是保费
			tempLCGetSchema.setStandMoney(currPrem);
			tempLCGetSchema.setActuGet(currPrem);
			// 加到领取集合中
			tLCGetSet.add(tempLCGetSchema);
		}
		// 新增责任项
		for (int m = 1; m <= tFreeLCDutySet.size(); m++) {
			LCDutySchema tempLCDutySchema = new LCDutySchema();
			tempLCDutySchema.setSchema(tFreeLCDutySet.get(m));
			tempLCDutySchema.setContNo(tMainPolSchema.getContNo());
			tempLCDutySchema.setPolNo(tPolNo);
			tempLCDutySchema.setStandPrem(tSplitPrem);
			tempLCDutySchema.setPrem(tSplitPrem);
			tempLCDutySchema.setSumPrem(tSplitPrem);
			tempLCDutySchema.setAmnt(currPrem);
			tempLCDutySchema.setRiskAmnt(currPrem);
			// 加到责任集合中
			tLCDutySet.add(tempLCDutySchema);
		}
		// 新增险种项
		for (int m = 1; m <= tFreeLCPolSet.size(); m++) {
			LCPolSchema tempLCPolSchema = new LCPolSchema();
			tempLCPolSchema.setSchema(tFreeLCPolSet.get(m));
			tempLCPolSchema.setContNo(tMainPolSchema.getContNo());
			tempLCPolSchema.setMainPolNo(tMainPolSchema.getMainPolNo());
			tempLCPolSchema.setPolNo(tPolNo);
			tempLCPolSchema.setInsuredNo(tMainPolSchema.getInsuredNo());
			tempLCPolSchema.setInsuredName(tMainPolSchema.getInsuredName());
			tempLCPolSchema.setInsuredSex(tMainPolSchema.getInsuredSex());
			tempLCPolSchema.setInsuredBirthday(tMainPolSchema
					.getInsuredBirthday());
			tempLCPolSchema.setInsuredAppAge(tMainPolSchema.getInsuredAppAge());
			// proposalno 没有必要设置
			tempLCPolSchema.setStandPrem(tSplitPrem);
			tempLCPolSchema.setPrem(tSplitPrem);
			tempLCPolSchema.setSumPrem(tSplitPrem);
			tempLCPolSchema.setAmnt(currPrem);
			tempLCPolSchema.setRiskAmnt(currPrem);
			// 加到险种集合中
			tLCPolSet.add(tempLCPolSchema);
		}

		return true;
	}



	/**
	 * 返回拆分结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return this.mResult;
	}

	private LCContSchema makeLCCont(LCPolSchema tLCPolSchema) {
		LCContSchema tLCContSchema = new LCContSchema();
		// 先把原有合同信息拷贝到新数据中
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLCContSchema, this.mOldLCContSchema);

		tLCContSchema.setPayIntv(tLCPolSchema.getPayIntv());
		tLCContSchema.setCValiDate(tLCPolSchema.getCValiDate());
		tLCContSchema.setSignDate(this.mCurrentDate);
		tLCContSchema.setSignTime(this.mCurrentTime);		
		tLCContSchema.setOrganizeDate(this.mOrganizeDate);
		tLCContSchema.setOrganizeTime(this.mOrganizeTime);
		// 被保人信息和合同号需要改变
		tLCContSchema.setContNo(tLCPolSchema.getContNo());
		tLCContSchema.setInsuredBirthday(tLCPolSchema.getInsuredBirthday());
		tLCContSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLCContSchema.setInsuredName(tLCPolSchema.getInsuredName());
		tLCContSchema.setInsuredSex(tLCPolSchema.getInsuredSex());
		// 从被保人信息表中取出缺失的信息
		for (int n = 1; n <= this.mLCInsuredSet.size(); n++) {
			if (mLCInsuredSet.get(n).getInsuredNo().equals(
					tLCContSchema.getInsuredNo())) {
				tLCContSchema.setInsuredIDNo(mLCInsuredSet.get(n).getIDNo());
				tLCContSchema
						.setInsuredIDType(mLCInsuredSet.get(n).getIDType());
				break;
			}
		}
		//合同的币种设置为主险的币种
		
		tLCContSchema.setCurrency(tLCPolSchema.getCurrency());
		//tongmeng 2009-02-26 modify
		//增加回单日期的默认处理
		this.dealDefaultGetPolDate(tLCContSchema);
		return tLCContSchema;
	}

	private void dealDefaultGetPolDate(LCContSchema tLCContSchema)
	{
		String tSQL = "select distinct NeedGetPolDate from lmriskapp where "
			        + " riskcode in (select riskcode from lcpol where contno='"+"?contno?"+"')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("contno",tLCContSchema.getPrtNo());
		ExeSQL tExeSQL = new ExeSQL();
		
		String needgetpoldate = tExeSQL.getOneValue(sqlbv2);

        if ((needgetpoldate == null) || needgetpoldate.equals("0"))
        {
            //回执日期为空
        }
        else if (needgetpoldate.equals("1"))
        {
            //令回执日期为投保单录入日期
        	tLCContSchema.setGetPolDate(tLCContSchema.getMakeDate());
        	tLCContSchema.setGetPolTime(tLCContSchema.getMakeTime());
            //保单回执客户签收日期
        	tLCContSchema.setCustomGetPolDate(tLCContSchema.getMakeDate());
        	//已回执回收的不需要打印
        	//tLCContSchema.setPrintCount(1);
        }
        else if (needgetpoldate.equals("2"))
        {
            //令回执日期为投保单录入日期
        	tLCContSchema.setGetPolDate(PubFun.getCurrentDate());
        	tLCContSchema.setGetPolTime(PubFun.getCurrentTime());
            //保单回执客户签收日期
        	
        	tLCContSchema.setCustomGetPolDate(PubFun.getCurrentDate());
        	//已回执回收的不需要打印
        	//tLCContSchema.setPrintCount(1);
        }
	}
	/**
	 * 准备需要删除数据的SQL
	 * 
	 * @return
	 */
	private boolean prepareDelSQL() {
		try {
			String tempcontno="";
			if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
			{
				tempcontno = mOldLCContSchema.getContNo();
			}
			else
			{
				//tempcontno = mOldLCContSchema.getPrtNo();
				if("card".equals(mMark))
				{
					//卡单签单前 CONTNO！=prtno  签单后 相同
					tempcontno =mOldLCContSchema.getContNo();
				}else{
					tempcontno = mOldLCContSchema.getPrtNo();
				}
			}
			String tSQL1 = "delete from lccont where contno = '"
					+ "?contno?" + "' ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL1);
			sqlbv3.put("contno", tempcontno);
			this.mMMap.put(sqlbv3, "DELETE");
			//tongmeng 2008-11-03 modify
			//修改为删除合同特约数据 ????
			String tSQL2 = "delete from lccspec where contno = '"
					+ "?contno?" + "' ";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSQL2);
			sqlbv4.put("contno", mOldLCContSchema.getPrtNo());
			this.mMMap.put(sqlbv4, "DELETE");
			String tSQL3 = "delete from lcbnf where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL3);
			sqlbv5.put("contno", tempcontno);
			this.mMMap.put(sqlbv5, "DELETE");
			String tSQL4 = "delete from lcpol where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL4);
			sqlbv6.put("contno", tempcontno);
			this.mMMap.put(sqlbv6, "DELETE");
			String tSQL5 = "delete from lcget where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSQL5);
			sqlbv7.put("contno", tempcontno);
			this.mMMap.put(sqlbv7, "DELETE");
			String tSQL6 = "delete from lcprem where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSQL6);
			sqlbv8.put("contno", tempcontno);
			this.mMMap.put(sqlbv8, "DELETE");
			String tSQL7 = "delete from lcduty where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSQL7);
			sqlbv9.put("contno", tempcontno);
			this.mMMap.put(sqlbv9, "DELETE");
			String tSQL8 = "delete from lcappnt where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSQL8);
			sqlbv10.put("contno", tempcontno);
			this.mMMap.put(sqlbv10, "DELETE");
			String tSQL9 = "delete from lcinsured where contno='" + "?contno?" + "'";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tSQL9);
			sqlbv11.put("contno", tempcontno);
			this.mMMap.put(sqlbv11, "DELETE");
			String tSQL10 = "delete from lacommisiondetail where grpcontno='" + "?grpcontno?" + "' and agentcode ='"+"?agentcode?"+"' ";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tSQL10);
			sqlbv12.put("grpcontno", mOldLCContSchema.getPrtNo());
			sqlbv12.put("agentcode", mOldLCContSchema.getAgentCode());
			this.mMMap.put(sqlbv12, "DELETE");
			//删除告知数据和告知参数数据
			String tSQL11 = "delete from LCCustomerImpart where contno='" + "?contno?" +"' ";
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tSQL11);
			sqlbv13.put("contno", tempcontno);
			this.mMMap.put(sqlbv13, "DELETE");
			String tSQL12 = "delete from LCCustomerImpartParams where contno='" + "?contno?" + "' ";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tSQL12);
			sqlbv14.put("contno", tempcontno);
			this.mMMap.put(sqlbv14, "DELETE");
			// 下面继续补充需要删除的数据.............
			//tongmeng 2010-11-17
			//投资 计划表换号
			String tSQL13 = "delete from lcperinvestplan where contno='" + "?contno?" + "' ";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSQL13);
			sqlbv15.put("contno", tempcontno);
			this.mMMap.put(sqlbv15, "DELETE");
			//删除应收数据和之前的折扣表
			String tSQL14 = "delete from lcdiscount where contno='" + "?contno?" + "' ";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSQL14);
			sqlbv16.put("contno", tempcontno);
			this.mMMap.put(sqlbv16, "DELETE");
			String tSQL15 = "delete from ljspay where otherno='" + "?contno?" + "' and othernotype='2' ";
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSQL15);
			sqlbv17.put("contno", tempcontno);
			this.mMMap.put(sqlbv17, "DELETE");
			String tSQL16 = "delete from ljspayperson where contno='" + "?contno?" + "' ";
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSQL16);
			sqlbv18.put("contno", tempcontno);
			this.mMMap.put(sqlbv18, "DELETE");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,"发生错误在准备删除数据方法里:"+e.toString(),"01","038");
			return false;
		}

		return true;
	}

	/**
	 * 准备输出数据
	 * 
	 * @return
	 */
	private boolean prepareOutData() {
		try {
			mMMap.put(this.mLCAppntSet, "INSERT");
			mMMap.put(this.mLCInsuredSet, "INSERT");
			mMMap.put(this.mLCContSet, "INSERT");
			mMMap.put(this.mLCPolSet, "INSERT");
			mMMap.put(this.mLCDutySet, "INSERT");
			mMMap.put(this.mLCPremSet, "INSERT");
			mMMap.put(this.mLCGetSet, "INSERT");
			// 下面的需要判断是否有值
			// 帐户数据在LCContSignBL处理.
			//保单状态数据
			mMMap.put(this.mLCContStateSet,"INSERT");
			if(this.mLACommisionDetailSet.size()>0)
			{
				mMMap.put(this.mLACommisionDetailSet,"INSERT");
			}
			
			//处理财务数据
			if(this.mInsertLJTempFeeClassSet.size()>0)
			{
				mMMap.put(this.mInsertLJTempFeeClassSet,"INSERT");
			}
			if(this.mInsertLJTempFeeSet.size()>0)
			{
				mMMap.put(this.mInsertLJTempFeeSet,"INSERT");
			}
			
			if(this.mInsertLJFIGetSet.size()>0)
			{
				mMMap.put(this.mInsertLJFIGetSet,"INSERT");
			}
			
			//以下三张表需要在此程序里处理,
			//LCContSignBL中只处理了排除LCInsureAcc之外的帐户相关表
			if (mLCGetToAccSet.size() > 0)
				mMMap.put(this.mLCGetToAccSet, "INSERT");
			if (mLCPremToAccSet.size() > 0)
				mMMap.put(this.mLCPremToAccSet, "INSERT");
			if (mLCInsureAccSet.size() > 0)
				mMMap.put(this.mLCInsureAccSet, "INSERT");
//			if (mLCInsureAccTraceSet.size() > 0)
//				mMMap.put(this.mLCInsureAccTraceSet, "INSERT");
//			if (mLCInsureAccClassSet.size() > 0)
//				mMMap.put(this.mLCInsureAccClassSet, "INSERT");
//			if (mLCInsureAccFeeSet.size() > 0)
//				mMMap.put(this.mLCInsureAccFeeSet, "INSERT");
//			if (mLCInsureAccFeeTraceSet.size() > 0)
//				mMMap.put(this.mLCInsureAccFeeTraceSet, "INSERT");
//			if (mLCInsureAccClassFeeSet.size() > 0)
//				mMMap.put(this.mLCInsureAccClassFeeSet, "INSERT");
			//
			
			if (mLCBnfSet.size() > 0)
				mMMap.put(this.mLCBnfSet, "INSERT");
//			if (mLCSpecSet.size() > 0)
//				mMMap.put(this.mLCSpecSet, "INSERT");
			if (mLCCSpecSet.size() > 0)
				mMMap.put(this.mLCCSpecSet, "INSERT");
			//处理告知数据
			if(this.mLCCustomerImpartSet.size()>0)
			{
				mMMap.put(this.mLCCustomerImpartSet, "INSERT");
			}
			if(this.mLCCustomerImpartParamsSet.size()>0)
			{
				mMMap.put(this.mLCCustomerImpartParamsSet, "INSERT");
			}
			
			
			//将暂收数据和暂收费分类表加到集合中
			mMMap.put(this.mLJTempFeeClassSet,"UPDATE");
			mMMap.put(this.mLJTempFeeSet,"UPDATE");
			//处理余额数据
			if(this.mLJAPayPersonYETSet.size()>0)
			{
				mMMap.put(this.mLJAPayPersonYETSet,"INSERT");
			}
			
			//如果有退费记录的话,也生成
			if(this.mLJAGetSet.size()>0)
			{
				mMMap.put(this.mLJAGetSet,"INSERT");
			}
			if(this.mLJAGetOtherSet.size()>0)
			{
				mMMap.put(this.mLJAGetOtherSet,"INSERT");
			}
			if(this.mLOPRTManagerSet.size()>0)
			{
				mMMap.put(this.mLOPRTManagerSet,"INSERT");
			}
			//处理实收个人缴费表数据
			mMMap.put(this.mLJAPayPersonSet, "INSERT");
			//准备实收总表数据
			mMMap.put(this.mLJAPaySet,"INSERT");
			//准备新的折扣数据
			mMMap.put(this.mLCDiscountSet,"INSERT");
			//提交前数据处理(包括设置家庭单号,给相关数据表做换号处理等操作.)
			
			//tongmeng 2010-11-17 add
			//投资计划表换号
			if(this.mLCPerInvestPlanSet.size()>0)
			{
				mMMap.put(this.mLCPerInvestPlanSet, "INSERT");
			}
			if (!this.prepareUpdataData()) {
				return false;
			}
			
			this.mResult.add(mMMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,"发生错误在准备输出数据里:"+e.toString(),"01","039");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要更新的数据
	 * 
	 * @return
	 */
	private boolean prepareUpdataData() {
		try {
			// 设置家庭单类型和家庭单号
			for (int i = 1; i <= this.mLCContSet.size(); i++) {
				// 家庭单类型
				this.mLCContSet.get(i).setFamilyType(mFamilyType);
				// 家庭单合同号
				this.mLCContSet.get(i).setFamilyContNo(mFamilyContNo);
			}
			// 更新签单前的相关数据表~
			MMap tmpMap = new MMap();
			// 进行换号处理
			String tables[] = {
					// 核保主表和轨迹表
					//6.0原有程序没对此表做换号...暂时不要处理...
					"LCCUWMaster", "LCCUWSub", "LCCUWError", "LCUWMaster",
					"LCUWSub", "LCUWError","LCINDUWMASTER","LCINDUWSUB",
					// 问题件表
					//6.0原有程序没对此表做换号...暂时不要处理...
					"LCIssuePol",
					// 体检表
					"lcpenotice", "lcpenoticeitem",
					// 生调表
					"lcrreport", "lcrreportprt", "lcrreportresult", "LCPolOther" };
			String condition = "";
			String newContNo = "";
			String oldContNo = "";
			if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
			{
				oldContNo = this.mOldLCContSchema.getContNo();
			}
			else
			{
				oldContNo = this.mOldLCContSchema.getPrtNo();
			}			
			if (this.mFamilyType.equals("0")) {
				newContNo = this.mLCContSet.get(1).getContNo();
			} else {
				newContNo = mFamilyContNo;
			}
			
			condition = " contno='?newContNo?', ModifyDate='?mCurrentDate?', ModifyTime='?mCurrentTime?' ";
			String wherepart = " contno='?oldContNo?'";
			Vector vec = PubFun.formUpdateSql(tables, condition, wherepart);
			if (vec != null) {
				for (int i = 0; i < vec.size(); i++) {
					// getClass();
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql((String) vec.get(i));
					sqlbv.put("newContNo", newContNo);
					sqlbv.put("mCurrentDate", this.mCurrentDate);
					sqlbv.put("mCurrentTime",  this.mCurrentTime);
					sqlbv.put("oldContNo", oldContNo);
					this.mMMap.put(sqlbv, "UPDATE");
				}
			}
			
			// 统一将账户表的结算时间改成当前时间
			String[] tAccTables = { "LCInsureAcc", "LCInsureAccClass",
					"LCInsureAccFee", "LCInsureAccClassFee" };
			//tongmeng 2010-11-22 modify
			//更新账户成立日期和时间
			String tAccCondition = " ModifyDate='" + this.mCurrentDate
					+ "', ModifyTime='" + this.mCurrentTime + "',maketime = '" + this.mCurrentTime
					+ "',balatime = '" + this.mCurrentTime + "',accfounddate='"+this.mCurrentDate+"' "
					+ ",accfoundtime='"+this.mCurrentTime+"' ";
			String tWherePart = " contno='" + newContNo + "'";
			Vector tAccVec = PubFun.formUpdateSql(tAccTables, tAccCondition,
					tWherePart);
			if (tAccVec != null) {
				for (int i = 0; i < tAccVec.size(); i++) {
					this.mMMap.put((String) tAccVec.get(i),"UPDATE");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,"发生错误在准备更新帐户等数据方法里:"+e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 拆分豁免险种
	 * 
	 * @param tVData
	 * @return
	 */
	private boolean splitFreeRisk(VData sVData, VData sFreeRiskVData, int i) {
		// 豁免险需要把一个大的豁免险,拆分到每个小合同上.
		// 需要新生成的数据有,LCDuty,lcprem,lcget,需要注意保费需要重新计算.....
		// ldcode1
		// 按照豁免险的种类处理拆分
		try {
			for (int n = 0; n < sFreeRiskVData.size(); n++) {
				VData oneFreeRiskData = new VData();
				oneFreeRiskData = (VData) sFreeRiskVData.getObjectByObjectName(
						"VData", n);
				LCPolSet tFreeLCPolSet = (LCPolSet) oneFreeRiskData
						.getObjectByObjectName("LCPolSet", 0);
				// 豁免险此处只会有一条记录!!!如果多了说明有问题
				if (tFreeLCPolSet.size() != 1) {
					CError.buildErr(this, "豁免险数据有问题!","01","022");
					return false;
				}
				LCPolSchema tFreeLCPolSchema = new LCPolSchema();
				tFreeLCPolSchema = tFreeLCPolSet.get(1);
				// 判断是何种豁免险
				String tRiskCode = tFreeLCPolSchema.getRiskCode();
				String tRiskFlag = "";
				String tSQL = "select (case when risktype7 is null then '0' else risktype7 end) from lmriskapp where riskcode = '"
						+ "?riskcode?" + "'";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(tSQL);
				sqlbv19.put("riskcode", tRiskCode);
				ExeSQL tExeSQL = new ExeSQL();
				tRiskFlag = tExeSQL.getOneValue(sqlbv19);
				logger.debug("tRiskFlag:" + tRiskFlag);

				// 查询所有可以豁免的险种

				if (tRiskFlag.equals("1")) {
					// 投保人豁免险
					//拆分时，如果是投保人豁免险，对家庭单需要特殊处理。
					//因为家庭单的第一被保人就是投保人。所以，豁免险拆分后，应该在其余被保人的合同上体现。
					//第一被保人的小合同上不会存在任何投保人豁免先的数据。
					if (!this.dealAppntFreeRisk(sVData, oneFreeRiskData,
							tRiskCode)) {
						this.mFreeAppntRiskTable = null;
						return false;
					}
					// 置空,防止险种有变化,导致豁免险种范围变化
					this.mFreeAppntRiskTable = null;
				} else if (tRiskFlag.equals("2")) {
					// 被保人豁免险
					if (!this.dealInsuredFreeRisk(sVData, oneFreeRiskData,
							tRiskCode)) {
						this.mFreeInsuredRiskTable = null;
						return false;
					}
					// 置空,防止险种有变化,导致豁免险种范围变化
					this.mFreeInsuredRiskTable = null;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		}

		return true;
	}

	/**
	 * 拆分正常险种
	 * 
	 * @param tVData
	 * @return
	 */
	private boolean splitNormRisk(VData tVData, int i) {
		VData res = new VData();
		// 险种相关表
		// LCContSet tLCContSet = new LCContSet();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCPolSet tLCPolSet = tVData.getObjectByObjectName("LCPolSet", 0) == null ? (new LCPolSet())
				: (LCPolSet) tVData.getObjectByObjectName("LCPolSet", 0);
		if (tLCPolSet.size() <= 0) {
			CError.buildErr(this,"合同下的保单信息有误,请检查!","01","025");
			return false;
		}
		// 取出主险数据
		LCPolSchema tMainLCPolSchema = new LCPolSchema();
		// 设置签单日期,签单时间
		for (int n = 1; n <= tLCPolSet.size(); n++) {
			if (tLCPolSet.get(n).getMainPolNo().equals(
					tLCPolSet.get(n).getPolNo())) {
				tMainLCPolSchema.setSchema(tLCPolSet.get(n));
			}
			
			tLCPolSet.get(n).setSignDate(this.mCurrentDate);
			tLCPolSet.get(n).setSignTime(this.mCurrentTime);
			tLCPolSet.get(n).setModifyDate(this.mCurrentDate);
			tLCPolSet.get(n).setModifyTime(this.mCurrentTime);
			
			//tongmeng 2010-11-17 modify
			//补充投资计划表换号
			
			LCPerInvestPlanSet tLCPerInvestPlanSet = new LCPerInvestPlanSet();
			LCPerInvestPlanDB tLCPerInvestPlanDB = new LCPerInvestPlanDB();
			String tSQL = "select * from LCPerInvestPlan where contno='"+"?contno?"+"' and polno='"+"?polno?"+"' ";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tSQL);
			sqlbv20.put("contno", tLCPolSet.get(n).getPrtNo());
			sqlbv20.put("polno", tLCPolSet.get(n).getProposalNo());
			tLCPerInvestPlanSet = tLCPerInvestPlanDB.executeQuery(sqlbv20);
			for(int m=1;m<=tLCPerInvestPlanSet.size();m++)
			{
				tLCPerInvestPlanSet.get(m).setContNo(tLCPolSet.get(n).getContNo());
				tLCPerInvestPlanSet.get(m).setPolNo(tLCPolSet.get(n).getPolNo());
			}
			if(tLCPerInvestPlanSet.size()>0)
			{
				this.mLCPerInvestPlanSet.add(tLCPerInvestPlanSet);
			}
			
		}
		this.mLCPolSet.add(tLCPolSet);
		//处理险种状态
		for (int k = 1; k <= tLCPolSet.size(); k++) {
			LCPolSchema tempLCPolSchema = new LCPolSchema();
			tempLCPolSchema = tLCPolSet.get(k);
			String polNo = tempLCPolSchema.getPolNo();
			String tCvalidate = tempLCPolSchema.getCValiDate();

			// Available: 0-有效 1-失效 （险种状态）
			// Terminate: 0-未终止 1-终止 （险种状态）
			// Lost: 0-未挂失 1-挂失 （保单状态）
			// PayPrem: 0-正常交费 1-保费自动垫交 （险种状态）
			// Loan: 0-未贷款 1-贷款 （险种状态和保单状态）
			// BankLoan: 0-未质押银行贷款 1-质押银行贷款 （险种状态和保单状态）
			// RPU：0-未减额缴清 1-减额缴清 （险种状态）
			LCContStateSchema tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setContNo(tempLCPolSchema.getContNo());
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo(tempLCPolSchema.getPolNo());
			tLCContStateSchema.setStateType("Available");
			tLCContStateSchema.setState("0");
			tLCContStateSchema.setStartDate(tCvalidate);
			tLCContStateSchema.setOperator(this.mOperator);
			tLCContStateSchema.setMakeDate(this.mCurrentDate);
			tLCContStateSchema.setMakeTime(this.mCurrentTime);
			tLCContStateSchema.setModifyDate(this.mCurrentDate);
			tLCContStateSchema.setModifyTime(this.mCurrentTime);
			this.mLCContStateSet.add(tLCContStateSchema);
		}
        //获取合同号，主要针对卡单 其合同号和印刷好不一样
		String tSQL ="select contno from lcpol where prtno ='"+"?prtno?"+"'";
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(tSQL);
		sqlbv21.put("prtno", tMainLCPolSchema.getPrtNo());
		ExeSQL tExeSQL =new ExeSQL();
		mContNo =tExeSQL.getOneValue(sqlbv21);
		// 复制投保人数据
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCAppntDB tOldAppntDB = new LCAppntDB();
		if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
		{
			tOldAppntDB.setContNo(tMainLCPolSchema.getContNo());
		}
		else
		{
			//tOldAppntDB.setContNo(tMainLCPolSchema.getPrtNo());
			//个险签单前 合同号和印刷号是一样的 
			tOldAppntDB.setContNo(mContNo);//不影响个险，同时能够保证卡单正常 卡单合同号和印刷号一直就不一样
		}		
		if (!tOldAppntDB.getInfo()) {
			CError.buildErr(this, "查询投保人信息出错!","01","026");
			return false;
		}
		tLCAppntSchema.setSchema(tOldAppntDB.getSchema());
		tLCAppntSchema.setModifyDate(this.mCurrentDate);
		tLCAppntSchema.setModifyTime(this.mCurrentTime);
		// if(i!=0)
		{
			tLCAppntSchema.setContNo(tMainLCPolSchema.getContNo());
		}

		this.mLCAppntSet.add(tLCAppntSchema);
		// 复制被保人数据
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tOldLCInsuredDB = new LCInsuredDB();
		if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
		{
			tOldLCInsuredDB.setContNo(tMainLCPolSchema.getContNo());
		}
		else
		{
			//tOldLCInsuredDB.setContNo(tMainLCPolSchema.getPrtNo());
			tOldLCInsuredDB.setContNo(mContNo);
		}		
		tOldLCInsuredDB.setInsuredNo(tMainLCPolSchema.getInsuredNo());
		if (!tOldLCInsuredDB.getInfo()) {
			CError.buildErr(this, "查询被保人信息出错!","01","027");
			return false;
		}
		
		
		tLCInsuredSchema.setSchema(tOldLCInsuredDB.getSchema());
		tLCInsuredSchema.setModifyDate(this.mCurrentDate);
		tLCInsuredSchema.setModifyTime(this.mCurrentTime);
		tLCInsuredSchema.setContNo(tMainLCPolSchema.getContNo());
		this.mLCInsuredSet.add(tLCInsuredSchema);
		// 取出保费数据
		LCPremSet tLCPremSet = tVData.getObjectByObjectName("LCPremSet", 0) == null ? (new LCPremSet())
				: (LCPremSet) tVData.getObjectByObjectName("LCPremSet", 0);
		// if(tLCPremSet.size())
		this.mLCPremSet.add(tLCPremSet);
		//tongmeng 2008-09-26 add
		//如果是第一被保人的话,记录他的第一主险信息,为后面处理余额做铺垫~
		//tongmeng 2009-03-06 add
		//为了兼容外包导入
		//tongmeng 2009-09-31 modify
		//取被保人表中最小的序列号.
		String tSequenceNo = tOldLCInsuredDB.getSequenceNo();
		if(tSequenceNo==null)
		{
			tSequenceNo = "";
		}
		//String tSQL = "select nvl(min(sequenceno),'') from lcinsured where contno='"++"'";
		if((tSequenceNo.equals("1")
				||tSequenceNo.equals("-1")
				||tSequenceNo.equals(this.mInsuredSeq))
				&&this.mOldLCPolSchema==null
				//为了兼容传统保单,允许RiskSequence是null
				&&(tMainLCPolSchema.getRiskSequence()==null||tMainLCPolSchema.getRiskSequence().equals("1")
						||tMainLCPolSchema.getRiskSequence().equals("-1"))
				)
		{
			this.mOldLCPolSchema = new LCPolSchema();
			this.mOldLCPolSchema.setSchema(tMainLCPolSchema);
		}
		// 取出责任数据
		LCDutySet tLCDutySet = tVData.getObjectByObjectName("LCDutySet", 0) == null ? (new LCDutySet())
				: (LCDutySet) tVData.getObjectByObjectName("LCDutySet", 0);
		logger.debug("i:" + i + ":tLCDutySet" + tLCDutySet.size()
				+ ":this.mLCDutySet" + this.mLCDutySet.size());
		this.mLCDutySet.add(tLCDutySet);

		// 取出领取项目数据
		LCGetSet tLCGetSet = tVData.getObjectByObjectName("LCGetSet", 0) == null ? (new LCGetSet())
				: (LCGetSet) tVData.getObjectByObjectName("LCGetSet", 0);
		this.mLCGetSet.add(tLCGetSet);
		// 帐户相关数据!注意,下面的可能没有数据 ~~
		// 以下的数据在外面处理!?
		LCPremToAccSet tLCPremToAccSet = tVData.getObjectByObjectName(
				"LCPremToAccSet", 0) == null ? (new LCPremToAccSet())
				: (LCPremToAccSet) tVData.getObjectByObjectName(
						"LCPremToAccSet", 0);
		if (tLCPremToAccSet.size() > 0) {
			this.mLCPremToAccSet.add(tLCPremToAccSet);
		}
		LCGetToAccSet tLCGetToAccSet = tVData.getObjectByObjectName(
				"LCGetToAccSet", 0) == null ? (new LCGetToAccSet())
				: (LCGetToAccSet) tVData.getObjectByObjectName("LCGetToAccSet",
						0);
		if (tLCGetToAccSet.size() > 0) {
			this.mLCGetToAccSet.add(tLCGetToAccSet);
		}
		//帐户总表数据
		LCInsureAccSet tLCInsureAccSet = tVData.getObjectByObjectName(
				"LCInsureAccSet", 0) == null ? (new LCInsureAccSet())
				: (LCInsureAccSet) tVData.getObjectByObjectName("LCInsureAccSet",
						0);
		if (tLCInsureAccSet.size() > 0) {
			//先做帐户数据表的合同换号工作
			//tMainLCPolSchema
			for(int m=1;m<=tLCInsureAccSet.size();m++)
			{
				tLCInsureAccSet.get(m).setContNo(tMainLCPolSchema.getContNo());
			}
			this.mLCInsureAccSet.add(tLCInsureAccSet);
		}
		// 取出折扣数据
		LCDiscountSet tLCDiscountSet = tVData.getObjectByObjectName("LCDiscountSet", 0) == null ? (new LCDiscountSet())
				: (LCDiscountSet) tVData.getObjectByObjectName("LCDiscountSet", 0);
		// if(tLCPremSet.size())
		this.mLCDiscountSet.add(tLCDiscountSet);
		// 取出应收子表数据
		LJSPayPersonSet tLJSPayPersonSet = tVData.getObjectByObjectName("LJSPayPersonSet", 0) == null ? (new LJSPayPersonSet())
				: (LJSPayPersonSet) tVData.getObjectByObjectName("LJSPayPersonSet", 0);
		//获取实收个人交费表数据
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		//tongmeng 2008-09-26 modify
		//实收个人交费表在这里处理~
		//tLJAPayPersonSet = this.prepareLJAPayPerson(tLCPremSet, tMainLCPolSchema,tLCPolSet);
		tLJAPayPersonSet = this.prepareLJAPayPersonNew(tLJSPayPersonSet,tMainLCPolSchema,tLCPolSet);
			
	    if(tLJAPayPersonSet.size()<=0)
	    {
	    	CError.buildErr(this,"实收个人交费表没有数据!","01","028");
	    	return false;
	    }
	    //放到缓存里,但是实收个人交费表是在LCContSignBL中准备提交的,
	    //在本程序中不做处理!只是用来汇总实收总表数据使用!
		//营改增   add  zhangyingfeng  2016-07-06 
		//税价分离 计算器
		TaxCalculator.calBySchemaSet(tLJAPayPersonSet);
		//end zhangyingfeng  2016-07-06 
	    this.mLJAPayPersonSet.add(tLJAPayPersonSet);
		/*
		 * 稍后做
		 */ 
		  // 受益人数据,承保时受益人挂靠在主险数据上!此处只需要做拆分即可 
	     LCBnfSet tLCBnfSet = new LCBnfSet(); 
	     //tMainLCPolSchema 
		 LCBnfDB tLCBnfDB = new LCBnfDB();
		 if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
		 {
			 tLCBnfDB.setContNo(tMainLCPolSchema.getContNo());
		 }
		 else
		 {
			 //tLCBnfDB.setContNo(tMainLCPolSchema.getPrtNo());
			 tLCBnfDB.setContNo(mContNo);
		 }
		 tLCBnfDB.setPolNo(tMainLCPolSchema.getProposalNo()); 
		 tLCBnfSet = tLCBnfDB.query(); 
		 for (int n = 1; n <= tLCBnfSet.size(); n++) {
		 	tLCBnfSet.get(n).setContNo(tMainLCPolSchema.getContNo());
		 	tLCBnfSet.get(n).setPolNo(tMainLCPolSchema.getPolNo()); 
		 	tLCBnfSet.get(n).setModifyDate(this.mCurrentDate);
		 	tLCBnfSet.get(n).setModifyTime(this.mCurrentTime);
		 } 
		 if(tLCBnfSet.size() > 0) 
		 { 
			 this.mLCBnfSet.add(tLCBnfSet); 
		 } 
		 // 特约数据
		 
//		 LCSpecSet tLCSpecSet = new LCSpecSet(); 
//		 LCSpecDB tLCSpecDB = new  LCSpecDB(); 
//		 tLCSpecDB.setContNo(tMainLCPolSchema.getPrtNo());
//		 tLCSpecDB.setPolNo(tMainLCPolSchema.getProposalNo()); 
//		 tLCSpecSet = tLCSpecDB.query();
//		  
//		 for (int n = 1; n <= tLCSpecSet.size(); n++) {
//		 tLCSpecSet.get(i).setContNo(tMainLCPolSchema.getContNo());
//		 tLCSpecSet.get(i).setPolNo(tMainLCPolSchema.getPolNo()); } 
//		 if(tLCSpecSet.size() > 0)
//		 { 
//			 this.mLCSpecSet.add(tLCSpecSet); 
//		 }
		 //经讨论,MS版Lis6.0启用合同特约表
		 //即所有特约记录在LCCSpec中.保单拆分时复制到每个小合同中.
		 LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		 LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		 tLCCSpecDB.setProposalContNo(tMainLCPolSchema.getPrtNo());
		 //tongmeng 2008-11-03 add
		 //特约分为两个层级，合同层和被保人层，
		 //合同层特约直接复制，被保人层需要过滤一下。
		 //
		 tLCCSpecSet = tLCCSpecDB.query();
		 if(tLCCSpecSet.size()>0)
		 {
			 //复制特约信息
			 for(int m=1;m<=tLCCSpecSet.size();m++)
			 {
				 LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
				 tempLCCSpecSchema.setSchema(tLCCSpecSet.get(m));
				 if(tempLCCSpecSchema.getCustomerNo()==null
				 //合同层特约
					||(tempLCCSpecSchema.getCustomerNo()!=null
					&&tempLCCSpecSchema.getCustomerNo().equals(tMainLCPolSchema.getInsuredNo()))
				 //被保人层特约	
				 )
				 {
					 tempLCCSpecSchema.setContNo(tMainLCPolSchema.getContNo());
					 tempLCCSpecSchema.setModifyDate(this.mCurrentDate);
					 tempLCCSpecSchema.setModifyTime(this.mCurrentTime);
					 this.mLCCSpecSet.add(tempLCCSpecSchema);
				 }
			 }
		 }
		  
		 // 告知数据....需要复制
		 //告知数据在外包导入到核心业务系统时，只存储录入为“是”的数据，“否”的数据不保存。
		 // 告知数据直接复制合同下的就可以了。
		 LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		 LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
		 if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
		 {
			 tLCCustomerImpartDB.setContNo(tMainLCPolSchema.getContNo());			 
		 }
		 else
		 {
			 //tLCCustomerImpartDB.setContNo(tMainLCPolSchema.getPrtNo());
			 tLCCustomerImpartDB.setContNo(mContNo);//个险合同号和印刷号一样 卡单不一样
		 }		 
		 tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		 for(int n=1;n<=tLCCustomerImpartSet.size();n++)
		 {
			 LCCustomerImpartSchema tempLCCustomerImpartSchema = new LCCustomerImpartSchema();
			 tempLCCustomerImpartSchema.setSchema(tLCCustomerImpartSet.get(n));
			 //更新数据
			 tempLCCustomerImpartSchema.setContNo(tMainLCPolSchema.getContNo());
			 tempLCCustomerImpartSchema.setModifyDate(this.mCurrentDate);
			 tempLCCustomerImpartSchema.setModifyTime(this.mCurrentTime);
			 this.mLCCustomerImpartSet.add(tempLCCustomerImpartSchema);
		 }
		 //LCCustomerImpartDetail告知明细表不做记录，先不处理。
		 //LCCustomerImpartParams告知参数表，做复制
		 LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		 LCCustomerImpartParamsDB tLCCustomerImpartParamsDB = new LCCustomerImpartParamsDB();
		 if("08".equals(mSellType) || "12".equals(mSellType))//12-电子商务
		 {
			 tLCCustomerImpartParamsDB.setContNo(tMainLCPolSchema.getContNo());
		 }
		 else
		 {
//			 tLCCustomerImpartParamsDB.setContNo(tMainLCPolSchema.getPrtNo());
			 tLCCustomerImpartParamsDB.setContNo(mContNo);//个险合同号和印刷号一样 卡单不一样
		 }		 
		 tLCCustomerImpartParamsSet = tLCCustomerImpartParamsDB.query();
		 for(int n=1;n<=tLCCustomerImpartParamsSet.size();n++)
		 {
			 LCCustomerImpartParamsSchema tempLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
			 tempLCCustomerImpartParamsSchema.setSchema(tLCCustomerImpartParamsSet.get(n));
			 //更新数据
			 tempLCCustomerImpartParamsSchema.setContNo(tMainLCPolSchema.getContNo());
			 tempLCCustomerImpartParamsSchema.setModifyDate(this.mCurrentDate);
			 tempLCCustomerImpartParamsSchema.setModifyTime(this.mCurrentTime);
			 this.mLCCustomerImpartParamsSet.add(tempLCCustomerImpartParamsSchema);
		 }
			
		return true;
	}

	/**
	 * 生成实收交费表数据~
	 * @param tLCPremSet
	 * @param tLCPolSchema
	 * @return
	 */
	private LJAPayPersonSet prepareLJAPayPerson(LCPremSet tLCPremSet,LCPolSchema tLCPolSchema ,LCPolSet tLCPolSet) {
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		// 实交个人表
		int m = tLCPremSet.size();
		if (m > 0) {
			for (int i = 1; i <= m; i++) {
				LCPremSchema tLCPremSchema = tLCPremSet.get(i);
				LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
				tLJAPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
				tLJAPayPersonSchema.setPayCount(1);
				tLJAPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJAPayPersonSchema.setContNo(tLCPolSchema.getContNo());
				tLJAPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJAPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJAPayPersonSchema.setPayNo(this.mPaySerialNo);
				tLJAPayPersonSchema.setPayAimClass("1");
				tLJAPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
				tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema
						.getPayPlanCode());
				tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
				tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
				tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
				tLJAPayPersonSchema.setPayDate(maxPayDate);
				tLJAPayPersonSchema.setPayType("ZC");
				tLJAPayPersonSchema.setEnterAccDate(maxEnterAccDate);
				tLJAPayPersonSchema.setConfDate(mCurrentDate);
				tLJAPayPersonSchema.setLastPayToDate("1899-12-31");
				tLJAPayPersonSchema.setCurPayToDate(tLCPremSchema
						.getPaytoDate());
				// tLJAPayPersonSchema.setSerialNo(mSerialNo);
				tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
				tLJAPayPersonSchema.setMakeDate(this.mCurrentDate);
				tLJAPayPersonSchema.setMakeTime(this.mCurrentTime);
				tLJAPayPersonSchema.setModifyDate(mCurrentDate);
				tLJAPayPersonSchema.setModifyTime(mCurrentTime);

				tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJAPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJAPayPersonSchema.setAgentType(tLCPolSchema.getAgentType());
				String tRiskCode = "";
				String tCurrency = "";
				for(int j=1;j<=tLCPolSet.size();j++)
				{
					if(tLCPremSchema.getPolNo().equals(tLCPolSet.get(j).getPolNo()))
					{
						tRiskCode = tLCPolSet.get(j).getRiskCode();
						tCurrency = tLCPolSet.get(j).getCurrency();
						if(tLCPolSet.get(j).getPolNo().equals(tLCPolSet.get(j).getMainPolNo())){
							MRCurrency = tCurrency;//主险币种信息
						}
						break;
					}
				}
				
				tLJAPayPersonSchema.setRiskCode(tRiskCode);
				//tongmeng 2010-11-30 modify
				tLJAPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());//币种信息
				tLJAPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJAPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());

				tLJAPayPersonSchema.setMainPolYear(1); // 新契约保单年度为“1”

				tLJAPayPersonSet.add(tLJAPayPersonSchema);
			}
			return tLJAPayPersonSet;
		}
		return null;
	}
	
	/**
	 * 生成实收交费表数据~
	 * @param tLCPremSet
	 * @param tLCPolSchema
	 * @return
	 */
	private LJAPayPersonSet prepareLJAPayPersonNew(LJSPayPersonSet tLJSPayPersonSet,LCPolSchema tLCPolSchema,LCPolSet tLCPolSet) {
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		Reflections tReflections=new Reflections();
		
		// 实交个人表
		int m = tLJSPayPersonSet.size();
		if (m > 0) {
			for (int i = 1; i <= m; i++) {
				LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
				LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
				tReflections.transFields(tLJAPayPersonSchema, tLJSPayPersonSchema);
				
				tLJAPayPersonSchema.setPayNo(this.mPaySerialNo);
				tLJAPayPersonSchema.setPayDate(maxPayDate);
				tLJAPayPersonSchema.setEnterAccDate(maxEnterAccDate);
				tLJAPayPersonSchema.setConfDate(mCurrentDate);
				// tLJAPayPersonSchema.setSerialNo(mSerialNo);
				tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
				tLJAPayPersonSchema.setMakeDate(this.mCurrentDate);
				tLJAPayPersonSchema.setMakeTime(this.mCurrentTime);
				tLJAPayPersonSchema.setModifyDate(mCurrentDate);
				tLJAPayPersonSchema.setModifyTime(mCurrentTime);
				String tCurrency = "";
				for(int j=1;j<=tLCPolSet.size();j++)
				{
					if(tLJSPayPersonSchema.getPolNo().equals(tLCPolSet.get(j).getPolNo()))
					{
						tCurrency = tLCPolSet.get(j).getCurrency();
						if(tLCPolSet.get(j).getPolNo().equals(tLCPolSet.get(j).getMainPolNo())){
							MRCurrency = tCurrency;//主险币种信息
						}
						break;
					}
				}

				tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJAPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJAPayPersonSchema.setAgentType(tLCPolSchema.getAgentType());			
				
				tLJAPayPersonSet.add(tLJAPayPersonSchema);
			}

			return tLJAPayPersonSet;
		}
		return null;
	}
	
	/**
	 * 财务核销和余额(或溢交退费)处理部分 
	 * @return
	 */
	private boolean dealFinance() {
		
		
		//查询暂交费表
		//合同下各险种是否都足额缴费,是在LCContSignBL中统一做校验O(∩_∩)O~
		//tongmeng 2008-09-26 add
		//从暂收费分类表入手开始循环比较好~
		//tongmeng 2008-10-29 add
		//支持多笔缴费财务处理
		
		//tongmeng 2008-11-13 add
		//余额挂靠在险种的leavingmoney上
		//先统计合同下每个险种的保费数，按照险种编码汇总。
		//tongmeng 2010-11-08 modify
		//增加多币种处理
		
		HashMap tRiskMap = new HashMap();
		//tongmeng 2010-11-30 modify
		//投资账户分币种缴费处理
		//修改为从lcprem进行汇总
		
		String tLimit = PubFun.getNoLimit(this.mOldLCPolSchema.getManageCom());
		
		for(int n=1;n<=this.mLJAPayPersonSet.size();n++)
		{
			double tCurrPrem = mLJAPayPersonSet.get(n).getSumActuPayMoney();
			double tSumPrem = 0;
			String tCurrRiskCode = mLJAPayPersonSet.get(n).getRiskCode();
			String tCurrency =  mLJAPayPersonSet.get(n).getCurrency();			
			
			if(tCurrRiskCode==null||tCurrRiskCode.equals(""))
			{
				CError.buildErr(this, "获取险种编码出错!","01","059");
				return false;
			}
			
			if(tRiskMap.containsKey(tCurrRiskCode+tCurrency))
			{
				tSumPrem = Double.parseDouble((String)tRiskMap.get(tCurrRiskCode+tCurrency));
			}
			//tongmeng 2009-12-23 modify
			//格式化再加和
			tSumPrem = PubFun.round(tSumPrem,2) + PubFun.round(tCurrPrem,2);

			tRiskMap.put(tCurrRiskCode+tCurrency, String.valueOf(PubFun.round(tSumPrem,2)));
		}
		
		
		
		//
		
		
		LJTempFeeClassSet finalLJTempFeeClassSet = new LJTempFeeClassSet();
		LJTempFeeSet finalLJTempFeeSet = new LJTempFeeSet();
		
		finalLJTempFeeClassSet = this.queryTempFeeClass(mOldLCContSchema);
		if(finalLJTempFeeClassSet==null||finalLJTempFeeClassSet.size()<=0)
		{
			CError.buildErr(this,"该合同:"+mOldLCContSchema.getPrtNo()+"没有暂交费!","01","029");
			return false;
		}
		//循环暂收费分类表
		//查询每个暂收分类表对应的LJTempFee数据.
		//校验是否到账
		//校验每笔暂收分类和暂收费数据缴费累计和是否相等
		double sumTempFeeClass = 0;//记录
		
		String tTempFeeOtherNo = "";
		String tTempFeeOtherNoType = "";
		if(this.mFamilyType.equals("0"))
		{
			if(this.mOldLCPolSchema!=null)
			tTempFeeOtherNo = this.mOldLCPolSchema.getContNo();
			tTempFeeOtherNoType = "0";//个人保单合同号
		}
		else
		{
			tTempFeeOtherNo = this.mFamilyContNo;
			tTempFeeOtherNoType = "3";//家庭单合同号
		}
		Hashtable tTempFeeNoHashtable = new Hashtable();
		for(int i=1;i<=finalLJTempFeeClassSet.size();i++)
		{
			LJTempFeeClassSchema tempLJTempFeeClassSchema = new LJTempFeeClassSchema();
			tempLJTempFeeClassSchema = finalLJTempFeeClassSet.get(i);
			if(tempLJTempFeeClassSchema.getEnterAccDate()==null
					||tempLJTempFeeClassSchema.getEnterAccDate().equals("3000-01-01"))
			{
				CError.buildErr(this,"该合同:"+mOldLCContSchema.getPrtNo()+
						"暂收费分类存在未到账保费,不能签单!","01","030");
				return false;
			}
			//查询暂收费分类表
			LJTempFeeSet tempLJTempFeeSet = new LJTempFeeSet();
			//如果该笔暂収据号没有处理，才查询暂収费表。否则只处理
			if(!tTempFeeNoHashtable.containsKey(tempLJTempFeeClassSchema.getTempFeeNo()))
			{
				double tempSumTempClass = 0;
				double tempSumTempFee = 0;
				double tempClassCom = 0;
				//sumTempFeeClass = 0;
				//保存已经处理过的TempFeeNo
				tTempFeeNoHashtable.put(tempLJTempFeeClassSchema.getTempFeeNo(), tempLJTempFeeClassSchema);
				//累计同一个缴费号下的交费金额
				for(int n=1;n<=finalLJTempFeeClassSet.size();n++)
				{
					if(finalLJTempFeeClassSet.get(n).getTempFeeNo().equals(tempLJTempFeeClassSchema.getTempFeeNo()))
					{
						tempSumTempClass = finalLJTempFeeClassSet.get(n).getPayMoney();
						//tongmeng 2009-12-23 modify
						//格式化后再加和
						sumTempFeeClass = PubFun.round(sumTempFeeClass,2) + PubFun.round(tempSumTempClass,2);
						tempClassCom = PubFun.round(tempClassCom,2)+PubFun.round(tempSumTempClass,2);
					}
				}
				tempLJTempFeeSet =  this.queryTempFeeRisk(tempLJTempFeeClassSchema);
				if(tempLJTempFeeSet==null||tempLJTempFeeSet.size()<=0)
				{
					CError.buildErr(this,"暂收费号:"+tempLJTempFeeClassSchema.getTempFeeNo()+
							"下没有暂收费记录,请查询原因!","01","031");
					return false;
				}
				for(int n=1;n<=tempLJTempFeeSet.size();n++)
				{
					//tongmeng 2009-12-23 modify
					//格式化后再加和
					tempSumTempFee = PubFun.round(tempSumTempFee,2) + PubFun.round(tempLJTempFeeSet.get(n).getPayMoney(),2);
					if(tempLJTempFeeSet.get(n).getEnterAccDate()==null
							||tempLJTempFeeSet.get(n).getEnterAccDate().equals("3000-01-01"))
					{
						CError.buildErr(this,"该合同:"+mOldLCContSchema.getPrtNo()+"存在未到账保费,不能签单!","01","032");
						return false;
					}
					if(tempLJTempFeeSet.get(n).getTempFeeType()==null
							||!tempLJTempFeeSet.get(n).getTempFeeType().equals("1"))
					{
						CError.buildErr(this,"该合同:"+mOldLCContSchema.getPrtNo()+"暂收费类型错误,不能签单!","01","033");
						return false;
					}
					//核销财务数据,处理暂收费表
					tempLJTempFeeSet.get(n).setConfDate(this.mCurrentDate);
					tempLJTempFeeSet.get(n).setConfFlag("1");
					tempLJTempFeeSet.get(n).setOtherNo(tTempFeeOtherNo);
					tempLJTempFeeSet.get(n).setOtherNoType(tTempFeeOtherNoType);
					tempLJTempFeeSet.get(n).setModifyDate(this.mCurrentDate);
					tempLJTempFeeSet.get(n).setModifyTime(this.mCurrentTime);
				}
				//格式化之后再做比较
				tempClassCom = PubFun.round(tempClassCom,2);
				tempSumTempFee = PubFun.round(tempSumTempFee,2);
				if(tempClassCom!=tempSumTempFee)
				{
					CError.buildErr(this,"暂收费号:"+tempLJTempFeeClassSchema.getTempFeeNo()+
							"缴费金额和暂收分类表不等,请查询原因!","01","034");
					return false;
				}
				//把暂收费数据加到最终集合中
				finalLJTempFeeSet.add(tempLJTempFeeSet);
			}
			//核销财务数据
			//处理暂收费分类表,直接在对象里修改,不用做特殊处理.
			tempLJTempFeeClassSchema.setConfDate(this.mCurrentDate);
			tempLJTempFeeClassSchema.setConfFlag("1");
			tempLJTempFeeClassSchema.setModifyDate(this.mCurrentDate);
			tempLJTempFeeClassSchema.setModifyTime(this.mCurrentTime);
			//tongmeng 2009-05-04 modify
		    //Ljtempfeeclass otherno 设置为payno
			tempLJTempFeeClassSchema.setOtherNo(this.mPaySerialNo);
			tempLJTempFeeClassSchema.setOtherNoType("1");
		}
		
		//将数据保存到全局变量中
		this.mLJTempFeeClassSet.add(finalLJTempFeeClassSet);
		this.mLJTempFeeSet.add(finalLJTempFeeSet);
		
		//开始处理余额和溢交退费
		logger.debug("开始处理余额~或者溢交退费~~");
		//获取合同的保费
		//tongmeng 2009-01-06 modify
		//修改查询逻辑
		//double sumContPrem = this.mOldLCContSchema.getPrem();
		double sumContPrem = 0;
		//tongmeng 2009-05-23 modify
		//查询条件增加核保通过的单子
		//tongmeng 2010-11-08 modify
		//增加多币种处理
		
		String tSQL_Prem = "select (select (case when sum(paymoney) is null then 0 else sum(paymoney) end) from ljtempfeeclass where tempfeeno in ( " 
						 + " select tempfeeno from LJTempFee where OtherNo='"+ "?prtno?" + "' " 
                         + " and ConfFlag='0' and ConfDate is null and tempfeetype='1' ) and currency=A.y)-A.x,A.y,A.x from  " 
			             + " (select sum(a.sumactupaymoney) x,a.currency y from ljspayperson a left join lcpol b on a.polno=b.polno "
			             + " and b.prtno='"+ "?prtno?" +"'"
			             + " where b.uwflag in ('3','4','9','d') group by a.currency ) A ";
		SQLwithBindVariables sqlbv22= new SQLwithBindVariables();
		sqlbv22.sql(tSQL_Prem);
		sqlbv22.put("prtno", this.mOldLCContSchema.getPrtNo());
		ExeSQL tExeSQL_Prem = new ExeSQL();
		//String tContPrem = tExeSQL_Prem.getOneValue(tSQL_Prem);
		
		SSRS tSSRS_ContPrem = new SSRS();
		
		tSSRS_ContPrem = tExeSQL_Prem.execSQL(sqlbv22);
//		if(tContPrem==null)
//		{
//			CError.buildErr(this,"查询合同累计保费出错!");
//			return false;
//		}
//		else
//		{
//			sumContPrem = Double.parseDouble(tContPrem);
//		}
		//判断是否有余额
//		sumTempFeeClass = PubFun.round(sumTempFeeClass, 2);
//		sumContPrem = PubFun.round(sumContPrem, 2);
//		double diff = sumTempFeeClass - sumContPrem;
//		logger.debug("PrtNo:"+this.mOldLCContSchema.getPrtNo()+":dif:"+diff+":sumTempFeeClass："+sumTempFeeClass+"：sumContPrem："+sumContPrem);
		Hashtable tFICustomerTable = new Hashtable();
		//判断是否有余额
		this.mTempfeeSerialNo = PubFun1.CreateMaxNo("SERIALNO",
				tLimit);
		for(int i=1;i<=tSSRS_ContPrem.getMaxRow();i++)
		{
		
			double diff = PubFun.round(Double.parseDouble(tSSRS_ContPrem
					.GetText(i, 1)), 2);
			String tempCurrency = tSSRS_ContPrem.GetText(i, 2);
			
			
			double shouldMoney = PubFun.round(Double.parseDouble(tSSRS_ContPrem
					.GetText(i, 3)), 2);
			
			//tFICustomerTable.put(tempCurrency, shouldMoney);
			
			if (diff < 0) {
				CError.buildErr(this, "保费不足,不能签单!", "01", "035");
				return false;
			} else if (diff > 0) {
				// 有余额.判断合同余额处理方式,是溢交退费还是当余额,做续期保费
				String tOutPayFlag = this.mOldLCContSchema.getOutPayFlag();
				// tongmeng 2009-08-31 modify
				// 溢交退费也要在ljapayperson生成YET的记录.
				// 1-溢交退费
				// 2-抵交续期保费
				if (tOutPayFlag == null || tOutPayFlag.equals("")) {
					tOutPayFlag = "1";

				}
				if (tOutPayFlag == null || tOutPayFlag.equals("1")) {
					// 退费
					// 为空默认为退费
					if (!dealYJTF(diff, tempCurrency)) {
						return false;
					}
					tFICustomerTable.put("1:"+tempCurrency, shouldMoney);
				}

				// tongmeng 2010-11-08 modify
				// 溢交退费不生产YET的记录.
				if (tOutPayFlag.equals("2")) {
					// tongmeng 2008-11-13 modify
					// 余额挂靠在具体的险种上。
					// LCContSignBL中对财务的校验是一定要每个险种足额到账。
					// 所以此处只需要判断溢交保费挂靠的险种即可。
					// 如果是家庭单，并且家庭单下存在多个被保人购买同样的险种，那么，余额挂在第一个被保人的险种下。
					// 查询ljtempfee表，按照险种编码汇总交费金额
					// 注：如果交费的险种合同下没有的话，余额挂靠在第一被保人的第一主险下。
					String tSQL = "  select riskcode,currency,sum(paymoney) from LJTempFee where "
							+ " otherno='"
							+ "?otherno?"
							+ "' "
							+ " and ConfFlag='0' and ConfDate is null "
							+ " and tempfeetype='1' "
							+ " and currency='"+"?currency?"+"' "
							+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01') "
							+ " group by riskcode,currency ";
					SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
					sqlbv23.sql(tSQL);
					sqlbv23.put("otherno", this.mOldLCPolSchema.getPrtNo());
					sqlbv23.put("currency", tempCurrency);
					SSRS tSSRS = new SSRS();
					ExeSQL tExeSQL = new ExeSQL();
					tSSRS = tExeSQL.execSQL(sqlbv23);
					for (int m = 1; m <= tSSRS.getMaxRow(); m++) {
						// 逐一险种判断是否溢交
						String tCurrRiskCode = tSSRS.GetText(m, 1);
						String tCurrCurrency = tSSRS.GetText(m, 2);
						double tCurrPrem = Double.parseDouble(tSSRS.GetText(m,
								3));
						// 格式化
						tCurrPrem = PubFun.round(tCurrPrem, 2);
						// 取出险种的保费表中的数据
						double tRiskPrem = 0;

						if (!tRiskMap
								.containsKey(tCurrRiskCode + tCurrCurrency)) {
							tRiskPrem = tCurrPrem;
							// tongmeng 2010-11-08 modify
							// 多币种溢交退费处理
							// 如果缴费的险种和币种在合同下不存在,需要转做溢交退费处理
							if (!dealYJTF(tRiskPrem, tCurrCurrency)) {
								return false;
							}
							tFICustomerTable.put("1:"+tempCurrency, shouldMoney);
							// 溢交退费不生产YET的记录,财务记账凭证的提取逻辑需要修改
							// if(!this.perpareYETLJAPayPerson(mOldLCPolSchema,
							// tRiskPrem,"1"))
							// {
							// CError.buildErr(this,"准备余额数据失败!","01","042");
							// return false;
							// }

							// ----------------------------------------------

							// 缴费险种合同下不存在，直接挂在第一被保人的第一主险下,并且多条合成一条记录
							// if(!this.perpareYETLJAPayPerson(mOldLCPolSchema,
							// tRiskPrem,tOutPayFlag))
							// {
							// CError.buildErr(this,"准备余额数据失败!","01","042");
							// return false;
							// }
						} else {
							// 缴费险种在签单的险种范围内。		
							// 取出险种保费
							tRiskPrem = Double.parseDouble((String) tRiskMap
									.get(tCurrRiskCode + tCurrCurrency));
							// 格式化
							tRiskPrem = PubFun.round(tRiskPrem, 2);
							double leaving = tCurrPrem - tRiskPrem;
							if (leaving > 0) {
								// 获取需要挂靠的险种Schema
								LCPolSchema tDealPolSchema = new LCPolSchema();
								for (int k = 1; k <= this.mLCPolSet.size(); k++) {
									if (this.mLCPolSet.get(k).getRiskCode()
											.equals(tCurrRiskCode)) {
										tDealPolSchema.setSchema(this.mLCPolSet
												.get(k));
										break;
									}
								}
								if (!this.perpareYETLJAPayPerson(
										tDealPolSchema, leaving, tOutPayFlag)) {
									CError.buildErr(this, "准备余额数据失败!", "01",
											"042");
									return false;
								}
								tFICustomerTable.put("2:"+tempCurrency, shouldMoney);
							}
						}
					}

				}
			}
		}
		//需要补充一段
		//如果存在暂交费中有当前合同不存在的币种数据,那么,直接做溢交退费处理.
		String tSQL_YJ = "select currency,sum(paymoney) from ljtempfee where otherno='"+"?prtno?"+"' "
			           + " and currency not in " 
			           + " (select a.currency y from ljspayperson a,lcpol b where a.polno=b.polno " 
			           + " and b.prtno='"+"?prtno?"+"' "
			           + " and b.uwflag in ('3','4','9','d')  ) "
			           + " group by currency ";
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql(tSQL_YJ);
		sqlbv24.put("prtno", mOldLCContSchema.getPrtNo());
	SSRS tSSRS = new SSRS();
	tSSRS = tExeSQL_Prem.execSQL(sqlbv24);
	for(int i=1;i<=tSSRS.getMaxRow();i++)
	{
		String tCurrency = tSSRS.GetText(i, 1);
		double tShouldPaymoney = 0;
		
		double tYJMoney = PubFun.round(Double.parseDouble(tSSRS.GetText(i, 2)),2);
		//直接做退费
		if (!dealYJTF(tYJMoney, tCurrency)) {
			return false;
		}
		tFICustomerTable.put("1:"+tCurrency, tShouldPaymoney);
	}
		
		
		
		//汇总ljtempfeeclass
		if(this.mInsertLJTempFeeSet.size()>=0)
		{
			String tNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
			//分币种累计缴费金额,生成ljtempfeeclass
			Hashtable tSumTempTable = new Hashtable();
			for(int t=1;t<=this.mInsertLJTempFeeSet.size();t++)
			{
				LJTempFeeSchema tempLJTempFeeSchema = new LJTempFeeSchema();
				tempLJTempFeeSchema = mInsertLJTempFeeSet.get(t);
				//补充设置tempfeeno
				tempLJTempFeeSchema.setTempFeeNo(tNo);
				
				String tCurrency = tempLJTempFeeSchema.getCurrency();
				double tempMoney = 0;
				if(tSumTempTable.containsKey(tCurrency))
				{
					tempMoney = (Double)tSumTempTable.get(tCurrency) + tempLJTempFeeSchema.getPayMoney();
				}
				else
				{
					tempMoney = tempLJTempFeeSchema.getPayMoney();
					
				}
				
				tSumTempTable.put(tCurrency, tempMoney);
			}
			
			Enumeration eKey=tSumTempTable.keys(); 
			while(eKey.hasMoreElements()) 
			{ 
				String key=(String)eKey.nextElement();
				double tValue = (Double)tSumTempTable.get(key);
				
				LJTempFeeClassSchema tempLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tempLJTempFeeClassSchema.setTempFeeNo(tNo);
				tempLJTempFeeClassSchema.setPayMode("5");//内部转账
				tempLJTempFeeClassSchema.setChequeNo("000000");
				tempLJTempFeeClassSchema.setPayMoney(tValue);
				tempLJTempFeeClassSchema.setPayDate(this.mCurrentDate);
				tempLJTempFeeClassSchema.setConfDate(this.mCurrentDate);
				tempLJTempFeeClassSchema.setEnterAccDate(this.mCurrentDate);
				tempLJTempFeeClassSchema.setSerialNo(this.mTempfeeSerialNo);
				tempLJTempFeeClassSchema.setManageCom(this.mOldLCPolSchema.getManageCom());
				tempLJTempFeeClassSchema.setPolicyCom(this.mOldLCPolSchema.getManageCom());
				tempLJTempFeeClassSchema.setConfMakeDate(this.mCurrentDate);
				tempLJTempFeeClassSchema.setConfMakeTime(this.mCurrentTime);
				tempLJTempFeeClassSchema.setConfFlag("1");
				tempLJTempFeeClassSchema.setOtherNo(this.mOldLCPolSchema.getContNo());
				tempLJTempFeeClassSchema.setOtherNoType("1");
				tempLJTempFeeClassSchema.setTempFeeNoType("1");
				tempLJTempFeeClassSchema.setOperState("0");
				tempLJTempFeeClassSchema.setCurrency(key);
				tempLJTempFeeClassSchema.setOperator(this.mOperator);
				tempLJTempFeeClassSchema.setMakeDate(this.mCurrentDate);
				tempLJTempFeeClassSchema.setMakeTime(this.mCurrentTime);
				tempLJTempFeeClassSchema.setModifyDate(this.mCurrentDate);
				tempLJTempFeeClassSchema.setModifyTime(this.mCurrentTime);
				
				
				
				
				/////////////////////////////////////////////////////////////
				// 客户账户的处理方案:如果是余额,产生付费数据,并且设置为已核销 ,
				// 模拟产生收费数据,设置为已核销. 该笔收费不区分险种.
				// 客户账户挂余额.
				// .........待添加客户账户处理
				if (!dealYJTFForYE(tValue, key,tempLJTempFeeClassSchema)) {
					return false;
				}
				////////////////////////////////////////////////////////////
				
				this.mInsertLJTempFeeClassSet.add(tempLJTempFeeClassSchema);
				
				
			}
			
			
			/***********************************/
			logger.debug("开始客户账户处理...");
			// 添加客户账户处理
			VData nInputData = new VData();		
			nInputData.add(this.mInsertLJTempFeeSet);
			nInputData.add(this.mInsertLJTempFeeClassSet);
			nInputData.add(this.mOldLCContSchema);
			
			nInputData.add(this.mGlobalInput);
			nInputData.add(tFICustomerTable);
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ContNo", this.mLCContSet.get(1).getContNo());
			nInputData.add(tTransferData);
			FICustomerMain tFICustomerMain = new FICustomerMain();
			// 调用客户账户收费接口，传入财务标志FI
			if (tFICustomerMain.submitData(nInputData, "NB"))
			{
				// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
				this.mMMap.add(tFICustomerMain.getMMap());
			}
			else
			{
				mErrors.copyAllErrors(tFICustomerMain.mErrors);
				return false;
			}
			logger.debug("结束客户账户处理...");
			
			/***********************************/
		}
		
		
		return true;
	}

	/**
	 * @param diff
	 * @param tempCurrency
	 */
	private boolean dealYJTF(double diff, String tempCurrency) {
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		//退费到相应主险的合同上
		String tContNo = "";
		String tOtherNoType = "";
		if(this.mFamilyType.equals("0"))
		{
			tContNo = this.mOldLCPolSchema.getContNo();
			tOtherNoType = "0";
		}
		else
		{
			tContNo = this.mFamilyContNo;
			tOtherNoType = "2";
		}
		tLJAGetSchema = this.getLJAGet(diff,tContNo,"6");
		if (tLJAGetSchema == null) {
			CError.buildErr(this, "准备溢交退费数据失败！","01","036");
			return false;
		}
		tLJAGetSchema.setCurrency(tempCurrency);
		this.mLJAGetSet.add(tLJAGetSchema);
		
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		tLJAGetOtherSchema = this.getLJAGetOther(tLJAGetSchema, tContNo, tOtherNoType);
		if(tLJAGetOtherSchema==null)
		{
			CError.buildErr(this,"准备溢交退费数据失败!","01","036");
			return false;
		}
		tLJAGetOtherSchema.setCurrency(tempCurrency);
		//营改增 add zhangyingfeng 2016-07-07
		//价税分离 计算器   
		TaxCalculator.calBySchema(tLJAGetOtherSchema);
		//补填应付总表净额 税额 税率
		tLJAGetSchema.setNetAmount(tLJAGetOtherSchema.getNetAmount());
		tLJAGetSchema.setTaxAmount(tLJAGetOtherSchema.getTaxAmount());
		tLJAGetSchema.setTax(tLJAGetOtherSchema.getTax());
		//end zhangyingfeng  2016-07-07
		this.mLJAGetOtherSet.add(tLJAGetOtherSchema);
		
		//需要判断是否需要生成通知书
		boolean tNoticeFlag = true;
		if(this.mLOPRTManagerSet.size()<=0)
		{
			tNoticeFlag = true;
		}
		else
		{
			for(int n=1;n<=this.mLOPRTManagerSet.size();n++)
			{
				LOPRTManagerSchema tempLOPRTManagerSchema = new LOPRTManagerSchema();
				tempLOPRTManagerSchema = this.mLOPRTManagerSet.get(n);
				//PrintManagerBL.ONT_INDPOL
				//PrintManagerBL.CODE_REFUND
				if(tempLOPRTManagerSchema.getOtherNo().equals(tContNo)
						&&tempLOPRTManagerSchema.getOtherNoType().equals(PrintManagerBL.ONT_INDPOL)
						&&tempLOPRTManagerSchema.getCode().equals(PrintManagerBL.CODE_REFUND))
				{
					tNoticeFlag = false;
					break;
				}
			}
		}
		if(tNoticeFlag)
		{
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = this.getLOPrtManager(tLJAGetSchema, tContNo);
			if (tLOPRTManagerSchema == null) {
				CError.buildErr(this, "准备溢交退费打印管理表失败！","01","037");
				return false;
			}
			this.mLOPRTManagerSet.add(tLOPRTManagerSchema);
		}
		
		return true;
	}
	
	
	//----------------------------------------------------------------------------
	//余额处理
	private boolean dealYJTFForYE(double diff, String tempCurrency,LJTempFeeClassSchema tTempFeeClassSchema) {
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		//退费到相应主险的合同上
		String tContNo = "";
		String tOtherNoType = "";
		if(this.mFamilyType.equals("0"))
		{
			tContNo = this.mOldLCPolSchema.getContNo();
			tOtherNoType = "0";
		}
		else
		{
			tContNo = this.mFamilyContNo;
			tOtherNoType = "2";
		}
		tLJAGetSchema = this.getLJAGet(diff,tContNo,"6");
		if (tLJAGetSchema == null) {
			CError.buildErr(this, "准备溢交退费数据失败！","01","036");
			return false;
		}
		tLJAGetSchema.setGetNoticeNo(tTempFeeClassSchema.getTempFeeNo());
		tLJAGetSchema.setSerialNo(tTempFeeClassSchema.getSerialNo());
		tTempFeeClassSchema.setChequeNo(tLJAGetSchema.getGetNoticeNo());
		//设置为已领取
		tLJAGetSchema.setEnterAccDate(this.mCurrentDate);
		tLJAGetSchema.setConfDate(this.mCurrentDate);
		tLJAGetSchema.setCurrency(tempCurrency);
		this.mLJAGetSet.add(tLJAGetSchema);
		
		//财务实付总表
		LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
		tLJFIGetSchema.setActuGetNo(tLJAGetSchema.getActuGetNo());
		tLJFIGetSchema.setPayMode(tLJAGetSchema.getPayMode());
		tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
		tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
		tLJFIGetSchema.setGetMoney(tLJAGetSchema.getSumGetMoney());
		tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
		tLJFIGetSchema.setEnterAccDate(tLJAGetSchema.getEnterAccDate());
		tLJFIGetSchema.setConfDate(tLJAGetSchema.getConfDate());
		tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
		tLJFIGetSchema.setManageCom(tLJAGetSchema.getManageCom());
		tLJFIGetSchema.setAPPntName(this.mOldLCContSchema.getAppntName());
		tLJFIGetSchema.setAgentCom(tLJAGetSchema.getAgentCom());
		tLJFIGetSchema.setAgentType(tLJAGetSchema.getAgentType());
		tLJFIGetSchema.setAgentGroup(tLJAGetSchema.getAgentGroup());
		tLJFIGetSchema.setAgentCode(tLJAGetSchema.getAgentCode());
		tLJFIGetSchema.setSerialNo(tLJAGetSchema.getSerialNo());
		tLJFIGetSchema.setDrawer(tLJAGetSchema.getDrawer());
		tLJFIGetSchema.setDrawerID(tLJAGetSchema.getDrawerID());
		tLJFIGetSchema.setOperator(tLJAGetSchema.getOperator());
		tLJFIGetSchema.setMakeTime(tLJAGetSchema.getMakeTime());
		tLJFIGetSchema.setMakeDate(tLJAGetSchema.getMakeDate());
		tLJFIGetSchema.setState("0");
		tLJFIGetSchema.setModifyDate(tLJAGetSchema.getModifyDate());
		tLJFIGetSchema.setModifyTime(tLJAGetSchema.getModifyTime());
		tLJFIGetSchema.setConfMakeTime(this.mCurrentTime);
		tLJFIGetSchema.setChequeNo(tLJAGetSchema.getActuGetNo());
		tLJFIGetSchema.setCurrency(tLJAGetSchema.getCurrency());	//TODO
		this.mInsertLJFIGetSet.add(tLJFIGetSchema);
		
		
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		tLJAGetOtherSchema = this.getLJAGetOther(tLJAGetSchema, tContNo, tOtherNoType);
		if(tLJAGetOtherSchema==null)
		{
			CError.buildErr(this,"准备溢交退费数据失败!","01","036");
			return false;
		}
		tLJAGetOtherSchema.setEnterAccDate(this.mCurrentDate);
		tLJAGetOtherSchema.setConfDate(this.mCurrentDate);
		tLJAGetOtherSchema.setCurrency(tempCurrency);
		//营改增 add zhangyingfeng 2016-07-07
		//价税分离 计算器 --抵交续期需要下期收缴保费时计算税，新契约暂不算税，
//		TaxCalculator.calBySchema(tLJAGetOtherSchema);
		//end zhangyingfeng  2016-07-07
		this.mLJAGetOtherSet.add(tLJAGetOtherSchema);
		

		
		return true;
	}
	//----------------------------------------------------------------------------
	/**
	 * 准备余额数据
	 * @param tLCPolSchema
	 * @param money
	 * @return
	 */
	private boolean perpareYETLJAPayPerson(LCPolSchema tLCPolSchema,double money,String tOutPayFlag)
	{
		try {
			//客户账户,余额不记YET的数据了
			/*
			LJAPayPersonSchema tYETLJAPayPersonSchema = null;
			for(int k=1;k<=this.mLJAPayPersonYETSet.size();k++)
			{
				LJAPayPersonSchema tempLJAPayPersonSchema = mLJAPayPersonYETSet.get(k);
				if(tempLJAPayPersonSchema.getPolNo().equals(tLCPolSchema.getPolNo()))
				{
					//如果有的话，直接指向引用。
					tYETLJAPayPersonSchema = new LJAPayPersonSchema();
					tYETLJAPayPersonSchema = tempLJAPayPersonSchema;
					break;
				}
			}
			if (tYETLJAPayPersonSchema == null) {
				tYETLJAPayPersonSchema = new LJAPayPersonSchema();
				//第一次生成的数据
				tYETLJAPayPersonSchema.setPolNo(tLCPolSchema
						.getPolNo());
				tYETLJAPayPersonSchema.setPayCount(1);
				tYETLJAPayPersonSchema
						.setGrpPolNo(tLCPolSchema
								.getGrpPolNo());
				tYETLJAPayPersonSchema.setContNo(tLCPolSchema
						.getContNo());
				tYETLJAPayPersonSchema.setGrpContNo(tLCPolSchema
						.getGrpContNo());
				tYETLJAPayPersonSchema.setAppntNo(tLCPolSchema
						.getAppntNo());
				tYETLJAPayPersonSchema.setPayNo(this.mPaySerialNo);
				tYETLJAPayPersonSchema.setPayAimClass("1");
				tYETLJAPayPersonSchema.setDutyCode("0000000000");
				tYETLJAPayPersonSchema.setPayPlanCode("00000000");
				tYETLJAPayPersonSchema.setSumDuePayMoney(0);
				tYETLJAPayPersonSchema.setSumActuPayMoney(money);
				tYETLJAPayPersonSchema.setPayIntv(tLCPolSchema
						.getPayIntv());
				tYETLJAPayPersonSchema.setPayDate(maxPayDate);
				tYETLJAPayPersonSchema.setPayType("YET");
				tYETLJAPayPersonSchema
						.setEnterAccDate(maxEnterAccDate);
				tYETLJAPayPersonSchema
						.setConfDate(this.mCurrentDate);
				tYETLJAPayPersonSchema
						.setLastPayToDate("1899-12-31");
				tYETLJAPayPersonSchema
						.setCurPayToDate(tLCPolSchema
								.getPaytoDate());
				// tLJAPayPersonSchema.setSerialNo(mSerialNo);
				tYETLJAPayPersonSchema.setOperator(mOperator);
				tYETLJAPayPersonSchema
						.setMakeDate(this.mCurrentDate);
				tYETLJAPayPersonSchema
						.setMakeTime(this.mCurrentTime);
				tYETLJAPayPersonSchema
						.setModifyDate(this.mCurrentDate);
				tYETLJAPayPersonSchema
						.setModifyTime(this.mCurrentTime);

				tYETLJAPayPersonSchema.setManageCom(tLCPolSchema
						.getManageCom());
				tYETLJAPayPersonSchema.setAgentCom(tLCPolSchema
						.getAgentCom());
				tYETLJAPayPersonSchema.setAgentType(tLCPolSchema
						.getAgentType());
				tYETLJAPayPersonSchema.setRiskCode(tLCPolSchema
						.getRiskCode());
				tYETLJAPayPersonSchema.setAgentCode(tLCPolSchema
						.getAgentCode());
				tYETLJAPayPersonSchema
						.setAgentGroup(tLCPolSchema
								.getAgentGroup());

				tYETLJAPayPersonSchema.setMainPolYear(1); // 新契约保单年度为“1”
				//tongmeng 2010-11-08 modify
				//增加多币种处理
				tYETLJAPayPersonSchema.setCurrency(tLCPolSchema.getCurrency());
				
				this.mLJAPayPersonYETSet
						.add(tYETLJAPayPersonSchema);
				
				
			}
			else
			{
				tYETLJAPayPersonSchema
						.setSumActuPayMoney(tYETLJAPayPersonSchema
								.getSumActuPayMoney()
								+ money);
			}
			*/
				// 找到当前需要处理的险种数据,把leavingmoney处理下~
			//tongmeng 2009-08-31 modify
			//溢交退费也要生成YET记录,但是leavingmoney记为0
			if(tOutPayFlag.equals("2"))
			{
				for (int n = 1; n <= this.mLCPolSet.size(); n++) {
					if (this.mLCPolSet.get(n).getPolNo().equals(
							tLCPolSchema.getPolNo())) {
						this.mLCPolSet.get(n).setLeavingMoney(mLCPolSet.get(n).getLeavingMoney()+money);
						break;
					}
				}
				
				//补充生成暂收费费的数据.
				//为客户账户,直接生成暂收数据,并且设置成已核销状态
				//ljtempfee tempfeetype设置为3 预交保费,othernotype 0 个单合同号  
				
				
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				//tLJTempFeeSchema.setTempFeeNo(tNo); //在外面汇总时再设置
				tLJTempFeeSchema.setTempFeeType("11");
				tLJTempFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJTempFeeSchema.setPayIntv(tLCPolSchema.getPayIntv());
				tLJTempFeeSchema.setOtherNo(tLCPolSchema.getContNo());
				tLJTempFeeSchema.setOtherNoType("0");//个单合同号
				tLJTempFeeSchema.setPayMoney(money);
				tLJTempFeeSchema.setPayDate(this.mCurrentDate);
				tLJTempFeeSchema.setEnterAccDate(this.mCurrentDate);
				tLJTempFeeSchema.setConfDate(this.mCurrentDate);
				tLJTempFeeSchema.setConfMakeDate(this.mCurrentDate);
				tLJTempFeeSchema.setConfMakeTime(this.mCurrentTime);
				tLJTempFeeSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJTempFeeSchema.setPolicyCom(tLCPolSchema.getManageCom());
				tLJTempFeeSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJTempFeeSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJTempFeeSchema.setConfFlag("1");//即时核销
				tLJTempFeeSchema.setSerialNo(this.mTempfeeSerialNo);
				tLJTempFeeSchema.setOperator(this.mOperator);
				tLJTempFeeSchema.setMakeDate(this.mCurrentDate);
				tLJTempFeeSchema.setMakeTime(this.mCurrentTime);
				tLJTempFeeSchema.setModifyDate(this.mCurrentDate);
				tLJTempFeeSchema.setModifyTime(this.mCurrentTime);
				tLJTempFeeSchema.setPayEndYear("0");
				tLJTempFeeSchema.setTempFeeNoType("1");//默认设置为 1,现金
				tLJTempFeeSchema.setPayYears(tLCPolSchema.getPayYears());
				tLJTempFeeSchema.setCurrency(tLCPolSchema.getCurrency());
				
				
				this.mInsertLJTempFeeSet.add(tLJTempFeeSchema);
				
				
				//暂收费分类表在外面再做汇总
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	/**
	 * 生成tLJAGetOther
	 * @param tLJAGetSchema
	 * @param tLCContSchema
	 * @param tNewContNo
	 * @return
	 */
	private LJAGetOtherSchema getLJAGetOther(LJAGetSchema tLJAGetSchema,
			 String tNewContNo,String tOtherNoType) {
		LJAGetOtherSchema tLJAGetOtherShema = new LJAGetOtherSchema();
		tLJAGetOtherShema.setActuGetNo(tLJAGetSchema.getActuGetNo());
		tLJAGetOtherShema.setOtherNo(tNewContNo);
		tLJAGetOtherShema.setOtherNoType(tOtherNoType); // 其它类型退费(溢交保费退费)
		tLJAGetOtherShema.setPayMode(this.mOldLCContSchema.getNewPayMode());
		tLJAGetOtherShema.setGetMoney(tLJAGetSchema.getSumGetMoney());
		tLJAGetOtherShema.setGetDate(mCurrentDate);
		tLJAGetOtherShema.setFeeOperationType("YJ"); // 溢交退费
		tLJAGetOtherShema.setFeeFinaType("YJ"); // 溢交退费
		tLJAGetOtherShema.setManageCom(mOldLCContSchema.getManageCom());
		tLJAGetOtherShema.setAgentCom(mOldLCContSchema.getAgentCom());
		tLJAGetOtherShema.setAgentType(mOldLCContSchema.getAgentType());
		tLJAGetOtherShema.setAPPntName(mOldLCContSchema.getAppntName());
		tLJAGetOtherShema.setAgentGroup(mOldLCContSchema.getAgentGroup());
		tLJAGetOtherShema.setAgentCode(mOldLCContSchema.getAgentCode());
		tLJAGetOtherShema.setSerialNo(tLJAGetSchema.getSerialNo());
		tLJAGetOtherShema.setOperator(mGlobalInput.Operator);
		tLJAGetOtherShema.setMakeTime(mCurrentTime);
		tLJAGetOtherShema.setMakeDate(mCurrentDate);
		tLJAGetOtherShema.setModifyDate(mCurrentDate);
		tLJAGetOtherShema.setModifyTime(mCurrentTime);

		return tLJAGetOtherShema;
	}
	/**
	 * 生成LJAGet
	 * @param tLCContSchema
	 * @param sumPayMoney
	 * @param tNewContNo
	 * @return
	 */
	private LJAGetSchema getLJAGet( double diff, String tNewContNo,String tOtherNoType) {
		String tLimit = PubFun.getNoLimit(this.mOldLCContSchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		String getNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生即付通知书号

		// 实付总表
		tLJAGetSchema.setActuGetNo(getNo);
		tLJAGetSchema.setShouldDate(mCurrentDate);
		tLJAGetSchema.setStartGetDate(mCurrentDate);

		// 如果组合交费方式，则按现金付费 续期可能会增加回写保单的银行信息（防止业务人员录错银行信息）
		// int tPayModeFlag = 0;
		for (int i = 1; i <= this.mLJTempFeeClassSet.size(); i++) {
			if (mLJTempFeeClassSet.get(i).getPayMode().equals("2")
					|| mLJTempFeeClassSet.get(i).getPayMode().equals("3")) {
				// tPayModeFlag++;
				tLJAGetSchema
						.setPayMode(mLJTempFeeClassSet.get(i).getPayMode());
				tLJAGetSchema.setBankCode(mLJTempFeeClassSet.get(i)
						.getBankCode());
				tLJAGetSchema.setBankAccNo(mLJTempFeeClassSet.get(i)
						.getChequeNo());
				break;
			}

			// if (tPayModeFlag == tLJTempFeeClassSet.size())
			else if ("4".equals(mLJTempFeeClassSet.get(i).getPayMode())
					|| "6".equals(mLJTempFeeClassSet.get(i).getPayMode())) {
				tLJAGetSchema.setPayMode("4"); // 银行代付
				tLJAGetSchema.setBankAccNo(mLJTempFeeClassSet.get(1)
						.getBankAccNo());
				tLJAGetSchema.setBankCode(mLJTempFeeClassSet.get(1)
						.getBankCode());
				tLJAGetSchema
						.setAccName(mLJTempFeeClassSet.get(1).getAccName());
			} else {
				tLJAGetSchema.setPayMode("1"); // 现金付费
				tLJAGetSchema.setBankAccNo("");
				tLJAGetSchema.setBankCode("");
				tLJAGetSchema.setAccName("");
			}
		}
		
		tLJAGetSchema.setOtherNo(tNewContNo);
		tLJAGetSchema.setOtherNoType(tOtherNoType); // 其它类型退费(溢交保费退费)
		tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
		tLJAGetSchema.setAppntNo(this.mOldLCContSchema.getAppntNo());
		tLJAGetSchema.setSumGetMoney(diff);
		tLJAGetSchema.setSaleChnl(this.mOldLCContSchema.getSaleChnl());
		tLJAGetSchema.setSerialNo(serNo);
		tLJAGetSchema.setOperator(mOperator);
		tLJAGetSchema.setManageCom(mOldLCContSchema.getManageCom());
		tLJAGetSchema.setAgentCode(mOldLCContSchema.getAgentCode());
		tLJAGetSchema.setAgentCom(mOldLCContSchema.getAgentCom());
		tLJAGetSchema.setAgentGroup(mOldLCContSchema.getAgentGroup());
		tLJAGetSchema.setShouldDate(mCurrentDate);
		tLJAGetSchema.setStartGetDate(mCurrentTime);
		tLJAGetSchema.setMakeDate(mCurrentDate);
		tLJAGetSchema.setMakeTime(mCurrentTime);
		tLJAGetSchema.setModifyDate(mCurrentDate);
		tLJAGetSchema.setModifyTime(mCurrentTime);

		return tLJAGetSchema;
	}
	/**
	 * 查询暂收交费
	 * @param tmpLCContchema
	 * @return
	 */
	private LJTempFeeSet queryTempFeeRisk(LJTempFeeClassSchema tempLJTempFeeClassSchema) {
		LJTempFeeSet tLJTempFeeSet;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		//目前只查询待核销的暂收数据
		String querytempfeeSQL = "select * from LJTempFee where tempfeeno='"
				+ "?tempfeeno?"
				+ "' "
				+ " and ConfFlag='0' and ConfDate is null"
		//tongmeng 2008-09-26 modify
	    //一定要合同下的所有缴费都到账才能核销,此处只取该合同下的所有缴费,在外面做判断是否全到账
		//		+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')"
				;
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(querytempfeeSQL);
		sqlbv25.put("tempfeeno", tempLJTempFeeClassSchema.getTempFeeNo());
		tLJTempFeeSet = (tLJTempFeeDB.executeQuery(sqlbv25));
		if (tLJTempFeeDB.mErrors.needDealError()) {
			return null;
		}
		return tLJTempFeeSet;
	}
	
	/**
	 * 查询暂收分类表
	 * @param tmpLCContSchema
	 * @return
	 */
	private LJTempFeeClassSet queryTempFeeClass(LCContSchema tmpLCContSchema)
	{
		LJTempFeeClassSet tLJTempFeeClassSet;
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		String tSQL_LJTempFeeClass = "select * from ljtempfeeclass where tempfeeno in "
								   + " ( "
								   + " select tempfeeno from LJTempFee where OtherNo='"+ "?OtherNo?"+ "' "
									+ " and ConfFlag='0' and ConfDate is null"
                             	   + " and tempfeetype='1' ) ";
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(tSQL_LJTempFeeClass);
		sqlbv26.put("OtherNo", tmpLCContSchema.getPrtNo());
		tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv26);
		if (tLJTempFeeClassDB.mErrors.needDealError()) {
			return null;
		}
		return tLJTempFeeClassSet;
	}
	/**
	 * 生成溢交退费通知书
	 * @param tLJAGetSchema
	 * @param tLCContSchema
	 * @param tNewContNo
	 * @return
	 */
	private LOPRTManagerSchema getLOPrtManager(LJAGetSchema tLJAGetSchema,
			String tNewContNo) {
		String tLimit = "";
		String prtSeqNo = "";

		// 3-准备打印数据,生成印刷流水号
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLimit = PubFun.getNoLimit(this.mOldLCContSchema.getManageCom());
		prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); //
		tLOPRTManagerSchema.setOtherNo(tNewContNo);
		tLOPRTManagerSchema.setMakeDate(mCurrentDate);
		tLOPRTManagerSchema.setMakeTime(mCurrentTime);
		tLOPRTManagerSchema.setManageCom(mOldLCContSchema.getManageCom());
		tLOPRTManagerSchema.setAgentCode(mOldLCContSchema.getAgentCode());
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_REFUND);
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mOperator);
		tLOPRTManagerSchema.setPrtType("0");
		tLOPRTManagerSchema.setStateFlag("0");
		// tongmeng 2007-12-29 add
		// 溢交保费通知书使用standbyflag6记录给付通知书号
		tLOPRTManagerSchema.setStandbyFlag6(tLJAGetSchema.getGetNoticeNo());
		// tLOPRTManagerSchema.setStandbyFlag1(tLCContSchema.getMainPolNo());
		tLOPRTManagerSchema.setStandbyFlag1("");
		PrintManagerBL tPrintManagerBL = new PrintManagerBL();
		VData tempVData = new VData();
		tempVData.add(tLOPRTManagerSchema);
		tempVData.add(mGlobalInput);
		if (tPrintManagerBL.submitData(tempVData, "REQ")) { // 打印数据处理
			tempVData = tPrintManagerBL.getResult();
			tLOPRTManagerSchema = (LOPRTManagerSchema) tempVData
					.getObjectByObjectName("LOPRTManagerSchema", 0);
		}

		return tLOPRTManagerSchema;
	}

	/**
	 * 入口函数
	 * 
	 * @param tInputData
	 * @param tFreeRiskFlag
	 * @return
	 */
	public boolean submitData(VData tInputData,TransferData spTransferData,String tFreeRiskFlag) {
		try {
			
			this.mOperator = (String)spTransferData.getValueByName("Operator");
			this.mFirstPayDate = (Date)spTransferData.getValueByName("FirstPayDate");
			this.maxEnterAccDate=(Date)spTransferData.getValueByName("EnterAccDate");
			this.maxPayDate =(Date)spTransferData.getValueByName("MaxPayDate");
			this.mPaySerialNo=(String)spTransferData.getValueByName("PAYNO");
			this.mSellType=(String)spTransferData.getValueByName("SellType");
			this.mGlobalInput= (GlobalInput)spTransferData.getValueByName("GlobalInput");
			this.mMark= (String)spTransferData.getValueByName("Mark");
			mFreeRiskFlag = tFreeRiskFlag;
			
			mOrganizeDate = (String)spTransferData.getValueByName("OrganizeDate");
			mOrganizeTime = (String)spTransferData.getValueByName("OrganizeTime");
			
			//tongmeng 2009-03-31 add
			//首先获得合同下的被保人的最小的序列号.
			String tContNo = (String)spTransferData.getValueByName("ContNo");
			String tSQL_Seq = "select (case when min(SEQUENCENO) is null then '' else min(SEQUENCENO) end) from lcinsured where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(tSQL_Seq);
			sqlbv27.put("contno", tContNo);
			ExeSQL tExeSQL = new ExeSQL();
			this.mInsuredSeq = tExeSQL.getOneValue(sqlbv27);
			if(this.mInsuredSeq==null)
			{
				this.mInsuredSeq = "-1";
			}
			// 判断是否有豁免
			int tMaxSize = tInputData.size();
			VData tFreeRiskData = new VData();
			if (!tFreeRiskFlag.equals("0")) {
				// 豁免险,首先将tMaxSize-1...
				tMaxSize = tMaxSize - Integer.parseInt(tFreeRiskFlag);
				
				// 获取豁免险险种数据
				for (int i = tMaxSize; i < tInputData.size(); i++) {
					VData tempVData = tInputData.getObjectByObjectName("VData",
							i) == null ? (new VData()) : (VData) tInputData
							.getObjectByObjectName("VData", i);
					tFreeRiskData.add(tempVData);
				}
				// 获取除豁免险之外的所有保单数据
				for (int i = 0; i < tMaxSize; i++) {
					VData tempVData = tInputData.getObjectByObjectName("VData",
							i) == null ? (new VData()) : (VData) tInputData
							.getObjectByObjectName("VData", i);
					LCPolSet tLCPolSet = new LCPolSet();
					tLCPolSet = (LCPolSet) tempVData.getObjectByObjectName(
							"LCPolSet", 0);
					mAllPolSet.add(tLCPolSet);
				}
			}
			logger.debug("mAllPolSet.size:" + mAllPolSet.size());
			mMaxSize = tMaxSize;
			for (int i = 0; i < tMaxSize; i++) {
				// 获取一套险种信息
				VData tempVData = tInputData.getObjectByObjectName("VData", i) == null ? (new VData())
						: (VData) tInputData.getObjectByObjectName("VData", i);

				// 豁免险种拆分
				// 将豁免险种的保费均分到每个主险上
				if (!this.mFreeRiskFlag.equals("0")) {
					if (!this.splitFreeRisk(tempVData, tFreeRiskData, i)) {
						logger.debug("拆分豁免险种失败");
						return false;
					}
				}
				// 正常险种处理,主要是投保人,被保人等数据的处理
				if (!this.splitNormRisk(tempVData, i)) {
					return false;
				}
				//处理帐户
				//稍后处理
				//帐户处理在外面做.不用处理~~~
				/*
				if(!this.dealInsuAcc(tempVData, i))
				{
					return false;
				}
				*/
				//处理实收保费相关数据
				//在外面做,不用处理~
				/*
				if(!this.dealJAPay(tempVData, i))
				{
					return false;
				}
				*/
				// 合同复制
				if (!this.copyLCCont(tempVData, i)) {
					return false;
				}
			}
			//开始处理合同整体部分
			//生成家庭单合同号
			mFamilyType = "0";
			mFamilyContNo = "00000000000000000000";
			if(this.mOldLCContSchema!=null)
			{
				String tContPrtNo = "";
				tContPrtNo = mOldLCContSchema.getPrtNo();
				if(tContPrtNo.substring(0,4).equals("8651"))
				{
					//假设8651开头的是家庭单的合同号...
					mFamilyType = "1";
				}
				else if(tContPrtNo.substring(0,4).equals("8661"))
				{
					//假设8661开头的是多主险的合同号...
					mFamilyType = "2";
				}
				
			}
			if(!mFamilyType.equals("0"))
			{
				String tLimit = PubFun.getNoLimit(mOldLCContSchema.getManageCom());
				mFamilyContNo = PubFun1.CreateMaxNo("CONTNO", tLimit);
			}
			
			//进行财务核销和余额的处理
			if(!dealFinance())
			{
				return false;
			}
			//生成总的实收数据
			if(!this.dealJAPay())
			{
				return false;
			}
				
			//
			//整体清除原有数据
			if(!this.prepareDelSQL())
			{
				return false;
			}
			
			//tongmeng 2009-12-23 add
			//在提交数据前增加校验
			//tongmeng 2010-12-02 modify
			//注释掉本次校验
			logger.debug("before beforeSaveCheck");
			/*if(!this.beforeSaveCheck())
			{
				return false;
			}*/
			logger.debug("end beforeSaveCheck");
			// 返回结果数据准备
			if (!this.prepareOutData()) {
				return false;
			}
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,e.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * tongmeng 2009-12-23 add
	 * 为了防止因为豁免范围描述错误，导致折算保费错误。在保存前特增加校验。
	 * @return
	 */
	private boolean beforeSaveCheck()
	{
		//tongmeng 2010-11-08 modify
		
		boolean resultFlag = true;
		/*校验规则:
		 * 1 险种保费+ YET金额 = 暂收费数据
		*/
		double sumRiskPrem = 0;
		double sumYET = 0;
		double sumGET = 0;
		double sumTempfee = 0;
		//累计险种保费
		for(int i=1;i<=this.mLCPolSet.size();i++)
		{
			double temp = 0;
			temp = this.mLCPolSet.get(i).getPrem();
			sumRiskPrem = PubFun.round(sumRiskPrem,2)+PubFun.round(temp,2);
		}
		//累计YET金额
		for(int i=1;i<=this.mLJAPayPersonYETSet.size();i++)
		{
			//tongmeng 2009-12-23 modify
			//先格式化后再加和
			sumYET = PubFun.round(sumYET,2) + PubFun.round(mLJAPayPersonYETSet.get(i).getSumActuPayMoney(),2);
		}
		//tongmeng 2010-11-08 modify
		//累计付费数据
		for(int i=1;i<=this.mLJAGetSet.size();i++)
		{
			sumGET = PubFun.round(sumGET,2) + PubFun.round(mLJAGetSet.get(i).getSumGetMoney(),2);
		}
		//累计暂收费数据
		for(int i=1;i<=this.mLJTempFeeSet.size();i++)
		{
			double temp = 0;
			temp = this.mLJTempFeeSet.get(i).getPayMoney();
			//tongmeng 2010-12-02 modify
			//
			double newSumPrem = 0;
			/*
			 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
			 * 如果返回值小于0，则有错误数据
			 * orgitype 传入的币种
			 * destype 需要转换的币种
			 * transdate 转换日期
			 * amnt 转换金额
			 */
			String tCurrency = mLJTempFeeSet.get(i).getCurrency();
			
			//newSumPrem = this.mLDExch.toOtherCur(tCurrency, this.mlgetCurrency(), tTransDate, mLCPremBLSet.get(m).getPrem());


			
			sumTempfee = PubFun.round(sumTempfee,2) + PubFun.round(temp, 2);
		}
		sumRiskPrem = PubFun.round(sumRiskPrem,2);
		sumYET = PubFun.round(sumYET,2);
		sumTempfee = PubFun.round(sumTempfee,2);
		
		logger.debug("PrtNo:"+mOldLCContSchema.getPrtNo()+":sumRiskPrem:"+sumRiskPrem+":sumYET:"+sumYET+":sumTempfee:"+sumTempfee);
		if(PubFun.round(sumRiskPrem+sumYET+sumGET,2)!=PubFun.round(sumTempfee,2))
		{
			logger.debug("PrtNo:"+mOldLCContSchema.getPrtNo()+"保费/余额/暂收费校验出错,请检查原因!");
			CError.buildErr(this,"PrtNo:"+mOldLCContSchema.getPrtNo()+"保费/余额/暂收费校验出错,请检查原因!","01","999");
			return false;
		}
		
		return resultFlag;
	}
}
