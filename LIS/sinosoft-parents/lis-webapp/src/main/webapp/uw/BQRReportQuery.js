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
    easyQueryClick(tContNo,tEdorNo);
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
function easyQueryClick(tContNo,tEdorNo)
{
	var strsql = "";
		
        if(tContNo != "" && tEdorNo != "")
        {
//		strsql = "select (select prtno from lpcont where contno=l.contno and edorno=l.edorno),remark,operator,makedate,replyoperator,"
//		        + " (select username from lduser where usercode=ReplyOperator),"
//		        + " replydate,(case replyflag when '0' then 'δ�ظ�' when '' then 'δ�ظ�' else '�ѻظ�' end),prtseq,(select codename from ldcode where codetype ='rreportreason' and code=l.RReportReason) from lprreport l where 1=1 "
//		        + " and contno = '"+tContNo+"' and edorno='"+tEdorNo+"' "
//		        + " and exists(select 1 from loprtmanager where oldprtseq=l.prtseq)" ;
		     	
		 var sqlid1="BQRReportQuerySql1";
		 var mySql1=new SqlClass();
		 mySql1.setResourceName("uw.BQRReportQuerySql");
		 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
		 mySql1.addSubPara(tContNo);//ָ���������
		 mySql1.addSubPara(tEdorNo);//ָ���������
		 strsql = mySql1.getString();
	
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

	var tPrtSeq = QuestGrid.getRowColData(tSelNo,9);
	
	//��ѯSQL�����ؽ���ַ���
	
//  	var strsql= "select Contente,ReplyContente,remark,makedate,replydate,Rreportfee from LPRReport where 1=1"	
//				+ " and ContNo = '"+tContNo+"'"
//				+ " and PrtSeq = '"+tPrtSeq+"'"; 	
		
  	 var sqlid2="BQRReportQuerySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.BQRReportQuerySql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(tContNo);//ָ���������
	 mySql2.addSubPara(tPrtSeq);//ָ���������
	 var strsql = mySql2.getString();
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
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
// var strSql = " select ContNo,Name,Operator,MakeDate,ReplyOperator,ReplyDate,ReplyFlag from lPRReport where 1=1 "
//          + " and PrtSeq = '"+tPrtSeq+"'";
 
 var sqlid3="BQRReportQuerySql3";
 var mySql3=new SqlClass();
 mySql3.setResourceName("uw.BQRReportQuerySql");
 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
 mySql3.addSubPara(tPrtSeq);//ָ���������
 var strSql = mySql3.getString();
  
   turnPage.queryModal(strSql, QuestGrid);
   
//   strSql ="select RReportItemCode,RReportItemName,Remark from lprreportitem where  prtseq = '"+tPrtSeq+"'";
   
   var sqlid4="BQRReportQuerySql4";
   var mySql4=new SqlClass();
   mySql4.setResourceName("uw.BQRReportQuerySql");
   mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
   mySql4.addSubPara(tPrtSeq);//ָ���������
   strSql = mySql4.getString();

   turnPage.queryModal(strSql, RReportGrid);
   
//   strSql = "select Name,CustomerNo from lPRReport where 1=1 "
//          + " and PrtSeq = '"+tPrtSeq+"'";
   
   var sqlid5="BQRReportQuerySql5";
   var mySql5=new SqlClass();
   mySql5.setResourceName("uw.BQRReportQuerySql");
   mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
   mySql5.addSubPara(tPrtSeq);//ָ���������
   strSql = mySql5.getString();

    
    arrResult = easyExecSql(strSql,1,0);
   
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
	
	   var sqlid6="BQRReportQuerySql6";
	   var mySql6=new SqlClass();
	   mySql6.setResourceName("uw.BQRReportQuerySql");
	   mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
	   mySql6.addSubPara(tContNo);//ָ���������
	   strsql = mySql6.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
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
	
	   var sqlid7="BQRReportQuerySql7";
	   var mySql7=new SqlClass();
	   mySql7.setResourceName("uw.BQRReportQuerySql");
	   mySql7.setSqlId(sqlid7);//ָ��ʹ��SQL��id
	   mySql7.addSubPara(tContNo);//ָ���������
	   mySql7.addSubPara(tContNo);//ָ���������
	   strsql = mySql7.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
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
  fm.submit(); //�ύ
   showInfo.close();
}

//ln 2008-10-24 add
function soreRReport()
{
	window.open("./UWscoreRReportMain.jsp?ContNo="+fm.ContNo.value+"&PrtSeq="+fm.PrtSeq.value,"window5");

}

function easyQueryClickItem()
{
	var tContNo = fm.ContNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
 
	
		if (tContNo != " ")
	{
//		strSQL = "select RReportItemCode,RReportItemName,Remark from lprreportitem where contno = '"+tContNo+"' and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')";
	
		   var sqlid8="BQRReportQuerySql8";
		   var mySql8=new SqlClass();
		   mySql8.setResourceName("uw.BQRReportQuerySql");
		   mySql8.setSqlId(sqlid8);//ָ��ʹ��SQL��id
		   mySql8.addSubPara(tContNo);//ָ���������
		   mySql8.addSubPara(tMissionID);//ָ���������
		   mySql8.addSubPara(tSubMissionID);//ָ���������
		   strSQL = mySql8.getString();
		
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
	
//  	var strsql= "select Contente from LPRReport where 1=1"	
//				+ " and ContNo = '"+tContNo+"'"
//				+ " and trim(prtseq) in (select missionprop14 from lwmission where missionid='"+tMissionID+"' and SubMissionID = '"+tSubMissionID+"' and activityid='0000001120')"; 	
		
  	   var sqlid9="BQRReportQuerySql9";
	   var mySql9=new SqlClass();
	   mySql9.setResourceName("uw.BQRReportQuerySql");
	   mySql9.setSqlId(sqlid9);//ָ��ʹ��SQL��id
	   mySql9.addSubPara(tContNo);//ָ���������
	   mySql9.addSubPara(tMissionID);//ָ���������
	   mySql9.addSubPara(tSubMissionID);//ָ���������
	   var strsql = mySql9.getString();
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strsql);
       
       if(arrReturn!=null)
       {
        
		document.all('Contente').value = arrReturn[0][0];	
}

}