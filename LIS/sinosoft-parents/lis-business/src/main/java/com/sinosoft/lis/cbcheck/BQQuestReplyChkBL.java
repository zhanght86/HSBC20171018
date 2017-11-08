package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPIssuePolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPIssuePolSchema;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class BQQuestReplyChkBL {
private static Logger logger = Logger.getLogger(BQQuestReplyChkBL.class);
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
	private LPContSet mLPContSet = new LPContSet();
	private LPContSet mAllLPContSet = new LPContSet();
	private LPContSchema mLPContSchema = new LPContSchema();
	private String mContNo = "";

	/** 核保主表 */
	private LPCUWMasterSet mLPCUWMasterSet = new LPCUWMasterSet();
	private LPCUWMasterSet mAllLPCUWMasterSet = new LPCUWMasterSet();
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();

	/** 问题件表 */
	private LPIssuePolSet mLPIssuePolSet = new LPIssuePolSet();
	private LPIssuePolSet mmLPIssuePolSet = new LPIssuePolSet();
	private LPIssuePolSet mAllLPIssuePolSet = new LPIssuePolSet();

	public BQQuestReplyChkBL() {
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
		if (!dealData(mLPContSchema)) {
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
	private boolean dealData(LPContSchema tLPContSchema) {
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

		mLPIssuePolSet.set((LPIssuePolSet) cInputData.getObjectByObjectName(
				"LPIssuePolSet", 0));

		if (mLPIssuePolSet.size() <= 0) {
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
		LPIssuePolSchema tLPIssuePolSchema = new LPIssuePolSchema();
		tLPIssuePolSchema = mLPIssuePolSet.get(1);
		tReply = tLPIssuePolSchema.getReplyResult();
		LPIssuePolDB tLPIssuePolDB = new LPIssuePolDB();
		LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();

		tLPIssuePolDB.setEdorNo(tLPIssuePolSchema.getEdorNo());
		tLPIssuePolDB.setContNo(tLPIssuePolSchema.getContNo());
		tLPIssuePolDB.setIssueType(tLPIssuePolSchema.getIssueType());
		logger.debug("issuetype:" + tLPIssuePolSchema.getIssueType());
		tLPIssuePolDB.setOperatePos(tLPIssuePolSchema.getOperatePos());
		logger.debug("operatepos:" + tLPIssuePolSchema.getOperatePos());
		tLPIssuePolDB.setSerialNo(tLPIssuePolSchema.getSerialNo());
		logger.debug("SerialNo:" + tLPIssuePolSchema.getSerialNo());
		tLPIssuePolSet = tLPIssuePolDB.query();
		
		if (tLPIssuePolSet==null || tLPIssuePolSet.size()!=1) {
			CError.buildErr(this, "LPIssuePol查询失败!") ;
			return false;
		} else {
			tLPIssuePolSchema.setSchema(tLPIssuePolSet.get(1));
		}

		tLPIssuePolSchema.setReplyMan(mOperator);
		tLPIssuePolSchema.setReplyResult(tReply);
		tLPIssuePolSchema.setState("1");
		tLPIssuePolSchema.setOperator(mOperator);
		tLPIssuePolSchema.setManageCom(mManageCom);
		tLPIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
		tLPIssuePolSchema.setModifyTime(PubFun.getCurrentTime());

		mAllLPIssuePolSet.clear();
		mAllLPIssuePolSet.add(tLPIssuePolSchema);

		// 修改核保问题件回复标记
		//checkQuesFlag(tLPIssuePolSchema);

		return true;
	}

	/**
	 * 
	 */
	private boolean checkQuesFlag(LPIssuePolSchema tLPIssuePolSchema) {
		String tProposalContNo = "";
		String tsql = "";
		mAllLPCUWMasterSet.clear();

		LPIssuePolDB tLPIssuePolDB = new LPIssuePolDB();
		LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();

		String tEdorNo = tLPIssuePolSchema.getEdorNo();
		tProposalContNo = tLPIssuePolSchema.getProposalContNo();

		// 判断是不是有没有回复问题件
		tsql = "select * from LPIssuePol where edorno='"+"?edorno?"+"' and proposalcontno = '"
				+ "?proposalcontno?"
				+ "' and replyresult is null  and needprint = 'Y' "
				+ "  and backobjtype ='4'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("edorno", tEdorNo);
		sqlbv.put("proposalcontno", tProposalContNo);
		// 查询回复内容为空且满足如下条件数据，1：从非录单处返回给保户和业务员的需打印问题件，或2：从非录单处返回给操作员或机构的问题件，或3：从录单处返回给业务员或保户的需打印问题件，或4从录单处返回给机构的问题件
		logger.debug(tsql);
		tLPIssuePolSet = tLPIssuePolDB.executeQuery(sqlbv);

				LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
				tLPCUWMasterDB.setEdorNo(tEdorNo);
				tLPCUWMasterDB.setContNo(mContNo);

				LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();

				if ((tLPCUWMasterSet == null) || (tLPCUWMasterSet.size() <= 0)) {
					CError.buildErr(this, "合同核保主表中无法查询合同号为" + mContNo
							+ " 的合同信息!") ;
					return false;
				}

				LPCUWMasterSchema tLPCUWMasterSchema = tLPCUWMasterSet.get(1);

				if ((tLPIssuePolSet!=null && tLPIssuePolSet.size()>1) && tLPCUWMasterSchema.getQuesFlag().equals("1")) {
					tLPCUWMasterSchema.setQuesFlag("2");
				}

				mAllLPCUWMasterSet.add(tLPCUWMasterSchema);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mAllLPIssuePolSet, "UPDATE");
		mMap.put(mAllLPCUWMasterSet, "UPDATE");
	}
}
