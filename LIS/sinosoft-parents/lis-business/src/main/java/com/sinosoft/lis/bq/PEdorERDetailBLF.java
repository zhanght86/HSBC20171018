package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 满期降低保额续保BLF
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

public class PEdorERDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorERDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 返回到界面结果，操作后要显示的信息，如果没有就不传 */
	private TransferData mTransferData;

	public PEdorERDetailBLF() {
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

		// 获得业务数据
		if (!getInputData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		// 删除旧数据
		if (!deleteOldData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 把利息的计算结果存到mResult中
		mResult.clear();
		// mResult.add(mTransferData);

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
		PEdorERDetailBL tPEdorERDetailBL = new PEdorERDetailBL();
		if (!tPEdorERDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorERDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorERDetailBL.getResult();
		// mTransferData = tPEdorERDetailBL.getTransferResult();
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 保全项目校验
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBLF";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 删除原来保存的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean deleteOldData() {
		try {
			// 保全项目校验
			MMap tMMap = new MMap();
			String tSqla = "DELETE FROM LPRNPolAmnt" + " WHERE EdorNo='?EdorNo?'" + " and EdorType='?EdorType?'" + " and ContNo='?ContNo?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSqla);
			sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			tMMap.put(sbv1, "DELETE");
			String tSqlb = "DELETE FROM LPPol" + " WHERE EdorNo='?EdorNo?'" + " and EdorType='?EdorType?'" + " and ContNo='?ContNo?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSqlb);
			sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
			tMMap.put(sbv2, "DELETE");
			VData tVData = new VData();
			tVData.add(tMMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tVData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorERDetailBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "删除旧数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERDetailBLF";
			tError.functionName = "deleteOldData";
			tError.errorMessage = "删除旧数据时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public static void main(String[] args) {
		PEdorERDetailBLF tPEdorERDetailBLF = new PEdorERDetailBLF();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("86000000003105");
		tLPEdorItemSchema.setContNo("230110000002659");
		tLPEdorItemSchema.setEdorNo("410000000002740");
		tLPEdorItemSchema.setEdorType("ER");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		VData tVData = new VData();
		tVData.add(tLPEdorItemSchema);
		boolean tag = tPEdorERDetailBLF.submitData(tVData, "UPDATE||MAIN");
		if (tag) {
			logger.debug("Successful");
		} else {
			logger.debug("Fail");
		}
	}
}
