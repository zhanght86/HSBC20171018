//             ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();



//���ذ�ť��Ӧ�Ĳ���
function returnParent()
{
  var arrReturn = new Array();
	var tSel = FinItemDefGrid.getSelNo();

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
	tRow = FinItemDefGrid.getSelNo();
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
	strSQL = "select a.VersionNo,a.FinItemID,a.FinItemName,a.FinItemType,a.ItemMainCode,a.DealMode,a.ReMark";
	strSQL = strSQL + " from FIFinItemDef a  where a.VersionNo ='"+FinItemDefGrid.getRowColData(tRow-1,1)+"' and a.FinItemID ='"+FinItemDefGrid.getRowColData(tRow-1,2)+"'";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinItemDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FinItemDefQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(FinItemDefGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql1.addSubPara(FinItemDefGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
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
	initFinItemDefGrid();
	//document.all('BranchType').value = top.opener.fm.BranchType.value;
	// ��дSQL���
	var strSQL = "";
	/**
	//var tReturn = parseManageComLimitlike();
	strSQL = "select VersionNo,FinItemID,FinItemName,FinItemType,FinItemType,ItemMainCode,DealMode,ReMark from FIFinItemDef where 1=1 "
     + getWherePart('VersionNo')
     + getWherePart('FinItemID','FinItemID','like')
     + getWherePart('FinItemName','FinItemName','like')     
     + getWherePart('FinItemType')
     + getWherePart('ItemMainCode')
     + getWherePart('DealMode')
     +"order by VersionNo,FinItemID";
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FinItemDefQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FinItemDefQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.FinItemID.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.FinItemName.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.FinItemType.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ItemMainCode.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.DealMode.value);//ָ������Ĳ���
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,FinItemDefGrid);
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