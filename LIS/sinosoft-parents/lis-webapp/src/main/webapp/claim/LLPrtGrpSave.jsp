 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>

<%
    //准备通用参数
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
   
    if(tG == null) 
    {
        loggerDebug("LLPrtGrpSave","登录信息没有获取!!!");
        return;
     } 
		
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("GrpClmNo",request.getParameter("GrpClmNo")); //团体赔案号
    String tOperator = request.getParameter("transact");
    // 准备传输数据 VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add( tG );        
    tVData.add( tTransferData );          
        
    //团体医疗给付清单打印
    if(tOperator.equals("MedBillGrp"))
    {
			   LLPRTMedBillGrpUI tLLPRTMedBillGrpUI = new LLPRTMedBillGrpUI();    
			   if (tLLPRTMedBillGrpUI.submitData(tVData,"") == false)
			   {
			       Content = Content + "打印失败，原因是: " + tLLPRTMedBillGrpUI.mErrors.getError(0).errorMessage;
			       FlagStr = "FAIL";     
			   }
			   else
			   {
					   tResult = tLLPRTMedBillGrpUI.getResult();	    
					   txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
					   FlagStr="Succ";
					   if(txmlExport==null)
					   {
					       FlagStr="FAIL";
					       Content="没有得到要显示的数据文件";
				         loggerDebug("LLPrtGrpSave","没有得到要显示的数据文件");
					   }
			   }    
    }
    //团体批单给付批注打印
    if(tOperator.equals("PostilGrp"))
    {
			   LLPRTPostilGrpUI tLLPRTPostilGrpUI = new LLPRTPostilGrpUI();    
			   if (tLLPRTPostilGrpUI.submitData(tVData,"") == false)
			   { 
			       Content = Content + "原因是: " + tLLPRTPostilGrpUI.mErrors.getError(0).errorMessage;
			       FlagStr = "FAIL";     
			   }
			   else
			   {
					   tResult = tLLPRTPostilGrpUI.getResult();	    
					   txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
					   FlagStr="Succ";
					   if(txmlExport==null)
					   {
					       FlagStr="FAIL";
					       Content="没有得到要显示的数据文件";
				         loggerDebug("LLPrtGrpSave","没有得到要显示的数据文件");
					   }
			   } 
     }    
        		
    if (FlagStr.equals("Succ"))
    {    
      	ExeSQL tExeSQL = new ExeSQL();
				//获取临时文件名
				String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
				String strFilePath = tExeSQL.getOneValue(strSql);
				String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
				//获取存放临时文件的路径
				String strRealPath = application.getRealPath("/").replace('\\','/');
				loggerDebug("LLPrtGrpSave","strRealPath="+strRealPath);
				String strVFPathName = strRealPath + strVFFileName;
				CombineVts tcombineVts = null;
				//合并VTS文件
				String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
				tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				tcombineVts.output(dataStream);
			
				//把dataStream存储到磁盘文件
				loggerDebug("LLPrtGrpSave","存储文件到"+strVFPathName);
				AccessVtsFile.saveToFile(dataStream,strVFPathName);
				loggerDebug("LLPrtGrpSave","==> Write VTS file to disk ");
			
				loggerDebug("LLPrtGrpSave","===strVFFileName : "+strVFFileName);
				session.putValue("RealPath", strVFPathName);
				request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request,response);
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

