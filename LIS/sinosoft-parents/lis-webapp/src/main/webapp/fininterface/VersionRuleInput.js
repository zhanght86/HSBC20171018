//Creator :范昕	
//Date :2008-08-21

var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) //shwoInfo是什么？
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

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
  function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}



function RulesVersionQuery()
{
	window.open("./FrameVersionRuleQuery.jsp");
}

function RulesVersionTraceQuery()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询！");
  	return;
  }
  showInfo=window.open("./FrameRulesVersionTraceQuery.jsp?VersionNo=" + VersionNo +"");
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('StartDate').value = arrResult[0][1];
		document.all('EndDate').value = arrResult[0][2];
		document.all('VersionReMark').value = arrResult[0][3];
		document.all('VersionState').value = arrResult[0][5];
		
		document.all('Maintenanceno').value = '';
		document.all('MaintenanceReMark').value = '';
		document.all('MaintenanceState').value = '';
		document.all('TraceVersionNo').value = '';
	}
}

function afterQuery1(arrQueryResult)
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('Maintenanceno').value = arrResult[0][0];
		document.all('MaintenanceReMark').value = arrResult[0][3];
		document.all('MaintenanceState').value = arrResult[0][2];
		document.all('TraceVersionNo').value = arrResult[0][1];
	}
}

function addVersion()
{
	 if((fm.StartDate.value=="")||(fm.StartDate.value=="null"))
  {
    alert("请您录入生效日期！！！");
    return false;
  }
  if((fm.VersionNo.value!=""))
	{
		alert("版本编号已存在，请刷新页面后再进行此操作！")
		return false;
	}
	if((fm.VersionReMark.value=="")||(fm.VersionReMark.value=="null"))
  {
    alert("请您录入版本描述！！！");
    return false;
  }
	if (confirm("您确实想进行添加版本的操作吗?"))
  {
    fm.OperateType.value = "addVersion";
    var i = 0;
    var showStr="正在添加版本，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了本次操作！");
  }
}

function deleteVersion()
{
	if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
  {
    alert("请您先进行版本信息查询！");
    return false;
  }
	if (confirm("您确实想进行删除版本的操作吗?"))
  {
    fm.OperateType.value = "deleteVersion";
    var i = 0;
    var showStr="正在删除版本，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了本次操作！");
  }
}

function applyAmend()
{
	if((fm.VersionNo.value=="null")||(fm.VersionNo.value==''))
  {
  	alert("请先录入版本编号");
  	return false;
  }
  if((fm.MaintenanceReMark.value=="null")||(fm.MaintenanceReMark.value==''))
  {
  	alert("请录入维护描述");
  	return false;
  }
  if((fm.Maintenanceno.value!=""))
	{
		alert("维护编号已存在，请刷新页面后再进行此操作！")
		return false;
	}
	if (confirm("您确定要提交本次修改的内容吗?"))
  {
    fm.OperateType.value = "applyAmend";
    var i = 0;
    var showStr="正在申请修改，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了本次操作！");
  }
}

function CompleteAmend()
{
	if((fm.VersionNo.value=="null")||(fm.VersionNo.value==''))
  {
  	alert("请先录入版本编号");
  	return false;
  }
	if((fm.Maintenanceno.value=="null")||(fm.Maintenanceno.value==''))
  {
		alert("请先进行财务规则版本轨迹查询");
  	return false;
  }
  if (confirm("您确定要提交本次修改的内容吗?"))
  {
    fm.OperateType.value = "CompleteAmend";
    var i = 0;
    var showStr="正在申请修改，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了本次操作！");
  }
}

function cancelAmend()
{
	if((fm.VersionNo.value=="null")||(fm.VersionNo.value==''))
  {
  	alert("请先添加版本或进行账务规则版本维护轨迹查询");
  	return false;
  }
	if((fm.Maintenanceno.value=="null")||(fm.Maintenanceno.value==''))
  {
		alert("请先进行财务规则版本轨迹查询");
  	return false;
  }

  if (confirm("您确定要提交本次修改的内容吗?"))
  {
    fm.OperateType.value = "cancelAmend";
    var i = 0;
    var showStr="正在申请修改，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './VersionRuleSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了本次操作！");
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
		mOperate="";
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
    mOperate="";
  }
  if(fm.OperateType.value = "cancelAmend")
  {
  	document.all('Maintenanceno').value = '';
  	document.all('TraceVersionNo').value = '';
  	document.all('MaintenanceState').value = '';
  	document.all('MaintenanceReMark').value = '';
  }
  if(fm.OperateType.value = "deleteVersion")
  {
  	document.all('VersionNo').value = '';
		document.all('StartDate').value = '';
		document.all('EndDate').value = '';
		document.all('VersionReMark').value = '';
		document.all('VersionState').value = '';
  	document.all('Maintenanceno').value = '';
  	document.all('TraceVersionNo').value = '';
  	document.all('MaintenanceState').value = '';
  	document.all('MaintenanceReMark').value = '';
  }
}