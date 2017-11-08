package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 减额缴清BLF
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

public class PEdorPUDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorPUDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 返回到界面结果，操作后要显示的信息，如果没有就不传 */
	private TransferData mTransferData;

	public PEdorPUDetailBLF() {
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
				tError.moduleName = "PEdorPUDetailBLF";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
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
		PEdorPUDetailBL tPEdorPUDetailBL = new PEdorPUDetailBL();
		if (!tPEdorPUDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorPUDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPUDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorPUDetailBL.getResult();
		mTransferData = tPEdorPUDetailBL.getTransferResult();
		return true;
	}

	public static void main(String[] args) {
		PEdorPUDetailBLF tPEdorPUDetailBLF = new PEdorPUDetailBLF();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("6120051203000002");
		tLPEdorItemSchema.setContNo("000000010537");
		tLPEdorItemSchema.setEdorNo("6020051203000004");
		tLPEdorItemSchema.setEdorType("PU");
		tLPEdorItemSchema.setInsuredNo("0000561130");
		tLPEdorItemSchema.setPolNo("210320000000383");

		TransferData tTransferData = new TransferData();
		// 个别险种处理
		tTransferData.setNameAndValue("InsuYear", "");
		tTransferData.setNameAndValue("GetDutyKind", "0");

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";

		VData tVData = new VData();
		tVData.add(tLPEdorItemSchema);
		tVData.add(tTransferData);
		tVData.add(tGI);
		// String tOperator = "QUERY||MAIN";
		String tOperator = "INSERT||MAIN";
		boolean tag = tPEdorPUDetailBLF.submitData(tVData, tOperator);
		if (tag) {
			if (tOperator.equals("QUERY||MAIN")) {
				if (tPEdorPUDetailBLF.getResult() != null
						&& tPEdorPUDetailBLF.getResult().size() > 0) {
					VData tResult = new VData();
					tResult = tPEdorPUDetailBLF.getResult();
					if (tResult == null) {
						logger.debug("tResult == null");
					}
					tTransferData = (TransferData) tResult
							.getObjectByObjectName("TransferData", 0);
					String sRiskCount = (String) tTransferData
							.getValueByName("RiskCount");
					int iRiskCount = Integer.parseInt(sRiskCount);
					logger.debug("缴清险种个数：" + iRiskCount);

					String StrPolGrid = ""; // 拼接成mulline
					String sPolNo;
					String sOldRiskCode;
					String sOldRiskName;
					String sNewRiskCode;
					String sNewRiskName;
					Double DSumBonus;
					Double DFinaleBonus;
					Double DCashValue;
					Double DNewAmnt;
					String sPayToDate;
					Double DPrem;
					Double DAmnt;
					Double DMult;
					for (int i = 1; i <= iRiskCount; i++) {
						sPolNo = (String) tTransferData.getValueByName("PolNo"
								+ i);
						sOldRiskCode = (String) tTransferData
								.getValueByName("OldRiskCode" + i);
						sOldRiskName = (String) tTransferData
								.getValueByName("OldRiskName" + i);
						sNewRiskCode = (String) tTransferData
								.getValueByName("NewRiskCode" + i);
						sNewRiskName = (String) tTransferData
								.getValueByName("NewRiskName" + i);
						DSumBonus = (Double) tTransferData
								.getValueByName("SumBonus" + i);
						DFinaleBonus = (Double) tTransferData
								.getValueByName("FinaleBonus" + i);
						DCashValue = (Double) tTransferData
								.getValueByName("CashValue" + i);
						DNewAmnt = (Double) tTransferData
								.getValueByName("NewAmnt" + i);

						sPayToDate = (String) tTransferData
								.getValueByName("PayToDate" + i);
						DPrem = (Double) tTransferData.getValueByName("Prem"
								+ i);
						DAmnt = (Double) tTransferData.getValueByName("Amnt"
								+ i);
						DMult = (Double) tTransferData.getValueByName("Mult"
								+ i);
						StrPolGrid += "^" + sPolNo + "|" + sOldRiskCode + "|"
								+ sOldRiskName + "|" + sNewRiskCode + "|"
								+ sNewRiskName + "|" + DSumBonus + "|"
								+ DFinaleBonus + "|" + DCashValue + "|"
								+ DNewAmnt + "|" + sPayToDate + "|" + DPrem
								+ "|" + DAmnt + "|" + DMult + "\n";
					}
					logger.debug(StrPolGrid);
				}

			} else {
				if (tPEdorPUDetailBLF.mErrors.needDealError()) {
					logger.debug(tOperator + ":Fail:"
							+ tPEdorPUDetailBLF.mErrors.getErrContent());
				} else {
					logger.debug(tOperator + ":Successful");
				}
			}
		} else {
			logger.debug(tOperator + ":Fail:"
					+ tPEdorPUDetailBLF.mErrors.getErrContent());
		}
	}
}
