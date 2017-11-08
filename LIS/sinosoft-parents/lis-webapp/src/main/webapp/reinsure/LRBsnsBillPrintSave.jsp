<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRBsnsTabSave.jsp
//程序功能：F1报表生成
//创建日期：2007-2-8
//创建人  ：zhangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.stat.antail.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.service.*" %>  


<%
	System.out.println("start");
	CError cError = new CError();
	boolean operFlag=true;

	String FlagStr = "";
	String Content = "";
	
	TransferData exportDate = new TransferData();
	
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
		
    String tRadio[] = request.getParameterValues("InpBillPrintListGridSel");  
    String tBillNo [] = request.getParameterValues("BillPrintListGrid1"); //账单编号
    String tContNo [] = request.getParameterValues("BillPrintListGrid4"); //分保合同编号
    String tRIcomCode[] = request.getParameterValues("BillPrintListGrid5"); //分保公司编号
    //参数格式=” Inp+MulLine对象名+Sel” 
	for (int index=0; index< tRadio.length;index++)
	{
		if("1".equals(tRadio[index]))
		{			
			String mContNo=tContNo[index]; //合同编码
			String mRIcomCode=tRIcomCode[index]; //分保公司
			String mBillNo = tBillNo[index]; //账单编号
			
			System.out.println("合同编号::"+mContNo);
			System.out.println("分保公司::"+mRIcomCode); 
			System.out.println("账单编号::"+mBillNo);
			
			exportDate.setNameAndValue("ContNo",mContNo);
			exportDate.setNameAndValue("RIcomCode",mRIcomCode);
			exportDate.setNameAndValue("BillNo",mBillNo); 
			
		}
	}
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tG);  
	tVData.addElement(exportDate);  
  
	XmlExport txmlExport = new XmlExport(); 
	   
	//保障保险帐单
	//LRBsnsBillPrintUI tLRBsnsBillPrintUI = new LRBsnsBillPrintUI();
		BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!uiBusinessDelegate.submitData(tVData,"PRINT","LRBsnsBillPrintUI"))
	{
	   	operFlag=false;
	   	Content=uiBusinessDelegate.getCErrors().getFirstError().toString();   
	   	FlagStr = "FAIL";              
	}
	else
	{  
	  	System.out.println("--------成功----------");  
		mResult = uiBusinessDelegate.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
	}

	//ExeSQL tExeSQL = new ExeSQL();
	/**获取临时文件名*/
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	//String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strFilePath="";       
	  BusinessDelegate tBusinessDelegateExeSQL=BusinessDelegate.getBusinessDelegate();
	  VData cInputDataExeSQL = new VData();
	  TransferData tTransferDataExeSQL = new TransferData();
	  tTransferDataExeSQL.setNameAndValue("SQL",strSql);
	  cInputDataExeSQL.add(tTransferDataExeSQL);
	  if(tBusinessDelegateExeSQL.submitData(cInputDataExeSQL,"getOneValue","ExeSQLUI"))
	  {
	  	strFilePath =(String) tBusinessDelegateExeSQL.getResult().getObjectByObjectName("String", 0);	
	  }   
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	/**获取存放临时文件的路径*/
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;
	
	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		/**合并VTS文件*/
		String strTemplatePath = application.getRealPath("f1print/picctemplate/") + "\\";
		System.out.println("strTemplatePath: "+strTemplatePath);
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		
		//把dataStream存储到磁盘文件
		System.out.println("存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		
		session.setAttribute("RealPath", strVFPathName);
		System.out.println("==> Write VTS file to disk ");
		
		System.out.println("===strVFFileName : "+strVFFileName);
		//本来打算采用get方式来传递文件路径
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