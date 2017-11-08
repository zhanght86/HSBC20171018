//程序名称：PDDutyPayLib.js
//程序功能：缴费责任库
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	
//定义sql配置文件
var tResourceName = "productdef.PDDutyPayLibInputSql";
function returnParent()
{
	/*
	if(Mulline9Grid.getRowColData(fm.all("PayPlanCode2RowOfMulLine").value,4) == "")
	{
		alert("请选中【缴费责任库查询结果】中的某项，再点击返回");
		return;
	}
	top.opener.afterQueryPayLib(Mulline9Grid.getRowColData(fm.all("PayPlanCode2RowOfMulLine").value,4));	
	*/
	var selNo = Mulline10Grid.getSelNo();
	if(selNo > 0 ){
		top.opener.afterQueryPayLib(Mulline10Grid.getRowColData(selNo-1,1));	
	}
	
	top.opener.focus();		
	top.close();
}
function submitForm()
{
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function queryRela()
{
		var selNo = Mulline10Grid.getSelNo();
		var mySql=new SqlClass();
		var sqlid = "PDDutyPayLibInputSql1";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(Mulline10Grid.getRowColData(selNo - 1, 1));//指定传入的参数
		var sql = mySql.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline11Grid);
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
    
    initButtonDisplay();    
    query();
    updateDisplayState();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function query()
{
		var mySql=new SqlClass();
		var sqlid = "PDDutyPayLibInputSql2";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(fm.PayPlanCode2.value);//指定传入的参数
		mySql.addSubPara(fm.PayPlanName.value);//指定传入的参数
		mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
		mySql.addSubPara(fm.PayPlanCode.value);//指定传入的参数
    var sql = mySql.getString();
  	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
  	
  	var mySql2=new SqlClass();
		var sqlid2 = "PDDutyPayLibInputSql3";
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.PayPlanCode2.value);//指定传入的参数
		mySql2.addSubPara(fm.PayPlanName.value);//指定传入的参数
		mySql2.addSubPara(fm.RiskCode.value);//指定传入的参数
		mySql2.addSubPara(fm.PayPlanCode.value);//指定传入的参数
  	sql = mySql2.getString();
  	Mulline11GridTurnPage.queryModal(sql,Mulline11Grid);
}
function button347()
{
  showInfo = window.open("");
}
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

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyPayLibInputSql4";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("sqlid");//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

