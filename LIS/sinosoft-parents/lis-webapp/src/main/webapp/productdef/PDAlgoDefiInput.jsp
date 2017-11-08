<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDAlgoDefiInput.jsp
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
 <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDAlgoDefi.js"></SCRIPT>
 <%@include file="PDAlgoDefiInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDAlgoDefiSave.jsp" method=post name=fm target="fraSubmit">

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
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR>
<table>
  <tr>
    <td class="titleImg" >子算法明细</td>
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
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline11GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline11GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline11GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline11GridTurnPage.lastPage();">
</BR></BR>

<table class=common>
	<tr class=common>
		<td class=title5>
算法编码
		</td>
		<td class=input5>
			<input class="common" name="AlgoCode" maxlength="10" elementtype=nacessary>
		</td>
		<td class=title5>
险种代码
		</td>
		<td class=input5>
			<input class="common" name="RiskCode" maxlength="8"  elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>
算法模板类型
		</td>
		<td class=input5>   
			<input class="codeno" name="AlgoType" ondblclick="return showCodeList('algotemptype',[this,AlgoTypeName],[0,1]);" onkeyup="return showCodeListKey('algotemptype',[this,AlgoTypeName],[0,1]);" ><input class="codename" name="AlgoTypeName"  elementtype=nacessary>
		</td>
		<td class=title5>
算法描述
		</td>
		<td class=input5>
			<input class="common" name=AlgoDesc  maxlength="500"  elementtype=nacessary>
		</td>
	</tr>

</table>

<div id="subAlgoDiv" style="display: none;">
<table class=common>
	<tr class=common>
		<td class=title5>
父算法编码
		</td>
		<td class=input5>
			<input class=common name="MainAlgoCode"  maxlength="6"   elementtype=nacessary onblur="getRiskName(this)">
		</td>
		<td class=title5>
父算法名称
		</td>
		<td class=input5>
			<input class=common name=MainAlgoName readonly="readonly">
		</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>
子算法优先级
		</td>
		<td class=input5>
			<input class=common name= SubAlgoGrade  maxlength="1"  elementtype=nacessary>
		</td>
		<td class=title5>
			
		</td>
		<td class=input5>
		</td>
	</tr>

</table>
</div>
<div align=left id=modifyButton1>
<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" ></div>
<br>
<table width=100%>
  <tr>
    <td class="titleImg" >算法内容&nbsp;&nbsp;<input value="查询算法模版" type=button  onclick="queryAlgoTemp( )" class="cssButton" type="button" ><input value="查询险种费率表名" type=button  onclick="button326( )" class="cssButton" type="button" ></td>
  </tr>
  <tr width=100%>
  	<td width=100%><textarea rows=3 cols=100 name=AlgoContent  ></textarea></td>
  </tr>
</table>
<table>
  <tr>
    <td class="titleImg" >算法可用基本要素：&nbsp;&nbsp;</td>
    <td class=common align=right><input value="查  询" type=button  onclick="query()" class="cssButton" type="button" ></td>
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
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR>
<input value="删除要素" type=button  onclick="button330( )" class="cssButton" type="button" id=modifyButton2>
<br><br>

<input value="测试" type=button  onclick="test( )" class="cssButton" type="button" id=modifyButton3>
<br><br>
<input value="返  回" type=button  onclick="retResult( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="mRiskCode">
<input type=hidden name="tableName" value="">
<input type=hidden name="IsReadOnly">

</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
