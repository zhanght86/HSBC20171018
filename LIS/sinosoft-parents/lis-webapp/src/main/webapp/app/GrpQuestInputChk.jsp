<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpQuestInputChk.jsp
//�����ܣ����������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
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
	
	//String tcont[] = request.getParameterValues("QuestGrid3");
	//String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("GrpQuestInputChk","grpcontno:"+tGrpContNo);
	loggerDebug("GrpQuestInputChk","operatepos:"+tFlag);
	loggerDebug("GrpQuestInputChk","backobj:"+tBackObj);
	loggerDebug("GrpQuestInputChk","issuetype:"+tQuest);
	loggerDebug("GrpQuestInputChk","content:"+tContent);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (tGrpContNo.equals("") || tFlag.equals("") || tBackObj.equals("") || tContent.equals("") || tQuest.equals(""))
	{
		Content = "����¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpQuestInputChk","ʧ��");
	}
	else
	{
		loggerDebug("GrpQuestInputChk","begin");

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 			
		tLCGrpContSchema.setGrpContNo(tGrpContNo);
	    		
    	tLCGrpContSet.add( tLCGrpContSchema );
	    				
		LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
		
		loggerDebug("GrpQuestInputChk","operatepos:"+tFlag);
		tLCGrpIssuePolSchema.setIssueCont(tContent);
		tLCGrpIssuePolSchema.setOperatePos(tFlag);
		tLCGrpIssuePolSchema.setBackObjType(tBackObj);
		tLCGrpIssuePolSchema.setIssueType(tQuest);
			
		tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);			    
	}
	
	loggerDebug("GrpQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSet );
		tVData.add( tLCGrpIssuePolSet);
		tVData.add( tG );
		
		// ���ݴ���
		//GrpQuestInputChkUI tGrpQuestInputChkUI   = new GrpQuestInputChkUI();
		
		String busiName="cbcheckGrpQuestInputChkUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
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
