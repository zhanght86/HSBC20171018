<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLGrpClaimPopedomSaveSave.jsp
//�����ܣ�����Ȩ����Ϣ����
//�������ڣ�2005-6-17 11:18
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>

<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
   
    if(tG == null) 
    {
        loggerDebug("LLGrpClaimPopedomSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLGrpClaimPopedomSave","û�л�ȡ������!!!");
        return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData();    
    
    LLGrpClaimPopedomSchema tLLGrpClaimPopedomSchema = new LLGrpClaimPopedomSchema();   
    tTransferData.setNameAndValue("OriJobCategory",request.getParameter("OriJobCategory"));
    tTransferData.setNameAndValue("OriCaseCategory",request.getParameter("OriCaseCategory"));
    
    
    //����Ȩ����Ϣ
    if (tOperate.equals("Popedom||INSERT")||tOperate.equals("Popedom||DELETE")||tOperate.equals("Popedom||UPDATE"))
    {
				//׼����̨����
	    	tLLGrpClaimPopedomSchema.setJobCategory(request.getParameter("JobCategory"));
	    	tLLGrpClaimPopedomSchema.setPopedomName(request.getParameter("JobCategoryName"));
	    	tLLGrpClaimPopedomSchema.setPopedomName(request.getParameter("JobCategoryName"));
	    	tLLGrpClaimPopedomSchema.setCaseCategory(request.getParameter("CaseCategory"));
	    	tLLGrpClaimPopedomSchema.setBaseMin(request.getParameter("BaseMin"));
	    	tLLGrpClaimPopedomSchema.setBaseMax(request.getParameter("BaseMax"));
	    	tLLGrpClaimPopedomSchema.setMngCom(request.getParameter("MngCom"));
	    	tLLGrpClaimPopedomSchema.setStartDate(request.getParameter("StartDate"));
	    	tLLGrpClaimPopedomSchema.setEndDate(request.getParameter("EndDate"));
                                      
    }
    //loggerDebug("LLGrpClaimPopedomSave","LLGrpClaimPopedomSaveSave.jsp����--"+tOperate);
  
    
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLGrpClaimPopedomSchema );
				
        LLGrpClaimPopedomUI tLLGrpClaimPopedomUI = new LLGrpClaimPopedomUI();
       if (tLLGrpClaimPopedomUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "����Ȩ����Ϣ����ʧ�ܣ�ԭ����: " + tLLGrpClaimPopedomUI.mErrors.getError(0).errorMessage;
          FlagStr = "FAIL";
        } else {
            Content = " ����ɹ�! ";
            FlagStr = "SUCC";
        }
    

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
    
//    loggerDebug("LLGrpClaimPopedomSave","LLGrpClaimPopedomSaveSave����--"+FlagStr);
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterPopedomSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
