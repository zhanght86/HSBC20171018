
<%
	//�������ƣ�IndiFinVerifyUrgeGetByPolNo.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>

<%
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput) session.getValue("GI"); //�μ�loginSubmit.jsp
	// �������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	int recordCount = 0;

	String ContNo = request.getParameter("ContNo");
	
	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	tLJTempFeeSchema.setOtherNo(ContNo);
	
	VData tVData = new VData();
	tVData.add(tLJTempFeeSchema);
	tVData.add(tGI);

	/*IndiFinUrgeVerifyUI tIndiFinUrgeVerifyUI = new IndiFinUrgeVerifyUI();
	tIndiFinUrgeVerifyUI.submitData(tVData, "VERIFY");
	tError = tIndiFinUrgeVerifyUI.mErrors;
	if (!tError.needDealError()) {
		Content = " ��������ɹ�";
		FlagStr = "Succ";
	} else {
		Content = " ��������ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}*/
	String busiName="IndiFinUrgeVerifyUI";
	  String mDescType="��������";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"VERIFY",busiName))
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

	Content.replace('\"', '$');
	System.out.println("Content:" + Content);
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>
