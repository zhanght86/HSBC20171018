<html>
<%
//�������ƣ�PEdorTypeFMInput.jsp
//�����ܣ����˱�ȫ-�ɷѷ�ʽ�����ޱ��
//�������ڣ�2005-05-14 11:05:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypePM.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypePMInit.jsp"%>
  <title> ���Ѽ����� </title>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypePMSubmit.jsp" method=post name=fm id=fm target="fraSubmit">

<!-- ��ȫ������Ϣ -->
    <div class=maxbox1>
    <TABLE class=common>
        <tr class=common>
            <td class=title> ��ȫ������ </td>
            <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
            <td class=title> �������� </td>
            <td class= input><Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true></td>
            <td class=title> ������ </td>
            <td class= input><Input type="input" class="readonly wid" readonly name=ContNo id=ContNo> </td>
        </tr>
        <tr class=common>
            <td class=title> ������������ </td>
            <td class= input><Input type="input" class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
            <td class=title>  </td>
            <td class= input> </td>
			<td class=title>  </td>
            <td class= input> </td>
        </tr>
    </TABLE>
    </div>
    <Div  id= "divMultiPol" style= "display: ''">
        <table>
                <tr>
                    <td class=common>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
                    </td>
                    <td class= titleImg> ���������Ϣ<font color = red >(ѡ�е�ѡ��ť��ѯ����ϸ��������Ϣ)</font></td>
                </tr>
        </table>

        <Div  id= "divPolGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanPolGrid" >
                        </span>
                    </td>
                </tr>
            </table>
        </Div>
    </Div>

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolInfo);">
            </td>
            <td class= titleImg> �����������Ϣ<font color = red >(�����ѡ�е�ѡ��ť��ѯ������������Ϣ)</font></td>
        </tr>
    </table>

    <Div id= "divPolInfo" class=maxbox1 style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td class=title> ���ֺ� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=PolNo_old id=PolNo_old></td>
                <td class=title> ���ִ��� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=RiskCode_old id=RiskCode_old></td>
                <td class=title> �������� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=RiskName_old id=RiskName_old></td>
            </tr>
            <tr  class= common>
                <td class=title> Ͷ���� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=AppntName_old></td>
                <td class=title> ������ </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=InsuredName_old></td>
                <td class=title> Ͷ������ </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=InsuredAppAge_old></td>
            </tr>
            <tr  class= common>
                <td class=title> ���ѱ�׼ </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=Prem_old id=Prem_old></td>
                <td class=title> �������� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=Amnt_old id=Amnt_old></td>
                <td class=title> ���� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=Mult_old id=Mult_old></td>
            </tr>
        </table>
    </DIV>


    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorTypeChangeInfo);">
            </td>
            <td class= titleImg> ���Ѽ�������Ϣ </td>
        </tr>
    </table>

    <Div id= "divEdorTypeChangeInfo" class=maxbox1 style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <TD  class= title> ���Ѽ�� </TD>
                <TD  class= input><Input class= "codeno" name=PayIntv_new id=PayIntv_new style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
                    ondblClick="initPayEndYear(this,PayIntv_newName);"
                    onkeyup="initPayEndYear(this,PayIntv_newName);" ><input class=codename name=PayIntv_newName id=PayIntv_newName readonly=true></TD>
                <TD class = title>&nbsp;</TD>
                <TD class = input>&nbsp;</TD>
                <TD class = title>&nbsp;</TD>
                <TD class = input>&nbsp;</TD>
            </tr>
        </table>
    </DIV>
  <br>
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" �� �� " onClick="saveEdorTypeFM()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <input type="button" class="cssButton" value="���±��鿴" onClick="showNotePad()">
        </div>

        <input type=hidden name="fmtransact">
        <input type=hidden name="InsuredNo">
        <input type=hidden name="PolNo">
        <input type=hidden name="EdorNo">
        <input type=hidden name="RiskCode">
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
		<br /><br /><br /><br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>