<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ConfigVATRateSave.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>

  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  CErrors tError = null;
  String Content = "";
  String FlagStr = "";
  /* String tOperate=request.getParameter("hideOperate"); */
  String tOperate = request.getParameter("fmtransact");
  String busiName=   "ConfigVATRateUI";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
      String configID = request.getParameter("ConfigID");//����ID
      String id = request.getParameter("TaxID");//����id
	  String RiskGrpCode = request.getParameter("RiskGrpCode");//������code
	  String FeeTypeCode  = request.getParameter("TFeeTypeCode");  //����ֻΪ����ʾ��
	  String ManageCom  = request.getParameter("ManageCom");  //����
	  String StartDate  = request.getParameter("StartDate");
	  String EndDate  = request.getParameter("EndDate");
	  String TaxRate  = request.getParameter("TaxRate"); //˰��
	  
	  LDVATTaxConfigSet tLDVATTaxConfigSet = new LDVATTaxConfigSet();
	  LDVATTaxConfigSchema tLDVATTaxConfigSchema = new LDVATTaxConfigSchema();
	  tLDVATTaxConfigSchema.setRiskGrpCode(RiskGrpCode);
	  tLDVATTaxConfigSchema.setManageCom(ManageCom);
	  tLDVATTaxConfigSchema.setStartDate(StartDate);
	  tLDVATTaxConfigSchema.setEndDate(EndDate);
	  tLDVATTaxConfigSchema.setTaxRate(TaxRate);
	  tLDVATTaxConfigSchema.setID(id); 
	  tLDVATTaxConfigSchema.setConfigID(configID);
	  tLDVATTaxConfigSet.add(tLDVATTaxConfigSchema);
	

  VData tVData = new VData();
  tVData.add(tLDVATTaxConfigSet);
  tVData.add(tG);
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData, tOperate, busiName))
  {
	  Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
	 	FlagStr = "Fail";
	}
	else
	{
		Content = " �����ɹ�! ";
	  FlagStr = "Succ";
  }  
 

  %>
  <html>
<script language="javascript">
 parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
