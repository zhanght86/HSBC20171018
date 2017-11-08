var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

/**=========================================================================
    修改状态：开始
    修改原因：以下费用信息查询功能区
    修 改 人：续涛
    修改日期：2005.05.13
   =========================================================================
**/
function showMedFeeCalGrid()
{

    var tSql;
    var arr;

    
    var tClaimNo = fm.claimNo.value;         //赔案号
       
    //查询LLClaimDetail
   /* tSql = " select a.PolNo,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyType)),"
       +" a.RiskCode, "
       +" (select d.GetDutyName from LMDutyGetRela d where trim(d.GetDutyCode) = trim(a.GetDutyCode)),"
       +" (select e.codeName from ldcode e where e.codetype='llfeetype' and trim(e.Code) = trim(a.DutyFeeType)),"              
       +" a.DutyFeeName,a.DutyFeeStaNo,a.HosID,a.HosName,a.StartDate,"
       +" a.EndDate,a.DayCount,"
       +" a.OriSum,a.adjSum,a.OutDutyAmnt,a.CalSum "

       +" from LLClaimDutyFee a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimRegMedFeeCalInputSql");
	mySql.setSqlId("LLClaimRegMedFeeCalSql1");
	mySql.addSubPara(tClaimNo); 
    //alert(tSql);
    arr = easyExecSql( mySql.getString() );
    if (arr) 
    {
        displayMultiline(arr,MedFeeCalGrid);
    } 
    
        
}


