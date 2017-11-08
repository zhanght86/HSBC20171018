<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

    String FlagStr="";      //操作结果
    String Content = "";    //控制台信息
    BusinessDelegate tBusinessDelegate;
    VData tVData = new VData();
    TransferData tTransferData = new TransferData();
 
    tTransferData.setNameAndValue("Year",request.getParameter("Year"));
    tTransferData.setNameAndValue("AmBegin",request.getParameter("AmBegin"));
    tTransferData.setNameAndValue("AmEnd",request.getParameter("AmEnd"));
    tTransferData.setNameAndValue("PmBegin",request.getParameter("PmBegin"));
    tTransferData.setNameAndValue("PmEnd",request.getParameter("PmEnd"));
    
    tTransferData.setNameAndValue("CalType",request.getParameter("CalendarType"));
    tTransferData.setNameAndValue("OtherProp",request.getParameter("OtherProp"));

    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    tVData.add(tTransferData);
 
	  tVData.add(tG);
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData, "DEFINE", "WorkDayDefUI"))
  	{
		  Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	   	FlagStr = "Fail";
	  }
	  else
	  {
	  	Content = " 保存成功! ";
		  FlagStr = "Succ";
    }
%>
<html>
<script language="javascript" >
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
