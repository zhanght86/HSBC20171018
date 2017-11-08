package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 年金，满期金给付BLF类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorAGDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorAGDetailBLF.class);
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

	public PEdorAGDetailBLF() {
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
			tError.moduleName = "PEdorAGDetailBLF";
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
		PEdorAGDetailBL tPEdorAGDetailBL = new PEdorAGDetailBL();
		if (!tPEdorAGDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorAGDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAGDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorAGDetailBL.getResult();
		return true;
	}

	public static void main(String[] args) {
		PEdorAGDetailBLF tPEdorAGDetailBLF = new PEdorAGDetailBLF();
		// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		//
		// tLPEdorItemSchema.setEdorAcceptNo("6120050801000047");
		// tLPEdorItemSchema.setContNo("230110000003098");
		// tLPEdorItemSchema.setEdorNo("6020050801000045");
		// tLPEdorItemSchema.setEdorType("AG");
		// tLPEdorItemSchema.setInsuredNo("0000530480");
		// tLPEdorItemSchema.setPolNo("210110000002762");
		//
		// TransferData tTransferData = new TransferData();
		// //个别险种处理
		// tTransferData.setNameAndValue("InsuYear","");
		// tTransferData.setNameAndValue("GetDutyKind","");
		//
		// GlobalInput tGI = new GlobalInput();
		// tGI.Operator = "001";
		// tGI.ManageCom = "86";
		//
		// VData tVData = new VData();
		// tVData.add(tLPEdorItemSchema);
		// tVData.add(tTransferData);
		// tVData.add(tGI);
		// //String tOperator = "QUERY||MAIN";
		// String tOperator = "INSERT||MAIN";
		// boolean tag = tPEdorAGDetailBLF.submitData(tVData, tOperator);
		// if (tag)
		// {
		// if (tPEdorAGDetailBLF.mErrors.needDealError())
		// {
		// logger.debug(tOperator + ":Fail:" +
		// tPEdorAGDetailBLF.mErrors.getErrContent());
		// }
		// else
		// {
		// logger.debug(tOperator + ":Successful");
		// }
		// }
		// else
		// {
		// logger.debug(tOperator + ":Fail:" +
		// tPEdorAGDetailBLF.mErrors.getErrContent());
		// }
	}
}
