package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMGrpChargeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LMGrpChargeSchema;
import com.sinosoft.lis.vschema.LMGrpChargeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class LMGrpChargeBL {
private static Logger logger = Logger.getLogger(LMGrpChargeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LMGrpChargeSchema mLMGrpChargeSchema = new LMGrpChargeSchema();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();

	// private LDDrugSet mLDDrugSet=new LDDrugSet();
	public LMGrpChargeBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return: tLCGrpPolSchema.setGrpContNo
	 */
	private boolean getInputData(VData cInputData) {
		this.mLMGrpChargeSchema.setSchema((LMGrpChargeSchema) cInputData
				.getObjectByObjectName("LMGrpChargeSchema", 0));
		this.mLCGrpPolSchema.setSchema((LCGrpPolSchema) cInputData
				.getObjectByObjectName("LCGrpPolSchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String GrpContNoSQL = "select GrpPolNo from LCGrpPol where  GrpContNo='"
				+ mLCGrpPolSchema.getGrpContNo()
				+ "' and RiskCode='"
				+ mLCGrpPolSchema.getRiskCode() + "'";
		ExeSQL exeSQL = new ExeSQL();
		String tGrpPolNo = exeSQL.getOneValue(GrpContNoSQL);
		if (this.mOperate.equals("INSERT||MAIN")) {
			String submitSQL = "select * from LMGrpCharge where GrpPolNo in(select GrpPolNo from LCGrpPol where  GrpContNo='"
					+ mLCGrpPolSchema.getGrpContNo()
					+ "' and RiskCode='"
					+ mLCGrpPolSchema.getRiskCode() + "')";
			LMGrpChargeSet mLMGrpChargeSet = new LMGrpChargeSet();
			LMGrpChargeDB mLMGrpChargeDB = new LMGrpChargeDB();
			mLMGrpChargeSet = mLMGrpChargeDB.executeQuery(submitSQL);

			if (mLMGrpChargeSet != null && mLMGrpChargeSet.size() > 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "OLDDrugBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据已经存在，在此不能新增！";
				this.mErrors.addOneError(tError);
				return false;
			}

			mLMGrpChargeSchema.setGrpPolNo(tGrpPolNo);
			mLMGrpChargeSchema.setOperator(mGlobalInput.Operator);
			mLMGrpChargeSchema.setMakeDate(PubFun.getCurrentDate());
			mLMGrpChargeSchema.setMakeTime(PubFun.getCurrentTime());
			mLMGrpChargeSchema.setModifyDate(PubFun.getCurrentDate());
			mLMGrpChargeSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(mLMGrpChargeSchema, "DELETE&INSERT");
		}

		if (this.mOperate.equals("UPDATE||MAIN")) {
			mLMGrpChargeSchema.setGrpPolNo(tGrpPolNo);
			mLMGrpChargeSchema.setOperator(mGlobalInput.Operator);
			mLMGrpChargeSchema.setMakeDate(PubFun.getCurrentDate());
			mLMGrpChargeSchema.setMakeTime(PubFun.getCurrentTime());
			mLMGrpChargeSchema.setModifyDate(PubFun.getCurrentDate());
			mLMGrpChargeSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(mLMGrpChargeSchema, "UPDATE");
		}

		if (this.mOperate.equals("DELETE||MAIN")) {
			mLMGrpChargeSchema.setGrpPolNo(tGrpPolNo);
			map.put(mLMGrpChargeSchema, "DELETE");
		}

		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中

		if (!getInputData(cInputData)) {
			return false;
		} else {
			logger.debug("getinputdata success!");
		}

		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLDDrugBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLDDrugBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		else {
			logger.debug("Start LMGrpChargeBL Submit...");
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "LMGrpChargeBL";
				tError.functionName = "LMGrpChargeBL";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("End LMGrpChargeBL Submit...");
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(this.mLMGrpChargeSchema);
			mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLMGrpChargeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDDrugBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
