 <html>
<%
//程序名称 :FIRulePlanDefQueryInput.jsp
//程序功能 :校验计划定义
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
<SCRIPT src = "FIRulePlanDefQueryInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRulePlanDefQueryInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulePlanDefQuery);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </tr>
  </table>
  <Div id= "divFIRulePlanDefQuery" style= "display: ''">
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
					  校验计划编码
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RulesPlanID id=RulesPlanID readonly=true>
				</TD>
				<TD class= title5>
					  校验计划名称
				 </TD>
				 <TD class=input5>
				 	<Input class="wid" class=common name=RulesPlanName id=RulesPlanName >
				</TD>
		</tr>
		<tr class= common>
				<TD class= title5>
					  校验计划状态
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RulePlanState id=RulePlanState verify=校验计划状态|NOTNULL"  CodeData="0|^0|未启用^1|启用 " ondblClick="showCodeListEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);"><input class=codename name=RulePlanStateName id=RulePlanStateName readonly=true elementtype = nacessary >
				</TD>
				<TD class= title5>
					  调用节点ID
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CallPointID id=CallPointID verify=调用节点ID|NOTNULL"  CodeData="0|^1|采集^2|凭证生成^3|凭证汇总 " ondblClick="showCodeListEx('CallPointID',[this,CallPointIDName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('CallPointID',[this,CallPointIDName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('CallPointID',[this,CallPointIDName],[0,1],null,null,null,[1]);"><input class=codename name=CallPointIDName id=CallPointIDName readonly=true elementtype = nacessary >
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
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRulePlanDefQueryGrid);">
    		</td>
    		<td class= titleImg>
    			 校验计划信息查询结果
    		</td>
    	</tr>
    </table>
    
		<Div  id= "divFIRulePlanDefQueryGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanFIRulePlanDefQueryGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        
       <!-- <div align="left"><input class="cssButton" type=button value="返  回" onclick="ReturnData()"></div>-->
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
