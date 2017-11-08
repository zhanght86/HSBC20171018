package com.sinosoft.lis.changePwd;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Dingzhong
 * @version 1.0
 */
public class LDChangePwdUI implements com.sinosoft.service.BusinessService {
private static Logger logger = Logger.getLogger(LDChangePwdUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	int mResultNum = 0;

	public LDChangePwdUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		LDChangePwdBL tChangePwdBL = new LDChangePwdBL();

		if (tChangePwdBL.submitData(cInputData, mOperate)) {
			mInputData = tChangePwdBL.getResult();
			return true;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tChangePwdBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ChangePwdUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		}
	}
	public VData getResult() {
		return mInputData;
	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
	public static void main(String[] args) {
	}
}
