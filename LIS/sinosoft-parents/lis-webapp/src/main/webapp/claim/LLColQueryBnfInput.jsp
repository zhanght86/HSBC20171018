<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
//=============================================================BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	  String tBnfKind = request.getParameter("BnfKind"); //��������
	  String tInsuredNo = request.getParameter("InsuredNo"); //�������˿ͻ���
//=============================================================END
%>
    <title>�����˷����ѯ</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLBnf.js"></script>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLBnfInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
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
                <TD style="text-align: left" colSpan=1><span id="spanLLBalanceGrid" ></span> </TD>
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
    <Div  id= "divLLBalanceForm" style= "display: none">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBalanceForm);"></TD>
            <TD class= titleImg> �ⰸ������ϸ��Ϣ </TD>
        </TR>
    </Table>
	    <div class=maxbox1>
         <TABLE class=common>
             <tr class=common>
                 <td class=title> ������ </td>
                 <td class= input><Input class="readonly wid" readonly name="ClmNo2" id=ClmNo2></td>
                 <td class=title> ������ </td>
                 <td class= input><Input class="readonly wid" readonly name="polNo" id=polNo></td>
                 <td class=title> �⸶��� </td>
                 <td class= input><Input class="readonly wid" readonly name="sumPay" id=sumPay></td>
             </tr>
         </TABLE>
		</div>
    </div>
<!--�����������˻�-->
    <Div  id= "divLLBalance" style= "display: """>
        <hr class=line>
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfGrid);"></TD>
                <TD class= titleImg> ������������Ϣ </TD>
            </TR>
         </Table>
         <Div  id= "divLLBnfGrid"  style= "display: ''">
             <Table class= common>
                 <TR class= common>
                     <TD style="text-align: left" colSpan=1><span id="spanLLBnfGrid" ></span> </TD>
                 </TR>
             </Table>
             
         </Div>
         <Div id= "divButtonBnf" style= "display: ''">
             <Table>
                  <TR>
                       <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfForm);"></TD>
                       <TD class= titleImg> ������������ϸ��Ϣ </TD>
                  </TR>
             </Table>
             <Div id= "divLLBnfForm" class=maxbox style= "display: ''">
                  <TABLE class=common>
                       <tr class=common>
                            <td class= title> �������뱻���˹�ϵ </td>
                            <td class= input><Input class=codeno name="relationtoinsured" id=relationtoinsured style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('relation',[this,relationtoinsuredName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtoinsuredName],[0,1]);"><input class=codename name=relationtoinsuredName id=relationtoinsuredName readonly=true></TD>
                            <td class= title> ���������� </td>
                            <td class= input><Input class="common wid" name="cName" id=cName ><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> �������Ա� </td>
                            <td class= input><Input class=codeno name="sex" id=sex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName id=SexName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                       </tr>
                       <tr class=common>
                            <td class= title> �����˳������� </td>
                            <td class= input><Input class="coolDatePicker" dateFormat="short" name="birthday" onblur=" CheckDate(fm.birthday); " onClick="laydate({elem: '#birthday'});" id="birthday"><span class="icon"><a onClick="laydate({elem: '#birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> ������֤������ </td>
                            <td class= input><Input class=codeno name="idtype" id=idtype style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,idtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');" onkeyup="return showCodeListKey('llidtype',[this,idtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');"><input class=codename name=idtypeName id=idtypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                            <td class= title> ������֤������ </td>
                            <td class= input><Input class="common wid" name="idno" id=idno elementtype=nacessary verify="������֤������|NOTNULL&LEN<=20"  verifyorder="1" onblur="checkidtype();getBirthdaySexByIDNo(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></td>
                       </tr>
                  </TABLE>
             </div>
             <Table>
                  <TR>
                       <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBrawer);"></TD>
                       <TD class= titleImg> �����������ϸ��Ϣ </TD>
                  </TR>
             </Table>
             <Div id= "divBrawer" class=maxbox1 style= "display: ''">
                  <TABLE class=common>
                       <tr class=common>
                            <td class= title> ������뱻���˹�ϵ </td>
                            <td class= input><Input class=codeno name="relationtopayee" id=relationtopayee style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('relation',[this,relationtopayeeName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtopayeeName],[0,1]);"><input class=codename name=relationtopayeeName id=relationtopayeeName readonly=true></TD>
                            <td class= title> ��������� </td>
                            <td class= input><Input class="common wid" name="payeename" id=payeename><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> ������Ա� </td>
                            <td class= input><Input class=codeno name="payeesex" id=payeesex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,payeesexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,payeesexName],[0,1]);"><input class=codename name=payeesexName id=payeesexName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                       </tr>
                       <tr class=common>
                            <td class= title> ����˳������� </td>
                            <td class= input><Input class="coolDatePicker" dateFormat="short" name="payeebirthday" onblur=" CheckDate(fm.payeebirthday); " onClick="laydate({elem: '#payeebirthday'});" id="payeebirthday"><span class="icon"><a onClick="laydate({elem: '#payeebirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> �����֤������ </td>
                            <td class= input><Input class=codeno name="payeeidtype" id=payeeidtype style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,payeeidtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');" onkeyup="return showCodeListKey('llidtype',[this,payeeidtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');"><input class=codename name=payeeidtypeName id=payeeidtypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                            <td class= title> �����֤������ </td>
                            <td class= input><Input class="common wid" name="payeeidno" id=payeeidno elementtype=nacessary verify="�����֤������|NOTNULL&LEN<=20" verifyorder="1" onblur="checkidtype1();getBirthdaySexByIDNo1(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></td>
                       </tr>
                  </TABLE>
            </div>
             <Table>
                  <TR>
                       <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayee);"></TD>
                       <TD class= titleImg> ������ϸ��Ϣ </TD>
                  </TR>
             </Table>
             <Div id= "divPayee" class=maxbox1 style= "display: ''">
                  <TABLE class=common>
                       <tr class=common>
                            <td class= title> ʣ�����(%)</td>
                            <td class= input><Input class="readonly wid" readonly name="RemainLot" id=RemainLot></td>
                            <td class= title> �������(%)</td>
                            <td class= input><Input class="common wid" name="bnflot" id=bnflot></td>
                            <td class= title> ������</td>
                            <td class= input><Input class="readonly wid" readonly name="getmoney" id=getmoney></td>
                       </tr>
                       <tr class=common>
                            <td class= title> ֧����ʽ</td>
                            <td class= input> <Input class=codeno name="CasePayMode" id=CasePayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name=CasePayModeName id=CasePayModeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                            <td class= title> ���б���</td>
                            <td class= input> <Input class=codeno name="BankCode" id=BankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,BankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCodeName],[0,1]);"><input class=codename name=BankCodeName id=BankCodeName readonly=true></TD>
                            <td class= title> �����ʺ�</td>
                            <!--<td class= input> <Input class=common name="BankAccNo" ></TD>-->
                            <td class= input><Input class="common wid" name="BankAccNo" id=BankAccNo  onblur="confirmSecondInput(this,'onblur');" >
                       </tr>
                       <tr class=common>
                            <td class= title> �����ʻ���</td>
                            <td class= input> <Input class="common wid" name="AccName" id=AccName ></TD>
                            <td class= title> </td>
                            <td class= input> </TD>
                            <td class= title> </td>
                            <td class= input> </TD>
                      </tr>
                  </TABLE>
            </div>
            <table>
                <tr>
                    <td><Input class=cssButton name="addButton" id=addButton value="��  ��" type=hidden onclick="addClick();"></td>
                    <td><Input class=cssButton name="updateButton" id=updateButton value="��  ��" type=hidden onclick="updateClick();"></td>
                    <td><Input class=cssButton name="deleteButton" id=deleteButton value="ɾ  ��" type=hidden onclick="deleteClick();"></td>
                    <td><Input class=cssButton name="saveButton" id=saveButton value="��  ��" type=hidden onclick="saveClick();"></td>
					<td><Input class=cssButton name="saveButton" id=saveButton value="ȫ������" type=hidden onclick="repealClick();"></td>
            	</tr>
            </table>
        </div>
        <!--ת�괦�������-->
        <Div id= "divButtonToPension" style= "display: none">
            <hr class=line>
             <Table>
                  <TR>
                       <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPensionForm);"></TD>
                       <TD class= titleImg> �����ת��� </TD>
                  </TR>
             </Table>
             <Div id= "divPensionForm" class=maxbox1 style= "display: ''">
                <table>
                    <TR class= common>
                        <TD class= title> ת�������</TD>
                        <TD class= input> <Input class=codeno name=PensionType id=PensionType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llclmtopension',[this,PensionTypeName],[0,1],null,fm.polNo.value,'polno','1');" onkeyup="return showCodeListKey('lldiseases2',[this,PensionTypeName],[0,1],null,fm.polNo.value,'polno','1');"><input class=codename name=PensionTypeName id=PensionTypeName readonly=true></TD>
                        <TD class= title> </TD>
                        <TD class= input> </TD>
                        <TD class= title> </TD>
                        <TD class= input> </TD>
                    </TR>
                </table>
                <table>
                    <tr>
                        <td><Input class=cssButton disabled name="clmToPension" id=clmToPension value="ת���ȷ��" type=button onclick = "saveClmToPension()"></td>
                        <td><Input class=cssButton name="adjustBnfButton" id=adjustBnfButton value="�����˵���" type=button onclick = "toAdjustBnf();"></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <hr class=line>
    <table>
        <tr>
            <td><Input class=cssButton name="goBack" id=goBack value="��  ��" type=button onclick="top.close();"></td>
        </tr>
    </table>    
    <%
    //******************
    //�������ݵ����ر�
    //******************
    %>
    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="ClmFlag" name="ClmFlag"><!--������ȡ��ʽ���(1һ��ͳһ���� 2 �����ʽ��ȡ 3 ����֧��),��ѯ��ʼ��-->
    
    <Input type=hidden id="GrpContNo" name="GrpContNo"><!--�����ͬ����-->
    <Input type=hidden id="GrpPolNo" name="GrpPolNo"><!--���屣�����ֺ���-->
    <Input type=hidden id="ContNo" name="ContNo"><!--��ͬ����-->
    <Input type=hidden id="PolNo" name="PolNo"><!--�������ֺź���-->
    <Input type=hidden id="BnfKind" name="BnfKind"><!--��������BΪԤ�������˷���-->
    <Input type=hidden id="FeeOperationType" name="FeeOperationType"><!--������Ŀ����-->
    <Input type=hidden id="BatNo" name="BatNo"><!--���κ���-->
    <Input class=hidden id="bnfCurrency" name="bnfCurrency" >
     <Input class=hidden id="Currency" name="Currency" >
    <!--��ɾ������-->
    <Input type=hidden id="bnfno" name="bnfno"><!--���������-->
    <Input type=hidden id="insuredno" name="insuredno" ><!--�����˺���-->
	<Input type=hidden id="insurednobak" name="insurednobak" ><!--�����˺��루��������������Ŀͻ��ţ�--> 
    <Input type=hidden id="bnftype" name="bnftype" value="0" ><!--���������-->
    <Input type=hidden id="bnfgrade" name="bnfgrade" value="0" ><!--�����˼���-->
    <Input type=hidden id="customerNo" name="customerNo" value="0"><!--�����˺���-->
    <Input type=hidden id="payeeNo" name="payeeNo" value="0"><!--����˺���-->
	<Input type=hidden id="oriBnfNo" name="oriBnfNo" value=""><!--ԭ���������-->  
	<Input type=hidden id="oriBnfPay" name="oriBnfPay" value=""><!--ԭ�����˽��-->  
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>
