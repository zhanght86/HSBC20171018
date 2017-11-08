
function testRisk()
{
	//fm.target = "../f1print";
	fm.action="./PDTRiskTestSave.jsp";
	fm.submit();
}

function returnRisk()
{
	parent.fraInterface.location='./RiskTypeSelect.jsp';
}
