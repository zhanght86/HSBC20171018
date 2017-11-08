
<%
	//程序名称：CertifySendOutSave.jsp
	//程序功能：
	//创建日期：2002-09-23
	//创建人  ：周平
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<SCRIPT src="./CQueryValueOperate.js"></SCRIPT>
<SCRIPT src="IndiDunFeeInput.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="java.util.*"%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//输出参数
	String strTakeBackNo = "";
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		// 单证信息部分
		String szSendOutCom = request.getParameter("SendOutCom");
		String szReceiveCom = request.getParameter("ReceiveCom");
		String szHandler = request.getParameter("Handler");
		String szHandleDate = request.getParameter("HandleDate");
		String szReason = request.getParameter("Reason");
		loggerDebug("CertifySendOutApplySave","szSendOutCom= " + szSendOutCom);
		loggerDebug("CertifySendOutApplySave","szReceiveCom= " + szReceiveCom);

		String szNo[] = request.getParameterValues("CertifyListNo");
		String szCertifyCode[] = request
		.getParameterValues("CertifyList1");
		String szStartNo[] = request.getParameterValues("CertifyList5");
		String szEndNo[] = request.getParameterValues("CertifyList6");
		String szSumCount[] = request
		.getParameterValues("CertifyList7");

		LZCardAppSet tLZCardAppSet = new LZCardAppSet();
		for (int nIndex = 0; nIndex < szNo.length; nIndex++) {
			LZCardAppSchema tLZCardAppschema = new LZCardAppSchema();

			tLZCardAppschema.setCertifyCode(szCertifyCode[nIndex]);

			tLZCardAppschema.setStartNo(szStartNo[nIndex]);
			tLZCardAppschema.setEndNo(szEndNo[nIndex]);
			tLZCardAppschema.setSumCount(szSumCount[nIndex]);

			tLZCardAppschema.setSendOutCom(szSendOutCom);
			tLZCardAppschema.setReceiveCom(szReceiveCom);

			tLZCardAppschema.setHandler(szHandler);
			tLZCardAppschema.setHandleDate(szHandleDate);
			tLZCardAppschema.setReason(szReason);

			tLZCardAppSet.add(tLZCardAppschema);
		}

		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardAppSet);

		// 数据传输
		/*CertSendOutApplyUI tCertSendOutApplyUI = new CertSendOutApplyUI();
		if (!tCertSendOutApplyUI.submitData(vData, "APPLY")) { //APPLY:申请
			Content = " 保存失败，原因是: "
			+ tCertSendOutApplyUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " 保存成功. ";
			FlagStr = "Succ";
		}*/
		String busiName="CertSendOutApplyUI";
		String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,"APPLY",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = mDescType+"失败";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = mDescType+"成功! ";
		      	FlagStr = "Succ";  
		  }
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = FlagStr + " 保存失败，原因是:" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
  	<%= szFailSet %>
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%= strTakeBackNo %>");
	</script>
<body>
</body>
</html>
