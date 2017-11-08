<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT> 
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="DiskApplyErrorInit.jsp"%>
<SCRIPT src="DiskApplyError.js"></SCRIPT>
<title>磁盘导入失败信息</title>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
	<table id="table1">
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divError);">
			</td>
			<td class="titleImg">导入失败，失败信息：</td>
		</tr>
	</table>
	<Div id="divError" style="display:  ">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanImportErrorGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>
    <Div  id = "divPage" align=center style = "display: none ">
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
		<INPUT VALUE="尾  页" class=cssButton92 TYPE=button onclick="turnPage.lastPage();">
    </Div>
    <Div id="divOtherError" style="display: none">
        <table>
            <tr>
    			<td class=common>
    				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divError);">
    			</td>
    			<td class="titleImg">其它错误信息：</td>
		    </tr>
        </table>
        <table>            
            <tr class=common>
				<td class=title>
					<textarea name="Remark" id=Remark cols="100%" rows="3" class="common"></textarea>
				</td>
			</tr>
		</table>
	</Div>
    <p>
        <INPUT VALUE="打印错误信息" class="cssButton"  TYPE=button onclick="errorPrint();"> 
        <INPUT VALUE="返  回"class="cssButton"  TYPE=button onclick="parent.close();"> 					
    </p>
	<input type=hidden name=GrpContNo id=GrpContNo value="">
	<input type=hidden name=BatchNo id=BatchNo value="">
	<input type=hidden name=ImportFlag id=ImportFlag value="">
	<br /><br /><br /><br />
</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
