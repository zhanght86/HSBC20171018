<%@page contentType="text/html;charset=GBK" %>

<html>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./PEdorTypePR.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypePRInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./PEdorTypePRSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
    <!--------------------------------------------------->
    <input type="hidden" readonly name="PolNo" id=PolNo>

    <!--------------------------------------------------->
	<div class=maxbox1>
   <TABLE class=common>
    <TR  class= common>
      <TD  class= title > ��ȫ����� </TD>
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
                 <TR  class= common>
               <TD class =title>������������</TD>
        <TD class = input>
            <input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class =title>��Ч����</TD>
        <TD class = input>
            <input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        <td class="title">&nbsp;</td>
        <td class="input">&nbsp;</td>
      </TR>
  </TABLE>
  </div>
  <table>
  <tr>
  <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
    </tr>
  </table>
  <Div  id= "divAGInfo" class=maxbox1 style= "display: ''">
      <TABLE class=common>

          <TR  class= common>
      <TD  class= title > Ͷ�������� </TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=AppntName id=AppntName >
      </TD>
      <TD class = title >  ֤������</TD>
      <TD class = input >
        <Input class=codeno  "readonly" readonly name=AppntIDType id=AppntIDType ><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true>
      </TD>

      <TD class = title > ֤������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo>
      </TD>

    </TR>


    </TABLE>

<TABLE class=common>

          <TR  class= common>
      <TD  class= title > ���������� </TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=InsuredName id=InsuredName >
      </TD>
      <TD class = title >֤������ </TD>
      <TD class = input >
        <Input class=codeno  "readonly" readonly name=InsuredIDType id=InsuredIDType ><input class=codename name=InsuredIDTypeName id=InsuredIDTypeName readonly=true>
      </TD>

      <TD class = title > ֤������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=InsuredIDNo id=InsuredIDNo>
      </TD>

    </TR>


    </TABLE>
    </Div>

  <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    ����������Ϣ
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPol" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </DIV>
   <table>

   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
      </td>
      <td class= titleImg>
        ����������
      </td>
   </tr>
   </table>

    <Div  id= "divLPInsured" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR  class= common>
             <TD  class= title>ԭ�������</TD>
          <TD  class= input >
        <input class="codeno" readonly name="OldManageCom" id=OldManageCom><input type="text" class="codename" name="OldManageComName" id=OldManageComName readonly>
          </TD>
          <TD  class= title>Ǩ���������</TD>
          <TD class= input>
            <Input class="codeno" name="ManageCom" id=ManageCom CodeData=""   verify="Ǩ���������|NotNull&len=8" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('',[this,ManageComName],[0,1])" onkeyup="showCodeListKeyEx('',[this,ManageComName],[0,1])">
			<input class=codename name=ManageComName id=ManageComName readonly>
          </TD>
        <TD  class= title></TD>
          <TD  class= input >
        <input class="readonly wid" readonly name= ></TD>

        </TR>
      </table>
      </Div>

      <!--
      <Div  id= "LPInsured1" style= "display: none">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="PostalAddress" verify="Ͷ������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name="ZipCode" verify="Ͷ������������|zipcode" >
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
          <input class= common name="Phone" verify="Ͷ������ϵ�绰|len<=18" >
          </TD>
          <TD  class= title>
            ͨѶ����
          </TD>
          <TD  class= input>
            <Input class= common name="Fax" verify="����|len<=15" >
          </TD>
          <TD  class= title>
            �����ʼ�
          </TD>
          <TD  class= input>
            <Input class= common name="e-mail" verify="����|len<=30" >
          </TD>
        </TR>
      </table>
       </Div>
        -->
<br>
  <Div id= "divEdorquery" style="display: ''">
             <Input class= cssButton type=Button value=" �� �� " onclick="edorTypePRSave()">
             <Input class= cssButton type=Button value=" �� �� " onclick="returnParent()">
             <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">
</Div>
     <input type="hidden" name="EdorNo">
     <input type=hidden name="fmtransact">
     <input type=hidden name="ContType">
     <input type=hidden name="addrFlag">
     <input type=hidden name="CustomerNo">
     <input type=hidden name="AddressNo">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>


