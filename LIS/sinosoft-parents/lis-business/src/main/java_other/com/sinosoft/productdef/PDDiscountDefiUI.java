

package com.sinosoft.productdef;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PDDiscountDefiUI implements BusinessService {
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public CErrors getErrors() {
		return this.mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData data, String Operater) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = Operater;
		PDDiscountDefiBL tPDDiscountDefiBL =new PDDiscountDefiBL();
		System.out.println("---PDDiscountDefiUI BEGIN---");
		if (tPDDiscountDefiBL.submitData(data, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPDDiscountDefiBL.getErrors());
			return false;
		} else {
			mResult = tPDDiscountDefiBL.getResult();
		}
		System.out.print(mErrors.toString());
		
		return true;
	}

}
