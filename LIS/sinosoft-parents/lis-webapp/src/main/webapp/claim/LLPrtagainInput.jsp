<%
//**************************************************************************************************
//文件名称：LLPrtagainInput.jsp
//文件功能：单证补打原因录入页面
//建立人：yuejw
//建立日期: 2005-08-21
//页面描述: 用于在补打单证时录入补打原因
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	String tPrtSeq=request.getParameter("PrtSeq"); //流水号
	String tPrtType=request.getParameter("prtType"); //批单类型
	String tCustomerNo = request.getParameter("CustomerNo");
	String tRgtClass = request.getParameter("RgtClass");
%>
<head>
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
    <SCRIPT src="LLPrtagain.js"></SCRIPT>
    <%@include file="LLPrtagainInit.jsp"%>
</head>

<body  onload="initForm()" >
<form method=post name=fm id=fm target="fraSubmit">
	<Div id= "" class=maxbox style= "display: ''">
		<Table class=common>
            <TR class=common>
                <TD class=title> 流水号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="PrtSeq" id=PrtSeq></TD>
                <TD class=title> 赔案号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="OtherNo" id=OtherNo></TD>
                <TD class=title> 单据类型 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="Code" id=Code></TD>                
            </TR>
            <TR class=common>
		        <TD class=title>发起人</TD> 
		        <TD class= input><Input class="readonly wid" readonly  name="ReqOperator"></TD>
		        <TD class=title> 发起机构 </TD>  <!--##ondblclick="return showCodeList('stati',[this,ReqComName],[0,1]);"##-->
		        <TD class= input><Input class=codeno readonly name="ReqCom" id=ReqCom onkeyup="return showCodeListKey('stati',[this,ReqComName],[0,1]);">
				<input class=codename name="ReqComName" id=ReqComName readonly ></TD>
                <TD class=title> 管理机构 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="ManageCom" id=ManageCom></TD>
            </TR>
        	<TR  class= common>
                <TD class=title> 执行人 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="ExeOperator" id=ExeOperator ></TD>
                <TD class=title> 打印方式 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="PrtType" id=PrtType ></TD>
                <TD class=title> 打印状态 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="StateFlag" id=StateFlag ></TD>
		    </TR>
        </table>
        <hr class=line>
        <Table class=common>
		    <TR class= common>
			  <TD class= title> 请录入补打原因<font size=1 color='#ff0000'><b>*</b></font> </TD>
			</TR> 
			<TR class= common>       
			  <TD colspan="6" class=normal> <textarea name="Remark" id=Remark rows="4"  cols=120 class="common"  ></textarea></TD>
			</TR>   
		</Table>
	</Div>
    <table>
        <tr>
            <td><input class=cssButton name="PrtagainReasion" id=PrtagainReasion VALUE="补打原因保存"  TYPE=button onclick="showPrtagainReasion()"></td>
            <td><input class=cssButton  disabled name="Prtagain" id=Prtagain VALUE="补打单证"  TYPE=button onclick="showPrtAffix()"></td>
            <td><input class=cssButton name="back" id=back VALUE="返   回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <!---隐藏区域--->
	<input type=hidden id="PrtCode" name="PrtCode">
	<input type=hidden id="ClmNo" name="ClmNo">
	<input type=hidden id="customerNo" name="customerNo">
	<input type=hidden id="RgtClass" name="RgtClass">
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id=prtType1 name="prtType1">
	
    
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
