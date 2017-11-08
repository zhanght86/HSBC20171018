<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CertificateTypeDefSave.jsp
//程序功能：凭证类型定义保存页面
//创建日期：2008-08-12
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //接收信息，并作校验处理。
  //输入参数
  FICertificateTypeDefSchema tFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
  //FICertificateTypeDefUI tFICertificateTypeDef = new FICertificateTypeDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mCertificateID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFICertificateTypeDefSchema.setVersionNo(request.getParameter("VersionNo"));
  tFICertificateTypeDefSchema.setCertificateID(request.getParameter("CertificateID"));
  tFICertificateTypeDefSchema.setCertificateName(request.getParameter("CertificateName"));
  tFICertificateTypeDefSchema.setDetailIndexID(request.getParameter("DetailIndexID"));
  tFICertificateTypeDefSchema.setDetailIndexName(request.getParameter("DetailIndexName"));
  tFICertificateTypeDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  tFICertificateTypeDefSchema.setRemark(request.getParameter("Remark"));

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFICertificateTypeDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("CertificateTypeDefSave","come========== in"+tFICertificateTypeDefSchema.getVersionNo());  
    loggerDebug("CertificateTypeDefSave","come========== in"+tFICertificateTypeDefSchema.getCertificateID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FICertificateTypeDefUI");
    loggerDebug("CertificateTypeDefSave","come out" + tOperate);
  }
  catch(Exception ex)
  {
    Content = "操作失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  if (!FlagStr.equals("Fail"))
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		mCertificateID = (String)sTransferData.getValueByName("String");
    	Content = " 操作已成功! ";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = " 操作失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }

  //添加各种预处理

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

