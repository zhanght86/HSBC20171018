package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLUWPremMasterSchema;
import com.sinosoft.lis.schema.LLUWPremSubSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LLUWPremSubSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 理赔核保加费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 续涛，2005-10-29
 * @version 1.0
 */

public class LLUWAddFeeBL {
private static Logger logger = Logger.getLogger(LLUWAddFeeBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private LLUWPremMasterSet mLLUWPremMasterSet = new LLUWPremMasterSet(); // 理赔核保加费
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private String mClmNo = "";
	private String mBatNo = "";
	private String mContNo = "";
	private String mPolNo = "";
	private String mAddReason = "";

	public LLUWAddFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("------------------理赔核保-----加费处理LLUWAddFeeBL-----开始------------------");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("------------------理赔核保-----加费处理LLUWAddFeeBL-----结束------------------");

		return true;
	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mInputData = cInputData;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mLLUWPremMasterSet = (LLUWPremMasterSet) cInputData
				.getObjectByObjectName("LLUWPremMasterSet", 0);

		this.mOperate = StrTool.cTrim(cOperate);
		this.mClmNo = StrTool.cTrim((String) mTransferData
				.getValueByName("ClmNo")); // 赔案号
		this.mBatNo = StrTool.cTrim((String) mTransferData
				.getValueByName("BatNo")); // 批次号
		this.mContNo = StrTool.cTrim((String) mTransferData
				.getValueByName("ContNo")); // 合同号
		this.mPolNo = StrTool.cTrim((String) mTransferData
				.getValueByName("PolNo")); // 险种号
		this.mAddReason = StrTool.cTrim((String) mTransferData
				.getValueByName("AddReason")); // 加费原因号

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return
	 */
	private boolean checkData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 进行基础数据校验
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mGlobalInput == null) {
			mErrors.addOneError("前台传输全局公共数据失败!!!");
			return false;
		}

		String tOperater = mGlobalInput.Operator;
		if (tOperater == null || tOperater.trim().equals("")) {
			mErrors.addOneError("前台传输的操作用户在后台没有取到!!!");
			return false;
		}

		String tManageCom = mGlobalInput.ManageCom;
		if (tManageCom == null || tManageCom.trim().equals("")) {
			mErrors.addOneError("前台传输的登录机构在后台没有取到!!!");
			return false;
		}

		if (mLLUWPremMasterSet == null || mLLUWPremMasterSet.size() == 0) {
			mErrors.addOneError("前台传输的理赔核保加费信息在后台取到的记录数："
					+ mLLUWPremMasterSet.size());
			return false;
		}

		if (mClmNo == null || mClmNo.trim().equals("")) {
			mErrors.addOneError("前台传输的赔案号在后台没有取到!!!");
			return false;
		}

		if (mContNo == null || mContNo.trim().equals("")) {
			mErrors.addOneError("前台传输的合同号在后台没有取到!!!");
			return false;
		}

		if (mPolNo == null || mPolNo.trim().equals("")) {
			mErrors.addOneError("前台传输的险种号在后台没有取到!!!");
			return false;
		}

		return true;
	}

	/**
	 * 业务处理
	 * 
	 * @return
	 */
	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 理赔核保加费信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mOperate.equals("DELETE")) {
			getDelete();
		} else {
			if (getLLUWPremMaster() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除指定的数据
	 * 
	 * @return
	 */
	private boolean getDelete() {

		for (int i = 1; i <= mLLUWPremMasterSet.size(); i++) {
			LLUWPremMasterSchema tLLUWPremMasterSchema = mLLUWPremMasterSet
					.get(i);
			String tPolNo = StrTool.cTrim(tLLUWPremMasterSchema.getPolNo());
			String tDutyCode = StrTool.cTrim(tLLUWPremMasterSchema
					.getDutyCode());
			String tPayPlanType = StrTool.cTrim(tLLUWPremMasterSchema
					.getPayPlanType());// 交费计划类型

			String tDelSql = "delete from LLUWPremMaster where 1=1 "
					+ " and PolNo='" + "?PolNo?" + "'" + " and DutyCode = '"
					+ "?DutyCode?" + "'" + " and PayPlanCode like '000000%'"
					+ " and PayPlanType = '" + "?PayPlanType?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tDelSql);
			sqlbv.put("PolNo", tPolNo);
			sqlbv.put("DutyCode", tDutyCode);
			sqlbv.put("PayPlanType", tPayPlanType);
			mMMap.put(sqlbv, "DELETE");
		}

		return true;
	}

	/**
	 * 准备理赔核保加费信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean getLLUWPremMaster() {
		String tInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);// 出险时间

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1 校验合同信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = mContNo;
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContSet == null || tLCContSet.size() != 1) {
			mErrors.addOneError("合同[" + tContNo + "]信息没有取到!!!");
			return false;
		}
		LCContSchema tLCContSchema = tLCContSet.get(1);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2 校验险种信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = mPolNo;
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tPolNo);
		if (!tLCPolDB.getInfo()) {
			mErrors.addOneError("保单险种[" + tPolNo + "]信息没有取到!!!");
			return false;
		}
		LCPolSchema tLCPolSchema = tLCPolDB.getSchema();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 校验险种核保信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
		 * tLLUWMasterDB.setCaseNo(mClmNo) ; tLLUWMasterDB.setPolNo(tPolNo) ;
		 * tLLUWMasterDB.setPassFlag("3") ;//加费承保 LLUWMasterSet tLLUWMasterSet =
		 * tLLUWMasterDB.query(); if(tLLUWMasterSet == null ||
		 * tLLUWMasterSet.size() != 1) {
		 * mErrors.addOneError("赔案号:["+mClmNo+"],险种号:["+mPolNo+"]没有理赔核保加费结论,不能执行加费确认操作!!!");
		 * return false; } LLUWMasterSchema tLLUWMasterSchema = new
		 * LLUWMasterSchema();
		 * tLLUWMasterSchema.setSchema(tLLUWMasterSet.get(1));
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.4 取险种名称
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
		if (!tLMRiskDB.getInfo()) {
			mErrors.addOneError("险种编码[" + tLCPolSchema.getRiskCode()
					+ "]没有取到!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 取责任信息，计算加费次数
		 * 计算除去本次加费项目,承保时的基本保费项后，该保单在该的加费项目数。以便计算本次加费的编码起始编码值.
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tCSql = "select (CASE WHEN MAX(to_number (payplancode)) IS NOT NULL THEN MAX(to_number (payplancode)) ELSE 0 END) from LCPrem where  polno = '"
				+ "?polno?" + "'  and PayPlanCode like '000000%'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tCSql);
		sqlbv1.put("polno", tPolNo);
		String tSPayPlanCode = new String();
		ExeSQL tExeSQL = new ExeSQL();
		tSPayPlanCode = tExeSQL.getOneValue(sqlbv1);
		logger.debug("------------------------------------------------------");
		logger.debug("--执行C表SQL语句[" + tSPayPlanCode + "]：" + tCSql + "");
		logger.debug("------------------------------------------------------");

		if (tExeSQL.mErrors.needDealError()) {
			mErrors.addOneError("执行SQL语句失败!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return false;
		}
		if (tSPayPlanCode == null || tSPayPlanCode.equals("")) {
			mErrors.addOneError("取得的缴费计划编码的次数为空!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 取责任信息，计算加费次数
		 * 计算除去本次加费项目,承保时的基本保费项后，该保单在该的加费项目数。以便计算本次加费的编码起始编码值.
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * String tUWSql = "select count(*) from LLUWPremMaster where polno =
		 * '"+tPolNo+"' and PayPlanCode like '000000%'"; String tSPayPlanCodeUW =
		 * new String(); tSPayPlanCodeUW = tExeSQL.getOneValue(tUWSql);
		 * logger.debug("------------------------------------------------------");
		 * logger.debug("--执行UW表SQL语句["+tSPayPlanCode+"]："+tUWSql+"");
		 * logger.debug("------------------------------------------------------");
		 * 
		 * if(tExeSQL.mErrors.needDealError()) {
		 * mErrors.addOneError("执行SQL语句失败!!!"+tExeSQL.mErrors.getError(0).errorMessage);
		 * return false; } if(tSPayPlanCodeUW == null ||
		 * tSPayPlanCodeUW.equals("")) {
		 * mErrors.addOneError("取得理赔暂存缴费计划编码的次数为空!!!"); return false; }
		 */

		int tIPayPlanCode = 0;
		tIPayPlanCode = Integer.parseInt(tSPayPlanCode);// 缴费计划编码的次数

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 循环责任信息，并保存加费信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLUWPremMasterSet tLLUWPremMasterSaveSet = new LLUWPremMasterSet();
		LLUWPremSubSet tLLUWPremSubSaveSet = new LLUWPremSubSet();
		for (int i = 1; i <= mLLUWPremMasterSet.size(); i++) {
			LLUWPremMasterSchema tLLUWPremMasterSchema = mLLUWPremMasterSet
					.get(i);
			String tDutyCode = StrTool.cTrim(tLLUWPremMasterSchema
					.getDutyCode());
			String tPayPlanType = StrTool.cTrim(tLLUWPremMasterSchema
					.getPayPlanType());// 交费计划类型

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.1 得到LCDuty责任信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCDutySchema tLCDutySchema = mLLClaimPubFunBL.getLCDuty(tPolNo,
					tDutyCode);
			if (tLCDutySchema == null) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.2
			 * 删除LLUWPremMaster中的原数据 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tDelSql = "delete from LLUWPremMaster where 1=1 "
					+ " and PolNo='" + "?PolNo?" + "'" + " and DutyCode = '"
					+ "?DutyCode?" + "'" + " and PayPlanCode like '000000%'"
					+ " and PayPlanType = '" + "?PayPlanType?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tDelSql);
			sqlbv2.put("PolNo", tPolNo);
			sqlbv2.put("DutyCode", tDutyCode);
			sqlbv2.put("PayPlanType", tPayPlanType);
			logger.debug("------------------------------------------------------");
			logger.debug("--删除LLUWPremMaster中的原数据：" + tDelSql);
			logger.debug("------------------------------------------------------");
			mMMap.put(sqlbv2, "DELETE");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.3 形成加费编码
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			tSPayPlanCode = String.valueOf(tIPayPlanCode + i);
			for (int j = tSPayPlanCode.length(); j < 8; j++) {
				tSPayPlanCode = "0" + tSPayPlanCode;
			}

			logger.debug("------------------------------------------------------");
			logger.debug("--生成的PayPlanCode：" + tSPayPlanCode);
			logger.debug("------------------------------------------------------");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.4 添加新的核保加费信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			tLLUWPremMasterSchema.setClmNo(mClmNo);
			tLLUWPremMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLLUWPremMasterSchema.setContNo(tLCPolSchema.getContNo());
			tLLUWPremMasterSchema.setPolNo(tLCPolSchema.getPolNo());
			tLLUWPremMasterSchema.setDutyCode(tLLUWPremMasterSchema
					.getDutyCode());

			tLLUWPremMasterSchema.setPayPlanCode(tSPayPlanCode); // 交费计划编码
			tLLUWPremMasterSchema.setPayPlanType(tPayPlanType); // 交费计划类型,01--首期健康加费,02-首期职业加费,03-复效健康加费,04-复效职业加费

			tLLUWPremMasterSchema.setAppntType("1");// 投保人类型,1-个人投保人,2-集体投保人
			tLLUWPremMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());

			tLLUWPremMasterSchema.setUrgePayFlag("Y"); // 加费相一定要催交，而不是去取该险种所描述的催交标志。
			tLLUWPremMasterSchema.setNeedAcc("0"); // 是否和账户相关
			tLLUWPremMasterSchema.setPayTimes("0"); // 已交费次数
			tLLUWPremMasterSchema.setRate("0"); // 保费分配比率

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.1 获取缴费计划信息LCPrem
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tSql = "select * from LCPrem where 1=1 " + " and PolNo='"
					+ "?PolNo?" + "'" + " and DutyCode = '" + "?DutyCode?" + "'"
					+ " and substr(PayPlanCode,1,6) not in ('000000')";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("PolNo", tPolNo);
			sqlbv3.put("DutyCode", tDutyCode);
			LCPremDB tLCPremDB = new LCPremDB();
			LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv3);

			logger.debug("---------------------------------------------------------------");
			logger.debug("--查询缴费计划表[" + tLCPremSet.size() + "]:" + tSql);
			logger.debug("---------------------------------------------------------------");

			if (tLCPremDB.mErrors.needDealError()) {
				mErrors.addOneError("查询缴费计划表失败!!!"
						+ tLCPremDB.mErrors.getError(0).errorMessage);
				return false;
			}

			if (tLCPremSet.size() == 0) {
				mErrors.addOneError("查询缴费计划为空，不能执行此操作!!!");
				return false;
			}
			LCPremSchema tLCPremSchema = tLCPremSet.get(1);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.4 计算新的缴费开始时间
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tStaDate = tLCPremSchema.getPayStartDate();// 起交日期

			// String tPayIntv = String.valueOf(tLCPremSchema.getPayIntv());//交费间隔
			// double tDPeriodTimes =
			// mLLClaimPubFunBL.getLCPremPeriodTimes(tStaDate,tLCPremSchema.getPaytoDate(),tPayIntv,tInsDate);
			//
			// int tIPayIntv = tLCPremSchema.getPayIntv() * (int)tDPeriodTimes;
			// String tNewStaDate = PubFun.calDate(tStaDate,tIPayIntv,"M",tStaDate);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0 开始置值
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			tLLUWPremMasterSchema.setPayStartDate(tStaDate); // 起交日期
			tLLUWPremMasterSchema.setPayEndDate(tLCPremSchema.getPayEndDate()); // 终交日期
			tLLUWPremMasterSchema.setPaytoDate(tLCPremSchema.getPaytoDate()); // 交至日期
			tLLUWPremMasterSchema.setPayIntv(tLCPremSchema.getPayIntv());

			tLLUWPremMasterSchema.setStandPrem(tLLUWPremMasterSchema.getPrem());// 每期保费
			tLLUWPremMasterSchema.setPrem(tLLUWPremMasterSchema.getPrem()); // 实际保费
			tLLUWPremMasterSchema.setSumPrem("0"); // 累计保费
			tLLUWPremMasterSchema.setSuppRiskScore(tLLUWPremMasterSchema
					.getSuppRiskScore());// 额外风险评分

			tLLUWPremMasterSchema.setFreeFlag("0"); // 免交标志
			tLLUWPremMasterSchema.setFreeRate("0"); // 免交比率
			tLLUWPremMasterSchema.setFreeStartDate(""); // 免交起期
			tLLUWPremMasterSchema.setFreeEndDate(""); // 免交止期

			tLLUWPremMasterSchema.setState("1");// 0:承保时的保费项。1:承保时的加费项；2：本次项目加费项
			// 3：前几次不通批单下的加费：
			tLLUWPremMasterSchema.setManageCom(tLCPolSchema.getManageCom());
			tLLUWPremMasterSchema.setOperator(this.mGlobalInput.Operator);
			tLLUWPremMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLLUWPremMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLLUWPremMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLLUWPremMasterSchema.setModifyTime(PubFun.getCurrentTime());

			tLLUWPremMasterSchema.setAddFeeDirect(tLLUWPremMasterSchema
					.getAddFeeDirect()); // 加费指向标记
			tLLUWPremMasterSchema.setSecInsuAddPoint(tLLUWPremMasterSchema
					.getSecInsuAddPoint());// 第二被保人加费评点

			tLLUWPremMasterSaveSet.add(tLLUWPremMasterSchema);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.7 将数据同步备份到轨迹信息表
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLUWPremSubSchema tLLUWPremSubSchema = new LLUWPremSubSchema();
			this.mReflections.transFields(tLLUWPremSubSchema,
					tLLUWPremMasterSchema);
			tLLUWPremSubSchema.setBatNo(mBatNo);

			tLLUWPremSubSaveSet.add(tLLUWPremSubSchema);
		}
		mMMap.put(tLLUWPremMasterSaveSet, "DELETE&INSERT");
		mMMap.put(tLLUWPremSubSaveSet, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0 更新理赔核保轨迹信息表
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ //
		 * tLLUWMasterSchema.setAddPremReason(mAddReason); //
		 * tLLUWMasterSchema.setAddPremFlag("1");//有加费标识 // //
		 * tLLUWMasterSchema.setOperator(this.mGlobalInput.Operator) ; //
		 * tLLUWMasterSchema.setModifyDate(CurrentDate) ; //
		 * tLLUWMasterSchema.setModifyTime(CurrentTime); //
		 * mMMap.put(tLLUWMasterSchema, "DELETE&INSERT");
		 */

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败,原因"
					+ tPubSubmit.mErrors.getError(0).errorMessage);
			return false;
		}
		return true;

	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "96";

		String tOperate = "";// DELETE
		String tClmNo = "90000013820";
		String tContNo = "HB420426111000952";
		String tPolNo = "HB420426111000952000";
		String tDutyCode = "611000";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tTransferData.setNameAndValue("ContNo", tContNo);
		tTransferData.setNameAndValue("PolNo", tPolNo);
		tTransferData.setNameAndValue("AddReason", "123");

		LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();
		LLUWPremMasterSet tLLUWPremMasterSet = new LLUWPremMasterSet();

		tLLUWPremMasterSchema.setPolNo(tPolNo);
		tLLUWPremMasterSchema.setDutyCode(tDutyCode);
		tLLUWPremMasterSchema.setAddFeeType("01");// 加费方式，本期，下期，追溯

		tLLUWPremMasterSchema.setPayPlanType("01");
		tLLUWPremMasterSchema.setPayStartDate("2005-01-01");
		tLLUWPremMasterSchema.setPayEndDate("2015-01-01");
		tLLUWPremMasterSchema.setAddFeeDirect("01");// 加费对象,01|投保人^02|被保险人^03|多被保险人^04|第二被保险人
		tLLUWPremMasterSchema.setSecInsuAddPoint("30");
		tLLUWPremMasterSchema.setPrem("300");
		tLLUWPremMasterSchema.setSuppRiskScore("30");
		tLLUWPremMasterSet.add(tLLUWPremMasterSchema);

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLLUWPremMasterSet);

		LLUWAddFeeBL tLLUWAddFeeBL = new LLUWAddFeeBL();
		tLLUWAddFeeBL.submitData(tVData, tOperate);
		logger.debug("----------------理赔核保加费后台提示信息打印------开始----------------");
		int n = tLLUWAddFeeBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "提示信息: "
					+ tLLUWAddFeeBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
		logger.debug("----------------理赔核保加费后台提示信息打印------结束----------------");

	}

}
