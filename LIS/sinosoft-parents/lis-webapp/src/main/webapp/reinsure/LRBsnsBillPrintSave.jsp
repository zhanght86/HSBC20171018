<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRBsnsTabSave.jsp
//�����ܣ�F1��������
//�������ڣ�2007-2-8
//������  ��zhangbin
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
    String tBillNo [] = request.getParameterValues("BillPrintListGrid1"); //�˵����
    String tContNo [] = request.getParameterValues("BillPrintListGrid4"); //�ֱ���ͬ���
    String tRIcomCode[] = request.getParameterValues("BillPrintListGrid5"); //�ֱ���˾���
    //������ʽ=�� Inp+MulLine������+Sel�� 
	for (int index=0; index< tRadio.length;index++)
	{
		if("1".equals(tRadio[index]))
		{			
			String mContNo=tContNo[index]; //��ͬ����
			String mRIcomCode=tRIcomCode[index]; //�ֱ���˾
			String mBillNo = tBillNo[index]; //�˵����
			
			System.out.println("��ͬ���::"+mContNo);
			System.out.println("�ֱ���˾::"+mRIcomCode); 
			System.out.println("�˵����::"+mBillNo);
			
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
	   
	//���ϱ����ʵ�
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
	  	System.out.println("--------�ɹ�----------");  
		mResult = uiBusinessDelegate.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
	}

	//ExeSQL tExeSQL = new ExeSQL();
	/**��ȡ��ʱ�ļ���*/
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
	/**��ȡ�����ʱ�ļ���·��*/
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;
	
	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		/**�ϲ�VTS�ļ�*/
		String strTemplatePath = application.getRealPath("f1print/picctemplate/") + "\\";
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