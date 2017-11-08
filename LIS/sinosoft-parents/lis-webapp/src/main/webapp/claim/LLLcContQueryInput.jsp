<%
//Name: LLLcContQueryInit.jsp
//Function：保单查询页面
//Date：2005.06.21
//Author ：quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN，接收参数
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  
		  String tAppntNo = request.getParameter("tAppntNo");	////投保人客户号
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLcContQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLcContQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> 保单查询清单  </td>
        </tr>
    </table>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
        <Div  id= "divLcContState" style= "display: none">
            <table>
                <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divState);"></td>
                    <td class= titleImg> 保单状态信息 </td>
                </tr>
            </table>
            <div id= "divState" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1><span id="spanLcContStateGrid" ></span></td>
                    </tr>      
                </table>    
            </div>
        </div>
    </div>
    <table style="display:none">
        <tr>
            <td><input class=cssButton  type=button value="保单详细" onclick="queryClick()"></td>
            <td><input class=cssButton  type=button value="返  回" onclick="top.close()"></td>
        </tr>      
    </table>
    <br> 
    <a href="javascript:void(0);" class="button" onClick="queryClick();">保单详细</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>  
    
    <!--隐藏区,保存信息用-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--投保人客户号码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
