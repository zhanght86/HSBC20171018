package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统问题件录入部分
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
 * @modified by ZhangRong 2004.11
 * @version 1.0
 */
public class QuestInputChkBL {
private static Logger logger = Logger.getLogger(QuestInputChkBL.class);
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
	private int mflag = 0; // 1:个单 2:团单
	private String Flag = "";

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mContNo = "";
	private String mOperatorPos = "";

	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = null; // new
															// LCCUWMasterSchema();
	private LCGCUWMasterSchema mLCGCUWMasterSchema = null; // new
															// LCGCUWMasterSchema();

	/** 问题件表 */
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mmLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mAllLCIssuePolSet = new LCIssuePolSet();

	public QuestInputChkBL() {
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
		logger.debug("---QuestInputChkBL calling getInputData---");

		if (!getInputData(cInputData)) {
			return false;
		}

		// 取所在机构号码
		if (!GetManageCom(mContNo)) {
			return false;
		}
		if (Flag.equals("1")) {
			if (!checkData()) {
				return false;
			}
		}
		logger.debug("---QuestInputChkBL dealData---");

		// 数据操作业务处理
		if (!dealData(mLCContSchema)) {
			return false;
		} else {
			flag = 1;
		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---QuestInputChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---QuestInputChkBL prepareOutputData---");

		// 数据提交
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---QuestInputChkBL commitData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCContSchema tLCContSchema) {
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

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.set(mLCIssuePolSet);
		mAllLCIssuePolSet.add(tLCIssuePolSet);

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.set(mLCContSet);
		mAllLCContSet.add(tLCContSet);

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

		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));
		mLCIssuePolSet.set((LCIssuePolSet) cInputData.getObjectByObjectName(
				"LCIssuePolSet", 0));

		if ((mLCContSet != null) && (mLCContSet.size() > 0)) {
			mLCContSchema = mLCContSet.get(1);
			logger.debug("mContNo=" + mContNo);
			mContNo = mLCContSchema.getContNo();
			Flag = mLCContSchema.getState();

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);

			LCContSet tLCContSet = tLCContDB.query();

			if (tLCContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "QuestInputChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在合同表中无法查到合同号为" + mContNo + " 的合同信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mLCContSchema.setSchema(tLCContSet.get(1));
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入合同信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mLCIssuePolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
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
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		tLCIssuePolSchema = mLCIssuePolSet.get(1);

		if (!tContNo.equals("")) {
			if (mLCContSchema.getGrpContNo().equals("00000000000000000000")) {
				mflag = 1; // 个单
			} else {
				mflag = 2; // 团单
			}

			if (mflag == 1) { // 目前只针对团体单(
								// flag.equals("12"))录入问题件所以该分支目前是不会的到执行
				mIsueManageCom = mLCContSchema.getManageCom();

				if (tLCIssuePolSchema.getOperatePos().equals("0")) { // 如果从录单界面进入问题件录入

					if (tLCIssuePolSchema.getBackObjType().equals("1")) { // 如果问题件转给录单员本人

						CError tError = new CError();
						tError.moduleName = "QuestInputChkBL";
						tError.functionName = "GetManageCom";
						tError.errorMessage = "严重警告：系统禁止操作员录入返回给自己（操作员）的问题件!";
						this.mErrors.addOneError(tError);

						return false;
					}

					mLCContSet.clear();

					return true;
				}

				if (tLCIssuePolSchema.getOperatePos().equals("1")) {
				}

				// 复核处录入了返回给操作员的问题件
				if (tLCIssuePolSchema.getOperatePos().equals("5")
						&& tLCIssuePolSchema.getBackObjType().equals("1")) {
					mLCContSchema.setUWFlag("0");
					mLCContSchema.setApproveCode(mOperator);
					mLCContSchema.setApproveFlag("1");
					mLCContSchema.setApproveDate(PubFun.getCurrentDate());
				}

				mLCContSet.clear();
				mLCContSet.add(mLCContSchema);
			}

			if (mflag == 2) { // 目前团体单录入问题件不置任何标志,只是在问题件表中添加一条记录
				mIsueManageCom = mLCContSchema.getManageCom();

				if ((tLCIssuePolSchema.getOperatePos().equals("5") && tLCIssuePolSchema
						.getBackObjType().equals("1"))) {
					// 新单复核处给该团体单录入了返回给操作员的问题件
				}
			}
		}

		return true;
	}

	private boolean checkData() {
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		tLCIssuePolDB.setContNo(mContNo);
		tLCIssuePolDB.setState("");
		tLCIssuePolDB.setIssueType("99");

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet = tLCIssuePolDB.query();
		if (tLCIssuePolSet.size() >= 1) {
			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "prepareQuest";
			tError.errorMessage = "已经发送客户合并问题件，不允许再次发送!";
			this.mErrors.addOneError(tError);
			return false;

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
		LCIssuePolSchema tLCIssuePolSchema = mLCIssuePolSet.get(1);
		mOperatorPos = tLCIssuePolSchema.getOperatePos();

		String tSerialNo = PubFun1.CreateMaxNo("QustSerlNo", 20);
		String tPrintSerialNo = PubFun1.CreateMaxNo("PRTSEQ2NO", 20);

		// //个人单默认打印标记
		if (tLCIssuePolSchema.getBackObjType().equals("3")
				|| tLCIssuePolSchema.getBackObjType().equals("2")) { // 保户,业务员
			tPrint = "Y";
		} else {
			tPrint = "N";
		}

		// 操作员
		tLCIssuePolSchema.setBackObj(mLCContSchema.getOperator());

		tLCIssuePolSchema.setContNo(mContNo);
		tLCIssuePolSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		tLCIssuePolSchema.setProposalContNo(mLCContSchema.getProposalContNo());
		tLCIssuePolSchema.setIssueType(tLCIssuePolSchema.getIssueType().trim()); // 设置问题类型
		tLCIssuePolSchema.setSerialNo(tSerialNo.toString());
		// tLCIssuePolSchema.setPrtSeq(tPrintSerialNo.toString());
		tLCIssuePolSchema.setIsueManageCom(mIsueManageCom);
		tLCIssuePolSchema.setPrintCount(0);
		tLCIssuePolSchema.setNeedPrint(tPrint);
		tLCIssuePolSchema.setReplyMan("");
		tLCIssuePolSchema.setReplyResult("");
		tLCIssuePolSchema.setState("");
		tLCIssuePolSchema.setOperator(mOperator);
		tLCIssuePolSchema.setManageCom(mManageCom);
		tLCIssuePolSchema.setMakeDate(PubFun.getCurrentDate());
		tLCIssuePolSchema.setMakeTime(PubFun.getCurrentTime());
		tLCIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCIssuePolSchema.setModifyTime(PubFun.getCurrentTime());
		tLCIssuePolSchema.setQuestionObjName(tLCIssuePolSchema
				.getQuestionObjName());
		tLCIssuePolSchema.setQuestionObj(tLCIssuePolSchema.getQuestionObj());
		tLCIssuePolSchema.setStandbyFlag1(tLCIssuePolSchema.getStandbyFlag1());

		mLCIssuePolSet.clear();
		mLCIssuePolSet.add(tLCIssuePolSchema);

		if (mOperatorPos.equals("1")) {
			// 核保主表信息
			if (mflag == 1) {
				LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
				tLCCUWMasterDB.setContNo(mContNo);

				// if (tLCCUWMasterDB.getInfo() == false)
				// {
				// // @@错误处理
				// CError tError = new CError();
				// tError.moduleName = "QuestInputChkBL";
				// tError.functionName = "prepareQuest";
				// tError.errorMessage = "无核保主表信息!";
				// this.mErrors.addOneError(tError);
				//
				// return false;
				// }

				// 校验是否以发核保通知书
				if (tLCCUWMasterDB.getPrintFlag() == null) {
				} else {
					if (tLCCUWMasterDB.getPrintFlag().equals("1")
							|| tLCCUWMasterDB.getPrintFlag().equals("4")) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "QuestInputChkBL";
						tError.functionName = "prepareQuest";
						tError.errorMessage = "已经发核保通知书，不可录入!";
						this.mErrors.addOneError(tError);

						return false;
					}
				}

				mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
				mLCCUWMasterSchema.setQuesFlag("1");
			} else if (mflag == 2) {
				LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
				tLCGCUWMasterDB.setProposalGrpContNo(mLCContSchema
						.getGrpContNo());
				logger.debug("----grppolno:----" + mContNo);

				// if (tLCGCUWMasterDB.getInfo() == false)
				// {
				// // @@错误处理
				// CError tError = new CError();
				// tError.moduleName = "QuestInputChkBL";
				// tError.functionName = "prepareQuest";
				// tError.errorMessage = "该团体单无核保主表信息!";
				// this.mErrors.addOneError(tError);
				//
				// return false;
				// }

				logger.debug("----State:----"
						+ tLCGCUWMasterDB.getState());

				// 校验是否以发核保通知书(目前团单下录入的问题件与核保通知书无任何关系)
				if (tLCGCUWMasterDB.getState() == null) {
				} else {
				}

				mLCGCUWMasterSchema = tLCGCUWMasterDB.getSchema();
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCIssuePolSet, "INSERT");
		mMap.put(mAllLCContSet, "UPDATE");

		if (mLCCUWMasterSchema != null) {
			// mMap.put(mLCCUWMasterSchema, "UPDATE");
		}

		if (mLCGCUWMasterSchema != null) {
			// mMap.put(mLCGCUWMasterSchema, "UPDATE");
		}
	}
}
