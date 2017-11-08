var mOperate = "";
var showInfo;
window.onfocus = myonfocus;
function myonfocus()
{
	if(showInfo != null)
	{
		try
		{
			showInfo.focus();
		}
		catch (ex)
		{
			showInfo = null;
		}
	}
}
function submitForm()
{
	if(verifyInput2() == false )
	{
		return false;
	}
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all("fmtransact").value = mOperate;
	document.getElementById("fm").submit();
}
function showSubmitFrame(cDebug)
{
	if(cDebug == "1")
	{
		parent.fraMain.rows = "0,0,50,82,*";
	}
	else
	{
		parent.fraMain.rows = "0,0,0,82,*";
	}
}
function afterSubmit(FlagStr,content)
{
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");/
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}
function resetForm()
{
	try
	{
		fm.reset();
		initForm();
    }
	catch(ex)
	{
		alert("-->InitForm函数中发生异常:初始化界面错误!");
	} 
}
function addClick()
{  
	mOperate = "INSERT||MAIN";
	if(document.all("PrintID").value != "")
	{
		alert("已存在打印号码，请重置！");
		return;
	}
	submitForm();
}
function deleteClick()
{
	if( verifyInput2() == false )
	{	  
		return false;
	}
	if(confirm("您确实想删除该记录吗?"))
	{
		var i = 0;
		var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus(); 
		fm.fmtransact.value = "DELETE||MAIN";
		document.getElementById("fm").submit(); //提交
		initForm();
	}
	else
	{
		alert("您取消了删除操作！");
	}
}
function updateClick()
{
	if(verifyInput2() == false)
	{
		return false;
	}
	if(confirm("您确实想修改该记录吗？"))
	{
		mOperate = "UPDATE||MAIN";
		submitForm();
	}
	else
	{
		alert("您取消了修改操作！");
	}
}
function queryClick()
{
	mOperate = "QUERY||MAIN";
	showInfo = window.open("./LPrtDefinitionQuery.jsp");
}
function afterQuery(arrQueryResult)
{
	var arrResult = new Array();
	if(arrQueryResult != null)
	{
		var sqlId1 = "LPrtDefinitionInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtDefinitionInputSql");//指定使用的properties文件名
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(arrQueryResult[0][0]);//指定传入的参数
	 	arrResult = easyExecSql(mySql1.getString());
	 	document.all('PrintID').value = arrResult[0][0];
	 	document.all('PrintName').value = arrResult[0][1];
	 	document.all('PrintObject').value = arrResult[0][2];
	 	document.all('PrintObjectName').value = arrResult[0][3];
	 	document.all('PrintType').value = arrResult[0][4];
	 	document.all('PrintTypeName').value = arrResult[0][5];
	 	document.all('LanguageType').value = arrResult[0][6];
	 	document.all('LanguageTypeName').value = arrResult[0][7];
	 	document.all('PrintID').readOnly = true;
	}
}




