
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuGrpSpecSave.jsp
//程序功能：人工核保特约承保
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//modify by zhangxing
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null)
   {
	 	out.println("session has expired");
		 return;
   } 
    //接收信息
  
  String tOperate = request.getParameter("Operate");
  String tGrpcontNo = request.getParameter("GrpContNo");
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	TransferData tTransferData = new TransferData();

  tTransferData.setNameAndValue("GrpContNo",tGrpcontNo);
	tTransferData.setNameAndValue("Remark",tRemark);
	tTransferData.setNameAndValue("SpecReason",tSpecReason);
	
try{
	  
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tG);
		
		 // 数据传输
	   GrpUWSpecUI tGrpUWSpecUI   = new GrpUWSpecUI();
	   if (!tGrpUWSpecUI.submitData(tVData,tOperate))
	   {             
			 int n = tGrpUWSpecUI.mErrors.getErrorCount();
			 Content = " 操作失败，原因是: " + tGrpUWSpecUI.mErrors.getError(0).errorMessage;
			 FlagStr = "Fail";
	   }
	 
	   if (FlagStr == "Fail")
	   {
		   tError = tGrpUWSpecUI.mErrors;
		   if(!tError.needDealError())
		   {                          
		    	Content = "核保特约成功! ";
		    	FlagStr = "Succ";
		   }
		   else                                                                           
		   {
		    FlagStr = "Fail";
		   }
	   }
	 }
	
  catch(Exception e)
  {
  	e.printStackTrace();
  	Content = Content.trim()+"提示：异常终止!";
  }
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

