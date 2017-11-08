<%
/**
* <p>Title: EasyScan影像系统</p>
* <p>Description: EasyScan查询单证详细数据处理</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-10-10
*/
%>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
loggerDebug("QueryDocument","-------------QueryDocument.jsp Begin---------------------");
DiskFileUpload nDiskFileUpload = new DiskFileUpload();
// 设置最大文件尺寸，这里是4*1024*1024=4MB
nDiskFileUpload.setSizeMax(4194304);
// 设置缓冲区大小，这里是4*1024=4kb
nDiskFileUpload.setSizeThreshold(4096);
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
  loggerDebug("QueryDocument",xmlBuffer.toString());

  //字符串参数转换为vData
  String strXML=xmlBuffer.toString();
  ParameterDataConvert convert = new ParameterDataConvert();
  VData nVData = new VData();
  convert.xMLToVData(strXML,nVData);
  GlobalInput tGI = new GlobalInput();
  String strOperator = request.getParameter("Operator");
  tGI.Operator = strOperator;
  //  nVData.add(tGI);
  nVData.setElementAt(tGI,5);

  //提交UI处理
  QueryDocumentUI tQueryDocumentUI   = new QueryDocumentUI();
  tQueryDocumentUI.submitData(nVData,"");

  //获得返回数据
  VData resultData = new VData();
  resultData = tQueryDocumentUI.getResult();

  //获得返回数据,把nVData转换为字符串参数
  convert.vDataToXML(bufXML,resultData);
}
catch(Exception e){
  //如果出现异常，返回异常信息
  String strErr = "QueryDocument.jsp--查询单证详细数据处理失败！\n" + e.toString();

  bufXML.delete(0,bufXML.length());
  bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><RETURN>");
  bufXML.append("<NUMBER>-4500</NUMBER><MESSAGE>");
  bufXML.append(strErr);
  bufXML.append( "</MESSAGE></RETURN></DATA>");

  loggerDebug("QueryDocument",strErr);
}
finally{
  //输出返回数据
  out.println("<IndexXML>" + bufXML.toString() + "</IndexXML>");
  loggerDebug("QueryDocument","-------------QueryDocument.jsp End---------------------");
}
%>
<HTML>
  <title>查询单证详细数据处理</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
