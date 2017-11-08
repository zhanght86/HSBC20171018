<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CostTypeDefSave.jsp
//程序功能：凭证费用类型定义保存页面
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
  FICostTypeDefSchema tFICostTypeDefSchema = new FICostTypeDefSchema();
  //FICostTypeDefUI tFICostTypeDef = new FICostTypeDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mCostID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFICostTypeDefSchema.setVersionNo(request.getParameter("VersionNo"));
	tFICostTypeDefSchema.setCertificateID(request.getParameter("CertificateID"));  
  tFICostTypeDefSchema.setCostID(request.getParameter("CostID"));
  tFICostTypeDefSchema.setCostName(request.getParameter("CostName"));
  tFICostTypeDefSchema.setFinItemType(request.getParameter("FinItemType"));
  tFICostTypeDefSchema.setFinItemID(request.getParameter("FinItemID"));  
  tFICostTypeDefSchema.setFinItemName(request.getParameter("FinItemName"));
  tFICostTypeDefSchema.setRemark(request.getParameter("Remark"));
  tFICostTypeDefSchema.setState(request.getParameter("State"));

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFICostTypeDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("CostTypeDefSave","come========== in"+tFICostTypeDefSchema.getVersionNo());
    loggerDebug("CostTypeDefSave","come========== in"+tFICostTypeDefSchema.getCertificateID());  
    loggerDebug("CostTypeDefSave","come========== in"+tFICostTypeDefSchema.getCostID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FICostTypeDefUI");
    loggerDebug("CostTypeDefSave","come out" + tOperate);
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
		mCostID = (String)sTransferData.getValueByName("String");
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

