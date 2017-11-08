<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：RIAthSchemeAssInput.jsp
 //程序功能：方案算法关联
 //创建日期：2011/6/17
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
 
  <SCRIPT src="RIAthSchemeAss.js"></SCRIPT>
  <%@include file="RIAthSchemeAssInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();">
<form action="./RIAthSchemeAssSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >查询条件</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">方案编码</td><td class="input5"><input class="readonly" name="ArithmeticDefID"  readonly="readonly" ></td>  
    <td class="title5">方案名称</td><td class="input5"><input class="readonly" name="ArithmeticDefName"  readonly="readonly"></td>  
    <td class="title5">方案类型</td>
    <td class="input5">
    	<input class="codeno" name="RISolType" readonly="readonly" ondblclick="return showCodeList('risolutiontype',[this,RISolTypeName],[0,1]);" 
    		onkeyup="return showCodeListKey('risolutiontype',[this,RISolTypeName],[0,1]);" nextcasing=><input class="codename" name="RISolTypeName" readonly="readonly">
    </td>  
  </tr>
  <tr>
    <td class="title5">状          态</td>
    <td class="input5">
    	<input class="codeno" name="RIPreceptState" ondblclick="return showCodeList('ristate',[this,RIPreceptStateName],[0,1]);" onkeyup="return showCodeListKey('ristate',[this,RIPreceptStateName],[0,1]);" 
   	 		nextcasing=><input class="codename" name="RIPreceptStateName" >
   	</td>  
    <td class="title5">
    	<Div id= "divTitle1" style= "display:none;" >分出责任编码</Div>
    	<Div id= "divTitle2" style= "display:none;" >合同方案编码</Div>
    </td>
    <td class="input5">
    	<Div id= "divInput1" style= "display:none;" >
    	    <input class="codeno" name="RIAccDefNo" ondblclick="return showCodeList('riaccmucode',[this,RIAccDefName],[0,1]);" onkeyup="return showCodeListKey('riaccmucode',[this,RIAccDefName],[0,1]);" 
    			nextcasing=><input class="codename" name="RIAccDefName" >
    	</Div>
    	<Div id= "divInput2" style= "display:none;" >
     	    <input class="codeno" name="RIRreceptNo" ondblclick="return showCodeList('riprecept',[this,RIRreceptName],[0,1]);" onkeyup="return showCodeListKey('riprecept',[this,RIRreceptName],[0,1]);" 
    			nextcasing=><input class="codename" name="RIRreceptName" >
		</Div>
    </td>   
  </tr>
</table>
<br>
<input value="查  询"  onclick="button110( )" class="cssButton" type="button" >

<input class="cssButton" type="button" value="关  闭" onclick="ClosePage()" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >明细信息</td>
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
<br>
<input value="关联方案"  onclick="button109()" class="cssButton" type="button"  style="display:none;">
<br>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
