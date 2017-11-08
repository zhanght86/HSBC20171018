<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：
//程序功能：
//创建日期：2009-09-10
//创建人  ：sunyu
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*" %>

<%
  String FlagStr = "";
  String Content = "";
      	 
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("RiskCode", request.getParameter("riskcode"));
  tTransferData.setNameAndValue("batch", request.getParameter("batch"));
  
  System.out.println("产品险种代码：" + request.getParameter("riskcode") + " "+"批次号："+"" + request.getParameter("batch"));
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
   
  //调用PDLBRiskInfoBL方法 ，返回一个ListTable ListTable中方的就是需要展现在页面上的信息
  PDProductCallBackBL tPDProductCallBackBL= new PDProductCallBackBL();
  
  if(!tPDProductCallBackBL.submitData(tVData, "callback")){
	  FlagStr = "Fail";
	  Content = ""+"回退失败，原因为："+"" + tPDProductCallBackBL.mErrors.getFirstError() + "!S";
  }else{
	  FlagStr = "True";
	  Content = ""+"回退成功！"+"";
  }
%>  
 
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 