<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//�������ƣ�
//�����ܣ����˱�ȫ����
//�������ڣ�2005-04-26 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    String tUserCode  = tG.Operator;
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====BGN===================================
    String tEdorAcceptNo    = request.getParameter("EdorAcceptNo"); //��ȫ�����
    String tMissionID       = request.getParameter("MissionID");    //����ID
    String tSubMissionID    = request.getParameter("SubMissionID"); //������ID
    String tLoadFlag        = request.getParameter("LoadFlag");     //ҳ������־
    String tOtherNo         = request.getParameter("OtherNo");     //���˴��뱣����
    String tActivityID      = request.getParameter("ActivityID"); 
//    tActivityID=tActivityID.replace(" ", ""); 
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====END===================================
%>


<html>
<head >
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./PEdorAppInput.js"></SCRIPT>
  <SCRIPT src="PEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  
  <script src="../common/javascript/jquery.imageView.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>
	<script src="../common/javascript/Signature.js"></script>
  <%
		if(request.getParameter("ScanFlag")!=null&&request.getParameter("ScanFlag").equals("1")){
	%>
		<script language="javascript">
			var prtNo=<%=request.getParameter("EdorAcceptNo")%>;
		</script>
		<script src="../app/ProposalAutoMoveNew.js"></script>
  	<script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  	<script src="../common/javascript/ScanPicView.js"></script>
  	<script language="javascript">window.document.onkeydown = document_onkeydown;</script>
	<%
		}
	%>
  <%@include file="PEdorAppInputInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorAppSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg> ��ȫ������Ϣ </td>
        </tr>
    </table>
    <Div  id= "divEdorAppInfo" style= "display: ">
        <Div  id= "divApplySaveWrite" class="maxbox" style= "display: none">
            <TABLE class=common>
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                    <td class=title> ������ </td>
                    <td class= input><Input class="wid" class="common"  onkeydown="QueryOnKeyDown();" name=OtherNo id=OtherNo></td>
                    <td class=title > �������� </td>
                    <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno"  name=OtherNoType id=OtherNoType value=3 verify="��������|NotNull&Code:EdorNoType" onDblClick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onKeyUp="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onKeyDown="QueryOnKeyDown();" ><input class="codename" class=readonly name=OtherNoName id=OtherNoName readonly=true value = "���˱�����"></td>
                </tr>
                <tr class=common>
                    <td class=title> ����״̬ </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=PEdorStateName id=PEdorStateName></td>
                    <!-- <TD  class= title type=hidden> �������� </TD> -->
                    <!--<td class= input>--><Input  type="hidden" class="readonly wid" readonly name=EdorAppDate id=EdorAppDate><!--</td>-->
                     <td class=title> ���뷽ʽ </td>
                    <td class= input ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType value=1 verify="���뷽ʽ|NotNull&Code:EdorAppType" onDblClick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onKeyUp="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly=true value = "�ͻ��װ�"></td>
                    <td class=title> �����ʸ��� </td>
                    <td class= input>
					<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="EdorApp" id="EdorApp" CodeData="" 
					onDblClick="getEdorAppList(); return showCodeListEx('nothis',[this,EdorAppName],[0,1],null,null,null,1);" onMouseDown="getEdorAppList(); return showCodeListEx('nothis',[this,EdorAppName],[0,1],null,null,null,1);" 
					onKeyUp="return showCodeListKeyEx('nothis',[this,EdorAppName],[0,1],null,null,null,1)">
                    <input class="codename" name="EdorAppName" id="EdorAppName"></td>
                </tr></TABLE>
                <TABLE class=common>
                <tr class=common>
                   
                    <TD  class= title>�����ʸ�����ϵ�绰</TD>
                    <TD  class= input>
                    <Input class="common wid" name="CustomerPhone" id="CustomerPhone" maxlength=15></TD>
                    <TD  class= title><font color = green>(�̻���010-66578858)</font></TD>
                    <TD  class= input></TD>
                   <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr></TABLE>
                <TABLE class=common>
                <tr class=common>
                	<td class=title> �����ʸ����ֻ� </td>
                    <td class= input ><Input class="wid" class=common name=Mobile id=Mobile maxlength=11></td>
                    <td class=title> �����ʸ�����ϵ��ַ </td>
                    <td class= input><input class="wid" class=common name=PostalAddress id=PostalAddress></td>
                    <TD  class= title>�ʱ�</TD>
                    <TD  class= input><Input class="wid" class=common name=ZipCode id=ZipCode></TD>
                </tr>
             <!--���˷���ȡ��/���֤��ԭ�ڴ��У���Ų���ո��ѷ�ʽ����-->
            </TABLE>
        </Div>
        
        <Div id ="divInternalSwitchInfo" style="display: none">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ת������ </TD>
        <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="InternalSwitchChnl" id="InternalSwitchChnl" CodeData="" onDblClick="return showCodeListEx('InternalSwitchChnl',[this,InternalSwitchChnlName],[0,1],null,null,null,1);" onMouseDown="return showCodeListEx('InternalSwitchChnl',[this,InternalSwitchChnlName],[0,1],null,null,null,1);" onKeyUp="return showCodeListKeyEx('InternalSwitchChnl',[this,InternalSwitchChnlName],[0,1],null,null,null,1)"><input type="text" class="codename" name="InternalSwitchChnlName" id="InternalSwitchChnlName">
        </TD>
        <td class= title ><font color = red>ת������Ϊ����ʱ¼����ϸ��Ϣ</font></td>
        <td class= input> </td>
        <td class=title>  </td>
        <td class= input> </td>
        </TR>
        </TABLE>
        </Div>
        
        <!-- add by jiaqiangli 2009-01-04 ���Ӵ�������Ϣ -->
        <Div id ="divBehalfAgentCodeInfo" style="display: none">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ҵ��Ա���� </TD>
        <TD  class= input>
        <Input class="common wid" name="BfAgentCode" id="BfAgentCode" onchange = "return QueryAgent();" maxlength=10></TD>
        <TD  class= title><font color = red>����ҵ��Ա�����Զ�����<!--ҵ��Ա-->������Ϣ </font></td>
        <td class= input> </td>
        <TD  class= title> ����������� </TD>
        <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center;" class="codeno" name=ManageCom id=ManageCom readonly maxlength=8 onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><Input class="codename" name=ManageComName id=ManageComName readonly maxlength=60></TD>
        </TR>
        </TABLE>
        </Div>
        <Div id ="divBehalfInfo" style="display: none">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ���������� </TD>
        <TD  class= input>
        <Input class="common wid" name="BfName" id="BfName" maxlength=20></TD>
        <TD  class= title> ������֤������ </TD>
        <TD  class= input>
        <Input type="text" class="codeno" readonly name="BfIDType" id="BfIDType"><input type="text" class="codename" name="BfIDTypeName" name="BfIDTypeName" readonly=true ></TD>
        <TD  class= title> ������֤������ </TD>
        <TD  class= input><Input class="common wid" name=BfIDNo id=BfIDNo maxlength=20></TD>
        </TR>
        <TR class=common>
        <TD  class= title> ��������ϵ�绰 </TD>
        <TD  class= input><Input class="common wid" name=BfPhone id=BfPhone maxlength=18></TD>
        <td class= title ><font color = red>(�̻���010-66578858)</font></td>
        <td class= input> </td>
        <td class=title>  </td>
        <td class= input> </td>
        </TR>
        </TABLE>
        </Div>
        <!-- add by jiaqiangli 2009-01-04 ���Ӵ�������Ϣ -->
        
        <Div  id= "divApplySaveRead" style= "display: none">
            <TABLE class=common>
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo_Read id=EdorAcceptNo_Read></td>
					<td class=title> ������ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=OtherNo_Read id=OtherNo_Read></td>
                    <td class=title > �������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=OtherNoType_Read id=OtherNoType_Read></td>
                </tr>
                <tr class=common>
                    
                    <td class=title> ����״̬ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=PEdorStateName_Read id=PEdorStateName_Read></td>
                    <td class=title> ���뷽ʽ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=AppType_Read id=AppType_Read></td>
                    <td class=title> �����ʸ��� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAppName_Read id=EdorAppName_Read></td>
                </tr>
                <tr class=common>
                    
                    <TD  class= title> �����ʸ�����ϵ�绰 </TD>
                    <td class= input><Input type="input" class="readonly wid" readonly name=CustomerPhone_Read id=CustomerPhone_Read></td>
                    <td class=title> �����ʸ����ֻ� </td>
                    <td class=input ><Input readonly  class="readonly wid" name=Mobile_Read id=Mobile_Read></td>
                    <td class=title> �����ʸ�����ϵ��ַ </td>
                    <td class=input><input readonly  class="readonly wid" name=PostalAddress_Read id=PostalAddress_Read></td>
                </tr>
                <tr class=common>
                    <!-- <TD  class= title > �������� </TD> -->
                    
                    <TD class=title>�ʱ�</TD>
                    <TD class=input><Input  readonly  class="readonly wid" name=ZipCode_Read></TD>
                    <Input type="hidden" class="readonly" readonly name=EdorAppDate_Read>
                    <td class=title></td>
                    <td class= input> </td>
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
                <!--deleted by liurx 051217
                <tr class=common>
                    <td class=title> ���˷���ȡ�� </td>
                    <td class= input><Input type="input" class="readonly" readonly name=PayGetName_Read></td>
                    <td class=title> ���֤�� </td>
                    <td class= input><Input type="input" class="readonly" readonly name=PersonID_Read></td>
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
                -->
            </TABLE>
        </Div>
        
        <!-- add by jiaqiangli 2009-01-04 ���Ӵ�������Ϣ read-->
        <Div id ="divBehalfAgentCodeInfoRead" style="display: none">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ҵ��Ա���� </TD>
        <TD  class= input>
        <Input class="readonly wid" name="BfAgentCode_Read" id="BfAgentCode_Read" readonly maxlength=10></TD>
        <TD  class= title> ����������� </TD>
        <TD  class= input><Input class="readonly wid" readonly  name=ManageCom_Read id=ManageCom_Read></TD>
        <td class=title>  </td>
        <td class= input> </td>
        
        </TR>
        </TABLE>
        </Div>
        <Div id ="divBehalfInfoRead" style="display: none">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ���������� </TD>
        <TD  class= input>
        <Input class="readonly wid" name="BfName_Read" id="BfName_Read" readonly maxlength=20></TD>
        <TD  class= title> ������֤������ </TD>
        <TD  class= input>
        <Input type="text" class="readonly wid" readonly name="BfIDType_Read" id="BfIDType_Read"></TD>
        <TD  class= title> ������֤������ </TD>
        <TD  class= input><Input class="readonly wid" name=BfIDNo_Read id=BfIDNo_Read readonly maxlength=20></TD>
        </TR>
        <TR class=common>
        <TD  class= title> ��������ϵ�绰 </TD>
        <TD  class= input><Input class="readonly wid" name=BfPhone_Read id=BfPhone_Read readonly maxlength=18></TD>
        <td class=title></td>
        <td class= input> </td>
        <td class=title>  </td>
        <td class= input> </td>
        </TR>
        </TABLE>
        </Div>
        <!-- add by jiaqiangli 2009-01-04 ���Ӵ�������Ϣ -->
		
		<Div id ="divInternalSwitchInfoRead" style="display: none">
        <TABLE class=common>
        <TR class=common>
        <TD  class= title> ת������ </TD>
        <TD  class= input><input type="text" class="readonly wid" name="InternalSwitchChnlName_Read" id="InternalSwitchChnlName_Read">
        </TD>
        <td class=title></td>
        <td class= input> </td>
        <td class=title>  </td>
        <td class= input> </td>
        </TR>
        </TABLE>
        </Div></Div>

        <Div  id= "divApplySaveBT" style= "display: none">
            <INPUT class=cssButton VALUE=" ȷ  �� "    TYPE=button onclick="applySave();">
            <INPUT class=cssButton VALUE=" ��  �� "    TYPE=button onclick="returnParent();">
           <!-- <INPUT class=cssButton VALUE="�ͻ���ѯ"  TYPE=button onclick="customerQuery();">-->
            <INPUT class=cssButton VALUE="������ѯ"  TYPE=button onclick="contQuery();"><br>
        </Div>
   

    <Div  id= "divCustomer" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divCustomerInfo);">
                </td>
                <td class= titleImg>
                    �ͻ���Ϣ
                </td>
            </tr>
        </table>
        <Div  id= "divCustomerInfo" style= "display: " class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> �ͻ����� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=CustomerNo id=CustomerNo ></TD>
              <TD  class= title> �ͻ����� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Name id=Name ></TD>
              <TD  class= title> �ͻ��Ա� </TD>
              <TD  class= input>
              <Input class="codeno" readonly name=Sex ><input class=codename name=SexName readonly=true >
              </TD>
            </TR>
            <TR  class= common>
              <TD  class= title> �ͻ��������� </TD>
              <TD  class= input><!--<Input class="multiDatePicker" readonly name=Birthday >-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>
              <TD  class= title> ֤������ </TD>
               <TD  class= input>
              <Input class="codeno" readonly name="IDType" ><input class=codename name=IDTypeName readonly=true > </TD>
              <TD  class= title> ֤������ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=IDNo id=IDNo ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> ��λ���� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=GrpName ></TD>
              <TD  class= title>  </TD>
              <TD  class= input><Input class= "readonly wid" readonly name= ></TD>
              <TD  class= title>  </TD>
              <TD  class= input><Input class= "readonly wid" readonly name= ></TD>
            </TR>

          </table>
        </Div>
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divCustomerContGrid);">
                </td>
                <td class= titleImg> �ͻ�����������Ϣ </td>
            </tr>
        </table>
        <Div  id= "divCustomerContGrid" style="display:">
            <table class=common>
                <tr class=common>
                    <td><span id="spanCustomerContGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageCustomerContGrid" align="center" style="display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onClick="turnPageCustomerContGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onClick="turnPageCustomerContGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onClick="turnPageCustomerContGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onClick="turnPageCustomerContGrid.lastPage()">
            </div>
            <div class="maxbox">
          <table  class= common>
            <TR  class= common>
              <TD  class= title>ʧЧ״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Available_C id=Available_C></TD>
              <TD  class= title>��ֹ״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Terminate_C id=Terminate_C></TD>
              <TD  class= title>��ʧ״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Lost_C id=Lost_C></TD>
            </TR>
            <TR  class= common>
              <TD  class= title>�Ե�״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=PayPrem_C id=PayPrem_C></TD>
              <TD  class= title>����״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Loan_C id=Loan_C></TD>
              <TD  class= title>��Ѻ���д���״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=BankLoan_C id=BankLoan_C></TD>
            </TR>
            <TR  class= common>
              <TD  class= title>�������״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=RPU_C id=RPU_C></TD>
              <td class="title">�����ʧ״̬</td>
              <td class="input"><input type="text" class="readonly wid" name="Password_C" id="Password_C" readonly></td>
            </TR>
          </table></div>
          <!--<input type="button" class="cssButton" value=" ������ϸ " onClick="viewPolDetail()">-->
          <!--<a href="javascript:void(0);" class="button" onClick="viewPolDetail();">������ϸ</a>-->
        </div>
    </Div>

    <Div  id= "divCont" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divContInfo);">
                </td>
                <td class= titleImg>
                    ������Ϣ
                </td>
            </tr>
        </table>
        <Div  id= "divContInfo" style= "display: " class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title> ������ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=ContNoApp id=ContNoApp ></TD>
              <TD  class= title> ��Ч���� </TD>
              <TD  class= input><!--<Input class="multiDatePicker" readonly name=CValiDate >-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#CValiDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>
              <TD  class= title>  </TD>
              
            </TR>
            <TR>
              <TD  class= title> Ͷ���� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=AppntName id=AppntName ></TD>
              <TD  class= title> �������� </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=InsuredName id=InsuredName ></TD>
              <TD  class= title> ҵ��Ա </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=AgentCode id=AgentCode ></TD>
            </TR>
            <TR  class= common style= "display: none">
              <TD  class= title> ���ѱ�׼ </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Prem id=Prem  ></TD>
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
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divContStateInfo);">
                </td>
                <td class= titleImg>
                    ����״̬��Ϣ
                </td>
            </tr>
        </table>
        <Div  id= "divContStateInfo" style= "display: " class="maxbox1">
          <table  class= common>
            <TR  class= common>
              <TD  class= title>ʧЧ״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Available id=Available></TD>
              <TD  class= title>��ֹ״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Terminate id=Terminate></TD>
              <TD  class= title>��ʧ״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Lost id=Lost></TD>
            </TR>
            <TR  class= common>
              <TD  class= title>�Ե�״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=PayPrem id=PayPrem></TD>
              <TD  class= title>����״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Loan id=Loan></TD>
              <TD  class= title>��Ѻ���д���״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=BankLoan id=BankLoan></TD>
            </TR>
            <TR  class= common>
              <TD  class= title>�������״̬</TD>
              <TD  class= input><Input class= "readonly wid" readonly name=RPU id=RPU></TD>
              <td class="title">�����ʧ״̬</td>
              <td class="input"><input type="text" class="readonly wid" name="Password" id="Password" readonly></td><!--
              <TD  class= title>  </TD>
              <TD  class= input><Input class= "readonly wid" readonly name= ></TD>
            --></TR>
          </table>
        </Div>
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divRiskGrid);">
                </td>
                <td class= titleImg> ������Ϣ </td>
            </tr>
        </table>
        <Div  id= "divRiskGrid" style= "display: ">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanRiskGrid"></span></td>
                </tr>
            </table></div></Div>
            <!--<input type="button" class="cssButton" value=" ������ϸ " onClick="viewPolDetail()">--><br>
            <!--<a href="javascript:void(0);" class="button" onClick="viewPolDetail();">������ϸ</a>-->
    

     <Div  id= "divContState" style= "display: none">

    </Div>
    <Div  id= "divEdorItemInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> ��ȫ��Ŀ��Ϣ </td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" class="maxbox1" style= "display: ">

            <table  class= common>
                <tr  class= common>
                    <TD  class= title> �������� </TD>
                    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name=EdorType id=EdorType verify="��������|notnull&code:EdorCode" onDblClick="initEdorType(this,EdorTypeName);" onMouseDown="initEdorType(this,EdorTypeName);" onKeyUp="actionKeyUp(this,EdorTypeName);"><input class=codename name=EdorTypeName id=EdorTypeName readonly=true></TD>
                    <TD  class= title> ������������ </TD>
                    <TD  class= input><!--<Input class="multiDatePicker" dateFormat="short" name=EdorItemAppDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EdorItemAppDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorItemAppDate id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ����Ա </TD>
                    <TD  class= input><Input class= "readonly wid" readonly name=Operator id=Operator></TD>
                </tr>
                <tr  class= common>
                    <TD  class= title> ����ԭ�� </TD>
                    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name=AppReason id=AppReason value="1" ondblClick="showCodeList('edorappreason',[this,AppReasonName],[0,1],null,null,null,0,207);" onMouseDown="showCodeList('edorappreason',[this,AppReasonName],[0,1],null,null,null,0,207);" onKeyUp="showCodeListKey('edorappreason',[this,AppReasonName],[0,1],null,null,null,0,207);"><input class=codename name=AppReasonName id=AppReasonName readonly=true value = "�ͻ�����" ></TD>
                    <TD  class= title> ��Ч���� </TD>
                    <TD  class= input><!--<Input class="multiDatePicker" readonly name=EdorValiDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EdorValiDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorValiDate id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <td class=title>  ϵͳ��������</td>
                    <td class= input> <!--<Input class="multiDatePicker" readonly name=EdorMakeDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EdorMakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EdorMakeDate id="EdorMakeDate"><span class="icon"><a onClick="laydate({elem: '#EdorMakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD></td>
                </tr>
            </table>
            <Div  id= "divPolInfo" style= "display: none">
                <table>
                    <tr>
                        <td class="common">
                            <IMG src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divPolGrid);">
                        </td>
                        <td class= titleImg>����ѡ��</td>
                    </tr>
                </table>

                <div id="divPolGrid" style="display:">
                    <table class="common">
                        <tr class="common">
                            <td><span id="spanPolGrid"></span></td>
                        </tr>
                    </table>
                </div>
            </Div>

            <Div  id= "divInsuredInfo" style= "display: none">
                <table>
                    <tr>
                        <td class="common">
                         <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divInsuredGrid);">
                        </td>
                        <td class= titleImg> �ͻ�ѡ�� </td>
                    </tr>
                </table>
                <Div  id= "divInsuredGrid" style= "display: ">
                    <table  class= common>
                        <tr  class= common>
                            <td><span id="spanInsuredGrid"></span>
                            </td>
                        </tr>
                    </table>
                </div>
            </Div>

           <input type =button class=cssButton name = "AddItem" value="��ӱ�ȫ��Ŀ" onclick="addEdorItem();"><br>
            
            <br><br>
            <br>

            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>

            <Div  id= "divAddDelButton" style= "display: " >
                <Input type =button class=cssButton value="��ȫ��Ŀ��ϸ" onclick="gotoDetail();">
                <input type =button class=cssButton value="��ȫ��Ŀ����" onclick="delEdorItem();">
                <input id="sqButton6" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="OtherNo"><br>

            </DIV>
        </div>
    </DIV>
    <br>
        <Div  id= "divGetPayFormInfo" style= "display: none">
            <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style="cursor:hand" OnClick= "showPage(this,divGetPayForm);">
                    </td>
                    <td class= titleImg> �ո��ѷ�ʽ��Ϣ </td>
                </tr>
            </table>
            <Div  id= "divGetPayForm" style= "display: " class="maxbox1">
              <TABLE class=common>
                <tr class=common>
                    <td class=title> �ո��ѷ�ʽ </td>
                    <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=GetPayForm id=GetPayForm onDblClick="showCodeList('edorgetpayform',[this,GetPayFormName],[0,1],null,null,null,null,'230');" onClick="showCodeList('edorgetpayform',[this,GetPayFormName],[0,1],null,null,null,null,'230');" onKeyUp="showCodeListKey('edorgetpayform', [this,GetPayFormName],[0,1],null,null,null,null,'230')" onFocus="initGetPayForm();" readonly><input class="codename" name=GetPayFormName id=GetPayFormName readonly=true ></TD>
                <td class="title">��������</td>
                        <td class="input"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="BankCode"  id="BankCode" onDblClick="showCodeListEx('bank1',[this,BankName],[0,1],null,null,null,1,317)"  onclick="showCodeListEx('bank1',[this,BankName],[0,1],null,null,null,1,317)" onKeyUp="showCodeListKeyEx('bank1',[this,BankName],[0,1],null,null,null,1,317)" onBlur="checkBank(BankCode,BankAccNo);"><input type="text" class="codename" name="BankName" id="BankName" readonly></td>
                        <td class="title">�����˻�</td>
                        <td class="input"><input type="text" class="coolConfirmBox wid" name="BankAccNo" id="BankAccNo" onBlur="checkBank(BankCode,BankAccNo);"></td>    
                </tr>
             </table>
              <Div  id= "divBankInfo" style= "display: none">
                <TABLE class=common>
                    <tr class=common>
                        
                        <td class="title">��    ��</td>
                        <td class="input"><Input type="text" class="common wid" name="AccName" id="AccName"></td>
                        <td class=title> ���˷���ȡ�� </td>
                    <td class= input><Input type="input" class="common wid" name=PayGetName id=PayGetName></td>
                    <td class=title> ���֤�� </td>
                    <td class= input><Input type="input" class="common wid" name=PersonID id=PersonID></td>
                    </tr>
                </table>
              </Div>
             <!-- <TABLE class=common>
                <tr class=common>
                    
                    <td class=title>  </td>
                    <td class= input> </td>
                </tr>
              </table>-->
            </Div>
        </Div>
<div id = "divApproveTrack" style = "display:none">
	   <table>
    	<tr>
        <td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divApproveTrackGrid);">
    		</td>
    		<td class= titleImg>
    			 ������¼
    		</td>
    	</tr>
    </table>
    <Div  id= "divApproveTrackGrid" style= "display: " align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanApproveTrackGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
    		<tr>
    			<td>
			      <INPUT class=cssButton VALUE="��  ҳ" TYPE=button onClick="turnPageApprove.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onClick="turnPageApprove.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onClick="turnPageApprove.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="β  ҳ" TYPE=button onClick="turnPageApprove.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  		<div id = "divAprIdea" style = "display:none">
	      <table>
	        <TR>	        	
	         	<TD class= titleImg>�������</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="ApproveContent" cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
  		</div>
  		
</div>
</div>
    <div id = "divappconfirm" style = "display:none" >
    	<input name="IsFull" type="checkbox" style="display:none"  checked ><b><!--���������Ƿ���ȫ</b>-->
    	<br>
        <Input class=cssButton type=Button value=" ����ȷ�� " onClick="edorAppConfirm()">
        <Input class=cssButton type=hidden value=" ��ȫ�����·� " onClick="EdorNoticeSend()">
        <INPUT class=cssButton type=Button value=" ��  �� " onClick="returnParent();">
    </div>

    <div id = "divApproveModify" style = "display:none" >
        <Input class=cssButton type=Button value=" �޸�ȷ�� " onClick="ApproveModifyConfirm()">
        <INPUT VALUE="  ���������ѯ  " class=cssButton TYPE=button onClick="UWQuery();">
        <INPUT class=cssButton type=Button value=" ��  �� " onClick="returnParent();">
    </div>

    <div id = "divEdorTest" style = "display:none" >

        <Input class=cssButton type=Button value=" ������� " onClick="EdorTestFinish()">
        <INPUT class=cssButton type=Button value=" ��  �� " onClick="returnParent();">
        <b><font color = red >(�����������������)</font> </b>
    <!--
        <INPUT class=cssButton type=Button value=" ��  �� " onclick="EdorTestFinish();">
    -->
    </div>
    <br>
  <div id = "divEdorPersonInfo" style = "display:none" >
  	<table>
       <tr>
           <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
           <td class= titleImg> �����ʸ�����ϵ��Ϣ���� </td>
       </tr>
   </table>
   <Div  id= "divPayPlan1" style= "display: " class="maxbox1">
    <table class=common>
     <tr class=common>
        <td class=title> �ֻ� </td>
        <td class= input ><Input class="wid" class=common name=Mobile_Mod id=Mobile_Mod maxlength=11></td>
        <td class=title> ��ϵ��ַ </td>
        <td class= input><input class="wid" class=common name=PostalAddress_Mod id=PostalAddress_Mod></td>
        <TD  class= title>�ʱ�</TD>
        <TD  class= input><Input class="wid" class=common name=ZipCode_Mod id=ZipCode_Mod maxlength=6></TD>
     </tr>
   </table></div>
  <Input class=cssButton type=Button value=" �޸������ʸ�����ϵ��Ϣ " onclick="ModEdorPersonInfo()">
   
</div>
    <input type="hidden" name="LoadFlag" id="LoadFlag">
    <input type="hidden" name="currentDay" id="currentDay">
    <input type="hidden" name="dayAfterCurrent" id="dayAfterCurrent">
    <input type="hidden" name="fmtransact" id="fmtransact">
    <input type="hidden" name="Transact" id="Transact">
    <input type="hidden" name="ContType" id="ContType">
    <input type="hidden" name="PEdorState" id="PEdorState">
    <input type="hidden" name="EdorState" id="EdorState">
    <input type="hidden" name="EdorItemState" id="EdorItemState">
    <input type="hidden" name="GrpContNo" id="GrpContNo">
    <input type="hidden" name="DisplayType" id="DisplayType">
    <input type="hidden" name="InsuredNo" id="InsuredNo">
    <input type="hidden" name="ContNo" id="ContNo">
    <input type="hidden" name="PolNo" id="PolNo">
    <input type="hidden" name="EdorNo" id="EdorNo">
    <input type="hidden" name="DelFlag" id="DelFlag">
    <input type="hidden" name="MakeDate" id="MakeDate">
    <input type="hidden" name="MakeTime" id="MakeTime">
    <input type="hidden" name="MainPolPaytoDate" id="MainPolPaytoDate">
    <input type="hidden" name="MissionID" id="MissionID">
    <input type="hidden" name="SubMissionID" id="SubMissionID">
    <input type="hidden" name="ActivityID" id="ActivityID">
    <input type="hidden" name="CustomerNoBak" id="CustomerNoBak">
    <input type="hidden" name="ContNoBak" id="ContNoBak">
    <input type="hidden" name="UserCode" id="UserCode">
    <input type="hidden" name="AppObj" id="AppObj" value="I">
    <input type="hidden" name="ButtonFlag" id="ButtonFlag" value="0">
    <input type="hidden" name="CMEdorNo" id="CMEdorNo" >
    <input type="hidden" name="CMCustomerNo" id="CMCustomerNo" >
    <input type="hidden" name="CMCustomerName" id="CMCustomerName" >

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
