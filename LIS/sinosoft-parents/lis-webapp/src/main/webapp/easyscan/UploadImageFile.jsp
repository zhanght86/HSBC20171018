<%
/**
* <p>Title: EasyScan影像系统</p>
* <p>Description: EasyScan上载图像文件处理</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-09-30
*/
%>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
loggerDebug("UploadImageFile","-------------UploadImageFile.jsp Begin---------------------");
DiskFileUpload nDiskFileUpload = new DiskFileUpload();
// 设置最大文件尺寸，这里是4*1024*1024=4MB
nDiskFileUpload.setSizeMax(50*1024*1024);
// 设置缓冲区大小，这里是100*1024=100kb
nDiskFileUpload.setSizeThreshold(60*1024);
// 设置临时目录
//nDiskFileUpload.setRepositoryPath("D:\\lis_hx\\ui\\xerox\\EasyScan\\temp");
//返回数据对象
StringBuffer bufXML = new StringBuffer(1024);
try{
  List nFileItems = nDiskFileUpload.parseRequest(request);
  // 得到所有的文件
  Iterator nIterator = nFileItems.iterator();
  Iterator nIterator1 = nFileItems.iterator();
  // 依次处理每一个文件
  StringBuffer xmlBuffer=new StringBuffer(1024);
  String CON_KEY="IndexXML";
  String strKey=CON_KEY;
  int i=0;
  //遍历传递的参数
  while(nIterator.hasNext()){
    FileItem nFileItem = (FileItem)nIterator.next();
    if(nFileItem.getFieldName().equalsIgnoreCase(strKey)){
      xmlBuffer.append(nFileItem.getString());
    }
    i++;
    strKey =CON_KEY + String.valueOf(i);
  }

  //字符串参数转换为vData
  String strXML=xmlBuffer.toString().trim();
  loggerDebug("UploadImageFile",strXML);
  ParameterDataConvert convert = new ParameterDataConvert();
  VData nVData = new VData();
  convert.xMLToVData(strXML,nVData);

  //创建索引处理对象
  UploadImageFile nUploadImageFile = new UploadImageFile(nVData);
  loggerDebug("UploadImageFile","UploadImageFile: Before save");
  //遍历所有的文件
  boolean blnGetRepositoryPath=false;
  while(nIterator1.hasNext()){
    FileItem nFileItem1 = (FileItem)nIterator1.next();
    //忽略其他不是文件域的所有表单信息
    if(!nFileItem1.isFormField()){
      String strFieldName=nFileItem1.getFieldName();
      if(!strFieldName.equals("UploadTemp")){
        //获得文件名
        String strFileName=nFileItem1.getName();
        //获得保存路径
        String[] strPathPatterns = nUploadImageFile.getPathPatterns(strFieldName, strFileName);
        String strSaveAsName = nUploadImageFile.getSaveAsName(strPathPatterns);        
        // 设置临时目录,没必要对每一页都去设置,只需要对第一页设置一次就可以了
        if(blnGetRepositoryPath){
          nDiskFileUpload.setRepositoryPath(nUploadImageFile.getTempPath());
          blnGetRepositoryPath=false;
        }
        //boolean isFileSavedSucceeded = false;
        //创建文件对象,保存文件
         File saveFile = null;
        if(!strSaveAsName.equals("")){
            saveFile=new File(strSaveAsName);
            nFileItem1.write(saveFile);          
        //    isFileSavedSucceeded = true;
        }
        else{
            String errorMessage = "保存文件名为空,请检查数据库相关配置!";
            loggerDebug("UploadImageFile",errorMessage);
            throw new Exception("保存文件名为空,请检查数据库相关配置!");
        }
        /* if(isFileSavedSucceeded){
        	com.sinosoft.lis.easyscan.cloud.CloudObjectStorageUploader cosu = com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory.getCloudObjectStorageUploader();
        	if(cosu == null){
        		loggerDebug("UploadImageFile", "没有找到任何被启用的云服务。文件将被保留在服务器。");        		
        	} else {
        	    String strCloudFilePath = cosu.getCloudFilePath(strPathPatterns);
        	    if(saveFile.exists() && cosu.uploadFileToCloud(strSaveAsName, strCloudFilePath)){
        		    loggerDebug("UploadImageFile", strSaveAsName + "\r\n上传成功至\r\n\t" + strCloudFilePath);
        		    try{
        		        saveFile.delete();
        		        loggerDebug("UploadImageFile", "已删除文件\r\n\t" + strSaveAsName);        			
        		    }catch(Exception dE){
        			
        		    }
        	    }else{
        		    loggerDebug("UploadImageFile", strSaveAsName + "\r\n上传至\r\n\t" + strCloudFilePath + " 时失败");
        		    throw new Exception("服务器已接收到文件，但是在上传至" + cosu.getFriendlyCloudName() + " 时失败");
        	    }
        	}
        } */
      }
    }
  }
  //获得返回数据
  //vData转换为字符串参数
  convert.vDataToXML(bufXML,nVData);
}
catch(Exception e){
  //如果出现异常，返回异常信息
  String strErr = "UploadImageFile.jsp--影像文件保存失败！\n" + e.toString();

  bufXML.delete(0,bufXML.length());
  bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><RETURN>");
  bufXML.append("<NUMBER>-4500</NUMBER><MESSAGE>");
  bufXML.append(strErr);
  bufXML.append( "</MESSAGE></RETURN></DATA>");

  loggerDebug("UploadImageFile",strErr);
}
finally{
  //输出返回数据
  out.println("<IndexXML>" + bufXML.toString() + "</IndexXML>");
  loggerDebug("UploadImageFile","-------------UploadImageFile.jsp End---------------------");
}
%>
<HTML>
  <title>影像上载处理</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
