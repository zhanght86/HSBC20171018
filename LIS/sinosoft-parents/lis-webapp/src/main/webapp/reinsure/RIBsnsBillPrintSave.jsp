<%@include file="/i18n/language.jsp"%>
<%@include file="/i18n/language.jsp"%>
<%@include file="/i18n/language.jsp"%>
<%
//绋嬪簭鍚嶇О锛�?BsnsTabSave.jsp
//绋嬪簭鍔熻兘锛欶1鎶ヨ〃鐢熸垚
//鍒涘缓鏃ユ湡锛�2007-2-8
//鍒涘缓浜�? 锛歾hangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.stat.antail.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="java.io.*"%>

<%
	CError cError = new CError();
	boolean operFlag=true;

	String FlagStr = "";
	String Content = "";
	
	TransferData exportDate = new TransferData();
	
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
		
    String tRadio[] = request.getParameterValues("InpBillPrintListGridSel");  
    String tBillNo [] = request.getParameterValues("BillPrintListGrid1"); //璐﹀崟缂栧彿
    String tBillTimes [] = request.getParameterValues("BillPrintListGrid3"); //璐﹀崟鏈熸
    String tContNo [] = request.getParameterValues("BillPrintListGrid4"); //鍒嗕繚鍚堝悓缂栧�?
    String tRIcomCode[] = request.getParameterValues("BillPrintListGrid5"); //鍒嗕繚鍏�?��缂栧�?
    String tBillBatchNo[] = request.getParameterValues("BillPrintListGrid10"); //璐﹀崟鎵规鍙�
    String tCurrency[] = request.getParameterValues("BillPrintListGrid11"); //甯佺�?
    //鍙傛暟鏍煎紡=鈥� Inp+MulLine瀵硅薄鍚�?Sel鈥� 
	for (int index=0; index< tRadio.length;index++)
	{
		if("1".equals(tRadio[index]))
		{
			exportDate.setNameAndValue("RIContNo",tContNo[index]);
			exportDate.setNameAndValue("RIComCode",tRIcomCode[index]);
			exportDate.setNameAndValue("BillNo",tBillNo[index]); 
			exportDate.setNameAndValue("BillTimes",tBillTimes[index]); 
			exportDate.setNameAndValue("BillBatchNo",tBillBatchNo[index]); 
			exportDate.setNameAndValue("Currency",tCurrency[index]); 
		}
	}
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tG);  
	tVData.addElement(exportDate);  
  
	XmlExport txmlExport = new XmlExport(); 
	   
	//淇濋殰淇濋櫓甯愬�?
	RIBsnsBillPrintUI tRIBsnsBillPrintUI = new RIBsnsBillPrintUI();
	if(!tRIBsnsBillPrintUI.submitData(tVData,"PRINT"))
	{
	   	operFlag=false;
	   	Content=tRIBsnsBillPrintUI.mErrors.getFirstError().toString();   
	   	FlagStr = "FAIL";              
	}
	else
	{  

		mResult = tRIBsnsBillPrintUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
	}
	ExeSQL tExeSQL = new ExeSQL();
	/**鑾峰彇涓存椎鏂囦欢鍚�?/
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	/**鑾峰彇�?樻斁涓存椂鏂囦欢鐨勮矾寰�?/
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;
	
	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		String strTemplatePath = application.getRealPath("f1print/picctemplate/") + "\\";

		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		
		session.setAttribute("RealPath", strVFPathName);
		

		request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName).forward(request,response); 
		//response.sendRedirect("../f1print/GetF1PrintJ1_new.jsp?Code=03&RealPath="+strVFPathName); //PICCH
	}
	else
	{
    	FlagStr = "Fail";
%>
<html>

<script type="text/javascript">	
	myAlert("<%=Content%>");
	top.close();	
	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");		
</script>
</html>
<%
  	}
%>
