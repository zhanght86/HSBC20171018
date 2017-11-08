package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportPrtSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCRReportPrtSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流节点任务:新契约发生调通知书
 * </p>
 * <p>
 * Description: 发生调通知书工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWRReportDealAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(UWRReportDealAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private HttpServletRequest httprequest;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mCustomerNo;
	// private String mInsuredNo;
	private String mMissionID;
	private String mPrtSeq = "";

	String mCode = "";

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	/** 生调打印关联表 */
	private LCRReportPrtSchema mLCRReportPrtSchema = new LCRReportPrtSchema();
	private LCRReportPrtSet mLCRReportPrtSet = new LCRReportPrtSet();
	private LCRReportPrtSet mNewLCRReportPrtSet = new LCRReportPrtSet();

	public UWRReportDealAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加体检通知书打印管理表数据
		map.put(mNewLCRReportPrtSet, "INSERT");

		// 添加续保批单核保主表通知书打印管理表数据

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
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 校验保单被保人编码信息
		// mInsuredNo = mLCContSchema.getInsuredNo();
		// if (mInsuredNo == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWRReportAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "的被保人编码信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_MEET); // 生调通知书
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStateFlag("0"); // 通知书未打印

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// //取生调人姓名
		// LDPersonDB tLDPersonDB = new LDPersonDB();
		// tLDPersonDB.setCustomerNo(mCustomerNo);
		// if (!tLDPersonDB.getInfo())
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthAfterInitService";
		// tError.functionName = "prepareHealth";
		// tError.errorMessage = "取被体检客户姓名失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		return true;
	}

	// private boolean CheckPrint() {
	// String tsql = "select distinct 1 from LOPRTManager where otherno = '" +
	// mContNo + "'"
	// + " and othernotype ='02'"
	// + " and code = '" + mCode + "'"
	// + " and stateflag in ('0','1')"
	// ;
	// logger.debug(tsql);
	//
	// ExeSQL tExeSQL = new ExeSQL();
	// String tflag = tExeSQL.getOneValue(tsql);
	//
	// if (tflag.trim().equals("1")) {
	// CError tError = new CError();
	// tError.moduleName = "UWSendPrintBL";
	// tError.functionName = "checkPrint";
	// tError.errorMessage = "通知书已发放，且未回收，不允许发放新的通知书!";
	// this.mErrors.addOneError(tError);
	// return false;
	// }
	// return true;
	//
	// }

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
		httprequest = (HttpServletRequest) cInputData.getObjectByObjectName(
				"HttpRequest", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");

		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		if (mCustomerNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中CustomerNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		if (mPrtSeq == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中mPrtSeq失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mCode = (String) mTransferData.getValueByName("Code");

		mLCRReportPrtSet = (LCRReportPrtSet) mTransferData
				.getValueByName("LCRReportPrtSet");
		if (mLCRReportPrtSet == null || mLCRReportPrtSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输获得问卷类型内容数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// httprequest.getSession().setAttribute("mPrtSeq",mPrtSeq);
		// 打印队列
		if (prepareLCRReportPrt() == false)
			return false;

		return true;

	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean prepareLCRReportPrt() {

		for (int i = 1; i <= mLCRReportPrtSet.size(); i++) {
			LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();

			tLCRReportPrtSchema.setContNo(mLCContSchema.getContNo());
			tLCRReportPrtSchema.setGrpContNo(mLCContSchema.getGrpContNo());

			tLCRReportPrtSchema.setPrtSeq(mPrtSeq);
			tLCRReportPrtSchema
					.setAskCode(mLCRReportPrtSet.get(i).getAskCode());
			tLCRReportPrtSchema.setCustomerNo(mCustomerNo);
			tLCRReportPrtSchema.setOperator(mGlobalInput.Operator);
			tLCRReportPrtSchema.setMakeDate(PubFun.getCurrentDate());
			tLCRReportPrtSchema.setMakeTime(PubFun.getCurrentTime());
			tLCRReportPrtSchema.setModifyDate(PubFun.getCurrentDate());
			tLCRReportPrtSchema.setModifyTime(PubFun.getCurrentTime());

			mNewLCRReportPrtSet.add(tLCRReportPrtSchema);
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent中的代理机构数据丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup中展业机构信息丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());

		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("CustomerNo", mCustomerNo);
		mTransferData.setNameAndValue("PrtSeq", mPrtSeq);
		mTransferData.setNameAndValue("Code", mCode);
		mTransferData.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());

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
}
