<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FindVATRateSave.jsp
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
  String busiName=   "FindVATRateUI";
  GlobalInput tG = new GlobalInput();   /*��ǰ��¼��Ϣ */
  tG=(GlobalInput)session.getValue("GI");
	
	 String FeeTypeCode  = request.getParameter("FeeTypeCode1");  //�������ͱ���
	  String FeeTypeName  = request.getParameter("FeeTypeName1");  //������������
	  String Taxible  = request.getParameter("Taxible");  //   �Ƿ�Ӧ˰
	  String RiskRele  = request.getParameter("RiskRele"); //  �Ƿ��������
	  String RecordID = request.getParameter("RecordID");  //id
	  LDVATConfigSet tLDVATConfigSet = new LDVATConfigSet();
	  LDVATConfigSchema tLDVATConfigSchema = new LDVATConfigSchema(); //��ֵ˰����
	  tLDVATConfigSchema.setFeeTypeCode(FeeTypeCode);
	  tLDVATConfigSchema.setFeeTypeName(FeeTypeName);
	  tLDVATConfigSchema.setIsTaxable(Taxible);
	  tLDVATConfigSchema.setIsReleToRiskType(RiskRele);
	  tLDVATConfigSchema.setID(RecordID); 
	  tLDVATConfigSet.add(tLDVATConfigSchema);

  VData tVData = new VData();
  tVData.add(tLDVATConfigSet);
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
