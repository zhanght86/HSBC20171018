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
		alert("�������ӡ����!")
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
			alert("��ӡ��ѯ����"+ex.message);
		}
	}
}
function returnParent() 
{
	var arrReturn = new Array();
	var tSel = LPrtDefinitionGrid.getSelNo();
	if (tSel == 0 || tSel == null) 
	{
		alert("��ѡ��һ����¼���ٵ�����ذ�ť��");
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
			alert("û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex.message);
		}
		top.close();
	}
}
function getQueryResult() 
{
	var arrSelected = null;
	tRow = LPrtDefinitionGrid.getSelNo();
	// getSelNo()��ȡѡ�е�ѡ�������
	if (tRow == null || tRow == 0) 
	{
		return arrSelected;
	}
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = LPrtDefinitionGrid.getRowData(tRow - 1);
	return arrSelected;
}
