<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//������Ѷ������У�鴦��
	//�������
	
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getAttribute("GI");	
	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	String tOperate = request.getParameter("OperateType");
	String ArithSubType = request.getParameter("ArithSubType");
		
	String tAccumulateDefNO = request.getParameter("AccumulateDefNO");//�ۼƷ��ձ���
	String tArithmeticDefID = request.getParameter("ArithmeticDefID");//���㷨����
	String tArithmeticDefName = request.getParameter("ArithmeticDefName");//���㷨����
	String PreceptType = request.getParameter("PreceptType");//���㷨����
	
	RICalDefSchema tRICalDefSchema = new RICalDefSchema();
	RIAccumulateDefSchema tRIAccumulateDefSchema = new RIAccumulateDefSchema();
	
	tRICalDefSchema.setArithmeticDefID(tArithmeticDefID);
	tRICalDefSchema.setArithmeticDefName(tArithmeticDefName);
	tRICalDefSchema.setArithSubType(ArithSubType);
	tRICalDefSchema.setAccumulateDefNO(tAccumulateDefNO);
	tRICalDefSchema.setPreceptType(PreceptType);
	//tRICalDefSchema.setStandbyString1(tArithType);

	if("L".equals(ArithSubType))
	{
		//������ȡ���㷨
		tRIAccumulateDefSchema.setArithmeticID(tArithmeticDefID);
		tRIAccumulateDefSchema.setAccumulateDefNO(tAccumulateDefNO);	
		//tRICalDefSchema.setArithSubType("01");
	}	
	else
	{
		tRICalDefSchema.setStandbyString2(tAccumulateDefNO);
	}
	
	// ׼���������� VData
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ArithSubType",ArithSubType);
	
	tVData.add(tRICalDefSchema);
	tVData.add(tRIAccumulateDefSchema);
	tVData.add(tTransferData);	
	tVData.add(tG);
	
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, tOperate,
			"RIItemCalUI")) {
		if (uiBusinessDelegate.getCErrors() != null
				&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+""
					+ uiBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = ""+"����ʧ��"+"";
			FlagStr = "Fail";
		}
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if ("".equals(FlagStr)) {
		tError = uiBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = ""+"����ɹ�"+""; 
			FlagStr = "Succ";
		} else {
			Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
  //��Ӹ���Ԥ����
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

