<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AnswerUWReInsureChk.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.reinsure.faculreinsure.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	    //�������
	    CErrors tError = null;
	    String FlagStr = "Fail";
	    String Content = "";
		
		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getAttribute("GI");
		
		String tPolNo = request.getParameter("PolNo"); 
		String tFilePath= request.getParameter("FilePath"); 
		String tFileName= request.getParameter("FileName");    
		String tRemark = request.getParameter("Remark");
		System.out.println("xxxxxxxxxxxxxxxxxxxxx: "+tRemark);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("FilePath",tFilePath);
		tTransferData.setNameAndValue("FileName",tFileName);		
		tTransferData.setNameAndValue("Remark",tRemark);
		System.out.println("PolNo:  "+tPolNo);
		System.out.println("FilePath: "+tFilePath);
			
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG );
		
		// ���ݴ���
		//AnswerUWReInsureUI tAnswerUWReInsureUI = new AnswerUWReInsureUI();
		BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (uiBusinessDelegate.submitData(tVData,"","AnswerUWReInsureUI") == false)
		{
			Content = " "+"����ʧ�ܣ�ԭ����:"+" " + uiBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
        else 
		{                          
		    Content = " "+"����ɹ�!"+" ";
		    FlagStr = "Succ";
		 }
%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
