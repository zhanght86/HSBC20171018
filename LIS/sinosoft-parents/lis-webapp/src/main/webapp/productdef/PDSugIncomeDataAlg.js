

//�������ƣ�PDSugIncomeData.js
//�����ܣ����������㷨����
//�������ڣ�2011-10-13

var showInfo;
//����sql�����ļ�
var tResourceName = "productdef.PDSugIncomeDataAlgInputSql";
var turnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass(); 


function controlValueChange(conVal){
	if(conVal == '1'){
		document.getElementById("REMARKTitile").style.display ='none';
		document.getElementById("REMARKInput").style.display ='none';
		document.getElementById("MaxMin").style.display ='';
	}else if(conVal == '2'){
		document.getElementById("REMARKTitile").style.display ='none';
		document.getElementById("REMARKInput").style.display ='none';
		document.getElementById("MaxMin").style.display ='';
	}else if(conVal == '3'){
		document.getElementById("REMARKTitile").style.display ='';
		document.getElementById("REMARKInput").style.display ='';
		document.getElementById("MaxMin").style.display ='none';
	}else if (conVal == '' || conVal == null){
		document.getElementById("REMARKTitile").style.display ='';
		document.getElementById("REMARKInput").style.display ='';
		document.getElementById("MaxMin").style.display ='';
	}
}
function queryMulline10Grid(){
    var strSQL = "";
    var mySql=new SqlClass();
	var sqlid = "PDSugIncomeDataAlgInputSql1";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(riskcode);//ָ������Ĳ��� ���ִ���
	mySql.addSubPara(proceedscode);//ָ������Ĳ��� ������Ŀ����
	strSQL = mySql.getString();
	Mulline10GridTurnPage.pageLineNum  = 10;
    Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

function terms(){
	showInfo = window.open("PDSugIncomeConditionMain.jsp?riskcode="+riskcode);
}

function newFactor(){
	showInfo = window.open("PDSugIncomeFactorMain.jsp?riskcode="+riskcode+"&operator=save");
}
function update(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"��ѡ��һ����¼!"+"");
	 	return;	
	}
    var itemcode = document.getElementById('hiddenItemCode').value;
    var calelement = document.getElementById('hiddenCalElement').value;
	showInfo = window.open("PDSugIncomeFactorMain.jsp?riskcode="+riskcode+"&itemcode="+itemcode+"&calelement="+calelement+"&operator=update");
}
function del(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"��ѡ��һ����¼!"+"");
		return;	
	}	
	fm.action = "./PDSugIncomeFactorSave.jsp";
	fm.all("operator").value="del";
	fm.all("tablename").value="PD_CalcuteElemet";
	submitForm();
}
function cannelFactor(){
	returnParent();
}

function returnParent(){
	top.opener.focus();
	top.close();
}
 
function save(){
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
	}
	else{
  	    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		if(document.getElementById("operator").value =='del' ){
			init();
		}else{
			top.opener.initForm();
			returnParent();	
		}
		
	}
}

