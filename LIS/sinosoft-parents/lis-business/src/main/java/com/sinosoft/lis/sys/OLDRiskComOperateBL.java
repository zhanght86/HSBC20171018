/*
 * <p>ClassName: OLDRiskComOperateBL </p>
 * <p>Description: OLDRiskComOperateBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2003-10-28 15:15:24
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDRiskComOperateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDRiskComOperateSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class OLDRiskComOperateBL {
private static Logger logger = Logger.getLogger(OLDRiskComOperateBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LDRiskComOperateSchema mLDRiskComOperateSchema = new LDRiskComOperateSchema();

	// private LDRiskComOperateSet mLDRiskComOperateSet=new
	// LDRiskComOperateSet();
	public OLDRiskComOperateBL() {
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
		logger.debug("this.mOperate " + this.mOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLDRiskComOperateBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLDRiskComOperateBL-->dealData!";
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
			logger.debug("Start OLDRiskComOperateBL Submit...");
			OLDRiskComOperateBLS tOLDRiskComOperateBLS = new OLDRiskComOperateBLS();
			tOLDRiskComOperateBLS.submitData(mInputData, mOperate);
			logger.debug("End OLDRiskComOperateBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tOLDRiskComOperateBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOLDRiskComOperateBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "OLDRiskComOperateBL";
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
		this.mLDRiskComOperateSchema
				.setSchema((LDRiskComOperateSchema) cInputData
						.getObjectByObjectName("LDRiskComOperateSchema", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LDRiskComOperateDB tLDRiskComOperateDB = new LDRiskComOperateDB();
		tLDRiskComOperateDB.setSchema(this.mLDRiskComOperateSchema);
		// 如果有需要处理的错误，则返回
		if (tLDRiskComOperateDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDRiskComOperateDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDRiskComOperateBL";
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
			this.mInputData.add(this.mLDRiskComOperateSchema);
			mResult.clear();
			mResult.add(this.mLDRiskComOperateSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDRiskComOperateBL";
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
