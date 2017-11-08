package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

/**
 * <p>Title: 人工核保初始化发放核保通知书结点 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author tongmeng
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.BeforeEndService;

public class ManuUWBeforeEnd implements BeforeEndService {
private static Logger logger = Logger.getLogger(ManuUWBeforeEnd.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mOperater;
	private String mManageCom;
	private String mCreateFlag = "";

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;

	public ManuUWBeforeEnd() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

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
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
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
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 选择工作流流转活动
		if (chooseActivity() == false)
			return false;

		return true;
	}

	/**
	 * preparePrt 准备打印表
	 * 
	 * @return boolean
	 */
	private boolean chooseActivity() {
		String tCustomerNo = (String) this.mTransferData
				.getValueByName("CustomerNo");
		String tCustomerNoType = (String) this.mTransferData
				.getValueByName("CustomerNoType");
		String tSQL =
		// 取投保人数量
		" select appntno,'A' from lcappnt a where contno='"
				+ "?contno?"
				+ "' "
				+ " and not exists "
				+ " (select 1 from lwmission where missionid='"
				+ "?missionid?"
				+ "' and activityid='0000001105' "
				+ " and missionprop16='A' and missionprop15=a.appntno ) "
				+ " union "
				// 取被保人数量
				+ " select insuredno,'I' from lcinsured a where contno='"
				+ "?missionid1?" + "' " + " and not exists "
				+ " (select 1 from lwmission where missionid='" + "?missionid2?"
				+ "' and activityid='0000001105' "
				+ " and missionprop16='I' and missionprop15=a.insuredno ) ";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSQL);
        sqlbv1.put("contno", this.mContNo);
        sqlbv1.put("missionid", this.mMissionID);
        sqlbv1.put("missionid1", mContNo);
        sqlbv1.put("missionid2", mMissionID);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() > 0) {
			this.mCreateFlag = "1";
		} else {
			this.mCreateFlag = "0";
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("CreateFlag", mCreateFlag);
		return true;
	}

	/**
	 * 向工作流引擎提交数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// map.put(mLCCUWMasterSchema, "UPDATE");
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
}
