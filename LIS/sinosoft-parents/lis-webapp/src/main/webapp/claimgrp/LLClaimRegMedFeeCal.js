var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�����·�����Ϣ��ѯ������
    �� �� �ˣ�����
    �޸����ڣ�2005.05.13
   =========================================================================
**/
function showMedFeeCalGrid()
{

    var tSql;
    var arr;

    
    var tClaimNo = fm.claimNo.value;         //�ⰸ��
       
    //��ѯLLClaimDetail
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


