<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
String tLoadFlag = "6";
%>
<head >
<script>
	
 //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����.
 var loadFlag = "<%=tLoadFlag%>";  
 var prtNo = "<%=request.getParameter("prtNo")%>";
 window.onfocus = FocusIt; 
 var showInfo;
 function FocusIt() {
   try { 
   	showInfo.focus(); 
   } catch(e) {}
 }
</script>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
 <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
 <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
 <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
 <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
 <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

 <SCRIPT src="GEdorTypeNIInput.js"></SCRIPT>
 <%@include file="GEdorTypeNIInit.jsp"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
</head>
<body  onload="initForm(); " >
 <form action="./GEdorTypeNISubmit.jsp" method=post name=fm id=fm target="fraSubmit">
 	<div class=maxbox1>
	<table class=common>
     <TR  class= common> 
       <TD  class= title > ������</TD>
       <TD  class= input > 
         	<input class="readonly wid" readonly name=EdorNo id=EdorNo >
       </TD>
       <TD class = title > �������� </TD>
       <TD class = input >
        	<Input class = codeno     readonly name=EdorType id=EdorType><input class = codename name=EdorTypeName id=EdorTypeName readonly=true>
        	<input class = "readonly wid" readonly name=EdorTypeCal id=EdorTypeCal type=hidden>
       </TD>      
       <TD class = title > ���屣���� </TD>
       <TD class = input >
        	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
       </TD>   
     </TR>
   </TABLE> 
   </div>   
   <table>
     <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
      </td>
      <td class= titleImg>
        ����������
      </td>
    </tr>
   </table>  
   <Div  id= "divLPInsured" style= "display: ''">
   	
     <table  class= common>
        <tr  class= common>
          <td text-align: left colSpan=1>
       			<span id="spanLPInsuredGrid" ></span> 
         	</td>
     		</tr>
     </table>  
     <br>   
      <Div  id= "divPageButton" style= "display: ''" align="center" >
      	<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
      	<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();">      
      	<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
      	<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();">
      </Div> 
      <br>
      <center>
   		<a href="../appgrp/download1.xls">���յ���ģ��</a> &nbsp;<a href="../appgrp/download2.xls">���յ���ģ��</a>
   		<br><br>   
   		<a href="../appgrp/explain.rar">���ش��̵���˵����rar�ļ���</a>
   		</center>                
   </div>  
       
 <hr>
   <Div  id= "divGetMoney" style= "display:none">
		<table class=common>
     <TR  class= common> 
       <TD  class= title > �ɷѽ��ϼ�</TD>
       <TD  class= input > 
         <input class="readonly wid" readonly name=GetMoney id=GetMoney>Ԫ   </TD>      
       <TD class = input ></TD>
       <TD class = input ></TD>       
       <TD class = input ></TD>   
     </TR>
   	</TABLE>    
   </Div>       
   <Div  id= "divSubmit" style= "display:''" align="center">    
    	
    	      
  		<Input class= cssButton type=Button value="  ��������  " 		onclick="edorSave()"> 
    	<Input class= cssButton type=Button  			value="���ӱ�������" 		onclick="edorNewInsured()">          
    	<INPUT class= cssButton id="pisdbutton1" 	value="���뱻�����嵥" 	TYPE=button 	onclick="getin();">
    	<Input class= cssButton type=Button 			value="   ������ϸ   "   onclick="MoneyDetail()">
		<Input class= cssButton type=Button value=" ��  �� "       onclick="returnParent()">
    	
    	
    	
   </Div>  
  
    
 
     	
    
     
 	 <input type=hidden id="fmtransact" 	name="fmtransact">
 	 <input type=hidden id="ContType" 		name="ContType">
 	 <input type=hidden id="ContNo" 			name="ContNo">
 	 <input type=hidden id="InsuredNo" 		name="InsuredNo">
 	 <input type=hidden id="EdorValiDate" name="EdorValiDate">
 	 <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
 	 <input type=hidden id="NameType" 		name="NameType">
 	 <input type=hidden id="Flag" 				name="Flag">
 	 <input type=hidden id="Money" 				name="Money">
 </form>
  <span id="spanCode"  		style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


