
<%
//�������ƣ�WFEdorCancelInput.jsp
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
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

<html>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFGrpEdorCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFGrpEdorCancelInit.jsp"%>
  <title>��ȫ����</title>
</head>
<body  onload="initForm()" >
  <form action="./WFEdorCancelSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
        <tr>
        	<td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
		    	</td>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
        <table  class= common >
            <TR  class= common>
                <td class=title> ��ȫ����� </td>
                <td class= input><Input class="wid" class="common" name=EdorAcceptNo id=EdorAcceptNo></td>
                <TD class= title> �������� </TD>
                <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('gedornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('gedornotype',[this, OtherNoName], [0, 1]);" onkeyup="showCodeListKey('gedornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName id=OtherNoName readonly=true ></td>
                <TD class= title> ���屣���� </TD>
                <td class= input><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
            </TR>
            <TR  class= common>
                <td class=title> ������ </td>
                <td class=input><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>
                <td class=title> ���뷽ʽ </td>
                <td class= input ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType  id=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly=true></td>
                <TD class= title> ¼������ </TD>
                <TD class= input><!--<Input class= "multiDatePicker" dateFormat="short" name=MakeDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR  class= common>
                <TD class=title> ������� </TD>
                <TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true></TD>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
            </TR>
        </table>
    </div>
</Div>
    <!--<INPUT VALUE=" �� ѯ " class = cssButton TYPE=button onclick="queryAllGrid()">-->
    <a href="javascript:void(0);" class="button" onClick="queryAllGrid();">��    ѯ</a>
	
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllGrid);">
            </td>
            <td class= titleImg>
                 ��������
            </td>
        </tr>
    </table>
    <div id="divAllGrid" style="display:''">
        <table class="common">
            <tr class="common">
                <td><span id="spanAllGrid"></span></td>
            </tr>
        </table>
        <div id="divTurnPageAllGrid" align="center" style="display:'none'">
            <!--<input type="button" class="cssButton" value="��  ҳ" onclick="turnPageAllGrid.firstPage()">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageAllGrid.previousPage()">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageAllGrid.nextPage()">
            <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageAllGrid.lastPage()">-->
        </div>
    </div>

    <br>
        <!--<INPUT class=cssButton VALUE=" �� �� " TYPE=button onClick="applyMission()">-->
        <a href="javascript:void(0);" class="button" onClick="applyMission();">��    ��</a>
    <br><br>

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
            </td>
            <td class= titleImg>
                 �ҵ�����
            </td>
        </tr>
    </table>
    <div id="divSelfGrid" style="display:''">
        <table class="common">
            <tr class="common">
                <td><span id="spanSelfGrid"></span></td>
            </tr>
        </table>
        <div id="divTurnPageSelfGrid" align="center" style="display:'none'">
            <!--<input type="button" class="cssButton" value="��  ҳ" onclick="turnPageSelfGrid.firstPage()">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.previousPage()">
            <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.nextPage()">
            <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageSelfGrid.lastPage()">-->
        </div>
    </div>

    <br>
    <!--<INPUT class= cssButton TYPE=button VALUE=" ��ȫ���� " onclick="GoToBusiDeal()">
    <INPUT class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">-->
      <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">��ȫ����</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a>

    <br>
<!-- ========zhangtao======2005-05-04==========BGN=============================================
    <table class= common >
            <tr class= common>
                <td class=title> ����ԭ�� </td>
                <td class= input colspan=5 ><input type="input" class="code" name='CancelMainReasonCode'  ondblclick="showCodeList('CancelEdorReason', [this, delMainReason], [0, 1]);" onkeyup=" showCodeListKey('CancelEdorReason', [this, delMainReason] , [0, 1]);"></td>
            </tr>
            <tr class= common>
              <td class= input colspan=6 >
              <textarea name="delMainReason" cols="60" rows="3" class="common"></textarea>
              </td>
          </tr>
    </table>
    <input type=button class= cssButton value="��������" onClick="CancelEdorMain();" >
=============zhangtao======2005-05-04==========END============================================= -->
        <!-- �����򲿷� -->
        <input type="hidden" name="MissionID">
        <input type="hidden" name="SubMissionID">
        <input type="hidden" name="ActivityID">
        <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
