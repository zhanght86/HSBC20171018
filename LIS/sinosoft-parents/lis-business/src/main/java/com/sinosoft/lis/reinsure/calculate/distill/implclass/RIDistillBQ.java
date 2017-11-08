

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
 * Description:再保保全通用可参考提数程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author LIJIAN
 * @version 1.0
 */
public class RIDistillBQ implements RIDistill {
	String mEventType;
	RIWFLogSchema mRIWFLogSchema;
	String mAccumulateDefNo;
	private MMap mMap;
	private VData mInputData;
	private PubSubmit mPubSubmit = new PubSubmit();
	private String tSql;

	public RIDistillBQ() {
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

			// 临时分保提数
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
			buildError("dealData", " 提取保全业务数据失败 " + ex.getMessage());
			return false;
		}
		return true;
	}

	private String getDistillSQL() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
		// 现价
		strBuffer
				.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
		strBuffer
				.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,StandbyDouble1,");
		// 年缴化保费
		strBuffer.append("APE(Prem ,PayIntv) ,");
		// 准备金
		strBuffer
				.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
		strBuffer
				.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");

		strBuffer.append(" select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '' as RecordType, ");
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
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" '000000' as DutyCode, ");
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
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.OccupationType as OccupationType, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		// 保全变更后数据：如果本次保全后有做过修改lcpol操作的保全项目，则后一次保全项目的lppol表为这次保全变更的业务数据
		// 如果本次保全后没有做过修改lcpol操作的保全项目，则lcpol表的数据为变更后的业务数据
		// 这个判断和取值调用 getPreInfo(contno,polno,edorno,edorvalidate,'要取得的字段名')
		// 函数来完成,
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 , getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'StandPrem')) as StandPrem, ");
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 , getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'Prem') ) as Prem, ");
		// 风险保额
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 , Risk_Amnt(a.payyears,ceil(months_between(b.edorvalidate,a.cvalidate-1)/12),getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'Amnt'),a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv)) as RiskAmnt, ");// 风险保额
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 , getpreinfo(a.contno,a.polno,b.edorno,b.edorvalidate,'Amnt') ) as Amnt,  ");
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
		strBuffer.append(" b.edorno as EdorNo, ");
		// strBuffer.append(" case when b.edortype='CT' and b.hesitateflag='Y' then 'CTY' else b.edortype end as FeeOperationType, ");
		strBuffer.append(" b.edortype as FeeOperationType, ");

		strBuffer.append(" '' as FeeFinaType, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer.append(" 0 as AccAmnt, ");
		// 但保全类型为复效时，变更前保额、保费、风险保额都为0；当保全类型为保单贷款时，由于保单但款不操作lppol所有保额、保费、风险保额都从c表中获取
		strBuffer
				.append(" decode( b.edortype,'RE', 0 ,'LN' ,a.standprem , c.standprem ) as PreStandPrem, ");
		strBuffer
				.append(" decode( b.edortype,'RE', 0 ,'LN' ,a.prem , c.prem ) as PrePrem, ");
		// 风险保额
		strBuffer
				.append(" decode( b.edortype,'RE', 0 ,'LN' ,Risk_Amnt(a.payyears,ceil(months_between(b.edorvalidate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv), Risk_Amnt(c.payyears,ceil(months_between(b.edorvalidate,c.cvalidate-1)/12),c.amnt,c.InsuredAppAge,c.years,c.InsuredSex,c.prem,c.riskcode,c.payendyear,c.payendyearflag,c.insuyear,c.insuyearflag,c.payintv)) as PreRiskAmnt, ");
		strBuffer
				.append(" decode( b.edortype,'RE', 0 ,'LN' ,a.amnt,c.amnt) as PreAmnt, ");
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
		// 业务日期
		strBuffer.append(" b.edorvalidate as GetDate, ");
		strBuffer.append(" null as StandbyString1, ");
		// 如果没有健康加费，提数为空的sql
		// strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		// 如果没有健康加费，提数为A=0
		strBuffer
				.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		// 计划别
		strBuffer.append(" '' as StandbyString3, ");// a.PLANTYPE
		// 长短险标志 1:短期险 2：长期险
		strBuffer
				.append(" (select decode( riskperiod,'M' , '1' ,'2') from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
		strBuffer.append(" null as StandbyString5, ");
		strBuffer.append(" 0 as StandbyDouble1, ");
		// 年缴化保费
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		// 生日
		strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom ");

		strBuffer.append(" from lcpol a ,lpedoritem b,lppol c ");

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
				.append(" and exists(select 'X' from riaccumulaterdcode r where a.riskcode = r.associatedcode and r.accumulatedefno='");
		strBuffer.append(mAccumulateDefNo);
		strBuffer.append("')");
		// edortype CT:退保 EA:解约 RE:复效 AA:加保 PT:减保
		// CM:客户资料变更(客户资料变更提数令配置类RIDistillBQCM.java) IO:被保人职业类别变更 FT:缴费年期变更
		// LN:保单贷款
		strBuffer
				.append(" and c.polno=a.polno and c.edortype=b.edortype and c.edorno=b.edorno and b.edortype in('CT','EA','PT','AA','RE','IO','FT','LN') and b.edorstate='0' ");
		strBuffer.append(" and b.edorvalidate between '"
				+ mRIWFLogSchema.getStartDate() + "' and '"
				+ mRIWFLogSchema.getEndDate() + "'");

		strBuffer.append(")order by GetDate,polno");
		System.out.println("保全提数sql: " + strBuffer.toString());
		return strBuffer.toString();
	}

	/**
	 * 保全临分提数
	 * 
	 * @return
	 */
	private String getDistillSQL1() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer
				.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
		// 现价
		strBuffer
				.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
		strBuffer
				.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,StandbyDouble1,");
		// 年缴化保费
		strBuffer.append("APE(Prem ,PayIntv) ,");
		// 准备金
		strBuffer
				.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
		strBuffer
				.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");

		strBuffer.append(" select ");
		strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
		strBuffer.append(" getSerialNo as EventNo, ");
		strBuffer.append(" '03' as EventType, ");
		strBuffer.append(" '' as RecordType, ");
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
		strBuffer.append(" a.RiskCode as RiskCode, ");
		strBuffer.append(" '000000' as DutyCode, ");
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
		strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
		strBuffer
				.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
		strBuffer
				.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
		strBuffer.append(" a.OccupationType as OccupationType, ");
		strBuffer
				.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 ,a.StandPrem ) as StandPrem, ");
		strBuffer.append(" decode( b.edortype,'CT' , 0 ,a.Prem ) as Prem, ");
		// 风险保额
		strBuffer
				.append(" decode( b.edortype,'CT' , 0 ,Risk_Amnt(a.payyears,ceil(months_between(b.edorvalidate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv)) as RiskAmnt, ");
		strBuffer.append(" decode( b.edortype,'CT' , 0 ,a.amnt ) as Amnt,  ");
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
		strBuffer.append(" b.edorno as EdorNo, ");
		// strBuffer.append(" case when b.edortype='CT' and b.hesitateflag='Y' then 'CTY' else b.edortype end as FeeOperationType, ");

		strBuffer.append("  b.edortype as FeeOperationType, ");
		strBuffer.append(" '' as FeeFinaType, ");
		// strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
		strBuffer.append(" 0 as AccAmnt, ");
		// 但保全类型为复效时，变更前保额、保费、风险保额都为0；当保全类型为保单贷款时，由于保单但款不操作lppol所有保额、保费、风险保额都从c表中获取
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 ,'LN' , a.standprem , c.standprem ) as PreStandPrem, ");
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 ,'LN' , a.prem , c.prem ) as PrePrem, ");
		// preRiskAmnt
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 ,'LN' ,Risk_Amnt(a.payyears,ceil(months_between(b.edorvalidate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) , Risk_Amnt(c.payyears,ceil(months_between(b.edorvalidate,c.cvalidate-1)/12),c.amnt,c.InsuredAppAge,c.years,c.InsuredSex,c.prem,c.riskcode,c.payendyear,c.payendyearflag,c.insuyear,c.insuyearflag,c.payintv)) as PreRiskAmnt, ");
		strBuffer
				.append(" decode( b.edortype,'RE' , 0 ,'LN' ,a.amnt , c.amnt ) as PreAmnt, ");
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
				.append(" (select RIPreceptNo from RITempContLink where proposalno=a.proposalno ) as RIPreceptNo, ");
		strBuffer.append(" '01' as NodeState, ");
		strBuffer
				.append(" (select State from ridutystate where proposalno=a.proposalno) as ReinsreFlag, ");
		strBuffer.append(" b.edorvalidate as GetDate, ");
		strBuffer.append(" null as StandbyString1, ");
		// 如果没有健康加费，提数为空的sql
		// strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
		// 如果没有健康加费，提数为A=0
		strBuffer
				.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where  l.suppriskscore <>0 and l.polno=a.polno ) as StandbyString2, ");
		// 计划别
		strBuffer.append(" '' as StandbyString3, ");// a.PLANTYPE
		// 长短险标志 1:短期险 2：长期险
		strBuffer
				.append(" (select decode(riskperiod,'M' , '1' , '2') from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
		strBuffer.append(" null as StandbyString5, ");
		strBuffer.append(" 0 as StandbyDouble1, ");
		// 年缴化保费
		strBuffer.append(" 0 as StandbyDouble2, ");
		strBuffer.append(" 0 as StandbyDouble3, ");
		// 生日
		strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
		strBuffer.append(" null as StandbyDate2, ");
		strBuffer.append(" date '" + PubFun.getCurrentDate()
				+ "' as MakeDate, ");
		strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
		strBuffer.append(" a.managecom as managecom ");

		strBuffer.append(" from lcpol a ,lpedoritem b,lppol c ");

		strBuffer.append(" where a.conttype='1' and a.contno=b.contno   ");

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
		// edortype CT:退保 EA:解约 RE:复效 AA:加保 PT:减保
		// CM:客户资料变更(客户资料变更提数令配置类RIDistillBQCM.java) IO:被保人职业类别变更 FT:缴费年期变更
		// LN:保单贷款
		strBuffer
				.append(" and c.polno=a.polno and c.edortype=b.edortype and c.edorno=b.edorno and b.edortype in('CT','EA','PT','AA','RE','IO','FT','LN') and b.edorstate='0'");
		strBuffer.append(" and b.edorvalidate between '"
				+ mRIWFLogSchema.getStartDate() + "' and '"
				+ mRIWFLogSchema.getEndDate() + "'");
		strBuffer.append(")order by GetDate,polno");

		System.out.println("保全临分提数sql: " + strBuffer.toString());
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
		mRIWFLogSchema.setStartDate("2009-3-1");
		mRIWFLogSchema.setEndDate("2009-3-9");
		mRIWFLogSchema.setTaskCode("L000000001");
		cInputData.add(mRIWFLogSchema);
		RIDistillBQ tRIDistillBQ = new RIDistillBQ();
		tRIDistillBQ.test(cInputData, "01");
	}
}
