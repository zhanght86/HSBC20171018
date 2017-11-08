/*
 * <p>ClassName: OLMFactoryTypeBL </p>
 * <p>Description: OLMFactoryTypeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2004-12-22 14:46:10
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMFactoryModeDB;
import com.sinosoft.lis.db.LMFactoryTypeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMFactoryTypeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LMFactoryTypeBL {
private static Logger logger = Logger.getLogger(LMFactoryTypeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	/** 业务处理相关变量 */
	private LMFactoryTypeSchema mLMFactoryTypeSchema = new LMFactoryTypeSchema();

	// private LMFactoryTypeSet mLMFactoryTypeSet=new LMFactoryTypeSet();
	public LMFactoryTypeBL() {
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
			tError.moduleName = "OLMFactoryTypeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLMFactoryTypeBL-->dealData!";
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
			// 数据提交、保存
			PubSubmit tPubSubmit = new PubSubmit();
			logger.debug("Start LMFactoryTypeBL Submit...");

			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LMFactoryTypeBL";
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
			map.put(mLMFactoryTypeSchema, "INSERT");
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			LMFactoryModeDB mLMFactoryModeDB = new LMFactoryModeDB();
			mLMFactoryModeDB.setFactoryType(mLMFactoryTypeSchema
					.getFactoryType());
			int lmodeNum = mLMFactoryModeDB.getCount();
			if (mLMFactoryModeDB.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "LMFactoryCodeToTypeBL";
				tError.functionName = "deleteData";
				tError.errorMessage = "查询健康险计算Sql模板失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (lmodeNum > 0) {
				CError tError = new CError();
				tError.moduleName = "LMFactoryCodeToTypeBL";
				tError.functionName = "deleteData";
				tError.errorMessage = "该要素类型下还有健康险计算Sql模板没有删除，不能进行删除操作！";
				this.mErrors.addOneError(tError);
				return false;
			}
			String sql1 ="delete from LMFactoryCodeToType where FactoryType='"
					+ "?FactoryType?" + "'" ;
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("FactoryType",mLMFactoryTypeSchema.getFactoryType());
			map.put(sqlbv1, "DELETE");
			map.put(mLMFactoryTypeSchema, "DELETE");

		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			map.put(mLMFactoryTypeSchema, "UPDATE");
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
		this.mLMFactoryTypeSchema.setSchema((LMFactoryTypeSchema) cInputData
				.getObjectByObjectName("LMFactoryTypeSchema", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LMFactoryTypeDB tLMFactoryTypeDB = new LMFactoryTypeDB();
		tLMFactoryTypeDB.setSchema(this.mLMFactoryTypeSchema);
		// 如果有需要处理的错误，则返回
		if (tLMFactoryTypeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMFactoryTypeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMFactoryTypeBL";
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
			this.mInputData.add(this.mLMFactoryTypeSchema);
			this.mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLMFactoryTypeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactoryTypeBL";
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
