<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<%
 //程序名称：PDRateCashValueInput.jsp
 //程序功能：数据表和现金价值定制
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
 <SCRIPT src="PDSugRateCashValue.js"></SCRIPT>
 <%@include file="PDSugRateCashValueInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDSugRateCashValueSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>险种代码</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5></td>
		<td class=input5>
			<input class=common name="PayCode" value="000000" type=hidden readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >请选择数据表所用要素：</td>
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
<input value="添  加" type=button  onclick="addRate( )" class="cssButton" type="button" >
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >已选择的数据表要素：</td>
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
<table class=common>
	<tr class=common>
		<td class=title5>数据表名称</td>
		<td class=input5>
			<input class=common name="DataTBLName" >
		</td>
		<td class=title5>数据小数点后位数</td>
		<td class=input5>
			<Input class=codeNo name=PremDataType ondblclick="return showCodeList('pdpremdatatype',[this,PremDataTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('pdpremdatatype',[this,PremDataTypeName],[0,1],null,null,null,1);"><input class=codename name=PremDataTypeName readonly="readonly" >
		</td>
	</tr>
</table>
<input value="创建数据表" type=button  onclick="createRateTable( )" class="cssButton" type="button" >
<br></BR>
<table>
  <tr>
    <td class="titleImg" >险种数据表信息</TD><!--TD width=200></TD><TD></TD><TD width=200></TD><TD><input value="创建险种页面" type=button  onclick="button310( )" class="cssButton" type="button" ></td-->
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
</BR>
<input value="删除数据表" type=button  onclick="deleteRateTable( )" class="cssButton" type="button" >
<input value="数据表模版下载" type=button  onclick="downloadRateTable( )" class="cssButton" type="button" >
<br><br>

<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline15Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline15GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline15GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline15GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline15GridTurnPage.lastPage();">
</BR>

<input type=hidden name=newTableName>
<input type=hidden name=operator>
<input type=hidden name="tableName" value="Pd_Scheratecalfactor_Lib">
<input type=hidden name=IsReadOnly>
</form>

<form name=uploadForm enctype="multipart/form-data" method=post action="PDImportExcelSave.jsp" target="fraSubmit">
<table>
<tr>
<td class="titleImg">&nbsp;&nbsp;</td>
</tr>
  <tr>
    <td>数据表导入&nbsp;<Input type="file" style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 180px;"  width="100%" name=FileName
			value="sdf"><input value="导入" type=button  onclick="ImportExcel( )" class="cssButton" type="button" ></td>
  </tr>
</table>
<input type=hidden name=IsReadOnly>
</form>

<form name=dealUpload method=post action="PDImportExcelSave.jsp" target="fraSubmit">
<input type=hidden name=targetFileName >
<input type=hidden name=IsUpload>
<input type=hidden name=newTableName>
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

