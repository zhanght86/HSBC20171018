<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--
   queryFlag���ã��ж�������Լ¼���������Ǵ�Ͷ����¼����õĲ�ѯ�����Ǳ�����¼����õĲ�ѯ��
   Ͷ����¼�룺queryFlag=queryappnt
   ������¼�룺queryFlag=queryInsured
   
--%>
<%
  String queryFlag = request.getParameter("queryFlag");
%>
<html>
<head>
<title>�ͻ���Ϣ��ѯ</title>
<script language="javascript">
  var queryFlag = "<%=queryFlag%>";
  //alert("aa="+queryFlag);
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./LDPersonQueryNew.js"></script> 

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDPersonQueryNewInit.jsp"%>

</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id=fm target=fraSubmit method=post>
<div class="maxbox">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>  �ͻ����� </TD>
          <TD  class= input5> <Input class="wid" class= common name=CustomerNo id=CustomerNo > </TD>
          <TD  class= title5> ���� </TD>
          <TD  class= input5> <Input class="wid" class= common name=Name id=Name > </TD>
          </TR>
          <TR  class= common>
          <TD  class= title5> �Ա� </TD>
          <!--<TD  class= input> <Input name=Sex class="code" MAXLENGTH=1 ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);" > </TD>-->
          <td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=Sex id=Sex ondblclick="showCodeList('Sex',[this, SexName], [0, 1]);" onclick="showCodeList('Sex',[this, SexName], [0, 1]);" onkeyup="showCodeListKey('Sex', [this, SexName], [0, 1]);" ><input class=codename name=SexName id=SexName readonly=true ></td>    
          <TD  class= title5> �������� </TD>
          <TD  class= input5>  <Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>           </TD>      
       </TR>             
      	<TR  class= common>
          
          <TD  class= title5> ֤������ </TD>
          <!--<TD  class= input> <Input class="code" name=IDType ondblclick="return showCodeList('IDType',[this]);"> </TD>-->
          <td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=IDType id=IDType ondblclick="showCodeList('IDType',[this, IDTypeName], [0, 1]);" onclick="showCodeList('IDType',[this, IDTypeName], [0, 1]);" onkeyup="showCodeListKey('IDType', [this, IDTypeName], [0, 1]);" ><input class=codename name=IDTypeName id=IDTypeName readonly=true ></td>
          <TD  class= title5> ֤������ </TD>
          <TD  class= input5>  <Input class="wid" class= common name=IDNo id=IDNo >   </TD>
       </TR>  
      	<TR  class= common>
          <TD  class= title5> ������ </TD>
          <TD  class= input5>  <Input class="wid" class= common name = ContNo id = ContNo >  </TD>
          <TD  class= title5>  </TD>
          <TD  class= input5>  </TD>
       </TR>
   </Table>  </div>
      <!--<INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="easyQueryClick();">--> 
       <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>  
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
    		</TD>
    		<TD class= titleImg>
    			 �ͻ���Ϣ
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divLDPerson1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD style=" text-align: lefttext-align: left" colSpan=1>
            <span id="spanPersonGrid" ></span> 
  	</TD>
      </TR>
    </Table>	<center>				
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();"> </center>
 </Div>					

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<!--<INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="returnParent();">-->	
<a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a> 				
</Form><br><br><br><br>
</body>
</html>
