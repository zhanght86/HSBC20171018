<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：InfoFinItemAssociatedSave.jsp
//程序功能：科目关联专项定义保存页面
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
  FIInfoFinItemAssociatedSchema tFIInfoFinItemAssociatedSchema = new FIInfoFinItemAssociatedSchema();
  //FIInfoFinItemAssociatedUI tFIInfoFinItemAssociated = new FIInfoFinItemAssociatedUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mAssociatedID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFIInfoFinItemAssociatedSchema.setVersionNo(request.getParameter("VersionNo"));
	tFIInfoFinItemAssociatedSchema.setFinItemID(request.getParameter("FinItemID"));  
  tFIInfoFinItemAssociatedSchema.setAssociatedID(request.getParameter("AssociatedID"));
  tFIInfoFinItemAssociatedSchema.setAssociatedName(request.getParameter("AssociatedName"));
  tFIInfoFinItemAssociatedSchema.setReMark(request.getParameter("ReMark"));

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIInfoFinItemAssociatedSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getVersionNo());
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getFinItemID());  
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getAssociatedID());
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getAssociatedName());
    uiBusinessDelegate.submitData(tVData,tOperate,"FIInfoFinItemAssociatedUI");
    loggerDebug("InfoFinItemAssociatedSave","come out" + tOperate);
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
		mAssociatedID = (String)sTransferData.getValueByName("String");
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

