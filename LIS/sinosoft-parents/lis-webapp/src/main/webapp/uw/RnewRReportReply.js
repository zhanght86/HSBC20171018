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

  document.getElementById("fm").submit(); //�ύ
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
  /*var wherePartStr = getWherePart( 'lccont.ContNo','QPrtNo' )
           +  getWherePart( 'Rnewrreport.name','QInsuredName' )
           +  getWherePart( 'lccont.ManageCom','QManageCom' );*/
  var sqlid916150045="DSHomeContSql916150045";
var mySql916150045=new SqlClass();
mySql916150045.setResourceName("uw.RnewRReportReplySql");//ָ��ʹ�õ�properties�ļ���
mySql916150045.setSqlId(sqlid916150045);//ָ��ʹ�õ�Sql��id
mySql916150045.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql916150045.addSubPara(fm.QInsuredName.value);
mySql916150045.addSubPara(fm.QManageCom.value);
strsql=mySql916150045.getString();
  
//  strsql = "select Rnewrreport.contno,lccont.prtno ,Rnewrreport.name,Rnewrreport.operator,Rnewrreport.makedate,"
//           + " Rnewrreport.replyoperator,Rnewrreport.replydate,Rnewrreport.replyflag,Rnewrreport.prtseq, lwmission.missionid, lwmission.submissionid "
//           + " from Rnewrreport,lccont,lwmission where 1 = 1 "           
//           + "and Rnewrreport.contno = lccont.contno and lccont.contno=lwmission.missionprop2 and ActivityID='0000007014'"
//           +  getWherePart( 'lccont.ContNo','QPrtNo' )
//           +  getWherePart( 'Rnewrreport.name','QInsuredName' )
//           +  getWherePart( 'lccont.ManageCom','QManageCom' )
//           + " and Rnewrreport.contente is not null"
//           + " and Rnewrreport.replycontente is null"
//           + " and prtseq is not null "
//           + " and Rnewrreport.replyflag = '0'"
//           + " order by Rnewrreport.makedate,Rnewrreport.maketime";
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  alert("û�д��ظ����������棡");
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
function easyQueryChoClick(parm1,parm2)
{	
	// ��дSQL���
	k++;
	var tProposalNo = "";
	var strSQL = "";

	if(document.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tProposalNo = document.all(parm1).all('QuestGrid1').value;
   		tSerialNo = document.all(parm1).all('QuestGrid9').value;
   		//alert(tSerialNo);
  	}	

	if (tProposalNo != " ")
	{
         window.open( "RnewRReportReplyDetailMain.jsp?PolNo=" + tProposalNo + "&SerialNo=" + tSerialNo );
	}
	
}


function ClearContent()
{
	fm.Content.value = '';
    fm.ReplyResult.value = '';
    fm.ProposalNoHide.value = '';
}