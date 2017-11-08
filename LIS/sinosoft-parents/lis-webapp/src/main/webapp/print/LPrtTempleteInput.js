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
//上载文件输入框和修改键隐藏
function fileUpdateClick()
{
	try
	{
		document.all("FilePath").style.display='';
		document.all("FilePath1").style.display='none';
		document.all("fileQueryClick").style.display='none';
	}
	catch(ex)
	{
		alert(ex.message);
	}
}
//上载文件输入框和修改键显示
function fileUpdateClick1()
{
	try
	{
		document.all("FilePath").style.display='none';
		document.all("FilePath1").style.display='';
		document.all("fileQueryClick").style.display='';
	}
	catch(ex)
	{
		alert(ex.message);
	}
}
function submitForm()
{
	if(beforeSubmit() == false )
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
function beforeSubmit()
{
	if(verifyInput2() == false)
	{
		return false;
	}
	if(verifyTemplete() == false )
	{
		return false;
	}
	return true;
}
function afterSubmit(FlagStr,content)
{
	showInfo.close();
	if(FlagStr == "Fail")
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}
function addClick()
{  
	mOperate = "INSERT||MAIN";
	if (document.all("TempleteID").value !="" )
	{
		alert("已存在该打印模板号码，请重置！")
		return false;
	}
	if(document.all("FilePath").value =="")
	{
		alert("文件不能为空！");
		return false;
	}
	submitForm();
	fileUpdateClick1();
}

//校验模板类型与上载文件类型是否相同
function verifyTemplete()
{
	var filePath = document.all("FilePath").value;
	var filePath1 = document.all("FilePath1").value;
	var filePath2;
	var tTempleteName = document.all("TempleteName").value;
	var tLanguage = document.all("Language").value;
	var	tTempleteType = document.all("TempleteType").value;
	var tDefaultType = document.all("DefaultType").value;
	var tTempleteID = document.all("TempleteID").value;
	if (filePath != "")
	{
		filePath2 = filePath;
	}
	else 
	{
		filePath2 = filePath1;
	}
	var fileLastName = filePath2.substring(filePath2.lastIndexOf(".") + 1);
	var templeteTypeName = document.all("TempleteTypeName").value;
	if(fileLastName!= templeteTypeName)
	{
		alert("模板类型与上载文件类型不符合!");
		return false;
	}
	if(mOperate == "INSERT||MAIN")
	{	
		var tResult1 = new Array();
		var sqlid1 = "LPrtTempleteInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtTempleteInputSql");
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tTempleteName);
		mySql1.addSubPara(tLanguage);
		mySql1.addSubPara(tTempleteType);
		tResult1 = easyExecSql(mySql1.getString());
		if(tResult1 != null)
		{
			if( tResult1[0][0] == tTempleteName && tResult1[0][1] == tLanguage 
					&& tResult1[0][2] == tTempleteType )
			{
				alert("打印模板已存在!");
				return false;
			}
		}
		else
		{
			var tResult2 = new Array();
			var sqlid2 = "LPrtTempleteInputSql2";
			var mySql2 = new SqlClass();
			mySql2.setResourceName("print.LPrtTempleteInputSql");
			mySql2.setSqlId(sqlid2);
			mySql2.addSubPara(tTempleteName);
			tResult2 = easyExecSql(mySql2.getString());
			if( tDefaultType == "Y"  && tResult2[0][0] != "0")
			{
				alert("该打印项目已存在默认模板!");
				return false;
			}
		}
	}
	if(mOperate == "UPDATE||MAIN")
	{	
		var tResult4 = new Array();
		var sqlid4 = "LPrtTempleteInputSql4";
		var mySql4 = new SqlClass();
		mySql4.setResourceName("print.LPrtTempleteInputSql");
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tTempleteName);
		mySql4.addSubPara(tLanguage);
		mySql4.addSubPara(tTempleteType);
		mySql4.addSubPara(tTempleteID);
		tResult4 = easyExecSql(mySql4.getString());
		if(tResult4 != null)
		{	
			if( tResult4[0][0] == tTempleteName && tResult4[0][1] == tLanguage 
					&& tResult4[0][2] == tTempleteType )
			{
				alert("打印模板已存在!");
				return false;
			}
		}
		else
		{
			var tResult3 = new Array();
			var sqlid3 = "LPrtTempleteInputSql3";
			var mySql3 = new SqlClass();
			mySql3.setResourceName("print.LPrtTempleteInputSql");
			mySql3.setSqlId(sqlid3);
			mySql3.addSubPara(tTempleteName);
			mySql3.addSubPara(tTempleteID);
			tResult3 = easyExecSql(mySql3.getString());
			if(tDefaultType == "Y" && tResult3[0][0] != "0")
			{
				alert("该打印项目已存在默认模板!");
				return false;
			}
		}
	}
}
function deleteClick()
{
	if( verifyInput2() == false ) return false;
	if(document.all("TempleteID").value =="" )
	{
		alert("请先查询！");
	}
	else
	{
		if (confirm("您确实想删除该记录吗?"))
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
			document.getElementById("fm").submit(); // 提交
			resetForm();
		}
		else
		{
			alert("您取消了删除操作！");
		}
	}
}
function resetForm()
{
	try
	{
		fm.reset();
		initForm();
		fileUpdateClick();
    }
	catch(ex)
	{
		alert("-->InitForm函数中发生异常:初始化界面错误!");
	} 
}
function updateClick()
{
	if(verifyInput2() == false)
	{
		return false;
	}
	if(document.all("TempleteID").value =="" )
	{
		alert("请先查询！");
	}
	else
	{	
		if(confirm("您确实想修改该记录吗?"))
		{
			mOperate = "UPDATE||MAIN";
			submitForm();
			fileUpdateClick1();
		}
		else
		{
			alert("您取消了修改操作！");
		}
	}
}
function queryClick()
{
	mOperate = "QUERY||MAIN";
	showInfo = window.open("./LPrtTempleteQuery.jsp");
}
function afterQuery(arrQueryResult)
{
	var arrResult = new Array();
	if(arrQueryResult != null)
	{
		var sqlid5 = "LPrtTempleteInputSql5";
		var mySql5 = new SqlClass();
		mySql5.setResourceName("print.LPrtTempleteInputSql");
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(arrQueryResult[0][0]);
	 	arrResult = easyExecSql(mySql5.getString());
	 	fm.reset();
	 	document.all('TempleteID').value = arrResult[0][0];
	 	document.all('TempleteName').value = arrResult[0][1];
	 	document.all('Language').value = arrResult[0][2];
	 	document.all('LanguageType').value = arrResult[0][3];
	 	document.all('TempleteType').value = arrResult[0][4];
	 	document.all('TempleteTypeName').value = arrResult[0][5];
	 	document.all('OutputType').value = arrResult[0][6];
	 	document.all('OutputTypeName').value = arrResult[0][7];
	 	document.all('Output').value = arrResult[0][8];
	 	document.all('OutputName').value = arrResult[0][9];
	 	document.all('FilePath1').style.display='';
	 	document.all('FilePath').style.display='none';
	 	document.all("fileQueryClick").style.display ='';
	 	document.all('FilePath1').value = arrResult[0][10];
	 	document.all('DefaultType').value = arrResult[0][11];
	 	document.all('DefaultFlag').value = arrResult[0][12];
	 	document.all('PrintID').value = arrResult[0][13];
	 	document.all('TempleteID').readonly = true;
	}
}


