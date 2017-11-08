<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";
	  String msg_id = request.getParameter("msg_id");
	  String msg_cn = request.getParameter("msg_cn");
	  String msg_ja = request.getParameter("msg_ja");
	  ExeSQL tExeSQL = new ExeSQL();
		try
		{  
				String sql = "update msg_mapping set msg_cn='"+msg_cn+"',msg_ja='"+msg_ja+"' where msg_id = '"+msg_id+"'";
				if(tExeSQL.execUpdateSQL(sql))
				{
					Content = bundle.getString("M0000055381");
					FlagStr = "Succ";
				} 
				else
				{
				Content = "数据提交失败";
		    FlagStr = "Fail";
				} 
				            
		}
		catch(Exception ex)
		{
		    Content = bundle.getString("M0000055382") + ex.toString();
		    FlagStr = "Fail";
		}
		System.out.println("------  end------");
%>                      
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

