

//�������ƣ�PDSugIncomeData.js
//�����ܣ����������㷨����
//�������ڣ�2011-10-13

var showInfo;
//����sql�����ļ�
var tResourceName = "productdef.PDSugIncomeDataInputSql";
var turnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass(); 
function incomeHeadButton(){
	showInfo = window.open("PDSugIncomeTabHeadMain.jsp?riskcode="+riskcode);
}
function queryMulline10Grid(){
    var strSQL = "";
    var mySql=new SqlClass();
	var sqlid = "PDSugIncomeDataInputSql1";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(riskcode);//ָ������Ĳ���
	strSQL = mySql.getString();
	Mulline10GridTurnPage.pageLineNum  = 10;
    Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}


function newIncome(){
	showInfo = window.open("PDSugIncomeDataAlgMain.jsp?riskcode="+riskcode+"&operator=save");
}
function update(){

	 var selNo = Mulline10Grid.getSelNo();
	 if( selNo == 0 || selNo == null ){
	 	myAlert(""+"��ѡ��һ����¼!"+"");
	 	return;	
	 }
	 var proceedscode = document.getElementById("PROCEEDSCODE").value;
	 var terms = document.getElementById("TERMS").value;
	 showInfo = window.open("PDSugIncomeDataAlgMain.jsp?riskcode="+riskcode+"&proceedscode="+proceedscode+"&terms="+terms+"&operator=update"); 
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


