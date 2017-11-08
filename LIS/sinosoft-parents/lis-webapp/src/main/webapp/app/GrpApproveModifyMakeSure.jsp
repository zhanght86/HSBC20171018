<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpQuestModifyMakeSure.jsp
//程序功能：
//创建日期：2002-08-15 11:48:43
//创建人  ：CrtHtml程序创建
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
	String tRela  = "";                
	String FlagStr="";
	String GrpProposalNo = "";
	String Content="";

	//输入参数
	VData tVData = new VData();
	LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	GrpProposalNo = request.getParameter( "GrpProposalNo" );
   
	if( GrpProposalNo=="")
	{
	    
	}
	else
	{
        //集体信息
	    tLCGrpContSchema.setProposalGrpContNo(request.getParameter("GrpProposalNo"));
	    tLCGrpContSchema.setApproveFlag("0");
	 
	
    loggerDebug("GrpApproveModifyMakeSure","end setSchema:");
	// 准备传输数据 VData
	tVData.add( tLCGrpContSchema );
	tVData.add( tG );
	String tOperate="UpDate";
	//GrpAppModifyMakeSureUI tGrpAppModifyMakeSureUI = new GrpAppModifyMakeSureUI();
	
	String busiName="tbGrpAppModifyMakeSureUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )
	{
		Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
		
	}
	else
	{
		Content = " 保存成功! ";
		FlagStr = "Succ";
		%>
    	<script language="javascript">
    	 	   	
    	</script>
		<%		
	}
      loggerDebug("GrpApproveModifyMakeSure","Content:"+Content);	
}
%>                      
<html>
<script language="javascript">
try {
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>")
    } 
catch(ex) {}
</script>
</html>

