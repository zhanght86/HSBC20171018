<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�LRBsnsBillSave.jsp
	//�����ܣ�
	//�������ڣ�2007-02-28
	//������  ��zhangbin
	//���¼�¼��  ������: zhangbin ��������  2008-4-14   ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	GlobalInput tG = new GlobalInput();
	tG.setSchema((GlobalInput) session.getAttribute("GI"));
	CErrors tError = null;

	String FlagStr = "";
	String Content = "";

	String mBillType = request.getParameter("BillType");
	String mStartDate = request.getParameter("StartDate");
	String mEndDate = request.getParameter("EndDate");
	String mRIComCode = request.getParameter("RIComCode");
	String mBillTimes = request.getParameter("BillTimes");

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("BillType", mBillType);
	tTransferData.setNameAndValue("StartDate", mStartDate);
	tTransferData.setNameAndValue("EndDate", mEndDate);
	tTransferData.setNameAndValue("RIComCode", mRIComCode);
	tTransferData.setNameAndValue("BillTimes", mBillTimes);
	
	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(tTransferData);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, "", "RIBsnsBillCalUI")) {
		if (uiBusinessDelegate.getCErrors() != null
				&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+""
					+ uiBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = ""+"����ʧ��"+"";
			FlagStr = "Fail";
		}
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if ("".equals(FlagStr)) {
		tError = uiBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = ""+"����ɹ�"+"";
			FlagStr = "Succ";
		} else {
			Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>