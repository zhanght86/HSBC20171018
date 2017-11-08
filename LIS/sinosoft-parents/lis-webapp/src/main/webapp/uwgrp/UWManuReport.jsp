<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWManuReport.jsp
//程序功能：人工核保核保报告录入
//创建日期：
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWManuReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 核保分析报告书 </title>
  <%@include file="UWManuReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWManuReportChk.jsp">
    <!-- 以往核保记录部分（列表） -->
    <table class= common>
    	<tr class= common>
    	    <td class= title>
    	        投保单号
    	    </td>
            <TD  class= input>
                <Input class= "readonly" name=ContNo >
            </TD>
    	    <td class= title>
    	        核保人
    	    </td>
            <TD  class= input>
                <Input class= "readonly" name=Operator >
            </TD>
    	</tr>
    </table>
    <table class= common>
    	<TR  class= common>
          <TD  class= title>
            报告内容
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="Content" cols="130" rows="30" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
    <Div  id= "divButton" style= "display: ''">
      <INPUT type= "button" class=common name= "sure" value="确认" onclick="inputReport()">
      <INPUT type= "button" class=common name= "sure" value="查询下级核保员分析报告" onclick="window.open('./UWQuerySubReportMain.jsp?ContNo='+fm.ContNo.value);">
    </Div>
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
