<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-09-10
//������  ��sunyu
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*" %>

<%
  String FlagStr = "Successs";
  String Content = ""+"�ύ�ɹ���"+"";
      	 
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
  
  System.out.println("��Ʒ���ִ��룺" + request.getParameter("RiskCode")+""+"������"+""+request.getParameter("Operator"));
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
  PDRiskCalDefBL tPDRiskCalDefBL=new PDRiskCalDefBL();
   if(!tPDRiskCalDefBL.submitData(tVData,operator)){
   FlagStr = "Fail";
   Content=""+"�ύʧ�ܣ�"+""+tPDRiskCalDefBL.getErrors().getFirstError();
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
 