/*
 * @(#)LCGrpContSignBL.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.finfee.NewGrpContFeeWithdrawBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保签单业务逻辑处理类-团体签单
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
public class LCGrpContSignBL {
private static Logger logger = Logger.getLogger(LCGrpContSignBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	// private VData mInputData;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private MMap mMapResult = new MMap();
	// private VData mResult = new VData();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	// *↓ liuhao 2005-4-18 * add *****************
	private LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
	// *↑ liuhao 2005-4-18 * add *****************

	// private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private HashMap mLJTempFeeMap = new HashMap();

	// 全局需要用到的计算日期
	private Date mFirstPayDate = null;
	private Date maxPayDate;
	private Date maxEnterAccDate;
	private Date maxPayEndDate;
	private String mCurrentDate;
	private String mCurrentTime;
	private String mContPlanCode;
	private boolean mISContPlan = false;

	// TransferData mTransferData ;
	private HashMap paytoDateMap = new HashMap();

	// 保费溢额
	private double mGrpContDiff = 0.00;

	private final int DB_ERROR = 100;
	double sumGrpContPrem = 0.000;
	double sumGrpContStandPrem = 0.000;
	HashMap PolNoMap = new HashMap();

	public LCGrpContSignBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		// mInputData = (VData) cInputData.clone();
		// this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!this.getInputData(cInputData)) {
			return false;
		}

		// 初始化系统当前日期
		// mCurrentDate = PubFun.getCurrentDate() ;
		// mCurrentTime = PubFun.getCurrentTime() ;

		// // 数据操作业务处理
		// if (this.dealData() == false)
		// return false;
		// 填充要签单的数据集
		if (!fillGrpCont()) {
			return false;
		}

		// 签单
		// logger.debug("测试............."+
		// mLCGrpContSet.get(1).getGrpContNo());
		if (!signGrpCont()) {
			// ↓ liuhao 2005-4-18 * add *****************
			// 签单后对锁定的保单合同进行解锁
			// 判断合同是否被锁定
			if (checkLockGrpCont(mLCGrpContSet.get(1))) {
				// 解除锁表状态
				this.ravelGrpCont(mLCGrpContSet.get(1));
			}
			// ↑ liuhao 2005-4-18 * add *****************
			return false;
		}

		// ↓ liuhao 2005-4-18 * add *****************
		// 签单后对锁定的保单合同进行解锁
		// 判断合同是否被锁定
		if (checkLockGrpCont(mLCGrpContSet.get(1))) {
			// 解除锁表状态
			this.ravelGrpCont(mLCGrpContSet.get(1));
		}
		// ↑ liuhao 2005-4-18 * add *****************
		return true;
	}

	/**
	 * 团体保单签单
	 * 
	 * @return boolean
	 */
	private boolean signGrpCont() {
		LCGrpContSchema signSchema = null;
		LCContSet tSignLCContSet = null;

		// for (int i = 1; i <= mLCGrpContSet.size(); i++)
		// {
		signSchema = mLCGrpContSet.get(1);

		mISContPlan = this.isContPlan(signSchema.getGrpContNo());

		// 是否已签单
		if ("1".equals(signSchema.getAppFlag())) {
			return true;
		}

		// 签单校验
		if (!checkGrpCont(signSchema)) {
			return false;
		}

		int cpFee = 0;
		if (this.mISContPlan) {
			cpFee = CheckPlanTempFee(signSchema);
		} else {
			cpFee = CheckTempFee(signSchema);
		}

		if (cpFee == this.DB_ERROR) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")交费信息查找错误!");
			return false;
		}
		if (cpFee == -1) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")交费不足或还有未到帐的交费!");
			return false;
		}
		if (cpFee == -2) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")查询个单险种信息失败!");
			return false;
		}
		if (cpFee == -3) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")查询暂交费信息失败!");
			return false;
		}

		// 判断合同是否被锁定
		if (checkLockGrpCont(signSchema)) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")已被锁定;");
			return false;
		} else { // 未锁定进行锁定操作
			// 锁定该合同
			if (!lockGrpCont(signSchema)) {
				CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
						+ ")锁定失败;");
				return false;
			}
		}

		/** 2005-03-19 先换可以换的号码* */
		// 未换号,则先换集体合同号，否则不用换号
		// logger.debug(signSchema.getGrpContNo());
		// logger.debug(signSchema.getProposalGrpContNo());
		// if
		// (signSchema.getGrpContNo().equals(signSchema.getProposalGrpContNo()))
		// {
		// //未换号，则先换集体保单号
		// String sql =
		// "select grppolno,GrpProposalNo from lcgrppol where grpcontno='" +
		// signSchema.getGrpContNo() + "' and uwflag not in ('1','a','z','5')";
		// ExeSQL exesql = new ExeSQL();
		// SSRS ssrs = exesql.execSQL(sql);
		// if (ssrs == null) {
		// CError.buildErr(this, "团单下没有相应的集体保单");
		// return false;
		// }
		// String limitNo = PubFun.getNoLimit(signSchema.getManageCom());
		// // newGrpContNo = PubFun1.CreateMaxNo("GRPCONTNO",
		// // limitNo);
		//
		// /**
		// * 集体单的保单号生成规则和个单保持一致
		// * edit by:chenrong
		// * edit date:2006.09.12
		// */
		// // String tReturn = PubFun1.CreateMaxNo("GRPCONTNO", "86"); //
		// String tReturn = PubFun1.CreateMaxNo("CONTNO", "86"); //
		// newGrpContNo = PubFun1.creatVerifyDigit(tReturn);
		//
		// MMap tmpMap = new MMap();
		// //险种单相关
		// for (int i = 1; i <= ssrs.getMaxRow(); i++) {
		// String oldProposalNo = ssrs.GetText(i, 1);
		// String newGrpPolNo = PubFun1.CreateMaxNo("GrpPolNo", limitNo);
		// tmpMap.add(prepareOtherGrpPolSql(signSchema,
		// newGrpContNo, oldProposalNo,
		// newGrpPolNo));
		// }
		// //合同相关
		// tmpMap.add(prepareOtherUpdateGrpCont(newGrpContNo,
		// signSchema.getGrpContNo()));
		//
		// PubSubmit ps = new PubSubmit();
		// VData sd = new VData();
		// sd.add(tmpMap);
		// if (!ps.submitData(sd, null)) {
		// CError.buildErr(this, "合同号换号保存错误");
		// return false;
		// }
		// //更新成功
		// signSchema.setGrpContNo(newGrpContNo);
		// }
		// else {
		// newGrpContNo = signSchema.getGrpContNo();
		// }
		//
		// 查询获取所有合同单( 个单 )
		tSignLCContSet = queryLCContSet(signSchema);
		if (this.mErrors.needDealError()) {
			logger.debug("签单个单合同查询错误");
			return false;
		}
		if (tSignLCContSet != null && tSignLCContSet.size() > 0) {
			// 集体单签单
			boolean tOneResult = SignOneGrpCont(signSchema, tSignLCContSet);
			if (!tOneResult) {
				if (!mErrors.needDealError()) {
					CError.buildErr(this, "团体合同(" + signSchema.getGrpContNo()
							+ "签单发生错误!");
				}
				return false;
			}
		}

		// 更新整个团单相关信息
		if (!dealOneGrpCont(signSchema)) {
			if (!mErrors.needDealError()) {
				CError.buildErr(this, "团体合同(" + signSchema.getGrpContNo()
						+ "签单发生错误!");
			}
			// logger.debug(this.mErrors.getErrContent());
			return false;
		}
		return true;
	}

	/**
	 * 集体保单签单
	 * 
	 * @param signSchema
	 *            LCGrpContSchema
	 * @param tSignLCContSet
	 *            LCContSet
	 * @param newGrpContNo
	 *            String
	 * @return boolean
	 */
	private boolean SignOneGrpCont(LCGrpContSchema signSchema,
			LCContSet tSignLCContSet) {

		boolean signResult = true;
		boolean result = true;
		if (tSignLCContSet != null && tSignLCContSet.size() > 0) {
			for (int i = 1; i <= tSignLCContSet.size(); i++) {
				// 调用合同签单
				LCContSignBL tLCContSignBL = new LCContSignBL();
				if ("1".equals(tSignLCContSet.get(i).getUWFlag())) {
					// 拒保跳出
					continue;
				}

				LCContSet signSet = new LCContSet();
				signSet.add(tSignLCContSet.get(i));
				VData tInput = new VData();
				tInput.add(mGlobalInput);
				tInput.add(signSet);
				// tInput.add( mTransferData );
				signResult = tLCContSignBL.submitData(tInput, "");
				if (!signResult) {
					this.mErrors.copyAllErrors(tLCContSignBL.mErrors);
					result = false;
				} else {

					// 处理签单
					VData contData = new VData();
					contData.add(mGlobalInput);
					MMap tmpMap = (MMap) tLCContSignBL.mVResult
							.getObjectByObjectName("MMap", 0);
					if (tmpMap == null || tmpMap.keySet().size() <= 0) {
						// 没有要签单的数据，准备下一个合同
						return true;
					}
					contData.add(tmpMap);
					PubSubmit pubSubmit = new PubSubmit();
					signResult = pubSubmit.submitData(contData, "");
					if (!signResult) {
						if (pubSubmit.mErrors.getErrorCount() > 0) {
							this.mErrors.copyAllErrors(pubSubmit.mErrors);
						} else {
							CError.buildErr(this, "合同签单["
									+ signSet.get(i).getContNo() + "]保存有误!");

						}
						result = false;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 处理与团体合同有关的相关表
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param inNewGrpContNo
	 *            String
	 * @return boolean
	 */
	private boolean dealOneGrpCont(LCGrpContSchema tLCGrpContSchema) {
		Vector sqlVec = new Vector();
		MMap tmpMap = new MMap();
		ExeSQL tExeSQL = new ExeSQL();
		int notSignCount = 0;
		StringBuffer tSBql = null;

		try {
			// conn.setAutoCommit( false );
			// 查询LCCont的更新情况
			// 2005-05-11 朱向峰修改String方法
			tSBql = new StringBuffer(128);
			tSBql.append("select count(*) from lccont where grpcontno ='");
			tSBql.append(tLCGrpContSchema.getGrpContNo());
			tSBql
					.append("' and (uwflag in ('4','9' ) and appflag!='1' or ( approveflag is null ) or (uwflag is null) )");
			try {
				notSignCount = Integer.parseInt(tExeSQL.getOneValue(tSBql
						.toString()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// 还有签单的个单合同,不进行团体签单
			if (notSignCount > 0) {
				CError.buildErr(this, "团单合同(" + tLCGrpContSchema.getGrpContNo()
						+ ")还有个单合同未签单完毕,请检查个单合同，险种保单信息");
				return false;
			}

			sqlVec.clear();
			mCurrentDate = PubFun.getCurrentDate();
			mCurrentTime = PubFun.getCurrentTime();
			String tPayNo = PubFun1.CreateMaxNo("PAYNO", PubFun
					.getNoLimit(tLCGrpContSchema.getManageCom()));

			/**
			 * 集体单的保单号生成规则和个单保持一致 edit by:chenrong edit date:2006.09.12
			 */
			String tReturn = PubFun1.CreateMaxNo("CONTNO", "86"); //
			String tNewGrpContNo = PubFun1.creatVerifyDigit(tReturn);

			// 更新集体险种保单
			MMap tGrpMap = null;
			if (!this.mISContPlan) {
				tGrpMap = this.dealGrpPol(tLCGrpContSchema, tNewGrpContNo,
						tPayNo);
			} else {
				// 产品组合集体险种处理
				tGrpMap = this.dealPlanGrpPol(tLCGrpContSchema, tNewGrpContNo,
						tPayNo);
			}
			if (tGrpMap == null) {
				return false;
			}
			tmpMap.add(tGrpMap);

			// 处理团单退费
			if (tLCGrpContSchema.getDif() > 0.0
					&& "1".equals(tLCGrpContSchema.getOutPayFlag())) {
				MMap tBackFeeMap = dealContBackTempFee(tLCGrpContSchema);
				if (tBackFeeMap == null) {
					return false;
				}
				tmpMap.add(tBackFeeMap);
			}

			// 总实收
			MMap jaPayMap = prepareLJAPAY(tLCGrpContSchema, tNewGrpContNo,
					tPayNo);
			tmpMap.add(jaPayMap);

			// 修改业务员的服务起期
			tmpMap.put("update LACommisionDetail set startserdate='"
					+ tLCGrpContSchema.getCValiDate() + "',ModifyDate='"
					+ PubFun.getCurrentDate() + "',ModifyTime='"
					+ PubFun.getCurrentTime() + "' where GrpContNo='"
					+ tLCGrpContSchema.getGrpContNo() + "'", "UPDATE");

			// sqlVec.clear();
			tSBql = new StringBuffer(256);
			tSBql.append("update lcgrpcont set  grpcontno='");
			tSBql.append(tLCGrpContSchema.getGrpContNo());
			tSBql.append("',prem=");
			tSBql.append(tLCGrpContSchema.getPrem());
			tSBql.append(",Amnt=");
			tSBql.append(tLCGrpContSchema.getAmnt());
			tSBql.append(",sumprem=");
			tSBql.append(tLCGrpContSchema.getSumPrem());
			tSBql.append(",dif=");
			tSBql.append(this.mGrpContDiff);
			tSBql.append(",customgetpoldate='");
			tSBql.append(mCurrentDate);
			tSBql.append("',getpoldate='");
			tSBql.append(mCurrentDate);
			tSBql.append("',getpoltime='");
			tSBql.append(mCurrentTime);
			tSBql.append("',appflag='1', signdate='");
			tSBql.append(mCurrentDate);
			tSBql.append("',signTime ='");
			tSBql.append(mCurrentTime);
			tSBql.append("',signCom ='");
			tSBql.append(this.mGlobalInput.ManageCom);
			tSBql.append("' where grpcontno='");
			tSBql.append(tLCGrpContSchema.getGrpContNo());
			tSBql.append("'");

			tmpMap.put(tSBql.toString(), "UPDATE");

			// 更新结算表时间
			tSBql = new StringBuffer(256);
			tSBql.append("update lxbalance set  conflag='1',");
			tSBql.append("confdate='");
			tSBql.append(mCurrentDate);
			tSBql.append("' where proposalno='");
			tSBql.append(tLCGrpContSchema.getPrtNo());
			tSBql.append("'");
			tmpMap.put(tSBql.toString(), "UPDATE");

			// 更新签单时间
			tmpMap.add(prepareUpdateSignDate(tLCGrpContSchema.getGrpContNo(),
					tPayNo));

			// 更新集体保单号
			tmpMap.add(this.changeGrpContNo(tLCGrpContSchema, tNewGrpContNo));

			// 提交保存
			VData contData = new VData();
			contData.add(mGlobalInput);
			contData.add(tmpMap);
			PubSubmit pubSubmit = new PubSubmit();
			if (!pubSubmit.submitData(contData, "")) {
				CError.buildErr(this, "合同签单更新有误", pubSubmit.mErrors);
				return false;
			}

		} catch (Exception ex2) {
		}

		return true;
	}

	/**
	 * 更新集体险种保单
	 * 
	 * @param tLCGrpContSchema
	 *            集体保单
	 * @param inNewGrpContNo
	 *            保单号
	 * @param tPayNo
	 *            String
	 * @return MMap
	 */
	private MMap dealGrpPol(LCGrpContSchema tLCGrpContSchema,
			String cNewGrpContNo, String tPayNo) {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		StringBuffer tSBql = null;
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolSet tMoreGrpPolSet = new LCGrpPolSet();
		LCGrpPolSet tLackGrpPolSet = new LCGrpPolSet();
		LCGrpPolSet tBalanceGrpPolSet = new LCGrpPolSet();
		double sumPrem = 0.000;
		double sumAmnt = 0.000;
		double sumAllPrem = 0.000;
		double sumAllAmnt = 0.000;
		double polSumPrem = 0.000; // 保单总实付
		double grpcontsumPrem = 0.000;
		mCurrentDate = PubFun.getCurrentDate();
		mCurrentTime = PubFun.getCurrentTime();
		MMap tTmpMap = new MMap();
		String outPayFlag = tLCGrpContSchema.getOutPayFlag();

		tSBql = new StringBuffer(128);
		tSBql.append("select * from lcgrppol where grpcontno='");
		tSBql.append(tLCGrpContSchema.getGrpContNo());
		tSBql.append("' and uwflag not in ('1','5','a','z') order by grppolno");
		tLCGrpPolSet = tLCGrpPolDB.executeQuery(tSBql.toString());

		try {
			if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
				this.mErrors.addOneError("没有要签单的集体险种单!");
				return null;
			}
			// 对每个集体保单更新，同时计算统计值到lcgrpcont表中
			for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
				sumPrem = 0;
				sumAmnt = 0;
				polSumPrem = 0;

				LCGrpPolSchema grpPolSchema = tLCGrpPolSet.get(i).getSchema();
				// 求总保费,总保额,更新到LCGrpPol
				tSBql = new StringBuffer(128);
				tSBql
						.append("select nvl(sum(prem),0),nvl(sum(amnt),0),nvl(sum(sumprem),0)");
				tSBql.append(" from lcpol where grpcontno='");
				tSBql.append(tLCGrpContSchema.getGrpContNo());
				tSBql.append("' and appflag='1' and grppolno='");
				tSBql.append(grpPolSchema.getGrpPolNo());
				tSBql.append("'");
				tSSRS = tExeSQL.execSQL(tSBql.toString());

				if (tSSRS != null && tSSRS.getMaxRow() > 0) {
					sumPrem = Double.parseDouble(tSSRS.GetText(1, 1));
					sumAmnt = Double.parseDouble(tSSRS.GetText(1, 2));
					polSumPrem = Double.parseDouble(tSSRS.GetText(1, 3));
				}
				sumAllPrem += sumPrem;
				sumAllAmnt += sumAmnt;
				grpcontsumPrem += polSumPrem;

				grpPolSchema.setPrem(sumPrem);
				grpPolSchema.setAmnt(sumAmnt);
				grpPolSchema.setSumPrem(polSumPrem);

				// 保存新旧集体单号
				// PolNoMap.put(StrTool.cTrim(lpGrpPolNo), newGrpPolNo);
				double sumTmpPay = 0.000;
				// 暂交费
				String tRiskCode = grpPolSchema.getRiskCode();
				LJTempFeeSet grpTempFeeSet = queryTempFeeSet(tLCGrpContSchema
						.getPrtNo(), tRiskCode);

				if (grpTempFeeSet == null) {
					// 本险种没有暂交费信息
					grpTempFeeSet = new LJTempFeeSet();
				}

				sumTmpPay = LCGrpContSignBL.sumPayMoney(grpTempFeeSet);
				// 更新暂交费信息
				tTmpMap.add(prepareUpdateTempFeeSet(grpTempFeeSet,
						cNewGrpContNo, tLCGrpContSchema));

				tTmpMap.add(dealPolFirstPayDate(grpTempFeeSet, grpPolSchema
						.getGrpPolNo()));

				// 缓存暂交费信息
				// if (grpTempFeeSet.size() > 0) {
				// this.cacheTempFee(grpPolSchema.getRiskCode(),
				// grpTempFeeSet.get(1));
				// }

				// 保费差额
				double tmpdiff = sumTmpPay - sumPrem;
				logger.debug("sumTmpPay=" + sumTmpPay);
				logger.debug("sumPrem=" + sumPrem);
				logger.debug("tmpdiff=" + sumPrem);
				grpPolSchema.setDif(PubFun.setPrecision(tmpdiff, "0.00"));
				if ("1".equals(outPayFlag)) {
					grpPolSchema.setOutPayFlag("1");
					// 划分富余与不足的保费
					if (sumTmpPay >= sumPrem) {
						if (sumTmpPay > sumPrem) {
							// 溢交退费
							tMoreGrpPolSet.add(grpPolSchema);
						} else {
							// 移交续期交费
							tBalanceGrpPolSet.add(grpPolSchema);
						}
					} else {
						tLackGrpPolSet.add(grpPolSchema);
					}
				} else {
					tBalanceGrpPolSet.add(grpPolSchema);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		tLCGrpContSchema.setDif(mGrpContDiff);
		tLCGrpContSchema.setPrem(sumAllPrem);
		tLCGrpContSchema.setAmnt(sumAllAmnt);
		tLCGrpContSchema.setSumPrem(grpcontsumPrem);

		// 均匀团单下各集体险种保单的保费
		MMap balMap = balanceFee(tLCGrpContSchema, tMoreGrpPolSet,
				tLackGrpPolSet, cNewGrpContNo);
		if (balMap == null) {
			CError.buildErr(this, "合同总保费金额不足，不能完成签单");
			return null;
		}
		tTmpMap.add(balMap);

		// 集体保单本身更新
		MMap grpPolSet1 = prepareNewGrpPolSet(tMoreGrpPolSet, cNewGrpContNo,
				tPayNo);
		tTmpMap.add(grpPolSet1);
		MMap grpPolSet2 = prepareNewGrpPolSet(tLackGrpPolSet, cNewGrpContNo,
				tPayNo);
		tTmpMap.add(grpPolSet2);
		MMap grpPolSet3 = prepareNewGrpPolSet(tBalanceGrpPolSet, cNewGrpContNo,
				tPayNo);
		tTmpMap.add(grpPolSet3);

		return tTmpMap;
	}

	/**
	 * 处理产品组合集体险种保单 Add by:chenrong Date:2006.12.08
	 * 
	 * @param tLCGrpContSchema
	 *            集体保单
	 * @param inNewGrpContNo
	 *            保单号
	 * @param tPayNo
	 *            String
	 * @return MMap
	 */
	private MMap dealPlanGrpPol(LCGrpContSchema tLCGrpContSchema,
			String cNewGrpContNo, String tPayNo) {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		StringBuffer tSBql = null;
		LCGrpPolSet tGrpPolSet = new LCGrpPolSet();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		double sumPrem = 0.000;
		double sumAmnt = 0.000;
		double sumAllPrem = 0.000;
		double sumAllAmnt = 0.000;
		double polSumPrem = 0.000; // 保单总实付
		double grpcontsumPrem = 0.000;
		mCurrentDate = PubFun.getCurrentDate();
		mCurrentTime = PubFun.getCurrentTime();
		MMap tTmpMap = new MMap();
		String outPayFlag = tLCGrpContSchema.getOutPayFlag();

		// 求总保费,总保额,更新到LCGrpPol
		tSBql = new StringBuffer(128);
		tSBql.append("select * from lcgrppol where grpcontno='");
		tSBql.append(tLCGrpContSchema.getGrpContNo());
		tSBql.append("' and uwflag not in ('1','5','a','z') order by grppolno");
		tGrpPolSet = tLCGrpPolDB.executeQuery(tSBql.toString());

		try {
			tSSRS = tExeSQL.execSQL(tSBql.toString());
			if (tGrpPolSet == null || tGrpPolSet.size() <= 0) {
				this.mErrors.addOneError("没有要签单的集体险种单!");
				return null;
			}

			// 对每个集体保单更新，同时计算统计值到lcgrpcont表中
			for (int i = 1; i <= tGrpPolSet.size(); i++) {
				sumPrem = 0;
				sumAmnt = 0;
				polSumPrem = 0;

				LCGrpPolSchema grpPolSchema = tGrpPolSet.get(i).getSchema();
				// 求总保费,总保额,更新到LCGrpPol
				tSBql = new StringBuffer(128);
				tSBql
						.append("select nvl(sum(prem),0),nvl(sum(amnt),0),nvl(sum(sumprem),0)");
				tSBql.append(" from lcpol where grpcontno='");
				tSBql.append(tLCGrpContSchema.getGrpContNo());
				tSBql.append("' and appflag='1' and grppolno='");
				tSBql.append(grpPolSchema.getGrpPolNo());
				tSBql.append("'");
				tSSRS = tExeSQL.execSQL(tSBql.toString());

				if (tSSRS != null && tSSRS.getMaxRow() > 0) {
					sumPrem = Double.parseDouble(tSSRS.GetText(1, 1));
					sumAmnt = Double.parseDouble(tSSRS.GetText(1, 2));
					polSumPrem = Double.parseDouble(tSSRS.GetText(1, 3));
				}

				sumAllPrem += sumPrem;
				sumAllAmnt += sumAmnt;
				grpcontsumPrem += polSumPrem;

				grpPolSchema.setPrem(sumPrem);
				grpPolSchema.setAmnt(sumAmnt);
				grpPolSchema.setSumPrem(polSumPrem);

				if ("1".equals(outPayFlag)) {
					grpPolSchema.setOutPayFlag("1");
				}
				tLCGrpPolSet.add(grpPolSchema);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// 保费差额
		tLCGrpContSchema.setPrem(sumAllPrem);
		tLCGrpContSchema.setDif(mGrpContDiff);
		tLCGrpContSchema.setAmnt(sumAllAmnt);
		tLCGrpContSchema.setSumPrem(grpcontsumPrem);

		// 暂交费
		LJTempFeeSet grpTempFeeSet = queryTempFeeSet(tLCGrpContSchema
				.getPrtNo(), this.mContPlanCode);
		if (grpTempFeeSet == null || grpTempFeeSet.size() <= 0) {
			// 产品组合没有暂交费信息
			grpTempFeeSet = new LJTempFeeSet();
		}

		// 更新个人险种表的首期交费日期
		tTmpMap.add(this.dealPolFirstPayDate(grpTempFeeSet, tLCGrpContSchema
				.getPrtNo()));

		// 更新暂交费信息
		tTmpMap.add(prepareUpdateTempFeeSet(grpTempFeeSet, cNewGrpContNo,
				tLCGrpContSchema));

		// 集体保单本身更新
		MMap tGrpPolSetMap = prepareNewGrpPolSet(tLCGrpPolSet, cNewGrpContNo,
				tPayNo);
		tTmpMap.add(tGrpPolSetMap);

		return tTmpMap;
	}

	/**
	 * 更新个人险种保单首期交费日期 Add by:chenrong Data:2006.12.08 10:30
	 * 
	 * @param cTempFeeSet
	 *            LJTempFeeSet
	 * @param cNo
	 *            String
	 * @return MMap
	 */
	private MMap dealPolFirstPayDate(LJTempFeeSet cTempFeeSet, String cNo) {
		MMap tTmpMap = new MMap();
		LJTempFeeSet tLJTempFeeSet = cTempFeeSet;
		String tNo = cNo;
		String tSQL = "";
		FDate fDate = new FDate();
		Date tFirstPayDate = null;
		int n = tLJTempFeeSet.size();

		for (int i = 1; i <= n; i++) {
			LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i).getSchema();

			// 取首次交费日期
			Date payDate = fDate.getDate(tLJTempFeeSchema.getPayDate());
			if (tFirstPayDate == null || payDate.before(tFirstPayDate)) {
				tFirstPayDate = payDate;
			}
		}

		if (this.mISContPlan) {
			// 对于产品组合的保单，交费都是按组合交费，所以首期交费方式一致！
			// 进行统一修改
			tSQL = "update lcpol set firstpaydate='"
					+ fDate.getString(tFirstPayDate) + "' where prtno='" + tNo
					+ "'";
		} else {
			// 对于非产品组合的保单，交费按不同险种交费，所以首期交费方式可能不一致！
			// 修改个人险种保单首期交费日期要按险种进行修改
			tSQL = "update lcpol set firstpaydate='"
					+ fDate.getString(tFirstPayDate) + "' where grppolno='"
					+ tNo + "'";
		}

		tTmpMap.put(tSQL, "UPDATE");

		return tTmpMap;
	}

	/**
	 * 处理暂交费退费 Add by:chenrong Date:2006.12.07
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return MMap
	 */
	private MMap dealContBackTempFee(LCGrpContSchema tLCGrpContSchema) {
		MMap tTempMap = new MMap();
		NewGrpContFeeWithdrawBL withdrawBL = new NewGrpContFeeWithdrawBL();
		VData tData = new VData();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.add(tLCGrpContSchema);
		TransferData tTf = new TransferData();
		tTf.setNameAndValue("GrpContSign", "1");
		tData.add(tLCGrpContSet);
		tData.add(tTf);
		tData.add(this.mGlobalInput);
		if (!withdrawBL.submitData(tData)) {
			CError.buildErr(this, "处理暂交费退费失败!");
			this.mErrors.copyAllErrors(withdrawBL.mErrors);
			return null;
		}

		VData rData = withdrawBL.getVResult();

		LJAGetSet getSet = (LJAGetSet) rData.getObjectByObjectName("LJAGetSet",
				0);
		if (getSet == null || getSet.size() <= 0) {
			CError.buildErr(this, "暂交费退费生成失败!");
			return null;
		}
		tTempMap.put(getSet, "INSERT");
		LJAGetOtherSet getOtherSet = (LJAGetOtherSet) rData
				.getObjectByObjectName("LJAGetOtherSet", 0);
		if (getOtherSet == null || getOtherSet.size() <= 0) {
			CError.buildErr(this, "暂交费退费生成失败!");
			return null;
		}
		tTempMap.put(getOtherSet, "INSERT");

		LOPRTManagerSet tLOPRTManagerSet = (LOPRTManagerSet) rData
				.getObjectByObjectName("LOPRTManagerSet", 0);
		if (tLOPRTManagerSet == null || tLOPRTManagerSet.size() <= 0) {
			CError.buildErr(this, "暂交费退费生成失败-没有生成打印总表信息!");
			return null;
		}
		tTempMap.put(tLOPRTManagerSet, "INSERT");

		return tTempMap;
	}

	/**
	 * 更新暂交费信息
	 * 
	 * @param tmpFeeSet
	 *            LJTempFeeSet
	 * @param newGrpContNo
	 *            String
	 * @param grppolSchema
	 *            LCGrpPolSchema
	 * @return MMap
	 */
	private MMap prepareUpdateTempFeeSet(LJTempFeeSet tmpFeeSet,
			String newGrpContNo, LCGrpContSchema grpContSchema) {
		MMap tmpMap = new MMap();
		String confDate = mCurrentDate;
		// String confTime = mCurrentTime;
		// String sql = "";
		FDate fDate = new FDate();
		Date tFirstPayDate = null;
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		for (int i = 1; i <= tmpFeeSet.size(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = tmpFeeSet.get(i);
			tFirstPayDate = fDate.getDate(tLJTempFeeSchema.getPayDate());
			if (maxEnterAccDate == null
					|| maxEnterAccDate.before(tFirstPayDate)) {
				maxEnterAccDate = tFirstPayDate;
			}
			if (mFirstPayDate == null || tFirstPayDate.before(mFirstPayDate)) {
				mFirstPayDate = tFirstPayDate;
			}
			if (maxPayDate == null || maxPayDate.before(tFirstPayDate)) {
				maxPayDate = tFirstPayDate;
			}
			tLJTempFeeSchema.setAgentCode(grpContSchema.getAgentCode());
			tLJTempFeeSchema.setAgentCom(grpContSchema.getAgentCom());
			tLJTempFeeSchema.setAgentGroup(grpContSchema.getAgentGroup());
			tLJTempFeeSchema.setAgentType(grpContSchema.getAgentType());
			tLJTempFeeSchema.setConfDate(confDate);
			// tLJTempFeeSchema.setConfMakeDate(confDate);
			tLJTempFeeSchema.setConfFlag("1");
			// tLJTempFeeSchema.setConfMakeTime(confTime);
			tLJTempFeeSchema.setSaleChnl(grpContSchema.getSaleChnl());
			tLJTempFeeSchema.setPolicyCom(grpContSchema.getManageCom());
			tLJTempFeeSchema.setAPPntName(grpContSchema.getGrpName());
			tLJTempFeeSchema.setOtherNo(newGrpContNo);
			// tLJTempFeeSchema.setOtherNoType("7");
			tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

			tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
			if (tLJTempFeeClassSet != null && tLJTempFeeClassSet.size() > 0) {
				for (int j = 1; j <= tLJTempFeeClassSet.size(); j++) {
					LJTempFeeClassSchema tSchema = tLJTempFeeClassSet.get(j);
					// tSchema.setAccName(grppolSchema.getAcc);
					tSchema.setApproveDate(confDate);
					tSchema.setConfDate(confDate);
					tSchema.setAppntName(grpContSchema.getGrpName());
					// tSchema.setConfMakeDate(confDate);
					// tSchema.setConfMakeTime(confTime);
					tSchema.setConfFlag("1");
					tSchema.setModifyDate(PubFun.getCurrentDate());
					tSchema.setModifyTime(PubFun.getCurrentTime());

				}
				tmpMap.put(tLJTempFeeClassSet, "UPDATE");
			}
		}
		tmpMap.put(PubFun.copySchemaSet(tmpFeeSet), "UPDATE");
		return tmpMap;
	}

	/**
	 * 更新集体合同号
	 * 
	 * @param grpPolSet
	 *            LCGrpPolSet
	 * @param newGrpContNo
	 *            String
	 * @param tPayNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareNewGrpPolSet(LCGrpPolSet grpPolSet,
			String newGrpContNo, String tPayNo) {
		MMap tmpmap = new MMap();
		LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
		for (int i = 1; i <= grpPolSet.size(); i++) {
			// String newGrpPolNo =
			// this.getNewPolNo(StrTool.cTrim(grpPolSet.get(i).
			// getGrpPolNo()));
			// tmpmap.put("delete from lcgrppol where grppolno='" +
			// grpPolSet.get(i).getGrpPolNo() + "'", "DELETE");

			// 更新暂交费信息
			MMap APayMap = dealGrpAPay(grpPolSet.get(i), tPayNo, newGrpContNo);

			//
			grpPolSet.get(i).setPayEndDate(maxPayEndDate);

			// 从缓存中找改险种下一次交费日期
			grpPolSet.get(i).setPaytoDate(
					(String) paytoDateMap.get(grpPolSet.get(i).getRiskCode()));

			grpPolSet.get(i).setFirstPayDate(mFirstPayDate);

			// grpPolSet.get(i).setGrpPolNo(newGrpPolNo);
			// grpPolSet.get(i).setGrpContNo(newGrpContNo);
			grpPolSet.get(i).setAppFlag("1");
			grpPolSet.get(i).setState("00019999");

			// ---------------------------------Beg
			// 添加团单状态信息
			// add by:chenrong
			// date:2006.6.6
			LCGrpContStateSchema tLGrpCContStateSchema = new LCGrpContStateSchema();
			tLGrpCContStateSchema.setGrpContNo(newGrpContNo);
			tLGrpCContStateSchema.setGrpPolNo(grpPolSet.get(i).getGrpPolNo());
			tLGrpCContStateSchema.setStateType("Available");
			tLGrpCContStateSchema.setState("0");
			tLGrpCContStateSchema.setStartDate(grpPolSet.get(i).getCValiDate());
			tLGrpCContStateSchema.setOperator(mGlobalInput.Operator);
			tLGrpCContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLGrpCContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLGrpCContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLGrpCContStateSchema.setModifyTime(PubFun.getCurrentTime());
			tLCGrpContStateSet.add(tLGrpCContStateSchema);
			// ---------------------------------End
			tmpmap.add(APayMap);
		}
		tmpmap.put(grpPolSet, "UPDATE");
		tmpmap.put(tLCGrpContStateSet, "INSERT");
		return tmpmap;
	}

	/**
	 * 均匀集体单保费,因为前面已经有不足保费条件的过滤，因此这里的暂交费总和肯定不小于应交保费
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param moreGrpPolSet
	 *            LCGrpPolSet
	 * @param lackGrpPolSet
	 *            LCGrpPolSet
	 * @param inNewGrpContNo
	 *            String
	 * @return MMap
	 */
	private MMap balanceFee(LCGrpContSchema tLCGrpContSchema,
			LCGrpPolSet moreGrpPolSet, LCGrpPolSet lackGrpPolSet,
			String inNewGrpContNo) {
		MMap tmpMap = new MMap();
		if (lackGrpPolSet.size() <= 0) {
			logger.debug("没有少交保费的保单");
		}
		if (moreGrpPolSet.size() <= 0) {
			logger.debug("没有保费多余的保单");
		}
		int redundantPos = 1;
		int lackPos = 1;
		double left = 0.000;
		LCGrpPolSchema lackGrpPol = null;
		LCGrpPolSchema redundantGrpPolSchema = null;
		if (lackGrpPolSet.size() > 0 && moreGrpPolSet.size() > 0) {
			// String tPaySerialNo =
			// PubFun1.CreateMaxNo("PAYNO",PubFun.getNoLimit(mGlobalInput.ComCode));

			double adjustMoney = 0.000; // 用以调配的money

			// 以富余补不足
			while (lackPos <= lackGrpPolSet.size()
					&& redundantPos <= moreGrpPolSet.size()) {
				if (left >= 0.0) {
					lackGrpPol = lackGrpPolSet.get(lackPos);
				}
				if (left <= 0.0) {
					redundantGrpPolSchema = moreGrpPolSet.get(redundantPos);
				}

				double lackmoney = lackGrpPol.getDif();
				double redundantmoney = redundantGrpPolSchema.getDif();

				left = redundantmoney + lackmoney;
				// logger.debug("adjustMoney:" + adjustMoney);

				if (left >= 0.0) { // 富余足以加给不足
					adjustMoney = lackmoney;
					tmpMap.add(createTempFee(redundantGrpPolSchema, lackGrpPol,
							adjustMoney, inNewGrpContNo));
					lackGrpPol.setDif(0);
					redundantGrpPolSchema.setDif(redundantGrpPolSchema.getDif()
							+ adjustMoney);
					lackPos++;
				} else { // 富余不够加给不足
					// 把富足的全部分给不足
					adjustMoney = redundantmoney;
					tmpMap.add(createTempFee(redundantGrpPolSchema, lackGrpPol,
							adjustMoney, inNewGrpContNo));
					redundantGrpPolSchema.setDif(0);
					lackGrpPol.setDif(lackGrpPol.getDif() + adjustMoney);
					redundantPos++;
				}

			}
		}
		// 还有少交保费的保单,要生成催收?
		if (lackPos < lackGrpPolSet.size()) {
			logger.debug("还有少交保费的保单,要生成催收");
			return null;
		}

		// 还有有保费多余的保单,要生成退费
		if (redundantPos > 0 && redundantPos <= moreGrpPolSet.size()) {
			// LCGrpPolSet withdrawLCGrpPolSet = new LCGrpPolSet();
			//
			logger.debug("还有有保费多余的保单,要生成退费");
			double SumDiff = 0.000;
			for (int t = redundantPos; t <= moreGrpPolSet.size(); t++) {
				redundantGrpPolSchema = moreGrpPolSet.get(t);
				redundantGrpPolSchema.setDif(0);
			}
		}

		return tmpMap;
	}

	private static void replaceOtherNo(LJAGetOtherSet otherSet,
			String newOtherNo) {
		for (int i = 1; i <= otherSet.size(); i++) {
			otherSet.get(i).setOtherNo(newOtherNo);
			// otherSet.get(i).setConfDate( null );
		}
	}

	private void replaceOtherNo(LJAGetSet agetSet, String newOtherNo) {
		for (int i = 1; i <= agetSet.size(); i++) {
			agetSet.get(i).setOtherNo(newOtherNo);
			// agetSet.get(i).setConfDate( mCurrentDate );
			agetSet.get(i).setShouldDate(mCurrentDate);
		}
	}

	/**
	 * 根据保单生成一张新的暂交费表
	 * 
	 * @param grpmorePolSchema
	 *            LCGrpPolSchema
	 * @param grplackPolSchema
	 *            LCGrpPolSchema
	 * @param money
	 *            double
	 * @param inNewGrpContNo
	 *            String
	 * @return MMap
	 */
	private MMap createTempFee(LCGrpPolSchema grpmorePolSchema,
			LCGrpPolSchema grplackPolSchema, double money, String inNewGrpContNo) {
		MMap tmpMap = new MMap();
		LJTempFeeSchema srcTempFeeSchema = (LJTempFeeSchema) this
				.getTempFee(grpmorePolSchema.getRiskCode());
		if (srcTempFeeSchema == null) {
			CError.buildErr(this, "没有查找到可用于借的暂交费");
			return null;
		}
		String confDate = mCurrentDate;
		String confTime = mCurrentTime;
		PubFun.setPrecision(money, "0.00");
		if (money < 0) {
			money = -money;
		}

		// 创建借的科目
		LJTempFeeSchema tjieSchema = new LJTempFeeSchema();
		tjieSchema.setSchema(srcTempFeeSchema);
		tjieSchema.setTempFeeType("9");
		tjieSchema.setRiskCode(grplackPolSchema.getRiskCode());
		tjieSchema.setPayMoney(money);
		tjieSchema.setMakeDate(confDate);
		tjieSchema.setMakeTime(confTime);
		tjieSchema.setModifyDate(PubFun.getCurrentDate());
		tjieSchema.setModifyTime(PubFun.getCurrentTime());

		// 创建贷的科目
		LJTempFeeSchema tdaiSchema = new LJTempFeeSchema();
		tdaiSchema.setSchema(srcTempFeeSchema);
		tdaiSchema.setTempFeeType("8");
		tdaiSchema.setRiskCode(grplackPolSchema.getRiskCode());
		tdaiSchema.setPayMoney(money);
		tdaiSchema.setMakeDate(confDate);
		tdaiSchema.setMakeTime(confTime);
		tdaiSchema.setModifyDate(PubFun.getCurrentDate());
		tdaiSchema.setModifyTime(PubFun.getCurrentTime());
		// 缓存
		if (this.getTempFee(grplackPolSchema.getRiskCode()) == null) {
			this.cacheTempFee(grplackPolSchema.getRiskCode(), tdaiSchema);
		}
		tmpMap.put(tjieSchema, "INSERT");
		tmpMap.put(tdaiSchema, "INSERT");
		return tmpMap;
	}

	private static LCGrpPolSchema queryGrpPol(String grpPolNo) {
		LCGrpPolDB grpPolDB = new LCGrpPolDB();
		grpPolDB.setGrpPolNo(grpPolNo);
		if (!grpPolDB.getInfo()) {
			return null;
		}
		return grpPolDB.getSchema();
	}

	/**
	 * 查询暂交费
	 * 
	 * @param prtNo
	 *            String
	 * @param riskcode
	 *            String
	 * @return LJTempFeeSet
	 */
	private LJTempFeeSet queryTempFeeSet(String prtNo, String riskcode) {
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		// LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		// tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setOtherNo(prtNo);
		// tLJTempFeeSchema.setOtherNoType("4");
		// tLJTempFeeSchema.setConfFlag("0");
		// tLJTempFeeSchema.setRiskCode(riskcode);
		// tLJTempFeeDB.setSchema(tLJTempFeeSchema);

		String tempSQL = "select * from ljtempfee where otherno='"
				+ prtNo
				+ "' and othernotype='4' and riskcode='"
				+ riskcode
				+ "'"
				+ " and confdate is null and (enteraccdate is not null and enteraccdate<>'3000-01-01') ";
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(tempSQL);
		// tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "queryTempFee", tLJTempFeeDB.mErrors);
			logger.debug("errrrrrrrrrr:"
					+ tLJTempFeeDB.mErrors.getErrContent());
			return null;
		}
		return tLJTempFeeSet;
	}

	/**
	 * 暂交费交费求和
	 * 
	 * @param tmpFeeSet
	 *            LJTempFeeSet
	 * @return double
	 */
	private static double sumPayMoney(LJTempFeeSet tmpFeeSet) {
		double sumPrem = 0.000;
		for (int i = 1; i <= tmpFeeSet.size(); i++) {
			sumPrem += tmpFeeSet.get(i).getPayMoney();
		}
		return sumPrem;

	}

	/**
	 * 更新团单合同换号
	 * 
	 * @param inNewGrpContNo
	 *            String
	 * @param oldGrpContNo
	 *            String
	 * @return MMap
	 */
	private static MMap prepareOtherUpdateGrpCont(String inNewGrpContNo,
			String oldGrpContNo) {
		String condition = " GrpContNo='" + inNewGrpContNo + "'";
		String wherepart = " grpcontno='" + oldGrpContNo + "'";
		// 涉及到团单合同号的表
		String[] grpCont = { "LCGrpCont", "LCGrpAppnt", "LCContPlan",
				"LCContPlanRisk", "LCGeneral", "LCGCUWMaster",
				"LACommisionDetail", "LCGrpIssuePol", "lccgrpspec" };
		Vector tmpVec = PubFun.formUpdateSql(grpCont, condition, wherepart);
		Vector sqlVec = new Vector();
		sqlVec.addAll(tmpVec);

		condition = " GrpContNo='" + inNewGrpContNo + "'";
		wherepart = " grpcontno='"
				+ oldGrpContNo
				+ "' and  exists(select 'X' from lccont b where b.contno=a.contno and b.appflag='1')";
		// 涉及到团单合同号的表
		String[] tCont = { "LCCont a", "LCGet a", "LCPrem a", "LCUWError a",
				"LCUWSub a", "LCUWMaster a", "LCCSpec a", "LCCustomerImpart a",
				"LCCustomerImpartParams a", "LCSpec a", "LCCUWMaster a",
				"LCCUWSub a", "LCCUWError a", "LCCUWMaster a", "LCRReport a",
				"LCNotePad a", "LCUWReport a", "LCIssuePol a",
				"LCPENoticeItem a", "LCPENotice a", "LCPENoticeResult a",
				"LCInsureAccClass a", "LCRReportItem a", "LCRReportResult a" };
		// 此处由于更新以上表时无法使用到索引，所以在sql增加hint词/*+RULE*/
		for (int i = 0; i < tCont.length; i++) {
			sqlVec.add("update /*+RULE*/ " + tCont[i] + " set " + condition
					+ " where " + wherepart);
		}
		// tmpVec = PubFun.formUpdateSql(tCont, condition, wherepart);
		// sqlVec.addAll(tmpVec);

		MMap tmpMap = new MMap();
		tmpMap.add(LCGrpContSignBL.getUpdateSql(sqlVec));
		tmpMap
				.put(
						"update LCInsured a set GrpContNo='"
								+ inNewGrpContNo
								+ "' where prtno='"
								+ oldGrpContNo
								+ "' and  exists(select 'X' from lccont b where b.contno=a.contno and b.appflag='1' )",
						"UPDATE");
		return tmpMap;
	}

	/**
	 * 个单合同签单时间校验
	 * 
	 * @param grpcontno
	 *            String
	 * @return MMap
	 */
	private MMap prepareUpdateSignDate(String grpcontno, String tPayNo) {
		Vector sqlVec = new Vector();
		StringBuffer wherepart = new StringBuffer(32);
		wherepart.append(" grpcontno='");
		wherepart.append(grpcontno);
		wherepart.append("'");
		// String wherepart = " grpcontno='" + grpcontno + "'";
		// String condition = "";
		// 加个单签单时间
		String[] signDateTime = { "LCCont", "LCPol" };
		StringBuffer condition = new StringBuffer(64);
		condition.append("signdate = '");
		condition.append(mCurrentDate);
		condition.append("',signTime = '");
		condition.append(mCurrentTime);
		condition.append("'");
		// condition = condition + "signdate = '" + mCurrentDate + "',signTime =
		// '" + mCurrentTime
		// + "'";
		Vector contVec = PubFun.formUpdateSql(signDateTime, condition
				.toString(), wherepart.toString());
		sqlVec.addAll(contVec);

		// 加账户相关
		String[] AccTable = { "LCInsureAcc", "LCInsureAccClass",
				"LCInsureAccFee", "LCInsureAccClassFee" };

		StringBuffer conditionAcc = new StringBuffer(128);
		conditionAcc.append(" AccFoundDate='");
		conditionAcc.append(mCurrentDate);
		conditionAcc.append("',AccFoundTime='");
		conditionAcc.append(mCurrentTime);
		// conditionAcc.append("',BalaDate='");
		// conditionAcc.append(mCurrentDate);
		conditionAcc.append("',BalaTime ='");
		conditionAcc.append(mCurrentTime);
		conditionAcc.append("',MakeTime ='");
		conditionAcc.append(mCurrentTime);
		conditionAcc.append("'");
		// String conditionAcc = " AccFoundDate='" + mCurrentDate +
		// "',AccFoundTime='" + mCurrentTime
		// + "',BalaDate='" + mCurrentDate + "',BalaTime ='" + mCurrentTime +
		// "'";
		Vector AccVec = PubFun.formUpdateSql(AccTable, conditionAcc.toString(),
				wherepart.toString());
		sqlVec.addAll(AccVec);

		String[] AccClassTable = { "LCInsureAccClass", "LCInsureAccClassFee" };

		StringBuffer conditionAccClass = new StringBuffer(128);
		conditionAccClass.append(" AccFoundDate='");
		conditionAccClass.append(mCurrentDate);
		conditionAccClass.append("',AccFoundTime='");
		conditionAccClass.append(mCurrentTime);
		conditionAccClass.append("',BalaTime ='");
		conditionAccClass.append(mCurrentTime);
		conditionAccClass.append("',OtherNo='");
		conditionAccClass.append(tPayNo);
		conditionAccClass.append("',MakeTime ='");
		conditionAccClass.append(mCurrentTime);
		conditionAccClass.append("'");
		Vector AccClassVec = PubFun.formUpdateSql(AccClassTable,
				conditionAccClass.toString(), wherepart.toString());
		sqlVec.addAll(AccClassVec);

		String[] AccTraceTable = { "LCInsureAccTrace", "LCInsureAccFeeTrace" };
		StringBuffer conditionTrace = new StringBuffer(32);
		conditionTrace.append(" MakeTime='");
		conditionTrace.append(mCurrentTime);
		conditionTrace.append("',OtherNo='");
		conditionTrace.append(tPayNo);
		conditionTrace.append("'");
		// String conditionTrace = " paydate='" + mCurrentDate + "'";
		Vector AccVecTrace = PubFun.formUpdateSql(AccTraceTable, conditionTrace
				.toString(), wherepart.toString());
		sqlVec.addAll(AccVecTrace);

		MMap tmpMap = new MMap();
		tmpMap.add(LCGrpContSignBL.getUpdateSql(sqlVec));
		return tmpMap;

	}

	/**
	 * 准备只需要更新团单合同号,集体单合同号的sql
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param inNewGrpContNo
	 *            String
	 * @param lpGrpPolNo
	 *            String
	 * @param newGrpPolNo
	 *            String
	 * @return MMap
	 */
	private static MMap prepareOtherGrpPolSql(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo, String lpGrpPolNo, String newGrpPolNo) {
		// 更新团体合同,集体险种保单表相关
		String[] GrpPolContTables = { "LCGeneralToRisk a",
				"LCContPlanDutyParam a", "LCPolOther a", "LCGUWError a",
				"LCGrpPolRiskElement a", "LCGUWSub a", "LCGUWMaster a",
				"LCGrpFee a", "LCGrpFeeParam a", "LCPayRuleFactory a",
				"LCPayRuleParams a", "LCGrpAccTrigger a",
				"LCAscriptionRuleFactory a", "LCAscriptionRuleParams a",
				"lcgrpcontstate a", "LCGrpPol a" };
		// 集体险种保单表相关
		// String[] GrpPolTables =
		// {"LCInsureAccTrace"};
		Vector sqlVec = new Vector();
		Vector VecContPol = new Vector();

		// 其余表的更新sql
		StringBuffer condition = new StringBuffer(64);
		condition.append(" GrpContNo='");
		condition.append(inNewGrpContNo);
		condition.append("',GrpPolNo='");
		condition.append(newGrpPolNo);
		condition.append("'");
		// String condition = " GrpContNo='" + inNewGrpContNo + "',GrpPolNo='" +
		// newGrpPolNo + "'";
		StringBuffer wherepart = new StringBuffer();
		wherepart.append(" grppolno='");
		wherepart.append(lpGrpPolNo);
		wherepart.append("' and  exists (select 'X' from lcgrppol b ");
		wherepart.append("where b.grppolno='");
		wherepart.append(lpGrpPolNo);
		wherepart.append("' and b.appflag='1' and b.grppolno=a.grppolno)");

		// String wherepart = " grppolno='" + lpGrpPolNo + "'";
		VecContPol = PubFun.formUpdateSql(GrpPolContTables, condition
				.toString(), wherepart.toString());
		sqlVec.addAll(VecContPol);

		// 更新个单层保单表相关
		String[] pPolContTables = {
				// 现换号后签单后增加的
				"LCPol a", "LCInsureAcc a", "LCInsureAccClass a",
				"LCInsureAccFee a", "LCInsureAccClassFee a",
				"LCInsureAccTrace a" };

		// 其余表的更新sql
		StringBuffer pCondition = new StringBuffer(64);
		pCondition.append(" GrpContNo='");
		pCondition.append(inNewGrpContNo);
		pCondition.append("',GrpPolNo='");
		pCondition.append(newGrpPolNo);
		pCondition.append("'");
		// String condition = " GrpContNo='" + inNewGrpContNo + "',GrpPolNo='" +
		// newGrpPolNo + "'";
		wherepart = new StringBuffer();
		wherepart.append(" grppolno='");
		wherepart.append(lpGrpPolNo);
		wherepart.append("' and  exists (select 'X' from lcpol b ");
		wherepart.append("where b.grppolno='");
		wherepart.append(lpGrpPolNo);
		wherepart.append("' and b.appflag='1' and b.polno=a.polno)");
		// String wherepart = " grppolno='" + lpGrpPolNo + "'";
		VecContPol = new Vector();
		VecContPol = PubFun.formUpdateSql(pPolContTables,
				pCondition.toString(), wherepart.toString());
		sqlVec.addAll(VecContPol);

		// 更新个单层保单表相关
		String[] tPayTables = { "ljapayperson", "ljapaygrp" };

		// 其余表的更新sql
		pCondition = new StringBuffer(64);
		pCondition.append(" GrpPolNo='");
		pCondition.append(newGrpPolNo);
		pCondition.append("'");
		wherepart = new StringBuffer();
		wherepart.append(" grppolno='");
		wherepart.append(lpGrpPolNo);
		wherepart.append("'");
		VecContPol = new Vector();
		VecContPol = PubFun.formUpdateSql(tPayTables, pCondition.toString(),
				wherepart.toString());
		sqlVec.addAll(VecContPol);

		return getUpdateSql(sqlVec);
	}

	/**
	 * sql转换
	 * 
	 * @param sql
	 *            Vector
	 * @return MMap
	 */
	private static MMap getUpdateSql(Vector sql) {
		MMap tmpMap = new MMap();
		for (int i = 0; i < sql.size(); i++) {
			tmpMap.put((String) sql.get(i), "INSERT");
		}
		return tmpMap;

	}

	// /**
	// * 获取暂交费的更新信息
	// * 输出：如果发生错误则返回false,否则返回true
	// */
	// private MMap dealTempFee(LCGrpPolSchema tLCGrpPolSchema,
	// String tNewGrpContNo)
	// {
	//
	// MMap tmpMap = new MMap();
	// VData tReturn = new VData();
	// String tOldGrpPolNo = tLCGrpPolSchema.getGrpContNo();
	//
	// String confDate = PubFun.getCurrentDate();
	// String confTime = PubFun.getCurrentTime();
	//
	// LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
	// LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
	//
	// // if (mPolType.equals("3"))
	// // { // 合同下的集体的投保单
	// // tReturn.add(tLJTempFeeSet);
	// // tReturn.add(tLJTempFeeClassSet);
	// // }
	// // if (mPolType.equals("2"))
	// // {
	// // 暂交费信息
	// LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
	// tLJTempFeeDB.setOtherNo(tOldGrpPolNo);
	// tLJTempFeeDB.setOtherNoType("1");
	// tLJTempFeeDB.setTempFeeType("1");
	// tLJTempFeeDB.setConfFlag("0");
	//
	// tLJTempFeeSet = tLJTempFeeDB.query();
	// if (tLJTempFeeDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "LJTempFee表取数失败!");
	// return null;
	// }
	// //houzm增加的部分：将暂加费纪录类型为银行扣款的也查询出来
	// LJTempFeeDB tempLJTempFeeDB = new LJTempFeeDB();
	// tempLJTempFeeDB.setOtherNo(tLCGrpPolSchema.getPrtNo()); //印刷号
	// tempLJTempFeeDB.setOtherNoType("4"); //其它号码类型为印刷号
	// //tempLJTempFeeDB.setTempFeeType( "5" ); //暂交费类型为银行扣款
	// tempLJTempFeeDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
	// tempLJTempFeeDB.setConfFlag("0"); //核销标记为假
	// LJTempFeeSet tempLJTempFeeSet = tempLJTempFeeDB.query();
	// if (tempLJTempFeeDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "财务信息取数失败表取数失败!");
	// return null;
	//
	// }
	// tLJTempFeeSet.add(tempLJTempFeeSet); //将后续查询到的纪录存放到set
	// tmpMap.put(PubFun.copySchemaSet(tLJTempFeeSet), "DELETE");
	// //以上为houzm添加
	// int n = tLJTempFeeSet.size();
	// for (int i = 1; i <= n; i++)
	// {
	// LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);
	//
	// LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
	// tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
	// tLJTempFeeClassSet = tLJTempFeeClassDB.query();
	// if (tLJTempFeeClassDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "LJTempFeeClass表取数失败!");
	// continue;
	// } // end of if
	//
	// int m = tLJTempFeeClassSet.size();
	// if (m > 0)
	// {
	// tmpMap.put(PubFun.copySchemaSet(tLJTempFeeClassSet), "DELETE");
	// for (int j = 1; j <= m; j++)
	// {
	// LJTempFeeClassSchema tLJTempFeeClassSchema =
	// tLJTempFeeClassSet.get(j);
	// tLJTempFeeClassSchema.setConfDate(confDate);
	// tLJTempFeeClassSchema.setConfFlag("1");
	// tLJTempFeeClassSchema.setModifyDate(confDate);
	// tLJTempFeeClassSchema.setModifyTime(confTime);
	//
	// tLJTempFeeClassSet.set(j, tLJTempFeeClassSchema);
	// } // end of for
	//
	// tLJTempFeeSchema.setOtherNo(tNewGrpContNo);
	// tLJTempFeeSchema.setOtherNoType("1");
	// tLJTempFeeSchema.setConfDate(confDate);
	// tLJTempFeeSchema.setConfFlag("1");
	// tLJTempFeeSchema.setModifyDate(confDate);
	// tLJTempFeeSchema.setModifyTime(confTime);
	//
	// tLJTempFeeSet.set(i, tLJTempFeeSchema);
	//
	// }
	//
	// } // end of for
	// // } // end of if
	// tmpMap.put(tLJTempFeeSet, "INSERT");
	//
	// return tmpMap;
	// }

	/**
	 * 处理集体保单财务实交信息
	 * 
	 * @param tLCGrpPolSchema
	 *            LCGrpPolSchema
	 * @param tPaySerialNo
	 *            String
	 * @param newGrpPolNo
	 *            String
	 * @param inNewGrpContNo
	 *            String
	 * @return MMap
	 */
	private MMap dealGrpAPay(LCGrpPolSchema tLCGrpPolSchema,
			String tPaySerialNo, String inNewGrpContNo) {
		LJAPayGrpSet PayGrpSet = new LJAPayGrpSet();
		MMap tmpMap = new MMap();
		String confDate = mCurrentDate;
		String confTime = mCurrentTime;
		LCPremSet tPremSet = new LCPremSet();
		LCPolSet tPolSet = new LCPolSet();
		LCPolSchema tPolSchema = null;
		LCPolDB tPolDB = new LCPolDB();
		LCPremDB tPremDB = new LCPremDB();
		LJAPayGrpSchema PayGrpSchema = null;
		FDate fdate = new FDate();
		Date tdate;
		String tGrpPolNo = tLCGrpPolSchema.getGrpPolNo();

		PayGrpSchema = new LJAPayGrpSchema();
		PayGrpSchema.setGrpPolNo(tGrpPolNo);
		PayGrpSchema.setRiskCode(tLCGrpPolSchema.getRiskCode());
		PayGrpSchema.setSumActuPayMoney(0);
		PayGrpSchema.setSumDuePayMoney(0);
		PayGrpSchema.setPayType("ZC");
		PayGrpSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
		PayGrpSchema.setAgentType(tLCGrpPolSchema.getAgentType());
		PayGrpSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
		PayGrpSchema.setAppntNo(tLCGrpPolSchema.getCustomerNo());
		PayGrpSchema.setApproveCode(mGlobalInput.Operator);
		PayGrpSchema.setApproveDate(confDate);
		PayGrpSchema.setPayCount(1);
		PayGrpSchema.setPayNo(tPaySerialNo);
		PayGrpSchema.setPayDate(maxPayDate);
		PayGrpSchema.setEnterAccDate(maxEnterAccDate);
		PayGrpSchema.setLastPayToDate(maxPayDate);
		// 下一次交费日期
		PayGrpSchema.setApproveTime(confTime);
		PayGrpSchema.setConfDate(confDate);
		PayGrpSchema.setGrpContNo(inNewGrpContNo);
		PayGrpSchema.setMakeDate(confDate);
		PayGrpSchema.setMakeTime(confTime);
		PayGrpSchema.setModifyDate(PubFun.getCurrentDate());
		PayGrpSchema.setModifyTime(PubFun.getCurrentTime());
		PayGrpSchema.setOperator(mGlobalInput.Operator);
		PayGrpSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		PayGrpSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());

		tPolSet = tPolDB.executeQuery("select * from lcpol where grppolno='"
				+ tLCGrpPolSchema.getGrpPolNo() + "' and appflag='1'");
		if (tPolSet == null) {
			return null;
		}
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		for (int j = 1; j <= tPolSet.size(); j++) {
			tPolSchema = tPolSet.get(j);
			if (j == 1) {
				// 缓存下一次交费日期
				paytoDateMap.put(tPolSchema.getRiskCode(), tPolSchema
						.getPaytoDate());
				PayGrpSchema.setCurPayToDate(tPolSchema.getPaytoDate());
				// 在第一个节点处初始化
			}
			// 实收信息,初始化基本信息
			tPolSchema = tPolSet.get(j);
			// 个人保单保费项查询
			tPremSet = tPremDB
					.executeQuery("select * from lcprem where polno='"
							+ tPolSchema.getPolNo() + "'");
			if (tPremSet == null) {
				continue;
			}
			tdate = fdate.getDate(tPolSchema.getPayEndDate());
			if (maxPayEndDate == null || maxPayEndDate.before(tdate)) {
				maxPayEndDate = tdate;
			}
			int m = tPremSet.size();
			if (m > 0) {
				for (int k = 1; k <= m; k++) {

					LCPremSchema tLCPremSchema = tPremSet.get(k);
					LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
					tLJAPayPersonSchema.setPolNo(tPolSchema.getPolNo());
					tLJAPayPersonSchema.setPayCount(1);
					tLJAPayPersonSchema.setGrpPolNo(tGrpPolNo);
					tLJAPayPersonSchema.setContNo(tPolSchema.getContNo());
					tLJAPayPersonSchema.setGrpContNo(inNewGrpContNo);
					tLJAPayPersonSchema.setAppntNo(tPolSchema.getAppntNo());
					tLJAPayPersonSchema.setPayNo(tPaySerialNo);
					tLJAPayPersonSchema.setPayAimClass("1");
					tLJAPayPersonSchema
							.setDutyCode(tLCPremSchema.getDutyCode());
					tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema
							.getPayPlanCode());
					tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema
							.getPrem());
					tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema
							.getPrem());
					tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
					tLJAPayPersonSchema.setPayDate(maxPayDate);
					tLJAPayPersonSchema.setPayType("ZC");
					tLJAPayPersonSchema.setEnterAccDate(maxEnterAccDate);
					tLJAPayPersonSchema.setConfDate(confDate);
					tLJAPayPersonSchema.setLastPayToDate("1899-12-31");
					tLJAPayPersonSchema.setCurPayToDate(tLCPremSchema
							.getPaytoDate());
					tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
					tLJAPayPersonSchema.setApproveCode(mGlobalInput.Operator);
					tLJAPayPersonSchema.setApproveDate(confDate);
					tLJAPayPersonSchema.setApproveTime(confTime);

					tLJAPayPersonSchema.setMakeDate(confDate);
					tLJAPayPersonSchema.setMakeTime(confTime);
					tLJAPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
					tLJAPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
					tLJAPayPersonSchema.setManageCom(tPolSchema.getManageCom());
					tLJAPayPersonSchema.setAgentCom(tPolSchema.getAgentCom());
					tLJAPayPersonSchema.setAgentType(tPolSchema.getAgentType());
					tLJAPayPersonSchema.setRiskCode(tPolSchema.getRiskCode());
					tLJAPayPersonSchema.setAgentCode(tPolSchema.getAgentCode());
					tLJAPayPersonSchema.setAgentGroup(tPolSchema
							.getAgentGroup());
					tLJAPayPersonSchema.setMainPolYear(1);

					tLJAPayPersonSet.add(tLJAPayPersonSchema);
				}

			}
			tmpMap.put(tLJAPayPersonSet, "INSERT");
			// 集体保单实交信息
			fillLJAPayGrp(PayGrpSchema, tPremSet);
		}

		// 团单总单保费和
		sumGrpContPrem += PayGrpSchema.getSumActuPayMoney();
		sumGrpContStandPrem += PayGrpSchema.getSumDuePayMoney();
		PayGrpSet.add(PayGrpSchema);
		tmpMap.put(PayGrpSet, "INSERT");
		return tmpMap;
	}

	/**
	 * 总实收
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param newGrpContNo
	 *            String
	 * @param tPayNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareLJAPAY(LCGrpContSchema tLCGrpContSchema,
			String newGrpContNo, String tPayNo) {
		String confDate = mCurrentDate;
		String confTime = mCurrentTime;
		MMap tmpMap = new MMap();
		// 生成流水号
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		tLJAPaySchema.setPayNo(tPayNo);
		tLJAPaySchema.setIncomeNo(newGrpContNo);
		tLJAPaySchema.setIncomeType("1");
		tLJAPaySchema.setOtherNoType("4");
		tLJAPaySchema.setOtherNo(tLCGrpContSchema.getPrtNo());
		tLJAPaySchema.setAppntNo(tLCGrpContSchema.getAppntNo());
		tLJAPaySchema.setSumActuPayMoney(sumGrpContPrem);
		tLJAPaySchema.setPayDate(maxPayDate);
		tLJAPaySchema.setEnterAccDate(PubFun.getCurrentDate());
		tLJAPaySchema.setConfDate(PubFun.getCurrentDate());
		tLJAPaySchema.setApproveCode(mGlobalInput.Operator);
		tLJAPaySchema.setApproveDate(confDate);
		tLJAPaySchema.setSerialNo(tPayNo);
		tLJAPaySchema.setOperator(mGlobalInput.Operator);
		tLJAPaySchema.setMakeDate(confDate);
		tLJAPaySchema.setMakeTime(confTime);
		tLJAPaySchema.setModifyDate(PubFun.getCurrentDate());
		tLJAPaySchema.setModifyTime(PubFun.getCurrentTime());
		tLJAPaySchema.setStartPayDate(mFirstPayDate);
		tLJAPaySchema.setManageCom(tLCGrpContSchema.getManageCom());
		tLJAPaySchema.setAgentCom(tLCGrpContSchema.getAgentCom());
		tLJAPaySchema.setAgentType(tLCGrpContSchema.getAgentType());
		tLJAPaySchema.setAgentCode(tLCGrpContSchema.getAgentCode());
		tLJAPaySchema.setAgentGroup(tLCGrpContSchema.getAgentGroup());
		tLJAPaySchema.setAgentType(tLCGrpContSchema.getAgentType());
		tLJAPaySchema.setBankAccNo(tLCGrpContSchema.getBankAccNo());
		tLJAPaySchema.setBankCode(tLCGrpContSchema.getBankCode());
		tLJAPaySchema.setAccName(tLCGrpContSchema.getAccName());
		tLJAPaySchema.setPayTypeFlag(tLCGrpContSchema.getPayMode()); // ?
		tmpMap.put(tLJAPaySchema, "INSERT");
		return tmpMap;
	}

	/**
	 * 根据传入保费项，求集体保单总应交和总实交
	 * 
	 * @param tPayGrpSchema
	 *            LJAPayGrpSchema
	 * @param tPremSet
	 *            LCPremSet
	 */
	private static void fillLJAPayGrp(LJAPayGrpSchema tPayGrpSchema,
			LCPremSet tPremSet) {
		// 求保费
		double sumPrem = 0.00;
		double sumStandPrem = 0.00;
		LCPremSchema tPremSchema = null;
		for (int t = 1; t <= tPremSet.size(); t++) {
			tPremSchema = tPremSet.get(t);
			sumPrem += tPremSchema.getPrem();
			sumStandPrem += tPremSchema.getStandPrem();
		}
		tPayGrpSchema.setSumActuPayMoney(PubFun.setPrecision(tPayGrpSchema
				.getSumActuPayMoney()
				+ sumPrem, "0.00"));
		tPayGrpSchema.setSumDuePayMoney(PubFun.setPrecision(tPayGrpSchema
				.getSumDuePayMoney()
				+ sumStandPrem, "0.00"));

	}

	/**
	 * 执行
	 * 
	 * @param conn
	 *            Connection
	 * @param sql
	 *            String
	 * @return double
	 */
	private static double execSumQuery(Connection conn, String sql) {
		PreparedStatement st = null;
		// Connection conn = null;
		ResultSet rs = null;
		try {
			// conn = DBConnPool.getConnection();
			if (conn == null) {
				return 0;
			}
			st = conn.prepareStatement(sql);
			if (st == null) {
				return 0;
			}
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			try {
				st.close();
				rs.close();
			} catch (Exception e) {
			}

		}
	}

	/**
	 * 签单校验
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return boolean
	 */
	private boolean checkGrpCont(LCGrpContSchema tLCGrpContSchema) {
		// 复核是否通过
		if (!"4".equals(tLCGrpContSchema.getApproveFlag())
				&& !"9".equals(tLCGrpContSchema.getApproveFlag())) {
			CError.buildErr(this, "团单合同(" + tLCGrpContSchema.getGrpContNo()
					+ ")未复核通过;");
			return false;
		}
		// 核保是否通过
		if (!"4".equals(tLCGrpContSchema.getUWFlag())
				&& !"9".equals(tLCGrpContSchema.getUWFlag())) {
			CError.buildErr(this, "团单合同(" + tLCGrpContSchema.getGrpContNo()
					+ ")未核保通过;");
			return false;
		}

		return true;

	}

	/**
	 * 团单暂交费查询比较
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return int
	 */
	private int CheckTempFee(LCGrpContSchema tLCGrpContSchema) {
		// 暂交费是否交够
		double tPrem = 0.00;
		double tTmpFee = 0.00;
		double tAllTmpFee = 0.00;
		double tAllPrem = 0.00;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSumPremSSRS = new SSRS();
		SSRS tSumTmpFeeSSRS = new SSRS();
		Connection conn = null;
		try {
			String sql0 = "select nvl(sum(PayMoney),0) from ljtempfee where TempFeeType in ('0','1')"
					+ " and othernotype='4' and otherno='"
					+ tLCGrpContSchema.getPrtNo()
					+ "' and confdate is null and (enteraccdate is not null and enteraccdate<>'3000-01-01') ";
			String tFee = tExeSQL.getOneValue(sql0);
			if (tFee == null || "".equals(tFee)) {
				tFee = "0";
			}
			tAllTmpFee = Double.parseDouble(tFee);
			if (tAllTmpFee <= 0) {
				return -1;
			} else {
				String sql = "";
				sql = "select sum(Prem) as sumprem,riskcode from lcpol where grpcontno='"
						+ tLCGrpContSchema.getGrpContNo()
						+ "' and uwflag not in ('1','5','a','z') group by riskcode order by riskcode";

				// sumPrem = LCGrpContSignBL.execSumQuery(conn, sql);
				tSumPremSSRS = tExeSQL.execSQL(sql);
				if (tExeSQL.mErrors.needDealError()) {
					return -2;
				}

				sql = " select sum(PayMoney) as sumpay,riskcode from ljtempfee where TempFeeType in ('0','1') and othernotype='4' and otherno='"
						+ tLCGrpContSchema.getPrtNo()
						+ "' and confdate is null and (enteraccdate is not null and enteraccdate<>'3000-01-01')  group by riskcode order by riskcode";

				// sumTmpFee = LCGrpContSignBL.execSumQuery(conn, sql);
				tSumTmpFeeSSRS = tExeSQL.execSQL(sql);
				if (tExeSQL.mErrors.needDealError()) {
					return -3;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (tSumPremSSRS != null && tSumPremSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSumPremSSRS.getMaxRow(); i++) {
				tPrem = Double.parseDouble(tSumPremSSRS.GetText(i, 1));
				for (int j = 1; j <= tSumTmpFeeSSRS.getMaxRow(); j++) {
					if (tSumTmpFeeSSRS.GetText(j, 2).equals(
							tSumPremSSRS.GetText(i, 2))) {
						tTmpFee = Double.parseDouble(tSumTmpFeeSSRS.GetText(j,
								1));
						break;
					}
				}
				if (tTmpFee <= 0.0) {
					return -1;
				}
				if (tTmpFee < tPrem) {
					return -1;
				}
				tAllPrem = tAllPrem + tPrem; // 记录各险种保费和
				tTmpFee = 0.00;
				tPrem = 0.00;
			}
		}

		// 判断交费是否多余
		if (tAllTmpFee > tAllPrem) {
			logger.debug("mGrpContDiff Bef=" + mGrpContDiff);
			this.mGrpContDiff = PubFun.setPrecision(tAllTmpFee - tAllPrem,
					"0.00");
		}
		if (this.mGrpContDiff > 0)
			return 1;

		return 0;

	}

	/**
	 * 团单产品组合暂交费查询比较 Add by:chenrong Date:2006.12.06
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return int
	 */
	private int CheckPlanTempFee(LCGrpContSchema tLCGrpContSchema) {
		// 暂交费是否交够
		ExeSQL tExeSQL = new ExeSQL();
		double tSumPrem = 0;
		double tSumTmpFee = 0;
		try {
			String sql0 = "select count(*)  from ljtempfee where TempFeeType in ('0','1') and othernotype='4' and otherno='"
					+ tLCGrpContSchema.getPrtNo()
					+ "' and confdate is null  and enteraccdate is  null and riskcode='"
					+ mContPlanCode + "'";
			double sumcount = Double.parseDouble(tExeSQL.getOneValue(sql0));
			if (sumcount > 0) {
				return -1;
			} else {
				String sql = "";
				String tPrem = "";
				sql = "select nvl(sum(Prem),0) as sumprem from lcpol where grpcontno='"
						+ tLCGrpContSchema.getGrpContNo()
						+ "' and uwflag not in ('1','5','a','z')";
				tPrem = tExeSQL.getOneValue(sql);
				if (tExeSQL.mErrors.needDealError()) {
					return -2;
				}
				if (tPrem == null || "".equals(tPrem)) {
					tPrem = "0";
				}
				tSumPrem = Double.parseDouble(tPrem);

				sql = " select nvl(sum(PayMoney),0) as sumpay from ljtempfee where TempFeeType in ('0','1') and othernotype='4' and otherno='"
						+ tLCGrpContSchema.getPrtNo()
						+ "' and confdate is null  and (enteraccdate is  not null and enteraccdate <> '3000-01-01')";
				tPrem = "";
				tPrem = tExeSQL.getOneValue(sql);
				if (tExeSQL.mErrors.needDealError()) {
					return -3;
				}
				if (tPrem == null || "".equals(tPrem)) {
					tPrem = "0";
				}
				tSumTmpFee = Double.parseDouble(tPrem);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (tSumTmpFee <= 0.0) {
			// CError.buildErr(this,"没有暂交费信息,不能签单");
			return -1;
		}
		if (tSumTmpFee < tSumPrem) {
			// CError.buildErr(this,"暂交费没有交够,不能签单");
			return -1;
		}
		if (tSumTmpFee > tSumPrem) {
			this.mGrpContDiff = PubFun.setPrecision(tSumTmpFee - tSumPrem,
					"0.00");
			logger.debug("mGrpContDiff Aft=" + mGrpContDiff);
		}
		if (this.mGrpContDiff > 0)
			return 1;
		return 0;
	}

	/**
	 * 查询团体下所有合同
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return LCContSet
	 */
	private LCContSet queryLCContSet(LCGrpContSchema tLCGrpContSchema) {
		// 查询集体单的个单：核保结论为标准或次标准。
		// 然后进行签单
		String tGrpContNo = tLCGrpContSchema.getGrpContNo();
		String sql = "select * from lccont a where grpcontno='" + tGrpContNo
				+ "' and appflag='0' and uwflag not in('1','5','z')";
		// + " and not exists(select 'X' from lcpol b where b.grpcontno='" +
		// tGrpContNo
		// + "' and b.contno=a.contno "
		// + "and b.mainpolno in (select polno from lcpol c where "
		// + "c.grpcontno='" + tGrpContNo + "' and "
		// + "c.contno=b.contno and c.mainpolno=c.polno and c.uwflag='1')"
		// + " and b.polno<>b.mainpolno and b.uwflag !='1')";
		LCContSet tLCContSet = null;
		LCContDB tLCContDB = new LCContDB();
		tLCContSet = tLCContDB.executeQuery(sql);
		if (tLCContDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			return null;
		}
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			return null;
		}
		return tLCContSet;

	}

	/**
	 * 查询并填充LCGrpContSet的数据
	 * 
	 * @return boolean
	 */
	private boolean fillGrpCont() {
		LCGrpContDB db = new LCGrpContDB();

		for (int i = 1; i <= mLCGrpContSet.size(); i++) {
			// 必须传进来的是投保单号
			db.setProposalGrpContNo(mLCGrpContSet.get(i).getGrpContNo());
			// db.getInfo();
			LCGrpContSet grpcontSet = db.query();

			if (grpcontSet == null || db.mErrors.needDealError()) {
				// CError.buildErr(this, db.mErrors.getErrContent());
				// this.mErrors.copyAllErrors( db.mErrors );
				CError.buildErr(this, "查找团单合同['"
						+ mLCGrpContSet.get(i).getGrpContNo() + "']信息失败");
				return false;
			}
			mLCGrpContSet.set(i, grpcontSet.get(1));
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLCGrpContSchema = (LCGrpContSchema) cInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);
		if (mLCGrpContSchema == null) {
			mLCGrpContSet = (LCGrpContSet) cInputData.getObjectByObjectName(
					"LCGrpContSet", 0);
		} else {
			if (StrTool.cTrim(mLCGrpContSchema.getGrpContNo()).equals("")) {

				CError.buildErr(this, "集体投保单号传入失败!");
				return false;
			}

			mLCGrpContSet.add(mLCGrpContSchema);
		}

		if (mLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有得到要签单的数据集");
			return false;
		}

		return true;
	}

	private void cacheTempFee(Object key, LJTempFeeSchema tempFeeSchema) {
		this.mLJTempFeeMap.put(key, tempFeeSchema);
	}

	private Object getTempFee(String key) {
		return mLJTempFeeMap.get(key);
	}

	/**
	 * 签单校验(判断表单是否被锁定)
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return boolean : true 表示被锁定 false 表示未被锁定 LiuHao 2005-04-16 add
	 */
	private static boolean checkLockGrpCont(LCGrpContSchema tLCGrpContSchema) {
		String tSql = "";
		int notSignCount = 0;
		Connection conn = DBConnPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 检索表单锁定状态
		tSql = "select count(*) from LDSysTrace where PolNo = '"
				+ tLCGrpContSchema.getPrtNo() + "' and polstate = 1999";

		try {
			ps = conn.prepareStatement(tSql);
			rs = ps.executeQuery();
			if (rs.next()) {
				notSignCount = rs.getInt(1);
				rs.close();
				ps.close();
			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		if (notSignCount > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 签单校验(指定表单锁定)
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return 无 LiuHao 2005-04-16 add
	 */
	private boolean lockGrpCont(LCGrpContSchema tLCGrpContSchema) {
		MMap map = new MMap();

		tLDSysTraceSchema.setPolNo(tLCGrpContSchema.getPrtNo()); //
		tLDSysTraceSchema.setOperator(tLCGrpContSchema.getOperator()); //
		tLDSysTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tLDSysTraceSchema.setPolState(1999);
		tLDSysTraceSchema.setCreatePos("签单阶段");
		tLDSysTraceSchema.setManageCom(mGlobalInput.ManageCom);
		tLDSysTraceSchema.setOperator2(tLCGrpContSchema.getOperator());
		tLDSysTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setModifyTime(PubFun.getCurrentTime());

		map.put(tLDSysTraceSchema, "INSERT");

		// 提交保存
		VData contData = new VData();
		contData.add(map);
		PubSubmit pubSubmit = new PubSubmit();
		if (!pubSubmit.submitData(contData, "")) {
			return false;
		}
		return true;
	}

	/**
	 * 签单校验(解除当前表的锁定状态) LiuHao 2005-04-16 add
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 */
	private void ravelGrpCont(LCGrpContSchema tLCGrpContSchema) {
		MMap map = new MMap();

		map.put(tLDSysTraceSchema, "DELETE");

		// 提交保存
		VData contData = new VData();
		contData.add(map);
		PubSubmit pubSubmit = new PubSubmit();
		if (!pubSubmit.submitData(contData, "")) {
			CError.buildErr(this, "团单合同(" + tLCGrpContSchema.getGrpContNo()
					+ ")解锁失败！请与管理员联系！;");
			// tLDSysTraceSchema = null;
			// return false;
		}
		// return true;
		// tLDSysTraceSchema = null;
	}

	/**
	 * Add by:chenrong Date:2006.12.06 是否为产品组合
	 * 
	 * @param cGrpContNo
	 *            String
	 * @return boolean
	 */
	private boolean isContPlan(String cGrpContNo) {
		String tStrSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tContPlanCode = "";
		tStrSQL = "select contplancode from lccontplan where grpcontno='"
				+ cGrpContNo + "' and plantype='6'";
		tContPlanCode = tExeSQL.getOneValue(tStrSQL);
		if (tContPlanCode == null || "".equals(tContPlanCode)) {
			return false;
		}
		mContPlanCode = tContPlanCode;
		return true;
	}

	/**
	 * 更换集体保单号
	 * 
	 * @param cLCGrpContSchema
	 *            LCGrpContSchema
	 * @return MMap
	 */
	private MMap changeGrpContNo(LCGrpContSchema cLCGrpContSchema,
			String cNewGrpContNo) {
		MMap tMMap = new MMap();
		String tNewGrpContNo = "";
		LCGrpContSchema tLCGrpContSchema = cLCGrpContSchema.getSchema();
		String sql = "select grppolno,GrpProposalNo from lcgrppol where grpcontno='"
				+ tLCGrpContSchema.getGrpContNo()
				+ "' and uwflag not in ('1','a','z','5')";
		ExeSQL exesql = new ExeSQL();
		SSRS ssrs = exesql.execSQL(sql);
		if (ssrs == null) {
			CError.buildErr(this, "团单下没有相应的集体保单");
			return null;
		}
		String limitNo = PubFun.getNoLimit(tLCGrpContSchema.getManageCom());
		tNewGrpContNo = cNewGrpContNo;

		// 险种单相关
		for (int i = 1; i <= ssrs.getMaxRow(); i++) {
			String tOldProposalNo = ssrs.GetText(i, 1);
			String tNewGrpPolNo = PubFun1.CreateMaxNo("GrpPolNo", limitNo);
			tMMap.add(prepareOtherGrpPolSql(tLCGrpContSchema, tNewGrpContNo,
					tOldProposalNo, tNewGrpPolNo));
		}
		// 合同相关
		tMMap.add(prepareOtherUpdateGrpCont(tNewGrpContNo, tLCGrpContSchema
				.getGrpContNo()));
		return tMMap;
	}

	public static void main(String[] args) {
		VData cInputData = new VData();
		String cOperate = "";
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "8621";
		tGlobalInput.ComCode = "001";
		cInputData.add(tGlobalInput);
		String[] testContNo = { "20060000000000" };
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		for (int i = 0; i < testContNo.length; i++) {
			LCGrpContSchema tLCContSchema = new LCGrpContSchema();
			tLCContSchema.setGrpContNo(testContNo[i]);
			tLCGrpContSet.add(tLCContSchema);
		}
		cInputData.add(tLCGrpContSet);
		LCGrpContSignBL lCGrpContSignBL = new LCGrpContSignBL();
		boolean actualReturn = lCGrpContSignBL.submitData(cInputData, cOperate);
	}
}
