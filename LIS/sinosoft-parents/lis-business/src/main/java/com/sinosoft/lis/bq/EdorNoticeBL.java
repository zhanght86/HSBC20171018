package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class EdorNoticeBL implements com.sinosoft.service.BusinessService{
private static Logger logger = Logger.getLogger(EdorNoticeBL.class);

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
	private TransferData mTransferData = new TransferData();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	/** 传输数据 */
	private String mApproveContent;
	private String mApproveFlag;
	private String mEdorAcceptNo;
	private String mMissionID;
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();


	public EdorNoticeBL() {
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

		logger.debug("after prepareOutputData...");
		mInputData.add(map);
		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			CError.buildErr(this, "保全逾期终止数据提交失败");
			return false;
		}
		return true;
	}

	private boolean getLPEdorMain() {
		// 查询保全申请下所有批单
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
		mLPEdorMainSet.set(tLPEdorMainDB.query());
		if (tLPEdorMainDB.mErrors.needDealError()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorNoticeBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保全申请批单查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}

		if (mLPEdorMainSet == null || mLPEdorMainSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "EdorNoticeBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保全申请下没有申请批单!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}


	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		if (!getLPEdorApp()) {
			return false;
		}



		String sEdorState = "";
		if (mApproveFlag.equals("1")) // 保全通过,置批改状态为[审核通过]
		{
			if (!InsertPassPRT()) {
				CError.buildErr(this, "插入保全审批通知书失败!");
				return false;
			}
		}else if(mApproveFlag.equals("2")) // 保全拒绝,置批改状态为[审核通过]
		{
			sEdorState="c";
			
			// 删除工作流保全撤销节点
			if (!delCancelMission()) {
				return false;
			}
			if (!InsertPRT()) {
				CError.buildErr(this, "插入保全终止通知书失败!");
				return false;
			}
				// 保单解挂
				BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
				if (!tContHangUpBL.hangUpEdorAccept(mEdorAcceptNo, "0")) {
					CError.buildErr(this, "保全保单解挂失败! ");
					return false;
				} else {
					MMap tMap = tContHangUpBL.getMMap();
					map.add(tMap); // del because DB hs not update zhangtao
									// 2005-08-02
				}

				// ===add===zhangtao===2007-03-29====删除C表数据====BGN==============
				PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(mGlobalInput);
				map.add(tPEdorOverDueBL.delLCPol(mEdorAcceptNo));
				map.add(tPEdorOverDueBL.delFinFee(mEdorAcceptNo));
				// ===add===zhangtao===2007-03-29====删除C表数据====END==============
						
			
			StringBuffer sbSQL = new StringBuffer();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sbSQL.append(" UPDATE LPEdorApp SET ")
			.append("EdorState = '")
			.append("?sEdorState?")
			// .append("' ,Operator = '")
			// .append(mGlobalInput.Operator)
			.append("' ,ModifyDate = '").append("?mCurrentDate?").append(
					"' ,ModifyTime = '").append("?mCurrentTime?").append(
					"'")
			.append(" WHERE EDORACCEPTNO = '").append("?mEdorAcceptNo?")
			.append("'");
			sqlbv.sql(sbSQL.toString());
			sqlbv.put("sEdorState", sEdorState);
			sqlbv.put("mCurrentDate", mCurrentDate);
			sqlbv.put("mCurrentTime", mCurrentTime);
			sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
	       map.put(sqlbv, "UPDATE");


//	 更新保全批改表
	sbSQL.setLength(0);
	sbSQL.append(" UPDATE LPEdorMain SET ")
			.append("EdorState = '")
			.append("?sEdorState?")
			.append("' ,ModifyDate = '").append("?mCurrentDate?").append(
					"' ,ModifyTime = '").append("?mCurrentTime?").append("'")
			.append(" WHERE EDORACCEPTNO = '").append("?mEdorAcceptNo?")
			.append("'");
	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	sqlbv1.sql(sbSQL.toString());
	sqlbv1.put("sEdorState", sEdorState);
	sqlbv1.put("mCurrentDate", mCurrentDate);
	sqlbv1.put("mCurrentTime", mCurrentTime);
	sqlbv1.put("mEdorAcceptNo", mEdorAcceptNo);
	map.put(sqlbv1, "UPDATE");
 

//	 更新保全项目表
	sbSQL.setLength(0);
	sbSQL.append(" UPDATE LPEdorItem SET ")
			.append("EdorState = '")
			.append("?sEdorState?")
			// .append("' ,Operator = '")
			// .append(mGlobalInput.Operator)
			.append("' ,ModifyDate = '").append("?mCurrentDate?").append(
					"' ,ModifyTime = '").append("?mCurrentTime?").append("'")
			.append(" WHERE EDORACCEPTNO = '").append("?mEdorAcceptNo?")
			.append("'");
	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	sqlbv2.sql(sbSQL.toString());
	sqlbv2.put("sEdorState", sEdorState);
	sqlbv2.put("mCurrentDate", mCurrentDate);
	sqlbv2.put("mCurrentTime", mCurrentTime);
	sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
	map.put(sqlbv2, "UPDATE");
 
		}
		
		return true;
	}

	/**
	 * 校验保全受理是否已经保全确认
	 * 
	 * @return: boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		// 获得保全受理号
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mEdorAcceptNo == null || mEdorAcceptNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorNoticeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得审核结论
		mApproveFlag = (String) mTransferData.getValueByName("ApproveFlag");
		if (mApproveFlag == null || mApproveFlag.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorNoticeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ApproveFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mApproveContent = (String) mTransferData.getValueByName("ApproveContent");
		if (mApproveContent == null || mApproveContent.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorNoticeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ApproveFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得任务号
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorNoticeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}



	/**
	 * 往打印管理表插入个险审批通知书
	 * 
	 * @return boolean
	 */
	private boolean InsertPassPRT() {
		LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSchema mmLOPRTManagerSchema;
		try {
			String Code = PrintManagerBL.CODE_PEdorApproveInfo;// 个险保全审批通知书

			mmLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setOtherNo(mLPEdorAppSchema.getOtherNo());
			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mmLOPRTManagerSchema.setCode(Code); // 打印类型
			mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom()); // 管理机构
			mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			mmLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mmLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setStandbyFlag1(mLPEdorAppSchema.getEdorAcceptNo());//受理号
			mmLOPRTManagerSchema.setStandbyFlag2(mApproveContent);//审批批注
			mmLOPRTManagerSchema.setStandbyFlag3(mLPEdorAppSchema.getOperator());//操作员
			mmLOPRTManagerSchema.setStandbyFlag4(mLPEdorAppSchema.getEdorAppName());//申请人
			mmLOPRTManagerSchema.setStandbyFlag5(mLPEdorAppSchema.getEdorAppDate());//申请时间
			mmLOPRTManagerSchema.setStandbyFlag6(mLPEdorAppSchema.getApproveOperator());//审核人
			mmLOPRTManagerSchema.setStandbyFlag7(mLPEdorAppSchema.getApproveDate());//审核时间
			mmLOPRTManagerSchema.setRemark("");//保留强制核销的原因
			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
		} catch (Exception e) {
			CError.buildErr(this, "插入保全审核通知书失败!");
			return false;
		}

		return true;
	}


	private boolean delCancelMission() {
	
		//删除保全撤销，无扫描申请，有扫描申请，保全申请确认节点
		String sql = " select * from lwmission a, lwactivity b where a.activityid = b.activityid and functionId in ('10020006','10020002','10020001') "
				+ " and missionid = '" + "?mMissionID?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mMissionID", mMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if (tLWMissionDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全撤销申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() < 1) {
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全撤销申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = tLWMissionSet.get(i);
			ActivityOperator tActivityOperator = new ActivityOperator();
			//begin zhangbx  20110511 
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("BusiOutDate", mCurrentDate);
			tTransferData.setNameAndValue("BusiOutTime", mCurrentTime);
			VData tVData=new VData();
			tVData.add(tTransferData);
			if (!tActivityOperator.DeleteMission(tLWMissionSchema
					.getMissionID(), tLWMissionSchema.getSubMissionID(),
					tLWMissionSchema.getActivityID(), tVData)) {
				CError tError = new CError();
				tError.errorMessage = "工作流保全撤销节点删除失败!";
				mErrors.addOneError(tError);
				return false;
			}
			//end zhangbx  20110511 
			VData tempVData = tActivityOperator.getResult();
			if ((tempVData != null) && (tempVData.size() > 0)) {
				MMap tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
		}
		return true;
	}

	/**
	 * 往打印管理表插入个险保全拒绝通知书
	 * 
	 * @return boolean
	 */
	private boolean InsertPRT() {
		LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSchema mmLOPRTManagerSchema;
		try {
			String Code = PrintManagerBL.CODE_PEdorInvaliInfo;// 个险保全拒绝通知书

			mmLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setOtherNo(mLPEdorAppSchema.getOtherNo());
			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mmLOPRTManagerSchema.setCode(Code); // 打印类型
			mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom()); // 管理机构
			mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			mmLOPRTManagerSchema.setMakeDate(mCurrentDate);
			mmLOPRTManagerSchema.setMakeTime(mCurrentTime);
			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setStandbyFlag1(mEdorAcceptNo);//受理号
			mmLOPRTManagerSchema.setStandbyFlag2(mApproveContent);//拒绝原因
			mmLOPRTManagerSchema.setStandbyFlag3(mLPEdorAppSchema.getOperator());//操作员
			mmLOPRTManagerSchema.setStandbyFlag4(mLPEdorAppSchema.getEdorAppName());//申请人
			mmLOPRTManagerSchema.setStandbyFlag5(mLPEdorAppSchema.getEdorAppDate());//申请时间
			mmLOPRTManagerSchema.setStandbyFlag6(mLPEdorAppSchema.getOperator());//审核人
			mmLOPRTManagerSchema.setStandbyFlag7(mCurrentDate);//审核时间
			mmLOPRTManagerSchema.setRemark("");//保留强制核销的原因
			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
		} catch (Exception e) {
			CError.buildErr(this, "插入保全拒绝通知书失败!");
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
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120070319000021");
		tTransferData.setNameAndValue("ApproveFlag", "1");
		tTransferData.setNameAndValue("ApproveContent", "zhangtao test");
		tTransferData.setNameAndValue("MissionID", "00000000000002891264");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		EdorNoticeBL tEdorNoticeBL = new EdorNoticeBL();

		if (!tEdorNoticeBL.submitData(tVData, "")) {
			// logger.debug(tEdorNoticeBL.mErrors.getError(0).errorMessage);
		}

	}



}
