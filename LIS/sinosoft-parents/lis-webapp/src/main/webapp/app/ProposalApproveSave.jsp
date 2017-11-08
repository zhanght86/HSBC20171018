<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ProposalApproveSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";

	try
	{
		GlobalInput tG = new GlobalInput();
		tG=(GlobalInput)session.getValue("GI");
	  
	  	//接收信息
		LCPolSchema tLCPolSchema = new LCPolSchema();
		
		String polNo = request.getParameter("polNo");
		String approveFlag = request.getParameter("approveFlag");
		loggerDebug("ProposalApproveSave","ProposalNo:" + polNo + "\napproveFlag:"+ approveFlag); 
		
		tLCPolSchema.setProposalNo(polNo);
		tLCPolSchema.setApproveFlag(approveFlag);
	
		
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCPolSchema );
		tVData.add( tG );
		
		// 数据传输
		//ProposalApproveUI tProposalApproveUI = new ProposalApproveUI();
		String busiName="tbProposalApproveUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData( tVData, "INSERT" ,busiName) == false)
		{
			Content = " 复核失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
	    	Content = " 复核成功! ";
	    	FlagStr = "Succ";
	    }
	} // end of try
	catch( Exception e1 )
	{
    	Content = " 复核失败，原因是:" + e1.toString().trim();
    	FlagStr = "Fail";
    }
	loggerDebug("ProposalApproveSave","---" + Content + "---\n");
%>                      
<html>
<script language="javascript">
try {
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
} catch(ex) { }
</script>
</html>
