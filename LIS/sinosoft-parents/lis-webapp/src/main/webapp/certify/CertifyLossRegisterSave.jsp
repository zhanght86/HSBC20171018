
<%
	//�������ƣ�CertifyLossRegisterSave.jsp
	//�����ܣ���֤��ʧ
	//�������ڣ�2002-09-23
	//������  ����ƽ
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
	//�������
	String strTakeBackNo = "";
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		// ��֤��Ϣ����
		String szHandler = request.getParameter("Handler");
		String szHandleDate = request.getParameter("HandleDate");
		String szReason = request.getParameter("Reason");

		String szNo[] = request.getParameterValues("CertifyListNo");
		String szCertifyCode[] = request
		.getParameterValues("CertifyList1");
		String szStartNo[] = request.getParameterValues("CertifyList3");
		String szEndNo[] = request.getParameterValues("CertifyList4");
		String szSumCount[] = request
		.getParameterValues("CertifyList5");

		LZCardAppSet tLZCardAppSet = new LZCardAppSet();
		for (int nIndex = 0; nIndex < szNo.length; nIndex++) {
			LZCardAppSchema tLZCardAppschema = new LZCardAppSchema();

			tLZCardAppschema.setCertifyCode(szCertifyCode[nIndex]);

			tLZCardAppschema.setStartNo(szStartNo[nIndex]);
			tLZCardAppschema.setEndNo(szEndNo[nIndex]);
			tLZCardAppschema.setSumCount(szSumCount[nIndex]);

			tLZCardAppschema.setHandler(szHandler);
			tLZCardAppschema.setHandleDate(szHandleDate);
			tLZCardAppschema.setReason(szReason);

			tLZCardAppSet.add(tLZCardAppschema);
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardAppSet);

		// ���ݴ���
		/*CertLossRegisterUI tCertLossRegisterUI = new CertLossRegisterUI();
		if (!tCertLossRegisterUI.submitData(vData, "APPLY")) { //APPLY:��ʧ
			Content = " ����ʧ�ܣ�ԭ����: "
			+ tCertLossRegisterUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " ����ɹ�. ";
			FlagStr = "Succ";
		}*/
		String busiName="CertLossRegisterUI";
		  String mDescType="����";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(vData,"APPLY",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"ʧ��";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"�ɹ�! ";
			      	FlagStr = "Succ";  
			  }
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = FlagStr + " ����ʧ�ܣ�ԭ����:" + ex.getMessage();
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
