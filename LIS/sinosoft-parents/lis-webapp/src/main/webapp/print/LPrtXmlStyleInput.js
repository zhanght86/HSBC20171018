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
		var sqlId1 = "LPrtXmlStyleInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtXmlStyleInputSql");
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(document.all("PrintName").value);
		mySql1.addSubPara(document.all("TempleteType").value);
		turnPage.queryModal(mySql1.getString(), LPrtXmlStyleGrid);
	} 
	catch (ex) 
	{
		alert(ex.message);
	}
}
// �����ļ�
function exportFile() 
{
	var tSel = LPrtXmlStyleGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("��ѡ��һ����¼���ٽ��е�����");
	} 
	else 
	{
		document.all("TempleteID").value = LPrtXmlStyleGrid.getRowColData(tSel - 1,1);
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

