<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AppntChkCustomerUnionSave.jsp
//�����ܣ�
//�������ڣ�2005-04-20 10:49:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strCustomerNo_OLD = request.getParameter("CustomerNo_NEW");
	String strCustomerNo_NEW = request.getParameter("CustomerNo_OLD");
  
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("CustomerNo_NEW", strCustomerNo_OLD);
    tTransferData.setNameAndValue("CustomerNo_OLD", strCustomerNo_NEW);
    
    CustomerUnionUI tCustomerUnionUI = new CustomerUnionUI();
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);   
		tCustomerUnionUI.submitData(tVData, "CUSTOMER|UNION");
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("ClientMergeSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tCustomerUnionUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		      Content ="����ɹ���";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
