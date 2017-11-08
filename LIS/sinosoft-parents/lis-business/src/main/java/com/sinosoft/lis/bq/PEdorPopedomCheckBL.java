/*
 * @(#)PEdorPopedomCheckBL.java 2005-12-8
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全权限校验业务逻辑
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class PEdorPopedomCheckBL implements BusinessService {
private static Logger logger = Logger.getLogger(PEdorPopedomCheckBL.class);
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
	private List mBomList = new ArrayList();
	private Boolean mBomListFlag=false;
	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
	LPEdorItemSchema mLPEdorItemSchema=new LPEdorItemSchema();
	private TransferData mTransferData = new TransferData();

	public PEdorPopedomCheckBL() {
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

		// *******************************************************************
		// mOperate 操作字符串说明：
		// [Apply] - 保全申请确认校验
		// [Approve] - 保全复核通过校验
		// [GApply] - 团体保全申请确认校验
		// [GApprove] - 团体保全复核通过校验
		// *******************************************************************

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		
		mResult.clear();

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

		if (mTransferData == null || mGlobalInput == null) {
			CError.buildErr(this, "传入数据错误!");
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
		return exeCheckRule(mOperate);
	}

	/**
	 * 执行保全权限校验规则
	 * 
	 * @param checkType
	 *            权限校验类型 Apply-保全申请校验 Approve-保全复核校验 GApply-团体保全申请校验
	 *            GApprove-团体保全复核校验
	 * @return: boolean
	 */
	public boolean exeCheckRule(String checkType) {
		String sql = " select calcode, msg from lmcheckfield where fieldname = '?checkType?' order by serialno ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("checkType", checkType);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全权限校验规则查询失败!");
			return false;
		}
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			return true; // 没有权限校验规则
		}

		String sResult; // 计算结果
		String sCalCode = ""; // 计算代码
		String sErroMsg = ""; // 提示信息

		String sEdorAcceptNo = (String) mTransferData
				.getValueByName("EdorAcceptNo");
		String sEdorType = (String) mTransferData.getValueByName("EdorType");
		String sEdorAppDate = (String) mTransferData
				.getValueByName("EdorAppDate");
		String sEdorValiDate = (String) mTransferData
				.getValueByName("EdorValiDate");
		mLPEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
		mLPEdorItemSchema.setEdorType(sEdorType);
		mLPEdorItemSchema.setEdorAppDate(sEdorAppDate);
		mLPEdorItemSchema.setOperator(sEdorValiDate);
		mLPEdorItemSchema.setEdorValiDate(mGlobalInput.Operator);
		BqCalBase tBqCalBase=new BqCalBase();
		tBqCalBase.setOperator(mGlobalInput.Operator);
		mPrepareBOMBQEdorBL.setBqCalBase(tBqCalBase);
		mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
		Calculator tCalculator = new Calculator();
		tCalculator.addBasicFactor("EdorAcceptNo", sEdorAcceptNo);
		tCalculator.addBasicFactor("EdorType", sEdorType);
		tCalculator.addBasicFactor("EdorAppDate", sEdorAppDate);
		tCalculator.addBasicFactor("EdorValiDate", sEdorValiDate);
		tCalculator.addBasicFactor("Operator", mGlobalInput.Operator);

		for (int j = 1; j <= tSSRS.getMaxRow(); j++) // 循环执行权限校验规则
		{
			sCalCode = tSSRS.GetText(j, 1);
			sErroMsg = tSSRS.GetText(j, 2);

			tCalculator.setCalCode(sCalCode);

			sResult = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				CError.buildErr(this, "保全权限校验规则执行失败!");
				return false;
			}

			if (sResult != null && sResult.equals("1")) {
				continue; // 继续执行下一条校验规则
			} else {
				CError.buildErr(this, sErroMsg); // 权限校验不通过，返回错误信息
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
			mResult.clear();
			mResult.add(map);
			mResult.add(mTransferData);
		} catch (Exception ex) {
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
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
		tG.Operator = "huxy";
		tG.ManageCom = "86";

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("EdorAcceptNo", "6120051219000047");
		VData tVData = new VData();
		tVData.add(mTransferData);
		tVData.add(tG);

		PEdorPopedomCheckBL tPEdorPopedomCheckBL = new PEdorPopedomCheckBL();
		tPEdorPopedomCheckBL.submitData(tVData, "Approve");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
