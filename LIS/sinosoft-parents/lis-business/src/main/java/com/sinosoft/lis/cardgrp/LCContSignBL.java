/*
 * @(#)LCContSignBL.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 合同签单处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wujs
 * @version 6.0
 */
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.finfee.NewPolFeeWithdrawBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LCContSignBL {
private static Logger logger = Logger.getLogger(LCContSignBL.class);
	/** 存放结果 */
	public VData mVResult = new VData();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSet mLCContSet = null;
	private final String INSERT = "INSERT";
	private final String UPDATE = "UPDATE";
	private final String DELETE = "DELETE";
	private final int ERROR = 100;
	private String mark; // add by yaory
	private String CardContNo;// add by zhangxing
	private String mSaleChnl;

	// 交费日期相关
	private Date mFirstPayDate = null;
	private Date maxPayDate;
	private Date maxEnterAccDate;
	TransferData mTransferData;
	// 当前时间
	private String mCurrentDate;
	private String mCurrentTime;

	// 记录已有交费记录
	private HashMap mLJTempFeeMap = new HashMap();

	// 保单表
	LCPolSchema mLCPolSchema = new LCPolSchema();
	LCContStateSet mLCContStateSet = new LCContStateSet();

	// 保单表
	LCPolSet mLCPolSet = new LCPolSet();

	// 被保险人客户号
	private String mInsuredNo;

	/** 险种保单签单类 */
	private ProposalSignBL proposalSignBL = new ProposalSignBL();
	private LCInsureAccClassSet mLCInsureAccClassSet;
	private LCInsureAccTraceSet mLCInsureAccTraceSet;
	private LCInsureAccFeeSet mLCInsureAccFeeSet;
	private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet;
	private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet;
	private String mUsePubAcc = "N";

	public VData getResult() {
		return this.mVResult;
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

		// 得到外部传入的数据,将数据备份到本类中
		if (this.getInputData() == false) {
			return false;
		}

		// 数据操作业务处理
		if (this.signLCContPol(mLCContSet) == false) {
			return false;
		}
		return true;
	}

	/**
	 * 对合同进行签单，
	 * 
	 * @param globalInput
	 *            GlobalInput
	 * @param inLCContSet
	 *            LCContSet
	 * @return boolean 修改
	 */
	private boolean signLCContPol(LCContSet inLCContSet) {
		this.mVResult = new VData();
		MMap contMap = new MMap();
		LCContSchema tLCContSchema = null;
		LCPolDB tLCPolDB = null;
		LCPolSet tLCPolSet = null;
		LCPolSchema tPolSchema = null;
		LCContDB tContDB = null;
		VData bufferData = new VData();
		int signCount = 0;
		if (inLCContSet == null || inLCContSet.size() <= 0) {
			CError.buildErr(this, "没有传入要签单的数据");
			return false;
		}
		/** 以一个合同为单位签单 */
		for (int i = 1; i <= inLCContSet.size(); i++) {

			tLCContSchema = inLCContSet.get(i);

			if (tLCContSchema == null) {
				continue;
			}
			// 获取合同所有信息
			tContDB = new LCContDB();
			tContDB.setContNo(tLCContSchema.getContNo());
			if (!tContDB.getInfo()) {
				continue;
			}

			if (tLCContSchema.getSaleChnl() != null) {
				this.mSaleChnl = tLCContSchema.getSaleChnl();
			} else {
				this.mSaleChnl = tContDB.getSaleChnl();
			}

			tLCContSchema.setSchema(tContDB.getSchema());
			mInsuredNo = tLCContSchema.getInsuredNo();
			// 复核,核保校验
			if (tLCContSchema.getAppFlag().equals("1")) {

				logger.debug("提示信息:合同(" + tLCContSchema.getContNo()
						+ ")已签单;");

			}

			// 获取合同下所有核保通过的保单
			tLCPolDB = new LCPolDB();
			String sql = "select * from lcpol where contno ='"
					+ tLCContSchema.getContNo() + "' and appflag!='1'";
			sql += " and uwflag in ('4','9','z') order by polno";
			logger.debug(sql);
			tLCPolSet = tLCPolDB.executeQuery(sql);
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				continue;
			}
			// 个人合同单
			if ("1".equals(tLCContSchema.getContType())) {

			}

			String tLimit = PubFun.getNoLimit(tLCContSchema.getManageCom());
			String newContNo = tLCContSchema.getContNo();// yaory
			String tPaySerialNo = PubFun1.CreateMaxNo("PAYNO", tLimit);

			VData tInput = new VData();
			tInput.add(this.mGlobalInput);
			tInput.add(tLCPolSet);
			tInput.add(newContNo);
			// 增加一个参数，是不是公共帐户poltyp=2如果是后面的管理费才处理
			String poltype = tLCContSchema.getPolType();

			if (mTransferData == null) {
				mTransferData = new TransferData();
			}
			mTransferData.setNameAndValue("poltype", poltype);
			// 增加保全支持
			mTransferData.setNameAndValue("MFee", mUsePubAcc);// 使用公共帐户，不计算管理费
			tInput.add(mTransferData);
			if (mInputData.getObjectByObjectName("TransferData", 0) != null) {
				tInput.add(mInputData.getObjectByObjectName("TransferData", 0)); // 保全签单会附加其他信息，将这些信息传到险种签单中使用
			}
			// 提交保单签单
			boolean signResult = proposalSignBL.submitData(tInput, null);
			mLCPolSchema = (LCPolSchema) proposalSignBL.getResult()
					.getObjectByObjectName("LCPolSchema", 0);
			mLCInsureAccClassSet = (LCInsureAccClassSet) proposalSignBL
					.getResult()
					.getObjectByObjectName("LCInsureAccClassSet", 0);
			mLCInsureAccTraceSet = (LCInsureAccTraceSet) proposalSignBL
					.getResult()
					.getObjectByObjectName("LCInsureAccTraceSet", 0);
			mLCInsureAccFeeSet = (LCInsureAccFeeSet) proposalSignBL.getResult()
					.getObjectByObjectName("LCInsureAccFeeSet", 0);
			mLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) proposalSignBL
					.getResult().getObjectByObjectName(
							"LCInsureAccClassFeeSet", 0);
			mLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) proposalSignBL
					.getResult().getObjectByObjectName(
							"LCInsureAccFeeTraceSet", 0);
			if (!signResult) {

				CError.buildErr(this, "合同(" + tLCContSchema.getContNo()
						+ ")签单没有通过.");
				if (proposalSignBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(proposalSignBL.mErrors);
				}

				if (mLCPolSchema.getAppFlag().equals("2")) {
					return false;
				}
				continue;

			}

			VData oneCont = new VData();
			oneCont = proposalSignBL.getResult();
			MMap srvResult = (MMap) oneCont.getObjectByObjectName("MMap", 0);

			if ("1".equals(tLCContSchema.getContType())) {

				// 财务处理与万能险处理 个险
				VData bufferdPolData = proposalSignBL.getBufferedPolData();
				MMap finaMap = this.dealFinance(tLCContSchema, bufferdPolData,
						newContNo, tPaySerialNo);
				if (finaMap == null) {
					return false;
				}
				srvResult.add(finaMap);
			}

			// 指定合同生效日
			FDate fdate = new FDate();
			Date cValidate = null;
			Date tPayToDate = null;
			Date paytodate;
			double sumprem = 0.0;
			for (int l = 1; l <= tLCPolSet.size(); l++) {

				if (tLCPolSet.get(l) != null
						&& tLCPolSet.get(l).getContNo().equals(
								tLCContSchema.getContNo())) {
					paytodate = fdate.getDate(tLCPolSet.get(l).getPaytoDate());
					sumprem += tLCPolSet.get(l).getSumPrem();
					if (tPayToDate == null || tPayToDate.after(paytodate)) {
						tPayToDate = paytodate;
					}
					if (tLCPolSet.get(l).getCValiDate() != null) {
						if (cValidate == null) {
							cValidate = fdate.getDate(tLCPolSet.get(l)
									.getCValiDate());
						}
						if (fdate.getDate(tLCPolSet.get(l).getCValiDate())
								.before(cValidate)) {
							cValidate = fdate.getDate(tLCPolSet.get(l)
									.getCValiDate());
						}
					}
				}
			}
			tLCContSchema.setPaytoDate(tPayToDate);
			if (tLCContSchema.getCValiDate() == null) {
				if (cValidate == null) {
					cValidate = PubFun.calDate(new Date(), 1, "D", new Date());
				}
				tLCContSchema.setCValiDate(cValidate);
			}

			mLCPolSet.add(tLCPolSet);

			// 合同本身处理
			srvResult.add(prepareCont(tLCContSchema, newContNo));// 处理LCCONTSTATE

			// 处理一些需要更新合同号的表
			srvResult.add(dealElseUpdateTable(tLCContSchema.getContNo(),
					newContNo));
			MMap aMMap = new MMap();

			// 替换万能险的相关表中的保单号

			if (contMap == null) {
				contMap = srvResult;
			} else {
				contMap.add(srvResult);
			}
			contMap.add(aMMap);

			signCount++;
		}
		if (contMap != null) {
			this.mVResult.add(contMap);
		}

		return true;

	}

	/**
	 * 生成仅仅需要更新合同号的表的sql
	 * 
	 * @param oldContNo
	 *            String
	 * @param newContNo
	 *            String
	 * @return MMap
	 */
	private MMap dealElseUpdateTable(String oldContNo, String newContNo) {
		String[] tables = { "LCInsured" };// ,"LCAppnt",
											// "LCCustomerImpart","LCCustomerImpartParams",

		String condition = " contno='" + newContNo + "', ModifyDate='"
				+ PubFun.getCurrentDate() + "', ModifyTime='"
				+ PubFun.getCurrentTime() + "' ";
		String wherepart = " contno='" + oldContNo + "'";
		Vector vec = PubFun.formUpdateSql(tables, condition, wherepart);
		if (vec != null) {
			MMap tmpMap = new MMap();
			for (int i = 0; i < vec.size(); i++) {
				tmpMap.put((String) vec.get(i), this.UPDATE);
			}
			return tmpMap;
		}
		return null;
	}

	/**
	 * 校验财务收费，比较一批保单保费之和与所有交费的大小
	 * 
	 * @param inPolSet
	 *            LCPolSet return 比较 交费?保单保费 大小 1 > 0 = -1 < 100 error
	 */
	private int compareTempFee(String prtNo, LCPolSet inPolSet) {
		double sumPolPrem = 0.0;
		double sumTmpFee = 0.0;
		LCPolSchema tmpPolSchema = null;
		LJTempFeeSet tLJTempFeeSet = null;
		// --交费校验精确到印刷号，暂时被否定-2005-3-8
		// tLJTempFeeSet = queryTempFee(prtNo);
		// if (tLJTempFeeSet == null || tLJTempFeeSet.size() <= 0)
		// return this.ERROR;
		// for (int j = 1; j <= tLJTempFeeSet.size(); j++)
		// {
		// //到帐
		// if (tLJTempFeeSet.get(j).getEnterAccDate() != null)
		// {
		// sumTmpFee += tLJTempFeeSet.get(j).getPayMoney();
		// }
		// }
		//
		// for (int i = 1; i <= inPolSet.size(); i++)
		// {
		//
		// tmpPolSchema = (LCPolSchema) inPolSet.get(i);
		// sumPolPrem += tmpPolSchema.getPrem();
		//
		// }
		//
		// if (sumTmpFee > sumPolPrem)return 1;
		// if (sumTmpFee < sumPolPrem)return -1;

		return 0;
	}

	/**
	 * 根据投保单查询投保单所有的交费
	 * 
	 * @param tmpPolSchema
	 *            LCPolSchema
	 * @return LJTempFeeSet
	 */
	private LJTempFeeSet queryTempFee(String prtNo) {
		LJTempFeeSet tLJTempFeeSet;
		LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setOtherNo(prtNo);
		tLJTempFeeSchema.setOtherNoType("6");
		if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
			tLJTempFeeSchema.setOtherNoType("7");
		}
		tLJTempFeeSchema.setConfFlag("0");
		tLJTempFeeSchema.setOperState("0");
		// tLJTempFeeSchema.setRiskCode( tmpPolSchema.getRiskCode());
		tLJTempFeeDB.setSchema(tLJTempFeeSchema);
		tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "queryTempFee", tLJTempFeeDB.mErrors);
			return null;
		}

		return tLJTempFeeSet;
	}

	/**
	 * 查询保单的交费 --精确到险种
	 * 
	 * @param tmpPolSchema
	 *            LCPolSchema
	 * @return LJTempFeeSet
	 */
	private LJTempFeeSet queryTempFeeRisk(LCPolSchema tmpPolSchema) {
		LJTempFeeSet tLJTempFeeSet;
		LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setOtherNo(tmpPolSchema.getPrtNo());
		tLJTempFeeSchema.setOtherNoType("6");
		if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
			tLJTempFeeSchema.setOtherNoType("7");

		}
		tLJTempFeeSchema.setConfFlag("0");
		tLJTempFeeSchema.setRiskCode(tmpPolSchema.getRiskCode());
		tLJTempFeeSchema.setOperState("0");
		tLJTempFeeDB.setSchema(tLJTempFeeSchema);

		tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "queryTempFee", tLJTempFeeDB.mErrors);
			return null;
		}
		return tLJTempFeeSet;

	}

	/**
	 * 比较保单的交费 --精确到险种 --没查到报错
	 * 
	 * @param tmpPolSchema
	 *            LCPolSchema
	 * @return LJTempFeeSet
	 */
	private boolean compareTempFeeRiskErr(LCPolSet inPolSet) {
		int tPolFlag = 0; // add by heyq 如果此险种被拒保或延期，则不需交验是否已交费
		// 安险种分组保单交费求和
		String sql = "select sum( paymoney ),riskcode from ljtempfee where otherno='"
				+ inPolSet.get(1).getPrtNo()
				+ "' and othernotype='6' and "
				+ " confflag='0'  and (EnterAccDate is not null ) and operstate='0' group by riskcode  ";

		// 如果是银代用7查询，如果是个险用6查询
		if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {

			sql = "select sum( paymoney ),riskcode from ljtempfee where otherno='"
					+ inPolSet.get(1).getPrtNo()
					+ "' and othernotype='7' and "
					+ " confflag='0'  and (EnterAccDate is not null ) and operstate='0' group by riskcode  ";
		}

		ExeSQL exesql = new ExeSQL();
		SSRS ssrs = exesql.execSQL(sql);
		if (ssrs == null) {
			CError.buildErr(this, "交费不足或交费没有到帐");
			return false;
		}
		double sumtmpfeemoney = 0;
		double sumpolprem = 0;
		// 按险种比较交费信息
		for (int i = 1; i <= ssrs.getMaxRow(); i++) {
			tPolFlag = 0;
			String riskcode = (String) ssrs.GetText(i, 2);
			double tempfeemoney = Double.parseDouble(ssrs.GetText(i, 1));
			sumtmpfeemoney += tempfeemoney;
			double polprem = 0;
			for (int j = 1; j <= inPolSet.size(); j++) {
				if (inPolSet.get(j).getRiskCode().equals(riskcode)) {
					polprem += inPolSet.get(j).getPrem();
					tPolFlag = 1; // add by heyq 如果此险种被拒保或延期，则不需交验是否已交费
				}
			}
			if (tPolFlag == 1) {
				// modify by zhangxing
				// double differ = Math.abs(tempfeemoney - polprem);
				double differ = polprem - sumtmpfeemoney;
				if (differ > 0.001) {
					CError.buildErr(this, "交费不足或交费没有到帐");
					return false;
				}
			}
		}

		// 整个个人保单交费保存
		for (int j = 1; j <= inPolSet.size(); j++) {
			sumpolprem += inPolSet.get(j).getPrem();
		}
		// double differ = Math.abs(sumpolprem - sumtmpfeemoney);
		double differ = sumpolprem - sumtmpfeemoney;
		if (differ > 0) {
			CError.buildErr(this, "保单总交费不足或交费没有到帐");
			return false;
		}
		/*
		 * LJTempFeeSet tLJTempFeeSet; LJTempFeeSchema tLJTempFeeSchema = null;
		 * LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB(); tLJTempFeeSchema = new
		 * LJTempFeeSchema();
		 * tLJTempFeeSchema.setOtherNo(tmpPolSchema.getPrtNo());
		 * tLJTempFeeSchema.setOtherNoType("4");
		 * tLJTempFeeSchema.setConfFlag("0");
		 * tLJTempFeeSchema.setRiskCode(tmpPolSchema.getRiskCode());
		 * tLJTempFeeDB.setSchema(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeSet = tLJTempFeeDB.query();
		 * 
		 * if (tLJTempFeeDB.mErrors.needDealError()) { CError.buildErr(this,
		 * "交费信息查询错误", tLJTempFeeDB.mErrors); return false; } if (tLJTempFeeSet ==
		 * null || tLJTempFeeSet.size() == 0) { CError.buildErr(this, "险种[" +
		 * tmpPolSchema.getRiskCode() + "]没有交费"); return false; } double prem =
		 * 0.00; for (int i = 1; i <= tLJTempFeeSet.size(); i++) { if
		 * (tLJTempFeeSet.get(i).getEnterAccDate() == null) {
		 * CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode() +
		 * "]交费没有到帐"); return false; } //所交保费求和 prem +=
		 * tLJTempFeeSet.get(i).getPayMoney(); } if
		 * (Math.abs(tmpPolSchema.getPrem() - prem) <= 0.001) {
		 * CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode() +
		 * "]交费金额与保单载明金额不一致，请查证"); return false; }
		 */
		return true;

	}

	/**
	 * 比较保单的交费 --精确到险种 --没查到报错
	 * 
	 * @param tmpPolSchema
	 *            LCPolSchema
	 * @return LJTempFeeSet
	 */
	private boolean compareTempFeeRiskErr(LCPolSchema tmpPolSchema) {
		LJTempFeeSet tLJTempFeeSet;
		LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setOtherNo(tmpPolSchema.getPrtNo());
		tLJTempFeeSchema.setOtherNoType("6");
		if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
			tLJTempFeeSchema.setOtherNoType("7");
		}
		tLJTempFeeSchema.setConfFlag("0");
		tLJTempFeeSchema.setRiskCode(tmpPolSchema.getRiskCode());
		tLJTempFeeSchema.setOperState("0");
		tLJTempFeeDB.setSchema(tLJTempFeeSchema);

		tLJTempFeeSet = tLJTempFeeDB.query();

		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "交费信息查询错误", tLJTempFeeDB.mErrors);
			return false;
		}
		if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
			CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode() + "]没有交费");
			return false;
		}
		double prem = 0.00;
		for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
			if (tLJTempFeeSet.get(i).getEnterAccDate() == null) {
				CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode()
						+ "]交费没有到帐");
				return false;
			}
			// 所交保费求和
			prem += tLJTempFeeSet.get(i).getPayMoney();
		}
		if (Math.abs(tmpPolSchema.getPrem() - prem) <= 0.001) {
			CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode()
					+ "]交费金额与保单载明金额不一致，请查证");
			return false;
		}
		return true;

	}

	/**
	 * 处理财务数据 包括暂交费，实交费表，退费处理等,更新LCCont中保费保额
	 * 
	 * @param financeData
	 *            VData financeData ---(VData) PolData --- LCPolSchema ---
	 *            LCPremSet
	 * 
	 * ---- LCTempFeeSet //(运行过程种，加入暂交费信息) ---- 计算过程仲改变险种保单的living money
	 */
	private MMap dealFinance(LCContSchema tLCContSchema, VData financeData,
			String newContNo, String tPaySerialNo) {
		VData result = new VData();
		VData onePolVData = null;
		LCPolSchema tmpPolSchema = null;
		LCPremSet tmpPremSet = null;
		VData lackVData = new VData(); // 保费不足的保单列表
		VData redundantVData = new VData(); // 保费有余的保单列表
		// VData balanceVData = new VData();
		double sumPrem = 0.0;
		double sumAmnt = 0.0;
		double sumContPrem = 0.0;

		MMap tmpMap = new MMap();
		double sumTmpRiskFee = 0.0;
		// 循环合同保单中所有险种保单，进行保费均匀
		for (int i = 0; i < financeData.size(); i++) {
			onePolVData = (VData) financeData.get(i);
			if (onePolVData == null || onePolVData.size() <= 0) {
				continue;
			}

			tmpPolSchema = (LCPolSchema) onePolVData.getObjectByObjectName(
					"LCPolSchema", 0);

			sumAmnt += tmpPolSchema.getAmnt();
			tmpPremSet = (LCPremSet) onePolVData.getObjectByObjectName(
					"LCPremSet", 0);
			// 求总保费
			for (int t = 1; t <= tmpPremSet.size(); t++) {
				sumPrem += tmpPremSet.get(t).getPrem();
			}

			// sumContPrem += sumPrem; /** 如果多被保险人同一险种交费，会造成保费求和错误，替换算法
			// 2005-3-10 **/
			sumContPrem += tmpPolSchema.getPrem();

			/** 如果多被保险人同一险种交费，会造成保费求和，故去掉 2005-3-9 wujs* */
			/** * tmpPolSchema.setPrem(PubFun.setPrecision(sumPrem,"0.00")); ** */

			// 根据险种取暂交费
			LJTempFeeSet riskTempFeeSet = this.queryTempFeeRisk(tmpPolSchema);
			// if (riskTempFeeSet == null || riskTempFeeSet.size() <= 0)
			// {
			// logger.debug("没有查询到险种保单相关的暂交费:");
			// continue;
			// }
			if (riskTempFeeSet != null && riskTempFeeSet.size() > 0) {
				// 更新暂交费信息,
				tmpMap.add(this.prepareFeeInfo(tmpPolSchema, riskTempFeeSet,
						tmpPremSet, tPaySerialNo, newContNo));

				// 缓存暂交费信息

				this.cacheTempFee(tmpPolSchema.getRiskCode(), riskTempFeeSet
						.get(1));

			}

			// 生成个人实交表
			tmpMap.add(prepareLJAPayPerson(tmpPolSchema, tmpPremSet,
					tPaySerialNo));

			// 求同一险种暂交费总和
			double tmpRiskFee = sumPayMoney(riskTempFeeSet);
			/** 均保费,无意义，去掉 2005-3-9 wujs* */
			/**
			 * tmpPolSchema.setLeavingMoney(PubFun.setPrecision(tmpRiskFee -
			 * tmpPolSchema.getPrem(),"0.00"));**
			 */
			sumTmpRiskFee += tmpRiskFee;
			// 暂交费信息加入到计算元素中
			onePolVData.add(riskTempFeeSet);

			// 划分两个列表:不足与有余
			if (tmpRiskFee > tmpPolSchema.getPrem()) {
				redundantVData.add(onePolVData);
			}
			if (tmpRiskFee < tmpPolSchema.getPrem()) {
				lackVData.add(onePolVData);
			}
			// add by yaory
			// 在此将902险种的ContNo和PolNo复制
			LCInsureAccClassSet newLCInsureAccClassSet = new LCInsureAccClassSet();
			LCInsureAccTraceSet newLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LCInsureAccFeeSet newLCInsureAccFeeSet = new LCInsureAccFeeSet();
			LCInsureAccClassFeeSet newLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
			LCInsureAccFeeTraceSet newLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
			String mainpolno = tmpPolSchema.getMainPolNo();
			String aPolNo = tmpPolSchema.getPolNo();
			if (aPolNo.equals(mainpolno) && mLCInsureAccClassSet != null
					&& mLCInsureAccTraceSet != null) {
				for (int j = 0; j < mLCInsureAccClassSet.size(); j++) {
					LCInsureAccClassSchema aLCInsureAccClassSchema = new LCInsureAccClassSchema();
					aLCInsureAccClassSchema = mLCInsureAccClassSet.get(j + 1);
					aLCInsureAccClassSchema.setPolNo(aPolNo);
					aLCInsureAccClassSchema.setContNo(newContNo);
					newLCInsureAccClassSet.add(aLCInsureAccClassSchema);
				}
				for (int j = 0; j < mLCInsureAccTraceSet.size(); j++) {
					LCInsureAccTraceSchema aLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					aLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(j + 1);
					aLCInsureAccTraceSchema.setPolNo(aPolNo);
					aLCInsureAccTraceSchema.setContNo(newContNo);
					aLCInsureAccTraceSchema.setPayDate(tmpPolSchema
							.getCValiDate());
					newLCInsureAccTraceSet.add(aLCInsureAccTraceSchema);
				}
				for (int j = 0; j < mLCInsureAccFeeSet.size(); j++) {
					LCInsureAccFeeSchema aLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
					aLCInsureAccFeeSchema = mLCInsureAccFeeSet.get(j + 1);
					aLCInsureAccFeeSchema.setPolNo(aPolNo);
					aLCInsureAccFeeSchema.setContNo(newContNo);
					newLCInsureAccFeeSet.add(aLCInsureAccFeeSchema);
				}
				for (int j = 0; j < mLCInsureAccClassFeeSet.size(); j++) {
					LCInsureAccClassFeeSchema aLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
					aLCInsureAccClassFeeSchema = mLCInsureAccClassFeeSet
							.get(j + 1);
					aLCInsureAccClassFeeSchema.setPolNo(aPolNo);
					aLCInsureAccClassFeeSchema.setContNo(newContNo);
					newLCInsureAccClassFeeSet.add(aLCInsureAccClassFeeSchema);
				}
				for (int j = 0; j < mLCInsureAccFeeTraceSet.size(); j++) {
					LCInsureAccFeeTraceSchema aLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
					aLCInsureAccFeeTraceSchema = mLCInsureAccFeeTraceSet
							.get(j + 1);
					aLCInsureAccFeeTraceSchema.setPolNo(aPolNo);
					aLCInsureAccFeeTraceSchema.setContNo(newContNo);
					newLCInsureAccFeeTraceSet.add(aLCInsureAccFeeTraceSchema);
				}
				tmpMap.put(newLCInsureAccClassSet, "INSERT");
				tmpMap.put(newLCInsureAccTraceSet, "INSERT");
				tmpMap.put(newLCInsureAccFeeSet, "INSERT");
				tmpMap.put(newLCInsureAccClassFeeSet, "INSERT");
				tmpMap.put(newLCInsureAccFeeTraceSet, "INSERT");
			}
			// mLCInsureAccClassSet
			// mLCInsureAccTraceSet
			// mLCInsureAccFeeSet
			// mLCInsureAccClassFeeSet

			// 还要加一个条件，就是数据库中存在可以修改的数据，如果没有还执行可能会报错
			// ExeSQL rexeSql = new ExeSQL();
			// SSRS nSSRS = new SSRS();
			// nSSRS = rexeSql.execSQL("select * from lcinsureaccclass where
			// ContNo='" + tLCContSchema.getContNo() + "'");

			//
			// if(mainpolno.equals(tmpPolSchema.getPolNo()) && nSSRS.MaxRow>0)
			// {
			// tmpMap.put("update lcinsureaccclass set ContNo='" + newContNo +
			// "',polno='" + tmpPolSchema.getPolNo() +
			// "',mainpolno='"+tmpPolSchema.getPolNo()+"',ModifyDate='" +
			// PubFun.getCurrentDate() +
			// "',ModifyTime='" + PubFun.getCurrentTime() +
			// "' where ContNo='" + tLCContSchema.getContNo() + "'",
			// "UPDATE"
			// );
			// tmpMap.put("update lcinsureacc set ContNo='" + newContNo +
			// "',polno='" + tmpPolSchema.getPolNo() +
			// "',mainpolno='"+tmpPolSchema.getPolNo()+"',ModifyDate='" +
			// PubFun.getCurrentDate() +
			// "',ModifyTime='" + PubFun.getCurrentTime() +
			// "' where ContNo='" + tLCContSchema.getContNo() + "'",
			// "UPDATE"
			// );
			// tmpMap.put("update lcinsureacctrace set ContNo='" + newContNo +
			// "',polno='" + tmpPolSchema.getPolNo() +
			// "',mainpolno='"+tmpPolSchema.getPolNo()+"',ModifyDate='" +
			// PubFun.getCurrentDate() +
			// "',ModifyTime='" + PubFun.getCurrentTime() +
			// "' where ContNo='" + tLCContSchema.getContNo() + "'",
			// "UPDATE"
			// );
			// }else{
			// if(nSSRS.MaxRow>0)
			// {
			// tmpMap.put("update lcinsureaccclass set ContNo='" +
			// newContNo +
			// "',polno='" + tmpPolSchema.getPolNo() +
			// "',ModifyDate='" + PubFun.getCurrentDate() +
			// "',ModifyTime='" + PubFun.getCurrentTime() +
			// "' where ContNo='" + tLCContSchema.getContNo() +
			// "'",
			// "UPDATE"
			// );
			// tmpMap.put("update lcinsureacc set ContNo='" + newContNo +
			// "',polno='" + tmpPolSchema.getPolNo() +
			// "',ModifyDate='" + PubFun.getCurrentDate() +
			// "',ModifyTime='" + PubFun.getCurrentTime() +
			// "' where ContNo='" + tLCContSchema.getContNo() +
			// "'",
			// "UPDATE"
			// );
			// tmpMap.put("update lcinsureacctrace set ContNo='" +
			// newContNo +
			// "',polno='" + tmpPolSchema.getPolNo() +
			// "',ModifyDate='" + PubFun.getCurrentDate() +
			// "',ModifyTime='" + PubFun.getCurrentTime() +
			// "' where ContNo='" + tLCContSchema.getContNo() +
			// "'",
			// "UPDATE"
			// );
			// }
			// }
			logger.debug("呵呵，，，哈哈，，，");

		} // end for financedata

		PubFun.setPrecision(sumContPrem, "0.00");
		// 如果是个单,生成实交个人表和实交总表,并均匀保费-暂交费
		if ("1".equals(tLCContSchema.getContType())) {

			// 生成总实交
			MMap JAPayMap = prepareLJAPay(tLCContSchema, sumContPrem,
					newContNo, tPaySerialNo);
			tmpMap.add(JAPayMap);

			// 更新合同保费保额
			tLCContSchema.setPrem(sumContPrem);
			tLCContSchema.setAmnt(sumAmnt);

			/** 均保费,结构不支持，去掉 2005-3-9 wujs* */
			// modify by zhangxing,溢交退费，应该把这一部分再加上

			if (lackVData.size() <= 0) {
				logger.debug("没有少交保费的保单");
			}
			if (redundantVData.size() <= 0) {
				logger.debug("没有保费多余的保单");
			}
			int redundantPos = 0;
			int lackPos = 0;
			if (lackVData.size() > 0 && redundantVData.size() > 0) {
				VData oneLackData = null;
				VData oneRedundantData = null;
				LCPolSchema lackPol = null;
				LCPolSchema redundantPolSchema = null;
				LJTempFeeSet lackFeeSet = null;
				LJTempFeeSet redundantFeeSet = null;
				double adjustMoney = 0.0; // 用以调配的money

				double left = 0.0;
				// 以富余补不足
				while (lackPos < lackVData.size()
						&& redundantPos < redundantVData.size()) {
					if (left >= 0) {
						oneLackData = (VData) lackVData.get(lackPos);
						lackPol = (LCPolSchema) oneLackData
								.getObjectByObjectName("LCPolSchema", 0);
						lackFeeSet = (LJTempFeeSet) oneLackData
								.getObjectByObjectName("LJTempFeeSet", 0);
						// 还缺多少钱
						lackPol.setLeavingMoney(PubFun.setPrecision(
								sumPayMoney(lackFeeSet) - lackPol.getPrem(),
								"0.00"));
					}
					if (left <= 0) {
						oneRedundantData = (VData) redundantVData
								.get(redundantPos);
						redundantPolSchema = (LCPolSchema) oneRedundantData
								.getObjectByObjectName("LCPolSchema", 0);
						redundantFeeSet = (LJTempFeeSet) oneRedundantData
								.getObjectByObjectName("LJTempFeeSet", 0);
						// 还多多少钱
						redundantPolSchema
								.setLeavingMoney(PubFun.setPrecision(
										sumPayMoney(redundantFeeSet)
												- redundantPolSchema.getPrem(),
										"0.00"));
					}

					double lackmoney = lackPol.getLeavingMoney();
					double redundantmoney = redundantPolSchema
							.getLeavingMoney();

					left = redundantmoney + lackmoney;
					// logger.debug("adjustMoney:" + adjustMoney);
					PubFun.setPrecision(left, "0.00");
					if (left >= 0) { // 富余足以加给不足
						adjustMoney = lackmoney;
						// tmpMap.add(createTempFee(redundantPolSchema,
						// lackPol, adjustMoney));
						tmpMap.add(this.prepareUpdateTempFee(oneLackData));
						lackPol.setLeavingMoney(0);
						redundantPolSchema.setLeavingMoney(PubFun.setPrecision(
								redundantPolSchema.getLeavingMoney()
										+ adjustMoney, "0.00"));
						lackPos++;
					} else { // 富余不足加给不足
						// 把富足的全部分给不足
						adjustMoney = redundantmoney;
						// tmpMap.add(createTempFee(redundantPolSchema,
						// lackPol,
						// adjustMoney));
						tmpMap.add(this.prepareUpdateTempFee(oneRedundantData));
						redundantPolSchema.setLeavingMoney(0);
						lackPol.setLeavingMoney(PubFun.setPrecision(lackPol
								.getLeavingMoney()
								- adjustMoney, "0.00"));
						redundantPos++;
					}

				}

			} // end if

			// 还有少交保费的保单,要生成催收
			if (lackPos < lackVData.size()) {
				// logger.debug("还有少交保费的保单,要生成催收");
				return null;
			}
			

			// if ("1".equals(tLCContSchema.getOutPayFlag()))
			// {
			// 溢交退费
			// 还有有保费多余的保单,要生成退费
			if (redundantPos < redundantVData.size()) {
				LCPolSet withdrawLCPolSet = new LCPolSet();
				// logger.debug("还有有保费多余的保单,要生成退费");
				for (int t = redundantPos; t < redundantVData.size(); t++) {
					VData oneRedundantData = (VData) redundantVData.get(t);
					withdrawLCPolSet.add((LCPolSchema) oneRedundantData
							.getObjectByObjectName("LCPolSchema", 0));

					NewPolFeeWithdrawBL tNewPolFeeWithdrawBL = new NewPolFeeWithdrawBL();
					VData inputData = new VData();
					inputData.add(withdrawLCPolSet);
					inputData.add(tLCContSchema);
					inputData.add(this.mGlobalInput);
					MMap map = tNewPolFeeWithdrawBL.submitDataAllNew(inputData);
					tmpMap.add(map);
					break;
				}
			}
			// }
			// else
			// { //移交到续期
			// tLCContSchema.setDif(PubFun.setPrecision(sumTmpRiskFee -
			// sumContPrem, "0.00"));
			// }

			// end if GrpPol_flag
			// } //end for financedata
		}

		return tmpMap;
	}

	/**
	 * 生成LJAPay的数据
	 * 
	 * @param tLCContSchema
	 *            LCContSchema
	 * @param sumpay
	 *            double
	 * @param newContNo
	 *            String
	 * @param tPaySerialNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareLJAPay(LCContSchema tLCContSchema, double sumpay,
			String newContNo, String tPaySerialNo) {
		MMap tmpmap = new MMap();
		sumpay = PubFun.setPrecision(sumpay, "0.00");
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		tLJAPaySchema.setPayNo(tPaySerialNo);
		tLJAPaySchema.setIncomeNo(newContNo);
		tLJAPaySchema.setIncomeType("2");
		tLJAPaySchema.setAppntNo(tLCContSchema.getAppntNo());
		tLJAPaySchema.setSumActuPayMoney(sumpay);
		tLJAPaySchema.setPayDate(maxPayDate);
		tLJAPaySchema.setEnterAccDate(maxEnterAccDate);
		tLJAPaySchema.setConfDate(PubFun.getCurrentDate());
		tLJAPaySchema.setSerialNo(tPaySerialNo);
		tLJAPaySchema.setOperator(mGlobalInput.Operator);
		tLJAPaySchema.setMakeDate(PubFun.getCurrentDate());
		tLJAPaySchema.setMakeTime(PubFun.getCurrentTime());
		tLJAPaySchema.setModifyDate(PubFun.getCurrentDate());
		tLJAPaySchema.setModifyTime(PubFun.getCurrentTime());

		tLJAPaySchema.setManageCom(tLCContSchema.getManageCom());
		tLJAPaySchema.setAgentCom(tLCContSchema.getAgentCom());
		tLJAPaySchema.setAgentType(tLCContSchema.getAgentType());
		tLJAPaySchema.setOtherNo(tLCContSchema.getProposalContNo());
		tLJAPaySchema.setOtherNoType("6"); // 个人投保单号
		if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
			tLJAPaySchema.setOtherNoType("7");
		}

		LJAPaySet tLJAPaySet = new LJAPaySet();
		tLJAPaySet.add(tLJAPaySchema);
		tmpmap.put(tLJAPaySet, this.INSERT);
		// 求收费总额
		// String updatesql ="update ljapay set SumActuPayMoney=(select
		// sum(t.SumActuPayMoney) from ljapayperson t where
		// t.payno=ljapay.payno)"
		// +" where payno='"+ tPaySerialNo+ "'";
		// tmpmap.put( updatesql, "UPDATE");

		return tmpmap;
	}

	/**
	 * 根据传入的暂交费集合,求暂交费和
	 * 
	 * @param riskTempFeeSet
	 *            LJTempFeeSet
	 * @return double
	 */
	private double sumPayMoney(LJTempFeeSet riskTempFeeSet) {
		double tmpRiskFee = 0.0;
		for (int j = 1; j <= riskTempFeeSet.size(); j++) {
			tmpRiskFee += riskTempFeeSet.get(j).getPayMoney();
		}
		return tmpRiskFee;
	}

	/**
	 * 合同状态改变
	 * 
	 * @param inContSchema
	 *            LCContSchema
	 * @param newContNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareCont(LCContSchema inContSchema, String newContNo) {
		MMap tmpMap = new MMap();
		LCContSchema contSchema = new LCContSchema();

		LCContStateSet tLCContStateSet = new LCContStateSet();
		LCContStateSet dLCContStateSet = new LCContStateSet();
		LCContStateDB dLCContStateDB = new LCContStateDB();

		contSchema.setSchema(inContSchema);
		if (contSchema.getUWFlag() != null
				&& contSchema.getUWFlag().equals("1")) {
			contSchema.setState("0");
		} else {
			contSchema.setState("1");
			contSchema.setUWFlag("9");
		}
		// if(contSchema.getUWFlag()==null)
		// {
		// contSchema.setUWFlag("9");
		// contSchema.setState("1");
		// }

		contSchema.setContNo(newContNo);
		contSchema.setSignCom(mGlobalInput.ComCode);
		contSchema.setSignDate(mCurrentDate);
		contSchema.setSignTime(mCurrentTime);
		// 非团单处理签单时间
		if ("1".equals(contSchema.getContType())) {

			// 加个单签单时间
			String polupdate = "update lcpol set signDate ='" + mCurrentDate
					+ "'" + ", signtime='" + mCurrentTime + "'"
					+ " where contno='" + newContNo + "'";
			// 加账户成立日

			tmpMap.put(polupdate, "UPDATE");
		}
		// tmpMap.put("update lcpol set state='1' where
		// contno='"+contSchema.getContNo()+"' and (uwflag is null or
		// uwflag!='1') ", "UPDATE");
		// tmpMap.put("update lcpol set state='1' where
		// contno='"+contSchema.getContNo()+"' and (uwflag is null or
		// uwflag!='1') ", "UPDATE");
		contSchema.setAppFlag("1");
		contSchema.setPaytoDate(maxPayDate);

		contSchema.setFirstPayDate(mFirstPayDate);

		// 卡单标志
		if ("1".equals(contSchema.getCardFlag())) {
			contSchema.setGetPolDate(PubFun.getCurrentDate());
			contSchema.setGetPolTime(PubFun.getCurrentTime());
			contSchema.setCustomGetPolDate(PubFun.getCurrentDate());
			contSchema.setCValiDate(PubFun.getCurrentDate());
		}

		// 为保单状态表准备数据

		String contNo = "";
		String polNo = "";
		String mCvalidate = "";

		contNo = newContNo;
		logger.debug("contNo" + contNo);

		for (int k = 0; k < mLCPolSet.size(); k++) {

			polNo = mLCPolSchema.getPolNo();
			logger.debug("polNo" + polNo);
			mCvalidate = mLCContSet.get(1).getCValiDate();
			logger.debug("mCvalidate==" + mCvalidate);
			logger.debug("Pol.mCvalidate==" + mLCPolSchema.getCValiDate());

			for (int i = 1; i <= dLCContStateSet.size(); i++) {
				// contNo =
				// dLCContStateSet.get(i).getContNo();logger.debug("contNo"+contNo);
				// insuredNo =
				// dLCContStateSet.get(i).getInsuredNo();logger.debug("insuredNo"+insuredNo);
				// polNo =
				// dLCContStateSet.get(i).getPolNo();logger.debug("polNo"+polNo);
			}

			// Available: 0-有效 1-失效 （险种状态）
			// Terminate: 0-未终止 1-终止 （险种状态）
			// Lost: 0-未挂失 1-挂失 （保单状态）
			// PayPrem: 0-正常交费 1-保费自动垫交 （险种状态）
			// Loan: 0-未贷款 1-贷款 （险种状态和保单状态）
			// BankLoan: 0-未质押银行贷款 1-质押银行贷款 （险种状态和保单状态）
			// RPU：0-未减额缴清 1-减额缴清 （险种状态）
			// for (int i = 1; i <= 7; i++)
			for (int i = 1; i <= 1; i++) {

				LCContStateSchema tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setContNo(contNo);

				tLCContStateSchema.setInsuredNo(mInsuredNo);
				tLCContStateSchema.setPolNo(mLCPolSet.get(k + 1).getPolNo());
				if (i == 1) {
					tLCContStateSchema.setStateType("Available");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				if (i == 2) {
					tLCContStateSchema.setStateType("Terminate");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				if (i == 3) {
					tLCContStateSchema.setStateType("Lost");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				if (i == 4) {
					tLCContStateSchema.setStateType("PayPrem");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				if (i == 5) {
					tLCContStateSchema.setStateType("Loan");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				if (i == 6) {
					tLCContStateSchema.setStateType("BankLoan");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				if (i == 7) {
					tLCContStateSchema.setStateType("RPU");
					logger.debug("i=" + i + "StateType"
							+ tLCContStateSchema.getStateType());
				}
				tLCContStateSchema.setState("0");
				tLCContStateSchema.setStartDate(mCvalidate);
				tLCContStateSchema.setOperator(mGlobalInput.Operator);
				tLCContStateSchema.setMakeDate(PubFun.getCurrentDate());
				tLCContStateSchema.setMakeTime(PubFun.getCurrentTime());
				tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());

				tLCContStateSet.add(tLCContStateSchema);
			}
			// mLCContStateSet.add(tLCContStateSet);

			logger.debug("tLCContStateSet" + tLCContStateSet);

		}

		tmpMap.put(inContSchema, "DELETE");
		tmpMap.put(contSchema, "INSERT");
		tmpMap.put(tLCContStateSet, "INSERT");

		return tmpMap;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// @@可以处理保单集和单个保单的情况
		mLCContSet = (LCContSet) mInputData.getObjectByObjectName("LCContSet",
				0);

		if (mLCContSet == null || mLCContSet.size() <= 0) {
			CError.buildErr(this, "没有传入数据集!");
			return false;
		}
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		// 签单使用统一时间
		if (mTransferData != null) {
			mCurrentDate = (String) mTransferData.getValueByName("CurrentDate");
			mCurrentTime = (String) mTransferData.getValueByName("CurrentTime");
			mark = (String) mTransferData.getValueByName("mark");
			CardContNo = (String) mTransferData.getValueByName("CardContNo");// 卡单保单号码
			mUsePubAcc = (String) mTransferData.getValueByName("MFee");// 卡单保单号码
			if (mUsePubAcc == null || mUsePubAcc.equals("")) {
				mUsePubAcc = "N";
			}
		}
		if (mCurrentDate == null) {
			mCurrentDate = PubFun.getCurrentDate();
			mCurrentTime = PubFun.getCurrentTime();

		}

		return true;

	}

	/**
	 * 根据保单生成一张新的暂交费表
	 * 
	 * @param polSchema
	 *            LCPolSchema
	 * @param money
	 *            double
	 * @return MMap
	 */
	private MMap createTempFee(LCPolSchema grpmorePolSchema,
			LCPolSchema grplackPolSchema, double money) {
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

	/**
	 * 根据保单生成一张新的暂交费表
	 * 
	 * @param polSchema
	 *            LCPolSchema
	 * @param money
	 *            double
	 * @return MMap
	 */
	// private MMap createTempFee(LCPolSchema moreSchema,LCPolSchema
	// lackPolSchema, double money)
	// {
	// MMap tmpMap = new MMap();
	// LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	// //转换精度
	// money = PubFun.setPrecision(money, "0.00");
	// String tPaySerialNo = lackPolSchema.getPrtNo();
	// tLJTempFeeSchema.setTempFeeNo(tPaySerialNo);
	// tLJTempFeeSchema.setTempFeeType("8");
	// tLJTempFeeSchema.setRiskCode(lackPolSchema.getRiskCode());
	// tLJTempFeeSchema.setOtherNo(lackPolSchema.getPolNo());
	// String modifyDate = PubFun.getCurrentDate();
	// String modifyTime = PubFun.getCurrentTime();
	// tLJTempFeeSchema.setOtherNoType("0");
	// tLJTempFeeSchema.setConfDate(mCurrentDate);
	// tLJTempFeeSchema.setConfFlag("1");
	// tLJTempFeeSchema.setMakeDate(modifyDate);
	// tLJTempFeeSchema.setMakeTime(modifyTime);
	// tLJTempFeeSchema.setModifyDate(modifyDate);
	// tLJTempFeeSchema.setModifyTime(modifyTime);
	// tLJTempFeeSchema.setPayDate(lackPolSchema.getPaytoDate());
	// tLJTempFeeSchema.setAgentCode(lackPolSchema.getAgentCode());
	// tLJTempFeeSchema.setAgentCom(lackPolSchema.getAgentCom());
	// tLJTempFeeSchema.setAgentGroup(lackPolSchema.getAgentGroup());
	// tLJTempFeeSchema.setAgentType(lackPolSchema.getAgentType());
	// tLJTempFeeSchema.setAPPntName(lackPolSchema.getAppntName());
	// // tLJTempFeeSchema.setEnterAccDate(confDate);
	// tLJTempFeeSchema.setSerialNo(tPaySerialNo);
	//
	// tLJTempFeeSchema.setPayMoney(money);
	// tLJTempFeeSchema.setConfMakeDate(mCurrentDate);
	// tLJTempFeeSchema.setSaleChnl(lackPolSchema.getSaleChnl());
	// tLJTempFeeSchema.setManageCom(lackPolSchema.getManageCom());
	// tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
	// // tLJTempFeeSchema.decode(lackPolSchema.getMasterPolNo());
	// tmpMap.put(tLJTempFeeSchema, this.INSERT);
	// /*
	// LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
	// tLJTempFeeClassSchema.setTempFeeNo(tPaySerialNo);
	// tLJTempFeeClassSchema.setPayMode("7");
	// tLJTempFeeClassSchema.setConfMakeDate(mCurrentDate);
	// tLJTempFeeClassSchema.setConfDate(mCurrentDate);
	// tLJTempFeeClassSchema.setConfFlag("1");
	// tLJTempFeeClassSchema.setPayMoney(money);
	// tLJTempFeeClassSchema.setSerialNo(tPaySerialNo);
	// // tLJTempFeeClassSchema.setAccName();
	// tLJTempFeeClassSchema.setMakeDate(mCurrentDate);
	// tLJTempFeeClassSchema.setMakeTime(mCurrentTime);
	// tLJTempFeeClassSchema.setModifyDate( mCurrentDate );
	// tLJTempFeeClassSchema.setModifyTime( mCurrentTime );
	// tLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
	// //tLJTempFeeClassSchema.set
	// LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
	// tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
	//
	// tmpMap.put(tLJTempFeeClassSet, INSERT);
	// */
	// return tmpMap;
	// }

	/**
	 * 根据传入的暂交费信息生成更新的数据
	 * 
	 * @param tmpFeeData
	 *            VData
	 * @param polSchema
	 *            LCPolSchema
	 * @return MMap
	 */
	private MMap prepareUpdateTempFee(VData tmpFeeData) {

		MMap tmpMap = new MMap();
		LJTempFeeSet tLJTempFeeSet = (LJTempFeeSet) tmpFeeData
				.getObjectByObjectName("LJTempFeeSet", 0);
		LCPolSchema polSchema = (LCPolSchema) tmpFeeData.getObjectByObjectName(
				"LCPolSchema", 0);
		LJTempFeeSchema tLCTempSchema = null;
		String modifyDate = PubFun.getCurrentDate();
		String modifyTime = PubFun.getCurrentTime();
		// 拷贝一份增加到删除队列
		tmpMap.put(PubFun.copySchemaSet(tLJTempFeeSet), this.DELETE);

		for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
			tLCTempSchema = tLJTempFeeSet.get(i);
			tLCTempSchema.setOtherNo(polSchema.getPolNo());
			// 2005-3-8更改为2
			// /tLCTempSchema.setOtherNoType("0");
			// tLCTempSchema.setOtherNoType("2");
			tLCTempSchema.setOtherNoType("6"); // 根据财务要求将othernotype由2改为6 hl
												// 050802
			if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
				tLCTempSchema.setOtherNoType("7");
			}
			tLCTempSchema.setConfDate(mCurrentDate);
			tLCTempSchema.setConfFlag("1");
			tLCTempSchema.setModifyDate(modifyDate);
			tLCTempSchema.setModifyTime(modifyTime);
		}
		// 增加队列
		tmpMap.put(PubFun.copySchemaSet(tLJTempFeeSet), this.INSERT);
		return tmpMap;
	}

	/**
	 * 暂交费更新 输出：如果发生错误则返回null,否则返回VData 修改tLCPolSchema的firstpaydate
	 */
	private MMap prepareFeeInfo(LCPolSchema tLCPolSchema,
			LJTempFeeSet tLJTempFeeSet, LCPremSet tLCPremSet,
			String tPaySerialNo, String newContNo) {

		MMap tReturn = new MMap();
		String tOldPolNo = tLCPolSchema.getProposalNo();

		String modifyDate = PubFun.getCurrentDate();
		String modifyTime = PubFun.getCurrentTime();
		double sumPay = 0;
		double left = 0;
		FDate fDate = new FDate();
		Date tFirstPayDate = null;
		Date tmaxPayDate = null;
		Date tmaxEnterAccDate = null;

		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();

		int n = tLJTempFeeSet.size();

		for (int i = 1; i <= n; i++) {
			LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);

			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			tLJTempFeeClassDB.setOperState("0");
			LJTempFeeClassSet tLJTempFeeClassSet1 = tLJTempFeeClassDB.query();

			if (tLJTempFeeClassDB.mErrors.needDealError()) {
				CError.buildErr(this, "LJTempFeeClass表取数失败!");
				return null;
			}

			// end of if
			int m = tLJTempFeeClassSet1.size();

			for (int j = 1; j <= m; j++) {
				LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet1
						.get(j);
				tLJTempFeeClassSchema.setConfDate(mCurrentDate);
				tLJTempFeeClassSchema.setConfFlag("1");
				tLJTempFeeClassSchema.setModifyDate(modifyDate);
				tLJTempFeeClassSchema.setModifyTime(modifyTime);
				tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
			}

			// end of for
			sumPay += tLJTempFeeSchema.getPayMoney();

			tLJTempFeeSchema.setOtherNo(newContNo);

			// tLJTempFeeSchema.setOtherNoType("2");
			tLJTempFeeSchema.setOtherNoType("6"); // 20050806 hl
			if (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
				tLJTempFeeSchema.setOtherNoType("7"); // 20050806 hl

			}
			tLJTempFeeSchema.setConfDate(mCurrentDate);
			tLJTempFeeSchema.setConfFlag("1");
			tLJTempFeeSchema.setModifyDate(modifyDate);
			tLJTempFeeSchema.setModifyTime(modifyTime);

			// 取首次交费日期
			Date payDate = fDate.getDate(tLJTempFeeSchema.getPayDate());

			if (tFirstPayDate == null || payDate.before(tFirstPayDate)) {
				tFirstPayDate = payDate;
			}
			// LJAPay中使用
			if (tmaxPayDate == null || tmaxPayDate.before(payDate)) {
				tmaxPayDate = payDate;
			}
			Date enterAccDate = fDate.getDate(tLJTempFeeSchema
					.getEnterAccDate());
			if (tmaxEnterAccDate == null
					|| tmaxEnterAccDate.before(enterAccDate)) {
				tmaxEnterAccDate = enterAccDate;
			}
			tLCPolSchema.setFirstPayDate(tFirstPayDate);
			tLJTempFeeSet.set(i, tLJTempFeeSchema);
		}

		// 全局交至日期，到帐日期等
		if (maxEnterAccDate == null || maxEnterAccDate.before(tmaxEnterAccDate)) {
			maxEnterAccDate = tmaxEnterAccDate;
		}
		if (mFirstPayDate == null || tFirstPayDate.before(mFirstPayDate)) {
			mFirstPayDate = tFirstPayDate;
		}
		if (maxPayDate == null || tmaxPayDate.before(maxPayDate)) {
			maxPayDate = tmaxPayDate;
		}

		if (tLJTempFeeClassSet.size() > 0) {
			tReturn.put(tLJTempFeeClassSet, this.UPDATE);
		}
		if (tLJTempFeeSet.size() > 0) {
			tReturn.put(tLJTempFeeSet, this.UPDATE);
		}
		// end of for
		// }

		return tReturn;
	}

	/**
	 * 创建个人实交
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param tLCPremSet
	 *            LCPremSet
	 * @param tPaySerialNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareLJAPayPerson(LCPolSchema tLCPolSchema,
			LCPremSet tLCPremSet, String tPaySerialNo

	) {
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		String modifyDate = PubFun.getCurrentDate();
		String modifyTime = PubFun.getCurrentTime();
		// 实交个人表
		int m = tLCPremSet.size();
		if (m > 0) {
			MMap tReturn = new MMap();
			for (int i = 1; i <= m; i++) {
				// String tLimit =
				// PubFun.getNoLimit(tLCPolSchema.getManageCom());
				// String tPayNo = PubFun1.CreateMaxNo("PayNo", tLimit);
				LCPremSchema tLCPremSchema = tLCPremSet.get(i);
				LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
				tLJAPayPersonSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJAPayPersonSchema.setPayCount(1);
				tLJAPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJAPayPersonSchema.setContNo(tLCPolSchema.getContNo());
				tLJAPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJAPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJAPayPersonSchema.setPayNo(tPaySerialNo);
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
				tLJAPayPersonSchema.setMakeDate(modifyDate);
				tLJAPayPersonSchema.setMakeTime(modifyTime);
				tLJAPayPersonSchema.setModifyDate(modifyDate);
				tLJAPayPersonSchema.setModifyTime(modifyTime);

				tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJAPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJAPayPersonSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJAPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJAPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJAPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());

				tLJAPayPersonSet.add(tLJAPayPersonSchema);
			}
			tReturn.put(tLJAPayPersonSet, this.INSERT);
			return tReturn;
		}
		return null;
	}

	/**
	 * 打印所有错误信息
	 */
	public void outErrors() {
		if (this.mErrors.needDealError()) {
			logger.debug("错误信息:");
			for (int i = 0; i < this.mErrors.getErrorCount(); i++) {
				logger.debug(this.mErrors.getError(i).errorMessage);
			}
		}
	}

	/**
	 * 缓存暂交费信息
	 * 
	 * @param key
	 *            Object
	 * @param tempFeeSchema
	 *            LJTempFeeSchema
	 */
	private void cacheTempFee(Object key, LJTempFeeSchema tempFeeSchema) {
		this.mLJTempFeeMap.put(key, tempFeeSchema);
	}

	/**
	 * 获取暂交费信息.
	 * 
	 * @param key
	 *            String
	 * @return Object
	 */
	private Object getTempFee(String key) {
		return mLJTempFeeMap.get(key);
	}

	public static void main(String[] args) {
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("00200508260010");
		LCContSet mLCContSet = new LCContSet();
		mLCContSet.add(tLCContSchema);
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86110000";
		tG.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CurrentDate", "2005-08-26");
		tTransferData.setNameAndValue("CurrentTime", "12:00:00");
		tTransferData.setNameAndValue("mark", "card");
		tTransferData.setNameAndValue("CardContNo", "zhangxing");
		tVData.add(tG);
		tVData.add(mLCContSet);
		tVData.add(tTransferData);
		LCContSignBL tLCContSignBL = new LCContSignBL();
		tLCContSignBL.submitData(tVData, "sign");

	}

}
