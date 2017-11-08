/*
 * @(#)PEdorAccepManuUWBL.java	2005-06-02
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPAppUWMasterMainDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPAppUWMasterMainSchema;
import com.sinosoft.lis.schema.LPAppUWSubMainSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPAppUWMasterMainSet;
import com.sinosoft.lis.vschema.LPAppUWSubMainSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全人工核保-保全申请层处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-02
 */
public class PEdorAccepManuUWBL {
private static Logger logger = Logger.getLogger(PEdorAccepManuUWBL.class);
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
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();

	private String mUWFlag = ""; // 核保通过标志
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDate = ""; // 延迟时间
	private String mUWIdea = ""; // 核保意见	
	private String mUpReportContent = ""; // 上报内容
	private String mMissionID = ""; // 上报内容
	private String mUWPrtSeq = "";//核保通知书的通知书流水号
	private String mEdorNo = "";
	private String mEdorType = "";
	private String mContNo = "";
	
	private String testAflag = "";//是否执行本类判断条件

	// 保全申请核保记录
	private LPAppUWMasterMainSet mLPAppUWMasterMainSet = new LPAppUWMasterMainSet();
	private LPAppUWSubMainSet mLPAppUWSubMainSet = new LPAppUWSubMainSet();
	// 打印信息
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	public static String mEDORAPP_UWPASS = "9"; // 保全申请人工核保通过 ？暂不确定编码？
	public static String mEDORAPP_UWSTOP = "1"; // 保全申请人工核不保通过？暂不确定编码？
	public static String mEDORAPP_UWSTOP1 = "3"; // 保全申请人工核不保通过？拒保

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAccepManuUWBL() {
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
		
		if(testAflag=="2"){
			// 校验
			if (!checkData()) {
				return false;
			}
			logger.debug("after checkData...");

			// 数据操作业务处理
			if (!dealData()) {
				return false;
			}

			logger.debug("after dealData...");

			// 准备提交后台的数据
			if (!prepareOutputData()) {
				return false;
			}

			logger.debug("after prepareOutputData...");

			 
		}
		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		mLPEdorAppSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setUWState(mUWFlag);
		mLPEdorAppSchema.setUWDate(mCurrentDate);
		mLPEdorAppSchema.setUWTime(mCurrentTime);
		// mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setModifyDate(mCurrentDate);
		mLPEdorAppSchema.setModifyTime(mCurrentTime);

		// 生成保全申请核保主表信息
		LPAppUWMasterMainSchema tLPAppUWMasterMainSchema = prepareEdorAppUWMasterMain(mLPEdorAppSchema);
		if (tLPAppUWMasterMainSchema != null) {
			mLPAppUWMasterMainSet.add(tLPAppUWMasterMainSchema);
		}

		// 生成保全申请核保轨迹表信息
		LPAppUWSubMainSchema tLPAppUWSubMainSchema = prepareEdorAppUWSubMain(tLPAppUWMasterMainSchema);
		if (tLPAppUWSubMainSchema != null) {
			mLPAppUWSubMainSet.add(tLPAppUWSubMainSchema);
		}

		// ====DEL=====zhangtao=====2005-06-03======================BGN=================
		// /**生成打印*/
		 LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		 tLPEdorItemDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		 tLPEdorItemDB.setEdorNo(mEdorNo);
		 tLPEdorItemDB.setEdorType(mEdorType);
		 LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		 LPContSchema tLPContSchema = new LPContSchema();
		 for (int j = 1; j <= tLPEdorItemSet.size(); j++){
			 if (!tLPEdorItemSet.get(j).getContNo().equals(tLPContSchema.getContNo())){
				 LPContDB tLPContDB = new LPContDB();
				 tLPContDB.setEdorNo(mEdorNo);
				 tLPContDB.setEdorType(mEdorType);
				 tLPContDB.setContNo(tLPEdorItemSet.get(j).getContNo());
				 if (!tLPContDB.getInfo()){
					 mErrors.copyAllErrors(tLPContDB.mErrors);
					 CError.buildErr(this, "保单信息查询失败") ;
					 return false;
				 }
				 tLPContSchema.setSchema(tLPContDB.getSchema());
			 }
			 if (!preparePrint(tLPEdorItemSet.get(j), tLPContSchema)){
				 CError.buildErr(this, "准备核保打印数据失败") ;
				 return false;
			 }
		 }
		// /**生成打印*/
		// ====DEL=====zhangtao=====2005-06-03======================END=================
		map.put(mLPEdorAppSchema, "UPDATE");
		map.put(mLPAppUWMasterMainSet, "DELETE&INSERT");
		map.put(mLPAppUWSubMainSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");

		String sOtherNoType = mLPEdorAppSchema.getOtherNoType();
		String sEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();

		// （团体）根据险种核保结论自动给保单置核保结论 add by zhangtao 2006-11-4
		if (sOtherNoType.equals("4")) {
			if (!getLPEdorMain()) {
				return false;
			}

			for (int i = 1; i <= mLPGrpEdorMainSet.size(); i++) {
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("UWFlag", mUWFlag);
				tTransferData.setNameAndValue("UWIdea", mUWIdea);
				tTransferData.setNameAndValue("PostponeDate", mPostponeDate);
				tTransferData.setNameAndValue("NoSubmit", "1");

				VData tVData = new VData();
				tVData.add(mGlobalInput);
				tVData.add(tTransferData);
				tVData.add(mLPGrpEdorMainSet.get(i));

				GEdorGrpContManuUWBL tGEdorGrpContManuUWBL = new GEdorGrpContManuUWBL();

				if (!tGEdorGrpContManuUWBL.submitData(tVData, "")) {
					mErrors.copyAllErrors(tGEdorGrpContManuUWBL.mErrors);
					return false;
				}

				map.add((MMap) tGEdorGrpContManuUWBL.getResult()
						.getObjectByObjectName("MMap", 0));
			}

			String sContUWFlag = "9";
			LPEdorMainSchema tLPEdorMainSchema;
			for (int i = 1; i <= mLPEdorMainSet.size(); i++) {
				tLPEdorMainSchema = mLPEdorMainSet.get(i);
				String sql = " select sum(case uwflag when '1' then 1 when '9' then -1 else 0 end ), count(*) from lppol where "
						+ " edorno = '"
						+ "?edorno?"
						+ "' and contno = '"
						+ "?contno?"
						+ "'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("edorno", tLPEdorMainSchema.getEdorNo());
				sqlbv.put("contno", tLPEdorMainSchema.getContNo());
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(sqlbv);
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "险种核保结论查询失败! ");
					return false;
				}
				int iCount = 0;
				int iSum = 0;
				try {
					iCount = Integer.parseInt(tSSRS.GetText(1, 2));
					iSum = Integer.parseInt(tSSRS.GetText(1, 1));
				} catch (Exception ex) {
					iCount = 0;
					iSum = 0;
				}

				if (iCount == 0) {
					sContUWFlag = "9";
				} else if (iCount == iSum) // 全部为1-拒保
				{
					sContUWFlag = "1";
				} else if (iCount == -iSum) // 全部为9-标准
				{
					sContUWFlag = "9";
				} else {
					sContUWFlag = "4";
				}

				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("UWFlag", sContUWFlag);
				tTransferData.setNameAndValue("UWIdea", mUWIdea);
				tTransferData.setNameAndValue("PostponeDate", mPostponeDate);
				tTransferData.setNameAndValue("NoSubmit", "1");

				VData tVData = new VData();
				tVData.add(mGlobalInput);
				tVData.add(tTransferData);
				tVData.add(mLPEdorMainSet.get(i));

				PEdorContManuUWBL tPEdorContManuUWBL = new PEdorContManuUWBL();

				if (!tPEdorContManuUWBL.submitData(tVData, "")) {
					mErrors.copyAllErrors(tPEdorContManuUWBL.mErrors);
					return false;
				}

				map.add((MMap) tPEdorContManuUWBL.getResult()
						.getObjectByObjectName("MMap", 0));

			}
		}

		// 疑问：客户号申请的保全受理呢？ 怎么处理？ zhangtao 2005-08-03
		// 判断如果是个人保单申请[3]，并且人工核保不通过，则直接置保全受理为核保终止[8]
		if ((mUWFlag.equals(mEDORAPP_UWSTOP) || mUWFlag.equals(mEDORAPP_UWSTOP1))
				&& (sOtherNoType.equals("3") || sOtherNoType.equals("4"))) {
			
			//不同意，不可强制核保，置上保全状态核保终止，保全确认里点强制终止
			if (mUWFlag != null && mUWFlag.equals(mEDORAPP_UWSTOP)) {
				updEdorState(sEdorAcceptNo, "8");
			}
			
			//add by jiaqiangli 2009-03-24 保全核保结论

//			// 保单解挂
//			BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
//			if (!tContHangUpBL.hangUpEdorAccept(sEdorAcceptNo, "0")) {
//				CError.buildErr(this, "保全保单解挂失败! ");
//				return false;
//			} else {
//				MMap tMap = tContHangUpBL.getMMap();
//				map.add(tMap); // del because DB hs not update zhangtao
//				// 2005-08-02
//			}
//
//			// ===add===zhangtao===2007-03-29====删除C表数据====BGN==============
//			PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(mGlobalInput);
//			map.add(tPEdorOverDueBL.delLCPol(sEdorAcceptNo));
//			// ===add===zhangtao===2007-03-29====删除C表数据====END==============
		}
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

		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		//核保完成2  发送核保通知书按钮1
		testAflag = (String)mTransferData.getValueByName("testAflag");
		if(testAflag=="2"){
			mUWFlag = (String) mTransferData.getValueByName("UWFlag");
			mUWIdea = (String) mTransferData.getValueByName("UWIdea");		
			mMissionID = (String) mTransferData.getValueByName("MissionID");
			mPostponeDate = (String) mTransferData.getValueByName("PostponeDate");
			mEdorNo = (String) mTransferData.getValueByName("EdorNo");
			mEdorType= (String) mTransferData.getValueByName("EdorType");
			mContNo= (String) mTransferData.getValueByName("ContNo");
			
			if (mUWFlag == null || mUWFlag.trim().equals("")) {
				CError tError = new CError();
				tError.errorMessage = "前台传输数据UWFlag失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			if (mEdorNo == null || "".equals(mEdorNo.trim())) {
				CError.buildErr(this, "前台传入EdorNo失败！");
				return false;
			}
			if (mEdorType == null || "".equals(mEdorType.trim())) {
				CError.buildErr(this, "前台传入EdorType失败！");
				return false;
			}
			if (mContNo == null || "".equals(mContNo.trim())) {
				CError.buildErr(this, "前台传入ContNo失败！");
				return false;
			}
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
			mInputData.clear();
			mInputData.add(map);

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
	 * 查询保全申请信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		if (tLPEdorAppDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppSet.get(1);

		return true;
	}

	/**
	 * 校验核保
	 */
	private boolean checkData() {
		// 查询保全申请信息
		String PrintSql="select * from lwmission where activityid in " +
				"('0000000302','0000000303','0000000304','0000000305','0000000312','0000000314'," +
				"'0000000315','0000000316') and missionid='"+"?mMissionID?"+"'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(PrintSql);
		sqlbv.put("mMissionID", mMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if(tLWMissionSet.size()>0){
			CError.buildErr(this, "工作流节点流转失败，有通知书尚未打印或尚未回收！");
			return false;
		}
		if (!getLPEdorApp()) {
			return false;
		}

		String sHasRefuse = hasRefuse();
		if (sHasRefuse == null) {
			return false;
		}
//		if (sHasRefuse.equals("true")) // 有拒保或延期
//		{
//			if (mUWFlag.equals(mEDORAPP_UWPASS)) {
//				CError tError = new CError();
//				tError.errorMessage = "保全申请下有保单拒保或延期，保全申请不能通过";
//				mErrors.addOneError(tError);
//				return false;
//			}
//		}

		// //校验核保级别
		// if (!checkUserGrade())
		// {
		// return false;
		// }

		return true;
	}

	/**
	 * 判断保全申请下保单或险种是否有拒保或延期的
	 * 
	 * @return boolean
	 */
	private String hasRefuse() {
		// 先循环判断保单核保结论
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请批单信息查询失败";
			mErrors.addOneError(tError);
			return null;
		}
		if (tLPEdorMainSet == null || tLPEdorMainSet.size() < 1) {
			CError tError = new CError();
			tError.errorMessage = "保全申请下没有批单信息";
			mErrors.addOneError(tError);
			return null;
		}

		String sUWState;
		for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
			sUWState = tLPEdorMainSet.get(i).getUWState();
			if (sUWState == null || sUWState.trim().equals("")
					|| sUWState.trim().equals("0")
					|| sUWState.trim().equals("5")) {
				if (!mLPEdorAppSchema.getOtherNoType().trim().equals("4")) {
					CError.buildErr(this, "保全申请下有保单尚未下核保结论!");
					return null;
				}
			} 
//			else if (sUWState.trim().equals("1")
//					|| sUWState.trim().equals("2")) {
//				// 如果有拒保或延期的 （1-拒保 2-延期）
//				return "true";
//			}
		}
		// ====del===zhangtao===2006-02-23====需求变动，只需判断保单层是否有拒保或延期，不用判断险种层====BGN====
		// //如果保单核保结论没有拒保或延期，再循环判断险种核保结论
		// String sql = " select * from lppol where appflag <> '4' and edorno in " +
		// " (select edorno from lpedormain " +
		// " where edoracceptno = '" +
		// mLPEdorAppSchema.getEdorAcceptNo() +
		// "')";
		// LPPolDB tLPPolDB = new LPPolDB();
		// LPPolSet tLPPolSet = tLPPolDB.executeQuery(sql);
		// if (tLPPolDB.mErrors.needDealError())
		// {
		// mErrors.copyAllErrors(tLPPolDB.mErrors);
		// CError tError = new CError();
		// tError.errorMessage = "险种信息查询失败";
		// mErrors.addOneError(tError);
		// return null;
		// }
		// if (tLPPolSet != null && tLPPolSet.size() > 0)
		// {
		// for (int j = 1; j <= tLPPolSet.size(); j++)
		// {
		// sUWState = tLPPolSet.get(j).getUWFlag();
		// if (sUWState.trim().equals("1") ||
		// sUWState.trim().equals("2"))
		// {
		// //如果有拒保或延期的 （1-拒保 2-延期）
		// return "true";
		// }
		// }
		// }
		// ====del===zhangtao===2006-02-23====需求变动，只需判断保单层是否有拒保或延期，不用判断险种层====END====
		return "false";
	}

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的批改状态为确认未生效 更新保全确认信息
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorState
	 */
	private void updEdorState(String sEdorAcceptNo, String sEdorState) {
		String wherePart = "where EdorAcceptNo='" + "?sEdorAcceptNo?" + "'";

		StringBuffer sbSQL = new StringBuffer();

		// 保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("sEdorState", sEdorState);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// 保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("sEdorState", sEdorState);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		// 团体保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorItem set EdorState = '").append(
				"?sEdorState?").append("', ModifyDate = '").append("?mCurrentDate?")
				.append("', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("sEdorState", sEdorState);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv3, "UPDATE");

		// 团体保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorMain set EdorState = '").append(
				"?sEdorState?").append("', ModifyDate = '").append("?mCurrentDate?")
				.append("', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sbSQL.toString());
		sbv4.put("sEdorState", sEdorState);
		sbv4.put("mCurrentDate", mCurrentDate);
		sbv4.put("mCurrentTime", mCurrentTime);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv4, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sbSQL.toString());
		sbv5.put("sEdorState", sEdorState);
		sbv5.put("mCurrentDate", mCurrentDate);
		sbv5.put("mCurrentTime", mCurrentTime);
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv5, "UPDATE");

	}

	/**
	 * 校验核保级别
	 * 
	 */
	private boolean checkUserGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mGlobalInput.Operator);
		if (!tLDUserDB.getInfo()) {
			mErrors.copyAllErrors(tLDUserDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "无此操作员信息，不能核保!" + "操作员："
					+ mGlobalInput.Operator;
			mErrors.addOneError(tError);
			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		if (tUWPopedom == null || tUWPopedom.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "操作员无核保权限，不能核保!" + "操作员："
					+ mGlobalInput.Operator;
			mErrors.addOneError(tError);
			return false;
		}

		if (mUWFlag.equals("6")) {
			// 如果上报，则不需校验核保级别
			return true;
		}

		String sql = "select * from LPEdorApp where EdorAcceptNo = '"
				+ "?EdorAcceptNo?" + "' ";
		logger.debug(sql);
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.executeQuery(sbv);

		if (tLPEdorAppSet == null || tLPEdorAppSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorAppManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有保全申请记录，不能核保!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			String tAppGrade = tLPEdorAppSet.get(1).getAppGrade();

			if ((tAppGrade == null) || tAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PEdorAppManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该保全申请无核保级别，不能核保!";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (tUWPopedom.compareTo(tAppGrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorAppManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "已经提交上级核保，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		return true;
	}

	/**
	 * 生成保全申请核保主表信息
	 * 
	 * @return boolean
	 */
	private LPAppUWMasterMainSchema prepareEdorAppUWMasterMain(
			LPEdorAppSchema tLPEdorAppSchema) {
		int tUWNo = 0;
		LPAppUWMasterMainSchema tLPAppUWMasterMainSchema = new LPAppUWMasterMainSchema();

		LPAppUWMasterMainDB tLPAppUWMasterMainDB = new LPAppUWMasterMainDB();
		tLPAppUWMasterMainDB
				.setEdorAcceptNo(tLPEdorAppSchema.getEdorAcceptNo());
		LPAppUWMasterMainSet tLPAppUWMasterMainSet = tLPAppUWMasterMainDB
				.query();
		if (tLPAppUWMasterMainSet.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPAppUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全申请核保主表信息失败！"));
			return null;
		}
		if (tLPAppUWMasterMainSet == null || tLPAppUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPAppUWMasterMainSchema.setEdorAcceptNo(tLPEdorAppSchema
					.getEdorAcceptNo());
			tLPAppUWMasterMainSchema.setOtherNo(tLPEdorAppSchema.getOtherNo());
			tLPAppUWMasterMainSchema.setOtherNoType(tLPEdorAppSchema
					.getOtherNoType());
			tLPAppUWMasterMainSchema.setUpReportContent("");
			tLPAppUWMasterMainSchema.setHealthFlag("0");
			tLPAppUWMasterMainSchema.setQuesFlag("0");
			tLPAppUWMasterMainSchema.setSpecFlag("0");
			tLPAppUWMasterMainSchema.setAddPremFlag("0");
			tLPAppUWMasterMainSchema.setAddPremReason("");
			tLPAppUWMasterMainSchema.setReportFlag("0");
			tLPAppUWMasterMainSchema.setPrintFlag("0");
			tLPAppUWMasterMainSchema.setPrintFlag2("0");
			tLPAppUWMasterMainSchema.setSpecReason("");
			tLPAppUWMasterMainSchema.setManageCom(mLPEdorAppSchema
					.getManageCom());
			tLPAppUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPAppUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPAppUWMasterMainSet.get(1).getUWNo() + 1;
			tLPAppUWMasterMainSchema.setSchema(tLPAppUWMasterMainSet.get(1));
		}
		tLPAppUWMasterMainSchema.setUWNo(tUWNo);

		tLPAppUWMasterMainSchema.setAutoUWFlag("2");
		tLPAppUWMasterMainSchema.setPassFlag(mLPEdorAppSchema.getUWState());
		tLPAppUWMasterMainSchema.setPostponeDate(mPostponeDate);
		tLPAppUWMasterMainSchema.setState(mLPEdorAppSchema.getUWState());
		tLPAppUWMasterMainSchema.setUWIdea(mUWIdea);
		tLPAppUWMasterMainSchema.setUWGrade(mLPEdorAppSchema.getUWGrade());
		tLPAppUWMasterMainSchema.setAppGrade(mLPEdorAppSchema.getAppGrade());
		tLPAppUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPAppUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		return tLPAppUWMasterMainSchema;
	}

	/**
	 * 生成保全申请核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPAppUWSubMainSchema prepareEdorAppUWSubMain(
			LPAppUWMasterMainSchema tLPAppUWMasterMainSchema) {
		LPAppUWSubMainSchema tLPAppUWSubMainSchema = new LPAppUWSubMainSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPAppUWSubMainSchema, tLPAppUWMasterMainSchema);

		tLPAppUWSubMainSchema.setOperator(mGlobalInput.Operator);
		tLPAppUWSubMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppUWSubMainSchema.setModifyTime(PubFun.getCurrentTime());
		tLPAppUWSubMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLPAppUWSubMainSchema.setMakeTime(PubFun.getCurrentTime());

		return tLPAppUWSubMainSchema;
	}

	/**
	 * 查询保全批单信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorMain() {
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全批单信息查询失败");
			return false;
		}

		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全批单信息查询失败");
			return false;
		}

		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return boolean
	 */
	private boolean preparePrint(LPEdorItemSchema cLPEdorItemSchema,
			LPContSchema tLPContSchema) {
		String uwflag = tLPContSchema.getUWFlag();

		// 9 保全核保结论同意
		if (mUWFlag.trim().equals("9")) {			
			if(uwflag.trim().equals("4"))// 核保通知书
			{
				String tSendNoticeFlag = ""; // 是否发核保通知书标志
				tSendNoticeFlag = (String) mTransferData.getValueByName("SendNoticeFlag");
				if (tSendNoticeFlag == null || tSendNoticeFlag.trim().equals("")) {
					CError.buildErr(this, "前台传输数据SendNoticeFlag失败!") ;
					return false;
				}
				if(tSendNoticeFlag.trim().equals("1"))
				{
					//String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
					String tPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PEdorSend);
					LOPRTManagerSchema tLOPRTManagerSchema;
					tLOPRTManagerSchema = new LOPRTManagerSchema();
					tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
					mUWPrtSeq=tPrtSeq;
					tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
					tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
					tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
					tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
					tLOPRTManagerSchema.setManageCom(tLPContSchema.getManageCom());
					tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
					tLOPRTManagerSchema.setAgentCode(tLPContSchema.getAgentCode());
					tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
					tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorSend);//25-保全核保通知书
					tLOPRTManagerSchema.setOtherNo(mContNo);
					tLOPRTManagerSchema.setStandbyFlag1(cLPEdorItemSchema.getEdorNo());
					tLOPRTManagerSchema.setStandbyFlag4(cLPEdorItemSchema.getEdorAcceptNo());
					tLOPRTManagerSchema.setStandbyFlag7(cLPEdorItemSchema.getEdorType());
					tLOPRTManagerSchema.setOldPrtSeq(tPrtSeq);
					tLOPRTManagerSchema.setStateFlag("0");
					mLOPRTManagerSet.add(tLOPRTManagerSchema);
					mTransferData.setNameAndValue("PrtSeq", mUWPrtSeq);
					mTransferData.setNameAndValue("Code", PrintManagerBL.CODE_PEdorSend);
					mTransferData.setNameAndValue("ConfirmFlag", "1");
				}
				else
					mTransferData.setNameAndValue("ConfirmFlag", "0");
			}
			else
				mTransferData.setNameAndValue("ConfirmFlag", "0");
		}

		/*// 延期通知书
		if (mUWFlag.trim().equals("2")) {
			//String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String tPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PEdorDEFER);
			LOPRTManagerSchema tLOPRTManagerSchema;
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setManageCom(tLPContSchema.getManageCom());
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
			tLOPRTManagerSchema.setAgentCode(tLPContSchema.getAgentCode());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorDEFER);
			tLOPRTManagerSchema.setOtherNo(cLPEdorItemSchema.getPolNo());
			tLOPRTManagerSchema.setStandbyFlag1(cLPEdorItemSchema.getEdorNo());
			tLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		}
		
		//加费通知书
		if (mUWFlag.trim().equals("4")) {
			//String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String tPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_EdorUWAdd);
			LOPRTManagerSchema tLOPRTManagerSchema;
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setManageCom(tLPContSchema.getManageCom());
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
			tLOPRTManagerSchema.setAgentCode(tLPContSchema.getAgentCode());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_EdorUWAdd);
			tLOPRTManagerSchema.setOtherNo(mContNo);
			tLOPRTManagerSchema.setStandbyFlag1(cLPEdorItemSchema.getEdorNo());
			tLOPRTManagerSchema.setStandbyFlag7(cLPEdorItemSchema.getEdorType());
			tLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		}*/		
		//拒保通知书
		else if (mUWFlag.trim().equals("3"))//拒保 
		{
			if(uwflag.trim().equals("1"))// 拒保通知书
			{
				// 通知书号
				//String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String tPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_EdorUWRANT);
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
				tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				tLOPRTManagerSchema.setManageCom(cLPEdorItemSchema.getManageCom());
				tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
				tLOPRTManagerSchema.setAgentCode(tLPContSchema.getAgentCode());
				tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);

				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_EdorUWRANT);//BQ84 保全拒保通知书
				tLOPRTManagerSchema.setOtherNo(cLPEdorItemSchema.getContNo());
				tLOPRTManagerSchema.setStandbyFlag1(cLPEdorItemSchema.getEdorNo());
				tLOPRTManagerSchema.setStandbyFlag4(cLPEdorItemSchema.getEdorAcceptNo());
				tLOPRTManagerSchema.setStandbyFlag7("BQJB");
				tLOPRTManagerSchema.setStateFlag("0");
				mLOPRTManagerSet.add(tLOPRTManagerSchema);
				mTransferData.setNameAndValue("ConfirmFlag", "0");
			}
			else
				mTransferData.setNameAndValue("ConfirmFlag", "0");
		}
		else if(mUWFlag.trim().equals("1"))//1 不同意 
		{
			mTransferData.setNameAndValue("ConfirmFlag", "0");
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

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("6120061213000001");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", "9");
		tTransferData.setNameAndValue("UWIdea", " test by zhangtao");
		tTransferData.setNameAndValue("PostponeDate", "");
		tTransferData.setNameAndValue("AppUser", "");

		// tTransferData.setNameAndValue("MissionID", "00000000000000006379");
		// tTransferData.setNameAndValue("SubMissionID", "1");
		// tTransferData.setNameAndValue("OtherNo", "230110000000520");
		// tTransferData.setNameAndValue("OtherNoType", "3");
		// tTransferData.setNameAndValue("EdorAppName", "陈洋洋");
		// tTransferData.setNameAndValue("Apptype", "2");
		// tTransferData.setNameAndValue("ManageCom", "86110000");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLPEdorAppSchema);

		PEdorAccepManuUWBL tPEdorAccepManuUWBL = new PEdorAccepManuUWBL();

		if (!tPEdorAccepManuUWBL.submitData(tVData, "")) {
			logger.debug(tPEdorAccepManuUWBL.mErrors.getError(0).errorMessage);
		}
	}
}
