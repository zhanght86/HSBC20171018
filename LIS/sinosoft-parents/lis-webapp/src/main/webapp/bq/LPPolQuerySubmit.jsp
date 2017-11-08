<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：LCPolQueyr.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：TJJ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Result ="";

  //保单信息部分
  LPEdorMainSchema tLPEdorMainSchema   = new LPEdorMainSchema();
  try{
  tLPEdorMainSchema.setPolNo(request.getParameter("PolNo"));
  tLPEdorMainSchema.setInsuredNo(request.getParameter("CustomerNo"));
  tLPEdorMainSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
  loggerDebug("LPPolQuerySubmit.jsp",tLPEdorMainSchema.getGrpPolNo());
  tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorMainSchema.setEdorType(request.getParameter("EdorType"));
  }catch(Exception e)
  {}
  // 准备传输数据 VData
  VData tVData = new VData();
	tVData.addElement(tLPEdorMainSchema);
	String tTransact = request.getParameter("fmtransact");
  // 数据传输
  try
  {
  	CPolUI tCPolUI   = new CPolUI();
		if (!tCPolUI.submitData(tVData,tTransact))
		{
      Content = " 查询失败，原因是: " + tCPolUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
		}
		else
		{
			FlagStr = "Succ";
			Content = "查询成功!";
			tVData.clear();
			Result = (String)tCPolUI.getResult().get(0);
			//loggerDebug("LPPolQuerySubmit.jsp",Result);
			%>
			<%		
			}
	 }
	catch(Exception ex)
	{
		Content = tTransact+"失败，原因是:" + ex.toString();
    		FlagStr = "Fail";
	}			
  
loggerDebug("LPPolQuerySubmit.jsp","------end------");
%>
 <html>
	<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
	</script>
</html>