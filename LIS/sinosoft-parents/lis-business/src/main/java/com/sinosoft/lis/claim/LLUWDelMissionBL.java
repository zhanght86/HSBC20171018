package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLUWPENoticeDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLUWDelMissionBL {
private static Logger logger = Logger.getLogger(LLUWDelMissionBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private TransferData mTransferData = new TransferData();

	private String mUWFlag = ""; // 核保通过标志
	private String mMissionID;
	/** 核保批次号*/
	private String mBatNo;
	/** 赔案号*/
	private String mClmNo;
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public static String mEDORAPP_UWPASS = "9"; // 保全申请人工核保通过 ？暂不确定编码？
	public static String mEDORAPP_UWSTOP = "1"; // 保全申请人工核不保通过？暂不确定编码？

	public LLUWDelMissionBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		//boolean tFlag=false;
		//if(tFlag){
		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		//}
		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		if(!createMission()){
			return false;
		}
		if(!updateMission()){
			return false;
		}
		//将二核的状态置为2
		String selMission = " select * from lwmission where activityid ="
				+ " '0000005505' and missionid = '" + "?missionid?"
				+ "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(selMission);
		sqlbv.put("missionid", mMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet delLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if (tLWMissionDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (delLWMissionSet == null || delLWMissionSet.size() < 1) {
			return true;
		}
		LBMissionSet insertLBMissionSet = new LBMissionSet();
		LBMissionSchema tLBMissionSchema;
		for (int i = 1; i <= delLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = delLWMissionSet.get(i);
			delLWMissionSet.get(i).setActivityStatus("2");
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			tLBMissionSchema = new LBMissionSchema();
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLBMissionSchema, tLWMissionSchema);

			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("4"); // 节点任务执行强制移除
			if (tLBMissionSchema.getActivityID().equals("0000005505")) {
				tLBMissionSchema.setActivityStatus("2"); // 二核节点正常执行完成
			}
			tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
			tLBMissionSchema.setOutDate(mCurrentDate);
			tLBMissionSchema.setOutTime(mCurrentTime);
			insertLBMissionSet.add(tLBMissionSchema);
		}

		map.put(delLWMissionSet, "UPDATE");

		map.put(insertLBMissionSet, "INSERT");

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if(mMissionID==null||"".equals(mMissionID)){
			CError.buildErr(this, "传入MissionID失败！");
			return false;
		}
		
		mBatNo = (String) mTransferData.getValueByName("BatNo");
		if(mBatNo==null||"".equals(mBatNo)){
			CError.buildErr(this, "传入BatNo失败！");
			return false;
		}
		mClmNo = (String) mTransferData.getValueByName("CaseNo");
		if(mClmNo==null||"".equals(mClmNo)){
			CError.buildErr(this, "传入ClmNo失败！");
			return false;
		}
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if(mUWFlag==null||"".equals(mUWFlag)){
			CError.buildErr(this, "传入UWFlag失败！");
			return false;
		}
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}
	/**
	 * 创建发送理赔核保通知书的节点
	 * */
	private boolean createMission(){
		//根据此次赔案下的合同数去创建发通知书的节点  每张合同创建两个节点
		//LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		String tSql = "	select contno from llcuwbatch where caseno='"+"?clmno?"+"' and batno='"+"?batno?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("clmno", mClmNo);
		sqlbv1.put("batno", mBatNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		int k=1;
		for (int i=1;i<=tSSRS.MaxRow;i++){
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tSSRS.GetText(i, 1));
			if(!tLCContDB.getInfo()){
				CError.buildErr(this, "查询合同信息失败！");
				return false;
			}
			String tSentUWAppntSql="insert into lwmission (missionid,submissionid,processid,activityid,"
							+ "activitystatus,missionprop1,missionprop2,missionprop3,missionprop15,"
							+" missionprop16,createoperator,makedate,maketime,modifydate,modifytime) "
							+" values "
							+"('"+"?missionid?"+"', '"+ "?submissionid?" +"', '0000000005', '0000005510', '1', '"+"?clmno?"
							+"', '"+"?misprop2?"+"', '"+"?batno?"+"', '"+"?appntno?"+"', 'A',"
							+"'"+"?operator?"+"', '"+"?curdate?"+"', '"+"?curtime?"
							+"', '"+"?curdate?"+"','"+"?curtime?"+"')";
			String tSentUWInsSql="insert into lwmission (missionid,submissionid,processid,activityid,"
							+ "activitystatus,missionprop1,missionprop2,missionprop3,missionprop15,"
							+" missionprop16,createoperator,makedate,maketime,modifydate,modifytime) "
							+" values "
							+"('"+"?missionid?"+"', '"+ "?submissionid?" +"', '0000000005', '0000005510', '1', '"+"?clmno?"
							+"', '"+"?misprop2?"+"', '"+"?batno?"+"', '"+"?insuredno?"+"', 'I',"
							+"'"+"?operator?"+"', '"+"?curdate?"+"', '"+"?curtime?"
							+"', '"+"?curdate?"+"','"+"?curtime?"+"')";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSentUWAppntSql);
			sqlbv2.put("missionid", mMissionID);
			sqlbv2.put("submissionid", k++);
			sqlbv2.put("clmno", mClmNo);
			sqlbv2.put("misprop2", tSSRS.GetText(1, i));
			sqlbv2.put("batno", mBatNo);
			sqlbv2.put("appntno", tLCContDB.getAppntNo());
			sqlbv2.put("operator", mGlobalInput.Operator);
			sqlbv2.put("curdate", PubFun.getCurrentDate());
			sqlbv2.put("curtime", PubFun.getCurrentTime());
			map.put(sqlbv2, "INSERT");
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSentUWInsSql);
			sqlbv3.put("missionid", mMissionID);
			sqlbv3.put("submissionid", k++);
			sqlbv3.put("clmno", mClmNo);
			sqlbv3.put("misprop2", tSSRS.GetText(1, i));
			sqlbv3.put("batno", mBatNo);
			sqlbv3.put("insuredno", tLCContDB.getInsuredNo());
			sqlbv3.put("operator", mGlobalInput.Operator);
			sqlbv3.put("curdate", PubFun.getCurrentDate());
			sqlbv3.put("curtime", PubFun.getCurrentTime());
			map.put(sqlbv3, "INSERT");
		}
		
		return true;
	}
	
	/**
	 * 修改二核节点的状态为2
	 * */
	private boolean updateMission(){
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		return true;
	}
}
