<html>
<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: �������
 ****************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeLN.js"></SCRIPT>
  <%@include file="PEdorTypeLNInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form action="./PEdorTypeLNSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title >��ȫ�����</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title >��������</TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
      <TD class = title >������</TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
      <TR class=common>
        <TD class =title>������������</TD>
        <TD class = input>
            <input class = "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD class =title>��Ч����</TD>
        <TD class = input>
            <input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD class =title></TD>
        <TD class = input>
            <input class = "readonly wid" readonly name=>
        </TD>
    </TR>

  </TABLE>
  </div>
  <table>
    <tr>
      <td class="common">
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerGrid);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
    </tr>
   </table>
  <Div id="divCustomerGrid" style="display:''">
        <table class= common>
            <tr class= common>
                <td><span id="spanCustomerGrid"></span></td>
            </tr>
        </table>
   </div>

    <table>
   <tr>
      <td class="common">
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick= "showPage(this,divLoan);">
      </td>
      <td class= titleImg>
        �����Ϣ
      </td>
   </tr>
   </table>

   <Div id="divLoan" style="display:''">
    <table class="common">
            <tr class=common>
            <td><span id="spanLoanGrid"></span></td>
            </tr>
    </table>
    <!--<table align='center'>
      <INPUT VALUE="��ҳ" TYPE=button class=cssButton onclick="getFirstPage();">
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton onclick="getPreviousPage();">
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton onclick="getNextPage();">
      <INPUT VALUE="βҳ" TYPE=button class=cssButton onclick="getLastPage();">
    </table>-->
  </div>
  <table>
   <tr>
      <td class=common>
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divLoan1);">
      </td>
      <td class= titleImg>
        ��������Ϣ
      </td>
   </tr>
   </table>
   <Div  id= "divLoan1" class=maxbox1 style= "display: ''">
  <table  class= common>
       <TR>
       	  <TD class = title>
             ������
          </TD>
          <TD class= input>
            <Input class="multiCurrency wid" name="MaxLoan" id=MaxLoan verify="������|notnull&num&value>0">
          </TD>
          <TD class = title>
             ��������
          </TD>
          <TD class= input>
            <Input class="multiCurrency wid" name="LoanRate" id=LoanRate readonly verify="��������|notnull&num&value>0" moneyprec="0.0000">
          </TD>
          <TD class = title>
             ����
          </TD>
<TD class= input>
            <Input class= "readonly wid" name="Currency" id=Currency readonly>
 </TD>

       </TR>

    </table>

  </Div>
  <br>
  <Div id= "divEdorquery" style="display: ''">
         <Input class=cssButton  type=Button value=" �� �� " onclick="saveEdorTypeLN()">
         <Input class=cssButton  type=Button value=" �� �� " onclick="returnParent()">
         <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
</Div>
     <input type=hidden id="fmtransact" name="fmtransact">
     <input type=hidden id="ContType" name="ContType">
     <input type=hidden id="PolNo" name="PolNo">
     <input type=hidden id="EdorNo" name="EdorNo">
     <input type=hidden id="InsuredNo" name="InsuredNo">
     <input type=hidden id="RevenueRate" name="RevenueRate">
     <input type=hidden id="Interest" name="Interest">
     <input type=hidden id="SaveMaxLoan" name="SaveMaxLoan"> 
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
    <br /><br /><br /><br />
  </form>
  <span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
