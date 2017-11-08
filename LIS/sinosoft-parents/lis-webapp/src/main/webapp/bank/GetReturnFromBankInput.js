//GetReturnFromBankInput.js该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var showInfo;
var mDebug="0";
var tFees;

var tResourceName="bank.GetReturnFromBankSql";
var tResourceSQL1="GetReturnFromBankSql1";



//提交，保存按钮对应操作
function submitForm() {
  if(verifyInput() == false) return false;
  
  if (BankGrid.getSelNo()) { 
    document.all("serialNo").value = BankGrid.getRowColData(BankGrid.getSelNo()-1 , 1);
    
    var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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

function initQuery() {
 // var strSql = "select SerialNo,BankCode,SUBSTR(InFile,INSTR(InFile,'/',-1)+1),ReturnDate from LYBankLog a where DealState is null and ComCode like '" + comCode + "%%' and LogType='S' order by SerialNo desc";
  var tComCode =comCode;
 
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(tResourceSQL1);//指定使用的Sql的id
    mySql1.addSubPara(tComCode);//指定传入的参数
 
         
//	alert('no save11121');
	    var strSql =mySql1.getString();	
  turnPage.queryModal(strSql, BankGrid);
} 
