
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
       //loggerDebug("PersonQueryInput",tDisplay+"--------------------");
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
   //loggerDebug("PersonQueryInput","�������-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�ͻ���Ϣ��ѯ</title>
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
    <script language="JavaScript" src="PersonQuery.js"></script>
    <%@ include file="PersonQueryInit.jsp" %>
</head>
<body  onload="initForm()" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- �ͻ�������Ϣ���� -->

  <!-- Add By QianLy --------------->
  <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIdNo);">
            </td>
            <td class= titleImg align=left>
               �ͻ�������Ϣ��ѯ��
              </td>
         </tr>
    </table>
        <Div  id= "divIdNo" style= "display: ''" align="left">
           <div class="maxbox1" >
        <table class= common border=0 width=100%>
            <tr>
            <td class= titleImg1 align="left">
               ע�⣺"֤������"��������д�Ǽǹ���֤���ţ��������֤�����ա�����֤�����յȡ�
              </td>
         </tr>
        </table>
        <table  class= common>
        <TR  class= common>
        <TD  class= title5>�ͻ�����</TD>
          <TD  class= input5> <Input class= "common wid" name=Name elementtype=nacessary> </TD>
           <TD  class= title5>�Ա�</TD>
         <TD  class= input5> <Input name=Sex class="codeno" MAXLENGTH=1 
         style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('Sex',[this,SexName],[0,1]);" 
         onDblClick="return showCodeList('Sex',[this,SexName],[0,1]);" 
         onKeyUp="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input name=SexName  class=codename readonly=true>      	
        	</TD>
             </TR>
             <tr class= common>
          <TD  class= title5>��������</TD>
         <TD  class= input5>
         <Input class="coolDatePicker" 
          onClick="laydate({elem: '#BIRTHDAY'});"dateFormat="short" name=BIRTHDAY id=BIRTHDAY><span class="icon"><a onClick="laydate({elem: '#BIRTHDAY'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         
         </TD>
          <TD  class= title5>֤������</TD>
          <TD  class= input5> <Input class="common wid"name=IDNo > </TD>
          </TR>
                        <!--td class="title">&nbsp;</td-->
                        <!--td class="input">&nbsp;</td-->
        </TR>
       </table>
       <a href="javascript:void(0);" class="button"onClick="QueryClick();">��   ѯ</a>
           <!-- <INPUT VALUE=" �� ѯ " class = cssButton TYPE=button onClick="QueryClick();">-->

        </div>
 
       </div>
  <!-- Add By QianLy --------------->


   <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomer1);">
            </td>
            <td class= titleImg>
                 �ͻ�������Ϣ
            </td>
        </tr>
    </table>
     <Div id= "divCustomer1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCustomerGrid"></span></td>
            </tr>
        </table>
          <!--  <div id="divTurnPageCustomerGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onClick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onClick="turnPageCustomerGrid.lastPage()">
            </div>-->
    </div>
    
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAddressGrid);">
            </td>
            <td class= titleImg>
                 �ͻ���ַ��Ϣ
            </td>
        </tr>
    </table>
    <Div id= "divAddressGrid" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAddressGrid"></span></td>
            </tr>
        </table>
           <!-- <div id="divTurnAddressGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onClick="turnPageAddressGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPageAddressGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onClick="turnPageAddressGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onClick="turnPageAddressGrid.lastPage()">
            </div>-->
    </div>
    
    
     <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccountGrid);">
            </td>
            <td class= titleImg>
                 �ͻ������˻���Ϣ
            </td>
        </tr>
    </table>
     <Div id= "divAccountGrid" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAccountGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPageAccountGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onClick="turnPageAccountGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onClick="turnPageAccountGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onClick="turnPageAccountGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onClick="turnPageAccountGrid.lastPage()">
            </div>
    </div>
    
    <p>
     <INPUT VALUE=" ����Ͷ�� " Name=Pol class = cssButton  TYPE=button onClick="PolClick();">
     <INPUT VALUE=" ������ȫ " Name=Edor class = cssButton  TYPE=button onClick="EdorClick();">
     <INPUT VALUE=" �������� " Name=Claim class = cssButton  TYPE=button onClick="ClaimClick();">
     <INPUT VALUE=" �����շ� " Name=Pay class = cssButton  TYPE=button onClick="PayClick();">
     <INPUT VALUE=" ������ȡ " Name=Get class = cssButton  TYPE=button onClick="GetClick();">
     </p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
