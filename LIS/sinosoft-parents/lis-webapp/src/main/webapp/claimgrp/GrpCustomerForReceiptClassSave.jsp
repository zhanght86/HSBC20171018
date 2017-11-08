<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpCustomerForReceiptClassSave.jsp
//程序功能：
//创建日期：2006-8-30
//创建人  ：CrtHtml程序创建
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

loggerDebug("GrpCustomerForReceiptClassSave","Path:"+path);
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
    loggerDebug("GrpCustomerForReceiptClassSave","上传路径:"+ImportPath);
  }
  //忽略其他不是文件域的所有表单信息
  if (!item.isFormField())
  {
    String name = item.getName();
    long size = item.getSize();
    if((name==null||name.equals("")) && size==0)
      continue;
    ImportPath= path + "temp/";
//    ImportPath= path + ImportPath;
    FileName = name.replace('\\','/');
    FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
    //保存上传的文件到指定的目录
    try
    {
      item.write(new File(ImportPath + FileName));
      count = 1;
    }
    catch(Exception e){
      loggerDebug("GrpCustomerForReceiptClassSave","upload file error ...");
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
loggerDebug("GrpCustomerForReceiptClassSave","----FileName:"+FileName);
boolean res = true;

//GrpReceiptClassGuideIn tGrpReceiptClassGuideIn   = new GrpReceiptClassGuideIn();
String busiName="grpGrpReceiptClassGuideIn";
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
    //res= tGrpReceiptClassGuideIn.submitData(tVData,"");
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

//  if (tGrpReceiptClassGuideIn.getResult()!=null)
//  {
//    try
//    {
//      if(tGrpReceiptClassGuideIn.getResult().get(0)!=null&&tGrpReceiptClassGuideIn.getResult().size()>0)
//      {
//        Result = (String)tGrpReceiptClassGuideIn.getResult().get(0);
//      }
//    }
//    catch(Exception e)
//    {
//    }
//  }
    if (tBusinessDelegate.getResult()!=null)
  {
    try
    {
      if(tBusinessDelegate.getResult().get(0)!=null&&tBusinessDelegate.getResult().size()>0)
      {
        Result = (String)tBusinessDelegate.getResult().get(0);
      }
    }
    catch(Exception e)
    {
    }
  }

String errMess = "";
  loggerDebug("GrpCustomerForReceiptClassSave","res-----------------"+res);
if (res)
{
  Content = " 提交成功!生成的赔案号为： "+Result;
  FlagStr = "Succ";
}
else
{
 // tError = tGrpReceiptClassGuideIn.mErrors;
  tError = tBusinessDelegate.getCErrors();
  for (int i=0;i<tError.getErrorCount();i++)
  {
    errMess+= tError.getError(i).errorMessage ;
  }
  Content = " 保存失败，原因是:" + errMess;
  FlagStr = "Fail";
}
loggerDebug("GrpCustomerForReceiptClassSave","---Result = "+Result);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");

/*
var FlagStr='<%=FlagStr%>';
var content='<%=Content%>';
var tResult = '<%=Result%>';
if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();


  }
  else
  {
    
      content = "数据保存成功！生成赔案号："+tResult;
      var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//      showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
      var name='提示';   //网页名称，可为空; 
      var iWidth=550;      //弹出窗口的宽度; 
      var iHeight=350;     //弹出窗口的高度; 
      var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
      var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
      showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

      showInfo.focus();


  }
 */
</script>
</html>
