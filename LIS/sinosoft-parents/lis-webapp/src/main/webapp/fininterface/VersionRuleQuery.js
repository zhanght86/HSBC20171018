//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�ð汾��ѯ���ڲ�ѯ�汾��ȫ����Ϣ
var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();

//���ذ�ť��Ӧ�Ĳ���
function returnParent()
{
  var arrReturn = new Array();
	var tSel = RulesVersionGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "������ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{

			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
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
	tRow = RulesVersionGrid.getSelNo();
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
	strSQL = "select a.VersionNo,a.StartDate,a.EndDate,a.VersionReMark,a.AppDate,a.VersionState,a.Operator,a.MakeDate,a.MakeTime,decode(a.VersionState,'01','����','02','ά��','03','ɾ��','����')";
	strSQL = strSQL + " from FIRulesVersion a  where a.VersionNo ='"+RulesVersionGrid.getRowColData(tRow-1,1)+"' ";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.VersionRuleQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("VersionRuleQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(RulesVersionGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
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
	initRulesVersionGrid();
	//document.all('BranchType').value = top.opener.fm.BranchType.value;
	// ��дSQL���
	var strSQL = "";
	//var tReturn = parseManageComLimitlike();
	/**
	strSQL = "select VersionNo,AppDate,StartDate,EndDate,VersionState,decode(VersionState,'01','����','02','ά��','03','ɾ��','����'),VersionReMark from FIRulesVersion where 1=1 "
     + getWherePart('VersionNo','VersionNo','like')
     + getWherePart('AppDate')
     + getWherePart('StartDate')
     + getWherePart('EndDate')
     + getWherePart('VersionState')
     +" order by VersionNo";
     */
    var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.VersionRuleQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("VersionRuleQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AppDate.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.StartDate.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.EndDate.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.VersionState.value);//ָ������Ĳ���
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,RulesVersionGrid);
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