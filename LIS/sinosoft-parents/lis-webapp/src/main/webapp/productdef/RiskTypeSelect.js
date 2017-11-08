var turnPage = new turnPageClass();

function afterCodeSelect( cCodeName, Field )
{
//alert("cCodeName:"+cCodeName);
 	//alert("cCodeName==="+cCodeName);
	if(cCodeName=="RiskCode"){
	//alert(123);
	//进入单证录入页面 模仿proposalinput.jsp页面
	    var RiskCode =fm.RiskCode.value;
		//alert("fm.CardType.value:"+fm.CardType.value);
		parent.fraInterface.location ='./PDTRiskTestInput.jsp?RiskCode='+Field.value;
	//getRiskInput();
	}
}
function getcodedata2()
{
//	var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and lmriskapp.PolType ='C' "
//	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
//	         +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+fm.all('ManageCom').value+"' "
//	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('C') order by RiskCode";
	var sql="";
	var sqlid="CardTypeSelect1";
	var mySql=new SqlClass();
	mySql.setResourceName("card.CardTypeSelectSql"); //指定使用的properties文件名
    mySql.setSqlId(sqlid);//指定使用的Sql的id
    mySql.addSubPara(fm.sysdate.value);//指定传入的参数
    mySql.addSubPara(fm.sysdate.value);
    mySql.addSubPara(fm.sysdate.value);
    mySql.addSubPara(fm.ManageCom.value);
	sql=mySql.getString();
	
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("查询险种代码:",sql);
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
    //alert(tCodeData);
    fm.all("CardType").CodeData=tCodeData;
	
}

function initForm(){
   fm.ManageCom.value = ManageCom;
}
