
<%
	//程序名称：CertifySendOutConfirmSave.jsp
	//程序功能：
	//创建日期：2002-09-23
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
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		String szLimitFlag = "NO";
		String tChk[] = request.getParameterValues("InpCertifyListChk");
		String tApplyNo[] = request.getParameterValues("CertifyList1");
		String tCertifyCode[] = request
		.getParameterValues("CertifyList2");

		String tSendOutCom[] = request
		.getParameterValues("CertifyList4");
		String tReceiveCom[] = request
		.getParameterValues("CertifyList6");

		String tStartNo[] = request.getParameterValues("CertifyList8");
		String tEndNo[] = request.getParameterValues("CertifyList9");
		String tSumCount[] = request
		.getParameterValues("CertifyList10");

		String tHandler = request.getParameter("Handler");
		String tHandleDate = request.getParameter("HandleDate");

		loggerDebug("CertifySendOutConfirmSave","tChk.length=" + tChk.length);

		LZCardSet tLZCardSet = new LZCardSet();
		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardSchema tLZCardschema = new LZCardSchema();
		tLZCardschema.setApplyNo(tApplyNo[i]);

		tLZCardschema.setCertifyCode(tCertifyCode[i]);
		tLZCardschema.setStartNo(tStartNo[i]);
		tLZCardschema.setEndNo(tEndNo[i]);
		tLZCardschema.setSumCount(tSumCount[i]);

		tLZCardschema.setSendOutCom(tSendOutCom[i]);
		tLZCardschema.setReceiveCom(tReceiveCom[i]);

		tLZCardschema.setHandler(tHandler);
		tLZCardschema.setHandleDate(tHandleDate);
		tLZCardSet.add(tLZCardschema);
		loggerDebug("CertifySendOutConfirmSave","CertifySendOutConfirmSave.jsp传入数据：");
		loggerDebug("CertifySendOutConfirmSave","增领申请号码=" + tApplyNo[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证编码=" + tCertifyCode[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证起号=" + tStartNo[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证止号=" + tEndNo[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证数量=" + tSumCount[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证发放者=" + tSendOutCom[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证接收者=" + tReceiveCom[i]);
		loggerDebug("CertifySendOutConfirmSave","增领单证经办人=" + tHandler);
		loggerDebug("CertifySendOutConfirmSave","增领单证经办日期=" + tHandleDate);
			}
		}

		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardSet);
		vData.addElement(szLimitFlag);

		// 设置操作字符串
		String szOperator = "ADD";

		// 数据传输
		/*CertSendOutUI tCertSendOutUI = new CertSendOutUI();
		if (!tCertSendOutUI.submitData(vData, szOperator)) {
			Content = " 保存失败，原因是: "
			+ tCertSendOutUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " 保存成功 ";
			FlagStr = "Succ";
		}*/
		
		String busiName="CertSendOutUI";
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
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = FlagStr + " 保存失败，原因是:" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
  	<%= szFailSet %>
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
	</script>
<body>
</body>
</html>
