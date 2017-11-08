<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRExportDataSub.jsp
//程序功能：F1报表生成
//创建日期：2007-2-8
//创建人  ：zhangbin
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
  String contNo 			= request.getParameter("ContNo");//合同编号 
	String reprotType 	= request.getParameter("ReprotType");//报表类型 
  String ricomCode		=	request.getParameter("RIcomCode");//分保公司编号 
  String reRiskCode		= request.getParameter("ReRiskCode");//分保险种编号 
  String tempType			= request.getParameter("TempType");//保单类型
  
 	System.out.println("StaTerm: "+ staTerm );
  System.out.println("合同编号: "+ contNo);
  System.out.println("报表类型: "+ reprotType);
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
//下面是对可能出现得四个合同进行判断具体是针对那个报表
// ***************************************************

//团体成数合同－承保报表
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
	  	System.out.println("--------成功----------");  
			mResult = tGrQuotSharCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	 }  
	//团体成数合同－理赔报表
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
	  	System.out.println("--------成功----------");  
			mResult = tGrQuotSharClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	 }                    
	//2.团体公共交通意外险溢额分保合同 
	//团体公共交通意外险溢额分保合同－承保报表
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
	  	System.out.println("--------成功----------");  
			mResult = tGrpubAcciCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		} 
	} 
	//团体公共交通意外险溢额分保合同－变更报表
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
	  	System.out.println("--------成功----------");  
			mResult = tGrpubAcciChageTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	} 
	//团体公共交通意外险溢额分保合同－理赔报表
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
	  	System.out.println("--------成功----------");  
			mResult = tGrpubAcciClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
 	} 
 	/**
 	* 3.团体境外紧急救援医疗保险成数分保合同
 	* 承保报表
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
	  	System.out.println("--------成功----------");  
			mResult = tGrpOutMedicCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	 } 
else if(reprotType.equals("030002")){ //---新撤销报表
		GrpOutMedicUndoTabUI tGrpOutMedicUndoTabUI = new GrpOutMedicUndoTabUI();
	  if(!tGrpOutMedicUndoTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpOutMedicUndoTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------成功----------");  
			mResult = tGrpOutMedicUndoTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	 }
else if(reprotType.equals("030003")){ //－理赔报表
		GrpOutMedicClaimTabUI tGrpOutMedicClaimTabUI = new GrpOutMedicClaimTabUI();
	  if(!tGrpOutMedicClaimTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpOutMedicClaimTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------成功----------");  
			mResult = tGrpOutMedicClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
 }
//4.团体寿险溢额分保合同
else if(reprotType.equals("040001")){ //---承保报表
		GrpLifeExCESSTabUI  tGrpLifeExCESSTabUI = new GrpLifeExCESSTabUI();
	  if(!tGrpLifeExCESSTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpLifeExCESSTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------成功----------");  
			mResult = tGrpLifeExCESSTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	 } 
else if(reprotType.equals("040002")){ //－变更报表
		GrpLifeExChangeTabUI tGrpLifeExChangeTabUI = new GrpLifeExChangeTabUI();
	  if(!tGrpLifeExChangeTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpLifeExChangeTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------成功----------");  
			mResult = tGrpLifeExChangeTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
	 } 
else  if(reprotType.equals("040003")){ //－理赔报表
		GrpLifeExClaimTabUI tGrpLifeExClaimTabUI = new GrpLifeExClaimTabUI();
	  if(!tGrpLifeExClaimTabUI.submitData(tVData,"PRINT"))
	  {
	     	operFlag=false;
	     	Content=tGrpLifeExClaimTabUI.mErrors.getFirstError().toString();   
	     	FlagStr = "FAIL";              
	  }
	  else
	  {  
	  	System.out.println("--------成功----------");  
			mResult = tGrpLifeExClaimTabUI.getResult();			
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	FlagStr="Succ";
	  	if(txmlExport==null)
	  	{
	   		operFlag=false;
	   		Content=""+"没有得到要显示的数据文件"+"";	  
	  	}
		}
}else  if(reprotType.equals("040004")){ //－汇总报表
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
			System.out.println("--------成功----------");  
			mResult = tSumaryReportUI.getResult();			
			txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
			FlagStr="Succ";
			if(txmlExport==null)
			{
					operFlag=false;
					Content="没有得到要显示的数据文件";	  
			}
	  }
	  */
}
	
	  
	ExeSQL tExeSQL = new ExeSQL();
	//获取临时文件名
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	String strVFFileName = tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//获取存放临时文件的路径
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("vtsfile/").replace('\\','/');
	String strVFPathName = strRealPath + "/" +strVFFileName;

	CombineVts tcombineVts = null;	
	if (operFlag==true)
	{
		//合并VTS文件
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
