<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//WorkFlowNotePadSave.jsp
//程序功能：
//创建日期：2005-04-22 14:49:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
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
    String busiName="tbWorkFlowNotePadUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //  WorkFlowNotePadUI tWorkFlowNotePadUI = new WorkFlowNotePadUI();
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tLWNotePadSchema);   
		tBusinessDelegate.submitData(tVData, "NOTEPAD|INSERT",busiName);
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      loggerDebug("WorkFlowNotePadSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		      Content ="保存成功！";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "保存失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
