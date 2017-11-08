<%@include file="/i18n/language.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：RIProfitLossDefQueryInput.jsp
 //程序功能：盈余佣金定义查询
 //创建日期：2011/8/20
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
  <SCRIPT src="RIProfitLossDefQuery.js"></SCRIPT>
  <%@include file="RIProfitLossDefQueryInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIProfitLossDefQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >查询条件</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">盈余佣金编码</td><td class="input5"><input class="common" name="RIProfitNo" ></td>  
    <td class="title5">盈余佣金名称</td><td class="input5"><input class="common" name="RIProfitName" ></td>  
    <td class="title5">关联类型</td><td class="input5"><input class="codeno" name="RelaType"
    ondblclick="return showCodeList('riprorelatype',[this,RelaTypeName],[0,1]);"
    onkeyup="return showCodeListKey('riprorelatype',[this,RelaTypeName],[0,1]);" nextcasing=><input class="codename" name="RelaTypeName" ></td>  
  </tr>
</table>
<input value="查询"  onclick="button138( )" class="cssButton" type="button" >
<input value="返回"  onclick="button139( )" class="cssButton" type="button" >
<input value="關閉"  onclick="button140( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >盈余佣金信息定义</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulDefQueryGrid" >
     </span> 
      </td>
   </tr>
</table>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
