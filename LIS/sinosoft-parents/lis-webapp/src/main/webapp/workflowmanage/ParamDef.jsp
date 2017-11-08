<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<html>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    String QactivityID=request.getParameter("ActivityID");
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script>	
	var QactivityID = "<%=QactivityID%>"; //记录登陆机构
</script>
<head>
<TITLE>参数定义</TITLE>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./ParamDef.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="ParamDefInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
<form action="./ParamDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table><!--页面公共部分-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class=titleImg>
                查询条件
            </TD>
        </TR>
    </table>
    <div id="divInvAssBuildInfo">
     <div class="maxbox1" >
        <table class=common>
            <TR class=common>
                <TD class=title5>
                    活动代码
                </TD>
                <TD class=input5>
                    <Input readonly=True class=code name=ActivityIDQ id=ActivityIDQ value="">
                </TD>
                <TD class=title5>活动名称</TD>
                <TD class=input5><input class=common name=ActivityNameQ id=ActivityNameQ readonly=True></TD>
            </TR>
        </table>

        <!--<INPUT align=right VALUE="查  看" TYPE=button class="cssButton" onClick="queryClick()">-->
<a href="javascript:void(0);" class="button"onclick="queryClick();">查  看</a>
<br><br>
        <table class=common>
            <TR class=common>
                <TD text-align: left colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
        <!--<INPUT VALUE="首页" class="cssButton" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页" class="cssButton" TYPE=button onClick="turnPage.lastPage();">-->
    </div> </div>


    <br> <br>
    <table><!--页面公共部分-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divdetailinfo);">
            </TD>
            <TD class=titleImg>
                信息录入
            </TD>
        </TR>
    </table>
    <div id="divdetailinfo">
     <div class="maxbox1" >
        <table class="common">
            <TR class=common>
                <TD class=title5>
                    活动代码
                </TD>
                <TD class=input5>
                    <Input class="common wid" readonly=True name=ActivityID id=ActivityID value="" verify="活动代码|notnull"  elementtype=nacessary>
                </TD>
                <TD class=title5>活动名称</TD>
                <TD class=input5><input class="common wid" name=ActivityName id=ActivityName readonly=True verify="活动名称|notnull" elementtype=nacessary></TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    源字段
                </TD>
                <TD class=input5>
                    <Input class=codeno name=SourFieldName id=SourFieldName
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeListKey('sourfieldname',[this,SourFieldCName],[0,1],null,null,null,1,'300');"
                     onDblClick="return showCodeList('sourfieldname',[this,SourFieldCName],[0,1],null,null,null,1,'300');" 
                     onKeyUp="return showCodeListKey('sourfieldname',[this,SourFieldCName],[0,1],null,null,null,1,'300');"
                      verify="源字段|notnull"><input class=codename name=SourFieldCName verify="源字段|notnull" elementtype=nacessary>
                </TD>
                <TD class=title5>
                    目标字段
                </TD>
                <TD class=input5>
                    <Input class=codeno name=DestFieldName  id=DestFieldName 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeListKey('destfieldname',[this,DestFieldCName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('destfieldname',[this,DestFieldCName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('destfieldname',[this,DestFieldCName],[0,1],null,null,null,1,'300');" 
                     verify="目标字段|notnull" ><input class=codename name=DestFieldCName readonly=TRue  elementtype=nacessary>
                </TD>
            </TR>
        </table>
    </div>
     </div>
    <br>
    <!--<INPUT align=right VALUE="保  存" TYPE=button class="cssButton" onClick="SaveClick()">
    <INPUT align=right VALUE="修  改" TYPE=button class="cssButton" onClick="ModifyClick()">
    <INPUT align=right VALUE="删  除" TYPE=button class="cssButton" onClick="DeleteClick()">
    <INPUT align=right VALUE="返  回" TYPE=button class="cssButton" onClick="return top.close();">-->
    <a href="javascript:void(0);" class="button"onClick="SaveClick()">保   存</a>
    <a href="javascript:void(0);" class="button"onClick="ModifyClick()">修   改</a>
    <a href="javascript:void(0);" class="button"onClick="DeleteClick()">删   除</a>
    <a href="javascript:void(0);" class="button"onClick="return top.close();">返   回</a>
    <INPUT type="hidden" value="" name="hiddenActivityID" id=hiddenActivityID>
    <INPUT type="hidden" value="" name="hiddenFieldOrder" id=hiddenFieldOrder>
    <br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
