



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
 * Description:再保失效(过了宽限期未缴费),自动垫交通用可参考提数程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class RINewDistillSX implements RIDistill {
	String mEventType;
	RIWFLogSchema mRIWFLogSchema;
	String mAccumulateDefNo;
	private MMap mMap;
	private VData mInputData;
	private PubSubmit mPubSubmit = new PubSubmit();

	private String tSql;

	public RINewDistillSX() {
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

	// 非临分SQL公共部分
	private String getUnSQL() {
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
		strBuffer.append(" 0 as RiskAmnt,");
		strBuffer
				.append(" Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,decode(FeeOperationType,'CT','CT','ZC') as StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,(ChangeRate*(RISK_AMNT2(InsuredYear,preAmnt,DutyCode,PolNo,Currency,Accumulatedefno))) as PreChRiskAmnt, ");
		strBuffer.append(" 0 as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '01' as RecordType, ");
		strBuffer.append(" '01' as NodeState,");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
				+ mAccumulateDefNo + "' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" d.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" d.DutyCode as DutyCode, ");
		strBuffer.append(" d.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12))), d.cvalidate - 1) / 12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" d.cvalidate as CValiDate, ");
		strBuffer.append(" d.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')  as InsuredName, ");
		strBuffer.append(" decode(a.InsuredSex,'0','0','1') as InsuredSex, ");
		strBuffer.append(" a.InsuredBirthday as InsuredBirthday, ");

		strBuffer.append(" floor(months_between(d.cvalidate, a.insuredbirthday) / 12) as InsuredAge, ");
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
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" '' as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag, ");
		strBuffer.append(" 0 as StandPrem, ");
		strBuffer.append(" 0 as Prem, ");
		strBuffer.append(" 0 as Amnt, ");
		strBuffer.append(" d.Mult as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		// strBuffer.append(" '' as APE, ");
		// strBuffer.append(" '' as CSV, ");
		strBuffer.append(" '' as Retention, ");
		// strBuffer.append(" '' as Suminsured, ");
		strBuffer.append(" '' as Faceamount, ");
		strBuffer.append(" d.Currency as Currency, ");
		// 风险保额
		// strBuffer.append(" d.RiskAmnt as RiskAmnt, ");
		strBuffer.append(" d.PayIntv as PayIntv, ");
		strBuffer.append(" d.PayYears as PayYears, ");
		strBuffer.append(" d.PayEndYearFlag as PayEndYearFlag, ");
		strBuffer.append(" d.PayEndYear as PayEndYear, ");
		strBuffer.append(" '' as GetYearFlag, ");
		strBuffer.append(" '' as GetYear, ");
		strBuffer.append(" d.InsuYearFlag as InsuYearFlag, ");
		strBuffer.append(" d.insuyear as InsuYear, ");
		strBuffer.append(" '' as AcciYearFlag, ");
		strBuffer.append(" '' as AcciYear, ");
		strBuffer.append(" '' as AcciEndDate, ");
		strBuffer.append(" '' as GetStartDate, ");
		strBuffer
				.append(" (select FirstTrialOperator from lccont lcc where lcc.contno=a.contno) as GetStartType, ");
		strBuffer.append(" '' as PeakLine, ");
		strBuffer.append(" '' as GetLimit, ");
		strBuffer.append(" '' as GetIntv, ");
		strBuffer.append(" '' as PayNo, ");
		strBuffer.append(" '' as PayCount, ");
		strBuffer.append(" '' as PayMoney, ");
		strBuffer.append(" '' as LastPayToDate, ");
		strBuffer.append(" '' as CurPayToDate, ");
		strBuffer.append(" '' as EdorNo, ");
		strBuffer.append(" 'SX' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null)  as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer.append(" a.RiskAmnt as AccAmnt, ");// 累计风险保额
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer
				.append(" d.amnt as PreAmnt, ");
		strBuffer.append(" '' as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" 0 as StandGetMoney, ");
		strBuffer.append(" '' as GetRate, ");// 赔付比例
		strBuffer.append(" 0 as ClmGetMoney, ");
		strBuffer.append(" '' as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer.append(" 'R00000000' as RIContNo, ");
		strBuffer
				.append(" (select b.Ripreceptno from RIPrecept b where b.Ricontno='R00000000' and b.Accumulatedefno='"
						+ mAccumulateDefNo + "') as RIPreceptNo, ");
		strBuffer.append(" '' as ReinsreFlag, ");
		strBuffer.append(" decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12))) as GetDate, ");
		strBuffer.append(" rigetplancode(d.contno,d.polno,d.dutycode) as StandbyString1, ");
		// 如果没有健康加费，提数为空的sql
		// strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		// 如果没有健康加费，提数为A=0
		// strBuffer.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, "); // a.PLANTYPE
		// 长短险标志 1:短期险 2：长期险
		// strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2') from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
		// strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		strBuffer.append(" null as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" a.managecom as ManageCom, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" 0 as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.MainPolNo as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" '' as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
		strBuffer.append(" '' as StandbyString4, ");
		strBuffer.append(" '' as StandbyString5, ");
		strBuffer.append(" '' as StandbyString6, ");
		strBuffer.append(" '' as StandbyString7, ");
		strBuffer.append(" '' as StandbyString8, ");
		strBuffer.append(" '' as StandbyString9, ");
		strBuffer.append(" 0 as StandbyDouble4, ");
		strBuffer.append(" 0 as StandbyDouble5, ");
		strBuffer.append(" 0 as StandbyDouble6, ");
		strBuffer.append(" 0 as StandbyDouble7, ");
		strBuffer.append(" 0 as StandbyDouble8, ");
		strBuffer.append(" 0 as StandbyDouble9, ");
		strBuffer.append(" '' as StandbyDate3, ");
		strBuffer.append(" '' as StandbyDate4, ");
		strBuffer.append(" '' as StandbyDate5 ");
		strBuffer.append(" from lcpol a ,lccontstate b ,lcduty d");

		System.out.println("失效提数公共部分sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	// 临分提数公共部分
	private String getSpecSQL() {
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
				.append(" Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,decode(FeeOperationType,'CT','CT','ZC') as StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,(ChangeRate*(RISK_AMNT2(InsuredYear,preAmnt,DutyCode,PolNo,Currency,Accumulatedefno))) as PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '01' as RecordType, ");
		strBuffer.append(" '01' as NodeState,");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
				+ mAccumulateDefNo + "' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" d.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" d.DutyCode as DutyCode, ");
		strBuffer.append(" d.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12))), d.cvalidate - 1) / 12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" d.cvalidate as CValiDate, ");
		strBuffer.append(" d.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')  as InsuredName, ");
		strBuffer.append(" decode(a.InsuredSex,'0','0','1') as InsuredSex, ");
		strBuffer.append(" a.InsuredBirthday as InsuredBirthday, ");

		strBuffer.append(" floor(months_between(d.cvalidate, a.insuredbirthday) / 12) as InsuredAge, ");
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
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" '' as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag, ");
		strBuffer.append(" 0 as StandPrem, ");
		strBuffer.append(" 0 as Prem, ");
		strBuffer.append(" 0 as Amnt, ");
		strBuffer.append(" d.Mult as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		// strBuffer.append(" '' as APE, ");
		// strBuffer.append(" '' as CSV, ");
		strBuffer.append(" '' as Retention, ");
		// strBuffer.append(" '' as Suminsured, ");
		strBuffer.append(" '' as Faceamount, ");
		strBuffer.append(" d.Currency as Currency, ");
		// 风险保额
		// strBuffer.append(" d.RiskAmnt as RiskAmnt, ");
		strBuffer.append(" d.PayIntv as PayIntv, ");
		strBuffer.append(" d.PayYears as PayYears, ");
		strBuffer.append(" d.PayEndYearFlag as PayEndYearFlag, ");
		strBuffer.append(" d.PayEndYear as PayEndYear, ");
		strBuffer.append(" '' as GetYearFlag, ");
		strBuffer.append(" '' as GetYear, ");
		strBuffer.append(" d.InsuYearFlag as InsuYearFlag, ");
		strBuffer.append(" d.insuyear as InsuYear, ");
		strBuffer.append(" '' as AcciYearFlag, ");
		strBuffer.append(" '' as AcciYear, ");
		strBuffer.append(" '' as AcciEndDate, ");
		strBuffer.append(" '' as GetStartDate, ");
		strBuffer
				.append(" (select FirstTrialOperator from lccont lcc where lcc.contno=a.contno) as GetStartType, ");
		strBuffer.append(" '' as PeakLine, ");
		strBuffer.append(" '' as GetLimit, ");
		strBuffer.append(" '' as GetIntv, ");
		strBuffer.append(" '' as PayNo, ");
		strBuffer.append(" '' as PayCount, ");
		strBuffer.append(" '' as PayMoney, ");
		strBuffer.append(" '' as LastPayToDate, ");
		strBuffer.append(" '' as CurPayToDate, ");
		strBuffer.append(" '' as EdorNo, ");
		strBuffer.append(" 'SX' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null)  as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer.append(" a.RiskAmnt as AccAmnt, ");// 累计风险保额
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer
				.append(" d.amnt as PreAmnt, ");
		strBuffer.append(" '' as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" 0 as StandGetMoney, ");
		strBuffer.append(" '' as GetRate, ");// 赔付比例
		strBuffer.append(" 0 as ClmGetMoney, ");
		strBuffer.append(" '' as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer
				.append(" nvl((select RIContNo from RITempContLink where proposalno=a.polno and dutycode=d.dutycode),'000000') as RIContNo, ");
		strBuffer
				.append(" nvl((select RIPreceptNo from RITempContLink where proposalno=a.polno and dutycode=d.dutycode),'000000') as RIPreceptNo, ");
		strBuffer
				.append(" nvl((select State from ridutystate where proposalno=a.polno and dutycode=d.dutycode),'02') as ReinsreFlag, ");
		strBuffer.append(" decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12))) as GetDate, ");
		strBuffer.append(" rigetplancode(d.contno,d.polno,d.dutycode) as StandbyString1, ");
		// 如果没有健康加费，提数为空的sql
		// strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		// 如果没有健康加费，提数为A=0
		// strBuffer.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, "); // a.PLANTYPE
		// 长短险标志 1:短期险 2：长期险
		// strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2') from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
		// strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		strBuffer.append(" null as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" a.managecom as ManageCom, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" 0 as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.MainPolNo as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" '' as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= d.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=d.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
		strBuffer.append(" '' as StandbyString4, ");
		strBuffer.append(" '' as StandbyString5, ");
		strBuffer.append(" '' as StandbyString6, ");
		strBuffer.append(" '' as StandbyString7, ");
		strBuffer.append(" '' as StandbyString8, ");
		strBuffer.append(" '' as StandbyString9, ");
		strBuffer.append(" 0 as StandbyDouble4, ");
		strBuffer.append(" 0 as StandbyDouble5, ");
		strBuffer.append(" 0 as StandbyDouble6, ");
		strBuffer.append(" 0 as StandbyDouble7, ");
		strBuffer.append(" 0 as StandbyDouble8, ");
		strBuffer.append(" 0 as StandbyDouble9, ");
		strBuffer.append(" '' as StandbyDate3, ");
		strBuffer.append(" '' as StandbyDate4, ");
		strBuffer.append(" '' as StandbyDate5 ");

		strBuffer.append(" from lcpol a ,lccontstate b ,lcduty d");
		System.out.println("失效临分提数公共部分sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	private boolean dealData() {
		try {
			RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
			RSWrapper rsWrapper = new RSWrapper();
			// 合同分保提数
			tSql = getUnSQL() + getDistillSQL();
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

			// 临时分保提数
			tSql = getSpecSQL() + getDistillSQL1();
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

			tSql = getUnSQL() + getDistillSQL2();
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

			tSql = getSpecSQL() + getDistillSQL3();
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

			tSql = getUnSQL() + getDistillSQL4();
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

			tSql = getSpecSQL() + getDistillSQL5();
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
			buildError("dealData", " 提取失效业务数据失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 失效合同提数
	 * 
	 * @return
	 */
	private String getDistillSQL() {
		StringBuffer strBuffer = new StringBuffer();

		// 只要曾经失效或者自垫就提不管现在的状态所有不用加enddate is null
		strBuffer.append(" where  a.conttype='1' ");
		// String
		// tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";
		// 不为临分的保单
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where state in ('00','02','03','04') and proposalno=a.polno ) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");

		strBuffer
				.append(" and exists(select 'X' from RIAccumulateGetDuty r where a.riskcode = r.associatedcode and d.Dutycode like r.Getdutycode||'%'  and r.accumulatedefno='");
		strBuffer.append(mAccumulateDefNo);
		strBuffer.append("')");
		if (mAccumulateDefNo.equals("L01010010050")) {
			strBuffer.append(" and a.riskcode ='IBW30'");
			strBuffer.append(" and a.SaleChnl ='08'");
			strBuffer.append(" and a.Currency='01'");
		}
		if (mAccumulateDefNo.equals("L01010010010")) {
			strBuffer.append(" and a.riskcode !='IBW30'");
		}
		if (mAccumulateDefNo.equals("L01010010040")) {
			strBuffer
					.append(" and not exists  (select 'X' from  lcpol  x  where   x.polno = a.Mainpolno  and x.riskcode ='IBT01'  ) ");
		}
		strBuffer
				.append("and a.riskcode not in('IBC02','IBC01','IRD04','IRA03')");
		// 保单失效日期落在区间内
		strBuffer
				.append(" and appflag ='1' and a.polno=b.polno and a.polno=d.polno and b.statetype in('Available') ");
		
		strBuffer
				.append(" and ((b.state = '1' and b.startdate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "') or (b.state='0' and exists (select 1 from LPRevalidateTrack track WHERE track.contno=a.contno and track.makedate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "'))) ");
		strBuffer.append(") ");
		System.out.println("失效提数sql: " + strBuffer.toString());
		return strBuffer.toString();

	}

	/**
	 * 失效临分提数
	 * 
	 * @return
	 */
	private String getDistillSQL1() {
		StringBuffer strBuffer = new StringBuffer();

		// 只要曾经失效或者自垫就提不管现在的状态所有不用加enddate is null
		strBuffer.append(" where  a.conttype='1' and appflag ='1' ");

		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		// 临分的保单
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where  state ='03' and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.serialno) and b.proposalno=a.polno) ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateGetDuty r where a.riskcode = r.associatedcode and d.Dutycode like r.Getdutycode||'%'  and r.accumulatedefno='");
		strBuffer.append(mAccumulateDefNo);
		strBuffer.append("')");
		if (mAccumulateDefNo.equals("L01010010050")) {
			strBuffer.append(" and a.riskcode ='IBW30'");
			strBuffer.append(" and a.SaleChnl ='08'");
			strBuffer.append(" and a.Currency='01'");
		}
		if (mAccumulateDefNo.equals("L01010010010")) {
			strBuffer.append(" and a.riskcode !='IBW30'");
		}
		if (mAccumulateDefNo.equals("L01010010040")) {
			strBuffer
					.append(" and not exists  (select 'X' from  lcpol  x  where   x.polno = a.Mainpolno  and x.riskcode ='IBT01'  ) ");
		}
		strBuffer
				.append("and a.riskcode not in('IBC02','IBC01','IRD04','IRA03')");
		// 保单失效日期落在区间内
		strBuffer
				.append(" and a.polno=b.polno  and a.polno=d.polno and b.statetype in('Available') ");
		strBuffer
				.append(" and ((b.state = '1' and b.startdate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "') or (b.state='0' and exists (select 1 from LPRevalidateTrack track WHERE track.contno=a.contno and track.makedate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "'))) ");
		
		strBuffer.append(") ");
		System.out.println("失效临分提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	private String getDistillSQL2() {
		StringBuffer strBuffer = new StringBuffer();

		// 只要曾经失效或者自垫就提不管现在的状态所有不用加enddate is null
		strBuffer.append(" where  a.conttype='1' ");
		// String
		// tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";
		// 不为临分的保单
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where state in ('00','02','03','04') and proposalno=a.polno ) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer.append(" and a.riskcode not in('IBC02','IBC01')");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateGetDuty r where a.riskcode = r.associatedcode and d.Dutycode like r.Getdutycode||'%' and r.accumulatedefno='"
						+ mAccumulateDefNo
						+ "' and r.accumulatedefno!='L01010010050')");
		strBuffer.append(" and a.riskcode ='IBW30'");
		strBuffer.append(" and a.SaleChnl ='03'");
		strBuffer.append(" and a.Currency='01'");
		// 保单失效日期落在区间内
		strBuffer
				.append(" and appflag ='1' and a.polno=b.polno and a.polno=d.polno and b.statetype in('Available') ");
		strBuffer
				.append(" and ((b.state = '1' and b.startdate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "') or (b.state='0' and exists (select 1 from LPRevalidateTrack track WHERE track.contno=a.contno and track.makedate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "'))) ");
		
		strBuffer.append(") ");
		System.out.println("失效提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	/**
	 * 失效临分提数
	 * 
	 * @return
	 */
	private String getDistillSQL3() {
		StringBuffer strBuffer = new StringBuffer();

		// 只要曾经失效或者自垫就提不管现在的状态所有不用加enddate is null
		strBuffer.append(" where  a.conttype='1' and appflag ='1' ");

		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		// 临分的保单
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where  state ='03' and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.serialno) and b.proposalno=a.polno) ");
		strBuffer.append("and a.riskcode not in('IBC02','IBC01')");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateGetDuty r where a.riskcode = r.associatedcode and d.Dutycode like r.Getdutycode||'%' and r.accumulatedefno='"
						+ mAccumulateDefNo
						+ "' and r.accumulatedefno!='L01010010050')");
		strBuffer.append(" and a.riskcode ='IBW30'");
		strBuffer.append(" and a.SaleChnl ='03'");
		strBuffer.append(" and a.Currency='01'");
		// 保单失效日期落在区间内
		strBuffer
				.append(" and a.polno=b.polno  and a.polno=d.polno and b.statetype in('Available') ");
		strBuffer
				.append(" and ((b.state = '1' and b.startdate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "') or (b.state='0' and exists (select 1 from LPRevalidateTrack track WHERE track.contno=a.contno and track.makedate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "'))) ");
		
		strBuffer.append(") ");
		System.out.println("失效临分提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	private String getDistillSQL4() {
		StringBuffer strBuffer = new StringBuffer();

		// 只要曾经失效或者自垫就提不管现在的状态所有不用加enddate is null
		strBuffer.append(" where  a.conttype='1' ");
		// String
		// tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";
		// 不为临分的保单
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where state in ('00','02','03','04') and proposalno=a.polno ) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");

		strBuffer
				.append(" and exists(select 'X' from RIAccumulateGetDuty r where a.riskcode = r.associatedcode and d.Dutycode like r.Getdutycode||'%' and r.accumulatedefno='"
						+ mAccumulateDefNo
						+ "' and r.accumulatedefno!='L01010010050')");
		strBuffer.append("and a.riskcode not in('IBC02','IBC01')");
		strBuffer.append(" and a.riskcode ='IBW30'");
		strBuffer.append(" and a.Currency !='01'");
		// 保单失效日期落在区间内
		strBuffer
				.append(" and appflag ='1' and a.polno=b.polno and a.polno=d.polno and b.statetype in('Available') ");
		strBuffer
				.append(" and ((b.state = '1' and b.startdate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "') or (b.state='0' and exists (select 1 from LPRevalidateTrack track WHERE track.contno=a.contno and track.makedate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "'))) ");
		
		strBuffer.append(") ");
		System.out.println("失效提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	/**
	 * 失效临分提数
	 * 
	 * @return
	 */
	private String getDistillSQL5() {
		StringBuffer strBuffer = new StringBuffer();

		// 只要曾经失效或者自垫就提不管现在的状态所有不用加enddate is null
		strBuffer.append(" where  a.conttype='1' and appflag ='1' ");

		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||',' || 'SX'||','||decode(b.state,'1',b.startdate,(add_months(d.cvalidate,ceil(months_between(to_date('"
						+ mRIWFLogSchema.getEndDate()
						+ "','yyyy-MM-dd'),d.cvalidate-1)/12-1)*12)))||','||'"
						+ mAccumulateDefNo + "')) ");
		// 临分的保单
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where  state ='03' and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.serialno) and b.proposalno=a.polno) ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateGetDuty r where a.riskcode = r.associatedcode and d.Dutycode like r.Getdutycode||'%' and r.accumulatedefno='"
						+ mAccumulateDefNo
						+ "' and r.accumulatedefno!='L01010010050')");
		strBuffer.append("and a.riskcode not in('IBC02','IBC01')");
		strBuffer.append(" and a.riskcode ='IBW30'");
		strBuffer.append(" and a.Currency !='01'");
		// 保单失效日期落在区间内
		strBuffer
				.append(" and a.polno=b.polno  and a.polno=d.polno and b.statetype in('Available') ");
		strBuffer
				.append(" and ((b.state = '1' and b.startdate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "') or (b.state='0' and exists (select 1 from LPRevalidateTrack track WHERE track.contno=a.contno and track.makedate between '" + mRIWFLogSchema.getStartDate() + "' and '"	+ mRIWFLogSchema.getEndDate() + "'))) ");
		
		strBuffer.append(") ");
		System.out.println("失效临分提数sql: " + strBuffer.toString());
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
		getUnSQL();
		getDistillSQL4();
	}

	public static void main(String[] args) {
		VData cInputData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("test00001");
		mRIWFLogSchema.setStartDate("2014-11-28");
		mRIWFLogSchema.setEndDate("2014-11-28");
		mRIWFLogSchema.setTaskCode("L01010010010");
		cInputData.add(mRIWFLogSchema);
		RINewDistillSX tRIDistillSX = new RINewDistillSX();
		tRIDistillSX.test(cInputData, "01");
	}
}


