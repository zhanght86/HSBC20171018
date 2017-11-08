<%
/***************************************************************
 * <p>ProName��LDQuestionSave.jsp</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LDQuestionSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tOtherNoType = request.getParameter("OtherNoType");
			String tOtherNo = request.getParameter("OtherNo");
			String tSubOtherNo = request.getParameter("SubOtherNo");
			String tActivityID = request.getParameter("ActivityID");
			String tQuesID = request.getParameter("QuesID");
			
			String tQuesType = request.getParameter("QuesType");
			String tSendContent = request.getParameter("SendContent");
			String tReplyContent = request.getParameter("ReplyContent");
			String tDetailType = request.getParameter("Mistake");
			//add by dianj ����Ӱ���������֤ϸ��
			String tSubType = request.getParameter("SubType");
			
			LDQuestionSchema tLDQuestionSchema = new LDQuestionSchema();
			
			tLDQuestionSchema.setQuesID(tQuesID);
			tLDQuestionSchema.setOtherNoType(tOtherNoType);
			tLDQuestionSchema.setOtherNo(tOtherNo);
			tLDQuestionSchema.setSubOtherNo(tSubOtherNo);
			tLDQuestionSchema.setQuesType(tQuesType);
			tLDQuestionSchema.setSendNode(tActivityID);
			tLDQuestionSchema.setSendContent(tSendContent);
			tLDQuestionSchema.setReplyContent(tReplyContent);
			tLDQuestionSchema.setDetailType(tDetailType);
			tLDQuestionSchema.setSubOtherNo(tSubType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDQuestionSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LDQuestionUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
