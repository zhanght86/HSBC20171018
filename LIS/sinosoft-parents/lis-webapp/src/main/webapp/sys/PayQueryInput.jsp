
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String tDisplay = "";
    String tContNo="";
    String tCustomerNo ="";
    try
    {
       tCustomerNo = request.getParameter("CustomerNo");
       tDisplay = request.getParameter("display");
       //loggerDebug("PayQueryInput",tDisplay+"--------------------");
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
   //loggerDebug("PayQueryInput","�������-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
  var customerNo = "<%=tCustomerNo%>"; //�ͻ���
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�ͻ������շ���Ϣ��ѯ</title>
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
    <script language="JavaScript" src="PayQuery.js"></script>
    <%@ include file="PayQueryInit.jsp" %>
</head>
<body  onload="initForm()" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- �ͻ�������Ϣ���� -->

  <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIdNo);">
            </td>
            <td class= titleImg align=left>
               �ͻ������շ���Ϣ��ѯ��
              </td>
         </tr>
    </table>
        <Div  id= "divIdNo" style= "display: ''" align="left" class=maxbox1>
        <table class= common border=0 width=100%>
            <tr>

         </tr>
        <table  class= common>
        <TR  class= common>
        <TD  class=title5>�շ�ҵ������</TD>
          <TD  class=input5> <Input class= codeno name=TempFeeType id=TempFeeType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData= "0|^6|����Լ����^4|����Լ����^7|����Լ����^2|���ڽ���(����-����)^3|���ڽ���(����-����)^10|��ȫ����^02|Ԥ�����ڱ���(����)^9|�����շ�^5|�ͻ�Ԥ��" ondblClick="showCodeListEx('TempFeeType',[this,TempFeeTypeName],[0,1]);" onkeyup="showCodeListKeyEx('TempFeeType',[this,TempFeeTypeName],[0,1]);"><input class="codename" name="TempFeeTypeName" id=TempFeeTypeName readonly="readonly">
           <TD  class=title5>�շѷ�ʽ</TD>
         <TD  class=input5> <Input class= codeno name=PayMode id=PayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData= "0|^0|��ϵͳ���ݲ�¼^1|�ֽ�^2|�ֽ��Ϳ^3|֧Ʊ^4|����ת�ʣ����Ʒ���)^5|�ڲ�ת��^6|POS�տ�^7|���д��ۣ��Ʒ���)^8|����ҵ��^9|�����տ�^A|����ͨ^C|���ڳ���" ondblClick="showCodeListEx('PayMode',[this,PayModeName],[0,1]);" onkeyup="showCodeListKeyEx('PayMode',[this,PayModeName],[0,1]);"><input class="codename" name="PayModeName" id=PayModeName readonly="readonly wid">      	
        	</TD>
			 </TR>
			 <TR  class= common>
          <TD  class=title5>�շ������˺�</TD>
         <TD  class=input5><Input class= "common wid" name=InBankAccNo id=InBankAccNo > </TD>
          <TD  class=title5>���ѽ��</TD>
          <TD  class=input5> <Input class= "common wid" name=PayMoney id=PayMoney > </TD>
          
                        <!--td class="title">&nbsp;</td-->
                        <!--td class="input">&nbsp;</td-->
        </TR>
       </table>
            <INPUT VALUE=" �� ѯ " class =cssButton TYPE=button onclick="initQuery();">

         
       </div>
  <!-- Add By QianLy --------------->


   <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomer1);">
            </td>
            <td class= titleImg>
                 �ͻ������շ���Ϣ
            </td>
        </tr>
    </table>
     <Div id= "divCustomer1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPayGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPagePayGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePayGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePayGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePayGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePayGrid.lastPage()">
            </div>
    </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
