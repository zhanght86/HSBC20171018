//           ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function ReturnData()
{
  var arrReturn = new Array();
	var tSel = AccountantPeriodGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = AccountantPeriodGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select * from FIPeriodManagement where 1=1 "
	       + "and year='"+AccountantPeriodGrid.getRowColData(tRow-1,1)+"' and month='"+AccountantPeriodGrid.getRowColData(tRow-1,2)+"'"; 
	         //alert(AccountantPeriodGrid.getRowColData(tRow-1,1));
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.AccountantPeriodQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("AccountantPeriodQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(AccountantPeriodGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql1.addSubPara(AccountantPeriodGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
		strSQL= mySql1.getString();
	     
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	return arrSelected;
}

// ��ѯ��ť
function submitForm()
{
	// ��ʼ�����
	initAccountantPeriodGrid();  
	// ��дSQL���
	var strSQL = ""; 
	/**
	strSQL = "select year,month,startdate,enddate,ManageCom,Operator,MakeDate,MakeTime,state from FIPeriodManagement"
	         +" where 1=1 "
	         + getWherePart('year','Year','like')
	         + getWherePart('month','Month')
	         + getWherePart('Operator','Operator')
	         + getWherePart('state','State');
	 //    alert(strSQL);
	*/ 
  var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.AccountantPeriodQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("AccountantPeriodQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.Year.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.Month.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.State.value);//ָ������Ĳ���
		strSQL= mySql2.getString();
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
//		alert("δ��ѯ���������������ݣ�");
		return false;
	}

	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	turnPage.pageLineNum = 30 ;
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����
	turnPage.pageDisplayGrid = AccountantPeriodGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}