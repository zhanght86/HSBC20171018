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

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}

//�ύ�����水ť��Ӧ����
function IfPrint()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
	var tSel = QuestGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	alert( "����ѡ��һ����¼���ٵ����ϸ��ѯ��ť��" );
	else
	{
	fm.HideSerialNo.value = QuestGrid.getRowColData(tSel - 1,10);
	fm.ContNoHide.value = QuestGrid.getRowColData(tSel - 1,1);
	}
	if(fm.ReplyResult.value=='')
	{
		alert("����������!!");
		return;
	}
  fm.action = "./QuestEndChk.jsp";
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
  }
  else
  { 
	var showStr="��ӡ��Ǹ��³ɹ�";
  	alert(showStr);
  	//showInfo.close();
  	
    //ִ����һ������
  }
  QuestQuery(tContNo,tFlag);
  initCodeData(tContNo,tFlag);
	query();
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


function manuchkspecmain()
{
	fm.submit();
}

function query()
{
	// ��ʼ�����
	initQuestGrid();
	initContent();

	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	var tOperatePos = fm.Flag.value;
	var tContNo = fm.ContNo.value;

	if (tOperatePos == "")
	{
		alert("����λ�ô���ʧ��!");
		return "";
	}
	//if(tOPos == "")
	//{
	//	alert("����λ�ò���Ϊ�գ�");
	//	return "";
	//}
	
	if (tOperatePos == "1"||tOperatePos == "3"||tOperatePos == "5")
	{
		if(tContNo == "")
		{
			alert("�����Ų���Ϊ��!");
			return "";
		}
	}
	
	//alert(ifreply);
	//if (ifreply == "N")
	//{

//		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,state,serialno from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5')"
//				 + " and backobjtype = '3'"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		
		   var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
	       var  BackObj = window.document.getElementsByName(trim("BackObj"))[0].value;
	       var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
	       var  OManageCom = window.document.getElementsByName(trim("OManageCom"))[0].value;
	       var  OperatePos = window.document.getElementsByName(trim("OperatePos"))[0].value;
	       var  Quest = window.document.getElementsByName(trim("Quest"))[0].value;
	   	   var  sqlid1="QuestEndSql0";
	   	   var  mySql1=new SqlClass();
	   	   mySql1.setResourceName("uw.QuestEndSql"); //ָ��ʹ�õ�properties�ļ���
	   	   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	   	   mySql1.addSubPara(k);//ָ������Ĳ���
	   	   mySql1.addSubPara(k);//ָ������Ĳ���
	   	   mySql1.addSubPara(manageCom);//ָ������Ĳ���
	   	   mySql1.addSubPara(ContNo);//ָ������Ĳ���
	   	   mySql1.addSubPara(BackObj);//ָ������Ĳ���
	   	   mySql1.addSubPara(ManageCom);//ָ������Ĳ���
	   	   mySql1.addSubPara(OManageCom);//ָ������Ĳ���
	   	   mySql1.addSubPara(OperatePos);//ָ������Ĳ���
	 	   mySql1.addSubPara(Quest);//ָ������Ĳ���
	   	   strSQL=mySql1.getString();
	  	

	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult == false) {
    alert("û�������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
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
	
	// ��дSQL���
	//alert("begin");
	k++;

	var strSQL = "";
	var tcho;
	var tOperatePos = fm.Flag.value;
	if(document.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tPos = document.all(parm1).all('QuestGrid7').value;
   		tQuest = document.all(parm1).all('QuestGrid2').value;
   		tSerialNo = document.all(parm1).all('QuestGrid10').value;
  	}
  	
	document.all('HideOperatePos').value=tPos;
	document.all('HideQuest').value=tQuest;
    document.all('HideSerialNo').value=tSerialNo;
    document.all('SerialNo').value=tSerialNo;
	tContNo = fm.ContNo.value;
	//tQuest = fm.Quest.value;
	//tPos = fm.OperatePos.value;
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
	
	if (tOperatePos == "16")
	{
	
		strSQL = "select issuecont,replyresult,issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
						+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')"
		alert(strSQL);
	}
	else
	{
		strSQL = "select issuecont,replyresult,issuetype,OperatePos from lcissuepol where "+k+"="+k+" "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'issuetype','HideQuest' )
				 + getWherePart( 'OperatePos','HideOperatePos')
				 + getWherePart( 'SerialNo','HideSerialNo');
				 //+ getWherePart( 'BackObjType','BackObj')
				 //+ getWherePart( 'ManageCom','ManageCom')
				 //+ getWherePart( 'IssueManageCom','OManageCom')
	}
	
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
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
    document.all('replyresult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    document.all('replyresult').readOnly = true;
    canReplyFlag = false;
  }
  document.all('replyresult').value = treply;
  
  document.all('Type').value = ttype;
  document.all('OperatePos').value = tOperatePos;
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
    alert("û�лظ����ݣ����ܻظ���");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("��������ѻظ��������ٴλظ���");  
    return;
  }
  
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
		fm.action = "./QuestReplyChk.jsp";
		fm.submit();
	}
	
}

function QuestQuery(tContNo, tFlag)
{
	// ��ʼ�����
	var i,j,m,n;
	//initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
	//if (tFlag == "1")
	//{
		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'Question'";
	//}
	
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
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
