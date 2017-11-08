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
  String FlagStr = "Successs";
  String Content = ""+"提交成功！"+"";
      	 
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");
  
  TransferData tTransferData = new TransferData();
  
  tTransferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
  tTransferData.setNameAndValue("CalCode", request.getParameter("CalCode"));
    tTransferData.setNameAndValue("CalModeType", request.getParameter("CalModeType"));
     tTransferData.setNameAndValue("Des", request.getParameter("Des"));
      tTransferData.setNameAndValue("ID", request.getParameter("ID"));
       tTransferData.setNameAndValue("NewRiskCode", request.getParameter("NewRiskCode"));
  tTransferData.setNameAndValue("NewCalCode", request.getParameter("NewCalCode"));
    tTransferData.setNameAndValue("OldCalCode", request.getParameter("OldCalCode"));
    tTransferData.setNameAndValue("NewCalModeType", request.getParameter("NewCalModeType"));
     tTransferData.setNameAndValue("NewDes", request.getParameter("NewDes"));
    String operator=request.getParameter("Operator");
        tTransferData.setNameAndValue("Operator", operator);
  
  System.out.println("产品险种代码：" + request.getParameter("RiskCode")+""+"操作："+""+request.getParameter("Operator"));
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
  PDRiskCalDefBL tPDRiskCalDefBL=new PDRiskCalDefBL();
   if(!tPDRiskCalDefBL.submitData(tVData,operator)){
   FlagStr = "Fail";
   Content=""+"提交失败！"+""+tPDRiskCalDefBL.getErrors().getFirstError();
   }
%>  
 
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 );
</script>
</html>
 