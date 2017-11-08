<html>
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
        String strEdorNo = request.getParameter("EdorNo");
        //add by Minim
        String strType = request.getParameter("type");
        loggerDebug("EndorsementF1P","prtType: " + strType);
	
	String strErrInfo = "";
	
	if( strEdorNo != null && !strEdorNo.equals("") ) {
	    try
	    {
		Connection conn = DBConnPool.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		if( conn == null ) {
			strErrInfo = "连接数据库失败";
		} else {
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			if (strType!=null && strType.equals("CashValue")) {
			   rs = stmt.executeQuery("SELECT * FROM LPEDORPRINT3 WHERE EdorNo = '" + strEdorNo + "' and EdorType='1'");
			}
			else {
                           rs = stmt.executeQuery("SELECT * FROM LPEDORPRINT WHERE EdorNo = '" + strEdorNo + "'");
                        }  
    
                       if( rs.next() ) {
                               COracleBlob tCOracleBlob = new COracleBlob();
                               Blob tBlob = null;
                               if (strType!=null && strType.equals("CashValue")) {
                                       String tSQL = " and EdorNo = '" + strEdorNo + "' and EdorType='1'";
                                       tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT3","edorinfo",tSQL,conn);
                               } else {
                                       String tSQL = " and EdorNo = '" + strEdorNo + "'";
      	                               tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT","edorinfo",tSQL,conn);
                               }	 	
      	
                               //BLOB blob = ((OracleResultSet)rs).getBLOB("edorinfo");
      	                       //BLOB blob = (oracle.sql.BLOB)tBlob; //delete by jianglai at 2004-7-22
      	                       session.putValue("PrintStream", tBlob.getBinaryStream());
      	                       loggerDebug("EndorsementF1P","get stream object");
                        } else {
      	                       loggerDebug("EndorsementF1P","can't get stream object");
      	                       session.putValue("PrintStream", null);
                        }
                        rs.close();
                        stmt.close();
                        conn.close();
	        	response.sendRedirect("GetF1Print.jsp");
	        }
	    }    
	    catch(Exception ex)
	    {
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
