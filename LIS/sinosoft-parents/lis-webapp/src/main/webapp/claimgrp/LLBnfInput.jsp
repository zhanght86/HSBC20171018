<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
//=============================================================BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	  String tBnfKind = request.getParameter("BnfKind"); //��������
//=============================================================END
%>
    <title>�����˷���</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLBnf.js"></script>
    <%@include file="LLBnfInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>
<!--�ⰸ�����б�-->
<br>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBalanceGrid);"></TD>
            <TD class= titleImg> �ⰸ������Ϣ </TD>
        </TR>
    </Table>
    <Div  id= "divLLBalanceGrid" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLBalanceGrid" ></span> </TD>
            </TR>
        </Table>
        <!--table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table-->
    </Div>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBalanceForm);"></TD>
            <TD class= titleImg> �ⰸ������ϸ��Ϣ </TD>
        </TR>
    </Table>
    <Div  id= "divLLBalanceForm" style= "display: ''">
         <TABLE class=common>
             <tr class=common>
                 <td class=title> �ⰸ�� </td>
                 <td class= input><Input class= "readonly" readonly name="ClmNo2"></td>
                 <td class=title> ������ </td>
                 <td class= input><Input class= "readonly" readonly name="polNo"></td>
                 <td class=title> �⸶��� </td>
                 <td class= input><Input class= "readonly" readonly name="sumPay"></td>
             </tr>
         </TABLE>
    </div>
<!--�����������˻�-->
    <Div  id= "divLLBalance" style= "display: """>
        <hr>
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfGrid);"></TD>
                <TD class= titleImg> ������������Ϣ </TD>
            </TR>
         </Table>
         <Div  id= "divLLBnfGrid" style= "display: ''">
             <Table class= common>
                 <TR class= common>
                     <TD text-align: left colSpan=1><span id="spanLLBnfGrid" ></span> </TD>
                 </TR>
             </Table>
             <!--table> 
                 <tr>
                     <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                     <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                     <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                     <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
                 </tr>
             </table-->
         </Div>
         <Table>
              <TR>
                   <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfForm);"></TD>
                   <TD class= titleImg> ������������ϸ��Ϣ </TD>
              </TR>
         </Table>
         <Div id= "divLLBnfForm" style= "display: ''">
              <TABLE class=common>
                   <tr class=common>
                        <td class= title> �������뱻���˹�ϵ </td>
                        <td class= input><Input class=codeno name="relationtoinsured" ondblclick="return showCodeList('relation',[this,relationtoinsuredName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtoinsuredName],[0,1]);"><input class=codename name=relationtoinsuredName readonly=true></TD>
                        <td class= title> ���������� </td>
                        <td class= input><Input class=common name="cName" onblur="setName();"><font size=1 color='#ff0000'><b>*</b></font></td>
                        <td class= title> �������Ա� </td>
                        <td class= input><Input class=codeno name="sex" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD>
                   </tr>
                   <tr class=common>
                        <td class= title> �����˳������� </td>
                        <td class= input><Input class="coolDatePicker" dateFormat="short" name="birthday"></td>
                        <td class= title> ������֤������ </td>
                        <td class= input><Input class=codeno name="idtype" ondblclick="return showCodeList('llidtype',[this,idtypeName],[0,1]);" onkeyup="return showCodeListKey('llidtype',[this,idtypeName],[0,1]);"><input class=codename name=idtypeName readonly=true></TD>
                        <td class= title> ������֤������ </td>
                        <td class= input><Input class=common name="idno"></td>
                   </tr>
              </TABLE>
         </div>
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayee);"></TD>
                   <TD class= titleImg> �����������ϸ��Ϣ </TD>
              </TR>
         </Table>
         <Div id= "divPayee" style= "display: ''">
              <TABLE class=common>
                   <tr class=common>
                        <td class= title> ������뱻���˹�ϵ </td>
                        <td class= input><Input class=codeno name="relationtopayee" ondblclick="return showCodeList('relation',[this,relationtopayeeName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtopayeeName],[0,1]);"><input class=codename name=relationtopayeeName readonly=true></TD>
                        <td class= title> ��������� </td>
                        <td class= input><Input class=common name="payeename"><font size=1 color='#ff0000'><b>*</b></font></td>
                        <td class= title> ������Ա� </td>
                        <td class= input><Input class=codeno name="payeesex" ondblclick="return showCodeList('sex',[this,payeesexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,payeesexName],[0,1]);"><input class=codename name=payeesexName readonly=true></TD>
                   </tr>
                   <tr class=common>
                        <td class= title> ����˳������� </td>
                        <td class= input><Input class="coolDatePicker" dateFormat="short" name="payeebirthday"></td>
                        <td class= title> �����֤������ </td>
                        <td class= input><Input class=codeno name="payeeidtype" ondblclick="return showCodeList('llidtype',[this,payeeidtypeName],[0,1]);" onkeyup="return showCodeListKey('llidtype',[this,payeeidtypeName],[0,1]);"><input class=codename name=payeeidtypeName readonly=true></TD>
                        <td class= title> �����֤������ </td>
                        <td class= input><Input class=common name="payeeidno"></td>
                   </tr>
              </TABLE>
        </div>
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayee);"></TD>
                   <TD class= titleImg> ������ϸ��Ϣ </TD>
              </TR>
         </Table>
         <Div id= "divPayee" style= "display: ''">
              <TABLE class=common>
                   <tr class=common>
                        <td class= title> ʣ�����(%)</td>
                        <td class= input><Input class=readonly readonly name="RemainLot"></td>
                        <td class= title> �������(%)</td>
                        <td class= input><Input class=common name="bnflot"></td>
                        <td class= title> ������</td>
                        <td class= input><Input class=readonly readonly name="getmoney"></td>
                   </tr>
                   <tr class=common>
                        <td class= title> ֧����ʽ</td>
                        <td class= input> <Input class=codeno name="CasePayMode" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name=CasePayModeName readonly=true></TD>
                        <td class= title> ���б���</td>
                        <td class= input> <Input class=codeno name="BankCode" ondblclick="return showCodeList('bank',[this,BankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCodeName],[0,1]);"><input class=codename name=BankCodeName readonly=true></TD>
                        <td class= title> �����ʺ�</td>
                        <td class= input> <Input class=common name="BankAccNo" ></TD>
                   </tr>
                   <tr class=common>
                        <td class= title> �����ʻ���</td>
                        <td class= input> <Input class=common name="AccName" ></TD>
                        <td class= title> </td>
                        <td class= input> </TD>
                        <td class= title> </td>
                        <td class= input> </TD>
                  </tr>
              </TABLE>
        </div>
        <br>
        <table>
            <tr>
                <td><Input class=cssButton name="addButton" value="��  ��" type=button onclick="addClick();"></td>
                <td><Input class=cssButton name="updateButton" value="��  ��" type=button onclick="updateClick();"></td>
                <td><Input class=cssButton name="deleteButton" value="ɾ  ��" type=button onclick="deleteClick();"></td>
                <td><Input class=cssButton name="saveButton" value="��  ��" type=button onclick="saveClick();"></td>
            </tr>
        </table>
    </div>
    <hr>
    <table>
        <tr>
            <td><Input class=cssButton name="goBack" value="��  ��" type=button onclick="top.close();"></td>
        </tr>
    </table>    
    <%
    //******************
    //�������ݵ����ر�
    //******************
    %>
    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    
    <Input type=hidden id="GrpContNo" name="GrpContNo"><!--�����ͬ����-->
    <Input type=hidden id="GrpPolNo" name="GrpPolNo"><!--���屣�����ֺ���-->
    <Input type=hidden id="ContNo" name="ContNo"><!--��ͬ����-->
    <Input type=hidden id="BnfKind" name="BnfKind"><!--��������BΪԤ�������˷���-->
    <Input type=hidden id="BatNo" name="BatNo"><!--���κ���-->
    
    <!--��ɾ������-->
    <Input type=hidden id="bnfno" name="bnfno"><!--���������-->
    <Input type=hidden id="insuredno" name="insuredno" value="0" ><!--�����˺���-->
    <Input type=hidden id="bnftype" name="bnftype" value="0" ><!--���������-->
    <Input type=hidden id="bnfgrade" name="bnfgrade" value="0" ><!--�����˼���-->
    <Input type=hidden id="customerNo" name="customerNo" value="0"><!--�����˺���-->
    <Input type=hidden id="payeeNo" name="payeeNo" value="0"><!--����˺���-->
    
    <span id="spanCode"  style="display: 'none'; position:absolute; slategray"></span>
</Form>
</body>
</html>
