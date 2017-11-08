<%
 loggerDebug("PayPlanSave","Start PayPlan JSP Submit...1");
//程序名称：PayPlanInput.jsp
//程序功能：
//创建日期：2002-07-24 08:38:44
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //接收信息，并作校验处理。
  //输入参数
  //输出参数
  CErrors tError = null;
  String tBmCert = "";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String Count = "";
  loggerDebug("PayPlanSave","Start PayPlan JSP Submit...");
 
  GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
	
 
  LCPolSchema tLCPolSchema=new LCPolSchema();
  tLCPolSchema.setContNo(request.getParameter("ContNo"));
  tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
  tLCPolSchema.setAppntNo(request.getParameter("AppntNo"));
  tLCPolSchema.setInsuredNo(request.getParameter("InsuredNo"));
  
  LCGetSchema tLCGetSchema=new LCGetSchema();
  tLCGetSchema.setContNo(request.getParameter("ContNo"));
  tLCGetSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCGetSchema.setManageCom(request.getParameter("ManageCom"));
  //tLCGetSchema.setAppntNo(request.getParameter("AppntNo"));
  tLCGetSchema.setInsuredNo(request.getParameter("InsuredNo"));
  
  TransferData aTransferData = new TransferData();
  //aTransferData.setNameAndValue("timeStart",request.getParameter("timeStart"));
  aTransferData.setNameAndValue("timeEnd",request.getParameter("timeEnd"));
  
 
  
 // PayPlanUI tPayPlanUI = new PayPlanUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  VData tVData = new VData();
  tVData.addElement(tGlobalInput) ;
	tVData.addElement(aTransferData);
	tVData.addElement(tLCPolSchema);
	tVData.addElement(tLCGetSchema);
	
  loggerDebug("PayPlanSave","Start PayPlan JSP Submit...4");
  try
  {	  
    //tPayPlanUI.submitData(tVData,"INSERT||PERSON");
    tBusinessDelegate.submitData(tVData,"INSERT||PERSON","PayPlanUI")
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    //tError = tPayPlanUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
   
      Content = " 保存成功";
      loggerDebug("PayPlanSave","------111---");
      // if (tPayPlanUI.getResult()!=null&&tPayPlanUI.getResult().size()>0)
      //{      
			//	loggerDebug("PayPlanSave","------SerialNo:"+tPayPlanUI.getResult().get(0));
      //  FlagStr = (String)tPayPlanUI.getResult().get(0);
      //  Count = (String)tPayPlanUI.getResult().get(1);
      //}
       if (tBusinessDelegate.getResult()!=null&&tBusinessDelegate.getResult().size()>0)
      {      
				loggerDebug("PayPlanSave","------SerialNo:"+tBusinessDelegate.getResult().get(0));
        FlagStr = (String)tBusinessDelegate.getResult().get(0);
        Count = (String)tBusinessDelegate.getResult().get(1);
      }
      else
      {
     	   Content = "催付失败：已经产生催付记录！";
     	   FlagStr = "Fail";
      }
  
    }
    else
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //添加各种预处理
 
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Count%>");
</script>
</html>

