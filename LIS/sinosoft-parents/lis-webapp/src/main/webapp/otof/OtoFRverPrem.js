var showInfo;


function OtoFReYCom()
{
			var comcode = fm.all('ManageCom').value;
			var bdate = fm.all('Bdate').value;
	    var edate = fm.all('Edate').value;
	    
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构！");
			return;
		}

	  if (bdate==""||bdate==null || edate=="" ||edate==null)
	  {
	      alert("请录入起止日期！!");
	      return ;
	  }

	if(fm.AccountDate.value=="")
	  {
	  	    alert("请您录入冲销日期！");
		    return false;	
	  }


  
	  if (confirm("您确实想冲销该财务凭证吗?"))
	  {
			  var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
			  var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  fm.Opt.value="YComm";
			  fm.submit(); 
	  }
}

function OtoFRePrem()
{
			var comcode = fm.all('ManageCom').value;
			var bdate = fm.all('Bdate').value;
	    var edate = fm.all('Edate').value;
	    
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构！");
			return;
		}

	  if (bdate==""||bdate==null || edate=="" ||edate==null)
	  {
	      alert("请录入起止日期！!");
	      return ;
	  }

	if(fm.AccountDate.value=="")
	  {
	  	    alert("请您录入冲销日期！");
		    return false;	
	  }


  
	  if (confirm("您确实想冲销该财务凭证吗?"))
	  {
			  var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
			  var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  fm.Opt.value="Prem";
			  fm.submit(); 
	  }
}

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