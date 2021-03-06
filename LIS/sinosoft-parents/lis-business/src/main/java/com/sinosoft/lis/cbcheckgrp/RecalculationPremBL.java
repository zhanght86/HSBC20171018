package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保费重算
 * </p>
 * <p>
 * Description: 保费重算通用类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class RecalculationPremBL {
private static Logger logger = Logger.getLogger(RecalculationPremBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 保单表
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSchema aLCPolSchema = new LCPolSchema();

	// 责任表
	private LCDutySet mLCDutySet = new LCDutySet();

	// 保费项表
	private LCPremSet mLCPremSet = new LCPremSet();

	// 领取项表
	private LCGetSet mLCGetSet = new LCGetSet();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private FDate fDate = new FDate();
	private Date mValiDate = null;

	public RecalculationPremBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		return true;
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
			tLCDutySchema.setDutyCode(null);

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

		LCPremSet tLCPremSet1 = tCalBL.getLCPrem();

		tLCGetSet = tCalBL.getLCGet();
		tLCDutySet = tCalBL.getLCDuty();

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
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(mLCPremSet, "UPDATE");
		map.put(mLCGetSet, "UPDATE");
		map.put(mLCDutySet, "UPDATE");
		map.put(mLCPolSchema, "UPDATE");

		mResult.add(mLCPolSchema);
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		aLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (aLCPolSchema == null) {
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;

		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 准备保单表的信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(aLCPolSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "在获取LCPol表中的数据时失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		mLCPolSchema = tLCPolDB.getSchema();
		mLCPolSchema.setCValiDate(aLCPolSchema.getCValiDate());
		mValiDate = fDate.getDate(mLCPolSchema.getCValiDate());

		int newAge = PubFun.calInterval(fDate.getDate(mLCPolSchema
				.getInsuredBirthday()), mValiDate, "Y");
		logger.debug(newAge);

		mLCPolSchema.setInsuredAppAge(newAge);

		// 准备保费项信息
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(mLCPolSchema.getPolNo());
		mLCPremSet = tLCPremDB.query();
		logger.debug(mLCPremSet.get(1).getOperator());

		// 准备责任项表
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		mLCDutySet = tLCDutyDB.query();

		// 准备领取项表
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(mLCPolSchema.getPolNo());
		mLCGetSet = tLCGetDB.query();

		VData tReCal = this.reCal(mLCPolSchema, mLCGetSet, mLCDutySet,
				mLCPremSet);
		if (tReCal == null) {
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "保费重算失败!";
			return false;
		}

		mLCPremSet.set((LCPremSet) tReCal.get(0));

		int n = mLCPremSet.size();

		for (int i = 1; i <= n; i++) {
			LCPremSchema tLCPremSchema = mLCPremSet.get(i);
			tLCPremSchema.setMakeDate(mLCPolSchema.getMakeDate());
			tLCPremSchema.setMakeTime(mLCPolSchema.getMakeTime());
			tLCPremSchema.setOperator(mGlobalInput.Operator);
			tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPremSchema.setModifyTime(PubFun.getCurrentTime());

			mLCPremSet.set(i, tLCPremSchema);
		}

		mLCGetSet.set((LCGetSet) tReCal.get(1));

		int m = mLCGetSet.size();

		for (int i = 1; i <= m; i++) {
			LCGetSchema tLCGetSchema = mLCGetSet.get(i);
			tLCGetSchema.setGetEndState("0");
			tLCGetSchema.setMakeDate(mLCPolSchema.getMakeDate());
			tLCGetSchema.setMakeTime(mLCPolSchema.getMakeTime());
			tLCGetSchema.setOperator(mGlobalInput.Operator);
			tLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGetSchema.setModifyTime(PubFun.getCurrentTime());

			mLCGetSet.set(i, tLCGetSchema);
		}

		mLCDutySet.set((LCDutySet) tReCal.get(2));

		int K = mLCDutySet.size();

		for (int i = 1; i <= K; i++) {
			LCDutySchema tLCDutySchema = mLCDutySet.get(i);
			tLCDutySchema.setMakeDate(mLCPolSchema.getMakeDate());
			tLCDutySchema.setMakeTime(mLCPolSchema.getMakeTime());
			tLCDutySchema.setOperator(mGlobalInput.Operator);
			tLCDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLCDutySchema.setModifyTime(PubFun.getCurrentTime());

			mLCDutySet.set(i, tLCDutySchema);
		}

		mLCPolSchema = (LCPolSchema) tReCal.get(3);
		mLCPolSchema.setMakeDate(mLCPolSchema.getMakeDate());
		mLCPolSchema.setMakeTime(mLCPolSchema.getMakeTime());
		mLCPolSchema.setOperator(mGlobalInput.Operator);
		mLCPolSchema.setModifyDate(PubFun.getCurrentDate());
		mLCPolSchema.setModifyTime(PubFun.getCurrentTime());
		mLCPolSchema.setSpecifyValiDate("Y");

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String arg[]) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setPolNo("110110000022238");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";

		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLCPolSchema);
		RecalculationPremBL tRecalculationPremBL = new RecalculationPremBL();
		tRecalculationPremBL.submitData(tVData, "");

	}

}
