<%@ page language="java" import="java.util.*" %>
<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %> 
<%@page import="java.net.URLDecoder"%> 
<%@page import="java.io.File"%> 
<%@page import="java.io.FileInputStream"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.lis.pubfun.LisImageShow"%> 
<%
    response.reset();  
    response.setContentType("image/gif");  
    response.setHeader("Cache-Control", "no-cache");  
    response.setDateHeader("Expires", 0);  
    String img_url = request.getParameter("img_url");
    
    GlobalInput tGlobalInput = new GlobalInput(); 
    tGlobalInput = session.getValue("GI")==null?null:(GlobalInput)session.getValue("GI");
    VData inVData = new VData();
    inVData.add(0,tGlobalInput);
    inVData.add(1,img_url);
    inVData.add(2,request);
    
    //LIS6.5 调用方法
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //if (!tUWQualityManageUI.submitData(inVData, "AGENT&&UPDATE")) {
	if (!tBusinessDelegate.submitData(inVData, "SHOWPAGE","LisImageShowUI")) {
	//失败,不做处理
	}else {
		 VData rVData = tBusinessDelegate.getResult();
		 java.io.InputStream fis = rVData.get(0)==null?null:(java.io.InputStream)rVData.get(0);
		 if(fis!=null){
		    	java.io.OutputStream os = response.getOutputStream();  
		    	byte[] buf = new byte[4096];  
		    	int bytes = 0;  
		    	while ((bytes = fis.read(buf)) != -1)  
		        	os.write(buf, 0, bytes);  
		    	fis.close();  
		    	os.flush();
		    	out.clear();
		    	out = pageContext.pushBody();
		  }else{
		    	 
		  }
	}
%>