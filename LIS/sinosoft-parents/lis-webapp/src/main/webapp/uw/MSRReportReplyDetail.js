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
  if(fm.ReplyResult.value.length>2000)
  {
	  alert("�����ظ����ݲ��ܳ���2000�֣�");
	    return false;
  }
  document.all('ActivityID').value = ActivityID;
  //alert(ActivityID);
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  lockScreen('lkscreen');  
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
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
 //  alert('%%%') ;
  var strsql = "";
  
  var sqlid917114209="DSHomeContSql917114209";
var mySql917114209=new SqlClass();
mySql917114209.setResourceName("uw.MSRReportReplyDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql917114209.setSqlId(sqlid917114209);//ָ��ʹ�õ�Sql��id
mySql917114209.addSubPara(PolNo);//ָ������Ĳ���
strsql = mySql917114209.getString();
// alert('%%%') ;
//  strsql = "select lcrreport.contno,lccont.prtno ,lcrreport.appntname,lcrreport.operator,lcrreport.makedate,"
//           + " lcrreport.replyoperator,lcrreport.replydate,decode(lcrreport.replyflag,'0','δ�ظ�','�ѻظ�'),lcrreport.prtseq "
//           + " from lcrreport,lccont where 1 = 1 "           
//           + "and lcrreport.contno = lccont.contno "
//           + "and lcrreport.contno = '" +PolNo + "'";
           //+ "and lcrreport.prtseq = '" + SerialNo +"'";
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
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
		var sqlid917114321="DSHomeContSql917114321";
var mySql917114321=new SqlClass();
mySql917114321.setResourceName("uw.MSRReportReplyDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql917114321.setSqlId(sqlid917114321);//ָ��ʹ�õ�Sql��id
mySql917114321.addSubPara(PolNo);//ָ������Ĳ���
mySql917114321.addSubPara(SerialNo);//ָ������Ĳ���
mySql917114321.addSubPara(SerialNo);//ָ������Ĳ���
strSQL=mySql917114321.getString();

		
//		strSQL = "select contente, remark, replycontente, missionid, submissionid from lcrreport,lwmission "
//		       + " where contno = '"+PolNo+"' and prtseq = '"+SerialNo+"' "
//		       + " and contno=missionprop2 and missionprop14='"+SerialNo+"' and activityid='0000001120'";
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

  return true;
}

