<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<html>
<%/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./MissionCtrl.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="MissionCtrlInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
<form action="./MissionCtrlSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table><!--ҳ�湫������-->
        <TR>
            <TD class=common>
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
                    <Input class=codeno name=BusiTypeQ id=BusiTypeQ 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1]);" 
                    onDblClick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');"><input class=codename name=BusiTypeNameQ readonly=TRue>
                </TD>
                <TD class=title5>
                   �����
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ActivityIDQ  id=ActivityIDQ 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('queryactivityid',[this,ActivityNameQ],[0,1]);" 
                    onDblClick="return showCodeList('queryactivityid',[this,ActivityNameQ],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('queryactivityid',[this,ActivityNameQ],[0,1],null,null,null,1,'300');"><input class=codename name=ActivityNameQ readonly=TRue>
                </TD>
            </TR>
                <TD class=title5>
                    ҵ���
                </TD>
                <TD class=input5>
                    <Input class="common wid " name=BusiNoQ >
                </TD>
                <TD class=title5>
                   ִ����
                </TD>
                <TD class=input5>
                    <Input class=codeno name=UserCodeQ id=UserCodeQ 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeList('queryuser',[this,UserNameQ],[0,1]);" 
                    onDblClick="return showCodeList('queryuser',[this,UserNameQ],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('queryuser',[this,UserNameQ],[0,1],null,null,null,1,'300');"><input class=codename name=UserNameQ readonly=TRue>
                </TD>
            </TR>
        </table>
        </div>

       <!-- <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="queryClick()">-->
        <a href="javascript:void(0);" class="button"onClick="queryClick()">��   ��</a>
<br><br>
        <table class=common>
            <TR class=common>
                <TD text-align: left colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
       <!-- <INPUT VALUE="��ҳ"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="βҳ"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->
    </div>


    <br> <br>
    <table><!--ҳ�湫������-->
        <TR>
            <TD class=common>
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
                    ��ִ����
                </TD>
                <TD class=input5>
                    <Input class=codeno name=UserCode  id=UserCode 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeList('queryuser',[this,UserName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('queryuser',[this,UserName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('queryuser',[this,UserName],[0,1],null,null,null,1,'300');"
                      verify="��ִ����|notnull" ><input class=codename name=UserName  id=UserName readonly=TRue verify="��ִ����|notnull" elementtype=nacessary>
                </TD>
                <TD class=input5 >
                </TD>
                <TD class=input5>
                </TD>
            </TR>
        </table>
    </div>
       </div>

    <INPUT align=right VALUE="ת  ��" TYPE=button class="cssButton" onClick="ModifyClick()">
     <!--<a href="javascript:void(0);" class="button"onClick="ModifyClick()">ת   ��</a>-->
    <INPUT type="hidden" value="" name="hiddenActivityID" >
    <br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
