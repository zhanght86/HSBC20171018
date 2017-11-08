
<%
//程序名称：UserPopedomInput.jsp
//程序功能：用户保全权限定制
//创建日期：2006-01-20 14:30:22
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    //添加页面控件的初始化。
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>


<html>
<script>
    var operator = "<%=tGI.Operator%>";   //记录操作员
    var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head >
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <title>用户保全权限定制</title>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="UserPopedom.js"></SCRIPT>
  <%@include file="UserPopedomInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form name=fm id=fm method=post target="fraSubmit">
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUserCode);">
            </td>
            <td class= titleImg>
                 用户信息查询
            </td>
        </tr>
    </table>
    
    <Div  id= "divUserCode" style= "display: ''">
    <div class="maxbox1">
        <table  class= common >
            <TR  class= common>
                <TD class= title> 用户代码 </TD>
                <td class= input><Input class="wid" class="common" onkeydown="QueryOnKeyDown();" name=UserCode id=UserCode verify="用户代码|NotNull" ></td>
                <TD class= title> 
                <td class= input></td>
                <TD class= title>  </TD>
                <td class= input></td>
            </TR>
        </table>

    </div>
</div>
<!--<INPUT class= cssButton TYPE=button VALUE="用户查询" onclick="queryUser();">-->
<a href="javascript:void(0);" class="button" onClick="queryUser();">用户查询</a>  </TD>
    

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUserInfo);">
            </td>
            <td class= titleImg>
                 用户详细信息
            </td>
        </tr>
    </table>
    <Div  id= "divUserInfo" style= "display: ''">
<div class="maxbox1">
        <table  class= common >
            <TR  class= common>
                <TD class= title> 用户代码 </TD>
                <td class= input><Input class="wid"  CLASS="readonly" readonly  name=UserCode_read id=UserCode_read></td>
                <TD class= title> 用户姓名 </TD>
                <td class= input><Input class="wid"  CLASS="readonly" readonly  name=UserName id=UserName></td>
                <TD class= title> 管理机构 </TD>
                <td class= input><Input class="wid"  CLASS="readonly" readonly  name=ComName id=ComName></td>
            </TR>
        </table>
        </div>
    </div>
    

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPopedom);">
            </td>
            <td class= titleImg>保全权限指定</td>
        </tr>
    </table>

            <!-- XinYQ modified 20 2006-12-23 : 加入团体保全权限 -->
            <div id="divPopedom" style="display:''">
            <div class="maxbox1">
                <table class="common">
                    <tr class="common">
                        <td class="title">个险权限级别</td>
                        <td class="input"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="EdorPopedom" id="EdorPopedom"  verify="个单权限级别|Code:EdorPopedom" ondblclick="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onMouseDown="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onkeyup="showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1])"><input type="text" class="codename" name="EdorPopedomName" id="EdorPopedomName" readonly></td>
                        <td class="title">团体权限级别</td>
                        <td class="input"><input  style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="GEdorPopedom" id="GEdorPopedom" verify="团单权限级别|Code:GEdorPopedom" ondblclick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onMouseDown="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onkeyup="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></td>
                        <td class="title"></td>
                        <td class="input"></td>
                    </tr>
                </table>
                </div>
            </div>
    
    <!--<INPUT class= cssButton TYPE=button VALUE=" 确 定 " onclick="saveUserPopedom()">-->
    <a href="javascript:void(0);" class="button" onClick="saveUserPopedom();">确    定</a>
  </form>
  <span id="spanCode" style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
