
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：LLUWSpecChk.jsp
	//程序功能：二核特约承保
	//创建日期：2005-11-04 
	//创建人  ：张星
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.claimnb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.workflowengine.*"%>
<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";

	boolean flag = true;
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		out.println("session has expired");
		return;
	}

	//接收信息
	String tContNo = request.getParameter("ContNo");
	String tCaseNo = request.getParameter("CaseNo");
	String tBatNo = request.getParameter("BatNo");
	String tInEffectFlag = request.getParameter("InEffectFlag");
	String tOperator = request.getParameter("Operator");

	if (tOperator.equals("AddFeeCancel")) {
		tInEffectFlag = "X";
	}

	if (tOperator.equals("1")) {
		tOperator = "";
	}

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("InEffectFlag", tInEffectFlag);
	tTransferData.setNameAndValue("ClmNo", tCaseNo);
	tTransferData.setNameAndValue("BatNo", tBatNo);
	tTransferData.setNameAndValue("ContNo", tContNo);

	try {
		if (flag == true) {
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);

			// 数据传输
//			LLUWResultCvalidateUI tLLUWResultCvalidateUI = new LLUWResultCvalidateUI();
//			if (!tLLUWResultCvalidateUI.submitData(tVData, tOperator)) {
//				int n = tLLUWResultCvalidateUI.mErrors.getErrorCount();
//				Content = " 提示信息，原因是: " + tLLUWResultCvalidateUI.mErrors.getError(0).errorMessage;
//				FlagStr = "Fail";
//			}
		String busiName="LLUWResultCvalidateUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,tOperator,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "提示信息，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "提示信息";
						   FlagStr = "Fail";				
				}
		   }


			if (FlagStr == "Fail") {
				//tError = tLLUWResultCvalidateUI.mErrors;
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError()) {
					Content = " 生效操作成功! ";
					FlagStr = "Succ";
				} else {
					FlagStr = "Fail";
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		Content = Content.trim() + "提示：异常终止!";
	}
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

