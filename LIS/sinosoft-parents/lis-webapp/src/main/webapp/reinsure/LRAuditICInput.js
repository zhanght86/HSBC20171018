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

//个人再保审核
function auditNewPolicy() {
  cOperateNo = fm.OperateNo.value;  //保单号码
  window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=01","window1");
}

//再保审核
function auditEdorPolicy() {
  cOperateNo = fm.OperateNo.value;  //保单号码
  window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=03","window1");
}

//再保审核
function auditClaimPolicy() {
  cOperateNo = fm.OperateNo.value;  //保单号码
  window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=04","window1");
}

//团体再保审核
function grpNewPolicy() {
	//查询grpContno下属于累计方案的新单
	divGrpTemp.style.display='';
	cOperateNo = fm.OperateNo.value;  //保单号码
	window.open("./ReInsureGrpInputMain.jsp?OperateNo="+cOperateNo+"&CodeType=05","window1");
}
function grpEdorPolicy(){
	//查询grpContno下属于累计方案的新单
	divGrpTemp.style.display='';
	cOperateNo = fm.OperateNo.value;  //保单号码
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


//提交前的校验、计算  
function beforeSubmit(){
  //添加操作	
}   

function afterCodeSelect(cCodeName, Field ){
	//选择了处理
}
