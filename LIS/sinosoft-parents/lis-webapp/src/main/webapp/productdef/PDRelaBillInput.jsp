<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
 //程序名称：PDRelaBillInput.jsp
 //程序功能：责任给付账单关联
 //创建日期：2009-3-16
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
<SCRIPT src="PDRelaBill.js"></SCRIPT>
<%@include file="PDRelaBillInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDRelaBillSave.jsp" method=post name=fm
	target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=common><input class="common" name="RiskCode" readonly="readonly">
		</td>
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">已关联账单：</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline10Grid">
		</span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
	onclick="Mulline10GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="上一页" TYPE=button
	onclick="Mulline10GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="下一页" TYPE=button
	onclick="Mulline10GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="尾  页" TYPE=button
	onclick="Mulline10GridTurnPage.lastPage();"> </BR>
</BR>

<table>
  <tr>
    <td class="titleImg" >关联账单明细</td>
  </tr>
</table>


<table  class= common>
	<tr class= common>
        <TD  class= title5>给付代码</TD>
        <TD  class= input5>
						<Input class=common readonly="readonly" name=GETDUTYCODE >
        </TD> 
        <TD  class= title5>给付名称</TD>
        <TD  class= input5>
						<Input class=common readonly="readonly" name=GETDUTYNAME > </TD> 
						
        <TD  class= title5>理赔类型</TD>
          <TD  class= input5>
						<Input class=common readonly="readonly" name=GETDUTYKIND > </TD> 
</tr>
<tr class= common>
	 <TD  class= title5>费用计算方式</TD>
        <TD  class= input5>
					<Input class="codeno" name=CLMFEECALTYPE readonly="readonly" verify="费用计算方式|NOTNUlL" ondblclick="return showCodeList('pd_feerela',[this,CLMFEECALTYPENAME],[0,1]);" onkeyup="return showCodeListKey('pd_feerela',[this,CLMFEECALTYPENAME],[0,1]);"><input class=codename name=CLMFEECALTYPENAME readonly="readonly"><font
			size=1 color='#ff0000'><b>*</b></font>
        </TD> 
    <TD  class= title5>费用明细计算公式</TD>
        <TD  class= input5>
						<Input class=common   name=CLMFEECALCODE verify="加费算法|LEN>=6&LEN<=10" >
        </TD> 
     <!-- TD  class= title5>费用默认值</TD>
        <TD  class= input5>
						<Input class=common   name=CLMFEEDEFVALUE verify="费用默认值|num" >
      </TD--> 
      <Input type=hidden    name=CLMFEEDEFVALUE verify="费用默认值|num" >
</tr>
<tr class=common>
	<TD  class= title5>账单项目编码</TD>
        <TD  class= input5>
					<Input class="codeno" name=CLMFEECODE  verify="账单项目编码|NOTNUlL" ondblclick="return showCodeList('pd_clmfeecode',[this,CLMFEENAME],[0,1]);" onkeyup="return showCodeListKey('pd_clmfeecode',[this,CLMFEENAME],[0,1]);"><input class=codename name=CLMFEENAME readonly="readonly"><font
			size=1 color='#ff0000'><b>*</b></font>
        </TD> 
</tr>
</table>
<!--算法定义引用页-->
<jsp:include page="CalCodeDefMain.jsp"/>
<hr>


<div align=right id=savabuttonid>
<input value="重  置" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>

<!--
<table class=common>
	<tr>
		<td class=title5Img>责任给付门诊账单定义</td>
		<td class=title5Img>责任给付住院账单定义</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline8Grid"></span>
		</td>
		<td style="text-align:left;" colSpan=1><span id="spanMulline9Grid"></span>
		</td>
		<td></td>
	</tr>
</table>
</div>	
<br>
<table class=common>
	<tr class=common>
	<td> 
<div align=left><input value="保  存" type=button onclick="save()"
	class="cssButton" type="button"> </div>
	</td>
	<td> 
<div align=left><input value="保  存" type=button onclick="save1()"
	class="cssButton" type="button"></div>
	</td>
		</tr>
</table>
-->
<br>
<input value="返  回" type=button onclick="returnParent( )"
	class="cssButton" type="button"> <br>
<br>

<input type=hidden name="operator">
<input type=hidden name="bttnflag">
<input type=hidden 	name="tableName" value="PD_LMDutyGetFeeRela">
<input type=hidden name=IsReadOnly></form>

<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
