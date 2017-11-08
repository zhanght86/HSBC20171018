package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保单挂失与解挂
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @version 1.0
 */
public class PEdorPLDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorPLDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorPLDetailBLF() {
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

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		PEdorPLDetailBL tPEdorPLDetailBL = new PEdorPLDetailBL();

		if (!tPEdorPLDetailBL.submitData(mInputData, mOperate)) {
			mErrors.copyAllErrors(tPEdorPLDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "处理失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mResult = tPEdorPLDetailBL.getResult();
		mMap.add((MMap) mResult.getObjectByObjectName("MMap", 0));
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBLF";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

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

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test start:");

		// GlobalInput tG = new GlobalInput();
		// tG.Operator = "liurx";
		// tG.ManageCom = "86110000";
		//
		// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		// LPContStateSchema tLPContStateSchema = new LPContStateSchema();
		// //tLPEdorItemSchema.setPolNo("000000");
		// tLPEdorItemSchema.setEdorAcceptNo("86000000001556");
		// tLPEdorItemSchema.setContNo("230110000000574");
		// tLPEdorItemSchema.setEdorNo("410000000001261");
		// tLPEdorItemSchema.setEdorType("PL");
		// // tLPEdorItemSchema.setInsuredNo("000000");
		// tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		// tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		// tLPEdorItemSchema.setOperator(tG.Operator);
		// tLPEdorItemSchema.setEdorReasonCode("2");
		// tLPEdorItemSchema.setEdorReason("电话挂失");
		//
		// tLPContStateSchema.setEdorNo("410000000001261");
		// tLPContStateSchema.setEdorType("PL");
		// tLPContStateSchema.setContNo("230110000000574");
		// tLPContStateSchema.setInsuredNo("000000");
		// tLPContStateSchema.setPolNo("000000");
		// tLPContStateSchema.setStateType("Lost");
		// tLPContStateSchema.setState("1");
		// tLPContStateSchema.setStartDate(PubFun.getCurrentDate());
		// tLPContStateSchema.setStateReason("1");
		// tLPContStateSchema.setStartDate("2005-06-22");
		//
		//
		//
		//
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLPEdorItemSchema);
		// tVData.add(tLPContStateSchema);
		//
		// PEdorPLDetailBLF tPEdorPLDetailBLF = new PEdorPLDetailBLF();
		//
		// if (!tPEdorPLDetailBLF.submitData(tVData, ""))
		// {
		// logger.debug(tPEdorPLDetailBLF.mErrors.getError(0).errorMessage);
		// }
		logger.debug("test end");
	}

}
