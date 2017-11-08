<%
//**************************************************************************************************
//Name��LLPRTCertificateSignforSave.jsp
//Function�����۱��沢����
//Author��wsz
//Date:   2005-8-2
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //׼��ͨ�ò���
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
   
    if(tG == null) 
    {
    	loggerDebug("LLPRTCertificateSignforSave","��¼��Ϣû�л�ȡ!!!");
       	return;
    } 

    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("customerNo"));
    tTransferData.setNameAndValue("ClaimTag","����");
 
    // ׼���������� VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add( tG );        
    tVData.add( tTransferData );        

//    LLPRTCertificateSignforUI tLLPRTCertificateSignforUI = new LLPRTCertificateSignforUI();
//    
//    if (tLLPRTCertificateSignforUI.submitData(tVData,"") == false)
//    {
//        int n = tLLPRTCertificateSignforUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            Content = Content + "ԭ����: " + tLLPRTCertificateSignforUI.mErrors.getError(i).errorMessage;
//            FlagStr = "FAIL";
//        }      
//    }
String busiName="grpLLPRTCertificateSignforUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,"",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLPRTCertificateSignforSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content =  Content + "ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   
		   FlagStr = "Fail";				
		} 
}

    else
    {
	    //tResult = tLLPRTCertificateSignforUI.getResult();
	     tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            	loggerDebug("LLPRTCertificateSignforSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }

    }    
    
    if (FlagStr.equals("Succ"))
    {    

      	ExeSQL tExeSQL = new ExeSQL();
	//��ȡ��ʱ�ļ���
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//-----------------------------jinsh--20070521--------------------------------------------//
	String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//-----------------------------jinsh--20070521--------------------------------------------//
	//��ȡ�����ʱ�ļ���·��
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("LLPRTCertificateSignforSave","strRealPath="+strRealPath);
	String strVFPathName = strRealPath + strVFFileName;

	CombineVts tcombineVts = null;

	//�ϲ�VTS�ļ�
	String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	loggerDebug("LLPRTCertificateSignforSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("LLPRTCertificateSignforSave","==> Write VTS file to disk ");

	loggerDebug("LLPRTCertificateSignforSave","===strVFFileName : "+strVFFileName);
	session.putValue("RealPath", strVFPathName);
	//�����������get��ʽ�������ļ�·��
	//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
	response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
    }
    else
    {

%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();   
</script>
</html>
<%
    }
%>
