//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
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

function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClick();
	top.close();
}

function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
		
	/*strSQL = "Select GrpContNo, GrpPolNo, riskcode, "
         + "paycount,  DutyCode,"
         + "PayPlanCode, Sumduepaymoney,  sumactupaymoney,"
         + "payintv, paydate,  paytype,  "
         + "curpaytodate,BankOnTheWayFlag, banksuccflag ,"
         + " startdate,enddate,startdate"
         + "  From LJSPayPerson a,LCContState b"
         + " Where a.ContNo = b.ContNo "
         + "   and a.polno = b.polno "
         + "   and a.ContNo='" + tContNo + "'"
         + "   and a.PolNo = '" + tPolNo + "'"
         + "   and StateType in ('Available','Terminate')"
         + "   and State in ('0','1')";*/
         mySql = new SqlClass();
		mySql.setResourceName("sys.PayPremQuerySql");
		mySql.setSqlId("PayPremQuerySql1");
		mySql.addSubPara(tContNo); 
		mySql.addSubPara(tPolNo); 
	}
	else
	if(tIsCancelPolFlag=="1"){//����������ѯ
	 
	 /*strSQL = "Select GrpContNo,GrpPolNo,riskcode,"
          + "paycount,DutyCode,"      
          + "PayPlanCode, Sumduepaymoney, sumactupaymoney,"
          + "payintv, paydate,paytype,"
          + "curpaytodate,BankOnTheWayFlag, banksuccflag, "
          + " startdate,enddate,startdate"
          + "  From LJSPayPerson "
          + " Where a.ContNo = b.ContNo "
          + "   and a.polno = b.polno "
          + "   and a.ContNo='" + tContNo + "'"
          + "   and a.PolNo = '" + tPolNo + "'"
          + "   and StateType in ('Available','Terminate')"
          + "   and State in ('0','1')";	*/ 
          mySql = new SqlClass();
		mySql.setResourceName("sys.PayPremQuerySql");
		mySql.setSqlId("PayPremQuerySql2");
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
  	alert("���ݿ���û���������������ݣ�");
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