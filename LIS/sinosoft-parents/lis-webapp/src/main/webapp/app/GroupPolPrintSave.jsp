<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�GroupPolPrintSave.jsp
	//�����ܣ�
	//�������ڣ�2002-11-26
	//������  ��Kevin
	//�޸���  �������
	//���¼�¼��  ������    ��������     ����ԭ��/����
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

<%!InputStream ins = null;
	String PrintContent = "";
	String FlagStr = "";
	String Content = "";
	int tFail = 0;
	int tSucc = 0;
	boolean handleFunction(HttpSession session, HttpServletRequest request,
			String szTemplatePath) {
		String tOperate = "";

		//���mutline�е�������Ϣ
		int nIndex = 0;
		String tLCGrpContGrids[] = request.getParameterValues("GrpContGridNo");
		String tGrpContNo[] = request.getParameterValues("GrpContGrid1");
		String tPrintCount[] = request.getParameterValues("GrpContGrid6");
		String tChecks[] = request.getParameterValues("InpGrpContGridChk");

		//���session�е���Ա��Ϣ
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		//������������
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		//LCGrpContF1PUI tLCGrpContF1PUI = new LCGrpContF1PUI();
		VData vData = new VData();

		//ѭ����ӡѡ�е��ŵ�
		for (nIndex = 0; nIndex < tChecks.length; nIndex++) {
			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			//If this line isn't selected, continue�����û��ѡ�е�ǰ�У������
			if (!tChecks[nIndex].equals("1")) {
				continue;
			}
			//�����ݷ����ͬ��������

			tLCGrpContSchema = new LCGrpContSchema();
			tLCGrpContSchema.setGrpContNo(tGrpContNo[nIndex]);
			//�ж���ӡģʽ
			loggerDebug("GroupPolPrintSave","��ӡģʽ" + tPrintCount[nIndex]);
			if (tPrintCount[nIndex].compareTo("0") == 0) {
				tOperate = "PRINT";
			} else {
				tOperate = "REPRINT";
			}
			tLCGrpContSet.add(tLCGrpContSchema);
		}
		//�����ݼ��Ϸ��������У�׼�������̨����
		vData = new VData();
		vData.add(tG);
		vData.addElement(tLCGrpContSet);
		vData.add(szTemplatePath);
		//vData.add(sOutXmlPath);
		//ִ�к�̨����
		String busiName="f1printLCGrpContF1PUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		try {
			if (!tBusinessDelegate.submitData(vData, "PRINT",busiName)) {
				PrintContent = tBusinessDelegate.getCErrors().getFirstError();
				return false;
			} else {
				ins = (InputStream) (tBusinessDelegate.getResult().get(0));
				//PrintContent = tLCGrpContF1PUI.mContent;
				//tSucc += tBusinessDelegate.tSucc;
				//tFail += tBusinessDelegate.tFail;
				VData tVData=tBusinessDelegate.getResult();
				TransferData tTransferData=(TransferData)tVData.getObjectByObjectName("TransferData",0);
				PrintContent =(String)tTransferData.getValueByName("Content");
				tSucc =tSucc+(Integer)tTransferData.getValueByName("Succ");
				tFail =tFail+(Integer)tTransferData.getValueByName("Fail");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Content += ex.getMessage();
			return false;
		}
		return true;
	}%>

<%
	//add by yt 20040426��Ϊ�˽��ͬʱ������ڴ�ӡ�ᵼ�²���ͬһ���ļ��������⡣
	String tSN = "";
	tSN = PubFun1.CreateMaxNo("SNPolPrint", 10);
	if (tSN.equals("")) {
		FlagStr = "Fail";
		loggerDebug("GroupPolPrintSave","������ˮ��ʧ�ܣ��������´�ӡ!");
	} else {
		try {
			//���ڱ�����ӡ�õ�����ģ�壬��Ҫ�����ŵ��±������嵥
			String szTemplatePath = application
			.getRealPath("f1print/template/")
			+ "/"; //ģ��·��
			loggerDebug("GroupPolPrintSave","�����ŵ��±������嵥ģ��XML�ļ�������Ӧ�÷�������·���ǣ�"
			+ szTemplatePath);
			if (handleFunction(session, request, szTemplatePath)
			&& tSucc > 0) {
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
			loggerDebug("GroupPolPrintSave","�޷���ȡ����XML�ļ���Ӧ�÷�������·��!");
		}
		//���ڱ�����Ӧ�÷�������ĳ��·��֮��        
		String strTemplatePath = tLDSysVarSchema
				.getSysVarValue().trim();
		//strTemplatePath = "c:/XML/"; //���ڲ�����ʱ����C��

		// It is important to use buffered streams to enhance performance
		BufferedInputStream bufIs = new BufferedInputStream(ins);
		String strTime = PubFun.getCurrentDate() + "_"
				+ PubFun.getCurrentTime();
		strTime = strTime.replace(':', '_').replace('-', '_');
		strTime = strTime + "_" + tSN.trim();
		//add by ssx 2008-04-24 �����ּ�Ŀ¼
		String[] step = strTime.split("_");
		String year = step[0];
		String month = step[1];
		String day = step[2];
		strTemplatePath += year + "/" + month + "/" + day + "/";
		File file = new File(strTemplatePath);
		if (!file.exists()) {
			loggerDebug("GroupPolPrintSave","��ʼ�½��ļ���!");
			file.mkdirs();
		} else {
			loggerDebug("GroupPolPrintSave","Ŀ¼�Ѿ�����: ");
		} //add end
		loggerDebug("GroupPolPrintSave","����XML�ļ�������Ӧ�÷�������·���ǣ�"
				+ strTemplatePath);
		FileOutputStream fos = new FileOutputStream(
				strTemplatePath + "LCGrpPolData" + strTime
				+ ".xml");
		BufferedOutputStream bufOs = new BufferedOutputStream(
				fos);
		int n = 0;

		while ((n = bufIs.read()) != -1) {
			bufOs.write(n);
		}
		bufOs.flush();
		bufOs.close();

		//��XML���䵽ָ����FTP�Ĵ�ӡ������
		LisFtpClient tLisFtpClient = new LisFtpClient();
		if (!tLisFtpClient.UpLoadFile(strTemplatePath
				+ "LCGrpPolData" + strTime + ".xml",
				"LCGrpPolData" + strTime + ".xml")) {
			FlagStr = "Fail";
			loggerDebug("GroupPolPrintSave","FTP�����ļ�ʧ�ܣ�������ط��������ã�ԭ�������·������,�ļ�Ŀ����̿ռ䲻��ȵȣ�");
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
	PrintContent = "�����ɹ����,���δ�ӡ�������ɹ���ӡ" + tSucc + "��,ʧ��" + tFail
			+ "��!";
   tSucc=0;
   tFail=0;			
	String strTime1 = PubFun.getCurrentDate() + "_"
			+ PubFun.getCurrentTime();
	loggerDebug("GroupPolPrintSave","������ӡ��" + PrintContent + strTime1);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=PrintContent%>");
</script>
</html>
