<html>
<%
//程序名称 :FIRuleDealErrLogInput.jsp
//程序功能 :错误数据处理
//创建人 :范昕
//创建日期 :2008-09-09
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。 
 	%>
<script>

  var comcode = "<%=tGI1.ComCode%>";
  var RuleDealBatchNo = <%=request.getParameter("RuleDealBatchNo")%>;
  var DataSourceBatchNo = <%=request.getParameter("DataSourceBatchNo")%>;
  var RulePlanID = <%=request.getParameter("RulePlanID")%>;
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "FIRuleDealErrLogInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDealErrLogInputInit.jsp"%>
</head>
<body  onload="initForm();queryFIRuleDealErrLogGrid();initElementtype();">
  <form action="./FIRuleDealErrLogSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divLLReport1" style= "display: ''">
  	<table class= common border=0 width=100%>
  		<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDealErrLogGrid);">
    		</td>
    		<td class= titleImg>
    			 清单列表
    		</td>
    	</tr>
    </table>
    <Div  id= "divFIRuleDealErrLogGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanFIRuleDealErrLogGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <center>
      <INPUT CLASS="cssButton90" VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS="cssButton93" VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></center>
   </Div>
   
  <table class= common>
	<INPUT VALUE="导出错误清单" class = cssButton TYPE=button onclick="ToExcel();" >  
	<INPUT VALUE="清除错误数据" class = cssButton TYPE=button onclick="DealErrdata();" >  
	<INPUT VALUE="忽略错误" class = cssButton TYPE=button onclick="DealErrdata1();" >  
	</table>   
  <div class="maxbox1">
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  校验批次号码
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RuleDealBatchNo id=RuleDealBatchNo readonly=true >
				</TD>
				<TD class= title5>
					  数据源批次号码
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=DataSourceBatchNo id=DataSourceBatchNo readonly=true >
				</TD>
	</tr>
	</table></div>
 <div class="maxbox1">
<table class= common>
	<tr class= common>
				 <TD class= title5>
					  凭证类型
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=CertificateID id=CertificateID readonly=true >
				</TD>
				<TD class= title5>
					  错误信息
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=ErrInfo id=ErrInfo readonly=true >
				</TD>
	</tr>
	<tr class= common>
				 <TD class= title5>
					  业务号码标识
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=businessno id=businessno readonly=true >
				</TD>
				<TD class= title5>
					  校验计划
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RulePlanID id=RulePlanID readonly=true >
				</TD>
	</tr>
	<tr class= common>
				 <TD class= title5>
					  校验规则
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RuleID id=RuleID readonly=true >
				</TD>
				<TD class= title5>
					  处理状态
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=DealState id=DealState readonly=true >
				</TD>
	</tr>
	</table></div>
  <input type=hidden NAME="OperateType" VALUE=''>
  <input type=hidden NAME="DelErrSerialNo" VALUE=''>
  <input type=hidden NAME="ExpSQL" VALUE=''>
</table>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
