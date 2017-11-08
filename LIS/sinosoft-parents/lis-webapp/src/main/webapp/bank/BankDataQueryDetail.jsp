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
    String cGetNoticeNo = request.getParameter("GetNoticeNo");
    String cPolNo = request.getParameter("PolNo");
    loggerDebug("BankDataQueryDetail","GetNoticeNo" + cGetNoticeNo + "   polno " + cPolNo);
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
  
  <SCRIPT src="./BankDataQueryDetail.js"></SCRIPT>   
  <%@include file="./BankDataQueryDetailInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>续期转账件报盘情况查询</title>
</head>

<body  onload="initForm();" >
  <form action="./BankQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    		<td class= titleImg>报盘数据查询</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" class=maxbox1 style= "display: ''">
		   <Table class= common>
		     <TR class= common > 
	          <TD class= title5 >缴费通知书号</TD>
	          <TD class= input5 ><Input class="readonly wid" readonly name=GetNoticeNo id=GetNoticeNo value=<%=cGetNoticeNo%>></TD>
	          <TD class= title5 >保单号</TD>
	          <TD class= input5 ><Input class="readonly wid" readonly name=PolNo id=PolNo value=<%=cPolNo%>></TD>
		     </TR>
		   	</Table>  
		   	<br>
		    <!--数据区-->
		    <INPUT VALUE="查询" class= cssButton TYPE=button onclick="easyQuery()"> 	
		    <INPUT VALUE="打印" class= cssButton TYPE=button onclick="easyPrint()"> 
		    <INPUT VALUE="关闭" class= cssButton TYPE=button onclick="easyClose()"> 		
		   	<p>
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
