<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpCustomerDiskForSimpleAllSave.jsp
//程序功能：
//创建日期：2006-01-18 17:25:18
//创 建 人：wanzh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
		int count=0;
		String path = application.getRealPath("").replace('\\','/');
		if(!path.endsWith("/"))
		{
		  path += "/";
		}
		
		
		DiskFileUpload fu = new DiskFileUpload();
		// 设置允许用户上传文件大小,单位:字节
		fu.setSizeMax(10000000);
		// maximum size that will be stored in memory?
		// 设置最多只允许在内存中存储的数据,单位:字节
		fu.setSizeThreshold(4096);
		// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
		fu.setRepositoryPath(path+"/temp");
		//开始读取上传信息
		List fileItems = fu.parseRequest(request);
		
		loggerDebug("GrpCustomerDiskForSimpleAllSave","Path:"+path);
		String ImportPath = "";
		String FileName = "";
		
		// 依次处理每个上传的文件
		Iterator iter = fileItems.iterator();
		while (iter.hasNext())
		{
		  FileItem item = (FileItem) iter.next();
		  if (item.getFieldName().compareTo("ImportPath")==0)
		  {
		    ImportPath = item.getString();
		    loggerDebug("GrpCustomerDiskForSimpleAllSave","上传路径:"+ImportPath);
		  }
		  //忽略其他不是文件域的所有表单信息
		  if (!item.isFormField())
		  {
		    String name = item.getName();
		    long size = item.getSize();
		    if((name==null||name.equals("")) && size==0)
		      continue;
		    ImportPath= path + "temp/";
		    FileName = name.replace('\\','/');
		    FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		    //保存上传的文件到指定的目录
		    try
		    {
		      item.write(new File(ImportPath + FileName));
		      count = 1;
		    }
		    catch(Exception e)
		    {
		      loggerDebug("GrpCustomerDiskForSimpleAllSave","upload file error ...");
		    }
		  }
		}
		
		//输出参数
		CErrors tError = null;
		String tRela  = "";
		String FlagStr = "Fail";
		String Content = "";
		String Result="";
		
		TransferData tTransferData = new TransferData();
		boolean res = true;
		
		//GrpClaimSimpleGuideIn tGrpClaimSimpleGuideIn   = new GrpClaimSimpleGuideIn();
		String busiName="grpGrpClaimSimpleGuideIn";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (count >0)
		{
			  GlobalInput tG = new GlobalInput();
			  tG=(GlobalInput)session.getValue("GI");
			  // 准备传输数据 VData
			  VData tVData = new VData();
			  FlagStr="";
			  tTransferData.setNameAndValue("FileName",FileName);
			  tTransferData.setNameAndValue("FilePath", path);
			  tVData.add(tTransferData);
			  tVData.add(tG);
			  try
			  {
			      //res= tGrpClaimSimpleGuideIn.submitData(tVData,"");
			      if(!tBusinessDelegate.submitData(tVData,"",busiName))
					{    
					    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
					    { 
							Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
				  Content = " 提交成功! ";
				  FlagStr = "Succ";
				  loggerDebug("GrpCustomerDiskForSimpleAllSave","---aaa");
				  //if (tGrpClaimSimpleGuideIn.getResult()!=null)
				  if (tBusinessDelegate.getResult()!=null)
				  {
					    try
					    {
//						      if(tGrpClaimSimpleGuideIn.getResult().get(0)!=null&&tGrpClaimSimpleGuideIn.getResult().size()>0)
//						      {
//						        Result = (String)tGrpClaimSimpleGuideIn.getResult().get(0);
//						      }
						      if(tBusinessDelegate.getResult().get(0)!=null&&tBusinessDelegate.getResult().size()>0)
						      {
						        Result = (String)tBusinessDelegate.getResult().get(0);
						      }
					    }
					    catch(Exception e)
					    {
					    }
				  }
		}
		else
		{
			  //tError = tGrpClaimSimpleGuideIn.mErrors;
			   tError = tBusinessDelegate.getCErrors();
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			    errMess+= tError.getError(i).errorMessage ;
			  }
			  loggerDebug("GrpCustomerDiskForSimpleAllSave","---ccc");
			  Content = " 保存失败，原因是:" + errMess;
			  FlagStr = "Fail";
		}
		loggerDebug("GrpCustomerDiskForSimpleAllSave","---bbb = "+FlagStr);
		//添加各种预处理
		loggerDebug("GrpCustomerDiskForSimpleAllSave","---Result = "+Result);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
