package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.RnewIssuePolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.RnewIssuePolSchema;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.RnewIssuePolSet;
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
public class RnewQuestReplyChkBL {
private static Logger logger = Logger.getLogger(RnewQuestReplyChkBL.class);
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
	private LPCUWMasterSet mLPCUWMasterSet = new LPCUWMasterSet();
	private LPCUWMasterSet mAllLPCUWMasterSet = new LPCUWMasterSet();
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();

	/** 问题件表 */
	private RnewIssuePolSet mRnewIssuePolSet = new RnewIssuePolSet();
	private RnewIssuePolSet mmRnewIssuePolSet = new RnewIssuePolSet();
	private RnewIssuePolSet mAllRnewIssuePolSet = new RnewIssuePolSet();

	public RnewQuestReplyChkBL() {
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
		logger.debug("---BQQuestReplyChkBL getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---BQQuestReplyChkBL dealData---");

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

		logger.debug("---BQQuestReplyChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---BQQuestReplyChkBL prepareOutputData---");

		// 数据提交
		logger.debug("Start BQQuestReplyChkBL Submit...");
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError.buildErr(this, "数据提交失败!") ;
			return false;
		}

		logger.debug("---BQQuestReplyChkBL commitData---");

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

		mRnewIssuePolSet.set((RnewIssuePolSet) cInputData.getObjectByObjectName(
				"RnewIssuePolSet", 0));

		if (mRnewIssuePolSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "没有传入问题件信息!") ;
			return false;
		}

		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareQuest() {
		String tReply = "";
		RnewIssuePolSchema tRnewIssuePolSchema = new RnewIssuePolSchema();
		tRnewIssuePolSchema = mRnewIssuePolSet.get(1);
		tReply = tRnewIssuePolSchema.getReplyResult();
		RnewIssuePolDB tRnewIssuePolDB = new RnewIssuePolDB();
		RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();

		tRnewIssuePolDB.setContNo(tRnewIssuePolSchema.getContNo());
		tRnewIssuePolDB.setIssueType(tRnewIssuePolSchema.getIssueType());
		logger.debug("issuetype:" + tRnewIssuePolSchema.getIssueType());
		tRnewIssuePolDB.setOperatePos(tRnewIssuePolSchema.getOperatePos());
		logger.debug("operatepos:" + tRnewIssuePolSchema.getOperatePos());
		tRnewIssuePolDB.setSerialNo(tRnewIssuePolSchema.getSerialNo());
		logger.debug("SerialNo:" + tRnewIssuePolSchema.getSerialNo());
		tRnewIssuePolSet = tRnewIssuePolDB.query();
		
		if (tRnewIssuePolSet==null || tRnewIssuePolSet.size()!=1) {
			CError.buildErr(this, "RnewIssuePol查询失败!") ;
			return false;
		} else {
			tRnewIssuePolSchema.setSchema(tRnewIssuePolSet.get(1));
		}

		tRnewIssuePolSchema.setReplyMan(mOperator);
		tRnewIssuePolSchema.setReplyResult(tReply);
		tRnewIssuePolSchema.setState("1");
		tRnewIssuePolSchema.setOperator(mOperator);
		tRnewIssuePolSchema.setManageCom(mManageCom);
		tRnewIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tRnewIssuePolSchema.setModifyTime(PubFun.getCurrentTime());

		mAllRnewIssuePolSet.clear();
		mAllRnewIssuePolSet.add(tRnewIssuePolSchema);

		return true;
	}


	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllRnewIssuePolSet, "UPDATE");
	}
}
