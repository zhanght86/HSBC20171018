<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskAccGetInput.jsp
 //程序功能：给付账户定义
 //创建日期：2009-3-16
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    String mRiskCode =request.getParameter("riskcode");
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
 <SCRIPT src="PDRiskAccGet.js"></SCRIPT>
 <%@include file="PDRiskAccGetInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskAccGetSave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >险种下的账户明细</td>
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
<table>
  <tr>
    <td class="titleImg" >已保存的险种账户给付</td>
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
<!--input value="帐户管理费定义" type=button  onclick="button184( )" class="cssButton" type="button" -->
<br><br>

<table>
  <tr>
    <td class="titleImg" >险种账户给付定义</td>
  </tr>
</table> 
<table  class= common>
	<tr class= common>
		<TD  class= title>险种编码</TD>
        <TD  class= input>
						<Input class=readonly readonly="readonly"   name=RISKCODE  >
        </TD> 
        <TD  class= title>给付代码</TD>
         <TD  class= input>
					<Input class="codeno" name=GETDUTYCODE readonly="readonly" verify="给付代码|NOTNUlL" ondblclick="return showCodeList('pd_getdutycode',[this,GETDUTYNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);" onkeyup="return showCodeListKey('pd_getdutycode',[this,GETDUTYNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=GETDUTYNAME readonly="readonly">

        </TD> 
        <TD  class= title>保险帐户号码</TD>
        <TD  class= input>
					<Input class="codeno" name=INSUACCNO readonly="readonly" verify="加费类型|NOTNUlL" ondblclick="return showCodeList('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);" onkeyup="return showCodeListKey('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=INSUACCNOName readonly="readonly">

        </TD> 
         <TD  class= title STYLE="display:none;">默认比例</TD>
        <TD  class= input STYLE="display:none;">
						<Input class=common verify="默认比例|num"  name=DEFAULTRATE  value="">
        </TD> 
    </tr>
    <tr class= common>
        <TD  class= title STYLE="display:none;">是否需要录入</TD>
        <TD  class= input STYLE="display:none;">
					<Input class="codeno" name=NEEDINPUT readonly="readonly" verify="是否需要录入|NOTNUlL" value="0" ondblclick="return showCodeList('pd_zeroone',[this,NEEDINPUTName],[0,1]);" onkeyup="return showCodeListKey('pd_zeroone',[this,NEEDINPUTName],[0,1]);"><input class=codename name=NEEDINPUTName value="0" readonly="readonly">

        </TD> 
        <TD  class= title STYLE="display:none;">转出账户时的算法编码</TD>
        <TD  class= input STYLE="display:none;">
						<Input class=common   name=CALCODEMONEY >
        </TD> 
        <TD  class= title STYLE="display:none;">处理方向</TD>
        <TD  class= input STYLE="display:none;">
			<Input class="codeno" name=DEALDIRECTION readonly="readonly" value="1"
				ondblclick="return showCodeList('pd_zeroone',[this,DEALDIRECTIONName],[0,1]);" 
				onkeyup="return showCodeListKey('pd_zeroone',[this,DEALDIRECTIONName],[0,1]);"><input class=codename name=DEALDIRECTIONName value="1" readonly="readonly">
        </TD> 

	<TD  class= title STYLE="display:none;">账户转出计算标志</TD>
        <TD  class= input STYLE="display:none;">
			<Input class="codeno" name=CALFLAG readonly="readonly" value="0" ondblclick="return showCodeList('pd_zeroone',[this,CALFLAGName],[0,1]);" onkeyup="return showCodeListKey('pd_zeroone',[this,CALFLAGName],[0,1]);"><input class=codename value="0" name=CALFLAGName readonly="readonly">
        </TD> 
  	</tr>
	<tr class= common>
   <TD  class= title STYLE="display:none;">账户产生位置</TD>
        <TD  class= input STYLE="display:none;">
					<Input class="codeno" name=ACCCREATEPOS value="1" readonly="readonly" ondblclick="return showCodeList('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);" onkeyup="return showCodeListKey('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);"><input class=codename name=ACCCREATEPOSName  value="投保单录入时产生" readonly="readonly">
        </TD> 
        
	
	</tr> 
</table>



<div align=left id=savabuttonid>
<input value="重  置" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" ></div>
 
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
</BR></BR>

<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskAccGet">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
