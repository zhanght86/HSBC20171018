

package com.sinosoft.lis.reinsure.test;

import com.sinosoft.lis.db.RIItemCalDB;
import com.sinosoft.lis.db.RIPolRecordDB;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.utility.ExeSQL;

import java.util.*;
import java.sql.*;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
      

        try{
        	String sql = "select count(*) from (select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,StandbyString5,StandbyDouble1,APE(Prem ,PayIntv) ,LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from(  select  '0000000121' as BatchNo,  getSerialNo as EventNo,  '01' as EventType,  '' as RecordType,  '01' as DataFlag,  trim(a.PolNo)||',1' as UnionKey,  a.GrpContNo as GrpContNo,  '00000000000000000000' as ProposalGrpContNo,  a.GrpPolNo as GrpPolNo,  '00000000000000000000' as GrpProposalNo,  a.ContNo as ContNo,  a.PolNo as PolNo,  a.ProposalNo as ProposalNo,  '' as ContPlanCode,  a.RiskCode as RiskCode,  '000000' as DutyCode,  a.Years as Years,  ceil(decode(months_between(greatest(a.signdate,a.cvalidate),a.cvalidate),0,1,months_between(greatest(a.signdate,a.cvalidate),a.cvalidate))/12) as InsuredYear,  a.SaleChnl as SaleChnl,  a.CValiDate as CValiDate,  a.EndDate as EndDate,  a.InsuredNo as InsuredNo,  a.InsuredName as InsuredName,  a.InsuredSex as InsuredSex,  a.InsuredAppAge as InsuredAge,  (select b.IDType from LCInsured b where trim(b.insuredno)=trim(a.insured";
        	sql =sql+  "no) and trim(b.contno)=trim(a.contno)) as IDType,  (select b.IDNo from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as IDNo,  a.OccupationType as OccupationType,  (select b.OccupationCode from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as OccupationCode ,  a.StandPrem as StandPrem,  a.Prem as Prem,  Risk_Amnt(a.payyears,ceil(decode(months_between(greatest(a.signdate,a.cvalidate),a.cvalidate),0,1,months_between(greatest(a.signdate,a.cvalidate),a.cvalidate))/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt,  a.Amnt as Amnt,   '' as Mult,  a.PayIntv as PayIntv,  a.PayYears as PayYears,  a.PayEndYearFlag as PayEndYearFlag,  a.PayEndYear as PayEndYear,  '' as GetYearFlag,  '' as GetYear,  a.InsuYearFlag as InsuYearFlag,  a.InsuYear as InsuYear,  '' as AcciYearFlag,  '' as AcciYear,  '' as AcciEndDate,  '' as GetStartDate,  '' as GetStartType,  '' as PeakLine,  '' as GetLimit,  '' as GetIntv,  '' as PayNo,  '' as PayCount,  '' as PayMoney,  '' as LastPayToDate,  '' as CurPayToDate,  '' as EdorNo,  '' as FeeOperationType,  '' as FeeFinaType,  0 as AccAmnt,  0 as PreStandPrem,  0 as PrePrem,  0 as PreRiskAmnt,  0 as PreAmnt,  '' as ClmNo,  '' as ClmFeeOperationType,  '' as ClmFeeFinaType,  0 as StandGetMoney,  '' as GetRate,  0 as ClmGetMoney,  '' as AccDate,  'L000000001' as AccumulateDefNO,  '' as RIContNo,  (select RIPreceptNo from RITempContLink where proposalno=a.proposalno) as RIPreceptNo,  '01' as NodeState,  (select max(State) from ridutystate where proposalno=a.proposalno) as ReinsreFlag,  greatest(a.signdate,a.cvalidate) as GetDate,  '' as StandbyString1,  (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2,  a.PLANTYPE as StandbyString3,  (select case when riskperiod='M' then";
        	sql =sql+  " '1' else '2' end from lmriskapp where riskcode=a.riskcode) as StandbyString4,  null as StandbyString5,  0 as StandbyDouble1,  0 as StandbyDouble2,  0 as StandbyDouble3,  (select max(birthday) from LCInsured where insuredno = a.insuredno) as StandbyDate1,  null as StandbyDate2,  '2009-01-11' as MakeDate,  '00:56:30' as MakeTime,  a.managecom as managecom  from lcpol a   where a.conttype='1' and appflag ='1' and exists(select 'X' from lccontstate where polno=a.polno and StateType = 'Available' and State = '0' and enddate is null)  and trim(a.riskcode) in (select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='L000000001')  and (select greatest(signdate,cvalidate) from lcpol where polno=a.polno) between '2007-01-01' and '2008-12-01' and not exists (Select * From RIPolRecord m where m.unionkey=(trim(a.PolNo)||',1'))  and not exists (Select * From RIPolRecordBake n where n.unionkey=(trim(a.PolNo)||',1'))  and exists (select * from ridutystate b where b.proposalno=a.proposalno and state in ('00','03') and exists (select * from RIGrpState c where trim(c.serialno)=trim(b.standbystring1) and c.state='04') )  and rownum <=5000 order by GetDate,polno))";
        	ExeSQL tExeSQL = new ExeSQL();
            int countNum = Integer.parseInt(tExeSQL.getOneValue(sql));
            System.out.println(countNum);

        }catch(Exception ex){
            System.out.println("业务数据提取时出错，查询业务记录数时报错。" + ex);
        }
	}

}
