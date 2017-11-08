package com.sinosoft.lis.cbcheck;
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
 * Description: 修改问题件是否计入误发标记
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @modified by tongmeng
 * @version 6.5
 */
public class QuestErrFlagChkBL {
private static Logger logger = Logger.getLogger(QuestErrFlagChkBL.class);
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
//	private FDate fDate = new FDate();
	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mContNo = "";

	/** 问题件表 */
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mmLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mAllLCIssuePolSet = new LCIssuePolSet();

	public QuestErrFlagChkBL() {
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
		logger.debug("---QuestErrFlagChkBL getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---QuestErrFlagChkBL dealData---");

		// 数据操作业务处理
		if (!dealData(mLCContSchema)) {
			return false;
		} else {
			flag = 1;
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
		// 
		if (prepareQuest() == false) {
			return false;
		}

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.set(mLCIssuePolSet);
		mAllLCIssuePolSet.add(tLCIssuePolSet);
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
				CError.buildErr(this,"在合同表中无法查到合同号为" + mContNo + " 的合同信息!");

				return false;
			}

			mLCContSchema.setSchema(tLCContSet.get(1));
		} else {
			// @@错误处理
			CError.buildErr(this,"没有传入合同信息!");

			return false;
		}

		if (mLCIssuePolSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this,"没有传入问题件信息!");
			return false;
		}

		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareQuest() {
		//String tReply = "";
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		tLCIssuePolSchema = mLCIssuePolSet.get(1);
		//tReply = tLCIssuePolSchema.getReplyResult();
		String tErrFalg = tLCIssuePolSchema.getErrFlag();
		String tErrOperator=tLCIssuePolSchema.getErrOperator();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();

		tLCIssuePolDB.setProposalContNo(mLCContSchema.getProposalContNo());
		tLCIssuePolDB.setIssueType(tLCIssuePolSchema.getIssueType());
		logger.debug("issuetype:" + tLCIssuePolSchema.getIssueType());
		tLCIssuePolDB.setOperatePos(tLCIssuePolSchema.getOperatePos());
		logger.debug("operatepos:" + tLCIssuePolSchema.getOperatePos());
		tLCIssuePolDB.setSerialNo(tLCIssuePolSchema.getSerialNo());
		logger.debug("SerialNo:" + tLCIssuePolSchema.getSerialNo());

		if (!tLCIssuePolDB.getInfo()) {
			CError.buildErr(this,"LCIssuePol查询失败!");

			return false;
		} else {
			tLCIssuePolSchema.setSchema(tLCIssuePolDB.getSchema());
		}

		tLCIssuePolSchema.setErrFlag(tErrFalg);
		tLCIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCIssuePolSchema.setModifyTime(PubFun.getCurrentTime());
		tLCIssuePolSchema.setErrOperator(tErrOperator);
		tLCIssuePolSchema.setErrMakeDate(PubFun.getCurrentDate());
		tLCIssuePolSchema.setErrMakeTime(PubFun.getCurrentTime());
		

		mLCIssuePolSet.clear();
		mLCIssuePolSet.add(tLCIssuePolSchema);

		return true;
	}


	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLCIssuePolSet, "UPDATE");
	}
}
