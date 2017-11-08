/*
 * <p>ClassName: ALDCodeBL </p>
 * <p>Description: LDCodeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2003-06-21
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ALDCodeBL {
private static Logger logger = Logger.getLogger(ALDCodeBL.class);
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
	private LDCodeSchema mLDCodeSchema = new LDCodeSchema();
	private LDCodeSet mLDCodeSet = new LDCodeSet();

	public ALDCodeBL() {
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
			tError.moduleName = "LDCodeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LDCodeBL-->dealData!";
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
			logger.debug("Start ALDCodeBL Submit...");
			ALDCodeBLS tALDCodeBLS = new ALDCodeBLS();
			tALDCodeBLS.submitData(mInputData, cOperate);
			logger.debug("End ALDCodeBL Submit...");
			// 如果有需要处理的错误，则返回
			if (tALDCodeBLS.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tALDCodeBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "ALDCodeBL";
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
		// 查询录入主键是否重复
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType(mLDCodeSchema.getCodeType());
		tLDCodeDB.setCode(mLDCodeSchema.getCode());
		int iCount = tLDCodeDB.getCount();
		if (tLDCodeDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ALDCodeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询所录代码是否存在出错！";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("count:" + iCount);
		if (this.mOperate.equals("INSERT||MAIN")) {
			if (iCount > 0) {
				CError tError = new CError();
				tError.moduleName = "ALDCodeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "编码" + mLDCodeSchema.getCode() + "已存在！";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else if (this.mOperate.equals("UPDATE||MAIN")
				|| this.mOperate.equals("DELETE||MAIN")) {
			if (iCount != 1) {
				CError tError = new CError();
				tError.moduleName = "ALDCodeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "编码" + mLDCodeSchema.getCode()
						+ "不存在，无法作修改或删除操作！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLDCodeSchema.setSchema((LDCodeSchema) cInputData
				.getObjectByObjectName("LDCodeSchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start LDCodeBLQuery Submit...");
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setSchema(this.mLDCodeSchema);
		this.mLDCodeSet = tLDCodeDB.query();
		this.mResult.add(this.mLDCodeSet);
		logger.debug("End LDCodeBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDCodeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDCodeBL";
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
			this.mInputData = new VData();
			this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mLDCodeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeBL";
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
