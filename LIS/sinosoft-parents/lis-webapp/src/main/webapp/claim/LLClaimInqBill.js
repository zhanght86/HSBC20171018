var showInfo;
var turnPage = new turnPageClass();


//[��ѯ]��[��ӡ]��Ӧǰ���ݼ��
function checkDate()
{
	return true;
}


function showLLClaimInqBill()
{
	if(checkDate()==false || verifyInput() == false)
	{
		return false;
	}
	fm.target = "../f1print";
	document.getElementById("fm").submit();	
}