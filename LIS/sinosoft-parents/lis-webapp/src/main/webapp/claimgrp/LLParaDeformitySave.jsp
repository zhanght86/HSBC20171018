<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLParaDeformitySave.jsp
//�����ܣ��˲еȼ���������Ϣ����
//�������ڣ�2005-6-24 13:38
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
        loggerDebug("LLParaDeformitySave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLParaDeformitySave","û�л�ȡ������!!!");
        return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData();    
    
    LLParaDeformitySchema tLLParaDeformitySchema = new LLParaDeformitySchema();        
    
    
    //����Ȩ����Ϣ
    if (tOperate.equals("Deformity||INSERT")||tOperate.equals("Deformity||DELETE")||tOperate.equals("Deformity||UPDATE"))
    {
				//׼����̨����
	    	tLLParaDeformitySchema.setDefoType(request.getParameter("DefoType"));
	    	tLLParaDeformitySchema.setDefoGrade(request.getParameter("DefoGrade"));
	    	tLLParaDeformitySchema.setDefoGradeName(request.getParameter("DefoGradeName"));
	    	tLLParaDeformitySchema.setDefoCode(request.getParameter("DefoCode"));
	    	tLLParaDeformitySchema.setDefoName(request.getParameter("DefoName"));
	    	tLLParaDeformitySchema.setDefoRate(request.getParameter("DefoRate"));
    }
    //loggerDebug("LLParaDeformitySave","LLParaDeformitySchemaSave.jsp����--"+tOperate);
  
    
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLParaDeformitySchema );
				
        LLParaDeformityUI tLLParaDeformityUI = new LLParaDeformityUI();
       if (tLLParaDeformityUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "�˲еȼ�������Ϣ����ʧ�ܣ�ԭ����: " + tLLParaDeformityUI.mErrors.getError(0).errorMessage;
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
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterDeformitySubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
