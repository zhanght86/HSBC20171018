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
  	initInpBox();
	initQuestGrid();
  	//showInfo.close();
  	
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
	
    	//initInpBox();
  initQuestGrid();
  
  
  var strsql = "";
  
  /*var wherePartStr = getWherePart( 'lprreport.contno','QContNo' )
           +  getWherePart( 'lpcont.insuredname','QInsuredName','like' )
           +  getWherePart( 'lpcont.ManageCom','QManageCom' );*/
  var sqlid915154553="DSHomeContSql915154553";
var mySql915154553=new SqlClass();
mySql915154553.setResourceName("uw.BQRReportReplySql");//ָ��ʹ�õ�properties�ļ���
mySql915154553.setSqlId(sqlid915154553);//ָ��ʹ�õ�Sql��id
mySql915154553.addSubPara(fm.QContNo.value);//ָ������Ĳ���
mySql915154553.addSubPara(fm.QInsuredName.value);
mySql915154553.addSubPara(fm.QManagecom.value);
mySql915154553.addSubPara(comcode+"%%");//ָ������Ĳ���
strsql=mySql915154553.getString();
  
  
//  strsql = "select lprreport.contno,lpcont.prtno ,lpcont.insuredname,lprreport.operator,lprreport.makedate,"
//           + " lprreport.replyoperator,lprreport.replydate,lprreport.replyflag,lprreport.prtseq, lwmission.missionid, lwmission.submissionid "
//           + " from lprreport,lpcont,lwmission where 1 = 1 "       
//           + " and lprreport.edorno=lpcont.edorno "    
//           + " and lprreport.contno = lpcont.contno and lpcont.contno=lwmission.missionprop2 and ActivityID='0000000316' "
//           +  getWherePart( 'lprreport.contno','QContNo' )
//           +  getWherePart( 'lpcont.insuredname','QInsuredName','like' )
//           +  getWherePart( 'lpcont.ManageCom','QManageCom' )
//           + " and lpcont.managecom like '" + comcode + "%%'"  //����Ȩ�޹�������
//           + " and lprreport.contente is not null"
//           + " and lprreport.replycontente is null"
//           + " and prtseq is not null "
//           + " and lprreport.replyflag = '0'"
//           + " order by lprreport.makedate,lprreport.maketime";
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  
  /*var wherePartStr = getWherePart( 'lprreport.contno','QContNo' )
           +  getWherePart( 'lprreport.name','QInsuredName' )
           +  getWherePart( 'lccont.ManageCom','QManageCom' );*/
  var sqlid915154904="DSHomeContSql915154904";
var mySql915154904=new SqlClass();
mySql915154904.setResourceName("uw.BQRReportReplySql");//ָ��ʹ�õ�properties�ļ���
mySql915154904.setSqlId(sqlid915154904);//ָ��ʹ�õ�Sql��id
mySql915154553.addSubPara(fm.QContNo.value);//ָ������Ĳ���
mySql915154553.addSubPara(fm.QInsuredName.value);
mySql915154553.addSubPara(fm.QManagecom.value);
mySql915154904.addSubPara(comcode+"%%");//ָ������Ĳ���
var strSQL=mySql915154904.getString();
  
//  strsql = "select lprreport.contno,lccont.prtno ,lprreport.name,lprreport.operator,lprreport.makedate,"
//           + " lprreport.replyoperator,lprreport.replydate,lprreport.replyflag,lprreport.prtseq, lwmission.missionid, lwmission.submissionid "
//           + " from lprreport,lccont,lwmission where 1 = 1 "           
//           + "and lprreport.contno = lccont.contno and lccont.contno=lwmission.missionprop2 and ActivityID='0000000316'"
//           +  getWherePart( 'lprreport.contno','QContNo' )
//           +  getWherePart( 'lprreport.name','QInsuredName' )
//           +  getWherePart( 'lccont.ManageCom','QManageCom' )
//           + " and lccont.managecom like '" + comcode + "%%'"  //����Ȩ�޹�������
//           + " and lprreport.contente is not null"
//           + " and lprreport.replycontente is null"
//           + " and prtseq is not null "
//           + " and lprreport.replyflag = '0'"
//           + " order by lprreport.makedate,lprreport.maketime";
   turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  //alert("û�д��ظ����������棡");
  //easyQueryClickInit();
  //return "";
}

if (!turnPage.strQueryResult) {
	alert("û�д��ظ����������棡");
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
function easyQueryChoClick(parm1,parm2)
{	
	// ��дSQL���
	k++;
	var tProposalNo = "";
	var strSQL = "";
	var tActivityID="";
	var selectRowNum = parm1.replace(/spanQuestGrid/g,"");
	if(document.all('InpQuestGridSel'+selectRowNum).value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tProposalNo = document.all('QuestGrid1'+'r'+selectRowNum).value;
   		tSerialNo = document.all('QuestGrid9'+'r'+selectRowNum).value;
   		tActivityID =document.all('QuestGrid12'+'r'+selectRowNum).value;;
   		//alert(tSerialNo);
  	}
	if (tProposalNo != " ")
	{
         window.open( "BQRReportReplyDetailMain.jsp?PolNo=" + tProposalNo + "&ActivityID=" + tActivityID +"&SerialNo=" + tSerialNo ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
	
}


function ClearContent()
{
	fm.Content.value = '';
	fm.ReplyResult.value = '';
	fm.ProposalNoHide.value = '';
}