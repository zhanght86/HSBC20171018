<%
//**************************************************************************************************
//�ļ����ƣ�ClaimCheckPrintSave.jsp
//�����ܣ��ո���--�� ��--��������շѷ�Ʊ��ӡ
//�������ڣ�2006-03-09
//������  ��zhaorx
//���¼�¼��
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.report.f1report.*"%>

<%
    loggerDebug("ClaimCheckPrintSave","��ʼִ�У��ո���--�� ��--��������շѷ�Ʊ��ӡ����");
    String Content = "";
    CErrors tError = null;
    String FlagStr = "Fail";

    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    if(tG == null) 
    {
    	 loggerDebug("ClaimCheckPrintSave","��¼��Ϣû�л�ȡ!!!");
    	 return;
    }  
    loggerDebug("ClaimCheckPrintSave","##########"+tG.ManageCom); 
  
    String tClmNo = request.getParameter("ClmNo");	
    loggerDebug("ClaimCheckPrintSave","С��������@@@@@@@@@@@@:"+request.getParameter("ClmNo"));
  
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ABClmNo",tClmNo);  //�ⰸ��  
    // ׼���������� VData
    VData tVData = new VData();
    VData tResult = new VData();
    XmlExport txmlExport = new XmlExport();
     
    tVData.add(tG);        
    tVData.add(tTransferData);        

    ClaimCheckPrintUI tClaimCheckPrintUI = new ClaimCheckPrintUI();
    
    if (tClaimCheckPrintUI.submitData(tVData,"") == false)
    {
            Content = Content + "ԭ����: " + tClaimCheckPrintUI.mErrors.getError(0).errorMessage;
            FlagStr = "FAIL";    
    }
    else
    {
	    tResult = tClaimCheckPrintUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
           	loggerDebug("ClaimCheckPrintSave","û�еõ�Ҫ��ʾ�������ļ�");
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
	//strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
	//String strRealPath = tExeSQL.getOneValue(strSql);
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("ClaimCheckPrintSave","strRealPath="+strRealPath);
	String strVFPathName = strRealPath + strVFFileName;
	CombineVts tcombineVts = null;
	//�ϲ�VTS�ļ�
	String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	loggerDebug("ClaimCheckPrintSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("ClaimCheckPrintSave","==> Write VTS file to disk ");

	loggerDebug("ClaimCheckPrintSave","===strVFFileName : "+strVFFileName);
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

