//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�

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
	var strSQL = ""; 
        //alert(document.all('EventType').value);		
        /**
  	strSQL = "select EventNo,EventType,LogFileName,LogFilePath,PerformState,MakeDate,MakeTime from FIOperationLog where 1=1 ";
		if(document.all('StartDay').value != '')
		{
			strSQL = strSQL + " and MakeDate >= '"+document.all('StartDay').value+"' ";
		}
		if(document.all('EndDay').value != '')
		{
			strSQL = strSQL + " and MakeDate <= '"+document.all('EndDay').value+"' ";
		}
		if(document.all('EventClass').value != '')
		{
			strSQL = strSQL + " and EventType = '"+document.all('EventClass').value+"' ";
		}
		if(document.all('EventFlag').value != '')
		{
			strSQL = strSQL + " and PerformState = '"+document.all('EventFlag').value+"' ";
		}		
		*/
		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.FIOperationLogQuerySql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId("FIOperationLogQuerySql1");//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(1);//ָ������Ĳ���
			mySql1.addSubPara(document.all('StartDay').value);//ָ������Ĳ���
			mySql1.addSubPara(document.all('EndDay').value);//ָ������Ĳ���
			mySql1.addSubPara(document.all('EventClass').value);//ָ������Ĳ���
			mySql1.addSubPara(document.all('EventFlag').value);//ָ������Ĳ���
			strSQL= mySql1.getString();
		
		
		
  	//alert(strSQL);  	
  	turnPage.queryModal(strSQL, FIOperationLogGrid);
} 

function DownloadAddress()
{
	if((document.all('EventNo1').value == '')||(document.all('EventNo1').value == null))
	{
		alert("������ϵͳ������־��ѯ�����ѡ��һ����¼�����ò�����");
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
  else 
  {
    parent.fraMain.rows = "0,0,0,82,*";
  }
}


//ѡ��mulline��һ�У��Զ��������ֵ
 function ReturnData()
{
 	var arrResult = new Array();

	var tSel = FIOperationLogGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	else
	{
	  
		//��ѯ�ɹ������ַ��������ض�ά����
	  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);  	
	  	document.all('EventNo').value = FIOperationLogGrid.getRowColData(tSel-1,1);
	  	document.all('EventType').value = FIOperationLogGrid.getRowColData(tSel-1,2);
	  	document.all('EventNo1').value = FIOperationLogGrid.getRowColData(tSel-1,1);
	  	//fileUrl.href = filepath + FIOperationLogGrid.getRowColData(tSel-1,3); 
	  	//alert(fileUrl.href);
	  	fileUrl.href = "http://localhost:8080/ui/fininterface/log/" + FIOperationLogGrid.getRowColData(tSel-1,3);
	    fileUrl.innerText =  FIOperationLogGrid.getRowColData(tSel-1,1);
	        FIOperationParameterQuery();
	}	 
}

function FIOperationParameterQuery()
{
	var strSQL = ""; 	
	/**	
  	strSQL = "select EventNo,EventType,ParameterType,ParameterMark,ParameterValue from FIOperationParameter"
	         +" where 1=1 "
	         + getWherePart('EventNo','EventNo1')	
	 */     
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIOperationLogQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FIOperationLogQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all('EventNo1').value);//ָ������Ĳ���
		strSQL= mySql2.getString();        
  	turnPage.queryModal(strSQL, FIOperationParameterGrid);	
}
