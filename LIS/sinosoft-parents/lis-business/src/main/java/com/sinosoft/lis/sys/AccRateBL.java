package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMAccGuratRateDB;
import com.sinosoft.lis.db.LMInsuAccRateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMAccGuratRateSchema;
import com.sinosoft.lis.schema.LMInsuAccRateSchema;
import com.sinosoft.lis.vschema.LMAccGuratRateSet;
import com.sinosoft.lis.vschema.LMInsuAccRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class AccRateBL {
private static Logger logger = Logger.getLogger(AccRateBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LMInsuAccRateSchema mLMInsuAccRateSchema = new LMInsuAccRateSchema();
	private LMAccGuratRateSchema mLMAccGuratRateSchema = new LMAccGuratRateSchema();

	public AccRateBL() {
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
		if (!checkDate()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccRateBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败AccRateBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL校验 很多校验已经在前台实现，这里只对日期进行校验 如果在处理过程中出错，则返回false,否则返回true
	 */

	private boolean checkDate() {

		if (mLMAccGuratRateSchema != null) {
			String tStartDate = mLMAccGuratRateSchema.getRateStartDate();
			String tEndDate = mLMAccGuratRateSchema.getRateEndDate();
			if ("".equals(tStartDate)) {
				CError tError = new CError();
				tError.moduleName = "AccRateBL";
				tError.functionName = "checkDate";
				tError.errorMessage = "开始日期不能为空";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!("".equals(tEndDate)) || tEndDate != null) {
				int tIntv = PubFun.calInterval(tStartDate, tEndDate, "D");
				if (tIntv < 0) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "checkDate";
					tError.errorMessage = "截至日期不应在开始日期之前";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			return true;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (mLMInsuAccRateSchema != null) {
			LMInsuAccRateSchema tLMInsuAccRateSchema = new LMInsuAccRateSchema();
			LMInsuAccRateSet tLMInsuAccRateSet = new LMInsuAccRateSet();
			LMInsuAccRateDB tLMInsuAccRateDB = new LMInsuAccRateDB();
			Reflections tReflections = new Reflections();

			if ("update".equals(mOperate)) {
				tLMInsuAccRateDB
						.setRiskCode(mLMInsuAccRateSchema.getRiskCode());
				tLMInsuAccRateDB.setInsuAccNo(mLMInsuAccRateSchema
						.getInsuAccNo());
				tLMInsuAccRateDB
						.setBalaDate(mLMInsuAccRateSchema.getBalaDate());
				tLMInsuAccRateDB
						.setRateIntv(mLMInsuAccRateSchema.getRateIntv());
				tLMInsuAccRateSet = tLMInsuAccRateDB.query();
				if (tLMInsuAccRateSet.size() < 1) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关利率数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tLMInsuAccRateSchema = tLMInsuAccRateSet.get(1);
					tReflections.transFields(tLMInsuAccRateSchema,
							mLMInsuAccRateSchema);
					tLMInsuAccRateSchema.setOperator(mGlobalInput.Operator);
					// tLMInsuAccRateSchema.setRateState("0");

					map.put(tLMInsuAccRateSchema, "UPDATE");
				}
			}
			if ("insert".equals(mOperate)) {
				tLMInsuAccRateDB
						.setRiskCode(mLMInsuAccRateSchema.getRiskCode());
				tLMInsuAccRateDB.setInsuAccNo(mLMInsuAccRateSchema
						.getInsuAccNo());
				tLMInsuAccRateDB
						.setBalaDate(mLMInsuAccRateSchema.getBalaDate());
				tLMInsuAccRateDB
						.setRateIntv(mLMInsuAccRateSchema.getRateIntv());
				tLMInsuAccRateSet = tLMInsuAccRateDB.query();
				if (tLMInsuAccRateSet.size() > 0) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据冲突，已存在相关利率数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tReflections.transFields(tLMInsuAccRateSchema,
							mLMInsuAccRateSchema);
					tLMInsuAccRateSchema.setOperator(mGlobalInput.Operator);
					// tLMInsuAccRateSchema.setRateState("0");
					map.put(tLMInsuAccRateSchema, "INSERT");
				}
			}
			if ("delete".equals(mOperate)) {
				tLMInsuAccRateDB
						.setRiskCode(mLMInsuAccRateSchema.getRiskCode());
				tLMInsuAccRateDB.setInsuAccNo(mLMInsuAccRateSchema
						.getInsuAccNo());
				tLMInsuAccRateDB
						.setBalaDate(mLMInsuAccRateSchema.getBalaDate());
				tLMInsuAccRateDB
						.setRateIntv(mLMInsuAccRateSchema.getRateIntv());
				tLMInsuAccRateSet = tLMInsuAccRateDB.query();
				if (tLMInsuAccRateSet.size() < 1) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关利率数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tLMInsuAccRateSchema = tLMInsuAccRateSet.get(1);
					map.put(tLMInsuAccRateSchema, "DELETE");
				}
			}

		}

		if (mLMAccGuratRateSchema != null) {

			LMAccGuratRateSchema tLMAccGuratRateSchema = new LMAccGuratRateSchema();
			LMAccGuratRateSet tLMAccGuratRateSet = new LMAccGuratRateSet();
			LMAccGuratRateDB tLMAccGuratRateDB = new LMAccGuratRateDB();
			Reflections tReflections = new Reflections();

			if ("update".equals(mOperate)) {
				tLMAccGuratRateDB.setRiskCode(mLMAccGuratRateSchema
						.getRiskCode());
				tLMAccGuratRateDB.setInsuAccNo(mLMAccGuratRateSchema
						.getInsuAccNo());
				tLMAccGuratRateDB.setRateStartDate(mLMAccGuratRateSchema
						.getRateStartDate());
				tLMAccGuratRateDB.setRateIntv(mLMAccGuratRateSchema
						.getRateIntv());
				tLMAccGuratRateSet = tLMAccGuratRateDB.query();
				if (tLMAccGuratRateSet.size() < 1) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关利率数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tLMAccGuratRateSchema = tLMAccGuratRateSet.get(1);
					tReflections.transFields(tLMAccGuratRateSchema,
							mLMAccGuratRateSchema);
					tLMAccGuratRateSchema.setOperator(mGlobalInput.Operator);
					// tLMAccGuratRateSchema.setRateState("0");
					map.put(tLMAccGuratRateSchema, "UPDATE");
				}
			}
			if ("insert".equals(mOperate)) {
				tLMAccGuratRateDB.setRiskCode(mLMAccGuratRateSchema
						.getRiskCode());
				tLMAccGuratRateDB.setInsuAccNo(mLMAccGuratRateSchema
						.getInsuAccNo());
				tLMAccGuratRateDB.setRateStartDate(mLMAccGuratRateSchema
						.getRateStartDate());
				tLMAccGuratRateDB.setRateIntv(mLMAccGuratRateSchema
						.getRateIntv());
				tLMAccGuratRateSet = tLMAccGuratRateDB.query();
				if (tLMAccGuratRateSet.size() > 0) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据冲突，已存在相关利率数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tReflections.transFields(tLMAccGuratRateSchema,
							mLMAccGuratRateSchema);
					tLMAccGuratRateSchema.setOperator(mGlobalInput.Operator);
					// tLMAccGuratRateSchema.setRateState("0");
					map.put(tLMAccGuratRateSchema, "INSERT");
				}
			}
			if ("delete".equals(mOperate)) {
				tLMAccGuratRateDB.setRiskCode(mLMAccGuratRateSchema
						.getRiskCode());
				tLMAccGuratRateDB.setInsuAccNo(mLMAccGuratRateSchema
						.getInsuAccNo());
				tLMAccGuratRateDB.setRateStartDate(mLMAccGuratRateSchema
						.getRateStartDate());
				tLMAccGuratRateDB.setRateIntv(mLMAccGuratRateSchema
						.getRateIntv());
				tLMAccGuratRateSet = tLMAccGuratRateDB.query();
				if (tLMAccGuratRateSet.size() < 1) {
					CError tError = new CError();
					tError.moduleName = "AccRateBL";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关利率数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tLMAccGuratRateSchema = tLMAccGuratRateSet.get(1);
					map.put(tLMAccGuratRateSchema, "DELETE");
				}
			}

		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLMInsuAccRateSchema = ((LMInsuAccRateSchema) cInputData
				.getObjectByObjectName("LMInsuAccRateSchema", 0));
		mLMAccGuratRateSchema = ((LMAccGuratRateSchema) cInputData
				.getObjectByObjectName("LMAccGuratRateSchema", 0));
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mResult.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccRateBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			CError tError = new CError();
			tError.moduleName = "AccRateBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "数据提交失败。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
