<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSendPrint.jsp
//程序功能：送打印队列
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="GrpUWSendPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpUWSendPrintInit.jsp"%>
  <title>发送通知书</title>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tOtherNoType%>','<%=tCode%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./GrpUWSendPrintChk.jsp">
    <!-- 以往核保记录部分（列表） -->
    <table>
    	<tr>
    		<td class= titleImg>
    			 人工核保数据存储......
    		</td>
    	</tr>
    </table> 
      <INPUT type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" id="OtherNoType" name= "OtherNoType" value="">
      <input type= "hidden" id="Code" name= "Code" value= "">			
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
