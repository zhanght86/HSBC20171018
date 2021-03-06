<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：SysCertTakeBackSave.jsp
//程序功能：
//创建日期：2002-10-25
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
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
		strReturn += "  parent.fraInterface.afterSubmit('Succ', '操作成功完成');";
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
//输出参数
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
	out.println( buildMsg(false, "网页超时或者没有操作员信息") );
	return;
}
else
{
	globalInput.setSchema(tempGI);
}

//校验处理
//内容待填充
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
 mTransferData.setNameAndValue("reasoncode",request.getParameter("reasoncode")); //延迟送达原因编码
 mTransferData.setNameAndValue("reasondesc",request.getParameter("reasondesc")); //延迟送达原因
 
	//准备传输数据 VData
	VData vData = new VData();
	vData.addElement(globalInput);
	vData.addElement(schemaLZSysCertify);
	vData.addElement(mTransferData);
		//数据传输
		loggerDebug("SysCertTakeBackSave","单证号码========"+request.getParameter("CertifyCode"));
		//如果是回单，另走一个类-----add by haopan in 2007-2-7
	if(tCertifyCode.equals("9995")||"9995".equals(tCertifyCode)||tCertifyCode=="9995")
	{
		/*ContTakeBackBL tContTakeBackBL= new ContTakeBackBL();
			loggerDebug("SysCertTakeBackSave","####################走回单的类#####################hp");
		if(!tContTakeBackBL.submitData(vData,""))
		{
		
			out.println( buildMsg(false, " 保存失败,原因是:"+ tContTakeBackBL.mErrors.getFirstError()));
			return;
			}*/
		loggerDebug("SysCertTakeBackSave","####################走回单的类#####################hp");
		String busiName="ContTakeBackBL";
		String mOperateType="";
		String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,mOperateType,busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
		    	   out.println( buildMsg(false, mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError()));
		    	   return;
			   }
			   else
			   {
				   out.println( buildMsg(false,  mDescType+"失败"));
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
			out.println( buildMsg(false, " 保存失败，原因是: " + sysCertTakeBackUI.mErrors.getFirstError()));
			sysCertTakeBackUI = null;
			return;
		}*/
		String busiName="SysCertTakeBackUI";
		String mOperateType=request.getParameter("hideOperation");
		String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,mOperateType,busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
		    	   out.println( buildMsg(false, mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError()));
		    	   return;
			   }
			   else
			   {
				   out.println( buildMsg(false,  mDescType+"失败"));
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
	out.println( buildMsg(false, " 保存失败，原因是: " + ex.toString()));
	return;
}*/
%>
