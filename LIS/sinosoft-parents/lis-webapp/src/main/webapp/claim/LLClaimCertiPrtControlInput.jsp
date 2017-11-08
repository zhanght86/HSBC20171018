<html>
<%
//文件名称:LLClaimCertiPrtControlInput.jsp
//文件功能：理赔单证打印控制
//创建日期：2005-10-15  创建人 ：                   
//更新记录：
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<% 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");		  
	String tClmNo=request.getParameter("ClmNo"); //赔案号 
	loggerDebug("LLClaimCertiPrtControlInput","---赔案号=="+tClmNo);  		  
	%> 	
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimCertiPrtControl.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimCertiPrtControlInit.jsp"%>
</head>
<body onload="initForm();">
<form  method=post name=fm id=fm target="fraSubmit">
   
    <Table>
        <TR>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ClaimCertiList);"></TD>
            <td class= titleImg>该赔案下可以进行批量打印控制的单证列表</TD>
        </TR>
    </Table>
    <Div id= "ClaimCertiList" style= "display: ;text-align:center">
        <Table class= common>
            <TR class= common>
                <td style="text-align: left" colSpan=1><span id="spanClaimCertiGrid" ></span></TD>
            </TR>      
        </Table>
        <Table style="display:none">
             <TR>
                 <TD><Input class=cssButton90 value="首  页" type=button onclick="turnPage.firstPage();"></TD>
                 <TD><Input class=cssButton91 value="上一页" type=button onclick="turnPage.previousPage();"></TD>
                 <TD><Input class=cssButton92 value="下一页" type=button onclick="turnPage.nextPage();"></TD>
                 <TD><Input class=cssButton93 value="尾  页" type=button onclick="turnPage.lastPage();"></TD>
             </TR>
         </Table>
    </Div>
    <hr class=line>
    <Table>
		<TR>
			<TD><Input class=cssButton value="提交批量打印" type=button onclick="showCertiPrtControlSave();"></TD>
			<TD><Input class=cssButton value="返  回" type=button onclick="top.close()"></TD>
		</TR>
   </Table>
    <!--##隐藏区域##-->
   <Input class=common type=hidden id="ClmNo" name="ClmNo" >
   
</form>
 	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>




