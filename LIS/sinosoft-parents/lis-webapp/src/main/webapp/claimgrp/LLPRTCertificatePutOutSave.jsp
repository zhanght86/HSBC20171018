<%
//**************************************************************************************************
//Name：LLPRTCertificatePutOutSave.jsp
//Function：结论保存并理算
//Author：续涛
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

<%
	loggerDebug("LLPRTCertificatePutOutSave","############################");
	loggerDebug("LLPRTCertificatePutOutSave","############################");
    //准备通用参数
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
   
    if(tG == null) 
    {
        loggerDebug("LLPRTCertificatePutOutSave","登录信息没有获取!!!");
       return;
     } 
	loggerDebug("LLPRTCertificatePutOutSave","############################");
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("customerNo"));
	tTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
 
    // 准备传输数据 VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add( tG );        
    tVData.add( tTransferData );        

    LLPRTCertificatePutOutUI tLLPRTCertificatePutOutUI = new LLPRTCertificatePutOutUI();
    
    
   if (tLLPRTCertificatePutOutUI.submitData(tVData,"") == false)
    {
        int n = tLLPRTCertificatePutOutUI.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            Content = Content + "原因是: " + tLLPRTCertificatePutOutUI.mErrors.getError(i).errorMessage;
            FlagStr = "FAIL";
        }      
    }
    else
    {
    	loggerDebug("LLPRTCertificatePutOutSave","2222222222222222222"+FlagStr);
	    tResult = tLLPRTCertificatePutOutUI.getResult();
	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            	loggerDebug("LLPRTCertificatePutOutSave","没有得到要显示的数据文件");
	    }

    }    

	loggerDebug("LLPRTCertificatePutOutSave","1111111111111111111"+FlagStr);
    if (FlagStr.equals("Succ"))
    {
	    ExeSQL tExeSQL = new ExeSQL();
		//获取临时文件名
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		//-------------------------------jinsh 20070521----------------------------------------//
		//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	  String strVFFileName = "vtsfile/" + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	  //-------------------------------jinsh 20070521----------------------------------------//
		//获取存放临时文件的路径
		String strRealPath = application.getRealPath("/").replace('\\','/');
		loggerDebug("LLPRTCertificatePutOutSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
	
		//合并VTS文件
		CombineVts tcombineVts = null;
		String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//把dataStream存储到磁盘文件
		loggerDebug("LLPRTCertificatePutOutSave","存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLPRTCertificatePutOutSave","==> Write VTS file to disk ");
		loggerDebug("LLPRTCertificatePutOutSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		
		//本来打算采用get方式来传递文件路径
		session.putValue("RealPath", strVFPathName);
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));
		loggerDebug("LLPRTCertificatePutOutSave","==流水号 : "+session.getValue("PrtSeq"));
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
