
<%@page import="com.sinosoft.easyscan5.util.NewUploadCommconFun"%>
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
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.easyscan5.core.controller.adapter.*"%>

<%
System.out.println("-------------UploadImageFile.jsp Begin---------------------");
try{
DiskFileUpload nDiskFileUpload = new DiskFileUpload();

nDiskFileUpload.setSizeMax(4194304);

StringBuffer bufXML = new StringBuffer(1024);
  List nFileItems = nDiskFileUpload.parseRequest(request);
 
  Iterator nIterator = nFileItems.iterator();
  Iterator nIterator1 = nFileItems.iterator();
 
  StringBuffer xmlBuffer=new StringBuffer(1024);
  String CON_KEY="IndexXML";
  String strKey=CON_KEY;
  int i=0;
 
  while(nIterator.hasNext()){
    FileItem nFileItem = (FileItem)nIterator.next();
    if(nFileItem.getFieldName().equalsIgnoreCase(strKey)){
      xmlBuffer.append(nFileItem.getString());
    }
    i++;
    strKey =CON_KEY + String.valueOf(i);
  }

  String strXML=xmlBuffer.toString().trim();
EasyScanAdapterBL bl = new EasyScanAdapterBL();

String result = bl.execute(request, strXML);
response.setContentType("text/html; charset=utf-8");
response.getWriter().print(result);
} catch (Exception e) {
// TODO: handle exception
	System.out.println("-------------EasyScanAdapter.jsp 异常---------------------");
}
System.out.println("-------------EasyScanAdapter.jsp End---------------------");
%>
<HTML>
<title>上载影像文件</title>
<BODY>

</BODY>
</HTML>
