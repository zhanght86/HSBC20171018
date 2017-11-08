<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�CertifySearchOut.jsp
//�����ܣ�
//�������ڣ�2003-06-16
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
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
		tLZCardSchema.setHandler( request.getParameter("Handler") );
		tLZCardSchema.setTakeBackNo( request.getParameter("TakeBackNo") );

		tLZCardSchema.setMakeDate( request.getParameter("MakeDateB") );		
		tLZCardSchema.setHandleDate( request.getParameter("HandleDateB") );
		tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateB") );
		
		// �����һ��Schema
		tLZCardSet.add( tLZCardSchema );
		
		tLZCardSchema = new LZCardSchema();
		
		tLZCardSchema.setMakeDate( request.getParameter("MakeDateE") );		
		tLZCardSchema.setHandleDate( request.getParameter("HandleDateE") );
		tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateE") );
		
		// ����ڶ���Schema
		tLZCardSet.add( tLZCardSchema );
		
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	    
	  // ׼���������� VData
	  VData tVData = new VData();
	
		tVData.add( globalInput );
		tVData.add( tLZCardSet );
		tVData.add( new Hashtable() );
	
	  // ���ݴ���
	  CertifySearchUI tCertifySearchUI = new CertifySearchUI();
	  
	  if( !tCertifySearchUI.submitData(tVData, "SEARCH||MAIN") ) 
	  {
	    if( tCertifySearchUI.mErrors.needDealError() )
	     {
	    	throw new Exception( tCertifySearchUI.mErrors.getFirstError() );
	    } 
	    else 
	    {
	    	throw new Exception("CertifySearchUI��ѯʧ�ܣ�����û���ṩ��ϸ����Ϣ");
	    }
	  } 
	  else 
	  {
	    tVData.clear();
	    tVData = tCertifySearchUI.getResult();
	    
	    tLZCardSet = (LZCardSet)tVData.getObjectByObjectName("LZCardSet", 0);
	    
	    strResult = "0|" + String.valueOf(tLZCardSet.size());
	    strResult += "^" + tLZCardSet.encode();
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
