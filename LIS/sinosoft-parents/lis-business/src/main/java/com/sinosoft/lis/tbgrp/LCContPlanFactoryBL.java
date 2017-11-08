/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanFactoryDB;
import com.sinosoft.lis.db.LCContPlanParamDB;
import com.sinosoft.lis.db.LMFactoryModeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPlanFactorySchema;
import com.sinosoft.lis.schema.LCContPlanParamSchema;
import com.sinosoft.lis.schema.LMFactoryModeSchema;
import com.sinosoft.lis.vschema.LCContPlanFactorySet;
import com.sinosoft.lis.vschema.LCContPlanParamSet;
import com.sinosoft.lis.vschema.LMFactoryModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * 保障计划要素数据准备类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据操作类型，进行数据校验、准备处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 * 
 * @author ZHUXF
 * @version 1.0
 */
public class LCContPlanFactoryBL {
private static Logger logger = Logger.getLogger(LCContPlanFactoryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LCContPlanFactorySet mLCContPlanFactorySet = new LCContPlanFactorySet();
	private LCContPlanFactorySet mOldLCContPlanFactorySet = new LCContPlanFactorySet();
	private LCContPlanParamSchema mLCContPlanParamSchema = new LCContPlanParamSchema();
	private LCContPlanParamSet mLCContPlanParamSet = new LCContPlanParamSet();
	private LCContPlanParamSet mOldLCContPlanParamSet = new LCContPlanParamSet();
	private String mGrpContNo = "";
	private String mProposalGrpContNo = "";
	private String mRiskCode = "";
	private String mFactoryType = "";
	private String mFactoryCode = "";
	private int mFactorySubCode = 0;

	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值
	String mCurrentTime = PubFun.getCurrentTime();

	public LCContPlanFactoryBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LCContPlanFactoryBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start LCContPlanFactoryBL Submit...");
		LCContPlanFactoryBLS tLCContPlanFactoryBLS = new LCContPlanFactoryBLS();
		tLCContPlanFactoryBLS.submitData(mInputData, cOperate);
		logger.debug("End LCContPlanFactoryBL Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCContPlanFactoryBLS.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContPlanFactoryBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBL";
			tError.functionName = "submitDat";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		boolean tReturn = true;
		// 新增处理
		if (this.mOperate.compareTo("INSERT||MAIN") == 0) {
			// 循环要素计算Sql集合，准备要素计算Sql中的计算子要素信息
			for (int i = 1; i <= mLCContPlanFactorySet.size(); i++) {
				LCContPlanFactorySchema tLCContPlanFactorySchema = new LCContPlanFactorySchema();
				tLCContPlanFactorySchema = mLCContPlanFactorySet.get(i);
				if (!prepareNewData(tLCContPlanFactorySchema, i)) {
					return false;
				}
			}
			// 准备需要删除的数据
			if (!prepareOldData()) {
				return false;
			}
		}
		// 删除处理
		if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
			// 准备需要删除的数据
			if (!prepareOldData()) {
				return false;
			}
		}
		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLCContPlanFactorySet.set((LCContPlanFactorySet) cInputData
				.getObjectByObjectName("LCContPlanFactorySet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mProposalGrpContNo = (String) cInputData.get(2);
		this.mGrpContNo = (String) cInputData.get(3);

		return true;
	}

	/**
	 * 往BLS传送准备好的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData = new VData();
			this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mLCContPlanFactorySet);
			this.mInputData.add(this.mLCContPlanParamSet);
			this.mInputData.add(this.mOldLCContPlanFactorySet);
			this.mInputData.add(this.mOldLCContPlanParamSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 返回集
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 逐条准备计算要素信息
	 * 
	 * @param tLCContPlanFactorySchema
	 *            LCContPlanFactorySchema
	 * @param tIndex
	 *            int
	 * @return boolean
	 */
	private boolean prepareNewData(
			LCContPlanFactorySchema tLCContPlanFactorySchema, int tIndex) {
		// 基本录入信息校验
		mRiskCode = tLCContPlanFactorySchema.getRiskCode(); // 险种信息
		mFactoryType = tLCContPlanFactorySchema.getFactoryType(); // 要素类别
		mFactoryCode = tLCContPlanFactorySchema.getFactoryCode(); // 要素计算编码
		mFactorySubCode = tLCContPlanFactorySchema.getFactorySubCode(); // 要素计算编码小号
		// mProposalGrpContNo = tLCContPlanFactorySchema.getProposalGrpContNo();
		// mGrpContNo = tLCContPlanFactorySchema.getGrpContNo();

		// 取要素描述信息
		LMFactoryModeDB tLMFactoryModeDB = new LMFactoryModeDB();
		LMFactoryModeSet tLMFactoryModeSet = new LMFactoryModeSet();

		tLMFactoryModeDB.setRiskCode(mRiskCode);
		tLMFactoryModeDB.setFactoryType(mFactoryType);
		tLMFactoryModeDB.setFactoryCode(mFactoryCode);
		tLMFactoryModeDB.setFactorySubCode(mFactorySubCode);
		tLMFactoryModeSet = tLMFactoryModeDB.query();
		// 如果集合为空，或者集合数不唯一，则数据异常
		if (tLMFactoryModeSet == null || tLMFactoryModeSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpHealthFactorySaveBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取计算编码为："
					+ tLCContPlanFactorySchema.getFactoryCode() + "的计算类型为："
					+ tLCContPlanFactorySchema.getFactoryType() + "的计算Sql失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LMFactoryModeSchema tLMFactoryModeSchema = new LMFactoryModeSchema();
		// 取查询出的要素信息
		tLMFactoryModeSchema = tLMFactoryModeSet.get(1);

		// String tCalSql = tLMFactoryModeSchema.getCalSql();//sql描述部分，需要替换
		int indexModeParams = tLMFactoryModeSchema.getParams().indexOf(","); // 模板，位置
		int indexRelaParams = tLCContPlanFactorySchema.getParams().indexOf(","); // 实际数据，位置
		String[] tParams = null;
		String[] tRelParams = null;

		String tNewCalSql = tLMFactoryModeSchema.getCalSql().trim();
		// 表示参数为一个的情况下
		if (indexModeParams == -1 && indexRelaParams == -1) {
			tNewCalSql = StrTool.replace(tNewCalSql, "?"
					+ tLMFactoryModeSchema.getParams() + "?",
					tLCContPlanFactorySchema.getParams());
		} else

		// 参数为多个的情况下
		if (indexModeParams != -1 && indexRelaParams != -1) {
			// 根据，拆分字符串，返回数组
			tParams = PubFun.split(tLMFactoryModeSchema.getParams(), ",");
			tRelParams = PubFun
					.split(tLCContPlanFactorySchema.getParams(), ",");

			if (tParams.length == tRelParams.length) {
				for (int i = 0; i < tParams.length; i++) {
					tNewCalSql = StrTool.replace(tNewCalSql, "?" + tParams[i]
							+ "?", tRelParams[i]);
				}
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpHealthFactorySaveBL";
				tError.functionName = "prepareHealth";
				tError.errorMessage = "录入参数与计算编码为："
						+ tLCContPlanFactorySchema.getFactoryCode() + "的计算类型为："
						+ tLCContPlanFactorySchema.getFactoryType()
						+ "的计算Sql描述中的参数个数不同!";
				this.mErrors.addOneError(tError);
				return false;

			}

		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpHealthFactorySaveBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "录入参数与计算编码为："
					+ tLCContPlanFactorySchema.getFactoryCode() + "的计算类型为："
					+ tLCContPlanFactorySchema.getFactoryType()
					+ "的计算Sql描述中的参数个数不同!";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 准备计算要素信息数据
		mLCContPlanFactorySet.get(tIndex).setCalSql(tNewCalSql);
		mLCContPlanFactorySet.get(tIndex).setCalRemark(
				tLMFactoryModeSchema.getCalRemark());
		mLCContPlanFactorySet.get(tIndex).setInerSerialNo(
				String.valueOf(tIndex));
		mLCContPlanFactorySet.get(tIndex).setMakeDate(mCurrentDate); // 当前值
		mLCContPlanFactorySet.get(tIndex).setMakeTime(mCurrentTime);
		mLCContPlanFactorySet.get(tIndex).setModifyDate(mCurrentDate); // 当前值
		mLCContPlanFactorySet.get(tIndex).setModifyTime(mCurrentTime);

		// 如果数组不为空，表示参数有多个，对应的LCContPlanParam表数据有多条
		if (tRelParams != null && tRelParams != null) {
			for (int i = 0; i < tRelParams.length; i++) {
				mLCContPlanParamSchema = new LCContPlanParamSchema();

				// 准备计算要素参数信息数据
				mLCContPlanParamSchema.setGrpContNo(mGrpContNo);
				mLCContPlanParamSchema.setContPlanCode(tLCContPlanFactorySchema
						.getContPlanCode());
				mLCContPlanParamSchema.setContPlanName(tLCContPlanFactorySchema
						.getContPlanName());
				mLCContPlanParamSchema.setRiskCode(tLCContPlanFactorySchema
						.getRiskCode());
				mLCContPlanParamSchema.setRiskVersion(tLCContPlanFactorySchema
						.getRiskVersion());
				mLCContPlanParamSchema.setMainRiskCode(tLCContPlanFactorySchema
						.getMainRiskCode());
				mLCContPlanParamSchema
						.setMainRiskVersion(tLCContPlanFactorySchema
								.getMainRiskVersion());
				mLCContPlanParamSchema.setProposalGrpContNo(mProposalGrpContNo);
				mLCContPlanParamSchema.setFactoryType(tLCContPlanFactorySchema
						.getFactoryType());
				mLCContPlanParamSchema.setFactoryCode(tLCContPlanFactorySchema
						.getFactoryCode());
				mLCContPlanParamSchema.setOtherNo(tLCContPlanFactorySchema
						.getOtherNo());
				mLCContPlanParamSchema.setFactoryName(tLCContPlanFactorySchema
						.getFactoryName());
				mLCContPlanParamSchema.setInerSerialNo(String.valueOf(tIndex));
				mLCContPlanParamSchema
						.setFactorySubCode(tLCContPlanFactorySchema
								.getFactorySubCode());
				mLCContPlanParamSchema.setPlanType(tLCContPlanFactorySchema
						.getPlanType());
				mLCContPlanParamSchema.setParamName(tParams[i]);
				mLCContPlanParamSchema.setParam(tRelParams[i]);
				mLCContPlanParamSchema.setMakeDate(mCurrentDate); // 当前值
				mLCContPlanParamSchema.setMakeTime(mCurrentTime);
				mLCContPlanParamSchema.setModifyDate(mCurrentDate); // 当前值
				mLCContPlanParamSchema.setModifyTime(mCurrentTime);

				mLCContPlanParamSet.add(mLCContPlanParamSchema);
			}
		} else {
			mLCContPlanParamSchema = new LCContPlanParamSchema();

			// 准备计算要素参数信息数据
			mLCContPlanParamSchema.setGrpContNo(mGrpContNo);
			mLCContPlanParamSchema.setProposalGrpContNo(mProposalGrpContNo);
			mLCContPlanParamSchema.setContPlanCode(tLCContPlanFactorySchema
					.getContPlanCode());
			mLCContPlanParamSchema.setContPlanName(tLCContPlanFactorySchema
					.getContPlanName());
			mLCContPlanParamSchema.setRiskCode(tLCContPlanFactorySchema
					.getRiskCode());
			mLCContPlanParamSchema.setRiskVersion(tLCContPlanFactorySchema
					.getRiskVersion());
			mLCContPlanParamSchema.setMainRiskCode(tLCContPlanFactorySchema
					.getMainRiskCode());
			mLCContPlanParamSchema.setMainRiskVersion(tLCContPlanFactorySchema
					.getMainRiskVersion());
			mLCContPlanParamSchema.setFactoryType(tLCContPlanFactorySchema
					.getFactoryType());
			mLCContPlanParamSchema.setFactoryCode(tLCContPlanFactorySchema
					.getFactoryCode());
			mLCContPlanParamSchema.setOtherNo(tLCContPlanFactorySchema
					.getOtherNo());
			mLCContPlanParamSchema.setFactoryName(tLCContPlanFactorySchema
					.getFactoryName());
			mLCContPlanParamSchema.setInerSerialNo(String.valueOf(tIndex));
			mLCContPlanParamSchema.setFactorySubCode(tLCContPlanFactorySchema
					.getFactorySubCode());
			mLCContPlanParamSchema.setPlanType(tLCContPlanFactorySchema
					.getPlanType());
			mLCContPlanParamSchema.setParamName(tLMFactoryModeSchema
					.getParams());
			mLCContPlanParamSchema.setParam(tLCContPlanFactorySchema
					.getParams());
			mLCContPlanParamSchema.setMakeDate(mCurrentDate); // 当前值
			mLCContPlanParamSchema.setMakeTime(mCurrentTime);
			mLCContPlanParamSchema.setModifyDate(mCurrentDate); // 当前值
			mLCContPlanParamSchema.setModifyTime(mCurrentTime);

			mLCContPlanParamSet.add(mLCContPlanParamSchema);
		}
		return true;
	}

	/**
	 * 准备删除的要素数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOldData() {
		// 删除旧的要素信息
		LCContPlanFactoryDB tLCContPlanFactoryDB = new LCContPlanFactoryDB();
		LCContPlanFactorySet tLCContPlanFactorySet = new LCContPlanFactorySet();
		tLCContPlanFactoryDB.setGrpContNo(mGrpContNo);
		tLCContPlanFactoryDB.setProposalGrpContNo(mProposalGrpContNo);
		tLCContPlanFactoryDB.setPlanType(mLCContPlanFactorySet.get(1)
				.getPlanType());
		tLCContPlanFactorySet = tLCContPlanFactoryDB.query();
		if (tLCContPlanFactorySet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询旧的要素项目信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLCContPlanFactorySet.size() > 0) {
			mOldLCContPlanFactorySet.add(tLCContPlanFactorySet);

			// 删除旧的计算要素参数信息
		}
		LCContPlanParamDB tLCContPlanParamDB = new LCContPlanParamDB();
		LCContPlanParamSet tLCContPlanParamSet = new LCContPlanParamSet();
		tLCContPlanParamDB.setGrpContNo(mGrpContNo);
		tLCContPlanParamDB.setProposalGrpContNo(mProposalGrpContNo);
		tLCContPlanParamSet = tLCContPlanParamDB.query();
		if (tLCContPlanParamSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpHealthFactorySaveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询旧的计算要素参数信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLCContPlanParamSet.size() > 0) {
			mOldLCContPlanParamSet.add(tLCContPlanParamSet);

		}
		return true;
	}
}
