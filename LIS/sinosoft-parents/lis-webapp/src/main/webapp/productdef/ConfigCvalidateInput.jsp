<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>

<%
 //程序名称：ConfigCvalidateInput.jsp
 //程序功能：产品生效日期配置入口
 //创建日期：2015-7-9
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
 String flag=request.getParameter("flag");
%>

<head>

<%
String CurrentDate = PubFun.getCurrentDate();
%>
<script type="text/javascript">
	var today00='<%=CurrentDate%>';
	var flag='<%=flag%>';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="ConfigCvalidateInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ConfigCvalidateInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
 
<body onload="initForm();">
<form action="PDSaleControlSave.jsp" method=post name=fm target="fraSubmit">
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSaleDate);">
		</TD>
		<TD class=title5Img></TD>
	</TR>
</Table>
<div id="divSaleDate" style="display: ''">
	<table class=common>
		<TR class=common>
			<TD  class= title>险种编码</TD>
				
			<TD  class= input ><Input class="codeno" name=RiskCode 
			     ondblclick="showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1);" 
			     onkeyup="showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input 
			     class="codename" name="RiskCodeName" id=RiskCodeName  ><font
				size=1 color='#ff0000'><b> *</b></font>
			</TD>
			<TD class=title5>销售渠道</TD>
			<TD class=input5><input class=codeno name=SaleChnl
				ondblclick="return showCodeList('salechnl',[this,SaleChnlName],[0,1]);"
				onkeyup="return showCodeListKey('salechnl',[this,SaleChnlName],[0,1]);"><input
				class=codename name=SaleChnlName readonly="readonly"
				><font
				size=1 color='#ff0000'><b> *</b></font>
			</TD>
			<TD class=title5>管理机构</TD>
			<TD  class= input>
	           <Input class=codeno name=ManageComGrp
	            ondblclick="return showCodeList('station',[this,ManageComGrpName],[0,1]);"
	            onkeyup="return showCodeListKey('station',[this,ManageComGrpName],[0,1]);" ><input 
	            name=ManageComGrpName class=codename  readonly="readonly" ><font
				size=1 color='#ff0000'><b> *</b></font>
	         </TD>
	
		</TR>
		
		<TR class=common>
			<TD class="title">币  种</TD>
			<TD class="input"><input class="codeno" name="Currency"
				ondblclick="return showCodeList('currency', [this,CurrencyName],[0,1],null,null,null,1);"
				onkeyup="return showCodeListKey('currency', [this,CurrencyName],[0,1],null,null,null,1);"
				nextcasing='' verify="幣種|NOTNULL&code:currency"><input
				class="codename" name="CurrencyName" elementtype=nacessary readonly="readonly">
			</TD>
			<TD class=title5></TD>
			<TD class=title5></TD>
			<TD class=title5></TD>
			<TD class=title5></TD>
	
		    
		</TR>
		
	</table>
	<Table>
		<TR>
			<TD width="10%"></TD>
			<TD><INPUT VALUE="查  询" class=cssbutton TYPE=button
				onclick="easyQueryClick();">
			</TD>
		</TR>
	</Table>
</div>
<div id="divSet" style="display: ''">
	<Table>
		<TR>
			
			<TD class=title5Img></TD>
		</TR>
	</Table>
	<Div id="divConfigCvalidateGrid" style="display: ''" align=center>
	<table  class= common>
	   <tr  class= common>
	      <td style="text-align:left;" colSpan=1>
	     <span id="spanConfigCvalidateGrid" >
	     </span> 
	      </td>
	   </tr>
	</table>
	</DIV>
	
	<div align=right style='margin-top: 1%;margin-right: 11.5%;'>
	    <INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="turnPageN.firstPage();"> 
		<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="turnPageN.previousPage();">      
		<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="turnPageN.nextPage();"> 
		<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="turnPageN.lastPage();">
	</div>
	<div id="divQuery" style="display: '';margin-top:5px;">
		<table class=common>
			<TR class=common>
				<TD  class= title>险种编码</TD>
					
				<TD  class= input ><Input class="codeno" name=ReRiskCode 
				     ondblclick="showCodeList('riskcode',[this,ReRiskCodeName],[0,1],null,null,null,1);" 
				     onkeyup="showCodeListKey('riskcode',[this,ReRiskCodeName],[0,1]);"><input 
				     class="codename" name=ReRiskCodeName id=ReRiskCodeName  ><font
					size=1 color='#ff0000'><b> *</b></font>
			    </TD>
				<TD class=title5>销售渠道</TD>
				<TD class=input5><input class=codeno name=ReSaleChnl
					ondblclick="return showCodeList('salechnl',[this,ReSaleChnlName],[0,1]);"
					onkeyup="return showCodeListKey('salechnl',[this,ReSaleChnlName],[0,1]);"><input
					class=codename name=ReSaleChnlName 
					><font
					size=1 color='#ff0000'><b> *</b></font>
				</TD>
				<TD class=title5>管理机构</TD>
				<TD  class= input>
		           <Input class=codeno name=ReManageComGrp
		            ondblclick="return showCodeList('station',[this,ReManageComGrpName],[0,1]);"
		            onkeyup="return showCodeListKey('station',[this,ReManageComGrpName],[0,1]);" ><input 
		            name=ReManageComGrpName class=codename  ><font
					size=1 color='#ff0000'><b> *</b></font>
		         </TD>
		         
		
			</TR>
			
			<TR class=common>
				<TD class="title">币  种</TD>
				<TD class="input"><input class="codeno" name="ReCurrency"
					ondblclick="return showCodeList('currency', [this,ReCurrencyName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('currency', [this,ReCurrencyName],[0,1],null,null,null,1);"
					nextcasing='' verify="幣種|NOTNULL&code:currency"><input
					class="codename" name="ReCurrencyName" elementtype=nacessary >
				</TD>
				<TD class=title5></TD>
				<TD  class= input>
           		     <Input class= "multiDatePicker" dateFormat="short" name=LISStartDate verify="|NOTNULL">
          		</TD>
			    <TD class=title5></TD>
			    <TD  class= input>
           		     <Input class= "multiDatePicker" dateFormat="short" name=LISEndDate verify="|NOTNULL">
          		</TD> 
			</TR>
			<TR class=common>
				<TD class=title5></TD>
				<TD  class= input>
           		     <Input class= "multiDatePicker" dateFormat="short" name=PPLStartDate verify="|NOTNULL">
          		</TD>
			    <TD class=title5></TD>
			    <TD  class= input>
           		     <Input class= "multiDatePicker" dateFormat="short" name=PPLEndDate verify="|NOTNULL">
          		</TD>
			    <TD class=title5></TD>
				<TD class=title5></TD>
			</TR>
			
		</table>
	</div>
	<table align=right >
		<tr>
			<td height="30px">&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><input type="button" class=cssbutton value="删除"
				onclick="deleteClick(<%=flag%>);"></td>
			<td><input type="button" class=cssbutton value="修改"
				onclick="updateClick(<%=flag%>);"></td>
			<td><input type="button" class=cssbutton value="增加"
				onclick="submitFrom(<%=flag%>);"></td>
			<td width="30%"></td>
		</tr>
	</table>
</div>
<input type=hidden name=Transact>
<input type=hidden name=Flag>
<input type=hidden name=RiskCode2>


</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>

</html>
