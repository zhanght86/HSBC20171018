<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：CollectorQuery.jsp
//程序功能：收费员基本信息查询
//创建日期：2005-09-30
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tAgentCode = request.getParameter("AgentCode"); 
%>

<head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="CollectorQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CollectorQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
  	
<table>
  <tr>
    <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCollector1);">
    </td>
    <td class= titleImg>
  	  收费员基本信息 
  	</td>
  </tr>
</table>

<Div  id= "divLCCollector1" style= "display: ''" align=center>
  <table  class= common>
   	<tr  class= common>
  	  <td text-align: left colSpan=1 >
				<span id="spanCollectorGrid" ></span> 
			</td>
		</tr>
	</table>     
</div>

    <p><INPUT VALUE="返  回" class= cssButton TYPE=button onClick="parent.close();"></P>
    <!--读取信息-->
    
    <Input type=hidden id="AgentCode" name="AgentCode"><!--收费员编号-->
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
