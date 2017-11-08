



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
 * Description:共保理赔通过可参考提数程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author JINTAO
 * @version 1.0
 */
public class RICoDistillCL implements RIDistill {
	String mEventType;
	RIWFLogSchema mRIWFLogSchema;
	String mAccumulateDefNo;
	private MMap mMap;
	private VData mInputData;
	private PubSubmit mPubSubmit = new PubSubmit();

	private String tSql;

	public RICoDistillCL() {
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

			// 回退提数
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

			// 合同分保临分提数
			tSql = getDistillSQL2();
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

			// 回退临分提数
			tSql = getDistillSQL3();
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
			buildError("dealData", " 提取共保理赔业务数据失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

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
				.append(" Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,decode(FeeOperationType,'CT','CT','ZC') as StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '04' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo || ','||g.dutycode||','||h.clmno||','||'"
				+ mAccumulateDefNo + "'||','||'CL' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" g.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" g.dutycode as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(h.endcasedate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')  as InsuredName, ");
		strBuffer.append(" decode(a.InsuredSex,'0','0','1') as InsuredSex, ");
		strBuffer.append(" a.InsuredBirthday as InsuredBirthday, ");
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
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" a.nonparflag as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag, ");
		// 追朔出险时点的保费
		// strBuffer.append(" nvl((select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno), a.StandPrem ) as StandPrem, ");
		// strBuffer.append(" nvl((select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.prem) as Prem, ");
		strBuffer.append(" g.standprem as StandPrem, ");
		strBuffer.append(" g.prem  as Prem, ");
		// 风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
		// strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(h.endcasedate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
		// 追朔出险时点的保额
		// strBuffer.append(" nvl((select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.amnt) as Amnt, ");
		strBuffer.append(" g.amnt as Amnt, ");
		strBuffer.append(" '' as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Suminsured, ");
		strBuffer.append(" '' as Faceamount, ");
		strBuffer.append(" a.currency as Currency, ");
		strBuffer.append(" a.PayIntv as PayIntv, ");
		strBuffer.append(" g.PayYears as PayYears, ");
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
		strBuffer.append(" 'CL' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null) as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		strBuffer.append(" 0 as AccAmnt, ");
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer.append(" 0 as PreAmnt, ");
		strBuffer.append(" h.clmno as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" h.standpay as StandGetMoney, ");
		strBuffer.append(" g.getrate as GetRate, ");
		strBuffer.append(" h.realpay as ClmGetMoney, ");
		strBuffer.append(" h.accdate as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer.append(" 'R00000000' as RIContNo, ");
		strBuffer
				.append(" (select b.Ripreceptno from RIPrecept b where b.Ricontno='R00000000' and b.Accumulatedefno='"
						+ mAccumulateDefNo + "') as RIPreceptNo, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '' as ReinsreFlag, ");
		strBuffer.append(" h.endcasedate as GetDate, ");
		strBuffer
				.append(" rigetplancode(g.contno,g.polno,g.dutycode) as StandbyString1, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, ");
		// 长短险标志 1:短期险 2：长期险
		strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		// 生日
		strBuffer.append(" '' as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" '' as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		/*strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate1, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate2, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate3, ");*/
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
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

		strBuffer.append(" from lcpol a,lcduty g,( ");
		strBuffer.append(" select c.caseno clmno,");// 分案号 *****************
		strBuffer.append(" c.polno polno,");
		strBuffer.append(" max(e.endcasedate) endcasedate,");// 结案日期
		strBuffer.append(" sum(c.standpay) standpay,");// 核算赔付金额
														// *****************
		strBuffer.append(" sum(c.realpay) realpay,");// 核赔赔付金额 *****************
		strBuffer.append(" max(d.Accidentdate) AccDate,");// 出险日期
															// AccStartDate//出险开始日期
															// *************
		strBuffer.append(" max(c.getdutykind) getdutykind ");// 付责任类型
																// 100意外医疗101意外伤残102意外死亡103意外高残104意外大病105意外特种疾病106意外失业失能109意外豁免200疾病医疗201疾病伤残
		// strBuffer.append(" max(confdate)   confdate "); //理赔核销日期
		// ****************
		strBuffer.append(" from  llregister d, LLClaim e,LLClaimDetail c ");
		strBuffer.append(" where d.Rgtno=e.Rgtno and c.caseno = e.caseno ");
		/*
		 * 如果提数是到给付责任增加下面的查询条件, LLClaimDetail.getdutykind类型： X00--医疗 X01--残疾金
		 * X02--身故金 X03--全残 X04--重大疾病 X05--女性特种疾病（描述女性妊娠期疾病保险金和婴儿身故保险金等）
		 * X06--失业和失能 X07--生命末期 X09--豁免 X为1，指意外；X为2，指疾病 001--临时保单意外死亡 如：102为意外身故
		 */
		// strBuffer.append(" and c.getdutycode='' and c.getdutykind='' and c.dutycode='' ");
		// strBuffer.append(" and f.othernotype='5' ");
		strBuffer.append(" and c.givetype <> '1'"); // 非拒赔
		strBuffer.append(" and e.clmstate = '50'");
		strBuffer.append(" and e.endcasedate between '"
				+ mRIWFLogSchema.getStartDate() + "' and '"
				+ mRIWFLogSchema.getEndDate() + "' ");
		strBuffer
				.append(" and exists (select 'X' from ljaget f where f.otherno=c.clmno and f.confdate is not null and  f.Othernotype = '5' ) ");
		strBuffer.append(" group by c.caseno, c.polno,d.Accidentdate");
		strBuffer.append(") h");

		strBuffer.append(" where a.conttype='1' ");
		// String
		// tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";

		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo || ','||g.dutycode||','||h.clmno||','||'"
						+ mAccumulateDefNo + "'||','||'CL')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo || ','||g.dutycode||','||h.clmno||','||'"
						+ mAccumulateDefNo + "'||','||'CL')) ");
		// 不为临分的保单
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where proposalno=a.polno and  state in ('00','02','03','04'))");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateRDCode r where a.riskcode = r.associatedcode  and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer.append(" and a.polno=h.polno ");
		strBuffer.append(" and g.polno=a.polno");
		// strBuffer.append(" and '"+mAccumulateDefNo+"' in ('L01010040010','L01010070010')");
		strBuffer.append(" and a.Currency='01'");
		strBuffer.append(") ");
		System.out.println("共保理赔提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	// 理赔回退未领取
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
				.append(" Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '04' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo || ','||h.clmno||','||h.Confdate||','||'"
				+ mAccumulateDefNo + "'||','||'CLHT' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" g.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" g.dutycode as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(h.Confdate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')  as InsuredName, ");
		strBuffer.append(" decode(a.InsuredSex,'0','0','1') as InsuredSex, ");
		strBuffer.append(" a.InsuredBirthday as InsuredBirthday, ");
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
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" a.nonparflag as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag, ");
		// 追朔出险时点的保费
		// strBuffer.append(" nvl((select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno), a.StandPrem ) as StandPrem, ");
		// strBuffer.append(" nvl((select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.prem) as Prem, ");
		strBuffer.append(" g.standprem as StandPrem, ");
		strBuffer
				.append(" (select Prem from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is null)  as Prem, ");
		// 风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
		// strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(h.endcasedate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
		// 追朔出险时点的保额
		// strBuffer.append(" nvl((select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.amnt) as Amnt, ");
		strBuffer.append(" g.amnt as Amnt, ");
		strBuffer.append(" '' as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Suminsured, ");
		strBuffer.append(" '' as Faceamount, ");
		strBuffer.append(" a.currency as Currency, ");
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
		strBuffer.append(" 'CLHT' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null) as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		strBuffer.append(" 0 as AccAmnt, ");
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer.append(" 0 as PreAmnt, ");
		strBuffer.append(" h.clmno as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" h.getpay as StandGetMoney, ");
		strBuffer.append(" g.getrate as GetRate, ");
		strBuffer.append(" h.getpay as ClmGetMoney, ");
		strBuffer.append(" h.accdate as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer.append(" 'R00000000' as RIContNo, ");
		strBuffer
				.append(" (select b.Ripreceptno from RIPrecept b where b.Ricontno='R00000000' and b.Accumulatedefno='"
						+ mAccumulateDefNo + "') as RIPreceptNo, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" '' as ReinsreFlag, ");
		strBuffer.append(" h.Confdate as GetDate, ");
		strBuffer
				.append(" rigetplancode(g.contno,g.polno,g.dutycode) as StandbyString1, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, ");
		strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		// 生日
		strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" '' as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		/*strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate1, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate2, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate3, ");*/
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
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
		strBuffer.append(" from lcduty g,lcpol a,( ");
		strBuffer.append(" select f.Polno,c.Actugetno,c.Confdate,a.Dutycode,");// 分案号
																				// *****************
		strBuffer.append(" max(e.Accidentdate) accdate,");// 出险日期
		strBuffer.append(" sum(a.Pay) getpay,");// 财务pay *****************
		// strBuffer.append(" max(confdate)   confdate "); //理赔核销日期
		// ****************
		strBuffer.append(" f.Caseno clmno ");
		strBuffer
				.append(" from  LJAGetClaim a ,LJAGet c, llregister e, LLClaimDetail f ");
		strBuffer
				.append(" where f.Rgtno=e.Rgtno  and c.Otherno = f.Caseno and a.Actugetno=c.Actugetno and a.Confdate is  null ");
		// strBuffer.append(" and c.EnterAccDate between '"+mRIWFLogSchema.getStartDate()+"' and '"+mRIWFLogSchema.getEndDate()+"' ");
		strBuffer
				.append(" and  exists (select 'X' from Llcaseback a  where a.Clmno = c.Otherno and c.Othernotype = '5' and (c.Dblconfstate != '9' or c.Dblconfstate is null) and a.Backdate between '"
						+ mRIWFLogSchema.getStartDate()
						+ "' and '"
						+ mRIWFLogSchema.getEndDate() + "' )");
		strBuffer
				.append(" group by  f.Caseno, f.Polno,c.Actugetno ,c.Confdate,a.Dutycode");
		strBuffer.append(") h");

		strBuffer.append(" where a.conttype='1' ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateRDCode r where a.riskcode = r.associatedcode  and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		// 不为临分的保单
		strBuffer
				.append(" and not exists (select 'X' from ridutystate where proposalno=a.polno and  state in ('00','02','03','04'))");
		strBuffer.append(" and h.Polno = g.Polno ");
		strBuffer.append(" and a.polno = h.polno");
		strBuffer.append(" and a.Currency='01'");
		strBuffer.append(" and h.dutycode=g.Dutycode");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo || ','||h.clmno||','||h.Confdate||','||'"
						+ mAccumulateDefNo + "'||','||'CLHT')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo || ','||h.clmno||','||h.Confdate||','||'"
						+ mAccumulateDefNo + "'||','||'CLHT')) ");
		// strBuffer.append(" and '"+mAccumulateDefNo+"' in ('L01010040010','L01010070010')");
		strBuffer.append(") ");
		System.out.println("共保理赔回退提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	// 临分
	private String getDistillSQL2() {
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
				.append(" Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,decode(FeeOperationType,'CT','CT','ZC') as StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '04' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo || ','||g.dutycode||','||h.clmno||', '||'"
				+ mAccumulateDefNo + "'||','||'CL' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" g.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" g.dutycode as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(h.endcasedate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')  as InsuredName, ");
		strBuffer.append(" decode(a.InsuredSex,'0','0','1') as InsuredSex, ");
		strBuffer.append(" a.InsuredBirthday as InsuredBirthday, ");
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
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" a.nonparflag as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag, ");
		// 追朔出险时点的保费
		// strBuffer.append(" nvl((select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno), a.StandPrem ) as StandPrem, ");
		// strBuffer.append(" nvl((select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.prem) as Prem, ");
		strBuffer.append(" g.standprem as StandPrem, ");
		strBuffer.append(" g.prem  as Prem, ");
		// 风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
		// strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(h.endcasedate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
		// 追朔出险时点的保额
		// strBuffer.append(" nvl((select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.amnt) as Amnt, ");
		strBuffer.append(" g.amnt as Amnt, ");
		strBuffer.append(" '' as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Suminsured, ");
		strBuffer.append(" '' as Faceamount, ");
		strBuffer.append(" a.currency as Currency, ");
		strBuffer.append(" a.PayIntv as PayIntv, ");
		strBuffer.append(" g.PayYears as PayYears, ");
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
		strBuffer.append(" 'CL' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null) as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		strBuffer.append(" 0 as AccAmnt, ");
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer.append(" 0 as PreAmnt, ");
		strBuffer.append(" h.clmno as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" h.standpay as StandGetMoney, ");
		strBuffer.append(" g.getrate as GetRate, ");
		strBuffer.append(" h.realpay as ClmGetMoney, ");
		strBuffer.append(" h.accdate as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer
				.append(" nvl((select RIContNo from RITempContLink where proposalno=a.polno and dutycode=g.dutycode),'000000') as RIContNo, ");
		strBuffer
				.append(" nvl((select RIPreceptNo from RITempContLink where proposalno=a.polno and dutycode=g.dutycode),'000000') as RIPreceptNo, ");
		strBuffer
				.append(" nvl((select State from ridutystate where proposalno=a.polno and dutycode=g.dutycode),'02') as ReinsreFlag, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" h.endcasedate as GetDate, ");
		strBuffer
				.append(" rigetplancode(g.contno,g.polno,g.dutycode) as StandbyString1, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, ");
		// 长短险标志 1:短期险 2：长期险
		strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		// 生日
		strBuffer.append(" '' as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" '' as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		/*strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate1, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate2, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate3, ");*/
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
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

		strBuffer.append(" from lcpol a,lcduty g,( ");
		strBuffer.append(" select c.caseno clmno,");// 分案号 *****************
		strBuffer.append(" c.polno polno,");
		strBuffer.append(" max(e.endcasedate) endcasedate,");// 结案日期
		strBuffer.append(" sum(c.standpay) standpay,");// 核算赔付金额
														// *****************
		strBuffer.append(" sum(c.realpay) realpay,");// 核赔赔付金额 *****************
		strBuffer.append(" max(d.Accidentdate) AccDate,");// 出险日期
															// AccStartDate//出险开始日期
															// *************
		strBuffer.append(" max(c.getdutykind) getdutykind ");// 付责任类型
																// 100意外医疗101意外伤残102意外死亡103意外高残104意外大病105意外特种疾病106意外失业失能109意外豁免200疾病医疗201疾病伤残
		// strBuffer.append(" max(confdate)   confdate "); //理赔核销日期
		// ****************
		strBuffer.append(" from  llregister d, LLClaim e,LLClaimDetail c ");
		strBuffer.append(" where d.Rgtno=e.Rgtno and c.caseno = e.caseno ");
		/*
		 * 如果提数是到给付责任增加下面的查询条件, LLClaimDetail.getdutykind类型： X00--医疗 X01--残疾金
		 * X02--身故金 X03--全残 X04--重大疾病 X05--女性特种疾病（描述女性妊娠期疾病保险金和婴儿身故保险金等）
		 * X06--失业和失能 X07--生命末期 X09--豁免 X为1，指意外；X为2，指疾病 001--临时保单意外死亡 如：102为意外身故
		 */
		// strBuffer.append(" and c.getdutycode='' and c.getdutykind='' and c.dutycode='' ");
		// strBuffer.append(" and f.othernotype='5' ");
		strBuffer.append(" and c.givetype <> '1'"); // 非拒赔
		strBuffer.append(" and e.clmstate = '50'");
		strBuffer.append(" and e.endcasedate between '"
				+ mRIWFLogSchema.getStartDate() + "' and '"
				+ mRIWFLogSchema.getEndDate() + "' ");
		strBuffer
				.append(" and exists (select 'X' from ljaget f where f.otherno=c.clmno and f.confdate is not null and  f.Othernotype = '5' ) ");
		strBuffer.append(" group by c.caseno, c.polno,d.Accidentdate");
		strBuffer.append(") h");

		strBuffer.append(" where a.conttype='1' ");
		// String
		// tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";

		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo || ','||g.dutycode||','||h.clmno||', '||'"
						+ mAccumulateDefNo + "'||','||'CL')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo || ','||g.dutycode||','||h.clmno||', '||'"
						+ mAccumulateDefNo + "'||','||'CL')) ");
		// 临分
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where  state ='03' and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.serialno) and b.proposalno=a.polno) ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateRDCode r where a.riskcode = r.associatedcode  and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer.append(" and a.polno=h.polno ");
		strBuffer.append(" and g.polno=a.polno");
		// strBuffer.append(" and '"+mAccumulateDefNo+"' in ('L01010040010','L01010070010')");
		strBuffer.append(" and a.Currency='01'");
		strBuffer.append(") ");
		System.out.println("共保理赔临分提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	// 理赔回退临分未领取
	private String getDistillSQL3() {
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
				.append(" Retention,Faceamount,Currency,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,PeakLine,GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,ChangeRate,AccCurrency,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyDouble1,StandbyDouble2,StandbyDouble3,StandbyDate1,StandbyDate2,ManageCom,MakeDate,MakeTime,PreChRiskAmnt, ");
		strBuffer
				.append(" (ChangeRate*(RISK_AMNT2(InsuredYear,Amnt,DutyCode,PolNo,Currency,Accumulatedefno))) as ChRiskAmnt, ");
		strBuffer
				.append(" PreChangeRate,MainPolNo,RIPreceptType,BFFlag, EdorPrem,ChEdorPrem,ChPrem,Bonus1,Bonus2,Bonus3,ExtRate1,ExtRate2,ExtRate3,ExtPrem1,ExtPrem2,ExtPrem3,StandbyString4,StandbyString5,StandbyString6,StandbyString7,StandbyString8,StandbyString9,StandbyDouble4,StandbyDouble5,StandbyDouble6,StandbyDouble7,StandbyDouble8,StandbyDouble9,StandbyDate3,StandbyDate4,StandbyDate5 ");
		strBuffer.append(" from (select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '04' as EventType, ");
		strBuffer.append(" '02' as RecordType, ");
		strBuffer.append(" '01' as DataFlag, ");// 01:个人险种 02：个人险种责任
		strBuffer.append(" a.PolNo || ','||h.clmno||','||h.Confdate||','||'"
				+ mAccumulateDefNo + "'||','||'CLHT' as UnionKey, ");
		strBuffer.append(" a.GrpContNo as GrpContNo, ");
		strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
		strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
		strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
		strBuffer.append(" a.ContNo as ContNo, ");
		strBuffer.append(" a.PolNo as PolNo, ");
		strBuffer.append(" a.ProposalNo as ProposalNo, ");
		strBuffer.append(" '' as ContPlanCode, ");
		strBuffer.append(" g.Planlevel as PlanType, ");
		strBuffer.append(" '' as RiskPeriod, ");
		strBuffer.append(" '' as RiskType, ");
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" g.dutycode as DutyCode, ");
		strBuffer.append(" a.Years as Years, ");
		// 保单年度
		strBuffer
				.append(" ceil(months_between(h.Confdate,a.cvalidate-1)/12) as InsuredYear, ");
		strBuffer.append(" a.SaleChnl as SaleChnl, ");
		strBuffer.append(" a.CValiDate as CValiDate, ");
		strBuffer.append(" a.EndDate as EndDate, ");
		strBuffer.append(" a.InsuredNo as InsuredNo, ");
		strBuffer
				.append(" nvl((select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno),'NONAME')  as InsuredName, ");
		strBuffer.append(" decode(a.InsuredSex,'0','0','1') as InsuredSex, ");
		strBuffer.append(" a.InsuredBirthday as InsuredBirthday, ");
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
				.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as AdditionalRate, ");
		strBuffer
				.append(" (select max(AddFeeDirect) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtType, ");
		strBuffer
				.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is not null) as ExtPrem, ");
		strBuffer.append(" '' as BegAccountValue, ");
		strBuffer.append(" '' as EndUnit, ");
		strBuffer.append(" '' as EndPrice, ");
		strBuffer.append(" '' as EndAccountValue, ");
		strBuffer.append(" a.nonparflag as FeeType, ");
		strBuffer.append(" '' as FeeMoney, ");
		strBuffer.append(" '' as NonCashFlag, ");
		// 追朔出险时点的保费
		// strBuffer.append(" nvl((select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno), a.StandPrem ) as StandPrem, ");
		// strBuffer.append(" nvl((select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.prem) as Prem, ");
		strBuffer.append(" g.standprem as StandPrem, ");
		strBuffer
				.append(" (select Prem from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect is null)  as Prem, ");
		// 风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
		// strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(h.endcasedate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
		// 追朔出险时点的保额
		// strBuffer.append(" nvl((select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.amnt) as Amnt, ");
		strBuffer.append(" g.amnt as Amnt, ");
		strBuffer.append(" '' as Mult, ");
		strBuffer.append(" '' as Reserve, ");
		strBuffer.append(" '' as Retention, ");
		strBuffer.append(" '' as Suminsured, ");
		strBuffer.append(" '' as Faceamount, ");
		strBuffer.append(" a.currency as Currency, ");
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
		strBuffer.append(" 'CLHT' as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer
				.append(" (select x.Exchangerate from RIExchangeRate x where x.Currency = a.Currency and x.Startdate  =(select max(d.Startdate) from RIExchangeRate d where d.Currency = a.Currency)and x.Enddate is null) as ChangeRate, ");
		strBuffer.append(" '13' as AccCurrency, ");
		strBuffer.append(" 0 as AccAmnt, ");
		strBuffer.append(" 0 as PreStandPrem, ");
		strBuffer.append(" 0 as PrePrem, ");
		strBuffer.append(" 0 as PreRiskAmnt, ");
		strBuffer.append(" 0 as PreAmnt, ");
		strBuffer.append(" h.clmno as ClmNo, ");
		strBuffer.append(" '' as ClmFeeOperationType, ");
		strBuffer.append(" '' as ClmFeeFinaType, ");
		strBuffer.append(" h.getpay as StandGetMoney, ");
		strBuffer.append(" g.getrate as GetRate, ");
		strBuffer.append(" h.getpay as ClmGetMoney, ");
		strBuffer.append(" h.accdate as AccDate, ");
		strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
		strBuffer
				.append(" nvl((select RIContNo from RITempContLink where proposalno=a.polno and dutycode=g.dutycode),'000000') as RIContNo, ");
		strBuffer
				.append(" nvl((select RIPreceptNo from RITempContLink where proposalno=a.polno and dutycode=g.dutycode),'000000') as RIPreceptNo, ");
		strBuffer
				.append(" nvl((select State from ridutystate where proposalno=a.polno and dutycode=g.dutycode),'02') as ReinsreFlag, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer.append(" h.Confdate as GetDate, ");
		strBuffer
				.append(" rigetplancode(g.contno,g.polno,g.dutycode) as StandbyString1, ");
		strBuffer.append(" '' as standbystring2, ");
		strBuffer.append(" '' as StandbyString3, ");
		strBuffer.append(" 0 as StandbyDouble1, ");
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		// 生日
		strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom, ");
		strBuffer.append(" '' as PreChRiskAmnt, ");
		strBuffer.append(" '' as ChRiskAmnt, ");
		strBuffer.append(" '' as PreChangeRate, ");
		strBuffer.append(" a.mainpolno as MainPolNo, ");
		strBuffer.append(" '' as RIPreceptType, ");
		strBuffer.append(" '' as BFFlag, ");
		strBuffer.append(" '' as EdorPrem,");
		strBuffer.append(" '' as ChEdorPrem,");
		strBuffer.append(" '' as ChPrem,");
		strBuffer.append(" '' as Bonus1, ");
		strBuffer.append(" '' as Bonus2,");
		strBuffer.append(" '' as Bonus3, ");
		/*strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate1, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate2, ");
		strBuffer.append(" (select sum(SuppRiskScore) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtRate3, ");*/
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '01') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '01' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate1,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '02') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '02' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate2,");
		strBuffer.append("(select case when (a.polno in (select polno from lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "' and suppriskscore>0)) then (case when (a.polno in (select polno from RI_LOADING_TABLE)) then (select max(m.SuppRiskScore) FROM RI_LOADING_TABLE m where m.polno = A.polno and m.loadingtype = '03') else (select sum(SuppRiskScore) FROM lcprem where polno= a.polno and dutycode= g.dutycode AND AddFeeDirect = '03' and PayEndDate > '"+ mRIWFLogSchema.getEndDate()+ "') end) else 0 end from dual) as ExtRate3,");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='01' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem1, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='02' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem2, ");
		strBuffer.append(" (select sum(Prem) from LCPrem where polno=a.polno and DutyCode=g.DutyCode and AddFeeDirect='03' and PayEndDate>'"+mRIWFLogSchema.getEndDate()+"') as ExtPrem3, ");
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
		strBuffer.append(" from lcduty g,lcpol a,( ");
		strBuffer.append(" select f.Polno,c.Actugetno,c.Confdate,a.Dutycode,");// 分案号
																				// *****************
		strBuffer.append(" max(e.Accidentdate) accdate,");// 出险日期
		strBuffer.append(" sum(a.Pay) getpay,");// 财务pay *****************
		// strBuffer.append(" max(confdate)   confdate "); //理赔核销日期
		// ****************
		strBuffer.append(" f.Caseno clmno ");
		strBuffer
				.append(" from  LJAGetClaim a ,LJAGet c, llregister e, LLClaimDetail f ");
		strBuffer
				.append(" where f.Rgtno=e.Rgtno  and c.Otherno = f.Caseno and a.Actugetno=c.Actugetno and a.Confdate is  null ");
		// strBuffer.append(" and c.EnterAccDate between '"+mRIWFLogSchema.getStartDate()+"' and '"+mRIWFLogSchema.getEndDate()+"' ");
		strBuffer
				.append(" and  exists (select 'X' from Llcaseback a  where a.Clmno = c.Otherno and c.Othernotype = '5' and (c.Dblconfstate != '9' or c.Dblconfstate is null) and a.Backdate between '"
						+ mRIWFLogSchema.getStartDate()
						+ "' and '"
						+ mRIWFLogSchema.getEndDate() + "' )");
		strBuffer
				.append(" group by  f.Caseno, f.Polno,c.Actugetno ,c.Confdate,a.Dutycode");
		strBuffer.append(") h");

		strBuffer.append(" where a.conttype='1' ");
		strBuffer
				.append(" and exists(select 'X' from RIAccumulateRDCode r where a.riskcode = r.associatedcode  and r.accumulatedefno='"
						+ mAccumulateDefNo + "') ");
		// String
		// tsql="select distinct trim(a.associatedcode) from riaccumulaterdcode a where trim(a.accumulatedefno)='"+mAccumulateDefNo+"' ";
		// strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
		strBuffer.append(" and h.Polno = g.Polno ");
		strBuffer.append(" and a.polno = h.polno");
		strBuffer.append(" and a.Currency='01'");
		strBuffer.append(" and h.dutycode=g.Dutycode");
		// 临分
		strBuffer
				.append(" and exists (select 'X' from ridutystate b where  state ='03' and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.serialno) and b.proposalno=a.polno) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo || ','||h.clmno||','||h.Confdate||','||'"
						+ mAccumulateDefNo + "'||','||'CLHT')) ");
		strBuffer
				.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo || ','||h.clmno||','||h.Confdate||','||'"
						+ mAccumulateDefNo + "'||','||'CLHT')) ");
		// strBuffer.append(" and '"+mAccumulateDefNo+"' in ('L01010040010','L01010070010')");
		strBuffer.append(") ");
		System.out.println("共保理赔回退临分提数sql: " + strBuffer.toString());
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
		mRIWFLogSchema.setStartDate("2011-11-01");
		mRIWFLogSchema.setEndDate("2011-11-20");
		mRIWFLogSchema.setTaskCode("L01010040010");
		cInputData.add(mRIWFLogSchema);
		RICoDistillCL tRIDistillCL = new RICoDistillCL();
		tRIDistillCL.submitData(cInputData, "01");
	}
}


