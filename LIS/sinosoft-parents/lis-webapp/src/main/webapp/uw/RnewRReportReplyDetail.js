//�������ƣ�RReportReply.js
//�����ܣ�������鱨��ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var turnPageDetail = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�

//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(fm.Reporter.value==null || fm.Reporter.value=="")
  {
    alert("�����˲���Ϊ�գ�");
    return false;
  }
  if(fm.ReportFee.value==null || fm.ReportFee.value=="")
  {
    alert("�������ò���Ϊ�գ�");
    return false;
  }
  if(fm.ReplyResult.value==null || fm.ReplyResult.value=="")
  {
    alert("�����ظ����ݲ���Ϊ�գ�");
    return false;
  }
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();
    alert(content);
  }
  else
  { 
	var showStr="�����ɹ���";
  	alert(showStr);
  	top.opener.easyQueryClick();
   	top.close();
  	
  	
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
function easyQueryClick()
{ 
  
  var strsql = "";
//  strsql = "select Rnewrreport.contno,lccont.prtno ,Rnewrreport.appntname,Rnewrreport.operator,Rnewrreport.makedate,"
//           + " Rnewrreport.replyoperator,Rnewrreport.replydate,(case Rnewrreport.replyflag when '0' then 'δ�ظ�' else '�ѻظ�' end),Rnewrreport.prtseq "
//           + " from Rnewrreport,lccont where 1 = 1 "           
//           + "and Rnewrreport.contno = lccont.contno "
//           + "and Rnewrreport.contno = '" +PolNo + "'";
  sql_id = "RnewRReportReplyDetailSql0";
 	my_sql = new SqlClass();  
 	my_sql.setResourceName("uw.RnewRReportReplyDetailSql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(PolNo);//ָ������Ĳ���
 	str_sql = my_sql.getString();
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(str_sql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  alert("û����Ҫ�ظ����������棡");
  //easyQueryClickInit();
  return "";
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
  
  return true;
}

// ��ѯ��ť
function easyQueryChoClick()
{	
	
	k++;
	
	var strSQL = "";

	if (PolNo != null && PolNo != "")
	{
//		strSQL = "select contente, remark, replycontente, missionid, submissionid from Rnewrreport,lwmission "
//		       + " where contno = '"+PolNo+"' and prtseq = '"+SerialNo+"' "
//		       + " and contno=missionprop2 and missionprop14='"+SerialNo+"' and activityid='0000007014'";
	    sql_id = "RnewRReportReplyDetailSql1";
	 	my_sql = new SqlClass();  // document.getElementsByName(trim('CodeS'))[0].value
	 	my_sql.setResourceName("uw.RnewRReportReplyDetailSql"); //ָ��ʹ�õ�properties�ļ���
	 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	 	my_sql.addSubPara(PolNo);//ָ������Ĳ���
	 	my_sql.addSubPara(SerialNo);//ָ������Ĳ���
	 	my_sql.addSubPara(SerialNo);//ָ������Ĳ���
	 	str_sql = my_sql.getString();
	}
	
	
	  //��ѯSQL�����ؽ���ַ���
  turnPageDetail.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPageDetail.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPageDetail.arrDataCacheSet = decodeEasyQueryResult(turnPageDetail.strQueryResult);

  fm.Content.value = turnPageDetail.arrDataCacheSet[0][0];
 // fm.Remark.value = turnPageDetail.arrDataCacheSet[0][1];
  fm.ReplyResult.value = turnPageDetail.arrDataCacheSet[0][2];
  fm.ProposalNoHide.value = PolNo;
  fm.PrtSeqHide.value = SerialNo;
  fm.MissionID.value = turnPageDetail.arrDataCacheSet[0][3];
  fm.SubMissionID.value = turnPageDetail.arrDataCacheSet[0][4];

  return true;
}

