<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="BPOReSendInput.js"></SCRIPT>
  <title>BPOReSend</title>
</head>
<body>
  <form method=post name=fm id="fm" action= "./BPOReSendSave.jsp" target="fraSubmit">
    <!-- 单张保单分保计算 -->
    <div class="maxbox1">
	    <Table  class= common align=center>
			  <TR  class= common>
			    <TD class=title5>合同号</TD>
	        <TD  class= input5><Input class="common wid" name=PrtNo id="PrtNo" ></TD>
          <td class=title5></td>
          <td class= input5></td>
        </TR>
      </table>
    </div>
<a href="javascript:void(0)" class=button name=BPOReSend id="BPOReSend" onclick="SubmitForm();">BPOReSend</a>
<!-- <INPUT  class=cssButton name=BPOReSend id="BPOReSend" VALUE="BPOReSend" TYPE=button onclick="SubmitForm();"> -->

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
