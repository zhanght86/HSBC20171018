<%@include file="/i18n/language.jsp"%>
<%
//PersonalFinDayCheckNCLPrintPDF.jsp
//创建�?:lijs
//创建时间:2006-11-30
//机构日结打印成PDF处理�?

%>
<html>
	<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<%@page import="com.sinosoft.lis.logon.MenuShow"%>
	<%@page import="com.sinosoft.utility.*"%>
	<%@page import="com.sinosoft.lis.f1print.*"%>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.lowagie.text.*"%>
    <%@page import="com.lowagie.text.pdf.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%//初始�?

	loggerDebug("TestInterfacePrintPDF","TestInterfacePrintPDF.JSP----start from here@-------");		
	//出错信息
			String strErrInfo = "";

			//获取realpath
			String RealPath = application.getRealPath("").replace('\\', '/'); //new TemplateToPdf().getPdfRealPath();
			//在tomcat里执行的时�?�需要将下面语句注释�?

        	String lastC = RealPath.substring(RealPath.length()-1);
            if(!lastC.trim().equalsIgnoreCase("/")&&!lastC.trim().equalsIgnoreCase("\\")){
                RealPath = RealPath + "/";
            }
            loggerDebug("TestInterfacePrintPDF","====RealPath=" + RealPath + "======");
			//取session中的登录用户名及管理机构
			GlobalInput tG = new GlobalInput();
			tG = (GlobalInput) session.getValue("GI");//添加页面控件的初始化�?


			String operator = request.getParameter("operator");
			loggerDebug("TestInterfacePrintPDF","====operator=" + operator + "======");

			String sSerialNo = request.getParameter("serialNo");//日结类型
			//String sOtherNoType = request.getParameter("othernotype");//结束时间	
			//String m_sOperator = request.getParameter("m_sOperator");
			
			VData tVData = new VData();
			tVData.add(sSerialNo);
			tVData.addElement(tG);

			try {
				// 打印附件
					if (operator.equalsIgnoreCase("printpdf")) {						
						TestInterfacePrintPDF oTestInterfacePrintPDF = new TestInterfacePrintPDF(RealPath,tVData);
						strErrInfo = oTestInterfacePrintPDF.prtAttachPdf();
						loggerDebug("TestInterfacePrintPDF","strErrInfo=====" + strErrInfo);

						if (strErrInfo.length() == 0) {
							response.sendRedirect("../" + oTestInterfacePrintPDF.getfilename1());
						} else {
							strErrInfo = strErrInfo + bundle.getString("M0000250758");
						}		
					}
			} catch (Exception e1) {
				strErrInfo = bundle.getString("M0000250759") + e1.getMessage();
			}

			String flag = "Sucess";
			if (strErrInfo.trim().length() > 1) {
				flag = "Fail";
			}
%>
	<!--p align="center"><font color="blue" size=3"><%=strErrInfo%></font></p-->
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
	    content = "<%=bundle.getString("M0000250760")%>";
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
	<body>

	</body>
</html>
