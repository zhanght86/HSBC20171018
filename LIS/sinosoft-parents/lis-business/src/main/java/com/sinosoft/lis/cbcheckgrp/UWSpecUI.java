package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class UWSpecUI implements BusinessService{
private static Logger logger = Logger.getLogger(UWSpecUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public UWSpecUI() {
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		boolean flag = true;
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";
		tG.ComCode = "86110000";

		// 接收信息
		LCSpecSchema tLCSpecSchema = new LCSpecSchema();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		String tContNo = "130110000000169";
		String tPrtNo = "00002004120266";
		String tPolNo = "110110000000137";
		tLCSpecSchema.setContNo(tContNo);
		tLCUWMasterSchema.setContNo(tContNo);
		tLCUWMasterSchema.setSpecReason("dfdfdf");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLCSpecSchema);
		tVData.add(tLCUWMasterSchema);
		tVData.add(tPolNo);
		tVData.add(tG);

		// 数据传输
		UWSpecUI tUWSpecUI = new UWSpecUI();
		if (!tUWSpecUI.submitData(tVData, "")) {

			int n = tUWSpecUI.mErrors.getErrorCount();
			Content = " 核保特约失败，原因是: "
					+ tUWSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}

	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		UWSpecBL tUWSpecBL = new UWSpecBL();

		logger.debug("----UI BEGIN---");
		if (tUWSpecBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWSpecBL.mErrors);
			return false;
		}
		logger.debug("----UI END---");
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
