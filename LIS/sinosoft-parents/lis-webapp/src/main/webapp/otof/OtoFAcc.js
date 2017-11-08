var showInfo;
var turnPage = new turnPageClass(); 

function easyprint()
{
	if(document.all('bDate').value=="")
	{
		alert("请选择起始日期");
		return ;
		}
			if(document.all('eDate').value=="")
	{
		alert("请选择终止日期");
		return ;
		}
			if(document.all('RiskCode').value=="")
	{
		alert("请选择险种代码");
		return ;
		}
			if(document.all('ManageCom').value=="")
	{
		alert("请选择管理机构");
		return ;
		}
		var bDate=document.all('bDate').value;
		var eDate=document.all('eDate').value;
		if(dateDiff(bDate,eDate,"D")<0)
		{
			 alert("起始日期不能大于终止日期，请重新选择");
		   return ;
			}
	  fm.fmAction.value="Print";
	  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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



function afterSubmit( FlagStr, content )
{
	  showInfo.close();
	  if (FlagStr == "Fail" )	  
	  {   	    
		    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
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

	if(document.all('bDate').value=="")
	{
		alert("请选择起始日期");
		return ;
		}
			if(document.all('eDate').value=="")
	{
		alert("请选择终止日期");
		return ;
		}

			if(document.all('ManageCom').value=="")
	 {
		alert("请选择管理机构");
		return ;
		}
		
		var bDate=document.all('bDate').value;
		var eDate=document.all('eDate').value;
		if(dateDiff(bDate,eDate,"D")>1)
    {
  	if(window.confirm("建议您提取一天的数据，您确定继续提取吗?"))
  	{
  		  fm.fmAction.value="Certifi";
        document.all('distill').disabled = true;
        document.getElementById("fm").submit();
  		}
  	}
  		else
  			{
  			fm.fmAction.value="Certifi";
        document.all('distill').disabled = true;
        document.getElementById("fm").submit();
  				}
  	

}

