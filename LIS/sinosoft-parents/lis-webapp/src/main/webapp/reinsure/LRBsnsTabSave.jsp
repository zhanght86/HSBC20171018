<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRBsnsTabSave.jsp
//�����ܣ�F1��������
//�������ڣ�2007-2-8
//������  ��zhangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.report.f1report.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="com.sinosoft.lis.reinsure.stat.zhongyi.*"%>
<%@page import="com.sinosoft.service.*" %> 
<%@page import="java.io.*"%>
<%
	System.out.println("start");
	CError cError = new CError();
	boolean operFlag=true;
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = request.getParameter("OperateType");;
	String mStaTerm=request.getParameter("StaTerm");
	String mContNo=request.getParameter("ContNo");
	String mAccountType=request.getParameter("AccountType");
	String mRIcomCode=request.getParameter("RIcomCode");
	String mRIcomName=request.getParameter("RIcomName");
	String tempType			= request.getParameter("TempType"); //��������
		
	System.out.println("ͳ���ڼ�::"+mStaTerm);
	System.out.println("��ͬ���::"+mContNo);
	System.out.println("�ʵ�����::"+mAccountType);
	System.out.println("�ֱ���˾::"+mRIcomCode);  
	
	TransferData exportDate = new TransferData();
	exportDate.setNameAndValue("StaTerm",mStaTerm);
	exportDate.setNameAndValue("ContNo",mContNo);
	exportDate.setNameAndValue("AccountType",mAccountType);
	exportDate.setNameAndValue("RIcomCode",mRIcomCode);
	exportDate.setNameAndValue("RIcomName",mRIcomName);
	exportDate.setNameAndValue("TempType",tempType); 

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tG);  
	tVData.addElement(exportDate);  
  
	XmlExport txmlExport = new XmlExport(); 
	
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	   
	if(mAccountType.equals("010001"))
	{ //���屣�ϱ����ʵ�
		
		String busiName="GrQuotSharAccountUI";			
		//GrQuotSharAccountUI tGrQuotSharAccountUI = new GrQuotSharAccountUI();
		if(!tBusinessDelegate.submitData( tVData, "PRINT",busiName))
		{
		   	operFlag=false;
		   	Content=tBusinessDelegate.getCErrors().getError(0).errorMessage;   
		   	FlagStr = "FAIL";              
		}
		else
		{  
			System.out.println("--------�ɹ�----------");  
			mResult = tBusinessDelegate.getResult();			
			txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
			FlagStr="Succ";
			if(txmlExport==null)
			{
		 		operFlag=false;
		 		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
			}
		}
	}
	else if(mAccountType.equals("020001"))
	{ //������ͨ��������� �����ʵ�
		//GrpubAcciSumAccountUI tGrpubAcciSumAccountUI = new GrpubAcciSumAccountUI();
		String busiName="GrpubAcciSumAccountUI";
		
		if(!tBusinessDelegate.submitData( tVData, "PRINT",busiName))
		{
		   	operFlag=false;
		   	Content=tBusinessDelegate.getCErrors().getFirstError().toString();   
		   	FlagStr = "FAIL";              
		}
		else
		{  
		 	System.out.println("--------�ɹ�----------");  
			mResult = tBusinessDelegate.getResult();			
		 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		 	FlagStr="Succ";
		 	if(txmlExport==null)
		 	{
		  		operFlag=false;
		  		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		 	}
		}
	}
	else if(mAccountType.equals("020002"))
	{ //������ͨ��������� �ֱ��ʵ�
		//GrpubAcciReinAccountUI tGrpubAcciReinAccountUI = new GrpubAcciReinAccountUI();
		String busiName="GrpubAcciReinAccountUI";
		
		if(!tBusinessDelegate.submitData( tVData, "PRINT",busiName))
		{
	    	operFlag=false;
	    	Content=tBusinessDelegate.getCErrors().getFirstError().toString();   
	    	FlagStr = "FAIL";              
		}
		else
		{  
		 	System.out.println("--------�ɹ�----------");  
			mResult = tBusinessDelegate.getResult();			
		 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		 	FlagStr="Succ";
		 	if(txmlExport==null)
		 	{
		  		operFlag=false;
		  		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		 	}
		}
	}	
	else if(mAccountType.equals("030001"))
	{ //���徳��ҽ�ƾ�Ԯ  �����ʵ�
		//GrpOutMedicSumAccountUI tGrpOutMedicSumAccountUI = new GrpOutMedicSumAccountUI();
		String busiName="GrpOutMedicSumAccountUI";
	
		if(!tBusinessDelegate.submitData( tVData, "PRINT",busiName))
		{
	    	operFlag=false;
	    	Content=tBusinessDelegate.getCErrors().getFirstError().toString();   
	    	FlagStr = "FAIL";              
		}
		else
		{  
		 	System.out.println("--------�ɹ�----------");  
			mResult = tBusinessDelegate.getResult();			
		 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		 	FlagStr="Succ";
		 	if(txmlExport==null)
		 	{
		  		operFlag=false;
		  		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		 	}
		}
	}
	else if(mAccountType.equals("030002"))
	{	//���徳�����ҽ�ƾ�Ԯ �ֱ��ʵ�
		//GrpOutMedicReinAccountUI tGrpOutMedicReinAccountUI = new GrpOutMedicReinAccountUI();
		String busiName="GrpOutMedicReinAccountUI";
		if(!tBusinessDelegate.submitData( tVData, "PRINT",busiName))
		{
		   	operFlag=false;
		   	Content=tBusinessDelegate.getCErrors().getFirstError().toString();   
		   	FlagStr = "FAIL";              
		}
		else
		{  
		  	System.out.println("--------�ɹ�----------");  
			mResult = tBusinessDelegate.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		  	}
		}
	}
	else if(mAccountType.equals("040001"))
	{	//�������� �ʵ�
		//GrpLifeExAccountUI tGrpLifeExAccountUI = new GrpLifeExAccountUI();
		String busiName="GrpLifeExAccountUI";
		if(!tBusinessDelegate.submitData( tVData, "PRINT",busiName))
		{
		   	operFlag=false;
		   	Content=tBusinessDelegate.getCErrors().getFirstError().toString();   
		   	FlagStr = "FAIL";              
		}
		else
		{  
		 	System.out.println("--------�ɹ�----------");  
			mResult = tBusinessDelegate.getResult();			
		 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		 	FlagStr="Succ";
		 	if(txmlExport==null)
		 	{
		  		operFlag=false;
		  		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		 	}
		}
	}

	ExeSQL tExeSQL = new ExeSQL();
	/**��ȡ��ʱ�ļ���*/
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	//String strFilePath = tExeSQL.getOneValue(strSql);
	
	TransferData sTransferData=new TransferData();
	sTransferData.setNameAndValue("SQL", strSql);
	VData sVData = new VData();
	sVData.add(sTransferData);
	String strFilePath = "";
	
	if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
	{
		strFilePath = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
	}
	

	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
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