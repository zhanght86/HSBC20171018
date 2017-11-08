<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>


<%
//程序名称：RuleInfor.jsp
//程序功能：规则定制基本信息录入
//创建日期：2008-10-09
//创建人  ：  阳纯飞
//更新记录：  

 GlobalInput tG = new GlobalInput();
 tG = (GlobalInput)session.getValue("GI");
 String Operator=tG.Operator;
 loggerDebug("RuleInfor","Operator::"+Operator);
 String LRTemplate_ID=(String)request.getParameter("LRTemplate_ID");
 loggerDebug("RuleInfor","LRTemplateT_ID::"+LRTemplate_ID);
 String LRTemplateName=(String)request.getParameter("LRTemplateName");
 loggerDebug("RuleInfor","LRTemplateName::"+LRTemplateName);
 String flag=(String)request.getParameter("flag");
 loggerDebug("RuleInfor","flag::"+flag);
 String State=(String)request.getParameter("State");
 loggerDebug("RuleInfor","State::"+State);
//flag对应的页面： 1：规则定制 2 ：规则复制 3 规则测试 4：规则修改 5：规则审批 6：规则发布 7：规则作废 8：规则查询 9：规则打印 
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<!--<script type="text/javascript" src="./baseLib/ext-base.js"></script>
<script type="text/javascript" src="./baseLib/ext-all.js"></script>
<script type="text/javascript" src="./baseLib/examples.js"></script>
<script type="text/javascript" src="./baseLib/ext-lang-zh_CN.js"></script>

  --><SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <SCRIPT type='text/javascript' src="./RuleInfor.js"></SCRIPT>
<SCRIPT type='text/javascript' src="./queryForInformation.js"></SCRIPT>
<script src="../common/javascript/MultiCom.js"></script>
<%@include file="./RuleInforInit.jsp" %>
</head>
<script type="text/javascript">
var Operator='<%=Operator%>';
var LRTemplate_ID='<%=LRTemplate_ID%>';
var flag=<%=flag%>;
var State=<%=State%>;
var LRTemplateName='<%=LRTemplateName%>';
</script>
<body onload="initForm();initElementtype();">
<form action="./RuleMake.jsp" method=post name=fm id=fm>
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,RuleBasicInfor);"></TD>
		<TD class=titleImg>规则基本信息录入</TD>
	</TR>
</Table>
<div id="RuleBasicInfor" style="display:''" class="maxbox">
  <Table class="common">
	<TR class="common">
		
		<TD class=title5>规则名</TD>
		<TD class=input5><Input class="wid" class=common name=RuleName id=RuleName
			elementtype=nacessary verify="规则名|NOTNULL" ></TD>
     <TD class=title5>
                          功能描述：
        </TD>	  
		<TD  class=input5>
           <textarea class="common" name=RuleDes cols=38></textarea>
        </TD>
    </TR>
    <TR class="common">
       <TD class=title5>
                          生效日期：
        </TD>	  
		<TD class=input5>
           
           <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="生效日期|NOTNULL&Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           <font color=red>*</font>
           </input>
        </TD>
		<TD class=title5>
                         失效日期：
        </TD>	  
		<TD class=input5>
           
           <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="失效日期|NOTNULL&Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </input>
        </TD>
	</TR>
<TR class="common">
<TD class=title5>
                         级别：
        </TD>	  
		<TD class=input5>
           <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=TemplateLevel id=TemplateLevel /><input
			class=codename name=TemplateLevelName id=TemplateLevelName /> 
            <font color=red>*</font>
        </TD>
       <TD class=title5>业务模块</TD>
				<TD class=input5>
       <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Business id=Business ondblclick="return showCodeList('ibrmsbusiness',[this,BusinessName],[0,1]);" onclick="return showCodeList('ibrmsbusiness',[this,BusinessName],[0,1]);" onkeyup="return showCodeListKey('ibrmsbusiness',[this,BusinessName],[0,1]);" /><input class=codename name=BusinessName id=BusinessName />
            <font color=red>*</font>
       </TD>
</TR>
    <TR class="common">
<TD class=title5>
                         创建者：
    </TD>	  
		<TD class=input5>
           <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Creator id=Creator /><input
			class=codename name=CreatorName id=CreatorName /> 
           <font color=red>*</font>
        </TD>
       <TD class=title5>
                         状态：
        </TD>	  
		<TD class=input5>
          <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State id=State /><input
			class=codename name=StateName id=StateName />
            <font color=red>*</font>
        </TD>
	</TR>
   <TR class="common">
		<TD class=title5>
                         有效标志：
    </TD>	  
		<TD class=input5>
           <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Valid id=Valid /><input
			class=codename name=ValidName id=ValidName />
<font color=red>*</font>
    </TD>
    
     <TD class=title5>规则类型</TD>
			<TD class=input5>
       <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RuleType id=RuleType ondblclick="return showCodeList('ibrmsruletype',[this,'RuleTypeName'],[0,1]);" onclick="return showCodeList('ibrmsruletype',[this,'RuleTypeName'],[0,1]);" onkeyup="return showCodeListKey('ibrmsruletype',[this,'RuleTypeName'],[0,1]);" /><input class=codename name=RuleTypeName id=RuleTypeName />
            <font color=red>*</font>
       </TD>
	</TR>
  </Table>
</div>
<!--<p>
  <Div id= divCmdButton style="display: ''">-->
 <!--<input class=cssButton type="button" value='下一步' onclick="formSubmit(<%=flag%>)"/>-->
 <a href="javascript:void(0);" class="button" onClick="formSubmit(<%=flag%>)">下一步</a>

<!--<input class=cssButton type="button" value='测试' onclick="queryForResult('select * from DTT0113')"/>
  --><!--</Div>-->
<input type="hidden" name="LRTemplateT_Id">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
