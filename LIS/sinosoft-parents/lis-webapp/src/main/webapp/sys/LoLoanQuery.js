//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

function easyQueryClick()
{
	
	//alert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
	//strSQL = "select ContNo,PolNo,EdorNo,ActuGetNo,LoanType,LoanDate,SumMoney,LeaveMoney,PayOffFlag,Operator from LOLoan where ContNo = '" + tContNo + "' and PolNo = '" + tPolNo + "'";			 
	mySql = new SqlClass();
	mySql.setResourceName("sys.LoLoanQuerySql");
	mySql.setSqlId("LoLoanQuerySql1");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(tPolNo); 
	}
	else
	if(tIsCancelPolFlag=="1"){//����������ѯ
	//strSQL = "select ContNo,PolNo,EdorNo,ActuGetNo,LoanType,LoanDate,SumMoney,LeaveMoney,PayOffFlag,Operator from LOLoan where ContNo = '" + tContNo + "' and PolNo = '" + tPolNo + "'";			 
	mySql = new SqlClass();
	mySql.setResourceName("sys.LoLoanQuerySql");
	mySql.setSqlId("LoLoanQuerySql2");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(tPolNo); 
	}
	else {
	  alert("�������ʹ������!");
	  return;
	  }
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("δ�鵽�ñ����潻/�����Ϣ��");
    //alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}


function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClickSelf();
	top.close();
}



// ��ѯ��ť
function QueryClick()
{
	var strSQL="";
	
	/*strSQL = "select ContNo,PolNo,EdorNo,ActuGetNo,LoanType,LoanDate,"
	         + " SumMoney,LeaveMoney,PayOffFlag,Operator from LOLoan where 1=1 "
	         + getWherePart( 'LOLoan.ContNo','ContNo')
	         + getWherePart( 'LOLoan.ContNo','PolNo')
	         + " order by LoanDate asc";*/
		mySql = new SqlClass();
	mySql.setResourceName("sys.LoLoanQuerySql");
	mySql.setSqlId("LoLoanQuerySql3");
	mySql.addSubPara(fm.ContNo.value); 
	mySql.addSubPara(fm.PolNo.value); 	
	   //��ѯSQL�����ؽ���ַ���
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  		//�ж��Ƿ��ѯ�ɹ�
       if (!turnPage.strQueryResult)
  	   {
  		   PolGrid.clearData();
       	   alert("δ�鵽�ñ����潻/�����Ϣ��");
           return false;
  	   }
  		//��ѯ�ɹ������ַ��������ض�ά����
  		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  		//���ó�ʼ������MULTILINE����
  		turnPage.pageDisplayGrid = PolGrid;    
       
  		//����SQL���
  		turnPage.strQuerySql     = strSQL; 
  
  		//���ò�ѯ��ʼλ��
  		turnPage.pageIndex = 0;  
  
 		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  		var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  		arrGrid = arrDataSet;
  		//����MULTILINE������ʾ��ѯ���
  		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}