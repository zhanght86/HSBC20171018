<html>
<%
//�������ƣ�EdorNoticeInput.jsp
//�����ܣ���ȫ���������
//�������ڣ�2008-12-08 15:20:22
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<head >
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //��ȫ�����
    String tMissionID = request.getParameter("MissionID");          //����ID
    loggerDebug("EdorNoticeInput","�����Ϊ"+tMissionID);
    String tSubMissionID = request.getParameter("SubMissionID");    //������ID
%>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="./EdorNotice.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="EdorNoticeInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./EdorNoticeSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr >
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
            </td>
            <td class= titleImg>��ȫ������Ϣ</td>
        </tr>
    </table>
    <Div  id= "divEdorAppInfo" style= "display: ''" class=maxbox1>
        <TABLE class=common>
        <!-- ��ʾ��ȫ����Ļ�����Ϣ,���������� �Թ��ο� -->
            <tr class=common>
                <td class=title> ��ȫ����� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                <td class=title> ������� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=OtherNo id=OtherNo></td>
                <td class=title> �������� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
            </tr>
            <tr class=common>
                <td class=title> ������ </td>
                <td class= input><input type="input" class="readonly wid" readonly name=EdorAppName id=EdorAppName></td>
                <td class=title> ���뷽ʽ </td>
                <td class= input><input type="input" class="readonly wid" readonly name=ApptypeName id=ApptypeName></td>
                <td class=title> ������� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=ManageComName id=ManageComName></td>
            </tr>
        </TABLE>
   </Div>


    <Div  id= "divEdorItemInfo" style= "display: ''">
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> ��ȫ������Ŀ��Ϣ </td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage2.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPage2.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPage2.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage2.lastPage()">
            </div>
        </div>
    </DIV>
        <Div  id= "divPayNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" ��ӡ�ܾ�֪ͨ�� "  class=cssButton TYPE=button onclick="InvaliNotice();">
        </Div>
        <Div  id= "divPayPassNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" ��ӡ���֪ͨ�� " class=cssButton TYPE=button onclick="InvaliPassNotice();">
        </Div>  

    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg>�·�����</td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class=maxbox>
            <table  class= common>
                <tr class=common>
                    <td class=title>��������</td>
                    <td class=input><Input class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" name=ApproveFlag id=ApproveFlag verify="��������|NotNull&Code:EdorNoticeIdea" ondblclick="return showCodeList('edornoticeidea',[this,'edornoticeideaName'],[0,1]);" onkeyup="return showCodeListKey('edornoticeidea',[this,'edornoticeideaName'],[0,1]);"><input class=codename name=edornoticeideaName id=edornoticeideaName readonly></td>
                    <td class="title">�·�����</td>
                    <td class="input"><Input class="codeno" name=SendTo id=SendTo value="1" readonly><input class=codename name=SendToName id=SendToName readonly value="�ͻ�"></td>
 
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > �������� </TD>
                </tr>
                <tr class=common>
                    <TD  class=input colspan=6 ><textarea name="ApproveContent" id=ApproveContent cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>

            <input type="button" class="cssButton" value=" ȷ �� " onClick="ApproveSubmit()">
            <input type="button" class="cssButton" value=" �� �� " onClick="ApproveCancel()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">

    <!-- ������-->
    <input type="hidden" id=MissionID name="MissionID">
    <input type="hidden" id=SubMissionID name="SubMissionID">
    <input type="hidden" id=EdorState name="EdorState">
    <input type="hidden" id=EdorMainState name="EdorMainState">
    <input type="hidden" id=EdorItemState name="EdorItemState">
    <input type="hidden" id=CancelType name="CancelType">
    <input type="hidden" id=EdorNo name="EdorNo">
    <input type="hidden" id=EdorType name="EdorType">
    <input type="hidden" id=ContNo name="ContNo">
    <input type="hidden" id=InsuredNo name="InsuredNo">
    <input type="hidden" id=PolNo name="PolNo">
    <input type="hidden" id=MakeDate name="MakeDate">
    <input type="hidden" id=MakeTime name="MakeTime">
    <input type="hidden" id=ActionFlag name="ActionFlag">
    <input type="hidden" id=OtherNoType name="OtherNoType">
    <input type="hidden" id=Apptype name="Apptype">
    <input type="hidden" id=ManageCom name="ManageCom">
    <input type="hidden" id=AppntName name="AppntName">
    <input type="hidden" id=PaytoDate name="PaytoDate">
    <input type="hidden" id=PrtSeq name="PrtSeq">
    <input type="hidden" id=EdorItemAppDate name="EdorItemAppDate">
    <input type="hidden" id=EdorAppDate name="EdorAppDate">
    <input type="hidden" id=EdorValiDate name="EdorValiDate">
    <input type="hidden" id=AppObj name="AppObj" value="I">
    <input type="hidden" id=ButtonFlag name="ButtonFlag" value="1">
	<input type="hidden" id=operator name="operator" value="">
	<input type="hidden" id=EdorPopedom name="EdorPopedom">
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>

</body>
</html>
