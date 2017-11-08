<%
//程序名称：OLDCodeInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //接收信息，并作校验处理。
  //输入参数
  LDCodeSchema tLDCodeSchema   = new LDCodeSchema();

  ALDCodeUI tOLDCode = new ALDCodeUI();

  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();

	//tG.Operator = "Admin";
	//tG.ComCode  = "001";
 // session.putValue("GI",tG);

  tG=(GlobalInput)session.getValue("GI");


    tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
    tLDCodeSchema.setCode(request.getParameter("Code"));
    tLDCodeSchema.setCodeName(request.getParameter("CodeName"));
    tLDCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCodeSchema.setComCode(request.getParameter("ComCode"));
    tLDCodeSchema.setOtherSign(request.getParameter("OtherSign"));


  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLDCodeSchema);
	tVData.add(tG);
  try
  {
    tOLDCode.submitData(tVData,tOperate);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tError = tOLDCode.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " 保存成功! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

