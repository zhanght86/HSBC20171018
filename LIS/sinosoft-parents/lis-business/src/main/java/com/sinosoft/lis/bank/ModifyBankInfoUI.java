package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 修改客户的银行信息</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ModifyBankInfoUI implements BusinessService{
private static Logger logger = Logger.getLogger(ModifyBankInfoUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ModifyBankInfoUI() {
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

		logger.debug("---ModifyBankInfo BL BEGIN---");
		ModifyBankInfoBL tModifyBankInfoBL = new ModifyBankInfoBL();

		if (tModifyBankInfoBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tModifyBankInfoBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tModifyBankInfoBL.getResult();
		}
		logger.debug("---ModifyBankInfo BL END---");

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
		ModifyBankInfoUI ModifyBankInfoUI1 = new ModifyBankInfoUI();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("PrtNo2", "80000000121432");
		transferData1.setNameAndValue("BankCode", "320102");
		transferData1.setNameAndValue("AccName", "21");
		transferData1.setNameAndValue("AccNo", "21");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(transferData1);
		// tVData.add(inLCPolSet);

		if (!ModifyBankInfoUI1.submitData(tVData, "INSERT")) {
			VData rVData = ModifyBankInfoUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
