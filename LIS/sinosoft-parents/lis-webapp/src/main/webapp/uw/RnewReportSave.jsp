<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//WorkFlowNotePadSave.jsp
//�����ܣ�
//�������ڣ�2005-04-22 14:49:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.xb.*"%>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strReportCont = request.getParameter("Content");
    String strProposalNo = request.getParameter("ProposalNo");
    String strNoType = request.getParameter("NoType");
  
    RnewUWReportSchema tRnewUWReportSchema = new RnewUWReportSchema();
    tRnewUWReportSchema.setOtherNo(strProposalNo);
    tRnewUWReportSchema.setOtherNoType(strNoType); 
    tRnewUWReportSchema.setReportCont(strReportCont);
     
    RnewReportInputUI tRnewReportInputUI = new RnewReportInputUI();
    
	try
	{	
		tVData.add(tG);		
		tVData.add(tRnewUWReportSchema);   
		tRnewReportInputUI.submitData(tVData, "REPORT|INSERT");
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("RnewReportSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tRnewReportInputUI.mErrors;
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
   
   
   
 
