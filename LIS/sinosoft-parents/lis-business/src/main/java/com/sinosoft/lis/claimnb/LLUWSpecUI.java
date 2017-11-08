package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

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
 * @author wanzh
 * @version 1.0
 */

public class LLUWSpecUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLUWSpecUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	
	private VData mResult = new VData();

	public LLUWSpecUI() {
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

		LLUWSpecBL tLLUWSpecBL = new LLUWSpecBL();
		logger.debug("----UI BEGIN---");
		if (tLLUWSpecBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLUWSpecBL.mErrors);
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
		return mResult;
	}

}
