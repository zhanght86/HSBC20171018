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
	if (document.all("TempleteName").value == "") 
	{
		alert("请输入打印名称!")
		return;
	}
	if (document.all("TempleteName").value != "") 
	{
		try 
		{
			var sqlId1 = "LPrtTemDownloadInputSql1";
			var mySql1 = new SqlClass();
			mySql1.setResourceName("print.LPrtTemDownloadInputSql");
			mySql1.setSqlId(sqlId1);
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
// 下载事件
function downloadClick() 
{
	var tSel = LPrtTempleteGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("请选择一条记录，再下载模板！");
	} 
	else 
	{
		document.all("FilePath").value = LPrtTempleteGrid.getRowColData(tSel - 1,6);
		document.all("OutputTypeName").value = LPrtTempleteGrid.getRowColData(tSel - 1,5);
		try 
		{
			document.getElementById("fm").submit();
		} 
		catch (ex) 
		{
			ex.message;
		}
	}
}


