

//�������ƣ�PDSugIncomeData.js
//�����ܣ����������㷨����
//�������ڣ�2011-10-13
var showInfo;
//����sql�����ļ�
var tResourceName = "productdef.PDSugInsureControlEleInputSql";
var turnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass(); 

function queryMulline10Grid(){
    var strSQL = "";
    var mySql=new SqlClass();
	var sqlid = "PDSugInsureControlEleInputSql1";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
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
		myAlert(""+"��ѡ��һ����¼!"+"");
		return;	
	}
	fm.all("operator").value="update";
	submitForm(); 
}
function del(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"��ѡ��һ����¼!"+"");
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
	  	myAlert(""+"����Ȩִ�д˲���"+"");
	  	return;
	}
	if(!checkNullable()){
		return false;
	}
	if(fm.all("FUNCTIONTYPE").value == "1"){
		if(fm.all("RELASHOWFLAG").value == null||fm.all("RELASHOWFLAG").value==''){
			myAlert(""+"�Ƿ���������Ϊ��"+"");
	  		return;
		}
	}
	if(fm.all("FUNCTIONTYPE").value == "2"){
		if(fm.all("RELAVALUESQL").value == null||fm.all("RELAVALUESQL").value==''){
			myAlert(""+"�������Ϊ��"+"");
	  		return;
		}
	}
	if(fm.all("FUNCTIONTYPE").value == "3"){
		if(fm.all("RELASHOWVALUE").value == null||fm.all("RELASHOWVALUE").value==''){
			myAlert(""+"Ĭ��ֵ����Ϊ��"+"");
	  		return;
		}
	}
    lockPage(""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"");
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


