
<%
	//程序名称:团险理赔报表打印
	//程序功能:团险理赔报表打印
	//创建日期：2009-06-01
	//创建人  ：mw
	//更新记录：  更新人    更新日期     更新原因/内容
	// * <p>GrpClaim1：团险理赔月报表</p>
	// * <p>GrpClaim2：团险理赔险种赔付统计报表</p>
	// * <p>GrpClaim3：团险理赔未结案件清单报表</p>
	// * <p>GrpClaim4：不予立案案件清单</p>
	// * <p>GrpClaim5：团险理赔回退案件清单报表</p>
	// * <p>GrpClaim6：团险理赔案件时效报表</p>
	// * <p>GrpClaim7：查勘费用统计</p>
	// * <p>GrpClaim8：核赔人统计清单</p>
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String ComCode = tG1.ComCode;
	String Operator = tG1.Operator; //操作员

	String CurrentDate = PubFun.getCurrentDate();
	String mDay[] = PubFun.calFLDate(CurrentDate);

	String Code = request.getParameter("Code");
	loggerDebug("GrpClaimReport","url的参数为:" + Code);

	LDMenuDB tLDMenuDB = new LDMenuDB();
	String name = "../f1print/GrpClaimReport.jsp?Code=" + Code;
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
<script language="javascript">
function initForm(){
  var code="<%=Code%>";
  if(code=="GrpClaim3"){ //退回率，需要选择统计类型：1-分公司退回率，2-总公司退回率
    divStatType.style.display='';
  }else{
    divStatType.style.display='none';
  }
}

function submitForm(){	
	//校验页面信息
	if(verifyInput() == false)
	{
    	return false;
	}	
	
    var code="<%=Code%>";
	if(code=="GrpClaim3" && fm.StatType.value==""){
	  alert("团险理赔未结案件清单报表需要录入统计类型!");
	  return false;
	}
	
	fm.submit();
   }  
</script>
</head>

<body onLoad="initForm();initElementtype();">
<form action="./GrpClaimReportF1Print.jsp" method=post name=fm id=fm
	target="PRINT">
    <div class="maxbox1" >
<Table class=common>
	<TR class=common>
		<TD class=title5>报表名称</TD>
		<TD class=input5><input  class="readonly wid" name=CodeName value=<%=CodeName%>></TD>
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
	<%
if(Code.equals("GrpClaim2")){
%>
	<TR class=common>
		<TD class=title5>案件类型</TD>
		<TD class=input5> <Input class="codeno"  name=CaseType verify="案件类型|NOTNULL" readonly 
             CodeData="0|^01|简易|^03|批量|^11|普通"
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('CaseType',[this,CaseTypeName],[0,1]);"
            ondblClick="showCodeListEx('CaseType',[this,CaseTypeName],[0,1]);"
            onkeyup="showCodeListKeyEx('CaseType',[this,CaseTypeName],[0,1]);"><input class=codename name=CaseTypeName elementtype=nacessary readonly></TD>
		<TD class=title5>案件结论</TD>
		<TD class=input5><Input class="codeno"  name=CaseResult verify="案件结论|NOTNULL" readonly 
             CodeData="0|^0|给付，部分给付|^1|全部给付" 
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('CaseResult',[this,CaseResultName],[0,1]);"
            ondblClick="showCodeListEx('CaseResult',[this,CaseResultName],[0,1]);"
            onkeyup="showCodeListKeyEx('CaseResult',[this,CaseResultName],[0,1]);"><input class=codename name=CaseResultName elementtype=nacessary readonly></TD></TD>
	</TR>
	<%
	}
%>
<%
if(!Code.equals("GrpClaim8")){
%>
	<TR class=common>
		<TD class="title5">起始日期</TD>
		<TD class="input5">
            <Input class="coolDatePicker"   onClick="laydate({elem: '#StartDate'});"dateFormat="short"value="<%=mDay[0]%>" name="StartDate" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span><font color='#ff0000'><b>*</b></font></TD>
            </TD>
		<TD class=title5>结束日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker"   onClick="laydate({elem: '#EndDate'});"dateFormat="short"value="<%= mDay[1]%>" name="EndDate" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span><font color='#ff0000'><b>*</b></font></TD>
	</TR>
	<%
	}
%>
	<TR class=common>
		<TD class=input style="display: none"><Input class=common
			name=Code value=<%=Code%>></TD>
	</TR>
</Table>
</div>

<div id="divStatType" style="display: 'none'">
<Table class=common>
	<TR class=common>
		<TD class=title5>统计类型</TD>
		<TD class=input5><Input class=codeno name=StatType
			CodeData="0|^0|全部^5|5日未结案件^10|10日未结案件^30|30日未结案件"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('StatType',[this,StatTypeName],[0,1],null,null,null,1);"
			ondblClick="showCodeListEx('StatType',[this,StatTypeName],[0,1],null,null,null,1);"
			onkeyup="showCodeListKeyEx('StatType',[this,StatTypeName],[0,1],null,null,null,1);"><Input
			class=codename name=StatTypeName readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
</div>


<INPUT class=cssButton VALUE="打印报表" TYPE=button onClick="submitForm()">
<!--<a href="javascript:void(0);" class="button"onclick="easyQuery();">打印报表</a>-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray">
</span>
</body>
</html>
<script>
var StrSql = "1 and length(comcode)<8 and comcode like #"+<%=ComCode%>+"%#";
</script>
