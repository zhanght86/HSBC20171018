package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全项目报单复效明细
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lizhuo
 * @version 1.0
 */
public class PEdorREDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorREDetailBLF.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private MMap mMap = new MMap();

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据检测
		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 数据检测
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorREDetailBLF";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		PEdorREDetailBL tPEdorREDetailBL = new PEdorREDetailBL();
		if (!tPEdorREDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorREDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorREDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorREDetailBL.getResult();
		return true;
	}

	public PEdorREDetailBLF() {
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
