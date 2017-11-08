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

		loggerDebug("OtoFLASave","开始提取凭证");
		String tFlag = "0"; //手工提取

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

		loggerDebug("OtoFLASave","加载完成");
  	if( !tOtoFLABL.submitData(vData, "PRINT") )
  	{
				FlagStr = "Fail";
				Content = "提数失败，原因是：" + tOtoFLABL.mErrors.getFirstError();
  	}
  	else
  	{
    		FlagStr = "True";
    		Content = "提数成功！";
		}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

