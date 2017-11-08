
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
	/****************************************团险理赔打印**********************************************/
	//初始化
	ManagePrtBill myManagePrtBill = new ManagePrtBill();
	CreateClaim MYCreateClaim = new CreateClaim();
	String strErrInfo = ""; //出错信息

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI"); //添加页面控件的初始化。

	String ServerIP = myManagePrtBill.getServerIP(); //服务器IP(现在写的是固定值,在类里写死了。)
	String ClientIP = request.getRemoteAddr(); //客户端IP
	String ClientHost = request.getRemoteHost(); //客户端机器名

	String ManageCom = request.getParameter("ManageCom"); //机构
	if (ManageCom == null || ManageCom.trim().length() == 0) {
		ManageCom = tG.ComCode;
	}
	String Operator = tG.Operator; //操作员 

	String RgtNo = request.getParameter("RgtNo"); //理赔批单号
	String option = request.getParameter("operator"); // 传入的操作
	loggerDebug("ClaimPrtPDF","团险理赔业务打印：传入参数RgtNo=" + RgtNo + "，操作operator=" + option);

	String RealPath = application.getRealPath("/"); //获取realpath
	String filename1 = "";
	String filename = "";

	/*************************1、理赔批单打印 *****************************/
	try {
		if ("batch".equals(option)) {
			loggerDebug("ClaimPrtPDF","======理赔批单打印RgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 25, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			strErrInfo = MYCreateClaim.CreateClaimBatch(filename, RgtNo, ManageCom).trim(); //生成pdf文件

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //转向显示PDF文件
			}
		}
	} catch (Exception e1) {
		strErrInfo = "理赔批单打印出错!" + e1.getMessage();
	}

	/*************************2、理赔支付清单 *****************************/
	try {
		if ("PayBill".equals(option)) {
			loggerDebug("ClaimPrtPDF","======理赔支付清单RgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 27, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			strErrInfo = MYCreateClaim.CreateOutBill(filename, RgtNo, ManageCom).trim(); //生成pdf文件

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //转向显示PDF文件 
			}
		}
	} catch (Exception e1) {
		strErrInfo = "理赔支付清单打印出错!" + e1.getMessage();
	}

	/*************************3、理赔赔款清单 *****************************/
	try {
		if ("GetBill".equals(option)) {
			loggerDebug("ClaimPrtPDF","======理赔赔款清单RgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 26, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			strErrInfo = MYCreateClaim.CreatePayBill(filename, RgtNo, ManageCom).trim(); //生成pdf文件

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //转向显示PDF文件        
			}
		}
	} catch (Exception e1) {
		strErrInfo = "赔款清单打印出错！" + e1.getMessage();
	}

	/*************************4、理赔给付通知 *****************************/
	try {
		if ("PrintPayNotice".equals(option)) {
			String InsuredNo = request.getParameter("InsuredNo"); //被保人客户号

			loggerDebug("ClaimPrtPDF","======理赔给付通知RgtNo=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo + InsuredNo, 40, ClientIP, ".pdf",
			RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=" + filename);

			//LLCaseReceiptClassExcel myLLCaseReceiptClassExcel = new LLCaseReceiptClassExcel();
			//strErrInfo = myLLCaseReceiptClassExcel.CreateReceiptExcel(filename, RgtNo);
			strErrInfo = MYCreateClaim.CreatePayNotice(filename, RgtNo, InsuredNo, ManageCom).trim(); //生成pdf文件

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //转向显示PDF文件 
			}
		}
	} catch (Exception e1) {
		strErrInfo = "理赔给付通知打印出错!" + e1.getMessage();
	}

	/**************************5、团险理赔决定通知书***************************/
	try {
		if ("PrintClaimPayTR".equals(option)) {
			loggerDebug("ClaimPrtPDF","======理赔通融/协议赔付通知书=" + RgtNo);
			filename1 = myManagePrtBill.setPrintPath(RgtNo, 42, ClientIP, ".pdf", RealPath);
			filename = RealPath + filename1;
			loggerDebug("ClaimPrtPDF","======filename=======:" + filename);

			strErrInfo = MYCreateClaim.CreateClaimNotice(filename, RgtNo, ManageCom).trim(); //生成pdf文件

			if (strErrInfo.length() == 0) {
		response.sendRedirect("../" + filename1); //转向显示PDF文件     
			}
		}
	} catch (Exception e1) {
		strErrInfo = "通融协议赔付通知书打印出错！" + e1.getMessage();
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
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		top.close();
	}
	else
	{
	    content = "生成文件成功！";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	    top.close();
    }
</script>
</body>
</html>

