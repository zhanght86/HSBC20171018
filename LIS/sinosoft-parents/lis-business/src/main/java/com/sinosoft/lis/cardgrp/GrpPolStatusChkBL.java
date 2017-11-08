package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人单状态查询部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class GrpPolStatusChkBL {
private static Logger logger = Logger.getLogger(GrpPolStatusChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mpassflag; // 通过标记

	private String mCalCode; // 计算编码

	private FDate fDate = new FDate();
	private float mValue;

	/** 业务处理相关变量 */
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mmLCGrpContSet = new LCGrpContSet();

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	/** 计算公式表* */

	private LMUWSet mLMUWSet = new LMUWSet();

	private LMCalModeSet mmLMCalModeSet = new LMCalModeSet();
	private LMCalModeSet mLMCalModeSet = new LMCalModeSet();

	private CalBase mCalBase = new CalBase();

	public GrpPolStatusChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---PolStatusChkBL getInputData---");

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = mLCGrpContSet.get(1);
		mLCGrpContSchema = mLCGrpContSet.get(1);

		logger.debug("---PolStatusChkBL checkData---");
		// 数据操作业务处理
		if (!dealData(tLCGrpContSchema)) {
			return false;
		}

		logger.debug("---PolStatusChkBL dealData---");
		// 准备返回的数据
		prepareOutputData();

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCGrpContSchema tLCGrpContSchema) {

		// 准备算法
		if (CheckKinds(tLCGrpContSchema) == false) {
			return false;
		}

		// 取保单信息
		int n = mmLMCalModeSet.size();
		if (n == 0) {
		} else {
			int j = 0;
			mLMCalModeSet.clear();
			for (int i = 1; i <= n; i++) {
				// 取计算编码
				LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
				tLMCalModeSchema = mmLMCalModeSet.get(i);
				mCalCode = tLMCalModeSchema.getCalCode();
				if (CheckPol() == 0) {
				} else {
					j++;
					mLMCalModeSet.add(tLMCalModeSchema);
				}
			}
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;

		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));
		int n = mLCGrpContSet.size();
		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (n > 0) {
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			LCGrpContSchema tLCGrpContSchema = mLCGrpContSet.get(1);
			tLCGrpContDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
			String temp = tLCGrpContSchema.getGrpContNo();
			logger.debug("temp" + temp);
			if (tLCGrpContDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "PolStatusChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = temp + "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "PolStatusChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据传输失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove(LCGrpContSchema tLCGrpContSchema) {
		if (tLCGrpContSchema.getApproveCode() == null
				|| tLCGrpContSchema.getApproveDate() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号："
					+ tLCGrpContSchema.getGrpContNo().trim() + "）";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */

	private boolean CheckKinds(LCGrpContSchema tLCGrpContSchema) {
		String tsql = "";
		mmLMCalModeSet.clear();
		LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
		// 查询算法编码
		tsql = "select * from LMCalMode where riskcode = 'GCstatu' order by calcode";

		LMCalModeDB tLMCalModeDB = new LMCalModeDB();

		mmLMCalModeSet = tLMCalModeDB.executeQuery(tsql);
		if (tLMCalModeDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMCalModeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds";
			this.mErrors.addOneError(tError);
			mLMUWSet.clear();
			return false;
		}
		return true;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCGrpContSchema tLCGrpContSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCGrpContSchema.getPrem());
		mCalBase.setGet(tLCGrpContSchema.getAmnt());
		mCalBase.setMult(tLCGrpContSchema.getMult());

		mCalBase.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		logger.debug(tLCGrpContSchema.getGrpContNo());
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private float CheckPol() {
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator
				.addBasicFactor("GrpContNo", mLCGrpContSchema.getGrpContNo());
		logger.debug(mCalBase.getGrpContNo());
		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Float.parseFloat(tStr);
		}

		logger.debug(mValue);
		return mValue;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		logger.debug("核保标志" + mpassflag);
		mLCGrpContSchema.setUWFlag(mpassflag); // 待人工核保

		mLCGrpContSchema.setUWDate(PubFun.getCurrentDate());

		return true;
	}

	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mLMCalModeSet);
	}

	public VData getResult() {
		return mResult;
	}

}
