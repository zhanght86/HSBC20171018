<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>

<%
 //程序名称：PDRiskDefiInput.jsp
 //程序功能：产品基础信息录入
 //创建日期：2009-3-12
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
 GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 String operator=tGI.Operator;
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <SCRIPT src="RiskCalDef.js"></SCRIPT>
 <%@include file="RiskCalDefInit.jsp"%>
<% session.setAttribute("LoadFlagButton1","1");%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();">
<form action="./RiskCalDefSave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >算法定义：</td>


</table>
<table class=common>
  <tr class=common>
            <td class="title">算法类型</td>
		<td class=input5><input class="codeNo" name="CalModeType"  
			ondblclick="return showCodeList('calmodetype',[this,CalModeTypeS],[0,1]);"
			onkeyup="return showCodeListKey('calmodetype',[this,CalModeTypeS],[0,1]);"><input
			class="codename" name="CalModeTypeS"></td>
       <td class="title">算法编码</td>
   <td> <input class="common" name="CalCode" verify="算法编码|len<=10"></td>
   <td class="title" >算法描述</td>
   <td> <input class="common" name="Des"></td>
  </tr>
  <tr> 
       <td class="title" >险种编码</td>
   <td> <input class="common" name="RiskCode" id="Riskcode" verify="险种编码|len<=8"></td>
  </tr>
</table>

<table>
</table>

<BR>
<div align=right>
<input value="查  询" type=button  onclick="query();" class="cssButton" type="button" >
<input value="保  存" type=button  onclick="save();" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="modify();" class="cssButton" type="button" >
<input value="重  置" type=reset   class="cssButton" >
</div>
<BR>
<table>
  <tr>
    <td class="titleImg" >算法清单：</td>


</table>
<div>
<table  class= common>
   <tr>
      <td>
     <span id="spanMullineGrid" >
     </span> 
      </td>
   </tr>
</table>
</div>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
<BR/><BR/>
<div align=left>
<input value="算法定义" type=button  onclick="def();" class="cssButton" type="button" >
<!-- <input value="算法复制" type=button  onclick="showbotton();" class="cssButton" type="button" > -->
<input value="算法删除" type=button  onclick="del();" class="cssButton" type="button" >
<input value="算法发布" type=button  onclick="deploy();" class="cssButton" type="button" >
<br>

<input value="SQL数据表定义" type=button  onclick="def_sql_data();" class="cssButton" type="button" >
<input value="SQL算法定义" type=button  onclick="def_sql();" class="cssButton" type="button" >
</div>
<div id='CalCodeCopyDiv' style='display:none'>

<table>
  <tr>
    <td class="titleImg" >算法复制：</td>


</table>
<table class=common>
  <tr class=common>
       <td class="title">原算法编码</td>
   <td><input class="common" name="OldCalCode" verify="原算法编码|len<=10"></td>
               <td class="title">算法类型</td>
		<td class=input5><input class="codeNo" name="NewCalModeType"  readonly
			ondblclick="return showCodeList('calmodetype',[this,NewCalModeTypeS],[0,1]);"
			onkeyup="return showCodeListKey('calmodetype',[this,NewCalModeTypeS],[0,1]);"><input
			class="codename" name="NewCalModeTypeS"></td>
   </tr><tr class=common>

       <td class="title">算法编码</td>      
   <td> <input class="common" name="NewCalCode" verify="算法编码|len<=10"></td>
      <td class="title" >算法描述</td>
   <td> <input class="common" name="NewDes"></td>
       <td class="title" >险种编码</td>
   <td> <input class="common" name="NewRiskCode"  verify="险种编码|len<=8"></td>
</table>
<BR/><BR/>
<div align=left>
<input value="复  制" type=button  onclick="copy();" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="showbotton();" class="cssButton" type="button" >
</div>
</div>
<input type=hidden name="Operator">
<input type=hidden name="ID">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
