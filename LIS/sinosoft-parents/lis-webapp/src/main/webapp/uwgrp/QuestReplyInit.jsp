<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestReplyInit.jsp
//�����ܣ�������ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tContNo = "";
  String tFlag = "";
  String tQuest = "";

  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");
  tQuest = request.getParameter("Quest");

  loggerDebug("QuestReplyInit","ContNo:"+tContNo);
  loggerDebug("QuestReplyInit","Flag:"+tFlag);
  loggerDebug("QuestReplyInit","issuetype:"+tQuest);

%>                            

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo)
{ 
try
  {                                   
    fm.all('ContNo').value = tContNo;
    fm.all('Content').value = '';
    fm.all('ReplyResult').value = '';
  }
  catch(ex)
  {
    alert("��UWManuDateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initForm(tContNo,tFlag,tQuest)
{
  try
  {
	//alert(3);
	initInpBox(tContNo);	
	//alert(2);
	initHide(tContNo,tFlag,tQuest);	
	//alert(tQuest);
	QueryCont(tContNo,tFlag,tQuest);

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initHide(tContNo,tFlag,tQuest)
{
	fm.all('ContNo').value=tContNo;
	fm.all('Flag').value=tFlag;
	fm.all('Quest').value=tQuest;
	//alert("pol:"+tContNo);
}

</script>


