<%
//**************************************************************************************************
//Name：LLPRTAppraisalSave.jsp
//Function：打印残疾鉴定提示通知
//Author：wsz
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
    //准备通用参数
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
   
    if(tG == null) 
    {
        loggerDebug("LLPRTAppraisalSave","登录信息没有获取!!!");
       return;
     } 

    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("PrtCode", "PCT008");
	tTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
 
    // 准备传输数据 VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add( tG );        
    tVData.add( tTransferData );        

//    LLPRTAppraisalUI tLLPRTAppraisalUI = new LLPRTAppraisalUI();
//    
//    
//   if (tLLPRTAppraisalUI.submitData(tVData,"") == false)
//    {
//        int n = tLLPRTAppraisalUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            Content = Content + "原因是: " + tLLPRTAppraisalUI.mErrors.getError(i).errorMessage;
//            FlagStr = "FAIL";
//        }      
//    }
	String busiName="grpLLPRTAppraisalUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	    {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
	        	int n = tBusinessDelegate.getCErrors().getErrorCount();
		        for (int i = 0; i < n; i++)
		        {
		            //loggerDebug("LLPRTAppraisalSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
		            Content =Content + "原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
	    //tResult = tLLPRTAppraisalUI.getResult();	
	    tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            loggerDebug("LLPRTAppraisalSave","没有得到要显示的数据文件");
	    }

    }    
    
    if (FlagStr.equals("Succ"))
    {    

      	ExeSQL tExeSQL = new ExeSQL();
	//获取临时文件名
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//-------------------------20070521--jinsh--------------------------------------------//
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strVFFileName ="vtsfile/"+ tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//-------------------------20070521--jinsh--------------------------------------------//
	//获取存放临时文件的路径
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("LLPRTAppraisalSave","strRealPath="+strRealPath);
	String strVFPathName = strRealPath + strVFFileName;
	
	//合并VTS文件
	CombineVts tcombineVts = null;
	String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	loggerDebug("LLPRTAppraisalSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("LLPRTAppraisalSave","==> Write VTS file to disk ");

	loggerDebug("LLPRTAppraisalSave","===strVFFileName : "+strVFFileName);
	session.putValue("RealPath", strVFPathName);
	session.putValue("PrtSeq", request.getParameter("PrtSeq"));
	loggerDebug("LLPRTAppraisalSave","==流水号 : "+session.getValue("PrtSeq"));
	//本来打算采用get方式来传递文件路径----传递路径读取文件
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
