<%
//程序名称：UsrCommonQueryInput.jsp
//程序功能：系统用户信息查询
//创建日期：2005-11-30(为工作绩效统计ui\f1print\WorkAchieveStatInput.jsp中提供用户的查询条件所建)
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    String tManageCom = "";
    String tUserCode = "";
    String tQueryFlag = "";    //用于区分是何处调用
    try
    {
        tManageCom = request.getParameter( "ManageCom" );
        //loggerDebug("UsrCommonQueryInput","---tManageCom:"+tManageCom);
        tQueryFlag = request.getParameter("queryflag");
        //loggerDebug("UsrCommonQueryInput","---tQueryFlag:"+tQueryFlag);
        tUserCode = request.getParameter( "UserCode" );
    }
    catch( Exception e1 )
    {
        //loggerDebug("UsrCommonQueryInput","---Exception:"+e1);
    }
%>

<html>
<script>
    var managecom = "<%=tManageCom%>";
    var queryFlag = "<%=tQueryFlag%>";
    var usercode = "<%=tUserCode%>";
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./UsrCommonQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./UsrCommonQueryInit.jsp"%>
  <title>用户查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <!--用户查询条件 -->
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUsr);">
            </td>
            <td class= titleImg>
                用户查询条件
            </td>
        </tr>
    </table>
  <Div  id= "divUsr" style= "display: ''" class="maxbox">
  <table  class= common>
      <TR  class= common>
          <TD  class= title>   管理机构  </TD>
        <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="ManageCom" id="ManageCom" verify="管理机构|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName],[0,1])" onMouseDown="showCodeList('Station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName],[0,1])"><input type="text" class="codename" name="ManageComName" id="ManageComName" readonly></td>
        <TD class= title>   用户代码  </TD>
        <TD  class= input>  <Input class="wid" class=common  name=UsrCode id=UsrCode > </TD>
        <TD class= title>   用户姓名  </TD>
        <TD  class= input>  <Input class="wid" class=common  name=UsrName id=UsrName > </TD>
      </TR>
        <!-- XinYQ modified 20 2006-12-23 : 加入团体保全权限 -->
        <tr class="common">
            <td class="title">个单保全权限级别</td>
            <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="EdorPopedom" id="EdorPopedom" verify="个单权限级别|Code:EdorPopedom" ondblclick="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onMouseDown="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onkeyup="showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1])"><input type="text" class="codename" name="EdorPopedomName" id="EdorPopedomName" readonly></td>
            <td class="title">团单保全权限级别</td>
            <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="GEdorPopedom" id="GEdorPopedom" verify="团单权限级别|Code:GEdorPopedom" ondblclick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onMouseDown="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onkeyup="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></td>
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>
        </tr>
  </table>
 <!-- <table>
    <tr>
        <td><INPUT class=cssButton VALUE=" 查 询 " TYPE=button onclick="easyQueryClick()"></td>
        
    </tr>
  </table>-->
  </Div>
<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
    <table>
        <tr>
        <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUsrGrid)">
            </td>
            <td class= titleImg>
                 用户信息列表
            </td>
        </tr>
    </table>
    <Div  id= "divUsrGrid" style= "display: ''">
      <table  class= common>
            <tr  class= common>
                <td><span id="spanUsrGrid"></span></td>
            </tr>
      </table>
      <!--<div align="left"><INPUT class=cssButton VALUE=" 返 回 " TYPE=button onclick="returnParent()"></div>-->
            <div align="center" style= "display:''">
                <!--<input type="button" class="cssButton" value="首  页" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPage.lastPage()">-->
            </div>
    </div><a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	  <br/><br/><br/><br/>
  </form>
</body>
</html>
