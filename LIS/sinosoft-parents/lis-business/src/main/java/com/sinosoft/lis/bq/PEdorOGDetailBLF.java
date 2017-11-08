package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保险金一次性给付BLF类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author TangPei
 * @version 1.0
 */

public class PEdorOGDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorOGDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	// private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */

	public PEdorOGDetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		// if (!getInputData())
		// {
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
			tError.moduleName = "PEdorOGDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	// private boolean getInputData()
	// {
	// try
	// {
	//
	// mLPEdorItemSchema = (LPEdorItemSchema)
	// mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
	// mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
	// "GlobalInput",0);
	// if (mLPAppntSchema == null || mLPAddressSchema == null ||
	// mLPEdorItemSchema == null || mGlobalInput == null)
	// {
	// return false;
	// }
	// }
	// catch (Exception e)
	// {
	// CError.buildErr(this, "接收数据失败");
	// return false;
	// }
	// return true;
	// }
	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorOGDetailBL tPEdorOGDetailBL = new PEdorOGDetailBL();
		if (!tPEdorOGDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorOGDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorOGDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorOGDetailBL.getResult();
		return true;
	}

	public static void main(String[] args) {
		PEdorOGDetailBLF tPEdorOGDetailBLF = new PEdorOGDetailBLF();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema
				.decode("6120240403000007|6020240403000014||OG|||000000040674|0000640250|210320000001897||2019-07-03|||||||0|0|0|0||||2006-04-13|09:56:58||||||||||");

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";

		VData tVData = new VData();
		LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
		tLJSGetDrawSet
				.decode("3603200002102|210320000001897|628000|120210|628040|||||||0|||||||||||||||||||||||||||||||");
		tVData.add(tLPEdorItemSchema);
		LPPolSchema tP = new LPPolSchema();
		tP
				.decode("6020240403000014|OG||||210320000001897||||||||||||||||||||||0|0|||||||||||||||0||0||0||0|||||0|0|0|0|0||0|0|0|0|0|0|0|0|0|0|0||||||0||||||||||||||||||||||||||||||||||||||0|1||||");
		tVData.add(tGI);
		tVData.add(tP);
		tVData.add(tLJSGetDrawSet);

		String tOperator = "INSERT||MAIN";

		boolean tag = tPEdorOGDetailBLF.submitData(tVData, tOperator);

	}
}
