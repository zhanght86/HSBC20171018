<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDDutyGetAliveInput.jsp
 //程序功能：责任给付生存
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
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
		
 		<SCRIPT src="PDSugTabHeadConf.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		 <%@include file="PDSugTabHeadConfInit.jsp"%>
		 <script type="text/javascript">
			var riskCode = '<%=request.getParameter("riskcode")%>';
			var contOpt = '<%=request.getParameter("contopt")%>';
  		</script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm(),initElementtype();">
<form action="./PDSugTabHeadConfSave.jsp" method=post name=fm target="fraSubmit">	
		<table class=common>
				<tr class=common>
					<td class=title5>
表头类型:
					</td>
					<td class=input5>
						<input class=codeno name="TYPE" readonly="readonly" verify="表头类型|NOTNUlL" ondblclick="return showMyCodeList('head',[this,TYPEName],[0,1]);" onkeyup="return showMyCodeListKey('head',[this,TYPEName],[0,1]);"><input class=codename name="TYPEName" readonly="readonly">
						<input value="类型管理" name="btnEdit" onClick="typeManage()" class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
						<input value="查   询" name="btnEdit" onClick="queryChangedGrid()" class="cssButton" type="button">
					</td>
					<td class=title5>
					</td>
					<td class=input5>
						
					</td>
				</tr>
		</table>
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDef);">
				</td>
				<td class= titleImg>表头定制信息</td>
			</tr>
		</table>
    
 <Div  id= "divWorkDayGrid" style="display:''">    
        <table  class= common>
          <tr  class= common>
            <td style="text-align:left;" colSpan=1>
             <span id="spanTabHeadConfGrid" >
             </span> 
           </td>
         </tr>
       </table>  
        <INPUT VALUE="首页" class="cssButton" TYPE=button onclick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton" TYPE=button onclick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton" TYPE=button onclick="turnPage.nextPage();">
        <INPUT VALUE="尾页" class="cssButton" TYPE=button onclick="turnPage.lastPage();">       
</Div>
 <INPUT VALUE="保  存" class =cssButton name=saveButton TYPE=button onclick="save();" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
<Div  id= "divSaveWorkDayGrid" style="display:none;">    
        <table  class= common>
          <tr  class= common>
            <td style="text-align:left;" colSpan=1>
             <span id="spanSaveTabHeadConfGrid" >
             </span> 
           </td>
         </tr>
       </table>
</Div>
<table>
	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDef);">
		</td>
		<td class= titleImg>以选中表头信息</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1>
			<span id="spanMulline10Grid"> </span>
		</td>
	</tr>
</table>
   <BR>
    <INPUT VALUE="表头预览" class =cssButton name=updateClickButton TYPE=button onclick="profitHeadPreview();">
    <INPUT VALUE="返  回" class =cssButton name=updateClickButton TYPE=button onclick="returnParent();">
    <input type=hidden name="operator" value=<%=request.getParameter("operator")%> >
	<input type=hidden name="tableName" value="PD_LMRiskProfitHead">
	<input type=hidden name=IsReadOnly>
	<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%> >
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

