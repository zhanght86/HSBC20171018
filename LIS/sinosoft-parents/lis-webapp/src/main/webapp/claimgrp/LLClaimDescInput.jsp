<%
//程序名称：LLSubReportDescInput.jsp
//程序功能：事故描述信息录入
//创建日期：2005-05-10
//创建人  ：zhoulei
//更新记录：
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
//==========BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//赔案号
	  String tCustomerNo = request.getParameter("custNo"); //出险人编码
	  String tStartPhase = request.getParameter("startPhase"); //提起阶段

//==========END
%>
    <title>事故描述信息录入</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLClaimDesc.js"></script>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimDescInit.jsp"%>

</head>
<body  onload="initForm()">
<form name=fm id=fm target=fraSubmit method=post>
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReportDesc1);"></TD>
            <TD class= titleImg>事故描述信息</TD>
        </TR>
    </table>
    <Div id= "divLLSubReportDesc1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLSubReportDescGrid" ></span></td>
            </tr>
        </table>
    </Div>
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReportDesc2);"></TD>
            <TD class= titleImg>描述信息</TD>
        </TR>
    </table>
    <Div id= "divLLSubReportDesc2" style= "display: ''" class="maxbox1">
        <!--TABLE class=common>
            <tr class=common>
                <td class= title> 描述类型 </td>
                <td class= input><Input type="input" class="common" name="DescType"></td>
                <td class= title></td>
                <td class= input></td>
                <td class= title></td>
                <td class= input></td>
            </tr>
        </TABLE-->
        <Table class= common>
            <tr class= common>
                <td class= title> 描述内容 </td>
            </tr>
            <tr class= common>
                <td colspan="6" style="padding-left:16px"> <textarea name="Describe" id="Describe" cols="196" rows="4" witdh=25% class="common"></textarea></td>
            </tr>
        </TABLE>
        <Table style="display:none">
            <tr>
                <td><Input class=cssButton name="addClick"value="添  加" type=button onclick="saveClick()"></td>
                <td><Input class=cssButton value="返  回" type=button onclick="top.close()"></td>
            </tr>
        </TABLE>
    </Div>
    <a href="javascript:void(0);" name="addClick" class="button" onClick="saveClick();">添    加</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    <%
    //隐藏表单，保存信息用
    %>
    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="WhoNo" name="WhoNo"><!--出险人代码-->
    <Input type=hidden id="StartPhase" name="StartPhase"><!--提起阶段-->

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>
