<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�SysCertTakeBackSave.jsp
//�����ܣ�
//�������ڣ�2002-10-25
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<SCRIPT src="./CQueryValueOperate.js"></SCRIPT>
<SCRIPT src="IndiDunFeeInput.js"></SCRIPT>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LZSysCertifySchema"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%!
String buildMsg(boolean bFlag, String strMsg) {
	String strReturn = "";
	strReturn += "<html><script language=\"javascript\">";
	if( bFlag )
	{
		strReturn += "  parent.fraInterface.afterSubmit('Succ', '�����ɹ����');";
	}
	else
	{
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
String tCertifyCode = request.getParameter("CertifyCode");
String tContno = request.getParameter("CertifyNo");
GlobalInput globalInput = new GlobalInput( );
GlobalInput tempGI = (GlobalInput)session.getValue("GI");
if( tempGI == null )
{
	out.println( buildMsg(false, "��ҳ��ʱ����û�в���Ա��Ϣ") );
	return;
}
else
{
	globalInput.setSchema(tempGI);
}

//У�鴦��
//���ݴ����
//try
//{
	LZSysCertifySchema schemaLZSysCertify = new LZSysCertifySchema();
	schemaLZSysCertify.setCertifyCode( request.getParameter("CertifyCode") );
	schemaLZSysCertify.setCertifyNo( request.getParameter("CertifyNo") );
	schemaLZSysCertify.setTakeBackOperator( request.getParameter("TakeBackOperator") );
	schemaLZSysCertify.setTakeBackDate( request.getParameter("TakeBackDate") );
	schemaLZSysCertify.setSendOutCom( request.getParameter("SendOutCom") );
	schemaLZSysCertify.setReceiveCom( request.getParameter("ReceiveCom") );

 TransferData mTransferData = new TransferData();
 mTransferData.setNameAndValue("contno",tContno);
 mTransferData.setNameAndValue("reasoncode",request.getParameter("reasoncode")); //�ӳ��ʹ�ԭ�����
 mTransferData.setNameAndValue("reasondesc",request.getParameter("reasondesc")); //�ӳ��ʹ�ԭ��
 
	//׼���������� VData
	VData vData = new VData();
	vData.addElement(globalInput);
	vData.addElement(schemaLZSysCertify);
	vData.addElement(mTransferData);
		//���ݴ���
		loggerDebug("SysCertTakeBackSave","��֤����========"+request.getParameter("CertifyCode"));
		//����ǻص�������һ����-----add by haopan in 2007-2-7
	if(tCertifyCode.equals("9995")||"9995".equals(tCertifyCode)||tCertifyCode=="9995")
	{
		/*ContTakeBackBL tContTakeBackBL= new ContTakeBackBL();
			loggerDebug("SysCertTakeBackSave","####################�߻ص�����#####################hp");
		if(!tContTakeBackBL.submitData(vData,""))
		{
		
			out.println( buildMsg(false, " ����ʧ��,ԭ����:"+ tContTakeBackBL.mErrors.getFirstError()));
			return;
			}*/
		loggerDebug("SysCertTakeBackSave","####################�߻ص�����#####################hp");
		String busiName="ContTakeBackBL";
		String mOperateType="";
		String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,mOperateType,busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
		    	   out.println( buildMsg(false, mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError()));
		    	   return;
			   }
			   else
			   {
				   out.println( buildMsg(false,  mDescType+"ʧ��"));
				   return;				
			   }
		  }
		  else
		  {
			  out.println( buildMsg(true, "") );  
		  }
	}
		
	else
	{
		/*SysCertTakeBackUI sysCertTakeBackUI = new SysCertTakeBackUI();
		loggerDebug("SysCertTakeBackSave","sysCertTakeBackUI.submitData");
		if (!sysCertTakeBackUI.submitData(vData, request.getParameter("hideOperation")))
		{
			out.println( buildMsg(false, " ����ʧ�ܣ�ԭ����: " + sysCertTakeBackUI.mErrors.getFirstError()));
			sysCertTakeBackUI = null;
			return;
		}*/
		String busiName="SysCertTakeBackUI";
		String mOperateType=request.getParameter("hideOperation");
		String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,mOperateType,busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
		    	   out.println( buildMsg(false, mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError()));
		    	   return;
			   }
			   else
			   {
				   out.println( buildMsg(false,  mDescType+"ʧ��"));
				   return;				
			   }
		  }
		  else
		  {
			  out.println( buildMsg(true, "") );  
		  }
	}

	
/*	out.println( buildMsg(true, "") );
}
catch(Exception ex)
{
	ex.printStackTrace( );
	out.println( buildMsg(false, " ����ʧ�ܣ�ԭ����: " + ex.toString()));
	return;
}*/
%>
