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
    <table><!--ҳ�湫������-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class=titleImg>
                ��ѯ����
            </TD>
        </TR>
    </table>
    <div id="divInvAssBuildInfo">
    <div class="maxbox1" >
        <table class=common>
            <TR class=common>
                <TD class=title5>
                    ҵ������
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
                    ���
                </TD>
                <TD class=input5>
                    <Input class=codeno name=StartActivityIDQ  id=StartActivityIDQ 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclickQ(this,StartActivityNameQ);"
                    onDblClick="return ondblclickQ(this,StartActivityNameQ);" 
                    onKeyUp="return ondblclickQ(this,StartActivityNameQ);"><input class=codename name=StartActivityNameQ readonly=TRue>
                </TD>
                <TD class=title5>
                    �յ�
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

       <!-- <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="queryClick()">-->
       <a href="javascript:void(0);" class="button"onClick="queryClick()">��   ѯ</a>
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
        <INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>
    <br>
    <table><!--ҳ�湫������-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divdetailinfo);">
            </TD>
            <TD class=titleImg>
                ��Ϣ¼��
            </TD>
        </TR>
    </table>
    <div id="divdetailinfo">
      <div class="maxbox1" >
        <table class="common">
            <TR class=common>
                <TD class=title5>
                    ҵ������
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BusiType id=BusiType
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'300');" 
                    verify="ҵ������|notnull" ><input class=codename name=BusiTypeName readonly=TRue verify="ҵ������|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    ���
                </TD>
                <TD class=input5>
                    <Input class=codeno name=StartActivityID  id=StartActivityID 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclick2(this,StartActivityName);"
                    onDblClick="return ondblclick2(this,StartActivityName);"
                     onKeyUp="return ondblclick2(this,StartActivityName);" 
                     verify="���|notnull"><input class=codename name=StartActivityName id=StartActivityName readonly=TRue verify="���|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    ת����������
                </TD>
                <TD class=input5>
                    <Input class=codeno name=TransitionCondT  id=TransitionCondT
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('transitioncondt',[this,TransitionCondTName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('transitioncondt',[this,TransitionCondTName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('transitioncondt',[this,TransitionCondTName],[0,1],null,null,null,1,'300');" ><input class=codename name=TransitionCondTName readonly=TRue ><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    �յ�
                </TD>
                <TD class=input5>
                    <Input class=codeno name=EndActivityID   id=EndActivityID 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return ondblclick2(this,EndActivityName);" 
                    ondblclick="return ondblclick2(this,EndActivityName);" 
                    onKeyUp="return ondblclick2(this,EndActivityName);" verify="�յ�|notnull" ><input class=codename name=EndActivityName readonly=TRue verify="�յ�|notnull" elementtype=nacessary>
                </TD>
            </TR>

        </table>
        </div>
        <Table class=common>
            <TR class=common>
                <TD class=titleImg>
                    ת������
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
                    ת����������
                </TD>
            </TR>
            <TR class=common>
                <TD class=input><textarea name="CondDesc" cols="176%" rows="4" width=100% class="common"></textarea></TD>
            </TR>
        </table>
    </div>

    <!--<INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="SaveClick()">
    <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="ModifyClick()">
    <INPUT align=right VALUE="ɾ  ��" TYPE=button class="cssButton" onClick="DeleteClick()">
    <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="return top.close();">-->
    <br>
    <a href="javascript:void(0);" class="button"onClick="SaveClick()">��  ��</a>
    <a href="javascript:void(0);" class="button"onClick="ModifyClick()">��  ��</a>
    <a href="javascript:void(0);" class="button"onClick="DeleteClick()">ɾ  ��</a>
    <a href="javascript:void(0);" class="button"onClick="return top.close();">��  ��</a>
    
    <%--<INPUT align=right VALUE="��ѯ" TYPE=button class="cssButton" onclick="return testQuery();">--%>
    <br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
