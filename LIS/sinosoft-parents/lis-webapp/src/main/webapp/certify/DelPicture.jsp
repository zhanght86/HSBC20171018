<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EsDocManageSave.jsp
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getValue("GI");
String tContno = request.getParameter("CertifyNo");
TransferData tTransferData=new TransferData();
tTransferData.setNameAndValue("ContNo",tContno);
loggerDebug("DelPicture","--ContNo:"+tContno);
//接收信息，并作校验处理。
  //DELPictureUI tDELPictureUI = new DELPictureUI();

  //输出参数
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";

  
  //try
  {
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tTransferData);
    tVData.add(tG);

    loggerDebug("DelPicture","--DELPicture.jsp--before submitData");
    //tDELPictureUI.submitData(tVData,transact);
    String busiName="DELPictureUI";
    String mDescType="保存";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"失败";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
	     	Content = mDescType+"成功! ";
	      	FlagStr = "Succ";  
	  }
    loggerDebug("DelPicture","--DELPicture.jsp--after  submitData");
  /*}
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tDELPictureUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " 保存成功! ";
      FlagStr = "Success";
    }
    else
    {
      Content = " 保存失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }*/  

  //添加各种预处理

%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
