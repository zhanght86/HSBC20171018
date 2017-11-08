<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PlanManuNormChk.jsp
//程序功能：人工核保最终结论录入保存
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) 
  {
  	out.println("session has expired");
	  return;
	}
  
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
	tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));
	tTransferData.setNameAndValue("ContPlanCode",request.getParameter("ContPlanCode"));
	tTransferData.setNameAndValue("PlanType",request.getParameter("PlanType"));
	tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo"));
	tTransferData.setNameAndValue("UWState",request.getParameter("UWState"));
	tTransferData.setNameAndValue("UWIdea",request.getParameter("UWIdea"));
	
	
 try
  {
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);
		// 数据传输
		//PlanUWManuNormGChkUI tPlanUWManuNormGChkUI   = new PlanUWManuNormGChkUI();
		
		String busiName="cbcheckPlanUWManuNormGChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 人工核保失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{    
		   tError = tBusinessDelegate.getCErrors();
		   if (!tError.needDealError())
		   {                     
		    	Content = " 人工核保操作成功!";
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
  	Content = Content.trim()+".提示：异常终止!";
 }
%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
