

package com.sinosoft.lis.reinsure.impdata.implclass;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.RIPolRecordBakeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIInvalidDataImportGeneral implements RIDataImport {
	private CErrors mErrors;
	private String endDate;
	private String mAccumulateDefNo;
	private TransferData mTransferData;
	private String tSql;
	private MMap mMap;
	private VData mInputData;
	private PubSubmit mPubSubmit = new PubSubmit();

	public RIInvalidDataImportGeneral() {
	}

	/**
	 * submitData
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 * @todo Implement this
	 *       com.sinosoft.lis.reinsure.impdata.implclass.RIDataImport method
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public boolean getInputData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		endDate = (String) mTransferData.getValueByName("EndDate");
		mAccumulateDefNo = (String) mTransferData
				.getValueByName("AccumulateDefNo");
		return true;
	}

	private boolean dealData() {
		try {
			RIPolRecordBakeSet tRIPolRecordBakeSet = new RIPolRecordBakeSet();
			RSWrapper rsWrapper = new RSWrapper();
			tSql = getInAvailableSQL();

			if (!rsWrapper.prepareData(tRIPolRecordBakeSet, tSql)) {
				mErrors.copyAllErrors(rsWrapper.mErrors);
				System.out.println(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
				mMap = new MMap();
				mInputData = new VData();
				rsWrapper.getData();
				if (tRIPolRecordBakeSet != null
						&& tRIPolRecordBakeSet.size() > 0) {
					mMap.put(tRIPolRecordBakeSet, "INSERT");
					mInputData.add(mMap);
					mPubSubmit.submitData(mInputData, "");
				}
				mMap = null;
				mInputData = null;

			} while (tRIPolRecordBakeSet != null
					&& tRIPolRecordBakeSet.size() > 0);

			rsWrapper.close();

		} catch (Exception ex) {
			buildError("dealData", " 提取业务数据失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 提取无效保单
	 * 
	 * @return
	 */
	public String getInAvailableSQL() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" select ");
		strBuffer.append(" '0000000000' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '01' as EventType, ");
		strBuffer.append(" '' as RecordType, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo||',1' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" '000000' as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		strBuffer.append(" ceil(decode(months_between('" + endDate
				+ "',a.cvalidate),0,1,months_between('" + endDate
				+ "',a.cvalidate))/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer.append(" a.InsuredName as InsuredName, ");
		strBuffer.append(" a.InsuredSex as InsuredSex, ");
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.OccupationType as OccupationType, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		strBuffer.append(" a.StandPrem as StandPrem, ");
		strBuffer.append(" a.Prem as Prem, ");
		strBuffer.append(" 0 as RiskAmnt, ");
		strBuffer.append(" a.Amnt as Amnt,  ");
		strBuffer.append(" '' as Mult, ");
		strBuffer.append(" a.PayIntv as PayIntv, ");
		strBuffer.append(" a.PayYears as PayYears, ");
		strBuffer.append(" a.PayEndYearFlag as PayEndYearFlag, ");
		strBuffer.append(" a.PayEndYear as PayEndYear, ");
		strBuffer.append(" '' as GetYearFlag, ");
		strBuffer.append(" '' as GetYear, ");
		strBuffer.append(" a.InsuYearFlag as InsuYearFlag, ");
		strBuffer.append(" a.InsuYear as InsuYear, ");
		strBuffer.append(" '' as AcciYearFlag, ");
		strBuffer.append(" '' as AcciYear, ");
		strBuffer.append(" '' as AcciEndDate, ");
		strBuffer.append(" '' as GetStartDate, ");
		strBuffer.append(" '' as GetStartType, ");
		strBuffer.append(" '' as PeakLine, ");
		strBuffer.append(" '' as GetLimit, ");
		strBuffer.append(" '' as GetIntv, ");
		strBuffer.append(" '' as PayNo, ");
		strBuffer.append(" '' as PayCount, ");
		strBuffer.append(" '' as PayMoney, ");
		strBuffer.append(" '' as LastPayToDate, ");
		strBuffer.append(" '' as CurPayToDate, ");
		strBuffer.append(" '' as EdorNo, ");
		strBuffer.append(" '' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer.append(" 0 as AccAmnt, ");
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer.append(" 0 as PreAmnt, ");
		strBuffer.append(" '' as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" 0 as StandGetMoney, ");
		strBuffer.append(" '' as GetRate, ");
		strBuffer.append(" 0 as ClmGetMoney, ");
		strBuffer.append(" '' as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer.append(" '' as RIContNo, ");
		strBuffer.append(" '' as RIPreceptNo, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '' as ReinsreFlag, ");
		strBuffer.append(" greatest(a.signdate,a.cvalidate) as GetDate, ");
		strBuffer.append(" '' as StandbyString1, ");
		// 如果没有健康加费，提数为A=0
		strBuffer.append(" '' as StandbyString2, ");
		// 计划别
		strBuffer.append(" a.PLANTYPE as StandbyString3, ");
		// 长短险标志 1:短期险 2：长期险
		strBuffer
				.append(" (select case when riskperiod='M' then '1' else '2' end from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
		strBuffer.append(" null as StandbyString5, ");
		// strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble1, ");// 用于提取额外死亡率(加费评点)
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		strBuffer.append(" null as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom ");
		strBuffer.append(" from lcpol a  ");

		strBuffer.append(" where a.conttype='1'");

		strBuffer.append(" and greatest(signdate,cvalidate) <= '"); // 保单生效日与签单日
		strBuffer.append(endDate);
		// 没有被提取过
		strBuffer
				.append("' and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',1')) ");
		strBuffer
				.append(" and exists(select 'X'  from riaccumulaterdcode c where c.associatedcode = a.riskcode and c.accumulatedefno ='"
						+ mAccumulateDefNo + "') ");

		strBuffer.append(" order by GetDate,polno");
		System.out.println("无效保单提取　sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	/**
	 * getCErrors
	 * 
	 * @return CErrors
	 * @todo Implement this
	 *       com.sinosoft.lis.reinsure.impdata.implclass.RIDataImport method
	 */
	public CErrors getCErrors() {
		return this.mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIDataMake";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

}
