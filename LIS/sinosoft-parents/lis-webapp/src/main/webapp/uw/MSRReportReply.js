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
 // initQuestGrid();
  
  
  var strsql = "";
  
  /*var wherePartStr =  getWherePart( 'lccont.PrtNo','QPrtNo' )
           +  getWherePart( 'lcrreport.name','QInsuredName' )
           +  getWherePart( 'lccont.ManageCom','QManageCom' );*/
  var sqlid915155541="DSHomeContSql915155541";
var mySql915155541=new SqlClass();
mySql915155541.setResourceName("uw.MSRReportReplySql");//ָ��ʹ�õ�properties�ļ���
mySql915155541.setSqlId(sqlid915155541);//ָ��ʹ�õ�Sql��id
mySql915155541.addSubPara(fm.QPrtNo.value);//ָ������Ĳ���
mySql915155541.addSubPara(fm.QInsuredName.value);
mySql915155541.addSubPara(fm.QManageCom.value);
mySql915155541.addSubPara(comcode+"%%");//ָ������Ĳ���
strsql=mySql915155541.getString();
  
//  strsql = "select lcrreport.contno,lccont.prtno ,lcrreport.name,lcrreport.operator,lcrreport.makedate,"
//           + " lcrreport.replyoperator,lcrreport.replydate,lcrreport.replyflag,lcrreport.prtseq, lwmission.missionid, lwmission.submissionid "
//           + " from lcrreport,lccont,lwmission where 1 = 1 "           
//           + "and lcrreport.contno = lccont.contno and lccont.contno=lwmission.missionprop2 and ActivityID='0000001120'"
//           +  getWherePart( 'lccont.PrtNo','QPrtNo' )
//           +  getWherePart( 'lcrreport.name','QInsuredName' )
//           +  getWherePart( 'lccont.ManageCom','QManageCom' )
//           + " and lccont.managecom like '" + comcode + "%%'"  //����Ȩ�޹�������
//           + " and lcrreport.contente is not null"
//           + " and lcrreport.replycontente is null"
//           + " and prtseq is not null "
//           + " and lcrreport.replyflag = '0'"
//           + " order by lcrreport.makedate,lcrreport.maketime";
	
  //��ѯSQL�����ؽ���ַ���
  //queryModal
  turnPage.queryModal(strsql,QuestGrid);
  //turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  alert("û�д��ظ����������棡");
  //easyQueryClickInit();
  return "";
}
  /*
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
  */
  return true;
}

// ��ѯ��ť
function easyQueryChoClick(parm1,parm2)
{	
	// ��дSQL���
	k++;
	var tProposalNo = "";
	var strSQL = "";
	
	var selectRowNum = parm1.replace(/spanPublicWorkPoolGrid/g,"");	
	
	if(document.all('InpPublicWorkPoolGridSel'+selectRowNum).value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tProposalNo = document.all('PublicWorkPoolGrid1'+'r'+selectRowNum).value;
		tSerialNo = document.all('PublicWorkPoolGrid18'+'r'+selectRowNum).value;
   		//tSerialNo = document.all(parm1).all('PublicWorkPoolGrid18').value;  	
   		//add by lzf 2013-+03-26
		var tActivityID = document.all('PublicWorkPoolGrid22'+'r'+selectRowNum).value;
		var tMissionID = document.all('PublicWorkPoolGrid19'+'r'+selectRowNum).value;
		var tSubMissionID = document.all('PublicWorkPoolGrid20'+'r'+selectRowNum).value;
   		
		//var tActivityID = document.all(parm1).all('PublicWorkPoolGrid22').value;
   		//var tMissionID = document.all(parm1).all('PublicWorkPoolGrid19').value;
   		//var tSubMissionID = document.all(parm1).all('PublicWorkPoolGrid20').value;
   		//alert("tActivityID==="+tActivityID+"tMissionID===="+tMissionID+"tSubMissionID----=-=-"+tSubMissionID);
  	}	

	if (tProposalNo != " ")
	{
         window.open( "MSRReportReplyDetailMain.jsp?PolNo=" + tProposalNo + "&SerialNo=" + tSerialNo+ "&ActivityID=" + tActivityID+ "&MissionID=" + tMissionID+ "&SubMissionID=" + tSubMissionID,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
	
}


function ClearContent()
{
	fm.Content.value = '';
    fm.ReplyResult.value = '';
    fm.ProposalNoHide.value = '';
}