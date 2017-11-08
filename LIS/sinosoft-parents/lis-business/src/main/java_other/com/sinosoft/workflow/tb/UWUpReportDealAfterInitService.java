package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCReinsureReportDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCReinsureReportSchema;
import com.sinosoft.lis.schema.LCReinsureReportTraceSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流节点任务:提起再保呈报处理
 * </p>
 * <p>
 * Description: 再保呈报处理工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class UWUpReportDealAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(UWUpReportDealAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

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
	private String mMissionID;

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 再保呈报表 */
	private LCReinsureReportSchema mLCReinsureReportSchema = new LCReinsureReportSchema();
	private LCReinsureReportSchema mmLCReinsureReportSchema = new LCReinsureReportSchema();
	private LCReinsureReportTraceSchema mLCReinsureReportTraceSchema = new LCReinsureReportTraceSchema();
	
	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	public UWUpReportDealAfterInitService() {
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

		logger.debug("After UWUpReportAfterInitService Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加生调数据
		if (mmLCReinsureReportSchema != null) {
			map.put(mmLCReinsureReportSchema, "UPDATE");
		}
		if (mLCReinsureReportTraceSchema != null) {
			map.put(mLCReinsureReportTraceSchema, "INSERT");
		}
		if(mLCCUWMasterSchema!=null)
			// 更新合同单核保主表
			map.put(mLCCUWMasterSchema, "UPDATE");
		
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
			CError.buildErr(this, "保单" + mContNo + "信息查询失败!") ;
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

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

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!") ;
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operate失败!") ;
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!") ;
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据失败!") ;
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");

		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!") ;
			return false;
		}

		// 获得业务生调通知数据
		mLCReinsureReportSchema = (LCReinsureReportSchema) mTransferData
				.getValueByName("LCReinsureReportSchema");
		if (mLCReinsureReportSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输获得业务生调数据失败!") ;
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中MissionID失败!") ;
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 准备再保呈报表信息
		if (prepareReinsureReport() == false)
			return false;

		// 准备再保呈报轨迹表信息
		if (prepareReinsureReportTrace() == false)
			return false;

		// 合同核保主表是否需要再保标志信息:如果有则置该信息为“N”
		if (prepareUpReportFlag() == false)
			return false;

		return true;

	}
	
	/**
	 * 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean prepareUpReportFlag() {
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		if(!tLCCUWMasterDB.getInfo())
		{
			CError.buildErr(this, "合同核保主表查询失败!") ;
			return false;
		}
		if(tLCCUWMasterDB.getSugPassFlag()!=null && tLCCUWMasterDB.getSugPassFlag().equals("Y"))
		{
			mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
			mLCCUWMasterSchema.setSugPassFlag("N");//是否需要再保呈报标志，下合同核保结论时用2008-12-12 ln add
			mLCCUWMasterSchema.setOperator(mOperater);
			mLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			mLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}	

		return true;

	}	

	/**
	 * 准备再保呈报资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareReinsureReport() {
		LCReinsureReportDB tLCReinsureReportDB = new LCReinsureReportDB();
		tLCReinsureReportDB.setContNo(mContNo);
		if (!tLCReinsureReportDB.getInfo()) {
			CError.buildErr(this, "查询再保呈报表出错") ;
			return false;
		}

		mmLCReinsureReportSchema = tLCReinsureReportDB.getSchema();

		mmLCReinsureReportSchema.setReinsuredResult(mLCReinsureReportSchema
				.getReinsuredResult());
		mmLCReinsureReportSchema.setReinsuDesc(mLCReinsureReportSchema
				.getReinsuDesc());
		mmLCReinsureReportSchema.setReinsuRemark(mLCReinsureReportSchema
				.getReinsuRemark());
		mmLCReinsureReportSchema.setDealUserCode(mGlobalInput.Operator);
		mmLCReinsureReportSchema.setModifyDate(PubFun.getCurrentDate());
		mmLCReinsureReportSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	private boolean prepareReinsureReportTrace() {

		mLCReinsureReportTraceSchema.setContNo(mmLCReinsureReportSchema
				.getContNo());
		mLCReinsureReportTraceSchema.setGrpContNo(mmLCReinsureReportSchema
				.getGrpContNo());
		mLCReinsureReportTraceSchema.setProposalContNo(mmLCReinsureReportSchema
				.getProposalContNo());
		mLCReinsureReportTraceSchema.setReinsuOrder(mmLCReinsureReportSchema
				.getReinsuReportNum());
		mLCReinsureReportTraceSchema
				.setReinsuredResult(mmLCReinsureReportSchema
						.getReinsuredResult());
		mLCReinsureReportTraceSchema.setMakeDate(mmLCReinsureReportSchema
				.getMakeDate());
		mLCReinsureReportTraceSchema.setMakeTime(mmLCReinsureReportSchema
				.getMakeTime());
		mLCReinsureReportTraceSchema.setUserCode(mmLCReinsureReportSchema
				.getUserCode());
		mLCReinsureReportTraceSchema.setDealUserCode(mGlobalInput.Operator);
		mLCReinsureReportTraceSchema.setModifyDate(PubFun.getCurrentDate());
		mLCReinsureReportTraceSchema.setModifyTime(PubFun.getCurrentTime());
		mLCReinsureReportTraceSchema.setReinsuDesc(mmLCReinsureReportSchema
				.getReinsuDesc());
		mLCReinsureReportTraceSchema.setReinsuRemark(mmLCReinsureReportSchema
				.getReinsuRemark());
		mLCReinsureReportTraceSchema.setReportRemark(mmLCReinsureReportSchema
				.getReportReason());

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
			CError.buildErr(this, "代理人表LAAgent查询失败!") ;
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError.buildErr(this, "代理人表LAAgent中的代理机构数据丢失!") ;
			return false;
		}

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "代理人展业机构表LABranchGroup查询失败!") ;
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError.buildErr(this, "代理人展业机构表LABranchGroup中展业机构信息丢失!") ;
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
		mTransferData.setNameAndValue("ReinsuReportNum",
				mmLCReinsureReportSchema.getReinsuReportNum());

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
