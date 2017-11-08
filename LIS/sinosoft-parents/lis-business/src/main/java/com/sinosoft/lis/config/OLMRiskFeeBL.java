/*
 * <p>ClassName: OLMRiskFeeBL </p>
 * <p>Description: OLMRiskFeeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-03-22 16:52:11
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class OLMRiskFeeBL {
private static Logger logger = Logger.getLogger(OLMRiskFeeBL.class);
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
	private LMRiskFeeSchema mLMRiskFeeSchema = new LMRiskFeeSchema();

	// private LMRiskFeeSet mLMRiskFeeSet=new LMRiskFeeSet();
	public OLMRiskFeeBL() {
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
		if (!getInputData(cInputData))
			return false;
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLMRiskFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLMRiskFeeBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start OLMRiskFeeBL Submit...");
			OLMRiskFeeBLS tOLMRiskFeeBLS = new OLMRiskFeeBLS();
			tOLMRiskFeeBLS.submitData(mInputData, mOperate);
			logger.debug("End OLMRiskFeeBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tOLMRiskFeeBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOLMRiskFeeBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "OLMRiskFeeBL";
				tError.functionName = "submitDat";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (this.mOperate.equals("INSERT||MAIN")) {
			map.put(mLMRiskFeeSchema, "INSERT");
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			map.put(mLMRiskFeeSchema, "UPDATE");
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			map.put(mLMRiskFeeSchema, "DELETE");
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
		this.mLMRiskFeeSchema.setSchema((LMRiskFeeSchema) cInputData
				.getObjectByObjectName("LMRiskFeeSchema", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		tLMRiskFeeDB.setSchema(this.mLMRiskFeeSchema);
		// 如果有需要处理的错误，则返回
		if (tLMRiskFeeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskFeeBL";
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
			this.mInputData.add(this.mLMRiskFeeSchema);
			mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLMRiskFeeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskFeeBL";
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
