//�������ƣ�QuestQuery.js
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var canReplyFlag = false;

//�ύ,���水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}

//�ύ,���水ť��Ӧ����
function IfPrint()
{
  var i = 0;
  var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
��cContNo = document.getELmentById("fm").ContNo.value;  //��������	
//��var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";

 var sqlid1="BQQuestQuerySql1";
 var mySql1=new SqlClass();
 mySql1.setResourceName("uw.BQQuestQuerySql");
 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
 mySql1.addSubPara(cContNo);//ָ���������
 var strSql = mySql1.getString();

    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {     
   ����alert("�ѷ��˱�֪ͨ��,��δ��ӡ,�������ڴ�ʱ�޸�������Ĵ�ӡ��־��");
          return;
      }      
  fm.action = "./QuestQueryUpdatePrintFlag.jsp";
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
  else if (FlagStr == "Succ") {
    content = "�����ɹ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //alert(document.all('hiddenOperate').value);
    if(document.all('hiddenOperate').value!=null&&document.all('hiddenOperate').value=='finishMission')
    {    
        showInfo.close();	
    	top.opener.easyQueryClickSelf();
    	top.close();    	
    }
  }
  else
  { 
	var showStr="��ӡ��Ǹ��³ɹ�";
  	alert(showStr);
  }
}

//��ʾfrmSubmit���,��������
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
         

//��ʾdiv,��һ������Ϊһ��div������,�ڶ�������Ϊ�Ƿ���ʾ,���Ϊ"true"����ʾ,������ʾ
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

function manuchkspecmain()
{
	fm.submit();
}

function query()
{
	var tFlag = fm.Flag.value;
	// ��ʼ�����
	initQuestGrid(tFlag);
	initContent();

	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	
	var tContNo = fm.ContNo.value;
	var tEdorNo = fm.EdorNo.value;	
	
	if (ifreply == "Y")
	{
	
	  
//		strSQL = "select EdorNo,issuetype,issuecont,replyresult,operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(select codename from ldcode where codetype='bqbackobj' and comcode=BackObjType),needprint,serialno,makedate,modifydate,OperatePos from lpissuepol a where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')	  	
				 
		 var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  EdorNo2 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	     var  BackObj2 = window.document.getElementsByName(trim("BackObj"))[0].value;
	     var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  OManageCom2 = window.document.getElementsByName(trim("OManageCom"))[0].value;
	     var  OperatePos2 = window.document.getElementsByName(trim("OperatePos"))[0].value;
		 var sqlid2="BQQuestQuerySql2";
		 var mySql2=new SqlClass();
		 mySql2.setResourceName("uw.BQQuestQuerySql");
		 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
		 mySql2.addSubPara(k);//ָ���������
		 mySql2.addSubPara(k);//ָ���������
		 mySql2.addSubPara(manageCom);//ָ���������
		 mySql2.addSubPara(ContNo2);//ָ���������
		 mySql2.addSubPara(EdorNo2);//ָ���������
		 mySql2.addSubPara(BackObj2);//ָ���������
		 mySql2.addSubPara(ManageCom2);//ָ���������
		 mySql2.addSubPara(OManageCom2);//ָ���������
		 mySql2.addSubPara(OperatePos2);//ָ���������
		 strSQL = mySql2.getString();
				 
	}
 	else if (ifreply == "N")
	{	  
//		strSQL = "select EdorNo,issuetype,issuecont,replyresult,operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(select codename from ldcode where codetype='bqbackobj' and comcode=BackObjType),needprint,serialno,makedate,'',OperatePos from lpissuepol a where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  
		
		 var  ContNo3 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  EdorNo3 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	     var  BackObj3 = window.document.getElementsByName(trim("BackObj"))[0].value;
	     var  ManageCom3 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  OManageCom3 = window.document.getElementsByName(trim("OManageCom"))[0].value;
	     var  OperatePos3 = window.document.getElementsByName(trim("OperatePos"))[0].value;
	     var  Quest3 = window.document.getElementsByName(trim("Quest"))[0].value;
		 var sqlid3="BQQuestQuerySql3";
		 var mySql3=new SqlClass();
		 mySql3.setResourceName("uw.BQQuestQuerySql");
		 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
		 mySql3.addSubPara(k);//ָ���������
		 mySql3.addSubPara(k);//ָ���������
		 mySql3.addSubPara(manageCom);//ָ���������
		 mySql3.addSubPara(ContNo3);//ָ���������
		 mySql3.addSubPara(EdorNo3);//ָ���������
		 mySql3.addSubPara(BackObj3);//ָ���������
		 mySql3.addSubPara(ManageCom3);//ָ���������
		 mySql3.addSubPara(OManageCom3);//ָ���������
		 mySql3.addSubPara(OperatePos3);//ָ���������
		 mySql3.addSubPara(Quest3);//ָ���������
		 strSQL = mySql3.getString();
		
	}
	//�������֧
	else
	{
		
//		strSQL = "select EdorNo,issuetype,issuecont,replyresult,operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(select codename from ldcode where codetype='bqbackobj' and comcode=BackObjType),needprint,serialno,makedate,(case ReplyMan when '' then (case when (select donedate from LOPRTManager where prtseq=a.prtseq and stateflag='2') is not null then (select donedate from LOPRTManager where prtseq=a.prtseq and stateflag='2') else null end) else modifydate end),OperatePos from lpissuepol a where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		
		 var  ContNo4 = window.document.getElementsByName(trim("ContNo"))[0].value;
	     var  EdorNo4 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	     var  BackObj4 = window.document.getElementsByName(trim("BackObj"))[0].value;
	     var  ManageCom4 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	     var  OManageCom4 = window.document.getElementsByName(trim("OManageCom"))[0].value;
	     var  OperatePos4 = window.document.getElementsByName(trim("OperatePos"))[0].value;
	     var  Quest4 = window.document.getElementsByName(trim("Quest"))[0].value;
		 var sqlid4="BQQuestQuerySql4";
		 var mySql4=new SqlClass();
		 mySql4.setResourceName("uw.BQQuestQuerySql");
		 mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
		 mySql4.addSubPara(k);//ָ���������
		 mySql4.addSubPara(k);//ָ���������
		 mySql4.addSubPara(manageCom);//ָ���������
		 mySql4.addSubPara(ContNo4);//ָ���������
		 mySql4.addSubPara(EdorNo4);//ָ���������
		 mySql4.addSubPara(BackObj4);//ָ���������
		 mySql4.addSubPara(ManageCom4);//ָ���������
		 mySql4.addSubPara(OManageCom4);//ָ���������
		 mySql4.addSubPara(OperatePos4);//ָ���������
		 mySql4.addSubPara(Quest4);//ָ���������
		 strSQL = mySql4.getString();
		
	}
//	strSQL = strSQL + " order by a.makedate,a.maketime ";
	
//	//prompt('',strSQL);
	
  //��ѯSQL,���ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult == false) {
    alert("û�������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����,HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;	
}

function queryone(parm1,parm2)
{	
	k++;
	var strSQL = "";
	var tcho;
	var tOperatePos = fm.Flag.value;
	var tselno=QuestGrid.getSelNo()-1;
	//tongmeng 2007-10-22 modify
	//ֱ��ȡ����λ�á�
	//tPos=QuestGrid.getRowColData(tselno,7);
	tPos=QuestGrid.getRowColData(tselno,13);	
	tQuest = QuestGrid.getRowColData(tselno,2);;
	tSerialNo = QuestGrid.getRowColData(tselno,10);;
	
	document.all('HideOperatePos').value=tPos;
	document.all('HideQuest').value=tQuest;
	document.all('HideSerialNo').value=tSerialNo;
	document.all('SerialNo').value=tSerialNo;
	tContNo = fm.ContNo.value;
	if (tPos == "")
	{
		alert("��ѡ�������!")
		return "";
	}	
	
	if (tContNo == "")
	{
		alert("�����Ų���Ϊ�գ�");
		return "";
	}
	if (tQuest == "")
	{
		alert("���������Ϊ�գ�");
		return "";
	}
	if (tSerialNo == "")
	{
		alert("��������벻��Ϊ�գ�");
		return "";
	}
	
//	strSQL = "select issuecont,replyresult,issuetype,OperatePos,operator,makedate,modifydate from lpissuepol where "+k+"="+k+" "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'issuetype','HideQuest' )
//				 //+ getWherePart( 'OperatePos','HideOperatePos')
//				 + getWherePart( 'SerialNo','HideSerialNo');
//				 //+ getWherePart( 'BackObjType','BackObj')
//				 //+ getWherePart( 'ManageCom','ManageCom')
//				 //+ getWherePart( 'IssueManageCom','OManageCom')
	
				 if(document.all('HideOperatePos').value=="5")
				 {
					strSQL = strSQL + " and OperatePos in ('5','I','A')";
					
					 var  ContNo5 = window.document.getElementsByName(trim("ContNo"))[0].value;
				     var  EdorNo5 = window.document.getElementsByName(trim("EdorNo"))[0].value;				    
				     var  HideQuest5 = window.document.getElementsByName(trim("HideQuest"))[0].value;
				     var  HideSerialNo5 = window.document.getElementsByName(trim("HideSerialNo"))[0].value;
					 var sqlid5="BQQuestQuerySql5";
					 var mySql5=new SqlClass();
					 mySql5.setResourceName("uw.BQQuestQuerySql");
					 mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
					 mySql5.addSubPara(k);//ָ���������
					 mySql5.addSubPara(k);//ָ���������
					 mySql5.addSubPara(ContNo5);//ָ���������
					 mySql5.addSubPara(EdorNo5);//ָ���������
					 mySql5.addSubPara(HideQuest5);//ָ���������
					 mySql5.addSubPara(HideSerialNo5);//ָ���������
					 strSQL = mySql5.getString();
				 }
				 else
				 {
				//	strSQL = strSQL + getWherePart( 'OperatePos','HideOperatePos');
					
					 var  ContNo6 = window.document.getElementsByName(trim("ContNo"))[0].value;
				     var  EdorNo6 = window.document.getElementsByName(trim("EdorNo"))[0].value;				    
				     var  HideQuest6 = window.document.getElementsByName(trim("HideQuest"))[0].value;
				     var  HideSerialNo6 = window.document.getElementsByName(trim("HideSerialNo"))[0].value;
				     var  HideOperatePos6 = window.document.getElementsByName(trim("HideOperatePos"))[0].value;
					 var sqlid6="BQQuestQuerySql6";
					 var mySql6=new SqlClass();
					 mySql6.setResourceName("uw.BQQuestQuerySql");
					 mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
					 mySql6.addSubPara(k);//ָ���������
					 mySql6.addSubPara(k);//ָ���������
					 mySql6.addSubPara(ContNo6);//ָ���������
					 mySql6.addSubPara(EdorNo6);//ָ���������
					 mySql6.addSubPara(HideQuest6);//ָ���������
					 mySql6.addSubPara(HideSerialNo6);//ָ���������
					 mySql6.addSubPara(HideOperatePos6);//ָ���������
					 strSQL = mySql6.getString();
				 }		 
				 

  //��ѯSQL,���ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var tOperator="";
  var tMakeDate = "";
  var tModifyDate="";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			tcont = turnPage.arrDataCacheSet[0][0];
			treply = turnPage.arrDataCacheSet[0][1];
			ttype = turnPage.arrDataCacheSet[0][2];
			tOperatePos = turnPage.arrDataCacheSet[0][3];
			tOperator= turnPage.arrDataCacheSet[0][4];
			tMakeDate = turnPage.arrDataCacheSet[0][5];
			tModifyDate= turnPage.arrDataCacheSet[0][6];
  		}
  		else
  		{
  			alert("û��¼����������");
  			return "";
  		}
  	
  }
  else
  {
  	alert("û��¼����������");
	return "";
  }
 
  if (tcont == "")
  {
  	alert("û��¼����������");
  	return "";
  }
  
  document.all('Content').value = tcont;
  
  if (treply == "") {
    document.all('ReplyResult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    document.all('ReplyResult').readOnly = true;
    canReplyFlag = false;
  }
  document.all('ReplyResult').value = treply;
  
  document.all('Type').value = ttype;
  document.all('OperatePos').value = tOperatePos;
   document.all('Operator').value = tOperator;
  
  document.all('MakeDate').value = tMakeDate;
  //document.all('ReplyDate').value = tModifyDate;
  document.all('Type').value = ttype;
  
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1");
	
}

function quickReply() {
  if (QuestGrid.getSelNo() == "0") {
    alert("����ѡ��һ���������");
    return;
  }
  
  fm.ReplyResult.value = "�����޸���ɣ�";
  reply();
}
function replySave()
{
		fm.QuesFlag.value = "allover";   //��"�ظ����"��־
		fm.action = "./QuestReplyChk.jsp";
		fm.submit();	
}
function reply()
{ 
	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("û�лظ�����,���ܻظ���");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("��������ѻظ�,�����ٴλظ���");  
    return;
  }
  
  	cEdorNo = fm.EdorNo.value;
	cContNo = fm.ContNo.value;  //��������
	cQuest = fm.HideQuest.value;            //���������
	cflag = fm.HideOperatePos.value;        //���������λ�� 
	
	fm.ContNoHide.value = fm.ContNo.value;  //��������
	fm.Quest.value = fm.HideQuest.value;            //���������
	fm.Flag.value = fm.HideOperatePos.value;        //���������λ�� 
	fm.SerialNo.value = fm.HideSerialNo.value;
	if(cQuest == "")
	{
		alert("��ѡ��Ҫ�ظ������!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./BQQuestReplyChk.jsp";
		fm.submit();
	}
	
}


function QuestQuery()
{
	// ��ʼ�����
	var i,j,m,n;
	//initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	//}
	
	//alert(strSQL);
	
		 var sqlid7="BQQuestQuerySql7";
		 var mySql7=new SqlClass();
		 mySql7.setResourceName("uw.BQQuestQuerySql");
		 mySql7.setSqlId(sqlid7);//ָ��ʹ��SQL��id
		 mySql7.addSubPara(k);//ָ���������
		 mySql7.addSubPara(k);//ָ���������
		 strSQL = mySql7.getString();
		
  //��ѯSQL,���ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����,HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  //turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("��ѯʧ��!!");
  			return "";
  		}
  	}
}
else
{
	alert("��ѯʧ��!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QuestPicQuery(){
	//alert("�����С���");
	 var tsel=QuestGrid.getSelNo()-1; 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
 	
}

//������ظ���Ϻ���
function finishMission()
{
	//�ж��Ƿ�������Ҫ�ظ�������������˻ظ�
//	var	strSQL = "select 1 from lpissuepol where 1=1 "				 	
//				 //+ " and OperatePos in ('0','1','5','6')"				 
//				 //+ " and IsueManageCom like '" + manageCom + "%%'"				
//				 + getWherePart( 'EdorNo','EdorNo' )
//				 + getWherePart( 'ContNo','ContNo' )
//				 //+ getWherePart( 'BackObjType','BackObj' )
//				 //+ getWherePart( 'ManageCom','ManageCom' )
//				 //+ getWherePart( 'IsueManageCom','OManageCom')
//				// + getWherePart( 'OperatePos','OperatePos')
//				 //+ getWherePart( 'IssueType','Quest')
//				 //+ " and replyresult is not null "
//				 + " and state is not null and state='0' "
//				 + " and backobjtype = '4' and replyman is null "
//				 + " and needprint='Y'"
//				 ;	  
	
	 var  EdorNo8 = window.document.getElementsByName(trim("EdorNo"))[0].value;
     var  ContNo8 = window.document.getElementsByName(trim("ContNo"))[0].value;
	 var sqlid8="BQQuestQuerySql8";
	 var mySql8=new SqlClass();
	 mySql8.setResourceName("uw.BQQuestQuerySql");
	 mySql8.setSqlId(sqlid8);//ָ��ʹ��SQL��id
	 mySql8.addSubPara(EdorNo8);//ָ���������
	 mySql8.addSubPara(ContNo8);//ָ���������
	 var strSQL = mySql8.getString();
	
	var brr = easyExecSql(strSQL);
	//alert(strSQL);
	//alert(brr);
	if ( brr )  //����û�б���������.
	{
		alert("��δ�ظ��Ļ��������,���Ȼظ�");
		return false;
	}
	else
 	{
 		//alert(2);return false;
 		var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		fm.QuesFlag.value = "allover";
 		fm.action = "./BQQuestReplyChk.jsp";
 		document.all('hiddenOperate').value="finishMission";
 		fm.submit();
	}
	
}
