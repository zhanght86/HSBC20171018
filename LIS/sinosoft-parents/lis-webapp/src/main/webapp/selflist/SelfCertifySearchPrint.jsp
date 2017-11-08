<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�SelfCertifySearch.jsp
//�����ܣ�����������ӡ�嵥
//�������ڣ�2008-03-13 
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.list.*"%>

<%
  //�������
  String FlagStr = "Fail";
  String Content = "";
  boolean bContinue = true;  

  GlobalInput globalInput = new GlobalInput( );
  globalInput.setSchema( (GlobalInput)session.getValue("GI") );
  
	try {	
		  
	  // ��֤��Ϣ����
	  String szNo[]			= request.getParameterValues("CardListInfoNo");
	  String szCertifyCode[]= request.getParameterValues("CardListInfo1");
	  String szRiskCode[] = request.getParameterValues("CardListInfo2");
	  String szSendOutCom[]	= request.getParameterValues("CardListInfo3");
	  String szReceiveCom[] = request.getParameterValues("CardListInfo4");
	  String szStartNo[]		= request.getParameterValues("CardListInfo6");
	  String szEndNo[]			= request.getParameterValues("CardListInfo7");
	  String szSumCount[]   = request.getParameterValues("CardListInfo8");
	  String szOperator[]   = request.getParameterValues("CardListInfo9");
	  String szModifyDate[]   = request.getParameterValues("CardListInfo10");	  
		
	  String szFeeType   = StrTool.unicodeToGBK(request.getParameter("hiddenFeeType"));
	  loggerDebug("SelfCertifySearchPrint","�ɷ���������:"+szFeeType);
	  
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

			schemaLZCard.setOperator(szOperator[nIndex]);
			schemaLZCard.setModifyDate(szModifyDate[nIndex]);
			loggerDebug("SelfCertifySearchPrint","***********���������"+schemaLZCard.getModifyDate());
			
			schemaLZCard.setTakeBackNo(szFeeType); //���û������㵥���ֶδ��ݽɷ�������Ϣ
			
			schemaLZCard.setState("0");

	    setLZCard.add(schemaLZCard);
	  }

	  // ׼���������� VData
	  VData vData = new VData();
	
	  vData.addElement(globalInput);
	  vData.addElement(setLZCard);
	  
	  // ���ݴ���
	  SelfCertifySearchUI tSelfCertifySearchUI = new SelfCertifySearchUI();
    loggerDebug("SelfCertifySearchPrint","�ύ��SelfCertifySearch����");
	  if (!tSelfCertifySearchUI.submitData(vData, "SEARCH||PRINT")) {
	    Content = " ����ʧ�ܣ�ԭ����: " + tSelfCertifySearchUI.mErrors.getFirstError();
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

		  vData = tSelfCertifySearchUI.getResult();

			XmlExport xe = (XmlExport)vData.getObjectByObjectName("XmlExport",0);
			
			session.setAttribute("PrintStream", xe.getInputStream());
			loggerDebug("SelfCertifySearchPrint","put session value");
			response.sendRedirect("../f1print/GetF1Print.jsp");
	  }
	  
	} catch(Exception ex) {
		ex.printStackTrace( );
   	Content = FlagStr + " ����ʧ�ܣ�ԭ����:" + ex.getMessage( );
   	FlagStr = "Fail";
	}
%>
