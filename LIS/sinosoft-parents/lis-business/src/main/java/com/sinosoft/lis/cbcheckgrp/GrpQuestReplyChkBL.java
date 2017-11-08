package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpIssuePolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpIssuePolSchema;
import com.sinosoft.lis.vschema.LCGCUWMasterSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
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
 * @author ZhangRong
 * @version 1.0
 */
public class GrpQuestReplyChkBL {
private static Logger logger = Logger.getLogger(GrpQuestReplyChkBL.class);
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
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mContNo = "";

	/** 核保主表 */
	private LCGCUWMasterSet mLCGCUWMasterSet = new LCGCUWMasterSet();
	private LCGCUWMasterSet mAllLCGCUWMasterSet = new LCGCUWMasterSet();
	private LCGCUWMasterSchema mLCGCUWMasterSchema = new LCGCUWMasterSchema();

	/** 问题件表 */
	private LCGrpIssuePolSet mLCGrpIssuePolSet = new LCGrpIssuePolSet();
	private LCGrpIssuePolSet mmLCGrpIssuePolSet = new LCGrpIssuePolSet();
	private LCGrpIssuePolSet mAllLCGrpIssuePolSet = new LCGrpIssuePolSet();

	public GrpQuestReplyChkBL() {
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
		logger.debug("---GrpQuestReplyChkBL getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---GrpQuestReplyChkBL dealData---");

		// 数据操作业务处理
		if (!dealData(mLCGrpContSchema)) {
			return false;
		} else {
			flag = 1;
		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpQuestReplyChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---GrpQuestReplyChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---GrpQuestReplyChkBL prepareOutputData---");

		// 数据提交
		logger.debug("Start GrpQuestReplyChkBL Submit...");
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpQuestReplyChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---GrpQuestReplyChkBL commitData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCGrpContSchema tLCGrpContSchema) {
		logger.debug("GrpQuestReplyChkBL --ApproveFlag： "+tLCGrpContSchema.getApproveFlag());
        //问题件回复后件apprvoeflag--“9”，修改确认时回查询approveflag=‘9’ 现已不查，用于维护！
		mLCGrpContSchema.setApproveFlag("9");
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

		LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();
		tLCGrpIssuePolSet.set(mLCGrpIssuePolSet);
		mAllLCGrpIssuePolSet.add(tLCGrpIssuePolSet);

		LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();
		tLCGCUWMasterSet.set(mLCGCUWMasterSet);
		mAllLCGCUWMasterSet.add(tLCGCUWMasterSet);

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
			mContNo = mLCGrpContSchema.getGrpContNo();

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mContNo);

			LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

			if (tLCGrpContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "QuestReplayChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在合同表中无法查到合同号为" + mContNo + " 的合同信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mLCGrpContSchema.setSchema(tLCGrpContSet.get(1));
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpQuestReplyChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入合同信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mLCGrpIssuePolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpQuestReplyChkBL";
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
		LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
		tLCGrpIssuePolSchema = mLCGrpIssuePolSet.get(1);
		tReply = tLCGrpIssuePolSchema.getReplyResult();

		LCGrpIssuePolDB tLCGrpIssuePolDB = new LCGrpIssuePolDB();

		tLCGrpIssuePolDB.setProposalGrpContNo(mLCGrpContSchema
				.getProposalGrpContNo());
		tLCGrpIssuePolDB.setIssueType(tLCGrpIssuePolSchema.getIssueType());
		logger.debug("issuetype:" + tLCGrpIssuePolSchema.getIssueType());
		tLCGrpIssuePolDB.setOperatePos(tLCGrpIssuePolSchema.getOperatePos());
		logger.debug("operatepos:" + tLCGrpIssuePolSchema.getOperatePos());
		tLCGrpIssuePolDB.setSerialNo(tLCGrpIssuePolSchema.getSerialNo());
		logger.debug("SerialNo:" + tLCGrpIssuePolSchema.getSerialNo());

		if (!tLCGrpIssuePolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpQuestReplyChkBL";
			tError.functionName = "prepareQuest";
			tError.errorMessage = "LCGrpIssuePol查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			tLCGrpIssuePolSchema.setSchema(tLCGrpIssuePolDB.getSchema());
		}

		tLCGrpIssuePolSchema.setReplyMan(mOperator);
		tLCGrpIssuePolSchema.setReplyResult(tReply);
		tLCGrpIssuePolSchema.setOperator(mOperator);
		tLCGrpIssuePolSchema.setManageCom(mManageCom);
		tLCGrpIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGrpIssuePolSchema.setModifyTime(PubFun.getCurrentTime());

		mLCGrpIssuePolSet.clear();
		mLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);

		// 修改核保问题件回复标记
		checkQuesFlag(tLCGrpIssuePolSchema);

		return true;
	}

	/**
	 * 
	 */
	private boolean checkQuesFlag(LCGrpIssuePolSchema tLCGrpIssuePolSchema) {
		String tProposalContNo = "";
		String tsql = "";
		mLCGCUWMasterSet.clear();

		LCGrpIssuePolDB tLCGrpIssuePolDB = new LCGrpIssuePolDB();
		LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();

		tProposalContNo = tLCGrpIssuePolSchema.getProposalGrpContNo();

		// 判断是不是有没有回复问题件
		tsql = "select * from LCGrpIssuePol where proposalcontno = '"
				+ tProposalContNo
				+ "' and replyresult is null and ((operatepos <> '0' and backobjtype in ('2','3') and needprint = 'Y') or (operatepos <> '0' and backobjtype in ('1','4')) or (operatepos = '0' and backobjtype = '3' and needprint = 'Y') or (operatepos = '0' and backobjtype = '2' and needprint = 'Y')or (operatepos = '0' and backobjtype = '4' ))";

		// 查询回复内容为空且满足如下条件数据，1：从非录单处返回给保户和业务员的需打印问题件，或2：从非录单处返回给操作员或机构的问题件，或3：从录单处返回给业务员或保户的需打印问题件，或4从录单处返回给机构的问题件
		logger.debug(tsql);
		tLCGrpIssuePolSet = tLCGrpIssuePolDB.executeQuery(tsql);

		if (!tLCGrpIssuePolSchema.getOperatePos().equals("0")
				|| (tLCGrpIssuePolSchema.getOperatePos().equals("0") && (tLCGrpIssuePolSchema
						.getBackObjType().equals("3")
						|| tLCGrpIssuePolSchema.getBackObjType().equals("2") || tLCGrpIssuePolSchema
						.getBackObjType().equals("4")))) {
			if (mLCGrpContSchema.getGrpContNo().equals("00000000000000000000")
					&& (tLCGrpIssuePolSet.size() == 1)) // 若是个单，且存在符合条件的问题件
			{
				LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
				tLCGCUWMasterDB.setGrpContNo(mContNo);

				LCGCUWMasterSet tLCGCUWMasterSet = tLCGCUWMasterDB.query();

				if ((tLCGCUWMasterSet == null)
						|| (tLCGCUWMasterSet.size() <= 0)) {
					CError tError = new CError();
					tError.moduleName = "GrpQuestReplyChkBL";
					tError.functionName = "checkQuesFlag";
					tError.errorMessage = "合同核保主表中无法查询合同号为" + mContNo
							+ " 的合同信息!";
					this.mErrors.addOneError(tError);

					return false;
				}

				LCGCUWMasterSchema tLCGCUWMasterSchema = tLCGCUWMasterSet
						.get(1);

				if (tLCGCUWMasterSchema.getQuesFlag().equals("1")) {
					tLCGCUWMasterSchema.setQuesFlag("2");
				}

				mLCGCUWMasterSet.add(tLCGCUWMasterSchema);
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCGrpIssuePolSet, "UPDATE");
		mMap.put(mLCGCUWMasterSet, "UPDATE");
		mMap.put(mLCGrpContSchema, "UPDATE");//approveflag='9' add by liuqh 2008-09-24
	}
}
