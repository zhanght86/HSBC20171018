package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 续期的冲正和回退
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class XQChargeRollBackBL {
private static Logger logger = Logger.getLogger(XQChargeRollBackBL.class);
	// 得到全局的变量
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private RNHangUp mrn = new RNHangUp(mGlobalInput);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	// 得到需要修改表的Set*****************业务上的表

	private LCPolSet mUpdateLCPolSet = new LCPolSet();
	private LCPremSet mUpdateLCPremSet = new LCPremSet();
	private LCDutySet mUpdateLCDutySet = new LCDutySet();
	private LCGetSet mUpdateLCGetSet = new LCGetSet();

	private LCPolSet meInsertCPolSet = new LCPolSet();
	private LCPremSet meInsertLCPremSet = new LCPremSet();
	private LCDutySet meInsertLCDutySet = new LCDutySet();
	private LCGetSet meInsertLCGetSet = new LCGetSet();

	private LCContSet mUpdateLCContSet = new LCContSet();
	private LOPRTManagerSet mDeleteLoprtManager = new LOPRTManagerSet();
	private LCPolSet mDeleteCPolSet = new LCPolSet();
	private LCPremSet mDeleteLCPremSet = new LCPremSet();
	private LCDutySet mDeleteLCDutySet = new LCDutySet();
	private LCGetSet mDeleteLCGetSet = new LCGetSet();
	// 得到需要修改表的Set*****************业务上的表财务上的处理

	private LJTempFeeSet mUpdateLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mUpdateLJtempfeeClassSet = new LJTempFeeClassSet();
	private LJAPaySet mUpdateLJAPaySet = new LJAPaySet();
	private LJAPayPersonSet mUpdateLJAPayPersonSet = new LJAPayPersonSet();
	private LJAGetSet mInsertLJAGetSet = new LJAGetSet();
	private LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet();

	// 基本参数的定义 **********************全局变量
	private String mContNo = "";
	private String mPayToDate = "";
	private String mCurrentDate = PubFun.getCurrentDate();
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	private String mPolNo = ""; // 定义主险保单号
	private String mEPolNo = null;
	private String mLCManagerCom = "";
	private String tNo = "";
	private String mPayNo = "";
	private String mOperator = "";
	private String mBankCode = "";
	private String mAccNo = "";
	private String mAccName = "";
	private String mPayMode = "";
	private String mGetnoticeNo = "";

	public XQChargeRollBackBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"READ"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		this.mOperator = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {

			return false;
		}
		logger.debug("---End getInputData---");
		// 检验该保单是否被挂起(没有被挂起),是否被核销(应被核销)
		if (!checkDate()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	// submit中方法
	private boolean getInputData(VData cInputData, String cOperate) {
		// 将得到Contno

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mBankCode = (String) mTransferData.getValueByName("BankCode");
		mAccNo = (String) mTransferData.getValueByName("AccNo");
		mAccName = (String) mTransferData.getValueByName("AccName");
		mPayMode = (String) mTransferData.getValueByName("PayMode");
		mPayToDate = (String) mTransferData.getValueByName("PayToDate");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mGetnoticeNo = (String) mTransferData.getValueByName("GetnoticeNo");

		if (mContNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "合同号传入为空，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mGetnoticeNo == null || mGetnoticeNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "未得到本次交费收据号,无法确定唯一交费";
			this.mErrors.addOneError(tError);

			return false;
		}

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	private boolean checkDate() {
		// 校验是否该合同号是否被挂起
		// LCContHangUpStateSchema tLCContHangUpStateSchema = new
		// LCContHangUpStateSchema();
		if (!mrn.checkHangUP(mContNo)) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该保单已被其他操作所挂起，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}
		// 校验是否核销
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String t1Sql = "";
		String tSql = "select * from ljapayperson where getnoticeno='?mGetnoticeNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mGetnoticeNo",mGetnoticeNo);

		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		mLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv);
		if (mLJAPayPersonSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该保单没有被核销或缴费对应日输入错误，请您确认!";
			this.mErrors.addOneError(tError);

			return false;

		} // endortype = NS 代表新加附加险
		// t1Sql = "select * from lpedoritem where edortype <>'AC' and edortype <>'PC'
		// and edorstate = '0' and contno = '" +
		// mContNo + "' and lpedoritem.edorvalidate >'" +
		// mLJAPayPersonSet.get(1).getConfDate() + "' ";
		// tSSRS = tExeSQL.execSQL(t1Sql);
		// if (tSSRS.getMaxRow() != 0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "XQChargeRoolBackBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "该保单做过保全，因此无法做此操作!";
		// this.mErrors.addOneError(tError);
		//
		// return false;
		//
		// }
		t1Sql = "select * from llclaimpolicy a, llregister b where b.rgtno = a.rgtno and a.contno = '?mContNo?' and b.rgtdate >'?rgtdate?' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(t1Sql);
		sqlbv2.put("mContNo",mContNo);
		sqlbv2.put("rgtdate",mLJAPayPersonSet.get(1).getConfDate());
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS.getMaxRow() != 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该保单做过理赔无法，因此无法做此操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean dealData() {
		// 因为在续期抽档后要对表进行更新或插入
		/* 下面抽档后对表update后还原其的方法********************业务上的处理********************************** */
		// 对lcpol表进行更新
		// if (!mOperator.equals("reHT"))
		// {
		// if (!UpdateLcPol())
		// {
		// return false;
		// }
		//
		// //对合同表更新
		// UpdateLCcont();
		// /*对进行了插入的修改*/
		// DeleteLoprtManager();
		// }
		/* 下面抽档后对表update后还原其的方法********************财务上的处理********************************** */
		if (mOperator.equals("reCZ")) {
			// //对暂交费表修改
			// if (!UpdateLjtempfee())
			// {
			// return false;
			// }
			// //对暂交费分类表修改
			// if (!UpdateLjtempfeeClass())
			// {
			// return false;
			// }
			//
			// //对实收表进行更新
			// UpdateLjaPay();
			// //因为实收个人表是按险种存的所以要修改多条
			// UpdateLjaPayperson();

			RnStrikeBalance tRnStrikeBalance = new RnStrikeBalance();
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GetnoticeNo", mGetnoticeNo);
			tTransferData.setNameAndValue("ContNo", mContNo);
			tVData.add(tTransferData);
			tVData.add(mGlobalInput);
			if (!tRnStrikeBalance.submitData(tVData, "CZ")) {
				logger.debug("错误原因："
						+ tRnStrikeBalance.mErrors.getFirstError().toString());
				this.mErrors.copyAllErrors(tRnStrikeBalance.mErrors);
				return false;
			}
		} else if (mOperator.equals("reHT")) {
			RnRollbackDealBL tRnRollbackDealBL = new RnRollbackDealBL();
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GetnoticeNo", mGetnoticeNo);
			tTransferData.setNameAndValue("ContNo", mContNo);
			tTransferData.setNameAndValue("BankCode", mBankCode);
			tTransferData.setNameAndValue("AccNo", mAccNo);
			tTransferData.setNameAndValue("AccName", mAccName);
			tTransferData.setNameAndValue("PayMode", mPayMode);
			tVData.add(tTransferData);
			tVData.add(mGlobalInput);

			if (!tRnRollbackDealBL.submitData(tVData, "HT")) {
				logger.debug("错误原因："
						+ tRnRollbackDealBL.mErrors.getFirstError().toString());
				this.mErrors.copyAllErrors(tRnRollbackDealBL.mErrors);
				return false;
			}

		}
		return true;
	}

	/* @todo***********对表中存了按polno的数据进行处理 */
	private void dealPolNo(String newPolNo, String oldPolNo, String tFlag) {
		// newPolNo appflag = '4'的状态, oldPolNo appflag= '1' ** tFlag = "1"主险
		// 对保费表更新
		UpdateLcPrem(newPolNo, oldPolNo, tFlag);
		// 对责任表更新
		UpdateLcDuty(newPolNo, oldPolNo, tFlag);
		// 对领取表更新
		UpdateLcGet(newPolNo, oldPolNo, tFlag);
	}

	private boolean prepareOutputData() {
		VData tVData = new VData();
		MMap tMMap = new MMap();
		tMMap.put(mDeleteLoprtManager, "DELETE");
		tMMap.put(mDeleteCPolSet, "DELETE");
		tMMap.put(mDeleteLCPremSet, "DELETE");
		tMMap.put(mDeleteLCDutySet, "DELETE");
		tMMap.put(mDeleteLCGetSet, "DELETE");

		tMMap.put(meInsertCPolSet, "INSERT");
		tMMap.put(meInsertLCPremSet, "INSERT");
		tMMap.put(meInsertLCDutySet, "INSERT");
		tMMap.put(meInsertLCGetSet, "INSERT");

		tMMap.put(mUpdateLCPolSet, "UPDATE");
		tMMap.put(mUpdateLCPremSet, "UPDATE");
		tMMap.put(mUpdateLCDutySet, "UPDATE");
		tMMap.put(mUpdateLCGetSet, "UPDATE");
		tMMap.put(mUpdateLCContSet, "UPDATE");
		tMMap.put(mUpdateLJTempFeeSet, "INSERT");
		tMMap.put(mUpdateLJtempfeeClassSet, "INSERT");
		tMMap.put(mUpdateLJAPaySet, "INSERT");
		tMMap.put(mUpdateLJAPayPersonSet, "INSERT");
		tMMap.put(mInsertLJAGetSet, "INSERT");
		tMMap.put(mLJAGetOtherSet, "INSERT");

		tVData.add(tMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "提交失败,请管理人员确认!";
			this.mErrors.addOneError(tError);
			logger.debug("提交失败!");
			return false;

		}

		return true;
	}

	/**
	 * 对业务处理中要进行已做插入和修改表的数据进行还原 insert
	 * 
	 * @param
	 * @param对插入的表的进行还原 @
	 */
	private void DeleteLoprtManager() { // mDeleteLoprtManager
		LOPRTManagerSchema tLOPRTManagerSchema = null;
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		String tSql = "select * from loprtmanager where otherno = '?otherno?' and code = '32' and standbyflag1 = '?standbyflag1?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("otherno",mLJAPayPersonSet.get(1).getContNo());
		sqlbv3.put("standbyflag1",mLJAPayPersonSet.get(1).getGetNoticeNo());
		tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(sqlbv3);
		tLOPRTManagerSchema = tLOPRTManagerSet.get(1);
		mDeleteLoprtManager.add(tLOPRTManagerSchema);

	}

	/**
	 * 对业务处理中要进行已做插入和修改表的数据进行还原 update
	 * 
	 * @param
	 * @param对update的表的进行还原 @
	 */
	/* 下面抽档后对表update后还原其的方法********************业务上的处理********************************** */

	private boolean UpdateLcPol() {
		/** @todo 1.进入险种层===================== */
		boolean tPolExists = false;
		/* 通过GetNoticeNo 得到本次交费发生的主险附加险 中的险种名称和交付保费* */
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSet t1LCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = null;
		double tSumPrem;
		// tLCPolSet = tLCPolDB.query();
		// 查询出本次参与交费的保单号（主险保费）
		String tSql = " select * from lcpol a where contno = '?mContNo?' and exists ( "
				+ " select polno from ljapayperson where getnoticeno = '?mGetnoticeNo?' " + " and contno = '?mContNo?' ) and appflag = '1' and a.polno = a.mainpolno";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("mContNo",mContNo);
		sqlbv4.put("mGetnoticeNo",mGetnoticeNo);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv4);
		logger.debug(tSql);

		if (tLCPolSet.size() > 0) {
			tLCPolSchema = tLCPolSet.get(1);
			tLCPolSchema.setModifyDate(mCurrentDate);
			tLCPolSchema.setPaytoDate(mPayToDate);
			double tLjapayMoney = 0;
			for (int j = 1; j <= mLJAPayPersonSet.size(); j++) {
				if (tLCPolSet.get(1).getPolNo().equals(
						mLJAPayPersonSet.get(j).getPolNo())) {

					// 将保费还原到以前的保费状态
					tLjapayMoney += mLJAPayPersonSet.get(j)
							.getSumActuPayMoney();

				}
			}
			tSumPrem = tLCPolSchema.getSumPrem() - tLjapayMoney;
			tLCPolSchema.setSumPrem(tSumPrem);
			tLCPolSchema.setModifyDate(mCurrentDate);
			tLCPolSchema.setPaytoDate(mPayToDate);

			mUpdateLCPolSet.add(tLCPolSchema);
			dealPolNo(tLCPolSchema.getPolNo(), "", "1");
			tPolExists = true;
		}

		// 查询出本次参与交费的保单号（附加险保费）
		tSql = " select * from lcpol a where contno = '?mContNo?' and exists ( "
				+ " select polno from ljapayperson where getnoticeno = '?mGetnoticeNo?' " + " and contno = '?mContNo?' ) and appflag = '1' and a.polno <> a.mainpolno";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("mContNo", mContNo);
		sqlbv5.put("mGetnoticeNo",mGetnoticeNo);

		tLCPolSet = tLCPolDB.executeQuery(sqlbv5);
		logger.debug(tSql);

		if (tLCPolSet != null && tLCPolSet.size() != 0) {
			tPolExists = true;
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				/*
				 * 21 环境 select * from lcpol where contno='HB010327011001228'
				 * 用你下面的sql查询得出来哪个是你要换的号么？ 要用上一个paytodate,用上次的缴费对应日去找,上一次的附加险纪录
				 */
				// 查询出旧的信息的要复制到当前的新信息中
				logger.debug("ddddddddddddd"
						+ tLCPolSet.get(i).getRiskCode());
				tSql = " select * from lcpol a where a.riskcode = '?riskcode?' and contno = '?mContNo?' and paytodate in ( "
						+ " select lastpaytodate from ljapayperson where getnoticeno = '?mGetnoticeNo?' " + " and contno = '?mContNo?' and riskcode = '?riskcode?' )  and appflag = '4' and a.polno <> a.mainpolno";
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql(tSql);
				sqlbv6.put("riskcode",tLCPolSet.get(i).getRiskCode());
				sqlbv6.put("mContNo", mContNo);
				sqlbv6.put("mGetnoticeNo", mGetnoticeNo);

				t1LCPolSet = tLCPolDB.executeQuery(sqlbv6);
				logger.debug(tSql);
				try {
					dealPolNo(t1LCPolSet.get(1).getPolNo(), tLCPolSet.get(i)
							.getPolNo(), "2");
				} catch (Exception exe) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "在查询该保单发现有增加附加险的记录!";
					this.mErrors.addOneError(tError);

					return false;

				}
				String tempfeepolno = tLCPolSet.get(i).getPolNo();
				LCPolSchema tTempLCPolSchema = new LCPolSchema();
				tTempLCPolSchema.setModifyDate(mCurrentDate);
				tTempLCPolSchema.setSchema(t1LCPolSet.get(1));
				tTempLCPolSchema.setPolNo(tempfeepolno);
				tTempLCPolSchema.setAppFlag("1");
				mDeleteCPolSet.add(t1LCPolSet);
				mDeleteCPolSet.add(tLCPolSet.get(i));
				meInsertCPolSet.add(tTempLCPolSchema);
			}

		}
		if (!tPolExists) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "保单信息查询失败，请联系管理员!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	private void UpdateLcPrem(String newPolNo, String oldPolNo, String tFlag) {
		LCPremSchema tupdateLCPremSchema;
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremSet tDeleteLCPremSet = new LCPremSet();
		String tSql = "select * from lcprem where  polno = '?newPolNo?'";
		String t1Sql = "select * from lcprem where  polno = '?oldPolNo?'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("newPolNo",newPolNo);
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(t1Sql);
		sqlbv8.put("oldPolNo", oldPolNo);
		logger.debug(tSql);
		logger.debug(t1Sql);
		tLCPremSet = tLCPremDB.executeQuery(sqlbv7);
		logger.debug(newPolNo);
		logger.debug(oldPolNo);
		if (oldPolNo != null && !oldPolNo.equals("null")
				&& !oldPolNo.equals("")) {
			tDeleteLCPremSet = tLCPremDB.executeQuery(sqlbv8);
		}
		if (tFlag.equals("1")) {
			for (int i = 1; i <= tLCPremSet.size(); i++) {
				tupdateLCPremSchema = tLCPremSet.get(i);
				tupdateLCPremSchema
						.setPayTimes(tLCPremSet.get(i).getPayTimes() - 1);
				tupdateLCPremSchema.setSumPrem(tLCPremSet.get(i).getSumPrem()
						- tLCPremSet.get(i).getPrem());
				mUpdateLCPremSet.add(tupdateLCPremSchema);
			}
		} else {
			String tEPolNo = tDeleteLCPremSet.get(1).getPolNo();
			mDeleteLCPremSet.add(tLCPremSet);
			mDeleteLCPremSet.add(tDeleteLCPremSet);
			logger.debug("删除lcprem的次数" + mDeleteLCPremSet.size());
			logger.debug(tEPolNo);
			logger.debug(tLCPremSet.get(1).getPolNo());
			for (int i = 1; i <= tLCPremSet.size(); i++) {
				LCPremSchema ttempLCPremSchema = new LCPremSchema();
				ttempLCPremSchema.setSchema(tLCPremSet.get(i));
				ttempLCPremSchema.setPolNo(oldPolNo);
				logger.debug(tEPolNo);
				logger.debug(tLCPremSet.get(i).getPolNo());
				mDeleteLCPremSet.add(ttempLCPremSchema);
				meInsertLCPremSet.add(ttempLCPremSchema);

			}

		}

	}

	private void UpdateLcDuty(String newPolNo, String oldPolNo, String tFlag) {
		LCDutySchema tupdateLCDutySchema = new LCDutySchema();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutySet tDeleteLCDutySet = new LCDutySet();
		String tSql = "select * from lcduty where  polno = '?newPolNo?'";
		String t1Sql = "select * from lcduty where  polno = '?oldPolNo?'";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("newPolNo", newPolNo);
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(t1Sql);
		sqlbv10.put("oldPolNo", oldPolNo);

		tLCDutySet = tLCDutyDB.executeQuery(sqlbv9);

		if (oldPolNo != null && !oldPolNo.equals("null")
				&& !oldPolNo.equals("")) {
			tDeleteLCDutySet = tLCDutyDB.executeQuery(sqlbv10);
		}
		if (tFlag.equals("1")) {
			for (int i = 1; i <= tLCDutySet.size(); i++) {
				tupdateLCDutySchema = tLCDutySet.get(i);
				// tupdateLCDutySchema.setPayTimes(tLCDutySet.get(i).getPayTimes()-1);
				tupdateLCDutySchema.setSumPrem(tLCDutySet.get(i).getSumPrem()
						- tLCDutySet.get(i).getPrem());
				mUpdateLCDutySet.add(tupdateLCDutySchema);

			}

		} else {

			mDeleteLCDutySet.add(tLCDutySet);
			mDeleteLCDutySet.add(tDeleteLCDutySet);

			for (int i = 1; i <= tLCDutySet.size(); i++) {
				tupdateLCDutySchema.setSchema(tLCDutySet.get(i));
				// tupdateLCDutySchema = tLCDutySet.get(i);
				tupdateLCDutySchema.setPolNo(oldPolNo);
				meInsertLCDutySet.add(tupdateLCDutySchema);

			}

		}

	}

	private void UpdateLcGet(String newPolNo, String oldPolNo, String tFlag) {
		LCGetSchema tupdateLCGetSchema = new LCGetSchema();
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetSet tDeleteLCGet = new LCGetSet();
		String tSql = "select * from lcget where  polno = '?newPolNo?'";
		String t1Sql = "select * from lcget where  polno = '?oldPolNo?'";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("newPolNo", newPolNo);
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(t1Sql);
		sqlbv12.put("oldPolNo", oldPolNo);
		tLCGetSet = tLCGetDB.executeQuery(sqlbv11);
		if (oldPolNo != null && !oldPolNo.equals("null")
				&& !oldPolNo.equals("")) {
			tDeleteLCGet = tLCGetDB.executeQuery(sqlbv12);
		}
		if (tFlag.equals("1")) {

		} else {

			mDeleteLCGetSet.add(tLCGetSet);
			mDeleteLCGetSet.add(tDeleteLCGet);

			for (int i = 1; i <= tLCGetSet.size(); i++) {
				LCGetSchema tinsertLCGetSchema = new LCGetSchema();
				tinsertLCGetSchema.setSchema(tLCGetSet.get(i));

				tinsertLCGetSchema.setPolNo(oldPolNo);
				mDeleteLCGetSet.add(tinsertLCGetSchema);
				meInsertLCGetSet.add(tinsertLCGetSchema);

			}

		}

	}

	private void UpdateLCcont() {
		LCContSchema tLCContSchema = null;
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		tLCContDB.getInfo();
		tLCContSchema = tLCContDB;
		String tSql = "select sumactupaymoney from ljapay where payno = '?payno?'";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(tSql);
		sqlbv13.put("payno",mLJAPayPersonSet.get(1).getPayNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv13);
		tLCContSchema.setModifyDate(mCurrentDate);
		double tSumPrem = tLCContSchema.getSumPrem()
				- Double.parseDouble(tSSRS.GetText(1, 1));
		tLCContSchema.setSumPrem(tSumPrem);
		tLCContSchema.setPaytoDate(mPayToDate);
		mLCManagerCom = tLCContSchema.getManageCom();
		mUpdateLCContSet.add(tLCContSchema);
	}

	/* 下面抽档后对表update后还原其的方法********************财务上的处理********************************** */

	private boolean UpdateLjtempfee() {

		String tLimit;
		tLimit = PubFun.getNoLimit(mLCManagerCom);
		tNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		String tSql = "select * from ljtempfee where tempfeeno = '?tempfeeno?'";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(tSql);
		sqlbv13.put("tempfeeno",mLJAPayPersonSet.get(1).getGetNoticeNo());
		logger.debug(tSql);
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv13);
		if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在收费到帐中查无此信息或此保单是旧系统转换，请确认!";
			this.mErrors.addOneError(tError);

			return false;

		}
		tLJTempFeeSchema = tLJTempFeeSet.get(1);
		tLJTempFeeSchema.setTempFeeNo(tNo);
		tLJTempFeeSchema.setPayMoney(-tLJTempFeeSet.get(1).getPayMoney());
		tLJTempFeeSchema.setTempFeeType("C");
		tLJTempFeeSchema.setModifyDate(mCurrentDate);
		tLJTempFeeSchema.setMakeDate(mCurrentDate);
		mUpdateLJTempFeeSet.add(tLJTempFeeSchema);
		return true;
	}

	private boolean UpdateLjtempfeeClass() {
		LJTempFeeClassSchema tLJTempFeeClassSchema = null;
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		String tSql = "select * from ljtempfeeClass where tempfeeno = '?tempfeeno?'";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		sqlbv14.sql(tSql);
		sqlbv14.put("tempfeeno",mLJAPayPersonSet.get(1).getGetNoticeNo());
		tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv14);
		if (tLJTempFeeClassSet == null || tLJTempFeeClassSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在收费到帐中查无此信息或此保单是旧系统转换，请确认!";
			this.mErrors.addOneError(tError);

			return false;

		}

		tLJTempFeeClassSchema = tLJTempFeeClassSet.get(1);
		tLJTempFeeClassSchema.setTempFeeNo(tNo);
		tLJTempFeeClassSchema.setPayMoney(-tLJTempFeeClassSet.get(1)
				.getPayMoney());
		tLJTempFeeClassSchema.setPayMode("C");
		tLJTempFeeClassSchema.setModifyDate(mCurrentDate);
		tLJTempFeeClassSchema.setMakeDate(mCurrentDate);
		mUpdateLJtempfeeClassSet.add(tLJTempFeeClassSchema);

		return true;
	}

	private void UpdateLjaPay() { // mUpdateLJAPaySet
		String tLimit = PubFun.getNoLimit(mLCManagerCom);
		mPayNo = PubFun1.CreateMaxNo("PayNo", tLimit);

		LJAPaySchema tLJAPaySchema = null;
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayDB tLJAPayDB = new LJAPayDB();
		String tSql = "select * from LJAPay where payno = '?payno?'";
		SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
		sqlbv15.sql(tSql);
		sqlbv15.put("payno",mLJAPayPersonSet.get(1).getPayNo());
		tLJAPaySet = tLJAPayDB.executeQuery(sqlbv15);
		tLJAPaySchema = tLJAPaySet.get(1);
		double tSumActuPayMoney = tLJAPaySet.get(1).getSumActuPayMoney();
		tLJAPaySchema.setSumActuPayMoney("-"+ tLJAPaySet.get(1).getSumActuPayMoney());
		tLJAPaySchema.setTaxAmount("-"+ tLJAPaySet.get(1).getTaxAmount());
		tLJAPaySchema.setNetAmount("-"+ tLJAPaySet.get(1).getNetAmount());
		tLJAPaySchema.setPayNo(mPayNo);
		tLJAPaySchema.setPayNo(mCurrentDate);
		tLJAPaySchema.setGetNoticeNo(tNo);
		mUpdateLJAPaySet.add(tLJAPaySchema);

	}

	private void UpdateLjaPayperson() { // mUpdateLJAPayPersonSet
		if (mOperator.equals("reHT")) // 当回退的是要处理的
		{
			String tLimit = PubFun.getNoLimit(mLCManagerCom);
			mPayNo = PubFun1.CreateMaxNo("PayNo", tLimit);

		}
		LJAPayPersonSchema tLJAPayPersonSchema = null;
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		String tSql = "select * from LJAPayPerson where payno = '?payno?'";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("payno", mLJAPayPersonSet.get(1).getPayNo());
		tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv16);
		for (int i = 1; i <= tLJAPayPersonSet.size(); i++) {
			tLJAPayPersonSchema = tLJAPayPersonSet.get(i);
			// asdfasdfasdfasdfasdfasdfasdfasdf

			tLJAPayPersonSchema.setSumActuPayMoney(-tLJAPayPersonSet.get(i).getSumActuPayMoney());
			tLJAPayPersonSchema.setSumDuePayMoney(-tLJAPayPersonSet.get(i).getSumDuePayMoney());
			tLJAPayPersonSchema.setTaxAmount(-tLJAPayPersonSet.get(i).getTaxAmount());
			tLJAPayPersonSchema.setNetAmount(-tLJAPayPersonSet.get(i).getNetAmount());
			tLJAPayPersonSchema.setPayNo(mPayNo);
			tLJAPayPersonSchema.setMakeDate(mCurrentDate);
			tLJAPayPersonSchema.setModifyDate(mCurrentDate);
			if (!mOperator.equals("reHT")) // 当不回退的时要处理
			{
				tLJAPayPersonSchema.setGetNoticeNo(tNo);
			} else if (mOperator.equals("reHT")) {
				tLJAPayPersonSchema.setEnterAccDate("");
				tLJAPayPersonSchema.setConfDate("");
			}
			mUpdateLJAPayPersonSet.add(tLJAPayPersonSchema);
		}
	}

	private void InsertLJAGet() {
		// 提取该笔合同在lccont的所有描述，在ljagt中插入相应的值
		LCContSchema tLCContSchema = null;
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		tLCContDB.getInfo();
		tLCContSchema = tLCContDB;
		// 提取该笔在ljapay中的值，mInsertLJAGetSet
		LJAPaySchema tLJAPaySchema = null;
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayDB tLJAPayDB = new LJAPayDB();
		String tSql = "select * from LJAPay where payno = '?payno?'";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(tSql);
		sqlbv17.put("payno",mLJAPayPersonSet.get(1).getPayNo());
		tLJAPaySet = tLJAPayDB.executeQuery(sqlbv17);
		tLJAPaySchema = tLJAPaySet.get(1);
		// 定义实付表。
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setAccName(mAccName);
		tLJAGetSchema.setBankAccNo(mAccNo);
		tLJAGetSchema.setBankCode(mBankCode);
		tLJAGetSchema.setActuGetNo(mPayNo);
		tLJAGetSchema.setOtherNo(mContNo);
		tLJAGetSchema.setOtherNoType("9");
		tLJAGetSchema.setSumGetMoney(tLJAPaySchema.getSumActuPayMoney());
		tLJAGetSchema.setTax(tLJAPaySchema.getTax());
		tLJAGetSchema.setTaxAmount(tLJAPaySchema.getTaxAmount());
		tLJAGetSchema.setNetAmount(tLJAPaySchema.getNetAmount());
		tLJAGetSchema.setGetNoticeNo(tLJAPaySchema.getGetNoticeNo());
		tLJAGetSchema.setPayMode(mPayMode);
		tLJAGetSchema.setBankOnTheWayFlag("0");
		tLJAGetSchema.setManageCom(tLCContSchema.getManageCom());
		tLJAGetSchema.setAgentCode(tLCContSchema.getAgentCode());
		tLJAGetSchema.setAgentGroup(tLCContSchema.getAgentGroup());
		tLJAGetSchema.setStartGetDate(PubFun.getCurrentDate());
		tLJAGetSchema.setAppntNo(tLCContSchema.getAppntNo());
		tLJAGetSchema.setSaleChnl(tLCContSchema.getSaleChnl());
		tLJAGetSchema.setShouldDate(PubFun.getCurrentDate());
		tLJAGetSchema.setOperator(mGlobalInput.Operator);
		tLJAGetSchema.setMakeDate(PubFun.getCurrentDate());
		tLJAGetSchema.setMakeTime(PubFun.getCurrentTime());
		tLJAGetSchema.setModifyDate(PubFun.getCurrentDate());
		tLJAGetSchema.setModifyTime(PubFun.getCurrentTime());

		mInsertLJAGetSet.add(tLJAGetSchema);

	}

	// 测试函数
	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "8632";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", "NJ020022341000647");
		tTransferData.setNameAndValue("PayToDate", "2005-9-22");
		tTransferData.setNameAndValue("GetnoticeNo", "3105100002943");
		tVData.add(tTransferData);
		tVData.add(tGlobalInput);
		XQChargeRollBackBL xqchargerollbackbl = new XQChargeRollBackBL();
		if (!xqchargerollbackbl.submitData(tVData, "reHT")) {
			logger.debug("no");
		} else {
			logger.debug("ok");

		}

	}
}
