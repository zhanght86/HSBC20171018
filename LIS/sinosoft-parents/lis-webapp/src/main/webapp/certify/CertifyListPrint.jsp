<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�CertifyListPrint.jsp
//�����ܣ���ӡ��֤�����嵥�Ĺ���ģ��
//�������ڣ�2003-04-17
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
  String strSum = "";
  
  try {
    // �������ڵĲ�ѯ�����п�������ֵ�������ڵ�һ��Schema�б��濪ʼʱ�䣬�ڵڶ���Schema�б������ʱ��
    LZCardSet tLZCardSet = new LZCardSet();
    
    String strTakeBackNo = (String)session.getAttribute("TakeBackNo");
    
		LZCardSchema tLZCardSchema = new LZCardSchema();
		
		Hashtable hashParams = new Hashtable();
		
		// ֱ�Ӵ�session��ȡֵ
		if( strTakeBackNo != null || !strTakeBackNo.equals("") ) {
			
			tLZCardSchema.setState( (String)session.getAttribute("State") );
			tLZCardSchema.setTakeBackNo( strTakeBackNo );
			
			if( session.getAttribute("NoUseAgentTemplate") != null ) {
				hashParams.put("NoUseAgentTemplate", "YES");
			}
			
			// Build set needed by CertStatUI
			tLZCardSet.add( tLZCardSchema );
			tLZCardSet.add( tLZCardSchema );
			
			// After using, clear session data
			session.removeAttribute("TakeBackNo");
			session.removeAttribute("State");
			session.removeAttribute("NoUseAgentTemplate");
			
		} else {  // ���򣬴�request������ȡֵ
			tLZCardSchema.setCertifyCode( request.getParameter("CertifyCode") );
			tLZCardSchema.setState( request.getParameter("State") );
	
			tLZCardSchema.setSendOutCom( request.getParameter("SendOutCom") );
			tLZCardSchema.setReceiveCom( request.getParameter("ReceiveCom") );
	
			tLZCardSchema.setOperator( request.getParameter("Operator") );
			tLZCardSchema.setHandler( request.getParameter("Handler") );
	
			tLZCardSchema.setMakeDate( request.getParameter("MakeDateB") );		
			tLZCardSchema.setHandleDate( request.getParameter("HandleDateB") );
			tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateB") );
			
			tLZCardSchema.setTakeBackNo( request.getParameter("TakeBackNo") );
			
			// �����һ��Schema
			tLZCardSet.add( tLZCardSchema );
			
			tLZCardSchema = new LZCardSchema();
			
			tLZCardSchema.setMakeDate( request.getParameter("MakeDateE") );		
			tLZCardSchema.setHandleDate( request.getParameter("HandleDateE") );
			tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateE") );
			
			// ����ڶ���Schema
			tLZCardSet.add( tLZCardSchema );
		}
		
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	    
	  // ׼���������� VData
	  VData tVData = new VData();
	
		tVData.add( globalInput );
		tVData.add( tLZCardSet );
		tVData.add( hashParams );
	
	  // ���ݴ���
	  CertStatUI tCertStatUI = new CertStatUI();
	  
	  if( !tCertStatUI.submitData(tVData, "QUERY||PRINT") ) {
	    if( tCertStatUI.mErrors.needDealError() ) {
	    	throw new Exception( tCertStatUI.mErrors.getFirstError() );
	    } else {
	    	throw new Exception("CertStatUI��ѯʧ�ܣ�����û���ṩ��ϸ����Ϣ");
	    }
	  } else {
	    tVData.clear();
	    tVData = tCertStatUI.getResult();
	
			XmlExport xe = (XmlExport)tVData.getObjectByObjectName("XmlExport", 0);
			session.setAttribute("PrintStream", xe.getInputStream());
		  loggerDebug("CertifyListPrint","put session value");
		  response.sendRedirect("../f1print/GetF1Print.jsp");
	  }
	  
	  FlagStr = "Succ";
	  
	} catch (Exception ex) {
	  ex.printStackTrace();
	  FlagStr = "Fail";
	  Content = ex.getMessage();
	}
%>
