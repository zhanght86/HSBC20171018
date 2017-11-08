 <html>
<%
//程序名称 :FIRulePlanDefDetailInput.jsp
//程序功能 :校验计划明细定义
//创建人 :范昕
//创建日期 :2008-09-17
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
  var VersionNo = <%=request.getParameter("VersionNo")%>;
  var RulePlanID = <%=request.getParameter("RulesPlanID")%>;
  var VersionState = <%=request.getParameter("VersionState")%>; 
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRulePlanDefDetailInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRulePlanDefDetailInputinit.jsp"%>
</head>
<body  onload="initForm();queryFIRulePlanDefDetailInputGrid();initElementtype();">
  <form action="./FIRulePlanDefDetailSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divLLReport1" style= "display: ''">
  	<table class= common border=0 width=100%>
  		<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRulePlanDefDetailInputGrid);">
    		</td>
    		<td class= titleImg>
    			 清单列表
    		</td>
    	</tr>
    </table>
		<Div  id= "divFIRulePlanDefDetailInputGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanFIRulePlanDefDetailInputGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
   </Div>
  	<table class= common border=0 width=100%>
  		<table class= common>
 			<tr class= common>
				 <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	 <Input class="readonly wid" name=VersionNo id=VersionNo readonly=true>
				</TD>
					<TD class= title5>
					  校验计划编码
				 </TD>
				 <TD class=input5>
				 	 <Input class="readonly wid" name=RulePlanID id=RulePlanID readonly=true>
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
					  校验规则编码
				 </TD>
				 <TD class=input5>
				 	<Input class=codeno name=RuleID id=RuleID style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="校验规则编码|NOTNULL" ondblClick="showCodeList('RuleID',[this,RuleIDName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKey('RuleID',[this,RuleIDName],[0,1],null,null,null,[1]);">
					<input class=codename name=RuleIDName id=RuleIDName readonly=true elementtype=nacessary>					
				</TD>
					<TD class= title5>
					  校验顺序
				 </TD>
				 <TD class=input5>
				 	 <Input class="readonly wid" name=Sequence id=Sequence readonly=true>
				</TD>
		</tr>
			<tr class= common>
				<TD class= title5>
					  校验规则状态
				 </TD>
				 <TD class=input5>
				 	 <Input class=codeno name=RuleState id=RuleState verify=校验规则状态|NOTNULL" readonly=true  CodeData="0|^0|未启用^1|启用 " style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('RuleState',[this,RuleStateName],[0,1]);" onkeyup="showCodeListKeyEx('RuleState',[this,RuleStateName],[0,1]);">
					 <input class=codename name=RuleStateName id=RuleStateName readonly=true elementtype = nacessary >
				</TD>
				<TD  class=title5></TD>
				<TD  class=input5></TD>
			</tr>
	</table>
	<INPUT VALUE="添加" TYPE=button class= cssButton name= addbutton onclick="submitForm()">
	<INPUT VALUE="修改" TYPE=button class= cssButton name= updatebutton onclick="updateClick()">   
  <INPUT VALUE="删除" TYPE=button class= cssButton name= deletebutton onclick="deleteClick()">
  <INPUT VALUE="重置" TYPE=button class= cssButton name= resetbutton onclick="resetAgain()">
  
  <input type=hidden NAME="OperateType" id=OperateType VALUE=''>
   <input type=hidden NAME=RuleID1 id=RuleID1 VALUE=''>
   <br /><br /><br /><br />
</table>
</Div>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
