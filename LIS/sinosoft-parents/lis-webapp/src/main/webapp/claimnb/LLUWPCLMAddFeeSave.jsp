<%
//**************************************************************************************************
//Name��LLUWPCLMAddFeeSave.jsp
//Function����ӡ����֪ͨ��
//Author��niuzj
//Date:   2006-01-24
//**************************************************************************************************
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
<%@page import="com.sinosoft.lis.claimnb.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
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
	    loggerDebug("LLUWPCLMAddFeeSave","ҳ��ʧЧ,�����µ�½");    
	}
	
    String transact = request.getParameter("fmtransact"); //��ȡ����
	  String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLUWPCLMAddFeeSave","tPath="+tPath);
    
   
    //Stringʹ��TransferData������ύ-----���ڴ����ⰸ�š�֪ͨ���
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ContNo", request.getParameter("ContNo")); //��ͬ��
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //�ⰸ��
	  mTransferData.setNameAndValue("NoticeNo",request.getParameter("NoticeNo")); //֪ͨ���
	  mTransferData.setNameAndValue("DoutyCode",request.getParameter("DoutyCode")); //���α���
	  mTransferData.setNameAndValue("PayPlayCode",request.getParameter("PayPlayCode")); //���Ѽƻ�����
	  mTransferData.setNameAndValue("PolNo",request.getParameter("PolNo")); //������
   	//mTransferData.setNameAndValue("Path",tPath);
   	
   	VData tVData = new VData(); //׼������������ VData
    VData tResult = new VData(); //���ܺ�̨������
    XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
    
//    LLUWPCLMAddFeeUI tLLUWPCLMAddFeeUI=new LLUWPCLMAddFeeUI();
//    if (!tLLUWPCLMAddFeeUI.submitData(tVData,transact))
//    {
//        Content = " �����ύʧ�ܣ�ԭ����: " + tLLUWPCLMAddFeeUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
	 String busiName="LLUWPCLMAddFeeUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   if(!tBusinessDelegate.submitData(tVData,transact,busiName))
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
	   }

    else
    {
    	Content = " �����ύ�ɹ�! ";
	    FlagStr = "Succ";
	    //tResult = tLLUWPCLMAddFeeUI.getResult();	
	    tResult = tBusinessDelegate.getResult();    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
          loggerDebug("LLUWPCLMAddFeeSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }
    }
if (FlagStr.equals("Succ"))
{
	/*ExeSQL tExeSQL = new ExeSQL();
	//��ȡ��ʱ�ļ���
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";
	String strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";
	
	//��ȡ�����ʱ�ļ���·��
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("LLUWPCLMAddFeeSave","strRealPath="+strRealPath);
	String strVFPathName = strRealPath + strVFFileName;
	CombineVts tcombineVts = null;
	
	//�ϲ�VTS�ļ�
	String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	/*String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);
		LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
		strFilePath = tLDSysVarSchema.getV("SysVarValue");
		strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";*/
	/*tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);
	
	//��dataStream�洢�������ļ�
	loggerDebug("LLUWPCLMAddFeeSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("LLUWPCLMAddFeeSave","==> Write VTS file to disk ");
	loggerDebug("LLUWPCLMAddFeeSave","===strVFFileName : "+strVFFileName);
	session.putValue("RealPath", strVFPathName);
		
	//�����������get��ʽ�������ļ�·��
//	response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
    //request.getRequestDispatcher("GetF1Print.jsp").forward(request,response);
	request.getRequestDispatcher("../uw/GetF1Print.jsp").forward(request,response);*/
	
	//session.putValue("PrintNo",PrtSeq );
	//session.putValue("MissionID",tMissionID );
	//session.putValue("SubMissionID",tSubMissionID );
	//session.putValue("Code",tNoticeType );	//�˱�֪ͨ�����
	//session.putValue("PrtNo",tPrtNo );
	//session.putValue("ContNo",tContNo );	
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("LLUWPCLMAddFeeSave","put session value");
   	loggerDebug("LLUWPCLMAddFeeSave","xxxxxxxxxx");
    //response.sendRedirect("../uw/GetF1Print.jsp");
    request.getRequestDispatcher("../uw/GetF1Print.jsp").forward(request,response);
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
