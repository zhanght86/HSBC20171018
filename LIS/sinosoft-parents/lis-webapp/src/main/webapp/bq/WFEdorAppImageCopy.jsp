<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//�������ƣ�
//�����ܣ�Ӱ����
//�������ڣ�2006-4-5
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
    String tEdorAcceptNo    = request.getParameter("EdorAcceptNo"); //��ȫ�����
    
    String FlagStr = "Fail";
    String Content = "";
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo",tEdorAcceptNo);
    tTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
    tTransferData.setNameAndValue("Operator",tGI.Operator);
 
    // ׼���������� VData
    VData tVData = new VData();
    tVData.addElement(tTransferData);

 
    String busiName="PEdorImageCopy";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"COPY",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="�����ɹ���";
		    	FlagStr = "Succ";	
	 }	     
   
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>