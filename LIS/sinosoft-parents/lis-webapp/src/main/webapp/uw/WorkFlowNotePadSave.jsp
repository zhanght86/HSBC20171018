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
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strMissionID = request.getParameter("MissionID");
	String strSubMissionID = request.getParameter("SubMissionID");
	String strActivityID = request.getParameter("ActivityID");
	String strNotePadCont = request.getParameter("Content");
    String strPrtNo = request.getParameter("PrtNo");
    String strNoType = request.getParameter("NoType");
  
    LWNotePadSchema tLWNotePadSchema = new LWNotePadSchema();
    tLWNotePadSchema.setMissionID(strMissionID);
    tLWNotePadSchema.setSubMissionID(strSubMissionID);
    tLWNotePadSchema.setActivityID(strActivityID);
    tLWNotePadSchema.setNotePadCont(strNotePadCont);
    tLWNotePadSchema.setOtherNo(strPrtNo);
    tLWNotePadSchema.setOtherNoType(strNoType);     
     
   // WorkFlowNotePadUI tWorkFlowNotePadUI = new WorkFlowNotePadUI();
     String busiName="tbWorkFlowNotePadUI";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     
	try
	{		   
		tVData.add(tG);		
		tVData.add(tLWNotePadSchema);   
		tBusinessDelegate.submitData(tVData, "NOTEPAD|INSERT",busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("WorkFlowNotePadSave","aaaa"+ex.toString());
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
   
   
   
 
