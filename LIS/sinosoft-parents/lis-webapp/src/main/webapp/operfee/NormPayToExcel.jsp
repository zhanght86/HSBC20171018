<%
//�������ƣ�NormPayToExcel.jsp
//�����ܣ�����ᱨ�������ݵ���
//�������ڣ�
//������ : ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>

<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.agentprint.*"%>   
  <%@page import="com.sinosoft.service.*" %> 
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
	
<%
  //������Ϣ������У�鴦��
	String GrpPolNo=request.getParameter("GrpPolNo1");
	String operator="download";
	String file="";
	
	//�������
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr = "Fail";
	String Content = "";
	String Result="";	
	boolean flag=true;
	GlobalInput tG = new GlobalInput();  
    tG=(GlobalInput)session.getValue("GI");  
    if(tG == null) {
		out.println("session has expired");
		return;
    } 
    System.out.println("�ŵ��ţ�"+GrpPolNo);
  
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("GrpPolNo",GrpPolNo);	
    
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tTransferData );
		tVData.add( tG );
		
		// ���ݴ���
  	//ExcelChange tExcelChange   = new ExcelChange();
  	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  			System.out.println("before ExcelChange!!!!");			
		 if (!tBusinessDelegate.submitData(tVData,"CHANGE","ExcelChange"))
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			System.out.println("Error: "+ tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " ���ݵ���ʧ�ܣ�ԭ����: " +  tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError =  tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = "�ɹ�����ģ���ļ�! ";
		    	FlagStr = "Succ";
		    	//file=tExcelChange.getFile();    
		    	file=tBusinessDelegate.getResult().getObjectByObjectName("String",0).toString();
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=operator%>","<%=file%>");
</script>
</html>
