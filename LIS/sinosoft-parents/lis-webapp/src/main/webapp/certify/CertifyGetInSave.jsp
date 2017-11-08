
<%
	//程序名称：单证入库,CertifyGetInSave.jsp
	//程序功能：单证印刷后、单证发放到下级机构时，需要接收机构做单证入库操作
	//创建日期：2009-01-04
	//创建人  ：mw
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
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	//输出参数
	String strTakeBackNo = "";
	String FlagStr = "Fail";
	String Content = "";
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	LZCardSet setLZCard = new LZCardSet();
	LZCardPrintSet setLZCardPrint = new LZCardPrintSet();

	try {
		// 单证信息部分
		String szHandler = request.getParameter("Operator");
		String szHandleDate = request.getParameter("OperateDate");
		
		// 设置操作字符串，INSERT为确认入库，CANCEL为拒绝入库
		String szOperator = request.getParameter("operateFlag");

		String tChk[] = request.getParameterValues("InpCertifyListGridChk");
		String szCertifyCode[] = request.getParameterValues("CertifyListGrid1");
		String szSendOutCom[] = request.getParameterValues("CertifyListGrid3");
		String szReceiveCom[] = request.getParameterValues("CertifyListGrid4");
		String szStartNo[] = request.getParameterValues("CertifyListGrid5");
		String szEndNo[] = request.getParameterValues("CertifyListGrid6");
		String szSumCount[] = request.getParameterValues("CertifyListGrid7");
		String szSourceFlag[] = request.getParameterValues("CertifyListGrid8");
		String szPrtNo[] = request.getParameterValues("CertifyListGrid9");
		String szReason[] = request.getParameterValues("CertifyListGrid10");

		loggerDebug("CertifyGetInSave","tChk.length=" + tChk.length);
		for (int i = 0; i < tChk.length; i++) {
			//loggerDebug("CertifyGetInSave",i);
			if ("1".equals(tChk[i])) {
		LZCardSchema schemaLZCard = new LZCardSchema();
		schemaLZCard.setCertifyCode(szCertifyCode[i]);
		schemaLZCard.setSendOutCom(szSendOutCom[i]);
		schemaLZCard.setReceiveCom(szReceiveCom[i]);
		schemaLZCard.setStartNo(szStartNo[i]);
		schemaLZCard.setEndNo(szEndNo[i]);
		schemaLZCard.setSumCount(szSumCount[i]);
		if ("CANCEL".equals(szOperator)) {
			schemaLZCard.setReason(szReason[i]);
		}
		schemaLZCard.setHandler(szHandler);
		schemaLZCard.setHandleDate(szHandleDate);
		setLZCard.add(schemaLZCard);
		loggerDebug("CertifyGetInSave","szCertifyCode=" + szCertifyCode[i]);
		loggerDebug("CertifyGetInSave","szSendOutCom=" + szSendOutCom[i]);
		loggerDebug("CertifyGetInSave","szReceiveCom=" + szReceiveCom[i]);
		loggerDebug("CertifyGetInSave","szStartNo=" + szStartNo[i]);
		loggerDebug("CertifyGetInSave","szEndNo=" + szEndNo[i]);
		loggerDebug("CertifyGetInSave","szSumCount=" + szSumCount[i]);
		loggerDebug("CertifyGetInSave","szHandler=" + szHandler);
		loggerDebug("CertifyGetInSave","szHandleDate=" + szHandleDate);

		//此处setLZCardPrint和setLZCard中Schema个数保持一致，如果为发放入库PrtNo其实是空的
		LZCardPrintSchema schemaLZCardPrint = new LZCardPrintSchema();
		schemaLZCardPrint.setPrtNo(szPrtNo[i]);
		setLZCardPrint.add(schemaLZCardPrint);
			}
		}

		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);
		vData.addElement(setLZCardPrint);

		// 数据传输
		/*CertGetInUI tCertGetInUI = new CertGetInUI();
		if (!tCertGetInUI.submitData(vData, szOperator)) {
			Content = "保存失败，原因是：" + tCertGetInUI.mErrors.getFirstError();
			FlagStr = "Fail";

			vData = tCertGetInUI.getResult();
			strTakeBackNo = (String) vData.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
		} else {
			Content = " 保存成功 ";
			FlagStr = "Succ";

			vData = tCertGetInUI.getResult();
			strTakeBackNo = (String) vData.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
		}*/
		String busiName="CertGetInUI";
		String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,szOperator,busiName))
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
		  vData = tBusinessDelegate.getResult();
			strTakeBackNo = (String) vData.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = "保存失败，原因是:" + ex.getMessage();
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
