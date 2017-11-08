/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPayRuleFactoryDB;
import com.sinosoft.lis.db.LCPayRuleParamsDB;
import com.sinosoft.lis.db.LMFactoryModeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPayRuleParamsSchema;
import com.sinosoft.lis.schema.LMFactoryModeSchema;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.lis.vschema.LCPayRuleParamsSet;
import com.sinosoft.lis.vschema.LMFactoryModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class LCPayRuleFactoryBL {
private static Logger logger = Logger.getLogger(LCPayRuleFactoryBL.class);
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
	private LCPayRuleFactorySet mLCPayRuleFactorySet = new LCPayRuleFactorySet();
	private LCPayRuleFactorySet mOldLCPayRuleFactorySet = new LCPayRuleFactorySet();
	private LCPayRuleFactorySchema mOldLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
	private LCPayRuleParamsSchema mLCPayRuleParamsSchema = new LCPayRuleParamsSchema();
	private LCPayRuleParamsSet mLCPayRuleParamsSet = new LCPayRuleParamsSet();
	private LCPayRuleParamsSet mOldLCPayRuleParamsSet = new LCPayRuleParamsSet();
	private String mGrpContNo = "";
	private String mRiskCode = "";
	private String mFactoryType = "";
	private String mFactoryCode = "";
	private int mFactorySubCode = 0;

	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值
	String mCurrentTime = PubFun.getCurrentTime();

	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
	public LCPayRuleFactoryBL() {
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
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LCPayRuleFactoryBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start LCPayRuleFactoryBL Submit...");
		LCPayRuleFactoryBLS tLCPayRuleFactoryBLS = new LCPayRuleFactoryBLS();
		tLCPayRuleFactoryBLS.submitData(mInputData, cOperate);
		logger.debug("End LCPayRuleFactoryBL Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCPayRuleFactoryBLS.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPayRuleFactoryBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBL";
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
		if (this.mOperate.compareTo("INSERT||MAIN") == 0
				|| this.mOperate.compareTo("UPDATE||MAIN") == 0) {
			// 循环要素计算Sql集合，准备要素计算Sql中的计算子要素信息
			for (int i = 1; i <= mLCPayRuleFactorySet.size(); i++) {
				LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
				tLCPayRuleFactorySchema = mLCPayRuleFactorySet.get(i);
				if (prepareNewData(tLCPayRuleFactorySchema, i) == false) {
					return false;
				}
			}

			if (this.mOperate.compareTo("UPDATE||MAIN") == 0) {
				// 准备需要删除的数据
				if (prepareOldData() == false) {
					return false;
				}

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
		this.mLCPayRuleFactorySet.set((LCPayRuleFactorySet) cInputData
				.getObjectByObjectName("LCPayRuleFactorySet", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mGrpContNo = (String) cInputData.get(2);
		this.mOldLCPayRuleFactorySchema
				.setSchema((LCPayRuleFactorySchema) cInputData
						.getObjectByObjectName("LCPayRuleFactorySchema", 1));

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
			this.mInputData.add(this.mLCPayRuleFactorySet); // 需要保存的缴费规则数据
			this.mInputData.add(this.mLCPayRuleParamsSet); // 需要保存的缴费规则要素
			this.mInputData.add(this.mOldLCPayRuleFactorySet); // 需要删除的旧有缴费规则数据
			this.mInputData.add(this.mOldLCPayRuleParamsSet); // 需要删除的旧有缴费规则要素
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBL";
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
			LCPayRuleFactorySchema tLCPayRuleFactorySchema, int tIndex) {
		// 基本录入信息校验
		mRiskCode = tLCPayRuleFactorySchema.getRiskCode(); // 险种信息
		mFactoryType = tLCPayRuleFactorySchema.getFactoryType(); // 要素类别
		mFactoryCode = tLCPayRuleFactorySchema.getFactoryCode(); // 要素计算编码
		mFactorySubCode = tLCPayRuleFactorySchema.getFactorySubCode(); // 要素计算编码小号
		mGrpContNo = tLCPayRuleFactorySchema.getGrpContNo();

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
					+ tLCPayRuleFactorySchema.getFactoryCode() + "的计算类型为："
					+ tLCPayRuleFactorySchema.getFactoryType() + "的计算Sql失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LMFactoryModeSchema tLMFactoryModeSchema = new LMFactoryModeSchema();
		// 取查询出的要素信息
		tLMFactoryModeSchema = tLMFactoryModeSet.get(1);

		// String tCalSql = tLMFactoryModeSchema.getCalSql();//sql描述部分，需要替换
		int indexModeParams = tLMFactoryModeSchema.getParams().indexOf(","); // 模板，位置
		int indexRelaParams = tLCPayRuleFactorySchema.getParams().indexOf(","); // 实际数据，位置
		String[] tParams = null;
		String[] tRelParams = null;

		String tNewCalSql = tLMFactoryModeSchema.getCalSql().trim();
		// 表示参数为一个的情况下
		if (indexModeParams == -1 && indexRelaParams == -1) {
			tNewCalSql = StrTool.replace(tNewCalSql, "?"
					+ tLMFactoryModeSchema.getParams() + "?",
					tLCPayRuleFactorySchema.getParams());
		} else

		// 参数为多个的情况下
		if (indexModeParams != -1 && indexRelaParams != -1) {
			// 根据，拆分字符串，返回数组
			tParams = PubFun.split(tLMFactoryModeSchema.getParams(), ",");
			tRelParams = PubFun.split(tLCPayRuleFactorySchema.getParams(), ",");

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
						+ tLCPayRuleFactorySchema.getFactoryCode() + "的计算类型为："
						+ tLCPayRuleFactorySchema.getFactoryType()
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
					+ tLCPayRuleFactorySchema.getFactoryCode() + "的计算类型为："
					+ tLCPayRuleFactorySchema.getFactoryType()
					+ "的计算Sql描述中的参数个数不同!";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 准备计算要素信息数据
		mLCPayRuleFactorySet.get(tIndex).setCalSql(tNewCalSql);
		mLCPayRuleFactorySet.get(tIndex).setCalRemark(
				tLMFactoryModeSchema.getCalRemark());
		mLCPayRuleFactorySet.get(tIndex)
				.setInerSerialNo(String.valueOf(tIndex));
		mLCPayRuleFactorySet.get(tIndex).setOperator(mGlobalInput.Operator);
		mLCPayRuleFactorySet.get(tIndex).setMakeDate(mCurrentDate); // 当前值
		mLCPayRuleFactorySet.get(tIndex).setMakeTime(mCurrentTime);
		mLCPayRuleFactorySet.get(tIndex).setModifyDate(mCurrentDate); // 当前值
		mLCPayRuleFactorySet.get(tIndex).setModifyTime(mCurrentTime);

		// 如果数组不为空，表示参数有多个，对应的LCPayRuleParam表数据有多条
		if (tRelParams != null && tRelParams != null) {
			for (int i = 0; i < tRelParams.length; i++) {
				mLCPayRuleParamsSchema = new LCPayRuleParamsSchema();

				// 准备计算要素参数信息数据
				mLCPayRuleParamsSchema.setGrpContNo(mGrpContNo);
				mLCPayRuleParamsSchema.setGrpPolNo(tLCPayRuleFactorySchema
						.getGrpPolNo());
				mLCPayRuleParamsSchema.setPayRuleCode(tLCPayRuleFactorySchema
						.getPayRuleCode());
				mLCPayRuleParamsSchema.setPayRuleName(tLCPayRuleFactorySchema
						.getPayRuleName());
				mLCPayRuleParamsSchema.setRiskCode(tLCPayRuleFactorySchema
						.getRiskCode());
				mLCPayRuleParamsSchema.setFactoryType(tLCPayRuleFactorySchema
						.getFactoryType());
				mLCPayRuleParamsSchema.setFactoryCode(tLCPayRuleFactorySchema
						.getFactoryCode());
				mLCPayRuleParamsSchema.setOtherNo(tLCPayRuleFactorySchema
						.getOtherNo());
				mLCPayRuleParamsSchema.setFactoryName(tLCPayRuleFactorySchema
						.getFactoryName());
				mLCPayRuleParamsSchema.setInerSerialNo(String.valueOf(tIndex));
				mLCPayRuleParamsSchema
						.setFactorySubCode(tLCPayRuleFactorySchema
								.getFactorySubCode());
				mLCPayRuleParamsSchema.setParamName(tParams[i]);
				mLCPayRuleParamsSchema.setParam(tRelParams[i]);
				mLCPayRuleParamsSchema.setOperator(mGlobalInput.Operator);
				mLCPayRuleParamsSchema.setMakeDate(mCurrentDate); // 当前值
				mLCPayRuleParamsSchema.setMakeTime(mCurrentTime);
				mLCPayRuleParamsSchema.setModifyDate(mCurrentDate); // 当前值
				mLCPayRuleParamsSchema.setModifyTime(mCurrentTime);

				mLCPayRuleParamsSet.add(mLCPayRuleParamsSchema);
			}
		} else {
			mLCPayRuleParamsSchema = new LCPayRuleParamsSchema();

			// 准备计算要素参数信息数据
			mLCPayRuleParamsSchema.setGrpContNo(mGrpContNo);
			mLCPayRuleParamsSchema.setGrpPolNo(tLCPayRuleFactorySchema
					.getGrpPolNo());
			mLCPayRuleParamsSchema.setPayRuleCode(tLCPayRuleFactorySchema
					.getPayRuleCode());
			mLCPayRuleParamsSchema.setPayRuleName(tLCPayRuleFactorySchema
					.getPayRuleName());
			mLCPayRuleParamsSchema.setRiskCode(tLCPayRuleFactorySchema
					.getRiskCode());
			mLCPayRuleParamsSchema.setFactoryType(tLCPayRuleFactorySchema
					.getFactoryType());
			mLCPayRuleParamsSchema.setFactoryCode(tLCPayRuleFactorySchema
					.getFactoryCode());
			mLCPayRuleParamsSchema.setOtherNo(tLCPayRuleFactorySchema
					.getOtherNo());
			mLCPayRuleParamsSchema.setFactoryName(tLCPayRuleFactorySchema
					.getFactoryName());
			mLCPayRuleParamsSchema.setInerSerialNo(String.valueOf(tIndex));
			mLCPayRuleParamsSchema.setFactorySubCode(tLCPayRuleFactorySchema
					.getFactorySubCode());
			mLCPayRuleParamsSchema.setParamName(tLMFactoryModeSchema
					.getParams());
			mLCPayRuleParamsSchema
					.setParam(tLCPayRuleFactorySchema.getParams());
			mLCPayRuleParamsSchema.setOperator(mGlobalInput.Operator);
			mLCPayRuleParamsSchema.setMakeDate(mCurrentDate); // 当前值
			mLCPayRuleParamsSchema.setMakeTime(mCurrentTime);
			mLCPayRuleParamsSchema.setModifyDate(mCurrentDate); // 当前值
			mLCPayRuleParamsSchema.setModifyTime(mCurrentTime);

			mLCPayRuleParamsSet.add(mLCPayRuleParamsSchema);
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
		LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
		LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactoryDB.setGrpContNo(mGrpContNo);

		if (mOldLCPayRuleFactorySchema != null) {
			tLCPayRuleFactorySchema = mOldLCPayRuleFactorySchema;
		}
		tLCPayRuleFactoryDB.setPayRuleCode(tLCPayRuleFactorySchema
				.getPayRuleCode());

		tLCPayRuleFactorySet = tLCPayRuleFactoryDB.query();
		if (tLCPayRuleFactorySet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询旧的要素项目信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLCPayRuleFactorySet.size() > 0) {
			mOldLCPayRuleFactorySet.add(tLCPayRuleFactorySet);

			// 删除旧的计算要素参数信息
		}
		LCPayRuleParamsDB tLCPayRuleParamsDB = new LCPayRuleParamsDB();
		LCPayRuleParamsSet tLCPayRuleParamSet = new LCPayRuleParamsSet();
		tLCPayRuleParamsDB.setGrpContNo(mGrpContNo);
		tLCPayRuleParamsDB.setPayRuleCode(tLCPayRuleFactorySchema
				.getPayRuleCode());
		tLCPayRuleParamSet = tLCPayRuleParamsDB.query();
		if (tLCPayRuleParamSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpHealthFactorySaveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询旧的计算要素参数信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLCPayRuleParamSet.size() > 0) {
			mOldLCPayRuleParamsSet.add(tLCPayRuleParamSet);
		}
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactorySchema = mLCPayRuleFactorySet.get(1);
		// 如果是删除或修改操作，需要校验是否有被保人拥有该规则，如果有，则该规则不允许删除、修改
		if (this.mOperate.compareTo("DELETE||MAIN") == 0
				|| this.mOperate.compareTo("UPDATE||MAIN") == 0) {
			String tSql = "";
			ExeSQL texeSQL = new ExeSQL();
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			tSql = "select count(*) from lcpol where grpcontno = '?mGrpContNo?' " 
					+ " and riskcode = '?RiskCode?'"
					+ " and ascriptionrulecode = '?PayRuleCode?'";
			sqlbv.sql(tSql);
			sqlbv.put("mGrpContNo", mGrpContNo);
			sqlbv.put("RiskCode", tLCPayRuleFactorySchema.getRiskCode());
			sqlbv.put("PayRuleCode", tLCPayRuleFactorySchema.getPayRuleCode());
			SSRS ssrs = texeSQL.execSQL(sqlbv);
			if (Integer.parseInt(ssrs.GetText(1, 1)) > 0) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该团单" + this.mGrpContNo + "的"
						+ tLCPayRuleFactorySchema.getRiskCode()
						+ "险种下，有被保人使用该缴费规则！";
				this.mErrors.addOneError(tError);
				return false;

			}
		}
		if (this.mOperate.compareTo("INSERT||MAIN") == 0
				|| (this.mOperate.compareTo("UPDATE||MAIN") == 0
						&& mOldLCPayRuleFactorySchema != null && !mOldLCPayRuleFactorySchema
						.getPayRuleCode().equalsIgnoreCase(
								tLCPayRuleFactorySchema.getPayRuleCode()))) {
			LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
			LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
			tLCPayRuleFactoryDB.setGrpContNo(mGrpContNo);
			// tLCAscriptionRuleFactoryDB.setRiskCode(
			// tLCAscriptionRuleFactorySchema.getRiskCode());
			tLCPayRuleFactoryDB.setPayRuleCode(tLCPayRuleFactorySchema
					.getPayRuleCode());
			tLCPayRuleFactorySet = tLCPayRuleFactoryDB.query();
			if (tLCPayRuleFactorySet.size() > 0) {
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该团单" + mGrpContNo + "的"
						+ tLCPayRuleFactorySchema.getRiskCode()
						+ "险种下已经存在该缴费规则"
						+ tLCPayRuleFactorySchema.getPayRuleCode() + "！";
				this.mErrors.addOneError(tError);
				return false;
			}

		}
		return true;
	}

}
