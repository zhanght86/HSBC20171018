//ReadFromFileInput.js该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var filePath;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量


var tResourceName="bank.ReadFromFileInputSql";
var tResourceSQL1="ReadFromFileInputSql1";
var tResourceSQL2="ReadFromFileInputSql2";
var tResourceSQL3="ReadFromFileInputSql3";
var tResourceSQL4="ReadFromFileInputSql4"; 

//提交，保存按钮对应操作
function submitForm() {
  //if(verifyInput() == false) return false;  
  
  if (BankGrid.getSelNo()) {     
    var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    var tempaction = fm.action;					//每次要保存以前的路径（./ReadFromFileSave.jsp + "?filePath=" + filePath;）
    fm.action = fm.action + "&serialno=" + BankGrid.getRowColData(BankGrid.getSelNo()-1, 2)
                          + "&bankCode=" + BankGrid.getRowColData(BankGrid.getSelNo()-1, 1)
                          + "&bussType=" + dealType;
//		alert(fm.action);
    document.getElementById("fm").submit(); //提交
		fm.action = tempaction;             //提交后复原原有的路径
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
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  easyQueryClick();
}   

//获取文件上传保存路径
function getUploadPath() {
 // var strSql = "select SysVarValue from LDSysVar where SysVar = 'ReturnFromBankPath'";
 // var strSql = wrapSql(tResourceName,tResourceSQL1,[]); 
 
  
    var sqlid1=tResourceSQL1;
	var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara("");
  var strSql = mySql1.getString(); 
  
   filePath = easyExecSql(strSql);
   //alert(filePath);
  fm.action = "./ReadFromFileSave.jsp" + "?filePath=" + filePath;
}

// 查询按钮
function easyQueryClick() {
	// 书写SQL语句
	//var strSql = "select BankCode, Serialno, Outfile, StartDate from LYBankLog where InFile is null and ComCode like '" + comCode + "%%'"
	//           + tBankDataSQL
	//           + " and LogType='" + dealType + "'"
	//			     + getWherePart('BankCode')
	//			     + getWherePart('SendDate')
	//			     +" order by SerialNo desc";

 var tComCode = comCode;
   var tDealType = dealType;
   var tBankCode = document.all("BankCode").value;
   var tSendDate = document.all("SendDate").value;
   //var tBankDataSQL = tBankDataSQL
  
  // alert('2');
    var sqlid1="ReadFromFileInputSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(tComCode);//指定传入的参数
   // alert('3');
    mySql1.addSubPara(tBankDataSQL);//指定传入的参数
   // alert('4');
    mySql1.addSubPara(tDealType);//指定传入的参数
    mySql1.addSubPara(tBankCode);//指定传入的参数
    mySql1.addSubPara(tSendDate);//指定传入的参数


	//var strSql = wrapSql(tResourceName,tResourceSQL2,[document.all("comCode").value,tBankDataSQL,document.all("dealType").value,document.all("BankCode").value,document.all("StartDate").value]); 			     
  var strSql =  mySql1.getString(); 
  //alert(strSql);
	turnPage.queryModal(strSql, BankGrid);
}
