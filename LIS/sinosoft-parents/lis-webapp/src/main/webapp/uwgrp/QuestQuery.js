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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.QuestQuerySql";
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
��cContNo = fm.ContNo.value;  //��������	
��//var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
  
  	var sqlid1="QuestQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(cContNo);
		
  
    var arrResult = easyExecSql(mySql1.getString());
       //alert(arrResult);
    if (arrResult != null) {     
   ����alert("�ѷ��˱�֪ͨ�飬��δ��ӡ,�������ڴ�ʱ�޸�������Ĵ�ӡ��־��");
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
    //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  }
  else
  { 
	var showStr="��ӡ��Ǹ��³ɹ�";
  	alert(showStr);
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
	
	
	if (ifreply == "Y")
	{
	
	  if (tOperatePos == "1")
	  {
	  /*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid2="QuestQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
		mySql2.addSubPara(manageCom);
		mySql2.addSubPara(fm.ContNo.value);
		mySql2.addSubPara(fm.BackObj.value);
		mySql2.addSubPara(fm.ManageCom.value);
		mySql2.addSubPara(fm.OManageCom.value);
		mySql2.addSubPara(fm.OperatePos.value);
		mySql2.addSubPara(fm.Quest.value);
		
		strSQL = mySql2.getString();
	  }		
	  else if(tOperatePos == "2")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('1','5')"				
				 + " and IsueManageCom like '" + manageCom + "%%'"
				  + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";
		*/		 
				 
	   var sqlid3="QuestQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(k);
		mySql3.addSubPara(k);
		mySql3.addSubPara(manageCom);
		mySql3.addSubPara(fm.ContNo.value);
		mySql3.addSubPara(fm.BackObj.value);
		mySql3.addSubPara(fm.ManageCom.value);
		mySql3.addSubPara(fm.OManageCom.value);
		mySql3.addSubPara(fm.OperatePos.value);
		mySql3.addSubPara(fm.Quest.value);
		
		strSQL = mySql3.getString();
	  }
	  else if(tOperatePos == "3")
	  {
	  /*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 					 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + " and ReplyResult is not null"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
				 
		*/		 
		 var sqlid4="QuestQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(k);
		mySql4.addSubPara(k);
		mySql4.addSubPara(manageCom);
		mySql4.addSubPara(fm.ContNo.value);
		mySql4.addSubPara(fm.BackObj.value);
		mySql4.addSubPara(fm.ManageCom.value);
		mySql4.addSubPara(fm.OManageCom.value);
		mySql4.addSubPara(fm.OperatePos.value);
		mySql4.addSubPara(fm.Quest.value);
		
		strSQL = mySql4.getString();
				
	  }
	  else if(tOperatePos == "4")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"				 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";	
		*/		 
	   var sqlid5="QuestQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(k);
		mySql5.addSubPara(k);
		mySql5.addSubPara(manageCom);
		mySql5.addSubPara(fm.ContNo.value);
		mySql5.addSubPara(fm.BackObj.value);
		mySql5.addSubPara(fm.ManageCom.value);
		mySql5.addSubPara(fm.OManageCom.value);
		mySql5.addSubPara(fm.OperatePos.value);
		mySql5.addSubPara(fm.Quest.value);
		
		strSQL = mySql5.getString();  
	  }
	  else if(tOperatePos == "5")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	 
		*/		 
				 
		 var sqlid6="QuestQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(k);
		mySql6.addSubPara(k);
		mySql6.addSubPara(manageCom);
		mySql6.addSubPara(fm.ContNo.value);
		mySql6.addSubPara(fm.BackObj.value);
		mySql6.addSubPara(fm.ManageCom.value);
		mySql6.addSubPara(fm.OManageCom.value);
		mySql6.addSubPara(fm.OperatePos.value);
		mySql6.addSubPara(fm.Quest.value);
		
		strSQL = mySql6.getString();  	
	  }
	  else if(tOperatePos == "16")
	  {
	  /*
		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcgrpissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
				 + " and ReplyMan is not null "
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	 
		*/		 
				 
		var sqlid7="QuestQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(k);
		mySql7.addSubPara(k);
		mySql7.addSubPara(manageCom);
		mySql7.addSubPara(tContNo);
		mySql7.addSubPara(fm.BackObj.value);
		mySql7.addSubPara(fm.ManageCom.value);
		mySql7.addSubPara(fm.OManageCom.value);
		mySql7.addSubPara(fm.OperatePos.value);
		mySql7.addSubPara(fm.Quest.value);
		
		strSQL = mySql7.getString();  			 
				  	
		divButton.style.display = "none";
	  }
	  else
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is not null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid8="QuestQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(k);
		mySql8.addSubPara(k);
		mySql8.addSubPara(manageCom);
		mySql8.addSubPara(fm.ContNo.value);
		mySql8.addSubPara(fm.BackObj.value);
		mySql8.addSubPara(fm.ManageCom.value);
		mySql8.addSubPara(fm.OManageCom.value);
		mySql8.addSubPara(fm.OperatePos.value);
		mySql8.addSubPara(fm.Quest.value);
		
		strSQL = mySql8.getString(); 
	  }	  	  	
}
 else if (ifreply == "N")
	{
		
	  if (tOperatePos == "1")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid9="QuestQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(k);
		mySql9.addSubPara(k);
		mySql9.addSubPara(manageCom);
		mySql9.addSubPara(fm.ContNo.value);
		mySql9.addSubPara(fm.BackObj.value);
		mySql9.addSubPara(fm.ManageCom.value);
		mySql9.addSubPara(fm.OManageCom.value);
		mySql9.addSubPara(fm.OperatePos.value);
		mySql9.addSubPara(fm.Quest.value);
		
		strSQL = mySql9.getString(); 
	  }		
	  else if(tOperatePos == "2")
	  {
	  /*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('1','5')"				
				 + " and IsueManageCom like '" + manageCom + "%%'"
				  + " and ReplyMan is null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";
		*/		 
		var sqlid10="QuestQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(k);
		mySql10.addSubPara(k);
		mySql10.addSubPara(manageCom);
		mySql10.addSubPara(fm.ContNo.value);
		mySql10.addSubPara(fm.BackObj.value);
		mySql10.addSubPara(fm.ManageCom.value);
		mySql10.addSubPara(fm.OManageCom.value);
		mySql10.addSubPara(fm.OperatePos.value);
		mySql10.addSubPara(fm.Quest.value);
		
		strSQL = mySql10.getString(); 
	  }
	  else if(tOperatePos == "3")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 					 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
				 
				 
	  var sqlid11="QuestQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId(sqlid11);
		mySql11.addSubPara(k);
		mySql11.addSubPara(k);
		mySql11.addSubPara(manageCom);
		mySql11.addSubPara(fm.ContNo.value);
		mySql11.addSubPara(fm.BackObj.value);
		mySql11.addSubPara(fm.ManageCom.value);
		mySql11.addSubPara(fm.OManageCom.value);
		mySql11.addSubPara(fm.OperatePos.value);
		mySql11.addSubPara(fm.Quest.value);
		
		strSQL = mySql11.getString(); 
	  }
	  else if(tOperatePos == "4")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"				 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";	
		*/		 
	    var sqlid12="QuestQuerySql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId(sqlid12);
		mySql12.addSubPara(k);
		mySql12.addSubPara(k);
		mySql12.addSubPara(manageCom);
		mySql12.addSubPara(fm.ContNo.value);
		mySql12.addSubPara(fm.BackObj.value);
		mySql12.addSubPara(fm.ManageCom.value);
		mySql12.addSubPara(fm.OManageCom.value);
		mySql12.addSubPara(fm.OperatePos.value);
		mySql12.addSubPara(fm.Quest.value);
		
		strSQL = mySql12.getString();   
	  }
	  else if(tOperatePos == "5")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and ReplyMan is  null "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
				 */
		var sqlid13="QuestQuerySql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName(sqlresourcename);
		mySql13.setSqlId(sqlid13);
		mySql13.addSubPara(k);
		mySql13.addSubPara(k);
		mySql13.addSubPara(manageCom);
		mySql13.addSubPara(fm.ContNo.value);
		mySql13.addSubPara(fm.BackObj.value);
		mySql13.addSubPara(fm.ManageCom.value);
		mySql13.addSubPara(fm.OManageCom.value);
		mySql13.addSubPara(fm.OperatePos.value);
		mySql13.addSubPara(fm.Quest.value);
		
		strSQL = mySql13.getString();     	
	  }
	  else if(tOperatePos == "16")
	  {/*
		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcgrpissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
				  + " and ReplyMan is  null "
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
				 
		*/		 
		var sqlid14="QuestQuerySql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName(sqlresourcename);
		mySql14.setSqlId(sqlid14);
		mySql14.addSubPara(k);
		mySql14.addSubPara(k);
		mySql14.addSubPara(manageCom);
		mySql14.addSubPara(tContNo);
		mySql14.addSubPara(fm.BackObj.value);
		mySql14.addSubPara(fm.ManageCom.value);
		mySql14.addSubPara(fm.OManageCom.value);
		mySql14.addSubPara(fm.OperatePos.value);
		mySql14.addSubPara(fm.Quest.value);  
		
		strSQL = mySql14.getString(); 
			
		divButton.style.display = "none";
	  }
	  else
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
				 
				 
	    var sqlid15="QuestQuerySql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName(sqlresourcename);
		mySql15.setSqlId(sqlid15);
		mySql15.addSubPara(k);
		mySql15.addSubPara(k);
		mySql15.addSubPara(manageCom);
		mySql15.addSubPara(fm.ContNo.value);
		mySql15.addSubPara(fm.BackObj.value);
		mySql15.addSubPara(fm.ManageCom.value);
		mySql15.addSubPara(fm.OManageCom.value);
		mySql15.addSubPara(fm.OperatePos.value);
		mySql15.addSubPara(fm.Quest.value);  
		
		strSQL = mySql15.getString(); 
	  }	  	  	
}

else
	{
		
	  if (tOperatePos == "1")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
		var sqlid16="QuestQuerySql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename);
		mySql16.setSqlId(sqlid16);
		mySql16.addSubPara(k);
		mySql16.addSubPara(k);
		mySql16.addSubPara(manageCom);
		mySql16.addSubPara(fm.ContNo.value);
		mySql16.addSubPara(fm.BackObj.value);
		mySql16.addSubPara(fm.ManageCom.value);
		mySql16.addSubPara(fm.OManageCom.value);
		mySql16.addSubPara(fm.OperatePos.value);
		mySql16.addSubPara(fm.Quest.value);  
		
		strSQL = mySql16.getString(); 
	  }		
	  else if(tOperatePos == "2")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('1','5')"
				 + " and backobjtype = '1'"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";
		*/		 
		var sqlid17="QuestQuerySql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName(sqlresourcename);
		mySql17.setSqlId(sqlid17);
		mySql17.addSubPara(k);
		mySql17.addSubPara(k);
		mySql17.addSubPara(manageCom);
		mySql17.addSubPara(fm.ContNo.value);
		mySql17.addSubPara(fm.BackObj.value);
		mySql17.addSubPara(fm.ManageCom.value);
		mySql17.addSubPara(fm.OManageCom.value);
		mySql17.addSubPara(fm.OperatePos.value);
		mySql17.addSubPara(fm.Quest.value);  
		
		strSQL = mySql17.getString(); 
	  }
	  else if(tOperatePos == "3")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 					 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
		*/		 
	    var sqlid18="QuestQuerySql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName(sqlresourcename);
		mySql18.setSqlId(sqlid18);
		mySql18.addSubPara(k);
		mySql18.addSubPara(k);
		mySql18.addSubPara(manageCom);
		mySql18.addSubPara(fm.ContNo.value);
		mySql18.addSubPara(fm.BackObj.value);
		mySql18.addSubPara(fm.ManageCom.value);
		mySql18.addSubPara(fm.OManageCom.value);
		mySql18.addSubPara(fm.OperatePos.value);
		mySql18.addSubPara(fm.Quest.value);  
		
		strSQL = mySql18.getString(); 
	  }
	  else if(tOperatePos == "4")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"				 
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest')
				 + " and replyresult is null";	
	    */
	    var sqlid19="QuestQuerySql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName(sqlresourcename);
		mySql19.setSqlId(sqlid19);
		mySql19.addSubPara(k);
		mySql19.addSubPara(k);
		mySql19.addSubPara(manageCom);
		mySql19.addSubPara(fm.ContNo.value);
		mySql19.addSubPara(fm.BackObj.value);
		mySql19.addSubPara(fm.ManageCom.value);
		mySql19.addSubPara(fm.OManageCom.value);
		mySql19.addSubPara(fm.OperatePos.value);
		mySql19.addSubPara(fm.Quest.value);  
		
		strSQL = mySql19.getString();   
	  }
	  else if(tOperatePos == "5")
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcissuepol where "+k+"="+k+" "				 	
				 + " and OperatePos in ('0','1','5')"
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
				 */
		var sqlid20="QuestQuerySql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName(sqlresourcename);
		mySql20.setSqlId(sqlid20);
		mySql20.addSubPara(k);
		mySql20.addSubPara(k);
		mySql20.addSubPara(manageCom);
		mySql20.addSubPara(fm.ContNo.value);
		mySql20.addSubPara(fm.BackObj.value);
		mySql20.addSubPara(fm.ManageCom.value);
		mySql20.addSubPara(fm.OManageCom.value);
		mySql20.addSubPara(fm.OperatePos.value);
		mySql20.addSubPara(fm.Quest.value);  
		
		strSQL = mySql20.getString();   	
	  }
	  else if(tOperatePos == "16")
	  {/*
		strSQL = "select GrpContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint ,serialno from lcgrpissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');	
		*/		 
		var sqlid21="QuestQuerySql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName(sqlresourcename);
		mySql21.setSqlId(sqlid21);
		mySql21.addSubPara(k);
		mySql21.addSubPara(k);
		mySql21.addSubPara(manageCom);
		mySql21.addSubPara(tContNo);
		mySql21.addSubPara(fm.BackObj.value);
		mySql21.addSubPara(fm.ManageCom.value);
		mySql21.addSubPara(fm.OManageCom.value);
		mySql21.addSubPara(fm.OperatePos.value);
		mySql21.addSubPara(fm.Quest.value);  
		
		strSQL = mySql21.getString();   	
		divButton.style.display = "none";
	  }
	  else
	  {/*
		strSQL = "select ContNo,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,needprint,serialno from lcissuepol where "+k+"="+k+" "
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + getWherePart( 'OperatePos','OperatePos')
				 + getWherePart( 'IssueType','Quest');
				 */
		var sqlid22="QuestQuerySql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName(sqlresourcename);
		mySql22.setSqlId(sqlid22);
		mySql22.addSubPara(k);
		mySql22.addSubPara(k);
		mySql22.addSubPara(manageCom);
		mySql22.addSubPara(tContNo);
		mySql22.addSubPara(fm.BackObj.value);
		mySql22.addSubPara(fm.ManageCom.value);
		mySql22.addSubPara(fm.OManageCom.value);
		mySql22.addSubPara(fm.OperatePos.value);
		mySql22.addSubPara(fm.Quest.value);  
		
		strSQL = mySql22.getString();   
	  }	  	  	
}
	
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
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' )
	{
	//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
   		tPos = fm.all(parm1).all('QuestGrid7').value;
   		tQuest = fm.all(parm1).all('QuestGrid2').value;
   		tSerialNo = fm.all(parm1).all('QuestGrid10').value;
  	}
  	
	fm.all('HideOperatePos').value=tPos;
	fm.all('HideQuest').value=tQuest;
    fm.all('HideSerialNo').value=tSerialNo;
    fm.all('SerialNo').value=tSerialNo;
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
	
		//strSQL = "select issuecont,replyresult,issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
		//				+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')"
		
		var sqlid23="QuestQuerySql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId(sqlid23);
		mySql23.addSubPara(k);
		mySql23.addSubPara(k);
		mySql23.addSubPara(tContNo);

		strSQL = mySql23.getString();   
		//alert(strSQL);
	}
	else
	{/*
		strSQL = "select issuecont,replyresult,issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'issuetype','HideQuest' )
				 + getWherePart( 'OperatePos','HideOperatePos')
				 + getWherePart( 'SerialNo','HideSerialNo');
		*/		 
		var sqlid24="QuestQuerySql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName(sqlresourcename);
		mySql24.setSqlId(sqlid24);
		mySql24.addSubPara(k);
		mySql24.addSubPara(k);
		mySql24.addSubPara(fm.ContNo.value);
		mySql24.addSubPara(fm.HideQuest.value);
		mySql24.addSubPara(fm.HideOperatePos.value);
		mySql24.addSubPara(fm.HideSerialNo.value);

		strSQL = mySql24.getString(); 
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
  
  fm.all('Content').value = tcont;
  
  if (treply == "") {
    fm.all('replyresult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    fm.all('replyresult').readOnly = true;
    canReplyFlag = false;
  }
  fm.all('replyresult').value = treply;
  
  fm.all('Type').value = ttype;
  fm.all('OperatePos').value = tOperatePos;
   fm.all('Operator').value = tOperator;
  
  fm.all('MakeDate').value = tMakeDate;
  fm.all('ReplyDate').value = tModifyDate;
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1",sFeatures);
	
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
		//strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
			//	 + " and codetype = 'Question'";
	//}
	  var sqlid25="QuestQuerySql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId(sqlid25);
		mySql25.addSubPara(k);
		mySql25.addSubPara(k);
		
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql25.getString(), 1, 0, 1);  
  
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


function QuestPicQuery(){
	alert("�����С���");
	 var tsel=QuestGrid.getSelNo()-1; 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
	
 	
}
