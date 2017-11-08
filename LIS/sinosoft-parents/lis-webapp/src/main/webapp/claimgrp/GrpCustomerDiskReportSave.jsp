<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpCustomerDiskReportSave.jsp
//程序功能：批量导入出险人功能
//创建日期：2008-11-03 09:25:18
//创建人  ：  zhangzheng
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

	//****当把程序挪到服务器上时这里的路径操作符要改，要用反斜杠
	String uploadPath=request.getParameter("ImportPath"); //上传路径
	loggerDebug("GrpCustomerDiskReportSave","上传路径:"+uploadPath);
	String tempPath="temp\\"; //存放临时文件的路径
	String path = application.getRealPath("").replace('\\','/');
	if(!path.endsWith("/")){
		path += "/";
	}
	//String saveExcelPath="excel\\"; //文件临时存放路径
	String saveExcelPath=""; //文件临时存放路径
	String FileName=""; //上传的文件名
	String ImportPath = "";

	//保存界面中录入的报案信息
	LLReportSchema tLLReportSchema=new LLReportSchema();
	tLLReportSchema.setRptNo(request.getParameter("RptNo")); //报案号
	tLLReportSchema.setRgtObjNo(tLLReportSchema.getRptNo()); //其他号码
	tLLReportSchema.setRptMode(StrTool.unicodeToGBK(request.getParameter("RptMode"))); //报案方式
	tLLReportSchema.setRptorName(StrTool.unicodeToGBK(request.getParameter("RptorName"))); //报案人姓名
	tLLReportSchema.setRptorAddress(StrTool.unicodeToGBK(request.getParameter("RptorAddress"))); //报案人联系地址
	tLLReportSchema.setRptorPhone(StrTool.unicodeToGBK(request.getParameter("RptorPhone"))); //报案人电话
	tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //事故日期
	tLLReportSchema.setAccidentReason(request.getParameter("AccidentReason")); //出险原因
	tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //团体合同号
	tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //投保人客户号
	tLLReportSchema.setGrpName(StrTool.unicodeToGBK(request.getParameter("GrpName"))); //投保人姓名
	tLLReportSchema.setRiskCode(request.getParameter("RiskCode")); //险种代码
	tLLReportSchema.setRgtState(request.getParameter("rgtstate")); //案件类型 
    tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //投保总人数
	tLLReportSchema.setRelation(request.getParameter("Relation"));
    tLLReportSchema.setRgtFlag("10"); //立案标志为10表示未立案
    tLLReportSchema.setRgtClass("2"); //个单团单标志 1:个单,2:团单
	

	int count=0;

	DiskFileUpload fu = new DiskFileUpload();
	// 设置允许用户上传文件大小,单位:字节
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// 设置最多只允许在内存中存储的数据,单位:字节
	fu.setSizeThreshold(4096);
	
	// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
	//****当把程序挪到服务器上时这里的路径操作符要改，要用反斜杠
	//fu.setRepositoryPath(uploadPath+tempPath);
	fu.setRepositoryPath(path+"/temp");
	//loggerDebug("GrpCustomerDiskReportSave","上传临时文件绝对路径:"+uploadPath+tempPath);
	loggerDebug("GrpCustomerDiskReportSave","上传临时文件绝对路径:"+path+"/temp");
	
	//开始读取上传信息
	List fileItems = fu.parseRequest(request);
	
	// 依次处理每个上传的文件
	Iterator iter = fileItems.iterator();
	loggerDebug("GrpCustomerDiskReportSave","iter.hasNext():"+iter.hasNext());
	
	while (iter.hasNext())
	{
	  FileItem item = (FileItem) iter.next();
	  if (item.getFieldName().compareTo("ImportPath")==0)
		{
			ImportPath = item.getString();
//			loggerDebug("GrpCustomerDiskReportSave","上传路径:"+ImportPath);
		}
	  //检查是一个普通的表单域还是File组件,忽略其他不是文件域的所有表单信息
	  if (!item.isFormField())
	  {
		  String name = item.getName();
		  loggerDebug("GrpCustomerDiskReportSave",name);
		  long size = item.getSize();
			if((name==null||name.equals("")) && size==0)
				continue;
			ImportPath= path + "temp/";
			FileName = name.replace('\\','/');
			FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		  loggerDebug("GrpCustomerDiskReportSave","item.getName():==>"+item.getName());
		  //FileName=item.getName().substring(item.getName().lastIndexOf("\\") + 1);
          loggerDebug("GrpCustomerDiskReportSave","FileName:==>"+FileName);
          //loggerDebug("GrpCustomerDiskReportSave","FieldName:==>"+item.getFieldName());
          loggerDebug("GrpCustomerDiskReportSave","Size:==>"+item.getSize());
          loggerDebug("GrpCustomerDiskReportSave","存储excel文件的基本路径:"+ImportPath);
          //****当把程序挪到服务器上时这里的路径操作符要改，要用一个反斜杠
          loggerDebug("GrpCustomerDiskReportSave","写文件的路径:"+uploadPath + saveExcelPath+FileName);

	      //if((FileName==null||FileName.equals("")) && item.getSize()==0)
	      //  continue;
	    
	      //保存上传的文件到指定的目录
	      try
	      {
	    	  //****当把程序挪到服务器上时这里的路径操作符要改，要用一个反斜杠
		      //item.write(new File(uploadPath + saveExcelPath+FileName.substring(FileName.lastIndexOf("\\") + 1)));
		      item.write(new File(ImportPath + FileName));
			  count = 1;
	    	  count = 1;
		      loggerDebug("GrpCustomerDiskReportSave","count:"+count);
	      }
	      catch(Exception e){
	    	
	      	  loggerDebug("GrpCustomerDiskReportSave","upload file error ...");
	      }
	  }
	}



	//输出参数
	CErrors tError = null;
	CErrors tlogError = null;
	String tRela  = "";
	String FlagStr = "Fail";
	String Content = "";
	String tRptNo=""; //报案号
	String tAccNo=""; //事件号
	String tsumcount="";
	String ttcount="";
	
	
	TransferData tTransferData = new TransferData();
	loggerDebug("GrpCustomerDiskReportSave","----FileName:"+FileName);
	boolean res = true;
	
	//建立批量导入类，
	//GrpCustomerBatchReportIn tGrpCustomerBatchReportIn   = null;
	String busiName="grpGrpCustomerBatchReportIn";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if (count >0)
	{
	  GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	  
	  // 准备传输数据 VData
	  VData tVData = new VData();
	  tVData.add(tLLReportSchema);
	  tTransferData.setNameAndValue("FileName",FileName);
	  tTransferData.setNameAndValue("SaveExcelPath", path);
	  tTransferData.setNameAndValue("GrpCustomerNo", request.getParameter("GrpCustomerNo"));
	  tTransferData.setNameAndValue("GrpName", StrTool.unicodeToGBK(request.getParameter("GrpName")));
	  
	  tVData.add(tTransferData);
	  tVData.add(tG);
	  
	  try
	  {
		    //将批量模板导入到系统中  
//			tGrpCustomerBatchReportIn = new GrpCustomerBatchReportIn();
//	    	res= tGrpCustomerBatchReportIn.submitData(tVData,"");
//	    	loggerDebug("GrpCustomerDiskReportSave","res="+res);
		if(!tBusinessDelegate.submitData(tVData,"",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
				
			}
			else
			{
				Content = "保存失败";
				FlagStr = "Fail";	
						
			}
			res = false;
		}

	  }
	  catch(Exception ex)
	  {
	    	Content = "保存失败，原因是:" + ex.toString();
	    	FlagStr = "Fail";
	  }
	}
	else
	{
	  Content = "上载文件失败! ";
	  FlagStr = "Fail";
	}
	
	String errMess = "";
	
	if (res)
	{

	 // if (tGrpCustomerBatchReportIn.getResult()!=null)
	  if (tBusinessDelegate.getResult()!=null)
	  {
	    try
	    {
	      	//if(tGrpCustomerBatchReportIn.getResult().get(0)!=null&&tGrpCustomerBatchReportIn.getResult().size()>0)
	      	if(tBusinessDelegate.getResult().get(0)!=null&&tBusinessDelegate.getResult().size()>0)
	      	{
	    	  	//tRptNo = (String)tGrpCustomerBatchReportIn.getResult().get(0);
	    	  	//tAccNo = (String)tGrpCustomerBatchReportIn.getResult().get(1);
	    	  	
	    	  	tRptNo = (String)tBusinessDelegate.getResult().get(0);
	    	  	tAccNo = (String)tBusinessDelegate.getResult().get(1);
	    	  	
	        	loggerDebug("GrpCustomerDiskReportSave","tRptNo="+tRptNo);
	        	loggerDebug("GrpCustomerDiskReportSave","tAccNo="+tAccNo);
	        	
	        	LWMissionDB tLWMissionDB = new LWMissionDB();
	        	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
	        	String sql = "select * from LWMission where"
	                   + " activityid = '0000009005' "
	                   + " and processid = '0000000009'"
	                   + " and  missionprop1 = '"+tRptNo+"'";
	        	loggerDebug("GrpCustomerDiskReportSave","Start query mission:" + sql);
	        	LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
	        	if (tLWMissionSet == null && tLWMissionSet.size() == 0)
	        	{
		            Content = "查询工作流节点失败!" ;
		            FlagStr = "Fail";
		        	}
		        else
		        {            
		       
		            tLWMissionSchema = tLWMissionSet.get(1);            
		            String MissionID = tLWMissionSchema.getMissionID();
		            String SubMissionID = tLWMissionSchema.getSubMissionID();
		            loggerDebug("GrpCustomerDiskReportSave","---返回新建工作流节点="+MissionID); 
		        }      
	        }
	      	
		       Content = "保存成功";
		       FlagStr = "Success!";   
	     
	%>
	<script language="javascript">
	    parent.fraInterface.fm.all("ClmNo").value="<%=tRptNo%>";
	    parent.fraInterface.fm.all("AccNo").value="<%=tAccNo%>";
	</script>
	<%
	    }
	    catch(Exception e)
	    {
	       Content = "保存失败，原因是:" + e.toString();
	       FlagStr = "Fail";    
	    }
	  }
	}
	else
	{
		  //tError = tGrpCustomerBatchReportIn.mErrors;
		  tError = tBusinessDelegate.getCErrors();
		  
		  for (int i=0;i<tError.getErrorCount();i++)
		  {
		    	errMess+= tError.getError(i).errorMessage ;
		  		//tGrpCustomerBatchReportIn.LogError("00000000000000000000",FileName,i+1,)
		  }
	      loggerDebug("GrpCustomerDiskReportSave","---ccc");
	      Content = " 保存失败，请查询失败原因";
	      FlagStr = "Fail";

	}
	loggerDebug("GrpCustomerDiskReportSave","---FlagStr = "+FlagStr);
	loggerDebug("GrpCustomerDiskReportSave","---Content = "+Content);
	//添加各种预处理
	loggerDebug("GrpCustomerDiskReportSave","---Result = "+tRptNo);
	%>
	<html>
	<script language="javascript">
	parent.fraInterface.afterSubmitDiskInput("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content.replaceAll("\\\\","/"))%>","<%=tRptNo%>");
	</script>
	</html>
