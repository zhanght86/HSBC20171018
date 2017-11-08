//               该文件中包含客户端需要处理的函数和事件
var showInfo;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    parent.fraInterface.document.all('compute').disabled = false;
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    parent.fraInterface.document.all('compute').disabled = false;
  }
}

function fileDownload() { 
	
 if( verifyInput() == false ) 
		return false; 
		var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus(); 
	var filename="GorupScanDetil_"+document.all('ManageCom').value+"_"+document.all('PrtNO').value+"_"+document.all('StartDay').value+"_"+document.all('EndDay').value+"_"+document.all('tjtype').value+".xls";

	fm.FileName.value=filename; 
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'TKReportCreat'";
	
	var sqlid1="GorupScanDetilSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("agentprint.GorupScanDetilSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	var strSql=mySql1.getString();
	
   filePath = easyExecSql(strSql);
 // filePath="d:/test/";
   fm.Url.value=filePath;
   fm.fmtransact.value = "download";
   fm.action="./GorupScanDetilSave.jsp";
   document.getElementById("fm").submit();}
 
function downAfterSubmit(cfilePath,fileName) { 
	showInfo.close();
  //alert("adadad");
  fileUrl.href = cfilePath + fileName; 
  //alert(fileUrl.href);
  //fileUrl.click();
  fm.action="./download.jsp?file="+fileUrl.href;
  document.getElementById("fm").submit();}
 
function fmsubmit()
{

	if( verifyInput() == false ) 
		return false; 
 	
		
		
		//parent.fraInterface.document.all('compute').disabled = true;
		
		var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
	var filename="GorupScanDetil_"+document.all('ManageCom').value+"_"+document.all('PrtNO').value+"_"+document.all('StartDay').value+"_"+document.all('EndDay').value+"_"+document.all('tjtype').value+".xls";

	fm.FileName.value=filename; 
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
//	var strSql = "select SysVarValue from LDSysVar where SysVar = 'TKReportCreat'";
	
	var sqlid1="GorupScanDetilSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("agentprint.GorupScanDetilSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	var strSql=mySql1.getString();
	
   filePath = easyExecSql(strSql);
 // filePath="d:/test/";
   fm.Url.value=filePath;
	　　fm.fmtransact.value = "Create";
	    fm.action="./GorupScanDetilSave.jsp";
	    document.getElementById("fm").submit();}

//确认框的回调函数
function ConfirmSelect()
{
	 showInfo.close();
	 
	 if(confirm("数据已经生成，是否重新计算?如果重算将覆盖原始数据。"))
	 {
	 	   var i = 0;
       var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	 	   //点确定之后将其值变为1 起到开关的作用
	 	   document.all('myconfirm').value = "1";
	 	   //相当于window.location重定向请求
	 	   document.getElementById("fm").submit();}
	else
		{
		}
}



	
