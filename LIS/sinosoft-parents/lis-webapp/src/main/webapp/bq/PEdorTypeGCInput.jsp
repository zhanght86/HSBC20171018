<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//�������ƣ�
//�����ܣ���ȡ��ʽ���
//�������ڣ�2005-4-25 01:40����
//������  ��Lihs
%>


<%@include file="../common/jsp/UsrCheck.jsp"%>


<html>
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�������ת����Ȩ</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeGC.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="./PEdor.js"></script>
    <script language="JavaScript" src="./PEdorTypeGC.js"></script>
    <%@include file="PEdorTypeGCInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeGCSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
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
                 <TR  class= common>
               <TD class =title>������������</TD>
        <TD class = input>
            <input class="readonly wid" readonly name=EdorItemAppDate  id=EdorItemAppDate></TD>
        </TD>
                <TD class = title> </TD>
                <TD class = input> </TD>
				<TD class = title> </TD>
                <TD class = input> </TD>
      </TR>
  </TABLE>
  </div>
  <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid1);">
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
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>

    <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
      </td>
      <td class= titleImg>
        ������Ϣ
      </td>
    </tr>
   </table>

    <Div  id= "divLCPol" class=maxbox1 style= "display: ''">

   <table  class= common>

       <TR>
          <TD class = title>
             Ͷ���˿ͻ���
          </TD>
          <TD class= input>
            <Input class="readonly wid" readonly  name=AppntNo id=AppntNo>
          </TD>

         <TD class= title>
            Ͷ��������
         </TD>
             <TD class= input>
                <Input class="readonly wid" readonly  name=AppntName id=AppntName>
             </TD>
              <TD class= title>
                ���ѷ�ʽ
                 </TD>
                 <TD class= input>
                 <Input class="readonly wid" readonly  name=PayMode id=PayMode>
                 </TD>
                </TR>
                <TR>
               <TD class= title>
                 ���ѱ�׼
         </TD>
         <TD class= input>
             <Input class="readonly wid" readonly  name=Prem id=Prem>
         </TD>

         <TD class= title>
         ��������
         </TD>
         <TD class= input>
             <Input class="readonly wid" readonly  name=Amnt id=Amnt>
         </TD>
         <TD class= title>
                ������ʽ
              </TD>
              <TD  class= input>
                <Input class="readonly wid" readonly name=PayLocation_S id=PayLocation_S>
              </TD>
            </TR>
            <TR>
         <TD class= title>
             ���б���
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=BankCode_S id=BankCode_S>
         </TD>
         <TD class= title>
             �����ʺ�
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=BankAccNo_S id=BankAccNo_S> 
         </TD>
         <TD class= title>
             �����˻���
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=AccName_S>
         </TD>
     </TR>
     <TR>
                <TD class = title>
                    ��ȡ��ʽ
                </TD>
                <TD class = input>
                    <input class ="readonly wid" readonly name=GerForm_S id=GerForm_S>
                </TD>
                <TD class = title> </TD>
                <TD class = input> </TD>
                <TD class = title> </TD>
                <TD class = input> </TD>
            </TR>
    </Table>
    </Div>

  <table>
    <tr>
      <td class=common>
        <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAGInfo);">
      </td>
      <td class= titleImg>
        ��ȡ��ʽ���
      </td>
    </tr>
   </table>
    <Div  id= "divAGInfo" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class = common >
          <TD class= title width="">
          ��ȡ��ʽ
          </TD>
          <TD  class= input width="">
          <input class="codeno" name=GetForm id=GetForm verify="��ȡ��ʽ|NotNull&Code:GetLocation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('GetLocation', [this,GetFormName],[0,1]);" onkeyup="showCodeListKey('GetLocation', [this,GetFormName],[0,1]);"><input class="codename" name=GetFormName id=GetFormName readonly>
                <!--<input class="codeno" name=GetForm CodeData="0|^1|������ȡ^2|����ת��^3|����֧��" ondblclick="showCodeListEx('GoonGetMethod', [this,GetFormName],[0,1]);" onkeyup="showCodeListKeyEx('GoonGetMethod', [this,GetFormName],[0,1]);" onfocus="showBankDiv();" ><input class="codename" name=GetFormName readonly=true>-->
                </TD>
                   <TD class= title width="">
                     </TD>
                    <TD  class= input width="">
                    </TD>
                    </TD>
                   <TD class= title width="">
                     </TD>
                    <TD  class= input width="">
                    </TD>
        </TR>
      </table>
      <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divOldBnfGridLayer)"></td>
                <td class="titleImg">�����˷���</td>
            </tr>
        </table>
      <!-- ����������¼���� -->
        <div id="divNewBnfGridLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanNewBnfGrid"></span></td>
                </tr>
            </table>
            <!-- �������˽����ҳ -->
            <div id="divTurnPageNewBnfGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageNewBnfGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageNewBnfGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageNewBnfGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageNewBnfGrid.lastPage()">
            </div>
        </div>
     <!-- modify by jiaqiangli 2008-08-21 ���ø����е�DIV
        <Div  id= "BankInfo" style= "display: none">
          <table  class= common>
            <TR class = common >
              <TD  class= title width="">
              ��������
              </TD>
              <TD  class= input width="">
                    <Input class="codeno" name=GetBankCode ondblclick="showCodeList('bank',[this, BankName], [0, 1]);"onkeyup="showCodeListKey('AgentCode', [this, BankName], [0, 1]);"><input class=codename name=BankName readonly=true>
              </TD>
                    <td class="title">�����ʺ�</td>
                    <td class="input"><input type="text" class="coolConfirmBox" name="GetBankAccNo"></td>
               <TD  class= title width="">
              ��   ��
              </TD>
              <TD  class= input width="">
                     <Input class= "common"  name="GetAccName">
              </TD>
            </TR>
          </table>
        </Div>
        -->
    </Div>
    <br>
    <Div id= "divEdorquery" style="display: ''">
             <Input  class= cssButton type=Button value=" �� �� " onclick="saveEdorTypeGC()">
             <Input  class= cssButton type=Button value=" �� �� " onclick="returnParent()">
             <Input  class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
    </Div>
     <input type="hidden" name="fmtransact">
     <input type="hidden" name="ContType">
     <input type="hidden" name="InsuredNo">
     <input type="hidden" name="PolNo">
     <input type="hidden" name="EdorNo">
     <input type="hidden" name="AppObj">
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</html>
