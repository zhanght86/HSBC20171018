 
<%
	 /**
	 * Created by IntelliJ IDEA.
	 * User: jinsh
	 * Date: 2009-1-7
	 * Time: 15:32:15
	 * To change this template use File | Settings | File Templates.
	 */
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String tGrpContNo = "";
	String tLoadFlag = "";
	try {
		tGrpContNo = request.getParameter("GrpContNo");
		tLoadFlag = request.getParameter("LoadFlag");
	} catch (Exception e) {
		tGrpContNo = "";
		tLoadFlag = "";
	}
%>
<head>
<script type="text/javascript">
var GrpContNo="<%=tGrpContNo%>";
var EdorAcceptNo="<%=request.getParameter("EdorAcceptNo")%>"
var LoadFlag="<%=tLoadFlag%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script src="../common/javascript/Common.js" type="text/javascript"></script>
<script src="../common/javascript/MulLine.js" type="text/javascript"></script>
<script src="../common/javascript/EasyQuery.js" type="text/javascript"></script>
<script src="../common/javascript/VerifyInput.js" type="text/javascript"></script>
<script src="../common/cvar/CCodeOperate.js" type="text/javascript"></script>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"
	type="text/javascript"></script>
<script src="../common/easyQueryVer3/EasyQueryCache.js"
	type="text/javascript"></script>
<script src="WorkDayDef.js" type="text/javascript"></script>
<link href="../common/css/Project.css" rel="stylesheet" type="text/css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
<%@include file="WorkDayDefInit.jsp"%>
<title>工作日定制</title>
</head>
<body onLoad="initForm(); initElementtype();">
<form method="post" name="fm" id="fm" target="fraSubmit" action="">
<table>
	<tr>
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" onClick="showPage(this,divDef);" alt=""></td>
		<td class="titleImg">定制信息</td>
	</tr>
</table>
<div id="divDef" style="display: ''">
 <div class="maxbox" >
<table class="common">
	<tr class="common">
		<td class="title5">工作日类型</td>
		<td class="input5"><input class="codeno" name="CalendarType"id="CalendarType"
			verify="工作日类型|NotNull&amp;num&amp;len=2"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('CalendarType',[this,CalTypeName],[0,1]);" 
			ondblclick="return showCodeList('CalendarType',[this,CalTypeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeLisKey('CalendarType',[this,CalTypeName],[0,1],null,null,null,1);"><input
			class=codename name=CalTypeName readonly=true elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">其他属性</td>
		<td class="input5"><input class="common wid " name="OtherProp"id="OtherProp"></td>
	</tr>
	<tr class="common">
		<td class="title5">定制年份</td>
		<td class="input5"><input class="common wid " name="Year"id="Year"
			verify="定制年份|NotNull&amp;num&amp;len=4" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">定制月份</td>
		<td class="input5"><input class="common wid " name="Month" id="Month"></td>
	</tr>
	<tr class="common">
		<td class="title5">上午工作开始时间</td>
		<td class="input5"><input class="common wid" name="AmBegin" id="AmBegin"
			verify="上午工作开始时间|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">上午工作结束时间</td>
		<td class="input5"><input class="common  wid " name="AmEnd" id="AmEnd"
			verify="上午工作结束时间|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>

	</tr>
	<tr class="common">
		<td class="title5">下午工作开始时间</td>
		<td class="input5"><input class="common wid" name="PmBegin" id="PmBegin"
			verify="下午工作开始时间|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">下午工作结束时间</td>
		<td class="input5"><input class="common  wid" name="PmEnd" id="PmEnd"
			verify="下午工作结束时间|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>

	</tr>
</table>
</div>
</div>
<!--<input value="  查  询  " class="cssButton" name="updateClickButton" type="button" onClick="easyQueryClick();"> 
<input value="定制工作表" class="cssButton" name="updateClickButton" type="button" onclick="submitForm();">-->
<a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
<a href="javascript:void(0);" class="button"onclick="submitForm();">定制工作表</a>
<br><br>
<div id="divWorkDayGrid" style="display:''">
<table class="common">
	<tr class="common">
		<td text-align: left colspan="1"><span id="spanWorkDayGrid">
		</span></td>
	</tr>
</table>
<!--<input value="首页" class="cssButton90" type="button"onclick="turnPage.firstPage();">
 <input value="上一页"class="cssButton91" type="button" onClick="turnPage.previousPage();">
 <input value="下一页" class="cssButton92" type="button"onclick="turnPage.nextPage();">
  <input value="尾页" class="cssButton93" type="button" onClick="turnPage.lastPage();">--></div>
<br>
<!--<input value="保  存" class="cssButton" name="updateClickButton"
	type="button" onClick="updateClick();">-->
    <a href="javascript:void(0);" class="button"onClick="updateClick();">保  存</a>
    <br><br><br><br>
    </form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
