 <html>
<%
//程序名称 :FIRuleDefQueryInput.jsp
//程序功能 :校验规则定义
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
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRuleDefQueryInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDefQueryInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRuleDefQuery);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </tr>
  </table>
  
  <Div id= "divFIRuleDefQuery" style= "display: ''">
  <div class="maxbox">
 		<table class= common>
 			<tr class= common>
				 <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
					  校验规则编码
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RuleID id=RuleID readonly=true >
				</TD>
				<TD class= title5>
					  校验规则名称
				 </TD>
				 <TD class=input5>
				 	<Input class="wid" class=common name=RuleName id=RuleName >
				</TD>
		</tr>
		<tr class= common>
				<TD class= title5>
					  校验处理方式
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RuleDealMode id=RuleDealMode verify="校验处理方式|NOTNULL"  CodeData="0|^1|类处理^2|SQL处理 " ondblClick="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);"><input class=codename name=RuleDealModeName id=RuleDealModeName readonly=true  >
				</TD>
	 </tr>	 
	</table>
		 <!--<input class="cssButton" type=button value="查  询" onclick="QueryForm()">-->
   	<a href="javascript:void(0);" class="button" onClick="QueryForm();">查    询</a> 
	</div>
	</Div>
  	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDefQueryGrid);">
    		</td>
    		<td class= titleImg>
    			 校验规则信息查询结果
    		</td>
    	</tr>
    </table>
		<Div  id= "divFIRuleDefQueryGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanFIRuleDefQueryGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        
        <!--<div align="left"><input class="cssButton" type=button value="返  回" onclick="ReturnData()"></div>-->
       <center> 
      <INPUT VALUE="首  页" TYPE=Button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="上一页" TYPE=Button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="下一页" TYPE=Button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="尾  页" TYPE=Button onclick="turnPage.lastPage();" class="cssButton93"></center>
   </Div>
   <a href="javascript:void(0);" class="button" onClick="ReturnData();">返    回</a>
   <input type=hidden id="OperateType" name="OperateType">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
