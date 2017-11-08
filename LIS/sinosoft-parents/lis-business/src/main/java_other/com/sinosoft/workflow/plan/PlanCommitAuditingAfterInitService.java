package com.sinosoft.workflow.plan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.config.PlanFunc;
import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDPlanApproveMasterSchema;
import com.sinosoft.lis.schema.LDPlanApproveSubSchema;
import com.sinosoft.lis.schema.LDPlanSchema;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.lis.vschema.LDPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class PlanCommitAuditingAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PlanCommitAuditingAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private MMap mMap = new MMap();

	private String mContPlanCode;
	private LDPlanSchema mLDPlanSchema = new LDPlanSchema();
	private LDPlanApproveMasterSchema mLDPlanApproveMasterSchema = new LDPlanApproveMasterSchema();
	private LDPlanApproveSubSchema mLDPlanApproveSubSchema = new LDPlanApproveSubSchema();

	public PlanCommitAuditingAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

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
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mContPlanCode = (String) mTransferData.getValueByName("ContPlanCode");
		if (mContPlanCode == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中mContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LDPlanSet tLDPlanSet = new LDPlanSet();
		LDPlanDB tLDPlanDB = new LDPlanDB();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select * from ldplan where contplancode='"
				+ "?mContPlanCode?" + "'");
		sqlbv1.put("mContPlanCode", mContPlanCode);
		tLDPlanSet = tLDPlanDB.executeQuery(sqlbv1);
		if (tLDPlanSet == null || tLDPlanSet.size() <= 0) {
			// @@错误处理
			this.buildError("checkData", "查询产品组合信息失败，请确认是否录入正确！");
			return false;
		}
		mLDPlanSchema = tLDPlanSet.get(1);

		// 校验产品组合险种是否录入
		LDPlanRiskDB tLDPlanRiskDB = new LDPlanRiskDB();
		LDPlanRiskSet tLDPlanRiskSet = new LDPlanRiskSet();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("select * from ldplanrisk where contplancode='"
				+ "?mContPlanCode?" + "'");
		sqlbv2.put("mContPlanCode", mContPlanCode);
		tLDPlanRiskSet = tLDPlanRiskDB.executeQuery(sqlbv2);
		if (tLDPlanRiskSet == null || tLDPlanRiskSet.size() <= 0) {
			this.buildError("checkData", "未录入产品组合险种信息，请确认是否录入正确！");
			return false;
		}

		// 校验险种责任是否已经录入
		for (int i = 1; i <= tLDPlanRiskSet.size(); i++) {

			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "select count(*) from ldplandutyparam where 1=1 "
					+ " and contplancode = '" + "?mContPlanCode?"
					+ "' and riskcode = '"
					+ "?riskcode?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("mContPlanCode", mContPlanCode);
			sqlbv3.put("riskcode", tLDPlanRiskSet.get(i).getRiskCode());
			String rs = tExeSQL.getOneValue(sqlbv3);
			if (rs == null || "0".equals(rs)) {
				this.buildError("checkData", "未录入产品组合险种责任信息，请确认是否录入正确！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String tManageCom = mLDPlanSchema.getManageCom().trim();

		// 如果是总公司，则是最终状态，不用进行审核、审批流程
		// 分公司则要进入审核流程
		if ("86".equals(tManageCom)) {
			logger.debug("******总公司定制,******直接“审批通过”******");
			mLDPlanSchema.setState(PlanFunc.PLAN_STATE_APPROVE_YES);// 如果是总公司定制保险组合，则直接“审批通过”
		} else {
			mLDPlanSchema.setState(PlanFunc.PLAN_STATE_BEFOREAUDITING);// 果是分公司定制保险组合，则待审核
		}
		mLDPlanSchema.setModifyDate(PubFun.getCurrentDate());
		mLDPlanSchema.setModifyTime(PubFun.getCurrentTime());
		mLDPlanSchema.setOperator(mOperator);

		// 往审核审批结果表里插入数据
		logger.debug("******准备审核审批表信息******开始******");
		// 获取操作顺序号
		String tOperateNo = PlanFunc.getOperateNo(mLDPlanSchema
				.getContPlanCode(), mLDPlanSchema.getPlanType(),
				PlanFunc.PLAN_OPERATE_APPLY);
		logger.debug("获取操作顺序号tOperateNo====" + tOperateNo);

		mLDPlanApproveMasterSchema.setContPlanCode(mLDPlanSchema
				.getContPlanCode());// 套餐组合代码
		mLDPlanApproveMasterSchema.setContPlanName(mLDPlanSchema
				.getContPlanName());// 套餐组合名称
		mLDPlanApproveMasterSchema.setPlanType(mLDPlanSchema.getPlanType());// 计划类别
		mLDPlanApproveMasterSchema.setManageCom(tManageCom);//
		mLDPlanApproveMasterSchema.setOperateType(PlanFunc.PLAN_OPERATE_APPLY);// 操作类型:01-申请
		mLDPlanApproveMasterSchema.setOperateNo(tOperateNo);// 操作顺序号
		mLDPlanApproveMasterSchema.setOperateResult("");
		mLDPlanApproveMasterSchema.setRemark("");
		mLDPlanApproveMasterSchema.setOperator(mOperator);
		mLDPlanApproveMasterSchema.setMakeDate(PubFun.getCurrentDate());
		mLDPlanApproveMasterSchema.setMakeTime(PubFun.getCurrentTime());
		mLDPlanApproveMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLDPlanApproveMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLDPlanApproveSubSchema
				.setContPlanCode(mLDPlanSchema.getContPlanCode()); // 套餐组合代码
		mLDPlanApproveSubSchema
				.setContPlanName(mLDPlanSchema.getContPlanName()); // 套餐组合名称
		mLDPlanApproveSubSchema.setPlanType(mLDPlanSchema.getPlanType()); // 计划类别
		mLDPlanApproveSubSchema.setManageCom(tManageCom); //
		mLDPlanApproveSubSchema.setOperateType(PlanFunc.PLAN_OPERATE_APPLY); // 操作类型:01-申请
		mLDPlanApproveSubSchema.setOperateNo(tOperateNo); // 操作顺序号
		mLDPlanApproveSubSchema.setOperateResult("");
		mLDPlanApproveSubSchema.setRemark("");
		mLDPlanApproveSubSchema.setOperator(mOperator);
		mLDPlanApproveSubSchema.setMakeDate(PubFun.getCurrentDate());
		mLDPlanApproveSubSchema.setMakeTime(PubFun.getCurrentTime());
		mLDPlanApproveSubSchema.setModifyDate(PubFun.getCurrentDate());
		mLDPlanApproveSubSchema.setModifyTime(PubFun.getCurrentTime());

		logger.debug("******准备审核审批表信息******结束******");

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ContPlanCode", mLDPlanSchema
				.getContPlanCode());
		mTransferData.setNameAndValue("ContPlanName", mLDPlanSchema
				.getContPlanName());
		mTransferData.setNameAndValue("InputDate", mLDPlanSchema.getMakeDate()
				.toString());
		mTransferData
				.setNameAndValue("ManageCom", mLDPlanSchema.getManageCom());
		mTransferData.setNameAndValue("Operator", mOperator);
		mTransferData.setNameAndValue("State", mLDPlanSchema.getState());
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mMap.put(mLDPlanSchema, "UPDATE");
		mMap.put(mLDPlanApproveMasterSchema, "DELETE&INSERT");
		mMap.put(mLDPlanApproveSubSchema, "INSERT");
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String cFunName, String cMsg) {
		CError tError = new CError();
		tError.moduleName = "PlanCommitAuditingAfterInitService";
		tError.functionName = cFunName;
		tError.errorMessage = cMsg;
		this.mErrors.addOneError(tError);
	}

	public static void main(String[] args) {
		PlanCommitAuditingAfterInitService plancommitauditingafterinitservice = new PlanCommitAuditingAfterInitService();
	}
}
