package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
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
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorMRDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorMRDetailBLF.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mOperate;

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
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
			tError.moduleName = "PEdorMCDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("OK!");
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
			if (mLPEdorItemSchema == null || mGlobalInput == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		PEdorMRDetailBL tPEdorMRDetailBL = new PEdorMRDetailBL();
		if (!tPEdorMRDetailBL.submitData(mInputData, "")) {
			this.mErrors.copyAllErrors(tPEdorMRDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorMRDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorMRDetailBL.getResult();
		return true;
	}

	public PEdorMRDetailBLF() {
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB dd = new LPEdorItemDB();
		dd.setEdorAcceptNo("6120050702000019");

		tLPEdorItemSchema = dd.query().get(1);
		// tLPEdorItemSchema.decode("6120040801000001|6020040801000001||RE|||000000003292|000000|000000|||||||||0|0|0|0|001|2005-08-30|22:03:09|2005-08-30|22:03:09||||||||||");
		// LCCustomerImpartSet td = new LCCustomerImpartSet();
		VData tt = new VData();
		tt.add(tG);
		tt.add(tLPEdorItemSchema);
		// tt.add(td);
		PEdorMRDetailBLF aa = new PEdorMRDetailBLF();
		aa.submitData(tt, "");
	}

}
