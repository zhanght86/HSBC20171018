var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
/**=========================================================================
    修改状态：开始
    修改原因：以下医院信息查询、公共函数功能区
    修 改 人：续涛
    修改日期：2005.05.13
   =========================================================================
**/

//选择账单类型
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

//显示所有账单
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

//隐藏所有账单
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

//区分手术类型
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

//区分要素类型
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

//门诊信息查询
function queryGrid1()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16--------------------------17--18--19--20
    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.Fee, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,b.DealFlag,(case b.DealFlag when '0' then '是' end),b.AdjSum,b.RefuseAmnt,b.AdjReason,b.SecurityAmnt,AdjRemark"
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
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeClinicInpGrid);
    }
}

//住院信息查询
function queryGrid2()
{
    var tclaimNo = fm.claimNo.value;
    var tcustNo   = fm.custNo.value;
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16
  /*  var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.Fee, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,b.DealFlag,(case b.DealFlag when '0' then '是' end),b.AdjSum,b.RefuseAmnt,b.AdjReason,b.SecurityAmnt,(select case when exists (select 'X' from dual where b.HospLineAmnt is not null) then b.HospLineAmnt else 0 end from dual),AdjRemark"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.MainFeeNo=b.MainFeeNo and a.CustomerNo=b.CustomerNo "
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'B'"
               + " and a.CustomerNo = '"+tcustNo+"'"
               + " order by a.MainFeeNo";*/
    //显示所有数据
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

//伤残信息查询
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
    //显示所有数据
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

//手术信息查询
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
    //显示所有数据
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

//其他信息查询
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
    //显示所有数据
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

//社保第三方给付查询
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
    //显示所有数据
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

//查询伤残赔付比例
function queryDeformityRate()
{
    if(fm.DefoCode.value != "" && fm.DefoCode.value !=null)
    {
       /* var strSql = " select t.deforate from LLParaDeformity t where "
                   + " t.defocode='" + fm.DefoCode.value + "'";*/
        //显示所有数据
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

/*提交后台前的页面数据校验*/
function beforeClinicSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("ClinicHosID").value == null || trim(document.all("ClinicHosID").value) ==''){
         alert('[医院代码]不能为空！');
         fm.ClinicHosID.focus();
         return false;
    }else if( document.all("ClinicHosName").value == null || trim(document.all("ClinicHosName").value) == '' ){
         alert('[医院名称]不能为空！');
         fm.ClinicHosName.focus();
         return false;
    }else if( document.all("ClinicHosGrade").value == null || trim(document.all("ClinicHosGrade").value) == '' ){
         alert('[医院等级]不能为空！');
         fm.ClinicHosGrade.focus();
         return false;
    }else if( document.all("ClinicStartDate").value == null || trim(document.all("ClinicStartDate").value) == '' ){
         alert('[开始日期]不能为空！');
         fm.ClinicStartDate.focus();
         return false;
    }else if( document.all("ClinicEndDate").value == null || trim(document.all("ClinicEndDate").value) == '' ){
         alert('[结束日期]不能为空！');
         fm.UClinicEndDate.focus();
         return false;
    }else if( document.all("ClinicMedFeeType").value == null || trim(document.all("ClinicMedFeeType").value) == '' ){
         alert('[费用代码]不能为空！');
         fm.ClinicMedFeeType.focus();
         return false;
    }else if( document.all("ClinicMedFeeTypeName").value == null || trim(document.all("ClinicMedFeeTypeName").value) == '' ){
         alert('[费用名称]不能为空！');
         fm.ClinicMedFeeTypeName.focus();
         return false;
    }else if( document.all("ClinicFeeSum").value == null || trim(document.all("ClinicFeeSum").value) == '' ){
         alert('[费用金额]不能为空！');
         fm.ClinicFeeSum.focus();
         return false;
    }
    return true;

}

/*[公共函数]提交到后台*/
function submitForm()
{
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
    fm.submit(); //提交
}
//单证删除判断
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
     alert("您已经进行单证操作请重新理算！");
     }
}
/*[公共函数]交后操作,服务器数据返回后执行的操作*/
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

        if ( fm.currentInput.value=="1")//门诊
        {
            initMedFeeClinicInpGrid(); 
            queryGrid1();
            //清空表单
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
        if ( fm.currentInput.value=="2")//医院
        {
            initMedFeeInHosInpGrid(); 
            queryGrid2();
            //清空表单
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
        if ( fm.currentInput.value=="3")//伤残
        {
            initMedFeeCaseInfoGrid();
            queryGrid3();
            //清空表单
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
        if ( fm.currentInput.value=="4")//手术特疾
        {
            initMedFeeOperGrid();
            queryGrid4();
            //清空表单
            fm.OperationType.value = "";
            fm.OperationTypeName.value = "";
            fm.OperationCode.value = "";
            fm.OperationName.value = "";
            fm.UnitName.value = "";
            fm.OpFee.value = "";
            fm.DiagnoseDate.value = "";
        }
        if ( fm.currentInput.value=="5")//其他
        {
            initMedFeeOtherGrid();
            queryGrid5();
            //清空表单
            fm.FactorType.value = "";
            fm.FactorTypeName.value = "";
            fm.FactorCode.value = "";
            fm.FactorName.value = "";
            fm.FactorValue.value = "";
            fm.FactorUnitName.value = "";
            fm.FactorStateDate.value = "";
            fm.FactorEndDate.value = "";
        }
        if ( fm.currentInput.value=="6")//社保第三方给付
        {
            initMedFeeThreeGrid();
            queryGrid6();
            //清空表单
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
     queryClaimPolicy();//单证删除判断
    }
    }
}

/*门诊[增加]按钮对应操作*/
function AddClick1()
{
    //非空检验
    if (fm.ClinicMainFeeNo.value == "" || fm.ClinicMainFeeNo.value == null)
    {
        alert("账单号不能为空！");
        return;
    }
    if (fm.ClinicMedFeeType.value == "" || fm.ClinicMedFeeType.value == null||fm.ClinicMedFeeTypeName.value==""||fm.ClinicMedFeeTypeName.value==null)

    {
        alert("费用类型不能为空！");
        return;
    }
    //日期检验
    if (fm.ClinicStartDate.value == '')
    {
        alert("日期不能为空!");
        return;
    }
    if (dateDiff(fm.ClinicStartDate.value,mCurrentDate,'D') < 0 || dateDiff(fm.ClinicStartDate.value,mCurrentDate,'D') < 0)
    {
        alert("门诊开始结束日期不能大于当前日期!");
        return;
    }
    //日期计算,与出险日期比较
    if (!dayCount(fm.ClinicStartDate.value,fm.ClinicStartDate.value,'C'))
    {
        return;
    }
    var date4 = dateDiff(fm.accDate2.value,fm.ClinicStartDate.value,'D');
    if (date4 < 0)
    {
        if(confirm("单证开始日期早于出险日期,是否继续!"))
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

/*住院[增加]按钮对应操作*/
function AddClick2()
{
//    alert(dateDiff("2005-01-01","2005-01-31","D"));
    //非空检验
    if (fm.HosMainFeeNo.value == "" || fm.HosMainFeeNo.value == null)
    {
        alert("账单号不能为空！");
        return;
    }
    if (fm.InHosMedFeeType.value == "" || fm.InHosMedFeeType.value == null||fm.InHosMedFeeTypeName.value==""||fm.InHosMedFeeTypeName.value==null)
    {
        alert("费用类型不能为空！");
        return;
    }
    //日期检验
    if (fm.InHosStartDate.value == '' || fm.InHosEndDate.value == '')
    {
        alert("日期不能为空！");
        return;
    }
    if (dateDiff(fm.InHosStartDate.value,mCurrentDate,'D') < 0 || dateDiff(fm.InHosEndDate.value,mCurrentDate,'D') < 0)
    {
        alert("日期错误");
        return;
    }
    //日期计算
    if (!dayCount(fm.InHosStartDate.value,fm.InHosEndDate.value,'H'))
    {
        return;
    }
    var date4 = dateDiff(fm.accDate2.value,fm.InHosStartDate.value,'D');
    if (date4 < 0)
    {
        if(confirm("单证开始日期早于出险日期,是否继续?"))
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

/*伤残[增加]按钮对应操作*/
function AddClick3()
{
    //非空检验
  //  if (fm.DefoType.value == "")
      if (fm.DefoType.value == ""||fm.DefoType.value ==null||fm.DefoTypeName.value ==""||fm.DefoTypeName.value ==null)
    {
        alert("请输入残疾类型！");
        return;
    }
//    if (fm.DefoGrade.value == "")
    if (fm.DefoGrade.value == ""||fm.DefoGrade.value==null||fm.DefoGradeName.value==""||fm.DefoGradeName.value==null)

    {
        alert("请输入伤残级别！");
        return;
    }
//    if (fm.DefoCode.value == "")
    if (fm.DefoCode.value == ""||fm.DefoCode.value ==null||fm.DefoCodeName.value ==""||fm.DefoCodeName.value ==null)
    {
        alert("请输入伤残代码！");
        return;
    }
    //检验系统日期
    if(dateDiff(fm.JudgeDate.value,mCurrentDate,'D') < 0)
    {
        alert("鉴定日期大于当前日期！");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "3";
    fm.action = "./LLMedicalFeeInp3Save.jsp";
    submitForm();
}

/*手术[增加]按钮对应操作*/
function AddClick4()
{
    //非空检验
    if (fm.OperationType.value == "" ||fm.OperationType.value == null||fm.OperationTypeName.value == ""||fm.OperationTypeName.value == null)
    {
        alert("请输入类型信息！");
        return;
    }
   if (fm.OperationCode.value == "" ||fm.OperationCode.value == null||fm.OperationName.value ==""||fm.OperationName.value ==null)
    {
        alert("请输入代码信息！");
        return;
    }
    if (fm.UnitName.value == "" || fm.UnitName.value == null)
    {
        alert("请输入医疗机构名称！");
        return;
    }
    if (dateDiff(fm.DiagnoseDate.value,mCurrentDate,'D') < 0)
    {
        alert("确诊日期不能大于当前日期!");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "4";
    fm.action = "./LLMedicalFeeInp4Save.jsp";
    submitForm();
}

/*其他[增加]按钮对应操作*/
function AddClick5()
{
    //非空检验
 //   if (fm.FactorCode.value == "" || fm.FactorCode.value == null)
    if (fm.FactorCode.value == "" || fm.FactorCode.value == null||fm.FactorName.value == ""||fm.FactorName.value == null)
    {
        alert("请输入特种费用代码！");
        return;
    }
    if (fm.FactorUnitName.value == "" || fm.FactorUnitName.value == null)
    {
        alert("请输入服务机构名称！");
        return;
    }
    var date4 = dateDiff(fm.FactorStateDate.value,fm.FactorEndDate.value,'D');
    if (date4 < 0)
    {
        alert("结束日期不能小于起始日期！");
        return;
    }
    mOperate="INSERT";
    fm.currentInput.value = "5";
    fm.action = "./LLMedicalFeeInp5Save.jsp";
    submitForm();
}

/*社保第三方给付[增加]按钮对应操作*/
function AddClick6()
{
    //非空检验
    if (fm.FeeThreeCode.value == "" || fm.FeeThreeCode.value == null||fm.FeeThreeName.value == ""||fm.FeeThreeName.value == null)
    {
        alert("请输入费用代码！");
        return;
    }
    if (fm.FeeThreeUnitName.value == "" || fm.FeeThreeUnitName.value == null)
    {
        alert("请输入服务机构名称！");
        return;
    }

    mOperate="INSERT";
    fm.currentInput.value = "6";
    fm.action = "./LLMedicalFeeInp6Save.jsp";
    submitForm();
}

/*门诊[删除]按钮对应操作*/
function DeleteClick1()
{
    if(MedFeeClinicInpGrid.getSelNo() >= 0)
    {
        if (confirm("您确实想删除该记录吗?"))
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
        alert("请选中一条记录！");
        return;
    }
}

/*住院[删除]按钮对应操作*/
function DeleteClick2()
{
    if(MedFeeInHosInpGrid.getSelNo() >= 0)
    {
        if (confirm("您确实想删除该记录吗?"))
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
        alert("请选中一条记录！");
        return;
    }
}


/*伤残[删除]按钮对应操作*/
function DeleteClick3()
{
    if(MedFeeCaseInfoGrid.getSelNo() >= 0)
    {
        if (confirm("您确实想删除该记录吗?"))
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
        alert("请选中一条记录！");
        return;
    }
}

/*手术[删除]按钮对应操作*/
function DeleteClick4()
{
    if(MedFeeOperGrid.getSelNo() >= 0)
    {
        if (confirm("您确实想删除该记录吗?"))
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
        alert("请选中一条记录！");
        return;
    }
}

/*其他[删除]按钮对应操作*/
function DeleteClick5()
{
    if(MedFeeOtherGrid.getSelNo() >= 0)
    {
        if (confirm("您确实想删除该记录吗?"))
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
        alert("请选中一条记录！");
        return;
    }
}

/*社保第三方给付[删除]按钮对应操作*/
function DeleteClick6()
{
    if(MedFeeThreeGrid.getSelNo() >= 0)
    {
        if (confirm("您确实想删除该记录吗?"))
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
        alert("请选中一条记录！");
        return;
    }
}

/*[门诊信息MulLine]的触发函数*/
function getMedFeeClinicInpGrid()
{
    //得到MulLine行
    var tNo = MedFeeClinicInpGrid.getSelNo();
    
    //赋值医院信息
    fm.ClinicHosID.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,1);             //医院编号
    fm.ClinicHosName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,2);            //医院名称
    fm.ClinicHosGrade.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,3);           //医院等级

    //赋值日期信息
    fm.ClinicStartDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,4);
    fm.ClinicEndDate.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,5);
    fm.ClinicDayCount1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,6);

    //赋值费用信息
    fm.ClinicMedFeeType.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,7);
    fm.ClinicMedFeeTypeName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,8);
    fm.OriginFee.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,9);//原始费用
    fm.MinusFee.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,19);//扣除费用
    fm.ClinicMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,18);//合理费用
    fm.MinusReason.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,20);//扣除原因
    fm.SocietyFee.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,21);//社保赔付费用
    //赋值关键信息
    fm.claimNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,11);       //赔案号
    fm.caseNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,12);        //分案号
    fm.custNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,13);        //客户号
    fm.ClinicMainFeeNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,14);     //帐单号
    fm.feeDetailNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,15);   //帐单明细号
    fm.DealFlag.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,16); //开始日期是否早于出险日期,0是1不是
    fm.MinusReasonName.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,22)//扣除原因
    //该条记录有特殊标志，提示后续处理人员注意
    if (fm.DealFlag.value == "0")
    {
        alert("该单证开始日期早于出险日期,请注意!");
    }

    //日期计算
    dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');
    
    //更改操作按钮
//    fm.saveButton1.disabled = true;
    fm.deleteButton1.disabled = false;
}

/*[住院信息MulLine]的触发函数*/
function getMedFeeInHosInpGrid()
{
    //得到MulLine行
    var tNo = MedFeeInHosInpGrid.getSelNo();

    //赋值医院信息
    fm.InHosHosID.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);             //医院编号
    fm.InHosHosName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);            //医院名称
    fm.InHosHosGrade.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);           //医院等级

    //赋值日期信息
    fm.InHosStartDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
    fm.InHosEndDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
    fm.InHosDayCount1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,6);

    //赋值费用信息
    fm.InHosMedFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,7);
    fm.InHosMedFeeTypeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,8);
    fm.OriginFee1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);//原始费用
    fm.InHosMedFeeSum1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,18);//合理费用
    fm.MinusFee1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,19);//扣除费用
    fm.MinusReason1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,20);//扣除原因
    fm.SocietyFee1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);//社保赔付费用
    
    //赋值关键信息
    fm.claimNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11);       //赔案号
    fm.caseNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);        //分案号
    fm.custNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);        //客户号
    fm.HosMainFeeNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,14);     //帐单号
    fm.feeDetailNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);   //帐单明细号
    fm.DealFlag.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16); //开始日期是否早于出险日期,0是1不是
    fm.MinusReasonName1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,23);//扣除原因
    //该条记录有特殊标志，提示后续处理人员注意
    if (fm.DealFlag.value == "0")
    {
        alert("该单证开始日期早于出险日期,请注意!");
    }
    //日期计算
    dayCount(fm.InHosStartDate.value,fm.InHosEndDate.value,'H');
}

/*[伤残信息MulLine]的触发函数*/
function getMedFeeCaseInfoGrid()
{
    //得到MulLine行
    var tNo = MedFeeCaseInfoGrid.getSelNo();

    //赋值伤残信息
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
    
    //赋值关键信息
    fm.claimNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,9);       //赔案号
    fm.caseNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,10);        //分案号
    fm.SerialNo3.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,11);     //流水号
    fm.custNo.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,12);        //客户号

}

/*[手术信息MulLine]的触发函数*/
function getMedFeeOperGrid()
{
    //得到MulLine行
    var tNo = MedFeeOperGrid.getSelNo();

    //赋值手术信息
    fm.OperationType.value = MedFeeOperGrid.getRowColData(tNo - 1,1);      //
    fm.OperationCode.value = MedFeeOperGrid.getRowColData(tNo - 1,2);      //
    fm.OperationName.value = MedFeeOperGrid.getRowColData(tNo - 1,3);      //
//    fm.OpLevel.value = MedFeeOperGrid.getRowColData(tNo - 1,4);
//    fm.OpGrade.value = MedFeeOperGrid.getRowColData(tNo - 1,5);

    fm.UnitName.value = MedFeeOperGrid.getRowColData(tNo - 1,12);
    fm.DiagnoseDate.value = MedFeeOperGrid.getRowColData(tNo - 1,13);
    
    //赋值关键信息
    fm.claimNo.value = MedFeeOperGrid.getRowColData(tNo - 1,8);       //赔案号
    fm.caseNo.value = MedFeeOperGrid.getRowColData(tNo - 1,9);        //分案号
    fm.SerialNo4.value = MedFeeOperGrid.getRowColData(tNo - 1,10);     //流水号
//        alert(fm.SerialNo4.value);
    fm.custNo.value = MedFeeOperGrid.getRowColData(tNo - 1,11);        //客户号

}

/*[其他信息MulLine]的触发函数*/
function getMedFeeOtherGrid()
{
    //得到MulLine行
    var tNo = MedFeeOtherGrid.getSelNo();

    //赋值其他信息
    fm.FactorType.value = MedFeeOtherGrid.getRowColData(tNo - 1,1);      //
    fm.FactorCode.value = MedFeeOtherGrid.getRowColData(tNo - 1,2);      //
    fm.FactorName.value = MedFeeOtherGrid.getRowColData(tNo - 1,3);      //
    fm.FactorValue.value = MedFeeOtherGrid.getRowColData(tNo - 1,4);
    
    fm.FactorUnitName.value = MedFeeOtherGrid.getRowColData(tNo - 1,9);
    showOneCodeName('llfactype','FactorType','FactorTypeName');
    fm.FactorStateDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,10);
    fm.FactorEndDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,11);
    
    //赋值关键信息
    fm.claimNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,5);       //赔案号
    fm.caseNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,6);        //分案号
    fm.SerialNo5.value = MedFeeOtherGrid.getRowColData(tNo - 1,7);     //流水号
    fm.custNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,8);        //客户号

}

/*[社保第三方给付MulLine]的触发函数*/
function getMedFeeThreeGrid()
{
    //得到MulLine行
    var tNo = MedFeeThreeGrid.getSelNo();

    //赋值其他信息
    fm.FeeThreeType.value = MedFeeThreeGrid.getRowColData(tNo - 1,1);      //
    fm.FeeThreeCode.value = MedFeeThreeGrid.getRowColData(tNo - 1,2);      //
    fm.FeeThreeName.value = MedFeeThreeGrid.getRowColData(tNo - 1,3);      //
    fm.FeeThreeValue.value = MedFeeThreeGrid.getRowColData(tNo - 1,4);
    
    fm.FeeThreeUnitName.value = MedFeeThreeGrid.getRowColData(tNo - 1,9);
    fm.AdjRemark.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    showOneCodeName('llfactype','FeeThreeType','FeeThreeTypeName');
//    fm.FeeThreeStateDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    fm.FeeThreeEndDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
    
    //赋值关键信息
    fm.claimNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,5);       //赔案号
    fm.caseNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,6);        //分案号
    fm.SerialNo5.value = MedFeeThreeGrid.getRowColData(tNo - 1,7);     //流水号
    fm.custNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,8);        //客户号

}

/*提交后台前的页面数据校验*/
function beforeInHosSubmit()
{
    if( verifyInput() == false ) return false;
    if ( document.all("InHosHosID").value == null || trim(document.all("InHosHosID").value) ==''){
         alert('[医院代码]不能为空！');
         fm.InHosHosID.focus();
         return false;
    }else if( document.all("InHosHosName").value == null || trim(document.all("InHosHosName").value) == '' ){
         alert('[医院名称]不能为空！');
         fm.InHosHosName.focus();
         return false;
    }else if( document.all("InHosHosGrade").value == null || trim(document.all("InHosHosGrade").value) == '' ){
         alert('[医院等级]不能为空！');
         fm.InHosHosGrade.focus();
         return false;
    }else if( document.all("InHosStartDate").value == null || trim(document.all("InHosStartDate").value) == '' ){
         alert('[开始日期]不能为空！');
         fm.InHosStartDate.focus();
         return false;
    }else if( document.all("InHosEndDate").value == null || trim(document.all("InHosEndDate").value) == '' ){
         alert('[结束日期]不能为空！');
         fm.UInHosEndDate.focus();
         return false;
    }else if( document.all("InHosMedFeeType").value == null || trim(document.all("InHosMedFeeType").value) == '' ){
         alert('[费用代码]不能为空！');
         fm.InHosMedFeeType.focus();
         return false;
    }else if( document.all("InHosMedFeeTypeName").value == null || trim(document.all("InHosMedFeeTypeName").value) == '' ){
         alert('[费用名称]不能为空！');
         fm.InHosMedFeeTypeName.focus();
         return false;
    }else if( document.all("InHosFeeSum").value == null || trim(document.all("InHosFeeSum").value) == '' ){
         alert('[费用金额]不能为空！');
         fm.InHosFeeSum.focus();
         return false;
    }
    return true;
}

//日期计算
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
        alert("单证结束日期不能早于开始日期!");
        return false;
    }
//    if (date2 < 0)
//    {
//        alert("单证结束日期不能早于意外事故发生日期!");
//        return false;
//    }
//    if (date3 < 0)
//    {
//        alert("单证结束日期不能早于出险日期!");
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
//    //门诊
//    if(hosType == 'C')
//    {
//        //实际门诊天数
//        fm.ClinicDayCount1.value = date1;
//        fm.ClinicDayCount2.value = date2;
//        fm.ClinicDayCount3.value = date3;
//    }
//    //住院
//    if(hosType == 'H')
//    {
//        //实际住院天数
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
    alert("请先输入原始费用！");
    return false;
  }
  
  if(isNumeric(fm.OriginFee.value)==false)
  {
    alert("请输入正确的原始费用！");
    fm.MinusFee.value="";
    fm.OriginFee.value=="";
    return false;
  }
  
  
  if(isNumeric(fm.MinusFee.value)==false)
  {
    alert("请输入正确的扣除费用！");
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
    alert("请先输入原始费用！");
    return false;
  }
  
  if(isNumeric(fm.OriginFee1.value)==false)
  {
    alert("请输入正确的原始费用！");
    fm.MinusFee.value="";
    fm.OriginFee.value=="";
    return false;
  }
  
  
  if(isNumeric(fm.MinusFee1.value)==false)
  {
    alert("请输入正确的扣除费用！");
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

//校验日期格式
function checkapplydate(){
  if(fm.ClinicStartDate.value.length==8){
  if(fm.ClinicStartDate.value.indexOf('-')==-1){ 
    var Year =     fm.ClinicStartDate.value.substring(0,4);
    var Month =    fm.ClinicStartDate.value.substring(4,6);
    var Day =      fm.ClinicStartDate.value.substring(6,8);
    fm.ClinicStartDate.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
        alert("您输入的日期有误!");
        fm.ClinicStartDate.value = ""; 
        return;
      }
  }
} else {var Year =     fm.ClinicStartDate.value.substring(0,4);
      var Month =    fm.ClinicStartDate.value.substring(5,7);
      var Day =      fm.ClinicStartDate.value.substring(8,10);
      if(Year=="0000"||Month=="00"||Day=="00"){
        alert("您输入的日期有误!");
        fm.ClinicStartDate.value = "";
        return;
         }
  }
}