/*
 * @(#)CustomerUnionUI.java	2005-04-18
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:新契约团单加费承保录入
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 * @CreateDate：2005-07-22
 */
public class GrpUWAddFeeUI {
private static Logger logger = Logger.getLogger(GrpUWAddFeeUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpUWAddFeeUI() {
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

		GrpUWAddFeeBL tGrpUWAddFeeBL = new GrpUWAddFeeBL();

		if (!tGrpUWAddFeeBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpUWAddFeeBL.mErrors);
			return false;
		}

		mResult = tGrpUWAddFeeBL.getResult();

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
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setGrpPolNo("120110000001546");
		tLCGrpPolSchema.setDif(50);
		tLCGrpPolSet.add(tLCGrpPolSchema);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLCGrpPolSet);

		GrpUWAddFeeUI tGrpUWAddFeeUI = new GrpUWAddFeeUI();

		if (tGrpUWAddFeeUI.submitData(tVData, "")) {
			logger.debug("== test succ ==");
		} else {
			logger.debug("== test fail ==");
			logger.debug("== error message:"
					+ tGrpUWAddFeeUI.mErrors.getFirstError().toString());
		}

	}
}
