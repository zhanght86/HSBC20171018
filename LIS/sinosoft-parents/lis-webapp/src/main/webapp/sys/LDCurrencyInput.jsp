<html> 
<%
//程序名称：
//程序功能：
//创建日期：2009-10-12 19:26:43
//创建人  ：zhanpeng程序创建
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
  <SCRIPT src="LDCurrencyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDCurrencyInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDCurrencySave.jsp" method=post name=fm id=fm target="fraSubmit">
   
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
          外汇币种维护
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            币种代码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CurrCode id=CurrCode verify="币种代码|notnull&len<=3" elementtype=nacessary>
          </TD>
          <TD  class= title5>
            币种名称
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CurrName id=CurrName verify="币种名称|notnull&len<=50" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            有效标志
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Validation id=Validation verify="有效标志|notnull&len<=1" elementtype=nacessary>
          </TD>
          <TD  class= title5>
            代码别名
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CodeAlias id=CodeAlias >
          </TD>
        </TR>
      </table>
    </Div>
    <input type=hidden name=hideOperate value=''>
    <br>

				<!--<INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return addClick();">
			
				<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
			
				<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">
			
				<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">-->
                <a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">增    加</a>
    <!--<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">-->
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">修    改</a>
    <!--<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">-->
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">查    询</a>
    <!--<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">-->
    <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">删    除</a>
			
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
