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
	var QactivityID = "<%=QactivityID%>"; //��¼��½����
</script>
<head>
<TITLE>��������</TITLE>
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
                    �����
                </TD>
                <TD class=input5>
                    <Input readonly=True class=code name=ActivityIDQ id=ActivityIDQ value="">
                </TD>
                <TD class=title5>�����</TD>
                <TD class=input5><input class=common name=ActivityNameQ id=ActivityNameQ readonly=True></TD>
            </TR>
        </table>

        <!--<INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="queryClick()">-->
<a href="javascript:void(0);" class="button"onclick="queryClick();">��  ��</a>
<br><br>
        <table class=common>
            <TR class=common>
                <TD text-align: left colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
        <!--<INPUT VALUE="��ҳ" class="cssButton" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="βҳ" class="cssButton" TYPE=button onClick="turnPage.lastPage();">-->
    </div> </div>


    <br> <br>
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
                    �����
                </TD>
                <TD class=input5>
                    <Input class="common wid" readonly=True name=ActivityID id=ActivityID value="" verify="�����|notnull"  elementtype=nacessary>
                </TD>
                <TD class=title5>�����</TD>
                <TD class=input5><input class="common wid" name=ActivityName id=ActivityName readonly=True verify="�����|notnull" elementtype=nacessary></TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    Դ�ֶ�
                </TD>
                <TD class=input5>
                    <Input class=codeno name=SourFieldName id=SourFieldName
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeListKey('sourfieldname',[this,SourFieldCName],[0,1],null,null,null,1,'300');"
                     onDblClick="return showCodeList('sourfieldname',[this,SourFieldCName],[0,1],null,null,null,1,'300');" 
                     onKeyUp="return showCodeListKey('sourfieldname',[this,SourFieldCName],[0,1],null,null,null,1,'300');"
                      verify="Դ�ֶ�|notnull"><input class=codename name=SourFieldCName verify="Դ�ֶ�|notnull" elementtype=nacessary>
                </TD>
                <TD class=title5>
                    Ŀ���ֶ�
                </TD>
                <TD class=input5>
                    <Input class=codeno name=DestFieldName  id=DestFieldName 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="return showCodeListKey('destfieldname',[this,DestFieldCName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('destfieldname',[this,DestFieldCName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('destfieldname',[this,DestFieldCName],[0,1],null,null,null,1,'300');" 
                     verify="Ŀ���ֶ�|notnull" ><input class=codename name=DestFieldCName readonly=TRue  elementtype=nacessary>
                </TD>
            </TR>
        </table>
    </div>
     </div>
    <br>
    <!--<INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="SaveClick()">
    <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="ModifyClick()">
    <INPUT align=right VALUE="ɾ  ��" TYPE=button class="cssButton" onClick="DeleteClick()">
    <INPUT align=right VALUE="��  ��" TYPE=button class="cssButton" onClick="return top.close();">-->
    <a href="javascript:void(0);" class="button"onClick="SaveClick()">��   ��</a>
    <a href="javascript:void(0);" class="button"onClick="ModifyClick()">��   ��</a>
    <a href="javascript:void(0);" class="button"onClick="DeleteClick()">ɾ   ��</a>
    <a href="javascript:void(0);" class="button"onClick="return top.close();">��   ��</a>
    <INPUT type="hidden" value="" name="hiddenActivityID" id=hiddenActivityID>
    <INPUT type="hidden" value="" name="hiddenFieldOrder" id=hiddenFieldOrder>
    <br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
