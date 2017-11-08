//程序名称：PDLFRisk.js
//程序功能：保监会报表描述
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDLFRiskInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
	if(document.getElementById("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
	
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{

  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
function save()
{
 fm.all("operator").value="save";
 fm.action="./PDLFRiskSave.jsp";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 fm.action="./PDLFRiskSave.jsp";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 fm.action="./PDLFRiskSave.jsp";
 submitForm();
}
function button214()
{
  showInfo = window.open("");
}
function button215()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=13&issuetype=0");
}
function button217()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=13&issuetype=0&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);
}
function btnLFRiskDone()
{
  fm.all("operator").value = "lfrisk";
  fm.action="./PDContDefiEntrySave.jsp";
  submitForm();
}
function button219()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   var sqlid = "PDLFRiskInputSql1";
   mySql.setResourceName(tResourceName); //指定使用的properties文件名
   mySql.setSqlId(sqlid);//指定使用的Sql的id
   mySql.addSubPara("sqlid");//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}


function queryRisk(){
	var URL = "PDRiskDefiInput.jsp?riskcode="+fm.all("riskcode").value;
	open(URL);
}
