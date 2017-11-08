package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class GrpAppModifyMakeSureBL {
private static Logger logger = Logger.getLogger(GrpAppModifyMakeSureBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	/** 保单数据 */
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	/** 复核标记 */
	private String mApproveFlag = "";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpAppModifyMakeSureBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (this.checkData() == false)
			return false;
		logger.debug("---checkData---");

		// 根据业务逻辑对数据进行处理
		if (this.dealData() == false)
			return false;
		logger.debug("---dealData---");

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		GrpAppModifyMakeSureBLS tGrpAppModifyMakeSureBLS = new GrpAppModifyMakeSureBLS();
		if (tGrpAppModifyMakeSureBLS.submitData(mInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpAppModifyMakeSureBLS.mErrors);
			return false;
		}
		logger.debug("---commitData---");

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		String tProposalGrpContNo = "";

		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 保单
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));
		mApproveFlag = tLCGrpContSchema.getApproveFlag();

		logger.debug("mApproveFlag:" + mApproveFlag);
		logger.debug("ProposalGrpContNo:"
				+ tLCGrpContSchema.getProposalGrpContNo());

		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "集体复核中复核员的ＩＤ传入失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mApproveFlag == null || StrTool.cTrim(mApproveFlag).equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "集体复核标记没有传入!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tProposalGrpContNo = tLCGrpContSchema.getProposalGrpContNo();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tProposalGrpContNo);
		if (tLCGrpContDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "集体投保单查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema.setSchema(tLCGrpContDB);

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (!mLCGrpContSchema.getAppFlag().trim().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此集体单不是投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug(" uwflag: " + mLCGrpContSchema.getUWFlag());
		if (!mLCGrpContSchema.getApproveFlag().equals("1")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此集体投保单已经进行核保操作，不能进行复核修改确认操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 确保集体投保单下有个人投保单
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(*) from LCCont " + "where GrpContNo = '"
				+ mLCGrpContSchema.getProposalGrpContNo() + "'";

		String tStr = "";
		double tCount = -1;
		tStr = tExeSQL.getOneValue(sql);
		if (tStr.trim().equals(""))
			tCount = 0;
		else
			tCount = Double.parseDouble(tStr);

		if (tCount <= 0.0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "集体投保单下没有个人投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 确保集体投保单进行复核修改确认操作时,已将所有操作员的问题件已回复
		sql = "select count(*) from LCGrpIssuePol "
				+ "where ProposalGrpContNo = '"
				+ mLCGrpContSchema.getProposalGrpContNo()
				+ "' and backobjtype='1' and replyman is null";
		tCount = -1;
		tStr = tExeSQL.getOneValue(sql);
		if (tStr.trim().equals(""))
			tCount = 0;
		else
			tCount = Double.parseDouble(tStr);

		if (tCount >= 1.0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "集体投保单下有未回复的操作员的问题件，不能进行复核修改确认操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {

		// 修改集体投保单复核人编码和复核日期
		mLCGrpContSchema.setInputOperator(mGlobalInput.Operator);
		mLCGrpContSchema.setApproveFlag(mApproveFlag);
		mLCGrpContSchema.setUWFlag("0");
		mLCGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGrpContSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(mLCGrpContSchema);

	}

}
