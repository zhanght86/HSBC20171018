
<%
	//�������ƣ�CertifyDestroyConfirmSave.jsp
	//�����ܣ�
	//�������ڣ�2002-09-23
	//������  ��mw
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
		String tChk[] = request.getParameterValues("InpCertifyListChk");
		String tApplyNo[] = request.getParameterValues("CertifyList1");
		String tStartNo[] = request.getParameterValues("CertifyList6");
		String tEndNo[] = request.getParameterValues("CertifyList7");
		
		String tOperator = request.getParameter("Operator");
		String tOperateDate = request.getParameter("OperateDate");
		
		loggerDebug("CertifyDestroyConfirmSave","tChk.length=" + tChk.length);

		LZCardAppSet tLZCardAppSet = new LZCardAppSet();
		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardAppSchema tLZCardAppSchema = new LZCardAppSchema();
		tLZCardAppSchema.setApplyNo(tApplyNo[i]);
		tLZCardAppSchema.setReplyPerson(tOperator);
		tLZCardAppSchema.setReplyDate(tOperateDate);
		tLZCardAppSchema.setStartNo(tStartNo[i]);
		tLZCardAppSchema.setEndNo(tEndNo[i]);
		
		tLZCardAppSet.add(tLZCardAppSchema);
		
		loggerDebug("CertifyDestroyConfirmSave","CertifyDestroyConfirmSave.jsp�������ݣ�");
		loggerDebug("CertifyDestroyConfirmSave","��ʧȷ���������=" + tApplyNo[i]);
			}
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardAppSet);

		// ���ò����ַ���
		String szOperator = "DESTROY";

		// ���ݴ���
		/*CertDestroyConfirmUI tCertDestroyConfirmUI = new CertDestroyConfirmUI();
		if (!tCertDestroyConfirmUI.submitData(vData, szOperator)) {
			Content = " ����ʧ�ܣ�ԭ����: "
			+ tCertDestroyConfirmUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " ����ɹ� ";
			FlagStr = "Succ";
		}*/
		
		String busiName="CertDestroyConfirmUI";
		  String mDescType="����";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(vData,szOperator,busiName))
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
			      	FlagStr = "Success!";  
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
	</script>
<body>
</body>
</html>
