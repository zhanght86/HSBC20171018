var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������ҽԺ��Ϣ��ѯ����������������
    �� �� �ˣ�����
    �޸����ڣ�2005.05.13
   =========================================================================
**/

//ѡ���˵�����
function choiseType()
{
    if (fm.MainFeeType.value == 'A')
  {
        divMedFee1.style.display="";
        queryGrid1();
    }
    else if (fm.MainFeeType.value == 'B')
    {
        divMedFee2.style.display="";
        queryGrid2();
    }
    else if (fm.MainFeeType.value == 'C')
    {
        divMedFee3.style.display="";
        queryGrid3();
    }
    else if (fm.MainFeeType.value == 'D' || fm.MainFeeType.value == 'E' || fm.MainFeeType.value == 'F')
    {
        divMedFee4.style.display="";
        queryGrid4();
    }
    else if (fm.MainFeeType.value == 'L')
    {
        divMedFee5.style.display="";
        queryGrid5();
    }
    else if (fm.MainFeeType.value == 'L')
    {
        divMedFee5.style.display="";
        queryGrid5();
    }
    else if (fm.MainFeeType.value == 'O')
    {
        divMedFee6.style.display="";
        queryGrid6();
    }
}

//��ʾ�����˵�
function showAll()
{
    divMedFee1.style.display="";
    queryGrid1();
    divMedFee2.style.display="";
    queryGrid2();
    divMedFee3.style.display="";
    queryGrid3();
    divMedFee4.style.display="";
    queryGrid4();
    divMedFee5.style.display="";
    queryGrid5();
    divMedFee6.style.display="";
    queryGrid6();
}

//���������˵�
function showNone()
{
    fm.MainFeeType.value = "";
    fm.MainFeeName.value = "";
    divMedFee1.style.display="none";
    divMedFee2.style.display="none";
    divMedFee3.style.display="none";
    divMedFee4.style.display="none";
    divMedFee5.style.display="none";
    divMedFee6.style.display="none";
}

//������������
function setOperType()
{
    if (fm.OperationType.value == 'D')
    {
        fm.llOperType.value = 'lloperationtype';
    }
    if (fm.OperationType.value == 'E')
    {
        fm.llOperType.value = 'lldiseasetype';
    }
    if (fm.OperationType.value == 'F')
    {
        fm.llOperType.value = 'llspegivetype';
    }
}

//����Ҫ������
function setFactorType()
{
    if (fm.FactorType.value == 'succor')
    {
        fm.llFactorType.value = 'llfactypesuccor';
    }
    else if (fm.FactorType.value == 'med')
    {
        fm.llFactorType.value = 'llfactypemed';
    }
}

//������Ϣ��ѯ
function queryGrid1()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16--------------------------17--18--19--20
    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.Fee, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,b.DealFlag,(case b.DealFlag when '0' then '��' end),b.AdjSum,b.RefuseAmnt,b.AdjReason,b.SecurityAmnt,AdjRemark"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.MainFeeNo=b.MainFeeNo and a.CustomerNo=b.CustomerNo "
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'A'"
               + " and a.CustomerNo = '"+tcustNo+"'"
               + " order by a.MainFeeNo";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql1");
	mySql.addSubPara(tclaimNo);  
	mySql.addSubPara(tcustNo);           
    //��ʾ��������
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeClinicInpGrid);
    }
}

//סԺ��Ϣ��ѯ
function queryGrid2()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16
  /*  var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.Fee, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,b.DealFlag,(case b.DealFlag when '0' then '��' end),b.AdjSum,b.RefuseAmnt,b.AdjReason,b.SecurityAmnt,(select case when exists (select 'X' from dual where b.HospLineAmnt is not null) then b.HospLineAmnt else 0 end from dual),AdjRemark"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.MainFeeNo=b.MainFeeNo and a.CustomerNo=b.CustomerNo "
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'B'"
               + " and a.CustomerNo = '"+tcustNo+"'"
               + " order by a.MainFeeNo";*/
    //��ʾ��������
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql2");
	mySql.addSubPara(tclaimNo);  
	mySql.addSubPara(tcustNo);   
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeInHosInpGrid);
    }
}

//�˲���Ϣ��ѯ
function queryGrid3()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
//-----------------------1---------2---------3-------4-----5----------6--------------7------------8-------9----10-------11---------12-----13
  /*  var strSql = " select defotype,defograde,DefoName,defocode,'',deformityrate,appdeformityrate,realrate,clmno,caseno,serialno,customerno,JudgeOrganName,JudgeDate"
               + " from LLCaseInfo where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo = '"+tcustNo+"'"
               + " order by serialno";*/
    //��ʾ��������
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql3");
	mySql.addSubPara(tclaimNo);  
	mySql.addSubPara(tcustNo);   
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeCaseInfoGrid);
    }
}

//������Ϣ��ѯ
function queryGrid4()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
//-------------------------1--------------2-------------3----------4------5-------6------7----8-----9-----10----------11
   /* var strSql = " select operationtype,operationcode,operationname,oplevel,opgrade,opfee,mainop,clmno,caseno,serialno,customerno,UnitName,DiagnoseDate"
               + " from LLOperation where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo = '"+tcustNo+"'"
               + " order by serialno";*/
    //��ʾ��������
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql4");
	mySql.addSubPara(tclaimNo);  
	mySql.addSubPara(tcustNo); 
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeOperGrid);
    }
}

//������Ϣ��ѯ
function queryGrid5()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
    //------------------------1-----------2--------3----------4----------5------6------7----------8
   /* var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,StartDate,EndDate"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and factortype = 'succor'"
               + " and CustomerNo = '"+tcustNo+"'"
               + " order by serialno";*/
    //��ʾ��������
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql5");
	mySql.addSubPara(tclaimNo);  
	mySql.addSubPara(tcustNo); 
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeOtherGrid);
    }
}

//�籣������������ѯ
function queryGrid6()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
    //------------------------1-----------2--------3----------4----------5------6------7----------8
   /* var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,AdjRemark"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and factortype = 'med'"
               + " and CustomerNo = '"+tcustNo+"'"
               + " order by serialno";*/
    //��ʾ��������
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql6");
	mySql.addSubPara(tclaimNo);  
	mySql.addSubPara(tcustNo); 
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeThreeGrid);
    }
}

//��ѯ�˲��⸶����
function queryDeformityRate()
{
    if(fm.DefoCode.value != "" && fm.DefoCode.value !=null)
    {
       /* var strSql = " select t.deforate from LLParaDeformity t where "
                   + " t.defocode='" + fm.DefoCode.value + "'";*/
        //��ʾ��������
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql7");
	mySql.addSubPara(fm.DefoCode.value);   
        var arr = easyExecSql(mySql.getString());
//        alert(arr)
        if (arr)
        {
            fm.AppDeformityRate.value = arr;
            fm.DeformityRate.value = arr;
            fm.RealRate.value = arr;
        }
    }
}

/*�ύ��̨ǰ��ҳ������У��*/
function beforeClinicSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("ClinicHosID").value == null || trim(document.all("ClinicHosID").value) ==''){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.ClinicHosID.focus();
         return false;
    }else if( document.all("ClinicHosName").value == null || trim(document.all("ClinicHosName").value) == '' ){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.ClinicHosName.focus();
         return false;
    }else if( document.all("ClinicHosGrade").value == null || trim(document.all("ClinicHosGrade").value) == '' ){
         alert('[ҽԺ�ȼ�]����Ϊ�գ�');
         fm.ClinicHosGrade.focus();
         return false;
    }else if( document.all("ClinicStartDate").value == null || trim(document.all("ClinicStartDate").value) == '' ){
         alert('[��ʼ����]����Ϊ�գ�');
         fm.ClinicStartDate.focus();
         return false;
    }else if( document.all("ClinicEndDate").value == null || trim(document.all("ClinicEndDate").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.UClinicEndDate.focus();
         return false;
    }else if( document.all("ClinicMedFeeType").value == null || trim(document.all("ClinicMedFeeType").value) == '' ){
         alert('[���ô���]����Ϊ�գ�');
         fm.ClinicMedFeeType.focus();
         return false;
    }else if( document.all("ClinicMedFeeTypeName").value == null || trim(document.all("ClinicMedFeeTypeName").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.ClinicMedFeeTypeName.focus();
         return false;
    }else if( document.all("ClinicFeeSum").value == null || trim(document.all("ClinicFeeSum").value) == '' ){
         alert('[���ý��]����Ϊ�գ�');
         fm.ClinicFeeSum.focus();
         return false;
    }
    return true;

}

/*[��������]�ύ����̨*/
function submitForm()
{
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.submit(); //�ύ
}
//��֤ɾ���ж�
function queryClaimPolicy(){
    var tclaimNo = fm.claimNo.value;
  //  var strSql = " select count(*) from llclaimpolicy where caseno = '"+tclaimNo+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLMedicalFeeInpInputSql");
	mySql.setSqlId("LLMedicalFeeInpSql8");
	mySql.addSubPara(tclaimNo); 
    var arr = easyExecSql(mySql.getString());
    if(arr)
     {
     alert("���Ѿ����е�֤�������������㣡");
     }
}
/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        mOperate = '';
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        if ( fm.currentInput.value=="1")//����
        {
            initMedFeeClinicInpGrid(); 
            queryGrid1();
            //��ձ�
//            fm.ClinicMainFeeNo.value = "";
//            fm.ClinicHosID.value = "";
//            fm.ClinicHosName.value = "";
            fm.ClinicMedFeeType.value = "";
            fm.ClinicMedFeeTypeName.value = "";
            fm.ClinicMedFeeSum.value = "";
            fm.ClinicStartDate.value = "";
            fm.ClinicEndDate.value = "";
            fm.ClinicDayCount1.value = "";
            fm.ClinicDayCount2.value = "";
            fm.ClinicDayCount3.value = "";
        }
        if ( fm.currentInput.value=="2")//ҽԺ
        {
            initMedFeeInHosInpGrid(); 
            queryGrid2();
            //��ձ�
//            fm.HosMainFeeNo.value = "";
//            fm.InHosHosID.value = "";
//            fm.InHosHosName.value = "";
            fm.InHosMedFeeType.value = "";
            fm.InHosMedFeeTypeName.value = "";
            fm.InHosMedFeeSum1.value = "";
            fm.InHosStartDate.value = "";
            fm.InHosEndDate.value = "";
            fm.InHosDayCount1.value = "";
            fm.InHosDayCount2.value = "";
            fm.InHosDayCount3.value = "";
        }
        if ( fm.currentInput.value=="3")//�˲�
        {
            initMedFeeCaseInfoGrid();
            queryGrid3();
            //��ձ�
            fm.DefoType.value = "";
            fm.DefoTypeName.value = "";
            fm.DefoGrade.value = "";
            fm.DefoGradeName.value = "";
            fm.DefoCode.value = "";
            fm.DefoCodeName.value = "";
            fm.DeformityRate.value = "";
            fm.JudgeOrganName.value = "";
            fm.JudgeDate.value = "";
        }
        if ( fm.currentInput.value=="4")//�����ؼ�
        {
            initMedFeeOperGrid();
            queryGrid4();
            //��ձ�
            fm.OperationType.value = "";
            fm.OperationTypeName.value = "";
            fm.OperationCode.value = "";
            fm.OperationName.value = "";
            fm.UnitName.value = "";
            fm.OpFee.value = "";
            fm.DiagnoseDate.value = "";
        }
        if ( fm.currentInput.value=="5")//����
        {
            initMedFeeOtherGrid();
            queryGrid5();
            //��ձ�
            fm.FactorType.value = "";
            fm.FactorTypeName.value = "";
            fm.FactorCode.value = "";
            fm.FactorName.value = "";
            fm.FactorValue.value = "";
            fm.FactorUnitName.value = "";
            fm.FactorStateDate.value = "";
            fm.FactorEndDate.value = "";
        }
        if ( fm.currentInput.value=="6")//�籣����������
        {
            initMedFeeThreeGrid();
            queryGrid6();
            //��ձ�
//            fm.FeeThreeType.value = "";
//            fm.FeeThreeTypeName.value = "";
            fm.FeeThreeCode.value = "";
            fm.FeeThreeName.value = "";
            fm.FeeThreeValue.value = "";
            fm.FeeThreeUnitName.value = "";
//            fm.FeeThreeStateDate.value = "";
//            fm.FeeThreeEndDate.value = "";
        }
if(mOperate == 'DELETE'){
     queryClaimPolicy();//��֤ɾ���ж�
    }
    }
}

/*����[����]��ť��Ӧ����*/
function AddClick1()
{
    //�ǿռ���
    if (fm.ClinicMainFeeNo.value == "" || fm.ClinicMainFeeNo.value == null)
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    if (fm.ClinicMedFeeType.value == "" || fm.ClinicMedFeeType.value == null||fm.ClinicMedFeeTypeName.value==""||fm.ClinicMedFeeTypeName.value==null)

    {
        alert("�������Ͳ���Ϊ�գ�");
        return;
    }
    //���ڼ���
    if (fm.ClinicStartDate.value == '')
    {
        alert("���ڲ���Ϊ��!");
        return;
    }
    if (dateDiff(fm.ClinicStartDate.value,mCurrentDate,'D') < 0 || dateDiff(fm.ClinicStartDate.value,mCurrentDate,'D') < 0)
    {
        alert("���￪ʼ�������ڲ��ܴ��ڵ�ǰ����!");
        return;
    }
    //���ڼ���,��������ڱȽ�
    if (!dayCount(fm.ClinicStartDate.value,fm.ClinicStartDate.value,'C'))
    {
        return;
    }
    var date4 = dateDiff(fm.accDate2.value,fm.ClinicStartDate.value,'D');
    if (date4 < 0)
    {
        if(confirm("��֤��ʼ�������ڳ�������,�Ƿ����!"))
        {
            fm.DealFlag.value = "0";
        }
        else
        {
            return;
        }
    }
    else
    {
        fm.DealFlag.value = "1";
    }
    mOperate="INSERT";
    fm.currentInput.value = "1";
    fm.action = "./LLMedicalFeeInp1Save.jsp";
    submitForm();
}

/*סԺ[����]��ť��Ӧ����*/
function AddClick2()
{
//    alert(dateDiff("2005-01-01","2005-01-31","D"));
    //�ǿռ���
    if (fm.HosMainFeeNo.value == "" || fm.HosMainFeeNo.value == null)
    {
        alert("�˵��Ų���Ϊ�գ�");
        return;
    }
    if (fm.InHosMedFeeType.value == "" || fm.InHosMedFeeType.value == null||fm.InHosMedFeeTypeName.value==""||fm.InHosMedFeeTypeName.value==null)
    {
        alert("�������Ͳ���Ϊ�գ�");
        return;
    }
    //���ڼ���
    if (fm.InHosStartDate.value == '' || fm.InHosEndDate.value == '')
    {
        alert("���ڲ���Ϊ�գ�");
        return;
    }
    if (dateDiff(fm.InHosStartDate.value,mCurrentDate,'D') < 0 || dateDiff(fm.InHosEndDate.value,mCurrentDate,'D') < 0)
    {
        alert("���ڴ���");
        return;
    }
    //���ڼ���
    if (!dayCount(fm.InHosStartDate.value,fm.InHosEndDate.value,'H'))
    {
        return;
    }
    var date4 = dateDiff(fm.accDate2.value,fm.InHosStartDate.value,'D');
    if (date4 < 0)
    {
        if(confirm("��֤��ʼ�������ڳ�������,�Ƿ����?"))
        {
            fm.DealFlag.value = "0";
        }
        else
        {
            return;
        }
    }
    else
    {
        fm.DealFlag.value = "1";
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "2";
    fm.action = "./LLMedicalFeeInp2Save.jsp";
    submitForm();
}

/*�˲�[����]��ť��Ӧ����*/
function AddClick3()
{
    //�ǿռ���
  //  if (fm.DefoType.value == "")
      if (fm.DefoType.value == ""||fm.DefoType.value ==null||fm.DefoTypeName.value ==""||fm.DefoTypeName.value ==null)
    {
        alert("������м����ͣ�");
        return;
    }
//    if (fm.DefoGrade.value == "")
    if (fm.DefoGrade.value == ""||fm.DefoGrade.value==null||fm.DefoGradeName.value==""||fm.DefoGradeName.value==null)

    {
        alert("�������˲м���");
        return;
    }
//    if (fm.DefoCode.value == "")
    if (fm.DefoCode.value == ""||fm.DefoCode.value ==null||fm.DefoCodeName.value ==""||fm.DefoCodeName.value ==null)
    {
        alert("�������˲д��룡");
        return;
    }
    //����ϵͳ����
    if(dateDiff(fm.JudgeDate.value,mCurrentDate,'D') < 0)
    {
        alert("�������ڴ��ڵ�ǰ���ڣ�");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "3";
    fm.action = "./LLMedicalFeeInp3Save.jsp";
    submitForm();
}

/*����[����]��ť��Ӧ����*/
function AddClick4()
{
    //�ǿռ���
    if (fm.OperationType.value == "" ||fm.OperationType.value == null||fm.OperationTypeName.value == ""||fm.OperationTypeName.value == null)
    {
        alert("������������Ϣ��");
        return;
    }
   if (fm.OperationCode.value == "" ||fm.OperationCode.value == null||fm.OperationName.value ==""||fm.OperationName.value ==null)
    {
        alert("�����������Ϣ��");
        return;
    }
    if (fm.UnitName.value == "" || fm.UnitName.value == null)
    {
        alert("������ҽ�ƻ������ƣ�");
        return;
    }
    if (dateDiff(fm.DiagnoseDate.value,mCurrentDate,'D') < 0)
    {
        alert("ȷ�����ڲ��ܴ��ڵ�ǰ����!");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "4";
    fm.action = "./LLMedicalFeeInp4Save.jsp";
    submitForm();
}

/*����[����]��ť��Ӧ����*/
function AddClick5()
{
    //�ǿռ���
 //   if (fm.FactorCode.value == "" || fm.FactorCode.value == null)
    if (fm.FactorCode.value == "" || fm.FactorCode.value == null||fm.FactorName.value == ""||fm.FactorName.value == null)
    {
        alert("���������ַ��ô��룡");
        return;
    }
    if (fm.FactorUnitName.value == "" || fm.FactorUnitName.value == null)
    {
        alert("���������������ƣ�");
        return;
    }
    var date4 = dateDiff(fm.FactorStateDate.value,fm.FactorEndDate.value,'D');
    if (date4 < 0)
    {
        alert("�������ڲ���С����ʼ���ڣ�");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "5";
    fm.action = "./LLMedicalFeeInp5Save.jsp";
    submitForm();
}

/*�籣����������[����]��ť��Ӧ����*/
function AddClick6()
{
    //�ǿռ���
    if (fm.FeeThreeCode.value == "" || fm.FeeThreeCode.value == null||fm.FeeThreeName.value == ""||fm.FeeThreeName.value == null)
    {
        alert("��������ô��룡");
        return;
    }
    if (fm.FeeThreeUnitName.value == "" || fm.FeeThreeUnitName.value == null)
    {
        alert("���������������ƣ�");
        return;
    }

    mOperate="INSERT";
    fm.currentInput.value = "6";
    fm.action = "./LLMedicalFeeInp6Save.jsp";
    submitForm();
}

/*����[ɾ��]��ť��Ӧ����*/
function DeleteClick1()
{
    if(MedFeeClinicInpGrid.getSelNo() >= 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "1";
            fm.action = "./LLMedicalFeeInp1Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}

/*סԺ[ɾ��]��ť��Ӧ����*/
function DeleteClick2()
{
    if(MedFeeInHosInpGrid.getSelNo() >= 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "2";
            fm.action = "./LLMedicalFeeInp2Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}


/*�˲�[ɾ��]��ť��Ӧ����*/
function DeleteClick3()
{
    if(MedFeeCaseInfoGrid.getSelNo() >= 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "3";
            fm.action = "./LLMedicalFeeInp3Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}

/*����[ɾ��]��ť��Ӧ����*/
function DeleteClick4()
{
    if(MedFeeOperGrid.getSelNo() >= 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "4";
            fm.action = "./LLMedicalFeeInp4Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}

/*����[ɾ��]��ť��Ӧ����*/
function DeleteClick5()
{
    if(MedFeeOtherGrid.getSelNo() >= 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "5";
            fm.action = "./LLMedicalFeeInp5Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}

/*�籣����������[ɾ��]��ť��Ӧ����*/
function DeleteClick6()
{
    if(MedFeeThreeGrid.getSelNo() >= 0)
    {
        if (confirm("��ȷʵ��ɾ���ü�¼��?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "6";
            fm.action = "./LLMedicalFeeInp6Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("��ѡ��һ����¼��");
        return;
    }
}

/*[������ϢMulLine]�Ĵ�������*/
function getMedFeeClinicInpGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeClinicInpGrid.getSelNo();
    
    //��ֵҽԺ��Ϣ
    fm.ClinicHosID.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,1);             //ҽԺ���
    fm.ClinicHosName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,2);            //ҽԺ����
    fm.ClinicHosGrade.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,3);           //ҽԺ�ȼ�

    //��ֵ������Ϣ
    fm.ClinicStartDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,4);
    fm.ClinicEndDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,5);
    fm.ClinicDayCount1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,6);

    //��ֵ������Ϣ
    fm.ClinicMedFeeType.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,7);
    fm.ClinicMedFeeTypeName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,8);
    fm.OriginFee.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,9);//ԭʼ����
    fm.MinusFee.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,19);//�۳�����
    fm.ClinicMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,18);//�������
    fm.MinusReason.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,20);//�۳�ԭ��
    fm.SocietyFee.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,21);//�籣�⸶����
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,11);       //�ⰸ��
    fm.caseNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,12);        //�ְ���
    fm.custNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,13);        //�ͻ���
    fm.ClinicMainFeeNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,14);     //�ʵ���
    fm.feeDetailNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,15);   //�ʵ���ϸ��
    fm.DealFlag.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,16); //��ʼ�����Ƿ����ڳ�������,0��1����
    fm.MinusReasonName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,22)//�۳�ԭ��
    //������¼�������־����ʾ����������Աע��
    if (fm.DealFlag.value == "0")
    {
        alert("�õ�֤��ʼ�������ڳ�������,��ע��!");
    }

    //���ڼ���
    dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');
    
    //���Ĳ�����ť
//    fm.saveButton1.disabled = true;
    fm.deleteButton1.disabled = false;
}

/*[סԺ��ϢMulLine]�Ĵ�������*/
function getMedFeeInHosInpGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeInHosInpGrid.getSelNo();

    //��ֵҽԺ��Ϣ
    fm.InHosHosID.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);             //ҽԺ���
    fm.InHosHosName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);            //ҽԺ����
    fm.InHosHosGrade.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);           //ҽԺ�ȼ�

    //��ֵ������Ϣ
    fm.InHosStartDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
    fm.InHosEndDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
    fm.InHosDayCount1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,6);

    //��ֵ������Ϣ
    fm.InHosMedFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,7);
    fm.InHosMedFeeTypeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,8);
    fm.OriginFee1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);//ԭʼ����
    fm.InHosMedFeeSum1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,18);//�������
    fm.MinusFee1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,19);//�۳�����
    fm.MinusReason1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,20);//�۳�ԭ��
    fm.SocietyFee1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);//�籣�⸶����
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11);       //�ⰸ��
    fm.caseNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);        //�ְ���
    fm.custNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);        //�ͻ���
    fm.HosMainFeeNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,14);     //�ʵ���
    fm.feeDetailNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);   //�ʵ���ϸ��
    fm.DealFlag.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16); //��ʼ�����Ƿ����ڳ�������,0��1����
    fm.MinusReasonName1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,23);//�۳�ԭ��
    //������¼�������־����ʾ����������Աע��
    if (fm.DealFlag.value == "0")
    {
        alert("�õ�֤��ʼ�������ڳ�������,��ע��!");
    }
    //���ڼ���
    dayCount(fm.InHosStartDate.value,fm.InHosEndDate.value,'H');
}

/*[�˲���ϢMulLine]�Ĵ�������*/
function getMedFeeCaseInfoGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeCaseInfoGrid.getSelNo();

    //��ֵ�˲���Ϣ
    fm.DefoType.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,1);             //
    fm.DefoGrade.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,2);            //
    fm.DefoGradeName.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,3);           //
    fm.DefoCode.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,4);
//    fm.DeformityReason.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,5);
    fm.DeformityRate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,6);
    fm.AppDeformityRate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,7);
    fm.RealRate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,8);
    
    fm.JudgeOrganName.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,13);
    fm.JudgeDate.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,14);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,9);       //�ⰸ��
    fm.caseNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,10);        //�ְ���
    fm.SerialNo3.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,11);     //��ˮ��
    fm.custNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,12);        //�ͻ���

}

/*[������ϢMulLine]�Ĵ�������*/
function getMedFeeOperGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeOperGrid.getSelNo();

    //��ֵ������Ϣ
    fm.OperationType.value = MedFeeOperGrid.getRowColData(tNo - 1,1);      //
    fm.OperationCode.value = MedFeeOperGrid.getRowColData(tNo - 1,2);      //
    fm.OperationName.value = MedFeeOperGrid.getRowColData(tNo - 1,3);      //
//    fm.OpLevel.value = MedFeeOperGrid.getRowColData(tNo - 1,4);
//    fm.OpGrade.value = MedFeeOperGrid.getRowColData(tNo - 1,5);

    fm.UnitName.value = MedFeeOperGrid.getRowColData(tNo - 1,12);
    fm.DiagnoseDate.value = MedFeeOperGrid.getRowColData(tNo - 1,13);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeOperGrid.getRowColData(tNo - 1,8);       //�ⰸ��
    fm.caseNo.value = MedFeeOperGrid.getRowColData(tNo - 1,9);        //�ְ���
    fm.SerialNo4.value = MedFeeOperGrid.getRowColData(tNo - 1,10);     //��ˮ��
//        alert(fm.SerialNo4.value);
    fm.custNo.value = MedFeeOperGrid.getRowColData(tNo - 1,11);        //�ͻ���

}

/*[������ϢMulLine]�Ĵ�������*/
function getMedFeeOtherGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeOtherGrid.getSelNo();

    //��ֵ������Ϣ
    fm.FactorType.value = MedFeeOtherGrid.getRowColData(tNo - 1,1);      //
    fm.FactorCode.value = MedFeeOtherGrid.getRowColData(tNo - 1,2);      //
    fm.FactorName.value = MedFeeOtherGrid.getRowColData(tNo - 1,3);      //
    fm.FactorValue.value = MedFeeOtherGrid.getRowColData(tNo - 1,4);
    
    fm.FactorUnitName.value = MedFeeOtherGrid.getRowColData(tNo - 1,9);
    showOneCodeName('llfactype','FactorType','FactorTypeName');
    fm.FactorStateDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,10);
    fm.FactorEndDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,11);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,5);       //�ⰸ��
    fm.caseNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,6);        //�ְ���
    fm.SerialNo5.value = MedFeeOtherGrid.getRowColData(tNo - 1,7);     //��ˮ��
    fm.custNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,8);        //�ͻ���

}

/*[�籣����������MulLine]�Ĵ�������*/
function getMedFeeThreeGrid()
{
    //�õ�MulLine��
    var tNo = MedFeeThreeGrid.getSelNo();

    //��ֵ������Ϣ
    fm.FeeThreeType.value = MedFeeThreeGrid.getRowColData(tNo - 1,1);      //
    fm.FeeThreeCode.value = MedFeeThreeGrid.getRowColData(tNo - 1,2);      //
    fm.FeeThreeName.value = MedFeeThreeGrid.getRowColData(tNo - 1,3);      //
    fm.FeeThreeValue.value = MedFeeThreeGrid.getRowColData(tNo - 1,4);
    
    fm.FeeThreeUnitName.value = MedFeeThreeGrid.getRowColData(tNo - 1,9);
    fm.AdjRemark.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    showOneCodeName('llfactype','FeeThreeType','FeeThreeTypeName');
//    fm.FeeThreeStateDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    fm.FeeThreeEndDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
    
    //��ֵ�ؼ���Ϣ
    fm.claimNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,5);       //�ⰸ��
    fm.caseNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,6);        //�ְ���
    fm.SerialNo5.value = MedFeeThreeGrid.getRowColData(tNo - 1,7);     //��ˮ��
    fm.custNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,8);        //�ͻ���

}

/*�ύ��̨ǰ��ҳ������У��*/
function beforeInHosSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("InHosHosID").value == null || trim(document.all("InHosHosID").value) ==''){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.InHosHosID.focus();
         return false;
    }else if( document.all("InHosHosName").value == null || trim(document.all("InHosHosName").value) == '' ){
         alert('[ҽԺ����]����Ϊ�գ�');
         fm.InHosHosName.focus();
         return false;
    }else if( document.all("InHosHosGrade").value == null || trim(document.all("InHosHosGrade").value) == '' ){
         alert('[ҽԺ�ȼ�]����Ϊ�գ�');
         fm.InHosHosGrade.focus();
         return false;
    }else if( document.all("InHosStartDate").value == null || trim(document.all("InHosStartDate").value) == '' ){
         alert('[��ʼ����]����Ϊ�գ�');
         fm.InHosStartDate.focus();
         return false;
    }else if( document.all("InHosEndDate").value == null || trim(document.all("InHosEndDate").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.UInHosEndDate.focus();
         return false;
    }else if( document.all("InHosMedFeeType").value == null || trim(document.all("InHosMedFeeType").value) == '' ){
         alert('[���ô���]����Ϊ�գ�');
         fm.InHosMedFeeType.focus();
         return false;
    }else if( document.all("InHosMedFeeTypeName").value == null || trim(document.all("InHosMedFeeTypeName").value) == '' ){
         alert('[��������]����Ϊ�գ�');
         fm.InHosMedFeeTypeName.focus();
         return false;
    }else if( document.all("InHosFeeSum").value == null || trim(document.all("InHosFeeSum").value) == '' ){
         alert('[���ý��]����Ϊ�գ�');
         fm.InHosFeeSum.focus();
         return false;
    }
    return true;
}

//���ڼ���
function dayCount(start,end,hosType)
{
    if (start == "" || end == "" || start == null || end == null)
    {
        return false;
    }
    var date1 = dateDiff(start,end,'D');
//    alert(date1);
    var date2 = dateDiff(fm.accDate1.value,end,'D');
    var date3 = dateDiff(fm.accDate2.value,end,'D');
    if (date1 < 0)
    {
        alert("��֤�������ڲ������ڿ�ʼ����!");
        return false;
    }
//    if (date2 < 0)
//    {
//        alert("��֤�������ڲ������������¹ʷ�������!");
//        return false;
//    }
//    if (date3 < 0)
//    {
//        alert("��֤�������ڲ������ڳ�������!");
//        return false;
//    }
    if (date1 == 0)
    {
        date1 = 1;
    }
//    if (date2 == 0)
//    {
//        date2 = 1;
//    }
//    if (date3 == 0)
//    {
//        date3 = 1;
//    }
//    //����
//    if(hosType == 'C')
//    {
//        //ʵ����������
//        fm.ClinicDayCount1.value = date1;
//        fm.ClinicDayCount2.value = date2;
//        fm.ClinicDayCount3.value = date3;
//    }
//    //סԺ
//    if(hosType == 'H')
//    {
//        //ʵ��סԺ����
//        if (fm.InHosDayCount1.value == "" || fm.InHosDayCount1.value == null)
//        {
//            fm.InHosDayCount1.value = date1;
//        }
//        fm.InHosDayCount2.value = date2;
//        fm.InHosDayCount3.value = date3;
//    }
    return true;
}


//yaory

function calculate()
{
  if(fm.OriginFee.value=="")
  {
    alert("��������ԭʼ���ã�");
    return false;
  }
  
  if(isNumeric(fm.OriginFee.value)==false)
  {
    alert("��������ȷ��ԭʼ���ã�");
    fm.MinusFee.value="";
    fm.OriginFee.value=="";
    return false;
  }
  
  
  if(isNumeric(fm.MinusFee.value)==false)
  {
    alert("��������ȷ�Ŀ۳����ã�");
    fm.MinusFee.value="";
    fm.OriginFee.value=="";
    return false;
  }
  
  var reasonFee=fm.OriginFee.value-fm.MinusFee.value;
  fm.ClinicMedFeeSum.value=reasonFee;
  
}

function isNumeric(strValue)
{
  
  
  var NUM="0123456789.";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false
  }
  if(strValue.indexOf(".")!=strValue.lastIndexOf(".")) return false;
  return true;
}

function calmix()
{
  try{
    var mixfee=fm.ClinicMedFeeSum.value-fm.SocietyFee.value;
    fm.MixFee.value=mixfee;
  }catch(ex)
  {}
}



function calculate1()
{
  if(fm.OriginFee1.value=="")
  {
    alert("��������ԭʼ���ã�");
    return false;
  }
  
  if(isNumeric(fm.OriginFee1.value)==false)
  {
    alert("��������ȷ��ԭʼ���ã�");
    fm.MinusFee.value="";
    fm.OriginFee.value=="";
    return false;
  }
  
  
  if(isNumeric(fm.MinusFee1.value)==false)
  {
    alert("��������ȷ�Ŀ۳����ã�");
    fm.MinusFee.value="";
    fm.OriginFee.value=="";
    return false;
  }
  
  var reasonFee=fm.OriginFee1.value-fm.MinusFee1.value;
  fm.InHosMedFeeSum1.value=reasonFee;
  
}


function calmix1()
{
  try{
    var mixfee=fm.InHosMedFeeSum1.value-fm.SocietyFee.value;
    fm.MixFee1.value=mixfee;
  }catch(ex)
  {}
}

//У�����ڸ�ʽ
function checkapplydate(){
  if(fm.ClinicStartDate.value.length==8){
  if(fm.ClinicStartDate.value.indexOf('-')==-1){ 
    var Year =     fm.ClinicStartDate.value.substring(0,4);
    var Month =    fm.ClinicStartDate.value.substring(4,6);
    var Day =      fm.ClinicStartDate.value.substring(6,8);
    fm.ClinicStartDate.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
        alert("���������������!");
        fm.ClinicStartDate.value = ""; 
        return;
      }
  }
} else {var Year =     fm.ClinicStartDate.value.substring(0,4);
      var Month =    fm.ClinicStartDate.value.substring(5,7);
      var Day =      fm.ClinicStartDate.value.substring(8,10);
      if(Year=="0000"||Month=="00"||Day=="00"){
        alert("���������������!");
        fm.ClinicStartDate.value = "";
        return;
         }
  }
}