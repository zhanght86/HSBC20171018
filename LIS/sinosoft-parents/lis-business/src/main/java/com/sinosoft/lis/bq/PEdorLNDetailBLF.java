package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单质押贷款BLF
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorLNDetailBLF implements EdorDetail,BusinessService {
private static Logger logger = Logger.getLogger(PEdorLNDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 查到的利息” */
	private double mInterest = 0.0;

	public PEdorLNDetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		if (!mOperate.equals("QUERY||MAIN")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorLNDetailBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 把利息的计算结果存到mResult中
			mResult.clear();
			Double rDouble = new Double(mInterest);
			mResult.add(rDouble);
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorLNDetailBL tPEdorLNDetailBL = new PEdorLNDetailBL();
		if (!tPEdorLNDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorLNDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorLNDetailBL.getResult();
		return true;
	}

	public static void main(String[] args) {
		PEdorLNDetailBLF tPEdorLNDetailBLF = new PEdorLNDetailBLF();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("86000000003093");
		tLPEdorItemSchema.setContNo("230110000002677");
		tLPEdorItemSchema.setEdorNo("410000000002672");
		tLPEdorItemSchema.setEdorType("LN");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		Double tInterest = new Double(6000.0);

		VData tVData = new VData();
		tVData.add(tLPEdorItemSchema);
		boolean tag = tPEdorLNDetailBLF.submitData(tVData, "QUERY||MAIN");
		if (tag) {
			logger.debug("Successful");
		} else {
			logger.debug("Fail");
			logger.debug(tPEdorLNDetailBLF.getErrors().getFirstError());
		}
	}
}
