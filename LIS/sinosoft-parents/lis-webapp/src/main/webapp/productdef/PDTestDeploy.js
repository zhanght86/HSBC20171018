//程序名称：PDTestDeploy.js
//程序功能：产品测试与发布
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDTestDeployInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.action = './PDTestDeploySave.jsp';
  fm.submit(); 
}

function afterSubmit( FlagStr, content )
{
  if(showInfo != null)
  {
	  showInfo.close();
  }
  if (FlagStr == "Fail" )
  {             
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }else if(FlagStr == "Successs"){
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
  }
  else
  {
  	var mySql=new SqlClass();
	var sqlid = "PDTestDeployInputSql1";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.all("ReleasePlatform").value);//指定传入的参数
    var sql = mySql.getString();
	var arr = easyExecSql(sql);
    
	if(arr == null)
	{
		myAlert(""+"产品信息已成功发布，但配置信息不完整，无法清除核心系统的险种缓存"+"");

		return;
	}
//	if(fm.all("ReleasePlatform").value=='LIS')
// window.open(arr[0][0]);
    
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function query()
{
	var mySql=new SqlClass();
  var sqlid = "PDTestDeployInputSql2";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  var RiskCode=fm.RiskCode.value;
  if(RiskCode.substring(0,1)=="#"){
	  mySql.addSubPara("");//指定传入的参数  
	  RiskCode=RiskCode.substring(1,RiskCode.length);
	  mySql.addSubPara(fm.RequDate.value);//指定传入的参数
	  mySql.addSubPara(RiskCode);
	  Mulline9GridTurnPage.pageLineNum  = 32767;
	  }else{
		  mySql.addSubPara(RiskCode);//指定传入的参数  
		  mySql.addSubPara(fm.RequDate.value);//指定传入的参数
		  mySql.addSubPara("") 
		 Mulline9GridTurnPage.pageLineNum  = 10;
	  }
 
  var sql = mySql.getString();

	Mulline9GridTurnPage.queryModal(sql,Mulline9Grid);
}
function button406()
{
  showInfo = window.open("");
}
function button407()
{
  showInfo = window.open("");
}
function button408()
{
  showInfo = window.open("");
}
function btnDeploy()
{
	var selNo = Mulline9Grid.mulLineCount;
	var flag=false;
	for(var i=0;i<selNo;i++){
		if(Mulline9Grid.getChkNo(i)){flag=true;break;}
	}
	if(!flag)
	{
		myAlert(""+"请选中某产品后，再点击"+"");
		return;
	}
	if(!verifyInput())
	  {
	  	return;
	  }
//	if(fm.all("pd_release").value=="LIS"&&fm.all("pd_noy").value==""){
//		alert("请选择是否需要PD表数据");
//		return;
//	}else if(fm.all("pd_release").value=="Proposal"){
//		alert("建议书系统不包含PD表数据");
//	}
	
	if(document.getElementById('deployReason').value == ''){
		myAlert(""+"发布原因不能为空！"+"");
		return;
	}
	/*
	fm.RiskCode.value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.RequDate.value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.MissionID.value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.SubMissionID.value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.ActivityID.value = Mulline9Grid.getRowColData(selNo-1,7);
			*/
	//传入发布平台的代码，用于创建dblink的别名，
	//传入发布平台的类型，如果非生产环境，不进行工作流的跳转
	var mySql=new SqlClass();
  	var sqlid = "PDTestDeployInputSql6";
 	mySql.setResourceName(tResourceName); //指定使用的properties文件名
  	mySql.setSqlId(sqlid);//指定使用的Sql的id
  	mySql.addSubPara(fm.all("pd_release").value);
  	var a = easyExecSql(mySql.getString());
	fm.all("ReleasePlatform").value = a[0][0];
	fm.all("SysType").value =  a[0][2];
	fm.all("EnvType").value =  a[0][3];
	fm.all("operator").value = "testdeploy";
	submitForm();
}
function btnDeployIssue()
{
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中某产品后，再点击"+"");
		return;
	}
	
	fm.RiskCode.value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.RequDate.value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.MissionID.value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.SubMissionID.value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.ActivityID.value = Mulline9Grid.getRowColData(selNo-1,7);
	//传入发布平台的代码，用于创建dblink的别名，
	//传入发布平台的类型，如果非生产环境，不进行工作流的跳转
	fm.all("ReleasePlatform").value ="" ;
	fm.all("PlatformType").value = "3";
	
	fm.all("operator").value = "testdeploy";
	
	submitForm();
}
function queryMulline10Grid()
{           
  var mySql=new SqlClass();
  var sqlid = "PDTestDeployInputSql3";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara("sqlid");//指定传入的参数
  var sql = mySql.getString();
	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}
function IssueInput()
{
	var selNo = Mulline9Grid.getSelNo();	
    if(selNo == 0)
    {
	   myAlert(""+"请先查询选中一条产品后，再点击"+"");
	   return;
    }
    fm.RiskCode.value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.RequDate.value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.MissionID.value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.SubMissionID.value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.ActivityID.value = Mulline9Grid.getRowColData(selNo-1,7);
	
    showInfo = window.open("PDIssueInputMain.jsp?RiskCode=" + fm.RiskCode.value + "&postcode=00&issuetype=2&missionid="+fm.MissionID.value+"&submissionid="+fm.SubMissionID.value+"&activityid="+fm.ActivityID.value);
}


//产品基本信息定义完毕
function baseInfoDone()
{
  fm.all("operator").value="workflow";
  
  var alStr = checkRules("00");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  fm.action = "./PDRiskDefiSave.jsp";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();  
}

//契约工作流处理
function button119()
{
  fm.all("IsDealWorkflow").value = "1";
  fm.all("operator").value = "cont";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

  if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  fm.action = "./PDContDefiEntrySave.jsp";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit(); 
}

// 理赔工作流处理
function btnClaimDone()
{
   var alStr = checkRules("12"); 
   if(alStr != "")
   {
  	  myAlert(alStr);
  	  return;
   }
            
   fm.all("operator").value = "claim";
   fm.action = "./PDContDefiEntrySave.jsp";
   submitForm();
}

function btnEdorDone()
{
  var alStr = checkRules("11");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

    fm.all("operator").value = "edor";
	fm.action = "./PDContDefiEntrySave.jsp";
	if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }

  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

//审核工作流处理
function btnAuditDone()
{
  	fm.all("operator").value = "audit";
	if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  fm.action = "./PDProdAudiDetailSave.jsp";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?RiskCode=" + fm.RiskCode.value + "&postcode=00&issuetype=1");
}

function showTestPlanList(){
	try{
	var rowNum=Mulline9Grid.getSelNo();	
	var iArrayIndex = Number(rowNum) - Number(1);
	var iArray=Mulline9Grid.getRowData(iArrayIndex);
	var tfileName = iArray[0];
	fm.RiskCode.value=Mulline9Grid.getRowData(iArrayIndex)[0];
	fm.RequDate.value=Mulline9Grid.getRowData(iArrayIndex)[2];
	}catch(ex){}
	/*
	var mySql=new SqlClass();
  var sqlid = "PDTestDeployInputSql4";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(tfileName);//指定传入的参数
	var sql = mySql.getString();
	
    turnPage1.queryModal(sql, Mulline11Grid);
    
    var rowNum=Mulline11Grid.mulLineCount;
    var mySql2=new SqlClass();
	  var sqlid2 = "PDTestDeployInputSql5";
	  mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	  mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	  mySql2.addSubPara(tfileName);//指定传入的参数
    var querySQL = mySql2.getString();
    var crr = easyExecSql(querySQL);
    
    for(var i = 0; i < rowNum; i ++){
    	if(crr[i][0] == 1){
    		Mulline11Grid.checkBoxSel(i + 1);
    	}
    }	*/
}

function saveTestState(){
	fm.action = 'PDTestPlanDepolySave.jsp';
	fm.submit();
}

function showDeployReason(){
	document.getElementById("deployReasonID").style.display = '';
}

function selectpd_release(value){
	if(value=="LIS"){
		//显示是否需要PD表数据
		document.getElementById("pd_noyStrdiv").style.display = '';
		document.getElementById("pd_noydiv").style.display = '';
	}else{
		//隐藏是否需要PD表数据
		document.getElementById("pd_noyStrdiv").style.display = 'none';
		document.getElementById("pd_noydiv").style.display = 'none';
	}
	showDeployReason();
}
