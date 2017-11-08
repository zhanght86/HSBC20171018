
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.f1print.BqPrintBean"%><html>
<%@page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page import="oracle.sql.*"%>
<%@page import="oracle.jdbc.driver.*"%>

<%
        String tEdorNo = request.getParameter("EdorNo");
        //add by Minim
        String tType = request.getParameter("type");
	
	String strErrInfo = "";
	
	if( tEdorNo != null && !tEdorNo.equals("") ) {
	    try
	    {	
			String tTableName = "";
			if(tType!= null && tType.equals("CashValue")){
				tTableName = "LPEDORPRINT3";
			}else{
				tTableName = "LPEDORPRINT";
			}
			String tColName = "EdorInfo";
			String sql ="SELECT 1 FROM "+tTableName+" WHERE EdorNo = '" + tEdorNo + "'"; 
			TransferData sTransferData=new TransferData();
			VData sVData = new VData();
			sTransferData.setNameAndValue("SQL", sql);
			sVData.add(sTransferData);
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			String hasPrint = "";
			
        	if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI")){
        		hasPrint = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
        	}
        	InputStream ins = null;
        	if("1".equals(hasPrint)){
        		tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        		TransferData tTransferData=new TransferData();
        		tTransferData.setNameAndValue("EdorNo",tEdorNo);
        		tTransferData.setNameAndValue("TableName",tTableName);
        		tTransferData.setNameAndValue("ColName",tColName);
        		VData tVData = new VData();
        		tVData.add(tTransferData);
        		if(tBusinessDelegate.submitData(tVData, "", "BQPritUI")){
        			BqPrintBean bpb = (BqPrintBean)tBusinessDelegate.getResult().getObjectByObjectName("BqPrintBean", 0);
        			if(bpb != null){
        				ins = new ByteArrayInputStream(bpb.getBytes());
        				session.putValue("PrintStream", ins);
	        			response.sendRedirect("GetF1Print.jsp");
        			}
        		}
        	}
	    } catch(Exception ex) {
	        ex.printStackTrace();
	    }
	} else {
		strErrInfo = "没有输入保全号";
	}
%>
<body>
<%= strErrInfo %>
</body>
</html>
