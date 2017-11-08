<%
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
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
loggerDebug("UploadImageFile","-------------UploadImageFile.jsp Begin---------------------");
DiskFileUpload nDiskFileUpload = new DiskFileUpload();
// ��������ļ��ߴ磬������4*1024*1024=4MB
nDiskFileUpload.setSizeMax(50*1024*1024);
// ���û�������С��������100*1024=100kb
nDiskFileUpload.setSizeThreshold(60*1024);
// ������ʱĿ¼
//nDiskFileUpload.setRepositoryPath("D:\\lis_hx\\ui\\xerox\\EasyScan\\temp");
//�������ݶ���
StringBuffer bufXML = new StringBuffer(1024);
try{
  List nFileItems = nDiskFileUpload.parseRequest(request);
  // �õ����е��ļ�
  Iterator nIterator = nFileItems.iterator();
  Iterator nIterator1 = nFileItems.iterator();
  // ���δ���ÿһ���ļ�
  StringBuffer xmlBuffer=new StringBuffer(1024);
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

  //�ַ�������ת��ΪvData
  String strXML=xmlBuffer.toString().trim();
  loggerDebug("UploadImageFile",strXML);
  ParameterDataConvert convert = new ParameterDataConvert();
  VData nVData = new VData();
  convert.xMLToVData(strXML,nVData);

  //���������������
  UploadImageFile nUploadImageFile = new UploadImageFile(nVData);
  loggerDebug("UploadImageFile","UploadImageFile: Before save");
  //�������е��ļ�
  boolean blnGetRepositoryPath=false;
  while(nIterator1.hasNext()){
    FileItem nFileItem1 = (FileItem)nIterator1.next();
    //�������������ļ�������б���Ϣ
    if(!nFileItem1.isFormField()){
      String strFieldName=nFileItem1.getFieldName();
      if(!strFieldName.equals("UploadTemp")){
        //����ļ���
        String strFileName=nFileItem1.getName();
        //��ñ���·��
        String[] strPathPatterns = nUploadImageFile.getPathPatterns(strFieldName, strFileName);
        String strSaveAsName = nUploadImageFile.getSaveAsName(strPathPatterns);        
        // ������ʱĿ¼,û��Ҫ��ÿһҳ��ȥ����,ֻ��Ҫ�Ե�һҳ����һ�ξͿ�����
        if(blnGetRepositoryPath){
          nDiskFileUpload.setRepositoryPath(nUploadImageFile.getTempPath());
          blnGetRepositoryPath=false;
        }
        //boolean isFileSavedSucceeded = false;
        //�����ļ�����,�����ļ�
         File saveFile = null;
        if(!strSaveAsName.equals("")){
            saveFile=new File(strSaveAsName);
            nFileItem1.write(saveFile);          
        //    isFileSavedSucceeded = true;
        }
        else{
            String errorMessage = "�����ļ���Ϊ��,�������ݿ��������!";
            loggerDebug("UploadImageFile",errorMessage);
            throw new Exception("�����ļ���Ϊ��,�������ݿ��������!");
        }
        /* if(isFileSavedSucceeded){
        	com.sinosoft.lis.easyscan.cloud.CloudObjectStorageUploader cosu = com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory.getCloudObjectStorageUploader();
        	if(cosu == null){
        		loggerDebug("UploadImageFile", "û���ҵ��κα����õ��Ʒ����ļ����������ڷ�������");        		
        	} else {
        	    String strCloudFilePath = cosu.getCloudFilePath(strPathPatterns);
        	    if(saveFile.exists() && cosu.uploadFileToCloud(strSaveAsName, strCloudFilePath)){
        		    loggerDebug("UploadImageFile", strSaveAsName + "\r\n�ϴ��ɹ���\r\n\t" + strCloudFilePath);
        		    try{
        		        saveFile.delete();
        		        loggerDebug("UploadImageFile", "��ɾ���ļ�\r\n\t" + strSaveAsName);        			
        		    }catch(Exception dE){
        			
        		    }
        	    }else{
        		    loggerDebug("UploadImageFile", strSaveAsName + "\r\n�ϴ���\r\n\t" + strCloudFilePath + " ʱʧ��");
        		    throw new Exception("�������ѽ��յ��ļ����������ϴ���" + cosu.getFriendlyCloudName() + " ʱʧ��");
        	    }
        	}
        } */
      }
    }
  }
  //��÷�������
  //vDataת��Ϊ�ַ�������
  convert.vDataToXML(bufXML,nVData);
}
catch(Exception e){
  //��������쳣�������쳣��Ϣ
  String strErr = "UploadImageFile.jsp--Ӱ���ļ�����ʧ�ܣ�\n" + e.toString();

  bufXML.delete(0,bufXML.length());
  bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><RETURN>");
  bufXML.append("<NUMBER>-4500</NUMBER><MESSAGE>");
  bufXML.append(strErr);
  bufXML.append( "</MESSAGE></RETURN></DATA>");

  loggerDebug("UploadImageFile",strErr);
}
finally{
  //�����������
  out.println("<IndexXML>" + bufXML.toString() + "</IndexXML>");
  loggerDebug("UploadImageFile","-------------UploadImageFile.jsp End---------------------");
}
%>
<HTML>
  <title>Ӱ�����ش���</title>
  <BODY>
    wellhi
  </BODY>
</HTML>
