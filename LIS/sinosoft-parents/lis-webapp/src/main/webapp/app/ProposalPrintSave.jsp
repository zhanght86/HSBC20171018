
<%
	//�������ƣ�ProposalPrintSave.jsp
	//�����ܣ�
	//�������ڣ�2002-11-25
	//������  ��Kevin
	//���¼�¼��  ������  pst   ��������  2007-10-25   ����ԭ��/���� ������ӡ�����ֲ�������������
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!InputStream ins = null;

	String PrintContent = "";

	String FlagStr = "";

	String Content = "";

  int tFail=0;
  int tSucc=0;

	boolean handleFunction(HttpSession session, HttpServletRequest request) {
		int nIndex = 0;
		String tLCContGrids[] = request.getParameterValues("ContGridNo");
		String tLCPrtNoNos[] = request.getParameterValues("ContGrid2");
		String tLCContNos[] = request.getParameterValues("ContGrid1");
		String tLCContFamilyContNo[] = request.getParameterValues("ContGrid8");
		String tChecks[] = request.getParameterValues("InpContGridChk");

		GlobalInput globalInput = new GlobalInput();

		if ((GlobalInput) session.getValue("GI") == null) {
			Content += "��ҳ��ʱ������û�в���Ա��Ϣ�������µ�¼";
			return false;
		} else {
			globalInput.setSchema((GlobalInput) session.getValue("GI"));
		}

		if (tLCContGrids == null) {
			Content += "û��������Ҫ�Ĵ�ӡ����";
			return false;
		}

		LCContSet tLCContSet = new LCContSet();
		//LCPolF1P_IDGFUI tLCPolF1P_IDGFUI = new LCPolF1P_IDGFUI();
        String busiName="f1printLCPolF1P_IDGFUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		for (nIndex = 0; nIndex < tChecks.length; nIndex++) {
			// If this line isn't selected, continue
			if (!tChecks[nIndex].equals("1")) {
				continue;
			}
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema.setContNo(tLCContNos[nIndex]);
			tLCContSchema.setPrtNo(tLCPrtNoNos[nIndex]);
			tLCContSchema.setFamilyContNo(tLCContFamilyContNo[nIndex]);
			tLCContSet.add(tLCContSchema);
		}

		// Prepare data for submiting
		VData vData = new VData();

		vData.addElement(tLCContSet);
		vData.add(globalInput);

		try {
			if (!tBusinessDelegate.submitData(vData, "PRINT",busiName)) {
				PrintContent = tBusinessDelegate.getCErrors().getFirstError();
				return false;
			} else {
				ins = (InputStream) (tBusinessDelegate.getResult().get(0));
				//tSucc=tBusinessDelegate.tSucc;
				//tFail=tBusinessDelegate.tFail;
				VData tData=tBusinessDelegate.getResult();
				TransferData tTransferData=(TransferData)tData.getObjectByObjectName("TransferData",0);
				tSucc=(Integer)tTransferData.getValueByName("Succ");
				tFail=(Integer)tTransferData.getValueByName("Fail");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Content += ex.getMessage();
			return false;
		}
		return true;
	} //End handleFunction%>

<%
	String FlagStr = "";
	String Content = "";

	//add by yt 20040426��Ϊ�˽��ͬʱ������ڴ�ӡ�ᵼ�²���ͬһ���ļ��������⡣
	String tSN = "";
//	tSN = PubFun1.CreateMaxNo("SNPolPrint", 10);
        TransferData tTransferData=new TransferData();
        tTransferData.setNameAndValue("NoType", "SNPolPrint");
        tTransferData.setNameAndValue("NoLength", 10);
        VData tVData = new VData();
        tVData.add(tTransferData);		
        String busiName="tPubFun1";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(tBusinessDelegate.submitData(tVData, "CreateMaxNo", busiName))
		{
        	VData mVData = tBusinessDelegate.getResult() ;
        	TransferData mTransferData = (TransferData) mVData.getObjectByObjectName("TransferData", 0);
        	//loggerDebug("ProposalPrintSave",mVData);
        	//loggerDebug("ProposalPrintSave",mTransferData);
        	tSN = (String)mTransferData.getValueByName("SN");
        	loggerDebug("ProposalPrintSave",tSN);
		} 
	if ("".equals(tSN)) {
		FlagStr = "Fail";
	} else {
		try {
		//ֻ�д���ɹ��ı�����������0ʱ�Ž��ж�д�ļ�
		if (handleFunction(session, request) && tSucc > 0) {
		//String strTemplatePath = application.getRealPath("xerox/printdata") + "/";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("printdatanew");
		LDSysVarSet tLDSysVarSet = new LDSysVarSet();
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();
		tLDSysVarSet = tLDSysVarDB.query();
		if (tLDSysVarSet.size() > 0) {
			tLDSysVarSchema = tLDSysVarSet.get(1);
		} else {
			FlagStr = "Fail";
			throw new Exception("�޷���ȡ����XML�ļ���Ӧ�÷�������·����");
		}

		String strTemplatePath = tLDSysVarSchema
				.getSysVarValue().trim();
		//strTemplatePath = "c:/XML/"; //���ڲ�����ʱ����C��

		// It is important to use buffered streams to enhance performance
		BufferedInputStream bufIs = new BufferedInputStream(ins);

		String strTime = PubFun.getCurrentDate() + "_"
				+ PubFun.getCurrentTime();
		strTime = strTime.replace(':', '_').replace('-', '_');
		strTime = strTime + "_" + tSN.trim();
		loggerDebug("ProposalPrintSave","***************************************"
				+ strTime);
		//      add by ssx 2008-04-24 �����ּ�Ŀ¼
		String[] step = strTime.split("_");
		String year = step[0];
		String month = step[1];
		String day = step[2];
		strTemplatePath += year + "/" + month + "/" + day + "/";
		File file = new File(strTemplatePath);
		if (!file.exists()) {
			loggerDebug("ProposalPrintSave","��ʼ�½��ļ���!");
			file.mkdirs();
		} else {
			loggerDebug("ProposalPrintSave","Ŀ¼�Ѿ�����: ");
		} //add end
		loggerDebug("ProposalPrintSave","����XML�ļ�������Ӧ�÷�������·���ǣ�"
				+ strTemplatePath);
		FileOutputStream fos = new FileOutputStream(
				strTemplatePath + "LCPolData" + strTime
				+ ".xml");
		BufferedOutputStream bufOs = new BufferedOutputStream(
				fos);
		int n = 0;

		while ((n = bufIs.read()) != -1) {
			bufOs.write(n);
		}
		bufOs.flush();
		bufOs.close();

		//FTP�Ĵ�ӡ������
		LisFtpClient tLisFtpClient = new LisFtpClient();
		if (!tLisFtpClient.UpLoadFile(strTemplatePath
				+ "LCPolData" + strTime + ".xml", "LCPolData"
				+ strTime + ".xml")) {
			FlagStr = "Fail";
    
		} else {
			FlagStr = "Succ";			
		}

			} else {
		FlagStr = "Fail";
			}
		} catch (Exception ex) {
			FlagStr = "Fail";
			ex.printStackTrace();
		}
	}
  PrintContent="�����ɹ����,���δ�ӡ�������ɹ���ӡ"+tSucc+"��,ʧ��"+tFail+"��!"; 
	String strTime1 = PubFun.getCurrentDate() + "_"
			+ PubFun.getCurrentTime();
   tSucc=0;
   tFail=0;
	loggerDebug("ProposalPrintSave","������ӡ��" + PrintContent + strTime1);
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=PrintContent%>");
</script>
</html>

