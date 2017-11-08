package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCNotePadSchema;
import com.sinosoft.lis.vschema.LCNotePadSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RnewNotePadUI {
private static Logger logger = Logger.getLogger(RnewNotePadUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public RnewNotePadUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---UWNotePad BL BEGIN---");

		RnewNotePadBL tUWNotePadBL = new RnewNotePadBL();

		if (tUWNotePadBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWNotePadBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());

			return false;
		} else {
			mResult = tUWNotePadBL.getResult();
		}

		logger.debug("---UWNotePad BL END---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		RnewNotePadUI UWNotePadUI1 = new RnewNotePadUI();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("name", "value");

		LCNotePadSchema tLCNotePadSchema = new LCNotePadSchema();
		tLCNotePadSchema.setContNo("00000000000000000006");
		tLCNotePadSchema.setPrtNo("00000000000000");
		tLCNotePadSchema.setOperatePos("1");
		tLCNotePadSchema.setNotePadCont("test");

		LCNotePadSet inLCNotePadSet = new LCNotePadSet();
		inLCNotePadSet.add(tLCNotePadSchema);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(transferData1);
		tVData.add(inLCNotePadSet);

		// if (!UWNotePadUI1.submitData(tVData, "INSERT")) {
		// VData rVData = UWNotePadUI1.getResult();
		// logger.debug("Submit Failed! " + (String)rVData.get(0));
		// }
		// else {
		// logger.debug("Submit Succed!");
		// }
		if (!UWNotePadUI1.submitData(tVData, "INSERT")) {
			VData rVData = UWNotePadUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			// VData rVData = UWNotePadUI1.getResult();
			// String result = ((LCNotePadSet)rVData.get(0)).encode();
			logger.debug("Submit Succed!");

			// logger.debug(result);
		}
	}
}
