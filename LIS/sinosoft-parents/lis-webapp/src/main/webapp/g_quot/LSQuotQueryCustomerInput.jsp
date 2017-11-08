<%
/***************************************************************
 * <p>ProName：LSQuotQueryCustomerInput.jsp</p>
 * <p>Title：查询准客户名称</p>
 * <p>Description：查询准客户名称</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getAttribute("GI");
    String tCurrentDate= PubFun.getCurrentDate();
    String tOperator = tGI.Operator;
    String tManageCom = tGI.ManageCom;
    String tMulLineNo = request.getParameter("SelNo");
    String tMark = request.getParameter("Mark");
    String tPreCustomerNo = request.getParameter("PreCustomerNo");
    String tPreCustomerName = null;
    try{
        tPreCustomerName = new String(request.getParameter("PreCustomerName").getBytes("ISO8859_1"),"GBK");        
    }catch(Exception e){}
    String tQuotNo = request.getParameter("QuotNo");
%>
<script>
    var tOperator = "<%=tOperator%>";//当前登录人
    var tManageCom = "<%=tManageCom%>";//当前登录机构
    var tMulLineNo = "<%=tMulLineNo%>";//MulLine序号
    var tMark = "<%=tMark%>";//传入方式标记
    var tPreCustomerNo1 = "<%=tPreCustomerNo%>";//询价一般信息录入界面传入客户号
    var tPreCustomerName = "<%=tPreCustomerName%>";
    var tQuotNo = "<%=tQuotNo%>";//询价号
    
</script>
<html>
<head>
    <title>准客户名称查询</title>
    <script src="../common/javascript/Common.js"></script>
    <script src="../common/cvar/CCodeOperate.js"></script>
    <script src="../common/javascript/MulLine.js"></script>
    <script src="../common/javascript/EasyQuery.js"></script>
    <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script src="../common/javascript/VerifyInput.js"></script>
    <script src="../common/laydate/laydate.js"></script>
    <link href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LSQuotQueryCustomerInput.js"></script>
    <%@include file="./LSQuotQueryCustomerInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 查询条件 -->
<form name=fm id=fm method=post action="" target=fraSubmit>
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
            </td>
            <td class=titleImg>准客户名称查询条件</td>
        </tr>
    </table>
    <div id="divInfo1" class=maxbox1 style="display: ''">
        <table class=common>
            <tr class=common>
                <td class=title>准客户号码</td>
                <td class=input><input class="wid common" name=PreCustomerNo id=PreCustomerNo></td>
                <td class=title>准客户名称</td>
                <td class=input><input class="wid common" name=PreCustomerName id=PreCustomerName></td>
                <td class=title></td>
                <td class=input></td>
            </tr>
        </table>
        <input class=cssButton type=button value="查  询" onclick="queryCustomer();">
        <input class=cssButton type=button value="选  择" onclick="returnCustomer();">
        <input class=cssButton type=button value="关  闭" onclick="top.close();">
    </div>
        
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
            </td>
            <td class=titleImg>准客户名称信息列表</td>
        </tr>
    </table>
    <div id="divResult1"  style="display: ''">
        <table class=common>
            <tr class=common>
                <td text-align: left colSpan=1>
                    <span id="spanRelaCustGrid"></span>
                </td>
            </tr>
        </table>
        <center>        
            <input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
            <input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
            <input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
            <input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
        </center>
    </div>
    <input type=hidden name=Operate id=Operate>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
    <div style="display: none">
        <input type=hidden name=Operate id=Operate>
    </div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
