package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.0
 */
public class UWHospitalQualityRecordUI implements BusinessService {
private static Logger logger = Logger.getLogger(UWHospitalQualityRecordUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public UWHospitalQualityRecordUI() {
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		UWHospitalQualityRecordBL tUWHospitalQualityRecordBL = new UWHospitalQualityRecordBL();

		logger.debug("---UI BEGIN---");
		if (tUWHospitalQualityRecordBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWHospitalQualityRecordBL.mErrors);
			return false;
		} else {
			mResult = tUWHospitalQualityRecordBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult() {
		return mResult;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {
		

	}
}
