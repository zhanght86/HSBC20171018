//             ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mOperate="";
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


//����ҳ���Զ���ѯ

function OperationLogQuery() 
{		
	  var sStartDay = document.all('StartDay').value;
		var sEndDay = document.all('EndDay').value;
		var strSQL = ""; 
		
		
  	//alert(strSQL);  
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIRuleDealLogQueryForDownLoadSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FIRuleDealLogQueryForDownLoadSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('StartDay').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('EndDay').value);//ָ������Ĳ���
		strSQL= mySql1.getString();
  		
  	turnPage.queryModal(strSQL, FIRuleDealLogQueryForDownLoadGrid);
  	
  	strSQL = "select CheckSerialNo,VersionNo,RuleDealBatchNo,DataSourceBatchNo,CallPointID,RuleDealResult,RulePlanID from FIRuleDealLog where 1=1 ";
  	if(document.all('StartDay').value != '')
  	{
  		strSQL = strSQL + " and RuleDealDate >= '"+document.all('StartDay').value+"' ";
  	}
  	if(document.all('EndDay').value != '')
  	{
  		strSQL = strSQL + " and RuleDealDate <= '"+document.all('EndDay').value+"' ";
  	}
} 

function DownloadAddress()
{
	if((document.all('CheckSerialNo1').value == '')||(document.all('CheckSerialNo1').value == null))
	{
		alert("������У����־��Ϣ��ѯ�����ѡ��һ����¼�����ò�����");
    return ;
	}
	Querydiv.style.display='';
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


//���ӡ�ɾ�����޸��ύǰ��У�顢����
function beforeSubmit()
{			
  if (!verifyInput2())
  {
  	return false;
  }
    
    return true;
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


//ѡ��mulline��һ�У��Զ��������ֵ
 function ReturnData()
{
 	var arrResult = new Array();

	var tSel = FIRuleDealLogQueryForDownLoadGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	else
	{
	//������Ҫ���ص�����
	// ��дSQL���		
  	var strSQL = "";
  	/**
		strSQL = "select a.CheckSerialNo,a.RulePlanID,a.LogFileName,a.LogFilePath from FIRuleDealLog a where a.CheckSerialNo='"+
	          FIRuleDealLogQueryForDownLoadGrid.getRowColData(tSel-1,1)+"'";
	          */
		var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.FIRuleDealLogQueryForDownLoadSql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("FIRuleDealLogQueryForDownLoadSql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(FIRuleDealLogQueryForDownLoadGrid.getRowColData(tSel-1,1));//ָ������Ĳ���
			strSQL= mySql2.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
		
		
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
	//��ѯ�ɹ������ַ��������ض�ά����
  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);  	
  	document.all('CheckSerialNo').value = arrResult[0][0];
  	document.all('RulePlanID').value = arrResult[0][1];
  	document.all('CheckSerialNo1').value = arrResult[0][0];
  	fileUrl.href = arrResult[0][3]+arrResult[0][2]; 
  	alert(fileUrl.href);
    fileUrl.innerText =  arrResult[0][0];  	  
	}	 
}

