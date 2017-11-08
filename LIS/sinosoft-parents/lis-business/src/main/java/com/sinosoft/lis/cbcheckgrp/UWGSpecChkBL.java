package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGUWErrorDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGUWErrorSchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGUWErrorSet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LDSysTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保团体人工核保条件承保录入
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
public class UWGSpecChkBL {
private static Logger logger = Logger.getLogger(UWGSpecChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate; // 操作员
	private String mManageCom;
	private String mpassflag; // 通过标记
	private String mBackFlag;
	private int merrcount; // 错误条数
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private double mprem;
	private double mamnt;

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mmLCPolSet = new LCPolSet();
	private LCPolSet m2LCPolSet = new LCPolSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";
	private String mGrpPolNo = "";
	private String mOldGrpPolNo = "";
	private String mUWFlag = ""; // 核保标志
	private String mSpecReason = ""; // 特约原因
	private String mAddReason = ""; // 加费原因
	private Date mvalidate = null;
	private String mUWIdea = ""; // 核保结论
	private int mpostday; // 延长天数
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mguwgrade = ""; // 操作员核保级别
	private String mQuesFlag = ""; // 是否有问题件标记

	/** 团体保单表 */
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSet mAllLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();

	/** 保费项表 */
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet mAllLCPremSet = new LCPremSet();
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	private LCPremSet mmLCPremSet = new LCPremSet();

	/** 责任表 */
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySet mAllLCDutySet = new LCDutySet();
	private LCDutySchema mLCDutySchema = new LCDutySchema();

	/** 特别约定表 */
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	private LCSpecSet mAllLCSpecSet = new LCSpecSet();
	private LCSpecSchema mLCSpecSchema = new LCSpecSchema();

	/** 核保主表 */
	private LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
	private LCGUWMasterSet mAllLCGUWMasterSet = new LCGUWMasterSet();
	private LCGUWMasterSchema mLCGUWMasterSchema = new LCGUWMasterSchema();

	/** 核保子表 */
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();
	private LCGUWSubSet mAllLCGUWSubSet = new LCGUWSubSet();
	private LCGUWSubSchema mLCGUWSubSchema = new LCGUWSubSchema();

	/** 核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();

	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();

	/** 轨迹锁表* */
	private LDSysTraceSchema mLDSysTraceSchema = new LDSysTraceSchema();
	private LDSysTraceSet mLDSysTraceSet = new LDSysTraceSet();
	private LDSysTraceSet mAllLDSysTraceSet = new LDSysTraceSet();

	private GlobalInput mGlobalInput = new GlobalInput();

	public UWGSpecChkBL() {
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
		logger.debug("---UWGSpecChkBL getInputData---");

		if (!checkUWGrade()) {
			return false;
		}

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---UWGSpecChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();
		logger.debug("---UWGSpecChkBL prepareOutputData---");

		// 数据提交
		UWGSpecChkBLS tUWGSpecChkBLS = new UWGSpecChkBLS();
		logger.debug("Start UWGSpecChkBL Submit...");
		if (!tUWGSpecChkBLS.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWGSpecChkBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "submitData";
			// tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("---UWGSpecChkBL commitData---");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (mUWFlag.equals("1")) { // 团体承保特约录入
			if (dealSpecPol() == false) {
				return false;
			}
		}

		if (mUWFlag.equals("2")) { // 对团体单下的个人加费承保
			if (dealAddPol() == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealSpecPol() {

		// 特约
		if (prepareSpec() == false) {
			return false;
		}

		// 核保主表
		if (prepareGMaster() == false) {
			return false;
		}

		// 核保轨迹表
		if (prepareGSub() == false) {
			return false;
		}

		LCSpecSet tLCSpecSet = new LCSpecSet();
		tLCSpecSet.set(mLCSpecSet);
		mAllLCSpecSet.add(tLCSpecSet);

		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet.set(mLCGUWMasterSet);
		mAllLCGUWMasterSet.add(tLCGUWMasterSet);

		LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
		tLCGUWSubSet.set(mLCGUWSubSet);
		mAllLCGUWSubSet.add(tLCGUWSubSet);
		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealAddPol() {
		// 特约
		if (preparePrem() == false) {
			return false;
		}

		// 核保主表
		if (prepareMaster() == false) {
			return false;
		}

		// 核保轨迹表
		if (prepareSub() == false) {
			return false;
		}

		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet.set(mmLCPremSet);
		mAllLCPremSet.add(tLCPremSet);

		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutySet.set(mLCDutySet);
		mAllLCDutySet.add(tLCDutySet);

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.set(mLCPolSet);
		mAllLCPolSet.add(tLCPolSet);

		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolSet.set(mLCGrpPolSet);
		mAllLCGrpPolSet.add(tLCGrpPolSet);

		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet.set(mLCUWMasterSet);
		mAllLCUWMasterSet.add(tLCUWMasterSet);

		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet.set(mLCUWSubSet);
		mAllLCUWSubSet.add(tLCUWSubSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		String getflag = "true"; // 判断承保条件是否接收

		// 全局变量
		GlobalInput tGlobalInput = new GlobalInput();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mOperate = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 取投保单
		mLCGUWMasterSet.set((LCGUWMasterSet) cInputData.getObjectByObjectName(
				"LCGUWMasterSet", 0));

		int n = mLCGUWMasterSet.size();
		if (n == 1) {
			LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
			tLCGUWMasterSchema = mLCGUWMasterSet.get(1);

			mUWFlag = tLCGUWMasterSchema.getPassFlag();
			logger.debug("muwflag=" + mUWFlag);
			mOldGrpPolNo = tLCGUWMasterSchema.getGrpPolNo();
			mGrpPolNo = tLCGUWMasterSchema.getGrpPolNo();
			mOldPolNo = tLCGUWMasterSchema.getGrpPolNo();
			mPolNo = tLCGUWMasterSchema.getGrpPolNo(); // 团体下各人单加费时(即uwflag=2),该团体保单号实际记录的是该个单的投保单号
			mSpecReason = tLCGUWMasterSchema.getSpecReason();
			mAddReason = tLCGUWMasterSchema.getSpecReason();
		} else {
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据传输失败";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mUWFlag.equals("1")) { // 团体承保特约录入

			mLCSpecSet.set((LCSpecSet) cInputData.getObjectByObjectName(
					"LCSpecSet", 0));
			LCSpecSchema tLCSpecSchema = mLCSpecSet.get(1);

			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();

			logger.debug("团体承保特约录入标志: " + mUWFlag);

			tLCGrpPolDB.setGrpPolNo(mGrpPolNo);
			logger.debug("--BL--GrpPol--" + mGrpPolNo);
			if (tLCGrpPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWGSpecChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mGrpPolNo + "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				mLCGrpPolSchema.setSchema(tLCGrpPolDB.getSchema());
			}
		}

		if (mUWFlag.equals("2")) { // 对团体单下的个人加费承保
			mLCPremSet.set((LCPremSet) cInputData.getObjectByObjectName(
					"LCPremSet", 0));

			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolDB tLCPolDB = new LCPolDB();

			logger.debug("muwflag=" + mUWFlag);

			tLCPolDB.setPolNo(mPolNo);
			logger.debug("--BL--Pol--" + mPolNo);
			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWGSpecChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mPolNo + "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				mLCPolSchema.setSchema(tLCPolDB.getSchema());
				mGrpPolNo = mLCPolSchema.getGrpPolNo();
				mOldGrpPolNo = mLCPolSchema.getGrpPolNo();
			}

			// 团单
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(mGrpPolNo);
			logger.debug("--BL--GrpPol--" + mGrpPolNo);
			if (tLCGrpPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWGSpecChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mGrpPolNo + "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				mLCGrpPolSchema.setSchema(tLCGrpPolDB.getSchema());
			}

		}

		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperate);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperate + "）";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tLDUserDB.getUWPopedom();
		mguwgrade = tLDUserDB.getUWPopedom();
		mAppGrade = mUWPopedom;

		if (tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperate + "）";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpPolNo(mGrpPolNo);

		if (!tLCGUWMasterDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperate + "）";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			String tappgrade = tLCGUWMasterDB.getAppGrade();

			if (tUWPopedom.compareTo(tappgrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "已经提交上级核保，不能核保!（操作员：" + mOperate + "）";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		LCGUWErrorDB tLCGUWErrorDB = new LCGUWErrorDB();
		tLCGUWErrorDB.setGrpPolNo(mGrpPolNo);
		String tsql = "select * from lcguwerror where grppolno = '"
				+ mGrpPolNo.trim()
				+ "' and uwno = (select max(uwno) from lcguwerror where grppolno = '"
				+ mGrpPolNo.trim() + "')";
		LCGUWErrorSet tLCGUWErrorSet = tLCGUWErrorDB.executeQuery(tsql);

		int errno = tLCGUWErrorSet.size();
		if (errno > 0) {
			for (int i = 1; i <= errno; i++) {
				LCGUWErrorSchema tLCGUWErrorSchema = new LCGUWErrorSchema();
				tLCGUWErrorSchema = tLCGUWErrorSet.get(i);
				String terrgrade = tLCGUWErrorSchema.getUWGrade();

				if (tUWPopedom.compareTo(terrgrade) < 0 && !mUWFlag.equals("6")) {
					CError tError = new CError();
					tError.moduleName = "GrpUWManuNormChkBL";
					tError.functionName = "checkUWGrade";
					tError.errorMessage = "核保级别不够，请录入核保意见，申请待上级核保!（操作员："
							+ mOperate + "）";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareSpec() {
		int n;
		// int i =0;
		int max;
		String sql;
		String specno = "";

		logger.debug("---prepareSpec---");

		n = 0;
		n = mLCSpecSet.size();
		if (n > 0) {
			LCSpecSchema tLCSpecSchema = new LCSpecSchema();

			tLCSpecSchema = mLCSpecSet.get(1);

			// tLCSpecSchema.setSpecNo(PubFun1.CreateMaxNo("SpecNo",
			// PubFun.getNoLimit(mGlobalInput.
			// ComCode)));

			// tLCSpecSchema.setPolNo(mGrpPolNo);
			// tLCSpecSchema.setPolType("2");
			tLCSpecSchema.setEndorsementNo("");
			tLCSpecSchema.setSpecType("");
			tLCSpecSchema.setSpecCode("");
			// tLCSpecSchema.setSpecContent();
			tLCSpecSchema.setPrtFlag("1");
			tLCSpecSchema.setBackupType("");
			tLCSpecSchema.setOperator(mOperate);
			tLCSpecSchema.setMakeDate(PubFun.getCurrentDate());
			tLCSpecSchema.setMakeTime(PubFun.getCurrentTime());
			tLCSpecSchema.setModifyDate(PubFun.getCurrentDate());
			tLCSpecSchema.setModifyTime(PubFun.getCurrentTime());

			mLCSpecSet.clear();
			mLCSpecSet.add(tLCSpecSchema);
		}
		return true;
	}

	/**
	 * 加费
	 * 
	 * @return
	 */
	private boolean preparePrem() {

		int n = 0;
		int max = 0;
		String sql = "";

		if ((n = mLCPremSet.size()) > 0) {
			logger.debug("premsize=" + n);

			// 取责任信息
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
			mLCDutySet = tLCDutyDB.query();

			// 更新责任项
			if (mLCDutySet.size() > 0) {
				for (int m = 1; m <= mLCDutySet.size(); m++) {
					int maxno = 0;
					LCDutySchema tLCDutySchema = new LCDutySchema();
					tLCDutySchema = mLCDutySet.get(m);

					// 减去原加费金额
					sql = "select * from LCPrem where payplancode  like '000000%' and polno = '"
							+ mLCPolSchema.getPolNo().trim()
							+ "' and dutycode = '"
							+ tLCDutySchema.getDutyCode().trim() + "'";
					logger.debug("dutysql:" + sql);
					LCPremDB t2LCPremDB = new LCPremDB();
					LCPremSet t2LCPremSet = new LCPremSet();

					t2LCPremSet = t2LCPremDB.executeQuery(sql);

					if (t2LCPremSet.size() > 0) {
						for (int j = 1; j <= t2LCPremSet.size(); j++) {
							LCPremSchema t2LCPremSchema = new LCPremSchema();
							t2LCPremSchema = t2LCPremSet.get(j);

							tLCDutySchema.setPrem(tLCDutySchema.getPrem()
									- t2LCPremSchema.getPrem());
							mLCPolSchema.setPrem(mLCPolSchema.getPrem()
									- t2LCPremSchema.getPrem());
							mLCGrpPolSchema.setPrem(mLCGrpPolSchema.getPrem()
									- t2LCPremSchema.getPrem());
						}
					}

					for (int i = 1; i <= n; i++) {
						LCPremSchema ttLCPremSchema = new LCPremSchema();
						ttLCPremSchema = mLCPremSet.get(i);
						LCPremSchema tLCPremSchema = new LCPremSchema();
						LCPremDB tLCPremDB = new LCPremDB();
						double tPrem;
						double tGPrem;

						if (ttLCPremSchema.getDutyCode().equals(
								tLCDutySchema.getDutyCode())) {
							maxno = maxno + 1;
							tLCPremDB.setPolNo(mLCPolSchema.getPolNo());
							tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
							String tDutyCode = tLCPremSchema.getDutyCode();

							LCPremSet tLCPremSet = tLCPremDB.query();
							tLCPremSchema = tLCPremSet.get(1);

							String PayPlanCode = "";

							int j = 0;
							max = 0;
							max = max + maxno;
							PayPlanCode = String.valueOf(max);
							for (j = PayPlanCode.length(); j < 8; j++) {
								PayPlanCode = "0" + PayPlanCode;
							}

							logger.debug("payplancode" + PayPlanCode);

							// 保单总保费
							tPrem = mLCPolSchema.getPrem()
									+ ttLCPremSchema.getPrem();
							tGPrem = mLCGrpPolSchema.getPrem()
									+ ttLCPremSchema.getPrem();
							// tLCPremSchema.setPolNo(mLCPolSchema.getPolNo());
							// tLCPremSchema.setDutyCode(mmaxDutyCode);
							tLCPremSchema.setPayPlanCode(PayPlanCode);
							// tLCPremSchema.setGrpPolNo(mLCPolSchema.get);
							tLCPremSchema.setPayPlanType(ttLCPremSchema
									.getPayPlanType());
							// tLCPremSchema.setPayTimes();
							// tLCPremSchema.setPayIntv();
							// tLCPremSchema.setMult();
							// tLCPremSchema.setStandPrem();
							tLCPremSchema.setPrem(ttLCPremSchema.getPrem());
							// tLCPremSchema.setSumPrem();
							// tLCPremSchema.setRate();
							tLCPremSchema.setPayStartDate(ttLCPremSchema
									.getPayStartDate());
							tLCPremSchema.setPayEndDate(ttLCPremSchema
									.getPayEndDate());
							// tLCPremSchema.setPaytoDate();
							// tLCPremSchema.setState();
							// tLCPremSchema.setBankCode();
							// tLCPremSchema.setBankAccNo();
							// tLCPremSchema.setAppntNo();
							// tLCPremSchema.setAppntType("1"); //投保人类型
							tLCPremSchema
									.setModifyDate(PubFun.getCurrentDate());
							tLCPremSchema
									.setModifyTime(PubFun.getCurrentTime());

							// 更新保险责任
							// if(tLCPremSchema.getDutyCode().equals(tLCDutySchema.getDutyCode()))
							// {
							mLCDutySet.remove(tLCDutySchema);
							tLCDutySchema.setPrem(tLCDutySchema.getPrem()
									+ tLCPremSchema.getPrem());
							mLCDutySet.add(tLCDutySchema);
							// }

							mmLCPremSet.add(tLCPremSchema);
							// 更新保单数据
							mLCPolSchema.setPrem(tPrem);
							mLCGrpPolSchema.setPrem(tGPrem);

						}

					}

				}
			}

		}

		mLCPolSet.clear();
		mLCPolSet.add(mLCPolSchema);

		mLCGrpPolSet.clear();
		mLCGrpPolSet.add(mLCGrpPolSchema);
		return true;
	}

	/**
	 * 核保主表
	 */
	private boolean prepareGMaster() {
		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();

		tLCGUWMasterDB.setGrpPolNo(mGrpPolNo);
		if (tLCGUWMasterDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareGMaster";
			tError.errorMessage = mOldGrpPolNo + "集体核保主表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLCGUWMasterSchema = tLCGUWMasterDB.getSchema();

		int tuwno = tLCGUWMasterSchema.getUWNo();
		tuwno = tuwno + 1;

		tLCGUWMasterSchema.setUWNo(tuwno);
		tLCGUWMasterSchema.setPassFlag("3"); // 通过标志
		tLCGUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
		// tLCGUWMasterSchema.setUWGrade(mguwgrade); //核保级别
		// tLCGUWMasterSchema.setAppGrade(mguwgrade); //申报级别
		tLCGUWMasterSchema.setOperator(mOperate); // 操作员
		tLCGUWMasterSchema.setSpecReason(mSpecReason);
		tLCGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLCGUWMasterSet.clear();
		mLCGUWMasterSet.add(tLCGUWMasterSchema);
		return true;
	}

	/**
	 * 核保主表
	 */
	private boolean prepareMaster() {
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setPolNo(mOldPolNo);
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareMaster";
			tError.errorMessage = "核保主表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int n = tLCUWMasterSet.size();
		logger.debug("mastercount=" + n);
		if (n == 1) {
			tLCUWMasterSchema = tLCUWMasterSet.get(1);

			// tLCUWMasterSchema.setUWNo
			tLCUWMasterSchema.setPassFlag("3"); // 通过标志
			tLCUWMasterSchema.setSpecFlag("1"); // 团体下个单特约标志做加费标志
			// tLCUWMasterSchema.setUWIdea(mUWIdea);
			tLCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
			// tLCUWMasterSchema.setUWGrade(mUWPopedom);
			// tLCUWMasterSchema.setAppGrade(mAppGrade);
			tLCUWMasterSchema.setAddPremReason(mAddReason);
			tLCUWMasterSchema.setOperator(mOperate); // 操作员
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareMaster";
			tError.errorMessage = "核保主表取数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCUWMasterSet.clear();
		mLCUWMasterSet.add(tLCUWMasterSchema);

		return true;
	}

	/**
	 * 核保轨迹表
	 */
	private boolean prepareGSub() {
		// 核保轨迹表
		LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
		LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB();
		tLCGUWSubDB.setGrpPolNo(mGrpPolNo);
		LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
		tLCGUWSubSet = tLCGUWSubDB.query();
		if (tLCGUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareSub";
			tError.errorMessage = mOldGrpPolNo + "集体核保轨迹表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCGUWSubSet.size();
		if (m > 0) {
			m++; // 核保次数
			tLCGUWSubSchema = tLCGUWSubSet.get(1);

			tLCGUWSubSchema.setUWNo(m); // 第几次核保
			// tLCGUWSubSchema.setUWFlag("3"); //核保意见
			// tLCGUWSubSchema.setUWGrade(mguwgrade); //核保级别
			// tLCGUWSubSchema.setAppGrade(mguwgrade); //申请级别
			tLCGUWSubSchema.setAutoUWFlag("2");
			tLCGUWSubSchema.setSpecReason(mSpecReason);
			tLCGUWSubSchema.setOperator(mOperate); // 操作员
			tLCGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareGSub";
			tError.errorMessage = mOldGrpPolNo + "集体核保轨迹表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCGUWSubSet.clear();
		mLCGUWSubSet.add(tLCGUWSubSchema);

		return true;
	}

	/**
	 * 个人单核保轨迹表
	 */
	private boolean prepareSub() {
		// 核保轨迹表
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		tLCUWSubDB.setPolNo(mOldPolNo);
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet = tLCUWSubDB.query();
		if (tLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareSub";
			tError.errorMessage = "核保轨迹表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCUWSubSchema = tLCUWSubSet.get(1);

			tLCUWSubSchema.setUWNo(m); // 第几次核保
			// tLCUWSubSchema.setUWFlag("3"); //核保意见
			// tLCUWSubSchema.setUWGrade(mUWPopedom); //核保级别
			// tLCUWSubSchema.setAppGrade(mAppGrade); //申请级别
			tLCUWSubSchema.setAutoUWFlag("2");
			tLCUWSubSchema.setState(mUWFlag);
			// tLCUWSubSchema.setUWIdea(mUWIdea);
			tLCUWSubSchema.setSpecReason(mAddReason);
			tLCUWSubSchema.setOperator(mOperate); // 操作员
			tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWGSpecChkBL";
			tError.functionName = "prepareSub";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCUWSubSet.clear();
		mLCUWSubSet.add(tLCUWSubSchema);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(mAllLCSpecSet);
		mInputData.add(mAllLCGUWMasterSet);
		mInputData.add(mAllLCGUWSubSet);

		mInputData.add(mAllLCPolSet);
		mInputData.add(mAllLCGrpPolSet);
		mInputData.add(mAllLCUWSubSet);
		mInputData.add(mAllLCUWMasterSet);
		mInputData.add(mAllLCPremSet);
		mInputData.add(mAllLCDutySet);
	}

}
