//程序名称：PDTestDeploy.js
//程序功能：产品测试与发布
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline8GridTurnPage = new turnPageClass(); 
//定义sql配置文件
var tResourceName = "productdef.PublicInfoDeployInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fm.action = './PublicInfoDeploySave.jsp';
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
  	/*
  	var mySql=new SqlClass();
	  var sqlid = "PDTestDeployInputSql1";
	  mySql.setResourceName(tResourceName); //指定使用的properties文件名
	  mySql.setSqlId(sqlid);//指定使用的Sql的id
	  mySql.addSubPara(fm.all("ReleasePlatform").value);//指定传入的参数
    var sql = mySql.getString();
	var arr = easyExecSql(sql);
    
	
	if(arr == null)
	{
		alert("产品信息已成功发布，但配置信息不完整，无法清除核心系统的险种缓存");

		return;
	}
    window.open(arr[0][0]);
    
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    */
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    initForm();
   
  } 
}

function query()
{
	
	var mySql=new SqlClass();
  var sqlid = "PublicInfoDeployInputSql2";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  //mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
  mySql.addSubPara('1');//指定传入的参数
 
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
	var selNo = Mulline9Grid.getSelNo();
	
	if(selNo == 0)
	{
		myAlert(""+"请选中需要发布的内容后，再点击"+"");
		return;
	}
	var selNo2 = Mulline10Grid.getSelNo();
	
	if(selNo2 == 0)
	{
		myAlert(""+"请选中发布平台后，再点击"+"");
		return;
	}
	
	
	fm.all("RuleType").value = Mulline9Grid.getRowColData(selNo-1,1);
	//传入发布平台的代码，用于创建dblink的别名，
	//传入发布平台的类型，如果非生产环境，不进行工作流的跳转
	fm.all("ReleasePlatform").value = Mulline10Grid.getRowColData(selNo2-1,1);
	fm.all("SysType").value = Mulline10Grid.getRowColData(selNo2-1,3);
	fm.all("EnvType").value = Mulline10Grid.getRowColData(selNo2-1,4);
	
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
	
	fm.all("RiskCode").value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all("RequDate").value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all("MissionID").value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.all("SubMissionID").value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.all("ActivityID").value = Mulline9Grid.getRowColData(selNo-1,7);
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
  var sqlid = "PublicInfoDeployInputSql3";
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
    fm.all("RiskCode").value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all("RequDate").value = Mulline9Grid.getRowColData(selNo-1,3);
	fm.all("RiskName").value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all("MissionID").value = Mulline9Grid.getRowColData(selNo-1,5);
	fm.all("SubMissionID").value = Mulline9Grid.getRowColData(selNo-1,6);
	fm.all("ActivityID").value = Mulline9Grid.getRowColData(selNo-1,7);
	
    showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=30&issuetype=2&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);
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
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=30&issuetype=1");
}


function saveTestState(){
	fm.action = 'PDTestPlanDepolySave.jsp';
	fm.submit();
}

function showDeployReason(){
	document.getElementById("deployReasonID").style.display = '';
}
function queryrule()
{
	var RiskCode=fm.all("RiskCode").value;
	var UWCODE=fm.all("UWCODE").value;
	var RELAPOLTYPE=fm.all("RELAPOLTYPE").value;
	var UWTYPE=fm.all("UWTYPE").value;
	var STANDBYFLAG2=fm.all("STANDBYFLAG2").value;
	var STANDBYFLAG1=fm.all("STANDBYFLAG1").value;
	  var mySql=new SqlClass();
	  var sqlid = "PublicInfoDeployInputSql6";
	if(UWCODE.substring(0,1)=="#"){
		sqlid="PublicInfoDeployInputSql7";
		UWCODE=UWCODE.substring(1,UWCODE.length);
		  mySql.addSubPara(UWCODE);
		  mySql.addSubPara(UWCODE);
		  Mulline8GridTurnPage.pageLineNum  = 1024;
	}else{
		  mySql.addSubPara(UWCODE);
		  mySql.addSubPara(RiskCode);
		  mySql.addSubPara(UWTYPE);
		  mySql.addSubPara(RELAPOLTYPE);
		  mySql.addSubPara(STANDBYFLAG2);
		  mySql.addSubPara(STANDBYFLAG1);
		  mySql.addSubPara(UWCODE);
		  mySql.addSubPara(RiskCode);
		  mySql.addSubPara(UWTYPE);
		  mySql.addSubPara(RELAPOLTYPE);
		  mySql.addSubPara(STANDBYFLAG2);
		  mySql.addSubPara(STANDBYFLAG1);
		  Mulline8GridTurnPage.pageLineNum  = 15;
	}	
	  mySql.setResourceName(tResourceName); 
	  mySql.setSqlId(sqlid);//指定使用的Sql的id


	  var sql = mySql.getString();

		Mulline8GridTurnPage.queryModal(sql,Mulline8Grid);
	
}
function uwDeploy()
{
	if(!inituwDeploy())return false;
var selNo = Mulline8Grid.mulLineCount;
var flag=false;
for(var i=0;i<selNo;i++){
	if(Mulline8Grid.getChkNo(i)){flag=true;break;}
}
if(!flag)
{
	myAlert(""+"请选中产品后，再点击"+"");
	return;
}
fm.all("operator").value="UWDEPLOY";
submitForm();
}
function uwDeploySelect()
{
	if(!inituwDeploy())return false;
	fm.all("operator").value="UWDEPLOYSELECT";
	var RiskCode=fm.all("RiskCode").value;
	var UWCODE=fm.all("UWCODE").value;
	var RELAPOLTYPE=fm.all("RELAPOLTYPE").value;
	var UWTYPE=fm.all("UWTYPE").value;
	var STANDBYFLAG2=fm.all("STANDBYFLAG2").value;
	var STANDBYFLAG1=fm.all("STANDBYFLAG1").value;	
	  var mySql=new SqlClass();
	  var sqlid = "PublicInfoDeployInputSql6";
	  mySql.setResourceName(tResourceName); 
	  mySql.setSqlId(sqlid);//指定使用的Sql的id

	  mySql.addSubPara(UWCODE);
	  mySql.addSubPara(RiskCode);
	  mySql.addSubPara(UWTYPE);
	  mySql.addSubPara(RELAPOLTYPE);
	  mySql.addSubPara(STANDBYFLAG2);
	  mySql.addSubPara(STANDBYFLAG1);
	  mySql.addSubPara(UWCODE);
	  mySql.addSubPara(RiskCode);
	  mySql.addSubPara(UWTYPE);
	  mySql.addSubPara(RELAPOLTYPE);
	  mySql.addSubPara(STANDBYFLAG2);
	  mySql.addSubPara(STANDBYFLAG1);
	  var sql = mySql.getString();
	  fm.all("SQL").value=sql;
	submitForm();	
}
function inituwDeploy()
{
//	var selNo = Mulline9Grid.getSelNo();
	
	//if(selNo == 0)
	//{
	//	alert("请选中需要发布的内容后，再点击");
	//	return;
	//}
	var selNo2 = Mulline10Grid.getSelNo();
	
	if(selNo2 == 0)
	{
		myAlert(""+"请选中发布平台后，再点击"+"");
		return false;
	}
	
	
	//fm.all("RuleType").value = Mulline9Grid.getRowColData(selNo-1,1);
	//传入发布平台的代码，用于创建dblink的别名，
	//传入发布平台的类型，如果非生产环境，不进行工作流的跳转
	fm.all("ReleasePlatform").value = Mulline10Grid.getRowColData(selNo2-1,1);
	fm.all("SysType").value = Mulline10Grid.getRowColData(selNo2-1,3);
	fm.all("EnvType").value = Mulline10Grid.getRowColData(selNo2-1,4);
	return true;
}