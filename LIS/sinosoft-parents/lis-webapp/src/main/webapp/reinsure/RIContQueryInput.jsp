<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：RIContQueryInput.jsp
 //程序功能：合同查询
 //创建日期：2011-7-10
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
 
  <SCRIPT src="RIContQuery.js"></SCRIPT>
  <%@include file="RIContQueryInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIContQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >查询条件</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">再保公司</td><td class="input5"><input class="codeno" name="" ondblclick="return showCodeList(null,[,],[0,1]);" onkeyup="return showCodeListKey(null,[,],[0,1]);" nextcasing=><input class="codename" name="ManageComName" ></td>  
    <td class="title5"></td><td class="input5"><input class="readonly" name="" readonly="readonly" ></td>  
    <td class="title5"></td><td class="input5"><input class="readonly" name="" readonly="readonly" ></td>  
  </tr>
</table>
<input value="查  询"  onclick="button134( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >再保方案列表</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMul10Grid" >
     </span> 
      </td>
   </tr>
</table>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
