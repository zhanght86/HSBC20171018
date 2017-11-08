<%
//**************************************************************************************************
//程序名称：LLUserDefinedBillPrtSave.jsp
//程序功能：自定义单证
//修改人:niuzj
//修改日期:2005-09-20
//修改原因:由原来打印一联改成打印两联
//修改人:niuzj
//修改日期:2005-10-04
//修改原因:把打印两联重新修改为只打印一联(重新GET原来版本)
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
    	loggerDebug("LLUserDefinedBillPrtSave","登录信息没有获取!!!");
       	return;
    } 

    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("CustNo"));
    
      //接收MulLine表格中数据集合   
     String tGridNo[] = request.getParameterValues("AffixGridNo");  //得到序号列的所有值
     String tGrid1[] = request.getParameterValues("AffixGrid1"); // 单证代码
     String tGrid2[] = request.getParameterValues("AffixGrid2"); // 单证名称
     String tGrid3[] = request.getParameterValues("AffixGrid3"); // 是否提交
     String tGrid4[] = request.getParameterValues("AffixGrid4"); // 页数
     String tGrid5[] = request.getParameterValues("AffixGrid5"); // 提交形式
	 String tGrid6[] = request.getParameterValues("AffixGrid6"); // 是否退还原件

	 LLAffixSet tLLAffixSet=new LLAffixSet(); //报案附件表
//     int Count = tGridNo.length; //得到接受到的记录数
	for(int index=0;index < tGridNo.length;index++)
    {
		LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		tLLAffixSchema.setAffixCode(tGrid1[index]); //单证代码
		tLLAffixSchema.setAffixName(tGrid2[index]); //单证名称
		tLLAffixSchema.setSubFlag(tGrid3[index]); //是否提交           
		tLLAffixSchema.setReadyCount(tGrid4[index]); //页数  
		tLLAffixSchema.setProperty(tGrid5[index]); //单证属性标志
		tLLAffixSchema.setReturnFlag(tGrid6[index]); //是否退还原件
		tLLAffixSet.add(tLLAffixSchema);                   	
    }
    // 准备传输数据 VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add(tG);        
    tVData.add(tTransferData );        
    tVData.add(tLLAffixSet);   
    
    LLPRTUserDefinedBillUI tLLPRTUserDefinedBillUI = new LLPRTUserDefinedBillUI();
    if (tLLPRTUserDefinedBillUI.submitData(tVData,"") == false)
    {
		Content ="原因是: " + tLLPRTUserDefinedBillUI.mErrors.getError(1).errorMessage;
		FlagStr = "FAIL";   
    }
    else
    {
	    tResult = tLLPRTUserDefinedBillUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            loggerDebug("LLUserDefinedBillPrtSave","没有得到要显示的数据文件");
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
		loggerDebug("LLUserDefinedBillPrtSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		
		CombineVts tcombineVts = null;
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
	
		//把dataStream存储到磁盘文件
		loggerDebug("LLUserDefinedBillPrtSave","存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLUserDefinedBillPrtSave","==> Write VTS file to disk ");
	
		loggerDebug("LLUserDefinedBillPrtSave","===strVFFileName : "+strVFFileName);
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
