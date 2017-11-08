package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流节点任务:新契约人工核保补打生调通知书
 * </p>
 * <p>
 * Description: 补打生调通知书工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RePrintSendNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RePrintSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

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

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mCode;//通知书类型

	private String mMissionID = "";
	private String mSubMissionID = "";
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();


	public RePrintSendNoticeAfterInitService() {
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

		logger.debug("After RePrintSendNoticeAfterInitService");

		// mResult.clear();
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

		// 校验是否有要补打的核保通知书
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
		tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			CError.buildErr(this, "核保通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "信息查询失败!") ;
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			CError.buildErr(this, "核保通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "信息查询失败!") ;
			return false;
		}

		mLOPRTManagerSchema = tLOPRTManagerSet.get(1);

		if (!mLOPRTManagerSchema.getCode().equals("TB89")
				&& !mLOPRTManagerSchema.getCode().equals("TB90")) {
			CError.buildErr(this, "核保通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "标识信息查询失败!") ;
			return false;
		}

		if (mLOPRTManagerSchema.getStateFlag() == null
				|| !mLOPRTManagerSchema.getStateFlag().equals("1")) {
			CError.buildErr(this, "核保通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "标识未处于已打印,但未回收状态!") ;
			return false;
		}
		return true;
	}



	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 打印队列
		if (preparePrint() == false)
			return false;
		// 准备需要删除的数据
		
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
		this.mMissionID = mTransferData.getValueByName("MissionID") == null ? ""
				: (String) mTransferData.getValueByName("MissionID");
		if (this.mMissionID.equals("")) {
			CError.buildErr(this, "前台传输业务数据中mMissionID失败!") ;
			return false;
		}
		this.mSubMissionID = mTransferData.getValueByName("SubMissionID") == null ? ""
				: (String) mTransferData.getValueByName("SubMissionID");
		if (this.mSubMissionID.equals("")) {
			CError.buildErr(this, "前台传输业务数据中mSubMissionID失败!") ;
			return false;
		}
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!") ;
			return false;
		}
		
		mCode = (String) mTransferData.getValueByName("Code");
		if (mCode == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中Code失败!") ;
			return false;
		}

		// 获得当前工作任务的任务ID
		mLOPRTManagerSchema = (LOPRTManagerSchema) mTransferData
				.getValueByName("LOPRTManagerSchema");
		if (mLOPRTManagerSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中LOPRTManagerSchema失败!") ;
			return false;
		}

		if (mLOPRTManagerSchema.getPrtSeq() == null
				|| mLOPRTManagerSchema.getPrtSeq().trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中LOPRTManagerSchema中的打印流水号失败!") ;
			return false;
		}
		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		if (mLOPRTManagerSchema.getPatchFlag() != null
				&& mLOPRTManagerSchema.getPatchFlag().equals("1")) {
			mLOPRTManagerSchema
					.setOldPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
		} else {
			mLOPRTManagerSchema.setOldPrtSeq(mLOPRTManagerSchema.getPrtSeq());
		}
		mLOPRTManagerSchema.setPatchFlag("1");
		mLOPRTManagerSchema.setStateFlag("0");

		//String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tCode = mLOPRTManagerSchema.getCode().replaceAll("[^0-9]", "");
		mLOPRTManagerSchema.setPrtSeq(PubFun1.CreateMaxNo("UWPRTSEQ",
				tCode));

		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

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
		// PrtSeqOper
		// PrtSeqUW
		// PrtSeqUW_UnPrint
		// tongmeng 2007-11-29 modify
		mTransferData.setNameAndValue("PrtSeqUWA", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUWI1", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUWI2", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUWI3", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUW_UnPrintA", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUW_UnPrintI1", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUW_UnPrintI2", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("PrtSeqUW_UnPrintI3", mLOPRTManagerSchema
				.getPrtSeq());
		
		mTransferData.setNameAndValue("PrtSeqOper", mLOPRTManagerSchema
				.getPrtSeq());

//		mTransferData.setNameAndValue("OldPrtSeq", mLOPRTManagerSchema
//				.getOldPrtSeq());
//		mTransferData
//				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
//		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
//				.getAgentGroup());
//		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
//				.getBranchAttr());
//		mTransferData
//				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
//		//tongmeng 2009-03-18 add
//		//增加通知书需要相关数据
//		String tSQL = "select * from lbmission where missionid='"+this.mMissionID+"' ";
//			if(mLOPRTManagerSchema.getPatchFlag() != null
//					&& mLOPRTManagerSchema.getPatchFlag().equals("1")) //补打
//				tSQL = tSQL + " and missionprop3='"+mLOPRTManagerSchema.getOldPrtSeq()+"' ";
//			else
//				tSQL = tSQL + " and missionprop3='"+mLOPRTManagerSchema.getPrtSeq()+"' ";
//			if(mLOPRTManagerSchema.getCode().equals("TB89"))
//			    tSQL = tSQL + " and activityid ='0000001107' order by serialno desc ";
//			else if(mLOPRTManagerSchema.getCode().equals("TB90"))
//			    tSQL = tSQL + " and activityid ='0000001302' order by serialno desc ";
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		tLWMissionSet = tLWMissionDB.executeQuery(tSQL);
//		if(tLWMissionSet.size()<=0)
//		{
//			CError.buildErr(this,"查询原打印数据失败!");
//			return false;
//		}
//		
//		mTransferData.setNameAndValue("CustomerNo", tLWMissionSet.get(1).getMissionProp8());
//		mTransferData.setNameAndValue("CustomerNoType", tLWMissionSet.get(1).getMissionProp9());
//		mTransferData.setNameAndValue("SpecFlag", tLWMissionSet.get(1).getMissionProp10());
//		mTransferData.setNameAndValue("ChangePolFlag", tLWMissionSet.get(1).getMissionProp11());
//		mTransferData.setNameAndValue("AddFeeFlag", tLWMissionSet.get(1).getMissionProp12());

		return true;
	}

	/**
	 * 准备向工作流引擎提交数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加体检通知书打印管理表数据
		map.put(mLOPRTManagerSchema, "INSERT");

		mResult.add(map);
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
		// SysUWNoticeBL sysUWNoticeBL1 = new SysUWNoticeBL();
	}
}
