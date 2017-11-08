package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 暂交费退费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class TempFeeWithdrawUI implements BusinessService
{
private static Logger logger = Logger.getLogger(TempFeeWithdrawUI.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	public TempFeeWithdrawUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		TempFeeWithdrawBL tTempFeeWithdrawBL = new TempFeeWithdrawBL();

		logger.debug("Start TempFeeWithdraw BL Submit...");
		tTempFeeWithdrawBL.submitData(mInputData, cOperate);
		logger.debug("End TempFeeWithdraw BL Submit...");

		// 如果有需要处理的错误，则返回
		if (tTempFeeWithdrawBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tTempFeeWithdrawBL.mErrors);
			mResult.clear();
		} else {
			mResult = tTempFeeWithdrawBL.getResult();
		}

		mInputData = null;
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
		TempFeeWithdrawUI tempFeeWithdrawUI1 = new TempFeeWithdrawUI();

		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();

		tLJTempFeeSchema.setTempFeeNo("11111122222233");
		tLJTempFeeSchema.setTempFeeType("1");
		tLJTempFeeSchema.setRiskCode("111298");
		tLJTempFeeSet.add(tLJTempFeeSchema);

		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		tLJAGetTempFeeSchema.setGetReasonCode("");
		tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
		// LJTempFeeSchema tLJTempFeeSchema1 = new LJTempFeeSchema();
		// tLJTempFeeSchema1.setTempFeeNo("00010220020360000002");
		// tLJTempFeeSchema1.setRiskCode("001");
		// tLJTempFeeSet.add(tLJTempFeeSchema1);
		logger.debug("set0:" + tLJTempFeeSet.encode());

		VData tVData = new VData();
		tVData.add(tLJTempFeeSet);
		tVData.add(tLJAGetTempFeeSet);

		tempFeeWithdrawUI1.submitData(tVData, "INSERT");
		CErrors tError = tempFeeWithdrawUI1.mErrors;
		if (!tError.needDealError()) {
			logger.debug("退费成功!");
		} else {
			logger.debug("退费失败，原因是:" + tError.getFirstError());
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
