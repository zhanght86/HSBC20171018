//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sqlresourcename = "sys.LDPersonQuerySql";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPersonGrid();
	// ��дSQL���
	var strSQL = "";
	/*
	strSQL = "select CustomerNo, Name, Sex, Birthday, IDType, IDNo from LDPerson where 1=1 "
		+ getWherePart( 'CustomerNo' )
		+ getWherePart( 'Name' )
		+ getWherePart( 'Sex' )
		+ getWherePart( 'Birthday' )
		+ getWherePart( 'IDType' )
		+ getWherePart( 'IDNo' );
	*/	
		
		var sqlid1="LDPersonQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.CustomerNo.value);
		mySql1.addSubPara(fm.Name.value);
		mySql1.addSubPara(fm.Sex.value);
		mySql1.addSubPara(fm.Birthday.value);
		mySql1.addSubPara(fm.IDType.value);
		mySql1.addSubPara(fm.IDNo.value);
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
    
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
  }

	//��ѯ�ɹ������ַ��������ض�ά����
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PersonGrid;    
          
  //����SQL���
  turnPage.strQuerySql = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}

function returnParent()
{
  var arrReturn = new Array();
	var tSel = PersonGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
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
	//������Ҫ���ص�����
	var arrSelected = null;
	
	//��ȡ��ȷ���к�
	var tRow = PersonGrid.getSelNo();	
	if( tRow == 0 || tRow == null ||arrDataSet == null )
	  return arrSelected;

	arrSelected = new Array();
	
	var CustomerNo = PersonGrid.getRowColData(tRow - 1,1);
	/*
	strSql = "select a.*, b.*,c.* from LDPerson a, LCAddress b, LCAccount c"
	       	 + " where a.CustomerNo = '" + trim(CustomerNo) + "'"
	       	 + " and a.CustomerNo = b.CustomerNo"
	       	 + " and a.CustomerNo = c.CustomerNo"
	       	 + " and b.AddressNo = '1'";
	*/
	//strSql = "select * from LDPerson where CustomerNo = '" + trim(CustomerNo) + "'";
	
	var sqlid2="LDPersonQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(trim(CustomerNo));

	
	var strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
	arrSelected = decodeEasyQueryResult(strQueryResult);

	return arrSelected;
}