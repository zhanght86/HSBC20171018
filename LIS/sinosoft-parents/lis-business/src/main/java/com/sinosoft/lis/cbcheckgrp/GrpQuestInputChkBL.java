package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpIssuePolSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统团单问题件录入部分
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
 * @author ZhangRong
 * @version 1.0
 */
public class GrpQuestInputChkBL {
private static Logger logger = Logger.getLogger(GrpQuestInputChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mIsueManageCom;
	private String mManageCom;
	private FDate fDate = new FDate();

	/** 业务处理相关变量 */
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mGrpContNo = "";
	private String mOperatorPos = "";

	/** 核保主表 */
	private LCGCUWMasterSchema mLCGCUWMasterSchema = null; // new
															// LCGCUWMasterSchema();

	/** 问题件表 */
	private LCGrpIssuePolSet mLCGrpIssuePolSet = new LCGrpIssuePolSet();
	private LCGrpIssuePolSet mmLCGrpIssuePolSet = new LCGrpIssuePolSet();
	private LCGrpIssuePolSet mAllLCGrpIssuePolSet = new LCGrpIssuePolSet();

	public GrpQuestInputChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功

		// 将操作数据拷贝到本类中，此类做数据提交使用
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---GrpQuestInputChkBL calling getInputData---");

		if (!getInputData(cInputData)) {
			return false;
		}

		// 取所在机构号码
		if (!GetManageCom(mGrpContNo)) {
			return false;
		}

		logger.debug("---GrpQuestInputChkBL dealData---");

		// 数据操作业务处理
		if (!dealData(mLCGrpContSchema)) {
			return false;
		} else {
			flag = 1;
		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpQuestInputChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---GrpQuestInputChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---GrpQuestInputChkBL prepareOutputData---");

		// 数据提交
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpQuestInputChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---GrpQuestInputChkBL commitData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCGrpContSchema tLCGrpContSchema) {
		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (prepareQuest() == false) {
			return false;
		}

		LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();
		tLCGrpIssuePolSet.set(mLCGrpIssuePolSet);
		mAllLCGrpIssuePolSet.add(tLCGrpIssuePolSet);

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.set(mLCGrpContSet);
		mAllLCGrpContSet.add(tLCGrpContSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;

		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));
		mLCGrpIssuePolSet.set((LCGrpIssuePolSet) cInputData
				.getObjectByObjectName("LCGrpIssuePolSet", 0));

		if ((mLCGrpContSet != null) && (mLCGrpContSet.size() > 0)) {
			mLCGrpContSchema = mLCGrpContSet.get(1);
			mGrpContNo = mLCGrpContSchema.getGrpContNo();

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mGrpContNo);

			LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

			if (tLCGrpContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpQuestInputChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在合同表中无法查到合同号为" + mGrpContNo + " 的合同信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mLCGrpContSchema.setSchema(tLCGrpContSet.get(1));
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpQuestInputChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入合同信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mLCGrpIssuePolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpQuestInputChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入问题件信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 取所在机构
	 * 
	 */
	private boolean GetManageCom(String tContNo) {
		LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
		tLCGrpIssuePolSchema = mLCGrpIssuePolSet.get(1);

		if (!tContNo.equals("")) {
			mIsueManageCom = mLCGrpContSchema.getManageCom();

			if (tLCGrpIssuePolSchema.getOperatePos().equals("0")) { // 如果从录单界面进入问题件录入

				if (tLCGrpIssuePolSchema.getBackObjType().equals("1")) { // 如果问题件转给录单员本人

					CError tError = new CError();
					tError.moduleName = "GrpQuestInputChkBL";
					tError.functionName = "GetManageCom";
					tError.errorMessage = "严重警告：系统禁止操作员录入返回给自己（操作员）的问题件!";
					this.mErrors.addOneError(tError);

					return false;
				}

				mLCGrpContSet.clear();

				return true;
			}

			if (tLCGrpIssuePolSchema.getOperatePos().equals("1")) {
			}

			// 复核处录入了返回给操作员的问题件
			/*
			 * if (tLCGrpIssuePolSchema.getOperatePos().equals("5") &&
			 * tLCGrpIssuePolSchema.getBackObjType().equals("1")) {
			 * mLCGrpContSchema.setUWFlag("0");
			 * mLCGrpContSchema.setApproveCode(mOperator);
			 * mLCGrpContSchema.setApproveFlag("1");
			 * mLCGrpContSchema.setApproveDate(PubFun.getCurrentDate()); }
			 */

			// mLCGrpContSet.clear();
			// mLCGrpContSet.add(mLCGrpContSchema);
		}

		return true;
	}

	private boolean Getbackobj() {
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareQuest() {
		String tPrint = "";
		LCGrpIssuePolSchema tLCGrpIssuePolSchema = mLCGrpIssuePolSet.get(1);
		mOperatorPos = tLCGrpIssuePolSchema.getOperatePos();

		String tSerialNo = PubFun1.CreateMaxNo("QustSerlNo", 20);
		String tPrintSerialNo = PubFun1.CreateMaxNo("PRTSEQ2NO", 20);

		// //个人单默认打印标记
		if (tLCGrpIssuePolSchema.getBackObjType().equals("3")
				|| tLCGrpIssuePolSchema.getBackObjType().equals("2")) { // 保户,业务员
			tPrint = "Y";
		} else {
			tPrint = "N";
		}

		// 操作员
		tLCGrpIssuePolSchema.setBackObj(mLCGrpContSchema.getOperator());

		tLCGrpIssuePolSchema.setGrpContNo(mGrpContNo);

		// tLCGrpIssuePolSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		tLCGrpIssuePolSchema.setProposalGrpContNo(mLCGrpContSchema
				.getProposalGrpContNo());
		tLCGrpIssuePolSchema.setIssueType(tLCGrpIssuePolSchema.getIssueType()
				.trim()); // 设置问题类型
		tLCGrpIssuePolSchema.setSerialNo(tSerialNo.toString());
		tLCGrpIssuePolSchema.setPrtSeq(tPrintSerialNo.toString());
		tLCGrpIssuePolSchema.setIsueManageCom(mIsueManageCom);
		tLCGrpIssuePolSchema.setPrintCount(0);
		tLCGrpIssuePolSchema.setNeedPrint(tPrint);
		tLCGrpIssuePolSchema.setReplyMan("");
		tLCGrpIssuePolSchema.setReplyResult("");
		tLCGrpIssuePolSchema.setState("");
		tLCGrpIssuePolSchema.setOperator(mOperator);
		tLCGrpIssuePolSchema.setManageCom(mManageCom);
		tLCGrpIssuePolSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpIssuePolSchema.setMakeTime(PubFun.getCurrentTime());
		tLCGrpIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGrpIssuePolSchema.setModifyTime(PubFun.getCurrentTime());

		mLCGrpIssuePolSet.clear();
		mLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);

		if (mOperatorPos.equals("1")) {
			// 核保主表信息
			LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
			tLCGCUWMasterDB.setProposalGrpContNo(mGrpContNo);

			if (tLCGCUWMasterDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpQuestInputChkBL";
				tError.functionName = "prepareQuest";
				tError.errorMessage = "无核保主表信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			// 校验是否以发核保通知书
			if (tLCGCUWMasterDB.getPrintFlag() == null) {
			} else {
				if (tLCGCUWMasterDB.getPrintFlag().equals("1")
						|| tLCGCUWMasterDB.getPrintFlag().equals("4")) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "GrpQuestInputChkBL";
					tError.functionName = "prepareQuest";
					tError.errorMessage = "已经发核保通知书，不可录入!";
					this.mErrors.addOneError(tError);

					return false;
				}
			}

			mLCGCUWMasterSchema = tLCGCUWMasterDB.getSchema();
			mLCGCUWMasterSchema.setQuesFlag("1");
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCGrpIssuePolSet, "INSERT");
		// mMap.put(mAllLCGrpContSet, "UPDATE");

		if (mLCGCUWMasterSchema != null) {
			mMap.put(mLCGCUWMasterSchema, "UPDATE");
		}
	}
}
