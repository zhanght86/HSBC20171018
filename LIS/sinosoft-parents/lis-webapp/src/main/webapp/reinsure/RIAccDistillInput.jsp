<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：RIAccDistillInput.jsp
 //程序功能：分出责任定义-费用类型定义
 //创建日期：2011/6/16
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
 
  <SCRIPT src="RIAccDistill.js"></SCRIPT>
  <%@include file="RIAccDistillInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIAccDistillSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >费用类型定义</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">分出责任编码</td><td class="input5"><input class="readonly" name="AccDefNo"  readonly="readonly" ></td>  
    <td class="title5">分出责任名称</td><td class="input5"><input class="readonly" name="AccDefName"  readonly="readonly" ></td>  
    <td class="title5"></td><td class="input5"></td>  
  </tr>
</table>
<br>
<input value="确  认"  onclick="button100( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >费用类型选择</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMul9Grid" >
     </span> 
      </td>
   </tr>
</table>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
