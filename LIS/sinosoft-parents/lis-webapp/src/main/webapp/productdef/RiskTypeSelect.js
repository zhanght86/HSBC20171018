var turnPage = new turnPageClass();

function afterCodeSelect( cCodeName, Field )
{
//alert("cCodeName:"+cCodeName);
 	//alert("cCodeName==="+cCodeName);
	if(cCodeName=="RiskCode"){
	//alert(123);
	//���뵥֤¼��ҳ�� ģ��proposalinput.jspҳ��
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
	mySql.setResourceName("card.CardTypeSelectSql"); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
    mySql.addSubPara(fm.sysdate.value);//ָ������Ĳ���
    mySql.addSubPara(fm.sysdate.value);
    mySql.addSubPara(fm.sysdate.value);
    mySql.addSubPara(fm.ManageCom.value);
	sql=mySql.getString();
	
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("��ѯ���ִ���:",sql);
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
