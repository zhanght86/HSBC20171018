<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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


<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./BaseDataManage.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="BaseDataManageInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
<form action="./BaseDataManageSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table><!--ҳ�湫������-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class=titleImg>
                ��ά������
            </TD>
        </TR>
    </table>
    <div id="divInvAssBuildInfo">
     <div class="maxbox1" >
        <table class=common>
            <TR class=common>
                <TD class=title5>
                    ��������
                </TD>
                <TD class=input5>
                    <Input class=codeno name=CodeTypeQ  id=CodeTypeQ 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('querycodetype',[this,CodeTypeNameQ],[0,1],null,null,null,1,'300');"  
                    onDblClick="return showCodeList('querycodetype',[this,CodeTypeNameQ],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('querycodetype',[this,CodeTypeNameQ],[0,1],null,null,null,1,'300');">
                    <input class=codename name=CodeTypeNameQ readonly=true>
                </TD>
                <TD class=input5 ></TD>
                <TD class=input5 ></TD>
            </TR>
        </table>
        </div>
       <!-- <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="queryClick()">-->
       <br>
       <a href="javascript:void(0);" class="button"onClick="queryClick()">��  ��</a>
       <br><br>

        <table class=common>
            <TR class=common>
                <TD text-align: left colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
        
        <!--<INPUT VALUE="��ҳ"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="βҳ"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->
    </div>


    <br> <br>
    <table><!--ҳ�湫������-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divdetailinfo);">
            </TD>
            <TD class=titleImg>
                ������Ϣ¼��
            </TD>
        </TR>
    </table>
    <div id="divdetailinfo">
     <div class="maxbox1" >
        <table class="common">
            <TR class="common">
                <TD class=title5>
                    ��������
                </TD>
                <TD class=input5>
                    <input class=code readonly name=CodeType id=CodeType value="" verify="��������|notnull" elementtype=nacessary 
                    style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
                     onclick="return showCodeList('querycodetype',[this,CodeTypeName],[0,1],null,null,null,1,'300');"  
                    onDblClick="return showCodeList('querycodetype',[this,CodeTypeName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('querycodetype',[this,CodeTypeName],[0,1],null,null,null,1,'300');">
                </TD>
                <TD class=title5>
                    ������������
                </TD>
                <TD class=input5>
                    <input class=common readonly name=CodeTypeName value="" verify="������������|notnull" elementtype=nacessary>
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    ����ֵ
                </TD>
                <TD class=input5>
                    <input class="common wid" name=Code value="" verify="����ֵ|notnull" elementtype=nacessary>
                </TD>
                <TD class=title5>
                    ��������
                </TD>
                <TD class=input5>
                    <input class=common name=CodeName value="" verify="��������|notnull" elementtype=nacessary>
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    �������
                </TD>
                <TD class=input5>
                    <input class="common wid" name=CodeAlias value="" >
                </TD>
                <!-- add by liuyuxiao 2011-05-24 �����˲˵��ڵ㣬��ʼ��ʱ����-->
                <TD class=title5>
                	<p class=title id=menuNod style="display: none">�˵��ڵ�</p>
                </TD>
                <TD class=input5>
                    <input class=codeno name=OtherSignCode value="" style="display: none" 
                    	   ondblclick="return showCodeList('querymenu',[this,OtherSignName],[0,1],null,null,null,1,'300');" 
                    	   onkeyup="return showCodeListKey('querymenu',[this,OtherSignName],[0,1],null,null,null,1,'300');" />
                	<input class=codename name=OtherSignName readonly style="display: none">
                </TD>
                <!-- end add by liuyuxiao 2011-05-24 �����˲˵��ڵ�-->
            </TR>

        </table>
        </div>

    </div>
    <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="SaveClick()">
    <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="ModifyClick()">
    <INPUT align=right VALUE="ɾ  ��" TYPE=button class="cssButton" onClick="DeleteClick()">
    <br>
    <!--<a href="javascript:void(0);" class="button"onClick="SaveClick()">��  ��</a>
    <a href="javascript:void(0);" class="button"onClick="ModifyClick()">��  ��</a>
    <a href="javascript:void(0);" class="button"onClick="DeleteClick()">ɾ  ��</a>-->
<br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
