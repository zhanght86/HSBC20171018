//程序名称：PDEdorZT1.js
//程序功能：具体保全项目定义2
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{
  var GridObject = Mulline9Grid;
  var rowCount = GridObject.mulLineCount;
  
  for(var i = 0; i < rowCount; i++)
  {
    var PropName = GridObject.getRowColData(i,1);
    var PropValue = GridObject.getRowColData(i,4);
    
  	if(PropName.indexOf("[*]") > -1)
  	{
  		if(PropValue == null || PropValue == "")
  		{
  			PropName = PropName.substring(3,PropName.length);
  		
  			myAlert("\"" + PropName + ""+"\"不能为空"+"");
  			
  			return false;
  		}
  	}
  }
  
  return true;
}

function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  
	if(!checkNullable())
	{
		return false;
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
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function save()
{
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}
function button275()
{
  var selNo = Mulline11Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选择一条已保存保全项目定义"+"");
		return;
	}
	else
	{
		var name = Mulline11Grid.getRowColData(selNo-1,5);
		if(name == null || name =="")
		{
			myAlert(""+"请先录入算法编码"+"");
			return;
		}
	}
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&algocode=" + Mulline11Grid.getRowColData(selNo-1,5));
}	
		
function afterQueryAlgo(AlgoCode)
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"选中行的焦点丢失"+"");
		return;
	}
	
	Mulline9Grid.setRowColDataCustomize(selNo-1,4,AlgoCode);
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMEdorCal') and isdisplay = '1' order by displayorder";Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL ="";
   strSQL =  "select a.riskcode,c.dutycode,c.payplancode,c.payplanname from PD_LMRisk a,PD_LMRiskDuty b,PD_LMDutyPay c where a.RiskCode = b.RiskCode and b.DutyCode = c.DutyCode and a.Riskcode = '"+fm.all('RiskCode').value +"'";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "select a.riskcode,a.dutycode,a.edortype,a.CALTYPE,a.CALCODE from PD_LMEdorCal a where a.riskcode = '"+fm.all('RiskCode').value + "'";
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
function queryCodeName()
{
	var strSQL = "select riskname from PD_LMRisk where Riskcode = '"+fm.all('RiskCode').value+"'";
	tRiskName = easyExecSql(strSQL);
	if(tRiskName!=null){
		fm.all('RiskCodeName').value = tRiskName[0][0];
	}
	strSQL = "select EdorName from pd_lmriskedoritem where RiskCode = '"+fm.all('RiskCode').value+"' and EdorCode = '"+fm.all('EdorType').value+"'";
	tEdorTypeName = easyExecSql(strSQL);
	if(tEdorTypeName!=null){
		fm.all('EdorTypeName').value = tEdorTypeName[0][0];
	}
}
