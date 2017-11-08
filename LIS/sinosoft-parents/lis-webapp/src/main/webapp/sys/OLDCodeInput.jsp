<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-08-16 17:44:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="OLDCodeInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="OLDCodeInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./OLDCodeSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
          代码维护
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" lclass=maxbox style= "display: ''">
     <div class="maxbox1" >
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            编码类型
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=CodeType verify="编码类型|notnull&len<=20">
          </TD>
          <TD  class= title5>
            编码
          </TD>
          <TD  class= input5>
            <Input class="common wid"name=Code verify="编码|notnull&len<=20">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            编码名称
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=CodeName >
          </TD>
          <TD  class= title5>
            编码别名
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=CodeAlias >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            机构代码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ComCode verify="机构代码|len<=10">
          </TD>
          <TD  class= title5>
            其它标志
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=OtherSign verify="其它标志|len<=10">
          </TD>
        </TR>
      </table>
    </Div>
     </Div>
    <input type=hidden name=hideOperate value=''>
    <span id="operateButton" >
	<table class="common" align=center>
		<tr align=left >
			<INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return addClick();">
			<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">
		</tr>
	</table>
	</span>
    <%@include file="../common/jsp/InputButton.jsp"%>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
