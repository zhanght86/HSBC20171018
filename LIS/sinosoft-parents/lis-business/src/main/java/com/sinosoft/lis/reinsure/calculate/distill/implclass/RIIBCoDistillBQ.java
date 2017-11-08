



package com.sinosoft.lis.reinsure.calculate.distill.implclass;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistill;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 共保保全通用可参考提数类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author JINTAO
 * @version 1.0
 */
public class RIIBCoDistillBQ implements RIDistill {
	String mEventType;
	RIWFLogSchema mRIWFLogSchema;
	String mAccumulateDefNo;
	private MMap mMap;
	private VData mInputData;
	private PubSubmit mPubSubmit = new PubSubmit();

	private String tSql;

	public RIIBCoDistillBQ() {
	}

	/**
	 * distillData
	 * 
	 * @return boolean
	 * @todo Implement this
	 *       com.sinosoft.lis.reinsure.calculate.distill.RIDistill method
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

	private boolean getInputData(VData cInputData, String cOperate) {
		mEventType = cOperate;
		mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
				"RIWFLogSchema", 0);
		mAccumulateDefNo = mRIWFLogSchema.getTaskCode();

		return true;
	}

	private boolean dealData() {
		try {
			RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
			RSWrapper rsWrapper = new RSWrapper();
			// 共保合同提数
			tSql = getDistillSQL();
			if (!rsWrapper.prepareData(tRIPolRecordSet, tSql)) {
				mErrors.copyAllErrors(rsWrapper.mErrors);
				System.out.println(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
				mMap = new MMap();
				mInputData = new VData();
				rsWrapper.getData();
				if (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0) {
					mMap.put(tRIPolRecordSet, "INSERT");
					mInputData.add(mMap);
					mPubSubmit.submitData(mInputData, "");
				}
				mMap = null;
				mInputData = null;

			} while (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0);

			rsWrapper.close();

			// 共保临分提数
			tSql = getDistillSQL1();
			if (!rsWrapper.prepareData(tRIPolRecordSet, tSql)) {
				mErrors.copyAllErrors(rsWrapper.mErrors);
				System.out.println(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
				mMap = new MMap();
				mInputData = new VData();
				rsWrapper.getData();
				if (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0) {
					mMap.put(tRIPolRecordSet, "INSERT");
					mInputData.add(mMap);
					mPubSubmit.submitData(mInputData, "");
				}
				mMap = null;
				mInputData = null;

			} while (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0);

			rsWrapper.close();

		} catch (Exception ex) {
			buildError("dealData", " 提取共保保全业务数据失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 共保保全合同提数
	 * 
	 * @return
	 */
	private String getDistillSQL() {

		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select BatchNo,EventNo,EventType,RecordType,NodeState,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,PlanType,RiskPeriod,RiskType,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredBirthday,InsuredAge,(InsuredAge+InsuredYear-1) as CurrentAge,IDType,IDNo,OccupationType,HealthTime1,HealthTime2,OccupationCode,SuppRiskScore,SmokeFlag,AdditionalRate,ExtType,ExtPrem,BegAccountValue,EndUnit,EndPrice,EndAccountValue,FeeType,FeeMoney,NonCashFlag,StandPrem,Prem,Amnt,Mult,");
		// 准备金
		strBuffer.append("Reserve,");
		// 年缴化保费
		strBuffer.append("APE(Prem,Payintv) as APE ,");
		// 现金价值
		strBuffer.append("CSV(InsuredYear,PolNo,DutyCode) as CSV,");
		// 核心风险保额
		strBuffer
				.append("SUMINSURED(Amnt,DutyCode,PolNo,Currency,Accumulatedefno) as Suminsured,");
		// 风险保额
		strBuffer
				.append("RISK_AMNT2 (InsuredYear,Amnt, DutyCode, PolNo, Currency,Accumulatedefno) as RiskAmnt,");
		strBuffer
				.append("Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,RISK_AMNT2 (InsuredYear,preAmnt, DutyCode, PolNo, Currency,Accumulatedefno) as PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,decode(FeeOperationType,'CT','CT','ZC') as StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,(ChangeRate*(RISK_AMNT2(InsuredYear,preAmnt,DutyCode,PolNo,Currency,Accumulatedefno))) as PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer
				.append(" a.PolNo||','|| d.Dutycode ||','||b.edorno||','||b.edortype||','||'"
						+ mAccumulateDefNo + "'||','||'GBBQ' as UnionKey, ");// 联合主键
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" c.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" d.Dutycode as DutyCode, ");
		strBuffer.append(" '' as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(d.getdate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME') as InsuredName, ");
		strBuffer.append(" a.InsuredSex as InsuredSex, ");
		strBuffer.append(" a.Insuredbirthday as InsuredBirthday, ");
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer.append(" '' as CurrentAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.Occupationlevel as OccupationType, ");
		strBuffer.append(" '' as HealthTime1, ");
		strBuffer.append(" '' as HealthTime2, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		strBuffer.append(" '' as SuppRiskScore, ");
		strBuffer
				.append(" nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') as SmokeFlag, ");
		strBuffer
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" '' as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");// Coupon
		strBuffer.append(" ''  as NonCashFlag, "); // Y-现金领取;N-非现金领取
		strBuffer.append(" c.StandPrem as StandPrem, ");
		strBuffer.append(" c.prem  as Prem, ");
		strBuffer.append(" c.amnt as Amnt, ");
		strBuffer.append(" 1 as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Faceamount,");
		strBuffer.append(" c.Currency as Currency, ");
		strBuffer.append(" c.PayIntv as PayIntv, ");
		strBuffer.append(" c.PayYears as PayYears, ");
		strBuffer.append(" ''as PayEndYearFlag, ");
		strBuffer.append(" '' as PayEndYear, ");
		strBuffer.append(" '' as GetYearFlag, ");
		strBuffer.append(" '' as GetYear, ");
		strBuffer.append(" '' as InsuYearFlag, ");
		strBuffer.append(" '' as InsuYear, ");
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
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" b.edorno as EdorNo, ");
		strBuffer.append(" b.edortype as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null)  as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		strBuffer.append(" 0 as AccAmnt, ");
		// 但保全类型为复效时，变更前保额、保费、风险保额都为0；当保全类型为保单贷款时，由于保单但款不操作lppol所有保额、保费、风险保额都从c表中获取
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer.append(" 0 as PreAmnt, ");
		strBuffer.append(" '' as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" 0 as StandGetMoney, ");
		strBuffer.append(" ''as GetRate, ");
		strBuffer.append(" 0 as ClmGetMoney, ");
		strBuffer.append(" '' as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer.append(" 'R00000000' as RIContNo, ");
		strBuffer
				.append(" (select b.Ripreceptno from RIPrecept b where b.Ricontno='R00000000' and b.Accumulatedefno='"
						+ mAccumulateDefNo + "') as RIPreceptNo, ");
		strBuffer.append(" '' as ReinsreFlag, ");
		// 业务日期
		strBuffer.append(" d.getdate as GetDate, ");
		strBuffer
				.append(" rigetplancode(c.contno,c.polno,c.dutycode) as StandbyString1, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, ");// a.PLANTYPE
		strBuffer
				.append(" nvl((SELECT sum(x.insuaccbala) FROM Lcinsureacc x where x.polno=a.polno),0) as StandbyDouble1, ");
		strBuffer
				.append(" nvl((SELECT x.interestrate FROM riinterest x where  x.enddate is null and x.state='01' and x.riskcode=a.riskcode and x.dutyname=c.Planlevel),0) as StandbyDouble2, ");
		strBuffer
				.append(" nvl((SELECT max(z.rate) FROM Ldpubrate z where z.Caltype in('ULR','ULRL')and a.cvalidate  between z.startdate and z.enddate and z.riskcode=a.riskcode),0) as StandbyDouble3, ");
		strBuffer.append(" '' as StandbyDate1, ");
		strBuffer.append(" '' as StandbyDate2, ");
		strBuffer.append(" a.managecom as managecom ,");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.Mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" d.GetMoney  as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		/*strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate1, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate2, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate3, ");*/
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
		strBuffer.append(" '' as StandbyString4, ");
		strBuffer.append(" '' as StandbyString5, ");
		strBuffer.append(" '' as StandbyString6, ");
		strBuffer.append(" '' as StandbyString7, ");
		strBuffer.append(" '' as StandbyString8, ");
		strBuffer.append(" '' as StandbyString9, ");
		strBuffer.append(" '' as StandbyDouble4, ");
		strBuffer.append(" '' as StandbyDouble5, ");
		strBuffer.append(" '' as StandbyDouble6, ");
		strBuffer.append(" '' as StandbyDouble7, ");
		strBuffer.append(" '' as StandbyDouble8, ");
		strBuffer.append(" '' as StandbyDouble9, ");
		strBuffer.append(" '' as StandbyDate3, ");
		strBuffer.append(" '' as StandbyDate4, ");
		strBuffer.append(" '' as StandbyDate5 ");

		strBuffer.append("from lcpol a,lpedoritem b,lcduty c,LJAGetEndorse d ");
		strBuffer.append("where a.ContType='1' ");
		strBuffer
				.append("and a.contno = b.contno  and a.polno = c.polno and c.Dutycode=d.Dutycode ");
		strBuffer.append("and a.polno = d.polno ");
		strBuffer
				.append("and ((c.StandPrem-d.GetMoney)<>0 or (b.Edortype='AB' and a.Mainpolno!=a.Polno)) ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateRDCode r where a.riskcode = r.associatedcode  and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		strBuffer.append(" and d.getdate between '"
				+ mRIWFLogSchema.getStartDate() + "' and '"
				+ mRIWFLogSchema.getEndDate() + "'");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','|| d.Dutycode ||','||b.edorno||','||b.edortype||','||'"
						+ mAccumulateDefNo + "'||','||'GBBQ' )) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','|| d.Dutycode ||','||b.edorno||','||b.edortype||','||'"
						+ mAccumulateDefNo + "'||','||'GBBQ')) ");
		// 有效保单
		strBuffer
				.append(" and exists(select 'X' from lccontstate where  StateType = 'Available' and State = '0' and enddate is null and polno=a.polno ) ");
		// 增额，减额，部分退保
		strBuffer
				.append(" and a.contno = c.Contno and b.edortype  not in('CF','CT','ES','SX','BR')  and b.edorstate='0'");
		// 不为临分的保单
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where proposalno=a.polno and state in ('00','02','03','04')) ");
		// strBuffer.append(" and '"+mAccumulateDefNo+"' in ('L01010040010','L01010070010')");
		strBuffer.append(" and a.Currency='01'");
		strBuffer.append(") ");
		System.out.println("共保保全提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	private String getDistillSQL1() {

		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select BatchNo,EventNo,EventType,RecordType,NodeState,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,PlanType,RiskPeriod,RiskType,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredBirthday,InsuredAge,(InsuredAge+InsuredYear-1) as CurrentAge,IDType,IDNo,OccupationType,HealthTime1,HealthTime2,OccupationCode,SuppRiskScore,SmokeFlag,AdditionalRate,ExtType,ExtPrem,BegAccountValue,EndUnit,EndPrice,EndAccountValue,FeeType,FeeMoney,NonCashFlag,StandPrem,Prem,Amnt,Mult,");
		// 准备金
		strBuffer.append("Reserve,");
		// 年缴化保费
		strBuffer.append("APE(Prem,Payintv) as APE ,");
		// 现金价值
		strBuffer.append("CSV(InsuredYear,PolNo,DutyCode) as CSV,");
		// 核心风险保额
		strBuffer
				.append("SUMINSURED(Amnt,DutyCode,PolNo,Currency,Accumulatedefno) as Suminsured,");
		// 风险保额
		strBuffer
				.append("RISK_AMNT2 (InsuredYear,Amnt, DutyCode, PolNo, Currency,Accumulatedefno) as RiskAmnt,");
		strBuffer
				.append("Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,RISK_AMNT2 (InsuredYear,preAmnt, DutyCode, PolNo, Currency,Accumulatedefno) as PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,decode(FeeOperationType,'CT','CT','ZC') as StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,(ChangeRate*(RISK_AMNT2(InsuredYear,preAmnt,DutyCode,PolNo,Currency,Accumulatedefno))) as PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer
				.append(" a.PolNo||','|| d.Dutycode ||','||b.edorno||','||b.edortype||','||'"
						+ mAccumulateDefNo + "'||','||'GBBQ' as UnionKey, ");// 联合主键
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" c.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" d.Dutycode as DutyCode, ");
		strBuffer.append(" '' as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(d.getdate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME') as InsuredName, ");
		strBuffer.append(" a.InsuredSex as InsuredSex, ");
		strBuffer.append(" a.Insuredbirthday as InsuredBirthday, ");
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer.append(" '' as CurrentAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.Occupationlevel as OccupationType, ");
		strBuffer.append(" '' as HealthTime1, ");
		strBuffer.append(" '' as HealthTime2, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		strBuffer.append(" '' as SuppRiskScore, ");
		strBuffer
				.append(" nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') as SmokeFlag, ");
		strBuffer
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" '' as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");// Coupon
		strBuffer.append(" ''  as NonCashFlag, "); // Y-现金领取;N-非现金领取
		strBuffer.append(" c.StandPrem as StandPrem, ");
		strBuffer.append(" c.prem  as Prem, ");
		strBuffer.append(" c.amnt as Amnt, ");
		strBuffer.append(" 1 as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Faceamount,");
		strBuffer.append(" c.Currency as Currency, ");
		strBuffer.append(" c.PayIntv as PayIntv, ");
		strBuffer.append(" c.PayYears as PayYears, ");
		strBuffer.append(" ''as PayEndYearFlag, ");
		strBuffer.append(" '' as PayEndYear, ");
		strBuffer.append(" '' as GetYearFlag, ");
		strBuffer.append(" '' as GetYear, ");
		strBuffer.append(" '' as InsuYearFlag, ");
		strBuffer.append(" '' as InsuYear, ");
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
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" b.edorno as EdorNo, ");
		strBuffer.append(" b.edortype as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null)  as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		strBuffer.append(" 0 as AccAmnt, ");
		// 但保全类型为复效时，变更前保额、保费、风险保额都为0；当保全类型为保单贷款时，由于保单但款不操作lppol所有保额、保费、风险保额都从c表中获取
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
		strBuffer
				.append(" nvl((select RIContNo from RITempContLink where proposalno=a.polno and dutycode=c.dutycode),'000000') as RIContNo, ");
		strBuffer
				.append(" nvl((select RIPreceptNo from RITempContLink where proposalno=a.polno and dutycode=c.dutycode),'000000') as RIPreceptNo, ");
		strBuffer
				.append(" nvl((select State from ridutystate where proposalno=a.polno and dutycode=c.dutycode),'02') as ReinsreFlag, ");
		// 业务日期
		strBuffer.append(" d.getdate as GetDate, ");
		strBuffer
				.append(" rigetplancode(c.contno,c.polno,c.dutycode) as StandbyString1, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, ");// a.PLANTYPE
		strBuffer
				.append(" nvl((SELECT sum(x.insuaccbala) FROM Lcinsureacc x where x.polno=a.polno),0) as StandbyDouble1, ");
		strBuffer
				.append(" nvl((SELECT x.interestrate FROM riinterest x where  x.enddate is null and x.state='01' and x.riskcode=a.riskcode and x.dutyname=c.Planlevel),0) as StandbyDouble2, ");
		strBuffer
				.append(" nvl((SELECT max(z.rate) FROM Ldpubrate z where z.Caltype in('ULR','ULRL')and a.cvalidate  between z.startdate and z.enddate and z.riskcode=a.riskcode),0) as StandbyDouble3, ");
		strBuffer.append(" '' as StandbyDate1, ");
		strBuffer.append(" '' as StandbyDate2, ");
		strBuffer.append(" a.managecom as managecom ,");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.Mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" d.GetMoney as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		/*strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate1, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate2, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate3, ");*/
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= c.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=c.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
		strBuffer.append(" '' as StandbyString4, ");
		strBuffer.append(" '' as StandbyString5, ");
		strBuffer.append(" '' as StandbyString6, ");
		strBuffer.append(" '' as StandbyString7, ");
		strBuffer.append(" '' as StandbyString8, ");
		strBuffer.append(" '' as StandbyString9, ");
		strBuffer.append(" '' as StandbyDouble4, ");
		strBuffer.append(" '' as StandbyDouble5, ");
		strBuffer.append(" '' as StandbyDouble6, ");
		strBuffer.append(" '' as StandbyDouble7, ");
		strBuffer.append(" '' as StandbyDouble8, ");
		strBuffer.append(" '' as StandbyDouble9, ");
		strBuffer.append(" '' as StandbyDate3, ");
		strBuffer.append(" '' as StandbyDate4, ");
		strBuffer.append(" '' as StandbyDate5 ");

		strBuffer
				.append("from lcpol a,lpedoritem b,lcduty c,LJAGetEndorse d ");
		strBuffer.append("where a.ContType='1' ");
		strBuffer
				.append("and a.contno = b.contno  and a.polno = c.polno  and c.Dutycode=d.Dutycode ");
		strBuffer.append("and a.polno = d.polno ");
		strBuffer
				.append("and ((c.StandPrem-d.GetMoney)<>0 or (b.Edortype='AB' and a.Mainpolno!=a.Polno)) ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateRDCode r where a.riskcode = r.associatedcode  and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		strBuffer.append(" and d.getdate between '"
				+ mRIWFLogSchema.getStartDate() + "' and '"
				+ mRIWFLogSchema.getEndDate() + "'");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','|| d.Dutycode ||','||b.edorno||','||b.edortype||','||'"
						+ mAccumulateDefNo + "'||','||'GBBQ' )) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','|| d.Dutycode ||','||b.edorno||','||b.edortype||','||'"
						+ mAccumulateDefNo + "'||','||'GBBQ')) ");
		// 有效保单
		strBuffer
				.append(" and exists(select 'X' from lccontstate where  StateType = 'Available' and State = '0' and enddate is null and polno=a.polno ) ");
		// 增额，减额，部分退保
		strBuffer
				.append(" and a.contno = c.Contno and b.edortype  not in('CF','CT','ES','SX','BR')  and b.edorstate='0'");
		// 临分的保单
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where state ='03' and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.serialno) and b.proposalno=a.polno) ");
		// strBuffer.append(" and '"+mAccumulateDefNo+"' in ('L01010040010','L01010070010')");
		strBuffer.append(" and a.Currency='01'");
		strBuffer.append(") ");
		System.out.println("共保保全临分提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	/**
	 * getCErrors
	 * 
	 * @return CErrors
	 * @todo Implement this
	 *       com.sinosoft.lis.reinsure.calculate.distill.RIDistill method
	 */
	public CErrors getCErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIDataMake";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public void test(VData cInputData, String cOperate) {
		getInputData(cInputData, cOperate);
		getDistillSQL();
	}

	public static void main(String[] args) {
		VData cInputData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("test00001");
		mRIWFLogSchema.setStartDate("2011-11-02");
		mRIWFLogSchema.setEndDate("2011-11-23");
		mRIWFLogSchema.setTaskCode("L01010040010");
		cInputData.add(mRIWFLogSchema);
		RIIBCoDistillBQ tRIDistillRN = new RIIBCoDistillBQ();
		tRIDistillRN.submitData(cInputData, "01");
	}
}


