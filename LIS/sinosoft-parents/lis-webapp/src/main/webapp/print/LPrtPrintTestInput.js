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
	if(verifyInput2() == false)
	{
		return false;
	}
	try 
	{
		var tResult = new Array();
		var sqlId1 = "LPrtPrintTestInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtPrintTestInputSql");
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(document.all("PrintName").value);
		mySql1.addSubPara(document.all("TempleteType").value);
		tResult = easyExecSql(mySql1.getString());
		if(tResult==""||tResult ==null)
		{
			alert("û��Ҫ���ԵĴ�ӡ��Ŀ��");
		}
		turnPage.queryModal(mySql1.getString(), LPrtPrintTestGrid);
	} 
	catch (ex) 
	{
		alert(ex.message);
	}
}
// ���Դ�ӡ 
function test() 
{
	var tSel = LPrtPrintTestGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("��ѡ��һ����¼���ٽ��в��ԣ�");
	} 
	else 
	{
		var TempleteID = LPrtPrintTestGrid.getRowColData(tSel - 1,1);
//		window.location ="./LPrtPrintTable.jsp?TempleteID="+TempleteID;
		showInfo = window.open("./LPrtPrintTable.jsp?TempleteID="+TempleteID);
//		window.location ="./test.jsp";
	}
}

