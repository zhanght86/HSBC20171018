var showInfo;
var mDebug="1";
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) 
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//Click事件，当点击增加图片时触发该函数
function submitForm()
{
	 
  if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
  fm.OperateType.value = "INSERT||MAIN";
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
  fm.action = './AccountantPeriodSave.jsp';
  document.getElementById("fm").submit(); //提交
}


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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.OperateType.value = "";
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if(fm.OperateType.value == "DELETE||MAIN")
    {
    	document.all('Year').value = '';
    	document.all('Month').value = '';  
  		document.all('StartDay').value = '';
    	document.all('EndDay').value='';           
    	document.all('State').value = '';
    	document.all('StateName').value = ''; 
    	
    	document.all('Year').readOnly = false;
    }
    fm.OperateType.value = "";
  }      
}



//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
  if (confirm("您确实想修改该记录吗?"))
   {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './AccountantPeriodSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
}
//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //fm.OperateType.value="QUERY";
  //fm.CertifyCode_1.value = "";
  window.open("./FrameAccountantPeriodQuery.jsp");
}

function deleteClick()
{
	if((fm.Year.value=="")||(fm.Year.value=="null"))
  {
    alert("年度信息为空！！！");
    return false;
  }
  if((fm.Month.value=="")||(fm.Month.value=="null"))
  {
    alert("月度信息为空！！！");
    return false;
  }
  if (confirm("您确实要删除该记录吗？"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或连接其他的界面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './AccountantPeriodSave.jsp';
    document.getElementById("fm").submit();//提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您已经取消了修改操作！");
  }
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('Year').value = arrResult[0][0];
		document.all('Month').value = arrResult[0][1];
		document.all('StartDay').value = arrResult[0][2];
		document.all('EndDay').value = arrResult[0][3];
		document.all('Operator').value = arrResult[0][6];
		document.all('State').value = arrResult[0][4];
		showCodeName(); 
		document.all('Year').readOnly = true;
	}
}

function beforeSubmit()
{
	if((fm.Year.value=="")||(fm.Year.value=="null"))
  {
    alert("请您录入年度！！！");
    return false;
  }
  if((fm.Month.value=="")||(fm.Month.value=="null"))
  {
    alert("请您录入月度！！！");
    return false;
  }
  if((fm.StartDay.value=="")||(fm.StartDay.value=="null"))
  {
  	alert("请输入该区间的起始日期！");
  	return false;
  }
  if((fm.EndDay.value=="")||(fm.EndDay.value=="null"))
  {
  	alert("请输入该区间的结束日期！");
  	return false;
  }
  if((fm.State.value==""||fm.State.value=="null"))
  {
  	alert("请输入该区间的状态！");
  	return false;
  }
  if(trim(document.all('Year').value).length!=4)
	{  
  	alert("请输入四位数字的年度！");
  	return false;
	}
 if(trim(document.all('Month').value).length!=2)
	{  
  	alert("请输入两位数字的月度！");
  	return false;
	}
	if(fm.EndDay.value <= fm.StartDay.value)
	{
		alert("月度止期不得大于或等于月度起期！");
		return false;
	}
  return true;
}

function resetAgain()
{
			document.all('Year').value = '';
    	document.all('Month').value = '';  
  		document.all('StartDay').value = '';
    	document.all('EndDay').value='';           
    	document.all('State').value = '';
    	document.all('StateName').value = ''; 
    	
    	document.all('Year').readOnly = false;
}