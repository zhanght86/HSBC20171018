
<%@page import="com.sinosoft.easyscan5.util.NewUploadCommconFun"%><%
/**
* <p>Title: EasyScanӰ��ϵͳ</p>
* <p>Description: EasyScan����ͼ���ļ�����</p>
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
// ��������ļ��ߴ磬������4*1024*1024=4MB
nDiskFileUpload.setSizeMax(50*1024*1024);
// ���û�������С��������100*1024=100kb
nDiskFileUpload.setSizeThreshold(60*1024);
//�������ݶ���
StringBuffer bufXML = new StringBuffer(1024);
NewUploadCommconFun commconFun = new NewUploadCommconFun();
try{
  List nFileItems = nDiskFileUpload.parseRequest(request);
  // �õ����е��ļ�
  Iterator nIterator = nFileItems.iterator();
  Iterator nIterator1 = nFileItems.iterator();
  // ���δ���ÿһ���ļ�
  StringBuffer xmlBuffer = new StringBuffer(1024);
  String CON_KEY="IndexXML";
  String strKey=CON_KEY;
  int i=0;
  //�������ݵĲ���
  while(nIterator.hasNext()){
    FileItem nFileItem = (FileItem)nIterator.next();
    if(nFileItem.getFieldName().equalsIgnoreCase(strKey)){
      xmlBuffer.append(nFileItem.getString());
    }
    i++;
    strKey =CON_KEY + String.valueOf(i);
  }
  System.out.println("����XML��" + xmlBuffer.toString());

  //�ַ�������ת��ΪvData
  String strXML = xmlBuffer.toString();

  String filePath = commconFun.getImageSavePath(strXML);
  
  //���������������
  System.out.println("UploadImageFile: Before save");
  //�������е��ļ�
  while(nIterator1.hasNext()){
    FileItem nFileItem1 = (FileItem)nIterator1.next();
    //�������������ļ�������б���Ϣ
    if(!nFileItem1.isFormField()){
      String strFieldName=nFileItem1.getFieldName();
      if(!strFieldName.equals("UploadTemp")){
        //����ļ���
        String strFileName=nFileItem1.getName();
        //��ñ���·��
        String strSaveAsName = filePath + strFileName;
        //�����ļ�����,�����ļ�
        if(!strSaveAsName.equals("")){
           File saveFile = new File(strSaveAsName);
           nFileItem1.write(saveFile);
        }else{
        	out.println(commconFun.getOutErrorStr("�����ļ���Ϊ�գ�����ϵIT������Ա"));
        }
      }
    }
  }
  out.println(commconFun.createReturnXml());
  System.out.println("����XML��" + bufXML.toString());
}
catch(Exception e){
    e.printStackTrace();
	//�����������
    out.println(commconFun.getOutErrorStr("����Ӱ���ļ��쳣"));
}
System.out.println("-------------UploadImageFile.jsp End---------------------");
%>
<HTML>
  <title>����Ӱ���ļ�</title>
  <BODY>
    
  </BODY>
</HTML>
