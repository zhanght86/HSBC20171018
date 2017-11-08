<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpCustomerDiskForAccSave.jsp
//程序功能：帐户理赔导入
//创建日期：2006-01-12 09:25:18
//创建人  ：万泽辉
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

String tFlag = request.getParameter("Flag"); //标示出处

//add by wood
String tRgtNo = request.getParameter("RgtNo"); //标示出处

String path = application.getRealPath("").replace('\\','/');
if(!path.endsWith("/")){
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

loggerDebug("GrpCustomerDiskForAccSave","Path:"+path);
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
    loggerDebug("GrpCustomerDiskForAccSave","上传路径:"+ImportPath);
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
    catch(Exception e){
      loggerDebug("GrpCustomerDiskForAccSave","upload file error ...");
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
VData tResult = new VData();
String tTrue  = "";
String tFalse = "";
String mContent = "";
boolean res = true;

/**====================================================================
 * 修改原因: 添加[出险人信息录入]按钮
 * 修 改 人：万泽辉
 * 修改日起：2006-01-12 16：00
 =====================================================================
 */
// String errMess = "";
if(tFlag.equals("TOACC")||tFlag == "TOACC")
{
    //GrpCustomerGuideForAccIn tGrpCustomerGuideForAccIn   
    //            = new GrpCustomerGuideForAccIn();
    String busiName="grpGrpCustomerGuideForAccIn";
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
        
        //add by wood
        tTransferData.setNameAndValue("RgtNo", tRgtNo);
        
        tVData.add(tTransferData);
        tVData.add(tG);
        
        try
        {
            //res= tGrpCustomerGuideForAccIn.submitData(tVData,"");
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
    loggerDebug("GrpCustomerDiskForAccSave","--------测试表示：-------"+res);
    
   // tResult = tGrpCustomerGuideForAccIn.getResult();
    tResult = tBusinessDelegate.getResult();
    if (tResult!=null)
    {
        try
        {
            tTransferData  = (TransferData)tResult.getObjectByObjectName("TransferData", 0);
            if(tTransferData != null)
            {
                tTrue  = (String)tTransferData.getValueByName("Succ");
                tFalse = (String)tTransferData.getValueByName("Fail");
                Result = (String)tTransferData.getValueByName("RgtNo");
                mContent = (String)tTransferData.getValueByName("Content");
                if(!mContent.equals("")){
                Content = mContent;
                }else{
                Content = " 操作完成。其中导入成功[ "+tTrue+" ]条，导入失败[ "+tFalse+" ]条！";
                }
                FlagStr = "Succ";
                
            }
            else
            {
                Content = " 操作完成。没有得到返回的导入信息！";
                FlagStr = "Succ";
            }
        }
        catch(Exception e)
        {
        }
    }
    else
    {
          Content = " 操作完成。没有得到返回的导入信息！";
          FlagStr = "Succ";
    }
    
    loggerDebug("GrpCustomerDiskForAccSave","--------测试结果：-------"+Content);
}

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
