/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAscriptionRuleFactoryDB;
import com.sinosoft.lis.db.LCAscriptionRuleParamsDB;
import com.sinosoft.lis.db.LMFactoryModeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAscriptionRuleFactorySchema;
import com.sinosoft.lis.schema.LCAscriptionRuleParamsSchema;
import com.sinosoft.lis.schema.LMFactoryModeSchema;
import com.sinosoft.lis.vschema.LCAscriptionRuleFactorySet;
import com.sinosoft.lis.vschema.LCAscriptionRuleParamsSet;
import com.sinosoft.lis.vschema.LMFactoryModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
public class LCAscriptionRuleFactoryBL {
private static Logger logger = Logger.getLogger(LCAscriptionRuleFactoryBL.class);
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
	private LCAscriptionRuleFactorySet mLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
	private LCAscriptionRuleFactorySet mOldLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
	private LCAscriptionRuleParamsSchema mLCAscriptionRuleParamsSchema = new LCAscriptionRuleParamsSchema();
	private LCAscriptionRuleParamsSet mLCAscriptionRuleParamsSet = new LCAscriptionRuleParamsSet();
	private LCAscriptionRuleParamsSet mOldLCAscriptionRuleParamsSet = new LCAscriptionRuleParamsSet();
	private String mGrpContNo = "";
	private String mRiskCode = "";
	private String mFactoryType = "";
	private String mFactoryCode = "";
	private int mFactorySubCode = 0;

	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值
	String mCurrentTime = PubFun.getCurrentTime();

	public LCAscriptionRuleFactoryBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!checkData()) {
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LCAscriptionRuleFactoryBL-->checkData!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LCAscriptionRuleFactoryBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start LCAscriptionRuleFactoryBL Submit...");
		LCAscriptionRuleFactoryBLS tLCAscriptionRuleFactoryBLS = new LCAscriptionRuleFactoryBLS();
		tLCAscriptionRuleFactoryBLS.submitData(mInputData, cOperate);
		logger.debug("End LCAscriptionRuleFactoryBL Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCAscriptionRuleFactoryBLS.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCAscriptionRuleFactoryBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBL";
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
	 */
	private boolean dealData() {
		boolean tReturn = true;
		// 新增处理
		if (this.mOperate.compareTo("DELETE||MAIN") != 0) {
			// 循环要素计算Sql集合，准备要素计算Sql中的计算子要素信息
			for (int i = 1; i <= mLCAscriptionRuleFactorySet.size(); i++) {
				LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
				tLCAscriptionRuleFactorySchema = mLCAscriptionRuleFactorySet
						.get(i);
				if (prepareNewData(tLCAscriptionRuleFactorySchema, i) == false) {
					return false;
				}
			}
			// 准备需要删除的数据
			if (prepareOldData() == false) {
				return false;
			}
		}
		// 删除处理
		if (this.mOperate.compareTo("DELETE||MAIN") == 0) {
			// 准备需要删除的数据
			if (prepareOldData() == false) {
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
		this.mLCAscriptionRuleFactorySet
				.set((LCAscriptionRuleFactorySet) cInputData
						.getObjectByObjectName("LCAscriptionRuleFactorySet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mGrpContNo = (String) cInputData.get(2);

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
			this.mInputData.add(this.mLCAscriptionRuleFactorySet);// 需要保存的缴费规则数据
			this.mInputData.add(this.mLCAscriptionRuleParamsSet);// 需要保存的缴费规则要素
			this.mInputData.add(this.mOldLCAscriptionRuleFactorySet);// 需要删除的旧有缴费规则数据
			this.mInputData.add(this.mOldLCAscriptionRuleParamsSet);// 需要删除的旧有缴费规则要素
			logger.debug("--old factoryset size:"
					+ mOldLCAscriptionRuleFactorySet.size());
			logger.debug("--old paramsset size:"
					+ mOldLCAscriptionRuleParamsSet.size());
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBL";
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
	 * @param tLCFactorySchema
	 *            LCFactorySchema
	 * @param tIndex
	 *            int
	 * @return boolean 如果发生错误则返回false,否则返回true
	 */
	private boolean prepareNewData(
			LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema,
			int tIndex) {
		// 基本录入信息校验
		mRiskCode = tLCAscriptionRuleFactorySchema.getRiskCode(); // 险种信息
		mFactoryType = tLCAscriptionRuleFactorySchema.getFactoryType(); // 要素类别
		mFactoryCode = tLCAscriptionRuleFactorySchema.getFactoryCode(); // 要素计算编码
		mFactorySubCode = tLCAscriptionRuleFactorySchema.getFactorySubCode(); // 要素计算编码小号
		mGrpContNo = tLCAscriptionRuleFactorySchema.getGrpContNo();

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
					+ tLCAscriptionRuleFactorySchema.getFactoryCode()
					+ "的计算类型为："
					+ tLCAscriptionRuleFactorySchema.getFactoryType()
					+ "的计算Sql失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LMFactoryModeSchema tLMFactoryModeSchema = new LMFactoryModeSchema();
		// 取查询出的要素信息
		tLMFactoryModeSchema = tLMFactoryModeSet.get(1);

		// String tCalSql = tLMFactoryModeSchema.getCalSql();//sql描述部分，需要替换
		int indexModeParams = tLMFactoryModeSchema.getParams().indexOf(","); // 模板，位置
		int indexRelaParams = tLCAscriptionRuleFactorySchema.getParams()
				.indexOf(","); // 实际数据，位置
		String[] tParams = null;
		String[] tRelParams = null;

		String tNewCalSql = tLMFactoryModeSchema.getCalSql().trim();
		// 表示参数为一个的情况下
		if (indexModeParams == -1 && indexRelaParams == -1) {
			tNewCalSql = StrTool.replace(tNewCalSql, "?"
					+ tLMFactoryModeSchema.getParams() + "?",
					tLCAscriptionRuleFactorySchema.getParams());
		} else

		// 参数为多个的情况下
		if (indexModeParams != -1 && indexRelaParams != -1) {
			// 根据，拆分字符串，返回数组
			tParams = PubFun.split(tLMFactoryModeSchema.getParams(), ",");
			tRelParams = PubFun.split(tLCAscriptionRuleFactorySchema
					.getParams(), ",");

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
						+ tLCAscriptionRuleFactorySchema.getFactoryCode()
						+ "的计算类型为："
						+ tLCAscriptionRuleFactorySchema.getFactoryType()
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
					+ tLCAscriptionRuleFactorySchema.getFactoryCode()
					+ "的计算类型为："
					+ tLCAscriptionRuleFactorySchema.getFactoryType()
					+ "的计算Sql描述中的参数个数不同!";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 准备计算要素信息数据
		mLCAscriptionRuleFactorySet.get(tIndex).setCalSql(tNewCalSql);
		mLCAscriptionRuleFactorySet.get(tIndex).setCalRemark(
				tLMFactoryModeSchema.getCalRemark());
		mLCAscriptionRuleFactorySet.get(tIndex).setInerSerialNo(
				String.valueOf(tIndex));
		mLCAscriptionRuleFactorySet.get(tIndex).setOperator(
				mGlobalInput.Operator);
		mLCAscriptionRuleFactorySet.get(tIndex).setMakeDate(mCurrentDate); // 当前值
		mLCAscriptionRuleFactorySet.get(tIndex).setMakeTime(mCurrentTime);
		mLCAscriptionRuleFactorySet.get(tIndex).setModifyDate(mCurrentDate); // 当前值
		mLCAscriptionRuleFactorySet.get(tIndex).setModifyTime(mCurrentTime);

		// 如果数组不为空，表示参数有多个，对应的LCAscriptionRuleParam表数据有多条
		if (tRelParams != null && tRelParams != null) {
			for (int i = 0; i < tRelParams.length; i++) {
				mLCAscriptionRuleParamsSchema = new LCAscriptionRuleParamsSchema();

				// 准备计算要素参数信息数据
				mLCAscriptionRuleParamsSchema.setGrpContNo(mGrpContNo);
				mLCAscriptionRuleParamsSchema
						.setGrpPolNo(tLCAscriptionRuleFactorySchema
								.getGrpPolNo());
				mLCAscriptionRuleParamsSchema
						.setAscriptionRuleCode(tLCAscriptionRuleFactorySchema
								.getAscriptionRuleCode());
				mLCAscriptionRuleParamsSchema
						.setAscriptionRuleName(tLCAscriptionRuleFactorySchema
								.getAscriptionRuleName());
				mLCAscriptionRuleParamsSchema
						.setRiskCode(tLCAscriptionRuleFactorySchema
								.getRiskCode());
				mLCAscriptionRuleParamsSchema
						.setFactoryType(tLCAscriptionRuleFactorySchema
								.getFactoryType());
				mLCAscriptionRuleParamsSchema
						.setFactoryCode(tLCAscriptionRuleFactorySchema
								.getFactoryCode());
				mLCAscriptionRuleParamsSchema
						.setOtherNo(tLCAscriptionRuleFactorySchema.getOtherNo());
				mLCAscriptionRuleParamsSchema
						.setFactoryName(tLCAscriptionRuleFactorySchema
								.getFactoryName());
				mLCAscriptionRuleParamsSchema.setInerSerialNo(String
						.valueOf(tIndex));
				mLCAscriptionRuleParamsSchema
						.setFactorySubCode(tLCAscriptionRuleFactorySchema
								.getFactorySubCode());
				mLCAscriptionRuleParamsSchema.setParamName(tParams[i]);
				mLCAscriptionRuleParamsSchema.setParam(tRelParams[i]);
				mLCAscriptionRuleParamsSchema
						.setOperator(mGlobalInput.Operator);
				mLCAscriptionRuleParamsSchema.setMakeDate(mCurrentDate); // 当前值
				mLCAscriptionRuleParamsSchema.setMakeTime(mCurrentTime);
				mLCAscriptionRuleParamsSchema.setModifyDate(mCurrentDate); // 当前值
				mLCAscriptionRuleParamsSchema.setModifyTime(mCurrentTime);

				mLCAscriptionRuleParamsSet.add(mLCAscriptionRuleParamsSchema);
			}
		} else {
			mLCAscriptionRuleParamsSchema = new LCAscriptionRuleParamsSchema();

			// 准备计算要素参数信息数据
			mLCAscriptionRuleParamsSchema.setGrpContNo(mGrpContNo);
			mLCAscriptionRuleParamsSchema
					.setGrpPolNo(tLCAscriptionRuleFactorySchema.getGrpPolNo());
			mLCAscriptionRuleParamsSchema
					.setAscriptionRuleCode(tLCAscriptionRuleFactorySchema
							.getAscriptionRuleCode());
			mLCAscriptionRuleParamsSchema
					.setAscriptionRuleName(tLCAscriptionRuleFactorySchema
							.getAscriptionRuleName());
			mLCAscriptionRuleParamsSchema
					.setRiskCode(tLCAscriptionRuleFactorySchema.getRiskCode());
			mLCAscriptionRuleParamsSchema
					.setFactoryType(tLCAscriptionRuleFactorySchema
							.getFactoryType());
			mLCAscriptionRuleParamsSchema
					.setFactoryCode(tLCAscriptionRuleFactorySchema
							.getFactoryCode());
			mLCAscriptionRuleParamsSchema
					.setOtherNo(tLCAscriptionRuleFactorySchema.getOtherNo());
			mLCAscriptionRuleParamsSchema
					.setFactoryName(tLCAscriptionRuleFactorySchema
							.getFactoryName());
			mLCAscriptionRuleParamsSchema.setInerSerialNo(String
					.valueOf(tIndex));
			mLCAscriptionRuleParamsSchema
					.setFactorySubCode(tLCAscriptionRuleFactorySchema
							.getFactorySubCode());
			mLCAscriptionRuleParamsSchema.setParamName(tLMFactoryModeSchema
					.getParams());
			mLCAscriptionRuleParamsSchema
					.setParam(tLCAscriptionRuleFactorySchema.getParams());
			mLCAscriptionRuleParamsSchema.setOperator(mGlobalInput.Operator);
			mLCAscriptionRuleParamsSchema.setMakeDate(mCurrentDate); // 当前值
			mLCAscriptionRuleParamsSchema.setMakeTime(mCurrentTime);
			mLCAscriptionRuleParamsSchema.setModifyDate(mCurrentDate); // 当前值
			mLCAscriptionRuleParamsSchema.setModifyTime(mCurrentTime);

			mLCAscriptionRuleParamsSet.add(mLCAscriptionRuleParamsSchema);
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
		LCAscriptionRuleFactoryDB tLCAscriptionRuleFactoryDB = new LCAscriptionRuleFactoryDB();
		LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
		LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
		tLCAscriptionRuleFactorySchema = mLCAscriptionRuleFactorySet.get(1);
		tLCAscriptionRuleFactoryDB.setGrpContNo(mGrpContNo);
		tLCAscriptionRuleFactoryDB.setRiskCode(tLCAscriptionRuleFactorySchema
				.getRiskCode());
		tLCAscriptionRuleFactoryDB
				.setAscriptionRuleCode(tLCAscriptionRuleFactorySchema
						.getAscriptionRuleCode());
		tLCAscriptionRuleFactorySet = tLCAscriptionRuleFactoryDB.query();
		logger.debug("size:" + tLCAscriptionRuleFactorySet.size());
		if (tLCAscriptionRuleFactorySet == null
				|| tLCAscriptionRuleFactorySet.size() == 0) {
			if (mOperate.equals("UPDATE||MAIN")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "dealData";
				tError.errorMessage = "未查询到旧的要素项目或如果你修改员工类别，请点增加!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		if (tLCAscriptionRuleFactorySet.size() > 0) {
			mOldLCAscriptionRuleFactorySet.add(tLCAscriptionRuleFactorySet);

			// 删除旧的计算要素参数信息
		}
		LCAscriptionRuleParamsDB tLCAscriptionRuleParamsDB = new LCAscriptionRuleParamsDB();
		LCAscriptionRuleParamsSet tLCAscriptionRuleParamSet = new LCAscriptionRuleParamsSet();
		tLCAscriptionRuleParamsDB.setGrpContNo(mGrpContNo);
		tLCAscriptionRuleParamsDB.setRiskCode(tLCAscriptionRuleFactorySchema
				.getRiskCode());
		tLCAscriptionRuleParamsDB
				.setAscriptionRuleCode(tLCAscriptionRuleFactorySchema
						.getAscriptionRuleCode());
		tLCAscriptionRuleParamSet = tLCAscriptionRuleParamsDB.query();
		logger.debug("param size:" + tLCAscriptionRuleParamSet.size());
		if (tLCAscriptionRuleParamSet == null
				|| tLCAscriptionRuleParamSet.size() == 0) {
			if (mOperate.equals("UPDATE||MAIN")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpHealthFactorySaveBL";
				tError.functionName = "dealData";
				tError.errorMessage = "未查询到旧的要素项目或如果你修改员工类别，请点增加!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		if (tLCAscriptionRuleParamSet.size() > 0) {
			mOldLCAscriptionRuleParamsSet.add(tLCAscriptionRuleParamSet);

		}
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
		tLCAscriptionRuleFactorySchema = mLCAscriptionRuleFactorySet.get(1);
		if (mGrpContNo.substring(0, 2).equals("28")) {
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBL";
			tError.functionName = "checkData";
			tError.errorMessage = "已经签单的保单需要到保全做归属规则变更！";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 如果是删除或修改操作，需要校验是否有被保人拥有该规则，如果有，则该规则不允许删除、修改
		if (this.mOperate.compareTo("DELETE||MAIN") == 0
				|| this.mOperate.compareTo("UPDATE||MAIN") == 0) {

			String tSql = "";
			ExeSQL texeSQL = new ExeSQL();
			tSql = "select count(*) from lcduty where polno in (select polno from lcpol where grpcontno = '"
					+ mGrpContNo
					+ "') "
					+ " and dutycode like  '"
					+ tLCAscriptionRuleFactorySchema.getRiskCode()
					+ "%'"
					+ " and ascriptionrulecode = '"
					+ tLCAscriptionRuleFactorySchema.getAscriptionRuleCode()
					+ "'";
			SSRS ssrs = texeSQL.execSQL(tSql);
			if (Integer.parseInt(ssrs.GetText(1, 1)) > 0) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该团单" + this.mGrpContNo + "的"
						+ tLCAscriptionRuleFactorySchema.getRiskCode()
						+ "险种下，有被保人使用该归属规则！";
				this.mErrors.addOneError(tError);
				return false;

			}
		} else {
			LCAscriptionRuleFactoryDB tLCAscriptionRuleFactoryDB = new LCAscriptionRuleFactoryDB();
			LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
			tLCAscriptionRuleFactoryDB.setGrpContNo(mGrpContNo);
			tLCAscriptionRuleFactoryDB
					.setRiskCode(tLCAscriptionRuleFactorySchema.getRiskCode());
			tLCAscriptionRuleFactoryDB
					.setAscriptionRuleCode(tLCAscriptionRuleFactorySchema
							.getAscriptionRuleCode());
			tLCAscriptionRuleFactorySet = tLCAscriptionRuleFactoryDB.query();
			if (tLCAscriptionRuleFactorySet.size() > 0) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该团单"
						+ mGrpContNo
						+ "的"
						+ tLCAscriptionRuleFactorySchema.getRiskCode()
						+ "险种下已经存在归属规则"
						+ tLCAscriptionRuleFactorySchema
								.getAscriptionRuleCode()
						+ "，如果您要变动，请使用归属规则修改功能！";
				this.mErrors.addOneError(tError);
				return false;
			}

		}
		// 增加逻辑校验
		LCAscriptionRuleFactorySchema ttLCAscriptionRuleFactorySchema;
		String tempParam = "";
		// 增加要素累进校验。
		String compare1 = "";
		String compare2 = "";
		// 增加要素类别校验。
		int n = 0;
		String factorvalue = "";

		for (int i = 1; i <= mLCAscriptionRuleFactorySet.size(); i++) {
			ttLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
			ttLCAscriptionRuleFactorySchema = mLCAscriptionRuleFactorySet
					.get(i);
			tempParam = ttLCAscriptionRuleFactorySchema.getParams();
			String str[] = tempParam.split(",");
			if (str.length != 3) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "第" + i + "行" + "参数填写错误！";
				this.mErrors.addOneError(tError);
				return false;

			}
			if (PubFun.getTenLengNo(str[0]).compareTo(
					PubFun.getTenLengNo(str[1])) > 0
					&& !str[1].equals("b")) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "第" + i + "行" + "参数设置错误！";
				this.mErrors.addOneError(tError);
				return false;

			}

			double tempNo = parseFloat(str[2]);
			if (tempNo == 0) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "第" + i + "行" + "参数设置归属比例错误(介于0与1之间)！";
				this.mErrors.addOneError(tError);
				return false;

			}
			if (str[2].compareTo("1") == 0) {
				String temparam = str[0] + ",100,1";
				mLCAscriptionRuleFactorySet.get(i).setParams(temparam);
			}
			// *****************************累进校验
			if (i == 1) {
				if (!str[0].equals("0")
						&& !ttLCAscriptionRuleFactorySchema.getFactoryType()
								.equals("000008")) {
					CError tError = new CError();
					tError.moduleName = "LCAscriptionRuleFactoryBL";
					tError.functionName = "checkData";
					tError.errorMessage = "归属规则第一行的设置必须为 0！";
					this.mErrors.addOneError(tError);
					return false;

				}
				compare1 = str[1];
				compare2 = str[1];
			}
			if (i > 1) {
				compare2 = str[0];
			}
			// ******************************
			if (compare1.compareTo(compare2) != 0
					&& !compare2.equals("0")
					&& !ttLCAscriptionRuleFactorySchema.getFactoryType()
							.equals("000008")) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "要素之间的期间指定不连续或第二种属性的起始期间不为0！";
				this.mErrors.addOneError(tError);
				return false;

			}
			compare1 = str[1];

			// add factorvalue verify
			if (!ttLCAscriptionRuleFactorySchema.getFactoryType().equals(
					factorvalue)) {
				n = n + 1;
			}
			factorvalue = ttLCAscriptionRuleFactorySchema.getFactoryType();
		}
		if (n > 1) {
			// CError tError = new CError();
			// tError.moduleName = "LCAscriptionRuleFactoryBL";
			// tError.functionName = "checkData";
			// tError.errorMessage = "要素类别要唯一！";
			// this.mErrors.addOneError(tError);
			// return false;

		}
		return true;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tFlag = "0";// 0 is correct
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else {
				tFlag = "1";
			}

		}
		if (tFlag.equals("0")) {
			f1 = Float.parseFloat(tmp);
			if (f1 > 1) {
				tFlag = "1";
			}
		}
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		if (tFlag.equals("1")) {
			return 0; // 非正常数值
		} else {
			return 1;
		}

	}

}
