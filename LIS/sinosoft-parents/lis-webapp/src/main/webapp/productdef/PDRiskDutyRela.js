//程序名称：PDRiskDutyRela.js
//程序功能：险种关联责任
//创建日期：2009-3-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
var Mulline12GridTurnPage = new turnPageClass(); 
var dic;

function checkNullable(GridObject)
{
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

  if(fm.all("SubmitMode").value == "Pay")
  {
  	if(!checkNullable(Mulline10Grid))
	{
		return false;
	}
  }
  if(fm.all("SubmitMode").value == "Get")
  {
  	if(!checkNullable(Mulline12Grid))
	{
		return false;
	}
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
  
  fm.action = "./PDRiskDutyRelaSave.jsp";
}

function QueryPayLib()
{
	window.open("PDDutyPayLibInput.jsp?type=1");
}

function QueryGetLib()
{
	window.open("PDDutyGetLibInput.jsp?type=1");
}

function afterQueryPayLib(PayPlanCode2)
{
	fm.all("payPlanCode2").value = PayPlanCode2;
	
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('" + fm.all("tableName").value + "') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 if(dic == null || dic("PAYPLANCODE2") != fm.all("payPlanCode2").value)
 {
 	var arr = new Array();
 	arr[0] = new Array();
 	arr[0][0] = "PAYPLANCODE2";
 	arr[0][1] = PayPlanCode2;
 	
	dic = GetDicOfTableByKeys(fm.all("tableName3").value, arr);
 }

 for(var i = 0; i < rowCount; i++)
 {
 	if(Mulline10Grid.getRowColData(i,2).toUpperCase() != "PAYPLANCODE")
 	{
		var tContent = dic((Mulline10Grid.getRowColData(i,2)).toUpperCase());
	
		Mulline10Grid.setRowColDataCustomize(i,4,tContent,null);
	}
 }
}

function afterQueryGetLib(GetDutyCode2)
{
	fm.all("getDutyCode2").value = GetDutyCode2;
	
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('" + fm.all("tableName").value + "') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 if(dic == null || dic("GETDUTYCODE2") != fm.all("getDutyCode2").value)
 {
 	var arr = new Array();
 	arr[0] = new Array();
 	arr[0][0] = "GETDUTYCODE2";
 	arr[0][1] = GetDutyCode2;
 	
	dic = GetDicOfTableByKeys(fm.all("tableName4").value, arr);
 }

 for(var i = 0; i < rowCount; i++)
 {
 	if(Mulline12Grid.getRowColData(i,2).toUpperCase() != "GETDUTYCODE")
 	{
		var tContent = dic((Mulline12Grid.getRowColData(i,2)).toUpperCase());
	
		Mulline12Grid.setRowColDataCustomize(i,4,tContent,null);
	}
 }
}


function ModifyPay()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
	fm.all("operator").value="update";
 	fm.all("SubmitMode").value="choflag";
 	fm.action = "./PDchoflagSave.jsp";
 	submitForm();
	//window.open("PDAccGuratRateMain.jsp?riskcode=" + fm.all("RiskCode").value+"&insuaccno=" + Mulline9Grid.getRowColData(selNo-1,2));
}
function DutyDefi()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
	window.open("PDLMDutyMain.jsp?riskcode=" + fm.all("RiskCode").value+"&dutycode=" + Mulline9Grid.getRowColData(selNo-1,4));
}


function checkUrgePayFlag()
{
	var rowCount = Mulline10Grid.mulLineCount;
	
	for(var i = 0; i < rowCount; i++)
 	{
	 	if(Mulline10Grid.getRowColData(i,2) == "URGEPAYFLAG")
	 	{
	 		var sql = "select CPAYFLAG from pd_lmrisk where riskcode = '" + fm.all("RiskCode").value + "'";
			var arr = easyExecSql(sql);	 		
			
			if(arr[0][0] != Mulline10Grid.getRowColData(i,4))
			{
				myAlert(""+"【催缴标记】的值必须与该险种【续期收费标记】描述一样"+","+"\r\n请去产品基础信息录入界面查看"+"");
				return false;
			}
			
			break;
	 	}
 	}
 	
 	return true;
}

function Paysave()
{
 if(!checkUrgePayFlag())
 {
 	return;
 }
 
 fm.all("operator").value="save";
 fm.all("SubmitMode").value="Pay";
 fm.all("dutyType2").value = "1";
 submitForm();
}
function Payupdate()
{
 if(!checkUrgePayFlag())
 {
 	return;
 }
 
 fm.all("operator").value="update";
 fm.all("SubmitMode").value="Pay";
 fm.all("dutyType2").value = "1";
 submitForm();
}
function Paydel()
{
 fm.all("operator").value="del";
 fm.all("SubmitMode").value="Pay";
 fm.all("dutyType2").value = "1";
 submitForm();
}
function Getsave()
{
 fm.all("operator").value="save";
 fm.all("SubmitMode").value="Get";
 fm.all("dutyType2").value = "2"; 
 submitForm();
}
function Getupdate()
{
 fm.all("operator").value="update";
 fm.all("SubmitMode").value="Get";
 fm.all("dutyType2").value = "2"; 
 submitForm();
}
function Getdel()
{
 fm.all("operator").value="del";
 fm.all("SubmitMode").value="Get";
 fm.all("dutyType2").value = "2"; 
 submitForm();
}
function returnParent()
{
  top.opener.focus();
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select c.riskcode,a.payplancode,a.payplanname,b.dutycode,c.choflag from pd_lmdutypay a ,pd_lmduty b ,pd_lmriskduty c  where a.dutycode = b.dutycode and "
						+ " b.dutycode = c.dutycode and c.riskcode = '"+fm.all("RiskCode").value+"'";	
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMDutyPay') and isdisplay = '1' order by displayorder";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "select c.riskcode,a.getdutycode,a.getdutyname,b.dutycode from pd_lmdutyget a ,pd_lmduty b ,pd_lmriskduty c  where a.dutycode = b.dutycode and "
						+" b.dutycode = c.dutycode and c.riskcode = '"+fm.all("RiskCode").value+"'";
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
function queryMulline12Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMDutyGet') and isdisplay = '1' order by displayorder";
   Mulline12GridTurnPage.pageLineNum  = 3215;
   Mulline12GridTurnPage.queryModal(strSQL,Mulline12Grid);
}


