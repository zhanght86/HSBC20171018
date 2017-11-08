 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>

<%
    //׼��ͨ�ò���
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
   
    if(tG == null) 
    {
        loggerDebug("LLPrtGrpSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
		
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("GrpClmNo",request.getParameter("GrpClmNo")); //�����ⰸ��
    String tOperator = request.getParameter("transact");
    // ׼���������� VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add( tG );        
    tVData.add( tTransferData );          
        
    //����ҽ�Ƹ����嵥��ӡ
    if(tOperator.equals("MedBillGrp"))
    {
			   LLPRTMedBillGrpUI tLLPRTMedBillGrpUI = new LLPRTMedBillGrpUI();    
			   if (tLLPRTMedBillGrpUI.submitData(tVData,"") == false)
			   {
			       Content = Content + "��ӡʧ�ܣ�ԭ����: " + tLLPRTMedBillGrpUI.mErrors.getError(0).errorMessage;
			       FlagStr = "FAIL";     
			   }
			   else
			   {
					   tResult = tLLPRTMedBillGrpUI.getResult();	    
					   txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
					   FlagStr="Succ";
					   if(txmlExport==null)
					   {
					       FlagStr="FAIL";
					       Content="û�еõ�Ҫ��ʾ�������ļ�";
				         loggerDebug("LLPrtGrpSave","û�еõ�Ҫ��ʾ�������ļ�");
					   }
			   }    
    }
    //��������������ע��ӡ
    if(tOperator.equals("PostilGrp"))
    {
			   LLPRTPostilGrpUI tLLPRTPostilGrpUI = new LLPRTPostilGrpUI();    
			   if (tLLPRTPostilGrpUI.submitData(tVData,"") == false)
			   { 
			       Content = Content + "ԭ����: " + tLLPRTPostilGrpUI.mErrors.getError(0).errorMessage;
			       FlagStr = "FAIL";     
			   }
			   else
			   {
					   tResult = tLLPRTPostilGrpUI.getResult();	    
					   txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
					   FlagStr="Succ";
					   if(txmlExport==null)
					   {
					       FlagStr="FAIL";
					       Content="û�еõ�Ҫ��ʾ�������ļ�";
				         loggerDebug("LLPrtGrpSave","û�еõ�Ҫ��ʾ�������ļ�");
					   }
			   } 
     }    
        		
    if (FlagStr.equals("Succ"))
    {    
      	ExeSQL tExeSQL = new ExeSQL();
				//��ȡ��ʱ�ļ���
				String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
				String strFilePath = tExeSQL.getOneValue(strSql);
				String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
				//��ȡ�����ʱ�ļ���·��
				String strRealPath = application.getRealPath("/").replace('\\','/');
				loggerDebug("LLPrtGrpSave","strRealPath="+strRealPath);
				String strVFPathName = strRealPath + strVFFileName;
				CombineVts tcombineVts = null;
				//�ϲ�VTS�ļ�
				String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
				tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				tcombineVts.output(dataStream);
			
				//��dataStream�洢�������ļ�
				loggerDebug("LLPrtGrpSave","�洢�ļ���"+strVFPathName);
				AccessVtsFile.saveToFile(dataStream,strVFPathName);
				loggerDebug("LLPrtGrpSave","==> Write VTS file to disk ");
			
				loggerDebug("LLPrtGrpSave","===strVFFileName : "+strVFFileName);
				session.putValue("RealPath", strVFPathName);
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

