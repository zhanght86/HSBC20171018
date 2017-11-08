package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDImpartParamDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LDImpartParamSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:团单要约信息录入
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author sunxy
 * @version 1.0
 */

public class CustomerImpartBL {
private static Logger logger = Logger.getLogger(CustomerImpartBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private Reflections ref = new Reflections();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;

	/** 业务数据操作字符串 */
	private String mGrpPolNo;
	private String mPolNo;
	private String mContNo;
	private String mRiskCode;

	/** 告知项目表 */
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCCustomerImpartParamsSet mLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();

	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值
	String mCurrentTime = PubFun.getCurrentTime();

	public CustomerImpartBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验处理
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (!cOperate.equals("IMPART||DEAL")) {
			logger.debug("Start SysUWNoticeBL Submit...");
			// 公共提交
			PubSubmit tPubSubmit = new PubSubmit();
			if (tPubSubmit.submitData(mResult, "") == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		int impartCount = mLCCustomerImpartSet.size();
		for (int i = 1; i <= impartCount; i++) {
			LCCustomerImpartSchema tImpart = mLCCustomerImpartSet.get(i);
			String impartCode = tImpart.getImpartCode();
			String impartVer = tImpart.getImpartVer();
			String customerNoType = tImpart.getCustomerNoType();
			String customerNo = tImpart.getCustomerNo();
			for (int j = i + 1; j <= impartCount; j++) {
				LCCustomerImpartSchema tImpart1 = mLCCustomerImpartSet.get(j);
				String impartCode1 = tImpart1.getImpartCode();
				String impartVer1 = tImpart1.getImpartVer();
				String customerNoType1 = tImpart1.getCustomerNoType();
				String customerNo1 = tImpart.getCustomerNo();
				if (impartCode.equals(impartCode1)
						&& impartVer.equals(impartVer1)
						&& customerNoType.equals(customerNoType1)
						&& customerNo.equals(customerNo1)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "CustomerImpartBL";
					tError.functionName = "checkData";
					tError.errorMessage = "客户告知录入重复。";
					this.mErrors.addOneError(tError);

					return false;
				}
				// end of if
			}
			// end of for
		}
		// end of for

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mOperate.equals("IMPART||INSERT")
				|| mOperate.equals("IMPART||DEAL")) {

			if (prepareImpart() == false) {
				return false;
			}
		}
		// if(mOperate.equals("delete") )
		// {
		// // 准备计算要素信息
		// for(int i=1;i<=mLCCustomerImpartSet.size() ;i++)
		// {
		// LCCustomerImpartSchema tLCCustomerImpartSchema = new
		// LCCustomerImpartSchema();
		// tLCCustomerImpartSchema=mLCCustomerImpartSet.get(i) ;
		// if (prepareImpartDelete(tLCCustomerImpartSchema,i) == false)
		// return false;
		// }
		// }

		return true;

	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mOperate = cOperate;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// mTransferData = (TransferData) cInputData.getObjectByObjectName(
		// "TransferData", 0);
		mInputData = cInputData;

		if (mOperate == null
				|| (!mOperate.equals("IMPART||INSERT")
						&& !mOperate.equals("IMPART||DELETE") && !mOperate
						.equals("IMPART||DEAL"))) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "CustomerImpartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据操作类型Operate失败(必须为save 或delete或DEAL)!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "CustomerImpartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "CustomerImpartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "CustomerImpartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		// 获得业务数据
		// if (mTransferData == null) {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "CustomerImpartBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输业务数据失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// 获得告知信息项目
		mLCCustomerImpartSet = (LCCustomerImpartSet) cInputData
				.getObjectByObjectName("LCCustomerImpartSet", 0);
		if (mLCCustomerImpartSet == null || mLCCustomerImpartSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到任何告知信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 逐条准备告知信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareImpart() {

		for (int i = 1; i <= mLCCustomerImpartSet.size(); i++) {
			LCCustomerImpartSchema tLCCustomerImpartSchema = mLCCustomerImpartSet
					.get(i);
			String mProposalContNo = tLCCustomerImpartSchema
					.getProposalContNo();
			String mImpartCode = tLCCustomerImpartSchema.getImpartCode();
			String mImpartVer = tLCCustomerImpartSchema.getImpartVer();
			String mCustomerNo = tLCCustomerImpartSchema.getCustomerNo();
			String mCustomerNoType = tLCCustomerImpartSchema
					.getCustomerNoType();
			String mImpartContent = tLCCustomerImpartSchema.getImpartContent();
			String mImpartParamModle = tLCCustomerImpartSchema
					.getImpartParamModle();
			if (mProposalContNo == null || mImpartCode == null
					|| mImpartVer == null || mCustomerNo == null
					|| mCustomerNoType == null || mImpartContent == null
					|| mImpartParamModle == null || mProposalContNo.equals("")
					|| mImpartCode.equals("") || mImpartVer.equals("")
					|| mCustomerNo.equals("") || mImpartParamModle.equals("")
					|| mCustomerNoType.equals("") || mImpartContent.equals("")) { // @@错误处理
				CError tError = new CError();
				tError.moduleName = "CustomerImpartBL";
				tError.functionName = "prepareHealth";
				tError.errorMessage = "第" + i + "条的告知基本信息（客户号，告知号码等）录入出错";
				this.mErrors.addOneError(tError);
				return false;
			}
			LDImpartParamSet tLDImpartParamSet = new LDImpartParamSet();
			LDImpartParamDB tLDImpartParamDB = new LDImpartParamDB();
			tLDImpartParamDB.setImpartCode(mImpartCode);
			tLDImpartParamDB.setImpartVer(mImpartVer);
			tLDImpartParamSet.set(tLDImpartParamDB.query());
			if (tLDImpartParamDB.mErrors.needDealError()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "CustomerImpartBL";
				tError.functionName = "prepareHealth";
				tError.errorMessage = "第" + i + "条的告知版别或者告知编码录入出错";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 告知信息参数表

			String[] mImpartParams = mImpartParamModle.split(",");
			if (mImpartParams.length > 1
					&& (mImpartParams.length != tLDImpartParamSet.size())) {
				CError tError = new CError();
				tError.moduleName = "CustomerImpartBL";
				tError.functionName = "prepareHealth";
				tError.errorMessage = "第" + i + "条的告知录入的参数数目与所需要的参数数目不一致！";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int j = 1; j <= tLDImpartParamSet.size(); j++) {
				LCCustomerImpartParamsSchema mLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				ref.transFields(mLCCustomerImpartParamsSchema,
						tLCCustomerImpartSchema);
				ref.transFields(mLCCustomerImpartParamsSchema,
						tLDImpartParamSet.get(j));
				mLCCustomerImpartParamsSchema
						.setImpartParam(mImpartParams[j - 1]);
				mLCCustomerImpartParamsSchema.setOperator(mOperater);
				mLCCustomerImpartParamsSchema.setMakeDate(mCurrentDate);
				mLCCustomerImpartParamsSchema.setMakeTime(mCurrentTime);
				mLCCustomerImpartParamsSchema.setModifyDate(mCurrentDate);
				mLCCustomerImpartParamsSchema.setModifyTime(mCurrentTime);
				mLCCustomerImpartParamsSet.add(mLCCustomerImpartParamsSchema);
			}
			tLCCustomerImpartSchema.setOperator(mOperater);
			tLCCustomerImpartSchema.setMakeDate(mCurrentDate);
			tLCCustomerImpartSchema.setMakeTime(mCurrentTime);
			tLCCustomerImpartSchema.setModifyDate(mCurrentDate);
			tLCCustomerImpartSchema.setModifyTime(mCurrentTime);

		}
		return true;
	} //

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();

		// 添加新的计算要素信息数据
		if (mLCCustomerImpartParamsSet != null
				&& mLCCustomerImpartParamsSet.size() > 0) {
			mResult.add(mLCCustomerImpartParamsSet);
		}

		// 添加新的计算要素参数信息数据
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			mResult.add(mLCCustomerImpartSet);
		}
		return true;
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 错误集
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {

		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		LCCustomerImpartSchema mLCCustomerImpartSchema = new LCCustomerImpartSchema();
		mLCCustomerImpartSchema.setContNo("130110000000197");
		mLCCustomerImpartSchema.setGrpContNo("00000000000000000000");
		mLCCustomerImpartSchema.setPrtNo("20022589000089");
		mLCCustomerImpartSchema.setProposalContNo("130110000000197");
		mLCCustomerImpartSchema.setCustomerNo("86110020040990000054");
		mLCCustomerImpartSchema.setImpartVer("001");
		mLCCustomerImpartSchema.setImpartCode("000");
		mLCCustomerImpartSchema.setImpartContent("asdfasdfasd");
		mLCCustomerImpartSchema.setImpartParamModle("asdfasdf");
		mLCCustomerImpartSchema.setCustomerNoType("A");

		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
		mLCCustomerImpartSet.add(mLCCustomerImpartSchema);
		CustomerImpartBL mCustomerImpartBL = new CustomerImpartBL();
		VData tempVData = new VData();
		tempVData.add(mLCCustomerImpartSet);
		tempVData.add(mGlobalInput);
		mCustomerImpartBL.submitData(tempVData, "IMPART||DEAL");
		if (mCustomerImpartBL.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "dealData";
			tError.errorMessage = mCustomerImpartBL.mErrors.getFirstError()
					.toString();
		}
		tempVData.clear();
		tempVData = mCustomerImpartBL.getResult();

	}
}
