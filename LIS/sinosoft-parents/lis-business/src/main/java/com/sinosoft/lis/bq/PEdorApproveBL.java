/*
 * @(#)PEdorAutoUWAfterInitService.java	2005-05-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LPApproveTrackDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPApproveTrackSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPApproveTrackSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全审批业务逻辑
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author sinosoft
 * @version 1.0
 */
public class PEdorApproveBL {
private static Logger logger = Logger.getLogger(PEdorApproveBL.class);
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
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	/** 传输数据 */
	private String mApproveContent;
	private String mApproveFlag;
	private String mEdorAcceptNo;
	private String mMissionID;
	private String mErrorChkFlag;
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	// 个人、团体保全标志（false为个人保全，true为团体保全） add by lizhuo at 2006-3-3
	private boolean PGrpFlag = false;

	public PEdorApproveBL() {
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

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

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
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保全申请批单查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}

		if (mLPEdorMainSet == null || mLPEdorMainSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保全申请下没有申请批单!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// /**
	// * 查询保全申请信息
	// * @return: boolean
	// */
	// private boolean getLPEdorApp()
	// {
	// LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
	// tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
	// if (tLPEdorAppDB.getInfo())
	// {
	// // @@错误处理
	// mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
	// CError tError = new CError();
	// tError.moduleName = "PEdorApproveBL";
	// tError.functionName = "getLCCont";
	// tError.errorMessage = "保全受理信息查询失败!" +
	// "保全受理号：" + mEdorAcceptNo;
	// mErrors.addOneError(tError);
	// return false;
	// }
	// mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
	//
	// return true;
	// }

	/**
	 * 查询保单信息
	 * 
	 * @return: boolean
	 */
	private boolean getLCCont(String sContNo) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(sContNo);
		if (!tLCContDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLCContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getLCCont";
			tError.errorMessage = "保单查询失败!" + "保单号：" + sContNo;
			mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB.getSchema());
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
		// 逐级审批，不在此校验核保权限，在申请时控制。
		// 校验当前用户的审批权限
		/*
		 * TransferData tTransferData = new TransferData();
		 * tTransferData.setNameAndValue("EdorAcceptNo", mEdorAcceptNo);
		 * tTransferData.setNameAndValue("OtherNoType",
		 * mLPEdorAppSchema.getOtherNoType()); VData tVData = new VData();
		 * tVData.add(mGlobalInput); tVData.add(tTransferData);
		 * PEdorPopedomCheckBL tPEdorPopedomCheckBL = new PEdorPopedomCheckBL();
		 * String sApproveFlag = "Approve"; if (PGrpFlag) { sApproveFlag =
		 * "GApprove"; } if (!tPEdorPopedomCheckBL.submitData(tVData,
		 * sApproveFlag)) { mErrors.copyAllErrors(tPEdorPopedomCheckBL.mErrors);
		 * return false; }
		 */

		// 更新保全审批状态
		if (!updApproveFlag()) {
			return false;
		}
		// 保全意见
        if(mApproveFlag.equals("1"))
        {
			LPApproveTrackSchema tLPApproveTrackSchema = new LPApproveTrackSchema();
			LPApproveTrackDB tLPApproveTrackDB = new LPApproveTrackDB();
			LPApproveTrackSet tLPApproveTrackSet = new LPApproveTrackSet();
			// 取出审批轨迹的最大值
			tLPApproveTrackSchema.setEdorAcceptNo(mEdorAcceptNo);
			tLPApproveTrackDB.setSchema(tLPApproveTrackSchema);
			tLPApproveTrackSet = tLPApproveTrackDB.query();
			tLPApproveTrackSchema
					.setApproveTimes(tLPApproveTrackSet.size() + 1);
			// 审批修改原因
			tLPApproveTrackSchema.setApproveOperator(mGlobalInput.Operator);
			tLPApproveTrackSchema.setApproveFlag(mApproveFlag);
			// 审批意见
			if (mTransferData.getValueByName("ApproveContent") != null
					&& !mTransferData.getValueByName("ApproveContent").equals(
							"")) {
				tLPApproveTrackSchema.setApproveContent(((String) mTransferData
						.getValueByName("ApproveContent")).trim());
			}
			tLPApproveTrackSchema.setApproveDate(mCurrentDate);
			tLPApproveTrackSchema.setApproveTime(mCurrentTime);
			// 常规字段的赋值
			tLPApproveTrackSchema.setManageCom(mLPEdorAppSchema.getManageCom());
			tLPApproveTrackSchema.setOperator(mGlobalInput.Operator);
			tLPApproveTrackSchema.setMakeDate(mCurrentDate);
			tLPApproveTrackSchema.setMakeTime(mCurrentTime);
			tLPApproveTrackSchema.setModifyDate(mCurrentDate);
			tLPApproveTrackSchema.setModifyTime(mCurrentTime);
			// 提交更新
			map.put(tLPApproveTrackSchema, "INSERT");
			// 垃圾清理
			tLPApproveTrackSet = null;
			tLPApproveTrackDB = null;
			tLPApproveTrackSchema = null;
//			 if (!sumLJSGetEndorse())
//			 {
//			 return false;
//			 }
        }
		// if (mApproveFlag.equals("1")) //审批通过
		// {
		// //Q：考虑将对审批结论的限制条件作为校验规则让产品定义描述，脱离程序实现 zhangtao 2005-10-25
		//
		// //如果保全申请核保结论为不通过，则不允许审批通过 add by zhangtao 2005-10-25
		// if (mLPEdorAppSchema.getUWState().equals("1")) //人工核保不通过
		// {
		// CError.buildErr(this, "保全申请人工核保结论为不通过，保全申请不能审批通过!");
		// return false;
		// }
		//
		// String sHasRefuse = hasRefuse(mEdorAcceptNo);
		// if (sHasRefuse == null)
		// {
		// return false;
		// }
		// if (sHasRefuse.equals("true")) //有拒保、延期
		// {
		// CError.buildErr(this, "保全申请下有保单拒保或延期，保全申请不能审批通过!");
		// return false;
		// }
		//
		// String sIORefuseFlag = hasIORefuse(mEdorAcceptNo);
		// if (sIORefuseFlag == null)
		// {
		// return false;
		// }
		// else if (sIORefuseFlag.equals("Y"))
		// {
		// CError.buildErr(this, "保全申请下有职业类别变更为拒保类职业，保全申请不能审批通过!");
		// return false;
		// }
		//
		// //判断如果包含AA 或 NS，将保单续期状态解挂（交主险保费）
		// String sEdorValidate = hasEdorType(mEdorAcceptNo, "'AA', 'NS'");
		// if (sEdorValidate == null)
		// {
		// return false;
		// }
		// else if (!sEdorValidate.equals("N"))
		// {
		// //判断主险是否已经续期交费，否则不允许审批通过
		// String sIsMainPolHasPay =
		// isMainPolHasPay(mLPEdorAppSchema.getOtherNo(), sEdorValidate);
		// if (sIsMainPolHasPay == null)
		// {
		// return false;
		// }
		// else if (sIsMainPolHasPay.equals("N"))
		// {
		// CError.buildErr(this, "主险续期保费尚未交纳，不能审批通过，请先交纳主险续期保费!");
		// return false;
		// }
		//
		// //================挪到申请确认时就对续期状态解挂====================BGN=============
		// // ContHangUpBL tContHangUpBL = new ContHangUpBL(mGlobalInput);
		// // if (!tContHangUpBL.HangUpRN(mLPEdorAppSchema.getOtherNo(), "0"))
		// // {
		// // CError.buildErr(this, "保全保单续期状态解挂失败! ");
		// // return false;
		// // }
		// // else
		// // {
		// // MMap tMap = tContHangUpBL.getMMap();
		// // map.add(tMap); //del because DB hs not update zhangtao 2005-08-02
		// // }
		// //================挪到申请确认时就对续期状态解挂====================END=============
		// }
		//
		// //保全财务合计处理 移至申请确认后
		
		
		// }
		// 审批功能改造为MS的审批功能
		// ========================================================================
		if (mApproveFlag.equals("2")) {
			LPApproveTrackSchema tLPApproveTrackSchema = new LPApproveTrackSchema();
			LPApproveTrackDB tLPApproveTrackDB = new LPApproveTrackDB();
			LPApproveTrackSet tLPApproveTrackSet = new LPApproveTrackSet();
			// 取出审批轨迹的最大值
			tLPApproveTrackSchema.setEdorAcceptNo(mEdorAcceptNo);
			tLPApproveTrackDB.setSchema(tLPApproveTrackSchema);
			tLPApproveTrackSet = tLPApproveTrackDB.query();
			tLPApproveTrackSchema
					.setApproveTimes(tLPApproveTrackSet.size() + 1);
			// 审批修改原因
			tLPApproveTrackSchema.setApproveOperator(mGlobalInput.Operator);
			tLPApproveTrackSchema.setApproveFlag(mApproveFlag);
			if (mTransferData.getValueByName("ModifyReason") == null
					|| mTransferData.getValueByName("ModifyReason").equals("")) {
				CError.buildErr(this, "请录入审批修改原因!");
				return false;
			} else {
				tLPApproveTrackSchema.setModifyReason((String) mTransferData
						.getValueByName("ModifyReason"));
			}
			// 审批意见
			if (mTransferData.getValueByName("ApproveContent") != null
					&& !mTransferData.getValueByName("ApproveContent").equals(
							"")) {
				tLPApproveTrackSchema.setApproveContent(((String) mTransferData
						.getValueByName("ApproveContent")).trim());
			}
			tLPApproveTrackSchema.setApproveDate(mCurrentDate);
			tLPApproveTrackSchema.setApproveTime(mCurrentTime);
			// 常规字段的赋值
			tLPApproveTrackSchema.setManageCom(mLPEdorAppSchema.getManageCom());
			tLPApproveTrackSchema.setOperator(mGlobalInput.Operator);
			tLPApproveTrackSchema.setMakeDate(mCurrentDate);
			tLPApproveTrackSchema.setMakeTime(mCurrentTime);
			tLPApproveTrackSchema.setModifyDate(mCurrentDate);
			tLPApproveTrackSchema.setModifyTime(mCurrentTime);
			tLPApproveTrackSchema.setErrorFlag(mErrorChkFlag);
			// 提交更新
			map.put(tLPApproveTrackSchema, "INSERT");
			// 垃圾清理
			tLPApproveTrackSet = null;
			tLPApproveTrackDB = null;
			tLPApproveTrackSchema = null;

			// 修改批改补退费记录-------------------start
			StringBuffer sbSQL = new StringBuffer();
			sbSQL
					.append(" select * from LJSGetEndorse l  ")
					.append(" where l.endorsementno in ")
					.append(
							" (select edorno from lpEdormain where edoracceptno = '")
					.append("?mEdorAcceptNo?")
					.append("' union ")
					.append(
							" select edorno from lpGrpEdormain where edoracceptno = '")
					.append("?mEdorAcceptNo?").append("' ) ").append(
							" and otherno!='000000'");
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sbSQL.toString());
			sqlbv1.put("mEdorAcceptNo", mEdorAcceptNo);
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB
					.executeQuery(sqlbv1);
			if (tLJSGetEndorseDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
				CError tError = new CError();
				tError.errorMessage = "批改补退费查询失败!";
				mErrors.addOneError(tError);
				return false;
			}
			/* 批改补退费GetNoticeNo恢复 */
			if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
				sbSQL.setLength(0);
				sbSQL
						.append(" update LJSGetEndorse set  ")
						.append(" GetNoticeNo = endorsementno")
						.append(" where endorsementno in ")
						.append(
								" (select edorno from lpEdormain where edoracceptno = '")
						.append("?mEdorAcceptNo?")
						.append("' union ")
						.append(
								" select edorno from lpGrpEdormain where edoracceptno = '")
						.append("?mEdorAcceptNo?").append("' ) ");
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(sbSQL.toString());
				sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
				map.put(sqlbv2, "UPDATE");
				String tCode = BqCode.LJ_OtherNoType;
				String tDelLJSPay = "delete from ljspay where otherno='"
						+ "?tDelLJSPay?" + "' and othernotype='" + "?tCode?" + "'";
				String tDelLJSGet = "delete from ljsget where otherno='"
						+ "?tDelLJSPay?" + "' and othernotype='" + "?tCode?" + "'";
				String Code = PrintManagerBL.ONT_EDORACCEPT;
				String tDelLOPRT = "delete from LOPRTManager where otherno='"
						+ "?tDelLJSPay?" + "' and othernotype='" + "?Code?" + "'";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(tDelLJSPay);
				sqlbv3.put("tCode", tCode);
				sqlbv3.put("mEdorAcceptNo", mEdorAcceptNo);
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(tDelLJSGet);
				sqlbv4.put("tCode", tCode);
				sqlbv4.put("mEdorAcceptNo", mEdorAcceptNo);
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(tDelLOPRT);
				sqlbv5.put("mEdorAcceptNo", mEdorAcceptNo);
				sqlbv5.put("Code", Code);
				map.put(sqlbv3, "DELETE");
				map.put(sqlbv4, "DELETE");
				map.put(sqlbv5, "DELETE");
			}
			// 修改批改补退费记录---------------------------------end
		}
		// ========================================================================
		// <END -->

		if (mApproveFlag.equals("3")) // 审批终止
		{
			
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
			
			if(!tPEdorOverDueBL.checkPayGet(mEdorAcceptNo))
			{
				CError.buildErr(this, "保全保全应收信息处理失败，可能此项目应收信息存在银行在途或者存在暂收! ");
				return false;
			}else
			{			
			 map.add(tPEdorOverDueBL.delLCPol(mEdorAcceptNo));
			 map.add(tPEdorOverDueBL.delFinFee(mEdorAcceptNo));
			}
			// ===add===zhangtao===2007-03-29====删除C表数据====END==============
			LPApproveTrackSchema tLPApproveTrackSchema = new LPApproveTrackSchema();
			LPApproveTrackDB tLPApproveTrackDB = new LPApproveTrackDB();
			LPApproveTrackSet tLPApproveTrackSet = new LPApproveTrackSet();
			// 取出审批轨迹的最大值
			tLPApproveTrackSchema.setEdorAcceptNo(mEdorAcceptNo);
			tLPApproveTrackDB.setSchema(tLPApproveTrackSchema);
			tLPApproveTrackSet = tLPApproveTrackDB.query();
			tLPApproveTrackSchema
					.setApproveTimes(tLPApproveTrackSet.size() + 1);
			// 审批修改原因
			tLPApproveTrackSchema.setApproveOperator(mGlobalInput.Operator);
			tLPApproveTrackSchema.setApproveFlag(mApproveFlag);
			// 审批意见
			if (mTransferData.getValueByName("ApproveContent") != null
					&& !mTransferData.getValueByName("ApproveContent").equals(
							"")) {
				tLPApproveTrackSchema.setApproveContent(((String) mTransferData
						.getValueByName("ApproveContent")).trim());
			}
			tLPApproveTrackSchema.setApproveDate(mCurrentDate);
			tLPApproveTrackSchema.setApproveTime(mCurrentTime);
			// 常规字段的赋值
			tLPApproveTrackSchema.setManageCom(mLPEdorAppSchema.getManageCom());
			tLPApproveTrackSchema.setOperator(mGlobalInput.Operator);
			tLPApproveTrackSchema.setMakeDate(mCurrentDate);
			tLPApproveTrackSchema.setMakeTime(mCurrentTime);
			tLPApproveTrackSchema.setModifyDate(mCurrentDate);
			tLPApproveTrackSchema.setModifyTime(mCurrentTime);
			tLPApproveTrackSchema.setErrorFlag(mErrorChkFlag);
			// 提交更新
			map.put(tLPApproveTrackSchema, "INSERT");
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
		if ("2".equals(mLPEdorAppSchema.getOtherNoType())
				|| "4".equals(mLPEdorAppSchema.getOtherNoType())) {
			PGrpFlag = true;// 团体保全处理
		}
		return true;
	}

	/**
	 * 判断保全申请下保单或险种是否有拒保或延期的
	 * 
	 * @param sEdorAcceptNo
	 * @return String
	 */
	private String hasRefuse(String sEdorAcceptNo) {
		// ===add===zhangtao===2007-03-20====团体保全核保结论判断====BGN======
		if (PGrpFlag) {
			// 先循环判断团体保单核保结论
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			tLPGrpEdorItemDB.setEdorAcceptNo(sEdorAcceptNo);
			LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
			if (tLPGrpEdorItemDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
				CError tError = new CError();
				tError.errorMessage = "保全申请批单信息查询失败";
				mErrors.addOneError(tError);
				return null;
			}
			if ((tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1)) {
				CError tError = new CError();
				tError.errorMessage = "保全申请下没有批单信息";
				mErrors.addOneError(tError);
				return null;
			}

			String sUWState;
			for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
				sUWState = tLPGrpEdorItemSet.get(i).getUWFlag();
				if (sUWState.trim().equals("1") || sUWState.trim().equals("2")) // ||
				// sUWState.trim().equals("x"))
				// 限额承保可以通过，因为第一次下限额承保，审批修改后核保结论依然是限额承保
				{
					// 如果有拒保或延期的 （1-拒保 2-延期 x-限额承保）
					return "true";
				}
				if (sUWState == null || sUWState.trim().equals("")) {
					sUWState = "9";
				}
				if (sUWState.trim().equals("0") || sUWState.trim().equals("5")) {
					// CError.buildErr(this, "保全申请下有保单尚未下核保结论!");
					// return null;
					sUWState = "9"; // 避免自动核保遗漏，影响保全审批 zhangtao 2007-1-24
				}
			}
			tLPGrpEdorItemSet = null;
			// 如果保单核保结论没有拒保或延期，再循环判断险种核保结论
			String sql = "  select * from lpgrppol where appflag <> '4' and edorno in "
					+ " (select edorno from lpgrpedoritem "
					+ " where edoracceptno = '" + "?sEdorAcceptNo?" + "')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
			LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
			LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.executeQuery(sqlbv);
			if (tLPGrpPolDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
				CError tError = new CError();
				tError.errorMessage = "险种信息查询失败";
				mErrors.addOneError(tError);
				return null;
			}
			if (tLPGrpPolSet != null && tLPGrpPolSet.size() > 0) {
				for (int j = 1; j <= tLPGrpPolSet.size(); j++) {
					sUWState = tLPGrpPolSet.get(j).getUWFlag();
					if (sUWState == null || sUWState.trim().equals("")) {
						sUWState = "9";
					}
					if (sUWState.trim().equals("1")
							|| sUWState.trim().equals("2")) // ||
					// sUWState.trim().equals("x"))
					// 限额承保可以通过，因为第一次下限额承保，审批修改后核保结论依然是限额承保
					{
						// 如果有拒保或延期的 （1-拒保 2-延期 x-限额承保）
						return "true";
					}
				}
				tLPGrpPolSet = null;
			}
		}
		// ===add===zhangtao===2007-03-20====团体保全核保结论判断====END======
		// 先循环判断保单核保结论
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(sEdorAcceptNo);
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请批单信息查询失败";
			mErrors.addOneError(tError);
			return null;
		}
		if ((tLPEdorMainSet == null || tLPEdorMainSet.size() < 1) && !PGrpFlag)// 团体保全可能不存在LPEdorMain记录
																				// add
																				// by
																				// lizhuo
		{
			CError tError = new CError();
			tError.errorMessage = "保全申请下没有批单信息";
			mErrors.addOneError(tError);
			return null;
		}

		String sUWState;
		for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
			sUWState = tLPEdorMainSet.get(i).getUWState();
			if (sUWState.trim().equals("1") || sUWState.trim().equals("2")) // ||
			// sUWState.trim().equals("x")) 限额承保可以通过，因为第一次下限额承保，审批修改后核保结论依然是限额承保
			{
				// 如果有拒保或延期的 （1-拒保 2-延期 x-限额承保）
				return "true";
			}
			if (sUWState == null || sUWState.trim().equals("")) {
				sUWState = "9";
			}
			if (sUWState.trim().equals("0") || sUWState.trim().equals("5")) {
				// CError.buildErr(this, "保全申请下有保单尚未下核保结论!");
				// return null;
				sUWState = "9"; // 避免自动核保遗漏，影响保全审批 zhangtao 2007-1-24
			}
		}

		// ====UPD===zhangtao===2006-12-22====之前需求理解有偏差,核保结论时只需判断保单层是否有拒保或延期，不用判断险种层,
		// 但是审批结论时依然要判断险种层====BGN====
		// ====del===zhangtao===2006-02-23====需求变动，只需判断保单层是否有拒保或延期，不用判断险种层====BGN====
		// 如果保单核保结论没有拒保或延期，再循环判断险种核保结论
		String sql = "  select * from lppol where appflag <> '4' and edorno in "
				+ " (select edorno from lpedoritem "
				+ " where edoracceptno = '" + "?sEdorAcceptNo?" + "')";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("sEdorAcceptNo", sEdorAcceptNo);
		if (PGrpFlag) {
			sql = "  select * from lppol where appflag <> '4' and edorno in "
					+ " (select edorno from lpgrpedoritem "
					+ " where edoracceptno = '" + "?sEdorAcceptNo?" + "')";
			sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("sEdorAcceptNo", sEdorAcceptNo);
		}
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = tLPPolDB.executeQuery(sbv);
		if (tLPPolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPPolDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "险种信息查询失败";
			mErrors.addOneError(tError);
			return null;
		}
		if (tLPPolSet != null && tLPPolSet.size() > 0) {
			for (int j = 1; j <= tLPPolSet.size(); j++) {
				sUWState = tLPPolSet.get(j).getUWFlag();
				if (sUWState == null || sUWState.trim().equals("")) {
					sUWState = "9";
				}
				if (sUWState.trim().equals("1") || sUWState.trim().equals("2")) // ||
				// sUWState.trim().equals("x"))
				// 限额承保可以通过，因为第一次下限额承保，审批修改后核保结论依然是限额承保
				{
					// 如果有拒保或延期的 （1-拒保 2-延期 x-限额承保）
					return "true";
				}
			}
		}
		// ====del===zhangtao===2006-02-23====需求变动，只需判断保单层是否有拒保或延期，不用判断险种层====END====
		// ====UPD===zhangtao===2006-12-22====之前需求理解有偏差,核保结论时只需判断保单层是否有拒保或延期，不用判断险种层,
		// 但是审批结论时依然要判断险种层====BGN====
		return "false";
	}

	/**
	 * 判断是否包含保全项目，如果包含则返回最晚保全生效日期
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorType
	 * @return String
	 */
	private String hasEdorType(String sEdorAcceptNo, String sEdorType) {
		String sql = " select max(edorvalidate) from lpedoritem where edortype in ("
				+ "?sEdorType?" + ") and edoracceptno = '" + "?sEdorAcceptNo?" + "'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sEdorType", sEdorType);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		String sEdorValidate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全项目查询失败");
			return null;
		}
		if (sEdorValidate == null || sEdorValidate.trim().equals("")) {
			return "N";
		} else {
			if (sEdorValidate.length() > 10) {
				sEdorValidate = sEdorValidate.substring(0, 10);
			}
			return sEdorValidate;
		}

	}

	/**
	 * 判断主险续期保费是否已交
	 * 
	 * @param sContNo
	 * @param sDate
	 * @return String
	 */
	private String isMainPolHasPay(String sContNo, String sDate) {
		String sql = " select 1 from dual where exists (select 'X' from lcpol "
				+ " where polno = mainpolno and payintv <> 0 and paytodate <= '"
				+ "?sDate?" + "' and paytodate < payenddate and contno = '"
				+ "?sContNo?" + "' )";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sDate", sDate);
		sqlbv.put("sContNo", sContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sEdorValidate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单主险交至日期查询失败!");
			return null;
		}
		if (sEdorValidate != null && sEdorValidate.trim().equals("1")) {
			return "N"; // 没有交费
		} else {
			return "Y"; // 已经交费
		}
	}

	/**
	 * 判断是否包含拒保类职业类别变更
	 * 
	 * @param sEdorAcceptNo
	 * @return String
	 */
	private String hasIORefuse(String sEdorAcceptNo) {
		String sql = " select  trim(i.occupationtype) from lpedoritem e, lpinsured i  "
				+ " where e.edoracceptno = '"
				+ "?sEdorAcceptNo?"
				+ "' and i.edorno = e.edorno and i.edortype = e.edortype ";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "职业类别查询失败");
			return null;
		}
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			return "N";
		} else {
			String sOccupationType;
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				sOccupationType = tSSRS.GetText(i, 1);
				if (sOccupationType != null
						&& sOccupationType.trim().equals("z")) {
					return "Y";
				}
			}
			return "N";
		}
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
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得审批结论
		mApproveFlag = (String) mTransferData.getValueByName("ApproveFlag");
		if (mApproveFlag == null || mApproveFlag.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ApproveFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mApproveContent = (String) mTransferData.getValueByName("ApproveContent");
//		if (mApproveContent == null || mApproveContent.equals("")) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "PEdorApproveBL";
//			tError.functionName = "getInputData";
//			tError.errorMessage = "前台传输业务数据中ApproveFlag失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
		
		// 获得任务号
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mErrorChkFlag = (String) mTransferData.getValueByName("ErrorChkFlag");

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
			mResult.add(mTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 更新保全申请、申请批单、保全项目的审批状态
	 * 
	 * @return: boolean
	 */
	private boolean updApproveFlag() {
		String sEdorState = "";

		if (mApproveFlag.equals("1")) // 审批通过,置批改状态为[审批通过]
		{
			sEdorState = "a";
		}
		if (mApproveFlag.equals("2")) // 审批修改,置批改状态为[审批修改]
		{
			sEdorState = "5";
		}
		if (mApproveFlag.equals("3")) // 审批终止,置批改状态为[审批终止]
		{
			sEdorState = "9";
		}
		
		//modify by jiaqiangli 2008-12-30 修改当于当前审批级别
		String tCurEdorPopedomGrade = (String)mTransferData.getValueByName("CurEdorPopedom");
		if (tCurEdorPopedomGrade == null || tCurEdorPopedomGrade.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中CurEdorPopedom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		//modify by jiaqiangli 2008-12-30 修改当于当前审批级别
		String tRiskProp = (String)mTransferData.getValueByName("RiskProp");
		String tGetMoney = (String)mTransferData.getValueByName("GetMoney");
		
		// 查询复合条件(具有审批权限)的最小权限 ------------start
		String tSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (mGlobalInput.ManageCom.length() == 8) {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ "?managecom?"
					+ "'))"
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ "?managecom1?"
					+ "'))"
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ "?managecom2?"
					+ "'))"
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select max(code) from ldcode where codetype='edorpopedom'"
					+ "	) g";
			sqlbv.sql(tSql);
			sqlbv.put("tGetMoney", tGetMoney);
			sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
			sqlbv.put("tRiskProp", tRiskProp);
			sqlbv.put("managecom", mGlobalInput.ManageCom);
			sqlbv.put("tCurEdorPopedomGrade", tCurEdorPopedomGrade);
			sqlbv.put("managecom1", mGlobalInput.ManageCom.substring(0, 6));
			sqlbv.put("managecom2", mGlobalInput.ManageCom.substring(0, 4));
		} else if (mGlobalInput.ManageCom.length() == 6) {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ "?managecom?"
					+ "'))"
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ "?managecom2?"
					+ "'))"
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select max(code) from ldcode where codetype='edorpopedom'"
					+ "	) g";
			sqlbv.sql(tSql);
			sqlbv.put("tGetMoney", tGetMoney);
			sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
			sqlbv.put("tRiskProp", tRiskProp);
			sqlbv.put("managecom", mGlobalInput.ManageCom);
			sqlbv.put("tCurEdorPopedomGrade", tCurEdorPopedomGrade);
			sqlbv.put("managecom2", mGlobalInput.ManageCom.substring(0, 4));
		} else if (mGlobalInput.ManageCom.length() == 4) {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='"
					+ "?managecom?"
					+ "'))"
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select max(code) from ldcode where codetype='edorpopedom'"
					+ "	) g";
			sqlbv.sql(tSql);
			sqlbv.put("tGetMoney", tGetMoney);
			sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
			sqlbv.put("tRiskProp", tRiskProp);
			sqlbv.put("managecom", mGlobalInput.ManageCom);
			sqlbv.put("tCurEdorPopedomGrade", tCurEdorPopedomGrade);
		} else {
			tSql = "select min(popedom) from ( "
					+ " select  min(b.edorpopedom) as popedom from LPEdorPopedom a,LDEdorUser b where "
					+ "?tGetMoney?"+" <= limitgetmoney and approveflag='1' and riskprop = '" + "?tRiskProp?"
					+ "' and b.usertype='1' and b.userstate='0' "
					+ " and edorcode =(select distinct edortype from lpedoritem where "
					+ " edoracceptno='"
					+ "?edoracceptno?"
					+ "') "
					+ " and a.edorpopedom=b.edorpopedom and b.usercode in "
					+ " (select usercode from LDEdorUser where usercode in "
					+ " (select usercode from lduser where managecom='86')) "
					+ " and b.edorpopedom>'"
					+ "?tCurEdorPopedomGrade?"
					+ "'"
					+ " union "
					+ " select max(code) from ldcode where codetype='edorpopedom'"
					+ "	) g";
			sqlbv.sql(tSql);
			sqlbv.put("tGetMoney", tGetMoney);
			sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
			sqlbv.put("tRiskProp", tRiskProp);
			sqlbv.put("tCurEdorPopedomGrade", tCurEdorPopedomGrade);
		}

		ExeSQL tExeSQL = new ExeSQL();
		String tPopeDom = tExeSQL.getOneValue(sqlbv);
		if (tPopeDom == null || tPopeDom.equals("")) {
			mErrors.addOneError(new CError("权限配置不正确，找不到符合条件的审批权限！"));
			return false;
		}
		// 查询复合条件的最小权限 ------------end
		// 更新保全申请主表
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" UPDATE LPEdorApp SET ").append(" ApproveFlag = '")
				.append("?mApproveFlag?").append("' ,ApproveOperator = '").append(
						"?ApproveOperator?").append("' ,ApproveDate = '")
				.append("?mCurrentDate?").append("' ,ApproveTime = '")
				.append("?mCurrentTime?")
				.append("' ,EdorState = '")
				.append("?sEdorState?")
				// .append("' ,Operator = '")
				// .append(mGlobalInput.Operator)
				.append("' ,ModifyDate = '").append("?mCurrentDate?").append(
						"' ,ModifyTime = '").append("?mCurrentTime?").append(
						"' ,apppregrade='").append("?tPopeDom?").append("'")
				.append(" WHERE EDORACCEPTNO = '").append("?mEdorAcceptNo?")
				.append("'");
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("mApproveFlag", mApproveFlag);
		sbv1.put("ApproveOperator", mGlobalInput.Operator);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("sEdorState", sEdorState);
		sbv1.put("tPopeDom", tPopeDom);
		sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sbv1, "UPDATE");
		logger.debug("== UPDATE LPEdorApp ==" + sbSQL.toString());

		// 更新保全批改表
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain SET ").append(" ApproveFlag = '")
				.append("?mApproveFlag?").append("' ,ApproveOperator = '").append(
						"?ApproveOperator?").append("' ,ApproveDate = '")
				.append("?mCurrentDate?").append("' ,ApproveTime = '")
				.append("?mCurrentTime?")
				.append("' ,EdorState = '")
				.append("?sEdorState?")
				// .append("' ,Operator = '")
				// .append(mGlobalInput.Operator)
				.append("' ,ModifyDate = '").append("?mCurrentDate?").append(
						"' ,ModifyTime = '").append("?mCurrentTime?").append("'")
				.append(" WHERE EDORACCEPTNO = '").append("?mEdorAcceptNo?")
				.append("'");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("mApproveFlag", mApproveFlag);
		sbv2.put("ApproveOperator", mGlobalInput.Operator);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("sEdorState", sEdorState);
		sbv2.put("tPopeDom", tPopeDom);
		sbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sbv2, "UPDATE");
		logger.debug("== UPDATE LPEdorMain ==" + sbSQL.toString());

		// 更新保全项目表
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem SET ").append(" ApproveFlag = '")
				.append("?mApproveFlag?").append("' ,ApproveOperator = '").append(
						"?ApproveOperator?").append("' ,ApproveDate = '")
				.append("?mCurrentDate?").append("' ,ApproveTime = '")
				.append("?mCurrentTime?")
				.append("' ,EdorState = '")
				.append("?sEdorState?")
				// .append("' ,Operator = '")
				// .append(mGlobalInput.Operator)
				.append("' ,ModifyDate = '").append("?mCurrentDate?").append(
						"' ,ModifyTime = '").append("?mCurrentTime?").append("'")
				.append(" WHERE EDORACCEPTNO = '").append("?mEdorAcceptNo?")
				.append("'");
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("mApproveFlag", mApproveFlag);
		sbv3.put("ApproveOperator", mGlobalInput.Operator);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("sEdorState", sEdorState);
		sbv3.put("tPopeDom", tPopeDom);
		sbv3.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sbv3, "UPDATE");
		logger.debug("== UPDATE LPEdorItem ==" + sbSQL.toString());


		return true;
	}


	 /**
	 * 保全财务合计处理
	 * @return: boolean
	 */
	 private boolean sumLJSGetEndorse()
	 {
	
	 TransferData tTransferData = new TransferData();
	 tTransferData.setNameAndValue("EdorAcceptNo", mEdorAcceptNo);
	 
	 LPEdorAppDB lpEdorAppDao = new LPEdorAppDB();
	 lpEdorAppDao.setEdorAcceptNo(mEdorAcceptNo);
	 LPEdorAppSet rs = lpEdorAppDao.query();
	 if (rs.size() != 1) {
		 //TODO
		 return false;
	 }
		 
	 LPEdorAppSchema lpEdorApp = rs.get(1);
	 VData tVData = new VData();
	 tVData.add(tTransferData);
	 tVData.add(mGlobalInput);
	 tVData.add(lpEdorApp);
	 LJSGetEndorseTotalBL tLJSGetEndorseTotalBL = new LJSGetEndorseTotalBL();
	
	 if (!tLJSGetEndorseTotalBL.submitData(tVData, ""))
	 {
	 mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
	 mErrors.addOneError(new CError("生成财务应收、应付信息失败!"));
	 return false;
	 }
	 if (tLJSGetEndorseTotalBL.getResult() == null)
	 {
	 mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
	 mErrors.addOneError(new CError("获得财务应收、应付信息失败!"));
	 return false;
	 }
	
	 VData tResult = tLJSGetEndorseTotalBL.getResult();
	 MMap tMMap = (MMap) (tResult.getObjectByObjectName("MMap", 0));
	 map.add(tMMap);
	 tTransferData =
	 (TransferData) tResult.getObjectByObjectName("TransferData", 0);
	 String sNeedPayFlag =
	 (String) tTransferData.getValueByName("NeedPayFlag");
	 mTransferData.setNameAndValue("NeedPayFlag", sNeedPayFlag);
	
	 return true;
	 }

	private boolean delCancelMission() {
		// String sYBTFlag = (String) mTransferData.getValueByName("YBT");
		// if (sYBTFlag != null && sYBTFlag.equals("YBT"))
		// {
		// return true;
		// }

		String sql = " select * from lwmission where ActivityID in ('0000000008', '0000008008') "
				+ " and missionid = '" + "?mMissionID?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mMissionID", mMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if (tLWMissionDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() < 1) {
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = tLWMissionSet.get(i);
			ActivityOperator tActivityOperator = new ActivityOperator();
			if (!tActivityOperator.DeleteMission(tLWMissionSchema
					.getMissionID(), tLWMissionSchema.getSubMissionID(),
					tLWMissionSchema.getActivityID(), mInputData)) {
				CError tError = new CError();
				tError.errorMessage = "工作流保全撤销节点删除失败!";
				mErrors.addOneError(tError);
				return false;
			}

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
			mmLOPRTManagerSchema.setStandbyFlag1(mEdorAcceptNo);//保单号
			mmLOPRTManagerSchema.setStandbyFlag2(mApproveContent);//拒绝原因
			mmLOPRTManagerSchema.setStandbyFlag3(mLPEdorAppSchema.getOperator());//操作员
			mmLOPRTManagerSchema.setStandbyFlag4(mLPEdorAppSchema.getEdorAppName());//申请人
			mmLOPRTManagerSchema.setStandbyFlag5(mLPEdorAppSchema.getMakeDate());//申请时间
			mmLOPRTManagerSchema.setStandbyFlag6(mGlobalInput.Operator);//审批人
			mmLOPRTManagerSchema.setStandbyFlag7(mCurrentDate);//审批时间
			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
		} catch (Exception e) {
			CError.buildErr(this, "插入保全收费通知书失败!");
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

		PEdorApproveBL tPEdorApproveBL = new PEdorApproveBL();

		if (!tPEdorApproveBL.submitData(tVData, "")) {
			// logger.debug(tPEdorApproveBL.mErrors.getError(0).errorMessage);
		}

	}

}
