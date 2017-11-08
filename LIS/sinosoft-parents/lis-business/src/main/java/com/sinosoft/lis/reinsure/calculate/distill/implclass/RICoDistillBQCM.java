

package com.sinosoft.lis.reinsure.calculate.distill.implclass;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistill;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:再保保全CM通用可参考提数程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 
 * @version 1.0
 */
public class RICoDistillBQCM implements RIDistill {
	String mEventType;
	RIWFLogSchema mRIWFLogSchema;
	String mAccumulateDefNo;
	private MMap mMap;
	private VData mInputData;
	private PubSubmit mPubSubmit = new PubSubmit();
	private String accCessPrem = "0";
	private String tSql;

	public RICoDistillBQCM() {
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
		if ("L01010040010".equals(mAccumulateDefNo)) {
			String sql = "select (case when sum(premchang)>400000000 then 1 else 0 end) from rirecordtrace a where a.ripreceptno='C01010040020' and a.ridate<='" + mRIWFLogSchema.getEndDate() + "'";
			accCessPrem = new ExeSQL().getOneValue(sql);
		}

		try {
			RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
			RSWrapper rsWrapper = new RSWrapper();
			// 合同分保提数
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
			// 临时分保提数
			tSql = getDistillSQL1();

		} catch (Exception ex) {
			buildError("dealData", " 提取保全CM业务数据失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

	private String getDistillSQL() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select BatchNo, EventNo,EventType,RecordType,NodeState,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,PlanType,RiskPeriod,RiskType,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredBirthday,InsuredAge,CurrentAge,IDType,IDNo,OccupationType,HealthTime1,HealthTime2,OccupationCode,SuppRiskScore,SmokeFlag,AdditionalRate,ExtType,ExtPrem,BegAccountValue,EndUnit,EndAccountValue,FeeType,FeeMoney,NonCashFlag,StandPrem,Prem,Amnt,Mult,Reserve,APE,");
		strBuffer
				.append("CSV,Retention,Suminsured,Faceamount,Currency,RiskAmnt,");
		strBuffer
				.append("PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag ,GetDate,");
		strBuffer
				.append("StandbyString1, StandbyString2,StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,");
		strBuffer
				.append("PreChRiskAmnt, ChRiskAmnt,PreChangeRate,MainPolNo,RIPreceptType,BFFlag from(");

		strBuffer.append(" select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer
				.append(" a.PolNo||','||b.edorno||','||b.edortype as UnionKey, ");// 联合主键：polno||","||edorno||","||edortype
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" '' as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append("  '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" d.Dutycode as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(b.edorvalidate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer.append(" (select x.Name from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno)  as InsuredName, ");
		strBuffer.append(" a.InsuredSex as InsuredSex, ");
		strBuffer.append(" a.Insuredbirthday as InsuredBirthday, ");
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer
				.append(" (a.InsuredAppAge+ceil(months_between(b.edorvalidate, a.cvalidate - 1)-1)) as CurrentAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.OccupationType as OccupationType, ");
		strBuffer.append(" '' as HealthTime1, ");
		strBuffer.append(" '' as HealthTime2, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		strBuffer.append(" '' as SuppRiskScore, ");
		strBuffer.append(" nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') as SmokeFlag, ");
		strBuffer.append(" '' as AdditionalRate, ");
		strBuffer.append(" '' as ExtType, ");
		strBuffer.append(" '' as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" '' as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag,  ");
//		strBuffer
//				.append(" getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'StandPrem') as StandPrem,  ");
//		strBuffer
//				.append(" getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'Prem') as Prem, ");
//
//		strBuffer
//				.append(" getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'Amnt') as Amnt,   ");
		strBuffer
				.append(" decode( b.edortype,'CT',0 , d.StandPrem ) as StandPrem, ");
		strBuffer.append(" decode( b.edortype,'CT' , 0 , d.Prem ) as Prem, ");
		
		strBuffer.append(" decode( b.edortype,'CT' , 0 , d.amnt ) as Amnt,   ");
				strBuffer.append(" '' as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as  APE, ");
		strBuffer.append(" '' as CSV, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Suminsured, ");
		strBuffer.append("  '' as Faceamount,");
		strBuffer.append(" a.Currency as Currency, ");
		// 风险保额
		strBuffer
				.append(" Risk_Amnt(a.payyears,ceil(months_between(b.edorvalidate,a.cvalidate-1)/12),getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'Amnt'),a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");// 风险保额
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
		strBuffer.append(" b.edorno as EdorNo, ");
		// strBuffer.append(" case when b.edortype='CT' and b.hesitateflag='Y' then 'CTY' else b.edortype end as FeeOperationType, ");
		strBuffer.append(" b.edortype as FeeOperationType, ");

		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency=a.Currency and x.Startdate=(select max(x.Startdate) from RIExchangeRate) and x.Enddate is null) as ChangeRate, ");
		strBuffer.append(" 'HKD' as AccCurrency, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer.append(" 0 as AccAmnt, ");
		// 但保全类型为复效时，变更前保额、保费、风险保额都为0；当保全类型为保单贷款时，由于保单但款不操作lppol所有保额、保费、风险保额都从c表中获取
		strBuffer.append(" e.standprem as PreStandPrem,  ");
		strBuffer.append(" e.prem as PrePrem, ");
		// 风险保额
		strBuffer
				.append(" Risk_Amnt(c.payyears,ceil(months_between(b.edorvalidate,c.cvalidate-1)/12),c.amnt,c.InsuredAppAge,c.years,c.InsuredSex,c.prem,c.riskcode,c.payendyear,c.payendyearflag,c.insuyear,c.insuyearflag,c.payintv) as PreRiskAmnt,");
		strBuffer.append(" e.amnt as PreAmnt, ");
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
		strBuffer.append(" '' as ReinsreFlag, ");
		// 业务日期
		strBuffer.append(" b.edorvalidate as GetDate, ");
		strBuffer.append(" rigetplancode(d.contno,d.polno,d.dutycode) as StandbyString1, ");
		strBuffer.append(" null as StandbyString2, ");
		strBuffer.append(" '' as StandbyString3, ");// a.PLANTYPE
		strBuffer.append(accCessPrem + " as StandbyDouble1, ");
		strBuffer.append("  '' as StandbyDouble2, ");
		strBuffer.append(" '' as StandbyDouble3, ");
		strBuffer.append(" '' as StandbyDate1, ");
		strBuffer.append("  '' as StandbyDate2, ");
		strBuffer.append(" a.managecom as managecom ,");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.Mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag  ");
		strBuffer.append(" from lcpol a ,lpedoritem b,Lcduty d,Lpduty e");

		strBuffer.append(" where a.contno=b.contno and a.conttype='1'");
		// String
		// tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";
		// 不为临分的保单
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where proposalno=a.proposalno and state in ('00','02','03','04')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||b.edorno||','||b.edortype)) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||b.edorno||','||b.edortype)) ");
		strBuffer
				.append(" and exists(select 'X' from riaccumulaterdcode r where a.riskcode = r.associatedcode and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		// 被保人重要资料变更
		strBuffer.append(" and a.insuredno=b.insuredno ");
		strBuffer.append(" and( d.StandPrem-e.StandPrem)<>0");
		strBuffer
				.append(" and  a.Polno=d.Polno and b.Edorno = e.Edorno and a.contno = e.Contno  ");
		// edortype CM:客户重要资料变更  IO:被保人职业类别变更
		strBuffer
				.append(" and b.edorstate='0' and b.edortype in('PP','OC') and b.edorvalidate between '"
						+ mRIWFLogSchema.getStartDate()
						+ "' and '"
						+ mRIWFLogSchema.getEndDate() + "' ");
		strBuffer.append(" and a.Riskcode in ");
		strBuffer.append("('BE05I', 'EE05I', 'BE05T', 'EE05T', 'BE05V', 'EE05V', 'BE05S','BE05U', 'BE05W', 'IBE53', 'BAU01', 'BE18A', 'EE18A', 'BE18B', 'BE18C', 'IBE62') ");
		
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer.append(") ");
		System.out.println("共保保全CM提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	private String getDistillSQL1() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select BatchNo, EventNo,EventType,RecordType,NodeState,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,PlanType,RiskPeriod,RiskType,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredBirthday,InsuredAge,CurrentAge,IDType,IDNo,OccupationType,HealthTime1,HealthTime2,OccupationCode,SuppRiskScore,SmokeFlag,AdditionalRate,ExtType,ExtPrem,BegAccountValue,EndUnit,EndAccountValue,FeeType,FeeMoney,NonCashFlag,StandPrem,Prem,Amnt,Mult,Reserve,APE,");
		strBuffer
				.append("CSV,Retention,Suminsured,Faceamount,Currency,RiskAmnt,");
		strBuffer
				.append("PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag ,GetDate,");
		strBuffer
				.append("StandbyString1, StandbyString2,StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,");
		strBuffer
				.append("PreChRiskAmnt, ChRiskAmnt,PreChangeRate,MainPolNo,RIPreceptType,BFFlag from(");

		strBuffer.append(" select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '' as RecordType, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer
				.append(" a.PolNo||','||b.edorno||','||b.edortype as UnionKey, ");// 联合主键：polno||","||edorno||","||edortype
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" '' as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append("  '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" d.Dutycode as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(b.edorvalidate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer.append(" a.InsuredName as InsuredName, ");
		strBuffer.append(" a.InsuredSex as InsuredSex, ");
		strBuffer.append(" a.Insuredbirthday as InsuredBirthday, ");
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer
				.append(" (a.InsuredAppAge+ceil(months_between(b.edorvalidate, a.cvalidate - 1)-1)) as CurrentAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.OccupationType as OccupationType, ");
		strBuffer.append(" '' as HealthTime1, ");
		strBuffer.append(" '' as HealthTime2, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		strBuffer.append(" '' as SuppRiskScore, ");
		strBuffer.append(" nvl((select x.Smokeflag from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'N') as SmokeFlag, ");
		strBuffer.append(" '' as AdditionalRate, ");
		strBuffer.append(" '' as ExtType, ");
		strBuffer.append(" '' as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" '' as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag,  ");
		strBuffer
				.append(" decode( b.edortype,'CT',0 , d.StandPrem ) as StandPrem, ");
		strBuffer.append(" decode( b.edortype,'CT' , 0 , d.Prem ) as Prem, ");

		strBuffer.append(" decode( b.edortype,'CT' , 0 , d.amnt ) as Amnt,   ");
		strBuffer.append(" '' as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as  APE, ");
		strBuffer.append(" '' as CSV, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Suminsured, ");
		strBuffer.append("  '' as Faceamount,");
		strBuffer.append(" a.Currency as Currency, ");
		// 风险保额
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 , Risk_Amnt(a.payyears,ceil(months_between(b.edorvalidate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv)) as RiskAmnt, ");// 风险保额
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
		strBuffer.append(" b.edorno as EdorNo, ");
		// strBuffer.append(" case when b.edortype='CT' and b.hesitateflag='Y' then 'CTY' else b.edortype end as FeeOperationType, ");
		strBuffer.append(" b.edortype as FeeOperationType, ");

		strBuffer.append(" '' as FeeFinaType, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency=a.Currency and x.Startdate=(select max(x.Startdate) from RIExchangeRate) and x.Enddate is null) as ChangeRate, ");
		strBuffer.append(" 'HKD' as AccCurrency, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer.append(" 0 as AccAmnt, ");
		// 但保全类型为复效时，变更前保额、保费、风险保额都为0；当保全类型为保单贷款时，由于保单但款不操作lppol所有保额、保费、风险保额都从c表中获取
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 ,e.standprem ) as PreStandPrem,  ");
		strBuffer.append(" decode( b.edortype,'RE' , 0 ,e.prem ) as PrePrem, ");
		// 风险保额
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 , Risk_Amnt(c.payyears,ceil(months_between(b.edorvalidate,c.cvalidate-1)/12),c.amnt,c.InsuredAppAge,c.years,c.InsuredSex,c.prem,c.riskcode,c.payendyear,c.payendyearflag,c.insuyear,c.insuyearflag,c.payintv)) as PreRiskAmnt,");
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 , e.amnt ) as PreAmnt, ");
		strBuffer.append(" '' as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" 0 as StandGetMoney, ");
		strBuffer.append(" '' as GetRate, ");
		strBuffer.append(" 0 as ClmGetMoney, ");
		strBuffer.append(" '' as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer.append(" '' as RIContNo, ");
		strBuffer
				.append(" (select RIPreceptNo from RITempContLink where proposalno=a.proposalno ) as RIPreceptNo,");
		strBuffer
				.append(" (select State from ridutystate where proposalno=a.proposalno) as ReinsreFlag, ");
		// 业务日期
		strBuffer.append(" b.edorvalidate as GetDate, ");
		strBuffer.append(" rigetplancode(d.contno,d.polno,d.dutycode) as StandbyString1, ");
		strBuffer.append(" null as StandbyString2, ");
		strBuffer.append(" '' as StandbyString3, ");// a.PLANTYPE
		strBuffer.append(accCessPrem + " as StandbyDouble1, ");
		strBuffer.append("  '' as StandbyDouble2, ");
		strBuffer.append(" '' as StandbyDouble3, ");
		strBuffer.append(" '' as StandbyDate1, ");
		strBuffer.append("  '' as StandbyDate2, ");
		strBuffer.append(" a.managecom as managecom ,");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.Mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag  ");
		strBuffer.append(" from lcpol a ,lpedoritem b,Lcduty d,lpduty e");

		strBuffer.append(" where a.contno=b.contno and a.conttype='1'");

		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||b.edorno||','||b.edortype)) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||b.edorno||','||b.edortype)) ");
		// 临分的保单
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where b.proposalno=a.proposalno and state in ('00','03') and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno=b.standbystring1) ) ");

		strBuffer
				.append(" and exists(select 'X' from riaccumulaterdcode r where a.riskcode = r.associatedcode and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		// 被保人重要资料变更
		strBuffer.append(" and a.insuredno=b.insuredno ");
		strBuffer.append(" and (d.StandPrem-e.StandPrem)<>0");
		strBuffer
				.append(" and  a.Polno=d.Polno and b.Edorno = e.Edorno and a.contno = e.Contno  ");
		// edortype CM:客户重要资料变更  IO:被保人职业类别变更
		strBuffer
				.append(" and b.edorstate='0' and b.edortype in('PP','OC') and b.edorvalidate between '"
						+ mRIWFLogSchema.getStartDate()
						+ "' and '"
						+ mRIWFLogSchema.getEndDate() + "'");
		strBuffer.append(") ");
		System.out.println("保全CM临分提数sql: " + strBuffer.toString());
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
		getDistillSQL1();
	}

	public static void main(String[] args) {
		VData cInputData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("test00001");
		mRIWFLogSchema.setStartDate("2011-10-1");
		mRIWFLogSchema.setEndDate("2011-10-9");
		mRIWFLogSchema.setTaskCode("L000000001");
		cInputData.add(mRIWFLogSchema);
		RICoDistillBQCM tRIDistillBQCM = new RICoDistillBQCM();
		tRIDistillBQCM.test(cInputData, "01");
	}
}

