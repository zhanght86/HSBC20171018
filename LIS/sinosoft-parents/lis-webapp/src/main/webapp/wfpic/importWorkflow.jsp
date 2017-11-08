<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.workflowengine.*" %>
<%  
    request.setCharacterEncoding("GBK");

    String Paras = request.getParameter("Para");
    //String Path = request.getParameter("Path");
    BusinessDelegate tBusinessDelegate; 
    VData mVData = new VData();
    mVData.add(Paras); 
   // mVData.add(Path); 
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(mVData, "", "ExportImageImbl"))
  	{
        //loggerDebug("active", tBusinessDelegate.getCErrors().getFirstError());
	  }
	  else
	  {  
         response.getWriter().println(StrTool.unicodeToGBK((String)tBusinessDelegate.getResult().getObject(0)));
    }
	  
%>