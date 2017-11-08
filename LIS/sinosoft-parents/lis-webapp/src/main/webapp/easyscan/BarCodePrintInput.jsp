<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<SCRIPT src="BarCodePrintInput.js"></SCRIPT>
  
  <title>BarCodePrint</title>
</head>
<body  onload="initSubtype();" >
  <form method=post name=fm id=fm action= "./BarCodePrintSave.jsp" target="_blank">
  <div class="maxbox1">
	    <Table  class= common>
			<TR  class= common>
			   <TD class=title5>单证号</TD>
	          <TD  class=input5><Input class="wid" class=common name=DocCode id=DocCode ></TD>
			   <TD class=title5>类型</TD>
	          <TD  class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SubType id=SubType ondblClick="showCodeListEx('SubTypeEX',[this,SubTypeName],[0,1],null,null,null,'1');" onClick="showCodeListEx('SubTypeEX',[this,SubTypeName],[0,1],null,null,null,'1');" onkeyup="showCodeListKeyEx('SubTypeEX',[this,SubTypeName],[0,1],null,null,null,'1');"><Input class="codename" name=SubTypeName id=SubTypeName readonly></TD>
          </tr>
    </table></div>
	<INPUT  class=cssButton name=BarCodePrint VALUE="单证分隔页打印" TYPE=button onclick="SubmitForm();">
	<INPUT  class=cssButton name=BarCodePrint VALUE="类型分隔页打印" TYPE=button onclick="SubmitForm1();"><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script type="text/javascript">
function initSubtype() {
	var sql = "select subtype,subtypename from es_doc_def where busstype like '<%=request.getParameter("BussType")%>%%' order by subtype";
	turnPage.strQueryResult = easyQueryVer3(sql, 1, 1, 1);
	arrDataSet = turnPage.strQueryResult;
	fm.SubType.value = "";
	fm.SubType.CodeData = arrDataSet;
}
</script>
</html>
