<html>
<%
//�������ƣ�GEdorAppInput.jsp
//�����ܣ��ŵ���ȫ����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<head >
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tUserCode  = tG.Operator;
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====BGN===================================
    String tEdorAcceptNo    = request.getParameter("EdorAcceptNo"); //��ȫ�����
    String tMissionID       = request.getParameter("MissionID");    //����ID
    String tSubMissionID    = request.getParameter("SubMissionID"); //������ID
    String tLoadFlag        = request.getParameter("LoadFlag");     //ҳ������־
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====END===================================

%>
<script>	
	var operator = "<%=tG.Operator%>";   //��¼����Ա
	var manageCom = "<%=tG.ManageCom%>"; //��¼��½����
	var comcode = "<%=tG.ComCode%>"; //��¼��½����
</script> 
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./GEdorAppInput.js"></SCRIPT>
  <SCRIPT src="PEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="GEdorAppInputInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./GEdorAppSave.jsp" method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg> ��ȫ������Ϣ </td>
        </tr>
    </table>
    <div  id= "divEdorAppInfo" style= "display: ''">
        <div  id= "divApplySaveWrite" style= "display: 'none'">
            <table class=common>
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                    <td class=title>  </td>
                    <td class= input> </td>
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
                <tr class=common>
                    <td class=title> ���屣���� </td>
                    <td class= input><Input class="wid" class="common"  onkeydown="QueryOnKeyDown();" name=OtherNo id=OtherNo></td>
                    <td class=title > �������� </td>
                    <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('gedornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('gedornotype',[this, OtherNoName], [0, 1]);" onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName id=OtherNoName readonly></td>
                    <td class=title> ����״̬ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=PEdorStateName id=PEdorStateName></td>
                </tr>
                <tr class=common>
                    <td class=title> ���������� </td>
                    <td class= input><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>
                    <td class=title> ���뷽ʽ </td>
                    <td class= input ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly></td>
                    <TD  class= title> �������� </TD>
                    <td class= input><!--<Input type="input" class="readonly" readonly name=EdorAppDate>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EdorAppDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorAppDate id="EdorAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </td>
                </tr>
                <!--���˷���ȡ��/���֤��ԭ�ڴ��У���Ų���ո��ѷ�ʽ����-->
            </table>
        </div>
        <div  id= "divApplySaveRead" style= "display: 'none'" class="maxbox1">
            <table class=common>
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAcceptNo_Read id=EdorAcceptNo_Read></td>
                    <td class=title>  </td>
                    <td class= input> </td>
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
                <tr class=common>
                    <td class=title> ���屣���� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=OtherNo_Read id=OtherNo_Read></td>
                    <td class=title > �������� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=OtherNoType_Read id=OtherNoType_Read></td>
                    <td class=title> ����״̬ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=PEdorStateName_Read id=PEdorStateName_Read></td>
                </tr>
                <tr class=common>
                    <td class=title> ���������� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAppName_Read id=EdorAppName_Read></td>
                    <td class=title> ���뷽ʽ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=AppType_Read id=AppType_Read></td>
                    <TD  class= title> �������� </TD>
                    <td class= input><!--<Input type="input" class="readonly" readonly name=EdorAppDate_Read>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EdorAppDate_Read'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorAppDate_Read id="EdorAppDate_Read"><span class="icon"><a onClick="laydate({elem: '#EdorAppDate_Read'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </td>
                </tr>

            </table>
        </div>

        <div  id= "divApplySaveBT" style= "display: 'none'">
            <input class=cssButton VALUE=" ȷ  �� "    TYPE=button onclick="applySave();">
            <input class=cssButton VALUE=" ��  �� "    TYPE=button onclick="returnParent();">
            <input class=cssButton VALUE="�ͻ���ѯ"  TYPE=hidden onclick="customerQuery();">
            <input class=cssButton VALUE="������ѯ"  TYPE=hidden onclick="contQuery();">
        </div>
   </div>

    <div  id= "divCustomer" style= "display: 'none'">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerInfo);">
                </td>
                <td class= titleImg>
                    ����ͻ���ϸ��Ϣ
                </td>
            </tr>
        </table>
        <div  id= "divCustomerInfo" style= "display: ''" class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> ����ͻ��� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=CustomerNo id=CustomerNo ></TD>
              <TD  class= title> ��λ���� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=GrpName2 id=GrpName2 ></TD>
              <TD  class= title> ��ҵ���� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=BusinessType id=BusinessType ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> ������ </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=Peoples id=Peoples ></TD>
              <TD  class= title> �ʲ��ܶ� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=Asset id=Asset ></TD>
              <TD  class= title>  </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name= ></TD>
            </TR>

          </table>
        </div>
    </div>

    <div  id= "divCont" style= "display: 'none'">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContInfo);">
                </td>
                <td class= titleImg>
                    �ŵ���ϸ��Ϣ
                </td>
            </tr>
        </table>
        <div  id= "divContInfo" style= "display: ''" class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> ���屣���� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=ContNoApp id=ContNoApp ></TD>
              <TD  class= title> Ͷ����λ </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=GrpName id=GrpName ></TD>
              <TD  class= title> ��Ч���� </TD>
              <TD  class= input><!--<Input class= "readonly" readonly name=CValiDate >-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#CValiDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>
            </TR>
            <TR>
              <TD  class= title> Ͷ������ </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=Peoples2 id=Peoples2 ></TD>
              <TD  class= title> ��ϵ�� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=LinkMan1 id=LinkMan1 ></TD>
              <TD  class= title> ҵ��Ա </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=AgentCode id=AgentCode ></TD>
            </TR>
            <TR style= "display: 'none'">
              <TD  class= title> �ܱ��� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=Prem id=Prem ></TD>
              <TD  class= title> �ܱ��� </TD>
              <TD  class= input><Input class="wid" class= "readonly" readonly name=Amnt id=Amnt ></TD>
              <TD  class= title></TD>
              <TD  class= input></TD>
            </TR>
          </table>
        </div>
        <div  id= "divContDetailButton" style= "display: ''" >
            <Input type =button class=cssButton value="������ϸ��ѯ" onclick="showContDetail();">
            <!--input type =button class=cssButton value="�����嵥��ѯ" onclick="showNameList();"-->
        </DIV>
    </div>

    <div  id= "divEdorItemInfo" style= "display: 'none'">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> ��ȫ��Ŀ��Ϣ </td>
            </tr>
        </table>

        <div  id= "divEdorItemGrid" style= "display: ''" class="maxbox1">

            <table  class= common>
                <tr  class= common>
                    <TD  class= title> �������� </TD>
                    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name=EdorType id=EdorType verify="��������|notnull&code:EdorCode" ondblclick="initEdorType(this,EdorTypeName)" onMouseDown="initEdorType(this,EdorTypeName)" onkeyup="actionKeyUp(this,EdorTypeName)"><input class=codename name=EdorTypeName id=EdorTypeName  readonly></TD>
                    <TD  class= title> �����㷨 </TD>
                    <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno"  readonly=ture name="EdorTypeCal" id="EdorTypeCal"  ondblclick="getEdorCalCodeData();return showCodeListEx('GetAddressNo',[this,EdorTypeCalName],[0,1],'', '', '', true);" onMouseDown="getEdorCalCodeData();return showCodeListEx('GetAddressNo',[this,EdorTypeCalName],[0,1],'', '', '', true);" onkeyup="getEdorCalCodeData();return showCodeListKeyEx('GetAddressNo',[this,EdorTypeCalName],[0,1],'', '', '', true);"><input class=codename name=EdorTypeCalName id=EdorTypeCalName readonly=true></td>
                    <TD  class= title> ������������ </TD>
                    <TD  class= input><!--<Input class= "multiDatePicker" dateFormat="short" name=EdorItemAppDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EdorItemAppDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorItemAppDate id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    
                </tr>
                <tr  class= common>
                    <TD  class= title> ����ԭ�� </TD>
                    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" value = 1 name=AppReason id=AppReason ondblClick="showCodeList('edorappreason',[this,AppReasonName],[0,1],null,null,null,0,207)" onMouseDown="showCodeList('edorappreason',[this,AppReasonName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('edorappreason',[this,AppReasonName],[0,1],null,null,null,0,207)"><input class=codename name=AppReasonName id=AppReasonName readonly value = "�ͻ�����"></TD>
                    <TD  class= title> ��Ч���� </TD>
                    <TD  class= input><!--<Input class= "readonly" readonly name=EdorValiDate>-->
                     <Input class="coolDatePicker" onClick="laydate({elem: '#EdorValiDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorValiDate id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ����Ա </TD>
                    <TD  class= input><Input class="wid" class= "readonly" readonly name=Operator id=Operator></TD>
                </tr>
            </table>
            </div>
            <div  id= "divPolInfo" style= "display: 'none'">
                <table>
                    <tr>
                        <td class="common">
                            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
                        </td>
                        <td class= titleImg> ����������Ϣ </td>
                    </tr>
                </table>
                <div  id= "divPolGrid" style= "display: ''" >
                    <table  class= common>
                        <tr  class= common>
                            <td text-align: left colSpan=1>
                                <span id="spanPolGrid" >
                                </span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div  id= "divInsuredInfo" style= "display: 'none'">
                <table>
                    <tr>
                        <td class="common">
                         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuredGrid);">
                        </td>
                        <td class= titleImg> �ͻ���Ϣ </td>
                    </tr>
                </table>
                <div  id= "divInsuredGrid" style= "display: ''">
                    <table  class= common>
                        <tr  class= common>
                            <td text-align: left colSpan=1>
                                <span id="spanInsuredGrid" >
                                </span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <input type =button class=cssButton value="��ӱ�ȫ��Ŀ" onclick="addEdorItem();">
            <br>

            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanEdorItemGrid" >
                        </span>
                    </td>
                </tr>
            </table>

            <div  id= "divAddDelButton" style= "display: ''" >
                <Input type =button class=cssButton value="��ȫ��Ŀ��ϸ" onclick="gotoDetail();">
                <input type =button class=cssButton value="��ȫ��Ŀ����" onclick="delEdorItem();">
                <input type =button class=cssButton value="�鿴���úϼ�" onclick="MoneyDetail();"> 
                <Input type =Button class=cssButton value="���˻�����¼��" onclick="toManyAcc()">
            </DIV>
           
        </div>
    </DIV>

    <br>
        <Div  id= "divGetPayFormInfo" style= "display: 'none'" >
            <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divGetPayForm);">
                    </td>
                    <td class= titleImg> �ո��ѷ�ʽ��Ϣ </td>
                </tr>
            </table>
            <Div  id= "divGetPayForm" style= "display: ''" class="maxbox1">
              <TABLE class=common>
                <tr class=common>
                    <td class=title> �ո��ѷ�ʽ </td>
                    <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=GetPayForm id=GetPayForm ondblclick="showCodeList('edorgetpayform',[this,GetPayFormName],[0,1],null,null,null,null,'207');" onMouseDown="showCodeList('edorgetpayform',[this,GetPayFormName],[0,1],null,null,null,null,'207');" onkeyup="showCodeListKey('edorgetpayform', [this,GetPayFormName],[0,1],null,null,null,null,'207');" onFocus="initGetPayForm();"><input class="codename" name=GetPayFormName id=GetPayFormName readonly></TD>
                    <td class=title>  </td>
                    <td class= input> </td>
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
              </table>
              <Div  id= "divBankInfo" style= "display: 'none'">
                <TABLE class=common>
                    <tr class=common>
                        <td class="title">��������</td>
                        <td class="input"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="BankCode" id="BankCode" ondblclick="showCodeList('bank',[this,BankName],[0,1],null,null,null,0,317)" onMouseDown="showCodeList('bank',[this,BankName],[0,1],null,null,null,0,317)" onkeyup="showCodeListKey('bank',[this,BankName],[0,1],null,null,null,0,317)"><input type="text" class="codename" name="BankName" id="BankName" readonly></td>
                        <td class="title">�����˻�</td>
                        <td class="input"><input class="wid" type="text" class="coolConfirmBox" name="BankAccNo" id="BankAccNo"></td>
                        <td class="title">�˻���</td>
                        <td class="input"><Input class="wid" type="text" class="common" name="AccName" id="AccName"></td>
                    </tr>
                </table>
              </Div>
              <TABLE class=common>
                <tr class=common>
                    <td class=title>���˷���ȡ��</td>
                    <td class= input><Input class="wid" type="input" class="common" name=PayGetName id=PayGetName></td>
                    <td class=title>���֤��</td>
                    <td class= input><Input class="wid" type="input" class="common" name=PersonID id=PersonID></td>
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
              </table>
            </Div>

            	

        </Div>
        <br>

    <div id = "divappconfirm" style = "display:none" >
        <Input class=cssButton type=Button value="����ȷ��" onclick="edorAppConfirm()">
        <input class=cssButton type=Button value=" ��  �� " onclick="returnParent();">
    </div>

    <div id = "divApproveModify" style = "display:none" >
        <Input class=cssButton type=Button value="�޸�ȷ��" onclick="ApproveModifyConfirm()">
        <input value="�˱���ѯ" class="cssButton" type="button" onclick="UWQuery()">
        <input value="������ѯ" class="cssButton" type="button" onclick="ApproveQuery()">
        <input class=cssButton type=Button value=" ��  �� " onclick="returnParent();">
    </div>
    
    <!--  add by jiaqiangli 2009-03-26  �ŵ���ȫ���� -->
    <div id = "divEdorTest" style = "display:none" >
        <Input class=cssButton type=Button value=" ������� " onclick="EdorTestFinish()">
        <INPUT class=cssButton type=Button value=" ��  �� " onclick="returnParent();">
        <b><font color = red >(�����������������)</font> </b>
    </div>

    <input type="hidden" name="LoadFlag" id=LoadFlag>
    <input type="hidden" name="currentDay" id=currentDay>
    <input type="hidden" name="dayAfterCurrent" id=dayAfterCurrent>
    <input type="hidden" name="fmtransact" id=fmtransact>
    <input type="hidden" name="Transact" id=Transact>
    <input type="hidden" name="ContType" id=ContType>
    <input type="hidden" name="PEdorState" id=PEdorState>
    <input type="hidden" name="EdorState" id=EdorState>
    <input type="hidden" name="EdorItemState" id=EdorItemState>
    <input type="hidden" name="GrpContNo" id=GrpContNo>
    <input type="hidden" name="PrtNo" id=PrtNo>
    <input type="hidden" name="DisplayType" id=DisplayType>
    <input type="hidden" name="InsuredNo" id=InsuredNo>
    <input type="hidden" name="ContNo" id=ContNo>
    <input type="hidden" name="PolNo" id=PolNo>
    <input type="hidden" name="EdorNo" id=EdorNo>
    <input type="hidden" name="DelFlag" id=DelFlag>
    <input type="hidden" name="MakeDate" id=MakeDate>
    <input type="hidden" name="MakeTime" id=MakeTime>
    <input type="hidden" name="MissionID" id=MissionID>
    <input type="hidden" name="SubMissionID" id=SubMissionID>
    <input type="hidden" name="CustomerNoBak" id=CustomerNoBak>
    <input type="hidden" name="ContNoBak" id=ContNoBak>
    <input type="hidden" name="UserCode" id=UserCode>
    <input type="hidden" name="ButtonFlag" id=ButtonFlag>
    <input type="hidden" name="AppObj" value="G" id=AppObj>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
