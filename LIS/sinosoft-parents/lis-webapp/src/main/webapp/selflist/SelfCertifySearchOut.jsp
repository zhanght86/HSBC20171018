<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�SelfCertifySearchOut.jsp
//�����ܣ�����������ӡ�嵥
//�������ڣ�2008-03-13 
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.list.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.util.*"%>

	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  String FlagStr = "Fail";
  String Content = "";
  String strResult = "";
  
  try 
  {
    	// �������ڵĲ�ѯ�����п�������ֵ�������ڵ�һ��Schema�б��濪ʼʱ�䣬�ڵڶ���Schema�б������ʱ��
    	LZCardSet tLZCardSet = new LZCardSet();
    
		LZCardSchema tLZCardSchema = new LZCardSchema();
		
		tLZCardSchema.setCertifyCode( request.getParameter("CertifyCode") );
		tLZCardSchema.setState( request.getParameter("State") );
		tLZCardSchema.setSendOutCom( request.getParameter("SendOutCom") );
		tLZCardSchema.setReceiveCom( request.getParameter("ReceiveCom") );
		tLZCardSchema.setStartNo( request.getParameter("StartNo") );
		tLZCardSchema.setEndNo( request.getParameter("EndNo") );		
		tLZCardSchema.setOperator( request.getParameter("Operator") );
		tLZCardSchema.setMakeDate( request.getParameter("MakeDateB") );
		
		// �����һ��Schema
		tLZCardSet.add( tLZCardSchema );
		
		tLZCardSchema = new LZCardSchema();		
		tLZCardSchema.setMakeDate( request.getParameter("MakeDateE") );		
		
		// ����ڶ���Schema
		tLZCardSet.add( tLZCardSchema );
		
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	    
	  	// ׼���������� VData
	  	VData tVData = new VData();
	
		tVData.add( globalInput );
		tVData.add( tLZCardSet );
		tVData.add( new Hashtable() );
	
	  	// ���ݴ���
	  	SelfCertifySearchUI tSelfCertifySearchUI = new SelfCertifySearchUI();
	  
	  	if( !tSelfCertifySearchUI.submitData(tVData, "SEARCH||MAIN") ) 
	  	{
	    	if( tSelfCertifySearchUI.mErrors.needDealError() )
	     	{
	    		throw new Exception( tSelfCertifySearchUI.mErrors.getFirstError() );
	    	} 
	    	else 
	    	{
	    		throw new Exception("CertifySearchUI��ѯʧ�ܣ�����û���ṩ��ϸ����Ϣ");
	    	}
	  } 
	  else 
	  {
	    tVData.clear();
	    tVData = tSelfCertifySearchUI.getResult();
	    
	    SSRS tSSRS=null;
	    
	    tSSRS = (SSRS)tVData.getObjectByObjectName("SSRS", 0);
	    
	    //strResult = "0|" + String.valueOf(tSSRS.getMaxRow());
	    //strResult += "^" + tSSRS.encode();
	    strResult =tSSRS.encode();
	    loggerDebug("SelfCertifySearchOut","strResult:"+strResult);
	  }
	  
	  FlagStr = "Succ";
	  
	} 
	catch (Exception ex)
	 {
	  ex.printStackTrace();
	  FlagStr = "Fail";
	  Content = ex.getMessage();
	}
%>

<html>
	<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
<% if( FlagStr.equals("Succ") ) { %>
		 var varResult = new Array();
		 
		 varResult[0] = "<%= strResult %>";
		 
		 parent.fraInterface.onShowResult(varResult);
<% } else { %>
<% } %>
	</script>
</html>
