//程序名称：PDRiskParamsDef.js
//程序功能：缴费字段控制定义
//创建日期：2009-3-13
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
var tResourceName = "productdef.PDRiskParamsDefInputSql";
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
  if(!checkNullable()){
  	return ;
  }

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
function button127()
{
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一个控制字段！"+"");
		return;
	}
  showInfo = window.open("PDRiskParamsDetailDefMain.jsp?riskcode=" + fm.all("RiskCode").value+"&dutycode="+fm.all("DutyCode").value+"&othercode="+fm.all("PayPlanCode").value+"&fieldname="+Mulline10Grid.getRowColData(selNo-1,3));
}
function button128()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
     Mulline9GridTurnPage.pageLineNum  = 3215;
  
  var mySql=new SqlClass();
	 var sqlid = "PDRiskParamsDefInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara('1');//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   //strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMDutyCtrl') and isdisplay = '1' order by displayorder";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskParamsDefInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all('DutyCode').value);//指定传入的参数
	 //mySql.addSubPara(fm.all('PayPlanCode').value);//指定传入的参数
   strSQL = mySql.getString();
    Mulline10GridTurnPage.pageLineNum  = 3215; 
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryDutyCode()
{
//	var strSQL = "";
//	strSQL = "select dutycode from PD_LMDutypay where payplancode = '"+fm.all('PayPlanCode').value +"'";
	
	  var mySql=new SqlClass();
	 var sqlid = "PDRiskParamsDefInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.DutyCode.value);//指定传入的参数
	// mySql.addSubPara(fm.all('PayPlanCode').value);//指定传入的参数
   strSQL = mySql.getString();
   
	tnumber = easyExecSql(strSQL);
	fm.PayPlanCode.value=tnumber[0];
}
function isshowbutton()
{   var value=getQueryState1();

	if(value=='0'||value==0){
	document.getElementById('savebuttonid').style.display = 'none';
	//document.getElementById('definedparams').disabled=true;
	}else if(value=='1'||value==1){
		document.getElementById('savebuttonid').style.display = '';
	}
}
