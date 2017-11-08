//程序名称：PDPrvRepealDetail.js
//程序功能：条款废止审核
//创建日期：2009-3-18
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
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
var Mulline10GridTurnPage = new turnPageClass(); 

function button391()
{
	showInfo = window.open("PDRiskDefiMain.jsp?riskcode=" + fm.all("RiskCode").value +"&requdate="+fm.all("RequDate").value);
}
function button392()
{
  showInfo = window.open("PDContDefiEntryMain.jsp?riskcode=" + fm.all("RiskCode").value+"&requdate="+fm.all("RequDate").value);
}
function button393()
{
  showInfo = window.open("PDEdorDefiEntryMain.jsp?riskcode=" + fm.all("RiskCode").value+"&requdate="+fm.all("RequDate").value+"&riskname="+fm.all("RiskName").value);
}
function button394()
{
  showInfo = window.open("PDClaimDefiEntryMain.jsp?riskcode=" + fm.all("RiskCode").value+"&requdate="+fm.all("RequDate").value);
}
function button395()
{
  window.open("PDLFRiskInput.jsp?riskcode="+fm.all("RiskCode").value+"&requdate="+fm.all("RequDate").value);
}
function button396()
{
  showInfo = window.open("");
}
function button397()
{
  showInfo = window.open("");
}
function button398()
{
  showInfo = window.open("");
}
function button399()
{
  showInfo = window.open("");
}
function btnAuditDone()
{
  	fm.all("operator").value = "audit";
	submitForm();
}
function button401()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select a.riskcode,b.payplancode,payplanname,a.Choflag from Pd_Lmriskduty a, pd_lmdutypay b where a.dutycode = b.dutycode and a.riskcode = '" + fm.all("RiskCode").value + "'";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = "select riskcode,c.getdutycode,c.getdutyname,c.Getdutycode from Pd_Lmriskduty a, PD_lmdutyget c where a.dutycode = c.dutycode and a.riskcode = '" + fm.all("RiskCode").value + "'";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=20&issuetype=1");
}
function IssueInput()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=20&issuetype=1&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);
}
