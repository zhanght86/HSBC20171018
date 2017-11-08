<%
//程序名称：LLColQueryHospitalInput.jsp
//程序功能：医院信息维护
//创建日期：2005-9-6 9:48
//创建人  ：zhoulei
//更新记录：
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
    <SCRIPT src="LLColQueryHospital.js"></SCRIPT>
    <%@include file="LLColQueryHospitalInit.jsp"%>
</head>
<title>医院信息查询</title>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);"></TD>
	        <TD class= titleImg>医院信息查询</TD>
	     </TR>
	</Table>
	<Div  id= "QueryHospital" style= "display:''" class="maxbox1">
	    <Table  class= common>
            <TR class= common>
				<TD  class= title>医院代码</TD>
				<TD  class= input> <input class="wid" class=common name=HospitalCodeQ id=HospitalCodeQ></TD>
                <TD  class= title>医院名称</TD>
                <TD  class= input> <input class="wid" class=common name=HospitalNameQ id=HospitalNameQ></TD>
                <TD  class= title>医院等级</TD>
				<TD  class= input> <input class="wid" class=common name=HospitalGradeQ id=HospitalGradeQ></TD>
	       </TR>
            <TR class= common>
				<TD  class= title>管理机构代码</TD><!--Modify by zhaorx 2006-05-23-->
				<TD  class= input> <input class="wid" class=common name=HospitalMngcomQ id=HospitalMngcomQ></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
				<TD  class= input></TD>
	       </TR>	       
	    </Table>
	</Div>
    <table style="display:none">
        <tr>
	        <td><input value="查  询" class= cssButton type=button onclick="InitQueryClick();"></td>
	        <td><input value="返  回" class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
    <a href="javascript:void(0);" class="button" onClick="InitQueryClick();">查    询</a>
   
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLColQueryHospitalGrid);"></TD>
	          <TD class= titleImg>推荐医院列表</TD>
	     </TR>
	</Table>
    <Div id= "DivLLColQueryHospitalGrid" style= "display:''" align = center>
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLColQueryHospitalGrid"></span></TD>
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
 <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
    <!--隐藏表单区域-->
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--咨询通知号码-->

 	<input type=hidden id="tOperator" name=tOperator >
	<input type=hidden id="tComCode" name=tComCode >
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="HosCode" name="HosCode"> <!--调用查询对应的代码input名-->
	<input type=hidden id="HosName" name="HosName"> <!--调用查询对应的名称input名-->
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
