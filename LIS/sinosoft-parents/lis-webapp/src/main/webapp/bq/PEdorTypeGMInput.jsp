<html>
<%
//�������ƣ�
//�����ܣ���ȫ
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>

  <SCRIPT src="./PEdorTypeGM.js"></SCRIPT>

  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@include file="PEdorTypeGMInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeGMSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
      <TD class = title > ������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
    <TR class=common>
        <TD class =title>������������</TD>
        <TD class = input>
            <Input class= "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class =title>��Ч����</TD>
        <TD class = input>
            <Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class = title></TD>
        <TD class = input></TD>
    </TR>
  </TABLE>
  </div>
<div id = "divPerson" style = "display:''">
  <TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo1);">
      </td>
      <TD class= titleImg>
        �ͻ�������Ϣ
      </TD>
   </TR>
   </TABLE>
   <Div  id= "divAGInfo1" class=maxbox1 style= "display: ''">
   <TABLE class=common>
    <TR  class= common>
      <TD  class= title > Ͷ��������</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=AppntName id=AppntName >
      </TD>
      <TD class = title > ֤������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=AppntIDType id=AppntIDType>
      </TD>

      <TD class = title > ֤������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo>
      </TD>
    </TR>
    <TR class=common>
        <TD class =title>����������</TD>
        <TD class = input>
            <Input class="readonly wid" readonly name=InsuredName id=InsuredName ></TD>
        </TD>
        <TD class =title>֤������</TD>
        <TD class = input>
            <Input class="readonly wid" readonly name=InsuredIDType id=InsuredIDType ></TD>
        </TD>
        <TD class = title>֤������</TD>
        <TD class = input>
            <input class="readonly wid" readonly name= InsuredIDNo id=InsuredIDNo ></TD>
    </TR>
  </TABLE>
</Div>
</div>
   <TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo2);">
      </td>
      <TD class= titleImg>
        ԭ����ȡ��ʽ
      </TD>
   </TR>
   </TABLE>
    <Div  id= "divAGInfo2" class=maxbox1 style= "display: ''">
      <table  class= common>
          <TD  class= title5 width="">
          ��ȡ��ʽ
          </TD>
          <TD  class= input5 width="">
                <input class="codeno" name=GetDutyKind_S id=GetDutyKind_S ><input class="codename" name=GetDutyKind_SName id=GetDutyKind_SName readonly=true>
          </TD>
          <TD  class= title5 width="">
          </TD>
          <TD  class= input5 width="">
          </TD>
      </table>
      <Div id = "OldGetStand" style = "display:''">
      <table  class= common>
          <TD  class= title5>���ǰ��ȡ��׼</TD>
          <TD  class= input5>
            <Input class="readonly wid"  readonly name=ChgBeforeStand id=ChgBeforeStand>
          </TD>
          <TD  class= title5 width="">
          </TD>
          <TD  class= input5 width="">
          </TD>
      </table>
      </Div>

    </Div>
     <TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGMInfo3);">
      </td>
      <TD class= titleImg>
        �������ȡ��ʽ
      </TD>
   </TR>
   </TABLE>
    <div id="divGMInfo3" class=maxbox1 style="display:''">
        <table  class= common>
            <TD  class= title5 width="">
            ��ȡ��ʽ
            </TD>
            <TD  class= input5 width="">
                <input class="codeno" name=GetDutyKind id=GetDutyKind style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="initGetDutyCode(this,GetDutyKindName);"
                    onkeyup="keyUpGetDutyCode(this,GetDutyKindName);"><input class="codename" name=GetDutyKindName id=GetDutyKindName readonly=true>
            </TD>
            <TD  class= title5 width="">
            </TD>
            <TD  class= input5 width="">
            </TD>
        </table>

        <Div id = "NewGetStand" style = "display:''">
        <table  class= common>
            <TD  class= title5>�������ȡ��׼</TD>
            <TD  class= input5>
              <Input class="readonly wid"  readonly name=ChgAfterStand id=ChgAfterStand>
            </TD>
            <TD  class= title5 width="">
            </TD>
            <TD  class= input5 width="">
            </TD>
        </table>
        </Div>
      </div>

    <Div id= "divEdorquery" style="display: ''">
        <Input  class= cssButton type=Button value="��  ��" onclick="edorTypeGMSave()">
        <Input  class= cssButton type=Button value="��  ��" onclick="returnParent()">
        <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">
    </Div>

     <input type=hidden id="fmtransact" name="fmtransact">
     <input type=hidden id="ContType" name="ContType">
     <input type=hidden id="PolNo" name="PolNo">
     <input type=hidden id="RiskCode" name="RiskCode">
     <input type=hidden id="EdorNo" name="EdorNo">
     <input type=hidden id="GetDutyCode" name="GetDutyCode">
     <input type=hidden id="DutyCode" name="DutyCode">
     <Input type=hidden id="GetStandMoney" name="GetStandMoney">
     <Input type=hidden id="InsuredNo" name="InsuredNo">
     
     <input type=hidden id="PGrpFlag" 	name="PGrpFlag">  <!-- �������ձ�ǣ�GroupΪ���������˱����PersonΪ���������˱��  --->
  <br /><br /><br /><br /><br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
