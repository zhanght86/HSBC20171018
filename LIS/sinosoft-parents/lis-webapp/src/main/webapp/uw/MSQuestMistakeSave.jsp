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
  <%@page import="com.sinosoft.workflow.tb.*"%>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strNotePadCont = request.getParameter("Content");
    String strPrtNo = request.getParameter("PrtNo");
    String tSerialNo = request.getParameter("SerialNo");
    String tErrManageCom = request.getParameter("ErrManageCom");
    String tErrorType = request.getParameter("ErrorType");
    String tErrContent = request.getParameter("Content");
    String tAction = request.getParameter("maction");
    loggerDebug("MSQuestMistakeSave","���β���Ϊ��"+tAction);
    
    LCIssueMistakeSchema tLCIssueMistakeSchema = new LCIssueMistakeSchema();
    tLCIssueMistakeSchema.setProposalContNo(strPrtNo);
    tLCIssueMistakeSchema.setContNo(strPrtNo);
    tLCIssueMistakeSchema.setSerialNo(tSerialNo);
    tLCIssueMistakeSchema.setErrManageCom(tErrManageCom);
    tLCIssueMistakeSchema.setErrorType(tErrorType);
    tLCIssueMistakeSchema.setErrContent(tErrContent);
    
    //tLCIssueMistakeSchema.setOtherNoType(strNoType);     
     
    MSQuestMistakeUI tMSQuestMistakeUI = new MSQuestMistakeUI();
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tLCIssueMistakeSchema);   
		tMSQuestMistakeUI.submitData(tVData, tAction);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("MSQuestMistakeSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tMSQuestMistakeUI.mErrors;
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
   
   
   
 
