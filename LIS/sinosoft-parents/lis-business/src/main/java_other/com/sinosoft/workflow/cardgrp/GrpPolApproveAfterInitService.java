package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheckgrp.GrpUWAutoChkBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流团单复核服务类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author heyq modify by zhangxing 2005-05-18
 * @version 1.0
 */

public class GrpPolApproveAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpPolApproveAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private MMap mMap;

	/** 数据操作字符串 */
	private String mOperator;

	/** 业务数据操作字符串 */
	private String mGrpContNo;
	private String mApproveFlag;
	private String mMissionID;
	private String mPolSql;
	private String mContSql;
	private String mGrpContSql;
	private String mGrpPolSql;
	private String mIssueFlag;

	// 是否有外部问题件 0-没有 1-有
	private String mOutIssueFlag;
	private String mNoticeType;
	private String mUWFlag = "0";
	private String mPrtSeq1 = "";
	private String mUWAuthority;

	// 记录保单的权限的标志
	private String mUWAuthorityFlag;

	public GrpPolApproveAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("=====This is GrpPolApproveAfterInitService->submitData=====\n");

		// 得到外部传入的数据,将数据备份到本类中
		// logger.debug("=====getInputData start=====\n");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// logger.debug("=====getInputData end=====\n");

		// 校验是否有未打印的体检通知书
		// logger.debug("=====checkData start=====\n");
		if (!checkData()) {
			return false;
		}
		// logger.debug("=====checkData end=====\n");

		// 进行业务处理
		// logger.debug("=====dealData start=====\n");
		if (!dealData()) {
			return false;
		}
		// logger.debug("=====dealData end=====\n");

		// 增加自核是否使用的校验，最近发现生产环境结点已经发现变化，但实际并没有核保过。
		if (this.mIssueFlag.equals("0")) {
			if (!checkApprove(mGrpContNo)) {
				return false;
			}
		}
		// 为工作流下一节点属性字段准备数据
		// logger.debug("=====prepareTransferData start=====\n");
		if (!prepareTransferData()) {
			return false;
		}
		// logger.debug("=====prepareTransferData end=====\n");

		// 准备往后台的数据
		// logger.debug("=====prepareOutputData start=====\n");
		if (!prepareOutputData()) {
			return false;
		}
		// logger.debug("=====prepareOutputData end=====\n");
		// logger.debug("Start Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.add(this.mMap);
		if (mOutIssueFlag.equals("1") && mNoticeType.equals("1")) {
			map.put(mLOPRTManagerSchema, "INSERT");
			
		}
		//有问题件时（不自核）add by liuqh 2008-09-24
		if(!this.mIssueFlag.equals("0"))
		{
			map.put(mLCGrpContSchema, "UPDATE");
		}
		//tongmeng 2009-04-21 add
		//团险卡单不走自核,直接更新相关字段
		if(this.mIssueFlag.equals("0"))
		{
			String tSQL = "update lcpol set approveflag='9',uwflag='9' where grpcontno='"+mGrpContNo+"' ";
			map.put(tSQL, "UPDATE");
			String tSQL1 = "update lccont set approveflag='9',uwflag='9' where grpcontno='"+mGrpContNo+"' ";
			map.put(tSQL1, "UPDATE");
			String tSQL2 = "update lcgrppol set approveflag='9',uwflag='9' where grpcontno='"+mGrpContNo+"' ";
			map.put(tSQL2, "UPDATE");
			String tSQL3 = "update lcgrpcont set approveflag='9',uwflag='9' where grpcontno='"+mGrpContNo+"' ";
			map.put(tSQL3, "UPDATE");
		}
		mResult.add(map);

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mGrpContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mLCGrpContSchema.setSchema(tLCGrpContDB);
		if (!mLCGrpContSchema.getAppFlag().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "此集体单不是投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);

			return false;
		}
		if (mLCGrpContSchema.getApproveFlag().equals("9")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "此集体投保单复核已经通过，不能再进行复核通过操作!";
			this.mErrors.addOneError(tError);

			return false;
		}
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(*) from LCCont " + "where GrpContNo = '"
				+ mLCGrpContSchema.getGrpContNo() + "'";
		String tStr = "";
		double tCount = -1;
		tStr = tExeSQL.getOneValue(sql);
		if (tStr.equals("")) {
			tCount = 0;
		} else {
			tCount = Double.parseDouble(tStr);
		}
		if (tCount <= 0.0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "dealData";
			tError.errorMessage = "集体投保单下没有个人投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);

			return false;
		}

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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得当前工作任务的GrpContNo
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中GrpContNo失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		if (mLCGrpContSchema.getAppFlag() != null
				&& !mLCGrpContSchema.getAppFlag().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此单不是投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);

			return false;
		}
		if (mLCGrpContSchema.getUWFlag() != null
				&& !mLCGrpContSchema.getUWFlag().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpProposalApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此投保单已经开始核保，不能进行复核操作!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mInputData.clear();
		mInputData.add(mGlobalInput);
		mInputData.add(mLCGrpContSchema);
		mInputData.add(mTransferData);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("=====This is GrpPolApproveAfterInitService->dealdata=====\n");

		// 判断团体主险投保单下是否有操作员的问题件,有则置该团体保单状态为待复核修改
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(*) from LCGrpIssuePol "
				+ "where GrpContNo = '" + mLCGrpContSchema.getGrpContNo() + "'"
				+ "and replyman is null ";
		// + "and backobjtype = '1'";

		String tStr = "";
		double tCount = -1;
		tStr = tExeSQL.getOneValue(sql);
		if (tStr.equals("")) {
			tCount = 0;
		} else {
			tCount = Double.parseDouble(tStr);
		}

		if (tCount > 0.0) {
			mApproveFlag = "1"; // 复核修改
		} else {
			mApproveFlag = "9"; // 自动核保
		}

		// logger.debug(" mApproveFlag: " + mApproveFlag);

		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		// 修改集体投保单复核人编码和复核日期
		mLCGrpContSchema.setApproveCode(mOperator);
		mLCGrpContSchema.setApproveDate(tCurrentDate);
		mLCGrpContSchema.setApproveTime(tCurrentTime);
		mLCGrpContSchema.setApproveFlag(mApproveFlag);
		mLCGrpContSchema.setModifyDate(tCurrentDate);
		mLCGrpContSchema.setModifyTime(tCurrentTime);
		mLCGrpContSchema.setSpecFlag("0");

		// 查看是否有问题件
		// logger.debug("开始判断是否发问题件");
		LCGrpIssuePolDB aLCGrpIssuePolDB = new LCGrpIssuePolDB();
		String aSQL = "select * from LCGrpIssuePol where Grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and replyman is null and replyresult is null";
		logger.debug("EXECUT　SQL===" + aSQL);
		LCGrpIssuePolSet aLCGrpIssuePolSet = aLCGrpIssuePolDB
				.executeQuery(aSQL);

		// 有问题件
		if (aLCGrpIssuePolSet.size() > 0) {
			this.mIssueFlag = "1";
		} else {
			this.mIssueFlag = "0";
		}
		this.mNoticeType = "0";

		// 有外部问题件
		aSQL = aSQL + " and BackObjType='2'";
		LCGrpIssuePolSet bLCGrpIssuePolSet = aLCGrpIssuePolDB
				.executeQuery(aSQL);
		logger.debug("EXECUT　SQL===" + aSQL);
		if (bLCGrpIssuePolSet.size() > 0) {
			this.mOutIssueFlag = "1";
		} else {
			this.mOutIssueFlag = "0";
		}

		// 是否是一般通知书
		aSQL = aSQL + " and IssueType <> '99'";
		LCGrpIssuePolSet cLCGrpIssuePolSet = aLCGrpIssuePolDB
				.executeQuery(aSQL);
		logger.debug("EXECUT　SQL===" + aSQL);
		if (cLCGrpIssuePolSet.size() > 0) {
			this.mNoticeType = "1";
		} else {
			this.mNoticeType = "0";
		}

		// 如果有外部问题件，发问题件通知书
		if (mOutIssueFlag.equals("1") && mNoticeType.equals("1")) {
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

			// 生成打印流水号
			String tPrtSeq1 = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
			mPrtSeq1 = tPrtSeq1;
			mLOPRTManagerSchema.setPrtSeq(tPrtSeq1);
			mLOPRTManagerSchema.setOtherNo(mLCGrpContSchema.getGrpContNo());

			// 保单号
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL);

			// 打印类型
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_QUEST);

			// 管理机构
			mLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom);
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

			// 前台打印
			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setPatchFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			mLOPRTManagerSchema.setOldPrtSeq(tPrtSeq1);
		}

		// 如果没有问题件则进行自动核保校验
		if (this.mIssueFlag.equals("0")) {
//			GrpUWAutoChkBL tGrpUWAutoChkBL = new GrpUWAutoChkBL();
//			boolean tUWResult = tGrpUWAutoChkBL.submitData(mInputData, null);
//			if (!tUWResult) {
//				CError.buildErr(this, "自动核保失败！");
//				return false;
//			} else {
//				this.mUWAuthority = tGrpUWAutoChkBL.getUWGrade();
//				mTransferData.setNameAndValue("FinishFlag", "1");
//				mMap = (MMap) tGrpUWAutoChkBL.getResult()
//						.getObjectByObjectName("MMap", 0);
//				mUWFlag = (String) tGrpUWAutoChkBL.getResult()
//						.getObjectByObjectName("String", 0);
//			}
		}

		return true;
	}

	/**
	 * 
	 */
	private boolean checkApprove(String tGrpcontno) {
		// LCPolDB tLCPolDB=new LCPolDB();
		// LCPolSet tLCPolSet=new LCPolSet();
		// tLCPolDB.setGrpContNo(tGrpcontno);
		// tLCPolSet=tLCPolDB.query();
		// if(tLCPolSet!=null && tLCPolSet.size()>0)
		// {
		// for(int i=1;i<=tLCPolSet.size();i++)
		// {
		// if(tLCPolSet.get(i).getUWFlag().equals("0"))
		// {
		// CError.buildErr(this, "自核未成功完成，请重新点'复核确认'！");
		// return false;
		// }
		// }
		// }
//		LCContDB tLCContDB = new LCContDB();
//		LCContSet tLCContSet = new LCContSet();
//		tLCContDB.setGrpContNo(tGrpcontno);
//		tLCContSet = tLCContDB.query();
//		if (tLCContSet != null && tLCContSet.size() > 0) {
//			for (int i = 1; i <= tLCContSet.size(); i++) {
//				if (tLCContSet.get(i).getUWFlag() == null
//						|| tLCContSet.get(i).getUWFlag().equals("0")) {
//					CError.buildErr(this, "自核未成功完成，请重新点'复核确认'！");
//					return false;
//				}
//			}
//		}
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ApproveFlag", mApproveFlag);
		mTransferData.setNameAndValue("IssueFlag", this.mIssueFlag);
		mTransferData.setNameAndValue("OutIssueFlag", this.mOutIssueFlag);
		mTransferData.setNameAndValue("NoticeType", mNoticeType);

		if (mUWAuthority == null || mUWAuthority.equals("")) {
			mUWAuthority = "B1";
		}
		mTransferData.setNameAndValue("UWAuthority", mUWAuthority);
		if (mPrtSeq1.equals("")) {
			mPrtSeq1 = "000000000";
		}
		mTransferData.setNameAndValue("PrtSeq1", mPrtSeq1);
		mTransferData.setNameAndValue("GrpContNo", mGrpContNo);
		mTransferData.setNameAndValue("OldPrtSeq", mPrtSeq1);
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		mTransferData.setNameAndValue("GrpNo", mLCGrpContSchema.getAppntNo());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());
		mTransferData.setNameAndValue("UWFlag", mUWFlag);

		// 增加核保完成日期与完成时间 add by yaory
		mTransferData.setNameAndValue("UWDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("UWTime", PubFun.getCurrentTime());

		// 是否分保标记
		mTransferData.setNameAndValue("ReDisMark", "0");
		mTransferData.setNameAndValue("PolApplyDate", mLCGrpContSchema
				.getPolApplyDate());
		mTransferData.setNameAndValue("UserCode", "0000000000");
		if (!getAuthority()) {
			return false;
		}
		if ("1".equals(mLCGrpContSchema.getDonateContflag())) {
			// 团单是赠送保单
			this.mUWAuthorityFlag = "6";// 赠送保单
		}
		mTransferData.setNameAndValue("ReportType", mUWAuthorityFlag);
		mTransferData.setNameAndValue("ApproveDate", PubFun.getCurrentDate());

		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 比较保单的核保权限和管理部门的核保权限
	 * 
	 * @return boolean
	 */
	public boolean getAuthority() {

		// 记录部门编码
		String tManageCom = "";
		tManageCom = (String) mGlobalInput.ManageCom;
		tManageCom = tManageCom.substring(0, 2);
		// tLDCodeDB.setCodeType("uwauthority");
		// tLDCodeDB.setCode(tManageCom);
		// tLDCodeSet = tLDCodeDB.query();
		// LDCodeDB tLDCodeDB = new LDCodeDB();
		// LDCodeSet tLDCodeSet = new LDCodeSet();
		String strSQL = "select max(uwpopedom) "
				+ "from lduwuser where substr(managecom,1,2) = '" + tManageCom
				+ "'";
		// + "' group by managecom ";
		// logger.debug("====EXECUT SQL = " + strSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSR = new SSRS();
		try {
			tSSR = tExeSQL.execSQL(strSQL);
		} catch (Exception ex) {

			CError.buildErr(this, "查询LDUwuser表发生错误!");
		}

		// 比较保单的权限和部门的最高核保权限
		String tUWAuthority = tSSR.GetText(1, 1);
		if (tUWAuthority == null || tUWAuthority.equals("")) {

			CError.buildErr(this, "返回分公司的最高核保权限为空, ManageCom = " + tManageCom);
			return false;
		}
		if (tUWAuthority.compareTo(this.mUWAuthority) < 0) {

			// 保单上报类型为超权限上报
			this.mUWAuthorityFlag = "2";
		} else {

			// 保单上报类型为新契约核保
			this.mUWAuthorityFlag = "0";
		}

		return true;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 
	 */
	public static void main(String[] args) {

		// GrpPolApproveAfterInitService tGrpPolApproveAfterInitService =
		// new GrpPolApproveAfterInitService();
		// GlobalInput tGlobalInput = new GlobalInput();
		// tGlobalInput.Operator = "tk";
		// tGlobalInput.ManageCom = "0101";
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("MissionID", "0000000103");
		// tTransferData.setNameAndValue("GrpContNo", "20060512000006");
		// VData tVData = new VData();
		// tVData.add(tGlobalInput);
		// tVData.add(tTransferData);
		// tGrpPolApproveAfterInitService.submitData(tVData, "");
		// VData tResult = new VData();
		// tResult = tGrpPolApproveAfterInitService.getResult();
		// tTransferData =
		// tGrpPolApproveAfterInitService.getReturnTransferData();
		// String tAuthority = String.valueOf(
		// tTransferData.getValueByName("ReportType"));
		// if (tAuthority != null && !tAuthority.equals("")){
		//
		// if (tAuthority.equals("0")){
		//
		// logger.debug("======新契约核保=====\n");
		// }
		// if (tAuthority.equals("2")){
		//
		// logger.debug("=====超权限上报=====\n");
		// }
		// }else{
		// logger.debug("=====返回Authority为空!");
		// }
	}
}
