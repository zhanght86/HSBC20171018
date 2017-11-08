package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
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
 * @author not attributable
 * @version 1.0
 */
public class PEdorPTDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorPTDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	GlobalInput mGlobalInput = new GlobalInput();
	LPPolSchema mLPPolSchema = new LPPolSchema();

	public PEdorPTDetailBLF() {
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param: cOperate 数据操作字符串
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
		
		if (mOperate != null && mOperate.equals("OnlyCheck")) {
			BqCalBase tBqCalBase = new BqCalBase();
			mResult.add(tBqCalBase);
			return true;
		}

		// 数据校验
		// if(!checkData()){
		// return false;
		// }

		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
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
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName("LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		}
		catch (Exception e) {
			mErrors.addOneError("接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorPTDetailBL tPEdorPTDetailBL = new PEdorPTDetailBL();
		if (!tPEdorPTDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorPTDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorPTDetailBL.getResult();
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
