//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

//Creator :���	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();


function ToExcel()
{
	if(FIRuleDealErrLogGrid.mulLineCount=="0"){
		alert("û�в�ѯ������");
		return false;
	} 	 	
	fm.action="./FinRuleDealCheckExcel.jsp";
	fm.target=".";
	document.getElementById("fm").submit(); //�ύ
}

//��ʼ��ҳ��
function queryFIRuleDealErrLogGrid()
{
	try
	{
		initFIRuleDealErrLogGrid();
		var strSQL = ""; 
		
  	   
  	    var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.FIRuleDealErrLogInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId("FIRuleDealErrLogInputSql1");//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(RuleDealBatchNo);//ָ������Ĳ���
			mySql1.addSubPara(DataSourceBatchNo);//ָ������Ĳ���
			mySql1.addSubPara(RulePlanID);//ָ������Ĳ���
			strSQL= mySql1.getString();
  	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	    strSQL = "select RuleDealBatchNo,DataSourceBatchNo,CertificateID || '-' ||(select c.certificatename from ficertificatetypedef c where c.certificateid = firuledealerrlog.certificateid),ErrInfo,(select d.detailindexname  from ficertificatetypedef d where d.certificateid = firuledealerrlog.certificateid) || '-' || businessno,RulePlanID || '-' || (select a.rulesplanname from firuleplandef a where a.rulesplanid = firuledealerrlog.ruleplanid),RuleID || '-' || (select b.rulename from firuledef b where b.ruleid = firuledealerrlog.ruleid),(case DealState when '1' then '�������' else 'δ����' end), ErrSerialNo from FIRuleDealErrLog where ";
  	    strSQL = strSQL + " RuleDealBatchNo ='"+RuleDealBatchNo+"' and DataSourceBatchNo ='"+DataSourceBatchNo+"' and ruleplanid = '" + RulePlanID + "' ";
  	    fm.ExpSQL.value = strSQL;
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
		turnPage.pageDisplayGrid = FIRuleDealErrLogGrid;
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


/*function queryFIRuleDealErrLogGrid()
{
	// ��ʼ�����
	initFIRuleDealErrLogGrid();
  var strSQL = ""; 
		//strSQL = "select RuleDealBatchNo,DataSourceBatchNo,ClassType,ErrInfo,KeyUnionValue,RulePlanID,RuleID,DealState from FIRuleDealErrLog where ";
		//strSQL = strSQL + " RuleDealBatchNo ='"+RuleDealBatchNo+"' and DataSourceBatchNo ='"+DataSourceBatchNo+"' ";
		//alert(strSQL);
		strSQL = "select 1,1,1,1,1,1,1,1 from dual ";	
  	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�в�ѯ�����ݣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = FIRuleDealErrLogGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}*/

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
	var tSel = FIRuleDealErrLogGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    //alert("dddd" + parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel-1,3));
	    document.all('CertificateID').value =  parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,3); //arrResult[0][1];
		document.all('ErrInfo').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,4);//arrResult[0][2];
		document.all('businessno').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,5); //arrResult[0][3];
		document.all('RulePlanID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,6);//arrResult[0][4];
		document.all('RuleID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,7);//arrResult[0][5];
		document.all('DealState').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,8);//arrResult[0][6];
		
	}
}

function DealErrdata1(){

   alert( "�����ݴ�����Գɹ������Խ����²�ƾ֤����!" );

}

function DealErrdata(){

	
	var tSel = FIRuleDealErrLogGrid.getSelNo();	

	if( tSel == 0 || tSel == null ){
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	}
	else
	{
	
	   var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
       var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
   	  document.all('DelErrSerialNo').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,9);
   	  fm.action="./FIRuleDealErrLogSave.jsp";
   	  fm.target="fraSubmit";
   	  document.getElementById("fm").submit();
    }

}

function getQueryResult()
{
	var arrSelected = null;
	tRow = FIRuleDealErrLogGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select CertificateID,ErrInfo,businessno,RulePlanID,RuleID,DealState,ErrSerialNo from FICostDataBaseDef where 1=1 "
	       + "and RuleDealBatchNo='"+FIRuleDealErrLogGrid.getRowColData(tRow-1,1)+"' and DataSourceBatchNo='"+FIRuleDealErrLogGrid.getRowColData(tRow-1,2)+"' " ; 
	 */
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRuleDealErrLogInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FIRuleDealErrLogInputSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(FIRuleDealErrLogGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql2.addSubPara(FIRuleDealErrLogGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
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

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('CertificateID').value =  parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,1); //arrResult[0][1];
		document.all('ErrInfo').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,2);//arrResult[0][2];
		document.all('businessno').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,3); //arrResult[0][3];
		document.all('RulePlanID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,4);//arrResult[0][4];
		document.all('RuleID').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,5);//arrResult[0][5];
		document.all('DealState').value = parent.fraInterface.FIRuleDealErrLogGrid.getRowColData(tSel - 1,6);//arrResult[0][6];		
	}
}
function initFIRuleDealErrLogQuery()
{
	try
	{
  	var strSQL = "";
  	/**
  	strSQL = "select RuleDealBatchNo,DataSourceBatchNo, CertificateID || '-' ||(select c.certificatename from ficertificatetypedef c where c.certificateid = firuledealerrlog.certificateid),ErrInfo,(select d.detailindexname  from ficertificatetypedef d where d.certificateid = firuledealerrlog.certificateid) || '-' || businessno,RulePlanID || '-' || (select a.rulesplanname from firuleplandef a where a.rulesplanid = firuledealerrlog.ruleplanid),RuleID || '-' || (select b.rulename from firuledef b where b.ruleid = firuledealerrlog.ruleid),(case DealState when '1' then '�������' else 'δ����' end),ErrSerialNo from FIRuleDealErrLog where ";
		strSQL = strSQL + " RuleDealBatchNo ='"+RuleDealBatchNo+"' and DataSourceBatchNo ='"+DataSourceBatchNo+"' ";
  	*/
  	//alert(strSQL);
  	var mySql3=new SqlClass();
		mySql3.setResourceName("fininterface.FIRuleDealErrLogInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId("FIRuleDealErrLogInputSql3");//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(RuleDealBatchNo);//ָ������Ĳ���
		mySql3.addSubPara(DataSourceBatchNo);//ָ������Ĳ���
		strSQL= mySql3.getString(); 
  	turnPage.queryModal(strSQL, FIRuleDealErrLogGrid);
  	 fm.ExpSQL.value = strSQL;
  	    document.all('CertificateID').value = '';
		document.all('ErrInfo').value = '';
		document.all('businessno').value = '';
		document.all('RulePlanID').value = '';
		document.all('RuleID').value = '';
		document.all('DealState').value = '';	
	}
	
	catch(Ex)
	{
		alert(Ex.message);
	}
}
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	mOperate="";
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


    mOperate="";
    initFIRuleDealErrLogQuery();
  }
}

