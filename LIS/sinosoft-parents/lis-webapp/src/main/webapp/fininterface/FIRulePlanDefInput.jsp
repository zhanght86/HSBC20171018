<html>
<%
//程序名称 :FIRulePlanDefInput.jsp
//程序功能 :校验计划定义
//创建人 :范昕
//创建日期 :2008-09-16
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
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRulePlanDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRulePlanDefInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIRulePlanDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  <Div id= "divFIRulePlanDef" style= "display: ''">
  <table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,misad);"></td>
    		 <td class= titleImg>
        		校验计划定义
       		 </td>   		 
    	</tr>
    </table>
    <Div id="misad" style="display: ''"><div class="maxbox1">
    <td class=button width="10%" align=right>
				
	</td>
<table class= common border=0 width=100%>
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
		   	   	版本状态
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=VersionState2 id=VersionState2 readonly=true>
		   		 </TD>
	</tr>
	</table>
    </div>
    </div>
   
	<!--<INPUT class=cssButton name="querybutton" VALUE="版本信息查询"  TYPE=button onclick="return VersionStateQuery();">-->
	<a href="javascript:void(0);" name="querybutton" class="button" onClick="return VersionStateQuery();">版本信息查询</a><br><br>
    <div class="maxbox1">
	<table class= common>
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
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RulePlanState id=RulePlanState verify=校验计划状态|NOTNULL" readonly=true  CodeData="0|^0|未启用^1|启用 " ondblClick="showCodeListEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);"><input class=codename name=RulePlanStateName id=RulePlanStateName readonly=true elementtype = nacessary >
				</TD>
				<TD class= title5>
					  调用节点ID
				 </TD>
				 <TD class=input5>
		     	 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CallPointID id=CallPointID verify="调用节点ID|NOTNULL"  ondblClick="showCodeList('callpoint',[this,CallPointIDName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpoint',[this,CallPointIDName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpoint',[this,CallPointIDName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=CallPointIDName id=CallPointIDName readonly=true elementtype=nacessary>					
		    	</TD>
	 </tr>
	 
	 <tr class= common>
    <TD class= title5>
					  描述
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=MarkInfo idMarkInfo >
				</TD>
   </tr>
	</table>
		 
	</table>
    <INPUT VALUE="校验计划信息查询" TYPE=button class= cssButton onclick="queryClick()">
	<INPUT VALUE="添加" TYPE=button class= cssButton name= addbutton onclick="submitForm()">   
  <INPUT VALUE="修改" TYPE=button class= cssButton name= updatebutton onclick="updateClick()">
  <INPUT VALUE="删除" TYPE=button class= cssButton name= deletebutton onclick="deleteClick()">
  <INPUT VALUE="重置" TYPE=button class= cssButton name= resetbutton onclick="resetAgain()">
  <br><br>
   <!-- <a href="javascript:void(0);" class="button" onClick="queryClick();">校验计划信息查询</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="submitForm();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">重    置</a>-->
</Div>
</div>
<hr class="line"></hr>
			 <!--<INPUT class=cssButton name="FIRulePlanDefDetailInputbutton" VALUE="校验计划明细定义"  TYPE=button onclick="return FIRulePlanDefDetailInputClick();">-->
             <a href="javascript:void(0);" name="FIRulePlanDefDetailInputbutton" class="button" onClick="return FIRulePlanDefDetailInputClick();">校验计划明细定义</a>
 <input type=hidden id="OperateType" name="OperateType">
 <input type=hidden name="Sequence" value=''>
 <INPUT type=hidden name=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
