


function testSql(){
	if(fm.all("CALSQL").value==null||fm.all("CALSQL").value==""){
		myAlert(""+"����дSQL!"+"");
		fm.all("CALSQL").focus();
		return;
	}
	fm.all("operator").value = "testSql";
	fm.submit();
}

function dataTableDef(){
	showInfo = window.open("PDSugRateCashValueMain.jsp?riskcode=" + riskcode );
}

function cannelFactor(){
	returnParent();
}

function returnParent(){
	top.opener.focus();
	top.close();
}

function save(){
	fm.all("operator").value = operator;
	submitForm();
}

function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
	  	myAlert(""+"����Ȩִ�д˲���"+"");
	  	return;
	}
	if(!checkNullable()){
		return false;
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
	}else if(FlagStr == "testSql"){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	}else{
  	    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		top.opener.init();
		returnParent();
	}
}

