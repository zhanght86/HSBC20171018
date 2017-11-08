<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDPublRuleInput.jsp
 //程序功能：算法定义界面
 //创建日期：2009-3-17
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDPublRule.js"></SCRIPT>
 <%@include file="PDPublRuleInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDPublRuleSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >已保存的险种算法</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR>
<div align=right><input value="保存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删除" type=button  onclick="del()" class="cssButton" type="button" ></div>
<br>
算法编码
<input class="common" name="AlgoCode" ><font color=red>*</font>
险种编码
<input class="readonly" name="RiskCode" readonly="readonly" value='000000' ><BR>
算法类型
<input class="codeno" name="AlgoType" value = 'U'  readonly="readonly" ><input class="codename" name="AlgoTypeName" value ='核保'' readonly="readonly" > 
算法描述
<input class="common" name=AlgoDesc>
<table width=100%>
  <tr>
    <td class="titleImg" >算法内容<input value="查询算法模版" type=button  onclick="queryAlgoTemp( )" class="cssButton" type="button" ></td>
  </tr>
  <tr width=100%>
  	<td width=100%><textarea rows=3 cols=100 name=AlgoContent ></textarea><font color=red>*</font></td>
  </tr>
</table>
<table>
  <tr>
    <td class="titleImg" >算法可用基本要素：<input value="查询" type=button  onclick="query()" class="cssButton" type="button" ></td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR></BR>
<input value="删除要素" type=button  onclick="button330( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >添加自定义子算法</td>
  </tr>
</table>
子算法编码<input type=common name=SubAlgoCode><font color=red>*</font>
子算法名称<input type=common name=SubAlgoName><font color=red>*</font>
<input value="添加" type=button  onclick="add( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >子算法说明</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline11Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline11GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline11GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline11GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline11GridTurnPage.lastPage();">
</BR></BR>
<input value="子算法属性定义" type=button  onclick="subAlgoDefi()" class="cssButton" type="button" >
<input value="测试" type=button  onclick="test()" class="cssButton" type="button" >
<br><br>
<input value="返回" type=hidden  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
