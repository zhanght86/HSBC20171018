//�������ƣ�PDSaleControl.js
//�����ܣ��������ۿ��ƶ���
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
//����sql�����ļ�
var tResourceName = "productdef.PDSaleControlInputSql";
var showInfo;	
function returnParent(){
	top.opener.focus();
	top.close();
}


function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
		myAlert(""+"����Ȩִ�д˲���"+"");
		return;
	}

	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
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
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara("sqlid");//ָ������Ĳ���
	strSQL = mySql.getString();
	Mulline9GridTurnPage.pageLineNum  = 3215;
	Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline10Grid(){
	var strSQL = "";
	var strSQL = "";
	var mySql=new SqlClass();
	var sqlid = "PDSaleControlInputSql2";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.all('RiskCode').value);//ָ������Ĳ���
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