<%
//�������ƣ�LLParaClaimSimpleSave.jsp
//�����ܣ�ҽԺ��Ϣά��
//�������ڣ�2005-9-19
//������  ��wuhao
//���¼�¼��  ������ wuhao    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

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
        loggerDebug("LLParaClaimSimpleSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLParaClaimSimpleSave","û�л�ȡ������!!!");
        return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData();    
    
    LLParaClaimSimpleSchema tLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();    
    //�򵥰�����׼��Ϣ
    if (tOperate.equals("Simple||INSERT")||tOperate.equals("Simple||DELETE")||tOperate.equals("Simple||UPDATE"))
    {
				//׼����̨����
	    	tLLParaClaimSimpleSchema.setComCode(request.getParameter("ComCode"));
	    	tLLParaClaimSimpleSchema.setComCodeName(request.getParameter("ComCodeName"));
	    	tLLParaClaimSimpleSchema.setUpComCode(request.getParameter("UpComCode"));
	    	tLLParaClaimSimpleSchema.setBaseMin(request.getParameter("BaseMin"));
	    	tLLParaClaimSimpleSchema.setBaseMax(request.getParameter("BaseMax"));
	    	tLLParaClaimSimpleSchema.setStartDate(request.getParameter("StartDate"));
	    	tLLParaClaimSimpleSchema.setEndDate(request.getParameter("EndDate"));
    }
    
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLParaClaimSimpleSchema );
				
        LLParaClaimSimpleUI tLLParaClaimSimpleUI = new LLParaClaimSimpleUI();
       if (tLLParaClaimSimpleUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "������׼����ʧ�ܣ�ԭ����: " + tLLParaClaimSimpleUI.mErrors.getError(0).errorMessage;
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
    
//    loggerDebug("LLParaClaimSimpleSave","LLParaClaimSimpleSave����--"+FlagStr);
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterSimpleSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
