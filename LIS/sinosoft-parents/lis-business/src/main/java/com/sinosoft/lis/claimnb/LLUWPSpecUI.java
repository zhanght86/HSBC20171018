package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔核保特约通知书打印-----------SpecPrint.vts
 * </p>
 * <p>
 * Description ：打印理赔二核的特约通知书
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wanzh 2005/11/29
 * @version 1.0
 */
public class LLUWPSpecUI {
private static Logger logger = Logger.getLogger(LLUWPSpecUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate = "";
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public LLUWPSpecUI() {
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

		LLUWPSpecBL tLLUWPSpecBL = new LLUWPSpecBL();
		logger.debug("----UI BEGIN---");
		if (tLLUWPSpecBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLUWPSpecBL.mErrors);
			return false;
		} else {
			mResult = tLLUWPSpecBL.getResult();
		}
		logger.debug("----UI END---");
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

}
