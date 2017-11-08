<%
//**************************************************************************************************
//文件名称：ClaimCheckPrintSave.jsp
//程序功能：收付费--收 费--理赔二核收费发票打印
//创建日期：2006-03-09
//创建人  ：zhaorx
//更新记录：
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.report.f1report.*"%>

<%
    loggerDebug("ClaimCheckPrintSave","开始执行：收付费--收 费--理赔二核收费发票打印操作");
    String Content = "";
    CErrors tError = null;
    String FlagStr = "Fail";

    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    if(tG == null) 
    {
    	 loggerDebug("ClaimCheckPrintSave","登录信息没有获取!!!");
    	 return;
    }  
    loggerDebug("ClaimCheckPrintSave","##########"+tG.ManageCom); 
  
    String tClmNo = request.getParameter("ClmNo");	
    loggerDebug("ClaimCheckPrintSave","小妖儿测试@@@@@@@@@@@@:"+request.getParameter("ClmNo"));
  
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ABClmNo",tClmNo);  //赔案号  
    // 准备传输数据 VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add(tG);        
    tVData.add(tTransferData);        

    ClaimCheckPrintUI tClaimCheckPrintUI = new ClaimCheckPrintUI();
    
    if (tClaimCheckPrintUI.submitData(tVData,"") == false)
    {
            Content = Content + "原因是: " + tClaimCheckPrintUI.mErrors.getError(0).errorMessage;
            FlagStr = "FAIL";    
    }
    else
    {
	    tResult = tClaimCheckPrintUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
           	loggerDebug("ClaimCheckPrintSave","没有得到要显示的数据文件");
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
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("ClaimCheckPrintSave","strRealPath="+strRealPath);
	String strVFPathName = strRealPath + strVFFileName;
	CombineVts tcombineVts = null;
	//合并VTS文件
	String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	loggerDebug("ClaimCheckPrintSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("ClaimCheckPrintSave","==> Write VTS file to disk ");

	loggerDebug("ClaimCheckPrintSave","===strVFFileName : "+strVFFileName);
	session.putValue("RealPath", strVFPathName);
	//本来打算采用get方式来传递文件路径
	//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
	//response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
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

