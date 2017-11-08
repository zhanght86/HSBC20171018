package com.sinosoft.workflow.proddef;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.workflow.proddef.ProdDefWorkFlowBL;

public class PDPrepAndDealWFBL implements BusinessService{
private static Logger logger = Logger.getLogger(PDPrepAndDealWFBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	// private VData mInputData = new VData();
	/** 全局数据 */
	// private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	// private MMap map = new MMap();
	// private boolean mHaveIssuesTo00 = false;
	// 發送問題件到定義崗標誌
	private boolean mHaveIssuesFlag = false;

	private boolean mSendIssueFlag = false;

	public PDPrepAndDealWFBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 只是查询是否发起了到基础信息岗的问题件
		// if (!QueryIssuesTo00(cInputData)) {
		// return false;
		// }

		// 查询是否向基础信息岗或契约定义岗或保全定义岗或理赔定义岗的问题件
		if (!QueryIssues(cInputData)) {
			return false;
		}

		// 定义完毕标志
		boolean tDefFinishFlag = check(cInputData, cOperate);
		if(!tDefFinishFlag){
			return false;
		}

		// 如果基础信息定义岗所有问题件已处理完毕，则对契约、保全、理赔定义岗置录入完毕标志
		if (!this.mHaveIssuesFlag || tDefFinishFlag) {

			if (!alterMissionProp(cInputData, cOperate)) {
				return false;
			}
		}

		if (!dealWorkflow(cInputData, cOperate)) {
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * 处理工作流信息
	 * 
	 * @desc mHaveIssuesFlag为true,发送问题件后，流程向前回退
	 * @desc isNeedCreateNextNode为true，问题件处理完毕后，流程向后
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	private boolean dealWorkflow(VData cInputData, String cOperate) {
		try {
			// if (mHaveIssuesTo00 || isNeedCreateNextNode(cInputData)) {
			if ((mHaveIssuesFlag & mSendIssueFlag)
					|| isNeedCreateNextNode(cInputData)) {
				ProdDefWorkFlowBL tProdDefWorkFlowBL = new ProdDefWorkFlowBL();
				if (!tProdDefWorkFlowBL.submitData(cInputData, cOperate)) {
					this.mErrors.addOneError(tProdDefWorkFlowBL.mErrors
							.getFirstError());
					return false;
				}
			}

			return true;

		} catch (Exception ex) {
			this.mErrors.addOneError("工作流处理错误：" + ex.getMessage());
			return false;
		}
	}

	/***************************************************************************
	 * 是否需要创建下一个节点
	 * 
	 * @desc 无问题件，流程向后
	 * @param cInputData
	 * @return
	 */
	private boolean isNeedCreateNextNode(VData cInputData) {

		String tRiskCode = (String) ((TransferData) cInputData
				.getObjectByObjectName("TransferData", 0))
				.getValueByName("RiskCode");

		String allBackPostSQL = "select Distinct othersign from ldcode where codetype = 'pdEnabledNode' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(allBackPostSQL);
		SSRS ssrs = new SSRS();
		ssrs = new ExeSQL().execSQL(sqlbv1);
		String allBackPosts = "";
		if (ssrs != null) {
			for (int i = 0; i < ssrs.MaxRow; i++) {
				allBackPosts += "'" + ssrs.GetText(i + 1, 1) + "',";
			}

			allBackPosts = allBackPosts.substring(0, allBackPosts.length() - 1);
		}

		String sql = "select 1 from PD_Issue where ISSUESTATE in ('0', '1') and backpost in ("
				+ "?allBackPosts?" + ") and riskcode = '" + "?tRiskCode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("allBackPosts", allBackPosts);
		sqlbv2.put("tRiskCode", tRiskCode);
		if (new ExeSQL().getOneValue(sqlbv2).equals("1")) {
			logger.debug("有未处理完毕的问题件，不创建下一个节点");
			return false;
		}

		// 各节点都进行了确认
		TransferData tData = new TransferData();
		tData = (TransferData) cInputData.getObjectByObjectName("TransferData",
				0);
		ExeSQL exec = new ExeSQL();

		String select = "select sysvarvalue from ldsysvar where Sysvar = 'pdworkflow01cond'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(select);
		String wherePart = exec.getOneValue(sqlbv3);

		sql = "select 1 from lwmission where " + wherePart
				+ "and missionid = '"
				+ "?missionid?" + "' "
				+ "and submissionid = '"
				+ "?submissionid?" + "' "
				+ "and activityid = '"
				+ "?activityid?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("missionid", ((String) tData.getValueByName("MissionID")).trim());
		sqlbv4.put("submissionid", ((String) tData.getValueByName("SubMissionID")).trim());
		sqlbv4.put("activityid", ((String) tData.getValueByName("ActivityID")).trim());
		if (exec.getOneValue(sqlbv4).equals("1")) {
			return true;
		}

		return false;
	}

	/***************************************************************************
	 * 点击【契约信息录入完毕】时，修改missionprop5的值为1，表示‘契约信息定义完毕’；
	 * 点击【理赔信息录入完毕】时，修改missionprop6的值为1，表示‘理赔信息定义完毕’；
	 * 点击【保全信息录入完毕】时，修改missionprop7的值为1，表示‘保全信息定义完毕’；
	 * 点击【保监会报表定义完毕】时，修改missionprop8的值为1，表示‘保监会信息定义完毕’；
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	private boolean alterMissionProp(VData cInputData, String cOperate) {
		try {
			TransferData tData = new TransferData();
			tData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);

			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema.setMissionID((String) tData
					.getValueByName("MissionID"));
			tLWMissionSchema.setSubMissionID((String) tData
					.getValueByName("SubMissionID"));
			tLWMissionSchema.setActivityID((String) tData
					.getValueByName("ActivityID"));

			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setSchema(tLWMissionSchema);
			if (!tLWMissionDB.getInfo()) {
				this.mErrors.addOneError("查询"
						+ (String) tData.getValueByName("RiskNo") + "的信息失败");
				return false;
			}

			LWMissionSchema oldLWMissionSchema = tLWMissionDB.getSchema();

			StringBuffer sb = new StringBuffer();
			sb.append("select codealias,comcode from ldcode where codetype = 'pdEnabledNode' and codename = '");
			sb.append("?cOperate?");
			sb.append("'");
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(sb.toString());
			sqlbv5.put("cOperate", cOperate);
			ExeSQL exec = new ExeSQL();
			SSRS ssrs = exec.execSQL(sqlbv5);

			if (ssrs != null) {
				oldLWMissionSchema.setV(ssrs.GetText(1, 1), ssrs.GetText(1, 2));
			} else {
				return true;
			}

			oldLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			oldLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

			// 分别提交，不影响

			tLWMissionDB.setSchema(oldLWMissionSchema);
			tLWMissionDB.update();

			return true;
		} catch (Exception ex) {
			this.mErrors.addOneError("PDContDefiEntryBL.alterMissionProp处理失败:"
					+ ex.getMessage());
			return false;
		}
	}

	// private boolean QueryIssuesTo00(VData cInputData) {
	// TransferData tData = new TransferData();
	// tData = (TransferData) cInputData.getObjectByObjectName("TransferData",
	// 0);
	//
	// LWMissionSchema tLWMissionSchema = new LWMissionSchema();
	// tLWMissionSchema.setMissionID((String) tData
	// .getValueByName("MissionID"));
	// tLWMissionSchema.setSubMissionID((String) tData
	// .getValueByName("SubMissionID"));
	// tLWMissionSchema.setActivityID((String) tData
	// .getValueByName("ActivityID"));
	//
	// LWMissionDB tLWMissionDB = new LWMissionDB();
	// tLWMissionDB.setSchema(tLWMissionSchema);
	// if (!tLWMissionDB.getInfo()) {
	// this.mErrors.addOneError("查询险种"
	// + (String) tData.getValueByName("RiskCode") + "的工作流信息失败");
	// return false;
	// }
	//
	// String strSQL = "select 1 from dual where (select count(1) from PD_Issue
	// where ISSUESTATE in ('0','1') and backpost = '00' and riskcode = '"
	// + tLWMissionDB.getMissionProp2() + "')>0";
	// if (new ExeSQL().execSQL(strSQL).getMaxRow() > 0) {
	// mHaveIssuesTo00 = true;
	// }
	//
	// return true;
	// }

	/**
	 * @author NicolE
	 * @desc 查询返回到基础信息定义，或契约、保全、理赔信息定义阶段
	 * @param cInputData
	 * @return
	 */
	private boolean QueryIssues(VData cInputData) {
		TransferData tData = new TransferData();
		tData = (TransferData) cInputData.getObjectByObjectName("TransferData",
				0);

		// 是否是在执行问题件的发送动作
		String tNeedTransferFlag = (String) ((TransferData) cInputData
				.getObjectByObjectName("TransferData", 0))
				.getValueByName("NeedTransferFlag");
		if (tNeedTransferFlag != null && tNeedTransferFlag.equals("true")) {
			mSendIssueFlag = true;
		} else {
			mSendIssueFlag = false;
		}

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		tLWMissionSchema.setMissionID((String) tData
				.getValueByName("MissionID"));
		tLWMissionSchema.setSubMissionID((String) tData
				.getValueByName("SubMissionID"));
		tLWMissionSchema.setActivityID((String) tData
				.getValueByName("ActivityID"));

		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setSchema(tLWMissionSchema);
		if (!tLWMissionDB.getInfo()) {
			this.mErrors.addOneError("查询险种"
					+ (String) tData.getValueByName("RiskCode") + "的工作流信息失败");
			return false;
		}

		// 所有定义岗是否存在未处理的问题件
		String strSQL = "select 1 from dual where (select count(1) from PD_Issue where ISSUESTATE in ('0','1') and backpost in ('00','10','11','12') and riskcode = '"
				+ "?riskcode?" + "') > 0";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(strSQL);
		sqlbv6.put("riskcode", tLWMissionDB.getMissionProp2());
		if (new ExeSQL().execSQL(sqlbv6).getMaxRow() > 0) {
			mHaveIssuesFlag = true;
		}

		return true;
	}

	private boolean check(VData cInputData, String cOperate) {

		// 对当前岗位的问题件状态进行查询，如果有未处理的，则不能置录入完毕标志
		StringBuffer sb = new StringBuffer();
		sb.append("select othersign from ldcode where codetype = 'pdEnabledNode' and codename = '");
		sb.append("?cOperate?");
		sb.append("'");
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(sb.toString());
		sqlbv7.put("cOperate", cOperate);
		ExeSQL exec = new ExeSQL();
		String backpost = exec.getOneValue(sqlbv7);

		if (backpost.equals("")) {
			this.mErrors.addOneError("校验失败!");
			logger.debug("Error Point: PDPrepAndDealWFBL.check");
			return false;
		}

		String tRiskCode = (String) ((TransferData) cInputData
				.getObjectByObjectName("TransferData", 0))
				.getValueByName("RiskCode");

		String sql = "select 1 from PD_Issue where ISSUESTATE in ('0', '1') and backpost = '"
				+ "?backpost?" + "' and riskcode = '" + "?tRiskCode?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("tRiskCode", tRiskCode);
		sqlbv8.put("backpost", backpost);
		if (new ExeSQL().getOneValue(sqlbv8).equals("1")) {
			this.mErrors.addOneError("有未处理完毕的问题件，请处理完毕后再执行此操作");
			return false;
		}

		return true;
	}
	
	  public CErrors getErrors() {
			// TODO Auto-generated method stub
			return mErrors;
		}

		public VData getResult() {
			// TODO Auto-generated method stub
			return this.mResult;
		}

	public static void main(String[] args) {
		ExeSQL exec = new ExeSQL();

		String select = "select transitioncond from LWProcessInstance  where transitionid = 'pd00001101' and processid = 'pd00000011' and transitionstart = 'pd00000001' and transitionend = 'pd00000002'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(select);
		String getWhereDualStr = exec.getOneValue(sqlbv9);
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(getWhereDualStr);
		String wherePart = exec.getOneValue(sqlbva);
		int thePoint = wherePart.lastIndexOf("((");
		wherePart = wherePart.substring(thePoint, wherePart.length() - 1);
	}
}
