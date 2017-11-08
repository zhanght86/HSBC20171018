
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
/**
 * FUNCTION:initList()
 * DESC :初始化__保项计算
 */

function initList()
{
    var tSql;
    var arr;
   try
  {

    //查询LLClaimPolicy,查询保单理赔类型层面的信息
   /* tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.clmno = '"+fm.CaseNo.value+"'"
       +" and a.PolNo = b.PolNo"      */ 
       //+" and a.PolNo = b.PolNo"
       //+" and a.clmno = '"+fm.CaseNo.value+"'"
       mySql = new SqlClass();
mySql.setResourceName("claimgrp.QuerydutySetInfoListSql");
mySql.setSqlId("QuerydutySetInfoListSql1");
mySql.addSubPara(fm.CaseNo.value ); 
       turnPage3.queryModal(mySql.getString(),PolDutyKindGrid);
//    arr = easyExecSql( tSql );
//    if (arr)
//    {
//        displayMultiline(arr,PolDutyKindGrid);
//    }

    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
  /*  tSql = " select a.CustomerNo,(select name from ldperson where customerno=a.CustomerNo),a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetRela c where trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //交至日期 + 宽限期--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and trim(e.code)=trim(a.GiveType)),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and trim(f.code)=trim(a.GiveReason)),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//预付金额
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and trim(g.code)=trim(a.AdjReason)),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
       +" a.DutyCode "
       +" from LLClaimDetail a,LCGet b ,LLcase c  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"
       +" and a.GetDutyCode = b.GetDutyCode"
       +" and a.ClmNo = '"+fm.CaseNo.value+"'"
       +" and a.ClmNo = c.caseno"
       +" and c.CustomerNo = a.CustomerNo"
       +" order by  lpad(c.SecondUWFlag,3,'0') "    */
        mySql = new SqlClass();
mySql.setResourceName("claimgrp.QuerydutySetInfoListSql");
mySql.setSqlId("QuerydutySetInfoListSql2");
mySql.addSubPara(fm.CaseNo.value );    
//    arr = easyExecSql( tSql );
       turnPage2.queryModal(mySql.getString(),PolDutyCodeGrid);

//    if (arr)
//    {
//        displayMultiline(arr,PolDutyCodeGrid);
//    }
  }
  catch(ex)
  {
   alert("CaseAffixList.js-->initList函数中发生异常!"+ ex.message);
  }
}

function returnParent()
{
   top.close();
}