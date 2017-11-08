

package com.sinosoft.lis.reinsure.impdata;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.reinsure.impdata.implclass.RIDataImport;
import com.sinosoft.utility.CError;

import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.vschema.RIItemCalSet;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class RIBDataImport {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 前台传来的字符型参数 */
	private TransferData mTransferData = new TransferData();

	private RIItemCalSet mRIItemCalSet = new RIItemCalSet();

	public RIBDataImport() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.globalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		if (this.globalInput == null) {
			buildError("getInputData", "前台传输用户信息数据失败");
			return false;
		}
		this.mInputData = (VData) cInputData.clone();
		if (mInputData == null) {
			buildError("getInputData", "前台传输全局公共数据失败");
			return false;
		}
		mRIItemCalSet = (RIItemCalSet) mInputData.getObjectByObjectName(
				"RIItemCalSet", 0);
		if (this.mRIItemCalSet == null || this.mRIItemCalSet.size() == 0) {
			buildError("getInputData", "前台传输数据导入算法为空 ");
			return false;
		}
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			buildError("getInputData", "前台传输数据失败 ");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			String tAccumulateDefNo = (String) mTransferData
					.getValueByName("AccumulateDefNo");
			String tEndDate = (String) mTransferData.getValueByName("EndDate");
			TransferData tTransferData = new TransferData();
			VData tVData = new VData();
			tTransferData.setNameAndValue("AccumulateDefNo", tAccumulateDefNo);
			tTransferData.setNameAndValue("EndDate", tEndDate);
			tVData.add(globalInput);
			tVData.add(tTransferData);
			for (int i = 1; i <= mRIItemCalSet.size(); i++) {
				if (mRIItemCalSet.get(i).getItemCalType().equals("3")) {
					// 业务数据导入必须为配置类算法提数
					System.out.println(mRIItemCalSet.get(i).getCalClass());
					Class tClass = Class.forName(mRIItemCalSet.get(i)
							.getCalClass());
					RIDataImport tRIDataImport = (RIDataImport) tClass
							.newInstance();
					if (!tRIDataImport.submitData(tVData, "")) {
						mErrors.copyAllErrors(tRIDataImport.getCErrors());
						return false;
					}
				} else {
					buildError("dealData", "累计风险编码为：" + tAccumulateDefNo
							+ " 的业务数据导入算法导入类型配置错误");
					return false;
				}
			}
		} catch (Exception ex) {
			buildError("dealData", "");
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
