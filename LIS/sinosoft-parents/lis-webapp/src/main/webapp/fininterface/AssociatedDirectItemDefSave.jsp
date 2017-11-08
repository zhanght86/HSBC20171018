 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AssociatedDirectItemDefSave.jsp
//程序功能：科目专项定义保存页面
//创建日期：2008-09-16
//创建人  ：FanXin
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
  FIAssociatedDirectItemDefSchema tFIAssociatedDirectItemDefSchema = new FIAssociatedDirectItemDefSchema();
  //FIAssociatedDirectItemDefUI tFIAssociatedDirectItemDef = new FIAssociatedDirectItemDefUI();
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
  tFIAssociatedDirectItemDefSchema.setVersionNo(request.getParameter("VersionNo"));
  tFIAssociatedDirectItemDefSchema.setColumnID(request.getParameter("ColumnID"));
  tFIAssociatedDirectItemDefSchema.setSourceTableID(request.getParameter("SourceTableID"));
  tFIAssociatedDirectItemDefSchema.setSourceColumnID(request.getParameter("SourceColumnID"));  
  tFIAssociatedDirectItemDefSchema.setReMark(request.getParameter("ReMark"));  

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIAssociatedDirectItemDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("AssociatedDirectItemDefSave","come========== in"+tFIAssociatedDirectItemDefSchema.getVersionNo());    
    loggerDebug("AssociatedDirectItemDefSave","come========== in"+tFIAssociatedDirectItemDefSchema.getColumnID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FIAssociatedDirectItemDefUI");
    loggerDebug("AssociatedDirectItemDefSave","come out" + tOperate);
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

