package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: UI功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class GroupRiskUI {
private static Logger logger = Logger.getLogger(GroupRiskUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public GroupRiskUI() {
	}

	// @Main
	public static void main(String[] args) {
		LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
		LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
		GlobalInput mGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		mLCGrpContSchema.setGrpContNo("120110000000081");
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		mLCGrpPolSchema.setGrpContNo("120110000000081");
		mLCGrpPolSchema.setRiskCode("111298");
		mLCGrpPolSchema.setPayIntv("0");
		mLCGrpPolSchema.setCValiDate("2004-11-24");
		mLCGrpPolSet.add(mLCGrpPolSchema);
		VData tVData = new VData();
		tVData.add(mLCGrpContSchema);
		tVData.add(mLCGrpPolSet);
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		GroupRiskBL tGroupRiskBL = new GroupRiskBL();
		if (tGroupRiskBL.submitData(tVData, "DELETE||GROUPRISK") == false) {
			logger.debug(tGroupRiskBL.mErrors.getFirstError().toString());

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

		GroupRiskBL tGroupRiskBL = new GroupRiskBL();

		logger.debug("---UI BEGIN---");
		if (tGroupRiskBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGroupRiskBL.mErrors);
			return false;
		} else {
			mResult = tGroupRiskBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */
	public VData getResult() {
		return mResult;
	}

}
