
<%
	//程序名称:个险理赔报表打印
	//程序功能:个险理赔报表打印
	//创建日期：2009-05-25
	//创建人  ：mw
	//更新记录：  更新人    更新日期     更新原因/内容
	// * <p>claim1：1机构理赔时效</p>
	// * <p>claim2：2机构操作时效</p>
	// * <p>claim3：3核赔人操作时效</p>
	// * <p>claim4：4调查率</p>
	// * <p>claim5：5调查时效</p>
	// * <p>claim6：6查勘费用统计</p>
	// * <p>claim7：7赔款和件数</p>
	// * <p>claim8：8赔款和件数（按销售渠道）</p>
	// * <p>claim9：9赔款和件数（按险种分类）</p>
	// * <p>claim10：10结案率</p>
	// * <p>claim11：11短期出险统计</p>
	// * <p>claim12：12退回率</p>
	// * <p>claim13：13撤件率</p>
	// * <p>claim14：14回退率</p>
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String ComCode = tG1.ComCode;
	String Operator = tG1.Operator; //操作员

	String CurrentDate = PubFun.getCurrentDate();
	String mDay[] = PubFun.calFLDate(CurrentDate);

	String Code = request.getParameter("Code");
	loggerDebug("ClaimReport","url的参数为:" + Code);

	LDMenuDB tLDMenuDB = new LDMenuDB();
	String name = "../f1print/ClaimReport.jsp?Code=" + Code;
	tLDMenuDB.setRunScript(name);
	LDMenuSet mLDMenuSet = tLDMenuDB.query();
	String CodeName = mLDMenuSet.get(1).getNodeName();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="Project.css" rel=stylesheet type=text/css>
<script type="text/javascript">
var StrSql = "1 and char_length(comcode)<8 and comcode like #"+<%=ComCode%>+"%#";
function initForm(){
	//alert("进入initform");
  var code="<%=Code%>";
  if(code=="claim12"){ //退回率，需要选择统计类型：1-分公司退回率，2-总公司退回率
    divStatType.style.display='';
  }
  if(code=="claim3"){ //核赔人
    divOperator.style.display='';
  }
  if(code == "claim9"){ //赔款和件数（按险种分类）
    divRiskApp.style.display = '';
  }
  if(code == "EverySection"){
	  divCaseSection.style.display = '';
	  
  }
}

function submitForm(){	
	//alert("进入submitform函数");
	//校验页面信息
	if(verifyInput() == false)
	{
    	return false;
	}
	if(dateDiff(fm.StartDate.value,fm.EndDate.value,'M') > 6){
		alert("时间间隔必须小于六个月");
		return false;
	}	
	
	
	
    var code="<%=Code%>";
	if(code=="claim12" && fm.StatType.value==""){
	  alert("退回率报表需要录入统计类型!");
	  return false;
	}
	if(code == "EverySection" && fm.CaseSection.value==""){
		alert("案件阶段不能为空");
		return false;
	}
	//alert("submit之前");
	fm.submit();
	//alert("submit之后");
   }  
</script>
</head>

<body onLoad="initForm()">
<form action="./ClaimReportF1Print.jsp" method=post name=fm id="fm"
	target="PRINT">
     <div class="maxbox1" >
<Table class=common>
	<TR class=common>
		<TD class=title5>报表名称</TD>
		<TD class=input5><input  readonly class="readonly wid" name=CodeName id="CodeName" value=<%=CodeName%>></TD>

		<TD class=title5>统计机构</TD>
		<TD class=input5><Input class=codeno name="MngCom"
			verify="统计机构|NOTNULL"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,MngComName],[0,1],null,StrSql,1,1);"
			ondblclick="return showCodeList('station',[this,MngComName],[0,1],null,StrSql,1,1);"
			onkeyup="return showCodeListKey('station',[this,MngComName],[0,1],null,StrSql,1,1);"><input
			class=codename name="MngComName" readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
	</TR>
	<TR class=common>
		<TD class=title5>起始日期</TD>
		<TD class=input5>
            <input class="coolDatePicker" dateFormat="short"value="<%= mDay[0]%>" name="StartDate"  id="StartDate" onClick="laydate({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
             <font color='#ff0000'><b>*</b></font></TD>
		<TD class=title5>结束日期</TD>
		<TD class=input5>
            <input class="coolDatePicker"dateFormat="short" value="<%= mDay[1]%>" name="EndDate"  id="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color='#ff0000'><b>*</b></font></TD>
	</TR>
	<TR class=common>
		<TD class=input5 style="display: none"><Input class=common
			name=Code value=<%=Code%>></TD>
	</TR>
</Table>
</div>
<div id="divStatType" style="display:none">
<Table class=common>
	<TR class=common>
		<TD class=title5>统计类型</TD>
		<TD class=input5><Input class=codeno name=StatType
			CodeData="0|^1|分公司退回率^2|总公司退回率"
			ondblClick="showCodeListEx('StatType',[this,StatTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('StatType',[this,StatTypeName],[0,1]);"><Input
			class=codename name=StatTypeName readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</Table>
</div>

<div id="divCaseSection" style="display:none">
<Table class=common>
	<TR class=common>
			<TD class=title>案件阶段</TD>
		<TD class=input><Input class=codeno name="CaseSection" 
			CodeData="0|^1|申请^2|受理^3|立案^4|审核^5|调查^6|分公司签批^7|总公司签批"
			
			ondblclick="return showCodeListEx('CaseSection',[this,CaseSectionName],[0,1]);"
			onkeyup="return showCodeListKeyEx('CaseSection',[this,CaseSectionName],[0,1]);"><input
			class=codename name="CaseSectionName"  readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
</div>





<div id="divOperator" style="display: none">
<Table class=common>
	<TR class=common>
		<TD class=title5>核赔人</TD>
		<TD class=input5><Input class=codeno name="defaultOperator" 
			ondblclick="return showCodeList('optname',[defaultOperatorName,this],[0,1],null,1,1,1);"
			onkeyup="return showCodeListKey('optname',[defaultOperatorName,this],[0,1],null,1,1,1);"><input
			class=codename name="defaultOperatorName"  readonly=true></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</Table>
</div>

<div id="divRiskApp" style="display:none">
<Table class=common>
	<TR class=common>
			<TD class=title>险种代码</TD>
		<TD class=input><Input class=codeno name="Risk" value="全部"
			verify="险种代码|NOTNULL"
			ondblclick="return showCodeList('selrisk',[this,RiskName],[0,1],null,null,1,1);"
			onkeyup="return showCodeListKey('selrisk',[this,RiskName],[0,1],null,null,1,1);"><input
			class=codename name="RiskName" value="全部" readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
</div>
</div>
<!--<a href="javascript:void(0);" class="button" onclick="submitForm();">打印报表</a>-->

<INPUT class=cssButton VALUE="打印报表" TYPE=button onClick="submitForm()"></input>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray">
</span>
</body>
</html>

