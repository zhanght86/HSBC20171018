<%
//**************************************************************************************************
//�������ƣ�LLUserDefinedBillPrtSave.jsp
//�����ܣ��Զ��嵥֤
//�޸���:niuzj
//�޸�����:2005-09-20
//�޸�ԭ��:��ԭ����ӡһ���ĳɴ�ӡ����
//�޸���:niuzj
//�޸�����:2005-10-04
//�޸�ԭ��:�Ѵ�ӡ���������޸�Ϊֻ��ӡһ��(����GETԭ���汾)
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

<%
    //׼��ͨ�ò���
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
   
    if(tG == null) 
    {
    	loggerDebug("LLUserDefinedBillPrtSave","��¼��Ϣû�л�ȡ!!!");
       	return;
    } 

    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("CustNo"));
    
      //����MulLine��������ݼ���   
     String tGridNo[] = request.getParameterValues("AffixGridNo");  //�õ�����е�����ֵ
     String tGrid1[] = request.getParameterValues("AffixGrid1"); // ��֤����
     String tGrid2[] = request.getParameterValues("AffixGrid2"); // ��֤����
     String tGrid3[] = request.getParameterValues("AffixGrid3"); // �Ƿ��ύ
     String tGrid4[] = request.getParameterValues("AffixGrid4"); // ҳ��
     String tGrid5[] = request.getParameterValues("AffixGrid5"); // �ύ��ʽ
	 String tGrid6[] = request.getParameterValues("AffixGrid6"); // �Ƿ��˻�ԭ��

	 LLAffixSet tLLAffixSet=new LLAffixSet(); //����������
//     int Count = tGridNo.length; //�õ����ܵ��ļ�¼��
	for(int index=0;index < tGridNo.length;index++)
    {
		LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		tLLAffixSchema.setAffixCode(tGrid1[index]); //��֤����
		tLLAffixSchema.setAffixName(tGrid2[index]); //��֤����
		tLLAffixSchema.setSubFlag(tGrid3[index]); //�Ƿ��ύ           
		tLLAffixSchema.setReadyCount(tGrid4[index]); //ҳ��  
		tLLAffixSchema.setProperty(tGrid5[index]); //��֤���Ա�־
		tLLAffixSchema.setReturnFlag(tGrid6[index]); //�Ƿ��˻�ԭ��
		tLLAffixSet.add(tLLAffixSchema);                   	
    }
    // ׼���������� VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add(tG);        
    tVData.add(tTransferData );        
    tVData.add(tLLAffixSet);   
    
    LLPRTUserDefinedBillUI tLLPRTUserDefinedBillUI = new LLPRTUserDefinedBillUI();
    if (tLLPRTUserDefinedBillUI.submitData(tVData,"") == false)
    {
		Content ="ԭ����: " + tLLPRTUserDefinedBillUI.mErrors.getError(1).errorMessage;
		FlagStr = "FAIL";   
    }
    else
    {
	    tResult = tLLPRTUserDefinedBillUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLUserDefinedBillPrtSave","û�еõ�Ҫ��ʾ�������ļ�");
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
		loggerDebug("LLUserDefinedBillPrtSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		
		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
	
		//��dataStream�洢�������ļ�
		loggerDebug("LLUserDefinedBillPrtSave","�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLUserDefinedBillPrtSave","==> Write VTS file to disk ");
	
		loggerDebug("LLUserDefinedBillPrtSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//�����������get��ʽ�������ļ�·��
		//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		//response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
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
