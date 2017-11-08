<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDDutyPayAddFeeInput.jsp
 //程序功能：险种责任加费定义
 //创建日期：2009-3-13
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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDDutyPayAddFee.js"></SCRIPT>
 <%@include file="PDDutyPayAddFeeInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDDutyPayAddFeeSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		
		<td class=title5>险种缴费编码</td>
		<td class=input5>
			<input class=common name="PayPlanCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >已保存险种责任加费类型</td>
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
</BR></BR>

<table>
  <tr>
    <td class="titleImg" >险种责任加费定义</td>
  </tr>
</table>
		
		
		
		
		
		
<table  class= common>
	<tr class= common>
        <TD  class= title>险种编码</TD>
        <TD  class= input>
			<Input class=common readonly="readonly" name=RISKCODE >
        </TD> 
        <TD  class= title>责任代码</TD>
        <TD  class= input>
						<Input class=common readonly="readonly" name=DUTYCODE >
						
        <TD  class= title>加费类别</TD>
        <TD  class= input>
					<Input class="codeno" name=ADDFEETYPE readonly="readonly" verify="加费类别|NOTNUlL" ondblclick="return showCodeList('pd_addfeetype',[this,ADDFEETYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_addfeetype',[this,ADDFEETYPEName],[0,1]);"><input class=codename name=ADDFEETYPEName readonly="readonly">
        <font color=red>*</font>
        </TD> 
        </tr>
        <tr class= common>
        <TD  class= title>加费方式</TD>
        <TD  class= input>
					<Input class="codeno" name=ADDFEEOBJECT readonly="readonly" verify="加费方式|NOTNUlL" ondblclick="return showCodeList('pd_addfeeobject',[this,ADDFEEOBJECTName],[0,1]);" onkeyup="return showCodeListKey('pd_addfeeobject',[this,ADDFEEOBJECTName],[0,1]);"><input class=codename name=ADDFEEOBJECTName readonly="readonly">
          <font color=red>*</font>
        </TD> 


	 <TD  class= title STYLE="display:none;">
加费评点最大值
	 </TD>
        <TD  class= input STYLE="display:none;">
		<Input class=input5   name=ADDPOINTLIMIT  >
        </TD> 
    <TD  class= title>加费算法</TD>
        <TD  class= input>
		<Input class=common   name=ADDFEECALCODE verify="加费算法|LEN>=6&LEN<=10" >
        </TD> 
</tr>
</table>
<!--算法定义引用页-->
<jsp:include page="CalCodeDefMain.jsp"/>
<hr>

<div align=left id=savabuttonid>
<input value="重  置" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" >
<input value="加费费率表创建" type=button  onclick="button136( )" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>
<div align=left id=checkFunc>
<input value="查看算法内容" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>

<!--table  class= common>
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
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();"-->
</BR></BR>

<!--input value="加费算法定义" type=button  onclick="button135( )" class="cssButton" type="button" -->

<br><br>

<br><br>

<input type=hidden name="operator">
<!--input type=hidden name="DutyCode"-->
<input type=hidden name="tableName" value="PD_LMDutyPayAddFee">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
