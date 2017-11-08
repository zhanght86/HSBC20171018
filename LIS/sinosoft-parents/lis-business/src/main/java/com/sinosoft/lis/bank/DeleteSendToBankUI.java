package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 删除发送银行数据和暂交费数据</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DeleteSendToBankUI {
private static Logger logger = Logger.getLogger(DeleteSendToBankUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public DeleteSendToBankUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"DELETE"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---DeleteSendToBank BL BEGIN---");
		DeleteSendToBankBL tDeleteSendToBankBL = new DeleteSendToBankBL();

		if (tDeleteSendToBankBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tDeleteSendToBankBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tDeleteSendToBankBL.getResult();
		}
		logger.debug("---DeleteSendToBank BL END---");

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
		DeleteSendToBankUI DeleteSendToBankUI1 = new DeleteSendToBankUI();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("name", "value");

		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassSchema.setTempFeeNo("86110020030360000376");
		LJTempFeeClassSet inLJTempFeeClassSet = new LJTempFeeClassSet();
		inLJTempFeeClassSet.add(tLJTempFeeClassSchema);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(transferData1);
		tVData.add(inLJTempFeeClassSet);

		if (!DeleteSendToBankUI1.submitData(tVData, "DELETE")) {
			VData rVData = DeleteSendToBankUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}
	}
}
