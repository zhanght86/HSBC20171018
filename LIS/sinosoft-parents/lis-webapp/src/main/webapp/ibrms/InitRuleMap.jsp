<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.ibrms.RuleMapCached"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page language="java" pageEncoding="GBK"%>

<%
	String subType = request.getParameter("subType");


	RuleMapCached mRuleMapCached = RuleMapCached.getInstance();
	
	
	      SSRS tMapSSRS=(SSRS)mRuleMapCached.getRuleSSRS();
	      // language,msgtype,keyid,msg
	      String outjson = "[";
	      for(int i = 1;i<=tMapSSRS.MaxRow;i++){
		    String tLan = tMapSSRS.GetText(i, 1);
				String tMsgType = tMapSSRS.GetText(i, 2);
				String tKeyId = tMapSSRS.GetText(i, 3);
				String tMsg = tMapSSRS.GetText(i, 4);
				
		      outjson += "{ \"Language\":\""+tLan+"\",\"MsgType\":\""+tMsgType+"\",\"KeyId\":\""+tKeyId+"\",\"Msg\":\""+tMsg+"\"}";
		      if(i<tMapSSRS.MaxRow){
		    	  outjson+=",";
		      }
	      }
	      outjson += "]";
	      loggerDebug("InitRuleMap",outjson);
	      out.print(outjson);
	 



%>
