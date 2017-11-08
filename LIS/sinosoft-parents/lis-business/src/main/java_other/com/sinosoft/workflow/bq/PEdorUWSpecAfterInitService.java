package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPRemarkDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBRemarkSchema;
import com.sinosoft.lis.schema.LBSpecSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPRemarkSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.vschema.LBRemarkSet;
import com.sinosoft.lis.vschema.LBSpecSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPRemarkSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:工作流节点任务:保全人工核保特约录入服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class PEdorUWSpecAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PEdorUWSpecAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mEdorNo;
	private String mPolNo;
	private String mPrtNo;
	private String mInsuredNo;
	private String mSpecReason;
	private String mRemark;
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流特约活动表任务0000000003 */
	/** 保单表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 保全核保主表 */
	private LPUWMasterMainSchema mLPUWMasterMainSchema = new LPUWMasterMainSchema();
	/** 特约表 */
	private LPSpecSchema mLPSpecSchema = new LPSpecSchema();
	private LPSpecSet mLPSpecSet = new LPSpecSet();
	private LBSpecSet mLBSpecSet = new LBSpecSet();
	/** 保全备注表 */
	private LPRemarkSchema mLPRemarkSchema = new LPRemarkSchema();
	private LBRemarkSchema mLBRemarkSchema = new LBRemarkSchema();
	private LBRemarkSet mLBRemarkSet = new LBRemarkSet();
	private LPRemarkSet mLPRemarkSet = new LPRemarkSet();

	public PEdorUWSpecAfterInitService() {
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

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保特约信息
		if (prepareSpec() == false)
			return false;
		// 核保备注信息
		if (prepareRemark() == false)
			return false;

		return true;

	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorSpecAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema.setSchema(tLCPolDB);
		mPrtNo = mLCPolSchema.getPrtNo();

		// 校验保全批单核保主表
		// 校验保单信息
		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		tLPUWMasterMainDB.setEdorNo(mEdorNo);
		// tLPUWMasterMainDB.setPolNo(mPolNo) ;
		if (!tLPUWMasterMainDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorSpecAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "保全批单核保主表信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPUWMasterMainSchema.setSchema(tLPUWMasterMainDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UW);// 核保通知书
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL);// 保单号
		tLOPRTManagerDB.setStandbyFlag2(mEdorNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的核保通知书!";
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
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPolNo = (String) mTransferData.getValueByName("PolNo");
		if (mPolNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PolNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSpecReason = (String) mTransferData.getValueByName("SpecReason");
		// if ( mSpecReason == null )
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PEdorUWSpecAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中SpecReason失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// } //注销考虑到:只录入特约内容,而不录入特约原因时处理

		// 获得业务特约通知书数据
		mLPSpecSchema = (LPSpecSchema) mTransferData
				.getValueByName("LPSpecSchema");
		if (mLPSpecSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输获得业务特约数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mRemark = (String) mTransferData.getValueByName("Remark");
		// if ( mRemark == null )
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "PEdorUWSpecAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据中mRemark失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// } //注销考虑到:只录入特约内容,而不录入保全备注时处理

		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareSpec() {

		// 取险种名称
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(mLCPolSchema.getRiskCode());
		// tLMRiskDB.setRiskVer(mLCPolSchema.getRiskVersion());
		if (!tLMRiskDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取险种名称失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取代理人姓名
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCPolSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取代理人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备特约的数据
		if (mLPSpecSchema != null
				&& !mLPSpecSchema.getSpecContent().trim().equals("")) {
			// mLPSpecSchema.setSpecNo(PubFun1.CreateMaxNo("SpecNo",PubFun.getNoLimit(mGlobalInput.ComCode)));
			// mLPSpecSchema.setPolNo(mPolNo);
			// mLPSpecSchema.setPolType("1");
			// mLPSpecSchema.setEndorsementNo("");
			// mLPSpecSchema.setSpecType();
			// mLPSpecSchema.setSpecCode();
			// mLPSpecSchema.setSpecContent();//前台已准备
			mLPSpecSchema.setPrtFlag("1");
			mLPSpecSchema.setBackupType("");
			// mLPSpecSchema.setState("0") ;
			mLPSpecSchema.setOperator(mOperater);
			mLPSpecSchema.setMakeDate(PubFun.getCurrentDate());
			mLPSpecSchema.setMakeTime(PubFun.getCurrentTime());
			mLPSpecSchema.setModifyDate(PubFun.getCurrentDate());
			mLPSpecSchema.setModifyTime(PubFun.getCurrentTime());
		} else
			mLPSpecSchema = null;

		// 准备备份上一次的特约信息
		LPSpecDB tLPSpecDB = new LPSpecDB();

		tLPSpecDB.setPolNo(mPolNo);
		tLPSpecDB.setEndorsementNo(mEdorNo);
		mLPSpecSet = tLPSpecDB.query();
		logger.debug("LPSpecSet.size()" + mLPSpecSet.size());
		for (int i = 1; i <= mLPSpecSet.size(); i++) {
			LBSpecSchema tLBSpecSchema = new LBSpecSchema();
			mReflections.transFields(tLBSpecSchema, mLPSpecSet.get(i));
			tLBSpecSchema.setMakeDate(PubFun.getCurrentDate());
			tLBSpecSchema.setMakeTime(PubFun.getCurrentTime());
			mLBSpecSet.add(tLBSpecSchema);
		}

		// 准备保全核保主表信息
		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		// tLPUWMasterMainDB.setPolNo(mPolNo);
		tLPUWMasterMainDB.setEdorNo(mEdorNo);
		if (tLPUWMasterMainDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWSpecAfterInitService";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "无保全批单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLPUWMasterMainSchema.setSchema(tLPUWMasterMainDB);
		String tSpecReason = (String) mTransferData
				.getValueByName("SpecReason");
		if (tSpecReason != null && !tSpecReason.trim().equals("")) {
			mLPUWMasterMainSchema.setSpecReason(tSpecReason);
		} else {
			mLPUWMasterMainSchema.setSpecReason("");
		}

		if (mLPSpecSchema != null) {
			mLPUWMasterMainSchema.setSpecFlag("1");// 特约标识
		} else {
			mLPUWMasterMainSchema.setSpecFlag("0");// 特约标识
		}

		mLPUWMasterMainSchema.setOperator(mOperater);
		mLPUWMasterMainSchema.setManageCom(mManageCom);
		mLPUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLPUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareRemark() {

		// 准备保全备注的数据
		if (mRemark != null && !mRemark.trim().equals("")) {
			String tSerialNo = PubFun1.CreateMaxNo("RemarkNo", 20);
			mLPRemarkSchema.setSerialNo(tSerialNo);
			mLPRemarkSchema.setEdorNo(mEdorNo);
			mLPRemarkSchema.setPolNo(mPolNo);
			mLPRemarkSchema.setPrtNo(mPrtNo);
			mLPRemarkSchema.setOperatePos("3");
			mLPRemarkSchema.setRemarkCont(mRemark);
			mLPRemarkSchema.setManageCom(mManageCom);
			mLPRemarkSchema.setOperator(mOperater);
			mLPRemarkSchema.setMakeDate(PubFun.getCurrentDate());
			mLPRemarkSchema.setMakeTime(PubFun.getCurrentTime());
			mLPRemarkSchema.setModifyDate(PubFun.getCurrentDate());
			mLPRemarkSchema.setModifyTime(PubFun.getCurrentTime());
		} else
			mLPRemarkSchema = null;

		// 准备备份上一次的保全备注信息
		LPRemarkDB tLPRemarkDB = new LPRemarkDB();
		tLPRemarkDB.setEdorNo(mEdorNo);
		tLPRemarkDB.setPolNo(mPolNo);
		tLPRemarkDB.setPrtNo(mPrtNo);
		tLPRemarkDB.setOperatePos("3");
		tLPRemarkDB.setPolNo(mPolNo);
		mLPRemarkSet = tLPRemarkDB.query();
		logger.debug("tLPRemarkSet.size()" + mLPRemarkSet.size());
		for (int i = 1; i <= mLPRemarkSet.size(); i++) {
			LBRemarkSchema tLBRemarkSchema = new LBRemarkSchema();
			mReflections.transFields(tLBRemarkSchema, mLPRemarkSet.get(i));
			tLBRemarkSchema.setMakeDate(PubFun.getCurrentDate());
			tLBRemarkSchema.setMakeTime(PubFun.getCurrentTime());
			mLBRemarkSet.add(tLBRemarkSchema);
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加删除上一个特约数据
		if (mLPSpecSet != null && mLPSpecSet.size() > 0) {
			map.put(mLPSpecSet, "DELETE");
		}
		// 添加本次保全特约数据
		if (mLPSpecSchema != null) {
			map.put(mLPSpecSchema, "INSERT");
		}

		// 添加删除上一个保全备注数据
		if (mLPRemarkSet != null && mLPRemarkSet.size() > 0) {
			map.put(mLPRemarkSet, "DELETE");
		}
		// 添加保全特约备份数据
		if (mLBSpecSet != null && mLBSpecSet.size() > 0) {
			map.put(mLBSpecSet, "INSERT");
		}

		// 添加本次保全备注数据
		if (mLPRemarkSchema != null) {
			map.put(mLPRemarkSchema, "INSERT");
		}

		// 添加保全备注备份数据
		if (mLBRemarkSet != null && mLBRemarkSet.size() > 0) {
			map.put(mLBRemarkSet, "INSERT");
		}

		// 添加保全批单核保主表通知书打印管理表数据
		map.put(mLPUWMasterMainSchema, "UPDATE");

		mResult.add(map);
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
}
