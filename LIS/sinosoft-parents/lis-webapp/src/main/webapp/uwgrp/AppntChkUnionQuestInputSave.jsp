<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AppntChkUnionQuestInputSave.jsp
//�����ܣ�
//�������ڣ�2005-04-20 16:49:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	LCContSet tLCContSet = new LCContSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tContNo = request.getParameter("ProposalNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = "2";
	String tQuest = "99";
	String tContent = request.getParameter("CUIContent");
	String tQuestionObj = request.getParameter("CustomerNo_OLD");
	String tQuestionObjName = request.getParameter("CustomerName");
	String tStandbyFlag1 = request.getParameter("CustomerNo_NEW");
	String tQuestionObjValue = "0";
	String mFlag = "1";
	
		LCContSchema tLCContSchema = new LCContSchema(); 			
		tLCContSchema.setContNo( tContNo);	
		tLCContSchema.setState(mFlag);		    		
    	tLCContSet.add( tLCContSchema );
	    				
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();		
		tLCIssuePolSchema.setIssueCont(tContent);
		loggerDebug("AppntChkUnionQuestInputSave",tContent);
		tLCIssuePolSchema.setOperatePos(tFlag);
		tLCIssuePolSchema.setBackObjType(tBackObj);
		tLCIssuePolSchema.setIssueType(tQuest);
		tLCIssuePolSchema.setQuestionObjType(tQuestionObjValue);
		tLCIssuePolSchema.setQuestionObj(tQuestionObj);
		tLCIssuePolSchema.setQuestionObjName(tQuestionObjName);
		tLCIssuePolSchema.setStandbyFlag1(tStandbyFlag1);		
		tLCIssuePolSet.add(tLCIssuePolSchema);	
		
	//QuestInputChkUI tQuestInputChkUI   = new QuestInputChkUI();	    
	String busiName="cbcheckgrpQuestInputChkUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	try
	{		   
		tVData.add(tG);		
		tVData.add( tLCContSet );
		tVData.add( tLCIssuePolSet);
		tBusinessDelegate.submitData(tVData, "INSERT",busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("AppntChkUnionQuestInputSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}

	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		        Content ="����ɹ���";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
