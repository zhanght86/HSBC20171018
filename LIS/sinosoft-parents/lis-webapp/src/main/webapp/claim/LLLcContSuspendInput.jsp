<%
//Name: LLLcContSuspendInput.jsp
//Function：保单挂起页面
//Date：
//Author ：yuejinwei
//Modify : zhoulei
//Modify date : 2005-11-19 14:18
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN，接收参数==========================================================
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");
		  String tInsuredNo = request.getParameter("InsuredNo");	//被保人客户号
		  String tClmNo = request.getParameter("ClmNo");
	//==========END====================================================================
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLcContSuspend.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLcContSuspendInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> 赔案无关保单挂起清单 </td>
        </tr>
    </table>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>
        </table>
    </div>

    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContIn);"></td>
            <td class= titleImg> 赔案相关保单挂起清单 </td>
        </tr>
    </table>
    <Div  id= "divLLLcContIn" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContInGrid" ></span></td>
            </tr>
        </table></div>
        <!-- <input class=cssButton  type=button value=" 保 存 " onclick="saveClick()"> -->
        <!--<input class=cssButton  type=button value=" 查 询 " onclick="queryClick()">
        <input class=cssButton  type=button value=" 返 回 " onclick="top.close()">--><br>
        <a href="javascript:void(0);" class="button" onClick="saveClick();">保    存</a> <!-- add  wyc -->
        <a href="javascript:void(0);" class="button" onClick="queryClick();">查    询</a>
        <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    

    <!--隐藏区,保存信息用-->
    <Input type=hidden id="InsuredNo" name="InsuredNo"><!--被保人客户号码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->
    <Input type=hidden id="ClmNo" name="ClmNo">

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
