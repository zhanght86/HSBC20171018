 //             ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//Creator :���	
//Date :2008-09-17

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//��ʼ��ҳ��
function QueryForm()
{
	initFIRuleDefQueryGrid();
	var strSQL = ""; 
	/**
	strSQL = "select VersionNo,RuleID,RuleName,RuleDealMode,RuleReturnMean"
	         +" from FIRuleDef where 1=1 "
	         + getWherePart('VersionNo','VersionNo')
	         + getWherePart('RuleID','RuleID')
	         + getWherePart('RuleName','RuleName','like')
	         + getWherePart('RuleDealMode','RuleDealMode');
	         */
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIRuleDefQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FIRuleDefQueryInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.VersionNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.RuleID.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.RuleName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.RuleDealMode.value);//ָ������Ĳ���
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
	turnPage.pageDisplayGrid = FIRuleDefQueryGrid;
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
	var tSel = FIRuleDefQueryGrid.getSelNo();	
		
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
	tRow = FIRuleDefQueryGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select VersionNo,RuleID,RuleName,RuleDealMode,RuleDealClass,RuleFileName,replace(RuleDealSQL,'|','@'),replace(RuleReturnMean,'|','@') from FIRuleDef where 1=1 "
	       + "and VersionNo='"+FIRuleDefQueryGrid.getRowColData(tRow-1,1)+"' and RuleID='"+FIRuleDefQueryGrid.getRowColData(tRow-1,2)+"'" ; 
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleDefQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FIRuleDefQueryInputSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(FIRuleDefQueryGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql2.addSubPara(FIRuleDefQueryGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
		strSQL= mySql2.getString();
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



