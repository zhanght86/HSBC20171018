var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//LLClaimUserGrid����Ӧ����
function NBUserGridClick()
{
	return true;
}

//����ѯ����ť
function queryClick()
{       
	/*if (trim(fm.UserCode.value) == "" || fm.UserCode.value == null)
	{
	    alert("����¼���û����룬�ٵ����ѯ��ť!");
	    fm.UserCode.focus();
	    return ;
	}*/
//	var strSQL="select usercode,username,comcode from lduser lu "
//                 + "where ComCode like '" + document.all('ComCode').value + "%%'"
//          //       + " and uwpopedom is null"
//                 + getWherePart( 'UserCode','UserCode')
//				 + getWherePart( 'UserName','UserName')
//				 + getWherePart( 'ComCode','ManageCom')
//				 + " and usercode in(select code from ldcode where codetype='appalluser')";
//				 + " order by UserCode";
				 
	var  UserCode = window.document.getElementsByName(trim("UserCode"))[0].value;
	var  UserName = window.document.getElementsByName(trim("UserName"))[0].value;
	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
    var sqlid1="NBUserQuerySql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.NBUserQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('ComCode').value);//ָ������Ĳ���
	mySql1.addSubPara(UserCode);//ָ������Ĳ���
	mySql1.addSubPara(UserName);//ָ������Ĳ���
	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
	turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(strSQL, NBUserGrid);
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	try
	{
		//��ȡ��ȷ���к�
		var tSelRow = NBUserGrid.getSelNo() - 1;
		if( tSelRow < 0 || tSelRow == null )
		{
			alert( "����ѡ��һ����¼���ٵ�����ذ�ť!" );
			return;
		}
		arrReturn[0] = NBUserGrid.getRowColData(tSelRow,1);
		arrReturn[1] = NBUserGrid.getRowColData(tSelRow,2);
		top.opener.afterQuery( arrReturn );
		top.close();
	}
	catch(ex)
	{
		alert( "û�з��ָ����ڵ�afterQuery�ӿ�" + ex );
	}
		
}



