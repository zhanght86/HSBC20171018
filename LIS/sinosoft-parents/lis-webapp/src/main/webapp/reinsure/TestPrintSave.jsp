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
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
	
	}
	
	ExeSQL tExeSQL = new ExeSQL();
	//��ȡ��ʱ�ļ���
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//��ȡ�����ʱ�ļ���·��
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;
	
	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/picctemplate/") + "\\".replace('\\','/');
		System.out.println("strTemplatePath: "+strTemplatePath);
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		
		//��dataStream�洢�������ļ�
		System.out.println("�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		
		session.setAttribute("RealPath", strVFPathName);
		System.out.println("==> Write VTS file to disk ");
		
		System.out.println("===strVFFileName : "+strVFFileName);
	//�����������get��ʽ�������ļ�·��
	request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName).forward(request,response); 
	//response.sendRedirect("../f1print/GetF1PrintJ1_new.jsp?Code=03&RealPath="+strVFPathName); //PICCH��ʽ
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
