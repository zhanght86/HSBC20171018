var showInfo;
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";

var turnPage = new turnPageClass();
this.window.onfocus=myonfocus;


//初始化险种编码
function getaddresscodedata()
{
	//alert(grpcontno);
	//初始化险种
	var sql="select contplancode,contplanname From lccontplan where grpcontno='"+grpcontno+"'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    
    document.all("ContPlan").CodeData=tCodeData;
	
}



function deleteall()
{
	if(fm.ContPlan.value=="")
	{
		alert("请选择要删除的保障计划！");
		return;
	}
	
	if (confirm("您将删除投保该保障计划下的所有被保人信息,确定吗?"))
	      {
	      	lockScreen('lkscreen');  
  fm.GrpContNo.value=grpcontno;
  
	document.getElementById("fm").submit();
}
	
}

function afterSubmit( FlagStr, content )
{
   
	unlockScreen('lkscreen');  
		document.all("info").innerText=content;
	
	    
}