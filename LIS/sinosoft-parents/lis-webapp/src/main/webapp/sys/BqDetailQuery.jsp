
<%
//�������ƣ�
//�����ܣ����˱�ȫ��ѯ
//�������ڣ�2005-06-22 15:15:22
//������  �� fanchao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>


<html>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./BqDetailQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="BqDetailQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./BqDetailQuery.jsp" method=post name=fm id=fm target="fraSubmit">
    <input type=hidden id="ContType2" name="ContType2">
    <table>
        <tr>
            <td class=" common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg> ��ȫ������Ϣ </td>
        </tr>
    </table>
    <Div id= "divEdorAppInfo" style= "display: ''" class="maxbox1">
            <TABLE class=common>
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo_Read id=EdorAcceptNo_Read></td>
                    <td class=title> �ͻ�/������ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=OtherNo_Read id=OtherNo_Read></td>
                    <td class=title > �������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=OtherNoType_Read id=OtherNoType_Read></td>
                </tr>
                <tr class=common>
                    
                    <td class=title> ����״̬ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=PEdorStateName_Read id=PEdorStateName_Read></td>
                    <td class=title> �����ʸ��� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAppName_Read id=EdorAppName_Read></td>
                    <td class=title> ���뷽ʽ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=AppType_Read id=AppType_Read></td>
                </tr>
                <tr class=common>
                    
                    <TD  class= title>ϵͳ��������</TD>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAppDate_Read id=EdorAppDate_Read></td>
                    <td class=title> �����ʸ�����ϵ�绰 </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=CustomerPhone_Read id=CustomerPhone_Read></td>
                    <td class=title> �������� </td>
                    <td class= input><input class= "readonly wid" readonly name=BankName id=BankName readonly=true></td>
                </tr> 
                <tr class=common>
                    
                    <td class=title> �����ʻ� </td>
                    <td class= input ><Input class= "readonly wid"  readonly name=BankAccNo id=BankAccNo type="text" ></td>
                    <td class=title> ��   �� </td>
                    <td class= input><Input class= "readonly wid"  readonly name="AccName" id="AccName"></td>
                    <td class=title> ���˷���ȡ�� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=PayGetName_Read id=PayGetName_Read></td>
                </tr>
                <tr class=common>
                    
                    <td class=title> ���֤�� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=PersonID_Read id=PersonID_Read></td>
                    <td class=title>  </td>
                    <td class= input> </td><td class=title>  </td>
                    <td class= input> </td>
                </tr>
            </TABLE>
   </Div>
   
   <!-- add by jiaqiangli 2009-01-04 ���Ӵ�������Ϣ read-->
        <Div id ="divBehalfAgentCodeInfoRead" style="display: 'none'">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ҵ��Ա���� </TD>
        <TD  class= input>
        <Input class="readonly" name="BfAgentCode_Read" readonly maxlength=10></TD>
        <TD  class= title> ����������� </TD>
        <TD  class= input><Input class="readonly" readonly  name=ManageCom_Read></TD>
        <td class=title>  </td>
        <td class= input> </td>
        </TR>
        </TABLE>
        </Div>
        <Div id ="divBehalfInfoRead" style="display: 'none'">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ���������� </TD>
        <TD  class= input>
        <Input class="readonly" name="BfName_Read" readonly maxlength=20></TD>
        <TD  class= title> ������֤������ </TD>
        <TD  class= input>
        <Input type="text" class="readonly" readonly name="BfIDType_Read"></TD>
        <TD  class= title> ������֤������ </TD>
        <TD  class= input><Input class="readonly" name=BfIDNo_Read readonly maxlength=20></TD>
        </TR>
        <TR class=common>
        <TD  class= title> ��������ϵ�绰 </TD>
        <TD  class= input><Input class="readonly" name=BfPhone_Read readonly maxlength=18></TD>
        </TR>
        </TABLE>
        </Div>
        <!-- add by jiaqiangli 2009-01-04 ���Ӵ�������Ϣ -->
        
        <Div id ="divInternalSwitchInfoRead" style="display: 'none'">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ת������ </TD>
        <TD  class= input><input type="text" class="readonly" name="InternalSwitchChnlName_Read">
        </TD>
        <td class=title></td>
        <td class= input> </td>
        <td class=title>  </td>
        <td class= input> </td>
        </TR>
        </TABLE>
        </Div>

    <Div  id= "divCustomer" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerInfo);">
                </td>
                <td class= titleImg>
                    �ͻ���ϸ��Ϣ
                </td>
            </tr>
        </table>
        <Div  id= "divCustomerInfo" style= "display: ''" class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> �ͻ����� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=CustomerNo id=CustomerNo ></TD>
              <TD  class= title> �ͻ����� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Name id=Name ></TD>
              <TD  class= title> �ͻ��Ա� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Sex id=Sex ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> �ͻ��������� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Birthday id=Birthday ></TD>
              <TD  class= title> ֤������ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=IDType id=IDType ></TD>
              <TD  class= title> ֤������ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=IDNo id=IDNo ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> ��λ���� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=GrpName id=GrpName ></TD>
              <TD  class= title> </TD>
              <TD  class= input> </TD>
              <TD  class= title> </TD>
              <TD  class= input> </TD>
            </TR>
          </table>
        </Div>
    </Div>

    <Div  id= "divCont" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContInfo);">
                </td>
                <td class= titleImg>
                    ������ϸ��Ϣ
                </td>
            </tr>
        </table>
        <Div  id= "divContInfo" style= "display: ''" class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> ������ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=ContNoApp id=ContNoApp ></TD>
              <TD  class= title> ��Ч���� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=CValiDate id=CValiDate ></TD>
              <TD  class= title>
              <div id = "divPayTitle" style= "display: ''">
               ���Ѷ�Ӧ��
               </div>
               </TD>
              <TD  class= input>
              <div id = "divPayToDate" style= "display: ''">
              <Input class= "readonly wid" readonly name=PaytoDate id=PaytoDate >
              </div>
              </TD>
            </TR>
            <TR>
              <TD  class= title> Ͷ���� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=AppntName id=AppntName ></TD>
              <TD  class= title> �������� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=InsuredName id=InsuredName ></TD>
              <TD  class= title> ҵ��Ա </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=AgentCode id=AgentCode ></TD>
            </TR>
            <TR  class= common  style= "display: 'none'">
              <TD  class= title> ���ѱ�׼ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Prem id=Prem ></TD>
              <TD  class= title> �������� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Amnt id=Amnt ></TD>
              <TD  class= title > ���� </TD>
              <TD  class= input ><Input class= "readonly wid" readonly name=Mult id=Mult ></TD>
            </TR>
          </table>
        </Div>
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContStateInfo);">
                </td>
                <td class= titleImg>
                    ����״̬��Ϣ
                </td>
            </tr>
        </table>
        <Div  id= "divContStateInfo" style= "display: ''" class=" maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> ��Ч״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Available id=Available ></TD>
              <TD  class= title> ��ֹ״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Terminate id=Terminate ></TD>
              <TD  class= title> ��ʧ״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Lost id=Lost ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> ����״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=PayPrem id=PayPrem ></TD>
              <TD  class= title> ����״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Loan id=Loan ></TD>
              <TD  class= title> ������Ѻ����״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=BankLoan id=BankLoan ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> �������״̬ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=RPU id=RPU ></TD>
              <TD  class= title>  </TD>
              <TD  class= input><Input class= "readonly wid" readonly name= ></TD>
              <TD  class= title>  </TD>
              <TD  class= input><Input class= "readonly wid" readonly name= ></TD>
            </TR>
          </table>
        </Div>
        <table>
            <tr>
                <td>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskGrid);">
                </td>
                <td class= titleImg> ������Ϣ </td>
            </tr>
        </table>
        <Div  id= "divRiskGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanRiskGrid" >
                        </span>
                    </td>
                </tr>
            </table>
        </div>

    </Div>
           <br>

    <Div  id= "divEdorItemInfo" style= "display: ''">
        <table>
            <tr>
                <td>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> ��ȫ��Ŀ��Ϣ </td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''" >
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanEdorItemGrid" >
                        </span>
                    </td>
                </tr>
            </table>
            <br>
                    <INPUT class=cssButton VALUE="��ȫ��ϸ��ѯ" TYPE=button onclick="QueryEdorClick();">
<!--                    <INPUT class=cssButton VALUE="�±�ȫ��ϸ��ѯ����" TYPE=button onclick="QueryEdorClickNew();">-->
                    <INPUT class=cssButton VALUE="��ȫӰ���ѯ" TYPE=button onclick="QueryPhoto();">
                    <INPUT class=cssButton VALUE="��ȫ������ѯ" TYPE=button onclick="QueryEdorRecipt();">
                    <INPUT VALUE="��ȫ�����켣" class=cssButton TYPE=button onclick="MissionQuery();">
                    <INPUT VALUE="  �˱���ѯ  " class=cssButton TYPE=button onclick="UWQuery();">

        </div>
    </DIV>

    <input type="hidden" name="fmtransact">
    <input type="hidden" name="ContType">
    <input type="hidden" name="EdorAcceptNo">
    <input type="hidden" name="OtherNo">
    <input type="hidden" name="OtherNoType">
    <input type="hidden" name="AppType">
    <input type="hidden" name="BankCode">
    <input type="hidden" name="addrFlag">
    <input type="hidden" name="EdorNo">
    <input type="hidden" name="EdorType">
    <input type="hidden" name="AddressNo">
    <input type="hidden" name="LoadFlag">
    <input type="hidden" name="currentDay">
    <input type="hidden" name="dayAfterCurrent">
    <input type="hidden" name="PEdorState">
    <input type="hidden" name="EdorItemState">
    <input type="hidden" name="GrpContNo">
    <input type="hidden" name="DisplayType">
    <input type="hidden" name="MainPolPaytoDate">
    <input type="hidden" name="ContNo">
    <input type="hidden" name="PolNo">
    <input type="hidden" name="DelFlag">
    <input type="hidden" name="MakeDate">
    <input type="hidden" name="MakeTime">
    <input type="hidden" name="MissionID">
    <input type="hidden" name="SubMissionID">
    <input type="hidden" name="ContNoBak">
    <Input type="hidden" name=CustomerNoBak >
    <input type="hidden" name="EdorValiDate">
    <input type="hidden" name="EdorItemAppDate">
    <input type="hidden" name="EdorAppDate">
    <input type="hidden" name="InsuredNo">
    <input type="hidden" name="AppObj" value="I">
    <input type="hidden" name="ButtonFlag" value="1">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
