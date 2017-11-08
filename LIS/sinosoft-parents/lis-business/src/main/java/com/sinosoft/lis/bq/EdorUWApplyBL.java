package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全人工核保申请
 * </p>
 * <p>
 * Description: 每个核保员可以申请n个核保单
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author FanXiang
 * @version 1.0
 */

public class EdorUWApplyBL {
private static Logger logger = Logger.getLogger(EdorUWApplyBL.class);

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

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mApplyType; // 申请类型 1-个险 2-团险
	private int mApplyNo; // 前台传输的需要申请的保单数
	private int mNo; // 实际申请的个数

	/** 业务操作类 */
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	private LWMissionSet mNewLWMissionSet = new LWMissionSet();

	public EdorUWApplyBL() {
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

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		if (!prepareOutputData())
			return false;

		logger.debug("dealData successful!");

		logger.debug("Start  Submit...");

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, "")) {
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据库提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("End Submit....");
		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		// 查出所有待核保,且未被申请的工作
		if (mApplyType.trim().equals("1")) // 个单新契约人工核保
		{
			tSql = "select * from lwmission where 1=1 "
					+ " and activityid in ('0000000017') " // 将来会加入续保、保全核保等的工作流节点
					+ " and defaultoperator is null "
					+ " order by modifydate asc,modifytime asc";
			sqlbv.sql(tSql);
		}
		// else if(mApplyType.trim().equals("2")) //团单新契约人工核保
		// {
		// tSql = "select * from lwmission where 1=1 "
		// + " and activityid in ('0000002004') " //将来会加入续保、保全核保等的工作流节点
		// + " and defaultoperator is null "
		// + " order by modifydate asc,modifytime asc"
		// ;
		// }
		// else if (mApplyType.trim().equals("3")) //询价人工核保
		// {
		// tSql = "select * from lwmission where 1=1 "
		// + " and activityid in ('0000006004') " //将来会加入续保、保全核保等的工作流节点
		// + " and defaultoperator is null "
		// + " order by modifydate asc,modifytime asc"
		// ;
		// }

		logger.debug("Sql=" + tSql);
		mLWMissionSet = tLWMissionDB.executeQuery(sqlbv);

		if (mLWMissionSet == null || mLWMissionSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "当前没有待核保的保单!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			if (mNo > mLWMissionSet.size()) {
				mNo = mLWMissionSet.size(); // 如果核保池中的待核保工作数小于申请数，则申请数=待核保数
			}
		}

		LWMissionSchema tLWMissionSchema;
		for (int i = 1; i <= mNo; i++) {
			tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema = mLWMissionSet.get(i);
			tLWMissionSchema.setDefaultOperator(mOperator);
			mNewLWMissionSet.add(tLWMissionSchema);
		}

		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// LWMissionSet tLWMissionSet = new LWMissionSet();
		// LWMissionDB tLWMissionDB = new LWMissionDB();
		// //查出此核保师所拥有的所有待核保工作
		// String tSql = "select * from lwmission where 1=1 "
		// + " and activityid in ('0000001100') " //将来会加入续保、保全核保等的工作流节点
		// + " and defaultoperator = '" + mOperator + "'"
		// ;
		// logger.debug("Sql="+tSql);
		// tLWMissionSet = tLWMissionDB.executeQuery(tSql);
		// if (tLWMissionSet.size() > mApplyNo)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "UWApplyBL";
		// tError.functionName = "checkData";
		// tError.errorMessage = "您的工作池中已有"+mApplyNo+"个待核保保单，不能申请！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mNo = mApplyNo - tLWMissionSet.size(); //还需要申请的工作数
		mNo = mApplyNo; // mod by heyq 20040206 不控制工作任务数
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mNewLWMissionSet, "UPDATE");

		mResult.add(map);

		return true;
	}

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
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
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mManageCom = mGlobalInput.Operator;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tNo = (String) mTransferData.getValueByName("ApplyNo");
		if (tNo == null || tNo.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ApplyNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mApplyNo = Integer.parseInt(tNo);

		mApplyType = (String) mTransferData.getValueByName("ApplyType");
		if (mApplyType == null || mApplyType.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ApplyType失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

}
