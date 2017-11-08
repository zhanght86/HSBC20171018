var mOperate = "";
var turnPage = new turnPageClass();
var showInfo;
var mySql1;
window.onfocus = myonfocus;
function myonfocus() 
{
	if (showInfo != null) 
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
function easyQueryClick() 
{
	if (document.all("State").value == "") 
	{
		alert("请选择审批状态!")
		return;
	}
	else
	{	
		try 
		{
			var sqlId1 = "LPrtTemApproveInputSql1";
			mySql1 = new SqlClass();
			mySql1.setResourceName("print.LPrtTemApproveInputSql");
			mySql1.setSqlId(sqlId1);
			mySql1.addSubPara(document.all("TempleteName").value);
			mySql1.addSubPara(document.all("TempleteType").value);
			mySql1.addSubPara(document.all("Language").value);
			mySql1.addSubPara(document.all("State").value);
			turnPage.queryModal(mySql1.getString(), LPrtTempleteGrid);
		} 
		catch (ex) 
		{
			alert(ex.message);
		}
	}
	DivApprove.style.display = 'none';
}
// 点击触发模板审批
function templeteApprove() 
{
	if(verifyInput2() == false)
	{
		return false;
	}
	var tSel = LPrtTempleteGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("请选择一条记录，再进行模板审批！");
	} 
	else 
	{
		try 
		{
			document.all("TempleteID").value = LPrtTempleteGrid.getRowColData(tSel-1 ,1);
			mOperate = "UPDATE||MAIN";
			submitForm();
	    } 
		catch (ex) 
		{
			alert("模板审批错误！" + ex.message);
		}
	}
}
function resetForm()
{
	try 
	{
		var sqlId1 = "LPrtTemApproveInputSql1";
		mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtTemApproveInputSql");
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(document.all("TempleteName").value);
		mySql1.addSubPara(document.all("TempleteType").value);
		mySql1.addSubPara(document.all("Language").value);
		mySql1.addSubPara(document.all("State").value);
		turnPage.queryModal(mySql1.getString(), LPrtTempleteGrid);
	} 
	catch (ex) 
	{
		alert(ex.message);
	}
	DivApprove.style.display = 'none';
}
function submitForm()
{
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
	resetForm();
}
function getQueryResult() 
{
	DivApprove.style.display = '';
	var arrResult = new Array();
	tSel = LPrtTempleteGrid.getSelNo();
	document.all("TempleteID").value = LPrtTempleteGrid.getRowColData(tSel-1 ,1);
	var tTempleteID = document.all("TempleteID").value;
	var sqlId2 = "LPrtTemApproveInputSql2";
	mySql2 = new SqlClass();
	mySql2.setResourceName("print.LPrtTemApproveInputSql");
	mySql2.setSqlId(sqlId2);
	mySql2.addSubPara(tTempleteID);
	arrResult = easyExecSql(mySql2.getString());
	document.all("State1").value = arrResult[0][0];
	document.all("ApproveFlag").value = arrResult[0][1];
	document.all("Remark").value = arrResult[0][2];
}