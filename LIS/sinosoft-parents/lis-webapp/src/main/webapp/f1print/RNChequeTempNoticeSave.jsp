<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%
 String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String strOperation = "PRINT";

	GlobalInput tG = new GlobalInput();
	String strVFPathName="";

  String tOtherNo = request.getParameter("OtherNoHidden");
  String tCustomerName = request.getParameter("AccNameHidden");
  String tChequeNo = request.getParameter("ChequeNoHidden");
  String tAccName = request.getParameter("AccNameHidden");
  String tMakeDate = request.getParameter("MakeDateHidden");
  String tPayMoney = request.getParameter("PayMoneyHidden");
  String tAgentCode = request.getParameter("AgentCodeHidden");
  String tAgentName = request.getParameter("AgentNameHidden");
  String tManageComName = request.getParameter("ManageCom1Hidden");

	TransferData tTransferData = new TransferData();

	tTransferData.setNameAndValue("OtherNo", tOtherNo);
	tTransferData.setNameAndValue("CustomerName", tCustomerName);
	tTransferData.setNameAndValue("ChequeNo", tChequeNo);
	tTransferData.setNameAndValue("AccName", tAccName);
	tTransferData.setNameAndValue("MakeDate", tMakeDate);
	tTransferData.setNameAndValue("PayMoney", tPayMoney);
	tTransferData.setNameAndValue("AgentCode", tAgentCode);
	tTransferData.setNameAndValue("AgentName", tAgentName);
	tTransferData.setNameAndValue("ManageComName", tManageComName);
	
 	tG = (GlobalInput)session.getValue("GI");
  
 	VData tVData = new VData();
 	VData mResult = new VData();

  tVData.addElement(tG);
  tVData.addElement(tTransferData);
  
  RNChequeTempNoticeUI tRNChequeTempNoticeUI = new RNChequeTempNoticeUI();

 	XmlExport txmlExport = new XmlExport();  
  if(!tRNChequeTempNoticeUI.submitData(tVData,strOperation))
    {
       System.out.println("=======================================1");
       	FlagStr="Fail";
       	Content=tRNChequeTempNoticeUI.mErrors.getFirstError().toString();
    }
    else
    {
    System.out.println("=======================================2");
    	FlagStr = "Succ";
			mResult = tRNChequeTempNoticeUI.getResult();
	  	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  	System.out.println("׼������û?��Ҫ�����ˣ�����������������");
	  	if(txmlExport==null)
	  	{
	   		FlagStr="Fail";
	   		System.out.println("=======================================3ssss");
	   		Content="û�еõ�Ҫ��ʾ�������ļ�";
	  	}
     else{
	ExeSQL tExeSQL = new ExeSQL();
	System.out.println("=======================================3");
	//��ȡ��ʱ�ļ���
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
	//��ȡ�����ʱ�ļ���·��
	String strRealPath = application.getRealPath("/").replace('\\','/');
	 strVFPathName = strRealPath + strVFFileName;
	   CombineVts tcombineVts = null;
	   //�ϲ�VTS�ļ�
		  String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		  tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		  System.out.println(strTemplatePath);

		  ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		  tcombineVts.output(dataStream);

		  //��dataStream�洢�������ļ�
		  System.out.println("�洢�ļ���"+strVFPathName);
		  AccessVtsFile.saveToFile(dataStream,strVFPathName);
		  System.out.println("==> Write VTS file to disk ");

		  System.out.println("===strVFFileName : "+strVFFileName);
		  session.putValue("RealPath", strVFPathName);
		  //�����������get��ʽ�������ļ�·��
		  response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		  //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
      System.out.println("Flag=="+FlagStr);
      session.setAttribute("PrintType","04" );
      session.setAttribute("PrintStream", txmlExport.getInputStream());
      System.out.println("put session value");
      //	response.sendRedirect("GetF1Print.jsp"); 
		  FlagStr = "succee";
		  Content = "��ӡ���";
    }
  }  
%>
<html>
<script language="javascript">	
	alert("<%=Content%>");
	top.close();
	
	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");	
	
</script>
</html>
