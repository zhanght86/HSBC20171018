
//Creator :���	
//Date :2008-09-09

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
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
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function queryFIRuleDealLog()
{
		try
		{
			initFIRuleDealLogGrid();
			var sStartDay = document.all('StartDay').value;
			var sEndDay = document.all('EndDay').value;
			var strSQL = ""; 
			/**
			strSQL = "select VersionNo,RuleDealBatchNo,DataSourceBatchNo,CallPointID || '-' || (select distinct b.codename from ficodetrans b where b.code = FIRuleDealLog.Callpointid and b.codetype = 'CallPointID'),RuleDealResult,DealOperator,RulePlanID || '-' || (select a.rulesplanname from firuleplandef a where a.rulesplanid = FIRuleDealLog.ruleplanid),RuleDealDate from FIRuleDealLog where ruledealresult = 'Fail' and VersionNo = '"+VersionNo+"' ";
			if(document.all('StartDay').value != '')
			{
				strSQL = strSQL + " and RuleDealDate >= '"+document.all('StartDay').value+"' ";
			}
			if(document.all('EndDay').value != '')
			{
				strSQL = strSQL + " and RuleDealDate <= '"+document.all('EndDay').value+"' ";
			}
			*/
			var mySql1=new SqlClass();
				mySql1.setResourceName("fininterface.FIRuleDealLogQuerySql"); //ָ��ʹ�õ�properties�ļ���
				mySql1.setSqlId("FIRuleDealLogQuerySql1");//ָ��ʹ�õ�Sql��id
				mySql1.addSubPara(VersionNo);//ָ������Ĳ���
				mySql1.addSubPara(document.all('StartDay').value);//ָ������Ĳ���
				mySql1.addSubPara(document.all('EndDay').value);//ָ������Ĳ���
				strSQL= mySql1.getString();
			
  		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  		if (!turnPage.strQueryResult)
			{
				return false;
			}
			//���ò�ѯ��ʼλ��
			turnPage.pageIndex = 0;
			//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
			turnPage.pageLineNum = 30 ;
			//��ѯ�ɹ������ַ��������ض�ά����
			turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
			//���ó�ʼ������MULTILINE����
			turnPage.pageDisplayGrid = FIRuleDealLogGrid;
			//����SQL���
			turnPage.strQuerySql = strSQL ;
			arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
			//����MULTILINE������ʾ��ѯ���
			displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  	}
  	catch(Ex)
	{
		alert(Ex.message);
	}
}

function ReturnData()
{
  var arrReturn = new Array();
	var tSel = FIRuleDealLogGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				arrReturn1 = getQueryResult();
				//alert(arrReturn1);
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
	tRow = FIRuleDealLogGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select RuleDealBatchNo,DataSourceBatchNo,CallPointID,RuleDealResult,DealOperator,RulePlanID,RuleDealDate,LogFilePath,LogFileName from FIRuleDealLog where 1=1 "
	       + "and VersionNo='"+FIRuleDealLogGrid.getRowColData(tRow-1,1)+"' and RuleDealBatchNo='"+FIRuleDealLogGrid.getRowColData(tRow-1,2)+"' and DataSourceBatchNo='"+FIRuleDealLogGrid.getRowColData(tRow-1,3)+"' and CallPointID = substr('"+FIRuleDealLogGrid.getRowColData(tRow-1,4)+"',1,2) and RulePlanID = substr('"+FIRuleDealLogGrid.getRowColData(tRow-1,7)+"',1,8) " ; 
	 */    
	//alert(strSQL);
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleDealLogQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FIRuleDealLogQuerySql4");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,3));//ָ������Ĳ���
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,4));//ָ������Ĳ���
		mySql2.addSubPara(FIRuleDealLogGrid.getRowColData(tRow-1,7));//ָ������Ĳ���
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