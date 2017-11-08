<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2005-01-26 13:18:16
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="LDCodeInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDCodeInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype()" >
  <form action="./LDCodeSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDCode1);">
    		</td>
    		 <td class=titleImg>
        		 公用代码表基本信息
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLDCode1" class=maxbox style= "display: ''">
<table  class= common align='center' >
  <TR  class= common>
    <TD  class="title5">
      编码类型
    </TD>
    <TD  class="input5">
      <Input class="wid common" name=CodeType id=CodeType elementtype=nacessary verify="编码类型|notnull&len<21" >
    </TD>
    <TD  class="title5">
      编码
    </TD>
    <TD  class="input5">
      <Input class="wid common" name=Code id=Code elementtype=nacessary verify="编码类型|notnull&len<21">
    </TD>
  </TR>
  <TR  class= common>
    <TD  class="title5">
      编码名称
    </TD>
    <TD  class="input5">
      <Input class="wid common" name=CodeName id=CodeName verify="编码名称|len<121">
    </TD>
    <TD  class="title5">
      编码别名
    </TD>
    <TD  class="input5">
      <Input class="wid common" name=CodeAlias id=CodeAlias verify="编码名称|len<121">
    </TD>
  </TR>
  <TR  class= common>
    <TD  class="title5">
      机构代码
    </TD>
    <TD  class="input5">
      <Input class="wid common" name=ComCode id=ComCode verify="机构代码|len<11">
    </TD>
    <TD  class="title5">
      其它标志
    </TD>
    <TD  class="input5">
      <Input class="wid common" name=OtherSign id=OtherSign verify="其它标志|len<11">
    </TD>
  </TR>
</table>
    </Div>
	<%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
  </form>
  <br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
