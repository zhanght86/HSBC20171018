<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRExportDataSub.jsp
//程序功能：F1报表生成
//创建日期：2007-2-8
//创建人  ：zhangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.stat.antail.*"%>
<%@page import="java.io.*"%>
<%
	System.out.println("============输出再保报表开始=============");
	CError cError = new CError();
	VData tVData = new VData();
	VData mResult = new VData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	boolean operFlag=true;
	String FlagStr = "";
	String Content = "";
	
//****************************
//  获取前台数据
//****************************
  	//caosg 2009-6-18,按管理机构生成报表数据
	//String strOperation = request.getParameter("OperateType");
	//String reRiskCode	= request.getParameter("ReRiskCode");//分保险种编号 
	String RValidate   	= request.getParameter("RValidate");
	String RInvalidate  = request.getParameter("RInvalidate");
	String contNo 		= request.getParameter("ContNo");//合同编号 
	String ricomCode	=	request.getParameter("RIcomCode");//分保公司编号 	
	String tempType		= request.getParameter("TempType");//保单类型
  	String manageCom    = request.getParameter("ManageCom");//管理机构
  	
 	System.out.println("统计起期: "+ RValidate );
 	System.out.println("统计止期: "+ RInvalidate );
	System.out.println("合同编号: "+ contNo);	
	System.out.println("分保公司: "+ ricomCode);
	System.out.println("管理机构: "+ manageCom);
	
	//System.out.println("报表类型: "+ reprotType);
	//System.out.println("ReRiskCode: "	+ reRiskCode);

//***************************	
//  前台数据封装
//***************************

	TransferData exportDate = new TransferData(); 
	//exportDate.setNameAndValue("ReprotType",reprotType); 
	//exportDate.setNameAndValue("ReRiskCode",reRiskCode); 
	exportDate.setNameAndValue("RValidate",RValidate); 
	exportDate.setNameAndValue("RInvalidate",RInvalidate); 
	exportDate.setNameAndValue("ContNo",contNo); 	
	exportDate.setNameAndValue("RIcomCode",ricomCode); 
	exportDate.setNameAndValue("TempType",tempType); 
	exportDate.setNameAndValue("ManageCom",manageCom); 

	tVData.addElement(tG);  
	tVData.addElement(exportDate);
  
	XmlExport txmlExport = new XmlExport();    
// ***************************************************
//下面是对可能出现得四个合同进行判断具体是针对那个报表
// ***************************************************


	GrQuotSharCESSTabUI tGrQuotSharCESSTabUI = new GrQuotSharCESSTabUI();
		
	if(!tGrQuotSharCESSTabUI.submitData(tVData,"PRINT"))
	{
		
		operFlag=false;
		Content=tGrQuotSharCESSTabUI.mErrors.getFirstError().toString();   
		FlagStr = "FAIL";   
		
	}else
	{  
		
	 	System.out.println("===========生成报表数据成功==========");  
		mResult = tGrQuotSharCESSTabUI.getResult();			
	 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	 	FlagStr="Succ";
	 	if(txmlExport==null)
	 	{
	  		operFlag=false;
	  		Content=""+"没有得到要显示的数据文件"+"";	  
	 	}
	}  
	  
	ExeSQL tExeSQL = new ExeSQL();
	//获取临时文件名
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//获取存放临时文件的路径
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;
	
	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/picctemplate/") + "\\".replace('\\','/');
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
		//response.sendRedirect("../f1print/GetF1PrintJ1_new.jsp?Code=03&RealPath="+strVFPathName); //PICCH方式
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
