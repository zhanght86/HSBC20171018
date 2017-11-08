<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*"%>
  <%@page import="com.sinosoft.service.*" %>    
<%
 //PDRiskCopyBL tPDRiskCopyBL = new PDRiskCopyBL();
 
 CErrors tError = null;
 
 String FlagStr = "";
 String Content = "";
 
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");

 TransferData transferData = new TransferData();
 transferData.setNameAndValue("NewRiskCode",request.getParameter("RiskCode"));
 transferData.setNameAndValue("DpyRiskCode",request.getParameter("RiskCode2"));
 
 try
 {
  	// 准备传输数据 VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  	//tPDRiskCopyBL.submitData(tVData,"");
  	
String busiName="PDRiskCopyBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, "",busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"处理成功!"+" ";
    	FlagStr = "Success";
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"保存失败，原因是:"+"" + ex.toString();
    FlagStr = "Fail";
   }

  	/*
 }
 catch(Exception ex)
 {
  	Content = "保存失败，原因是:" + ex.toString();
  	FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  	tError = tPDRiskCopyBL.mErrors;
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
 }
*/
%>                      

<html>
<script type="text/javascript">
 	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

