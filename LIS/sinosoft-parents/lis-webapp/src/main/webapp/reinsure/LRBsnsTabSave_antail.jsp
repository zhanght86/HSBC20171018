<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRBsnsTabSave.jsp
//�����ܣ�F1��������
//�������ڣ�2007-2-8
//������  ��zhangbin
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.report.f1report.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.stat.zhongyi.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.service.*" %>  

<%
	System.out.println("start");
	CError cError = new CError();
	boolean operFlag=true;
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = request.getParameter("OperateType");;
	String mStartDate=request.getParameter("StartDate");
	String mEndDate=request.getParameter("EndDate");
	String mContNo=request.getParameter("ContNo");
	String mAccountType=request.getParameter("AccountType");
	String mRIcomCode=request.getParameter("RIcomCode");
	String mRIcomName=request.getParameter("RIcomName");
	String tempType			= request.getParameter("TempType"); //��������
		
	System.out.println("ͳ������::"+mStartDate);
	System.out.println("ͳ��ֹ��::"+mEndDate);
	System.out.println("��ͬ���::"+mContNo);
	System.out.println("�ʵ�����::"+mAccountType);
	System.out.println("�ֱ���˾::"+mRIcomCode);  
  
	TransferData exportDate = new TransferData();
	exportDate.setNameAndValue("StartDate",mStartDate);
	exportDate.setNameAndValue("EndDate",mEndDate);
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
	   
	if(mAccountType.equals("010001"))
	{
		//���屣�ϱ����ʵ�
		//GrQuotSharAccountUI tGrQuotSharAccountUI = new GrQuotSharAccountUI();
		BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!uiBusinessDelegate.submitData(tVData,"PRINT","GrQuotSharAccountUI"))
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
	}
	else if(mAccountType.equals("010002"))
	{
		//������ͨ��������� �����ʵ�
		//GrpubAcciSumAccountUI tGrpubAcciSumAccountUI = new GrpubAcciSumAccountUI();
		BusinessDelegate uiBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
		if(!uiBusinessDelegate1.submitData(tVData,"PRINT","GrpubAcciSumAccountUI"))
		{
		   	operFlag=false;
		   	Content=uiBusinessDelegate1.getCErrors().getFirstError().toString();   
		   	FlagStr = "FAIL";              
		}
		else
		{  
		  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate1.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		  	}
		}
	}else if(mAccountType.equals("010003"))
	{
		//������ͨ��������� �ֱ��ʵ�
		//GrpubAcciReinAccountUI tGrpubAcciReinAccountUI = new GrpubAcciReinAccountUI();
		BusinessDelegate uiBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
		if(!uiBusinessDelegate2.submitData(tVData,"PRINT","GrpubAcciReinAccountUI"))
		{
	     	operFlag=false;
	     	Content=uiBusinessDelegate2.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
		}
		else
		{  
		  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate2.getResult();			
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
	{
		//���徳��ҽ�ƾ�Ԯ  �����ʵ�
		//GrpOutMedicSumAccountUI tGrpOutMedicSumAccountUI = new GrpOutMedicSumAccountUI();
		BusinessDelegate uiBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
		if(!uiBusinessDelegate3.submitData(tVData,"PRINT","GrpOutMedicSumAccountUI"))
		{
	     	operFlag=false;
	     	Content=uiBusinessDelegate3.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
		}
		else
		{  
		  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate3.getResult();			
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
	{
		//���徳�����ҽ�ƾ�Ԯ �ֱ��ʵ�
		//GrpOutMedicReinAccountUI tGrpOutMedicReinAccountUI = new GrpOutMedicReinAccountUI();
		BusinessDelegate uiBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
		if(!uiBusinessDelegate4.submitData(tVData,"PRINT","GrpOutMedicReinAccountUI"))
		{
		   	operFlag=false;
		   	Content=uiBusinessDelegate4.getCErrors().getFirstError().toString();   
		   	FlagStr = "FAIL";              
		}
		else
		{  
		  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate4.getResult();			
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
	{
		//�����������ֱ���ͬ�ʵ�
		//GrpLifeExAccountUI tGrpLifeExAccountUI = new GrpLifeExAccountUI();
		BusinessDelegate uiBusinessDelegate5=BusinessDelegate.getBusinessDelegate();
		if(!uiBusinessDelegate5.submitData(tVData,"PRINT","GrpLifeExAccountUI"))
		{
		   	operFlag=false;
		   	Content=uiBusinessDelegate5.getCErrors().getFirstError().toString();   
		   	FlagStr = "FAIL";              
		}
		else
		{  
			System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate5.getResult();			
		 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		 	FlagStr="Succ";
		 	if(txmlExport==null)
		 	{
		  		operFlag=false;
		  		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
		 	}
		}
	}

	//ExeSQL tExeSQL = new ExeSQL();
	/**��ȡ��ʱ�ļ���*/
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	//String strFilePath = tExeSQL.getOneValue(strSql);
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