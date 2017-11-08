/*
 * @(#)GEdorAppBL.java	2005-08-16
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 团体保全-无扫描申请数据校验
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

public class GEdorAppBL implements AfterEndService {
private static Logger logger = Logger.getLogger(GEdorAppBL.class);

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

	public GEdorAppBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("==============GEdorAppBL====================");
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

		if (mLPEdorAppSchema.getOtherNoType().equals("4")) // 团体保单号
		{
			// 校验保单状态
			if (!checkContState(mLPEdorAppSchema.getOtherNo())) {
				return false;
			}

			BqContHangUpBL tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
			// 校验保单保全是否被挂起
			if (!tBqContHangUpBL.checkGrpHangUpState(mLPEdorAppSchema
					.getOtherNo(), mLPEdorAppSchema.getOtherNoType())) {
				mErrors.copyAllErrors(tBqContHangUpBL.mErrors);
				return false;
			}

			if (!tBqContHangUpBL.hangUpGrpCont(mLPEdorAppSchema.getOtherNo(),
					"1", mLPEdorAppSchema.getEdorAcceptNo())) {
				CError.buildErr(this, "保单挂起失败! 保单号："
						+ mLPEdorAppSchema.getOtherNo());
				return false;
			} else {
				MMap tMap = tBqContHangUpBL.getMMap();
				map.add(tMap);
			}

		}
		if (mLPEdorAppSchema.getOtherNoType().equals("2")) // 团体客户号
		{
			LDGrpDB tLDGrpDB = new LDGrpDB();
			tLDGrpDB.setCustomerNo(mLPEdorAppSchema.getOtherNo());
			LDGrpSet tLDGrpSet = tLDGrpDB.query();
			if (tLDGrpDB.mErrors.needDealError()) {
				CError.buildErr(this, "团体客户查询失败!");
				return false;
			}
			if (tLDGrpSet == null || tLDGrpSet.size() != 1) {
				CError.buildErr(this, "该团体客户号不存在!");
				return false;
			}
		}

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

		// 添加保全申请主表记录
		mLPEdorAppSchema.setEdorState("3");
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPEdorAppSchema.getOtherNo());
		if(!tLCGrpContDB.getInfo())
		{
			CError.buildErr(this, "团单信息获取失败!");
			return false;
		}
//		mLPEdorAppSchema.setManageCom(mGlobalInput.ManageCom);
		mLPEdorAppSchema.setManageCom(tLCGrpContDB.getManageCom());
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
	private boolean checkContState(String sGrpContNo) {
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(sGrpContNo);
		//tLCGrpContDB.setAppFlag("1");
		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();
		if (tLCGrpContDB.mErrors.needDealError()) {
			CError.buildErr(this, "团体保单查询失败!");
			return false;
		}
		if (tLCGrpContSet == null || tLCGrpContSet.size() != 1) {
			CError.buildErr(this, "该团体保单号不存在!");
			return false;
		}

		int iMessage = checkManageCom(tLCGrpContSet.get(1).getManageCom(),
				mGlobalInput.ManageCom);
		if (iMessage == -1) {
			CError.buildErr(this, "您无权受理上属机构保单保全申请!");
			return false;
		} else if (iMessage == 0) {
			CError.buildErr(this, "您无权受理其他机构保单保全申请!");
			return false;
		}

		if (!"1".equals(tLCGrpContSet.get(1).getAppFlag())&&!"4".equals(tLCGrpContSet.get(1).getAppFlag())) {

			CError.buildErr(this, "该团体保单还未签单生效或不存在!");
			return false;
		}
		// ====del===zhangtao===2006-07-24=======经新契约确认，团单不需要回单===BGN==========
		// if (mLPEdorAppSchema.getAppType() != null &&
		// mLPEdorAppSchema.getAppType().trim().equals("6"))
		// {
		// //部门转办可以不用校验客户签收日期
		// }
		// else
		// {
		// if (tLCGrpContSet.get(1).getCustomGetPolDate() == null ||
		// tLCGrpContSet.get(1).getCustomGetPolDate().equals(""))
		// {
		// CError.buildErr(this, "该团体保单客户尚未签收!");
		// return false;
		// }
		// int intv =
		// PubFun.calInterval(tLCGrpContSet.get(1).getCustomGetPolDate(),
		// mLPEdorAppSchema.getEdorAppDate(), "D");
		// if (intv < 0)
		// {
		// CError.buildErr(this, "保全申请日期不能早于客户签收日期!");
		// return false;
		// }
		// }
		// ====del===zhangtao===2006-07-24=======经新契约确认，团单不需要回单===END==========
		return true;
	}

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
		String sContManageComTrim = sContManageCom.trim().substring(0,
				iLengthOperator);
		if (!sContManageComTrim.equals(sOperatorManageCom)) {
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
	// CError.buildErr(this, "您选择了开户银行，请录入银行帐户户名!");
	// return false;
	// }
	// if (pLPEdorAppSchema.getBankAccNo() == null ||
	// pLPEdorAppSchema.getBankAccNo().trim().equals(""))
	// {
	// CError.buildErr(this, "您选择了开户银行，请录入银行帐户帐号!");
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
	// CError.buildErr(this, "请录入银行帐户帐号!");
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
	// CError.buildErr(this, "请录入银行帐户户名!");
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
