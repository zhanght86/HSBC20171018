<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LDCodeSave.jsp
//程序功能：
//创建日期：2005-01-26 13:18:17
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.config.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
  //接收信息，并作校验处理。
  //输入参数
  LDCodeSchema tLDCodeSchema   = new LDCodeSchema();
  OLDCodeUI tOLDCodeUI   = new OLDCodeUI();
  //输出参数
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
	
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
    tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
    tLDCodeSchema.setCode(request.getParameter("Code"));
    tLDCodeSchema.setCodeName(request.getParameter("CodeName"));
    tLDCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCodeSchema.setComCode(request.getParameter("ComCode"));
    tLDCodeSchema.setOtherSign(request.getParameter("OtherSign"));
  try
  {
  // 准备传输数据 VData
  	VData tVData = new VData();
	tVData.add(tLDCodeSchema);
  	tVData.add(tG);
    tOLDCodeUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tOLDCodeUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " 保存成功! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  
  //添加各种预处理
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
