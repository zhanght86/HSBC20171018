<%@page contentType="text/html;charset=GBK" %>
<html>

<%
	GlobalInput tGI1 = new GlobalInput();
    tGI1=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����    
%>    
<script>
	var managecom = "<%=tGI1.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI1.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  
  <SCRIPT src="./RePrtGrpEndorse.js"></SCRIPT>
  <SCRIPT src="PEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PrtGrpEndorseInit.jsp"%>

  <title>������������</title>
</head>
<body  onload="initForm();" >

<form action="./PEdorQueryOut.jsp" method=post name=fm id=fm target="fraSubmit" >
   <table>
     <tr>
       <td class= common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor1);">
       </td>
       <td class= titleImg>
         ���������ѯ������
       </td>
     </tr>
   </table>
   <Div  id= "divLPEdor1" style= "display: ''">
   <div class="maxbox1" >
     <table  class= common>
       <TR  class= common>
         <TD  class= title5>  ��ȫ�����  </TD>
         <TD  class= input5>  <Input class="common wid" name=EdorAcceptNo > </TD>
         <TD  class= title5>  ������ </TD>
         <TD  class= input5> <Input class="common wid" name=EdorNo > </TD>
          </TR>
       <tr class=common>
         <TD  class= title5>  �����ͬ�� </TD>
         <TD  class= input5>  <Input class="common wid" name=GrpContNo > </TD>
      
         <TD  class= title5>   �������  </TD>
         <!--<TD  class= input><Input class= code name=ManageCom ondblclick="return showCodeList('ComCode',[this]);" onkeyup="return showCodeListKey('ComCode',[this]);"></TD>-->
         <TD  class= input5><Input class="common wid" name=ManageCom  id=ManageCom
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeListEx('nothis', [this],[0,1],null,null,null,1,'260');"
          onDblClick="return showCodeListEx('nothis', [this],[0,1],null,null,null,1,'260');"
           onKeyUp="return showCodeListKeyEx('nothis', [this],[0,1],null,null,null,1,'260');"></TD>
         
       </tr>
     </table>
   </Div> </Div>
    
  <!-- <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="queryEndorse();"> -->
  <a href="javascript:void(0);" class="button"onClick="queryEndorse();">��   ѯ</a>
          					
   <table>
     <tr>
       <td class=common>
     	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor2);">
       </td>
       <td class= titleImg>����������Ϣ</td>
     </tr>    	 
   </table>   
   <Div  id= "divLPEdor2" style= "display: ''" align=center>
     <table  class= common>
       <tr  class= common>
     	 <td text-align: left colSpan=1>
   			<span id="spanPEdorMainGrid" >
   			</span> 
   		 </td>
   	   </tr>
   	 </table>   	 
       <INPUT VALUE="��ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
       <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
       <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
       <INPUT VALUE="βҳ" class= cssButton93 TYPE=button onClick="getLastPage();">
   </div>  
   
  	<br>
  	
  <!-- <INPUT VALUE="��������" class= cssButton TYPE=button onClick="print();">-->
<a href="javascript:void(0);" class="button"onClick="print();">��������</a>
<br><br><br><br>
  <!-- ������ -->
  <input type=hidden id="EdorType" name="EdorType">
  <input type=hidden id="ContType" name="ContType">

  	 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
