<%
//�ļ����ƣ�LLCaseBackNoticeSave.jsp
//�ļ����ܣ�������˷���֪ͨ��
//�����ˣ�wl
//��������:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//�������
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  

//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLCaseBackNoticeSave","ҳ��ʧЧ,�����µ�½");    
}
    String transact = request.getParameter("fmtransact"); //��ȡ����
	//String tPath = application.getRealPath("f1print/MStemplate") + "/";
    String tPath = request.getSession().getServletContext().getResource("/").getPath()+"f1print/MStemplate/";
    loggerDebug("LLCaseBackNoticeSave","tPath="+tPath);
	
    //Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š�����ԭ��֪ͨ������
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNoQ", request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("BackReasonQ", request.getParameter("BackReason")); //����ԭ��
    mTransferData.setNameAndValue("PrtNoticeNo", request.getParameter("PrtNoticeNo")); //֪ͨ������
      
    VData tVData = new VData(); //׼������������ VData
    VData tResult = new VData(); //���ܺ�̨������
     XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
   
//	LLCaseBackNoticeUI tLLCaseBackNoticeUI=new LLCaseBackNoticeUI();
//    if (!tLLCaseBackNoticeUI.submitData(tVData,transact))
//    {
//        Content = " �����ύʧ�ܣ�ԭ����: " + tLLCaseBackNoticeUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
	String busiName="LLCaseBackNoticeUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "�����ύʧ��";
			FlagStr = "Fail";				
		}
	}

    else
    {
    	Content = " �����ύ�ɹ�! ";
	    FlagStr = "Succ";
	    //tResult = tLLCaseBackNoticeUI.getResult();
	    tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLCaseBackNoticeSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		/*String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";*/
		String strVFFileName="";
    	VData vtsVData=new VData();
    	vtsVData.add( tGI );
    	BusinessDelegate tvtsBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	  if(tvtsBusinessDelegate.submitData(vtsVData,"","BqFileNameUI"))
    	  {
    		  strVFFileName = (String)tvtsBusinessDelegate.getResult().get(0);
    	  }
		//��ȡ�����ʱ�ļ���·��
		String strRealPath = application.getRealPath("/").replace('\\','/');
		//String strRealPath = request.getSession().getServletContext().getResource("/").getPath().replace('\\','/');
		loggerDebug("LLCaseBackNoticeSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		//String strTemplatePath = request.getSession().getServletContext().getResource("/").getPath()+"f1print/MStemplate/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		//��dataStream�洢�������ļ�
		loggerDebug("LLCaseBackNoticeSave","�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLCaseBackNoticeSave","==> Write VTS file to disk ");
		loggerDebug("LLCaseBackNoticeSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//�����������get��ʽ�������ļ�·��
        request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request,response);
	}
	else
    {
%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();  
</script>
</html>
<%
  }
%>
