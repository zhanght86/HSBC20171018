//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�

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
	var tSel = FIDataBaseLinkGrid.getSelNo();	
		
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
	tRow = FIDataBaseLinkGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select * from FIDataBaseLink where 1=1 "
	       + "and InterfaceCode='"+FIDataBaseLinkGrid.getRowColData(tRow-1,1)+"'"; 
	       */
	         //alert(FIDataBaseLinkGrid.getRowColData(tRow-1,1));
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIDataBaseLinkQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FIDataBaseLinkQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(FIDataBaseLinkGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
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
	initFIDataBaseLinkGrid();  
	// ��дSQL���
	var strSQL = ""; 

	strSQL = "select InterfaceCode,InterfaceName,DBType,IP,Port,DBName,ServerName,UserName,PassWord,ManageCom,Operator,MakeDate,MakeTime from FIDataBaseLink"
	         +" where 1=1 "
	         + getWherePart('InterfaceCode','InterfaceCode','like')
	         + getWherePart('InterfaceName','InterfaceName','like')
	         + getWherePart('DBType','DBType','like')
	         + getWherePart('IP','IP','like')
	         + getWherePart('Port','Port','like')
	         + getWherePart('DBName','DBName','like')
	         + getWherePart('ServerName','ServerName','like')
	         + getWherePart('UserName','UserName','like')
	         + getWherePart('PassWord','PassWord','like')
	         + getWherePart('Operator','Operator');
	      
	 //    alert(strSQL);
	
  
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
	turnPage.pageDisplayGrid = FIDataBaseLinkGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}