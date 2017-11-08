

package com.sinosoft.lis.reinsure.impdata;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.db.RIItemCalDB;
import com.sinosoft.lis.reinsure.impdata.implclass.RIDataImport;
import com.sinosoft.lis.schema.RIItemCalSchema;

/**
 * <p>
 * Title: 分保数据导入处理类
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
public class RIRDataImpl {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 需要进行再保数据导入的算法集合 */
	private RIItemCalSet mRIItemCalSet = new RIItemCalSet();

	public RIRDataImpl() {
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
		this.mInputData = (VData) cInputData.clone();
		if (mInputData == null) {
			buildError("getInputData", "前台传输全局公共数据失败");
			return false;
		}
		this.globalInput.setSchema((GlobalInput) mInputData
				.getObjectByObjectName("GlobalInput", 0));
		if (this.globalInput == null) {
			buildError("getInputData", "前台传输用户信息数据失败");
			return false;
		}
		this.mRIItemCalSet = (RIItemCalSet) mInputData.getObjectByObjectName(
				"RIItemCalSet", 0);
		if (this.mRIItemCalSet == null || this.mRIItemCalSet.size() == 0) {
			buildError("getInputData", "获取再保数据导入算法失败");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		RIItemCalSchema tRIItemCalSchema;
		for (int i = 1; i <= mRIItemCalSet.size(); i++) {
			try {
				tRIItemCalSchema = new RIItemCalSchema();
				tRIItemCalDB.setArithmeticID(mRIItemCalSet.get(i)
						.getArithmeticID());
				tRIItemCalDB.setArithmeticType(mRIItemCalSet.get(i)
						.getArithmeticType());
				tRIItemCalDB.setCalItemOrder(mRIItemCalSet.get(i)
						.getCalItemOrder());
				if (!tRIItemCalDB.getInfo()) {
					buildError("dealData", " 获取算法编码为："
							+ mRIItemCalSet.get(i).getArithmeticID()
							+ " 的分保数据导入算法信息出错");
					return false;
				}
				if (tRIItemCalDB.mErrors.needDealError()) {
					buildError("dealData", "获取算法编码为："
							+ mRIItemCalSet.get(i).getArithmeticID()
							+ " 的分保数据导入算法信息出错");
					return false;
				}
				tRIItemCalSchema.setSchema(tRIItemCalDB.getSchema());
				this.mInputData.clear();
				this.mInputData.add(tRIItemCalSchema);
				this.mInputData.add(globalInput);
				if (mRIItemCalSet.get(i).getItemCalType().equals("3")) { // 业务数据导入必须为配置类算法提数
					Class tClass = Class.forName(mRIItemCalSet.get(i)
							.getCalClass());
					RIDataImport tRIDataImport = (RIDataImport) tClass
							.newInstance();
					if (!tRIDataImport.submitData(mInputData, "")) {
						mErrors.copyAllErrors(tRIDataImport.getCErrors());
						return false;
					}
				} else {
					buildError("dealData", "算法编码为："
							+ mRIItemCalSet.get(i).getArithmeticID()
							+ " 的再保数据导入算法导入类型配置错误");
					return false;
				}
			} catch (Exception ex) {
				buildError("dealData", "执行算法编码为："
						+ mRIItemCalSet.get(i).getArithmeticID()
						+ " 的再保数据导入算法导入数据时出错错误");
				return false;
			}
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
