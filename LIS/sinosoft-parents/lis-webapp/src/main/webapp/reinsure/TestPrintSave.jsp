<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.test.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	boolean operFlag = true;
	String Content = "";
	String FlagStr = "";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
		
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tG);  
	  
	XmlExport txmlExport = new XmlExport(); 
	//PrintUI tPrintUI = new PrintUI();
	
	if(!tBusinessDelegate.submitData(tVData, "" ,"PrintUI")){
		operFlag=false;
     	Content=tBusinessDelegate.getCErrors().getFirstError().toString();   
     	FlagStr = "FAIL";
	}else{
		mResult = tBusinessDelegate.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null){
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
	
	}
	
	ExeSQL tExeSQL = new ExeSQL();
	//获取临时文件名
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//获取存放临时文件的路径
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
