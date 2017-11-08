package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
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
 * Title: Web业务系统问题件回复部分
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
public class QuestReplyChkBL {
private static Logger logger = Logger.getLogger(QuestReplyChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private FDate fDate = new FDate();
	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mContNo = "";

	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 问题件表 */
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mmLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mAllLCIssuePolSet = new LCIssuePolSet();

	public QuestReplyChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		logger.debug("---QuestReplyChkBL getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---QuestReplyChkBL dealData---");

		// 数据操作业务处理
		if (!dealData(mLCContSchema)) {
			return false;
		} else {
			flag = 1;
		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "QuestReplyChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---QuestReplyChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---QuestReplyChkBL prepareOutputData---");

		// 数据提交
		logger.debug("Start QuestReplyChkBL Submit...");
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "QuestReplyChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---QuestReplyChkBL commitData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCContSchema tLCContSchema) {
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

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.set(mLCIssuePolSet);
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

		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));
		mLCIssuePolSet.set((LCIssuePolSet) cInputData.getObjectByObjectName(
				"LCIssuePolSet", 0));

		if ((mLCContSet != null) && (mLCContSet.size() > 0)) {
			mLCContSchema = mLCContSet.get(1);
			mContNo = mLCContSchema.getContNo();

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);

			LCContSet tLCContSet = tLCContDB.query();

			if (tLCContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "QuestReplayChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在合同表中无法查到合同号为" + mContNo + " 的合同信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mLCContSchema.setSchema(tLCContSet.get(1));
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QuestReplyChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入合同信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mLCIssuePolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QuestReplyChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入问题件信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareQuest() {
		String tReply = "";
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		tLCIssuePolSchema = mLCIssuePolSet.get(1);
		tReply = tLCIssuePolSchema.getReplyResult();

		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();

		tLCIssuePolDB.setProposalContNo(mLCContSchema.getProposalContNo());
		tLCIssuePolDB.setIssueType(tLCIssuePolSchema.getIssueType());
		logger.debug("issuetype:" + tLCIssuePolSchema.getIssueType());
		tLCIssuePolDB.setOperatePos(tLCIssuePolSchema.getOperatePos());
		logger.debug("operatepos:" + tLCIssuePolSchema.getOperatePos());
		tLCIssuePolDB.setSerialNo(tLCIssuePolSchema.getSerialNo());
		logger.debug("SerialNo:" + tLCIssuePolSchema.getSerialNo());

		if (!tLCIssuePolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "QuestReplyChkBL";
			tError.functionName = "prepareQuest";
			tError.errorMessage = "LCIssuePol查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			tLCIssuePolSchema.setSchema(tLCIssuePolDB.getSchema());
		}

		tLCIssuePolSchema.setReplyMan(mOperator);
		tLCIssuePolSchema.setReplyResult(tReply);
		tLCIssuePolSchema.setOperator(mOperator);
		tLCIssuePolSchema.setManageCom(mManageCom);
		tLCIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCIssuePolSchema.setModifyTime(PubFun.getCurrentTime());

		mLCIssuePolSet.clear();
		mLCIssuePolSet.add(tLCIssuePolSchema);

		// 修改核保问题件回复标记
		checkQuesFlag(tLCIssuePolSchema);

		return true;
	}

	/**
	 * 
	 */
	private boolean checkQuesFlag(LCIssuePolSchema tLCIssuePolSchema) {
		String tProposalContNo = "";
		String tsql = "";
		mLCCUWMasterSet.clear();

		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		tProposalContNo = tLCIssuePolSchema.getProposalContNo();

		// 判断是不是有没有回复问题件
		tsql = "select * from lcissuepol where proposalcontno = '"
				+ tProposalContNo
				+ "' and replyresult is null and ((operatepos <> '0' and backobjtype in ('2','3') and needprint = 'Y') or (operatepos <> '0' and backobjtype in ('1','4')) or (operatepos = '0' and backobjtype = '3' and needprint = 'Y') or (operatepos = '0' and backobjtype = '2' and needprint = 'Y')or (operatepos = '0' and backobjtype = '4' ))";

		// 查询回复内容为空且满足如下条件数据，1：从非录单处返回给保户和业务员的需打印问题件，或2：从非录单处返回给操作员或机构的问题件，或3：从录单处返回给业务员或保户的需打印问题件，或4从录单处返回给机构的问题件
		logger.debug(tsql);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);

		if (!tLCIssuePolSchema.getOperatePos().equals("0")
				|| (tLCIssuePolSchema.getOperatePos().equals("0") && (tLCIssuePolSchema
						.getBackObjType().equals("3")
						|| tLCIssuePolSchema.getBackObjType().equals("2") || tLCIssuePolSchema
						.getBackObjType().equals("4")))) {
			if (mLCContSchema.getGrpContNo().equals("00000000000000000000")
					&& (tLCIssuePolSet.size() == 1)) // 若是个单，且存在符合条件的问题件
			{
				LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
				tLCCUWMasterDB.setContNo(mContNo);

				LCCUWMasterSet tLCCUWMasterSet = tLCCUWMasterDB.query();

				if ((tLCCUWMasterSet == null) || (tLCCUWMasterSet.size() <= 0)) {
					CError tError = new CError();
					tError.moduleName = "QuestReplyChkBL";
					tError.functionName = "checkQuesFlag";
					tError.errorMessage = "合同核保主表中无法查询合同号为" + mContNo
							+ " 的合同信息!";
					this.mErrors.addOneError(tError);

					return false;
				}

				LCCUWMasterSchema tLCCUWMasterSchema = tLCCUWMasterSet.get(1);

				if (tLCCUWMasterSchema.getQuesFlag().equals("1")) {
					tLCCUWMasterSchema.setQuesFlag("2");
				}

				mLCCUWMasterSet.add(tLCCUWMasterSchema);
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCIssuePolSet, "UPDATE");
		mMap.put(mLCCUWMasterSet, "UPDATE");
	}
}
