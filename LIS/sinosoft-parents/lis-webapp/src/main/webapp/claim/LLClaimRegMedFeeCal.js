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
    /*tSql = " select a.PolNo,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyType)),"
       +" a.RiskCode, "
       +" (select d.GetDutyName from LMDutyGetRela d where trim(d.GetDutyCode) = trim(a.GetDutyCode)),"
       +" (select e.codeName from ldcode e where e.codetype='llfeetype' and trim(e.Code) = trim(a.DutyFeeType)),"              
       +" a.DutyFeeName,a.DutyFeeStaNo,a.HosID,a.HosName,a.StartDate,"
       +" a.EndDate,(case substr(dutyfeecode,1,2) when 'CR' then DayCount else to_number(EndDate-StartDate) end),"
       +" a.OriSum,a.adjSum,a.OutDutyAmnt,(select realpay from llclaimdetail b where a.clmno=b.clmno and a.getdutycode=b.getdutycode and a.polno=b.polno and a.dutycode=b.dutycode) "

       +" from LLClaimDutyFee a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
       mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegMedFeeCalInputSql");
		mySql.setSqlId("LLClaimRegMedFeeCalSql1");
		mySql.addSubPara(tClaimNo ); 

    //alert(tSql);
    arr = easyExecSql( mySql.getString() );
    if (arr) 
    {
        displayMultiline(arr,MedFeeCalGrid);
    } 
    
        
}


