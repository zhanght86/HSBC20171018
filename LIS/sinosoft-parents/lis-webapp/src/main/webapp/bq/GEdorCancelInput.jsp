<html>
<%
//�������ƣ�EdorCancelInput.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 09:20:22
//������  ��zhangtao
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

//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====BGN===================================
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //��ȫ�����
    String tMissionID = request.getParameter("MissionID");          //����ID
    String tSubMissionID = request.getParameter("SubMissionID");    //������ID
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====END===================================

%>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./GEdorCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="GEdorCancelInit.jsp"%>

</head>
<body  onload="initForm();" >
    <form action="./PEdorAppCancelSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
                </td>
                <td class= titleImg> ��ȫ������Ϣ </td>
            </tr>
        </table>
        <Div  id= "divEdorAppInfo" style= "display: ''" class="maxbox1">
            <TABLE class=common>
            <!- ��ʾ��ȫ����Ļ�����Ϣ,���������� �Թ��ο� ->
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                    <td class=title> ������� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=OtherNo id=OtherNo></td>
                    <td class=title> �������� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
                </tr>
                <tr class=common>
                    <td class=title> ������ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAppName id=EdorAppName></td>
                    <td class=title> ���뷽ʽ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=Apptype id=Apptype></td>
                    <td class=title> ������� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=ManageCom id=ManageCom></td>
                </tr>
                <tr class=common>
                    <td class=title> ��/�˷ѽ�� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=GetMoney id=GetMoney></td>
                    <td class=title> ��/�˷���Ϣ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=GetInterest id=GetInterest></td>
                    <td class= title> </td>
                    <td class= input> </td>
                </tr>
            </TABLE>
        </Div>
        
        <!-- XinYQ commented on 2006-02-09 : ��ʼ����ʾ������������Ŀ��Ϣ : BGN -->
        <!--
        <Div  id= "divEdorMainInfo" style= "display: 'none'">
            <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                    </td>
                    <td class= titleImg> ��ȫ����������Ϣ </td>
                </tr>
            </table>
            <Div  id= "divEdorMainGrid" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1>
                            <span id="spanEdorMainGrid" >
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </DIV>
        <Div  id= "divEdorItemInfo" style= "display: 'none'">
            <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                    </td>
                    <td class= titleImg> ��ȫ������Ŀ��Ϣ </td>
                </tr>
            </table>
            <Div  id= "divEdorItemGrid" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1>
                            <span id="spanEdorItemGrid" >
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </DIV>
        -->
        <!-- XinYQ commented on 2006-02-09 : ��ʼ����ʾ������������Ŀ��Ϣ : BGN -->

        <!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : BGN -->
        <!-- ������Ŀ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divAppItemList)"></td>
                <td class="titleImg">������Ŀ��Ϣ</td>
           </tr>
        </table>
        <!-- ������Ŀ���չ�� -->
        <div id="divAppItemList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ŀ�����ҳ -->
            <!--<div align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage.lastPage()">
            </div>-->
        </div>
        <!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : END -->

    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg> ��ȫ���볷�� </td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>����ԭ��</td>
                    <td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CancelReasonCode" id="CancelReasonCode" verify="��ȫ���볷��ԭ��|NotNull&Code:CancelEdorReason" ondblclick="return showCodeList('CancelEdorReason',[this,CancelReasonName],[0,1]);" onMouseDown="return showCodeList('CancelEdorReason',[this,CancelReasonName],[0,1]);" onkeyup="clearEmptyCode(this,CancelReasonName); return showCodeListKey('CancelEdorReason',[this,CancelReasonName],[0,1]);"><input class=codename name=CancelReasonName id=CancelReasonName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > ��ϸ��� </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px"  class=input colspan=6 ><textarea name="CancelReasonContent" id=CancelReasonContent cols="222%" rows="4" witdh=100% class="common wid"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>

        
        <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="cSubmit();">
        <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="cCancel();" style= "display: 'none'">
        <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="returnParent();">

    <!-- ������-->
    <input type="hidden" name="MissionID" id=MissionID>
    <input type="hidden" name="SubMissionID" id=SubMissionID>
    <input type="hidden" name="EdorState" id=EdorState>
    <input type="hidden" name="EdorMainState" id=EdorMainState>
    <input type="hidden" name="EdorItemState" id=EdorItemState>
    <input type="hidden" name="DelFlag" id=DelFlag>
    <input type="hidden" name="EdorNo" id=EdorNo>
    <input type="hidden" name="EdorType" id=EdorType>
    <input type="hidden" name="ContNo" id=ContNo>
    <input type="hidden" name="InsuredNo" id=InsuredNo>
    <input type="hidden" name="PolNo" id=PolNo>
    <input type="hidden" name="MakeDate" id=MakeDate>
    <input type="hidden" name="MakeTime" id=MakeTime>
    <input type="hidden" name="ActionFlag" id=ActionFlag>
    <input type="hidden" name="OtherNoType" id=OtherNoType>
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>

</body>
</html>
