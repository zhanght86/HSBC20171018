<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRExportDataSub.jsp
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
	//String strOperation = request.getParameter("OperateType");
	//String reRiskCode	= request.getParameter("ReRiskCode");//�ֱ����ֱ�� 
	String RValidate   	= request.getParameter("RValidate");
	String RInvalidate  = request.getParameter("RInvalidate");
	String contNo 		= request.getParameter("ContNo");//��ͬ��� 
	String ricomCode	=	request.getParameter("RIcomCode");//�ֱ���˾��� 	
	String tempType		= request.getParameter("TempType");//��������
  	String manageCom    = request.getParameter("ManageCom");//�������
  	
 	System.out.println("ͳ������: "+ RValidate );
 	System.out.println("ͳ��ֹ��: "+ RInvalidate );
	System.out.println("��ͬ���: "+ contNo);	
	System.out.println("�ֱ���˾: "+ ricomCode);
	System.out.println("�������: "+ manageCom);
	
	//System.out.println("��������: "+ reprotType);
	//System.out.println("ReRiskCode: "	+ reRiskCode);

//***************************	
//  ǰ̨���ݷ�װ
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
//�����ǶԿ��ܳ��ֵ��ĸ���ͬ�����жϾ���������Ǹ�����
// ***************************************************


	GrQuotSharCESSTabUI tGrQuotSharCESSTabUI = new GrQuotSharCESSTabUI();
		
	if(!tGrQuotSharCESSTabUI.submitData(tVData,"PRINT"))
	{
		
		operFlag=false;
		Content=tGrQuotSharCESSTabUI.mErrors.getFirstError().toString();   
		FlagStr = "FAIL";   
		
	}else
	{  
		
	 	System.out.println("===========���ɱ������ݳɹ�==========");  
		mResult = tGrQuotSharCESSTabUI.getResult();			
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
