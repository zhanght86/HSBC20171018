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
		String prtseq = request.getParameter("prtseq"); 
		String tPayCode= request.getParameter("PayCode1");
		System.out.println("�����ӡ��ˮ��==========="+prtseq);
		System.out.println("�������з��̱��еĽ��ѱ���========"+tPayCode);

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	  tLOPRTManagerSchema.setPrtSeq(prtseq);
	  tLOPRTManagerSchema.setStandbyFlag4(tPayCode);
  	tG = (GlobalInput)session.getValue("GI");
  	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
  	System.out.println("++++++++++++++++++"+request.getParameter("OtherNoType")+"+++++++++++++++++++");
  	String OthernoType = request.getParameter("OtherNoType"); 
  	
 		VData tVData = new VData();
  	VData mResult = new VData();
    tVData.addElement(tG);
    tVData.addElement(tLOPRTManagerSchema);
    XQWoodcutterNoticeUI tXQWoodcutterNoticeUI = new XQWoodcutterNoticeUI();

 		XmlExport txmlExport = new XmlExport();  
  	if(!tXQWoodcutterNoticeUI.submitData(tVData,OthernoType))
    {
        System.out.println("=======================================1");
       	FlagStr="Fail";
       	Content=tXQWoodcutterNoticeUI.mErrors.getFirstError().toString();
    }
    else
    {
    		System.out.println("=======================================2");
    		FlagStr = "Succ";
				mResult = tXQWoodcutterNoticeUI.getResult();
	  		txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  		System.out.println("adsf�ҵ����");
	  		if(txmlExport==null)
	  		{
	   				FlagStr="Fail";	   				
	   				Content="û�еõ�Ҫ��ʾ�������ļ�";
	  		}
     		else
     		{
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
		  			//response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		  			//response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
		  			request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
		  			System.out.println("done and Content null");
						Content = "";
    		}
  	}

		if( !Content.equals("")) {
				System.out.println("outputStream object has been open");
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