package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统保单查询部分
 * </p>
 * <p>
 * Description:接口功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.0
 */
public class UWErrUI implements BusinessService {
private static Logger logger = Logger.getLogger(UWErrUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWErrUI() {
	}

	public static void main(String[] args) {
		
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		UWErrBL tUWErrBL = new UWErrBL();

		if (tUWErrBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWErrBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mInputData = tUWErrBL.getResult();
		}

		return true;
	}

	public VData getResult() {
		return mInputData;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
