<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�PDDiscountDefiInput.jsp
//�����ܣ���Ʒ�ۿ۶���
//�������ڣ�2011-03-03
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
%>

<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var tRiskCode = "<%=request.getParameter("riskcode")%>";
</script>

<head >
<title>��Ʒ�ۿ۶������</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="PDDiscountDefi.js"></SCRIPT>
  <%@include file="PDDiscountDefiInit.jsp"%>
  
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>

<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./PDDiscountDefiSave.jsp">
   <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    <td class=title5Img>�ѱ����ۿ��б�</td>
    </td>
    </table>   
    
   <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td style="text-align:left;" colSpan=1>
	 					<span id="spanPremDiscountGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            <INPUT CLASS=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
            <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"> 					
            <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
            <INPUT CLASS=cssButton VALUE="βҳ" TYPE=button onclick="turnPage1.lastPage();">
    </Div>
   <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    <td class=title5Img>�ۿ���ϸ</td>
    </td>
    </tr>
    </table> 
    
       <table class= common >
    	<tr class= common>
        <TD  class= title>�ۿ�����</TD>
        <TD  class= input>
            <Input class="codeno" name=DiscountType verify ="�ۿ�����|notnull" ondblclick="return showCodeList('discounttype',[this,DiscounTypeName],[0,1],null,'D_PR','code');" onkeyup="return showCodeListKey('discounttype',[this,DiscounTypeName],[0,1],null,'D_PR','code');"><input class=codename name=DiscounTypeName readonly="readonly">
        </TD> 
        <TD  class= title>�ۿۺ���</TD>
        <TD  class= input>
          <Input class=readonly  readonly="readonly"  name=DiscountCode  >
        </TD>
        
        <TD  class= title>�ӷ��ۿ۱��</TD>
       <TD  class= input>
          <Input class="codeno" name=AddFeeDiscFlag verify ="�ӷ��ۿ۱��|notnull" ondblclick="return showCodeList('yesno',[this,AddFeeDiscFlagName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,AddFeeDiscFlagName],[0,1]);"><input class=codename name=AddFeeDiscFlagName readonly="readonly">
        </TD>
    	</tr>
   
    <tr>
        <TD  class= title>���α���</TD>
        <TD  class= input>
						<Input class=codeno name=DutyCode ondblclick="return showCodeList('pd_dutycode',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'RiskCode',1);" onkeyup="return showCodeListKey('pd_dutycode',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'RiskCode',1);"><input class=codename name="DutyCodeName" readonly="readonly">
        </TD> 
        <TD  class= title>
��ʼ����
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="StartDate" >
        </TD>
        <TD  class= title>
��ֹ����
        </TD>
        <TD  class= input>
          <input class="multiDatePicker" dateFormat="short" name="EndDate" >
        </TD>
      </tr>
   
    <tr>  
        <TD  class= title>�㷨����</TD>
        <TD  class= input>
          <Input class=common  name=CalCode  readonly="readonly" >
        </TD>
        <TD  class= title>
        <INPUT CLASS=CssButton VALUE="�����㷨" TYPE=button onclick="defCalRule()">
      </TD>
    </tr>
    </table>

  <!-- INPUT CLASS=CssButton VALUE="��    ��" TYPE=button onclick="initAll()" -->
  <INPUT CLASS=CssButton VALUE="����" TYPE=button onclick="InsertClick()">
  <INPUT CLASS=CssButton VALUE="�޸�" TYPE=button onclick="update()">
  <INPUT CLASS=CssButton VALUE="ɾ��" TYPE=button onclick="del()">
  <!--INPUT CLASS=CssButton VALUE="�����㷨" TYPE=button onclick="defCalCode()"-->
  <!--INPUT CLASS=CssButton VALUE="��  ��" TYPE=button onclick="UpdateClick()"-->
  <input type=hidden name="mOperator">
  <input type=hidden name="RiskCode">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>