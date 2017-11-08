<%
//程序名称：BankDataQuery.jsp
//程序功能：财务报盘数据查询
//创建日期：2004-10-20
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
%>

<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="./BankDataQuery.js"></SCRIPT>   
  <%@include file="./BankDataQueryInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>续期转账件报盘情况查询</title>
</head>

<body  onload="initForm();" >
  <form action="./BankQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);"></td>
    		<td class= titleImg>报盘数据查询</td>
    	</tr>
    </table>
  	<Div  id= "divQuery" class=maxbox1  style= "display: ''">
		   <Table class= common>
		     <TR class= common> 
	          <TD  class= title5>管理机构</TD>
	          <TD  class= input5>
	            <Input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1',null,250);">
				<input class=codename name=ManageComName id=ManageComName readonly=true>
	          </TD>	            				
	          <TD  class= title5>合同号</TD>
	          <TD  class= input5><Input class="common wid" name=PolNo id=PolNo></TD>
		     </TR>
		     <TR  class= common>
	       		<TD  class= title5>开始日期</TD>
	       		<TD  class= input5>
					<Input class= "coolDatePicker" dateFormat="short" name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate">
					<span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
	       		<TD  class= title5>终止日期</TD>
	       		<TD  class= input5>
					<Input class= "coolDatePicker" dateFormat="short" name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate">
					<span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
		     </TR>
		   	</Table>  
		   	<br>
		    <!--数据区-->
		    <INPUT VALUE="查    询" class= cssButton TYPE=button onclick="easyQuery()" name="bnteasyQuery" > 	
		    <INPUT VALUE="明细查询" class= cssButton TYPE=button onclick="QueryDetail()" name="bntQueryDetail" > 	
		    <INPUT VALUE="打    印" class= cssButton TYPE=button onclick="easyPrint()" name="bntPrint" > 	
		   	<p>
  	</div>
  	<Div  id= "divCodeGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1><span id="spanCodeGrid" ></span> </td>
					</tr>
    		</table> 
    		<center>	
      	<INPUT VALUE="首页" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="尾页" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
      </center>	
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
