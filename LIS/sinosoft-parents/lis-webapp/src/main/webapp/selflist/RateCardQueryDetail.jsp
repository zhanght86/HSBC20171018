<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//name :RateCardQueryDetail.jsp
//Creator :zz
//date :2008-06-20
//�������ʱ��ѯ����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.list.*"%>

<%
    loggerDebug("RateCardQueryDetail","��ʼִ��RateCardQueryDetail.jsp");  
    
    //�������:��������ַ����жϱ�־����ʾ�ɹ���ʧ�ܵ���������
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";


    //������Ϣ���֣��������ͣ���������
    String mOperateType="RETURNDATA";
	loggerDebug("RateCardQueryDetail","���ڵĲ�����־��"+mOperateType);
		
	//����LZCardFeeSchema,���ǰһ���洫��ĵ�֤����
    RateCardSchema tRateCardSchema = new RateCardSchema();
    tRateCardSchema.setRiskCode(request.getParameter("RiskCode"));
    tRateCardSchema.setPrem(request.getParameter("Prem"));
    
    
    // ׼���������� VData
    VData tVData = new VData();
    tVData.addElement(tRateCardSchema);
    
    // ���ݴ���
    RateCardBL tRateCardBL = new RateCardBL();
    if (!tRateCardBL.submitData(tVData,mOperateType))
    {
    	Content = " ��ѯʧ�ܣ�ԭ����: " + tRateCardBL.mErrors.getError(0).errorMessage;
      	FlagStr = "Fail";
    }
    else
    {
		tVData.clear();
		tVData = tRateCardBL.getResult();
		RateCardSchema yRateCardSchema = new RateCardSchema();
		RateCardSet yRateCardSet = new RateCardSet();
		yRateCardSet = (RateCardSet)tVData.getObjectByObjectName("RateCardSet",0);
		yRateCardSchema=yRateCardSet.get(1);
		%>
    	<script language="javascript">
	       top.opener.document.all("Riskcode").value = "<%=yRateCardSchema.getRiskCode()%>";
	       top.opener.document.all("ProductPlan").value = "<%=yRateCardSchema.getProductPlan()%>";
	       top.opener.document.all("InsuYear").value = "<%=yRateCardSchema.getInsuYear()%>";
	       top.opener.document.all("InsuYearFlag").value = "<%=yRateCardSchema.getInsuYearFlag()%>";
	       top.opener.document.all("Prem").value = "<%=yRateCardSchema.getPrem()%>";
	       top.opener.document.all("Mult").value = "<%=1%>";
	       
	       top.opener.document.all("HiddenRiskCode").value = "<%=yRateCardSchema.getRiskCode()%>";
	       top.opener.document.all("HiddenPrem").value = "<%=yRateCardSchema.getPrem()%>";
    	</script>
		<%
	}
  if (FlagStr.equals("Fail"))
  {
    tError = tRateCardBL.mErrors;
    if (!tError.needDealError())
    {
    	Content = " ��ѯ�ɹ�! ";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
loggerDebug("RateCardQueryDetail","------end------");
loggerDebug("RateCardQueryDetail",FlagStr);
loggerDebug("RateCardQueryDetail",Content);
out.println("<script language=javascript>");
out.println("top.close();");
out.println("</script>");
%>
