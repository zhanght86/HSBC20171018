<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�
//�����ܣ����˱�ȫ
//�������ڣ�2002-07-19 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//            XinYQ     2007-06-04
%>


<%@ include file="../common/jsp/UsrCheck.jsp" %>

<html>
<head >

    <title>Э���˱�</title>
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
    <!-- ˽�����ýű� -->
    <SCRIPT language="JavaScript" src="../common/javascript/MultiCom.js"></SCRIPT>
    <script language="JavaScript" src="PEdor.js"></script>
    <script language="JavaScript" src="PEdorTypeXT.js"></script>
    <%@ include file="PEdorTypeXTInit.jsp" %>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeXTSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
            </tr>
            <tr class="common">
                <td class="title">������������</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">��Ч����</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
    <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPolGrid1);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
    </tr>
   </table>
  <Div  id= "divPolGrid1" style= "display: ''">
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
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    ����������Ϣ������'121704','121705','121801'���������˱���
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
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
      </td>
      <td class= titleImg>
        �˱���Ϣ
      </td>
   </tr>
   </table>


    <Div  id= "divLPInsured" style= "display: ''">
      <table  class= common>
        <TR class = common>
            <TD  class= title > ������Ч���� </TD>
            <TD  class= input ><input class="coolDatePicker" readonly name=CvaliDate onClick="laydate({elem: '#CvaliDate'});" id="CvaliDate"><span class="icon"><a onClick="laydate({elem: '#CvaliDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
            <TD  class= title > �ͻ�ǩ������ </TD>
            <TD  class= input ><input class="coolDatePicker" readonly name=CustomGetPolDate onClick="laydate({elem: '#CustomGetPolDate'});" id="CustomGetPolDate"><span class="icon"><a onClick="laydate({elem: '#CustomGetPolDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
            <TD  class= title > </TD>
            <TD  class= input > </TD>
          <!--  <TD  class= title > ���Ѷ�Ӧ�� </TD>
            <TD  class= input ><input class="readonly wid" readonly name=PayToDate ></TD>-->
        </TR>
        <TR class = common >
            <TD  class= title > ����������� </TD>
            <TD  class= input ><input class="readonly wid" readonly name=Inteval id=Inteval ></TD>
            <!-- <TD  class= title > ��ԥ�ڱ�־ </TD> -->
			<TD  class= title > </TD>
            <TD  class= input ><input class="readonly wid" type=hidden readonly name=CTType id=CTType ></TD>
            <TD  class= title > </TD>
            <TD  class= input > </TD>
        </TR>
        <TR class = common>
            <td class = title> �˱�ԭ�� </td>
            <td class=input><Input class=codeno name=SurrReason id=SurrReason verify="�˱�ԭ��|code:XSurrORDeReason&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('XSurrORDeReason',[this,SurrReasonName],[0,1]);" onkeyup="return showCodeListKey('XSurrORDeReason',[this,SurrReasonName],[0,1]);"><input class=codename name=SurrReasonName id=SurrReasonName readonly=true elementtype=nacessary></td>
            <td  class = title> Ͷ������ҵ��Ա��ϵ </td>
            <td class= input ><Input class="codeno" name=RelationToAppnt id=RelationToAppnt style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('relationtoappnt',[this, RelationToAppntName], [0, 1]);"onkeyup="showCodeListKey('relationtoappnt', [this, RelationToAppntName], [0, 1]);"><input class=codename name=RelationToAppntName id=RelationToAppntName readonly=true></td>
            <TD  class= title > </TD>
            <TD  class= input > </TD>
       </TR>
	</table>
	<table>
        <tr class=common>
            <TD class=title> Э���˱�����ԭ��ע </TD>
        </tr>
        <tr class=common>
            <TD  class=input><textarea name="ReasonContent" id=ReasonContent cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </tr>
     </table>
   </Div>
 <Div  id= "divLRInfo" style= "display: none">
      <table  class= common>
        <TR  class = common>
            <TD  class= title width="15%"> ������������ </TD>
            <TD  class= input ><input class="common" type="text" name="LostTimes" id=LostTimes onblur="return checkNumber(this)"></TD>
            <input type=hidden id="TrueLostTimes" name="TrueLostTimes" id=TrueLostTimes>
        </TR>
     </table>
</Div>
   <Div id= "divCalButton" style="display: ''">
        <Input  class= cssButton type=Button value=" �� �� " onclick="edorTypeCTCal()">
        <b><font color = red >(���Ҫ���µ����������¼���)</font> </b>
    </Div>
    <BR>
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeeInfo);">
                </td>
                <td class= titleImg> �˷Ѻϼ� </td>
            </tr>
    </table>

    <Div  id= "divCTFeeInfo" class=maxbox1 style= "display: ''">
            <table class="common">
                <tr class="common">
                    <td class="title">Ӧ�˽��</td>
                    <td class="input"><input type="text" class="multiCurrency wid"  name="GetMoney" id=GetMoney></td>
                    <td class="title">ʵ�˽��</td>
                    <td class="input"><input type="text" class="multiCurrency wid" name="AdjustMoney" id=AdjustMoney></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
    </DIV>

    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeePolDetail);">
                </td>
                <td class= titleImg> �����˷Ѻϼ� </td>
            </tr>
    </table>

    <Div  id= "divCTFeePolDetail" style="display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCTFeePolGrid"></span></td>
            </tr>
        </table>
    </Div>

    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeeDetail);">
                </td>
                <td class= titleImg> �˱����õ��� </td>
            </tr>
    </table>

    <Div  id= "divCTFeeDetail" style="display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCTFeeDetailGrid"></span></td>
            </tr>
        </table>
    </Div>
    <BR><BR>
   <Div id= "divEdorquery" style="display: ''">
             <Input  class= cssButton type=Button value=" �� �� " onclick="saveEdorTypeXT()">
             <Input  class= cssButton type=Button value=" �� �� " onclick="returnParent()">
             <Input  class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
    </Div>
    <BR><BR>
	<div id="divAppnt" style= "display: none">
	     <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divActLCBnfPol);">
                </td>
                <td class= titleImg>
               ʵ����ȡ���б�:<font color=red>���֤��Ϊ���֤,�Ա𡢳������ڲ�����д,Ĭ��ΪͶ����</font></td>
                </td>
            </tr>
        </table>
    	<Div  id= "divActLCBnfPol" style= "display: ''">
        	<table  class= common>
            	<tr  class= common>
                	<td text-align: left colSpan=1>
                    	<span id="spanCustomerActBnfGrid" >
                    	</span>
                	</td>
            	</tr>
        	</table>
    	</DIV>
		<Div id= "divEdorSubmit" style="display: ''">
             <Input  class= cssButton type=Button value=" �� ��  ʵ ��  �� �� �� �� Ϣ" onclick="edorActBnfSave()">
    	</Div>
  	</div>

    <input type=hidden name="fmtransact">
    <input type=hidden name="ContType">
    <input type=hidden name="EdorNo">
    <input type=hidden name="hasCal">
    <input type=hidden name="PayToDate"> <!--���Ѷ�Ӧ��-->
    <input type=hidden name="CTType"> <!--���Ѷ�Ӧ��-->
    <input type=hidden id="WTContFLag" name="WTContFLag">
 
<br /><br /><br /><br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
