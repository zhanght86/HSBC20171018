
<%@page import="com.sinosoft.easyscan5.util.NewUploadCommconFun"%><%
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
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>

<%
System.out.println("-------------UploadImageFile.jsp Begin---------------------");
DiskFileUpload nDiskFileUpload = new DiskFileUpload();
// 设置最大文件尺寸，这里是4*1024*1024=4MB
nDiskFileUpload.setSizeMax(50*1024*1024);
// 设置缓冲区大小，这里是100*1024=100kb
nDiskFileUpload.setSizeThreshold(60*1024);
//返回数据对象
StringBuffer bufXML = new StringBuffer(1024);
NewUploadCommconFun commconFun = new NewUploadCommconFun();
try{
  List nFileItems = nDiskFileUpload.parseRequest(request);
  // 得到所有的文件
  Iterator nIterator = nFileItems.iterator();
  Iterator nIterator1 = nFileItems.iterator();
  // 依次处理每一个文件
  StringBuffer xmlBuffer = new StringBuffer(1024);
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
  System.out.println("请求XML：" + xmlBuffer.toString());

  //字符串参数转换为vData
  String strXML = xmlBuffer.toString();

  String filePath = commconFun.getImageSavePath(strXML);
  
  //创建索引处理对象
  System.out.println("UploadImageFile: Before save");
  //遍历所有的文件
  while(nIterator1.hasNext()){
    FileItem nFileItem1 = (FileItem)nIterator1.next();
    //忽略其他不是文件域的所有表单信息
    if(!nFileItem1.isFormField()){
      String strFieldName=nFileItem1.getFieldName();
      if(!strFieldName.equals("UploadTemp")){
        //获得文件名
        String strFileName=nFileItem1.getName();
        //获得保存路径
        String strSaveAsName = filePath + strFileName;
        //创建文件对象,保存文件
        if(!strSaveAsName.equals("")){
           File saveFile = new File(strSaveAsName);
           nFileItem1.write(saveFile);
        }else{
        	out.println(commconFun.getOutErrorStr("保存文件名为空，请联系IT技术人员"));
        }
      }
    }
  }
  out.println(commconFun.createReturnXml());
  System.out.println("返回XML：" + bufXML.toString());
}
catch(Exception e){
    e.printStackTrace();
	//输出返回数据
    out.println(commconFun.getOutErrorStr("上载影像文件异常"));
}
System.out.println("-------------UploadImageFile.jsp End---------------------");
%>
<HTML>
  <title>上载影像文件</title>
  <BODY>
    
  </BODY>
</HTML>
