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
		var sqlId1 = "LPrtTempleteLogInputSql1";
		var mySql1 = new SqlClass();
		mySql1.setResourceName("print.LPrtTempleteLogInputSql");
		mySql1.setSqlId(sqlId1);
		mySql1.addSubPara(document.all("PrintID").value);
		mySql1.addSubPara(document.all("StartDate").value);
		mySql1.addSubPara(document.all("EndDate").value);
		turnPage.queryModal(mySql1.getString(), LPrtTempleteLogGrid);
	} 
	catch (ex) 
	{
		alert(ex.message);
	}
}



