<%
//程序名称：BPOOperatorWorkQueryInput.jsp
//程序功能：录单外异常件工作量统计
//创建日期：2007-09-19 16:53
//创建人  ：JL
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BPOOperatorWorkQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>录单外包异常件工作量统计</title>
</head>
<body>
  <form action="./BPOOperatorWorkQuerySave.jsp" method=post name=fm id=fm target="f1print">
    <table>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
    		<td class= titleImg>录单外包异常件工作量统计</td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" style= "display: ''" class="maxbox1">
      <table  class= common>
　　　　 <TR  class= common>
       		<TD  class= title5>开始日期</TD>
       		<TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
       		<TD  class= title5>结束日期</TD>
       		<TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
        </TR>
      </table>
      
      </div>
      
      <!--<INPUT VALUE="打印报表" class=cssButton TYPE=button name=btnSubmit onclick="submitForm();"> -->
      <a href="javascript:void(0);" name=btnSubmit class="button" onClick="submitForm();">打印报表</a>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
