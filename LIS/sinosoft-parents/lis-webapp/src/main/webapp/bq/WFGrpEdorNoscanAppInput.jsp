<html>
<%
//�������ƣ�WFEdorNoscanAppInput.jsp
//�����ܣ���ȫ������-�ŵ���ȫ��ɨ������
//�������ڣ�2005-08-15 15:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>

<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  
  <SCRIPT src="WFGrpEdorNoscanApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFGrpEdorNoscanAppInit.jsp"%>

  <title>�ŵ���ȫ��ɨ������</title>

</head>

<body  onload="initForm();" >
  <form action="./WFGrpEdorNoscanAppSave.jsp" method=post name=fm id=fm target="fraSubmit">

    <table class= common border=0 width=100%>
        <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
    		</td>
            <td class= titleImg>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
        <table  class= common >
            <TR  class= common>
                <td class=title5>��ȫ�����</td>
                <td class= input5><Input class="wid" class="common" name=EdorAcceptNo_ser id=EdorAcceptNo_ser></td>
                <TD class= title5>��������</TD>
                <td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('gedornotype',[this,OtherNoName],[0,1])" onMouseDown="showCodeList('gedornotype',[this,OtherNoName],[0,1])" onkeyup="showCodeListKey('gedornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName id=OtherNoName readonly></td></TR>
                <TR  class= common>
                <TD class= title5>���屣����</TD>
                <td class= input5><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
                 <td class=title5>������</td>
                <td class=input5><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>
            </TR>
            <TR  class= common>
               
                <td class=title5>���뷽ʽ</td>
                <td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" onMouseDown="showCodeList('edorapptype',[this,AppTypeName],[0,1])" onkeyup="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName id=AppTypeName readonly></td>
                <TD class= title5>¼������</TD>
                <TD class= input5>
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR  class= common>
                <TD class=title5>�������</TD>
                <TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom_ser  id=ManageCom_ser ondblclick="showCodeList('station',[this,ManageComName],[0,1])" onMouseDown="showCodeList('station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1])" readonly=true><input class=codename name=ManageComName id=ManageComName readonly></TD>
                <TD class= title5>  </TD>
                <TD class= title5>  </TD>
            </TR>
        </table>
    </div>
    </Div>
        <!--<INPUT VALUE=" �� ѯ " class = cssButton TYPE=button onclick="easyQueryClickSelf();">
        <INPUT class=cssButton id="riskbutton" VALUE=" �� �� " TYPE=button onClick="applyMission();">-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClickSelf();">��    ѯ</a>
        <a href="javascript:void(0);" id="riskbutton" class="button" onClick="applyMission();">��    ��</a>
    

    <table>
        <tr>
            <td class=common>
                <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divSelfGrid);">
            </td>
            <td class= titleImg>
                 �ҵ�����
            </td>
        </tr>
    </table>
    <div id="divSelfGrid" style="display:''" align="center">
        <table class="common">
            <tr class="common">
                <td><span id="spanSelfGrid"></span></td>
            </tr>
        </table>
        <!--<input type="button" class="cssButton" value="��  ҳ" onclick="turnPage2.firstPage()">
        <input type="button" class="cssButton" value="��һҳ" onclick="turnPage2.previousPage()">
        <input type="button" class="cssButton" value="��һҳ" onclick="turnPage2.nextPage()">
        <input type="button" class="cssButton" value="β  ҳ" onclick="turnPage2.lastPage()">-->
    </div>

    <!--<INPUT class=cssButton TYPE=button VALUE=" ��ȫ���� " onclick="GoToBusiDeal()">
    <INPUT class=cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">��ȫ����</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a>

        <!-- �����򲿷� -->
            <input type="hidden" name="EdorAcceptNo">
            <input type="hidden" name="MissionID">
            <input type="hidden" name="SubMissionID">
            <input type="hidden" name="ActivityID">

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	<br><br><br><br>
</body>
</html>
