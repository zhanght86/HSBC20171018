<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%
request.setCharacterEncoding("UTF-8");
String Action = request.getParameter("Action");
//loggerDebug("active",Action);
String Para = request.getParameter("Para");
//loggerDebug("active",Para);
String ActionId = request.getParameter("ActionId");
//loggerDebug("active",ActionId);
//long t = System.currentTimeMillis();

BusinessDelegate tBusinessDelegate; 
TransferData tTransferData = new TransferData();
tTransferData.setNameAndValue("Action", Action);
tTransferData.setNameAndValue("Para", Para);
tTransferData.setNameAndValue("ActionId", ActionId);

    VData mVData = new VData();
    mVData.add(tTransferData); 
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(mVData, "", "activeUI"))
  	{
        loggerDebug("active", tBusinessDelegate.getCErrors().getFirstError());
	  }
	  else
	  {
         response.getWriter().println(StrTool.unicodeToGBK((String)tBusinessDelegate.getResult().getObject(0)));
    }        
%>
