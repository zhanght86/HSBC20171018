<%
//�������ƣ�
//�����ܣ��ֺ���ϵ������
//�������ڣ�2008-11-09 17:55:57
//������  ������ͥ

%>
<!--�û�У����-->
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
  //�������
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
	    Content = "����ʧ��ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  }   
	  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	  if (FlagStr=="")
	  {
	    tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
	     Content="����ɹ���"+tBusinessDelegate.getResult().getObjectByObjectName("String",0);
	     FlagStr = "Succ";
	    }
	    else                                                                           
	    {
	    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	  }
 %>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

