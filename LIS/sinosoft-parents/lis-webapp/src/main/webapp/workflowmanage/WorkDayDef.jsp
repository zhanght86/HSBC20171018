 
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
<title>�����ն���</title>
</head>
<body onLoad="initForm(); initElementtype();">
<form method="post" name="fm" id="fm" target="fraSubmit" action="">
<table>
	<tr>
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" onClick="showPage(this,divDef);" alt=""></td>
		<td class="titleImg">������Ϣ</td>
	</tr>
</table>
<div id="divDef" style="display: ''">
 <div class="maxbox" >
<table class="common">
	<tr class="common">
		<td class="title5">����������</td>
		<td class="input5"><input class="codeno" name="CalendarType"id="CalendarType"
			verify="����������|NotNull&amp;num&amp;len=2"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('CalendarType',[this,CalTypeName],[0,1]);" 
			ondblclick="return showCodeList('CalendarType',[this,CalTypeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeLisKey('CalendarType',[this,CalTypeName],[0,1],null,null,null,1);"><input
			class=codename name=CalTypeName readonly=true elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">��������</td>
		<td class="input5"><input class="common wid " name="OtherProp"id="OtherProp"></td>
	</tr>
	<tr class="common">
		<td class="title5">�������</td>
		<td class="input5"><input class="common wid " name="Year"id="Year"
			verify="�������|NotNull&amp;num&amp;len=4" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">�����·�</td>
		<td class="input5"><input class="common wid " name="Month" id="Month"></td>
	</tr>
	<tr class="common">
		<td class="title5">���繤����ʼʱ��</td>
		<td class="input5"><input class="common wid" name="AmBegin" id="AmBegin"
			verify="���繤����ʼʱ��|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">���繤������ʱ��</td>
		<td class="input5"><input class="common  wid " name="AmEnd" id="AmEnd"
			verify="���繤������ʱ��|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>

	</tr>
	<tr class="common">
		<td class="title5">���繤����ʼʱ��</td>
		<td class="input5"><input class="common wid" name="PmBegin" id="PmBegin"
			verify="���繤����ʼʱ��|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>
		<td class="title5">���繤������ʱ��</td>
		<td class="input5"><input class="common  wid" name="PmEnd" id="PmEnd"
			verify="���繤������ʱ��|NotNull&amp;len=8" elementtype="nacessary"><font size=1 color='#ff0000'><b>*</b></font></td>

	</tr>
</table>
</div>
</div>
<!--<input value="  ��  ѯ  " class="cssButton" name="updateClickButton" type="button" onClick="easyQueryClick();"> 
<input value="���ƹ�����" class="cssButton" name="updateClickButton" type="button" onclick="submitForm();">-->
<a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
<a href="javascript:void(0);" class="button"onclick="submitForm();">���ƹ�����</a>
<br><br>
<div id="divWorkDayGrid" style="display:''">
<table class="common">
	<tr class="common">
		<td text-align: left colspan="1"><span id="spanWorkDayGrid">
		</span></td>
	</tr>
</table>
<!--<input value="��ҳ" class="cssButton90" type="button"onclick="turnPage.firstPage();">
 <input value="��һҳ"class="cssButton91" type="button" onClick="turnPage.previousPage();">
 <input value="��һҳ" class="cssButton92" type="button"onclick="turnPage.nextPage();">
  <input value="βҳ" class="cssButton93" type="button" onClick="turnPage.lastPage();">--></div>
<br>
<!--<input value="��  ��" class="cssButton" name="updateClickButton"
	type="button" onClick="updateClick();">-->
    <a href="javascript:void(0);" class="button"onClick="updateClick();">��  ��</a>
    <br><br><br><br>
    </form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
