<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpCustomerDiskForAccSave.jsp
//�����ܣ��ʻ����⵼��
//�������ڣ�2006-01-12 09:25:18
//������  �������
%>
<!--�û�У����-->
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

String tFlag = request.getParameter("Flag"); //��ʾ����

//add by wood
String tRgtNo = request.getParameter("RgtNo"); //��ʾ����

String path = application.getRealPath("").replace('\\','/');
if(!path.endsWith("/")){
  path += "/";
}


DiskFileUpload fu = new DiskFileUpload();
// ���������û��ϴ��ļ���С,��λ:�ֽ�
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
fu.setSizeThreshold(4096);
// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
fu.setRepositoryPath(path+"/temp");
//��ʼ��ȡ�ϴ���Ϣ
List fileItems = fu.parseRequest(request);

loggerDebug("GrpCustomerDiskForAccSave","Path:"+path);
String ImportPath = "";
String FileName = "";


// ���δ���ÿ���ϴ����ļ�
Iterator iter = fileItems.iterator();
while (iter.hasNext())
{
  FileItem item = (FileItem) iter.next();
  if (item.getFieldName().compareTo("ImportPath")==0)
  {
    ImportPath = item.getString();
    loggerDebug("GrpCustomerDiskForAccSave","�ϴ�·��:"+ImportPath);
  }
  //�������������ļ�������б���Ϣ
  if (!item.isFormField())
  {
    String name = item.getName();
    long size = item.getSize();
    if((name==null||name.equals("")) && size==0)
      continue;
    ImportPath= path + "temp/";
    FileName = name.replace('\\','/');
    FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
    //�����ϴ����ļ���ָ����Ŀ¼
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

//�������
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
 * �޸�ԭ��: ���[��������Ϣ¼��]��ť
 * �� �� �ˣ������
 * �޸�����2006-01-12 16��00
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
        // ׼���������� VData
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
					Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "����ʧ��";
					FlagStr = "Fail";				
				}
				res = false;
			}
            
        }
        catch(Exception ex)
        {
            Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }
    }
    else
    {
        Content = "�����ļ�ʧ��! ";
        FlagStr = "Fail";
    }
    
    String errMess = "";
    loggerDebug("GrpCustomerDiskForAccSave","--------���Ա�ʾ��-------"+res);
    
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
                Content = " ������ɡ����е���ɹ�[ "+tTrue+" ]��������ʧ��[ "+tFalse+" ]����";
                }
                FlagStr = "Succ";
                
            }
            else
            {
                Content = " ������ɡ�û�еõ����صĵ�����Ϣ��";
                FlagStr = "Succ";
            }
        }
        catch(Exception e)
        {
        }
    }
    else
    {
          Content = " ������ɡ�û�еõ����صĵ�����Ϣ��";
          FlagStr = "Succ";
    }
    
    loggerDebug("GrpCustomerDiskForAccSave","--------���Խ����-------"+Content);
}

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
