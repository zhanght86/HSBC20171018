<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
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
  
  <SCRIPT src="./PEdorTypeXS.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="PEdorTypeXSInit.jsp"%>
  <title>Э�����</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
 <TABLE class=common>
    <TR  class= common>
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
        <Input class=codeno name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly>
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
        <TD class = title></TD>
        <TD class = input></TD>
		<TD class = title></TD>
        <TD class = input></TD>
    </TR>
  </TABLE>
  </div>
  <!--���������б���Ϣ-->
 <Div  id= "divPolInfo" style="display:''">
     <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Customer);">
            </td>
            <td class= titleImg>
                �ͻ���Ϣ
            </td>
        </tr>
    </table>
    <Div id = "Customer" style="display:''">
        <table>
            <tr>
                <td>
                    <span id="spanCustomerGrid"></span>
                </td>
            </tr>
        </table>
    </Div>
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid1);">
                </td>
                <td class= titleImg>
                    ���������б���Ϣ
                </td>
            </tr>
        </table>
    <Div  id= "divPolGrid1" style="display:''">
        <table  class=common>
            <tr  class=common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
    </DIV>

   <!--���ֵ���ϸ��Ϣ-->

     <!--��������-->
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,OperationDiv);">
      </td>
      <td class= titleImg>
        ��ǰ���� <font color = red >������ǰ��ѡ��������� �����ճ��⣩</font>
      </td>
   </tr>
   </table>
   <div id="OperationDiv" class=maxbox1>
   <table>
   <TR class = common>
            <td class = common> ����ԭ�� </td>
            <td class=input><Input class=codeno name=SurrReasonCode id=SurrReasonCode verify="�˱�ԭ��|code:XSurrORDeReason&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('XSurrORDeReason',[this,SurrReasonName],[0,1]);" onkeyup="return showCodeListKey('XSurrORDeReason',[this,SurrReasonName],[0,1]);"><input class=codename name=SurrReasonName id=SurrReasonName readonly=true elementtype=nacessary></td>
            <td  class = common> Ͷ������ҵ��Ա��ϵ </td>
            <td class= input ><Input class="codeno" name=RelationToAppnt id=RelationToAppnt style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('relationtoappnt',[this, RelationToAppntName], [0, 1]);"onkeyup="showCodeListKey('relationtoappnt', [this, RelationToAppntName], [0, 1]);"><input class=codename name=RelationToAppntName id=RelationToAppntName readonly=true></td>
            <TD  class= title > </TD>
            <TD  class= input > </TD>
       </TR>
       <tr class=common>
            <TD class=title colspan=6 > Э���������ԭ��ע </TD>
        </tr>
        <tr class=common>
            <TD  class=input colspan=6 ><textarea name="ReasonContent" id=ReasonContent cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </tr>
       </table>
   <!-- ������ -->
    <div id = "PTAmntDiv" style= "display: none">	
      <table class = common>
       		<tr  class= common>
		       <TD  class= title >���ٱ���</TD>
		       <TD  class= input > <input class="multiCurrency wid"  name=MinusPTAmnt id=MinusPTAmnt></TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
            </tr>   
       </table>
    </div>
    <!-- ������ -->
    <div id = "PTPremDiv" style= "display: none">	
      <table class = common>
       		<tr  class= common>
		       <TD  class= title >���ٱ���</TD>
		       <TD  class= input > <input class="multiCurrency wid"  name=MinusPTPrem id=MinusPTPrem></TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
            </tr>   
       </table>
    </div>
    <!-- ������ -->
    <div id = "PTMutDiv" style= "display: none">	
      <table class = common>
       		<tr  class= common>
		       <TD  class= title >���ٷ���</TD>
		       <TD  class= input > ]<input class="common wid"  name=MinusPTMut id=MinusPTMut></TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   
            </tr>   
       </table>
    </div>
    <div id = "divPTbuttion" style = "display: ''">
        <Input class= cssButton type=Button value=" Э����� " onclick="reduceOneRisk()">
    </div>
    
    <!--Э���������Ϣ add by jiaqiangli 2008-09-16 -->
    <!--Ӧ����ʵ��-->
    <Div  id= "divCTFeeInfo" style= "display: ''">
            <table class="common">
                <tr class="common">
                    <td class="title">Ӧ�˽��</td>
                    <td class="input"><input type="text" class="multiCurrency wid" name="GetMoney" id=GetMoney readonly></td>
                    <td class="title">ʵ�˽��</td>
                    <td class="input"><input type="text" class="multiCurrency wid" name="AdjustMoney" id=AdjustMoney readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
    </DIV>
	</div>
    <!--Э���������Ϣ add by jiaqiangli 2008-09-16 -->
    
    <!--Э���������Ϣ add by jiaqiangli 2008-09-16 -->
    <Div  id= "divCTFeePolDetailButton" style="display: none">
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeePolDetail);">
                </td>
                <td class= titleImg> �����˷Ѻϼ� </td>
            </tr>
    </table>
	</Div>
    <Div  id= "divCTFeePolDetail" style="display: none">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCTFeePolGrid"></span></td>
            </tr>
        </table>
    </Div>
		 <Div  id= "divCTFeeDetailButton" style="display: none">
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeeDetail);">
                </td>
                <td class= titleImg> �˱����õ��� </td>
            </tr>
    </table>
	</Div>
    <Div  id= "divCTFeeDetail" style="display: none">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCTFeeDetailGrid"></span></td>
            </tr>
        </table>
    </Div>
    <BR>
        <!--��������Ϣ-->
<div id = 'divnone' style = "display: none">
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol5);">
      </td>
      <td class= titleImg>
        �����˻������Ϣ
      </td>
   </tr>
   </table>
<Div id = "divPol5" class=maxbox1 style= "display: none">
    <table class = common>
        <tr class = common>
            <td class = title>��������Ӧ�˽��</td>
            <td class = input>
                <input class="readonly wid" name = CashValue id=CashValue>
            </td>
            <td class = title>�ۼƺ���Ӧ�˽��</td>
            <td class = input>
                <input class="readonly wid" readonly name = AddupBonus id=AddupBonus>
            </td>
            <td class = title>���˺���Ӧ�˽��</td>
            <td class = input>
                <input class="readonly wid" readonly name = FinaBonus id=FinaBonus>
            </td>
        </tr>
        <tr class = common>
            <td class = title>�˻����ѽ��</td>
            <td class = input>
                <input class="readonly wid" readonly name = Prem id=Prem>
            </td>
            <td class = title>�����ӷ�Ӧ�˽��</td>
            <td class = input>
                <input class="readonly wid" readonly name = AddJK id=AddJK>
            </td>

            <td class = title>ְҵ�ӷ�Ӧ�˽��</td>
            <td class = input>
                <input class="readonly wid" readonly name = AddZY id=AddZY>
            </td>
        </tr>
    </table>
</Div>
<table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
      </td>
      <td class= titleImg>
        �۳������Ϣ
      </td>
   </tr>
   </table>
   <Div id = "divPol2" class=maxbox1 style= "display:none">
    <table class = common>
        <tr class = common>
            <td class = title>�����</td>
            <td class = input>
                <input class="readonly wid" readonly name=LoanCorpus id=LoanCorpus>
            </td>
            <td class = title>������Ϣ</td>
            <td class = input>
                <input class="readonly wid" readonly name=LoanInterest id=LoanInterest>
            </td>
            <td class = title>�Ե汾��</td>
            <td class = input>
                <input class="readonly wid" readonly name=AutoPayPrem id=AutoPayPrem>
            </td>
            </tr>
        <tr>
            <td class = title>�Ե���Ϣ</td>
            <td class = input>
                <input class="readonly wid" readonly name=AutoPayInterest id=AutoPayInterest>
            </td>
            <td class = title>Ƿ�ɱ���</td>
            <td class = input>
                <input class="readonly wid" readonly name=OwePrem id=OwePrem>
            </td>
            <td class = title>Ƿ�ɱ�����Ϣ</td>
            <td class = input>
                <input class="readonly wid" readonly name=OweInterest id=OweInterest>
            </td>
        </tr>
        <tr>
            <td class = title>�۳����ϼ�</td>
            <td class = input>
                <input class="readonly wid" readonly name=PayMoney id=PayMoney>
            </td>
            <td class = title></td>
            <td class = input>
            </td>
            <td class = title></td>
            <td class = input></td>
        </tr>
    </table>
  </Div>


   <Div id = "divPol4" style= "display: none">
    <table class = common>
        <tr class = common>
            <td class = title>�������������</td>
            <td class = input>
                <input class="readonly wid" readonly>
            </td>
            <td class = title> ���ڽɷѱ�׼</td>
            <td class = input>
                <input class="readonly wid" readonly>
            </td>
            <td class = title>�������ۻ���������</td>
            <td class = input>
                <input class="readonly wid" readonly>
            </td>
            </tr>

    </table>
  </Div>

</div>

  <br><br>
<Div  id= "divLRInfo" style= "display: none">
      <table  class= common>
        <TR  class = common>
            <TD  class= title width="15%"> ������������ </TD>
            <TD  class= input ><input class="common wid" type="text" name="LostTimes" id=LostTimes></TD>
            <input type=hidden id="TrueLostTimes" name="TrueLostTimes">
        </TR>
     </table>
</Div>
 
  <Div id= "divEdorquery" style="display: ''">
       <Input class= cssButton type=Button value=" �������� " onclick="saveEdorTypePT()">
       <Input class= cssButton type=Button value=" �� �� " onclick="returnParent()">
       <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
 </Div>

     <input type="hidden" name="AppObj">
     <input type="hidden" name="CalMode">
     <input type="hidden" name="fmtransact">
     <!-- comment by jiaqiangli 2008-11-20 ���ٱ�־ PTFlag 0���ٱ���1���ٷ���2���ٱ���-->
     <input type="hidden" name="PTFlag">
     <input type="hidden" name="ContType" value="1">
     <input type="hidden" name="EdorNo">
     <input type="hidden" name="Flag266">
     <input type="hidden" name="Flag267">
     <input type="hidden" name="SubScale">  <!--- ������Ͷ������ ��������ʹ�� add at 2005-11-29--->
     <input type="hidden" name="InsuredNo">

    <!-- comment by jiaqiangli -->
    <!--br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br-->
	<br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
