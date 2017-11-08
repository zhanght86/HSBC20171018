/*
 * @(#)CustomerUnionUI.java	2005-04-18
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:新契约复核-客户合并处理调用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @CreateDate：2005-04-18
 */
public class CustomerUnionUI implements BusinessService {
private static Logger logger = Logger.getLogger(CustomerUnionUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public CustomerUnionUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		CustomerUnionBL tCustomerUnionBL = new CustomerUnionBL();

		if (!tCustomerUnionBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tCustomerUnionBL.mErrors);
			return false;
		}

		mResult = tCustomerUnionBL.getResult();

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		// StringBuffer sbSQL = new StringBuffer();
		// sbSQL
		// .append( " SELECT TABLE_NAME, COLUMN_NAME ")
		// .append( " FROM COL ")
		// .append( " WHERE COLUMN_NAME IN ")
		// .append( " ('INSUREDNO', 'CUSTOMERNO', 'APPNTNO' ) ")
		// .append(" ORDER BY TABLE_NAME, COLUMN_NAME DESC ");
		// logger.debug(sbSQL.toString());
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CustomerNo_NEW", "0001892350");
		tTransferData.setNameAndValue("CustomerNo_OLD", "0001892300");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		CustomerUnionUI tCustomerUnionUI = new CustomerUnionUI();

		if (tCustomerUnionUI.submitData(tVData, "CUSTOMER|UNION")) {
			logger.debug("== test succ ==");
		} else {
			logger.debug("== test fail ==");
			logger.debug("== error message:"
					+ tCustomerUnionUI.mErrors.getFirstError().toString());
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
