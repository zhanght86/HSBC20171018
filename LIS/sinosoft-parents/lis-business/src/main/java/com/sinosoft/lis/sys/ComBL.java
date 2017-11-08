/*
 * <p>ClassName: ComBL </p>
 * <p>Description: LDComBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ComBL {
private static Logger logger = Logger.getLogger(ComBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	// private GlobalInput mGlobalInput =new GlobalInput() ;
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LDComSchema mLDComSchema = new LDComSchema();

	// private LDComSet mLDComSet=new LDComSet();
	public ComBL() {
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
		// 进行插入数据
		if (mOperate.equals("INSERT||COM")) {
			if (!dealData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "COMBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败COMBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		// 对数据进行修改操作
		if (mOperate.equals("UPDATE||COM")) {
			if (!updateData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "COMBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败COMBL-->updateData!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("---updateData---");
		}
		// 对数据进行删除操作
		if (mOperate.equals("DELETE||COM")) {
			if (!deleteData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "COMBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败COMBL-->deleteData!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("----deleteData---");
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start ComBL Submit...");
			ComBLS tComBLS = new ComBLS();
			tComBLS.submitData(mInputData, mOperate);
			logger.debug("End ComBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tComBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tComBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "ComBL";
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
		LDComSchema tLDComSchema = new LDComSchema();
		tLDComSchema.setComCode(mLDComSchema.getComCode());
		mLDComSchema.setSchema(tLDComSchema);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLDComSchema.setSchema((LDComSchema) cInputData
				.getObjectByObjectName("LDComSchema", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start LDComBLQuery Submit...");
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setSchema(this.mLDComSchema);
		// /this.mLDComSet=tLDComDB.query();
		// this.mResult.add(this.mLDComSet);
		logger.debug("End LDComBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDComDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDComDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDComBL";
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
			// this.mInputData=new VData();
			// this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mLDComSchema);
			mResult.clear();
			mResult.add(this.mLDComSchema);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
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
