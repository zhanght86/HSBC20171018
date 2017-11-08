package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.operfee.NewIndiDueFeeBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-17
 */
public class ProposalApproveBL {
private static Logger logger = Logger.getLogger(ProposalApproveBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	/** 保单数据 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// @Constructor
	public ProposalApproveBL() {
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
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");

		// 根据业务逻辑对数据进行处理
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealData---");

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		ProposalApproveBLS tProposalApproveBLS = new ProposalApproveBLS();
		if (tProposalApproveBLS.submitData(mInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tProposalApproveBLS.mErrors);
			return false;
		}
		logger.debug("---commitData---");

		// 判断是否需要新单催收（发首期银行代收（非银行险或银行险时候转账））
		// 银代险无暂交费纪录，不去财务交费。录入投保单后，在复核时根据填写的银行编码和卡号发催收，生成送银行数据
		// 非银代险有暂交费纪录，去财务交费后根据填写的银行编码和卡号发催收，生成送银行数据。因此需要先录入财务，而不能先复核
		// 后流程改为：银代险和非银代险都去财务交费。因此复核不需要再发催收了。
		// try
		// {
		// if(!dealNewPolBankFee())
		// {
		// return false;
		// }
		// }
		// catch(Exception ex)
		// {
		// mErrors.addOneError(ex.toString());
		// logger.debug("复核已通过，但是发首期银行代收时错误："+ex.toString());
		// return false;
		// }
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 保单
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setSchema((LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0));
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tLCPolSchema.getProposalNo());
		if (tLCPolDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "投保单查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema.setSchema(tLCPolDB);
		logger.debug("===ApproveFlag:" + tLCPolSchema.getApproveFlag());

		if (!mLCPolSchema.getApproveFlag().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "投保单不是未复核状态，不能进行该操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema.setApproveFlag(tLCPolSchema.getApproveFlag());

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (!mLCPolSchema.getAppFlag().trim().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此单不是投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!mLCPolSchema.getUWFlag().trim().equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此投保单已经开始核保，不能进行复核操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/**
		 * @todo 查询该次申请的投保单是否已经被其他用户申请
		 */
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionProp1(mLCPolSchema.getPrtNo());
		tLWMissionDB.setActivityID("0000001001");// 复核节点
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.get(1).getDefaultOperator() == null
				|| tLWMissionSet.get(1).getDefaultOperator().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBL";
			tError.functionName = "checkData";
			tError.errorMessage = "此投保单已经被操作员选取，不能重复进行操作!";
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
		// 修改复核人编码和复核日期
		mLCPolSchema.setApproveCode(mGlobalInput.Operator);
		mLCPolSchema.setApproveDate(PubFun.getCurrentDate());
		mLCPolSchema.setModifyDate(PubFun.getCurrentDate());
		mLCPolSchema.setModifyTime(PubFun.getCurrentTime());

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
		mInputData.add(mLCPolSchema);
	}

	/**
	 * 发首期银行代收（非银行险或银行险时候转账）
	 * 
	 * @return
	 */
	private boolean dealNewPolBankFee() {
		if (mLCPolSchema.getPayLocation() == null) {
			return true;
		}

		// 校验描述变量
		// NewPolDunFlag (新单催收标记) 0 不催收 1 只催收个险 3 只催收银行险 9 都催收
		String mSignFlag = "";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("NewPolDunFlag");
		if (tLDSysVarDB.getInfo() == false) {
			mSignFlag = "0";
			return false;
		} else {
			mSignFlag = tLDSysVarDB.getSysVarValue();
			if (mLCPolSchema.getSaleChnl() == null) {
				CError tError = new CError();
				tError.moduleName = "ProposalApproveBL";
				tError.functionName = "dealNewPolBankFee";
				tError.errorMessage = "复核已经成功，但是在发首期银行代收时出现错误：投保单的销售渠道为空！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mSignFlag.equals("3")) {
				if (!mLCPolSchema.getSaleChnl().equals("03")) {
					return true;
				}
			} else {
				return true;
			}

		}

		// 如果是需要处理的类型
		if (mLCPolSchema.getPayLocation().equals("0")
				|| mLCPolSchema.getPayLocation().equals("8")) {
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(mLCPolSchema);
			NewIndiDueFeeBL tNewIndiDueFeeBL = new NewIndiDueFeeBL();
			tNewIndiDueFeeBL.submitData(tVData, "INSERT");
			if (tNewIndiDueFeeBL.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tNewIndiDueFeeBL.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalApproveBL";
				tError.functionName = "dealNewPolBankFee";
				tError.errorMessage = "复核已经成功，但是在发首期银行代收时出现错误："
						+ tNewIndiDueFeeBL.mErrors.getFirstError();
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

}
