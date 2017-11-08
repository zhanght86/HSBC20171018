<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：CertifySearchPrint.jsp
//程序功能：
//创建日期：2003-06-16
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>

<%
  //输出参数
  String FlagStr = "Fail";
  String Content = "";
  boolean bContinue = true;

  GlobalInput globalInput = new GlobalInput( );
  globalInput.setSchema( (GlobalInput)session.getValue("GI") );
  
	try {
	  // 单证信息部分
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
			loggerDebug("CertifySearchPrint","***********入机日期是"+schemaLZCard.getMakeDate());
			schemaLZCard.setMakeTime(szMakeTime[nIndex]);
			loggerDebug("CertifySearchPrint","***********入机时间是"+schemaLZCard.getMakeTime());
			schemaLZCard.setState("0");

	    setLZCard.add(schemaLZCard);
	  }

	  // 准备传输数据 VData
	  VData vData = new VData();
	
	  vData.addElement(globalInput);
	  vData.addElement(setLZCard);
	  
	  // 数据传输
	  CertifySearchUI tCertifySearchUI = new CertifySearchUI();
    loggerDebug("CertifySearchPrint","提交给CertifySearch处理");
	  if (!tCertifySearchUI.submitData(vData, "SEARCH||PRINT")) {
	    Content = " 保存失败，原因是: " + tCertifySearchUI.mErrors.getFirstError();
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
	  	Content = " 保存成功 ";
	  	FlagStr = "Succ";

		  vData = tCertifySearchUI.getResult();

			XmlExport xe = (XmlExport)vData.getObjectByObjectName("XmlExport",0);
			
			session.setAttribute("PrintStream", xe.getInputStream());
			loggerDebug("CertifySearchPrint","put session value");
			response.sendRedirect("../f1print/GetF1Print.jsp");
	  }
	  
	} catch(Exception ex) {
		ex.printStackTrace( );
   	Content = FlagStr + " 保存失败，原因是:" + ex.getMessage( );
   	FlagStr = "Fail";
	}
%>
