<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpQuestInputModify.jsp
//�����ܣ����������¼��
//�������ڣ�2005-03-24 11:10:36
//������  ��YAQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();

	String tGrpContNo = request.getParameter("GrpContNoHide");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("BackObj");
	String tQuest = request.getParameter("Quest");
	String tContent = request.getParameter("Content");
	String tSerialNo= request.getParameter("SerialNoHide");
	
	loggerDebug("GrpQuestInputModify","grpcontno:"+tGrpContNo);
	loggerDebug("GrpQuestInputModify","operatepos:"+tFlag);
	loggerDebug("GrpQuestInputModify","backobj:"+tBackObj);
	loggerDebug("GrpQuestInputModify","issuetype:"+tQuest);
	loggerDebug("GrpQuestInputModify","content:"+tContent);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (tGrpContNo.equals("") || tFlag.equals("") || tBackObj.equals("") || tContent.equals("") || tQuest.equals(""))
	{
		Content = "����¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpQuestInputModify","ʧ��");
	}
	else
	{
		loggerDebug("GrpQuestInputModify","begin");

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 			
		tLCGrpContSchema.setGrpContNo(tGrpContNo);	
		loggerDebug("GrpQuestInputModify","���ڵĺ�ͬ������"+tSerialNo);    		
    	        tLCGrpContSet.add( tLCGrpContSchema );	    				
		LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();		
		loggerDebug("GrpQuestInputModify","operatepos:"+tFlag);
		
		tLCGrpIssuePolSchema.setProposalGrpContNo(tGrpContNo);
		tLCGrpIssuePolSchema.setGrpContNo(tGrpContNo);
		tLCGrpIssuePolSchema.setIssueCont(tContent);
		tLCGrpIssuePolSchema.setOperatePos(tFlag);
		tLCGrpIssuePolSchema.setBackObjType(tBackObj);
		tLCGrpIssuePolSchema.setIssueType(tQuest);
		tLCGrpIssuePolSchema.setSerialNo(tSerialNo);	
		tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);			    
	}
	
	loggerDebug("GrpQuestInputModify","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSet );
		tVData.add( tLCGrpIssuePolSet);
		tVData.add( tG );
		
		// ���ݴ���
		   String busiName="cbcheckgrpOrderManageUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//	GrpQuestInputChkUI tGrpQuestInputChkUI   = new GrpQuestInputChkUI();
		if (tBusinessDelegate.submitData(tVData,"UPDATE",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	} 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
