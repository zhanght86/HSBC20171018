
<%
//�������ƣ�
//�����ܣ����˱�ȫ
//�������ڣ�2002-07-19 16:49:22
//������  ��Supl
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>������Ч</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeRE.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeRE.js"></script>
    <%@ include file="PEdorTypeREInit.jsp" %>
</head>
<body onload="initForm();" >
  <form action="./PEdorTypeRESubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class="maxbox1">
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id="EdorAcceptNo" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id="EdorType" readonly><input type="text" class="codename" name="EdorTypeName" id="EdorTypeName" readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">������������</td>
                <td class="input"><!--<input type="text" class="multiDatePicker" name="EdorItemAppDate" readonly>-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#EdorItemAppDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorItemAppDate id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td>
                <!--  
                <td class="title">��Ч����</td>
                <td class="input"><input type="text" class="multiDatePicker" name="EdorValiDate" readonly></td>
                -->
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
   <!--���ֵ���ϸ��Ϣ-->
   <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol1);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
   </tr>
   </table>

    <Div  id= "divPol1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanCustomerGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>

  <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
      </td>
      <td class= titleImg>
        ���ֻ�����Ϣ
      </td>
   </tr>
   </table>
        <Div  id= "divPol2" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanLCInsuredGrid"></span></td>
            </tr>
        </table>
      </Div>
        <!--�ӱ�����Ϣ-->
<table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <td class= titleImg>
        �����˽�����֪��Ϣ
      </td>
   </tr>
   </table>

  <Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
<div id = "divAppnt" style = "display: none">
<table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divImpart);">
      </td>
      <td class= titleImg>
        Ͷ���˽�����֪��Ϣ
      </td>
   </tr>
   </table>
</div>
<div id = "divInsured" style = "display: none">
<table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divImpart);">
      </td>
      <td class= titleImg>
        �����յڶ������˽�����֪��Ϣ
      </td>
   </tr>
   </table>
</div>

  <Div  id= "divImpart" style= "display: none">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAppntImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
</div>

   <!--
   <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,OperationDiv);">
      </td>
      <td class= titleImg>
        ������Ч��Ϣ
      </td>
   </tr>
   </table>
    <Div id = "OperationDiv" style= "display: ">
        <table class = common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolInsuredGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>
    -->
    
    <table>
      <tr>
        <td class= titleImg>
          ��Ч��Ϣ���Ƿ���㸴Ч������Ϣ<Input name="isCalInterest" type=checkbox checked verify="�Ƿ������Ϣ|code:YesNo">��
        </td>
      </tr>
    </table>

    <br>
    <Div  id= "divEdorquery" style= "display: ''">
             <Input class= cssButton type=Button value=" �� �� " onclick="saveEdorTypeRE()">
             <Input class= cssButton type=Button value=" �� �� " onclick="returnParent()">
             <Input class= cssButton type=Button value="���±��鿴" onclick="showNotePad()">
   </Div>

     <input type="hidden" name="CalMode">
     <input type="hidden" name="fmtransact">
     <input type="hidden" value= 1 name="ContType">
     <input type="hidden" value= 1 name="EdorNo">
     <input type="hidden" value= 1 name="InsuredNo">
     <input type="hidden" value= 1 name="CustomerNo"> <!-- ��Ͷ�������κ������ձ����˹���-->
     <input type="hidden" value= 1 name="ReFlag">  <!-- �����ձ�־--->

    <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
