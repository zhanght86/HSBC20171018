//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
//var mSwitch = parent.VD.gVSwitch;
//var turnPage = new turnPageClass(); 

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

function test1()
{
	myAlert("111");
}


// ��ѯ��ť
function easyQueryClick()
{
	
	myAlert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	
	//strSQL = "select PolNo,GrpPolNo,ContNo,PayNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,PayType,CurPayToDate from LJAPayPerson where PayNo='" + tPayNo + "'";			 
	//strSQL = "select PolNo,PayNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,CurPayToDate,GrpPolNo,ContNo,PayType from LJAPayPerson";			 
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.ReEdorQueryPDetailSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("ReEdorQueryPDetailSql100");//ָ��ʹ�õ�Sql��id
	    mySql100.addSubPara("1");
	    strSQL=mySql100.getString();
	
	myAlert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    myAlert(""+"��ѯʧ�ܣ�"+"");
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
