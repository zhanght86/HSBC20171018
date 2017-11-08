//ReadFromFileInput.js该文件中包含客户端需要处理的函数和事件

var showInfo;
var filePath;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量


var tResourceName="bank.CancelReadFromFileInputSql";
var tResourceSQL1="CancelReadFromFileInputSql1";

//提交，保存按钮对应操作
function submitForm() {
  if(document.all('BackReason').value.length=0){
  	alert("请录入回滚原因");
  	return;	
  } 
  if(document.all('BackReason').value.length>200){
  	alert("回滚原因字数超长，请录入200字以内");
  	return;	
  } 
  if (BankGrid.getSelNo()) {    
    var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
  }
  else {
    alert("请先选择一条批次号信息！"); 
  }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ) {
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}   

// 查询按钮
function easyQueryClick() {
  if(document.all('BankCode').value.length==0 && document.all('SerialNo').value.length==0){
  	alert("银行编码与批次号请至少录入一项再进行查询");
  	return;	
  }
	// 书写SQL语句
	var inFileSql = "";
	if(document.all('InFile').value.length>0){
		inFileSql = " and InFile like '%" + document.all('InFile').value + "%' ";
	}
	//var strSql = " select BankCode,(select bankname from ldbank where bankcode=a.bankcode),SerialNo,SUBSTR(InFile,INSTR(InFile,'/',-1)+1),ReturnDate,TotalNum, "
	//					 + "(select count(1) from LYReturnFromBank where SerialNo=a.serialno and banksuccflag='0000' ), "
	//					 + "SUBSTR(OutFile,INSTR(OutFile,'/',-1)+1),SendDate "
	//					 + " from LYBankLog a "
	//					 + "	where a.dealstate is null and ComCode like '" + comCode + "%%'  and LogType='" + dealType + "' "
	//			     + getWherePart('BankCode')
	//			     + getWherePart('SerialNo')
	//			     + getWherePart('ReturnDate')
	//			     + inFileSql
	//			     + tBankDataSQL
	//					 + " and exists (select 1 from LYReturnFromBank where SerialNo=a.serialno) "		
	//					 + " and not exists(select 1 from LYReturnFromBankB where SerialNo = a.serialno) "	     
	//					 + " order by SerialNo desc";	
	var strSql = wrapSql(tResourceName,tResourceSQL1,[comCode,dealType,document.all('BankCode').value,document.all('SerialNo').value,document.all('ReturnDate').value,inFileSql,tBankDataSQL]);			     
  //prompt('',strSql);
	turnPage.queryModal(strSql, BankGrid);
	if(BankGrid.mulLineCount == 0){
		alert("查无结果");	
	}
}
