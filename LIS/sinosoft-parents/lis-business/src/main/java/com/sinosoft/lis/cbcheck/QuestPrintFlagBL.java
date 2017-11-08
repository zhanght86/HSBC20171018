package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统问题件打印标记设置部分
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
public class QuestPrintFlagBL {
private static Logger logger = Logger.getLogger(QuestPrintFlagBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperator;
	private String mIsueManageCom;
	private String mManageCom;

	/** 业务处理相关变量 */

	private String mContNo = "";

	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 问题件表 */
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mmLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mAllLCIssuePolSet = new LCIssuePolSet();

	public QuestPrintFlagBL() {
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

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---QuestPrintFlagBL getInputData---");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---QuestPrintFlagBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---QuestPrintFlagBL prepareOutputData---");
		// 数据提交
		mResult.add(mMap);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "QuestPrintFlagBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("---QuestPrintFlagBL commitData---");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		return dealOnePol();
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (prepareQuest() == false) {
			return false;
		}

		if (checkQuest() == false) {
			return false;
		}

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.set(mmLCIssuePolSet);
		mAllLCIssuePolSet.add(tLCIssuePolSet);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);

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
		mLCIssuePolSet.set((LCIssuePolSet) cInputData.getObjectByObjectName(
				"LCIssuePolSet", 0));

		if (mLCIssuePolSet.size() > 0) {
			LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
			tLCIssuePolSchema = mLCIssuePolSet.get(1);

			mContNo = tLCIssuePolSchema.getContNo();
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QuestPrintFlagBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入数据";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备打印信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareQuest() {
		mmLCIssuePolSet.clear();
		int count = mLCIssuePolSet.size();

		if (count > 0) {
			LCIssuePolSchema tLCIssuePolSchema = null;
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = null;
			LCContSchema tLCContSchema = new LCContSchema();
			for (int n = 1; n <= count; n++) {
				tLCIssuePolSchema = mLCIssuePolSet.get(n);
				// 取标记
				String tNeedPrint = tLCIssuePolSchema.getNeedPrint();
				tLCIssuePolSchema.setNeedPrint("");

				mContNo = tLCIssuePolSchema.getContNo();
				tLCContDB.setContNo(mContNo);
				tLCContSet = tLCContDB.query();
				if (tLCContSet == null || tLCContSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "QuestInputChkBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "在合同表中无法查到合同号为" + mContNo + " 的合同信息!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCContSchema.setSchema(tLCContSet.get(1));

				LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
				tLCIssuePolSchema.setProposalContNo(tLCContSchema
						.getProposalContNo());
				tLCIssuePolDB.setSchema(tLCIssuePolSchema);
				if (tLCIssuePolDB.getInfo() == false) {
					this.mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "QuestPrintFlagBL";
					tError.functionName = "prepareQuest";
					tError.errorMessage = "LCIssuePol表取数据失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCIssuePolSchema = new LCIssuePolSchema();
				tLCIssuePolSchema = tLCIssuePolDB.getSchema();
				tLCIssuePolSchema.setNeedPrint(tNeedPrint);
				mmLCIssuePolSet.add(tLCIssuePolSchema);
			}
		}
		return true;
	}

	/**
	 * 校验问题件是否需要回退
	 */
	private boolean checkQuest() {
		String tflag = "0";
		mLCCUWMasterSet.clear();

		for (int i = 1; i <= mmLCIssuePolSet.size(); i++) {
			LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
			tLCIssuePolSchema = mmLCIssuePolSet.get(i);

			if (tLCIssuePolSchema.getBackObj().equals("1")
					&& tLCIssuePolSchema.getOperatePos().equals("1")) {
				if (tLCIssuePolSchema.getReplyResult() == null) {
					tflag = "1";
				}
			}

			if (tLCIssuePolSchema.getBackObj().equals("2")
					&& tLCIssuePolSchema.getOperatePos().equals("1")) {
				if (tLCIssuePolSchema.getNeedPrint().equals("Y")
						|| tLCIssuePolSchema.getNeedPrint().equals("P")) {
					tflag = "1";
				}
			}

			if (tLCIssuePolSchema.getBackObj().equals("3")
					&& tLCIssuePolSchema.getOperatePos().equals("1")) {
				if (tLCIssuePolSchema.getNeedPrint().equals("Y")
						|| tLCIssuePolSchema.getNeedPrint().equals("P")) {
					tflag = "1";
				}
			}

			if (tLCIssuePolSchema.getBackObj().equals("1")
					&& tLCIssuePolSchema.getOperatePos().equals("1")) {
				if (tLCIssuePolSchema.getReplyResult() == null) {
					tflag = "1";
				}
			}
		}

		if (tflag.equals("0")) {
			LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
			LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();

			tLCCUWMasterDB.setContNo(mContNo);

			if (!tLCCUWMasterDB.getInfo()) {
				this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "QuestPrintFlagBL";
				tError.functionName = "checkQuest";
				tError.errorMessage = "LCCUWMaster表取数据失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				tLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
				tLCCUWMasterSchema.setQuesFlag("2");
				mLCCUWMasterSet.add(tLCCUWMasterSchema);
			}
		}
		// 修改问题件打印标志不会影响核保通知书的QuestionFlag.因为在前台控制如果已发核保通知书后,未打印状态下，不再容许修改问题件标志。2004-02-25
		// sxy
		// 修改问题件打印标志会影响核保通知书的QuestionFlag.因为在前台控制如果未发核保通知书前，容许修改问题件标志。2004-05-26
		// sxy

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCIssuePolSet, "UPDATE");
		mMap.put(mAllLCCUWMasterSet, "UPDATE");
	}
}
