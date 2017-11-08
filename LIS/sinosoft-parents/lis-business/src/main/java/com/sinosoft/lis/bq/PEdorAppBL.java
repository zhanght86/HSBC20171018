/*
 * @(#)PEdorAppBL.java	2005-04-28
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.get.PersonPayPlanCancelUI;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.operfee.IndiDueFeeCancelUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 保全-无扫描申请数据校验
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */

public class PEdorAppBL implements AfterEndService {
private static Logger logger = Logger.getLogger(PEdorAppBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	/** 数据操作字符串 */
	private String mOperate;

	// 统一更新日期，时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAppBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("==============PEdorAppBL====================");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		int intv = PubFun.calInterval(mCurrentDate, mLPEdorAppSchema
				.getEdorAppDate(), "D");
		if (intv > 0) {
			CError.buildErr(this, "保全申请日期不能晚于系统当前日期!");
			return false;
		}

		BqContHangUpBL tBqContHangUpBL;
		
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorAppSet tLPEdorApp = tLPEdorAppDB.query();
		if (tLPEdorAppDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全受理查询失败!");
			return false;
		}
		if (tLPEdorApp != null && tLPEdorApp.size() > 0) {
			CError.buildErr(this, "该保全受理号已经保存过!");
			return false;
		}

		// //校验银行账户信息是否完整
		// if (!checkBankInfo(mLPEdorAppSchema))
		// {
		// return false;
		// }

		if (mLPEdorAppSchema.getOtherNoType().equals("3")) {
			// 校验保单状态
			if (!checkContState(mLPEdorAppSchema.getOtherNo())) {
				return false;
			}
			tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);

			// 校验保单保全是否被挂起
			if (!tBqContHangUpBL.checkHangUpState(
					mLPEdorAppSchema.getOtherNo(), mLPEdorAppSchema
							.getOtherNoType())) {
				mErrors.copyAllErrors(tBqContHangUpBL.mErrors);
				return false;
			}

//			if (!tBqContHangUpBL.hangUpCont(mLPEdorAppSchema.getOtherNo(), "1",
//					mLPEdorAppSchema.getEdorAcceptNo())) {
//				CError.buildErr(this, "保单挂起失败! 保单号："
//						+ mLPEdorAppSchema.getOtherNo());
//				return false;
//			} else {
				MMap tMap = tBqContHangUpBL.getMMap();
				map.add(tMap);
			//}
			
			//add by jiaqiangli 2009-06-05删除催付数据，否则导致：
			//gettodate='2009-06-05','2009-06-04'来退保，由于未删除催付，导致保单生存领取查询不一致
			//保全操作完成之后系统会进行重新催付
			if (!DealLJSGetDraw(mLPEdorAppSchema.getOtherNo())) {
				return false;
			}
			//催收撤销挪到里头执行
			if(!DealLJSPay(mLPEdorAppSchema.getOtherNo()))
			{
				return false;
			}
			
		} else if (mLPEdorAppSchema.getOtherNoType().equals("1")) {
			// 个人客户号
			LDPersonDB tLDPersonDB = new LDPersonDB();
			tLDPersonDB.setCustomerNo(mLPEdorAppSchema.getOtherNo());
			LDPersonSet tLDPersonSet = tLDPersonDB.query();
			if (tLDPersonDB.mErrors.needDealError()) {
				CError.buildErr(this, "客户查询失败!");
				return false;
			}
			if (tLDPersonSet == null || tLDPersonSet.size() != 1) {
				CError.buildErr(this, "该客户号不存在!");
				return false;
			}

			// 查出该客户下所有保单
			tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
			LCContSet tLCContSet = tBqContHangUpBL.getCustomerCont(
					mLPEdorAppSchema.getOtherNo(), mLPEdorAppSchema
							.getEdorAppDate(), mLPEdorAppSchema.getAppType());
			if (tLCContSet == null || tLCContSet.size() < 1) {
				CError.buildErr(this, "该客户没有允许变更的保单!");
				return false;
			}
			for (int k = 1; k <= tLCContSet.size(); k++) {
				// ========del======zhangtao======2005-09-20=============BGN=====================
				// 客户层面的保全受理范围为全国
				// //校验保单与操作员管理机构
				// int iMessage =
				// checkManageCom(tLCContSet.get(1).getManageCom(),
				// mGlobalInput.ManageCom);
				// if (iMessage == -1)
				// {
				// CError.buildErr(this, "该客户有保单属上属机构，您无权受理其保全申请! " +
				// "保单号：" + tLCContSet.get(1).getContNo());
				// return false;
				// }
				// else if (iMessage == 0)
				// {
				// CError.buildErr(this, "该客户有保单属其他机构，您无权受理其保全申请! " +
				// "保单号：" + tLCContSet.get(1).getContNo());
				// return false;
				// }
				// ========del======zhangtao======2005-09-20=============END=====================
				// 校验保单保全是否被挂起
				if (!tBqContHangUpBL.checkHangUpState(tLCContSet.get(k)
						.getContNo(), mLPEdorAppSchema.getOtherNoType())) {
					mErrors.copyAllErrors(tBqContHangUpBL.mErrors);
					return false;
				}

				tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
				if (!tBqContHangUpBL.hangUpCont(tLCContSet.get(k).getContNo(),
						"1", mLPEdorAppSchema.getEdorAcceptNo())) {
					CError.buildErr(this, "保单挂起失败! 保单号："
							+ tLCContSet.get(k).getContNo());
					return false;
				} else {
					MMap tMap = tBqContHangUpBL.getMMap();
					map.add(tMap);
				}
				//客户层保全下循环撤销应收
				if(!DealLJSPay(tLCContSet.get(k).getContNo()))
				{
					return false;
				}
			}
		}
	 
		if(mLPEdorAppSchema.getOtherNoType().equals("3"))
		{
			// add by jiaqiangli 2009-04-27 lpedorapp.managecom=lccont.managecom
			LCContDB tLCContDB = new LCContDB();// 个人保单表
			tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
			if (tLCContDB.getInfo() == false) {
				CError.buildErr(this, "查询保单号"+mLPEdorAppSchema.getOtherNo()+"失败!");
				return false;
			}
			// add by jiaqiangli 2009-04-27 lpedorapp.managecom=lccont.managecom
			mLPEdorAppSchema.setManageCom(tLCContDB.getManageCom());
		}
		if(mLPEdorAppSchema.getOtherNoType().equals("1"))
		{
			mLPEdorAppSchema.setManageCom(mGlobalInput.ManageCom);
		}
		 
		// 添加保全申请主表记录
		mLPEdorAppSchema.setEdorState("3");
		mLPEdorAppSchema.setEdorConfNo(mLPEdorAppSchema.getEdorAcceptNo());
		
		
		mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setMakeDate(mCurrentDate);
		mLPEdorAppSchema.setMakeTime(mCurrentTime);
		mLPEdorAppSchema.setModifyDate(mCurrentDate);
		mLPEdorAppSchema.setModifyTime(mCurrentTime);
		map.put(mLPEdorAppSchema, "INSERT");

		return true;
	}

	/**
	 * 校验保单状态
	 * 
	 * @return boolean
	 */
	private boolean checkContState(String sContNo) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(sContNo);
		// tLCContDB.setAppFlag("1");
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单查询失败!");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() != 1) {
			CError.buildErr(this, "该保单号不存在!");
			return false;
		}

		int iMessage = checkManageCom(tLCContSet.get(1).getManageCom(),
				mGlobalInput.ManageCom);
		if (iMessage == -1) {
			CError.buildErr(this, "您无权受理上属机构保单保全申请!");
			return false;
		} else if (iMessage == 0) {
			CError.buildErr(this, "您无权受理其他机构保单保全申请!");
			return false;
		}

		// 校验保单生存领取状态是否终止,sGetEndState为1未终止,为空已终止
		String asql = " select distinct 1 from lcget where contno = '"
				+ "?sContNo?"
				+ "' and livegettype = '0' and (case when getendstate is not null then getendstate else '0' end) = '0'";
		logger.debug(asql);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(asql);
		sqlbv.put("sContNo", sContNo);
		ExeSQL aExeSQL = new ExeSQL();
		String sGetEndState = aExeSQL.getOneValue(sqlbv);
		if (aExeSQL.mErrors.needDealError()) {
			logger.debug("保单生存领取状态查询失败");
			return false;
		}

		if (!tLCContSet.get(1).getAppFlag().equals("1")) {
			String sql = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				sql = "select statereason from lccontstate where statetype in ('Terminate') and state = '1' "
					+ " and ((startdate <= '"
					+ "?date?"
					+ "' and '"
					+ "?date?"
					+ "' <= enddate) or "
					+ " (startdate <= '"
					+ "?date?"
					+ "' and enddate is null)) "
					+ " and polno =  (select mainpolno from (select mainpolno from lcpol where contno = '"+"?sContNo?"+"' and polno=mainpolno order by cvalidate desc) g where rownum<=1)";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				sql = "select statereason from lccontstate where statetype in ('Terminate') and state = '1' "
						+ " and ((startdate <= '"
						+ "?date?"
						+ "' and '"
						+ "?date?"
						+ "' <= enddate) or "
						+ " (startdate <= '"
						+ "?date?"
						+ "' and enddate is null)) "
						+ " and polno =  (select mainpolno from (select mainpolno from lcpol where contno = '"+"?sContNo?"+"' and polno=mainpolno order by cvalidate desc) g limit 0,1)";	
			}
			logger.debug(sql);
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("date", mLPEdorAppSchema.getEdorAppDate());
			sbv.put("sContNo", sContNo);
			ExeSQL tExeSQL = new ExeSQL();
			String sStateReason = tExeSQL.getOneValue(sbv);
			if (tExeSQL.mErrors.needDealError()) {
				logger.debug("保单状态查询失败");
				return false;
			}
			if (sStateReason == null || sStateReason.trim().equals("")) {
				CError.buildErr(this, "保单已经终止，不能受理保全!");
				return false;
			} else if (sStateReason.trim().equals("01")
					|| sStateReason.trim().equals("05")
					|| sStateReason.trim().equals("06")
					|| sStateReason.trim().equals("02")
					|| sStateReason.trim().equals("04")) {
				if (sStateReason.trim().equals("04")
						&& (sGetEndState == null || sGetEndState.trim().equals(
								""))) {
					CError.buildErr(this, "保单已经理赔终止，不能受理保全!"); // 理赔终止且生存领取也终止的,不可以受理
					return false;
				} else {
					return true; // 退保、满期、自垫、贷款终止,可以受理; 理赔终止且有生存领取的，可以受理
				}
			} else {
				if (sStateReason.trim().equals("03")) {
					CError.buildErr(this, "保单已经解约终止，不能受理保全!");
					return false;
				} /*else if (sStateReason.trim().equals("07")) {
					CError.buildErr(this, "保单已经失效终止，不能受理保全!");
					return false;
				}*/ else if (!sStateReason.trim().equals("07")) {
					//modify by jiaqiangli 2009-07-14 失效终止可以做退保
					CError.buildErr(this, "保单已经终止，不能受理保全!");
					return false;
				}
			}
		}

		if (mLPEdorAppSchema.getAppType() != null
				&& (mLPEdorAppSchema.getAppType().trim().equals("6") || mLPEdorAppSchema
						.getAppType().trim().equals("7"))) {
			// 部门转办不用校验客户签收日期 6-新契约内部转办 7-其他内部转办
		} else {
			if (tLCContSet.get(1).getCustomGetPolDate() == null
					|| tLCContSet.get(1).getCustomGetPolDate().equals("")) {
				CError.buildErr(this, "该保单客户尚未签收!");
				return false;
			}
			int intv = PubFun.calInterval(tLCContSet.get(1)
					.getCustomGetPolDate(), mLPEdorAppSchema.getEdorAppDate(),
					"D");
			if (intv < 0) {
				CError.buildErr(this, "保全申请日期不能早于客户签收日期!");
				return false;
			}
		}

		return true;
	}

	// /**
	// * 校验保单保全是否被挂起
	// * @return boolean
	// */
	// private boolean checkEdorHangUp(String sContNo, String OtherNoType)
	// {
	// LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
	// tLCContHangUpStateDB.setContNo(sContNo);
	// tLCContHangUpStateDB.setInsuredNo("000000");
	// tLCContHangUpStateDB.setPolNo("000000");
	// LCContHangUpStateSet tLCContHangUpStateSet =
	// tLCContHangUpStateDB.query();
	// if (tLCContHangUpStateDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "保单挂起状态查询失败!");
	// return false;
	// }
	// if (tLCContHangUpStateSet == null || tLCContHangUpStateSet.size() < 1)
	// {
	// //保单没有挂起状态
	// return true;
	// }
	// else
	// {
	// if (tLCContHangUpStateSet.get(1).getPosFlag().equals("1"))
	// {
	// String sHangUpReason = "";
	// if (tLCContHangUpStateSet.get(1).getStandFlag2() != null &&
	// !tLCContHangUpStateSet.get(1).getStandFlag2().trim().equals(""))
	// {
	// sHangUpReason = "保全";
	// if (tLCContHangUpStateSet.get(1).getStandFlag2().substring(1,
	// 2).equals("4"))
	// {
	// sHangUpReason = "保全试算";
	// }
	//
	// }
	// if (tLCContHangUpStateSet.get(1).getStandFlag3() != null &&
	// !tLCContHangUpStateSet.get(1).getStandFlag3().trim().equals(""))
	// {
	// sHangUpReason = "理赔";
	// }
	// // if (tLCContHangUpStateSet.get(1).getStandFlag5() != null &&
	// // !tLCContHangUpStateSet.get(1).getStandFlag5().trim().equals(""))
	// // {
	// // sHangUpReason = "续期";
	// // }
	//
	// String sErrorMsg = "";
	// if (OtherNoType.equals("3"))
	// {
	// sErrorMsg = "该保单被" + sHangUpReason + "挂起,不能受理保全申请!";
	// if (tLCContHangUpStateSet.get(1).getStandFlag2() != null &&
	// !tLCContHangUpStateSet.get(1).getStandFlag2().trim().equals(""))
	// {
	// if (tLCContHangUpStateSet.get(1).getStandFlag2().substring(1,
	// 2).equals("4"))
	// {
	// sErrorMsg += "试算流水号：" + tLCContHangUpStateSet.get(1).getStandFlag2();
	// }
	// else
	// {
	// sErrorMsg += "保全受理号：" + tLCContHangUpStateSet.get(1).getStandFlag2();
	// }
	// }
	// }
	// else if (OtherNoType.equals("1")) //个人客户号
	// {
	// sErrorMsg = "该客户有相关保单被" + sHangUpReason + "挂起,不能受理保全申请! 保单号：" +
	// sContNo;
	// if (tLCContHangUpStateSet.get(1).getStandFlag2() != null &&
	// !tLCContHangUpStateSet.get(1).getStandFlag2().trim().equals(""))
	// {
	// if (tLCContHangUpStateSet.get(1).getStandFlag2().substring(1,
	// 2).equals("4"))
	// {
	// sErrorMsg += "试算流水号：" + tLCContHangUpStateSet.get(1).getStandFlag2();
	// }
	// else
	// {
	// sErrorMsg += "保全受理号：" + tLCContHangUpStateSet.get(1).getStandFlag2();
	// }
	// }
	// }
	// //保单保全状态被挂起
	// CError.buildErr(this, sErrorMsg);
	// return false;
	// }
	// else if (tLCContHangUpStateSet.get(1).getPosFlag().equals("0"))
	// {
	// return true;
	// }
	// }
	// return true;
	// }

	/**
	 * 校验保单与操作员管理机构
	 * 
	 * @return boolean
	 */
	private int checkManageCom(String sContManageCom, String sOperatorManageCom) {
		int iLengthCont = sContManageCom.trim().length();
		int iLengthOperator = sOperatorManageCom.trim().length();
		if (iLengthCont < iLengthOperator) // 上属机构
		{
			// CError.buildErr(this, "您无权受理上属机构保单保全申请!");
			return -1;
		}
		if (iLengthOperator > 6) {
			iLengthOperator = 6; // 管理机构只比较前六位
		}
		String sContManageComTrim = sContManageCom.trim().substring(0,
				iLengthOperator);
		if (!sContManageComTrim.equals(sOperatorManageCom.substring(0,
				iLengthOperator))) {
			// CError.buildErr(this, "您无权受理其他机构保单保全申请!");
			return 0;
		} else {
			return 1;
		}
	}

	// /**
	// * 校验银行账户信息是否完整(此校验已经转移到PEdorAcceptAppConfirmBL中，以该类中的此校验为准)
	// * @param pLPEdorAppSchema
	// * @return boolean
	// */
	// private boolean checkBankInfo(LPEdorAppSchema pLPEdorAppSchema)
	// {
	// if (pLPEdorAppSchema.getBankCode() != null &&
	// !pLPEdorAppSchema.getBankCode().trim().equals(""))
	// {
	// if (pLPEdorAppSchema.getAccName() == null ||
	// pLPEdorAppSchema.getAccName().trim().equals(""))
	// {
	// CError.buildErr(this, "您选择了开户银行，请录入银行账户户名!");
	// return false;
	// }
	// if (pLPEdorAppSchema.getBankAccNo() == null ||
	// pLPEdorAppSchema.getBankAccNo().trim().equals(""))
	// {
	// CError.buildErr(this, "您选择了开户银行，请录入银行账号!");
	// return false;
	// }
	// }
	// if (pLPEdorAppSchema.getAccName() != null &&
	// !pLPEdorAppSchema.getAccName().trim().equals(""))
	// {
	// if (pLPEdorAppSchema.getBankCode() == null ||
	// pLPEdorAppSchema.getBankCode().trim().equals(""))
	// {
	// CError.buildErr(this, "请选择开户银行!");
	// return false;
	// }
	// if (pLPEdorAppSchema.getBankAccNo() == null ||
	// pLPEdorAppSchema.getBankAccNo().trim().equals(""))
	// {
	// CError.buildErr(this, "请录入银行账户账号!");
	// return false;
	// }
	// }
	// if (pLPEdorAppSchema.getBankAccNo() != null &&
	// !pLPEdorAppSchema.getBankAccNo().trim().equals(""))
	// {
	// if (pLPEdorAppSchema.getBankCode() == null ||
	// pLPEdorAppSchema.getBankCode().trim().equals(""))
	// {
	// CError.buildErr(this, "请选择开户银行!");
	// return false;
	// }
	// if (pLPEdorAppSchema.getAccName() == null ||
	// pLPEdorAppSchema.getAccName().trim().equals(""))
	// {
	// CError.buildErr(this, "请录入银行账户户名!");
	// return false;
	// }
	// }
	//
	// //校验领取人信息是否完整
	// if (pLPEdorAppSchema.getPayGetName() != null &&
	// !pLPEdorAppSchema.getPayGetName().trim().equals(""))
	// {
	// if (pLPEdorAppSchema.getPersonID() == null ||
	// pLPEdorAppSchema.getPersonID().trim().equals(""))
	// {
	// CError.buildErr(this, "请录入领取人身份证号!");
	// return false;
	// }
	// }
	// if (pLPEdorAppSchema.getPersonID() != null &&
	// !pLPEdorAppSchema.getPersonID().trim().equals(""))
	// {
	// if (pLPEdorAppSchema.getPayGetName() == null ||
	// pLPEdorAppSchema.getPayGetName().trim().equals(""))
	// {
	// CError.buildErr(this, "请录入领取人姓名!");
	// return false;
	// }
	// }
	//
	// if (pLPEdorAppSchema.getBankAccNo() != null &&
	// !pLPEdorAppSchema.getBankAccNo().trim().equals("") &&
	// pLPEdorAppSchema.getBankCode() != null &&
	// !pLPEdorAppSchema.getBankCode().trim().equals("") &&
	// pLPEdorAppSchema.getAccName() != null &&
	// !pLPEdorAppSchema.getAccName().trim().equals("") &&
	// pLPEdorAppSchema.getOtherNoType().equals("1")) //客户层
	// {
	// if (pLPEdorAppSchema.getPersonID() == null ||
	// pLPEdorAppSchema.getPersonID().trim().equals(""))
	// {
	// CError.buildErr(this, "选择银行收付费方式时必须录入领取人及身份证号码!");
	// return false;
	// }
	// }
	// return true;
	// }

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorNoscanAppBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}
	
	private boolean DealLJSGetDraw(String tContNo) {
		//modify by jiaqiangli 2009-09-29 倒排日期进行催付撤销
		String selSQL = " select * from ljsget where othernotype in ('2') " + " and otherno = '" + "?tContNo?" + "' order by getdate desc,getnoticeno ";
		LJSGetDB tLJSGetDB = new LJSGetDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(selSQL);
		sqlbv.put("tContNo", tContNo);
		LJSGetSet tLJSGetSet = tLJSGetDB.executeQuery(sqlbv);
		if (tLJSGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "生存金催付信息查询失败!");
			return false;
		}
		LJSGetSchema aLJSGetSchema = new LJSGetSchema();
//		if (tLJSGetSet != null && tLJSGetSet.size() > 0) {
//			aLJSGetSchema = tLJSGetSet.get(1);
//		}
		if (tLJSGetSet != null && tLJSGetSet.size() > 0) {
			for (int i=1;i<=tLJSGetSet.size();i++) {
				//分记录进行催付撤销
				aLJSGetSchema = tLJSGetSet.get(i);
				
				VData tVData = new VData();
				tVData.add(aLJSGetSchema);
				tVData.add(mGlobalInput);
	
				PersonPayPlanCancelUI tPersonPayPlanCancelUI = new PersonPayPlanCancelUI();
				if (!tPersonPayPlanCancelUI.submitData(tVData, "DELETE")) {
					CError.buildErr(this, "生存金催付撤销失败！原因是：" + tPersonPayPlanCancelUI.mErrors.getFirstError());
					return false;
				}
			}
		}
		return true;
	}

	/** 保全申请。删除续期催收 */
	private boolean DealLJSPay(String tContNo) {

		String selSQL = " select * from ljspay where othernotype in ('2') "
				+ " and otherno = '" + "?tContNo?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(selSQL);
		sqlbv.put("tContNo", tContNo);
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);
		if (tLJSPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "续期应收信息查询失败!");
			return false;
		}
		LJSPaySchema aLJSPaySchema = new LJSPaySchema();
		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
			aLJSPaySchema = tLJSPaySet.get(1);
		}
		
		String tSQL = " select * from ljtempfee where tempfeeno='" + "?tempfeeno?"
		+ "' " + " and confdate is null ";
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tSQL);
		sbv.put("tempfeeno", aLJSPaySchema.getGetNoticeNo());
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sbv);

		if (tLJTempFeeDB.mErrors.needDealError()) {
			mErrors.addOneError("暂交费收据号码[" + aLJSPaySchema.getGetNoticeNo() + "]的暂交费信息出错!!!"
					+ tLJTempFeeDB.mErrors.getError(0).errorMessage);
			return false;
		}
		if(tLJTempFeeSet.size()>0)
		{
			CError.buildErr(this, "存在续期暂收，暂时不能进行保全！可以做续期暂收退费或者是续期核销后再做保全！");
			return false;
		}
		if(tLJSPaySet.size() > 0)
		{
			LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
			tLCContSchema.setContNo(aLJSPaySchema.getOtherNo());
			
			VData tVData = new VData();
			tVData.add(tLCContSchema);
			tVData.add(mGlobalInput);
			//此处与万能险有冲突
//			IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
//			if(!tIndiDueFeeCancelBL.submitData(tVData, "BQDelete"))
//			{
//				CError.buildErr(this, "续期撤销失败！原因是：" +tIndiDueFeeCancelBL.mErrors.getFirstError());
//				return false;
//			}
//			map.add(tIndiDueFeeCancelBL.getMap());
		}
		

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
	private boolean getInputData() {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return null;
	}
}
