//WriteToFileInput.js该文件中包含客户端需要处理的函数和事件

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var showInfo;
var mDebug="0";
var tSelNo = "";
var filePath = "";

var tResourceName="bank.RenewBankConfirmSql";
var tResourceSQL1="RenewBankConfirmSql1";
var tResourceSQL2="RenewBankConfirmSql2";
var tResourceSQL3="RenewBankConfirmSql3";



//提交，保存按钮对应操作
function submitForm() {
  //if(verifyInput() == false) return false;  
  
  if (BankGrid.getSelNo()) { 
    document.all("serialNo").value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);
    document.all("fmtransact").value = "create"
    
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
    fm.action="./WriteToFileSave.jsp";
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
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  easyQueryClick();
}

//选择进行文件下载
function fileDownload() {
  if (BankGrid.getSelNo()) { 
    //alert(fileUrl);
    //fileUrl.href = filePath + BankGrid.getRowColData(BankGrid.getSelNo()-1, 2);
    //fileUrl.click();
    document.all('Url').value = filePath;
    document.all('fmtransact').value = "download";
    fm.action="./WriteToFileSave.jsp";
    document.getElementById("fm").submit();
  }
  else {
    alert("请先选择一条批次号信息，将下载对应的文件！"); 
  } 
}

function afterSubmit2( FlagStr, content )
{
	unlockScreen('lkscreen');  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  try { showInfo.close(); } catch(e) {}
	alert(content);
  easyQueryClick();
  fm.sendBankFileButton.disabled = false;
}
function sendBankFile() 
{
  if (BankGrid.getSelNo()) 
  { 
    //alert("sendBankFile");
    document.all("serialNo").value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);
    document.all('fmtransact').value = "send";
    
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');
    fm.sendBankFileButton.disabled = true;
    fm.action="./SendBankFileSave.jsp";
    document.getElementById("fm").submit();
  }
  else 
  {
    alert("请先选择一条批次号信息"); 
  } 
}

function viewBatDetail() 
{
  if (BankGrid.getSelNo()) 
  { 
   //alert("viewBatDetail");
   window.open("BankSerDetailMain.jsp?SerialNo="+BankGrid.getRowColData(BankGrid.getSelNo()-1, 1));
  }
  else 
  {
    alert("请先选择一条批次号信息"); 
  } 
}

function initBankFileDealDiv()
{
	if(tBankFlag == "1")  //如果是邮保通批量代付代扣，显示对应的按钮
	{
		DivBankFileDeal.style.display="none";
		DivYBTBankFileDeal.style.display="";
		fm.YBTBankCode.value = tYBTBankCode;
	}
	
}

//提交后自动弹出文件下载
function downAfterSubmit(cfilePath,cflag) {
 //对于非批量代扣代付，下载文件
 //alert("BankFlag: "+tBankFlag);
 if(tBankFlag != "1")
 {
	filePath = cfilePath;
	
	if (cflag == 0)
	{
		if(BankGrid.getRowColData(BankGrid.getSelNo()-1, 2)==""){
			alert("No File");
			return;
		}
		fileUrl.href = filePath + BankGrid.getRowColData(BankGrid.getSelNo()-1, 2);
 	}
	else
	{
  	fileUrl.href = filePath + turnPage.arrDataCacheSet[tSelNo][1];
	}
	//alert(fileUrl.href);
  fileUrl.click();
 }
}

//获取文件下载路径
function getFilePath() {
  //var strSql = "select SysVarValue from LDSysVar where SysVar = 'DisplayBankFilePath'";
  var strSql = wrapSql(tResourceName,tResourceSQL1,[]); 
  filePath = easyExecSql(strSql);
  //alert(filePath);
}

// 查询按钮
function easyQueryClick() {
	// 书写SQL语句
	//var strSql = "select SerialNo, OutFile from LYBankLog where InFile is null and ComCode like '" + comCode + "%%'"
	//           + tBankDataSQL
	//           + " and LogType='" + dealType + "'"
	//           //+ " and bankcode like '03%' "
	//			     + getWherePart('BankCode')
	//			     + getWherePart('StartDate')
	//			     +" order by SerialNo desc";
	var strSql = wrapSql(tResourceName,tResourceSQL2,[document.all("comCode").value,tBankDataSQL,document.all("dealType").value,document.all("BankCode").value,document.all("StartDate").value]); 			     
  //alert(strSql);
	turnPage.queryModal(strSql, BankGrid);
}

//
function showStatistics(parm1, parm2) {
  //var strSql = "select Totalmoney, Totalnum from lybanklog where SerialNo = '" 
  //           + BankGrid.getRowColData(BankGrid.getSelNo() - 1, 1) 
  //           + "'";
  var strSql = wrapSql(tResourceName,tResourceSQL3,[BankGrid.getRowColData(BankGrid.getSelNo() - 1, 1)]); 			               
  //alert(easyExecSql(strSql));      
  var arrResult = easyExecSql(strSql);
  
  
  document.all("TotalMoney").value = arrResult[0][0];
  document.all("TotalNum").value = arrResult[0][1];
  
  tSelNo = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
}
