<html> 
<%
//程序名称：
//程序功能：
//创建日期：2009-10-13 9:42:43
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
  <SCRIPT src="LDExOtherRateInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDExOtherRateInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDExOtherRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
          主要外汇币种对美元折算率维护
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>
        	<TD  class= title5>                                                                                                                                                                                                                                               
            外汇币种代码
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
             onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="外汇币种代码|notnull&currcode"><input name=CurrCodeName id=CurrCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            外币数额单位
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Per id=Per verify="外币数额单位|notnull&num&len<=10" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
        	<TD  class= title5>
            目标币种代码
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCurrCode id=DestCurrCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);"  onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="目标币种代码|notnull&currcode"><input name=DestCodeName id=DestCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            对美元的折算率
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ExchRate id=ExchRate verify="对美元的折算率|notnull" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            启用日期
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=StartDate verify="启用日期|notnull&date" elementtype=nacessary>-->
             <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="启用日期|notnull&date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            停用日期
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate verify="启用日期|date">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="启用日期|date" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
      </table>
    </Div>
    <input type=hidden name=hideOperate value=''>
    <br>
    <INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return addClick();">
    <!--<a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">增    加</a>-->
    <INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
    <!--<a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">修    改</a>-->
    <INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">
    <!--<a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">查    询</a>-->
    <INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">
    <!--<a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">删    除</a>-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
