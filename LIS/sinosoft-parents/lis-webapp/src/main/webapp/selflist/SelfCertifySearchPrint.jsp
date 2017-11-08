<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：SelfCertifySearch.jsp
//程序功能：自助卡单打印清单
//创建日期：2008-03-13 
//创建人  ：zz
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.list.*"%>

<%
  //输出参数
  String FlagStr = "Fail";
  String Content = "";
  boolean bContinue = true;  

  GlobalInput globalInput = new GlobalInput( );
  globalInput.setSchema( (GlobalInput)session.getValue("GI") );
  
	try {	
		  
	  // 单证信息部分
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
	  loggerDebug("SelfCertifySearchPrint","缴费类型名称:"+szFeeType);
	  
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
			loggerDebug("SelfCertifySearchPrint","***********入机日期是"+schemaLZCard.getModifyDate());
			
			schemaLZCard.setTakeBackNo(szFeeType); //借用回收清算单号字段传递缴费类型信息
			
			schemaLZCard.setState("0");

	    setLZCard.add(schemaLZCard);
	  }

	  // 准备传输数据 VData
	  VData vData = new VData();
	
	  vData.addElement(globalInput);
	  vData.addElement(setLZCard);
	  
	  // 数据传输
	  SelfCertifySearchUI tSelfCertifySearchUI = new SelfCertifySearchUI();
    loggerDebug("SelfCertifySearchPrint","提交给SelfCertifySearch处理");
	  if (!tSelfCertifySearchUI.submitData(vData, "SEARCH||PRINT")) {
	    Content = " 保存失败，原因是: " + tSelfCertifySearchUI.mErrors.getFirstError();
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

		  vData = tSelfCertifySearchUI.getResult();

			XmlExport xe = (XmlExport)vData.getObjectByObjectName("XmlExport",0);
			
			session.setAttribute("PrintStream", xe.getInputStream());
			loggerDebug("SelfCertifySearchPrint","put session value");
			response.sendRedirect("../f1print/GetF1Print.jsp");
	  }
	  
	} catch(Exception ex) {
		ex.printStackTrace( );
   	Content = FlagStr + " 保存失败，原因是:" + ex.getMessage( );
   	FlagStr = "Fail";
	}
%>
