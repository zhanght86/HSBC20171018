<%
//**************************************************************************************************
//Name��LLPRTProtestNoRegisterSave.jsp
//Function�����۱��沢����
//Author������
//Date:   2005-07-14
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
        loggerDebug("LLPRTProtestNoRegisterSave","��¼��Ϣû�л�ȡ!!!");
       return;
     } 

    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("customerNo"));
	tTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
 
    // ׼���������� VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add( tG );        
    tVData.add( tTransferData );        

//    LLPRTProtestNoRegisterUI tLLPRTProtestNoRegisterUI = new LLPRTProtestNoRegisterUI();
//    
//    
//   if (tLLPRTProtestNoRegisterUI.submitData(tVData,"") == false)
//    {
//        int n = tLLPRTProtestNoRegisterUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            Content = Content + "ԭ����: " + tLLPRTProtestNoRegisterUI.mErrors.getError(i).errorMessage;
//            FlagStr = "FAIL";
//        }      
//    }
String busiName="grpLLPRTProtestNoRegisterUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,"",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLPRTProtestNoRegisterSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
    	
	    //tResult = tLLPRTProtestNoRegisterUI.getResult();
	    tResult = tBusinessDelegate.getResult();
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            	loggerDebug("LLPRTProtestNoRegisterSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }

    }    
    


    if (FlagStr.equals("Succ"))
    {    

      ExeSQL tExeSQL = new ExeSQL();
	//��ȡ��ʱ�ļ���
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//---------------------------------------------jinsh20070521----------------------//
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strVFFileName = "vtsfile/" + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//---------------------------------------------jinsh20070521----------------------//
	//��ȡ�����ʱ�ļ���·��
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("LLPRTProtestNoRegisterSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;

	CombineVts tcombineVts = null;

	//�ϲ�VTS�ļ�
	String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	loggerDebug("LLPRTProtestNoRegisterSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("LLPRTProtestNoRegisterSave","==> Write VTS file to disk ");

	loggerDebug("LLPRTProtestNoRegisterSave","===strVFFileName : "+strVFFileName);
	session.putValue("RealPath", strVFPathName);
	session.putValue("PrtSeq", request.getParameter("PrtSeq"));
	loggerDebug("LLPRTProtestNoRegisterSave","==��ˮ�� : "+session.getValue("PrtSeq"));
	//�����������get��ʽ�������ļ�·��----����·����ȡ�ļ�
	response.sendRedirect("../claim/LLClaimGetF1PrintJ.jsp");	
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
