var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//LLClaimUserGrid����Ӧ����
function LLClaimUserGridClick()
{
	//var i = LLClaimUserGrid.getSelNo();
    //i = i - 1;
    //fm.ContNo.value=LLClaimUserGrid.getRowColData(i,2);//��ͬ��
	//fm.ComCode.value=LLClaimUserGrid.getRowColData(i,3);
	return true;
}

//����ѯ����ť
function queryClick()
{       
	/*var strSQL="select UserCode,UserName,ComCode,ReportFlag,RegisterFlag,PrepayFlag,CheckFlag,CheckLevel,UWFlag,UWLevel,SimpleFlag,1,StateFlag from llclaimuser where 1=1 "
				 + getWherePart( 'UserCode','UserCodeQ')
				 + getWherePart( 'UserName','UserNameQ')
				 + getWherePart( 'ComCode','ComCodeQ')	
				 + " and ComCode like '" + document.all('ComCode').value + "%%'"			 
				 + " order by UserCode";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimUserQueryInputSql");
	mySql.setSqlId("LLClaimUserQuerySql1");
	mySql.addSubPara(fm.UserCodeQ.value ); 
	mySql.addSubPara(fm.UserNameQ.value ); 
	mySql.addSubPara(fm.ComCodeQ.value ); 
	mySql.addSubPara(document.all('ComCode').value ); 
	turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(mySql.getString(), LLClaimUserGrid);
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	try
	{
//		arrReturn = getQueryResult();
		//��ȡ��ȷ���к�
		var tRow = LLClaimUserGrid.getSelNo();
		if( tRow < 1||tRow == null )
		{
			alert( "����ѡ��һ����¼���ٵ�����ذ�ť!" );
			return;
		}
		arrReturn[0]= LLClaimUserGrid.getRowColData(tRow-1,1);
		arrReturn[1]= LLClaimUserGrid.getRowColData(tRow-1,3);
		top.opener.afterQuery( arrReturn );
		top.close();
	}
	catch(ex)
	{
		alert( "û�з��ָ����ڵ�afterQuery�ӿ�" + ex );
	}
		
}



