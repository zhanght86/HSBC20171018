<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.lis.otof.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>

<%
		GlobalInput tG1 = new GlobalInput();
		tG1 = (GlobalInput)session.getValue("GI");
		GlobalInput tG = new GlobalInput();
		tG.setSchema(tG1);
		
	  String FlagStr = "";
	  String Content = "";
	  String tStatYear = request.getParameter("StatYear");
	  String tStatMon = request.getParameter("StatMon");
	  String tOpt = request.getParameter("Opt");
	  String tVouchNo = request.getParameter("VouchNo");
	  String tManageCom = request.getParameter("ManageCom");

		loggerDebug("OtoFLASave","��ʼ��ȡƾ֤");
		String tFlag = "0"; //�ֹ���ȡ

		VData vData = new VData();
	  OtoFLABL tOtoFLABL = new OtoFLABL();

		Integer itemp = new Integer(tVouchNo) ;

		vData = new VData();
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("mStatYear",tStatYear);
		mTransferData.setNameAndValue("mStatMon",tStatMon);
		mTransferData.setNameAndValue("itemp",itemp);
		mTransferData.setNameAndValue("DateFlag",tFlag);
		mTransferData.setNameAndValue("mInputDate","");
		mTransferData.setNameAndValue("cManageCom",tManageCom);			
		vData.addElement(tG);
		vData.add(mTransferData);

		loggerDebug("OtoFLASave","�������");
  	if( !tOtoFLABL.submitData(vData, "PRINT") )
  	{
				FlagStr = "Fail";
				Content = "����ʧ�ܣ�ԭ���ǣ�" + tOtoFLABL.mErrors.getFirstError();
  	}
  	else
  	{
    		FlagStr = "True";
    		Content = "�����ɹ���";
		}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

