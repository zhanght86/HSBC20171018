//�������ƣ�RReportQuery.js
//�����ܣ�������鱨���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    showInfo.close();
    
  }
  else
  { 
 // showInfo.close();
	var showStr="�����ɹ�";
  alert(showStr);
 // parent.close();
    //ִ����һ������
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

// ��ѯ��ť
function easyQueryClick(tContNo)
{
	var strsql = "";
	

		
        if(tContNo != "")
        {
		strsql = "select contno,name,operator,makedate,replyoperator,replydate,replyflag,prtseq,RReportReason from lcrreport where 1=1 "
		        + " and contno = '"+tContNo+"'" ;
		     	
	
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ�鵽�ÿͻ������������Ϣ��");
	initQuestGrid();
    return true;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
}

// ��ѯ��ť
function easyQueryChoClick(tContNo)
{	
  
	
	var tContNo = fm.ContNo.value;

	var tSelNo = QuestGrid.getSelNo()-1;

	var tPrtSeq = QuestGrid.getRowColData(tSelNo,8);
	
		if (tContNo != " ")
	{
		strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and prtseq = '"+tPrtSeq+"'";
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
		fm.Contente.value = strSQL;
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
    return "";
    
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);    
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = RReportGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}


	//��ѯSQL�����ؽ���ַ���
	
  	var strsql= "select Contente,ReplyContente from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and PrtSeq = '"+tPrtSeq+"'"; 	
		
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		fm.all('Contente').value = arrReturn[0][0];
		fm.all('ReplyContente').value = arrReturn[0][1];
	}
  
  return true;
}


function initReport()
{
  
	var i,j,m,n;
	var returnstr;
  var tPrtSeq = fm.all('PrtSeq').value ;
	//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
 var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
  
   turnPage.queryModal(strSql, QuestGrid);
   
   strSql ="select RReportItemCode,RReportItemName from lcrreportitem where  prtseq = '"+tPrtSeq+"'";

   turnPage.queryModal(strSql, RReportGrid);
   
   strSql = "select Name,CustomerNo from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
    
    arrResult = easyExecSql(strSql,1,0);
   
    fm.all('CustomerName').value = arrResult[0][0];
	  fm.all('CustomerNo').value = arrResult[0][1];
   

}



function QueryCont(tGrpContNo)
{

	var strSQL="select ContNo,PrtSeq,CustomerNo,MakeDate from LCRReport where GrpContNo ='"+tGrpContNo+"'";
	turnPage.queryModal(strSQL, ContGrid); 
	return true;
	
}

function showSelectRecord(tContNo)
{
	easyQueryClick(tContNo, fm.all("CustomerNo").value);
}

function afterCodeSelect(cCodeName, Field)
{
	if (cCodeName == "CustomerName")
	{
		showSelectRecord(fm.all("ContNo").value);
	}
}

/*********************************************************************
 *  ������Ϣ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function saveRReport()
{
	var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  fm.action= "./RReportQuerySave.jsp";
  fm.submit(); //�ύ
   showInfo.close();
}

function easyQueryClickItem()
{
	for (i=0; i<ContGrid.mulLineCount; i++) 
   	{
   	 if (ContGrid.getSelNo(i)) 
     	 { 
     		var tContNo=ContGrid.getRowColData(ContGrid.getSelNo()-1,1);
     		var tPrtSeq=ContGrid.getRowColData(ContGrid.getSelNo()-1,2);	
      		//checkFlag = GrpSubGrid.getSelNo();
      		break;
     	 }
   	}

	
		if (tContNo != " ")
	{
		strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and PrtSeq='"+tPrtSeq+"'";
	
		
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
		
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
    return "";
    
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);    
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = RReportGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}


	//��ѯSQL�����ؽ���ַ���
	
  	var strsql= "select ReplyContente from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and PrtSeq='"+tPrtSeq+"'"; 	
		
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		fm.all('ReplyContente').value = arrReturn[0][0];	
}

}
function ReportInfoClick()
{
	 easyQueryClickItem();
	 
}