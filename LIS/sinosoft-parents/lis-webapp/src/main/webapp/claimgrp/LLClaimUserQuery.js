var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//LLClaimUserGrid����Ӧ����
function LLClaimUserGridClick()
{
	//var i = LLClaimUserGrid.getSelNo();
    //i = i - 1;
    //fm.ContNo.value=LLClaimUserGrid.getRowColData(i,2);//��ͬ��
	return true;
}


//����ѯ����ť
function queryClick()
{       
	var strSQL="select UserCode,UserName,ComCode,ReportFlag,RegisterFlag,PrepayFlag,CheckFlag,CheckLevel,UWFlag,UWLevel,SimpleFlag,1,StateFlag from llclaimuser where 1=1 "
				 + getWherePart( 'UserCode','UserCodeQ')
				 + getWherePart( 'UserName','UserNameQ')
				 + getWherePart( 'ComCode','ComCodeQ')				 
				 + " order by UserCode";
	turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(strSQL, LLClaimUserGrid);
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
		top.opener.afterQuery( arrReturn );
		top.close();
	}
	catch(ex)
	{
		alert( "û�з��ָ����ڵ�afterQuery�ӿ�" + ex );
	}
		
}



