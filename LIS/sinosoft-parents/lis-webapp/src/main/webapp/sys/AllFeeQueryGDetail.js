//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
//var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 

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
	alert("111");
}


// ��ѯ��ť
function easyQueryClick()
{
	
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	
var sqlid28="ProposalQuerySql28";
var mySql28=new SqlClass();
mySql28.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
mySql28.addSubPara(tPayNo );//ָ������Ĳ���
strSQL=mySql28.getString();
	
	//strSQL = "select PolNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,CurPayToDate,PayType,ContNo,GrpPolNo from LJAPayPerson where PayNo='" + tPayNo + "'";			 
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
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
  
	//�������ؼ����ƽ��и�ֵ
	var tGrpPolNo=PolGrid.getRowColData(0,9);
	var tContNo=PolGrid.getRowColData(0,8);
	
	fm.GrpPolNo.value = tGrpPolNo;
	fm.ContNo.value = tContNo;
  
}
