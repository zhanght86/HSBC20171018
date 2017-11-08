

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 再保费率表磁盘导入类
 * </p>
 * <p>
 * Description: 把从磁盘导入的费率表添加到数据库
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangBin
 * @version 1.0
 */
public class RiDataImpUI implements BusinessService {
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局变量 */
	private GlobalInput mGlobalInput;
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 当前日期 */
	private String mCurrentDate = PubFun.getCurrentDate();
	/** 当前时间 */
	private String mCurrentTime = PubFun.getCurrentTime();

	private String tImportPath = "";
	private String tFileName = "";
	private VData mInputData;
	private String mOperate;

	public RiDataImpUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		this.mOperate = cOperate;
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		mInputData = null;
		System.out.println("End RIDataRevertBL Submit...");
		return true;
	}

	private boolean getInputData() {
		this.mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		TransferData t = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tImportPath = (String) t.getValueByName("ImportPath");
		tFileName = (String) t.getValueByName("FileName");
		return true;
	}

	private boolean dealData() {

		System.out.println("tImportPath======" + tImportPath);
		System.out.println("tFileName======" + tFileName);

		System.out.println("---RiDataImp BL BEGIN---");
		RiDataImpBL tRiDataImpBL = new RiDataImpBL(mGlobalInput);
		if (!tRiDataImpBL.submitdata(tImportPath, tFileName)) {
			this.mErrors.copyAllErrors(tRiDataImpBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tRiDataImpBL.getResult();
		}

		System.out.println("---RiDataImp BL END---");
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public void main() {
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
