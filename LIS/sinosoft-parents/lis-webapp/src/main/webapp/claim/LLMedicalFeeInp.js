var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
var checkAccDate;//参与校验的出险日期,如果两个出险日期都存在，则取最早的那个日期
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
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
	//门诊
    if (fm.MainFeeType.value == 'A')
	  {
        divMedFee1.style.display="";
        queryGrid1();
    }
    //住院
    else if (fm.MainFeeType.value == 'B')
    {
        divMedFee2.style.display="";
        queryGrid2();
    }
    //费用补偿
    else if (fm.MainFeeType.value == 'C')
    {
        divMedFee7.style.display="";
        queryGrid7();
    }
    //比例给付(原伤残)
    else if (fm.MainFeeType.value == 'L')
    {
        divMedFee3.style.display="";
        queryGrid3();
    }
    //其他
    else if (fm.MainFeeType.value == 'T')
    {
        divMedFee5.style.display="";
        queryGrid5();
    }   
    //社保第三方给付
    else if (fm.MainFeeType.value == 'D')
    {
        divMedFee6.style.display="";
        queryGrid6();
    }
    //MMA医疗账单信息录入
    else if (fm.MainFeeType.value == 'H')
    {
        divMedFee8.style.display="";
        queryGrid8();
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
    
    //2008-12-8 zhangzheng 由于MS没有这种产品和责任,所以屏蔽特定手术、特种疾病与特定给付录入项
    //divMedFee4.style.display="";
    //queryGrid4();
    
    divMedFee5.style.display="";
    queryGrid5();
    divMedFee6.style.display="";
    queryGrid6();
    divMedFee8.style.display="";
    queryGrid8();
    //alert("fm.GrpFlag.value:"+fm.GrpFlag.value);
    if(fm.GrpFlag.value!=1){//alert(77);
	    divMedFee7.style.display="";
	    queryGrid7();
    }
}

//隐藏所有账单
function showNone()
{
    fm.MainFeeType.value = "";
    fm.MainFeeName.value = "";
    divMedFee1.style.display="none";
    divMedFee2.style.display="none";
    divMedFee3.style.display="none";
    //divMedFee4.style.display="none";
    divMedFee5.style.display="none";
    divMedFee6.style.display="none";
    divMedFee7.style.display="none";
}

//区分手术类型
function setOperType()
{
	//特定手术
    if (fm.OperationType.value == 'D')
    {
        fm.llOperType.value = 'lloperationtype';
    }
    //特种疾病
    if (fm.OperationType.value == 'E')
    {
        fm.llOperType.value = 'lldiseasetype';
    }
    //特定给付
    if (fm.OperationType.value == 'F')
    {
        fm.llOperType.value = 'llspegivetype';
    }
}

//区分要素类型
function setFactorType()
{
    if (fm.FactorType.value == 'TP')
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
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15------------16--------------------------17
   /* var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, '',a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,"
    	       + " b.SelfAmnt, (select (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end)  from ldperson k where k.customerno=d.customerno) as 社保标志"
    	       + " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) 距离事故日期,"
    	       + " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
               + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b ,llcase d ,llregister e where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno   and a.customerno = b.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
               + " and a.customerno = b.customerno"
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'A'"
               + " order by a.MainFeeNo";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql1");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);         
//    prompt("门诊信息查询sql",strSql);
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
    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, b.SelfAmnt,a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo"
    			+ " , (select (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end)  from ldperson k where k.customerno=d.customerno) as 社保标志"
    			+ " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) 距离事故日期 ,"
    			+ " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
    			+ " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b ,llcase d ,llregister e  where 1=1 "
                + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno  and a.customerno = b.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
                + " and a.HospitalCode = c.HospitalCode "
                + " and a.ClmNo='" + tclaimNo + "'"
                + " and b.FeeItemType = 'B'"
                + " order by a.MainFeeNo";*/
    		mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql2");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);   
    //prompt("住院信息查询sql",strSql);
    
    //显示所有数据
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
    //-----------------------1---------2---------3-------4-----5----------6--------------7------------8-------9----10-------11---------12-----13
   /* var strSql = " select defotype,defograde,DefoName,defocode,DefoCodeName,deformityrate,appdeformityrate,realrate,clmno,caseno,serialno,customerno,JudgeOrganName,JudgeDate,adjremark"
               + " from LLCaseInfo where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " order by serialno";*/
       		mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql3");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);  
    //显示所有数据
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
    //-------------------------1--------------2-------------3----------4------5-------6------7----8-----9-----10----------11
   /* var strSql = " select operationtype,operationcode,operationname,oplevel,opgrade,opfee,SelfAmnt,mainop,clmno,caseno,serialno,customerno,UnitName,DiagnoseDate"
               + " from LLOperation where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " order by serialno";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql9");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
		
    //prompt("手术信息查询",strSql);
    //显示所有数据
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
    //------------------------1-----------2--------3----------4----------5------6------7----------8
    /*var strSql = " select factortype,factorcode,factorname,factorvalue,SelfAmnt,clmno,caseno,serialno,customerno,UnitName,StartDate,EndDate,"
    		   + " (select codename from ldcode where codetype='deductreason' and code=adjreason),adjreason,adjremark"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " and FeeItemType = 'T'"
               + " order by serialno";*/
             mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql4");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
    //prompt("其他信息查询",strSql);
    //显示所有数据
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
    //------------------------1-----------2--------3----------4----------5------6------7----------8
    /*var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,AdjRemark"
               + " from LLOtherFactor where "
               + " ClmNo='" + tclaimNo + "'"
               + " and CustomerNo='"+document.all('custNo').value+"'"
               + " and FeeItemType = 'D'"
               + " order by serialno";*/
                 mySql = new SqlClass();
		mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql5");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeThreeGrid);
    }
}


//费用补偿项目信息查询
function queryGrid7()
{
    var tclaimNo = fm.claimNo.value;

    /*var strSql = " select a.HospitalCode,c.HospitalName,c.HosAtti,b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, '',"
    	       + " a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,"
    	       + " b.SelfAmnt, (select (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end)  from ldperson k where k.customerno=d.customerno) as 社保标志"
   			   + " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) 距离事故日期,"
   			   + " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
   			   + " from LLFeeMain a,LLCommendHospital c ,LLCaseReceipt b ,llcase d ,llregister e  where 1=1 "
               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
               + " and a.HospitalCode = c.HospitalCode "
               + " and a.ClmNo='" + tclaimNo + "'"
               + " and b.FeeItemType = 'C'"
               + " order by a.MainFeeNo";*/
       mySql = new SqlClass();        
    	mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql6");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);
    //prompt("费用补偿项目信息查询",strSql)
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,MedFeeCompensateInpGrid);
    }
}

//MMA理赔账单查询
function queryGrid8()
{
    var tclaimNo = fm.claimNo.value;

//    var strSql = " select b.StartDate,b.EndDate,b.DayCount,b.FeeItemCode,b.FeeItemName,b.adjsum, '',"
//    	       + " a.ClmNo,a.CaseNo,a.CustomerNo,a.MainFeeNo,b.FeeDetailNo,"
//    	       + " b.SelfAmnt, (select (case when trim((case when SocialInsuFlag is not null then SocialInsuFlag else '0' end)) = '1' then '有' else '无' end)  from ldperson k where k.customerno=d.customerno) as 社保标志"   
//    	       + " ,b.dealflag ,(round(to_number(enddate-e.accidentdate))+1) 距离事故日期,"  
//    	       + " (select codename from ldcode where codetype='deductreason' and code=b.adjreason),b.adjreason,b.adjremark"
//   			   + " from LLFeeMain a,LLCaseReceipt b ,llcase d ,llregister e  where 1=1 "
//               + " and a.ClmNo=b.ClmNo and a.CaseNo=b.CaseNo and a.caseno = d.caseno and a.clmno = e.rgtno and a.MainFeeNo=b.MainFeeNo and a.customerno = d.customerno and a.CustomerNo='"+document.all('custNo').value+"'"
//               + " and a.ClmNo='" + tclaimNo + "'"
//               + " and b.FeeItemType = 'H'"
//               + " order by a.MainFeeNo";
   /* mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql6");
		mySql.addSubPara(document.all('custNo').value ); 
		mySql.addSubPara(tclaimNo);*/
    //prompt("费用补偿项目信息查询",strSql)
    //显示所有数据
    
    
    sql_id = "LLMedicalFeeInpSql10";
    my_sql = new SqlClass();
    my_sql.setResourceName("claim.LLMedicalFeeInpInputSql"); //指定使用的properties文件名
    my_sql.setSqlId(sqlid2);//指定使用的Sql的id
    my_sql.addSubPara(document.all('custNo').value);//指定传入的参数
    my_sql.addSubPara(tclaimNo);//指定传入的参数
    str_sql = my_sql.getString();
    
    var arr = easyExecSql(str_sql);
    if (arr)
    {
        displayMultiline(arr,MedFeeHospitalGrid);
    }
}


//查询伤残赔付比例
function queryDeformityRate()
{
    if(fm.DefoCode.value != "" && fm.DefoCode.value !=null)
    {
       /* var strSql = " select t.deforate from LLParaDeformity t where "
                   + " t.defocode='" + fm.DefoCode.value + "'";*/
        mySql = new SqlClass();
        mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql7");
		mySql.addSubPara(fm.DefoCode.value); 

        //显示所有数据
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
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.hideOperate.value=mOperate;
    document.getElementById("fm").submit(); //提交
}

/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
            fm.ClinicMedFeeSum.value = "";//费用金额
            fm.selfMedFeeSum.value = "";//自费/自付项目金额
            //fm.ClinicStartDate.value = "";
            //fm.ClinicEndDate.value = "";
            fm.ClinicDayCount1.value = "";//天数
            fm.ClinicDayCount2.value = "";//距离事故日期天数
            //fm.ClinicDayCount3.value = "";
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
            fm.InHosMedFeeSum.value = "";
            fm.selfInHosFeeSum.value = "";//自费/自付项目金额
            //fm.InHosStartDate.value = "";
            //fm.InHosEndDate.value = "";
            fm.InHosDayCount1.value = "";
            fm.InHosDayCount2.value = "";
            //fm.InHosDayCount3.value = "";
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
            fm.OpFee.value = "";//费用金额
            fm.selfOpFeeSum.value = "";
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
            fm.selfFactorFeeSum.value = "";
            //fm.FactorStateDate.value = "";
            //fm.FactorEndDate.value = "";
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
        
        if ( fm.currentInput.value=="7")//费用补偿项目
        {
        	initMedFeeCompensateInpGrid(); 
            queryGrid7();
            //清空表单
//            fm.HosMainFeeNo.value = "";
//            fm.InHosHosID.value = "";
//            fm.InHosHosName.value = "";
            fm.MedFeeCompensateType.value = "";
            fm.MedFeeCompensateTypeName.value = "";
            fm.MedFeeCompensateSum.value = "";
            fm.selfMedFeeCompensateFeeSum.value = "";//自费/自付金额
            //fm.InHosStartDate.value = "";
            //fm.InHosEndDate.value = "";
            fm.MedFeeCompensateEndDateInHosDayCount1.value = "";
            fm.MedFeeCompensateEndDateInHosDayCount2.value = "";
            //fm.MedFeeCompensateEndDateInHosDayCount3.value = "";
        }
        if ( fm.currentInput.value=="8")//MMA
        {
        	  initMedFeeHospitalGrid();
            queryGrid8()
            //清空表单
            fm.HospitalFeeNo.value = "";
            fm.ClinicMedFeeType8.value = "";
            fm.ClinicMedFeeType8Name.value = "";
            fm.ClinicStartDate8.value = "";
            fm.ClinicEndDate8.value = "";
            fm.HospMedFeeSum.value = "";
            fm.ClinicDayCount8.value = "";
            fm.Remark8.value = "";
        }
    }
}


/*
 * 日期校验,校验两个日期都不能晚于当前日期，且第2个日期不能早于第1个日期
 * Date1,传入的第一个日期,这里是开始日期
 * Date2 传入的第二个日期,这里是结束日期
 * tDateName 传入的组件名称(如门诊,住院等)，用于组成弹出的提示语言
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var tStartDate  =  tDate1;//事故日期
    var tEndDate =  tDate2;//出险日期日期

       
    //检查开始日期
    if (tStartDate == null || tStartDate == '')
    {
        alert(tDateName+"开始日期不能为空!");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,tStartDate,'D') > 0)
        {
      		alert(tDateName+"开始日期不能晚于当前日期!");
            return false; 
        }
    }
    

    //校验结束日期
    if (tEndDate == null || tEndDate == '')
    {
        alert(tDateName+"结束日期不能为空!");
        return false;
    }
    else
    {//alert("mCurrentDate:"+mCurrentDate);
    //alert("tEndDate:"+tEndDate);
    	
       	//比较结束日期和当前日期
    	if (dateDiff(mCurrentDate,tEndDate,'D') > 0)
        {
        	alert(tDateName+"结束日期不能晚于当前日期!");
            return false; 
        }

        //比较出险日期和单证开始日期
    	//alert(dateDiff(tStartDate,tEndDate,'D'));
    	if (dateDiff(tStartDate,tEndDate,'D') < 0)
        {
        	alert(tDateName+"开始日期不能大于结束日期!");
            return false; 
        }
    }
    
    
    //校验开始日期和出险日期
    if(dateDiff(checkAccDate,fm.ClinicStartDate.value,'D')<0)
    {
        if(confirm(tDateName+"开始日期早于出险日期,是否继续!"))
        {
            fm.DealFlag.value = "0";
        }
        else
        {
            return false;
        }
    }
    else
    {
        fm.DealFlag.value = "1";
    }
    
    return true;
}


/*门诊[增加]按钮对应操作*/
function AddClick1()
{
    //非空检验
    if (fm.ClinicMainFeeNo.value == null || fm.ClinicMainFeeNo.value == "")
    {
        alert("账单号不能为空！");
        return;
    }
    if (fm.ClinicHosID.value == null || fm.ClinicHosID.value == "")
    {
        alert("医院名称不能为空！");
        return;
    }    
    if (fm.ClinicMedFeeType.value == null || fm.ClinicMedFeeType.value == "")
    {
        alert("费用类型不能为空！");
        return;
    }
    
	//日期校验
	if (checkDate(fm.ClinicStartDate.value,fm.ClinicEndDate.value,"门诊") == false)
	{
	     return false;
	}    
    
    if (fm.ClinicMedFeeSum.value == null || fm.ClinicMedFeeSum.value == "")
    {
        alert("费用金额不能为空！");
        return;
    }
    
    if (fm.MedCurrency.value == null || fm.MedCurrency.value == "")
    {
        alert("币种不能为空！");
        return;
    }
    
    
    if (fm.selfMedFeeSum.value == null || fm.selfMedFeeSum.value == "")
    {
        alert("自费/自付金额不能为空！");
        return;
    }

    if(fm.selfMedFeeSum.value!=0){
	    if (fm.theReason1.value == null || fm.theReason1.value == "")
	    {
	    	alert("扣减原因不能为空！");
	    	return;
	    }
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "1";
    fm.action = "./LLMedicalFeeInp1Save.jsp";
    submitForm();
    
    //wyc add
    initClick1();
    
}

/*住院[增加]按钮对应操作*/
function AddClick2()
{

    //非空检验
    if (fm.HosMainFeeNo.value ==  null || fm.HosMainFeeNo.value == "")
    {
        alert("账单号不能为空！");
        return;
    }
    if (fm.InHosHosID.value ==  null || fm.InHosHosID.value == "")
    {
        alert("医院名称不能为空！");
        return;
    }    
    if (fm.InHosMedFeeType.value ==  null || fm.InHosMedFeeType.value == "")
    {
        alert("费用类型不能为空！");
        return;
    }
    
	//日期校验
	if (checkDate(fm.InHosStartDate.value,fm.InHosEndDate.value,"住院") == false)
	{
	     //return false;
	}    
    
    if (fm.InHosMedFeeSum.value == null || fm.InHosMedFeeSum.value == "")
    {
        alert("费用金额不能为空！");
        return;
    }
    
    if (fm.InHosMedCurrency.value == null || fm.InHosMedCurrency.value == "")
    {
        alert("币种不能为空！");
        return;
    }
    
    if (fm.selfInHosFeeSum.value == null || fm.selfInHosFeeSum.value == "")
    {
        alert("自费/自付金额不能为空！");
        return;
    }
    if(fm.selfInHosFeeSum.value != "0"){
	    if (fm.theReason2.value == null || fm.theReason2.value == "")
	    {
	        alert("扣减原因不能为空！");
	        return;
	    }
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "2";
    fm.action = "./LLMedicalFeeInp2Save.jsp";
    submitForm();
    
    // add wyc 
    initClick2();
}

/*比例给付(原伤残)[增加]按钮对应操作*/
function AddClick3()
{
    //非空检验
    if (fm.DefoType.value ==  null ||fm.DefoType.value == "")
    {
        alert("请输入比例给付类型！");
        return;
    }
    if ((fm.DefoGrade.value == "")&&((fm.DefoType.value=='LF')||(fm.DefoType.value=='LZ')))
    {
        alert("请输入比例给付级别！");
        return;
    }
    if (fm.DefoCode.value == "")
    {
        alert("请输入比例给付代码！");
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
    
    //add wyc
    fm.Remark9.value = "";
}


/*手术[增加]按钮对应操作*/
function AddClick4()
{
    //非空检验
    if (fm.OperationType.value == null || fm.OperationType.value == "")
    {
        alert("费用类型不能为空！");
        return;
    }    
    if (fm.OperationCode.value == null || fm.OperationCode.value == "")
    {
        alert("费用类型代码不能为空！");
        return;
    }
    if (fm.UnitName.value == null || fm.UnitName.value == "")
    {
        alert("医疗机构名称不能为空！");
        return;
    }
    
    if (fm.DiagnoseDate.value == null || fm.DiagnoseDate.value == "")
    {
        alert("确诊日期不能为空！");
        return;
    }    
    
    if (dateDiff(fm.DiagnoseDate.value,mCurrentDate,'D') < 0)
    {
        alert("确诊日期不能大于当前日期!");
        return;
    }
    
    if (fm.OpCurrency.value == null || fm.OpCurrency.value == "")
    {
        alert("币种不能为空！");
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
    if (fm.FactorType.value == null || fm.FactorType.value == "")
    {
        alert("特种费用类型不能为空！");
        return;
    }    
    if (fm.FactorCode.value == null || fm.FactorCode.value == "")
    {
        alert("特种费用代码不能为空！");
        return;
    }

    if (fm.FactorValue.value == null || fm.FactorValue.value == "")
    {
        alert("特种费用金额不能为空！");
        return;
    }
    
    if (fm.FactorCurrency.value == null || fm.FactorCurrency.value == "")
    {
        alert("币种不能为空！");
        return;
    }
    
    if (fm.selfFactorFeeSum.value == null || fm.selfFactorFeeSum.value == "")
    {
        alert("自费/自付金额不能为空！");
        return;
    }
    if(fm.selfFactorFeeSum.value != "0"){
	    if (fm.theReason4.value == null || fm.theReason4.value == "")
	    {
	    	alert("扣减原因不能为空！");
	    	return;
	    }
    }
 
	//日期校验
	if (checkDate(fm.FactorStateDate.value,fm.FactorEndDate.value,"特种费用") == false)
	{
	     return false;
	}    
    
    
    if (fm.FactorUnitName.value == null || fm.FactorUnitName.value == "")
    {
        alert("服务机构名称不能为空！");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "5";
    fm.action = "./LLMedicalFeeInp5Save.jsp";
    submitForm();
    
    //add wyc
    initClick5();
}

/*社保第三方给付[增加]按钮对应操作*/
function AddClick6()
{
	//非空检验
    if (fm.FeeThreeType.value == "" || fm.FeeThreeType.value == null)
    {
        alert("请输入费用类型代码！");
        return;
    }
    if (fm.FeeThreeTypeName.value == "" || fm.FeeThreeTypeName.value == null)
    {
        alert("请输入费用类型名称！");
        return;
    }
    if (fm.FeeThreeCode.value == "" || fm.FeeThreeCode.value == null)
    {
        alert("请输入费用代码！");
        return;
    }
    if (fm.FeeThreeUnitName.value == "" || fm.FeeThreeUnitName.value == null)
    {
        alert("请输入服务机构名称！");
        return;
    }

    if (fm.FeeThreeCurrency.value == null || fm.FeeThreeCurrency.value == "")
    {
        alert("币种不能为空！");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "6";
    fm.action = "./LLMedicalFeeInp6Save.jsp";
    submitForm();
    
    //add wyc
    initClick6();
}


/*费用补偿项目[增加]按钮对应操作*/
function AddClick7()
{
    //非空检验
    if (fm.CompensateMainFeeNo.value == null || fm.CompensateMainFeeNo.value == "")
    {
        alert("账单号不能为空！");
        return;
    }
    if (fm.MedFeeCompensateHosID.value == null || fm.MedFeeCompensateHosID.value == "")
    {
        alert("医院名称不能为空！");
        return;
    }    
    if (fm.MedFeeCompensateType.value == null || fm.MedFeeCompensateType.value == "")
    {
        alert("费用类型不能为空！");
        return;
    }
    
    //日期校验
	if (checkDate(fm.MedFeeCompensateStartDate.value,fm.MedFeeCompensateEndDate.value,"费用补偿") == false)
	{
	     return false;
	}    
    
    if (fm.MedFeeCompensateSum.value == null || fm.MedFeeCompensateSum.value == "")
    {
        alert("费用金额不能为空！");
        return;
    }
    
    if (fm.MedFeeCompensateCurrency.value == null || fm.MedFeeCompensateCurrency.value == "")
    {
        alert("币种不能为空！");
        return;
    }
    
    if (fm.selfMedFeeCompensateFeeSum.value == null || fm.selfMedFeeCompensateFeeSum.value == "")
    {
        alert("自费/自付金额不能为空！");
        return;
    }
    if(fm.selfMedFeeCompensateFeeSum.value != "0"){
	    if (fm.theReason3.value == null || fm.theReason3.value == "")
	    {
	    	alert("扣减原因不能为空！");
	    	return;
	    }
    }
 
    mOperate="INSERT";
    fm.currentInput.value = "7";
    fm.action = "./LLMedicalFeeInp7Save.jsp";
    submitForm();
    
    //add wyc
    initClick7();
}

/*MMA理赔账单录入*/
function AddClick8()
{
    //非空校验
    if (fm.HospitalFeeNo.value == null || fm.HospitalFeeNo.value == "")
    {
        alert("账单号不能为空");
        return;
    }
    if (fm.ClinicMedFeeType8.value == null || fm.ClinicMedFeeType8.value == "")
    {
        alert("费用类型不能为空");
        return;
    }    
    
	//日期校验
	if (checkDate(fm.ClinicStartDate8.value,fm.ClinicEndDate8.value,"MmaKind") == false)
	{
	     //return false;
	}    
    
    if (fm.HospMedFeeSum.value == null || fm.HospMedFeeSum.value == "")
    {
        alert("费用金额不能为空");
        return;
    }
    
    mOperate="INSERT";
    fm.currentInput.value = "8";
    fm.action = "./LLMedicalFeeInp8Save.jsp";
    submitForm();
}


/*门诊[删除]按钮对应操作*/
function DeleteClick1()
{
    if(MedFeeClinicInpGrid.getSelNo() > 0)
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
    
    // wyc add
    initClick1();
}

/*住院[删除]按钮对应操作*/
function DeleteClick2()
{
    if(MedFeeInHosInpGrid.getSelNo() > 0)
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
    
    //add wyc 
    initClick2();
}


/*伤残[删除]按钮对应操作*/
function DeleteClick3()
{
    if(MedFeeCaseInfoGrid.getSelNo() > 0)
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
    
  //add wyc
    fm.Remark9.value = "";
    
}

/*手术[删除]按钮对应操作*/
function DeleteClick4()
{
    if(MedFeeOperGrid.getSelNo() > 0)
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
    if(MedFeeOtherGrid.getSelNo() > 0)
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
  //add wyc
    initClick5();
}

/*社保第三方给付[删除]按钮对应操作*/
function DeleteClick6()
{
    if(MedFeeThreeGrid.getSelNo() > 0)
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
    
  //add wyc
    initClick6();
}


/*费用补偿项目[删除]按钮对应操作*/
function DeleteClick7()
{
    if(MedFeeCompensateInpGrid.getSelNo() > 0)
    {
        if (confirm("您确实想删除该记录吗?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "7";
            fm.action = "./LLMedicalFeeInp7Save.jsp";
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
    
    //add wyc 
    initClick7();
    
}

/*MMA理赔账单删除*/
function DeleteClick8()
{
    if(MedFeeHospitalGrid.getSelNo() > 0)
    {
        if (confirm("您确实想删除该记录吗?"))
        {
            mOperate="DELETE";
            fm.currentInput.value = "8";
            fm.action = "./LLMedicalFeeInp8Save.jsp";
            submitForm();
        }
        else
        {
            mOperate="";
        }
    }
    else
    {
        alert("请选中一条记录");
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
    fm.MedCurrency.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,9);
    fm.ClinicMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,10);//费用金额

    //赋值关键信息
    fm.claimNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,12);       //赔案号
    fm.caseNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,13);        //分案号
    fm.custNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,14);        //客户号
    fm.ClinicMainFeeNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,15);     //帐单号
    fm.feeDetailNo.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,16);   //帐单明细号
    
    fm.selfMedFeeSum.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,17); //自费/自付金额
    //alert("自费/自付金额:"+fm.selfMedFeeSum.value);
    
    fm.DealFlag.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,19); //处理标记:1-正常费用,0-出险前费用
    //alert("费用处理标记:"+fm.DealFlag.value);
    
    fm.ClinicDayCount2.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,20); //距离事故日期天数
    
    fm.theReason1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,22);
    fm.theReasonName1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,21);
    fm.Remark1.value = MedFeeClinicInpGrid.getRowColData(tNo - 1,23);
    //该条记录有特殊标志，提示后续处理人员注意
    if (fm.DealFlag.value == "0")
    {
        alert("该账单开始日期早于出险日期,请注意!");
    }

    
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
    
    fm.InHosMedCurrency.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);
    fm.InHosMedFeeSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,10); //费用金额
    fm.selfInHosFeeSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11); //自费/自付金额  
    
    //赋值关键信息
    fm.claimNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);       //赔案号
    fm.caseNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);        //分案号
    fm.custNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,14);        //客户号
    
    fm.HosMainFeeNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);     //帐单号
    fm.feeDetailNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16);   //帐单明细号
       
    fm.DealFlag.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,18); //处理标记:1-正常费用,0-出险前费用
    fm.InHosDayCount2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,19); //距离事故日期天数
    
    fm.theReason2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21); 
    fm.theReasonName2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,20); 
    fm.Remark2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,22); 
    
    //该条记录有特殊标志，提示后续处理人员注意
    if (fm.DealFlag.value == "0")
    {
        alert("该账单开始日期早于出险日期,请注意!");
    }
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
    fm.DefoCodeName.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,5);
    
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
    
    fm.Remark9.value = MedFeeCaseInfoGrid.getRowColData(tNo - 1,15);        //客户号

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
    
    fm.OpCurrency.value = MedFeeOperGrid.getRowColData(tNo - 1,6);
    fm.OpFee.value = MedFeeOperGrid.getRowColData(tNo - 1,7);
    fm.selfOpFeeSum.value = MedFeeOperGrid.getRowColData(tNo - 1,8);

    fm.UnitName.value = MedFeeOperGrid.getRowColData(tNo - 1,14);
    //alert("机构名称:"+fm.UnitName.value);
    
    fm.DiagnoseDate.value = MedFeeOperGrid.getRowColData(tNo - 1,15);
    //alert("确认日期:"+fm.DiagnoseDate.value);
    
    //赋值关键信息
    fm.claimNo.value = MedFeeOperGrid.getRowColData(tNo - 1,10);       //赔案号
    fm.caseNo.value = MedFeeOperGrid.getRowColData(tNo - 1,11);        //分案号
    fm.SerialNo4.value = MedFeeOperGrid.getRowColData(tNo - 1,12);     //流水号
    //alert("手术序号:"+fm.SerialNo4.value);
    
    fm.custNo.value = MedFeeOperGrid.getRowColData(tNo - 1,13);        //客户号
    //alert("客户号:"+fm.custNo.value);

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
    fm.FactorCurrency.value = MedFeeOtherGrid.getRowColData(tNo - 1,4);
    fm.FactorValue.value = MedFeeOtherGrid.getRowColData(tNo - 1,5);//费用金额
    fm.selfFactorFeeSum.value = MedFeeOtherGrid.getRowColData(tNo - 1,6);//自费/自付金额
    //alert("fm.selfFactorFeeSum.value"+fm.selfFactorFeeSum.value);
    
    //赋值关键信息
    fm.claimNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,7);       //赔案号
    fm.caseNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,8);        //分案号
    //alert("fm.caseNo.value"+fm.caseNo.value);
    fm.SerialNo5.value = MedFeeOtherGrid.getRowColData(tNo - 1,9);     //流水号
    fm.custNo.value = MedFeeOtherGrid.getRowColData(tNo - 1,10);        //客户号
    //alert("fm.custNo.value"+fm.custNo.value);
    
    
    fm.FactorUnitName.value = MedFeeOtherGrid.getRowColData(tNo - 1,11);
    showOneCodeName('llfactype','FactorType','FactorTypeName');
    fm.FactorStateDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,12);
    fm.FactorEndDate.value = MedFeeOtherGrid.getRowColData(tNo - 1,13);

    fm.theReason4.value = MedFeeOtherGrid.getRowColData(tNo - 1,15);
    fm.theReasonName4.value = MedFeeOtherGrid.getRowColData(tNo - 1,14);
    fm.Remark4.value = MedFeeOtherGrid.getRowColData(tNo - 1,16);
    //alert("fm.FactorEndDate.value"+fm.FactorEndDate.value);
    


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
    fm.FeeThreeCurrency.value = MedFeeThreeGrid.getRowColData(tNo - 1,4);
    fm.FeeThreeValue.value = MedFeeThreeGrid.getRowColData(tNo - 1,5);
    
    fm.FeeThreeUnitName.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
    fm.AdjRemark.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
//    showOneCodeName('llfactype','FeeThreeType','FeeThreeTypeName');
//    fm.FeeThreeStateDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,10);
//    fm.FeeThreeEndDate.value = MedFeeThreeGrid.getRowColData(tNo - 1,11);
    
    //赋值关键信息
    fm.claimNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,6);       //赔案号
    fm.caseNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,7);        //分案号
    fm.SerialNo5.value = MedFeeThreeGrid.getRowColData(tNo - 1,8);     //流水号
    fm.custNo.value = MedFeeThreeGrid.getRowColData(tNo - 1,9);        //客户号

}


/*[费用补偿项目MulLine]的触发函数*/
function getMedFeeCompensateInpGrid()
{
    //得到MulLine行
    var tNo = MedFeeCompensateInpGrid.getSelNo();

    //赋值医院信息
    fm.MedFeeCompensateHosID.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,1);             //医院编号
    fm.MedFeeCompensateHosName.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,2);            //医院名称
    fm.InHosHosGrade.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,3);           //医院等级
    
    //赋值日期信息
    fm.MedFeeCompensateStartDate.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,4);
    fm.MedFeeCompensateEndDate.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,5);
    fm.MedFeeCompensateEndDateInHosDayCount1.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,6);

    //赋值费用信息
    fm.MedFeeCompensateType.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,7);
    fm.MedFeeCompensateTypeName.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,8);
    fm.MedFeeCompensateCurrency.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,9);
    fm.MedFeeCompensateSum.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,10);

    
    //赋值关键信息
    fm.claimNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,12);       //赔案号
    fm.caseNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,13);        //分案号
    fm.custNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,14);        //客户号
    fm.CompensateMainFeeNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,15);     //帐单号
    fm.feeDetailNo.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,16);   //帐单明细号
    

    fm.selfMedFeeCompensateFeeSum.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,17); //自费/自付金额  
    fm.DealFlag.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,19); //处理标记:1-正常费用,0-出险前费用
    fm.MedFeeCompensateEndDateInHosDayCount2.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,20); //距离事故日期天数
    
    fm.theReason3.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,22); //
    fm.theReasonName3.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,21); //
    fm.Remark3.value = MedFeeCompensateInpGrid.getRowColData(tNo - 1,23); //

        //该条记录有特殊标志，提示后续处理人员注意
    if (fm.DealFlag.value == "0")
    {
        alert("该账单开始日期早于出险日期,请注意!");
    }
}

/*MMA的触发函数*/
function getMedFeeHospitalGrid()
{
    //得到MulLine行
    var tNo = MedFeeHospitalGrid.getSelNo();
    
    //赋值日期信息
    fm.ClinicStartDate8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,1);
    fm.ClinicEndDate8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,2);
    fm.ClinicDayCount8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,3);

    //赋值费用信息
    fm.ClinicMedFeeType8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,4);
    fm.ClinicMedFeeType8Name.value = MedFeeHospitalGrid.getRowColData(tNo - 1,5);

    
    //赋值关键信息
    fm.claimNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,8);       //赔案号
    fm.caseNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,9);        //分案号
    fm.custNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,10);        //客户号
    fm.HospitalFeeNo.value = MedFeeHospitalGrid.getRowColData(tNo - 1,11);     //帐单号
    

    fm.HospMedFeeSum.value = MedFeeHospitalGrid.getRowColData(tNo - 1,6); //自费/自付金额  
    fm.DealFlag.value = MedFeeHospitalGrid.getRowColData(tNo - 1,15); //处理标记:1-正常费用,0-出险前费用
    fm.ClinicDayCount8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,16); //距离事故日期天数
    fm.Remark8.value = MedFeeHospitalGrid.getRowColData(tNo - 1,19); //
    
    //该条记录有特殊标志，提示后续处理人员注意
    if (fm.DealFlag.value == "0")
    {
        alert("该账单开始日期早于出险日期,请注意!");
    }
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


//医院模糊查询
function showHospital(tCode,tName)
{
	var strUrl="LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//伤残代码查询
function showDefo(tCode,tName)
{
	var strUrl="LLColQueryDefoMain.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查询治疗医院
function queryHospitalxx(tCaseNo,tCustNo)
{
	//var strSql =" select hospitalcode from llcase where caseno ='"+tCaseNo+"' and customerno ='"+tCustNo+"'" ;
	     mySql = new SqlClass();   
	        mySql.setResourceName("claim.LLMedicalFeeInpInputSql");
		mySql.setSqlId("LLMedicalFeeInpSql8");
		mySql.addSubPara(tCaseNo); 
		mySql.addSubPara(tCustNo); 
	//prompt("医疗单证调整界面初始化查询",strSql);
	var tICDName = easyExecSql(mySql.getString());
	if (tICDName!=null && tICDName != "")
	{
		fm.ClinicHosID.value = tICDName;
		showOneCodeName('commendhospital','ClinicHosID','ClinicHosName');//门诊医院
		fm.InHosHosID.value = tICDName;
		showOneCodeName('commendhospital','InHosHosID','InHosHosName');//住院医院
		
		fm.MedFeeCompensateHosID.value = tICDName;
		showOneCodeName('commendhospital','MedFeeCompensateHosID','MedFeeCompensateHosName');//费用补偿项目医院
	}
}


//2008-10-17 zhangzheng 比例给付级别响应函数:当比例类型选择为LF:伤残,LZ:骨折时才需要选择双击本下拉框选择给付级别，其他的则不需要选择
function respondDefoGrade()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
        alert('请先选择比例给付类型!');
        return false;
	}
	//LZ;骨折,LF:伤残两种类型
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
	    return showCodeList('lldefograde',[fm.DefoGrade,fm.DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');
	}
	else
	{
		alert("比例给付类型:"+fm.DefoType.value+"-"+fm.DefoTypeName.value+"不需选择比例给付级别,请直接选择比例给付代码!");
		return false;
	}
}


//2008-10-17 zhangzheng 比例给付级别响应函数:当比例类型选择为LF:伤残,LZ:骨折时才需要选择双击本下拉框选择给付级别，其他的则直接选择具体的比例给付代码
function respondDefoCode()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
		
        alert('请先选择比例给付类型!');
        return false;
	}
	//LZ;骨折,LF:伤残两种类型
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
		if(fm.DefoGrade.value == null || trim(fm.DefoGrade.value) ==''){
			
			alert('请先选择比例给付级别!');
	        return false;
		}
		else
		{
			//同时关联比例给付类型和比例给付级别查询
			return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','300');
		}
	}
	//其他类型
	else
	{
		//只关联比例给付类型查询,比例给付级别默认为1
		return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,'1','1','300');
	}
}


//2008-10-17 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//比例给付类型
    if(cCodeName=="lldefotype"){
    	if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF'){
    		//当比例给付类型不是LZ;骨折,LF:伤残两种类型时，比例给付级别不需要录入，需要清空
    		return true;
    	}
    	else{
    		//其他类型则需要将比例给付级别清空
    		fm.DefoGrade.value='';
    		fm.DefoGradeName.value='';
    	}
        return true;
    }
    
    //币种//设置币种金额显示类型
    if(cCodeName=="currency"){
    	
    	if(fm.MedCurrency.value!=null && fm.MedCurrency.value!="")
    	{
	    	fm.ClinicMedFeeSum.moneytype=fm.MedCurrency.value;
	    	fm.selfMedFeeSum.moneytype=fm.MedCurrency.value;
    	}
    	if(fm.InHosMedCurrency.value!=null && fm.InHosMedCurrency.value!="")
    	{
    		fm.InHosMedFeeSum.moneytype=fm.InHosMedCurrency.value;
    		fm.selfInHosFeeSum.moneytype=fm.InHosMedCurrency.value;
    	}
    	if(fm.MedFeeCompensateCurrency.value!=null && fm.MedFeeCompensateCurrency.value!="")
	    {
	    	fm.MedFeeCompensateSum.moneytype=fm.MedFeeCompensateCurrency.value;
	    	fm.selfMedFeeCompensateFeeSum.moneytype=fm.MedFeeCompensateCurrency.value;
	    }
	    if(fm.OpCurrency.value!=null && fm.OpCurrency.value!="")
	    {
	    	fm.OpFee.moneytype=fm.OpCurrency.value;
	    	fm.selfOpFeeSum.moneytype=fm.OpCurrency.value;
	    }
	    if(fm.FactorCurrency.value!=null && fm.FactorCurrency.value!="")
	    {
	    	fm.FactorValue.moneytype=fm.FactorCurrency.value;
	    	fm.selfFactorFeeSum.moneytype=fm.FactorCurrency.value;
	    }
	    if(fm.FeeThreeCurrency.value!=null && fm.FeeThreeCurrency.value!="")
	    	fm.FeeThreeValue.moneytype=fm.FeeThreeCurrency.value;
    }	
	
}

// wyc add 针对点击增加，删除页面不初始化，增加初始化
function initClick1(){
	fm.ClinicMainFeeNo.value = "";  //初始化账单号
	fm.ClinicHosID.value = "";      //医院名称  
	fm.ClinicHosName.value = ""; 
	fm.ClinicMedFeeType.value = ""; //费用类型 
	fm.ClinicMedFeeTypeName.value = "";
	fm.ClinicStartDate.value = "";  //开始时间
	fm.ClinicEndDate.value = "";    //结束时间
	fm.ClinicDayCount1.value = "";  //天数
	fm.MedCurrency.value = "";      //币种 
	fm.MedCurrencyName.value = ""; 
	fm.ClinicMedFeeSum.value = "";  //费用金额
	fm.selfMedFeeSum.value = "";    //自费/自付金额
	fm.ClinicDayCount2.value = "";  //距离事故日期天数
	fm.theReason1.value = "";       //扣减原因 
	fm.theReasonName1.value = "";  
	fm.Remark1.value = "";          //备注
	
}

function initClick2(){
	fm.HosMainFeeNo.value = "";  //初始化账单号
	fm.InHosHosID.value = "";      //医院名称  
	fm.InHosHosName.value = ""; 
	fm.InHosMedFeeType.value = ""; //费用类型
	fm.InHosMedFeeTypeName.value = "";
	fm.InHosStartDate.value = "";  //开始时间
	fm.InHosEndDate.value = "";    //结束时间
	fm.InHosDayCount1.value = "";  //天数
	fm.InHosMedCurrency.value = "";      //币种 
	fm.InHosMedCurrencyName.value = ""; 
	fm.InHosMedFeeSum.value = "";  //费用金额
	fm.selfInHosFeeSum.value = "";    //自费/自付金额
	fm.InHosDayCount2.value = "";  //距离事故日期天数
	fm.theReason2.value = "";       //扣减原因 
	fm.theReasonName2.value = "";  
	fm.Remark2.value = "";          //备注
	
}

function initClick7(){
	fm.CompensateMainFeeNo.value = "";  //初始化账单号
	fm.MedFeeCompensateHosID.value = "";      //医院名称  
	fm.MedFeeCompensateHosName.value = ""; 
	fm.MedFeeCompensateType.value = ""; //费用类型
	fm.MedFeeCompensateTypeName.value = "";
	fm.MedFeeCompensateStartDate.value = "";  //开始时间
	fm.MedFeeCompensateEndDate.value = "";    //结束时间
	fm.MedFeeCompensateEndDateInHosDayCount1.value = "";  //天数
	fm.MedFeeCompensateCurrency.value = "";      //币种 
	fm.MedFeeCompensateCurrencyName.value = ""; 
	fm.MedFeeCompensateSum.value = "";  //费用金额
	fm.selfMedFeeCompensateFeeSum.value = "";    //自费/自付金额
	fm.MedFeeCompensateEndDateInHosDayCount2.value = "";  //距离事故日期天数
	fm.theReason3.value = "";       //扣减原因 
	fm.theReasonName3.value = "";  
	fm.Remark3.value = "";          //备注
}

function initClick5(){
	fm.FactorStateDate.value = "";  //起始日期
	fm.FactorEndDate.value = "";  //结束日期
	fm.theReason4.value = "";  //扣减原因
	fm.theReasonName4.value = ""; 
	fm.Remark4.value = "";          //备注
}

function initClick6(){
	fm.FeeThreeType.value = "";   //费用类型
	fm.FeeThreeTypeName.value = ""; 
	fm.AdjRemark.value = "";          //备注
}

// end 



