<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：RIAlgorithmSetInput.jsp
 //程序功能：方案算法集定义
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
 
  <SCRIPT src="RIAlgorithmSet.js"></SCRIPT>
  <%@include file="RIAlgorithmSetInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIAlgorithmSetSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >查询条件</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">方案编码</td><td class="input5"><input class="readonly" name="" readonly="readonly" ></td>  
    <td class="title5">方案名称</td><td class="input5"><input class="readonly" name="" readonly="readonly" ></td>  
    <td class="title5">方案类型</td><td class="input5"><input class="codeno" name="RISolType" ondblclick="return showCodeList('risolutiontype',[this,RISolTypeName],[0,1]);" onkeyup="return showCodeListKey('risolutiontype',[this,RISolTypeName],[0,1]);" nextcasing=><input class="codename" name="RISolTypeName" ></td>  
  </tr>
	<tr class="common">
    <td class="title5">算法类型</td><td class="input5"><input class="codeno" name="RIAthType" ondblclick="return showCodeList('riathtype',[this,RIAthTypeName],[0,1]);" onkeyup="return showCodeListKey('riathtype',[this,RIAthTypeName],[0,1]);" nextcasing=><input class="codename" name="RIAthTypeName" ></td>  
    <td class="title5">业务类型</td><td class="input5"><input class="codeno" name="RIAthBSType" ondblclick="return showCodeList('riathbstype',[this,RIAthBSTypeName],[0,1]);" onkeyup="return showCodeListKey('riathbstype',[this,RIAthBSTypeName],[0,1]);" nextcasing=><input class="codename" name="RIAthBSTypeName" ></td>  
    <td class="title5">事件类型</td><td class="input5"><input class="codeno" name="RIAthEvenType" ondblclick="return showCodeList('riatheventype',[this,RIAthEvenTypeName],[0,1]);" onkeyup="return showCodeListKey('riatheventype',[this,RIAthEvenTypeName],[0,1]);" nextcasing=><input class="codename" name="RIAthEvenTypeName" ></td>  
  </tr>
	<tr class="common">
    <td class="title5">算法编码</td><td class="input5"><input class="common" name="" ></td>  
    <td class="title5">算法名称</td><td class="input5"><input class="common" name="" ></td>  
    <td class="title5"></td><td class="input5"></td>  
  </tr>
</table>
<br>
<input value="查  询"  onclick="button113( )" class="cssButton" type="button" >
<input value="返  回"  onclick="button114( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >算法库列表</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanAlgLibMul11Grid" >
     </span> 
      </td>
   </tr>
</table>
<input class=cssbutton value="首  页" type=button onclick="AlgLibMul11GridTurnPage.firstPage();"> 
<input class=cssbutton value="上一页" type=button onclick="AlgLibMul11GridTurnPage.previousPage();">      
<input class=cssbutton value="下一页" type=button onclick="AlgLibMul11GridTurnPage.nextPage();"> 
<input class=cssbutton value="尾  页" type=button onclick="AlgLibMul11GridTurnPage.lastPage();">
<br><br>
<input value="添  加"  onclick="button115( )" class="cssButton" type="button" >
<input value="删  除"  onclick="button116( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >方案算法列表</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanAlgSetMul11Grid" >
     </span> 
      </td>
   </tr>
</table>
<input class=cssbutton value="首  页" type=button onclick="AlgSetMul11GridTurnPage.firstPage();"> 
<input class=cssbutton value="上一页" type=button onclick="AlgSetMul11GridTurnPage.previousPage();">      
<input class=cssbutton value="下一页" type=button onclick="AlgSetMul11GridTurnPage.nextPage();"> 
<input class=cssbutton value="尾  页" type=button onclick="AlgSetMul11GridTurnPage.lastPage();">
<br><br>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
