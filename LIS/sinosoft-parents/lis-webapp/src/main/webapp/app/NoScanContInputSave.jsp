<%@page import="com.sinosoft.workflowengine.WorkFlowUI"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：NoScanContInputSave.jsp
	//程序功能：个单新契约无扫描件保单录入
	//创建日期：2002-06-19 11:10:36
	//创建人  ：HYQ
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.workflow.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "Succ";
	String Content = "";
	String tFlag = request.getParameter("tFlag");
	String ContType = request.getParameter("ContType");
	if (tFlag.equals("1")) {
		if (ContType == null || ContType.equals("")) {
			ContType = "TB01";
		}
	} else
		ContType = "TB05";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		loggerDebug("NoScanContInputSave", "session has expired");
		return;
	}
	//prepare data for workflow
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("PrtNo",
			request.getParameter("PrtNo"));
	tTransferData.setNameAndValue("ManageCom",
			request.getParameter("ManageCom"));
	tTransferData.setNameAndValue("InputDate", PubFun.getCurrentDate());
	tTransferData.setNameAndValue("Operator", tG.Operator);
	tTransferData.setNameAndValue("DefaultOperator", tG.Operator);
	tTransferData.setNameAndValue("SubType", ContType);

	try {
		if (tFlag.equals("2")) {
			//对于团单的第一个节点的生成（无扫描录入）
			//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
			String busiName = "tbgrpGrpTbWorkFlowUI";
			BusinessDelegate tBusinessDelegate = BusinessDelegate
					.getBusinessDelegate();
			tVData.add(tTransferData);
			tVData.add(tG);
			if (tBusinessDelegate.submitData(tVData, "7699999999",
					busiName) == false) {
				int n = tBusinessDelegate.getCErrors().getErrorCount();
				loggerDebug("NoScanContInputSave", "n==" + n);
				for (int j = 0; j < n; j++)
					loggerDebug("NoScanContInputSave", "Error: "
							+ tBusinessDelegate.getCErrors()
									.getError(j).errorMessage);
				Content = " 投保单申请失败，原因是: "
						+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr != "Fail") {
				tError = tBusinessDelegate.getCErrors();
				//tErrors = tTbWorkFlowUI.mErrors;
				Content = " 投保单申请成功! ";
				FlagStr = "Succ";
				if (!tError.needDealError()) {
					int n = tError.getErrorCount();
					if (n > 0) {
						for (int j = 0; j < n; j++) {
							//tError = tErrors.getError(j);
							Content = Content.trim()
									+ j
									+ ". "
									+ tError.getError(j).errorMessage
											.trim() + ".";
						}
					}
					FlagStr = "Succ";
				} else {
					int n = tError.getErrorCount();
					if (n > 0) {
						for (int j = 0; j < n; j++) {
							//tError = tErrors.getError(j);
							Content = Content.trim()
									+ j
									+ ". "
									+ tError.getError(j).errorMessage
											.trim() + ".";
						}
					}
					FlagStr = "Fail";
				}
			}
		} else if (tFlag.equals("1") || tFlag.equals("3")) {
			/***************************SYY*******************************************/
			String busiName = "WorkFlowUI";
			BusinessDelegate tBusinessDelegate = BusinessDelegate
					.getBusinessDelegate();
			tTransferData.setNameAndValue("BusiType", "1001");
			tVData.add(tTransferData);
			tVData.add(tG);
			if (tBusinessDelegate
					.submitData(tVData, "create", busiName)) {
				FlagStr = "Succ";
			} else {
				FlagStr = "Fail";
/* 				System.out.println("tWorkFlowUI:"
						+ tBusinessDelegate.getCErrors()
								.getErrorCount()); */
				loggerDebug("NoScanContInputSave.jsp","tWorkFlowUI:"+String.valueOf(tBusinessDelegate.getCErrors()
						.getErrorCount()));
			}
			/*************************************************************************/
		} else if (tFlag.equals("5")) {
			//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
			String busiName = "tbTbWorkFlowUI";
			BusinessDelegate tBusinessDelegate = BusinessDelegate
					.getBusinessDelegate();
			tVData.add(tTransferData);
			tVData.add(tG);
			if (tBusinessDelegate.submitData(tVData, "7099999999",
					busiName) == false) {
				int n = tBusinessDelegate.getCErrors().getErrorCount();
				loggerDebug("NoScanContInputSave", "n==" + n);
				for (int j = 0; j < n; j++)
					loggerDebug("NoScanContInputSave", "Error: "
							+ tBusinessDelegate.getCErrors()
									.getError(j).errorMessage);
				Content = " 投保单申请失败，原因是: "
						+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr != "Fail") {
				tError = tBusinessDelegate.getCErrors();
				//tErrors = tTbWorkFlowUI.mErrors;
				Content = " 投保单申请成功! ";
				FlagStr = "Succ";
				if (!tError.needDealError()) {
					int n = tError.getErrorCount();
					if (n > 0) {
						for (int j = 0; j < n; j++) {
							//tError = tErrors.getError(j);
							Content = Content.trim()
									+ j
									+ ". "
									+ tError.getError(j).errorMessage
											.trim() + ".";
						}
					}

					FlagStr = "Succ";
				} else {
					int n = tError.getErrorCount();
					if (n > 0) {
						for (int j = 0; j < n; j++) {
							//tError = tErrors.getError(j);
							Content = Content.trim()
									+ j
									+ ". "
									+ tError.getError(j).errorMessage
											.trim() + ".";
						}
					}
					FlagStr = "Fail";
				}
			}
		}

	} catch (Exception ex) {
		ex.printStackTrace();
		Content = Content.trim() + " 提示:异常退出.";
	}
%>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
