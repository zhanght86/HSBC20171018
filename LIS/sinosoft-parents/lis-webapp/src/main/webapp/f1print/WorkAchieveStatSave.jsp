<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�WorkAchieveStatSave.jsp
//�����ܣ�������Чͳ���嵥
//�������ڣ�2005-11-29 17:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/���� 
%>
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
  System.out.println("��ȫ������Чͳ�ƴ�ӡ��ʼ..........");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  String strOperation = "";      
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  VData tVData = new VData();
  String ManageCom = request.getParameter("ManageCom");
  String StartDate = request.getParameter("StartDate");
  String EndDate = request.getParameter("EndDate");
  String UsrType = request.getParameter("UsrType");
  String EdorType = request.getParameter("EdorType");
  String UsrCode = request.getParameter("UsrCode");
  String EdorState = request.getParameter("EdorState");
  strOperation = request.getParameter("fmtransact");
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("ManageCom",ManageCom);
  tTransferData.setNameAndValue("StartDate",StartDate);
  tTransferData.setNameAndValue("EndDate",EndDate);
  tTransferData.setNameAndValue("UsrType",UsrType);
  tTransferData.setNameAndValue("EdorType",EdorType);
  tTransferData.setNameAndValue("UsrCode",UsrCode);
  tTransferData.setNameAndValue("EdorState",EdorState);
  tVData.add(tG);
  tVData.add(tTransferData);
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
	    
  XmlExport txmlExport = new XmlExport();  
  EdorWorkAchievePrintUI tEdorWorkAchievePrintUI = new EdorWorkAchievePrintUI();
  if(!tEdorWorkAchievePrintUI.submitData(tVData,strOperation))
  {
          System.out.println("������Чͳ���嵥��ӡʧ�ܣ�");
          FlagStr = "Fail";
          Content=tEdorWorkAchievePrintUI.mErrors.getFirstError().toString();                 
  } 
  else
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
      String strVFPathName = strRealPath + strVFFileName;

      CombineVts tcombineVts = null;

	  mResult = tEdorWorkAchievePrintUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	    System.out.println("��ӡʧ��,ԭ���ǣ���"+tError.getFirstError());
	    FlagStr = "Fail";
	    tError = tEdorWorkAchievePrintUI.mErrors;
        Content = "��ӡʧ��,ԭ���ǣ���"+tError.getFirstError();  
	  }
      else
      {
           System.out.println("��ȫ������Чͳ���嵥�����ϲ�ģ��");
           //�ϲ�VTS�ļ�
	       String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	       tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	       ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
           tcombineVts.output(dataStream);

           //��dataStream�洢�������ļ�
           //System.out.println("�洢�ļ���"+strVFPathName);
           AccessVtsFile.saveToFile(dataStream,strVFPathName);
           System.out.println("==> Write VTS file to disk ");

           System.out.println("===strVFFileName : "+strVFFileName);
           session.putValue("RealPath", strVFPathName);
           //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
           request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request,response);
      }                                                       
   }
	if( !Content.equals("") ) {
		System.out.println("outputStream object has been open");
  %>
  <html>
  <script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
	}
%>