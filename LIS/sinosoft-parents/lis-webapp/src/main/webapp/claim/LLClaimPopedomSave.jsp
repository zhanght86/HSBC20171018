<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLClaimPopedomSave.jsp
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
<%@page import="com.sinosoft.lis.claim.*"%>

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
        loggerDebug("LLClaimPopedomSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimPopedomSave","û�л�ȡ������!!!");
        return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData();    
    
    LLClaimPopedomSchema tLLClaimPopedomSchema = new LLClaimPopedomSchema();   
    tTransferData.setNameAndValue("OriJobCategory",request.getParameter("OriJobCategory"));
    tTransferData.setNameAndValue("OriCaseCategory",request.getParameter("OriCaseCategory"));
    
    
    //����Ȩ����Ϣ
    if (tOperate.equals("Popedom||INSERT")||tOperate.equals("Popedom||DELETE")||tOperate.equals("Popedom||UPDATE"))
    {
				//׼����̨����
	    	tLLClaimPopedomSchema.setJobCategory(request.getParameter("JobCategory"));
	    	tLLClaimPopedomSchema.setPopedomName(request.getParameter("JobCategoryName"));
	    	tLLClaimPopedomSchema.setPopedomName(request.getParameter("JobCategoryName"));
	    	tLLClaimPopedomSchema.setCaseCategory(request.getParameter("CaseCategory"));
	    	tLLClaimPopedomSchema.setBaseMin(request.getParameter("BaseMin"));
	    	tLLClaimPopedomSchema.setBaseMax(request.getParameter("BaseMax"));
	    	tLLClaimPopedomSchema.setMngCom(request.getParameter("MngCom"));
	    	tLLClaimPopedomSchema.setStartDate(request.getParameter("StartDate"));
	    	tLLClaimPopedomSchema.setEndDate(request.getParameter("EndDate"));
                                      
    }
    //loggerDebug("LLClaimPopedomSave","LLClaimPopedomSave.jsp����--"+tOperate);
  
    
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLClaimPopedomSchema );
				
        LLClaimPopedomUI tLLClaimPopedomUI = new LLClaimPopedomUI();
       if (tLLClaimPopedomUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "����Ȩ����Ϣ����ʧ�ܣ�ԭ����: " + tLLClaimPopedomUI.mErrors.getError(0).errorMessage;
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
    
//    loggerDebug("LLClaimPopedomSave","LLClaimPopedomSave����--"+FlagStr);
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterPopedomSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
