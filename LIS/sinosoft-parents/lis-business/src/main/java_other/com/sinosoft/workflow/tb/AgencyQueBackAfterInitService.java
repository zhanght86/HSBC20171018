package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流服务类:新契约机构问题件回复
 * </p>
 * <p>
 * Description: 机构问题件回复AfterInit服务类
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

public class AgencyQueBackAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(AgencyQueBackAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 工作流任务节点表 */
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mAgencyQueFlag = "0";
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mPrtSeq = "";
	private String mOperate = "";
	private String mState = "1";
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 续保业务员主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 如果该单证是补打单证,则同时将遗失原单证也回收.反之如果回收原单证,但其已补发过,则同时也要把补发的单证回收掉
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();
	private String mQueModFlag = "0";

	public AgencyQueBackAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
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
		if (!prepareTransferData())
			return false;

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加业务员通知书打印管理表数据
		if (mLOPRTManagerSet != null && mLOPRTManagerSet.size() > 0) {
			map.put(mLOPRTManagerSet, "UPDATE");
		}

		// 添加续保批单业务员主表数据
		if (mLCCUWMasterSchema != null) {
			map.put(mLCCUWMasterSchema, "UPDATE");
		}

		// 添加续保体检通知书自动发放表数据
		if (mLZSysCertifySet != null && mLZSysCertifySet.size() > 0) {
			map.put(mLZSysCertifySet, "UPDATE");
		}
		if (this.mLCIssuePolSet != null && this.mLCIssuePolSet.size() > 0) {
			map.put(this.mLCIssuePolSet, "UPDATE");
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
		// 校验是否已回收所有机构问题件
		//tongmeng 2009-05-15 modify
		//支持needprint='Y' 
		String tStr = "select * from lcissuepol where 1=1" + " and ContNo = '"
				+ "?ContNo?" + "'" + " and state is not null and state='0' "
				+ " and backobjtype = '4' and replyman is null "
				+ " and needprint='Y' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("ContNo",mContNo);
		logger.debug("tStr==" + tStr);
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv1);
		if (tLCIssuePolSet.size() > 0) {
			CError.buildErr(this,"存在未回复的机构问题件,请回复!");
			return false;
		}
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this,"保单" + mContNo + "信息查询失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 校验续保批单业务员主表
		// 校验保单信息
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		if (!tLCCUWMasterDB.getInfo()) {
			mLCCUWMasterSchema = null;
		} else {
			mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);
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

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得当前工作任务的子任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中SubMissionID失败!");
			return false;
		}
		mLCIssuePolSet.set((LCIssuePolSet) cInputData.getObjectByObjectName(
				"LCIssuePolSet", 0));

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (chooseActivity() == false)
			return false;

		return true;
	}

	/**
	 * 选择工作流流转
	 * 
	 * @return
	 */
	private boolean chooseActivity() {
		// 查询所有本次已经回复完毕的问题件.把状态修改为1
		String tStr = "select * from lcissuepol where 1=1 " + " and ContNo = '"
				+ "?ContNo1?" + "'" + " and state is not null and state='0' "
				+ " and backobjtype = '4' and replyman is not null "
				+ " and needprint='Y' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tStr);
		sqlbv2.put("ContNo1", mContNo);
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv2);

		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			tLCIssuePolSet.get(i).setState("1");// 机构问题件扭转完毕
			tLCIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			tLCIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
			// tLCIssuePolSet.get(i).setPrtSeq(mPrtSeq);
			this.mLCIssuePolSet.add(tLCIssuePolSet.get(i));
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("QueModFlag", mQueModFlag);
		mTransferData.setNameAndValue("AgencyQueFlag", mAgencyQueFlag);
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
		mTransferData.setNameAndValue("PrtSeq", this.mPrtSeq);
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		String tSQL = "select branchcode from laagent where agentcode='"
				+ "?agentcode?" + "'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("agentcode", mLCContSchema.getAgentCode());
		ExeSQL tExeSQL = new ExeSQL();
		mTransferData.setNameAndValue("AgentGroup", tExeSQL.getOneValue(sqlbv2));
		/*
		 * ContNo PrtNo AppntName ApproveDate ManageCom AgentCode AgentGroup
		 */
		mTransferData.setNameAndValue("State", this.mState);
		if(this.mState!=null&&this.mState.equals("1"))
		{
		//tongmeng 2009-04-14 add
		//如果是问题件已回复,记录回复日期和时间
		mTransferData.setNameAndValue("ReplyDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("ReplyTime", PubFun.getCurrentTime());
		}

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
