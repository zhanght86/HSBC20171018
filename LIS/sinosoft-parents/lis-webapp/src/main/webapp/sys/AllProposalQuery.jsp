
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String tDisplay = "";
    String tContNo="";
    try
    {
        tDisplay = request.getParameter("display");
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
   //loggerDebug("AllProposalQuery","�������-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>������ѯ</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <script language="JavaScript" src="../common/laydate/laydate.js"></script>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="AllProposalQuery.js"></script>
    <%@ include file="AllProposalQueryInit.jsp" %>
</head>
<body  onload="initForm()" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
  <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIdNo);">
            </td>
            <td class= titleImg>
               һ�ſ��ٲ�ѯ��
              </td>
         </tr>
    </table>
        <Div  id= "divIdNo" style= "display: ''"  class="maxbox1">
       <!-- <table class= common border=0 width=100%>
            <tr>
            <td class= titleImg align="left">
               ע�⣺"֤������"��������д�Ǽǹ���֤���ţ��������֤�����ա�����֤�����յȡ�
              </td>
         </tr>
        </table>-->
        <font color = red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע�⣺"֤������"��������д�Ǽǹ���֤���ţ��������֤�����ա�����֤�����յȡ�</font>
        <table  class= common>
        <TR  class= common>
          <TD  class= title>֤������</TD>
          <TD  class= input> <Input class="wid" class= common name=IdCardNo id=IdCardNo > </TD>
          <TD  class= title>�ͻ�����</TD>
          <TD  class= input> <Input class="wid" class= common name=CustomerNo id=CustomerNo > </TD>
          <TD  class= title>ӡˢ��</TD>
          <TD  class= input> <Input class="wid" class= common name=PrtNo id=PrtNo > </TD>
        </TR>
       </table> </div>
           <!-- <INPUT VALUE=" �� ѯ " class = cssButton TYPE=button onclick="quickQueryClick();">

          <INPUT VALUE=" �� �� " name=ReturnBack class = cssButton TYPE=button STYLE="display:none" onclick="returnParent()">-->
          <a href="javascript:void(0);" class="button" onClick="quickQueryClick();">��    ѯ</a>
          <a href="javascript:void(0);" name=ReturnBack class="button" STYLE="display:none" onClick="returnParent();">��    ��</a>
      
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFq);">
            </td>
            <td class= titleImg>
                ����������ѯ��
              </td>
        </tr>
    </table>

    <Div  id= "divFq" style= "display: ''" class="maxbox1">
 <!-- <table>
        <tr>
            <td class= titleImg align= center>ע�⣺�������������º���֮һ���������롢Ͷ�������롢����Э����š����޷��ṩ����������������������������Ϊ��ѯ������</td>
        </tr>
    </table>-->
    <font color = red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע�⣺�������������º���֮һ���������롢Ͷ�������롢����Э����š����޷��ṩ����������������������������Ϊ��ѯ������</font>
    <table  class= common>
        <TR  class= common>
          <TD  class= title>���պ�ͬ�� </TD>
          <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo></TD>
          <TD  class= title> ���屣������</TD>
          <TD  class= input> <Input class="wid" class= common name=GrpContNo id=GrpContNo></TD>
          <TD  class= title> ӡˢ���� </TD>
          <TD  class= input> <Input class="wid" class= common name=ProposalContNo id=ProposalContNo></TD>
        </TR>
        <TR  class= common>

          <TD  class= title> �����˱���</TD>
            <TD  class= input> <input class="wid" NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" ondblclick="return queryAgent();" onclick="return queryAgent();" > </TD>
					<Input class=common type=hidden name=AgentGroup verify="���������|notnull&len<=12" > </TD>
          <TD  class= title> ������� </TD>
          <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName" readonly></TD>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
        </TR>

        <TR  class= common>
          <TD  class= title> Ͷ�������� </TD>
          <TD  class= input> <Input class="wid" class= common name=AppntName id=AppntName > </TD>
          <TD  class= title> ������Ч�� </TD>
          <TD  class= input><Input class="wid" class= "multiDatePicker" dateFormat="short" name=CValiDate id=CValiDate></TD>
          <TD  class= title> ���ڽ��ѷ�ʽ </TD>
          <TD  class="input"> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="PayLocation" id="PayLocation"  ondblClick="showCodeList('PayLocation',[this,PayLocationName],[0,1]);" onClick="showCodeList('PayLocation',[this,PayLocationName],[0,1]);" onkeyup="showCodeListKey('PayLocation',[this,PayLocationName],[0,1]);" ><input class="codename" name="PayLocationName" id="PayLocationName" readonly></TD>
        </TR>

        <TR  class= common>
          <TD  class= title> �����˿ͻ��� </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredNo id=InsuredNo > </TD>
          <TD  class= title> ���������� </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredName id=InsuredName> </TD>
          <TD  class= title> �ͻ�֤������ </TD>
          <TD  class= input> <input class="wid" class="common" name="AppntIDNo" id="AppntIDNo" ></TD>
     </TR>
    <TR  class= common>
          <TD class= title>�����վݺ�/����Э�����</TD>
          <TD class= input> <Input class="wid" class= common name=tempfeeno id=tempfeeno >
                    <td class="title">���ִ���</td>
                    <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="RiskCode" id="RiskCode" verify="���ִ���|Code:IYRiskCode" ondblclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onkeyup="showCodeListKey('IYRiskCode',[this,RiskCodeName],[0,1])"><input type="text" class="codename" name="RiskCodeName" id="RiskCodeName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
        </TR>
    </table></div>
          <!--<INPUT VALUE=" �� ѯ " class = cssButton TYPE=button onclick="easyQueryClick();">

          <INPUT VALUE=" �� �� " name=Return class=cssButton TYPE=button STYLE="display:none" onclick="returnParent()">-->
    		<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
            <a href="javascript:void(0);" name=Return class="button" STYLE="display:none" onClick="returnParent();">��    ��</a>

   <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomer1);">
            </td>
            <td class= titleImg>
                 �ͻ���Ϣ
            </td>
        </tr>
    </table>
     <Div id= "divCustomer1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCustomerGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPageCustomerGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageCustomerGrid.lastPage()">
            </div>
    </div>
    
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
            </td>
            <td class= titleImg>
                 ������Ϣ
            </td>
        </tr>
    </table>
    <Div id= "divLCPol1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPagePolGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePolGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePolGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePolGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePolGrid.lastPage()">
            </div>
    </div>
    <p>
     <!--<INPUT VALUE=" ������ϸ " class = cssButton TYPE=button onclick="PolClick();">-->
     <a href="javascript:void(0);" class="button" onClick="PolClick();">������ϸ</a>
     </p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
