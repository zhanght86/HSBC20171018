var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();


//初始化数据
function initQuery()
{

	//显示区

	//********************************************Beg
	//置空相关表单
	//********************************************
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	fm.SocialInsuFlag.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.AccidentDate2.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
    
	//理赔类型置空
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //********************************************End
    
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }

    //取得数据

    //检索事件号、事故日期(一条)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/

mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql1");
mySql.addSubPara(rptNo );                 
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];
    
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion from llregister where "
                + "rgtno = '"+rptNo+"'";*/
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql2");
mySql.addSubPara(rptNo );     
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //更新页面内容
    fm.AccidentDate.value = AccNo[0][1];

    fm.occurReason.value = RptContent[0][16];
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因


    //检索分案关联出险人信息(多条)
   /* var strSQL3 = "select CustomerNo,Name," 
    	+ " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
        + " Birthday,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
        + " from LDPerson where "
                + "CustomerNo ='"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql3");
mySql.addSubPara(fm.customerNo.value );  
    //prompt("检索分案关联出险人信息(多条)",strSQL3);
    
    var tSubReportGrid = new Array;
	tSubReportGrid = easyExecSql(mySql.getString());
 
        fm.customerNo.value = tSubReportGrid[0][0];
        fm.customerName.value = tSubReportGrid[0][1];
        fm.customerSex.value = tSubReportGrid[0][2];
        fm.customerAge.value = getAge(tSubReportGrid[0][3]);
        fm.customerBir.value = tSubReportGrid[0][3];
        fm.SocialInsuFlag.value = tSubReportGrid[0][4];
        showOneCodeName('sex','customerSex','SexName');//性别
    

    //查询获得理赔类型
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '" + rptNo + "'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql4");
mySql.addSubPara(rptNo);  
mySql.addSubPara(fm.customerNo.value );
    //prompt("查询获得理赔类型",strSQL1);
    
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    	  alert("理赔类型为空，请检查此记录的有效性！");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }
    
    //查询分案表信息
    var tSubReport = new Array;
   /* var strSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
                + getWherePart( 'CaseNo','ClmNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql5");
mySql.addSubPara(fm.ClmNo.value );
mySql.addSubPara(fm.customerNo.value );
//    alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);


    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    
    
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('accidentcode','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    //查询出险结果2
    queryResult2("AccResult2","AccResult2Name");

    //修改区

	//********************************************Beg
	//置空相关表单
	//********************************************
	fm.newcustomerName.value = "";
	fm.newcustomerAge.value = "";
	fm.newcustomerSex.value = "";
	fm.newSexName.value = "";
	fm.newSocialInsuFlag.value = "";
	fm.newhospital.value = "";
	fm.newTreatAreaName.value = "";
	fm.newAccidentDate2.value = "";
	fm.newaccidentDetail.value = "";
	fm.newaccidentDetailName.value = "";
//	fm.newIsDead.value = "";
//	fm.newIsDeadName.value = "";
	fm.newclaimType.value = "";
	fm.newcureDesc.value = "";
	fm.newcureDescName.value = "";
	fm.newAccResult1.value = "";
	fm.newAccResult1Name.value = "";
	fm.newAccResult2.value = "";
	fm.newAccResult2Name.value = "";
	//理赔类型置空
    for (var j = 0;j < fm.newclaimType.length; j++)
    {
    	  fm.newclaimType[j].checked = false;
    }
    //********************************************End
    
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }

    //取得数据

    //检索事件号、事故日期(一条)
    /*var newstrSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql6");
mySql.addSubPara(rptNo);          
//    alert("strSQL1= "+strSQL1);
    var newAccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
   /* var newstrSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion from llregister where "
                + "rgtno = '"+rptNo+"'";*/
//    alert("strSQL2= "+strSQL2);
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql7");
mySql.addSubPara(rptNo);   
    var newRptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //更新页面内容
    fm.newAccidentDate.value = newAccNo[0][1];

    fm.newoccurReason.value = newRptContent[0][16];
    showOneCodeName('lloccurreason','newoccurReason','newReasonName');//出险原因
    
    //检索分案关联出险人信息(多条)
   /* var newstrSQL3 = "select CustomerNo,Name," 
    	+ " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
        + " Birthday,"
        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
        + " from LDPerson where CustomerNo ='"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql8");
mySql.addSubPara(fm.customerNo.value); 
    var newtSubReportGrid = new Array;
	newtSubReportGrid = easyExecSql(mySql.getString());
    
        fm.customerNo.value = newtSubReportGrid[0][0];
        fm.newcustomerName.value = newtSubReportGrid[0][1];
        fm.newcustomerSex.value = newtSubReportGrid[0][2];
        fm.newcustomerAge.value = getAge(newtSubReportGrid[0][3]);
        fm.newcustomerBir.value = newtSubReportGrid[0][3];
        fm.newSocialInsuFlag.value = newtSubReportGrid[0][4];
        showOneCodeName('sex','newcustomerSex','newSexName');//性别
    

    //查询获得理赔类型
    var newtClaimType = new Array;
   /* var newstrSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '" + rptNo + "'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
 mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql9");
mySql.addSubPara(rptNo); 
mySql.addSubPara(fm.customerNo.value); 
//    alert(tClaimType);
    newtClaimType = easyExecSql(mySql.getString());
    if (newtClaimType == null)
    {
    	  alert("理赔类型为空，请检查此记录的有效性！");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.newclaimType.length;j++)
        {
        	  for (var l=0;l<newtClaimType.length;l++)
        	  {
        	  	  var tClaim = newtClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
//        	  	  alert(tClaim);
        	  	  if(fm.newclaimType[j].value == tClaim)
        	  	  {
                	  fm.newclaimType[j].checked = true;
            	  }
            }
        }
    }
    //查询分案表信息
    var newtSubReport = new Array;
    /*var newstrSQL2 = "select hospitalcode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2 from LLCase where 1=1 "
                + getWherePart( 'CaseNo','ClmNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
   mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql10");
mySql.addSubPara(fm.ClmNo.value); 
mySql.addSubPara(fm.customerNo.value);              
//    alert(strSQL2);
    newtSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.newhospital.value = newtSubReport[0][0];
    fm.newAccidentDate2.value = newtSubReport[0][1];
    fm.newaccidentDetail.value = newtSubReport[0][2];
//    fm.newIsDead.value = newtSubReport[0][3];
    fm.newcureDesc.value = newtSubReport[0][4];
    fm.newRemark.value = newtSubReport[0][5];
    fm.newAccDesc.value = newtSubReport[0][6];
    fm.newAccResult1.value = newtSubReport[0][7];
    fm.newAccResult2.value = newtSubReport[0][8];
    showOneCodeName('commendhospital','newhospital','newTreatAreaName');//治疗医院
    showOneCodeName('accidentcode','newaccidentDetail','newaccidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','newcureDesc','newcureDescName');//治疗情况
    showOneCodeName('lldiseases1','newAccResult1','newAccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','newAccResult2','newAccResult2Name');//出险结果2
    //出险结果2
    queryResult2("newAccResult2","newAccResult2Name");

}

//参数为出生年月,如1980-5-9 
function getAge(birth)
{
    var now = new Date();
    var mNowYear = now.getFullYear();
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}

//
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();


   // showSubmitFrame(mDebug);
//    fm.fmtransact.value = "INSERT"
    fm.action = './LLClaimImportModifySave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";    
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,tAccNo )
{
    showInfo.close();
    
    if (FlagStr == "Fail" )
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


    }
    else
    {
    	document.all('AccNo').value=tAccNo;
    	//alert(tAccNo);
    	
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

	
		top.opener.queryRegister();
		
		if(fm.recalculationFlag.value=='1')
		{
	        top.opener.showMatchDutyPay2(tAccNo);//自动匹配并理算
		}

    }
    
    //初始化
    tSaveFlag ="0";
    fm.recalculationFlag.value='0';
    fm.cancelMergeFlag.value='0';
}

//返回按钮
function goToBack()
{
    location.href="LLClaimImportModifyInput.jsp";
}


//返回按钮
function goToBack2()
{
    location.href="LLClaimAuditInput.jsp?claimNo="+fm.ClmNo.value;
}

//修改操作
function saveClick()
{
	//出险原因激活
    fm.occurReason.disabled=false;
	

	//理赔类型激活
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].disabled = false;
    }

    //首先进行非空字段检验
    if (beforeSubmit())
    {
        //判断区分保存报案还是保存出险人
        if (fm.ClmNo.value != "")
        {
        	fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        
        //置重算标志和清除案件合并信息标志
        checkRecalculation();
        
        
        fm.action = './LLClaimImportModifySave.jsp';
        submitForm();
    }
}


/**=========================================================================
修改状态：修改
修改原因：提交前的校验、计算
修 改 人：续涛
修改日期：2005.07.14
=========================================================================
**/
function beforeSubmit()
{
	  //获取表单域信息
	//var RptNo = fm.RptNo.value;//赔案号
	//var CustomerNo = fm.customerNo.value;//出险人编号
	
	var AccDate = fm.newAccidentDate.value;//事故日期
	var AccReason = fm.newoccurReason.value;//出险原因
	
	var AccDesc = fm.newaccidentDetail.value;//出险细节
	
	var ClaimType = new Array;
	var EditReason = fm.EditReason.value;//修改备注
	
	//理赔类型
	for(var j=0;j<fm.newclaimType.length;j++)
	{
		  if(fm.newclaimType[j].checked == true)
		  {
	        ClaimType[j] = fm.newclaimType[j].value;
	    }
	}
	
	var newclaimType=0;//选择理赔类型数量
	
	
	//理赔类型
	for(var j=0;j<fm.newclaimType.length;j++)
	{   	     	  
		  if(fm.newclaimType[j].checked == true)
		  {
			  newclaimType++;
	      }
	}
	
	
	//5 检查理赔类型
	if (newclaimType==0)
	{
	    alert("理赔类型不能为空！");
	    return false;
	}
	
	//当存在医疗理赔类型时需要校验医疗出险日期
	if(fm.claimType[5].checked == true)
	{
		//校验医疗出险日期和事故日期
		if (checkDate(fm.newAccidentDate.value,fm.newAccidentDate2.value,"出险日期") == false)
		{
		     return false;
		}
	}
	
	
	
	//3 检查出险原因
	if (AccReason == null || AccReason == '')
	{
	    alert("出险原因不能为空！");
	    return false;
	}
	else if (AccReason == "1")
	{
	    //4 意外的时候必须录入险细节
	    if (AccDesc == null || AccDesc == '')
	    {
	        alert("出险细节不能为空！");
	        return false;
	    }
	}    
	
//	
//	//-----------------------------------------------------------**Beg*2005-7-12 16:27
//	//添加出险原因为疾病时，且理赔类型为医疗时，事故日期等于医疗出险日期
//	//-----------------------------------------------------------**
//	if(fm.newoccurReason.value == "2" && (fm.newAccidentDate.value != fm.newMedicalAccidentDate.value)&& fm.newclaimType[5].checked == true)
//	{
//	    alert("出险原因为疾病时，事故日期必须等于医疗出险日期！");
//	    return false;
//	}
	
	
	//6 检查修改备注
	if (EditReason == null || EditReason == '')
	{
	    alert("修改备注不能为空！");
	    return false;
	}
	//**********************************************Beg*2005-6-13 20:26
	//添加理赔类型中选中"医疗"必须选择医院的判断
	//**********************************************
	if(fm.newclaimType[5].checked == true && (fm.newhospital.value == "" || fm.newhospital.value == null))
	{
	    alert("请选择医院！");
	    return false;
	}
	//**********************************************End
	
	//---------------------------------------------------------Beg*2005-6-27 11:55
	//添加理赔类型中选中豁免,必须选中死亡,高残或者重大疾病之一的判断
	//---------------------------------------------------------
	
	// add by wanzh begin
//	if (fm.newclaimType[4].checked == true && fm.newclaimType[1].checked == false && fm.newclaimType[0].checked == false && fm.newclaimType[2].checked == false)
//	{
//	    alert("选中豁免,必须选中死亡，高残或者是重大疾病之一!");
//	    return false;
//	}
//	if (fm.newclaimType[4].checked == true && fm.newclaimType[1].checked == false && fm.newclaimType[0].checked == false && fm.newclaimType[2].checked == false)
//	{
//	    alert("选中豁免,必须选中死亡，高残或者是重大疾病之一!");
//	    return false;
//	}
	// add by wanzh end
	
	
	
	//---------------------------------------------------------End
	
	
	
	
return true;
}



//*****************************************************
//功能：理赔类型逻辑判断
//处理：1 死亡、高残、医疗三者只可选一
//      2 选择死亡或高残时，默认选中豁免
//*****************************************************
function callClaimType(ttype)
{
    switch (ttype)
    {
        case "02" : //死亡
            if ((fm.newclaimType[1].checked == true || fm.newclaimType[5].checked == true) && fm.newclaimType[0].checked == true)
            {
                //alert("死亡、高残、医疗三者只可选一!");
                //fm.newclaimType[0].checked = false;
                //return;
            }
            if (fm.newclaimType[0].checked == true)
            {
                fm.newclaimType[4].checked = true;
            }
        case "03" :
            if ((fm.newclaimType[0].checked == true || fm.newclaimType[5].checked == true)&& fm.newclaimType[1].checked == true)
            {
                //alert("死亡、高残、医疗三者只可选一!");
                //fm.newclaimType[1].checked = false;
                //return;
            }        
            if (fm.newclaimType[1].checked == true)
            {
                fm.newclaimType[4].checked = true;
            }
        case "09" :
//            if ((fm.newclaimType[0].checked == true || fm.newclaimType[1].checked == true) && fm.newclaimType[4].checked == false)
//            {
//                alert("选取死亡、高残时系统会自动选择豁免!");
//                fm.newclaimType[4].checked = true;
//                return;
//            }            
        case "00" :
            if ((fm.newclaimType[0].checked == true || fm.newclaimType[1].checked == true) && fm.newclaimType[5].checked == true)
            {
                //alert("死亡、高残、医疗三者只可选一!");
                //fm.newclaimType[5].checked = false;
                //return;
            }
        default :
            return;
    }     
}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.newAccResult1.value;
}


/**=========================================================================
    修改状态：开始
    修改原因：以下为根据出险原因校验出险结果，如果为1意外必须录入，为2疾病不录
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/

function checkInsReason()
{

    var tInsReason = fm.newoccurReason.value;
    if ( tInsReason == "1")//如果为意外
    {
        fm.newaccidentDetail.disabled = false;
        fm.newaccidentDetailName.disabled = false;
    }
    else if ( tInsReason == "2")//如果为疾病
    {
        fm.newaccidentDetail.value = "";
        fm.newaccidentDetailName.value = "";        
        fm.newaccidentDetail.disabled = true;
        fm.newaccidentDetailName.disabled = true;
    }        
}


/**=========================================================================
    修改状态：开始
    修改原因：查询历史的修改原因
    修 改 人：续涛
    修改日期：2005.07.14
   =========================================================================
**/
function queryHisEditReason()
{
    
  /*  var strSql = " select a.Operator,a.MakeDate,a.EditReason "
       +" from LLBCase a where 1=1 "
       +" and a.CaseNo = '"+fm.ClmNo.value+"'"
       +" and a.CustomerNo = '"+fm.customerNo.value+"'";     */         
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql11");
mySql.addSubPara(fm.ClmNo.value); 
mySql.addSubPara(fm.customerNo.value);  
    turnPage.pageLineNum=5;    //每页2条
    turnPage.queryModal(mySql.getString(),HisEditReasonGrid);
        
       
}

//查询出险结果2
function queryResult2(tInput,tInputValue)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + document.all(tInput).value + "'"
               + " order by ICDCode";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql12");
mySql.addSubPara(document.all(tInput).value); 
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tInputValue).value = tICDName;
    }
}


/**
 * 2009-01-08 zhangzheng
 * 比较本次是否修改了出险日期(包括医疗出险日期和其他出险日期),出险原因,理赔类型，三项中任何一项修改了都要自动重新理算
 * 修改了出险日期和出险原因要清除案件合并信息!
 */
function checkRecalculation(){
	
	//初始化
	fm.recalculationFlag.value='0';
	fm.cancelMergeFlag.value='0';
	
	
	//比较出险原因
	if(fm.newoccurReason.value!=fm.occurReason.value)
	{
		  fm.recalculationFlag.value='1';
		  fm.cancelMergeFlag.value='1'
	}
	else if(fm.AccidentDate2.value!=fm.newAccidentDate2.value)
	{
		  fm.recalculationFlag.value='1';
		  fm.cancelMergeFlag.value='1'
	}
	else
	{
		 //循环判断理赔类型是否变化
		 var newClaimType = new Array;
         for(var j=0;j<fm.newclaimType.length;j++)
         {
	     	 //alert("fm.newclaimType["+j+"].checked:"+fm.newclaimType[j].checked);
	     	 //alert("fm.claimType["+j+"].checked:"+fm.claimType[j].checked);
	     	 
	  	     if(fm.newclaimType[j].checked != fm.claimType[j].checked)
	  	     {
			  			fm.recalculationFlag.value='1';
			  			break;
	  	     }
         }
	}
	
	//如果出险原因和出险日期有变化,则判断是否存在案件合并,如果不存在,则依然置为0
	if(fm.cancelMergeFlag.value=='1')
	{
		//先判断险种是否可以执行案件合并
	  /*  var strSql = "  select nvl(count(1),0) from lmrisksort a where"
	    	       + "  exists(select 1 from llclaimpolicy b where b.riskcode=a.riskcode "
                   + "  and b.clmno = '" + document.all('ClmNo').value + "' )"
                   + "  and a.risksorttype = '6'";*/
	    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql13");
mySql.addSubPara(document.all('ClmNo').value); 
	    //prompt("判断险种是否可以执行案件合并",strSql);
	    
		var tFlag = easyExecSql(mySql.getString());
		
		if (tFlag)
		{
			//等于0描述该险种不准许执行案件合并,则不必判断是否执行案件合并
		    if (tFlag[0][0] ==0)
		    {
		    	fm.cancelMergeFlag.value='0';
		    }
		    else
		    {
		    	//判断是否已经执行过案件合并
			   /* strSql = " select nvl(count(1),0),a.caserelano from llcaserela a,llcaserela b  where a.caserelano=b.caserelano"
		                    + " and a.caseno = '" + document.all('ClmNo').value + "' group by a.caserelano";*/
			     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql14");
mySql.addSubPara(document.all('ClmNo').value); 
			    //prompt("判断是否已经执行过案件合并的sql",strSql);
			    
				tFlag = easyExecSql(mySql.getString());
				
				if (tFlag)
				{
					//小于等于1表示没有执行过案件合并,则回置清除案件合并标志
				    if (tFlag[0][0] <=1)
				    {
				    	fm.cancelMergeFlag.value='0';
				    }
				}
		    }
		}
	  
	}
	
	//alert("fm.cancelMergeFlag.value:"+fm.cancelMergeFlag.value);
	//alert("fm.recalculationFlag.value:"+fm.recalculationFlag.value);

}




/*
 * 日期校验,校验两个日期都不能晚于当前日期，且第2个日期不能早于第1个日期
 * Date1,传入的第一个日期,此处为事故日期
 * Date2 传入的第二个日期,此处根据情况为医疗出险日期或其他出险日期
 * tDateName 传入的日期名称，用于组成弹出的提示语言
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//事故日期
    var AccDate2 =  tDate2;//出险日期日期
   
    //alert("AccDate:"+AccDate);
    //alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("事故日期不能晚于当前日期!");
            return false; 
        }
    }
    
    //验证事故下有两个或以上的处理中赔案--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //新增报案时，不校验事故日期
    }    
    
    var OAccDate = fm.BaccDate.value;//原事故日期Modify by zhaorx 2006-07-04
 
   
    if (OAccDate != AccDate)
    {    
       /* var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"*/
       mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimImportModifyInputSql");
mySql.setSqlId("LLClaimImportModifySql15");
mySql.addSubPara(fm.AccNo.value); 
        //prompt("验证事故下有两个或以上的处理中赔案",strSQL);
        
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("事故下有两个或以上的处理中赔案，不允许修改事故日期！");
            fm.newAccidentDate.value = OAccDate;
            return false;
        }
    }
    

    //校验出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert(tDateName+"不能为空！");
        return false;
    }
    else
    {
    	
       	//比较出险日期和当前日期
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert(tDateName+"不能晚于当前日期!");
            return false; 
        }

        //比较出险日期和事故日期       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert(tDateName+"不能早于事故日期!");
            return false; 
        }

    }
    
    return true;
}


//出险细节查询
function showAccDetail(tCode,tName)
{
	var strUrl="../claim/LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//医院模糊查询
function showHospital(tCode,tName)
{
	var strUrl="../claim/LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}