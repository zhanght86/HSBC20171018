//�������ƣ�RReportQuery.js
//�����ܣ�������鱨���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  �����ˣ�ln    �������ڣ�2008-10-24   ����ԭ��/���ݣ������º˱�Ҫ������޸�

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
    
  }
  else
  { 
 // showInfo.close();
	var showStr="�����ɹ�";
  alert(showStr);
 // parent.close();
    //ִ����һ������
    easyQueryClick(tContNo);
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
//		strsql = "select (select prtno from lccont where contno=l.contno),remark,operator,makedate,replyoperator,"
//		        + " (select username from lduser where usercode=ReplyOperator),"
//		        + " replydate,(case replyflag when '0' then 'δ�ظ�' when '' then 'δ�ظ�' else '�ѻظ�' end),prtseq,(select codename from ldcode where codetype ='rreportreason' and code=l.RReportReason) from RnewRReport l where 1=1 "
//		        + " and contno = '"+tContNo+"'" ;
		     	
		sql_id = "RnewReportQuerySql0";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(tContNo);//ָ������Ĳ���
		str_sql = my_sql.getString();
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(str_sql, 1, 1, 1);  
  
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

	var tPrtSeq = QuestGrid.getRowColData(tSelNo,9);
	
	//��ѯSQL�����ؽ���ַ���
	
//  	var strsql= "select Contente,ReplyContente,remark,makedate,replydate,Rreportfee from RnewRReport where 1=1"	
//				+ " and ContNo = '"+tContNo+"'"
//				+ " and PrtSeq = '"+tPrtSeq+"'"; 	
		
  	sql_id = "RnewReportQuerySql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	my_sql.addSubPara(tPrtSeq);//ָ������Ĳ���
	str_sql = my_sql.getString();
	var arrReturn = new Array();
        arrReturn = easyExecSql(str_sql);
       
       if(arrReturn!=null)
       {
        
		document.all('Contente').value = arrReturn[0][0];
		document.all('ReplyContente').value = arrReturn[0][1];
		document.all('Operator').value = arrReturn[0][2];
		document.all('MakeDate').value = arrReturn[0][3];
		document.all('ReplyDate').value = arrReturn[0][4];
		document.all('RReportFee').value = arrReturn[0][5];
		document.all('PrtSeq').value=tPrtSeq;
		
		if(QueryFlag == null || QueryFlag == '')
		{			
			if(arrReturn[0][2]!=null || arrReturn[0][2]!="")
			   fm.Assess.disabled = false;
		}
		else if(QueryFlag != '1')
		{
			if(arrReturn[0][2]!=null || arrReturn[0][2]!="")
			   fm.Assess.disabled = false;
	    }	    

	}
  
  return true;
}


function initReport()
{
  
	var i,j,m,n;
	var returnstr;
  var tPrtSeq = document.all('PrtSeq').value ;
	//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
// var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from RnewRReport where 1=1 "
//          + " and PrtSeq = '"+tPrtSeq+"'";
 sql_id = "RnewReportQuerySql2";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tPrtSeq);//ָ������Ĳ���
	str_sql = my_sql.getString();
   turnPage.queryModal(str_sql, QuestGrid);
   
//   strSql ="select RReportItemCode,RReportItemName,Remark from RnewRReportitem where  prtseq = '"+tPrtSeq+"'";
   sql_id = "RnewReportQuerySql3";
  	my_sql = new SqlClass();
  	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
  	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
  	my_sql.addSubPara(tPrtSeq);//ָ������Ĳ���
  	str_sql = my_sql.getString();
   turnPage.queryModal(str_sql, RReportGrid);
   
//   strSql = "select Name,CustomerNo from RnewRReport where 1=1 "
//          + " and PrtSeq = '"+tPrtSeq+"'";
   sql_id = "RnewReportQuerySql4";
 	my_sql = new SqlClass();
 	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(tPrtSeq);//ָ������Ĳ���
 	str_sql = my_sql.getString();
    arrResult = easyExecSql(str_sql,1,0);
   
    document.all('CustomerName').value = arrResult[0][0];
	  document.all('CustomerNo').value = arrResult[0][1];
   

}



function QueryCustomerList(tContNo)
{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
//	strsql = "select appntno, appntname from lcappnt where contno = '" + tContNo+"'";
	sql_id = "RnewReportQuerySql5";
 	my_sql = new SqlClass();
 	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(tContNo);//ָ������Ĳ���
 	str_sql = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
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
	
//	strsql = "select insuredno, name from lcinsured where contno = '" + tContNo + "' and not insuredno in (select appntno from lcappnt where contno = '" + tContNo + "')";
	sql_id = "RnewReportQuerySql6";
 	my_sql = new SqlClass();
 	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	my_sql.addSubPara(tContNo);//ָ������Ĳ���
 	str_sql = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
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
	easyQueryClick(tContNo, document.all("CustomerNo").value);
}

function afterCodeSelect(cCodeName, Field)
{
	if (cCodeName == "CustomerName")
	{
		showSelectRecord(document.all("ContNo").value);
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
    fm.action= "./RReportQuerySave.jsp";
    document.getElementById("fm").submit(); //�ύ
    showInfo.close();
}

//ln 2008-10-24 add
function soreRReport()
{
	window.open("./RnewUWscoreRReportMain.jsp?ContNo="+fm.ContNo.value+"&PrtSeq="+fm.PrtSeq.value,"window5");

}

function easyQueryClickItem()
{
	var tContNo = fm.ContNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
 
	
		if (tContNo != " ")
	{
//		strSQL = "select RReportItemCode,RReportItemName,Remark from RnewRReportitem where contno = '"+tContNo+"' and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')";
		sql_id = "RnewReportQuerySql7";
	 	my_sql = new SqlClass();
	 	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
	 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	 	my_sql.addSubPara(tContNo);//ָ������Ĳ���
		my_sql.addSubPara(tMissionID);//ָ������Ĳ���
		my_sql.addSubPara(tSubMissionID);//ָ������Ĳ���
	 	str_sql = my_sql.getString();
		
		turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 1, 1);  
		
  
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
	
//  	var strsql= "select Contente from RnewRReport where 1=1"	
//				+ " and ContNo = '"+tContNo+"'"
//				+ " and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')"; 	
  	sql_id = "RnewReportQuerySql8";
 	my_sql = new SqlClass();
 	my_sql.setResourceName("uw.RnewReportQuerySql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	my_sql.addSubPara(tMissionID);//ָ������Ĳ���
	my_sql.addSubPara(tSubMissionID);//ָ������Ĳ���
 	str_sql = my_sql.getString();	
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(str_sql);
       
       if(arrReturn!=null)
       {
        
		document.all('Contente').value = arrReturn[0][0];	
}

}