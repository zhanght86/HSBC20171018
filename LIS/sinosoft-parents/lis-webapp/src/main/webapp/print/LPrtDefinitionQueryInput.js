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
	if (document.all("PrintName").value == "" ) 
	{
		alert("请输入打印名称!")
		return ;
	}
	else
	{
		try 
		{
			var sqlid1 = "LPrtDefinitionQueryInputSql1";
			var mySql1 = new SqlClass();
			mySql1.setResourceName("print.LPrtDefinitionQueryInputSql");
			mySql1.setSqlId(sqlid1);
			mySql1.addSubPara(document.all("PrintName").value);
			mySql1.addSubPara(document.all("PrintObject").value);
			mySql1.addSubPara(document.all("PrintType").value);
			turnPage.queryModal(mySql1.getString(), LPrtDefinitionGrid);
		} 
		catch (ex) 
		{
			alert("打印查询出错！"+ex.message);
		}
	}
}
function returnParent() 
{
	var arrReturn = new Array();
	var tSel = LPrtDefinitionGrid.getSelNo();
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
	tRow = LPrtDefinitionGrid.getSelNo();
	// getSelNo()获取选中单选框的行数
	if (tRow == null || tRow == 0) 
	{
		return arrSelected;
	}
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = LPrtDefinitionGrid.getRowData(tRow - 1);
	return arrSelected;
}
