
<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.ebaprt.claim.*"%>
<%@page import="com.sinosoft.lis.ebaprt.uwprt.*"%>
<%@ page
	import="java.io.*,java.awt.Color,com.lowagie.text.*,com.lowagie.text.pdf.*,java.awt.font.*"%>
<html>
<body>
<%
	/****************************************���������ӡ**********************************************/
	//��ʼ��
	ManagePrtBill myManagePrtBill = new ManagePrtBill();
	CreateClaim MYCreateClaim = new CreateClaim();
	String strErrInfo = ""; //������Ϣ

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����

	String ServerIP = myManagePrtBill.getServerIP(); //������IP(����д���ǹ̶�ֵ,������д���ˡ�)
	String ClientIP = request.getRemoteAddr(); //�ͻ���IP
	String ClientHost = request.getRemoteHost(); //�ͻ��˻�����

	String ManageCom = request.getParameter("ManageCom"); //����
	if (ManageCom == null || ManageCom.trim().length() == 0) {
		ManageCom = tG.ComCode;
	}
	String Operator = tG.Operator; //����Ա 

	String RgtNo = request.getParameter("RgtNo"); //����������
	String option = request.getParameter("operator"); // ����Ĳ���
	loggerDebug("ClaimPrtPDF","��������ҵ���ӡ���������RgtNo=" + RgtNo + "������operator=" + option);

	String RealPath = application.getRealPath("/"); //��ȡrealpath
	String filename1 = "";
	String filename = "";

	/*************************1������������ӡ *****************************/
	try {
		if ("batch".equals(option)) {
			loggerDebug("ClaimPrtPDF","======����������ӡRgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 25, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			strErrInfo = MYCreateClaim.CreateClaimBatch(filename, RgtNo, ManageCom).trim(); //����pdf�ļ�

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //ת����ʾPDF�ļ�
			}
		}
	} catch (Exception e1) {
		strErrInfo = "����������ӡ����!" + e1.getMessage();
	}

	/*************************2������֧���嵥 *****************************/
	try {
		if ("PayBill".equals(option)) {
			loggerDebug("ClaimPrtPDF","======����֧���嵥RgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 27, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			strErrInfo = MYCreateClaim.CreateOutBill(filename, RgtNo, ManageCom).trim(); //����pdf�ļ�

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //ת����ʾPDF�ļ� 
			}
		}
	} catch (Exception e1) {
		strErrInfo = "����֧���嵥��ӡ����!" + e1.getMessage();
	}

	/*************************3����������嵥 *****************************/
	try {
		if ("GetBill".equals(option)) {
			loggerDebug("ClaimPrtPDF","======��������嵥RgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 26, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			strErrInfo = MYCreateClaim.CreatePayBill(filename, RgtNo, ManageCom).trim(); //����pdf�ļ�

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //ת����ʾPDF�ļ�        
			}
		}
	} catch (Exception e1) {
		strErrInfo = "����嵥��ӡ����" + e1.getMessage();
	}

	/*************************4���������֪ͨ *****************************/
	try {
		if ("PrintPayNotice".equals(option)) {
			String InsuredNo = request.getParameter("InsuredNo"); //�����˿ͻ���

			loggerDebug("ClaimPrtPDF","======�������֪ͨRgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo + InsuredNo, 40, ClientIP, ".pdf",
			RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			//LLCaseReceiptClassExcel myLLCaseReceiptClassExcel = new LLCaseReceiptClassExcel();
			//strErrInfo = myLLCaseReceiptClassExcel.CreateReceiptExcel(filename, RgtNo);
			strErrInfo = MYCreateClaim.CreatePayNotice(filename, RgtNo, InsuredNo, ManageCom).trim(); //����pdf�ļ�

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //ת����ʾPDF�ļ� 
			}
		}
	} catch (Exception e1) {
		strErrInfo = "�������֪ͨ��ӡ����!" + e1.getMessage();
	}

	/**************************5�������������֪ͨ��***************************/
	try {
		if ("PrintClaimPayTR".equals(option)) {
			loggerDebug("ClaimPrtPDF","======����ͨ��/Э���⸶֪ͨ��=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 42, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=======:" + filename);

			strErrInfo = MYCreateClaim.CreateClaimNotice(filename, RgtNo, ManageCom).trim(); //����pdf�ļ�

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //ת����ʾPDF�ļ�     
			}
		}
	} catch (Exception e1) {
		strErrInfo = "ͨ��Э���⸶֪ͨ���ӡ����" + e1.getMessage();
	}

	/*************************end*****************************/
	String flag = "Sucess";
	if (strErrInfo.trim().length() > 1) {
		flag = "Fail";
		loggerDebug("ClaimPrtPDF",strErrInfo);
	}
%>

<script language="javascript">
var FlagStr='<%=flag%>';
var content='<%=strErrInfo%>';
if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		top.close();
	}
	else
	{
	    content = "�����ļ��ɹ���";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	    top.close();
    }
</script>
</body>
</html>

