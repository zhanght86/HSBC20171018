/*
 * @(#)ProposalSignBL.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bq.EdorSignBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolOtherDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.CManageFee;
import com.sinosoft.lis.pubfun.DealAccount;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCDiscountSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
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
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保签单业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WUJS
 * @version 6.0
 */
public class ProposalSignBL {
private static Logger logger = Logger.getLogger(ProposalSignBL.class);
	/**
	 * 存放结果
	 */
	public VData mVResult = new VData();

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();

	/**
	 * 往后面传输数据的容器
	 */
	private VData mInputData;

	TransferData mTransferData = new TransferData();

	private SimpleDateFormat tSDF = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 数据操作字符串
	 */
	private String mOperate;
	private FDate fDate = new FDate();

	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	/**
	 * 全局数据
	 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/**
	 * 个人投保单表
	 */
	private LCPolSet mLCPolSet = new LCPolSet();

	/**
	 * 实收总表
	 */
	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	/**
	 * 实收总表--存储用
	 */
	private LJAPaySet mSaveLJAPaySet = new LJAPaySet();

	/**
	 * 旧主险保单号
	 */
	private String mOldMainPolNo = "";

	private String mark;

	/**
	 * 系统变量表
	 */
	LDSysVarSchema mLDSysVarSchema = null;
	/**
	 * 变量拷贝
	 */
	Reflections rf = new Reflections();
	/**
	 * 险种承保描述表
	 */
	LMRiskAppSchema mLMRiskAppSchema = null;
	private String mRiskFlag = "";
	private String mNeedReadBankFlag = "";
	private String mSameBatch = "0"; // 主附险是否属于同一批 "0"--非同一批 "1"--同一批
	private String mMainPolNo = null; // 同一批处理的附险对应的主险的新保单号
	private String mSerialNo = "";
	private String mPolType = "1"; // 1-个人单 2-集体下的个人单 3-合同下的个人单
	private String mBack = ""; // 签单追溯标记 "0"--不追溯 "1"--追溯到签单次日 "2"--追溯到交费到帐最大次日
	// "3"--追溯到交费到帐最小次日
	private Date mValiDate = null;
	private double mLeft = 0;
	private Date mFirstPayDate = null;
	private double sumMoney = 0; // 总的交费额
	private double sumPrem = 0;
	private Date maxPayDate = null;
	private Date maxEnterAccDate = null;
	private boolean mInsuredAppAgeChangeFlag = false;
	private boolean alreadyChangeCValiDate = false; // 是否已经更改过生效日期
	private boolean mBQFlag = false; // 保全标记
	private boolean mNotCreatInsuaccFlag = false; // 不生成帐户的标记
	private String DELETE = "DELETE";
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";

	private String mNewContNo = null; // 新的合同号

	private TransferData mBQData;
	MMap rMap = new MMap();

	private VData mBufferedPolData = new VData();
	//tongmeng add
	private VData mPolDetailData = new VData();
	
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LCPremToAccSet mLCPremToAccSet = new LCPremToAccSet();
	private LCGetToAccSet mLCGetToAccSet = new LCGetToAccSet();	
	private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
	private LCDiscountSet mLCDiscountSet = new LCDiscountSet();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private MMap mSplitMap = new MMap();
	//end tongmeng add
	//tongmeng 2010-12-02 modify
	LDExch mLDExch = new LDExch();
	private String mNewMainPolNo;

	public VData getResult() {
		return this.mInputData;
	}

	public VData getBufferedPolData() {
		return this.mBufferedPolData;
	}
	public VData getPolDetailData() {
		return this.mPolDetailData;
	}


	public static void main(String[] args) {

		/**
		 * 插入方法测试 ProposalSignBL proposalSignBL = new ProposalSignBL(); MMap
		 * dest = new MMap(); VData srcData = new VData();
		 * 
		 * for ( int i=0;i<4;i++){ LCUWMasterSet tLCUWMasterSet = new
		 * LCUWMasterSet(); LCUWMasterSchema tLCUWMasterSchema = new
		 * LCUWMasterSchema();
		 * 
		 * tLCUWMasterSchema.setPolNo("tNewPolNo<"+i+">");
		 * tLCUWMasterSchema.setGrpPolNo("tLCPolSchema.getGrpPolNo("+i+")");
		 * tLCUWMasterSchema.setContNo("tLCPolSchema.getContNo("+i+")");
		 * tLCUWMasterSet.add(tLCUWMasterSchema); srcData.add(i
		 * ,tLCUWMasterSet); }
		 * 
		 * 
		 * int changeIndex = 3; proposalSignBL.addIntoMapByAction(dest, srcData,
		 * changeIndex); logger.debug("here");
		 */
		/**
		 * * 拷贝schemaset测试 ProposalSignBL ps = new ProposalSignBL();
		 * LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		 * tLJAPaySchema.setPayNo("payno"); tLJAPaySchema.setIncomeNo("polno");
		 * tLJAPaySchema.setIncomeType("2");
		 * tLJAPaySchema.setAppntNo("appntno"); LJAPaySet tSet = new
		 * LJAPaySet(); tSet.add( tLJAPaySchema ); LJAPaySet tmpSet =
		 * (LJAPaySet) ps.copySchemaSet((SchemaSet)tSet );
		 */

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "001";

		// 生成一个序列号
		String tLimit = PubFun.getNoLimit("86");
		String mSerialNo = PubFun1.CreateMaxNo("SerialNo", tLimit);

		// 获取实收总表数据
		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setIncomeNo("86330020040220000029");
		tLJAPayDB.setIncomeType("1");

		LJAPaySet tLJAPaySet = tLJAPayDB.query();
		LJAPaySchema tLJAPaySchema = tLJAPaySet.get(1);

		// 准备调用签单需要的数据
		VData vPolPub = new VData();
		vPolPub.add(tG);
		vPolPub.add(mSerialNo);
		vPolPub.add(tLJAPaySchema);

		TransferData tBQData = new TransferData();
		tBQData.setNameAndValue("EdorType", "GANAME");

		ProposalSignBL tProposalSignBL = new ProposalSignBL();
		// MMap tVData = tProposalSignBL.dealOnePol("00000000000000000000",
		// "00000000000000000000", "86110020040210000240", vPolPub, tBQData);
		tProposalSignBL.mGlobalInput.Operator = "001";
		tProposalSignBL.mGlobalInput.ManageCom = "86";
		// VData tVData = tProposalSignBL.dealOnePol("",
		// "", "86110020040210000243", null, null);
		VData tVData = null;
		if (tVData == null) {

			return;
		}
		MMap tmpMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
		LCPolSchema tLCPolSchema = (LCPolSchema) tVData.getObjectByObjectName(
				"LCPolSchema", 0);
		tmpMap.put(tLCPolSchema, "INSERT");
		VData tInputData = new VData();
		tInputData.add(tmpMap);

		PubSubmit pubSubmit = new PubSubmit();
		pubSubmit.submitData(tInputData, null);

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (this.getInputData() == false) {
			return false;
		}

		// 数据操作业务处理
		if (this.dealData() == false) {
			return false;
		}

		return true;

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		String tPolNo = "";
		String tContNo = "";
		String tGrpPolNo = "";

		TransferData tTransferData = new TransferData();

		mInputData = new VData();
		// 处理签单数据--假设传入的数据都是主险
		int polCount = mLCPolSet.size();
		for (int i = 1; i <= polCount; i++) {
			LCPolSchema tLCPolSchema = mLCPolSet.get(i);
			if (tLCPolSchema == null) {
				continue;
			}
			tPolNo = tLCPolSchema.getPolNo();
			tGrpPolNo = tLCPolSchema.getGrpPolNo();
//			日志监控，性能监控。 保单***的***险种签单开始；			
			PubFun.logPerformance (mGlobalInput,tLCPolSchema.getContNo(),"保单"+tLCPolSchema.getContNo()+"的"+tLCPolSchema.getRiskCode()+"险种签单开始","0");	
			// 下面dealOnePol函数被集体签单处调用
			VData mapPol = this.dealOnePolData(tLCPolSchema);
//			日志监控，性能监控。 保单***的***险种签单结束；
			PubFun.logPerformance (mGlobalInput,tLCPolSchema.getContNo(),"保单"+tLCPolSchema.getContNo()+"的"+tLCPolSchema.getRiskCode()+"险种签单结束","1");	
			if (mapPol != null) {
//				日志监控，过程监控。 保单***的***险种签单成功；
				PubFun.logTrack (mGlobalInput,tLCPolSchema.getContNo(),"保单"+tLCPolSchema.getContNo()+"的"+tLCPolSchema.getRiskCode()+"险种签单成功");
				MMap tmpMap = (MMap) mapPol.getObjectByObjectName("MMap", 0);
				tLCPolSchema = (LCPolSchema) mapPol.getObjectByObjectName(
						"LCPolSchema", 0);
				mLCPolSet.set(i, tLCPolSchema);
				tmpMap.put(tLCPolSchema, "INSERT");
				if (rMap == null) {
					rMap = tmpMap;
				} else {
					rMap.add(tmpMap);
				}

				tTransferData = (TransferData) mapPol.getObjectByObjectName(
						"TransferData", 0);

			} else {
//				日志监控，过程监控。 保单***的***险种签单失败；
				PubFun.logTrack (mGlobalInput,tLCPolSchema.getContNo(),"保单"+tLCPolSchema.getContNo()+"的"+tLCPolSchema.getRiskCode()+"险种签单失败");
				return false;
			}

			mLCPolSchema = tLCPolSchema;
		}

		for (int j = 1; j <= mLCPolSet.size(); j++) {
			if (mLCPolSet.get(j).getMainPolNo().equals(
					mLCPolSet.get(j).getPolNo())) {
				mNewMainPolNo = mLCPolSet.get(j).getMainPolNo();
			}

		}

		// 增加签单前对管理机构的赋值
		
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCPolSet.get(1).getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			CError.buildErr(this, "查询代理人失败！","01","019");
			return false;
		}
		LAAgentSchema tLAAgentSchema = tLAAgentDB.getSchema();

		for (int j = 1; j <= mLCPolSet.size(); j++) {
			if (!mLCPolSet.get(j).getMainPolNo().equals(
					mLCPolSet.get(j).getPolNo())
					&& mNewMainPolNo != null) {
				mLCPolSet.get(j).setMainPolNo(mNewMainPolNo);
			}

			// 增加签单前对管理机构的赋值
			//tongmeng 2009-06-25 屏蔽此处
			//mLCPolSet.get(j).setManageCom(tLAAgentSchema.getManageCom());
		}
		 
		mInputData.add(mLCPolSchema);
		mInputData.add(rMap);
		mInputData.add(mLCPolSet);
		mInputData.add(tTransferData);
		
		this.mPolDetailData.add(mLCPolSet);
		this.mPolDetailData.add(this.mLCDutySet);
		this.mPolDetailData.add(this.mLCPremSet);
		this.mPolDetailData.add(this.mLCGetSet);
		this.mPolDetailData.add(this.mLCInsureAccSet);
		this.mPolDetailData.add(this.mLCPremToAccSet);
		this.mPolDetailData.add(this.mLCGetToAccSet);
		this.mPolDetailData.add(this.mSplitMap);
		this.mPolDetailData.add(this.mLJSPayPersonSet);
		this.mPolDetailData.add(this.mLCDiscountSet);
		
		return true;
	}

	/**
	 * 处理一张投保单的数据- 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tContNo
	 *            合同号
	 * @param tGrpPolNo
	 *            集体保单号
	 * @param tPolNo
	 *            保单号
	 * @param tPolPub
	 *            集体下个人签单时传入的需要用到数据
	 * @param tBQData
	 *            保全个人签单时传入的需要用到数据
	 * @return
	 */
	private VData dealOnePolData(LCPolSchema tLCPolSchema) {
		VData tReturn = new VData();

		TransferData tTransferData = new TransferData();

		// 初始化类属性
		if (!this.initV()) {
			return null;
		}

		MMap tmpMap = new MMap();

		LMRiskSchema tLMRiskSchema = null;

		// 准备投保单的数据
		if (tLCPolSchema.getContType().equals("2")) {
			mPolType = "2";
		}
		// 保全签单
		if (tLCPolSchema.getAppFlag().equals("2")
				|| tLCPolSchema.getAppFlag().equals("4")) {
			mBQFlag = true;
		}

		LCPolSchema delPolSchema = new LCPolSchema();
		delPolSchema.setSchema(tLCPolSchema);
		tmpMap.put(delPolSchema, "DELETE");
		mSplitMap.put(delPolSchema, "DELETE");
		// 保全不需要进行签单校验 modify at 2005-11-16
		if (mBQFlag == false) {
			// 签单校验
			if (this.checkOnePol(tLCPolSchema) == false) {
				return null;
			}
		}
		// 准备公用信息-生成险种保单号
		String tNewPolNo = "";
		if (!"2".equals(mPolType)) {
			tNewPolNo = this.preparePubInfo(tLCPolSchema);
			if (tNewPolNo == null) {
				return null;
			}
		} else {
			// 集体单个单层不换号
			tNewPolNo = tLCPolSchema.getProposalNo();
		}

		// 准备一个险种保单的签单数据
		VData tPolDetail = this.getOnePol(tLCPolSchema, tNewPolNo);
		//tongmeng 2008-09-11 for MS多主险,家庭单的拆分,用tPolDetail详细记录保单的所有信息.
		//在外面使用
		if (tPolDetail == null) {
			return null;
		}

		// (保全财务部分，承保部分财务不在这里处理) ---该部分会更改tPolDetail
		if (mBQFlag) {
			String oldPolNo = tLCPolSchema.getProposalNo();
			String inEdorNo = (String) mBQData.getValueByName("EdorNo");
			String inEdorType = (String) mBQData.getValueByName("EdorType");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			String strSql = "update LJAGetEndorse set contno='" + "?mNewContNo?"
					+ "',polno='" + "?tNewPolNo?" + "' where EndorsementNo='"
					+ "?inEdorNo?" + "'" + " and FeeOperationType='" + "?inEdorType?"
					+ "' and polno='" + "?oldPolNo?" + "'";
			sqlbv1.sql(strSql);
			sqlbv1.put("mNewContNo", mNewContNo);
			sqlbv1.put("tNewPolNo", tNewPolNo);
			sqlbv1.put("inEdorNo", inEdorNo);
			sqlbv1.put("inEdorType", inEdorType);
			sqlbv1.put("oldPolNo", oldPolNo);
			rMap.put(sqlbv1, "UPDATE");
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("update ljapayperson set contno = '" + "?mNewContNo?"
					+ "',polno = '" + "?tNewPolNo?" + "' where polno = '"
					+ "?oldPolNo?" + "'");
			sqlbv2.put("mNewContNo", mNewContNo);
			sqlbv2.put("tNewPolNo", tNewPolNo);
			sqlbv2.put("oldPolNo", oldPolNo);
			rMap.put(sqlbv2, "UPDATE");
		}

		// 处理帐户
		// 在getOnePol函数中，如果因为签单日期追溯部分的重新计算，此时tLCPolSchema的保单号就会是投保单号
		if (mNotCreatInsuaccFlag == false) {
			tLCPolSchema.setPolNo(tNewPolNo);

			if (DealAccount(tPolDetail, tLCPolSchema, tLMRiskSchema) == false) {
				return null;
			}
		}

		// 把准备好的数据放到结果集中
		tmpMap.add((MMap) tPolDetail.getObjectByObjectName("MMap", 0));
		tmpMap.put(
				(LCPremSet) tPolDetail.getObjectByObjectName("LCPremSet", 0),
				this.INSERT);
		tmpMap.put(
				(LCDutySet) tPolDetail.getObjectByObjectName("LCDutySet", 0),
				this.INSERT);
		tmpMap.put((LCGetSet) tPolDetail.getObjectByObjectName("LCGetSet", 0),
				this.INSERT);
		// tmpMap.put((LJAPaySet)tAPay.getObjectByObjectName("LJAPaySet",0 ) ,
		// tAPay.getObjectByObjectName("String",0));
		tLCPolSchema = (LCPolSchema) tPolDetail.getObjectByObjectName(
				"LCPolSchema", 0);

		tTransferData = (TransferData) tPolDetail.getObjectByObjectName(
				"TransferData", 0);

		tReturn.add(tmpMap);

		tReturn.add(tLCPolSchema);

		tReturn.add(tPolDetail.getObjectByObjectName("LCPremSet", 0));

		tReturn.add(tTransferData);

		// //同时把数据缓冲起来，待外头调用
		VData bufferData = new VData();
		bufferData.add((LCPolSchema) tPolDetail.getObjectByObjectName(
				"LCPolSchema", 0));
		bufferData.add(tPolDetail.getObjectByObjectName("LCPremSet", 0));
		this.mBufferedPolData.add(bufferData);
		//this.mPolDetailData = tPolDetail;
		return tReturn;

	}

	/**
	 * 处理一张投保单以及其关联的附险投保单的数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private VData getOnePol(LCPolSchema tLCPolSchema, String tNewPolNo) {
		VData tReturn = new VData();
		TransferData tTransferData = new TransferData();
		MMap tmpMap = new MMap();
		tmpMap.add(this.getElseUpdateTableSql(tLCPolSchema.getPolNo(),
				mNewContNo, tNewPolNo));

		// 取出原来的保费项
		LCPremSet tLCPremSet = this.getOldPrem(tLCPolSchema, tNewPolNo);

		if (tLCPremSet == null) {
			return null;
		}

		tmpMap.put((LCPremSet) PubFun.copySchemaSet(tLCPremSet), "DELETE");
		mSplitMap.put((LCPremSet) PubFun.copySchemaSet(tLCPremSet), "DELETE");

		// 取出原来的领取项
		LCGetSet tLCGetSet = this.getOldGet(tLCPolSchema, tNewPolNo);

		if (tLCGetSet == null) {
			return null;
		}
		tmpMap.put((LCGetSet) PubFun.copySchemaSet(tLCGetSet), "DELETE");
		mSplitMap.put((LCGetSet) PubFun.copySchemaSet(tLCGetSet), "DELETE");

		// 取出原来的责任
		LCDutySet tLCDutySet = this.getOldDuty(tLCPolSchema, tNewPolNo);
		if (tLCDutySet == null) {
			return null;
		}
		
		// 取出原来的应收子表
		LJSPayPersonSet tLJSPayPersonSet = this.getOldLJSPayPerson(tLCPolSchema, tNewPolNo);
		if (tLJSPayPersonSet == null) {
			return null;
		}	
		
		// 取出原来的折扣
		LCDiscountSet tLCDiscountSet = this.getOldDiscount(tLCPolSchema, tNewPolNo);
		if (tLCDiscountSet == null) {
			logger.debug("签单时旧保单号："+tLCPolSchema.getPolNo()+"新保单号："+tNewPolNo+"没有折扣...");
		}
		
		// 判断是否组合而且是卡单
		if ("2".equals(mPolType) && "card".equals(mark)) {
			ExeSQL sExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			String strSOL = " select contplancode from lcinsured where contno='"
					+ "?contno?"
					+ "' "
					+ " and contplancode is not null ";
			sqlbv3.sql(strSOL);
			sqlbv3.put("contno", tLCPolSchema.getContNo());
			String sContPlanCode = sExeSQL.getOneValue(sqlbv3);
			// 查询组合的代码并赋值给tLCDutySet
			String sCalDutyCode = "";
			String sCalFactor = "";
			String sCalFactorValue = "";
			strSOL = "select dutycode,calfactor,calfactorvalue from ldplandutyparam "
					+ " where contplancode ='"
					+ "?sContPlanCode?"
					+ "'"
					+ " and riskcode='"
					+ "?riskcode?"
					+ "' and PlanType in ('0','3')";
			sqlbv3.sql(strSOL);
			sqlbv3.put("sContPlanCode", sContPlanCode);
			sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
			SSRS tSSRS = sExeSQL.execSQL(sqlbv3);
			if (tSSRS.getMaxRow() > 0) {
				mTransferData.setNameAndValue("ISPlanRisk", "Y");
			}
			for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
				sCalDutyCode = tSSRS.GetText(k, 1); // 参数名
				sCalFactor = tSSRS.GetText(k, 2); // 参数名
				sCalFactorValue = tSSRS.GetText(k, 3); // 参数值
				if (sCalFactorValue == null || sCalFactorValue.equals("")) {
					continue;
				}
				for (int jj = 1; jj <= tLCDutySet.size(); jj++) {
					if (tLCDutySet.get(jj).getDutyCode().equals(sCalDutyCode)) {
						tLCDutySet.get(jj).setV(sCalFactor, sCalFactorValue);
						// 对其他参数赋值
						tLCDutySet.get(jj).setStandPrem(
								tLCDutySet.get(jj).getPrem());
						tLCDutySet.get(jj).setSumPrem(0);
						tLCDutySet.get(jj).setRiskAmnt(0);
						// 当计算规则为统一费率时，不将费率带到界面，在计算中重新到保险计划要素中取
						if (StrTool.compareString(tLCDutySet.get(jj)
								.getCalRule(), "1")) {
							tLCDutySet.get(jj).setFloatRate(0.0);
						}
					}
				}
			}
			//

		}

		tmpMap.put((LCDutySet) PubFun.copySchemaSet(tLCDutySet), "DELETE");
		mSplitMap.put((LCDutySet) PubFun.copySchemaSet(tLCDutySet), "DELETE");

		// 如果是需要生效日期追溯
		if (mBQFlag == false && this.needReCal(tLCPolSchema) == true) {
			// 改变保单生效日期
			tLCPolSchema.setCValiDate(fDate.getString(mValiDate));

			// 签单日期追溯部分的重新计算（涉及领取项、保费项、责任和保单部分）
			VData tReCal = this.reCal(tLCPolSchema, tLCGetSet, tLCDutySet,
					tLCPremSet,tLCDiscountSet);

			if (tReCal == null) {
				return null;
			}

			tLCPremSet.set((LCPremSet) tReCal.get(0));
			tLCGetSet.set((LCGetSet) tReCal.get(1));
			tLCDutySet.set((LCDutySet) tReCal.get(2));
			tLCPolSchema = (LCPolSchema) tReCal.get(3);
			tLJSPayPersonSet = (LJSPayPersonSet) tReCal.get(4);
		} else {
			logger.debug("mPolType====" + mPolType);
			if ((mValiDate == null || mValiDate.equals(""))
					&& mBQFlag == false
					&& (mPolType.equals("1") || ("2".equals(mPolType) && "card"
							.equals(mark)))) {
				return null;
			}

		}

		tTransferData.setNameAndValue("InsuredAppAgeChangeFlag", ""
				+ mInsuredAppAgeChangeFlag);
		// tongmeng 2008-02-27 add
		// 增加对生日单的校验
		if (mInsuredAppAgeChangeFlag == true) {
			return null;
		}
		// else //如果不需要追溯
		// {
		// //如果是附加险，并且系统变量指定其生效日期为主险，那么需要重新计算日期（涉及领取项、保费项、责任和保单部分）
		// if (alreadyChangeCValiDate == true)
		// {
		// VData tReCal = this.reCal(tLCPolSchema, tLCGetSet, tLCDutySet,
		// tLCPremSet);
		// tLCPremSet.set((LCPremSet) tReCal.get(0));
		// tLCGetSet.set((LCGetSet) tReCal.get(1));
		// tLCDutySet.set((LCDutySet) tReCal.get(2));
		// tLCPolSchema = (LCPolSchema) tReCal.get(3);
		// }
		// }

		// 保费项
		tLCPremSet = this.preparePrem(tLCPolSchema, tLCPremSet, tNewPolNo);

		if (tLCPremSet == null) {
			return null;
		}
		this.mLCPremSet.add(tLCPremSet);
		tReturn.add(tLCPremSet);

		// 领取项
		tLCGetSet = this.prepareGet(tLCPolSchema, tLCGetSet, tNewPolNo);

		if (tLCGetSet == null) {
			return null;
		}
		this.mLCGetSet.add(tLCGetSet);
		tReturn.add(tLCGetSet);

		// 责任
		tLCDutySet = this.prepareDuty(tLCPolSchema, tLCDutySet, tNewPolNo,
				tLCPremSet);

		if (tLCDutySet == null) {
			return null;
		}
		this.mLCDutySet.add(tLCDutySet);
		tReturn.add(tLCDutySet);
		
		// 应收子表
		tLJSPayPersonSet = this.prepareLJSPayPerson(tLCPolSchema, tLJSPayPersonSet, tNewPolNo);

		if (tLJSPayPersonSet == null) {
			return null;
		}
		this.mLJSPayPersonSet.add(tLJSPayPersonSet);
		tReturn.add(tLJSPayPersonSet);
		
		// 折扣表
		if(tLCDiscountSet!=null)
		{
			tLCDiscountSet = this.prepareDiscount(tLCPolSchema, tLCDiscountSet, tNewPolNo);

			if (tLCDiscountSet == null) {
				return null;
			}
		}		
		this.mLCDiscountSet.add(tLCDiscountSet);
		tReturn.add(tLCDiscountSet);

		// 险种保单（需要处理的投保单）
		LCPolSchema tLCPolMain = this.preparePol(tLCPolSchema, tNewPolNo,
				tLCDutySet);

		if (tLCPolMain == null) {
			return null;
		}

		tReturn.add(tLCPolMain);

		tReturn.add(tmpMap);
		// 将年龄变化flag传入
		tReturn.add(tTransferData);

		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		// @@可以处理险种保单集和单个险种保单的情况
		LCPolSet tLCPolSet = (LCPolSet) mInputData.getObjectByObjectName(
				"LCPolSet", 1);
		mNewContNo = (String) mInputData.getObjectByObjectName("String", 2);

		mBQData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			mLCPolSet.set(tLCPolSet);
		} else {
			LCPolSchema tSchema = (LCPolSchema) mInputData
					.getObjectByObjectName("LCPolSchema", 0);
			if (tSchema != null) {
				mLCPolSet.add(tSchema);
			}
		}
		logger.debug("mPolType:" + mPolType);

		// 处理卡单
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null) {
			logger.debug("get mark .................");
			mark = (String) mTransferData.getValueByName("mark");

		}
		String tempValiDate = (String) mTransferData
				.getValueByName("CValiDate");
		if (tempValiDate != null) {
			try {
				this.mValiDate = tSDF.parse(tempValiDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			CError.buildErr(this, "险种获得生效时间出错!","01","018");
			return false;
		}
		logger.debug("mark＝＝＝＝＝＝＝" + mark);
		if (mLCPolSet.size() <= 0) {
			return false;
		}
		return true;

	}

	/**
	 * 校验数据是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkOnePol(LCPolSchema tLCPolSchema) {

		//tongmeng 2008-09-11 modify
		//没有必要校验代理人状态
		/*
		// 检验代理人是否已经离职
		ExeSQL tExeSQL = new ExeSQL();
		String state = tExeSQL
				.getOneValue("SELECT agentstate FROM laagent where agentcode='"
						+ tLCPolSchema.getAgentCode() + "'");

		if ((state == null) || state.equals("")) {
			CError.buildErr(this, "无法查到该代理人的状态!");
			return false;
		}
		if (Integer.parseInt(state) >= 3) {
			CError.buildErr(this, "该代理人已经离职，不能签单!");
			return false;
		}*/

		return true;
	}

	/**
	 * 准备公用信息 输出：如果发生错误则返回false,否则返回true
	 */
	private String preparePubInfo(LCPolSchema tLCPolSchema) {
		// 生成险种保单号
		String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String tPolNo = PubFun1.CreateMaxNo("POLNO", tLimit);

		if (StrTool.cTrim(tPolNo).equals("")) {
			// @@错误处理
			CError.buildErr(this, "险种保单号生成失败!","01","009");
			return null;
		}
		return tPolNo;
	}

	/**
	 * 准备保费项信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCPremSet getOldPrem(LCPolSchema tLCPolSchema, String tNewPolNo) {
		String tOldPolNo = tLCPolSchema.getProposalNo();

		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(tOldPolNo);
		LCPremSet tLCPremSet = tLCPremDB.query();

		if (tLCPremDB.mErrors.needDealError() || tLCPremSet == null
				|| tLCPremSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "LCPrem表取数失败!","01","010");
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 准备保费项信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCPremSet preparePrem(LCPolSchema tLCPolSchema,
			LCPremSet tLCPremSet, String tNewPolNo) {
		int n = tLCPremSet.size();

		for (int i = 1; i <= n; i++) {
			LCPremSchema tLCPremSchema = tLCPremSet.get(i);

			tLCPremSchema.setPolNo(tNewPolNo);
			tLCPremSchema.setSumPrem(tLCPremSchema.getPrem());
			sumPrem += tLCPremSchema.getPrem();

			// 计算交至日期
			Date baseDate = fDate.getDate(tLCPremSchema.getPayStartDate());
			Date paytoDate = null;

			if (tLCPremSchema.getPayIntv() == 0) { // 趸交按照交费终止日期计算
				paytoDate = fDate.getDate(tLCPremSchema.getPayEndDate());
			} else {
				int interval = tLCPremSchema.getPayIntv();

				if (interval == -1) {
					interval = 0; // 不定期缴费按照交费日期计算
				}

				String unit = "M";
				paytoDate = PubFun.calDate(baseDate, interval, unit, null);

			}

			tLCPremSchema.setPayTimes(1);
			tLCPremSchema.setContNo(mNewContNo);
			tLCPremSchema.setPaytoDate(fDate.getString(paytoDate));
			tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			tLCPremSchema.setOperator(mGlobalInput.Operator);
			// tLCPremSchema.setMakeDate( );
			// 集体投保单

			tLCPremSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			// 需要在此查询出原LCPrem表中的MakeDate和MakeTime
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(tLCPolSchema.getProposalNo());
			tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLCPremDB.getInfo();
			tLCPremSchema.setMakeDate(tLCPremDB.getMakeDate());
			tLCPremSchema.setMakeTime(tLCPremDB.getMakeTime());
			tLCPremSet.set(i, tLCPremSchema);
		}
		if (tLCPremSet == null || tLCPremSet.size() <= 0) {
			return null;
		}
		return tLCPremSet;
	}

	/**
	 * 准备领取项信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCGetSet getOldGet(LCPolSchema tLCPolSchema, String tNewPolNo) {
		String tOldPolNo = tLCPolSchema.getProposalNo();

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(tOldPolNo);

		LCGetSet tLCGetSet = tLCGetDB.query();

		if (tLCGetDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LCGet表取数失败!","01","011");
			return null;
		}

		return tLCGetSet;
	}

	/**
	 * 准备领取项信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCGetSet prepareGet(LCPolSchema tLCPolSchema, LCGetSet tLCGetSet,
			String tNewPolNo) {
		int n = tLCGetSet.size();

		for (int i = 1; i <= n; i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);

			tLCGetSchema.setPolNo(tNewPolNo);

			tLCGetSchema.setContNo(mNewContNo);
			tLCGetSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCGetSchema.setMakeDate(tLCPolSchema.getMakeDate());
			tLCGetSchema.setMakeTime(tLCPolSchema.getMakeTime());
			tLCGetSchema.setOperator(mGlobalInput.Operator);
			tLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGetSchema.setModifyTime(PubFun.getCurrentTime());

			tLCGetSet.set(i, tLCGetSchema);
		}

		return tLCGetSet;
	}

	/**
	 * 准备责任信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCDutySet getOldDuty(LCPolSchema tLCPolSchema, String tNewPolNo) {
		String tOldPolNo = tLCPolSchema.getProposalNo();

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(tOldPolNo);

		LCDutySet tLCDutySet = tLCDutyDB.query();

		if (tLCDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "LCDuty表取数失败!","01","012");
			return null;
		}
		if (tLCDutySet == null || tLCDutySet.size() <= 0) {
			return null;
		}

		return tLCDutySet;
	}
	
	/**
	 * 准备责任信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LJSPayPersonSet getOldLJSPayPerson(LCPolSchema tLCPolSchema, String tNewPolNo) {
		String tOldPolNo = tLCPolSchema.getProposalNo();

		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		tLJSPayPersonDB.setPolNo(tOldPolNo);

		LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();

		if (tLJSPayPersonDB.mErrors.needDealError()) {
			CError.buildErr(this, "LJSPayPerson表取数失败!","01","012");
			return null;
		}
		if (tLJSPayPersonSet == null || tLJSPayPersonSet.size() <= 0) {
			return null;
		}

		return tLJSPayPersonSet;
	}
	
	/**
	 * 准备责任信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCDiscountSet getOldDiscount(LCPolSchema tLCPolSchema, String tNewPolNo) {
		String tOldPolNo = tLCPolSchema.getProposalNo();

		LCDiscountDB tLCDiscountDB = new LCDiscountDB();
		tLCDiscountDB.setPolNo(tOldPolNo);

		LCDiscountSet tLCDiscountSet = tLCDiscountDB.query();

		if (tLCDiscountDB.mErrors.needDealError()) {
			CError.buildErr(this, "LCDiscount表取数失败!","01","012");
			return null;
		}
		if (tLCDiscountSet == null || tLCDiscountSet.size() <= 0) {
			return null;
		}

		return tLCDiscountSet;
	}

	/**
	 * 准备责任信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCDutySet prepareDuty(LCPolSchema tLCPolSchema,
			LCDutySet tLCDutySet, String tNewPolNo, LCPremSet tLCPremSet) {
		int n = tLCDutySet.size();

		for (int i = 1; i <= n; i++) {
			LCDutySchema tLCDutySchema = tLCDutySet.get(i);

			tLCDutySchema.setPolNo(tNewPolNo);
			tLCDutySchema.setSumPrem(tLCDutySchema.getPrem());

			// 交至日期
			Date maxPaytoDate = fDate.getDate("1900-01-01");
			int m = tLCPremSet.size();

			for (int j = 1; j <= m; j++) {
				LCPremSchema tLCPremSchema = tLCPremSet.get(j);

				if (tLCDutySchema.getDutyCode().trim().equals(
						tLCPremSchema.getDutyCode().trim())
						&& fDate.getDate(tLCPremSchema.getPaytoDate()).after(
								maxPaytoDate)
//								&& fDate.getDate(tLCDutySchema.getPayEndDate()).after( //增加原因：ipul多缴费签单
//										fDate.getDate(tLCPremSchema.getPaytoDate()))
										) {
					maxPaytoDate = fDate.getDate(tLCPremSchema.getPaytoDate());
					// logger.debug("maxPaytoDate1:" + maxPaytoDate);
				}
			}

			// logger.debug("maxPaytoDate2:" + maxPaytoDate);
			tLCDutySchema.setPaytoDate(maxPaytoDate);
			tLCDutySchema.setContNo(mNewContNo);
			// ? tLCDutySchema.setFirstPayDate(tLCPolSchema.getCValiDate());
			tLCDutySchema.setCValiDate(tLCPolSchema.getCValiDate());
			tLCDutySchema.setMakeDate(tLCPolSchema.getMakeDate());
			tLCDutySchema.setMakeTime(tLCPolSchema.getMakeTime());
			tLCDutySchema.setOperator(mGlobalInput.Operator);
			tLCDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLCDutySchema.setModifyTime(PubFun.getCurrentTime());

			tLCDutySet.set(i, tLCDutySchema);
		}
		if (tLCDutySet == null || tLCDutySet.size() <= 0) {
			return null;
		}
		return tLCDutySet;
	}
	
	/**
	 * 准备责任信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LJSPayPersonSet prepareLJSPayPerson(LCPolSchema tLCPolSchema,
			LJSPayPersonSet tLJSPayPersonSet, String tNewPolNo) {
		int n = tLJSPayPersonSet.size();

		for (int i = 1; i <= n; i++) {
			LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
			tLJSPayPersonSchema.setPolNo(tNewPolNo);			
			tLJSPayPersonSchema.setContNo(mNewContNo);
			tLJSPayPersonSchema.setMakeDate(tLCPolSchema.getMakeDate());
			tLJSPayPersonSchema.setMakeTime(tLCPolSchema.getMakeTime());
			tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
			tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());

			tLJSPayPersonSet.set(i, tLJSPayPersonSchema);
		}
		if (tLJSPayPersonSet == null || tLJSPayPersonSet.size() <= 0) {
			return null;
		}
		return tLJSPayPersonSet;
	}
	
	/**
	 * 准备责任信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCDiscountSet prepareDiscount(LCPolSchema tLCPolSchema,
			LCDiscountSet tLCDiscountSet, String tNewPolNo) {
		int n = tLCDiscountSet.size();

		for (int i = 1; i <= n; i++) {
			LCDiscountSchema tLCDiscountSchema = tLCDiscountSet.get(i);
			tLCDiscountSchema.setPolNo(tNewPolNo);			
			tLCDiscountSchema.setContNo(mNewContNo);
			tLCDiscountSchema.setMakeDate(tLCPolSchema.getMakeDate());
			tLCDiscountSchema.setMakeTime(tLCPolSchema.getMakeTime());
			tLCDiscountSchema.setOperator(mGlobalInput.Operator);
			tLCDiscountSchema.setModifyDate(PubFun.getCurrentDate());
			tLCDiscountSchema.setModifyTime(PubFun.getCurrentTime());

			tLCDiscountSet.set(i, tLCDiscountSchema);
		}
		if (tLCDiscountSet == null || tLCDiscountSet.size() <= 0) {
			return null;
		}
		return tLCDiscountSet;
	}

	/**
	 * 准备险种保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCPolSchema preparePol(LCPolSchema ttLCPolSchema, String tNewPolNo,
			LCDutySet tLCDutySet) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setSchema(ttLCPolSchema);
		if (tLCPolSchema.getMainPolNo().equals(tLCPolSchema.getPolNo())) {
			tLCPolSchema.setMainPolNo(tNewPolNo);
		}
		tLCPolSchema.setPolNo(tNewPolNo);
		tLCPolSchema.setContNo(mNewContNo);
		tLCPolSchema.setSignCom(mGlobalInput.ManageCom);
		tLCPolSchema.setSumPrem(tLCPolSchema.getPrem());
		tLCPolSchema.setLeavingMoney(mLeft);
		// 在合同统一处理
		tLCPolSchema.setSignDate("");
		tLCPolSchema.setSignTime("");
		tLCPolSchema.setLastRevDate(tLCPolSchema.getCValiDate()); // 把最近复效日期置为起保日期
		// if (!mNewGrpContNo.equals(""))
		// tLCPolSchema.setGrpContNo(mNewGrpContNo) ;
		tLCPolSchema.setAppFlag("1");
		//tongmeng 2009-01-10 
		//modify 修改保单状态规则
		tLCPolSchema.setPolState("1"); // sxy-2003-09-08(polstatus编码规则:前两位描述险种保单状态,后两位描述使险种保单处于该状态的原因.末尾四位描述了前一险种保单状态和处于该状态的原因)

		// 交至日期
		Date maxPaytoDate = null;
		Date PayEndDate = null;
		for (int j = 1; j <= tLCDutySet.size(); j++) {
			LCDutySchema tLCDutySchema = tLCDutySet.get(j);

			if (maxPaytoDate == null
					|| fDate.getDate(tLCDutySchema.getPaytoDate()).after(
							maxPaytoDate)) {
				maxPaytoDate = fDate.getDate(tLCDutySchema.getPaytoDate());
			}
			if (PayEndDate == null
					|| PayEndDate.before(fDate.getDate(tLCDutySchema
							.getPayEndDate()))) {
				PayEndDate = fDate.getDate(tLCDutySchema.getPayEndDate());
			}
		}
		// 交至日期
		tLCPolSchema.setPaytoDate(fDate.getString(maxPaytoDate));
		tLCPolSchema.setPayEndDate(fDate.getString(PayEndDate));
		// 从暂交费信息中取firstpaydate日期信息
		if (mBQFlag == false) {
			if (getPaydates(tLCPolSchema) == false) {
				return null;
			}
		} else {
			mFirstPayDate = fDate.getDate(tLCPolSchema.getCValiDate());
		}
		tLCPolSchema.setFirstPayDate(this.mFirstPayDate);
		// 增加prem
		tLCPolSchema.setSumPrem(tLCPolSchema.getPrem());

		tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCPolSchema.setModifyTime(PubFun.getCurrentTime());

		// 查询LMRiskApp,看是否从暂缴费表中读取银行的账号和户名--并且是个人
		// mNeedReadBankFlag 在前面已经付过值
		// if ((mNeedReadBankFlag != null) && mNeedReadBankFlag.equals("1") &&
		// mPolType.equals("1"))
		// {
		// // logger.debug("个人险种保单标记:" + mPolType);
		//
		// GetPayType tGetPayType = new GetPayType();
		//
		// if (tGetPayType.getPayTypeForLCPol(tLCPolSchema.getPrtNo(), 4) == false)
		// {
		// }
		// else
		// {
		// /*Lis5.3 upgrade set
		// tLCPolSchema.setBankAccNo(tGetPayType.getBankAccNo());
		// tLCPolSchema.setAccName(tGetPayType.getAccName());
		// */
		// }
		// }

		return tLCPolSchema;
	}

	/**
	 * 初始化类属性 输出：无
	 */
	private boolean initV() {
		mLJAPaySchema = new LJAPaySchema();
		mRiskFlag = "";
		mNeedReadBankFlag = "";
		mSameBatch = "0"; // 主附险是否属于同一批 "0"--非同一批 "1"--同一批
		mPolType = "1"; // 1-个人单 2-集体下的个人单 3-合同下的个人单
		mLeft = 0;
		mFirstPayDate = null;
		mMainPolNo = null;
		sumMoney = 0; // 总的交费额
		maxPayDate = null;
		maxEnterAccDate = null;
		mInsuredAppAgeChangeFlag = false;
		alreadyChangeCValiDate = false; // 是否已经更改过生效日期
		mBQFlag = false; // 保全标记初始化置为假
		mLMRiskAppSchema = null;
		mNotCreatInsuaccFlag = false;

		// 生成流水号
		if (mSerialNo.trim().equals("")) {
			String tLimit = "";
			tLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
			mSerialNo = PubFun1.CreateMaxNo("SerialNo", tLimit);
		}
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("ChangeSubValiDate");
		if (tLDSysVarDB.getInfo() == false) {
			CError.buildErr(this, "系统变量表没有ChangeSubValiDate变量！");
			return false;
		}
		mLDSysVarSchema = tLDSysVarDB.getSchema();
		return true;
	}

	/**
	 * 判断是否需要追溯险种保单生效日期 输出：boolean
	 */
	private boolean needReCal(LCPolSchema tLCPolSchema) {
		mInsuredAppAgeChangeFlag = false;
		logger.debug("mInsuredAppAgeChangeFlag==="
				+ mInsuredAppAgeChangeFlag);
		// 如果附险的生效日期随主险，那么直接跳过，不用重新计算
		if (alreadyChangeCValiDate == true) {
			return false;
		}

		if (mPolType.equals("1")
				|| ("2".equals(mPolType) && "card".equals(mark))) // 个人单才需要进行追溯
		{
			// tongmeng 2008-08-11 modify
			// 根据险种不同，查找出新生效日期的计算方法，如果是附加险，为了保证主附险生效日期一致，则取主险的生效日期回溯方式
			// tongmeng 2008-08-12 modify
			// 生效时间在合同程序中计算
			// String tBack=getSignValiDateBackFlag(tLCPolSchema);

			if (tLCPolSchema.getSpecifyValiDate() == null) { // 如果为空，置为N
				tLCPolSchema.setSpecifyValiDate("N");
			}

			if ((tLCPolSchema.getSpecifyValiDate() != null)
					&& tLCPolSchema.getSpecifyValiDate().equals("N")) {

				// mValiDate = this.calValiDate(tLCPolSchema, tBack);

				if (mValiDate == null || mValiDate.equals("")) {
					// CError.buildErr(this, "没有交费，或交费没有到帐，导致生效日计算失败！");
					if (mark == null || !mark.equals("card")) {
						if (!sendBFNotice(mLCPolSet)) {
							CError.buildErr(this, "补费通知书发送失败！","01","013");
							return false;
						}
					}
					return false;
				}
				int newAge = PubFun.calInterval(fDate.getDate(tLCPolSchema
						.getInsuredBirthday()), mValiDate, "Y");
				// 以下根据险种号<RiskCode>来判断是否银代险<LMRiskApp表RiskProp字段为“Y”>
				LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
				// tLMRiskAppDB.setRiskProp("Y");
				tLMRiskAppDB.getInfo();
				tLMRiskAppSchema.setSchema(tLMRiskAppDB.getSchema());
				String tRiskProp = tLMRiskAppSchema.getRiskProp(); // 险种性质,"Y--银代险"
				// 银代和卡单不需要以下校验
				logger.debug("newAge:" + newAge);
				logger.debug("tLCPolSchema.getInsuredAppAge()"
						+ tLCPolSchema.getInsuredAppAge());
				if (newAge == tLCPolSchema.getInsuredAppAge() // ||
				// tongmeng 2007-12-25 add
				// 增加生日单校验
				// tRiskProp.equals("Y") ||
				// (mark != null && mark.equals("card"))
				) {
					tLCPolSchema.setInsuredAppAge(newAge);
					return true;
				} else {
					mInsuredAppAgeChangeFlag = true;
					tLCPolSchema.setInsuredAppAge(newAge);
					// return true;
					// tongmeng 2007-12-25 add
					// 增加生日单校验
					// @@错误处理
					CError.buildErr(this, tLCPolSchema.getPrtNo()
							+ " 生日原因导致保费变化","01","017");
					return false;
				}
			} else {
				// 如果指定生效日的话,使用险种的生效日
				mValiDate = new FDate().getDate(tLCPolSchema.getCValiDate());
			}
		}		
		return true;
	}

	/**
	 * 重新计算投保单 输出：VData
	 */
	private VData reCal(LCPolSchema tLCPolSchema, LCGetSet tLCGetSet,
			LCDutySet tLCDutySet, LCPremSet tLCPremSet,LCDiscountSet tLCDiscountSet) {
		VData tReturn = new VData();
		LCPolBL tLCPolBL = new LCPolBL();
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		LCGetBLSet tLCGetBLSet = new LCGetBLSet();

		// 把保费项中加费的部分提出来
		String newStart = tLCPolSchema.getCValiDate();
		int premCount = tLCPremSet.size();
		//tongmeng 2010-11-30 modify
		LCPremSet tReCalLCPremSet = new LCPremSet();
		for(int i=1;i<=tLCPremSet.size();i++)
		{
			
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setSchema(tLCPremSet.get(i));
			
			//tongmeng 2010-12-01 modify
			//排除加费的数据
			if (tLCPremSchema.getPayPlanCode().substring(0, 6)
					.equals("000000")) {
				continue;
			}
			
			tReCalLCPremSet.add(tLCPremSchema);
		}
		double tStandPrem = 0;
		String tTransDate = tLCPolSchema.getCValiDate()==null?tLCPolSchema.getPolApplyDate():tLCPolSchema.getCValiDate();


		for (int i = 1; i <= premCount; i++) {
			LCPremSchema tLCPremSchema = tLCPremSet.get(i);

			if (!tLCPremSchema.getPayPlanCode().substring(0, 6)
					.equals("000000")) {
				
				/*
				 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
				 * 如果返回值小于0，则有错误数据
				 * orgitype 传入的币种
				 * destype 需要转换的币种
				 * transdate 转换日期
				 * amnt 转换金额
				 */
				String tCurrency = tLCPremSchema.getCurrency();
				
				double newPrem = 0;
				newPrem = PubFun.round(this.mLDExch.toOtherCur(tCurrency, tLCPolSchema.getCurrency(), tTransDate,tLCPremSchema.getPrem()),2);

				if(tLCPremSchema.getPayStartDate().equals(tLCPolSchema.getCValiDate()))
					tStandPrem += newPrem;
				tLCPremSet.remove(tLCPremSchema);
				premCount--;
				i--;
			} else {
				int chg = PubFun.calInterval(tLCPremSchema.getPayStartDate(),
						newStart, "D");
				Date payStartDate = PubFun.calDate(fDate.getDate(tLCPremSchema
						.getPayStartDate()), chg, "D", null);
				Date payEndDate = PubFun.calDate(fDate.getDate(tLCPremSchema
						.getPayEndDate()), chg, "D", null);
				tLCPremSchema.setPayStartDate(fDate.getString(payStartDate));
				tLCPremSchema.setPayEndDate(fDate.getString(payEndDate));
			}
		}

		int dutyCount = tLCDutySet.size();

		if (dutyCount == 1) {
			LCDutySchema tLCDutySchema = tLCDutySet.get(1);
			// tLCDutySchema.setDutyCode(null);			
			tLCDutySchema.setPrem(tStandPrem);
			tLCDutySet.set(1, tLCDutySchema);
		}

		int getCount = tLCGetSet.size();

		for (int i = 1; i <= getCount; i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);

			if (tLCGetSchema.getGetDutyKind() == null) {
				tLCGetSet.remove(tLCGetSchema);
				getCount--;
				i--;
			}
		}

		tLCPolBL.setSchema(tLCPolSchema);
		tLCDutyBLSet.set(tLCDutySet);
		tLCGetBLSet.set(tLCGetSet);

		CalBL tCalBL;

		if (getCount == 0) {
			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, mTransferData, "");
		} else {
			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, tLCGetBLSet,
					mTransferData, "");
		}
		
		// 险种其他信息
		LCPolOtherDB tLCPolOtherDB = new LCPolOtherDB();
		LCPolOtherSet tLCPolOtherSet = new LCPolOtherSet(); // 险种其他信息
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("select * from lcpolother where polno='"
				+ "?polno?" + "'");
		sqlbv5.put("polno", tLCPolBL.getPolNo());
		tLCPolOtherSet.set(tLCPolOtherDB.executeQuery(sqlbv5));
		if (tLCPolOtherSet != null && tLCPolOtherSet.size() >= 1) {
			tCalBL.SetLCPolOther(tLCPolOtherSet.get(1));
		}
		//tongmeng 2010-11-30 modify
		//投资账户分币种缴费处理
		int tDutyCount =tLCDutyBLSet.size();
		int tPremCount = tReCalLCPremSet.size();
		if(tDutyCount!=tPremCount)
		{
			tCalBL.setNoCalFalg(false);
			if (tCalBL.calPol2(tReCalLCPremSet) == false) { // 前面tCalBL的成员变量保存了传入的数据后
				// ，
				// 这里计算将用到这些成员变量
				// @@错误处理
				CError
						.buildErr(
								this,"险种保单重新计算时失败:"
								+ tCalBL.mErrors.getFirstError(),"01","014");
				return null;
			}
		}
		else
		{
		if (tCalBL.calPol() == false) {
			CError.buildErr(this, "险种保单重新计算时失败:"
					+ tCalBL.mErrors.getFirstError(),"01","014");
			return null;
		}
		}

		// 取出计算的结果
		tLCPolSchema = tCalBL.getLCPol().getSchema();

		LCPremSet tLCPremSet1 = (LCPremSet) tCalBL.getLCPrem();
		tLCGetSet = (LCGetSet) tCalBL.getLCGet();
		tLCDutySet = (LCDutySet) tCalBL.getLCDuty();
	
		
		// 加入加费的部分
		double addPrem = 0;
		int dutyCount1 = tLCDutySet.size();

		for (int i = 1; i <= premCount; i++) {
			LCPremSchema tLCPremSchema = tLCPremSet.get(i);
			
			/*
			 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
			 * 如果返回值小于0，则有错误数据
			 * orgitype 传入的币种
			 * destype 需要转换的币种
			 * transdate 转换日期
			 * amnt 转换金额
			 */
			String tCurrency = tLCPremSchema.getCurrency();
			
			
			addPrem += PubFun.round(this.mLDExch.toOtherCur(tCurrency, tLCPolSchema.getCurrency(), tTransDate,tLCPremSchema.getPrem()),2);

			for (int j = 1; j <= dutyCount1; j++) {
				LCDutySchema tLCDutySchema = tLCDutySet.get(j);

				if (tLCDutySchema.getDutyCode().equals(
						tLCPremSchema.getDutyCode())&&tLCPremSchema.getPayStartDate().equals(tLCPolSchema.getCValiDate())) {
					
					double newSumPrem = 0;
					
					
					newSumPrem = this.mLDExch.toOtherCur(tCurrency, tLCDutySchema.getCurrency(), tTransDate, tLCPremSchema.getPrem());

					
					
					tLCDutySchema.setPrem(PubFun.round(newSumPrem, 2)
							+ PubFun.round(tLCDutySchema.getPrem(),2));
				}
			}
		}
		//addPrem = 
		tLCPolSchema.setPrem(PubFun.round(tLCPolSchema.getPrem(),2) + PubFun.round(addPrem,2));
		tLCPremSet1.add(tLCPremSet);
		
		//重算折扣和应收
		//************************************2011-1-14**************************************
		LJSPayPersonSet rLJSPayPersonSet = new LJSPayPersonSet();
		/** @todo 2----获取本次处理交费号* */
		String tGetNoticeNo ="";
		LJSPaySet qLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(tLCPolSchema.getPrtNo());
		tLJSPayDB.setOtherNoType("2");
		qLJSPaySet = tLJSPayDB.query();
		if(qLJSPaySet!=null && qLJSPaySet.size()>0)
		{
			tGetNoticeNo=qLJSPaySet.get(1).getGetNoticeNo();
		}
		else
		{
			CError.buildErr(this, "应收总表数据查询失败！");
			return null;
		}
		logger.debug("本次处理交费号:::::::::::::::::::" + tGetNoticeNo);			
		LCPremBLSet tLCPremBLSet = new LCPremBLSet();		
		for(int k=1;k<=tLCPremSet1.size();k++)
		{
			if(tLCPremSet1.get(k).getPayStartDate().equals(tLCPolSchema.getCValiDate()))
				tLCPremBLSet.add(tLCPremSet1.get(k));
		}//************增加折扣处理 start
		if(tLCDiscountSet!=null && tLCDiscountSet.size()>0)
		{
			DiscountCalBL tDiscountCalBL = new DiscountCalBL();
			VData tzkVData = new VData();
			tzkVData.add(tLCPolSchema);
			tzkVData.add(tLCPremBLSet);
			tzkVData.add(tLCDiscountSet);
			tzkVData.add(tGetNoticeNo);
			//得到该保单折扣减去的钱 ，为负值
			if(!tDiscountCalBL.calculate(tzkVData))
			{
				CError.buildErr(this, "折扣计算失败！");
				return null;
			}
			
			//得到折扣和应收子表数据
			VData rVData = tDiscountCalBL.getResult();
			LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
			if(tLJSPayPersonSet!=null)
				rLJSPayPersonSet.add(tLJSPayPersonSet);						
		}	
		//************增加折扣处理 end
		//************增加应收总表和子表处理 start
		TBPrepareLJS tTBPrepareLJS = new TBPrepareLJS();
		VData tzkVData = new VData();
		tzkVData.add(tLCPolSchema);
		tzkVData.add(tLCPremBLSet);
		tzkVData.add(rLJSPayPersonSet);
		tzkVData.add(tGetNoticeNo);
		if(!tTBPrepareLJS.prepare(tzkVData,"3"))
		{
			CError.buildErr(this, "准备应收信息失败！");
			return null;
		}
		
		//得到应收数据
		VData rVData = tTBPrepareLJS.getResult();		
		LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
		LJSPaySet tLJSPaySet = (LJSPaySet)rVData.getObjectByObjectName("LJSPaySet", 0);
		if(tLJSPayPersonSet==null ||tLJSPaySet==null)
		{
			CError.buildErr(this, "应收信息返回失败！");
			return null;
		}
		rLJSPayPersonSet = new LJSPayPersonSet();
		rLJSPayPersonSet.add(tLJSPayPersonSet);
		//************增加应收处理 end
		//************************************2011-1-14***********************************************

		tReturn.add(tLCPremSet1);
		tReturn.add(tLCGetSet);
		tReturn.add(tLCDutySet);
		tReturn.add(tLCPolSchema);
		tReturn.add(rLJSPayPersonSet);

		return tReturn;
	}

	/**
	 * 处理账户类险种的账户信息
	 * 
	 * @param PolDetail
	 *            VData
	 * @param inLCPolSchema
	 *            LCPolSchema
	 * @param inLMRiskSchema
	 *            LMRiskSchema
	 * @return boolean
	 */
	private boolean DealAccount(VData PolDetail, LCPolSchema inLCPolSchema,
			LMRiskSchema inLMRiskSchema) {

		if (inLMRiskSchema == null) {
			// 查询险种描述表：是否和帐户相关
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(inLCPolSchema.getRiskCode());
			if (tLMRiskDB.getInfo() == false) {
				CError.buildErr(this, "查询险种描述表失败！","01","016");

				return false;
			}

			inLMRiskSchema = tLMRiskDB.getSchema();
		}

		if ((inLMRiskSchema.getInsuAccFlag() == null)
				|| inLMRiskSchema.getInsuAccFlag().equals("N")) {
			return true;
		}
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String tSQL = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tSQL = "select acccreatepos from lmriskinsuacc a "
					+ "where a.insuaccno=(select insuaccno from lmrisktoacc "
					+ "where riskcode='" + "?riskcode?"
					+ "' and rownum=1)";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tSQL = "select acccreatepos from lmriskinsuacc a "
					+ "where a.insuaccno=(select insuaccno from lmrisktoacc "
					+ "where riskcode='" + "?riskcode?"
					+ "' limit 0,1)";
		}
		sqlbv6.sql(tSQL);
		sqlbv6.put("riskcode", inLMRiskSchema.getRiskCode());
		ExeSQL tExeSQL = new ExeSQL();
		String tAccCreatePos = tExeSQL.getOneValue(sqlbv6);
		if (tAccCreatePos == null || "".equals(tAccCreatePos)) {
			tAccCreatePos = "2";
		}

		String Rate = null; // 分配比率置空，待调整

		LCPremSet tLCPremSet = (LCPremSet) PolDetail.getObjectByObjectName(
				"LCPremSet", 0);
		DealAccount tDealAccount = new DealAccount(this.mGlobalInput);
		TransferData tTransferData = new TransferData();
		// diff-----AccCreatePos:"1"
		// tTransferData.setNameAndValue("AccCreatePos", "2"); //生成位置：缴费时 即签单核销时
		tTransferData.setNameAndValue("AccCreatePos", tAccCreatePos); // 生成位置：承保时
		tTransferData.setNameAndValue("OtherNoType", "1"); // 其它号码类型：个人险种保单号
		tTransferData.setNameAndValue("Rate", Rate);

		// 注意：此时数据尚未存入数据库,因此，暂时用投保单号代替保单号。
		tTransferData.setNameAndValue("PolNo", inLCPolSchema.getProposalNo());
		tTransferData.setNameAndValue("OtherNo", inLCPolSchema.getProposalNo());

		// 生成帐户结构
		VData tVData = tDealAccount.createInsureAccForBat(tTransferData,
				inLCPolSchema, inLMRiskSchema);

		if (tDealAccount.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tDealAccount.mErrors);
			return false;
		}
		// 计算并填充账户结构
		if (tVData == null) {
			CError.buildErr(this, "创建账户结构失败");
			return false;
		}

		// 创建管理费结构表
		LCInsureAccSet tmpLCInsureAccSet = (LCInsureAccSet) (tVData
				.getObjectByObjectName("LCInsureAccSet", 0));

		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) (tVData
				.getObjectByObjectName("LCInsureAccSet", 0));
		LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) (tVData
				.getObjectByObjectName("LCPremToAccSet", 0));
		LCGetToAccSet tLCGetToAccSet = (LCGetToAccSet) (tVData
				.getObjectByObjectName("LCGetToAccSet", 0));
		LCInsureAccTraceSet tLCInsureAccTraceSet = (LCInsureAccTraceSet) (tVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0));
		LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) (tVData
				.getObjectByObjectName("LCInsureAccClassSet", 0));
		if (tLCInsureAccTraceSet == null) {
			tLCInsureAccTraceSet = new LCInsureAccTraceSet();
			tVData.add(tLCInsureAccTraceSet);
		}
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = null;
		VData feeData = null;
		// 创建管理费结构
		feeData = tDealAccount.getManageFeeStru(inLCPolSchema, tLCPremToAccSet,
				tmpLCInsureAccSet);
		if (feeData != null) {
			tLCInsureAccFeeSet = (LCInsureAccFeeSet) feeData
					.getObjectByObjectName("LCInsureAccFeeSet", 0);
			tLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) feeData
					.getObjectByObjectName("LCInsureAccClassFeeSet", 0);
			tLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) feeData
					.getObjectByObjectName("LCInsureAccFeeTraceSet", 0);
			if (tLCInsureAccFeeTraceSet == null) {
				tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
			}

		}
		tVData.add(tLCInsureAccFeeSet);
		tVData.add(tLCInsureAccClassFeeSet);
		tVData.add(tLCInsureAccFeeTraceSet);

		// 计算管理费和账户注入资金
		tVData.add(tLCPremSet);
		tVData.add(inLCPolSchema);

		CManageFee cmanageFee = new CManageFee();
		boolean cal = cmanageFee.calPremManaFee(tVData, "1", inLCPolSchema
				.getPolNo(), "1", "BF");

		if (!cal || cmanageFee.mErrors.needDealError()) {
			if (cmanageFee.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(cmanageFee.mErrors);
			} else {
				CError.buildErr(this, "计算账户金额或管理费时出错");
			}
			return false;
		}

		MMap tmpMap = (MMap) PolDetail.getObjectByObjectName("MMap", 0);
		tmpMap.put(tLCInsureAccSet, this.INSERT);
		tmpMap.put(tLCPremToAccSet, this.INSERT);
		tmpMap.put(tLCGetToAccSet, this.INSERT);
		//tongmeng 2008-09-11 add
		//PolDetail保存所有保单数据
		PolDetail.add(tLCInsureAccSet);
		PolDetail.add(tLCPremToAccSet);
		PolDetail.add(tLCGetToAccSet);
		this.mLCInsureAccSet.add(tLCInsureAccSet);
		this.mLCPremToAccSet.add(tLCPremToAccSet);
		this.mLCGetToAccSet.add(tLCGetToAccSet);
		// 对于团单直接进行插入操作
		// 个单需进行险种保单换号处理
		if (mPolType.equals("2") && !"card".equals(mark)) {
			tmpMap.put(tLCInsureAccClassSet, this.INSERT);
			tmpMap.put(tLCInsureAccTraceSet, this.INSERT);
			tmpMap.put(tLCInsureAccFeeSet, this.INSERT);
			tmpMap.put(tLCInsureAccClassFeeSet, this.INSERT);
			tmpMap.put(tLCInsureAccFeeTraceSet, this.INSERT);
		} else {
			this.mInputData.add(tLCInsureAccClassSet);
			this.mInputData.add(tLCInsureAccTraceSet);
			this.mInputData.add(tLCInsureAccFeeSet);
			this.mInputData.add(tLCInsureAccClassFeeSet);
			this.mInputData.add(tLCInsureAccFeeTraceSet);
		}
		
		PolDetail.add(tLCInsureAccClassSet);
		PolDetail.add(tLCInsureAccTraceSet);
		PolDetail.add(tLCInsureAccFeeSet);
		PolDetail.add(tLCInsureAccClassFeeSet);
		PolDetail.add(tLCInsureAccFeeTraceSet);


		return true;
	}

	/**
	 * 生成紧紧需要更新合同号的表的sql
	 * 
	 * @param oldPolNo
	 *            String
	 * @param newContNo
	 *            String
	 * @param newPolNo
	 *            String
	 * @return MMap
	 */
	private MMap getElseUpdateTableSql(String oldPolNo, String newContNo,
			String newPolNo) {
		Vector sqlVector = new Vector();
		String wherepart = " PolNo='" + oldPolNo + "'";
		String[] tables1 = { "LCSpecNote" };
		String condition = " polno='" + newPolNo + "'";
		sqlVector.addAll(PubFun.formUpdateSql(tables1, condition, wherepart));
		// 只更新PolNo的表
		String[] polOnlyTables = { "LCSpec", "LCInsuredRelated", "LCPrem_1" };
		condition = condition + " , ModifyDate='" + PubFun.getCurrentDate()
				+ "', ModifyTime='" + PubFun.getCurrentTime() + "' ";

		sqlVector.addAll(PubFun.formUpdateSql(polOnlyTables, condition,
				wherepart));
		// 还需更新ContNo的表
		String[] tables = { "LCUWMaster", "LCUWError", "LCUWSub", "LCBnf" };
		condition = " contno='" + newContNo + "'," + condition;

		sqlVector.addAll(PubFun.formUpdateSql(tables, condition, wherepart));

		if (sqlVector != null) {
			MMap tmpMap = new MMap();
			for (int i = 0; i < sqlVector.size(); i++) {
				tmpMap.put((String) sqlVector.get(i), this.UPDATE);
			}
			return tmpMap;
		}
		return null;
	}

	/**
	 * 从投保单财务信息中查询交费日期信息等 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean getPaydates(LCPolSchema tLCPolSchema) {
		double sumPrem = 0;
		sumMoney = 0;
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		if (!mBQFlag) {
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			String querytmpfeeSQL = "select * from ljtempfee where tempfeetype='1'"
					+ " and otherno='"
					+ "?otherno?"
					+ "'"
					+ " and othernotype in('6','7') and confflag='0' and confdate is null"
					+ " and ((enteraccdate is null) or "
					+ " (enteraccdate is not null and enteraccdate<>'3000-01-01'))"
					+ " order by makedate,maketime";
			sqlbv7.sql(querytmpfeeSQL);
			sqlbv7.put("otherno", tLCPolSchema.getPrtNo());
			logger.debug("###querytmpfeeSQL====" + querytmpfeeSQL);
			tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv7);
		} else {
			String tEdorNo = (String) mBQData.getValueByName("EdorNo");
			tLJTempFeeDB.setOtherNo(tEdorNo);
			tLJTempFeeDB.setOtherNoType("3");
			tLJTempFeeDB.setTempFeeType("4");
			tLJTempFeeDB.setConfFlag("1");
			tLJTempFeeSet = tLJTempFeeDB.query();
		}
		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "财务信息取数失败!");
			return false;
		}
		//
		int m = tLJTempFeeSet.size();

		for (int j = 1; j <= m; j++) {
			LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(j);
			if (tLJTempFeeSchema.getPayDate() == null
					|| tLJTempFeeSchema.getEnterAccDate() == null) {
				CError.buildErr(this, "有交费号为["
						+ tLJTempFeeSchema.getTempFeeNo() + "]未到帐","01","015");
				return false;
			}

			// 取首次交费日期
			Date payDate = fDate.getDate(tLJTempFeeSchema.getPayDate());

			if (mFirstPayDate == null || payDate.before(mFirstPayDate)) {
				mFirstPayDate = payDate;
			}

			if (maxPayDate == null || maxPayDate.before(payDate)) {
				maxPayDate = payDate;
			}

			Date enterAccDate = fDate.getDate(tLJTempFeeSchema
					.getEnterAccDate());

			if (maxEnterAccDate == null || maxEnterAccDate.before(enterAccDate)) {
				maxEnterAccDate = enterAccDate;
			}

		}

		return true;
	}

	/**
	 * 核销财务（根据不同情况处理）
	 * 
	 * @param tPolData
	 * @param tLCPolSchema
	 * @param PolNo
	 * @return
	 */
	private boolean verifyFinance(VData tPolData, LCPolSchema tLCPolSchema,
			String PolNo, TransferData tBQTransferData) {
		LCPremSet tLCPremSet = (LCPremSet) tPolData.getObjectByObjectName(
				"LCPremSet", 0);

		String inEdorType = (String) tBQTransferData.getValueByName("EdorType");

		// 如果保全类型是集体下新单加人
		if ("NI".equals(inEdorType)) {
			// 根据保全下个人签单流程走-将前面保存的个人险种保单签单标记为1(得到该对象的引用，直接更改)
			LCPolSchema saveLCPolSchema = (LCPolSchema) tPolData
					.getObjectByObjectName("LCPolSchema", 0);

			VData tVData = new VData();
			tBQTransferData.setNameAndValue("NewPolNo", PolNo);
			tVData.add(mGlobalInput);
			tVData.add(tBQTransferData);
			tVData.add(tLCPremSet);
			tVData.add(tLCPolSchema);

			EdorSignBL tEdorSignBL = new EdorSignBL();

			if (tEdorSignBL.submitData(tVData, "QUERY") == false) {
				return false;
			}

			tVData = tEdorSignBL.getResult();

			LJAGetEndorseSet outLJAGetEndorseSet = (LJAGetEndorseSet) tVData
					.getObjectByObjectName("LJAGetEndorseSet", 0);
			LJAPayPersonSet outLJAPayPersonSet = (LJAPayPersonSet) tVData
					.getObjectByObjectName("LJAPayPersonSet", 0);
			rMap.put(outLJAGetEndorseSet, "DELETE");
			rMap.put(outLJAPayPersonSet, "INSERT");
			return true;
		}

		// 如果保全类型是新增附加险(不走此财务)
		if ("NS".equals(inEdorType)) {
			return true;
		}

		// 集体下无名单加人。其实不是保全项目，但是流程类似，借用
		if ("GANAME".equals(inEdorType)) {
			// 将前面保存的个人险种保单签单标记为4的改为5(得到该对象的引用，直接更改)
			LCPolSchema saveLCPolSchema = (LCPolSchema) tPolData
					.getObjectByObjectName("LCPolSchema", 0);
			saveLCPolSchema.setAppFlag("1");

			LCDutySet txLCDutySet = (LCDutySet) tPolData.getObjectByObjectName(
					"LCDutySet", 0);
			LCPremSet txLCPremSet = (LCPremSet) tPolData.getObjectByObjectName(
					"LCPremSet", 0);

			VData tVData = new VData();
			GrpAddNameSignBL tGrpAddNameSignBL = new GrpAddNameSignBL();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("NewPolNo", saveLCPolSchema
					.getPolNo());
			tTransferData.setNameAndValue("NewPrem", saveLCPolSchema
					.getStandPrem());
			tTransferData.setNameAndValue("SerialNo", mSerialNo);
			tTransferData.setNameAndValue("OldPolNo", saveLCPolSchema
					.getMasterPolNo());
			tTransferData.setNameAndValue("ProposalNo", saveLCPolSchema
					.getProposalNo());
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			tVData.add(saveLCPolSchema);
			tVData.add(txLCDutySet);
			tVData.add(txLCPremSet);

			if (tGrpAddNameSignBL.submitData(tVData, "QUERY") == false) {
				this.mErrors.copyAllErrors(tGrpAddNameSignBL.mErrors);

				CError tError = new CError();
				tError.moduleName = "ProposalSignBL";
				tError.functionName = "preparePol";
				tError.errorMessage = "无名单加人数据处理失败!";
				this.mErrors.addOneError(tError);

				return false;
			}

			tVData = tGrpAddNameSignBL.getResult();
			tBQTransferData.setNameAndValue("GANAME", tVData);
			mNotCreatInsuaccFlag = true;
		}

		return true;
	}

	/**
	 * 发送补费通知书
	 * 
	 * @param tLCPolSet
	 * @return boolean
	 */
	private boolean sendBFNotice(LCPolSet tLCPolSet) {

		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		// 查询是否已经有补费通知书已经发送或已经打印没有回收，如果有通知书没有回收则不会再次发送
		String tSQL = "select * from loprtmanager where code='BF00' and otherno='"
				+ "?otherno?" + "'";
		sqlbv8.sql(tSQL);
		sqlbv8.put("otherno", tLCPolSet.get(1).getPrtNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv8);

		if (tSSRS.getMaxRow() > 0) {
			return true;
		}
		double sumprem = 0;
		for (int i = 0; i < tLCPolSet.size(); i++) {
			sumprem += tLCPolSet.get(i + 1).getPrem();

		}

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tLOPRTManagerSchema.setOtherNo(tLCPolSet.get(1).getPrtNo());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerSchema.setCode("BF00"); // 打印类型
		tLOPRTManagerSchema.setManageCom(tLCPolSet.get(1).getManageCom()); // 管理机构
		tLOPRTManagerSchema.setAgentCode(tLCPolSet.get(1).getAgentCode()); // 代理人编码
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		tLOPRTManagerSchema.setStateFlag("0");
		tLOPRTManagerSchema.setPatchFlag("0");
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		tLOPRTManagerSchema.setForMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setRemark("");
		tLOPRTManagerSchema.setOldPrtSeq(tPrtSeq);
		tLOPRTManagerSchema.setStandbyFlag1("" + sumprem);

		MMap tMMap = new MMap();
		tMMap.put(tLOPRTManagerSchema, "INSERT");

		VData tVData = new VData();
		tVData.add(tMMap);

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			return false;
		}
		return true;
	}

}
