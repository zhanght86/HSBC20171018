var showInfo;
var turnPage = new turnPageClass();


//[查询]或[打印]响应前数据检测
function checkDate()
{
	return true;
}


function showLLClaimPrepayBill()
{
	if(checkDate()==false || verifyInput() == false)
	{
		return false;
	}
	fm.target = "../f1print";
	document.getElementById("fm").submit();	
}