//程序名称：PDSaleControl.js
//程序功能：险种销售控制定义
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
//定义sql配置文件
var tResourceName = "productdef.PDSaleControlInputSql";
var showInfo;	
function returnParent(){
	top.opener.focus();
	top.close();
}


function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
		myAlert(""+"您无权执行此操作"+"");
		return;
	}

	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
	fm.submit();
}

function afterSubmit( FlagStr, content ){
	showInfo.close();
	if (FlagStr == "Fail" ){             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    	initForm();    
  } 
}


function save(){
	fm.all("operator").value="save";
	submitForm();
}
function update(){
	fm.all("operator").value="update";
	submitForm();
}
function del(){
	fm.all("operator").value="del";
	submitForm();
}
function button341(){
	showInfo = window.open("");
}

function queryMulline9Grid(){
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDSaleControlInputSql1";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara("sqlid");//指定传入的参数
	strSQL = mySql.getString();
	Mulline9GridTurnPage.pageLineNum  = 3215;
	Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline10Grid(){
	var strSQL = "";
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDSaleControlInputSql2";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.all('RiskCode').value);//指定传入的参数
	strSQL = mySql.getString();
	//Mulline10GridTurnPage.pageLineNum  = 3215;
	Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function isshowbutton(){   
	var value=getQueryState1();
	if(value=='0'||value==0){
	document.getElementById('savabuttonid').style.display = 'none';
	//document.getElementById('baseInfoDoneId').disabled=true;
	}
}