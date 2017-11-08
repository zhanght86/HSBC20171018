

//程序名称：PDSugIncomeData.js
//程序功能：收益数据算法定义
//创建日期：2011-10-13
var showInfo;
//定义sql配置文件
var tResourceName = "productdef.PDSugInsureControlEleInputSql";
var turnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass(); 

function queryMulline10Grid(){
    var strSQL = "";
    var mySql=new SqlClass();
	var sqlid = "PDSugInsureControlEleInputSql1";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(riskcode);
	strSQL = mySql.getString();
	Mulline10GridTurnPage.pageLineNum  = 10;
    Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

function save(){
	fm.all("operator").value="save";
	submitForm();
}
function update(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条记录!"+"");
		return;	
	}
	fm.all("operator").value="update";
	submitForm(); 
}
function del(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条记录!"+"");
		return;	
	}	
	fm.all("operator").value="del";
	submitForm();
}

function controlValueChange(conVal){
	if(conVal == '1'){
		document.getElementById("RELASHOWFLAGTr").style.display ='';
		document.getElementById("RELAVALUESQLTr").style.display ='none';
		document.getElementById("RELASHOWVALUETr").style.display ='none';
	}else if(conVal == '2'){
		document.getElementById("RELASHOWFLAGTr").style.display ='none';
		document.getElementById("RELAVALUESQLTr").style.display ='';
		document.getElementById("RELASHOWVALUETr").style.display ='none';
	}else if(conVal == '3'){
		document.getElementById("RELASHOWFLAGTr").style.display ='none';
		document.getElementById("RELAVALUESQLTr").style.display ='none';
		document.getElementById("RELASHOWVALUETr").style.display ='';
	}else if(conVal == ''|| conVal == null){
		document.getElementById("RELASHOWFLAGTr").style.display ='';
		document.getElementById("RELAVALUESQLTr").style.display ='';
		document.getElementById("RELASHOWVALUETr").style.display ='';
	}
}
function returnParent(){
	top.opener.focus();
	top.close();
}

function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
	  	myAlert(""+"您无权执行此操作"+"");
	  	return;
	}
	if(!checkNullable()){
		return false;
	}
	if(fm.all("FUNCTIONTYPE").value == "1"){
		if(fm.all("RELASHOWFLAG").value == null||fm.all("RELASHOWFLAG").value==''){
			myAlert(""+"是否显隐不能为空"+"");
	  		return;
		}
	}
	if(fm.all("FUNCTIONTYPE").value == "2"){
		if(fm.all("RELAVALUESQL").value == null||fm.all("RELAVALUESQL").value==''){
			myAlert(""+"备用项不能为空"+"");
	  		return;
		}
	}
	if(fm.all("FUNCTIONTYPE").value == "3"){
		if(fm.all("RELASHOWVALUE").value == null||fm.all("RELASHOWVALUE").value==''){
			myAlert(""+"默认值不能为空"+"");
	  		return;
		}
	}
    lockPage(""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"");
    fm.submit();
}
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }
  
  return true;
}
function afterSubmit( FlagStr, content ){
	unLockPage();
	if (FlagStr == "Fail" ){             
	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	}
	else{
  	    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		initForm();
	}
}


