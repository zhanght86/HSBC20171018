<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：CerifyBatchGetSave
//程序功能：单证批量导入作废功能
//创建日期：2009-08-10 09:25:18
//创建人  ：  zhangzheng
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

	//当把程序挪到服务器上时这里的路径操作符要改，要用反斜杠
	String uploadPath=request.getParameter("ImportPath"); //上传路径
	String stateFlag=request.getParameter("CertifyState"); //单证状态
	
	loggerDebug("CerifyBatchGetSave","上传路径:"+uploadPath);
	String tempPath="temp\\"; //存放临时文件的路径
	/*String path = application.getRealPath("").replace('\\','/');
	if(!path.endsWith("/")){
		path += "/";
	}*/
	String path ="";
	//String saveExcelPath="excel\\"; //文件临时存放路径
	String saveExcelPath=""; //文件临时存放路径
	String FileName=""; //上传的文件名
	String ImportPath = "";
	

	int count=0;

	DiskFileUpload fu = new DiskFileUpload();
	// 设置允许用户上传文件大小,单位:字节
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// 设置最多只允许在内存中存储的数据,单位:字节
	fu.setSizeThreshold(4096);
	
	// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
	//当把程序挪到服务器上时这里的路径操作符要改，要用反斜杠
	//fu.setRepositoryPath(uploadPath+tempPath);
	//fu.setRepositoryPath(path+"/temp");
	//loggerDebug("CerifyBatchGetSave","上传临时文件绝对路径:"+uploadPath+tempPath);
	//loggerDebug("CerifyBatchGetSave","上传临时文件绝对路径:"+path+"/temp");
	
	//开始读取上传信息
	List fileItems = fu.parseRequest(request);
	
	// 依次处理每个上传的文件
	Iterator iter = fileItems.iterator();
	loggerDebug("CerifyBatchGetSave","iter.hasNext():"+iter.hasNext());
	
	while (iter.hasNext())
	{
	  FileItem item = (FileItem) iter.next();
	  if (item.getFieldName().compareTo("ImportPath")==0)
		{
			ImportPath = item.getString();
//			loggerDebug("CerifyBatchGetSave","上传路径:"+ImportPath);
		}
	  //检查是一个普通的表单域还是File组件,忽略其他不是文件域的所有表单信息
	  if (!item.isFormField())
	  {
		  String name = item.getName();
		  loggerDebug("CerifyBatchGetSave",name);
		  long size = item.getSize();
			if((name==null||name.equals("")) && size==0)
				continue;
			//ImportPath= path + "temp/";
			//FileName = name.replace('\\','/');
			//FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		  loggerDebug("CerifyBatchGetSave","item.getName():==>"+item.getName());
		  FileName=item.getName().substring(item.getName().lastIndexOf("\\") + 1);
          loggerDebug("CerifyBatchGetSave","FileName:==>"+FileName);
          //loggerDebug("CerifyBatchGetSave","FieldName:==>"+item.getFieldName());
          loggerDebug("CerifyBatchGetSave","Size:==>"+item.getSize());
          loggerDebug("CerifyBatchGetSave","存储excel文件的基本路径:"+uploadPath);
          //当把程序挪到服务器上时这里的路径操作符要改，要用一个反斜杠
          loggerDebug("CerifyBatchGetSave","写文件的路径:"+uploadPath + saveExcelPath+FileName);

	      //if((FileName==null||FileName.equals("")) && item.getSize()==0)
	      //  continue;
	    
	      //保存上传的文件到指定的目录
	      try
	      {
	    	  //当把程序挪到服务器上时这里的路径操作符要改，要用一个反斜杠
		      //item.write(new File(uploadPath + saveExcelPath+FileName.substring(FileName.lastIndexOf("\\") + 1)));
		      item.write(new File(uploadPath + saveExcelPath+FileName));
			  count = 1;
	    	  count = 1;
		      loggerDebug("CerifyBatchGetSave","count:"+count);
	      }
	      catch(Exception e){
	    	
	      	  loggerDebug("CerifyBatchGetSave","upload file error ...");
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
	loggerDebug("CerifyBatchGetSave","----FileName:"+FileName);
	boolean res = true;
	
	//建立批量导入类，
	//CertifyBatchReportIn tCertifyBatchReportIn  = null;
	
	if (count >0)
	{
	  GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	  
	  path=uploadPath + saveExcelPath+FileName;
	  
	  // 准备传输数据 VData
	  VData tVData = new VData();
	  tTransferData.setNameAndValue("FileName",FileName);
	  tTransferData.setNameAndValue("StateFlag", stateFlag);
	  tTransferData.setNameAndValue("SaveExcelPath", path);
	  
	  tVData.add(tTransferData);
	  tVData.add(tG);
	  
	  /*try
	  {
		    //将批量模板导入到系统中  
			tCertifyBatchReportIn = new CertifyBatchReportIn();
	    	res= tCertifyBatchReportIn.submitData(tVData,"");
	    	loggerDebug("CerifyBatchGetSave","res="+res);
	  }
	  catch(Exception ex)
	  {
	    	Content = "保存失败，原因是:" + ex.toString();
	    	FlagStr = "Fail";
	  }*/
	  String busiName="CertifyBatchReportIn";
	  String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = mDescType+"失败";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = mDescType+"成功! ";
		      	FlagStr = "Success!";  
		  }
	}
	else
	{
	  Content = "上载文件失败! ";
	  FlagStr = "Fail";
	}
	
	String errMess = "";
	
	/*if (res)
	{

	    try
	    {      	
		       Content = "保存成功";
		       FlagStr = "Success!";   
	    }
	    catch(Exception e)
	    {
	       Content = "保存失败，原因是:" + e.toString();
	       FlagStr = "Fail";    
	    }
	}
	else
	{
		  tError = tCertifyBatchReportIn.mErrors;
		  
		  for (int i=0;i<tError.getErrorCount();i++)
		  {
		    	errMess+= tError.getError(i).errorMessage ;
		  }
		  
	      loggerDebug("CerifyBatchGetSave","---ccc");
	      Content = " 保存失败，请查询失败原因:"+errMess;
	      FlagStr = "Fail";

	}*/
	loggerDebug("CerifyBatchGetSave","---FlagStr = "+FlagStr);
	loggerDebug("CerifyBatchGetSave","---Content = "+Content);
	//添加各种预处理
	loggerDebug("CerifyBatchGetSave","---Result = "+tRptNo);
	%>
	<html>
	<script language="javascript">
	parent.fraInterface.afterSubmitDiskInput("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content.replaceAll("\\\\","/"))%>","<%=tRptNo%>");
	</script>
	</html>
