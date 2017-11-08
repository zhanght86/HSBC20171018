<%
//程序名称：LLColQueryAccDetailInput.jsp
//程序功能：伤残代码查询
//创建日期：2005-9-6 17:46
//创建人  ：zhoulei
//更新记录：niuzj,2005-12-23,把“出险细节”修改为“意外细节”
%>
<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>

<head >
	<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tCode = request.getParameter("codeno");
    String tName = request.getParameter("codename");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLColQueryAccDetail.js"></SCRIPT>
    <%@include file="LLColQueryAccDetailInit.jsp"%>
</head>
<title>意外细节查询</title>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <br>
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryAccDetail);"></TD>
	        <TD class= titleImg>意外细节查询</TD>
	     </TR>
	</Table>
	<Div  id= "QueryAccDetail" class=maxbox1 style= "display:''">
	    <Table  class= common>
            <TR class= common>
				<TD  class= title>意外细节代码</TD>
				<TD  class= input> <input class="common wid" name=ICDCodeQ id=ICDCodeQ></TD>
                <TD  class= title>意外细节名称</TD>
                <TD  class= input> <input class=common name=ICDNameQ id=ICDNameQ></TD>
                <TD  class= title> </TD>
				<TD  class= input> </TD>
	       </TR>
	    </Table>
	</Div>
    <table>
        <tr>
	        <td><input value="查  询" class= cssButton type=button onclick="InitQueryClick();"></td>
	        <td><input value="返  回" class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLColQueryAccDetailGrid);"></TD>
	          <TD class= titleImg>意外细节列表</TD>
	     </TR>
	</Table>
    <Div id= "DivLLColQueryAccDetailGrid" style= "display:''" align = center>
		<Table  class= common>
		    <TR>
		    	<TD style="text-align: left" colSpan=1><span id="spanLLColQueryAccDetailGrid"></span></TD>
		    </TR>
		</Table>
        <table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
	</Div>

    <!--隐藏表单区域-->
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--咨询通知号码-->

 	<input type=hidden id="tOperator" name=tOperator >
	<input type=hidden id="tComCode" name=tComCode >
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="HosCode" name="HosCode"> <!--调用查询对应的代码input名-->
	<input type=hidden id="HosName" name="HosName"> <!--调用查询对应的名称input名-->
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
