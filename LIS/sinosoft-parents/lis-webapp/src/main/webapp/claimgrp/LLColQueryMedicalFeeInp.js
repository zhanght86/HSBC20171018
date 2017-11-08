var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
/**=========================================================================
    ???????
    ?????????????????????
    ? ? ????
    ?????2005.05.13
   =========================================================================
**/

//??????
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

//??????
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

//??????
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

//??????
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

//??????
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

//??????
function queryGrid1()
{
    var tclaimNo = fm.claimNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16--------------------------17
    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.Fee, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,b.DealFlag,(case b.DealFlag when '0' then '?' end)"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.MainFeeNo=b.MainFeeNo "
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'A'"
               + " order by a.MainFeeNo";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql1");
	mySql.addSubPara(tclaimNo);
    //??????
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeClinicInpGrid);
    }
}

//??????
function queryGrid2()
{
    var tclaimNo = fm.claimNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16
   /* var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.Fee, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,b.DealFlag,(case b.DealFlag when '0' then '?' end)"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.MainFeeNo=b.MainFeeNo "
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'B'"
               + " order by a.MainFeeNo";*/
    //??????
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql2");
	mySql.addSubPara(tclaimNo);
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeInHosInpGrid);
    }
}

//??????
function queryGrid3()
{
    var tclaimNo = fm.claimNo.value;
    //-----------------------1---------2---------3-------4-----5----------6--------------7------------8-------9----10-------11---------12-----13
   /* var strSql = " select defotype,defograde,DefoName,defocode,'',deformityrate,appdeformityrate,realrate,clmno,caseno,serialno,customerno,JudgeOrganName,JudgeDate"
               + " from LLCaseInfo where "
               + " ClmNo='" + tclaimNo + "'"
               + " order by serialno";*/
    //??????
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql3");
	mySql.addSubPara(tclaimNo);
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeCaseInfoGrid);
    }
}

//??????
function queryGrid4()
{
    var tclaimNo = fm.claimNo.value;
    //-------------------------1--------------2-------------3----------4------5-------6------7----8-----9-----10----------11
    /*var strSql = " select operationtype,operationcode,operationname,oplevel,opgrade,opfee,mainop,clmno,caseno,serialno,customerno,UnitName,DiagnoseDate"
               + " from LLOperation where "
               + " ClmNo='" + tclaimNo + "'"
               + " order by serialno";*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql4");
	mySql.addSubPara(tclaimNo);            
    //??????
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeOperGrid);
    }
}

//??????
function queryGrid5()
{
    var tclaimNo = fm.claimNo.value;
    //------------------------1-----------2--------3----------4----------5------6------7----------8
   /* var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,StartDate,EndDate"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and factortype = 'succor'"
               + " order by serialno";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql5");
	mySql.addSubPara(tclaimNo);  
    //??????
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeOtherGrid);
    }
}

//?????????
function queryGrid6()
{
    var tclaimNo = fm.claimNo.value;
    //------------------------1-----------2--------3----------4----------5------6------7----------8
   /* var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,AdjRemark"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and factortype = 'med'"
               + " order by serialno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql6");
	mySql.addSubPara(tclaimNo);  
    //??????
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeThreeGrid);
    }
}

//????????
function queryDeformityRate()
{
    if(fm.DefoCode.value != "" && fm.DefoCode.value !=null)
    {
       /* var strSql = " select t.deforate from LLParaDeformity t where "
                   + " t.defocode='" + fm.DefoCode.value + "'";*/
        //??????
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLColQueryMedicalFeeInpInputSql");
	mySql.setSqlId("LLColQueryMedicalFeeInpSql7");
	mySql.addSubPara(tclaimNo);  
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

/*????????????*/
function beforeClinicSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("ClinicHosID").value == null || trim(document.all("ClinicHosID").value) ==''){
         alert('[????]?????');
         fm.ClinicHosID.focus();
         return false;
    }else if( document.all("ClinicHosName").value == null || trim(document.all("ClinicHosName").value) == '' ){
         alert('[????]?????');
         fm.ClinicHosName.focus();
         return false;
    }else if( document.all("ClinicHosGrade").value == null || trim(document.all("ClinicHosGrade").value) == '' ){
         alert('[????]?????');
         fm.ClinicHosGrade.focus();
         return false;
    }else if( document.all("ClinicStartDate").value == null || trim(document.all("ClinicStartDate").value) == '' ){
         alert('[????]?????');
         fm.ClinicStartDate.focus();
         return false;
    }else if( document.all("ClinicEndDate").value == null || trim(document.all("ClinicEndDate").value) == '' ){
         alert('[????]?????');
         fm.UClinicEndDate.focus();
         return false;
    }else if( document.all("ClinicMedFeeType").value == null || trim(document.all("ClinicMedFeeType").value) == '' ){
         alert('[????]?????');
         fm.ClinicMedFeeType.focus();
         return false;
    }else if( document.all("ClinicMedFeeTypeName").value == null || trim(document.all("ClinicMedFeeTypeName").value) == '' ){
         alert('[????]?????');
         fm.ClinicMedFeeTypeName.focus();
         return false;
    }else if( document.all("ClinicFeeSum").value == null || trim(document.all("ClinicFeeSum").value) == '' ){
         alert('[????]?????');
         fm.ClinicFeeSum.focus();
         return false;
    }
    return true;

}

/*[????]?????*/
function submitForm()
{
    var showStr="?????????????????????????????";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.submit(); //??
}

/*[????]????,?????????????*/
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        mOperate = '';
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        if ( fm.currentInput.value=="1")//??
        {
            initMedFeeClinicInpGrid(); 
            queryGrid1();
            //????
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
        if ( fm.currentInput.value=="2")//??
        {
            initMedFeeInHosInpGrid(); 
            queryGrid2();
            //????
//            fm.HosMainFeeNo.value = "";
//            fm.InHosHosID.value = "";
//            fm.InHosHosName.value = "";
            fm.InHosMedFeeType.value = "";
            fm.InHosMedFeeTypeName.value = "";
            fm.InHosMedFeeSum.value = "";
            fm.InHosStartDate.value = "";
            fm.InHosEndDate.value = "";
            fm.InHosDayCount1.value = "";
            fm.InHosDayCount2.value = "";
            fm.InHosDayCount3.value = "";
        }
        if ( fm.currentInput.value=="3")//??
        {
            initMedFeeCaseInfoGrid();
            queryGrid3();
            //????
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
        if ( fm.currentInput.value=="4")//????
        {
            initMedFeeOperGrid();
            queryGrid4();
            //????
            fm.OperationType.value = "";
            fm.OperationTypeName.value = "";
            fm.OperationCode.value = "";
            fm.OperationName.value = "";
            fm.UnitName.value = "";
            fm.OpFee.value = "";
            fm.DiagnoseDate.value = "";
        }
        if ( fm.currentInput.value=="5")//??
        {
            initMedFeeOtherGrid();
            queryGrid5();
            //????
            fm.FactorType.value = "";
            fm.FactorTypeName.value = "";
            fm.FactorCode.value = "";
            fm.FactorName.value = "";
            fm.FactorValue.value = "";
            fm.FactorUnitName.value = "";
            fm.FactorStateDate.value = "";
            fm.FactorEndDate.value = "";
        }
        if ( fm.currentInput.value=="6")//???????
        {
            initMedFeeThreeGrid();
            queryGrid6();
            //????
//            fm.FeeThreeType.value = "";
//            fm.FeeThreeTypeName.value = "";
            fm.FeeThreeCode.value = "";
            fm.FeeThreeName.value = "";
            fm.FeeThreeValue.value = "";
            fm.FeeThreeUnitName.value = "";
//            fm.FeeThreeStateDate.value = "";
//            fm.FeeThreeEndDate.value = "";
        }
    }
}

/*??[??]??????*/
function AddClick1()
{
    //????
    if (fm.ClinicMainFeeNo.value == "" || fm.ClinicMainFeeNo.value == null)
    {
        alert("????????");
        return;
    }
    if (fm.ClinicMedFeeType.value == "" || fm.ClinicMedFeeType.value == null)
    {
        alert("?????????");
        return;
    }
    //????
    if (dateDiff(fm.ClinicStartDate.value,mCurrentDate,'D') < 0 || dateDiff(fm.ClinicEndDate.value,mCurrentDate,'D') < 0)
    {
        alert("????????????????!");
        return;
    }
    //????,???????
    if (!dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C'))
    {
        return;
    }
    var date4 = dateDiff(fm.accDate2.value,fm.ClinicStartDate.value,'D');
    if (date4 < 0)
    {
        if(confirm("????????????,????!"))
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

/*??[??]??????*/
function AddClick2()
{
//    alert(dateDiff("2005-01-01","2005-01-31","D"));
    //????
    if (fm.HosMainFeeNo.value == "" || fm.HosMainFeeNo.value == null)
    {
        alert("????????");
        return;
    }
    if (fm.InHosMedFeeType.value == "" || fm.InHosMedFeeType.value == null)
    {
        alert("?????????");
        return;
    }
    //????
    if (dateDiff(fm.InHosStartDate.value,mCurrentDate,'D') < 0 || dateDiff(fm.InHosEndDate.value,mCurrentDate,'D') < 0)
    {
        alert("????");
        return;
    }
    //????
    if (!dayCount(fm.InHosStartDate.value,fm.InHosEndDate.value,'H'))
    {
        return;
    }
    var date4 = dateDiff(fm.accDate2.value,fm.InHosStartDate.value,'D');
    if (date4 < 0)
    {
        if(confirm("????????????,?????"))
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

/*??[??]??????*/
function AddClick3()
{
    //????
    if (fm.DefoType.value == "")
    {
        alert("????????");
        return;
    }
    if (fm.DefoGrade.value == "")
    {
        alert("????????");
        return;
    }
    if (fm.DefoCode.value == "")
    {
        alert("????????");
        return;
    }
    //??????
    if(dateDiff(fm.JudgeDate.value,mCurrentDate,'D') < 0)
    {
        alert("???????????");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "3";
    fm.action = "./LLMedicalFeeInp3Save.jsp";
    submitForm();
}

/*??[??]??????*/
function AddClick4()
{
    //????
    if (fm.OperationCode.value == "" && fm.OperationCode.value == null)
    {
        alert("??????");
        return;
    }
    if (fm.UnitName.value == "" || fm.UnitName.value == null)
    {
        alert("??????????");
        return;
    }
    if (dateDiff(fm.DiagnoseDate.value,mCurrentDate,'D') < 0)
    {
        alert("????????????!");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "4";
    fm.action = "./LLMedicalFeeInp4Save.jsp";
    submitForm();
}

/*??[??]??????*/
function AddClick5()
{
    //????
    if (fm.FactorCode.value == "" || fm.FactorCode.value == null)
    {
        alert("??????????");
        return;
    }
    if (fm.FactorUnitName.value == "" || fm.FactorUnitName.value == null)
    {
        alert("??????????");
        return;
    }
    var date4 = dateDiff(fm.FactorStateDate.value,fm.FactorEndDate.value,'D');
    if (date4 < 0)
    {
        alert("?????????????");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "5";
    fm.action = "./LLMedicalFeeInp5Save.jsp";
    submitForm();
}

/*???????[??]??????*/
function AddClick6()
{
    //????
    if (fm.FeeThreeCode.value == "" || fm.FeeThreeCode.value == null)
    {
        alert("????????");
        return;
    }
    if (fm.FeeThreeUnitName.value == "" || fm.FeeThreeUnitName.value == null)
    {
        alert("??????????");
        return;
    }

    mOperate="INSERT";
    fm.currentInput.value = "6";
    fm.action = "./LLMedicalFeeInp6Save.jsp";
    submitForm();
}

/*??[??]??????*/
function DeleteClick1()
{
    if(MedFeeClinicInpGrid.getSelNo() >= 0)
    {
        if (confirm("???????????"))
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
        alert("????????");
        return;
    }
}

/*??[??]??????*/
function DeleteClick2()
{
    if(MedFeeInHosInpGrid.getSelNo() >= 0)
    {
        if (confirm("???????????"))
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
        alert("????????");
        return;
    }
}


/*??[??]??????*/
function DeleteClick3()
{
    if(MedFeeCaseInfoGrid.getSelNo() >= 0)
    {
        if (confirm("???????????"))
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
        alert("????????");
        return;
    }
}

/*??[??]??????*/
function DeleteClick4()
{
    if(MedFeeOperGrid.getSelNo() >= 0)
    {
        if (confirm("???????????"))
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
        alert("????????");
        return;
    }
}

/*??[??]??????*/
function DeleteClick5()
{
    if(MedFeeOtherGrid.getSelNo() >= 0)
    {
        if (confirm("???????????"))
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
        alert("????????");
        return;
    }
}

/*???????[??]??????*/
function DeleteClick6()
{
    if(MedFeeThreeGrid.getSelNo() >= 0)
    {
        if (confirm("???????????"))
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
        alert("????????");
        return;
    }
}

/*[????MulLine]?????*/
function getMedFeeClinicInpGrid()
{
    //??MulLine?
    var tNo = MedFeeClinicInpGrid.getSelNo();
    
    //??????
    fm.ClinicHosID.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,1);             //????
    fm.ClinicHosName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,2);            //????
    fm.ClinicHosGrade.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,3);           //????

    //??????
    fm.ClinicStartDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,4);
    fm.ClinicEndDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,5);
    fm.ClinicDayCount1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,6);

    //??????
    fm.ClinicMedFeeType.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,7);
    fm.ClinicMedFeeTypeName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,8);
    fm.ClinicMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,9);

    //??????
    fm.claimNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,11);       //???
    fm.caseNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,12);        //???
    fm.custNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,13);        //???
    fm.ClinicMainFeeNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,14);     //???
    fm.feeDetailNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,15);   //?????
    fm.DealFlag.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,16); //????????????,0?1??
    
    //????????????????????
    if (fm.DealFlag.value == "0")
    {
        alert("?????????????,???!");
    }

    //????
    dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');
    
    //??????
//    fm.saveButton1.disabled = true;
    fm.deleteButton1.disabled = false;
}

/*[????MulLine]?????*/
function getMedFeeInHosInpGrid()
{
    //??MulLine?
    var tNo = MedFeeInHosInpGrid.getSelNo();

    //??????
    fm.InHosHosID.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);             //????
    fm.InHosHosName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);            //????
    fm.InHosHosGrade.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);           //????

    //??????
    fm.InHosStartDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
    fm.InHosEndDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
    fm.InHosDayCount1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,6);

    //??????
    fm.InHosMedFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,7);
    fm.InHosMedFeeTypeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,8);
    fm.InHosMedFeeSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);
    
    //??????
    fm.claimNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11);       //???
    fm.caseNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);        //???
    fm.custNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);        //???
    fm.HosMainFeeNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,14);     //???
    fm.feeDetailNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);   //?????
    fm.DealFlag.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16); //????????????,0?1??
    
    //????????????????????
    if (fm.DealFlag.value == "0")
    {
        alert("?????????????,???!");
    }
    //????
    dayCount(fm.InHosStartDate.value,fm.InHosEndDate.value,'H');
}

/*[????MulLine]?????*/
function getMedFeeCaseInfoGrid()
{
    //??MulLine?
    var tNo = MedFeeCaseInfoGrid.getSelNo();

    //??????
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
    
    //??????
    fm.claimNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,9);       //???
    fm.caseNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,10);        //???
    fm.SerialNo3.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,11);     //???
    fm.custNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,12);        //???

}

/*[????MulLine]?????*/
function getMedFeeOperGrid()
{
    //??MulLine?
    var tNo = MedFeeOperGrid.getSelNo();

    //??????
    fm.OperationType.value = MedFeeOperGrid.getRowColData(tNo - 1,1);      //
    fm.OperationCode.value = MedFeeOperGrid.getRowColData(tNo - 1,2);      //
    fm.OperationName.value = MedFeeOperGrid.getRowColData(tNo - 1,3);      //
//    fm.OpLevel.value = MedFeeOperGrid.getRowColData(tNo - 1,4);
//    fm.OpGrade.value = MedFeeOperGrid.getRowColData(tNo - 1,5);

    fm.UnitName.value = MedFeeOperGrid.getRowColData(tNo - 1,12);
    fm.DiagnoseDate.value = MedFeeOperGrid.getRowColData(tNo - 1,13);
    
    //??????
    fm.claimNo.value = MedFeeOperGrid.getRowColData(tNo - 1,8);       //???
    fm.caseNo.value = MedFeeOperGrid.getRowColData(tNo - 1,9);        //???
    fm.SerialNo4.value = MedFeeOperGrid.getRowColData(tNo - 1,10);     //???
//        alert(fm.SerialNo4.value);
    fm.custNo.value = MedFeeOperGrid.getRowColData(tNo - 1,11);        //???

}

/*[????MulLine]?????*/
function getMedFeeOtherGrid()
{
    //??MulLine?
    var tNo = MedFeeOtherGrid.getSelNo();

    //??????
    fm.FactorType.value = MedFeeOtherGrid.getRowColData(tNo - 1,1);      //
    fm.FactorCode.value = MedFeeOtherGrid.getRowColData(tNo - 1,2);      //
    fm.FactorName.value = MedFeeOtherGrid.getRowColData(tNo - 1,3);      //
    fm.FactorValue.value = MedFeeOtherGrid.getRowColData(tNo - 1,4);
    
    fm.FactorUnitName.value = MedFeeOtherGrid.getRowColData(tNo - 1,9);
    showOneCodeName('llfactype','FactorType','FactorTypeName');
    fm.FactorStateDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,10);
    fm.FactorEndDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,11);
    
    //??????
    fm.claimNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,5);       //???
    fm.caseNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,6);        //???
    fm.SerialNo5.value = MedFeeOtherGrid.getRowColData(tNo - 1,7);     //???
    fm.custNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,8);        //???

}

/*[???????MulLine]?????*/
function getMedFeeThreeGrid()
{
    //??MulLine?
    var tNo = MedFeeThreeGrid.getSelNo();

    //??????
    fm.FeeThreeType.value = MedFeeThreeGrid.getRowColData(tNo - 1,1);      //
    fm.FeeThreeCode.value = MedFeeThreeGrid.getRowColData(tNo - 1,2);      //
    fm.FeeThreeName.value = MedFeeThreeGrid.getRowColData(tNo - 1,3);      //
    fm.FeeThreeValue.value = MedFeeThreeGrid.getRowColData(tNo - 1,4);
    
    fm.FeeThreeUnitName.value = MedFeeThreeGrid.getRowColData(tNo - 1,9);
    fm.AdjRemark.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    showOneCodeName('llfactype','FeeThreeType','FeeThreeTypeName');
//    fm.FeeThreeStateDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    fm.FeeThreeEndDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
    
    //??????
    fm.claimNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,5);       //???
    fm.caseNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,6);        //???
    fm.SerialNo5.value = MedFeeThreeGrid.getRowColData(tNo - 1,7);     //???
    fm.custNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,8);        //???

}

/*????????????*/
function beforeInHosSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("InHosHosID").value == null || trim(document.all("InHosHosID").value) ==''){
         alert('[????]?????');
         fm.InHosHosID.focus();
         return false;
    }else if( document.all("InHosHosName").value == null || trim(document.all("InHosHosName").value) == '' ){
         alert('[????]?????');
         fm.InHosHosName.focus();
         return false;
    }else if( document.all("InHosHosGrade").value == null || trim(document.all("InHosHosGrade").value) == '' ){
         alert('[????]?????');
         fm.InHosHosGrade.focus();
         return false;
    }else if( document.all("InHosStartDate").value == null || trim(document.all("InHosStartDate").value) == '' ){
         alert('[????]?????');
         fm.InHosStartDate.focus();
         return false;
    }else if( document.all("InHosEndDate").value == null || trim(document.all("InHosEndDate").value) == '' ){
         alert('[????]?????');
         fm.UInHosEndDate.focus();
         return false;
    }else if( document.all("InHosMedFeeType").value == null || trim(document.all("InHosMedFeeType").value) == '' ){
         alert('[????]?????');
         fm.InHosMedFeeType.focus();
         return false;
    }else if( document.all("InHosMedFeeTypeName").value == null || trim(document.all("InHosMedFeeTypeName").value) == '' ){
         alert('[????]?????');
         fm.InHosMedFeeTypeName.focus();
         return false;
    }else if( document.all("InHosFeeSum").value == null || trim(document.all("InHosFeeSum").value) == '' ){
         alert('[????]?????');
         fm.InHosFeeSum.focus();
         return false;
    }
    return true;
}

//????
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
        alert("??????????????!");
        return false;
    }
//    if (date2 < 0)
//    {
//        alert("??????????????????!");
//        return false;
//    }
//    if (date3 < 0)
//    {
//        alert("??????????????!");
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
    //??
    if(hosType == 'C')
    {
        //??????
        fm.ClinicDayCount1.value = date1;
        fm.ClinicDayCount2.value = date2;
        fm.ClinicDayCount3.value = date3;
    }
    //??
    if(hosType == 'H')
    {
        //??????
        if (fm.InHosDayCount1.value == "" || fm.InHosDayCount1.value == null)
        {
            fm.InHosDayCount1.value = date1;
        }
        fm.InHosDayCount2.value = date2;
        fm.InHosDayCount3.value = date3;
    }
    return true;
}
