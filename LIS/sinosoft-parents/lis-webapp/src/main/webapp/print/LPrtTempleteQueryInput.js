var turnPage = new turnPageClass();
var showInfo;
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
	if (document.all("TempleteName").value == "" ) 
	{
		alert("请输入打印名称!")
		return ;
	}
	if (document.all("TempleteName").value !="" )
	{
		try 
		{
			var sqlid1 = "LPrtTempleteQueryInputSql1";
			var mySql1 = new SqlClass();
			mySql1.setResourceName("print.LPrtTempleteQueryInputSql");
			mySql1.setSqlId(sqlid1);
			mySql1.addSubPara(document.all("TempleteName").value);
			mySql1.addSubPara(document.all("TempleteType").value);
			mySql1.addSubPara(document.all("Language").value);
			turnPage.queryModal(mySql1.getString(), LPrtTempleteGrid);
		} 
		catch (ex) 
		{
			alert(ex.message);
		}
	}
}
function returnParent() 
{
	var arrReturn = new Array();
	var tSel = LPrtTempleteGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("请选择一条记录，再点击返回按钮。");
	} 
	else 
	{
		try 
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery(arrReturn);
			
		} 
		catch (ex) 
		{
			alert("没有发现父窗口的afterQuery接口。" + ex.message);
		}
		top.close();
	}
}
function getQueryResult() 
{
	var arrSelected = null;
	tRow = LPrtTempleteGrid.getSelNo();
	if (tRow == null || tRow == 0) 
	{
		return arrSelected;
	}
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = LPrtTempleteGrid.getRowData(tRow - 1);
	return arrSelected;
}
