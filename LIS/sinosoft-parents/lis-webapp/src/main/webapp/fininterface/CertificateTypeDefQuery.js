//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();


//���ذ�ť��Ӧ�Ĳ���
function returnParent()
{
  var arrReturn = new Array();
	var tSel = CertTypeDefGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "������ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{

			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery2( arrReturn );
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
	tRow = CertTypeDefGrid.getSelNo();
	if( tRow == 0 || tRow == null )
	{
	  return arrSelected;
	}
	arrSelected = new Array();
	//tRow = 10 * turnPage.pageIndex + tRow; //10����multiline������
	//������Ҫ���ص�����
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
		// ��дSQL���
	var strSQL = "";
	/**
	strSQL = "select a.VersionNo,a.CertificateID,a.CertificateName,a.DetailIndexID,a.DetailIndexName,a.AcquisitionType,a.Remark";
	strSQL = strSQL + " from FICertificateTypeDef a  where a.VersionNo ='"+CertTypeDefGrid.getRowColData(tRow-1,1)+"' and a.CertificateID ='"+CertTypeDefGrid.getRowColData(tRow-1,2)+"'";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CertificateTypeDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("CertificateTypeDefQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(CertTypeDefGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql1.addSubPara(CertTypeDefGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
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



// ��ѯ��ť��Ӧ�Ĳ���
function easyQueryClick()
{
	// ��ʼ�����
	initCertTypeDefGrid();

	// ��дSQL���
	var strSQL = "";
	/**
	strSQL = "select VersionNo,CertificateID,CertificateName,DetailIndexID,DetailIndexName,AcquisitionType,Remark from FICertificateTypeDef where 1=1 "
     + getWherePart('VersionNo')
     + getWherePart('CertificateID','CertificateID','like')
     + getWherePart('CertificateName','CertificateName','like')     
     + getWherePart('DetailIndexID')
     + getWherePart('DetailIndexName','DetailIndexName','like')
     + getWherePart('AcquisitionType')
     +"order by VersionNo,CertificateID";
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.CertificateTypeDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("CertificateTypeDefQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.CertificateID.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.CertificateName.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.DetailIndexID.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.DetailIndexName.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AcquisitionType.value);//ָ������Ĳ���
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,CertTypeDefGrid);
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