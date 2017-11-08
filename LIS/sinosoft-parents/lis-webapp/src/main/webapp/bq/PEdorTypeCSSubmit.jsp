<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>

<%@ page language="java" pageEncoding="GBK"%>

<%
		String tFlag = "Succ";
		String tInfo = "";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String w = request.getParameter("w");
		String h =  request.getParameter("h");
		String pageName =  request.getParameter("pageName");
		String EdorAcceptNo =  request.getParameter("EdorAcceptNo");
		String tRealPath = application.getRealPath("/").replace('\\','/');
		if(tRealPath == null || tRealPath.equals("")){
			tRealPath = session.getServletContext().getRealPath("/");
		}
		TransferData sTransferData = new TransferData();
		sTransferData.setNameAndValue("x1", x1);
		sTransferData.setNameAndValue("y1", y1);   	
		sTransferData.setNameAndValue("w", w);   	
		sTransferData.setNameAndValue("h", h);   	
		sTransferData.setNameAndValue("pageName", pageName);   	
		sTransferData.setNameAndValue("EdorAcceptNo", EdorAcceptNo);   
		sTransferData.setNameAndValue("realPath", tRealPath);   
	 	VData tVData = new VData();
	 	tVData.add(sTransferData);
	 	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 	if(!tBusinessDelegate.submitData(tVData, "", "PEdorCSDetailBL")){
			 tFlag = "Fail";
			 tInfo = "½ØÍ¼Ê§°Ü,Ô­ÒòÊÇ:"+tBusinessDelegate.getCErrors().getFirstError();
		 }
		 String outjson = "{ \"Flag\":\""+tFlag+"\",\"Info\":\""+tInfo+"\"}";
	 	 out.print(outjson);
		
	
	
%>
