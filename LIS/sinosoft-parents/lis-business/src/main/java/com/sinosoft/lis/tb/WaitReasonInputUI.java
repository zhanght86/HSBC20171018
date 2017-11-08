package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import java.text.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:核保等待功能类（界面输入）
 * </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author ln
 * @version 1.0
 */
public class WaitReasonInputUI
{
private static Logger logger = Logger.getLogger(WaitReasonInputUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public WaitReasonInputUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---WaitReasonInput BL BEGIN---");
		WaitReasonInputBL tWaitReasonInputBL = new WaitReasonInputBL();

		if (tWaitReasonInputBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tWaitReasonInputBL.mErrors);
			mResult.clear();
			return false;
		} else {
			// mResult = tScanApplyBL.getResult();
		}
		logger.debug("---ScanApply BL END---");

		mResult = tWaitReasonInputBL.getResult();
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

	public static void main(String[] args) {		
	}

}
