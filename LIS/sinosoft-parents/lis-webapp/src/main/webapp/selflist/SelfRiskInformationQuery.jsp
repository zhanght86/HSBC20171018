<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：SelfRiskInformationQuery.jsp
//程序功能：自助卡单险种信息查询
//创建日期：2008-03-14
//创建人  ：zz
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tCertifyNo = "";
	try
	{
		tCertifyNo = request.getParameter("CertifyNo");
		loggerDebug("SelfRiskInformationQuery","险种信息查询获得卡号="+tCertifyNo);
	}
	catch( Exception e )
	{
		tCertifyNo = "";
	}
	
	String tCertifyCode=""; //单证类型
	
	if(!tCertifyNo.equals(""))
	{
		//没有借位
		if(tCertifyNo.substring(4,5).equals("0")&&tCertifyNo.substring(5,6).equals("0"))
		{
			tCertifyCode=tCertifyNo.substring(2,4)+tCertifyNo.substring(6,8);
		}
		  
	    //两位都被借,即四位版本号的第1位被借用
		else if(tCertifyNo.substring(5,6).equals("0"))
		{
			tCertifyCode=tCertifyNo.substring(2,8);
		}
		//四位版本号的第2位被借用
		else
		{
			tCertifyCode=tCertifyNo.substring(2,4)+tCertifyNo.substring(5,8);
		}

		loggerDebug("SelfRiskInformationQuery","卡号"+tCertifyNo+"对应的单证类型:"+tCertifyCode);
		
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

%>
<script>
    var CertifyCode="<%=tCertifyCode%>"
</script>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="SelfRiskInformationQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SelfRiskInformationQueryInit.jsp"%>
  <title>险种基本信息查询</title>
</head>
<body  onload="initForm();" >
  <form action="" method=post name=fm id=fm target="fraSubmit">
    <!-- 险种信息部分 -->
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCertifyInfo);"></td>
    	<td class= titleImg>险种基本信息</td></tr>
    </table>
    
  	<div id="divRiskInfo" style="display: ''">
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanRiskInfo"></span></td></tr>
      </table>					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
