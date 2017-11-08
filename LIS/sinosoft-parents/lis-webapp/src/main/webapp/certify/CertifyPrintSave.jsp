<%
//�������ƣ�CertifyPrintInput.jsp
//�����ܣ�
//�������ڣ�2002-10-14 10:20:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	String handleFunction(HttpSession session, HttpServletRequest request) {
	  LZCardPrintSchema schemaLZCardPrint = new LZCardPrintSchema();
	
	  //�������
	  CErrors tError = null;
	  String strOperate = request.getParameter("hideOperate").trim();
	  
		GlobalInput globalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		
		globalInput.setSchema((GlobalInput)session.getValue("GI"));
	
		schemaLZCardPrint.setPrtNo(request.getParameter("PrtNo"));
		schemaLZCardPrint.setCertifyCode(request.getParameter("CertifyCode"));
		schemaLZCardPrint.setRiskCode(request.getParameter("RiskCode"));
		schemaLZCardPrint.setRiskVersion(request.getParameter("RiskVersion"));
		schemaLZCardPrint.setSubCode(request.getParameter("SubCode"));
		schemaLZCardPrint.setMaxMoney("1");
		schemaLZCardPrint.setMaxDate(request.getParameter("MaxDate"));
		schemaLZCardPrint.setComCode(request.getParameter("ComCode"));
		schemaLZCardPrint.setPhone(request.getParameter("Phone"));
		schemaLZCardPrint.setLinkMan(request.getParameter("LinkMan"));
		schemaLZCardPrint.setCertifyPrice(request.getParameter("CertifyPrice"));
		schemaLZCardPrint.setManageCom(request.getParameter("ManageCom"));
		schemaLZCardPrint.setOperatorInput(request.getParameter("OperatorInput"));
		schemaLZCardPrint.setInputDate(request.getParameter("InputDate"));
		schemaLZCardPrint.setInputMakeDate(request.getParameter("InputMakeDate"));
		schemaLZCardPrint.setGetMan(request.getParameter("GetMan"));
		schemaLZCardPrint.setGetDate(request.getParameter("GetDate"));
		schemaLZCardPrint.setOperatorGet(request.getParameter("OperatorGet"));
		schemaLZCardPrint.setStartNo(request.getParameter("StartNo"));
		schemaLZCardPrint.setEndNo(request.getParameter("EndNo"));
		schemaLZCardPrint.setGetMakeDate(request.getParameter("GetMakeDate"));
		schemaLZCardPrint.setSumCount(request.getParameter("SumCount"));
		schemaLZCardPrint.setState(request.getParameter("State"));
		
    tTransferData.setNameAndValue("SubManageCom",request.getParameter("subManageCom"));

		String strCertifyClass = request.getParameter("CertifyClass");
			
	  // ׼���������� VData
	  VData vData = new VData();

		vData.addElement(schemaLZCardPrint);
		vData.add(globalInput);
		vData.add(tTransferData);
		
		Hashtable hashParams = new Hashtable();
		
		hashParams.put("CertifyClass", strCertifyClass);
		
		vData.add( hashParams );
	
	  try {
	 	  CardPrintUI tCardPrintUI = new CardPrintUI();
	 	  
	    if ( !tCardPrintUI.submitData(vData, strOperate) ) {
		   	if( tCardPrintUI.mErrors.needDealError() ) {
		   		return tCardPrintUI.mErrors.getFirstError();
			  } else {
			  	return "����ʧ�ܣ�����û����ϸ��ԭ��";
			  }
			}
	    
	  } catch(Exception ex) {
	  	ex.printStackTrace();
			return "����ʧ�ܣ�ԭ����:" + ex.toString();	  	
	  }
	  
	  return "";
	}
%>

<%
	String FlagStr = "";
	String Content = "";
	
	try {
		Content = handleFunction(session, request);
		
		if( Content.equals("") ) {
			FlagStr = "Succ";
			Content = "�����ɹ����";
		} else {
			FlagStr = "Fail";
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

