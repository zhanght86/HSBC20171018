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
<%@page import="com.sinosoft.service.*" %>  
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
		//GrQuotSharCESSTabUI tGrQuotSharCESSTabUI = new GrQuotSharCESSTabUI();
	  	BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  
	  if(!uiBusinessDelegate.submitData(tVData,"PRINT","GrQuotSharCESSTabUI"))
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
	//���������ͬ�����ⱨ��
	else if(reprotType.equals("010002")){ 
		//GrQuotSharClaimTabUI tGrQuotSharClaimTabUI = new GrQuotSharClaimTabUI();
	  	  	BusinessDelegate uiBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
	  
	  if(!uiBusinessDelegate1.submitData(tVData,"PRINT","GrQuotSharClaimTabUI"))
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
	 }                    
	//2.���幫����ͨ���������ֱ���ͬ 
	//���幫����ͨ���������ֱ���ͬ���б�����
 	else if(reprotType.equals("020001")){ 
		//GrpubAcciCESSTabUI tGrpubAcciCESSTabUI = new GrpubAcciCESSTabUI();
		BusinessDelegate uiBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
		
	  if(!uiBusinessDelegate2.submitData(tVData,"PRINT","GrpubAcciCESSTabUI"))
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
	//���幫����ͨ���������ֱ���ͬ���������
	else if(reprotType.equals("020002")){ 
		//GrpubAcciChageTabUI tGrpubAcciChageTabUI = new GrpubAcciChageTabUI();
	  		BusinessDelegate uiBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
	  
	  if(!uiBusinessDelegate3.submitData(tVData,"PRINT","GrpubAcciChageTabUI"))
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
	//���幫����ͨ���������ֱ���ͬ�����ⱨ��
 	else if(reprotType.equals("020003")){ 
		//GrpubAcciClaimTabUI tGrpubAcciClaimTabUI = new GrpubAcciClaimTabUI();
	  	BusinessDelegate uiBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate4.submitData(tVData,"PRINT","GrpubAcciClaimTabUI"))
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
 	/**
 	* 3.���徳�������Ԯҽ�Ʊ��ճ����ֱ���ͬ
 	* �б�����
 	*/
	else if(reprotType.equals("030001")){ 
		//GrpOutMedicCESSTabUI tGrpOutMedicCESSTabUI = new GrpOutMedicCESSTabUI();
	  BusinessDelegate uiBusinessDelegate5=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate5.submitData(tVData,"PRINT","GrpOutMedicCESSTabUI"))
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
else if(reprotType.equals("030002")){ //---�³�������
		//GrpOutMedicUndoTabUI tGrpOutMedicUndoTabUI = new GrpOutMedicUndoTabUI();
	  	BusinessDelegate uiBusinessDelegate6=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate6.submitData(tVData,"PRINT","GrpOutMedicUndoTabUI"))
	  {
	     	operFlag=false;
	     	Content=uiBusinessDelegate6.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate6.getResult();			
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
		//GrpOutMedicClaimTabUI tGrpOutMedicClaimTabUI = new GrpOutMedicClaimTabUI();
	  BusinessDelegate uiBusinessDelegate7=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate7.submitData(tVData,"PRINT","GrpOutMedicClaimTabUI"))
	  {
	     	operFlag=false;
	     	Content=uiBusinessDelegate7.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate7.getResult();			
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
		//GrpLifeExCESSTabUI  tGrpLifeExCESSTabUI = new GrpLifeExCESSTabUI();
	   BusinessDelegate uiBusinessDelegate8=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate8.submitData(tVData,"PRINT","GrpLifeExCESSTabUI"))
	  {
	     	operFlag=false;
	     	Content=uiBusinessDelegate8.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate8.getResult();			
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
		//GrpLifeExChangeTabUI tGrpLifeExChangeTabUI = new GrpLifeExChangeTabUI();
	  BusinessDelegate uiBusinessDelegate9=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate9.submitData(tVData,"PRINT","GrpLifeExChangeTabUI"))
	  {
	     	operFlag=false;
	     	Content=uiBusinessDelegate9.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate9.getResult();			
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
		//GrpLifeExClaimTabUI tGrpLifeExClaimTabUI = new GrpLifeExClaimTabUI();
	  BusinessDelegate uiBusinessDelegate10=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate10.submitData(tVData,"PRINT","GrpLifeExClaimTabUI"))
	  {
	     	operFlag=false;
	     	Content=uiBusinessDelegate10.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate10.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
	  	}
		}
}else  if(reprotType.equals("040004")){ //�����ܱ���
		//SumaryReportUI tSumaryReportUI = new SumaryReportUI();
	   BusinessDelegate uiBusinessDelegate11=BusinessDelegate.getBusinessDelegate();
	  if(!uiBusinessDelegate11.submitData(tVData,"PRINT","SumaryReportUI"))
	  {
	     	operFlag=false;
	     	Content=uiBusinessDelegate11.getCErrors().getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------�ɹ�----------");  
			mResult = uiBusinessDelegate11.getResult();			
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
	//��ȡ��ʱ�ļ���
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