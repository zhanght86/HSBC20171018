<%@include file="/i18n/language.jsp"%>
<html>
<%
//程序名称：再保合同查询
//程序功能：
//创建日期：2007-02-28
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="LRAccRiskQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<%@include file="LRAccRiskQueryInit.jsp"%>
</head>

<body  onload="initForm();accQuery();" >
<form action="" method=post name=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- 显示或隐藏LLReport1的信息 -->
   <br>
   <table>
   	  <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></td>
    	<td class= titleImg>累计方案列表</td></tr>
    </table>

   <Div id= "divLLReport1" style= "display: ''">

   	<Table class= common>
   		<TR class= common>
   			<TD class= title5>累计方案编号</TD>
   			<TD class= input5>
   				<Input class="readonly" name= AccumulateNo >
   			</TD>
        <td class="title5"></td>
        <td class="input5"></td>
   			<td class="title5"></td>
        <td class="input5"></td>
   		</TR>

    </Table>

		<input class="cssButton" type=button value="查询" onclick="submitForm()">
		<input class="cssButton" type=button value="返回" onclick="ReturnData()" style='display:"none"'>
		<input class="cssButton" type=button value="关闭" onclick="ClosePage()">

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
累计风险列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLLReport2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style="text-align:left;" colSpan=1>
  					<span id="spanAccumulateGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <Div  id= "divPage" align=center style= "display:none;">
      <INPUT CLASS=cssButton VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
  	</div>
  	<input type="hidden" name="PageFlag">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
