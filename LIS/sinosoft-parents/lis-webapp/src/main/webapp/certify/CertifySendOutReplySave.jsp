
<%
	//�������ƣ�CertifySendOutSave.jsp
	//�����ܣ�
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
		String tNote = request.getParameter("note");
		String tReplyPerson = request.getParameter("ReplyPerson");
		String tReplyDate = request.getParameter("ReplyDate");

		// ���ò����ַ�����INSERTΪȷ����⣬CANCELΪ�ܾ����
		String szOperator = request.getParameter("operateFlag");

		String tChk[] = request.getParameterValues("InpCertifyListChk");
		String tApplyNo[] = request.getParameterValues("CertifyList1");

		loggerDebug("CertifySendOutReplySave","tChk.length=" + tChk.length);

		LZCardAppSet tLZCardAppSet = new LZCardAppSet();
		for (int i = 0; i < tChk.length; i++) {
			loggerDebug("CertifySendOutReplySave",i);
			if ("1".equals(tChk[i])) {
		LZCardAppSchema tLZCardAppschema = new LZCardAppSchema();
		tLZCardAppschema.setApplyNo(tApplyNo[i]);
		tLZCardAppschema.setReplyPerson(tReplyPerson);
		tLZCardAppschema.setReplyDate(tReplyDate);
		tLZCardAppschema.setnote(tNote);
		if (szOperator.equals("true")) {
			tLZCardAppschema.setStateFlag("2"); //2������ȷ��δ����
		} else {
			tLZCardAppschema.setStateFlag("4"); //4�������ܾ�
		}

		tLZCardAppSet.add(tLZCardAppschema);
			}
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardAppSet);

		// ���ݴ���
		/*CertSendOutApplyUI tCertSendOutApplyUI = new CertSendOutApplyUI();

		if (!tCertSendOutApplyUI.submitData(vData, "REPLY")) { //REPLY:����
			Content = " ����ʧ�ܣ�ԭ����: "
			+ tCertSendOutApplyUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " ����ɹ� ";
			FlagStr = "Succ";
		}*/
		String busiName="CertSendOutApplyUI";
		String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,"REPLY",busiName))
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
	</script>
<body>
</body>
</html>
