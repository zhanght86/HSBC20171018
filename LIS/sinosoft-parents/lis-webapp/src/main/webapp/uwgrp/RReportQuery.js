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
var sqlresourcename = "uwgrp.RReportQuerySql";
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
        {/*
		strsql = "select contno,name,operator,makedate,replyoperator,replydate,replyflag,prtseq,RReportReason from lcrreport where 1=1 "
		        + " and contno = '"+tContNo+"'" ;
		 */    	
	var sqlid1="RReportQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
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
		//strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and prtseq = '"+tPrtSeq+"'";
		
		var sqlid2="RReportQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
		mySql2.addSubPara(tPrtSeq);
		
		turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
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
	/*
  	var strsql= "select Contente,ReplyContente,operator,makedate,modifydate,Rreportfee from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and PrtSeq = '"+tPrtSeq+"'"; 	
	*/	
		var sqlid3="RReportQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
		mySql3.addSubPara(tPrtSeq);
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(mySql3.getString());
       
       if(arrReturn!=null)
       {
        
		fm.all('Contente').value = arrReturn[0][0];
		fm.all('ReplyContente').value = arrReturn[0][1];
		fm.all('Operator').value = arrReturn[0][2];
		fm.all('MakeDate').value = arrReturn[0][3];
		fm.all('ReplyDate').value = arrReturn[0][4];
		fm.all('RReportFee').value = arrReturn[0][5];
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
	/*
 var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
  */
  var sqlid4="RReportQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tPrtSeq);
  
   turnPage.queryModal(mySql4.getString(), QuestGrid);
   
 //  strSql ="select RReportItemCode,RReportItemName from lcrreportitem where  prtseq = '"+tPrtSeq+"'";
  
  var sqlid5="RReportQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tPrtSeq);
		
   turnPage.queryModal(mySql5.getString(), RReportGrid);
   /*
   strSql = "select Name,CustomerNo from lcRReport where 1=1 "
          + " and PrtSeq = '"+tPrtSeq+"'";
    */
    var sqlid6="RReportQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tPrtSeq);
    
    arrResult = easyExecSql(mySql6.getString(),1,0);
   
    fm.all('CustomerName').value = arrResult[0][0];
	  fm.all('CustomerNo').value = arrResult[0][1];
   

}



function QueryCustomerList(tContNo)
{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	//strsql = "select appntno, appntname from lcappnt where contno = '" + tContNo+"'";
	
	var sqlid7="RReportQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);  
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][1] + "|" + turnPage.arrDataCacheSet[i][0];
		}
	}
	//strsql = "select insuredno, name from lcinsured where contno = '" + tContNo + "' and not insuredno in (select appntno from lcappnt where contno = '" + tContNo + "')";
	
	var sqlid8="RReportQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tContNo);
		mySql8.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
	if (turnPage.strQueryResult != null && turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		n = turnPage.arrDataCacheSet.length;
		for (i = 0; i < n; i++)
		{
			j = i + m + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][1] + "|" + turnPage.arrDataCacheSet[i][0];
		}
	}
//	alert ("tcodedata : " + tCodeData);

	return tCodeData;
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
	var tContNo = fm.ContNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
 
	
		if (tContNo != " ")
	{
		//strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where contno = '"+tContNo+"' and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')";
	
		var sqlid9="RReportQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(tContNo);
		mySql9.addSubPara(tMissionID);
		mySql9.addSubPara(tSubMissionID);
	
		turnPage.strQueryResult  = easyQueryVer3(mySql9.getString(), 1, 1, 1);  
		
  
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
	/*
  	var strsql= "select Contente from LCRReport where 1=1"	
				+ " and ContNo = '"+tContNo+"'"
				+ " and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')"; 	
	*/	
		var sqlid10="RReportQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(tContNo);
		mySql10.addSubPara(tMissionID);
		mySql10.addSubPara(tSubMissionID);
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(mySql10.getString());
       
       if(arrReturn!=null)
       {
        
		fm.all('Contente').value = arrReturn[0][0];	
}

}