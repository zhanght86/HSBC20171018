
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�LLUWSpecChk.jsp
	//�����ܣ�������Լ�б�
	//�������ڣ�2005-11-04 
	//������  ������
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.claimnb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.workflowengine.*"%>
<%
	//�������
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

	//������Ϣ
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
			// ׼���������� VData
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);

			// ���ݴ���
//			LLUWResultCvalidateUI tLLUWResultCvalidateUI = new LLUWResultCvalidateUI();
//			if (!tLLUWResultCvalidateUI.submitData(tVData, tOperator)) {
//				int n = tLLUWResultCvalidateUI.mErrors.getErrorCount();
//				Content = " ��ʾ��Ϣ��ԭ����: " + tLLUWResultCvalidateUI.mErrors.getError(0).errorMessage;
//				FlagStr = "Fail";
//			}
		String busiName="LLUWResultCvalidateUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,tOperator,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "��ʾ��Ϣ��ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "��ʾ��Ϣ";
						   FlagStr = "Fail";				
				}
		   }


			if (FlagStr == "Fail") {
				//tError = tLLUWResultCvalidateUI.mErrors;
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError()) {
					Content = " ��Ч�����ɹ�! ";
					FlagStr = "Succ";
				} else {
					FlagStr = "Fail";
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		Content = Content.trim() + "��ʾ���쳣��ֹ!";
	}
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

