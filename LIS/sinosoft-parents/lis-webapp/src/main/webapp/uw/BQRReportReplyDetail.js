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
  var sqlid917113605="DSHomeContSql917113605";
var mySql917113605=new SqlClass();
mySql917113605.setResourceName("uw.BQRReportReplyDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql917113605.setSqlId(sqlid917113605);//ָ��ʹ�õ�Sql��id
mySql917113605.addSubPara(PolNo);//ָ������Ĳ���
strsql=mySql917113605.getString();
  
//  strsql = "select lprreport.contno,lpcont.prtno ,lprreport.appntname,lprreport.operator,lprreport.makedate,"
//           + " lprreport.replyoperator,lprreport.replydate,decode(lprreport.replyflag,'0','δ�ظ�','�ѻظ�'),lprreport.prtseq "
//           + " from lprreport,lpcont where 1 = 1 "           
//           + "and lprreport.contno = lpcont.contno and lprreport.edorno=lpcont.edorno "
//           + "and lprreport.contno = '" +PolNo + "' order by lprreport.makedate,lprreport.maketime";
           //+ "and lcrreport.prtseq = '" + SerialNo +"'";
	//prompt("",strsql);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  
  var sqlid917113708="DSHomeContSql917113708";
var mySql917113708=new SqlClass();
mySql917113708.setResourceName("uw.BQRReportReplyDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql917113708.setSqlId(sqlid917113708);//ָ��ʹ�õ�Sql��id
mySql917113708.addSubPara(PolNo);//ָ������Ĳ���
strsql=mySql917113708.getString();
  
//  strsql = "select lprreport.contno,lccont.prtno ,lprreport.appntname,lprreport.operator,lprreport.makedate,"
//           + " lprreport.replyoperator,lprreport.replydate,decode(lprreport.replyflag,'0','δ�ظ�','�ѻظ�'),lprreport.prtseq "
//           + " from lprreport,lccont where 1 = 1 "           
//           + "and lprreport.contno = lccont.contno "
//           + "and lprreport.contno = '" +PolNo + "' order by lprreport.makedate,lprreport.maketime";
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  //alert("û����Ҫ�ظ����������棡");
  //easyQueryClickInit();
  //return "";
}

	if (!turnPage.strQueryResult) {
		alert("û����Ҫ�ظ����������棡");
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
		var sqlid917113841="DSHomeContSql917113841";
var mySql917113841=new SqlClass();
mySql917113841.setResourceName("uw.BQRReportReplyDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql917113841.setSqlId(sqlid917113841);//ָ��ʹ�õ�Sql��id
mySql917113841.addSubPara(PolNo);//ָ������Ĳ���
mySql917113841.addSubPara(SerialNo);//ָ������Ĳ���
mySql917113841.addSubPara(SerialNo);//ָ������Ĳ���
strSQL=mySql917113841.getString();
		
//		strSQL = "select contente, remark, replycontente, missionid, submissionid,edorno from lprreport,lwmission "
//		       + " where contno = '"+PolNo+"' and prtseq = '"+SerialNo+"' "
//		       + " and contno=missionprop2 and missionprop14='"+SerialNo+"' and activityid='0000000316'";
	}
	
	
	  //��ѯSQL�����ؽ���ַ���
  turnPageDetail.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
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
  fm.EdorNo.value = turnPageDetail.arrDataCacheSet[0][5];
  fm.ActivityID.value = turnPageDetail.arrDataCacheSet[0][6];

  return true;
}

