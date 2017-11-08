//程序名称：PDContDefiEntry.js
//程序功能：契约信息定义入口
//创建日期：2009-3-13
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
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
function button100()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRateCashValueMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button101()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}	
  showInfo = window.open("PDDutyPayMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button102()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskParamsDefMain.jsp?riskcode=" + fm.all("RiskCode").value + "&type=1&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button103() // 与102同
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskParamsDefMain.jsp?riskcode=" + fm.all("RiskCode").value + "&type=2&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button104()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDDutyPayAddFeeMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button105()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskDutyFactorMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button106()
{
  showInfo = window.open("PDRiskRoleMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button107()
{
  showInfo = window.open("PDRiskPrintMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button108()
{
  showInfo = window.open("PDRiskPayMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button109()
{
  showInfo = window.open("PDRiskPayIntvMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button110()
{
  showInfo = window.open("PDCheckFieldMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button111()
{
  showInfo = window.open("PDUMMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button112()
{
  showInfo = window.open("");
}
function button113()
{
  showInfo = window.open("");
}
function button114()
{
  showInfo = window.open("PDSaleControlMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button115()
{
  showInfo = window.open("PDRiskRelaMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button116()
{
  showInfo = window.open("PDRiskAccPayMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button117()
{
  showInfo = window.open("PDRiskDefiMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button118()
{
  showInfo = window.open("");
}
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

  submitForm();
  
}
function button120()
{window.open("PDRiskFeeMain.jsp");
	window.opener=null;
window.open("","_self");
window.close(); 

       
}
function button121()
{
  fm.all("operator").value = "simplecont";
  fm.all("SimpleContPara").value = "111";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  submitForm();
}
function button122()
{
  showInfo = window.open("PDContDefiConfigInput.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button123()
{
  showInfo = window.open("PDContDefiPayInput.jsp?riskcode=" + fm.all("RiskCode").value);
}
function queryMulline9Grid()
{
   var strSQL = "";
   //strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('LMRisk') and isdisplay = '1' order by displayorder";
   strSQL = "select a.riskcode,a.riskname,c.payplancode,c.payplanname from pd_lmrisk a,pd_lmriskduty b ,pd_lmdutypay c " 
   					+ " where a.riskcode = b.riskcode and b.dutycode = c.dutycode and a.riskcode = '"+fm.all('RiskCode').value +"'"; 
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=10&issuetype=0");
}

// modify by nicole 将MissionID等参数传递到问题件录入页面
function IssueInput()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("riskcode").value + "&postcode=10&issuetype=0&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);
}

