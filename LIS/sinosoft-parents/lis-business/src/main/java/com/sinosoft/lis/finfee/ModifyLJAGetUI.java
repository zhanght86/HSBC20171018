package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 付费方式修改功能</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ModifyLJAGetUI implements BusinessService{
private static Logger logger = Logger.getLogger(ModifyLJAGetUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ModifyLJAGetUI() {
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

		logger.debug("---ModifyLJAGet BL BEGIN---");
		ModifyLJAGetBL tModifyLJAGetBL = new ModifyLJAGetBL();

		if (tModifyLJAGetBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tModifyLJAGetBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tModifyLJAGetBL.getResult();
		}
		logger.debug("---ModifyLJAGet BL END---");

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
		ModifyLJAGetUI ModifyLJAGetUI1 = new ModifyLJAGetUI();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("name", "value");

		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo("86110020030370000206");
		tLJAGetSchema.setPayMode("4");
		tLJAGetSchema.setBankCode("0101");
		tLJAGetSchema.setBankAccNo("1234567890");
		tLJAGetSchema.setAccName("Minim");
		LJAGetSet inLJAGetSet = new LJAGetSet();
		inLJAGetSet.add(tLJAGetSchema);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(transferData1);
		tVData.add(inLJAGetSet);

		if (!ModifyLJAGetUI1.submitData(tVData, "INSERT")) {
			VData rVData = ModifyLJAGetUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}

		// if (!ModifyLJAGetUI1.submitData(tVData, "QUERY")) {
		// VData rVData = ModifyLJAGetUI1.getResult();
		// logger.debug("Submit Failed! " + (String)rVData.get(0));
		// }
		// else {
		// VData rVData = ModifyLJAGetUI1.getResult();
		// String result = ((LJAGetSet)rVData.get(0)).encode();
		// logger.debug("Submit Succed!");
		// logger.debug(result);
		// }
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
