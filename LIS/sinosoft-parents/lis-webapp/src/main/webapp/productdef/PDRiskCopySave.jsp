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
  	// ׼���������� VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  	//tPDRiskCopyBL.submitData(tVData,"");
  	
String busiName="PDRiskCopyBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, "",busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"����ɹ�!"+" ";
    	FlagStr = "Success";
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
    FlagStr = "Fail";
   }

  	/*
 }
 catch(Exception ex)
 {
  	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  	FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  	tError = tPDRiskCopyBL.mErrors;
  	if (!tError.needDealError())
  	{                          
   		Content = " ����ɹ�! ";
   		FlagStr = "Success";
  	}
  	else                                                                           
  	{
   		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
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

