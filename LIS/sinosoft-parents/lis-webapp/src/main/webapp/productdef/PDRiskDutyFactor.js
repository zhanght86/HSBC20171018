//程序名称：PDRiskDutyFactor.js
//程序功能：责任录入要素定义
//创建日期：2009-3-13
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
Mulline12GridTurnPage = new turnPageClass();
function save()
{
 fm.all('submitFlag').value="PD_LMRiskDutyFactor";
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
	fm.all('submitFlag').value="PD_LMRiskDutyFactor";
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
fm.all('submitFlag').value="PD_LMRiskDutyFactor";
 fm.all("operator").value="del";
 submitForm();
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMRiskDutyFactor') and isdisplay = '1'  and fieldcode<>'FACTORNAME' order by displayorder";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   //strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMRiskDutyFactor') and isdisplay = '1' order by displayorder";
   strSQL = "select riskcode,dutycode,calfactor,factorname,calfactortype from pd_LMRiskDutyFactor where dutycode = '"+fm.all('DutyCode').value +"' and riskcode = '"+fm.all('RiskCode').value+"'";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryDutyCode()
{
//	var strSQL = "";
//	strSQL = "select dutycode from PD_LMDutypay where payplancode = '"+fm.all('PayPlanCode').value +"'";
//	alert("payplancode=="+fm.all('PayPlanCode').value);
//	tnumber = easyExecSql(strSQL);
//	alert("tnumber==="+tnumber);
//	fm.all('DutyCode').value=tnumber[0];
	fm.all('DutyCode').value=fm.all('PayPlanCode').value;//在参数传递过程中，payplancode传的是dutycode，不需要查数据库直接赋值即可，待后续优化改正
}




function afterCodeSelect(cCodeName,Field){
 	if(cCodeName=='pd_tbstrinfo'){
 	var sql = "select trim(Code), trim(CodeName) from ldcode where 1 = 1 and codetype = 'pd_tbstrinfo' order by Code ";
  var result = easyExecSql(sql,1,1,1); 
  var tbstrinfos = new Array(result.length);
  var factors = new Array(result.length);
  for(var i=0;i<result.length;i++){
  	tbstrinfos[i] = result[i][0];
  	factors[i] = result[i][1];
  }
  
	for(var i=0;i<Mulline12Grid.mulLineCount;i++){
		var tbstrinfo = Mulline12Grid.getRowColData(i,4);
		for(var j=0; j<tbstrinfos.length;j++){
			if(tbstrinfos[j] == tbstrinfo){
					Mulline12Grid.setRowColData(i,5,factors[j]);
			}
		}
  }		
 	}
 
}


function saveCalFactorSub(){
	 fm.all('submitFlag').value="ldcode";
	 fm.all("operator").value="save";
	 submitForm();
}

function updateCalFactorSub(){
	fm.all('submitFlag').value="ldcode";
	fm.all("operator").value="update";
	submitForm();
}

function delCalFactorSub(){
	fm.all('submitFlag').value="ldcode";
	fm.all("operator").value="del";
	submitForm();
}

function NotNull(vName, strValue)
{
	if (strValue == "")
	{
		myAlert(vName + ""+"不能为空！\n"+"");
		return false;
	}
	return true;
}


function 	judgeCalFactor(){
	var selNo = Mulline10Grid.getSelNo();
	if(selNo <1){
		document.getElementById('calFactorSub').style.display = "none";	
		return;
	}
	var calFactor = Mulline10Grid.getRowColData(selNo-1,3);
	var calFactors = ['GetLimit','GetRate','Mult','StandbyFlag1','StandbyFlag2','StandbyFlag3'];
	for(var i=0;i<calFactors.length;i++){
		if(calFactor == calFactors[i]){
				document.getElementById('calFactorSub').style.display = "";		
				var strSQL = "select codetype,code,codename,codealias,comcode from ldcode where codetype='"+Mulline10Grid.getRowColData(selNo-1,2)+"' and code='"+Mulline10Grid.getRowColData(selNo-1,3)+"'";
				Mulline12GridTurnPage.queryModal(strSQL,Mulline12Grid);
				return;				
		}
	}
			document.getElementById('calFactorSub').style.display = "none";	
}

function setDutyCode(){
	
	if(Mulline12Grid.mulLineCount > 1){
	 		Mulline12Grid.delBlankLine(); 
	 		return;
	}
	
	var selNo = Mulline10Grid.getSelNo();
	for(var i=0;i<Mulline12Grid.mulLineCount;i++){
  	Mulline12Grid.setRowColData(i,1,Mulline10Grid.getRowColData(selNo-1,2));
  	Mulline12Grid.setRowColData(i,2,Mulline10Grid.getRowColData(selNo-1,3));
  }
  	
}

function save2(flag){
	fm.action = "./PDRiskDutyFactorSave2.jsp?flag="+flag;
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.submit();
}
function queryLMDutyParam(){
	
	var strSql = "select CalFactor,FactorName,ChooseFlag,Standbyflag2,Standbyflag1,FactorNoti from PD_LMRiskDutyFactor where riskcode='"+fm.RiskCode1.value+"' and dutycode='"+fm.PayPlanCode.value+"'";
	turnPage.queryModal(strSql,LMDutyParamGrid);
}
