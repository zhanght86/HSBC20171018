//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");	var name='提示';   //网页名称，可为空; 	var iWidth=550;      //弹出窗口的宽度; 	var iHeight=350;     //弹出窗口的高度; 	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);	showInfo.focus();
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");	var name='提示';   //网页名称，可为空; 	var iWidth=550;      //弹出窗口的宽度; 	var iHeight=350;     //弹出窗口的高度; 	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);	showInfo.focus();
	}
}

function SubmitForm()
{
	if(fm.Bdate.value == "" || fm.Edate.value == "")
	{
		myAlert(I18NMsg("M0000250551"));
	}
	else
	{
     var i = 0;
     var showStr=I18NMsg("M0000050071");
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  	 var name='提示';   //网页名称，可为空; 	var iWidth=550;      //弹出窗口的宽度; 	var iHeight=250;     //弹出窗口的高度; 	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);	showInfo.focus();
     fm.submit();
  }
}
