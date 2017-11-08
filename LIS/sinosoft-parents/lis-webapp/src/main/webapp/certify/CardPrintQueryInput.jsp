<%
//程序名称：CardPrintQueryInput.jsp
//程序功能：
//创建日期：2002-10-14 10:20:49
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./CardPrintQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./CardPrintQueryInit.jsp"%>
  <title>单证印刷表 </title>
</head>
<body  onload="initForm();" >
  <form action="./CardPrintQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
      <table class="common">
        <tr class="common">
          <td class="title">印刷批次号</td>
          <td class="input"><input class="wid common" name="PrtNo"></td>
          
          <td class="title">单证编码</td>
          <td class="input"><input class="code" name="CertifyCode" ondblclick="return showCodeList('CardCode', [this]);" onkeyup="return showCodeList('CardCode', [this]);"></td>
          
        <tr class="common">
          <td class="title">最大金额</td>
          <td class="input"><input class="wid common" name="MaxMoney"></td>
          
          <td class="title">最大日期</td>
          <td class="input"><input class="wid common" name="MaxDate"></td></tr>
          
        <tr class="common">
          <td class="title">单证价格</td>
          <td class="input"><input class="wid common" name="CertifyPrice"></td>
          
          <td class="title">管理机构</td>
          <td class="input"><input class="wid common" name="ManageCom"></td></tr>
          
        <tr class="common">
          <td class="title">起始号</td>
          <td class="input"><input class="wid common" name="StartNo"></td>
          
          <td class="title">终止号</td>
          <td class="input"><input class="wid common" name="EndNo"></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title">厂商编码</td>
          <td class="input"><input class="wid common" name=ComCode ></td>
          
          <td class="title">电话</td>
          <td class="input"><input class="wid common" name=Phone ></td></tr>

        <tr class="common">
          <td class="title">定单操作员</td>
          <td class="input"><input class="wid common" name="OperatorInput"></td>
          
          <td class="title">联系人</td>
          <td class="input"><input class="wid common" name=LinkMan ></td></tr>
          
        <tr class="common">
          <td class="title">定单日期</td>
          <td class="input"><input class="wid common" name="InputDate"></td>

          <td class="title">定单操作日期</td>
          <td class="input"><input class="wid common" name=InputMakeDate ></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title">提单操作员</td>
          <td class="input"><input class="wid common" name=OperatorGet ></td>
          
          <td class="title">提单人</td>
          <td class="input"><input class="wid common" name=GetMan ></td></tr>
          
        <tr class="common">
          <td class="title">提单日期</td>
          <td class="input"><input class="wid common" name=GetDate ></td>
          
          <td class="title">提单操作日期</td>
          <td class="input"><input class="wid common" name=GetMakeDate ></td></tr>
          
      </table>

    </table>
          <INPUT VALUE="查询" TYPE=button onclick="submitForm();return false;"> 
          <INPUT VALUE="返回" TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCardPrintGrid);">
    		</td>
    		<td class= titleImg>
    			 单证印刷表结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCertifyPrintGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCertifyPrintGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE=" 首页 " TYPE="button" onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" TYPE="button" onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" TYPE="button" onclick="turnPage.nextPage();"> 
      <INPUT VALUE=" 尾页 " TYPE="button" onclick="turnPage.lastPage();"> 					
  	</div>
  	
  	<input type=hidden name="sql_where">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
