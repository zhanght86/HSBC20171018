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
    <SCRIPT src="./ConditionDef.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="ConditionDefInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
<form action="./ConditionDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
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
                    业务类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BusiTypeQ  id=BusiTypeQ 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                      onclick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');"><input class=codename name=BusiTypeNameQ readonly=TRue>
                </TD>
                <TD >
                </TD>
                <TD>
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    起点活动
                </TD>
                <TD class=input5>
                    <Input class=codeno name=StartActivityIDQ  id=StartActivityIDQ 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclickQ(this,StartActivityNameQ);"
                    onDblClick="return ondblclickQ(this,StartActivityNameQ);" 
                    onKeyUp="return ondblclickQ(this,StartActivityNameQ);"><input class=codename name=StartActivityNameQ readonly=TRue>
                </TD>
                <TD class=title5>
                    终点活动
                </TD>
                <TD class=input5>
                    <Input class=codeno name=EndActivityIDQ  id=EndActivityIDQ
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclickQ(this,EndActivityNameQ);"
                    onDblClick="return ondblclickQ(this,EndActivityNameQ);"
                     onKeyUp="return ondblclickQ(this,EndActivityNameQ);"><input class=codename name=EndActivityNameQ readonly=TRue>
                </TD>
            </TR>
        </table>
        </div>

       <!-- <INPUT align=right VALUE="查  看" TYPE=button class="cssButton" onClick="queryClick()">-->
       <a href="javascript:void(0);" class="button"onClick="queryClick()">查   询</a>
<br><br>
        <table class=common>
            <TR class=common>
                <TD text-align: left colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
         <div align="center">
        <INPUT VALUE="首  页" class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾  页" class="cssButton93" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>
    <br>
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
                    业务类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BusiType id=BusiType
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'300');" 
                    verify="业务类型|notnull" ><input class=codename name=BusiTypeName readonly=TRue verify="业务类型|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    起点活动
                </TD>
                <TD class=input5>
                    <Input class=codeno name=StartActivityID  id=StartActivityID 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclick2(this,StartActivityName);"
                    onDblClick="return ondblclick2(this,StartActivityName);"
                     onKeyUp="return ondblclick2(this,StartActivityName);" 
                     verify="起点活动|notnull"><input class=codename name=StartActivityName id=StartActivityName readonly=TRue verify="起点活动|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    转移条件类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=TransitionCondT  id=TransitionCondT
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('transitioncondt',[this,TransitionCondTName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('transitioncondt',[this,TransitionCondTName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('transitioncondt',[this,TransitionCondTName],[0,1],null,null,null,1,'300');" ><input class=codename name=TransitionCondTName readonly=TRue ><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    终点活动
                </TD>
                <TD class=input5>
                    <Input class=codeno name=EndActivityID   id=EndActivityID 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclick2(this,EndActivityName);" 
                    ondblclick="return ondblclick2(this,EndActivityName);" 
                    onKeyUp="return ondblclick2(this,EndActivityName);" verify="终点活动|notnull" ><input class=codename name=EndActivityName readonly=TRue verify="终点活动|notnull" elementtype=nacessary>
                </TD>
            </TR>

        </table>
        </div>
        <Table class=common>
            <TR class=common>
                <TD class=titleImg>
                    转移条件
                </TD>
            </TR>
            <TR class=common>
                <TD class=input>
                    <textarea name="TransitionCond" cols="176%" rows="4" width=100% class="common"></textarea></TD>
            </TR>
        </table>
        <Table class=common>
            <TR class=common>
                <TD class=titleImg>
                    转移条件描述
                </TD>
            </TR>
            <TR class=common>
                <TD class=input><textarea name="CondDesc" cols="176%" rows="4" width=100% class="common"></textarea></TD>
            </TR>
        </table>
    </div>

    <!--<INPUT align=right VALUE="保  存" TYPE=button class="cssButton" onClick="SaveClick()">
    <INPUT align=right VALUE="修  改" TYPE=button class="cssButton" onClick="ModifyClick()">
    <INPUT align=right VALUE="删  除" TYPE=button class="cssButton" onClick="DeleteClick()">
    <INPUT align=right VALUE="返  回" TYPE=button class="cssButton" onClick="return top.close();">-->
    <br>
    <a href="javascript:void(0);" class="button"onClick="SaveClick()">保  存</a>
    <a href="javascript:void(0);" class="button"onClick="ModifyClick()">修  改</a>
    <a href="javascript:void(0);" class="button"onClick="DeleteClick()">删  除</a>
    <a href="javascript:void(0);" class="button"onClick="return top.close();">返  回</a>
    
    <%--<INPUT align=right VALUE="查询" TYPE=button class="cssButton" onclick="return testQuery();">--%>
    <br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
