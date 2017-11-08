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
	//接收信息，并作校验处理。
	//输入参数	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getAttribute("GI");
	//输出参数
	CErrors tError = new CErrors();
	VData tVData = new VData();
	//后面要执行的动作：添加，修改，删除
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

	//上载文件失败时 count = -1 
	if(count != -1){
	try {
		// 准备传输数据 VData
		tTransferData.setNameAndValue("Transact", transact);//传递操作符		
		
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
			Content = ""+"删除失败，原因是"+""
			+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
	} else {
		if (transact.equals("INSERT")) {
			Content = ""+"保存成功"+"";
			FlagStr = "Succ";
		} else if (transact.equals("UPDATE")) {
			Content = ""+"修改成功"+"";
			FlagStr = "Succ";
		} else if (transact.equals("DELETE")) {
			Content = ""+"删除成功"+"";
			FlagStr = "Succ";
		}
	}
	} catch (Exception ex) {
		Content = transact + ""+"失败，原因是:"+"" + ex.toString();
		FlagStr = "Fail";
	}
	}
%>

<script type="text/javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
