<%
//程序名称：
//程序功能：分红险系数计算
//创建日期：2008-11-09 17:55:57
//创建人  ：彭送庭

%>
<!--用户校验类-->
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.get.*"%>
 <%@page import="com.sinosoft.service.*" %>
 
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

  String busiName="getBonusCountManuBL";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  CErrors tError = null;
               
  String FlagStr = "";
  String Content = "";
  GlobalInput tG = new GlobalInput();      
  tG = (GlobalInput)session.getValue("GI");
  String tFiscalYear=request.getParameter("FiscalYear");
  TransferData tTransferData = new TransferData();
tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
  VData tVData = new VData();   
  tVData.addElement(tTransferData);
  tVData.addElement(tG);
  
	  try
	  {	
		  tBusinessDelegate.submitData(tVData,"",busiName);
	  }
	  catch(Exception ex)
	  {
	    Content = "计算失败失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  }   
	  //如果在Catch中发现异常，则不从错误类中提取错误信息
	  if (FlagStr=="")
	  {
	    tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
	     Content="计算成功！"+tBusinessDelegate.getResult().getObjectByObjectName("String",0);
	     FlagStr = "Succ";
	    }
	    else                                                                           
	    {
	    	Content = " 计算失败，原因是:" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	  }
 %>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

