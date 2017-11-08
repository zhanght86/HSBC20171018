<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LDCode1Save.jsp
//程序功能：
//创建日期：2005-01-26 11:24:08
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
  LDCode1Schema tLDCode1Schema   = new LDCode1Schema();
  OLDCode1UI tOLDCode1UI   = new OLDCode1UI();
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
    tLDCode1Schema.setCodeType(request.getParameter("CodeType"));
    tLDCode1Schema.setCode(request.getParameter("Code"));
    tLDCode1Schema.setCode1(request.getParameter("Code1"));
    tLDCode1Schema.setCodeName(request.getParameter("CodeName"));
    tLDCode1Schema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCode1Schema.setComCode(request.getParameter("ComCode"));
    tLDCode1Schema.setOtherSign(request.getParameter("OtherSign"));
  try
  {
  // 准备传输数据 VData
  	VData tVData = new VData();
	  tVData.add(tLDCode1Schema);
  	tVData.add(tG);
    tOLDCode1UI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tOLDCode1UI.mErrors;
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
