<%
/***************************************************************
 * <p>ProName：LCInsuredUploadInput.jsp</p>
 * <p>Title：人员清单导入</p>
 * <p>Description：人员清单导入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-27
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tContPlanType  = request.getParameter("ContPlanType");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
%>
<script>
	var tContPlanType  = "<%=tContPlanType%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head >
	<title>人员清单导入</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LCInsuredUploadInput.js"></script>
	<%@include file="./LCInsuredUploadInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fmupload id=fmupload method=post action="./LDAttachmentSave.jsp" ENCTYPE="multipart/form-data" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divImport);">
			</td>
			<td class=titleImg>人员清单导入</td>
		</tr>
	</table>
	<div id="divImport" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>导入文件路径</td>
				<td class=input colspan=5><input class=common type=file name=ImportPath style="width:400px"><a id="temp1" style="display:none " href="../template/InsuredList1.xlsx"><font color="#FF0000">【导入模板下载】</font></a><a id="temp2" style="display:none" href="../template/InsuredList2.xlsx"><font color="#FF0000">【导入模板下载】</font></a><a id="temp3" style="display:none" href="../template/InsuredList3.xlsx"><font color="#FF0000">【导入模板下载】</font></a></td>
			</tr>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="导  入" onclick="importClick();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
</form>
<form name=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBatch);">
			</td>
			<td class=titleImg>导入批次列表</td>
		</tr>
	</table>
	<div id="divBatch" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBatchGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
		
		<input class=cssButton type=button value="导入批次信息下载" name="DownloadButton" onclick="downloadClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
		 	<td class=titleImg>导入明细查询条件</td>
		</tr>
	</table>
	
	<div id="divQueryInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>批次号</td>
				<td class=input><input class="wid common" id=BatchNo name=BatchNo></td>
				<td class=title>是否导入成功</td>
				<td class=input><input class=codeno name=State readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
					ondblclick="return showCodeList('trueflag',[this, StateName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('trueflag',[this, StateName],[0, 1],null,null,null,'1',180);"><input class=codename name=StateName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="查  询" onclick="queryDetailInfo();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBatchDetail);">
			</td>
			<td class=titleImg>导入批次明细信息列表</td>
		</tr>
	</table>
	<div id="divBatchDetail" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBatchDetailGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	</div>
	
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetSql>
	<input type=hidden name=SheetSql>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
