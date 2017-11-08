package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
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
 * @author guanwei
 * @version 1.0
 */

public class RNContAccGetUI {
private static Logger logger = Logger.getLogger(RNContAccGetUI.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		RNContAccGetBL tRNContAccGetBL = new RNContAccGetBL();

		logger.debug("Start RNContAccGetBL Submit...");
		tRNContAccGetBL.submitData(mInputData, cOperate);
		logger.debug("End RNContAccGetBL Submit...");

		// 如果有需要处理的错误，则返回
		if (tRNContAccGetBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tRNContAccGetBL.mErrors);
			mResult.clear();
		} else {
			mResult = tRNContAccGetBL.getResult();
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
		RNContAccGetUI tRNContAccGet1 = new RNContAccGetUI();
		GlobalInput GlobalInput = new GlobalInput();
		String ContNo = "000006618549";
		String GetMode = "1";
		double GetMoney = 500;

		LCContSet tLCContSet = new LCContSet();
		LCContSchema tLCContSchema = new LCContSchema();

		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
		logger.debug("set0:" + tLJTempFeeSet.encode());

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tLJTempFeeSet);
		tVData.add(tLJAGetTempFeeSet);

		tRNContAccGet1.submitData(tVData, "INSERT");
		CErrors tError = tRNContAccGet1.mErrors;
		if (!tError.needDealError()) {
			logger.debug("领取成功!");
		} else {
			logger.debug("领取失败，原因是:" + tError.getFirstError());
		}
	}

}
