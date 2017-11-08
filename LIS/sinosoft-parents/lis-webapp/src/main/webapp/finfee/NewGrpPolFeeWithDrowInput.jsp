<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：NewGrpPolFeeWithDrowInput.jsp
//程序功能：新单的溢交退费
//创建日期：2007-12-11
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<% 
	//Add by lujun 2006-10-8 20:49
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var ManageCom = "<%=tGI.ComCode%>"; //记录登陆机构
	if(ManageCom.length != 6)
{
	//alert(manageCom);
	alert("溢交退费操作只能在六位机构下进行，请重新选择六位机构登陆！");
}
</script>
<html>
<head>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="NewGrpPolFeeWithDrowInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NewGrpPolFeeWithDrowInputInit.jsp"%>
</head>
<body  onload="initForm();" >
	<form name=fm id="fm" action=./NewGrpPolFeeWithDrow.jsp target=fraSubmit method=post>
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPersonSingle);">
				</td>
				<td class= titleImg>团体新单溢交保费退费</td>
			</tr>
		</table>
		<div class="maxbox1">
		<Div id="divPersonSingle" style= "display: ''">
			<Table  class= common width="100%">
				<TR  class= common>
					<TD  class= title5>团体合同保单号</TD>
					<TD  class= input5>
						<Input class= "common wid" name=GrpContNo id="GrpContNo">
					</TD>
					<TD  class= title5>团体合同印刷号</TD>
					<TD  class= input5>
						<Input class= "common wid" name=PrtNo id="PrtNo">
					</TD>
				</TR>
				<TR  class= common>
					<TD  class= title5>投保单位名称</TD>
					<TD  class= input5>
						<Input class= "common wid" name=GrpName id="GrpName">
					</TD>
					<TD  class= title5>代理人编码</TD>
					<TD  class= input5>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=AgentCode maxlength=10 onclick="return queryAgent();" ondblclick="return queryAgent();" id="AgentCode">	
					</TD>
				</TR>
			</table>
			</Div>
		</div>
			<br>
			<TR class= common>
				<td class=title>
					<a href="javascript:void(0)" class=button onclick="queryClick();">查  询</a>
					<a href="javascript:void(0)" class=button onclick="submitForm();">确认退费</a>
					<!-- <input class="cssButton" value="查  询" type=button onclick="queryClick()">
					<INPUT class="cssButton" VALUE="确认退费" TYPE=button onclick="submitForm()"> -->
				</td>
				<td class=input>						
					请到综合打印打印通知书					
				</TD>
			</TR>			
		<br>
		<Table>
	    	<TR>
	        	<TD class=common>
		           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
	    		</TD>
	    		<TD class= titleImg>
	    			 溢交保费退费信息
	    		</TD>
	    	</TR>
    	</Table>    	
    	<Div  id= "divFinFee1" style= "display: ''" align=center>
	       <Table  class= common>
	         <TR  class= common>
	          <TD text-align: left colSpan=1>
	            <span id="spanFinFeeWithDrawGrid" ></span> 
	  	      </TD>
	        </TR>
	       </Table>					
	       <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
	       <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
	       <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
	       <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    	</Div>
      </table>
	<input type=hidden id="ActuGetNo" name="ActuGetNo">
	<input type=hidden name="ManageCom" value=<%=tGI.ComCode%> id="ManageCom">
	</form>
	<br>
	<br>
	<br>
	<br>
</body>
</html>
