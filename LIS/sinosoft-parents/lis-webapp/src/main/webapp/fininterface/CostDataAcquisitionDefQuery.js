//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//Creator :���	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//��ʼ��ҳ��
function QueryForm()
{
	initCostDataAcquisitionDefQueryGrid();
	var strSQL = ""; 
	/**
	strSQL = "select VersionNo,AcquisitionID,AcquisitionType,DataSourceType,CostOrDataID,DistillMode,Remark"
	         +" from FICostDataAcquisitionDef where 1=1 "
	         + getWherePart('VersionNo','VersionNo')
	         + getWherePart('AcquisitionID','AcquisitionID','like')
	         + getWherePart('AcquisitionType','AcquisitionType')
	         + getWherePart('DataSourceType','DataSourceType')
	         + getWherePart('CostOrDataID','CostOrDataID')
	         + getWherePart('DistillMode','DistillMode')
	         + getWherePart('Remark','Remark','like')
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CostDataAcquisitionDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("CostDataAcquisitionDefQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AcquisitionID.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AcquisitionType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.DataSourceType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.CostOrDataID.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.DistillMode.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.Remark.value);//ָ������Ĳ���
		strSQL= mySql1.getString();
	
	 
  
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
	turnPage.pageDisplayGrid = CostDataAcquisitionDefQueryGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

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
	var tSel = CostDataAcquisitionDefQueryGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				
				arrReturn1 = getQueryResult();
				top.opener.afterQuery1( arrReturn1 );
				
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
	tRow = CostDataAcquisitionDefQueryGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	var strSQL = "";
	/**
	strSQL = "select AcquisitionID,AcquisitionType,DataSourceType,CostOrDataID,DistillMode,Remark,replace(DistillSQL,'|','@'), replace(DistillSQLForOne,'|','@'),DistillClass,DistillClassForOne from FICostDataAcquisitionDef where 1=1 "
	       + "and VersionNo='"+CostDataAcquisitionDefQueryGrid.getRowColData(tRow-1,1)+"' and AcquisitionID='"+CostDataAcquisitionDefQueryGrid.getRowColData(tRow-1,2)+"'" ; 
 	*/
 	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.CostDataAcquisitionDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("CostDataAcquisitionDefQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(CostDataAcquisitionDefQueryGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql2.addSubPara(CostDataAcquisitionDefQueryGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
		strSQL= mySql2.getString();
	//alert(strSQL);
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



