//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();


//���ذ�ť��Ӧ�Ĳ���
function returnParent()
{
  var arrReturn = new Array();
	var tSel = AssociatedItemDefGrid.getSelNo();

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
	tRow = AssociatedItemDefGrid.getSelNo();
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
			strSQL = "select a.VersionNo,a.AssociatedID,a.AssociatedName,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.ColumnID and tableid='FIDataTransResult'),a.SourceTableID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.SourceColumnID and tableid='FIAboriginalData'),a.TransFlag,a.TransSQL,a.TransClass,a.ReMark";
	strSQL = strSQL + " from FIAssociatedItemDef a  where a.VersionNo ='"+AssociatedItemDefGrid.getRowColData(tRow-1,1)+"' and a.AssociatedID ='"+AssociatedItemDefGrid.getRowColData(tRow-1,2)+"'";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.AssociatedItemDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("AssociatedItemDefQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(AssociatedItemDefGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql1.addSubPara(AssociatedItemDefGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
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
	initAssociatedItemDefGrid();
	// ��дSQL���
	var strSQL = "";
	/**
	strSQL = "select VersionNo,AssociatedID,AssociatedName,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.ColumnID and tableid='FIDataTransResult'),SourceTableID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.SourceColumnID and tableid='FIAboriginalData'),TransFlag,decode(TransFlag,'N','��ת��','S','SQLת��','C','����ת��','����') from FIAssociatedItemDef a where 1=1 "
     + getWherePart('VersionNo')
     + getWherePart('AssociatedID','AssociatedID','like')
     + getWherePart('AssociatedName','AssociatedName','like')
     + getWherePart('ColumnID')
     + getWherePart('SourceColumnID')          
     + getWherePart('TransFlag')
     +"order by VersionNo,AssociatedID";
     */
//alert(strSQL);
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.AssociatedItemDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("AssociatedItemDefQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AssociatedID.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AssociatedName.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ColumnID.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.SourceColumnID.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.TransFlag.value);//ָ������Ĳ���
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,AssociatedItemDefGrid);
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