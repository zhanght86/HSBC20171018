<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRExportDataSub.jsp
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
<%@page import="java.io.*"%>
<%
	System.out.println("start");
  CError cError = new CError();
  boolean operFlag=true;
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	
	String strOperation = request.getParameter("OperateType");
  String staTerm   		= request.getParameter("StaTerm"); 
  String contNo 			= request.getParameter("ContNo");//��ͬ��� 
	String reprotType 	= request.getParameter("ReprotType");//�������� 
  String ricomCode		=	request.getParameter("RIcomCode");//�ֱ���˾��� 
  String reRiskCode		= request.getParameter("ReRiskCode");//�ֱ����ֱ�� 
  String tempType			= request.getParameter("TempType");//��������
  
 	System.out.println("StaTerm: "+ staTerm );
  System.out.println("��ͬ���: "+ contNo);
  System.out.println("��������: "+ reprotType);
  System.out.println("rIcomCode: "	+ ricomCode);
  System.out.println("ReRiskCode: "	+ reRiskCode);
  
  TransferData exportDate = new TransferData(); 
  
  exportDate.setNameAndValue("StaTerm",staTerm); 
  exportDate.setNameAndValue("ContNo",contNo); 
  exportDate.setNameAndValue("ReprotType",reprotType); 
  exportDate.setNameAndValue("RIcomCode",ricomCode); 
  exportDate.setNameAndValue("ReRiskCode",reRiskCode); 
	exportDate.setNameAndValue("TempType",tempType); 
	
  GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
  tVData.addElement(tG);  
  tVData.addElement(exportDate);
  
	XmlExport txmlExport = new XmlExport();    
// ***************************************************
//�����ǶԿ��ܳ��ֵ��ĸ���ͬ�����жϾ���������Ǹ�����
// ***************************************************

//���������ͬ���б�����
if(reprotType.equals("010001")){ 
		GrQuotSharCESSTabUI tGrQuotSharCESSTabUI = new GrQuotSharCESSTabUI();
	  if(!tGrQuotSharCESSTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrQuotSharCESSTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrQuotSharCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	 }  
	//���������ͬ�����ⱨ��
	else if(reprotType.equals("010002")){ 
		GrQuotSharClaimTabUI tGrQuotSharClaimTabUI = new GrQuotSharClaimTabUI();
	  if(!tGrQuotSharClaimTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrQuotSharClaimTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrQuotSharClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	 }                    
	//2.���幫����ͨ���������ֱ���ͬ 
	//���幫����ͨ���������ֱ���ͬ���б�����
 	else if(reprotType.equals("020001")){ 
		GrpubAcciCESSTabUI tGrpubAcciCESSTabUI = new GrpubAcciCESSTabUI();
	  if(!tGrpubAcciCESSTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpubAcciCESSTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpubAcciCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		} 
	} 
	//���幫����ͨ���������ֱ���ͬ���������
	else if(reprotType.equals("020002")){ 
		GrpubAcciChageTabUI tGrpubAcciChageTabUI = new GrpubAcciChageTabUI();
	  if(!tGrpubAcciChageTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpubAcciChageTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpubAcciChageTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	} 
	//���幫����ͨ���������ֱ���ͬ�����ⱨ��
 	else if(reprotType.equals("020003")){ 
		GrpubAcciClaimTabUI tGrpubAcciClaimTabUI = new GrpubAcciClaimTabUI();
	  if(!tGrpubAcciClaimTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpubAcciClaimTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpubAcciClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
 	} 
 	/**
 	* 3.���徳�������Ԯҽ�Ʊ��ճ����ֱ���ͬ
 	* �б�����
 	*/
	else if(reprotType.equals("030001")){ 
		GrpOutMedicCESSTabUI tGrpOutMedicCESSTabUI = new GrpOutMedicCESSTabUI();
	  if(!tGrpOutMedicCESSTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpOutMedicCESSTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpOutMedicCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	 } 
else if(reprotType.equals("030002")){ //---�³�������
		GrpOutMedicUndoTabUI tGrpOutMedicUndoTabUI = new GrpOutMedicUndoTabUI();
	  if(!tGrpOutMedicUndoTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpOutMedicUndoTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpOutMedicUndoTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	 }
else if(reprotType.equals("030003")){ //�����ⱨ��
		GrpOutMedicClaimTabUI tGrpOutMedicClaimTabUI = new GrpOutMedicClaimTabUI();
	  if(!tGrpOutMedicClaimTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpOutMedicClaimTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpOutMedicClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
 }
//4.�����������ֱ���ͬ
else if(reprotType.equals("040001")){ //---�б�����
		GrpLifeExCESSTabUI  tGrpLifeExCESSTabUI = new GrpLifeExCESSTabUI();
	  if(!tGrpLifeExCESSTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpLifeExCESSTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpLifeExCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	 } 
else if(reprotType.equals("040002")){ //���������
		GrpLifeExChangeTabUI tGrpLifeExChangeTabUI = new GrpLifeExChangeTabUI();
	  if(!tGrpLifeExChangeTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpLifeExChangeTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpLifeExChangeTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
	 } 
else  if(reprotType.equals("040003")){ //�����ⱨ��
		GrpLifeExClaimTabUI tGrpLifeExClaimTabUI = new GrpLifeExClaimTabUI();
	  if(!tGrpLifeExClaimTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpLifeExClaimTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = tGrpLifeExClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
}else  if(reprotType.equals("040004")){ //�����ܱ���
	/**
	  SumaryReportUI tSumaryReportUI = new SumaryReportUI();
	  if(!tSumaryReportUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tSumaryReportUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
			System.out.println("--------�ɹ�----------");  
			mResult = tSumaryReportUI.getResult();			
			txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
			FlagStr="Succ";
			if(txmlExport==null)
			{
					operFlag=false;
					Content="û�еõ�Ҫ��ʾ�������ļ�";	  
			}
	  }
	  */
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
