<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRBsnsTabSave.jsp
//程序功能：F1报表生成
//创建日期：2007-2-8
//创建人  ：zhangbin
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
	String tempType			= request.getParameter("TempType"); //保单类型
		
	System.out.println("统计起期::"+mStartDate);
	System.out.println("统计止期::"+mEndDate);
	System.out.println("合同编号::"+mContNo);
	System.out.println("帐单类型::"+mAccountType);
	System.out.println("分保公司::"+mRIcomCode);  
  
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
		//团体保障保险帐单
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
		  	System.out.println("--------成功----------");  
			mResult = uiBusinessDelegate.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"没有得到要显示的数据文件"+"";	  
		  	}
		}
	}
	else if(mAccountType.equals("010002"))
	{
		//公共交通意外溢额险 汇总帐单
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
		  	System.out.println("--------成功----------");  
			mResult = uiBusinessDelegate1.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"没有得到要显示的数据文件"+"";	  
		  	}
		}
	}else if(mAccountType.equals("010003"))
	{
		//公共交通意外溢额险 分保帐单
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
		  	System.out.println("--------成功----------");  
			mResult = uiBusinessDelegate2.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"没有得到要显示的数据文件"+"";	  
		  	}
		}
	}	
	else if(mAccountType.equals("030001"))
	{
		//团体境外医疗救援  汇总帐单
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
		  	System.out.println("--------成功----------");  
			mResult = uiBusinessDelegate3.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"没有得到要显示的数据文件"+"";	  
		  	}
		}
	}
	else if(mAccountType.equals("030002"))
	{
		//团体境外紧急医疗救援 分保帐单
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
		  	System.out.println("--------成功----------");  
			mResult = uiBusinessDelegate4.getResult();			
		  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		  	FlagStr="Succ";
		  	if(txmlExport==null)
		  	{
		   		operFlag=false;
		   		Content=""+"没有得到要显示的数据文件"+"";	  
		  	}
		}
	}
	else if(mAccountType.equals("040001"))
	{
		//团体寿险溢额分保合同帐单
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
			System.out.println("--------成功----------");  
			mResult = uiBusinessDelegate5.getResult();			
		 	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		 	FlagStr="Succ";
		 	if(txmlExport==null)
		 	{
		  		operFlag=false;
		  		Content=""+"没有得到要显示的数据文件"+"";	  
		 	}
		}
	}

	//ExeSQL tExeSQL = new ExeSQL();
	/**获取临时文件名*/
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
	/**获取存放临时文件的路径*/
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;
	
	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		/**合并VTS文件*/
		String strTemplatePath = application.getRealPath("f1print/picctemplate/") + "\\";
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