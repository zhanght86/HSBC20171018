<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�CertifySearchPrint.jsp
//�����ܣ�
//�������ڣ�2003-06-16
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>

<%
  //�������
  String FlagStr = "Fail";
  String Content = "";
  boolean bContinue = true;

  GlobalInput globalInput = new GlobalInput( );
  globalInput.setSchema( (GlobalInput)session.getValue("GI") );
  
	try {
	  // ��֤��Ϣ����
	  String szNo[]					= request.getParameterValues("CardListInfoNo");
	  String szCertifyCode[]= request.getParameterValues("CardListInfo1");
	  String szSendOutCom[]	= request.getParameterValues("CardListInfo2");
	  String szReceiveCom[] = request.getParameterValues("CardListInfo3");
	  String szStartNo[]		= request.getParameterValues("CardListInfo4");
	  String szEndNo[]			= request.getParameterValues("CardListInfo5");
	  String szSumCount[]   = request.getParameterValues("CardListInfo6");
	  String szStateFlag[]  = request.getParameterValues("CardListInfo7");
	  String szOperator[]   = request.getParameterValues("CardListInfo8");
	  String szMakeDate[]   = request.getParameterValues("CardListInfo9");
	  String szMakeTime[]   = request.getParameterValues("CardListInfo10");
	  int nIndex = 0;
	  
	  LZCardSet setLZCard = new LZCardSet( );
		
	  for( nIndex = 0; nIndex < szNo.length; nIndex ++ ) 
	  {

  		LZCardSchema schemaLZCard = new LZCardSchema( );
	    
	    schemaLZCard.setCertifyCode(szCertifyCode[nIndex]);

	    schemaLZCard.setSendOutCom(szSendOutCom[nIndex]);
	    schemaLZCard.setReceiveCom(szReceiveCom[nIndex]);

	    schemaLZCard.setStartNo(szStartNo[nIndex]);
	    schemaLZCard.setEndNo(szEndNo[nIndex]);
	    
			schemaLZCard.setSumCount(szSumCount[nIndex]);
			
			schemaLZCard.setStateFlag(szStateFlag[nIndex]);
			schemaLZCard.setOperator(szOperator[nIndex]);
			schemaLZCard.setMakeDate(szMakeDate[nIndex]);
			loggerDebug("CertifySearchPrint","***********���������"+schemaLZCard.getMakeDate());
			schemaLZCard.setMakeTime(szMakeTime[nIndex]);
			loggerDebug("CertifySearchPrint","***********���ʱ����"+schemaLZCard.getMakeTime());
			schemaLZCard.setState("0");

	    setLZCard.add(schemaLZCard);
	  }

	  // ׼���������� VData
	  VData vData = new VData();
	
	  vData.addElement(globalInput);
	  vData.addElement(setLZCard);
	  
	  // ���ݴ���
	  CertifySearchUI tCertifySearchUI = new CertifySearchUI();
    loggerDebug("CertifySearchPrint","�ύ��CertifySearch����");
	  if (!tCertifySearchUI.submitData(vData, "SEARCH||PRINT")) {
	    Content = " ����ʧ�ܣ�ԭ����: " + tCertifySearchUI.mErrors.getFirstError();
	    FlagStr = "Fail";
%>
		  <script language="javascript">
			  alert('<%= Content %>');
			  window.opener = null;
			  window.close();
		  </script>
<%
		  return;

	  } else {
	  	Content = " ����ɹ� ";
	  	FlagStr = "Succ";

		  vData = tCertifySearchUI.getResult();

			XmlExport xe = (XmlExport)vData.getObjectByObjectName("XmlExport",0);
			
			session.setAttribute("PrintStream", xe.getInputStream());
			loggerDebug("CertifySearchPrint","put session value");
			response.sendRedirect("../f1print/GetF1Print.jsp");
	  }
	  
	} catch(Exception ex) {
		ex.printStackTrace( );
   	Content = FlagStr + " ����ʧ�ܣ�ԭ����:" + ex.getMessage( );
   	FlagStr = "Fail";
	}
%>
