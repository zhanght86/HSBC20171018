

//程序名称：PDSugIncomeData.js
//程序功能：收益数据算法定义
//创建日期：2011-10-13
var showInfo;
//定义sql配置文件
var tResourceName = "productdef.PDSugStaticDocumentInputSql";
var turnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass(); 

function queryMulline10Grid(){
    var strSQL = "";
    var mySql=new SqlClass();
	var sqlid = "PDSugStaticDocumentInputSql1";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(riskcode);//指定传入的参数
	strSQL = mySql.getString();
	Mulline10GridTurnPage.pageLineNum  = 10;
    Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

function save(){
	fm.all("operator").value="save";
	submitForm();
}
function fieldUpload(){
	if(fm2.all('filepath2').value==""||fm2.all('filepath2').value==null){
		myAlert(""+"请选择上传文件！"+"");
		fm2.all('filepath2').focus();
		return ;
	}
	if(fm.all('ID').value==""||fm.all('ID').value==null){
		myAlert(""+"请选择文档类型！"+"");
		fm.all('ID').focus();
		return;
	}
	lockPage(""+"正在上传文件，请您稍候并且不要修改屏幕上的值或链接其他页面"+"");
	fm2.action="PDSugStaticDocumentUpload.jsp?operator=upload&filepath="+fm2.all('filepath2').value+"&id="+fm.all('ID').value;
	fm2.submit();
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


function typeManage(){
	showInfo = window.open("PDSugTypeManageMain.jsp");
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

