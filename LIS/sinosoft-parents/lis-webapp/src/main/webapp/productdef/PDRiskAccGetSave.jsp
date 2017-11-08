<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRiskAccGetSave.jsp 
//程序功能：给付账户定义
//创建日期：2009-3-16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 

 PDRiskAccGetBL pDRiskAccGetBL = new PDRiskAccGetBL();
 
	 CErrors tError = null;
	 String tRela  = "";                 
	 String FlagStr = "";
	 String Content = "";
	 String operator = "";
	 TransferData transferData = new TransferData();
	 GlobalInput tG = new GlobalInput(); 
	 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 	operator = request.getParameter("operator");
	transferData.setNameAndValue("GETDUTYCODE",request.getParameter("GETDUTYCODE"));
	transferData.setNameAndValue("INSUACCNO",request.getParameter("INSUACCNO"));
	transferData.setNameAndValue("RISKCODE",request.getParameter("RISKCODE"));
	transferData.setNameAndValue("GETDUTYNAME",request.getParameter("GETDUTYNAME"));
	transferData.setNameAndValue("tableName",request.getParameter("tableName"));
	
	transferData.setNameAndValue("DEFAULTRATE","");
	transferData.setNameAndValue("NEEDINPUT","");
	transferData.setNameAndValue("CALCODEMONEY","");
	transferData.setNameAndValue("DEALDIRECTION","1");
	transferData.setNameAndValue("CALFLAG","");
	transferData.setNameAndValue("ACCCREATEPOS","");
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDRiskAccGetBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDRiskAccGetBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

