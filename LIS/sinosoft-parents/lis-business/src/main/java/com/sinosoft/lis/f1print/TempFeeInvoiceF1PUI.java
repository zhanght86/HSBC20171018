package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author yanglh
 * @version 1.0
 */
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class TempFeeInvoiceF1PUI implements PrintService {
private static Logger logger = Logger.getLogger(TempFeeInvoiceF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mOperate = "";

	private TransferData inTransferData = new TransferData();

	public TempFeeInvoiceF1PUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备传往后台的数据
		VData vData = new VData();

		if (!prepareOutputData(vData)) {
			return false;
		}

		TempFeeInvoiceF1PBL tTempFeeInvoiceF1PBL = new TempFeeInvoiceF1PBL();
		logger.debug("Start TempFeeInvoiceF1P UI Submit ...");

		if (!tTempFeeInvoiceF1PBL.submitData(vData, cOperate)) {
			if (tTempFeeInvoiceF1PBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tTempFeeInvoiceF1PBL.mErrors);
				return false;
			} else {
				CError.buildErr(this, "TempFeeInvoiceF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tTempFeeInvoiceF1PBL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		vData.add(mGlobalInput);
		vData.add(inTransferData);
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = (VData) cInputData.clone();
		inTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		if (inTransferData == null || mGlobalInput == null) {
			CError.buildErr(this, "没有得到足够数据");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {

	}
}
