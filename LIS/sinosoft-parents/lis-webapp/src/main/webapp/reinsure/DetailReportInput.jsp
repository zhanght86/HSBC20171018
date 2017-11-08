<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="DetailReportInput.js"></SCRIPT> 
	<%@include file="DetailReportInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initElementtype();initForm();">    
	<form action="" method=post name=fm target="fraSubmit" >
		<div style="width:200">
			<table class="common">
				<tr class="common">
					<td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
					<td class="titleImg">再保报表</td>
				</tr>
			</table>
		</div>
		<br>
		<Div  id= "divCessGetData" style= "display: ''" >
			<table class=common border=0>
			  	<TR  class=common>
			  		<TD class= title5>管理机构</TD>
					<TD class=input5>
						 <Input class=codeno name=ManageCom ondblclick="return showCodeList('managecom1',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('managecom1',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class= title5>统计起期</TD>
					<TD class= input5><Input name=RValidate class="coolDatePicker" onClick="laydate({elem: '#RValidate'});" dateFormat='short'  id="RValidate">
<span class="icon"><a onClick="laydate({elem: '#RValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD class= title5>统计止期</TD>
			   		<TD class= input5><Input name=RInvalidate class="coolDatePicker" onClick="laydate({elem: '#RInvalidate'});" dateFormat='short'  id="RInvalidate">
<span class="icon"><a onClick="laydate({elem: '#RInvalidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			   	</TR>
				<TR>
					<TD class=title5>
						<Div id= "divTitle1" style= "display: ''" >分保公司</Div>
					</TD>
					<TD  class= input5 > 
						<Div  id= "divInput1" style= "display: ''" >
						<Input class="codeno" name="RIcomCode" 
							ondblClick="showCodeList('lrcompanysta',[this,RIcomName],[0,1],null,null,fm.ContNo.value,1);"
							onkeyup="showCodeListKey('lrcompanysta',[this,RIcomName],[0,1],null,null,fm.ContNo.value,1);" verify="分保公司|NOTNULL" ><Input 
							class=codename name='RIcomName'  elementtype=nacessary >
						</Div>
					</TD>
					<TD></TD>
					<TD></TD>
				</TR>

				<TR class= common>
					<TD class=title5>
						<Div id= "divTitle4" style= "display:none;">保单类型</Div>
					</TD>
		      		<TD class= input5 >
			      		<Div  id= "divInput4" style= "display:none;">
			      			<input class=codeno readonly="readonly" name="TempType" CodeData="0|^1|合同分保^2|临时分保|^||" 
		        			ondblclick="return showCodeListEx('State', [this,TempTypeName],[0,1],null,null,null,1);" 
		        			onkeyup="return showCodeListKeyEx('State', [this,TempTypeName],[0,1],null,null,null,1);"><input 
		        			class=codename name=TempTypeName readonly="readonly" >
		        		</Div>
		      		</TD>
		      		<TD class=title >
		      			<Div id="divTitle2" style="display:none;">合同名称</Div>
		      		</TD>
					<TD class=input > 
						<Div  id= "divInput2" style= "display:none;" >
							<Input class="codeno" name="ContNo" 
						ondblClick="showCodeList('lrcontno',[this,ContName],[0,1],null,null,null,1,260);"
						onkeyup="showCodeListKey('lrcontno',[this,ContName],[0,1],null,null,null,1,260);" ><Input 
						class= codename name='ContName' >
					</Div>
					</TD> 
					<TD  class= title5 >
						<Div id="divTitle3" style="display:none;">分保险种</Div>
					</TD>
					<TD  class= input5 > 
						<Div id= "divInput3" style= "display:none;" >
							<Input class="codeno" name="ReRiskCode" 
						ondblClick="showCodeList('lrriskcode',[this,ReRiskName],[0,1],null,null,fm.ContNo.value,1,250);"
						onkeyup="showCodeListKey('lrriskcode',[this,ReRiskName],[0,1],null,null,fm.ContNo.value,1,250);" ><Input 
						class=codename name='ReRiskName'>
					</Div>
					</TD>
<!--
					<TD class=title >报表类型 </TD>
					<TD  class=input > 
						<Input class="codeno" name="ReprotType" 
						ondblClick="showCodeList('lreport',[this,ReprotTypeName],[0,1],null,null,fm.ContNo.value,1,250);"
						onkeyup="showCodeListKey('lreport',[this,ReprotTypeName],[0,1],null,null,fm.ContNo.value,1,250);"  verify="报表类型|NOTNULL" ><Input 
						class=codename name='ReprotTypeName' elementtype=nacessary >
					</TD>
-->					
				</TR>

			</table>
		    <br>		
			<hr>
			<br>
			<INPUT class=cssButton  VALUE="统   计" TYPE=button onClick="StatisticData();">
			<INPUT class=cssButton  VALUE="重置" TYPE=button onClick="ResetForm();">	
		</Div>
	    <input type="hidden" name=OperateType value="">
	    <input type="hidden" name="Operator" value="<%=Operator%>">   
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 