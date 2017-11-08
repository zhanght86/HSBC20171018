//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	fm.all('distill').disabled = false;
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
	}
}


function SubmitForm()
{
	if(fm.all('ManageCom').value=="")
	{
		alert("请选择提取的管理机构！");
		return false;
	}

	if(fm.all('VouchNo').value!="")
	{
			var i = fm.all('VouchNo').value;
			if(!isNumeric(i))
	    {
				alert("录入的凭证序号必须为数字");
				return false;
		  }
		  if(i>=9 || i<=0)
		  {
				alert("该界面只提取凭证号为1-8的业务凭证");
				return ;
		  }	
	}
	
	if(fm.all('Bdate').value=="" || fm.all('Edate').value=="")
	{
		alert("请录入起止日期！");
		return false;
	}
	
	fm.Opt.value="WT";
  var sdate = fm.all('Bdate').value;
	var edate = fm.all('Edate').value;
	if(dateDiff(sdate,edate,"D")>1)
	{
	  	if(window.confirm("建议您提取一天的数据，您确定继续提取吗?"))
	  	{	
	       fm.all('distill').disabled = true;
	       fm.submit();
	  	}
	}
	else
	{
	   fm.all('distill').disabled = true;
	   fm.submit();
	  		
	}
}



