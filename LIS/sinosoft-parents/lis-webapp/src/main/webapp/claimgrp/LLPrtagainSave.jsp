<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�ļ����ƣ�LLPrtagainSave.jsp
//�ļ����ܣ���֤����ԭ��¼��ҳ��
//�����ˣ�yuejw
//��������: 2005-08-21
//ҳ������: �����ڲ���֤ʱ¼�벹��ԭ��
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
    //�������
	CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
	GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");
  	if(tG == null) 
  	{
		loggerDebug("LLPrtagainSave","session has expired");
		return;
	}
    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
    tLOPRTManagerSchema.setPrtSeq(request.getParameter("PrtSeq"));
    tLOPRTManagerSchema.setRemark(request.getParameter("Remark"));
    
    // ׼���������� VData
    VData tVData = new VData();	
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
    // ���ݴ���
//    LLPrtagainReasonUI tLLPrtagainReasonUI   = new LLPrtagainReasonUI();
//	if (!tLLPrtagainReasonUI.submitData(tVData,""))
//	{
//     	Content = "����ԭ�򱣴�ʧ�ܣ�ԭ����: " + tLLPrtagainReasonUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//	} 
String busiName="grpLLPrtagainReasonUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "����ԭ�򱣴�ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "����ԭ�򱣴�ʧ��";
		FlagStr = "Fail";				
	}
}

    else
	{ 
        Content = "�����ύ�ɹ�";
        FlagStr = "Succ";            
	}
%>                      
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
