<%
//�������ƣ�SysCertSendOutSave.jsp
//�����ܣ�
//�������ڣ�2002-10-25
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<SCRIPT src="./CQueryValueOperate.js"></SCRIPT>
<SCRIPT src="IndiDunFeeInput.js"></SCRIPT>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	String buildMsg(boolean bFlag, String strMsg) {
		String strReturn = "";
		
		strReturn += "<html><script language=\"javascript\">";
		
		if( bFlag ) {
		loggerDebug("SysCertSendOutSave","debug1:--------------------------------");
		   if( strMsg.equals(""))
		   {
		     loggerDebug("SysCertSendOutSave","--------------------------------");
			 strReturn += "  parent.fraInterface.afterSubmit('Succ', '�����ɹ����--------------------');";
		   }
		   else
		   {
		     loggerDebug("SysCertSendOutSave","debug2:--------------------------------");
		     strReturn += "  parent.fraInterface.afterSubmit('Fail', '" + strMsg + "');";
		   }
			        
		} else {
			strReturn += "  parent.fraInterface.afterSubmit('Fail','" + strMsg + "');";
		}
		strReturn += "</script></html>";
		
		return strReturn;
	}
%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  boolean bContinue = true;

  GlobalInput globalInput = new GlobalInput( );
  GlobalInput tempGI = (GlobalInput)session.getValue("GI");
  if( tempGI == null ) {
  	out.println( buildMsg(false, "��ҳ��ʱ����û�в���Ա��Ϣ") );
  	return;
  } else {
    globalInput.setSchema(tempGI);
  }
  
  //У�鴦��
  //���ݴ����
	//try {
	  String szNo[]					= request.getParameterValues("CertifyListNo");
	  String szCertifyNo[]	= request.getParameterValues("CertifyList1");
	  String szValidDate[]	= request.getParameterValues("CertifyList2");
	  int nIndex;
  
  	if( szNo == null ) {
  		out.println( buildMsg(false, "�����뵥֤������Ϣ") );
  		return;
  	} else if( szNo.length == 0 ) {
  		out.println( buildMsg(false, "�����뵥֤������Ϣ") );
  		return;
  	}
  	
  	LZSysCertifySet setLZSysCertify = new LZSysCertifySet();
  	
  	for(nIndex = 0; nIndex < szNo.length; nIndex ++) {
  		loggerDebug("SysCertSendOutSave","nIndex is " + String.valueOf(nIndex));
  		loggerDebug("SysCertSendOutSave",request.getParameter("ReceiveCom"));
  		
		  LZSysCertifySchema schemaLZSysCertify = new LZSysCertifySchema();
		  
			schemaLZSysCertify.setCertifyCode( request.getParameter("CertifyCode") );
			schemaLZSysCertify.setCertifyNo( szCertifyNo[nIndex] );
			schemaLZSysCertify.setValidDate( szValidDate[nIndex] );
			schemaLZSysCertify.setSendOutCom( request.getParameter("SendOutCom") );
			schemaLZSysCertify.setReceiveCom( request.getParameter("ReceiveCom") );
			schemaLZSysCertify.setHandler( request.getParameter("Handler") );
			schemaLZSysCertify.setHandleDate( request.getParameter("HandleDate") );
			
			setLZSysCertify.add( schemaLZSysCertify );
		}
		
	  // ׼���������� VData
	  VData vData = new VData();
	
	  vData.addElement(globalInput);
	  vData.addElement(setLZSysCertify);
		
	  // ���ݴ���
	  /*SysCertSendOutUI sysCertSendOutUI = new SysCertSendOutUI();

	  loggerDebug("SysCertSendOutSave","sysCertSendOutUI.submitData");
	  if (!sysCertSendOutUI.submitData(vData, request.getParameter("hideOperation"))) {
	  	out.println( buildMsg(false, " ����ʧ�ܣ�ԭ����: " + sysCertSendOutUI.mErrors.getFirstError()));
	 	return;
	  }
	  else
	  {
	        loggerDebug("SysCertSendOutSave","Debug :"+sysCertSendOutUI.mErrors.getFirstError());
	        
	        if(!sysCertSendOutUI.mErrors.getFirstError().equals(""))
	        {
	                out.println(buildMsg(true,sysCertSendOutUI.mErrors.getFirstError()));
	                return;
	        }
	        else
	        {
	                out.println( buildMsg(true, "") );
	        }
	  }
	  
	  
	  
	} catch(Exception ex) {
		ex.printStackTrace( );
	 	out.println( buildMsg(false, " ����ʧ�ܣ�ԭ����: " + ex.toString()));
	 	return;
	}*/
	
	String busiName="SysCertSendOutUI";
	String mDescType="����";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(vData,request.getParameter("hideOperation"),busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
	    	   out.println( buildMsg(false, mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError()));
	    	   return;
		   }
		   else
		   {
			   out.println( buildMsg(false, mDescType+"ʧ��"));
	    	   return;				
		   }
	  }
	  else
	  {
		  out.println( buildMsg(true, "") );  
	  }
%>
