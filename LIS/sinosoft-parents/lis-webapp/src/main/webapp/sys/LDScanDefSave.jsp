<%@page import="java.net.URLDecoder"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.CErrors"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LDScanMainSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@ page language="java" pageEncoding="GBK"%>

<%
  String oper = request.getParameter("oper");
  LDScanMainSchema tLDScanMainSchema   = new LDScanMainSchema();
  //输出参数
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";
  	GlobalInput tG = new GlobalInput();
  	tG=(GlobalInput)session.getValue("GI");
	if(oper.equals("am")){
		VData tVData = new VData();
 		String tSubTypeName = request.getParameter("SubTypeName");
		tLDScanMainSchema.setSubType(request.getParameter("SubType"));
		tLDScanMainSchema.setSubTypeName(tSubTypeName);
		tLDScanMainSchema.setNumPages(0);
		tLDScanMainSchema.setDefPage(request.getParameter("DefPage"));
		tLDScanMainSchema.setOperator(tG.Operator);
		tLDScanMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLDScanMainSchema.setMakeTime(PubFun.getCurrentTime());
		tVData.addElement(tLDScanMainSchema);
		tVData.add(tG);
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  		try {
		 	if(!tBusinessDelegate.submitData(tVData, oper, "LDScanUI")){
			 	FlagStr = "Fail";
			 	Content = "保存失败"+tBusinessDelegate.getCErrors().getFirstError();
			}	
  		}  catch(Exception ex) {
  		    Content = "保存失败，" + ex.toString();
  		    FlagStr = "Fail";
  		}
	  if (!FlagStr.equals("Fail")){
			Content = "保存成功";
	    	FlagStr = "Succ";
	  }
	}
  
	

  
%>   

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>                   



