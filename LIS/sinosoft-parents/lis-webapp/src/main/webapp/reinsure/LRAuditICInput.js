var showInfo;

var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;

function myonfocus(){
	if(showInfo!=null){
	  try{
	    showInfo.focus();  
	  }
	  catch(ex){
	    showInfo=null;
	  }
	}
}

//�����ٱ����
function auditNewPolicy() {
  cOperateNo = fm.OperateNo.value;  //��������
  window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=01","window1");
}

//�ٱ����
function auditEdorPolicy() {
  cOperateNo = fm.OperateNo.value;  //��������
  window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=03","window1");
}

//�ٱ����
function auditClaimPolicy() {
  cOperateNo = fm.OperateNo.value;  //��������
  window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=04","window1");
}

//�����ٱ����
function grpNewPolicy() {
	//��ѯgrpContno�������ۼƷ������µ�
	divGrpTemp.style.display='';
	cOperateNo = fm.OperateNo.value;  //��������
	window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=05","window1");
}
function grpEdorPolicy(){
	//��ѯgrpContno�������ۼƷ������µ�
	divGrpTemp.style.display='';
	cOperateNo = fm.OperateNo.value;  //��������
	fm.action = "./AutoGrpTempCessChk.jsp?OpeData="+cOperateNo+"&OpeFlag=06";
	fm.submit();
}

function afterQuery(){
}

function afterSubmit(FlagStr,content,Result)
{
	if(showInfo!=null){
  	showInfo.close();
	}
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    do{
    	Result = Result.replace(";","\n");
    }while(Result.indexOf(";")>-1)
    
    
    fm.Remark.value=Result;
  }
}


//�ύǰ��У�顢����  
function beforeSubmit(){
  //��Ӳ���	
}   

function afterCodeSelect(cCodeName, Field ){
	//ѡ���˴���
}
