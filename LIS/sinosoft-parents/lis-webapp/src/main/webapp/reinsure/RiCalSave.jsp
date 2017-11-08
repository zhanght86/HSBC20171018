<%@include file="/i18n/language.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page import="java.util.*"%>

<%
	CErrors tError = new CErrors();
	String FlagStr = "";
	String Content = "";
	String flag = "true";

	RIWFLogSet tRIWFLogSet = new RIWFLogSet();
	String path = application.getRealPath("").replace('\\', '/') + '/';
	String startDate = request.getParameter("StartDate");
	String endDate = request.getParameter("EndDate");
	String processType = request.getParameter("ProcessType");

	String tChk[] = request.getParameterValues("InpTaskListGridChk");
	String batchNo[] = request.getParameterValues("TaskListGrid1");
	String accumulateDefNo[] = request
			.getParameterValues("TaskListGrid2");
	String nodeState[] = request.getParameterValues("TaskListGrid6");

	int lineCount = tChk.length;
	for (int i = 0; i < lineCount; i++) {
		if (tChk[i].equals("1")) {
			RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();

			tRIWFLogSchema.setBatchNo(batchNo[i]);
			tRIWFLogSchema.setTaskCode(accumulateDefNo[i]);
			tRIWFLogSchema.setTaskType("01");
			tRIWFLogSchema.setNodeState(processType);
			tRIWFLogSchema.setStartDate(startDate);
			tRIWFLogSchema.setEndDate(endDate);
			tRIWFLogSet.add(tRIWFLogSchema);
		}
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getAttribute("GI");
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tRIWFLogSet);
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("PATH", path); //日志保存路径
	if (processType.equals("00") || processType.equals("01")
			|| processType.equals("02")) {
		tTransferData.setNameAndValue("AUTOFLAG", "2"); //分步执行
	} else if (processType.equals("03")) {
		tTransferData.setNameAndValue("AUTOFLAG", "1"); //顺序全部执行
	}
	tVData.add(tTransferData);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

		if (processType.equals("00")) {
			if (!uiBusinessDelegate.submitData(tVData, "",
					"RITaskApplyBL")) {
				if (uiBusinessDelegate.getCErrors() != null
						&& uiBusinessDelegate.getCErrors()
								.getErrorCount() > 0) {
					Content = ""+"数据处理错误，原因是:"+" "
							+ uiBusinessDelegate.getCErrors()
									.getFirstError();
					FlagStr = "Fail";
				}
			}
		} else {
			if (!uiBusinessDelegate
					.submitData(tVData, "", "RIWFManage")) {
				if (uiBusinessDelegate.getCErrors() != null
						&& uiBusinessDelegate.getCErrors()
								.getErrorCount() > 0) {
					Content = ""+"数据处理错误，原因是:"+" "
							+ uiBusinessDelegate.getCErrors()
									.getFirstError();
					FlagStr = "Fail";
				}
			}
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if ("".equals(FlagStr)) {
			tError = uiBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
				Content = ""+"数据处理成功"+"";
				FlagStr = "Succ";
			} else {
				Content = ""+"数据处理错误，原因是:"+" " + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

