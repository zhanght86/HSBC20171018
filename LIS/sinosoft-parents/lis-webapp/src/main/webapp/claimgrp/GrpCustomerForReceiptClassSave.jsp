<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpCustomerForReceiptClassSave.jsp
//�����ܣ�
//�������ڣ�2006-8-30
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
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

loggerDebug("GrpCustomerForReceiptClassSave","Path:"+path);
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
    loggerDebug("GrpCustomerForReceiptClassSave","�ϴ�·��:"+ImportPath);
  }
  //�������������ļ�������б���Ϣ
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
    //�����ϴ����ļ���ָ����Ŀ¼
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

//�������
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
  // ׼���������� VData
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
  Content = " �ύ�ɹ�!���ɵ��ⰸ��Ϊ�� "+Result;
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
  Content = " ����ʧ�ܣ�ԭ����:" + errMess;
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
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();


  }
  else
  {
    
      content = "���ݱ���ɹ��������ⰸ�ţ�"+tResult;
      var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//      showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
      var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
      var iWidth=550;      //�������ڵĿ��; 
      var iHeight=350;     //�������ڵĸ߶�; 
      var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
      var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
      showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

      showInfo.focus();


  }
 */
</script>
</html>
