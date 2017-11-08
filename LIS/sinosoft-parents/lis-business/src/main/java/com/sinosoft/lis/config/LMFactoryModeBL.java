/*
 * <p>ClassName: OLMFactoryModeBL </p>
 * <p>Description: OLMFactoryModeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2004-12-22 14:24:48
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMFactoryModeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMFactoryModeSchema;
import com.sinosoft.lis.schema.LMParamModeSchema;
import com.sinosoft.lis.vschema.LMParamModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LMFactoryModeBL {
private static Logger logger = Logger.getLogger(LMFactoryModeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LMFactoryModeSchema mLMFactoryModeSchema = new LMFactoryModeSchema();
	private LMParamModeSet mLMParamModeSet = new LMParamModeSet();
	private LMParamModeSchema mLMParamModeSchema = new LMParamModeSchema();

	// private LMFactoryModeSet mLMFactoryModeSet=new LMFactoryModeSet();
	public LMFactoryModeBL() {
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
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLMFactoryModeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLMFactoryModeBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("---prepareOutputData---");
			// 数据提交、保存
			PubSubmit tPubSubmit = new PubSubmit();
			logger.debug("Start LMFactoryModeBL Submit...");

			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LMFactoryModeBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---commitData---");
		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (this.mOperate.equals("INSERT||MAIN")) {
			map.put(mLMFactoryModeSchema, "INSERT");
			map.put(mLMParamModeSet, "INSERT");
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			map.put(mLMFactoryModeSchema, "DELETE");
			String sql1 = "delete from LMParamMode where RiskCode='"
					+ "?RiskCode?"
					+ "' and FactoryType='"
					+ "?FactoryType?"
					+ "' and FactoryCode='"
					+ "?FactoryCode?"
					+ "' and FactorySubCode='"
					+ "?FactorySubCode?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("RiskCode",mLMFactoryModeSchema.getRiskCode());
			sqlbv1.put("FactoryType",mLMFactoryModeSchema.getFactoryType());
			sqlbv1.put("FactoryCode",mLMFactoryModeSchema.getFactoryCode());
			sqlbv1.put("FactorySubCode",mLMFactoryModeSchema.getFactorySubCode());
			map.put(sqlbv1, "DELETE");
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			map.put(mLMFactoryModeSchema, "UPDATE");
			String sql2 = "delete from LMParamMode where RiskCode='"
					+ "?RiskCode?"
					+ "' and FactoryType='"
					+ "?FactoryType?"
					+ "' and FactoryCode='"
					+ "?FactoryCode?"
					+ "' and FactorySubCode='"
					+ "?FactorySubCode?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql2);
			sqlbv2.put("RiskCode",mLMFactoryModeSchema.getRiskCode());
			sqlbv2.put("FactoryType",mLMFactoryModeSchema.getFactoryType());
			sqlbv2.put("FactoryCode",mLMFactoryModeSchema.getFactoryCode());
			sqlbv2.put("FactorySubCode",mLMFactoryModeSchema.getFactorySubCode());
			map.put(sqlbv2, "DELETE");
			map.put(mLMParamModeSet, "INSERT");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean deleteData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLMFactoryModeSchema.setSchema((LMFactoryModeSchema) cInputData
				.getObjectByObjectName("LMFactoryModeSchema", 0));
		this.mLMParamModeSet.set((LMParamModeSet) cInputData
				.getObjectByObjectName("LMParamModeSet", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LMFactoryModeDB tLMFactoryModeDB = new LMFactoryModeDB();
		tLMFactoryModeDB.setSchema(this.mLMFactoryModeSchema);
		// 如果有需要处理的错误，则返回
		if (tLMFactoryModeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMFactoryModeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMFactoryModeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(this.mLMFactoryModeSchema);
			this.mInputData.add(this.mLMParamModeSet);
			this.mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLMFactoryModeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactoryModeBL";
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
