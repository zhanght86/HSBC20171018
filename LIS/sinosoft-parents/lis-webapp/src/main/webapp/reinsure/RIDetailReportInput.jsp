<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js">
	</SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="RIDetailReportInput.js"></SCRIPT> 
	<%@include file="RIDetailReportInit.jsp"%>
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
				<Div  id= "divCessGetData" style= "display: ''" >
			<table class=common border=0>
			  	<TR  class=common>
			  	<TD class= title5>报表类型</TD>
					<TD class= input5>
						<input class="codeno" name="ReportType" 
				    ondblclick="return showCodeList('rireporttype', [this,ReportTypeName],[0,1],null,null,null,1);" 
				    onkeyup="return showCodeListKey('rireporttype', [this,ReportTypeName],[0,1],null,null,null,1);" nextcasing=''
			    	><input class="codename" verify="报表类型|NOTNULL" elementtype=nacessary  name="ReportTypeName" >
					</TD>
			  <td class=title id="RICoCode" style="display: ''">分保公司</td>
				<td class=input id="RICoName" style="display: ''"><Input
					class="codeno" name="RIComCode"
					ondblClick="showCodeList('riincompany',[this,RIComName],[0,1],null,null,null,1,250);"
					onkeyup="showCodeListKey('riincompany',[this,RIComName],[0,1],null,null,null,1,250);"><Input
					class=codename name='RIComName' elementtype=nacessary></td>
			   	</TR>
				<TR >
					<TD class= title5>统计起期</TD>
					<TD class= input5><Input name=RValidate class="coolDatePicker" onClick="laydate({elem: '#RValidate'});" dateFormat='short'  id="RValidate">
<span class="icon"><a onClick="laydate({elem: '#RValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					
					
					<TD class= title5>统计止期</TD>
				<TD class= input5><Input name=RInvalidate class="coolDatePicker" onClick="laydate({elem: '#RInvalidate'});" dateFormat='short'  id="RInvalidate">
<span class="icon"><a onClick="laydate({elem: '#RInvalidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					
				</TR>			
			</table>
		 	
			<hr>
		
			<INPUT class=cssButton  VALUE="统   计" TYPE=button onClick="StatisticData();">
			<INPUT class=cssButton  VALUE="重   置" TYPE=button onClick="ResetForm();">	
		</Div>
	    <input type="hidden" name=OperateType value="">
	    <input type="hidden" name="Operator" value="<%=Operator%>">   
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
