<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	
  System.out.println("111");
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");

  LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

  tLOPRTManagerSchema.setPrtSeq(request.getParameter("PrtSeq"));
  
  CErrors tErrors = new CErrors( );

  //后面要执行的动作：添加，修改，删除
  
	  VData tVData = new VData();
    VData mResult = new VData();

     tVData.addElement(tG);
     tVData.addElement(tLOPRTManagerSchema);

	   String strErrMsg = "";
	   boolean Flag=true;
	   String FlagStr = "";
     String Content = "";
     
		
     RePrintUI tRePrintUI = new RePrintUI();
     try
  {
    tRePrintUI.submitData(tVData,"CONFIRM");
  }
  catch(Exception ex)
  {
    Content = "数据提交失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tErrors = tRePrintUI.mErrors;
    if (!tErrors.needDealError())
    {                          
    	Content = " 数据提交成功! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	
    	Content = " 数据提交失败，请检查数据是否正确";
    	FlagStr = "Fail";
    }
  }     
	   
        System.out.println("数据已提交");
        mResult = tRePrintUI.getResult();
        
  
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
