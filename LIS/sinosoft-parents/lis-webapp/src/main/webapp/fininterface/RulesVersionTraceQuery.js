//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();


//���ذ�ť��Ӧ�Ĳ���
function returnParent()
{
  var arrReturn = new Array();
	var tSel = RulesVersionTraceGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "������ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{

			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery1( arrReturn );
			}
			catch(ex)
			{
				//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
				alert(ex.message);
			}
			top.close();

	}
}



function getQueryResult()
{
	var arrSelected = null;
	tRow = RulesVersionTraceGrid.getSelNo();
	if( tRow == 0 || tRow == null )
	{
	  return arrSelected;
	}
	arrSelected = new Array();
		// ��дSQL���
	var strSQL = "";
	/**
	strSQL = "select * ";
	strSQL = strSQL + " from FIRulesVersionTrace a  where a.Maintenanceno ='"+RulesVersionTraceGrid.getRowColData(tRow-1,1)+"' ";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.RulesVersionTraceQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("RulesVersionTraceQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(RulesVersionTraceGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
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
	initRulesVersionTraceGrid();
	// ��дSQL���
	var strSQL = "";
	/**
	strSQL = "select * from FIRulesVersionTrace where 1=1 "
     + getWherePart('Maintenanceno','Maintenanceno','like')
     + getWherePart('VersionNo','VersionNo','like')
     + getWherePart('MaintenanceState')
     + getWherePart('MaintenanceReMark','MaintenanceReMark','like')
     +" order by Maintenanceno";
     */
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.RulesVersionTraceQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("RulesVersionTraceQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.Maintenanceno.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.MaintenanceState.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.MaintenanceReMark.value);//ָ������Ĳ���
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,RulesVersionTraceGrid);
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