package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统到帐确认部分
 * </p>
 * <p>
 * Description:接口功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class FinFeeSureUI implements BusinessService{
private static Logger logger = Logger.getLogger(FinFeeSureUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public FinFeeSureUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		// 暂交费关联表
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		tLJTempFeeClassSchema.setChequeNo("9876543210987654");
		tLJTempFeeClassSchema.setPayMode("3");
		tLJTempFeeClassSchema.setTempFeeNo("3602100035407");
		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);

		VData tVData = new VData();
		// tVData.add( tLCPolSet );
		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		// tVData.add( tLCIssuePolSet);
		tVData.add(tLJTempFeeClassSet);
		tVData.add(tG);
		FinFeeSureUI ui = new FinFeeSureUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		FinFeeSureBL tFinFeeSureBL = new FinFeeSureBL();

		logger.debug("---FinFeeSureBL UI BEGIN---");
		if (tFinFeeSureBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tFinFeeSureBL.mErrors);
			CError.buildErr(this, "数据查询失败!");
			mResult.clear();
			return false;
		}
		return true;
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}


	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}
}
