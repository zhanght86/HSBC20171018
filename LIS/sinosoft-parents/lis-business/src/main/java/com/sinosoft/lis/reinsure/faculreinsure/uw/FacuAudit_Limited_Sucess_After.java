

package com.sinosoft.lis.reinsure.faculreinsure.uw;

/**
 * <p> Title: </p> 
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2011</p>  
 * <p> Company: sinosoft</p>
 * <p> 累计风险保额</p> 
 * @zhangbin
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RICalFactorValueSchema;
import com.sinosoft.lis.vschema.RIExchangeRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FacuAudit_Limited_Sucess_After implements FacuAudit {

	private String mContNo;
	private String mInsuredNo;
	private String mFactorID;
	private String mFactorValue;
	private String mCurrency;
	private String mAccumulateDefNO;
	private String mAccumulateMode;
	private String mPolNo;
	private String mDutyCode;
	public CErrors mErrors = new CErrors();
	private boolean uwFlag = true;

	private RIExchangeRateSet mRIExchangeRateSet;

	private RICalFactorValueSchema mRICalFactorValueSchema = new RICalFactorValueSchema();
	private TransferData mTransferData = new TransferData();

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mRIExchangeRateSet = (RIExchangeRateSet) cInputData
				.getObjectByObjectName("RIExchangeRateSet", 0);

		mContNo = (String) mTransferData.getValueByName("ContNo");
		mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		mFactorID = (String) mTransferData.getValueByName("FactorID");
		mFactorValue = (String) mTransferData.getValueByName("FactorValue");
		mCurrency = (String) mTransferData.getValueByName("Currency");
		mAccumulateDefNO = (String) mTransferData
				.getValueByName("AccumulateDefNO");
		mAccumulateMode = (String) mTransferData
				.getValueByName("AccumulateMode");
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		mDutyCode = (String) mTransferData.getValueByName("DutyCode");

		return true;
	}

	private boolean dealData() {
		uwFlag = true;
		StringBuffer strBuffer = new StringBuffer();
		double accAmnt = 0;
		ExeSQL tExeSQL = new ExeSQL();

		String tSql = "select currency,salechnl,to_char(cvalidate,'yyyy-mm-dd') from lcpol where Contno = '"
				+ mContNo + "' and Polno ='" + mPolNo + "' ";
		SSRS infoSSRS = tExeSQL.execSQL(tSql);
		if (infoSSRS.getMaxRow() == 0) {
			buildError("InitInfo", "没有保单的币种和销售渠道信息！PolNo=" + mPolNo);
			return false;
		}
		if (!RIPubFun.checkDate("2009-08-06", infoSSRS.getAllData()[0][2])
				|| "01".equals(infoSSRS.getAllData()[0][0])) {
			uwFlag = true;
			return true;
		}

		// 如果分出责任累计，将历史保单币种全部转换为港币进行累计
		if (!mAccumulateMode.equals("01")) {
			// 计算历史累计风险保额
			SSRS amntSSRS = new SSRS();
			strBuffer
					.append(" select nvl(risk_amnt2(ceil(months_between(date'"
							+ PubFun.getCurrentDate()
							+ "',a.cvalidate-1)/12),b.Amnt ,b.DutyCode ,b.PolNo,b.Currency,'"
							+ mAccumulateDefNO
							+ "'),0),b.Currency from lcpol a,lcduty b  ");
			strBuffer.append(" where a.Polno = b.Polno  and a.Cvalidate<='");
			strBuffer.append(PubFun.getCurrentDate());
			strBuffer.append("' and a.Insuredno = '");
			strBuffer.append(mInsuredNo);
			strBuffer
					.append("' and exists (select * from Riaccumulategetduty x where x.Accumulatedefno = '"
							+ mAccumulateDefNO
							+ "' and x.Associatedcode = a.Riskcode and x.Getdutycode = b.Dutycode) ");
			strBuffer
					.append(" and appflag ='1' and exists(select 'X' from lccontstate where  StateType = 'Available' and State = '0' and enddate is null and polno=a.polno ) and a.contno <>'"
							+ mContNo + "' ");
			System.out.println("sql1: " + strBuffer.toString());
			amntSSRS = tExeSQL.execSQL(strBuffer.toString());
			if (tExeSQL.mErrors.needDealError()) {
				buildError("InitInfo", "获取累计风险保额明细出错！");
				return false;
			}
			for (int i = 0; i < amntSSRS.getAllData().length; i++) {
				accAmnt = 0;
				if (amntSSRS.getAllData()[i][1].equals("13")) {
					accAmnt = accAmnt
							+ Double.parseDouble(amntSSRS.getAllData()[i][0]);
				} else {
					for (int j = 1; j <= mRIExchangeRateSet.size(); j++) {
						if (mRIExchangeRateSet.get(j).getCurrency()
								.equals(amntSSRS.getAllData()[i][1])) {
							accAmnt = accAmnt
									+ Double.parseDouble(amntSSRS.getAllData()[i][0])
									* mRIExchangeRateSet.get(j)
											.getExchangeRate();
						}
					}
				}
			}
		}
		// 计算本单风险保额，转换为港币进行累计
		SSRS contSSRS = new SSRS();
		StringBuffer contBuffer = new StringBuffer();
		contBuffer
				.append(" select nvl(risk_amnt2(ceil(months_between(date'"
						+ PubFun.getCurrentDate()
						+ "',a.cvalidate-1)/12),a.Amnt ,a.DutyCode ,a.PolNo,a.Currency,'"
						+ mAccumulateDefNO
						+ "'),0),a.Currency from lcduty a where a.Polno = '"
						+ mPolNo + "' and a.Dutycode = '" + mDutyCode + "' ");

		System.out.println("sql2: " + contBuffer.toString());
		contSSRS = tExeSQL.execSQL(contBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("InitInfo", "获取累计风险保额明细出错！");
			return false;
		}
		if (contSSRS.getAllData()[0][1].equals("13")) {
			accAmnt = accAmnt + Double.parseDouble(contSSRS.getAllData()[0][0]);
		} else {
			for (int j = 1; j <= mRIExchangeRateSet.size(); j++) {
				if (mRIExchangeRateSet.get(j).getCurrency()
						.equals(contSSRS.getAllData()[0][1])) {
					accAmnt = accAmnt
							+ Double.parseDouble(contSSRS.getAllData()[0][0])
							* mRIExchangeRateSet.get(j).getExchangeRate();
				}
			}
		}
		// 将港币转换为限额币种
		if (mCurrency.equals("13")) {
			if (accAmnt > Double.parseDouble(mFactorValue)) {
				uwFlag = false;
			} else {
				uwFlag = true;
			}
		} else {
			for (int j = 1; j <= mRIExchangeRateSet.size(); j++) {
				if (mRIExchangeRateSet.get(j).getCurrency().equals(mCurrency)) {
					if (accAmnt / mRIExchangeRateSet.get(j).getExchangeRate() > Double
							.parseDouble(mFactorValue)) {
						uwFlag = false;
					} else {
						uwFlag = true;
					}
				}
			}
		}
		return true;
	}

	public boolean getResult() {
		return uwFlag;
	}

	public CErrors getCErrors() {
		return this.mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIRiskCal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

}

