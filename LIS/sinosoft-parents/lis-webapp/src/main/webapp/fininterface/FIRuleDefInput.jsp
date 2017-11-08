<html>
<%
//程序名称 :FIRuleDefInput.jsp
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
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRuleDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDefInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIRuleDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  
  <table>
    	<tr> 
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFIRuleDef);"></td>
    		 <td class= titleImg>
        		校验规则定义
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "divFIRuleDef" style= "display: ''"><div class="maxbox1">
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
	
	
    <table>
    	<tr> 
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFIRul);"></td>
    		 <td class= titleImg>
        		校验规则
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "divFIRul" style= "display: ''"><div class="maxbox">
	<table class= common>
		<tr class= common>
				 <TD class= title>
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
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RuleDealMode id=RuleDealMode readonly=true verify="校验处理方式|NOTNULL"  CodeData="0|^1|类处理^2|SQL处理 " ondblClick="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);"><input class=codename name=RuleDealModeName id=RuleDealModeName readonly=true  >
				</TD>
	 </tr>
	 <table>
	 <tr  class= common>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校验返回数据(这里仅仅针对的是返回的为不符合的条件
这里描述的是不符合的原因
比如 账号为空/账号不存在等信息
直接作为错误来源信息保存)</TD>
			</tr>
			<tr  class= common>
				<TD  class= common style="padding-left:10px; padding-top:6px">
					<textarea name="RuleReturnMean" id="RuleReturnMean" verify="校验返回数据|len<4000" verifyorder="1" cols="170%" rows="4" witdh=50% class="common" ></textarea>
				</TD>
			</tr>
		</table>
	</table>
	</div>
    </div>
    <!--<INPUT VALUE="校验规则信息查询" TYPE=button class= cssButton onclick="queryClick()">-->
    <a href="javascript:void(0);" class="button" onClick="queryClick();">校验规则信息查询</a><br><br>
<div class="maxbox1">
	
	<Div  id= "classdiv" style= "display: none" align=left>
	<table class=common>
	 <tr class= common>
				<TD class= title5>
					  处理类名称
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=RuleDealClass id=RuleDealClass >
				</TD> 
				<TD class=title5></TD>	
				<TD class=input5></TD>		
	</tr>
 </table> 	  
</div>	
<Div  id= "sqldiv" style= "display: none" align=left>
	<table class=common>
			<tr  class= common>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校验规则的SQL</TD>
			</tr>
			<tr  class= common>
				<TD  class= common style="padding-left:10px; padding-top:6px">
					<textarea name="RuleDealSQL" verify="校验规则的SQL|len<4000" verifyorder="1" cols="170%" rows="4" witdh=50% class="common" ></textarea>
				</TD>
			</tr>
			
		</table>
</div>	
	<INPUT VALUE="添加" TYPE=button class= cssButton name= addbutton onclick="submitForm()">   
  <INPUT VALUE="修改" TYPE=button class= cssButton name= updatebutton onclick="updateClick()">
  <INPUT VALUE="删除" TYPE=button class= cssButton name= deletebutton onclick="deleteClick()">
  <INPUT VALUE="重置" TYPE=button class= cssButton name= resetbutton onclick="resetAgain()">
  <!--<a href="javascript:void(0);" class="button" name="addbutton" onClick="submitForm();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">重    置</a>-->
	</table>
</Div>
</div>
 <input type=hidden id="OperateType" name="OperateType">
 <input type=hidden name="Sequence" value=''>
 <INPUT type=hidden name=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
