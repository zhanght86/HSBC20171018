<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
	//������Ϣ������У�鴦��
	//�������	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getAttribute("GI");
	//�������
	CErrors tError = new CErrors();
	VData tVData = new VData();
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
	String transact = "";
	String FlagStr = "";
	String Content = "";
	
	String tRiskCode ="";
	String tPrintID ="";
	String tTemplateName ="";
	String tDocCode ="";
	String tControlType ="";
	String tDisplayLabel ="";
	String tMultiple ="";
	String tCriteria ="";
	String tPrintConfigValue ="";
	String tPrintConfigID ="";

	int count = 0;
	
	transact = request.getParameter("Transact");
	//System.out.println(transact);
	PD_LDPrintConfigSchema tPD_LDPrintConfigSchema = new PD_LDPrintConfigSchema();
	tPD_LDPrintConfigSchema.setPrintConfigID(request.getParameter("PrintConfigID"));
	tPD_LDPrintConfigSchema.setRiskCode(request.getParameter("RiskCode2"));
	tPD_LDPrintConfigSchema.setPrintID(request.getParameter("PrintID2"));
	tPD_LDPrintConfigSchema.setTemplateName(request.getParameter("TemplateName2"));
	tPD_LDPrintConfigSchema.setDocCode(request.getParameter("DocCode2"));
	tPD_LDPrintConfigSchema.setControlType(request.getParameter("ControlType"));
	tPD_LDPrintConfigSchema.setDisplayLabel(request.getParameter("DisplayLabel"));
	tPD_LDPrintConfigSchema.setDisplayLabel(request.getParameter("Multiple"));
	tPD_LDPrintConfigSchema.setCriteria(request.getParameter("Criteria"));
	tPD_LDPrintConfigSchema.setPrintConfigValue(request.getParameter("PrintConfigValue"));
	tPD_LDPrintConfigSchema.setRemark1(request.getParameter("Remark1"));
	tPD_LDPrintConfigSchema.setRemark2(request.getParameter("Remark2"));
	TransferData tTransferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getAttribute("GI");

	
	String busiName = "tbPD_PrintConfigUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();

	//�����ļ�ʧ��ʱ count = -1 
	if(count != -1){
	try {
		// ׼���������� VData
		tTransferData.setNameAndValue("Transact", transact);//���ݲ�����		
		
		tVData.add(tPD_LDPrintConfigSchema);
		tVData.add(tTransferData);
		tVData.add(tGI);
		//tBusinessDelegate.submitData(tVData, transact, busiName);
		if (tBusinessDelegate.submitData(tVData, transact, busiName) == false) {
		tError = tBusinessDelegate.getCErrors();
		if (transact.equals("INSERT")) {
			Content = ""+""+""
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "fail";
		} else if (transact.equals("UPDATE")) {
			Content = ""+""+""
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		} else if (transact.equals("DELETE")) {
			Content = ""+"ɾ��ʧ�ܣ�ԭ����"+""
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
	} else {
		if (transact.equals("INSERT")) {
			Content = ""+"����ɹ�"+"";
			FlagStr = "Succ";
		} else if (transact.equals("UPDATE")) {
			Content = ""+"�޸ĳɹ�"+"";
			FlagStr = "Succ";
		} else if (transact.equals("DELETE")) {
			Content = ""+"ɾ���ɹ�"+"";
			FlagStr = "Succ";
		}
	}
	} catch (Exception ex) {
		Content = transact + ""+"ʧ�ܣ�ԭ����:"+"" + ex.toString();
		FlagStr = "Fail";
	}
	}
%>

<script type="text/javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
