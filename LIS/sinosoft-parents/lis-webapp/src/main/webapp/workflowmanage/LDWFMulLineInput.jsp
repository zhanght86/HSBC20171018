<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<html>
<%/**
 * Created by IntelliJ IDEA.
 * User: jiyt
 * Date: 2012-6-26
 * Time: 10:48:15
 * To change this template use File | Settings | File Templates.
 */
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>
 
<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./LDWFMulLine.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
     <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LDWFMulLineInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
  <form action="./LDWFMulLineSave.jsp" method=post name=fm  id=fm target="fraSubmit">
      <table><!--页面公共部分-->
        <tr>
            <td class=common>
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFieldInfo);">
            </td>
            <td class=titleImg>字段信息</td>
        </tr>
     </table>
      <div id="divFieldInfo">
      <div class="maxbox1">
        <table class=common>
            <TR class=common>          
                <TD class=title5>功能名称</TD>
                <TD class=input5>
                    <Input class=codeno name=FunctionID  id=FunctionID  
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="功能名称|notnull"
                     onclick="showCodeList('FunctionID',[this,FunctionIDName],[0,1]);" 
                      onDblClick="return showCodeList('FunctionID',[this,FunctionIDName],[0,1],null,null,null,'1');"
                      onKeyUp=" return showCodeListKey('FunctionID',[this,FunctionIDName],[0,1],null,null,null,'1');"><input class=codename name=FunctionIDName readonly=true elementtype=nacessary>
                </TD>          
                <TD class=title5>业务表</TD>
                <TD class=input5>
                    <Input class=codeno name=BusiNessTable id=BusiNessTable
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('BusiNessTable',[this,BusiNessTableName],[0,1]);" 
                      onDblClick="return showCodeList('BusiNessTable',[this,BusiNessTableName],[0,1],null,fm.FunctionID.value,'FunctionID','1');"
                      onKeyUp=" return showCodeListKey('BusiNessTable',[this,BusiNessTableName],[0,1],null,fm.FunctionID.value,'FunctionID','1');"><input class=codename name=BusiNessTableName elementtype=nacessary>
                </TD>
                  </TR>
            <TR class=common>          
                <TD class=title5>字段名称</TD>
                <TD class=input5>
                 
                    <Input class=codeno name=FieldCode  id=FieldCode 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeList('FieldCode',[this,FieldCodeName],[0,1]);" 
                    onDblClick="checkBusiNessTable();return showCodeList('FieldCode',[this,FieldCodeName],[0,1],null,fm.BusiNessTable.value,'BusiNessTable','1');" 
                    onKeyUp="checkBusiNessTable();return showCodeListKey('FieldCode',[this,FieldCodeName],[0,1],null,fm.BusiNessTable.value,'BusiNessTable','1');"><input class=codename name=FieldCodeName  elementtype=nacessary>
                </TD>
          
                <TD class=title5>列宽</TD>
                <TD class=input5>
                    <input class="common wid"name=ColWidth id=ColWidth value="" >
                </TD>
                  </TR>
            <TR class=common>
                <TD class=title5>列序号</TD>
                <TD class=input5>
                    <input class="common wid"  name=ColSerialNo id=ColSerialNo value="" >
                </TD>
                <TD class=title5>是否显示</TD>
                <TD class=input5> 
                     <Input class=codeno name=IsShow id=IsShow CodeData="0|^0|否 ^1|是"
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                      onclick="showCodeListEx('IsShow',[this,IsShowName],[0,1])"
                      onDblClick="showCodeListEx('IsShow',[this,IsShowName],[0,1])"
                       onKeyUp="return showCodeListKeyEx('IsShow',[this,IsShowName],[0,1])"><input class=codename name=IsShowName readonly=true>
                </TD>
            </TR>
        </table>
        </div>
  </div>
      <!--  <INPUT align=right VALUE="查  看" TYPE=button class="cssButton" onClick="queryClick()">
        <INPUT align=right VALUE="保  存" TYPE=button class="cssButton" onClick="saveClick()">
        <INPUT align=right VALUE="修  改" TYPE=button class="cssButton" onClick="updateClick()">
        <INPUT align=right VALUE="删  除" TYPE=button class="cssButton" onClick="deleteClick()">-->
        <a href="javascript:void(0);" class="button"onClick="queryClick()">查   看</a>
        <a href="javascript:void(0);" class="button"onClick="saveClick()">保   存</a>
        <a href="javascript:void(0);" class="button"onClick="updateClick()">修   改</a>
        <a href="javascript:void(0);" class="button"onClick="deleteClick()">删   除</a>
      <br>  <br> 
        <table class=common>
            <TR class=common>
                <TD>
   				<span id="spanToBusiTableGrid"></span>
                </TD>
            </TR>
        </table>
       <!-- <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->
  
    <br><br><br><br>
  </form>
  <span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>