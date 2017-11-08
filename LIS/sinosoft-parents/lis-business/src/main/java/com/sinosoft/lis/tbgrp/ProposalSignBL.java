/*
 * @(#)ProposalSignBL.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bqgrp.EdorSignBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCOutManagePayDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAccPayDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.CManageFee;
import com.sinosoft.lis.pubfun.DealAccount;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCOutManagePaySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMRiskAccPaySet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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
	/** 存放结果 */
	public VData mVResult = new VData();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;
	private FDate fDate = new FDate();

	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 个人投保单表 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCBnfSet mLCBnfSet = new LCBnfSet();

	/** 实收总表 */
	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	/** 实收总表--存储用 */
	private LJAPaySet mSaveLJAPaySet = new LJAPaySet();

	/** 旧主险保单号 */
	private String mOldMainPolNo = "";

	/** 系统变量表 */
	LDSysVarSchema mLDSysVarSchema = null;
	/** 变量拷贝 */
	Reflections rf = new Reflections();
	/** 险种承保描述表 */
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
	private boolean mInsuredAppAgeChangeFalg = false;
	private boolean alreadyChangeCValiDate = false; // 是否已经更改过生效日期
	private boolean mBQFlag = false; // 保全标记
	private boolean mNotCreatInsuaccFlag = false; // 不生成帐户的标记
	private String DELETE = "DELETE";
	private String INSERT = "INSERT";
	private String UPDATE = "UPDATE";
	private String poltype = "0"; // yaory
	private String mUsePubAcc = "N";
	private String mNewContNo = null; // 新的合同号
	private String mPayType = "";
	private String mPayTypeleft = "";
	private String mMoneyType = "";

	private TransferData mBQData;
	MMap rMap = new MMap();

	private VData mBufferedPolData = new VData();
	private String mNewMainPolNo;

	public VData getResult() {
		return this.mInputData;
	}

	public VData getBufferedPolData() {
		return this.mBufferedPolData;
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

			// 下面dealOnePol函数被集体签单处调用
			VData mapPol = this.dealOnePolData(tLCPolSchema);

			if (mapPol != null) {
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

			} else {
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

		for (int j = 1; j <= mLCPolSet.size(); j++) {
			if (!mLCPolSet.get(j).getMainPolNo().equals(
					mLCPolSet.get(j).getPolNo())) {
				mLCPolSet.get(j).setMainPolNo(mNewMainPolNo);
			}

		}
		// 增加受益人处理
		for (int j = 1; j <= mLCPolSet.size(); j++) {
			String sqlbnf = "select * from lcbnf where polno='"
					+ mLCPolSet.get(j).getPolNo() + "'";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbnf);

			// &&mLCPolSet.get(j).getPolTypeFlag().equals("0")
			if (tSSRS.MaxRow == 0
					&& (mLCPolSet.get(j).getPolTypeFlag() == null || mLCPolSet
							.get(j).getPolTypeFlag().equals("0"))) {
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				tLCBnfSchema.setContNo(mLCPolSet.get(j).getContNo());
				tLCBnfSchema.setPolNo(mLCPolSet.get(j).getPolNo());
				tLCBnfSchema.setInsuredNo(mLCPolSet.get(j).getInsuredNo());
				tLCBnfSchema.setCustomerNo(mLCPolSet.get(j).getInsuredNo());
				tLCBnfSchema.setName("法定");
				tLCBnfSchema.setBirthday(mLCPolSet.get(j).getInsuredBirthday());
				tLCBnfSchema.setSex(mLCPolSet.get(j).getInsuredSex());
				tLCBnfSchema.setBnfType("1");
				tLCBnfSchema.setBnfGrade("1");
				tLCBnfSchema.setBnfLot(1);
				tLCBnfSchema.setBnfNo(1);
				tLCBnfSchema.setRelationToInsured("");
				tLCBnfSchema.setOperator(mGlobalInput.Operator);
				tLCBnfSchema.setMakeDate(PubFun.getCurrentDate());
				tLCBnfSchema.setMakeTime(PubFun.getCurrentTime());
				tLCBnfSchema.setModifyDate(PubFun.getCurrentDate());
				tLCBnfSchema.setModifyTime(PubFun.getCurrentTime());
				mLCBnfSet.add(tLCBnfSchema);
			}

		}
		if (mLCBnfSet != null && mLCBnfSet.size() > 0) {
			rMap.put(mLCBnfSet, "INSERT");
		}
		mInputData.add(mLCPolSchema);
		mInputData.add(rMap);
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

		// 签单校验
		if (this.checkOnePol(tLCPolSchema) == false) {
			return null;
		}

		// 准备公用信息-生成险种保单号

		String tNewPolNo = tLCPolSchema.getPolNo();

		logger.debug(tNewPolNo);
		if (tNewPolNo == null) {
			return null;
		}

		// 准备一个险种保单的签单数据
		VData tPolDetail = this.getOnePol(tLCPolSchema, tNewPolNo);

		if (tPolDetail == null) {
			return null;
		}

		// (保全财务部分，承保部分财务不在这里处理) ---该部分会更改tPolDetail
		if (mBQFlag) {
			String oldPolNo = tLCPolSchema.getProposalNo();
			if (verifyFinance(tPolDetail, tLCPolSchema, tNewPolNo, mBQData) == false) {
				return null;
			}
			String inEdorNo = (String) mBQData.getValueByName("EdorNo");
			String inEdorType = (String) mBQData.getValueByName("EdorType");

		}

		// 处理帐户
		// 在getOnePol函数中，如果因为签单日期追溯部分的重新计算，此时tLCPolSchema的保单号就会是投保单号
		// if (mNotCreatInsuaccFlag == false) {目前只有139险种，处理方式是这样的，只收团单帐户
		if (mNotCreatInsuaccFlag == false) {
			tLCPolSchema.setPolNo(tNewPolNo);
			// 长险开始上线，设计：139走以前的处理，其余得走自己新写得方法。
			String tSql = "select nvl(risktype6,0) from lmriskapp where riskcode='"
					+ tLCPolSchema.getRiskCode() + "' ";
			ExeSQL ttExeSQL = new ExeSQL();
			String tRiskType6 = ttExeSQL.getOneValue(tSql); // 判断险种的类型

			if (tRiskType6.equals("1")) { // 139 151
				if (DealAccount(tPolDetail, tLCPolSchema, tLMRiskSchema) == false) {
					return null;
				}
			} else {
				// 新写的方法处理帐户。以前的帐户处理效率不是很好，难维护。
				if (NewDealAccount(tPolDetail, tLCPolSchema, tLMRiskSchema) == false) {
					return null;
				}

			}
		}

		// 把准备好的数据放到结果集中
		// ///*******************增加139的处理 在最后做处理，现在已经可以保证帐户的正确性啦
		tLCPolSchema = (LCPolSchema) tPolDetail.getObjectByObjectName(
				"LCPolSchema", 0);

		String contno = tLCPolSchema.getPrtNo();
		LCOutManagePayDB tOutManagePayDB = new LCOutManagePayDB();
		LCOutManagePaySet tOutManagePaySet = new LCOutManagePaySet();
		String sql = "select * from LCOutManagePay where grpcontno='" + contno
				+ "' and grppolno='000000'";
		tOutManagePaySet = tOutManagePayDB.executeQuery(sql);
		String outpayflag = "0";
		double outpaymoney = 0;
		if (tOutManagePaySet != null && tOutManagePaySet.size() > 0) {
			outpayflag = "1";
			outpaymoney = tOutManagePaySet.get(1).getPrem();
			logger.debug(outpaymoney);
		}
		// 增加对法人保费的修改。
		String tPublicFeeFlag = "0";
		double tPublicFee = 0;
		sql = "select * from LCOutManagePay where grpcontno='" + contno
				+ "' and grppolno='000001'";
		LCOutManagePaySet ttOutManagePaySet = new LCOutManagePaySet();
		ttOutManagePaySet = tOutManagePayDB.executeQuery(sql);
		if (ttOutManagePaySet != null && ttOutManagePaySet.size() > 0) {
			tPublicFeeFlag = "1";
			tPublicFee = ttOutManagePaySet.get(1).getPrem();
		}
		// **************************end
		tmpMap.add((MMap) tPolDetail.getObjectByObjectName("MMap", 0));
		// 还要增加一条就是这个险种与帐户相关

		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());

		if (tLMRiskDB.getInfo() == false) {
			CError.buildErr(this, "查询险种描述表失败！");
		}

		LMRiskSchema inLMRiskSchema = tLMRiskDB.getSchema();
		// 还要增加一个条件是这个险种是公共帐户
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		String tsql = "select * From lccont where contno in (select contno from lcpol where polno='"
				+ tLCPolSchema.getPolNo() + "')";
		logger.debug(tsql);
		tLCContSet = tLCContDB.executeQuery(tsql);
		String poltype = "0";
		if (tLCContSet != null && tLCContSet.size() > 0) {
			if (tLCContSet.get(1).getPolType() == null
					|| tLCContSet.get(1).getPolType().equals("")) {

			} else {
				poltype = tLCContSet.get(1).getPolType();
			}
		}
		if (outpayflag.equals("1")
				&& inLMRiskSchema.getInsuAccFlag().equals("Y")
				&& poltype.equals("2")) {
			// 如果是帐户的，而且是外缴的做处理
			LCPremSet mLCPremSet = (LCPremSet) tPolDetail
					.getObjectByObjectName("LCPremSet", 0);
			LCDutySet mLCDutySet = (LCDutySet) tPolDetail
					.getObjectByObjectName("LCDutySet", 0);
			mLCPremSet.get(1)
					.setPrem(mLCPremSet.get(1).getPrem() + outpaymoney);
			mLCDutySet.get(1)
					.setPrem(mLCDutySet.get(1).getPrem() + outpaymoney);
			mLCPremSet.get(1).setSumPrem(mLCPremSet.get(1).getPrem());
			mLCDutySet.get(1).setSumPrem(mLCDutySet.get(1).getPrem());

			tmpMap.put(mLCPremSet, "DELETE&INSERT");
			// 增加个人财务处理
			if (!mBQFlag) {
				LJAPayPersonSet tLJAPayPersonSet = this.prepareLJAPayPerson(
						tLCPolSchema, mLCPremSet);
				tmpMap.put(tLJAPayPersonSet, "INSERT");
			}

			tmpMap.put(mLCDutySet, "DELETE&INSERT");
			tLCPolSchema.setPrem(tLCPolSchema.getPrem() + outpaymoney);
			tLCPolSchema.setSumPrem(tLCPolSchema.getPrem());
		} else if (tPublicFeeFlag.equals("1")
				&& inLMRiskSchema.getInsuAccFlag().equals("Y")
				&& poltype.equals("3")) {
			LCPremSet mLCPremSet = (LCPremSet) tPolDetail
					.getObjectByObjectName("LCPremSet", 0);
			LCDutySet mLCDutySet = (LCDutySet) tPolDetail
					.getObjectByObjectName("LCDutySet", 0);
			mLCPremSet.get(1).setPrem(mLCPremSet.get(1).getPrem() + tPublicFee);
			mLCDutySet.get(1).setPrem(mLCDutySet.get(1).getPrem() + tPublicFee);
			mLCPremSet.get(1).setSumPrem(mLCPremSet.get(1).getPrem());
			mLCDutySet.get(1).setSumPrem(mLCDutySet.get(1).getPrem());

			tmpMap.put(mLCPremSet, "DELETE&INSERT");
			if (!mBQFlag) {
				LJAPayPersonSet tLJAPayPersonSet = this.prepareLJAPayPerson(
						tLCPolSchema, mLCPremSet);
				tmpMap.put(tLJAPayPersonSet, "INSERT");
			}

			tmpMap.put(mLCDutySet, "DELETE&INSERT");
			tLCPolSchema.setPrem(tLCPolSchema.getPrem() + tPublicFee);
			tLCPolSchema.setSumPrem(tLCPolSchema.getPrem());

		} else {

			tmpMap.put((LCPremSet) tPolDetail.getObjectByObjectName(
					"LCPremSet", 0), this.INSERT);
			if (!mBQFlag) {
				LJAPayPersonSet tLJAPayPersonSet = this.prepareLJAPayPerson(
						tLCPolSchema, (LCPremSet) tPolDetail
								.getObjectByObjectName("LCPremSet", 0));
				tmpMap.put(tLJAPayPersonSet, "INSERT");
			}

			tmpMap.put((LCDutySet) tPolDetail.getObjectByObjectName(
					"LCDutySet", 0), this.INSERT);
		}
		tmpMap.put((LCGetSet) tPolDetail.getObjectByObjectName("LCGetSet", 0),
				"DELETE&INSERT");

		tLCPolSchema.setAppFlag("1");
		tLCPolSchema.setSignDate(PubFun.getCurrentDate());
		tLCPolSchema.setSignTime(PubFun.getCurrentTime());//add by liuqh 2008-09-22

		tReturn.add(tmpMap);
		//

		tReturn.add(tLCPolSchema);
		tReturn.add(tPolDetail.getObjectByObjectName("LCPremSet", 0));

		// //同时把数据缓冲起来，待外头调用
		VData bufferData = new VData();
		bufferData.add((LCPolSchema) tPolDetail.getObjectByObjectName(
				"LCPolSchema", 0));
		bufferData.add(tPolDetail.getObjectByObjectName("LCPremSet", 0));
		this.mBufferedPolData.add(bufferData);

		return tReturn;

	}

	/**
	 * 处理一张投保单以及其关联的附险投保单的数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private VData getOnePol(LCPolSchema tLCPolSchema, String tNewPolNo) {
		VData tReturn = new VData();
		MMap tmpMap = new MMap();
		// tmpMap.add(this.getElseUpdateTableSql(tLCPolSchema.getPolNo(),
		// mNewContNo, tNewPolNo));
		// 162险种，且只有一个责任 ,把它补全add by wanglei
		// 取出原来的保费项
		LCPremSet tLCPremSet = this.getOldPrem(tLCPolSchema, tNewPolNo);

		if (tLCPremSet == null) {
			return null;
		}
		if (tLCPolSchema.getRiskCode().equals("162")
				&& tLCPremSet.size() == 1
				&& (tLCPremSet.get(1).getDutyCode().equals("162001") || tLCPremSet
						.get(1).getDutyCode().equals("162002"))) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			rf.transFields(tLCPremSchema, tLCPremSet.get(1));
			if (tLCPremSet.get(1).getDutyCode().equals("162001")) {
				tLCPremSchema.setDutyCode("162002");
				tLCPremSchema.setPayPlanCode("162102");
			} else {
				tLCPremSchema.setDutyCode("162001");
				tLCPremSchema.setPayPlanCode("162101");
			}
			tLCPremSchema.setPrem(0);
			tLCPremSchema.setSumPrem(0);
			tLCPremSchema.setStandPrem(0);
			tLCPremSet.add(tLCPremSchema);
		}
		tmpMap.put((LCPremSet) PubFun.copySchemaSet(tLCPremSet), "DELETE");

		// 取出原来的领取项
		LCGetSet tLCGetSet = this.getOldGet(tLCPolSchema, tNewPolNo);
		if (tLCGetSet == null) {
			return null;
		}
		// 取出原来的责任
		LCDutySet tLCDutySet = this.getOldDuty(tLCPolSchema, tNewPolNo);
		if (tLCDutySet == null) {
			return null;
		}

		if (tLCPolSchema.getRiskCode().equals("162")
				&& tLCDutySet.size() == 1
				&& (tLCDutySet.get(1).getDutyCode().equals("162001") || tLCDutySet
						.get(1).getDutyCode().equals("162002"))) {
			LCGetSet ttLCGetSet = (LCGetSet) PubFun.copySchemaSet(tLCGetSet);

			for (int i = 1; i <= ttLCGetSet.size(); i++) {
				if (tLCDutySet.get(1).getDutyCode().equals("162001")) {
					ttLCGetSet.get(i).setDutyCode("162002");
					if (ttLCGetSet.get(i).getGetDutyCode().equals("162204")) {
						ttLCGetSet.get(i).setGetDutyCode("162208");
					} else {
						ttLCGetSet.get(i).setGetDutyCode("162209");
					}

				} else {
					ttLCGetSet.get(i).setDutyCode("162001");
					if (ttLCGetSet.get(i).getGetDutyCode().equals("162208")) {
						ttLCGetSet.get(i).setGetDutyCode("162204");
					} else {
						ttLCGetSet.get(i).setGetDutyCode("162205");
					}

				}
				ttLCGetSet.get(i).setActuGet(0);
				ttLCGetSet.get(i).setStandMoney(0);
				ttLCGetSet.get(i).setSumMoney(0);

			}
			tLCGetSet.add(ttLCGetSet);
		}

		tmpMap.put((LCGetSet) PubFun.copySchemaSet(tLCGetSet), "DELETE");

		if (tLCPolSchema.getRiskCode().equals("162")
				&& tLCDutySet.size() == 1
				&& (tLCDutySet.get(1).getDutyCode().equals("162001") || tLCDutySet
						.get(1).getDutyCode().equals("162002"))) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			rf.transFields(tLCDutySchema, tLCDutySet.get(1));
			if (tLCDutySet.get(1).getDutyCode().equals("162001")) {
				tLCDutySchema.setDutyCode("162002");
			} else {
				tLCDutySchema.setDutyCode("162001");
			}
			tLCDutySchema.setPrem(0);
			tLCDutySchema.setSumPrem(0);
			tLCDutySchema.setStandPrem(0);
			tLCDutySchema.setAmnt(0);
			tLCDutySchema.setRiskAmnt(0);
			tLCDutySet.add(tLCDutySchema);
		}

		tmpMap.put((LCDutySet) PubFun.copySchemaSet(tLCDutySet), "DELETE");

		// 如果是需要生效日期追溯
		if (this.needReCal(tLCPolSchema) == true) {
			// 改变保单生效日期
			tLCPolSchema.setCValiDate(fDate.getString(mValiDate));
			// logger.debug(mValiDate);

			// 签单日期追溯部分的重新计算（涉及领取项、保费项、责任和保单部分）
			VData tReCal = this.reCal(tLCPolSchema, tLCGetSet, tLCDutySet,
					tLCPremSet);

			if (tReCal == null) {
				return null;
			}

			tLCPremSet.set((LCPremSet) tReCal.get(0));
			tLCGetSet.set((LCGetSet) tReCal.get(1));
			tLCDutySet.set((LCDutySet) tReCal.get(2));
			tLCPolSchema = (LCPolSchema) tReCal.get(3);
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

		if (mInsuredAppAgeChangeFalg == true) {
			return null;
		}

		// 保费项
		tLCPremSet = this.preparePrem(tLCPolSchema, tLCPremSet, tNewPolNo);

		if (tLCPremSet == null) {
			return null;
		}
		// tmpMap .put(tLCPremSet , "INSERT");
		tReturn.add(tLCPremSet);

		// 领取项
		tLCGetSet = this.prepareGet(tLCPolSchema, tLCGetSet, tNewPolNo);

		if (tLCGetSet == null) {
			return null;
		}

		tReturn.add(tLCGetSet);

		// 责任
		tLCDutySet = this.prepareDuty(tLCPolSchema, tLCDutySet, tNewPolNo,
				tLCPremSet);

		if (tLCDutySet == null) {
			return null;
		}
		tReturn.add(tLCDutySet);

		// 险种保单（需要处理的投保单）
		LCPolSchema tLCPolMain = this.preparePol(tLCPolSchema, tNewPolNo,
				tLCDutySet);

		if (tLCPolMain == null) {
			return null;
		}

		tReturn.add(tLCPolMain);

		tReturn.add(tmpMap);
		return tReturn;
	}

	private LJAPayPersonSet prepareLJAPayPerson(LCPolSchema tLCPolSchema,
			LCPremSet tLCPremSet) {
		LCPremSchema tLCPremSchema;
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJAPayPersonSchema tLJAPayPersonSchema;
		String tRiskcode;
		String tSql;
		LCGrpPolSet tLCGrpPolSet;
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tRiskcode = tLCPolSchema.getRiskCode();
		 //健康险
        LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
        LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
        mLMRiskAppDB.setRiskType("H");
        mLMRiskAppDB.setHealthType("1");
        mLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
        mLMRiskAppSet = mLMRiskAppDB.query();
        if(mLMRiskAppSet.size()>0){
            mPayType = "TM";
            mPayTypeleft = "YETTM";
            mMoneyType = "TM";
        }
        else{
            mPayType = "ZC";
            mPayTypeleft = "YET";
        }
		// logger.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~tRiskcode"+tRiskcode);
		for (int k = 1; k <= tLCPremSet.size(); k++) {
			tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(k);
			tLJAPayPersonSchema = new LJAPayPersonSchema();
			tLJAPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJAPayPersonSchema.setPayCount(1);
			tLJAPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tSql = "select * From lcgrppol where grpcontno='"
					+ tLCPolSchema.getGrpContNo() + "' and riskcode='"
					+ tRiskcode + "'";
			tLCGrpPolSet = new LCGrpPolSet();
			tLCGrpPolSet = tLCGrpPolDB.executeQuery(tSql);
			tLJAPayPersonSchema.setGrpPolNo(tLCGrpPolSet.get(1).getGrpPolNo());
			tLJAPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
			tLJAPayPersonSchema.setAgentType(tLCPolSchema.getAgentType());
			tLJAPayPersonSchema.setRiskCode(tRiskcode);
			tLJAPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLJAPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLJAPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLJAPayPersonSchema.setPayNo(tLCPolSchema.getPolNo()); // 临时放的数据，签单主程序要进行修改的。
			tLJAPayPersonSchema.setPayAimClass("1");
			tLJAPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
			tLJAPayPersonSchema.setPayDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setPayType(mPayType);
			tLJAPayPersonSchema.setEnterAccDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setConfDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setCurPayToDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setLastPayToDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setApproveCode(tLCPolSchema.getApproveCode());
			tLJAPayPersonSchema.setApproveDate(tLCPolSchema.getApproveDate());
			tLJAPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
			tLJAPayPersonSchema.setOperator(tLCPolSchema.getOperator());
			tLJAPayPersonSet.add(tLJAPayPersonSchema);

		}
		return tLJAPayPersonSet;
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
		// logger.debug("newContNo :" + mNewContNo);
		mBQData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mBQData != null) {
			poltype = (String) mBQData.getValueByName("poltype");
			if (poltype == null) {
				poltype = "0";
			}
			logger.debug("保单类型：" + poltype);
		}
		// mUsePubAcc
		if (mBQData != null) {
			mUsePubAcc = (String) mBQData.getValueByName("MFee");
			if (mUsePubAcc == null || mUsePubAcc.equals("")) {
				mUsePubAcc = "N";
			}
			logger.debug("账户处理：" + mUsePubAcc);
		}

		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			mLCPolSet.set(tLCPolSet);
		} else {
			LCPolSchema tSchema = (LCPolSchema) mInputData
					.getObjectByObjectName("LCPolSchema", 0);
			if (tSchema != null) {
				mLCPolSet.add(tSchema);
			}
		}

		if (mLCPolSet.size() <= 0) {
			return false;
		}
		return true;

	}

	/**
	 * 校验数据是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkOnePol(LCPolSchema tLCPolSchema) {

		// 检验代理人是否已经离职
		// ExeSQL tExeSQL = new ExeSQL();
		// String state = tExeSQL.getOneValue(
		// "SELECT agentstate FROM laagent where agentcode='" +
		// tLCPolSchema.getAgentCode() + "'");
		// //jixf 20051228 保全增人就不在判断代理人状态了
		// if (tLCPolSchema.getAppFlag().equals("2"))
		// {
		// return true;
		// }
		// if ((state == null) || state.equals("")) {
		// CError.buildErr(this, "无法查到该代理人的状态!");
		// return false;
		// }
		// if (Integer.parseInt(state) >= 3) {
		// CError.buildErr(this, "该代理人已经离职，不能签单!");
		// return false;
		// }

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
			CError.buildErr(this, "险种保单号生成失败!");
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
			CError.buildErr(this, "LCPrem表取数失败!");
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
				// logger.debug(paytoDate);
				// logger.debug(baseDate);
				logger.debug(interval);
			}

			tLCPremSchema.setPayTimes(1);
			tLCPremSchema.setContNo(mNewContNo);
			tLCPremSchema.setPaytoDate(fDate.getString(paytoDate));
			logger.debug(fDate.getString(paytoDate));
			tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			tLCPremSchema.setOperator(mGlobalInput.Operator);
			// tLCPremSchema.setMakeDate( );
			// 集体投保单

			tLCPremSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCPremSchema.setMakeDate(tLCPolSchema.getMakeDate());
			tLCPremSchema.setMakeTime(tLCPolSchema.getMakeTime());
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
			CError.buildErr(this, "LCGet表取数失败!");
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
			CError.buildErr(this, "LCDuty表取数失败!");
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
								maxPaytoDate)) {
					maxPaytoDate = fDate.getDate(tLCPremSchema.getPaytoDate());
					logger.debug("maxPaytoDate1:" + maxPaytoDate);
				}
			}

			// logger.debug("maxPaytoDate2:" + maxPaytoDate);
			tLCDutySchema.setPaytoDate(maxPaytoDate);
			logger.debug(maxPaytoDate);
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
	 * 准备险种保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private LCPolSchema preparePol(LCPolSchema tLCPolSchema, String tNewPolNo,
			LCDutySet tLCDutySet) {

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
		// tLCPolSchema.setAppFlag("1");//wenhuan test
		// tLCPolSchema.setPolState("00019999");
		// //sxy-2003-09-08(polstatus编码规则:前两位描述险种保单状态,后两位描述使险种保单处于该状态的原因.末尾四位描述了前一险种保单状态和处于该状态的原因)
		if (tLCPolSchema.getUWFlag() != null
				&& tLCPolSchema.getUWFlag().equals("1")) {
			tLCPolSchema.setPolState("0");
		} else {
			tLCPolSchema.setPolState("1");
			tLCPolSchema.setUWFlag("9");
		}
		// if(tLCPolSchema.getUWFlag()==null)
		// {
		// tLCPolSchema.setUWFlag("9");
		// tLCPolSchema.setPolState("1");
		// }
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
		tLCPolSchema.setLastRegetDate(fDate.getString(maxPaytoDate));
		logger.debug(maxPaytoDate);
		tLCPolSchema.setPayEndDate(fDate.getString(PayEndDate));
		// 从暂交费信息中取firstpaydate日期信息
		getPaydates(tLCPolSchema);
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
		mInsuredAppAgeChangeFalg = false;
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
		mInsuredAppAgeChangeFalg = false;

		// 如果附险的生效日期随主险，那么直接跳过，不用重新计算
		if (alreadyChangeCValiDate == true) {
			return false;
		}

		if (mPolType.equals("1")) { // 个人单才需要进行追溯
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("SignValiDateBackFlag");
			tLDSysVarDB.getInfo();
			mBack = tLDSysVarDB.getSysVarValue().trim();

			if (mBack.equals("1") || mBack.equals("2") || mBack.equals("3")) {
				if (tLCPolSchema.getSpecifyValiDate() == null) { // 如果为空，置为N
					tLCPolSchema.setSpecifyValiDate("N");
				}

				if ((tLCPolSchema.getSpecifyValiDate() != null)
						&& tLCPolSchema.getSpecifyValiDate().equals("N")) {
					mValiDate = this.calValiDate(tLCPolSchema, mBack);

					int newAge = PubFun.calInterval(fDate.getDate(tLCPolSchema
							.getInsuredBirthday()), mValiDate, "Y");

					if (newAge == tLCPolSchema.getInsuredAppAge()) {
						return true;
					} else {
						mInsuredAppAgeChangeFalg = true;

						CError.buildErr(this, tLCPolSchema.getPolNo()
								+ "号投保单以签单生效日期变更后，则被保险人年龄有变化，不能签单！");

						return false;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 改变险种保单生效日期 输出：LCPolSchema
	 */
	private Date calValiDate(LCPolSchema tLCPolSchema, String flag) {
		Date tValiDate = null;

		// 选择签单次日
		if (flag.equals("1")) {
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
			if (!tLMRiskAppDB.getInfo()) {
				CError.buildErr(this, "查询LMRiskApp表失败!");
				return null;
			}
			int tSignDateCalMode = tLMRiskAppDB.getSignDateCalMode();
			/**
			 * 0--签单当天生效 1--签单次日生效 2--首期交费次日 3--投保次日
			 * 
			 */

			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%tSignDateCalMode=="
					+ tSignDateCalMode);

			if (tSignDateCalMode == 0) {
				tValiDate = fDate.getDate(PubFun.getCurrentDate());
			} else if (tSignDateCalMode == 1) {
				tValiDate = fDate.getDate(PubFun.getCurrentDate());
				tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
			} else if (tSignDateCalMode == 2) {
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tempSSRS = new SSRS();
				String aSQL = "select paymode from ljtempfeeclass where otherno='"
						+ tLCPolSchema.getPrtNo()
						+ "' and operstate='0' order by makedate ,maketime ";
				tempSSRS = tExeSQL.execSQL(aSQL);
				String payMode = "1";
				if (tempSSRS != null && tempSSRS.getMaxRow() > 0) {
					payMode = tempSSRS.GetText(1, 1);
				}

				String sql = " select EnterAccDate from LJTempFee where otherno = '"
						+ tLCPolSchema.getPrtNo()
						+ "'"
						+ " and operstate='0' order by makedate ,maketime ";

				// 如果是支票方式则追溯到交费日
				if (payMode != null && payMode.equals("3")) {
					sql = " select PayDate from LJTempFee where otherno = '"
							+ tLCPolSchema.getPrtNo() + "'"
							+ " and operstate='0' order by makedate ,maketime ";

				}

				tempSSRS = tExeSQL.execSQL(sql);
				logger.debug(tempSSRS.GetText(1, 1));
				if ((tempSSRS.GetText(1, 1).equals("0")
						|| tempSSRS.GetText(1, 1).trim().equals("") || tempSSRS
						.GetText(1, 1).equals("null"))) {
					CError.buildErr(this, "在取得财物缴费日期时发生错误!");

					return null;

				}

				tValiDate = fDate.getDate(tempSSRS.GetText(1, 1));
				tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
			} else if (tSignDateCalMode == 3) {
				tValiDate = fDate.getDate(tLCPolSchema.getPolApplyDate());
				tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
			}

		}

		// 选择交费的到帐日期的最大值
		if (flag.equals("2") || flag.equals("3")) {
			Date maxDate = fDate.getDate("1900-01-01");
			Date minDate = fDate.getDate("3000-01-01");

			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setOtherNo(tLCPolSchema.getProposalNo());
			tLJTempFeeDB.setOtherNoType("0");
			tLJTempFeeDB.setTempFeeType("1");
			tLJTempFeeDB.setConfFlag("0");
			tLJTempFeeDB.setOperState("0");

			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();

			if (tLJTempFeeDB.mErrors.needDealError()) {
				CError.buildErr(this, "财务信息取数失败!");

				return null;
			}

			int n = tLJTempFeeSet.size();

			for (int i = 1; i <= n; i++) {
				LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);

				Date enterAccDate = fDate.getDate(tLJTempFeeSchema
						.getEnterAccDate());

				if (enterAccDate.before(minDate)) {
					minDate = enterAccDate;
				}

				if (enterAccDate.after(maxDate)) {
					maxDate = enterAccDate;
				}
			}

			if (flag.equals("2")) {
				tValiDate = PubFun.calDate(maxDate, 1, "D", null);
			}

			if (flag.equals("3")) {
				tValiDate = PubFun.calDate(minDate, 1, "D", null);
			}
		}
		logger.debug("%%%%%%%%%%%%%%%%");
		logger.debug("%%%%%%%%%%tValiDate==" + tValiDate);
		return tValiDate;
	}

	/**
	 * 重新计算投保单 输出：VData
	 */
	private VData reCal(LCPolSchema tLCPolSchema, LCGetSet tLCGetSet,
			LCDutySet tLCDutySet, LCPremSet tLCPremSet) {
		VData tReturn = new VData();
		LCPolBL tLCPolBL = new LCPolBL();
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		LCGetBLSet tLCGetBLSet = new LCGetBLSet();

		// 把保费项中加费的部分提出来
		String newStart = tLCPolSchema.getCValiDate();
		int premCount = tLCPremSet.size();

		for (int i = 1; i <= premCount; i++) {
			LCPremSchema tLCPremSchema = tLCPremSet.get(i);

			if (!tLCPremSchema.getPayPlanCode().substring(0, 6)
					.equals("000000")) {
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
			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, "");
		} else {
			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, tLCGetBLSet, "");
		}

		if (tCalBL.calPol() == false) {
			CError.buildErr(this, "险种保单重新计算时失败:"
					+ tCalBL.mErrors.getFirstError());

			return null;
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
			addPrem += tLCPremSchema.getPrem();

			for (int j = 1; j <= dutyCount1; j++) {
				LCDutySchema tLCDutySchema = tLCDutySet.get(j);

				if (tLCDutySchema.getDutyCode().equals(
						tLCPremSchema.getDutyCode())) {
					tLCDutySchema.setPrem(tLCPremSchema.getPrem()
							+ tLCDutySchema.getPrem());
				}
			}
		}

		tLCPolSchema.setPrem(tLCPolSchema.getPrem() + addPrem);
		tLCPremSet1.add(tLCPremSet);

		tReturn.add(tLCPremSet1);
		tReturn.add(tLCGetSet);
		tReturn.add(tLCDutySet);
		tReturn.add(tLCPolSchema);

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
	private boolean NewDealAccount(VData PolDetail, LCPolSchema inLCPolSchema,
			LMRiskSchema inLMRiskSchema) {

		if (inLMRiskSchema == null) {
			// 查询险种描述表：是否和帐户相关
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(inLCPolSchema.getRiskCode());

			if (tLMRiskDB.getInfo() == false) {
				CError.buildErr(this, "查询险种描述表失败！");

				return false;
			}

			inLMRiskSchema = tLMRiskDB.getSchema();
		}

		if ((inLMRiskSchema.getInsuAccFlag() == null)
				|| inLMRiskSchema.getInsuAccFlag().equals("N")) {
			return true;
		}

		String Rate = null; // 分配比率置空，待调整
		// 应该从LCGRPFEE中查询出来 20051008
		logger.debug(inLCPolSchema.getGrpContNo());
		logger.debug(inLCPolSchema.getGrpPolNo());
		logger.debug(inLCPolSchema.getRiskCode());
		// LCGrpFeeDB tLCGrpFeeDB=new LCGrpFeeDB();

		LCPremSet tLCPremSet = (LCPremSet) PolDetail.getObjectByObjectName(
				"LCPremSet", 0);
		DealAccount tDealAccount = new DealAccount(this.mGlobalInput);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AccCreatePos", "1"); // 生成位置：缴费时 即签单核销时

		tTransferData.setNameAndValue("Rate", Rate);

		// 注意：此时数据尚未存入数据库,因此，暂时用投保单号代替保单号。
		tTransferData.setNameAndValue("PolNo", inLCPolSchema.getProposalNo());
		// 这两个数值取决于定义
		tTransferData.setNameAndValue("OtherNo", inLCPolSchema.getProposalNo());
		tTransferData.setNameAndValue("OtherNoType", "1"); // 其它号码类型：个人险种保单号
		// 生成帐户结构
		VData tVData = tDealAccount.createInsureAccForBat(tTransferData,
				inLCPolSchema, inLMRiskSchema);

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
		;
		VData feeData = null;
		// 创建管理费结构
		String tSql = "select nvl(risktype6,0) from lmriskapp where riskcode='"
				+ inLCPolSchema.getRiskCode() + "' ";
		ExeSQL ttExeSQL = new ExeSQL();
		String tRiskType6 = ttExeSQL.getOneValue(tSql); // 判断险种的类型

		if (tRiskType6.equals("1")) { // 139 151
			feeData = tDealAccount.getManageFeeStru(inLCPolSchema,
					tLCPremToAccSet, tmpLCInsureAccSet);
		} else {
			feeData = tDealAccount.getNewManageFeeStru(inLCPolSchema,
					tLCPremToAccSet, tmpLCInsureAccSet);

		}
		if (feeData != null) {
			tLCInsureAccFeeSet = (LCInsureAccFeeSet) feeData
					.getObjectByObjectName("LCInsureAccFeeSet", 0);
			tLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) feeData
					.getObjectByObjectName("LCInsureAccClassFeeSet", 0);

		}
		tVData.add(tLCInsureAccFeeSet);
		tVData.add(tLCInsureAccClassFeeSet);

		// 计算管理费和账户注入资金
		tVData.add(tLCPremSet);
		tVData.add(inLCPolSchema);

		CManageFee cmanageFee = new CManageFee();
		boolean cal = true;
		if (tRiskType6.equals("1")) { // 139 151
			cal = cmanageFee.calPremManaFee(tVData, "1", inLCPolSchema
					.getPolNo(), "1", "BF");
		} else {
			cal = cmanageFee.NewcalPremManaFee(tVData, "1", inLCPolSchema
					.getPolNo(), "1", "BF", mUsePubAcc);

		}
		if (!cal || cmanageFee.mErrors.needDealError()) {
			if (cmanageFee.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(cmanageFee.mErrors);
			} else {
				CError.buildErr(this, "计算账户金额或管理费时出错");
			}
			return false;
		}

		MMap tmpMap = (MMap) PolDetail.getObjectByObjectName("MMap", 0);
		// 增加归属规则处理。LCInsureAccTrace ,LCInsureAccClass .
		// added by wanglei 用于删除多余账户
		LCInsureAccSet ttLCInsureAccSet = new LCInsureAccSet();
		for (int k = 1; k <= tLCInsureAccSet.size(); k++) {
			int n = 0;
			for (int q = 1; q <= tLCInsureAccClassSet.size(); q++) {
				if (tLCInsureAccClassSet.get(q).getInsuAccNo().equals(
						tLCInsureAccSet.get(k).getInsuAccNo())) {
					n++;
				}
			}
			if (n > 0) {
				ttLCInsureAccSet.add(tLCInsureAccSet.get(k));
			}
			n = 0;
		}
		tmpMap.put(tLCInsureAccSet, "DELETE");
		tmpMap.put(ttLCInsureAccSet, this.INSERT);
		// end by wanglei
		tmpMap.put(tLCPremToAccSet, this.INSERT);
		tmpMap.put(tLCGetToAccSet, this.INSERT);
		LMRiskAccPayDB tLMRiskAccPayDB = new LMRiskAccPayDB();
		for (int m = 1; m <= tLCInsureAccClassSet.size(); m++) {
			//
			// LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
			// tLMRiskAccPayDB.setPayPlanCode(tLCInsureAccClassSet.get(m).
			// getPayPlanCode());
			// tLMRiskAccPaySet = tLMRiskAccPayDB.query();
			// //select ascriptionrulecode from lcduty where polno='260101020403' and
			// dutycode=(select dutycode From lmdutypayrela where payplancode='162104')
			// if (tLMRiskAccPaySet.get(1).getascription().equals("0")) {
			// //判断是否已经设置归属规则，如果没有设置归属规则还是要设置为1
			//tongmeng 2009-10-21 modify
			//所有归属规则设置为1  已归属
			tLCInsureAccClassSet.get(m).setAccAscription("1");
			/*
			ExeSQL ppExeSQL = new ExeSQL();
			SSRS ppSSRS = new SSRS();
			String ppsql = "select ascriptionrulecode from lcduty where polno='"
					+ tLCInsureAccClassSet.get(m).getPolNo()
					+ "' and dutycode=(select dutycode From lmdutypayrela  where payplancode='"
					+ tLCInsureAccClassSet.get(m).getPayPlanCode() + "')";
			ppSSRS = ppExeSQL.execSQL(ppsql);
			if (ppSSRS.MaxRow > 0) {
				if (ppSSRS.GetText(1, 1) != null
						&& !ppSSRS.GetText(1, 1).equals("")) {
					tLCInsureAccClassSet.get(m).setAccAscription("0");
				} else {
					tLCInsureAccClassSet.get(m).setAccAscription("1");
				}
			} else {
				tLCInsureAccClassSet.get(m).setAccAscription("1");
				// }
				// } else {
				// tLCInsureAccClassSet.get(m).setAccAscription(tLMRiskAccPaySet.
				// get(1).
				// getascription());
			}
			*/
		}
		// ***********************增加轨迹
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema;
		String tLimit = "";
		tLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

		for (int n = 1; n <= tLCInsureAccClassFeeSet.size(); n++) {

			LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
			tLMRiskAccPayDB.setPayPlanCode(tLCInsureAccClassFeeSet.get(n)
					.getPayPlanCode());
			tLMRiskAccPaySet = tLMRiskAccPayDB.query();
			// if (tLMRiskAccPaySet.get(1).getascription().equals("0")) {
			// //判断是否已经设置归属规则，如果没有设置归属规则还是要设置为1
			//tongmeng 2009-10-21 modify
			//所有归属规则设置为1  已归属
			tLCInsureAccClassFeeSet.get(n).setAccAscription("1");
			/*
			ExeSQL ppExeSQL = new ExeSQL();
			SSRS ppSSRS = new SSRS();
			String ppsql = "select ascriptionrulecode from lcduty where polno='"
					+ tLCInsureAccClassSet.get(n).getPolNo()
					+ "' and dutycode=(select dutycode From lmdutypayrela  where payplancode='"
					+ tLCInsureAccClassSet.get(n).getPayPlanCode() + "')";
			ppSSRS = ppExeSQL.execSQL(ppsql);
			if (ppSSRS.MaxRow > 0) {
				if (ppSSRS.GetText(1, 1) != null
						&& !ppSSRS.GetText(1, 1).equals("")) {
					tLCInsureAccClassFeeSet.get(n).setAccAscription("0");
				} else {
					tLCInsureAccClassFeeSet.get(n).setAccAscription("1");
				}
			} else {
				tLCInsureAccClassFeeSet.get(n).setAccAscription("1");
			}
			*/
			// } else {
			// tLCInsureAccClassFeeSet.get(n).setAccAscription(
			// tLMRiskAccPaySet.get(1).
			// getascription());
			// }

			// ***************************
			String temSerialno = PubFun1.CreateMaxNo("SerialNo", tLimit);
			tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
			rf.transFields(tLCInsureAccFeeTraceSchema, tLCInsureAccClassFeeSet
					.get(n));
			tLCInsureAccFeeTraceSchema.setSerialNo(temSerialno);
			tLCInsureAccFeeTraceSchema.setMoneyType("GL");
			tLCInsureAccFeeTraceSchema.setInerSerialNo("1");
			tLCInsureAccFeeTraceSchema.setState("0");
			// inLCPolSchema
			tLCInsureAccFeeTraceSchema.setPayDate(inLCPolSchema.getCValiDate());
			tLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);

		}
		for (int k = 1; k <= tLCInsureAccTraceSet.size(); k++) {
			LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
			tLMRiskAccPayDB.setPayPlanCode(tLCInsureAccTraceSet.get(k)
					.getPayPlanCode());
			tLMRiskAccPaySet = tLMRiskAccPayDB.query();
			// if (tLMRiskAccPaySet.get(1).getascription().equals("0")) {
			// //判断是否已经设置归属规则，如果没有设置归属规则还是要设置为1
			//tongmeng 2009-10-21 modify
			//所有设置为1,已归属
			tLCInsureAccTraceSet.get(k).setAccAscription("1");
			/*
			ExeSQL ooExeSQL = new ExeSQL();
			SSRS ooSSRS = new SSRS();
			String oosql = "select ascriptionrulecode from lcduty where polno='"
					+ tLCInsureAccTraceSet.get(k).getPolNo()
					+ "' and dutycode=(select dutycode From lmdutypayrela  where payplancode='"
					+ tLCInsureAccTraceSet.get(k).getPayPlanCode() + "')";
			ooSSRS = ooExeSQL.execSQL(oosql);
			if (ooSSRS.MaxRow > 0) {
				if (ooSSRS.GetText(1, 1) != null
						&& !ooSSRS.GetText(1, 1).equals("")) {
					tLCInsureAccTraceSet.get(k).setAccAscription("0");
				} else {
					tLCInsureAccTraceSet.get(k).setAccAscription("1");
				}
			} else {
				tLCInsureAccTraceSet.get(k).setAccAscription("1");
			}
			*/
			// } else {
			// tLCInsureAccTraceSet.get(k).setAccAscription(tLMRiskAccPaySet.
			// get(1).
			// getascription());
			// }
			// inLCPolSchema
			tLCInsureAccTraceSet.get(k)
					.setPayDate(inLCPolSchema.getCValiDate());

		}
		tmpMap.put(tLCInsureAccClassSet, "INSERT");
		tmpMap.put(tLCInsureAccTraceSet, "INSERT");
		if (mUsePubAcc.equals("N")) {
			tmpMap.put(tLCInsureAccFeeSet, "INSERT");
			tmpMap.put(tLCInsureAccClassFeeSet, "INSERT");
			tmpMap.put(tLCInsureAccFeeTraceSet, "INSERT");
		}
		// tLCInsureAccFeeTraceSet
		this.mInputData.add(tLCInsureAccClassSet);
		this.mInputData.add(tLCInsureAccTraceSet);
		this.mInputData.add(tLCInsureAccFeeSet);
		this.mInputData.add(tLCInsureAccClassFeeSet);
		this.mInputData.add(tLCInsureAccFeeTraceSet);

		return true;
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
				CError.buildErr(this, "查询险种描述表失败！");

				return false;
			}

			inLMRiskSchema = tLMRiskDB.getSchema();
		}

		if ((inLMRiskSchema.getInsuAccFlag() == null)
				|| inLMRiskSchema.getInsuAccFlag().equals("N")) {
			return true;
		}

		String Rate = null; // 分配比率置空，待调整
		// 应该从LCGRPFEE中查询出来 20051008
		logger.debug(inLCPolSchema.getGrpContNo());
		logger.debug(inLCPolSchema.getGrpPolNo());
		logger.debug(inLCPolSchema.getRiskCode());
		// LCGrpFeeDB tLCGrpFeeDB=new LCGrpFeeDB();

		LCPremSet tLCPremSet = (LCPremSet) PolDetail.getObjectByObjectName(
				"LCPremSet", 0);
		DealAccount tDealAccount = new DealAccount(this.mGlobalInput);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AccCreatePos", "1"); // 生成位置：缴费时 即签单核销时
		tTransferData.setNameAndValue("OtherNoType", "2"); // 其它号码类型：个人险种保单号
		tTransferData.setNameAndValue("Rate", Rate);

		// 注意：此时数据尚未存入数据库,因此，暂时用投保单号代替保单号。
		tTransferData.setNameAndValue("PolNo", inLCPolSchema.getProposalNo());
		tTransferData.setNameAndValue("OtherNo", inLCPolSchema.getProposalNo());

		// 生成帐户结构
		VData tVData = tDealAccount.createInsureAccForBat(tTransferData,
				inLCPolSchema, inLMRiskSchema);

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
		VData feeData = null;
		// 创建管理费结构
		feeData = tDealAccount.getManageFeeStru(inLCPolSchema, tLCPremToAccSet,
				tmpLCInsureAccSet);
		if (feeData != null) {
			tLCInsureAccFeeSet = (LCInsureAccFeeSet) feeData
					.getObjectByObjectName("LCInsureAccFeeSet", 0);
			tLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) feeData
					.getObjectByObjectName("LCInsureAccClassFeeSet", 0);

		}
		tVData.add(tLCInsureAccFeeSet);
		tVData.add(tLCInsureAccClassFeeSet);

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
		// added by wanglei
		LCInsureAccSet ttLCInsureAccSet = new LCInsureAccSet();
		for (int k = 1; k <= tLCInsureAccSet.size(); k++) {
			int n = 0;
			for (int q = 1; q <= tLCInsureAccClassSet.size(); q++) {
				if (tLCInsureAccClassSet.get(q).getInsuAccNo().equals(
						tLCInsureAccSet.get(k).getInsuAccNo())) {
					n++;
				}
			}
			if (n > 0) {
				ttLCInsureAccSet.add(tLCInsureAccSet.get(k));
			}
			n = 0;
		}
		tmpMap.put(tLCInsureAccSet, "DELETE");
		tmpMap.put(ttLCInsureAccSet, this.INSERT);
		tmpMap.put(tLCPremToAccSet, this.INSERT);
		tmpMap.put(tLCGetToAccSet, this.INSERT);
	//	tmpMap.put(tLCInsureAccTraceSet, "INSERT");
		tmpMap.put(tLCInsureAccClassSet, "INSERT");
		// 更新前将AccAscription改为1，并且如果money为0，不保持此表
		// 查询收据号
		String tempno = "";
		if (!inLCPolSchema.getAppFlag().trim().equals("2")) {
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
			String tsql = "select * From ljtempfee where otherno in (select prtno from lcgrpcont where grpcontno='"
					+ inLCPolSchema.getGrpContNo() + "') and operstate='0'";
			tLJTempFeeSet = tLJTempFeeDB.executeQuery(tsql);
			tempno = tLJTempFeeSet.get(1).getTempFeeNo();
		} else {
			tempno = tLCInsureAccClassSet.get(1).getOtherNo();
		}
		logger.debug(tempno);
		LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceSchema mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		if (tLCInsureAccTraceSet != null) {
			for (int t = 1; t <= tLCInsureAccTraceSet.size(); t++) {
				mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				mLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(t);
				mLCInsureAccTraceSchema.setAccAscription("1");
				mLCInsureAccTraceSchema.setOtherNo(tempno);
				mLCInsureAccTraceSchema
						.setPayDate(inLCPolSchema.getCValiDate()); // 签单日期
				if (mLCInsureAccTraceSchema.getMoney() > 0) {
					mLCInsureAccTraceSet.add(mLCInsureAccTraceSchema);

				}
			}
		}
		tmpMap.put(mLCInsureAccTraceSet, "INSERT");
		// tmpMap.put(tLCInsureAccTraceSet, "INSERT");

		// 更新前将AccAscription改为1
		LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccClassSchema mLCInsureAccClassSchema = new LCInsureAccClassSchema();
		if (tLCInsureAccClassSet != null) {
			for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
				mLCInsureAccClassSchema = new LCInsureAccClassSchema();
				mLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);
				mLCInsureAccClassSchema.setAccAscription("1");
				mLCInsureAccClassSet.add(mLCInsureAccClassSchema);
			}
		}
		// this.mInputData.add(tLCInsureAccClassSet);
		this.mInputData.add(mLCInsureAccClassSet);
		this.mInputData.add(tLCInsureAccTraceSet);
		this.mInputData.add(tLCInsureAccFeeSet);
		// 更新前将AccAscription改为1
		LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccClassFeeSchema mLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
		// *****
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema;
		String tLimit = "";
		tLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		if (tLCInsureAccClassFeeSet != null) {
			for (int i = 1; i <= tLCInsureAccClassFeeSet.size(); i++) {
				mLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
				mLCInsureAccClassFeeSchema = tLCInsureAccClassFeeSet.get(i);
				mLCInsureAccClassFeeSchema.setAccAscription("1");
				mLCInsureAccClassFeeSet.add(mLCInsureAccClassFeeSchema);
				// ***************************
				String temSerialno = PubFun1.CreateMaxNo("SerialNo", tLimit);
				tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
				rf.transFields(tLCInsureAccFeeTraceSchema,
						mLCInsureAccClassFeeSchema);
				tLCInsureAccFeeTraceSchema.setSerialNo(temSerialno);
				tLCInsureAccFeeTraceSchema.setMoneyType("GL");
				tLCInsureAccFeeTraceSchema.setInerSerialNo("1");
				tLCInsureAccFeeTraceSchema.setState("0");
				// inLCPolSchema
				tLCInsureAccFeeTraceSchema.setPayDate(inLCPolSchema
						.getCValiDate());
				tLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
			}
		}
		// this.mInputData.add(tLCInsureAccClassFeeSet);
		this.mInputData.add(mLCInsureAccClassFeeSet);
		this.mInputData.add(tLCInsureAccFeeTraceSet);
		// 只有团体帐户即poltype=2时才保存管理费 yaory
		//tongmeng 2008-09-10 modify
		//个人账户和团险账户 都保存管理费
		//?????????????
		//if (poltype.equals("2")) 
		if (mUsePubAcc.equals("N"))
		{
			tmpMap.put(tLCInsureAccFeeSet, "INSERT");
			tmpMap.put(tLCInsureAccClassFeeSet, "INSERT");
			tmpMap.put(tLCInsureAccFeeTraceSet, "INSERT");
		}

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
		// mFirstPayDate = null;
		// maxPayDate = null;
		// maxEnterAccDate = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		if (!mBQFlag) {
			tLJTempFeeDB.setOtherNo(tLCPolSchema.getPrtNo());
			// tLJTempFeeDB.setRiskCode(tLCPolSchema.getRiskCode());
			tLJTempFeeDB.setOtherNoType("6");
			tLJTempFeeDB.setTempFeeType("1");
			tLJTempFeeDB.setConfFlag("0");
			tLJTempFeeDB.setOperState("0");
		} else {
			String tEdorNo = (String) mBQData.getValueByName("EdorNo");
			tLJTempFeeDB.setOtherNo(tEdorNo);
			tLJTempFeeDB.setOtherNoType("3");
			tLJTempFeeDB.setTempFeeType("4");
			tLJTempFeeDB.setConfFlag("1");
		}
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();
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
						+ tLJTempFeeSchema.getTempFeeNo() + "]未到帐");
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
			// rMap.put(outLJAGetEndorseSet, "DELETE");
			rMap.put(outLJAPayPersonSet, "INSERT");
			return true;
		}

		// 如果保全类型是新增附加险(不走此财务)
		if ("NS".equals(inEdorType)) {
			// delete by lizhuo for NCL at 2005.07.25
			// //根据保全下个人签单流程走-将前面保存的个人险种保单签单标记为3(得到该对象的引用，直接更改)
			// LCPolSchema saveLCPolSchema = (LCPolSchema) tPolData.
			// getObjectByObjectName("LCPolSchema",
			// 0);
			// saveLCPolSchema.setAppFlag("1");
			// /*Lis5.3 upgrade set
			// saveLCPolSchema.setCustomGetPolDate(PubFun.getCurrentDate());
			// saveLCPolSchema.setGetPolDate(PubFun.getCurrentDate());
			// */
			// LCPolDB tLCPolDB = new LCPolDB();
			// tLCPolDB.setPrtNo(saveLCPolSchema.getPrtNo());
			// tLCPolDB.setRiskCode(saveLCPolSchema.getRiskCode());
			// tLCPolDB.setAppFlag("1");
			//
			// LCPolSet tLCPolSet = tLCPolDB.query();
			//
			// if (tLCPolSet.size() > 0)
			// {
			// //需要更新已经存在的附加险的续保标记
			// tBQTransferData.setNameAndValue("UPLCPol", tLCPolSet);
			// }
			//
			// //VData LJAPayData=(VData)tPolData.getObjectByObjectName("VData",0);
			// //LJAPaySet
			// tLJAPaySet=(LJAPaySet)tPolData.getObjectByObjectName("LJAPaySet",0);
			// VData tVData = new VData();
			//
			// //tBQTransferData.setNameAndValue("NewPolNo",PolNo);
			// tVData.add(mGlobalInput);
			// tVData.add(tBQTransferData);
			// tVData.add(tLCPremSet);
			// tVData.add(tLCPolSchema);
			//
			// //tVData.add(tLJAPaySet);
			// EdorSignBL tEdorSignBL = new EdorSignBL();
			//
			// if (tEdorSignBL.submitData(tVData, "QUERY") == false)
			// {
			// return false;
			// }
			//
			// tVData = tEdorSignBL.getResult();
			//
			// //删除
			// LJAGetEndorseSet outLJAGetEndorseSet = (LJAGetEndorseSet) tVData.
			// getObjectByObjectName("LJAGetEndorseSet",
			// 0);
			//
			// //插入
			// LJAPayPersonSet outLJAPayPersonSet = (LJAPayPersonSet) tVData.
			// getObjectByObjectName(
			// "LJAPayPersonSet",
			// 0);
			//
			// tPolData.add(outLJAGetEndorseSet);
			// tPolData.add(outLJAPayPersonSet);

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

}
