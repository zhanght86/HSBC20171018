package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: 银行接口</p>
 * <p>Description: 银行返回文件撤销模块</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Sinosoft</p>
 * @author yanglh
 * @version 1.0
 */

public class CancelReadFromFileUI implements BusinessService{
private static Logger logger = Logger.getLogger(CancelReadFromFileUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public CancelReadFromFileUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"READ"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---CancelReadFromFile BL BEGIN---");
		CancelReadFromFileBL tCancelReadFromFileBL = new CancelReadFromFileBL();

		if (tCancelReadFromFileBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tCancelReadFromFileBL.mErrors);
			return false;
		} else {
			this.mErrors.copyAllErrors(tCancelReadFromFileBL.mErrors);
			mResult = tCancelReadFromFileBL.getResult();
		}
		logger.debug("---CancelReadFromFile BL END---");

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
	public CErrors getErrors()
	{
	      return mErrors;
	}
	public static void main(String[] args) {
		
	}
}
