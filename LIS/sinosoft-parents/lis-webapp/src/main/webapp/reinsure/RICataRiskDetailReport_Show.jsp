<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�RICataRiskDetailReport_Show.jsp
//�����ܣ�F1��������
//�������ڣ�2007-2-8
//������  ��zhangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.stat.antail.*"%>
<%@page import="java.io.*"%>
<%
	System.out.println("============����ٱ�����ʼ=============");
	CError cError = new CError();
	VData tVData = new VData();
	VData mResult = new VData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	boolean operFlag=true;
	String FlagStr = "";
	String Content = "";
	
//****************************
//  ��ȡǰ̨����
//****************************
  	//caosg 2009-6-18,������������ɱ�������
	String tableType   	= request.getParameter("tableType");
	String EndDate  = request.getParameter("EndDate");

	
	//System.out.println("��������: "+ reprotType);
	//System.out.println("ReRiskCode: "	+ reRiskCode);

//***************************	
//  ǰ̨���ݷ�װ
//***************************

	TransferData exportDate = new TransferData(); 
	exportDate.setNameAndValue("tableType",tableType); 
	exportDate.setNameAndValue("EndDate",EndDate); 

	tVData.addElement(tG);  
	tVData.addElement(exportDate);
  
	XmlExport txmlExport = new XmlExport();    
// ***************************************************
//�����ǶԿ��ܳ��ֵ��ĸ���ͬ�����жϾ���������Ǹ�����
// ***************************************************


	RICataRiskUI tRICataRiskUI = new RICataRiskUI();
		
	if(!tRICataRiskUI.submitData(tVData,"PRINT"))
	{
		
		operFlag=false;
		Content=tRICataRiskUI.mErrors.getFirstError().toString();   
		FlagStr = "FAIL";   
		
	}else
	{  
		
	 	System.out.println("===========���ɱ������ݳɹ�==========");  
		mResult = tRICataRiskUI.getResult();			
	 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	 	FlagStr="Succ";
	 	if(txmlExport==null)
	 	{
	  		operFlag=false;
	  		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	 	}
	}  
	  
	ExeSQL tExeSQL = new ExeSQL();
	//��ȡ��ʱ�ļ���
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//��ȡ�����ʱ�ļ���·��
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
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
