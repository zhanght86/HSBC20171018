<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRAccRDInput.jsp
//function :
//Creator :zhangbin
//date :2007-3-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./LRAuditICInput.js"></SCRIPT> 
<%@include file="./LRAuditICInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="./ReComManageSave.jsp" method=post name=fm target="fraSubmit">
  <table>
    <tr>
      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
      	OnClick= "showPage(this,divLLReport1);"></td>
  	<td class= titleImg>核保核赔接口</td></tr>
  </table>
  <Div id= "divLLReport1" style= "display: ''">
   	<Table class= common>
   		<TR class= common>
   			<TD class= title5>
操作号
   			</TD>
   			<TD class= input5>
   				<Input class= common name= OperateNo id="ContNoId" value = '2009011403'> 
   			</TD>
   			<TD class= title5></TD>
        <TD class= input5></TD>
   			<TD class= title5></TD>
        <TD class= input5></TD>
      </TR> 
   	</Table>
   	<br><hr>


	<INPUT  class=cssButton VALUE="个单核保" TYPE=Button onclick="auditNewPolicy();" style="display:''">
	<INPUT  class=cssButton VALUE="团单核保" TYPE=Button onclick="grpNewPolicy();" style="display:''">
	<INPUT  class=cssButton VALUE="团单保全核保" TYPE=Button onclick="grpEdorPolicy();" style="display:none;">
	<INPUT  class=cssButton VALUE="保全核保" TYPE=Button onclick="auditEdorPolicy();" style="display:''">
	<INPUT  class=cssButton VALUE="理赔核赔" TYPE=Button onclick="auditClaimPolicy();" style="display:''">
	<br><br>
	
	<Div id= "divGrpTemp" style= "display:none;">
		<table>
	    <tr>
	      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	      	OnClick= "showPage(this,divLLReport2);"></td>
	  	<td class= titleImg>团单核保结果</td></tr>
	  </table>
	  <Div id= "divLLReport2" style= "display: ''">
			<table>     
		     <TD  class= common>
		       <textarea name="Remark" cols="110" rows="5" class="common" onfocus=''>
		       </textarea>
		     </TD>
		  </table>
		</Div>
	</Div>
	
  <input type="hidden" name="OperateType">
  <input type="hidden" name="DeTailType">
  
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>