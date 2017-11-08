package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.AutoUWCheckBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流节点任务:新契约自动核保
 * </p>
 * <p>
 * Description: 自动核保工作流后台AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 续涛，2005-10-04
 * @version 1.0
 */

public class UWAutoChkAfterInitServiceBLF implements AfterInitService {
private static Logger logger = Logger.getLogger(UWAutoChkAfterInitServiceBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();
	private VData mInputData;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mMissionID;
	private String mSubMissionID;

	private LCContSchema mLCContSchema = new LCContSchema();
	private String mContNo = "";

	/* 为下一个节点的属性所加 */
	private String mPolPassFlag = "";
	private String mContPassFlag = "";
	private String mProductSaleFlag = "";

	// private String mIssueFlag; //是否有问题件 0-没有 1-有
	// private String mOutIssueFlag; //是否有外部问题件 0-没有 1-有
	// private String mPrtSeq; //问题件打印流水号
	private String mUWAuthority = " "; // 核保权限
	private String mReDisMark = ""; // 分保标志

	public UWAutoChkAfterInitServiceBLF() {
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

		if (!getLCCont())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

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
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpContSignAfterEndService";
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
			tError.moduleName = "GrpContSignAfterEndService";
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
			tError.moduleName = "GrpContSignAfterEndService";
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
			tError.moduleName = "GrpContSignAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpContSignAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProposalApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
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
		return true;
	}

	/**
	 * 通过ContNo查询LCCont表的信息
	 * 
	 * @return boolean
	 * 
	 */
	private boolean getLCCont() {
		LCContDB aLCContDB = new LCContDB();
		aLCContDB.setContNo(this.mContNo);
		LCContSet aLCContSet = aLCContDB.query();
		if (aLCContSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "PrintNBNoticeAfterInitService";
			tError.functionName = "prepareLCCont";
			tError.errorMessage = "通过合同号查询LCCont表没有数据!";
			this.mErrors.addOneError(tError);
			return false;

		}
		this.mLCContSchema = aLCContSet.get(1);
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		try {
			
			if (!dealAutoUW()) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}

		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean dealAutoUW() {
		logger.debug("-------------------------调用自动核保BL---开始-------------------------");

		AutoUWCheckBL tAutoUWCheckBL = new AutoUWCheckBL();

		boolean tResult = tAutoUWCheckBL.submitData(this.mInputData, "");

		// 只传入合同号
		if (tResult) {
			mMap = (MMap) tAutoUWCheckBL.getResult().getObjectByObjectName(
					"MMap", 0);
			TransferData tTransferData = (TransferData) tAutoUWCheckBL
					.getResult().getObjectByObjectName("TransferData", 0);

			this.mPolPassFlag = (String) tTransferData
					.getValueByName("PolPassFlag");
			this.mContPassFlag = (String) tTransferData
					.getValueByName("ContPassFlag");
			this.mProductSaleFlag = (String) tTransferData
					.getValueByName("ProductSaleFlag");
			logger.debug("mPolPassFlag==" + this.mPolPassFlag);
			logger.debug("mContPassFlag==" + this.mContPassFlag);
			logger.debug("ProductSaleFlag==" + this.mProductSaleFlag);

			mUWAuthority = tAutoUWCheckBL.getHierarhy(); // 核保级别 add by yaory
			mReDisMark = tAutoUWCheckBL.getReDistribute(); // 分保标志 add by yaory
			logger.debug("核保级别与分保标志======" + mReDisMark + "-"
					+ mUWAuthority);
		} else {
			this.mErrors.copyAllErrors(tAutoUWCheckBL.mErrors);
			return false;
		}

		logger.debug("-------------------------调用自动核保BL---结束-------------------------");
		return true;
	}

	private boolean prepareTransferData() {

		if (mPolPassFlag.equals("5")) {
			mContPassFlag = "5";
		}

		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCContSchema
				.getAgentGroup());

		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveAfterEndService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mTransferData
				.setNameAndValue("AgentName", tLAAgentSet.get(1).getName());

		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());

		mTransferData.setNameAndValue("ApproveDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("ContNo", this.mContNo);
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("PolApplyDate", mLCContSchema
				.getPolApplyDate());
		mTransferData.setNameAndValue("ProposalContNo", mLCContSchema
				.getProposalContNo());

		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ReDisMark", mReDisMark); // add by yaory
																// 分保标志 1-分保0不分保
		mTransferData.setNameAndValue("ReportType", "0");

		mTransferData.setNameAndValue("UWAuthority", mUWAuthority); // add by
																	// yaory 权限
																	// 如H1

		mTransferData.setNameAndValue("UWDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("UWUpReport", "0");// 上报流向--目前无法赋值
		mTransferData.setNameAndValue("UserCode", "0000000000");

		mTransferData.setNameAndValue("UWFlag", mContPassFlag);
		// tongmeng 2007-12-03 add
		// 增加扫描时间和核保状态
		String tScanDate = "";
		tScanDate = PubFun1.getScanDate(mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ScanDate", tScanDate);
		
		//SYY BEGIN
		String tUwState = PubFun1.getUwState("10010005", mLCContSchema.getContNo(), this.mMissionID, this.mSubMissionID);
		//String tUwState = PubFun1.getUwState("0000001003", mLCContSchema.getContNo(), this.mMissionID, this.mSubMissionID);
		//SYY END
		
		logger.debug("tUwState:" + tUwState);
		mTransferData.setNameAndValue("Uw_State", tUwState);
		String tDefaultOperator;
		if (tUwState.equals("2")) {
			// 如果是核保已回复的话,返回核保人自己的工作池
			//tDefaultOperator = this.getDefalutOperator();
			//tongmeng 2009-04-29 modify
			//核保已回复,返回到公共池
			tDefaultOperator = null;
		} else {
			// 返回到公共池
			tDefaultOperator = null;
		}
		
		
		mTransferData.setNameAndValue("DefaultOperator", tDefaultOperator);
		//SYY BEGIN
//		mTransferData.setNameAndValue("LastUserCode", this.getDefalutOperator());
//		logger.debug("LastUserCode="+this.getDefalutOperator());
		mTransferData.setNameAndValue("LastUserCode", (String) mTransferData.getValueByName("LastUserCode"));
		logger.debug("LastUserCode="+(String) mTransferData.getValueByName("LastUserCode"));
		//SYY END
		mTransferData.setNameAndValue("StateDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("StateTime", PubFun.getCurrentTime());
		mTransferData.setNameAndValue("ProductSaleFlag", mProductSaleFlag);
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();

		mResult.add(mMap);
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

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("MissionID", "00000000000000026283");
		tTransferData.setNameAndValue("SubMissionID", "1");
		tTransferData.setNameAndValue("ActivityID", "0000001003");
		tTransferData.setNameAndValue("ContNo", "86336000000000");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);

		TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();

		String Content = "";
		if (tTbWorkFlowUI.submitData(tVData, "0000001003") == false) {
			int n = tTbWorkFlowUI.mErrors.getErrorCount();
			logger.debug("n==" + n);
			for (int j = 0; j < n; j++)
				logger.debug("Error: "
						+ tTbWorkFlowUI.mErrors.getError(j).errorMessage);
			Content = " 自动核保失败，原因是: "
					+ tTbWorkFlowUI.mErrors.getError(0).errorMessage;
		}

	}

}
