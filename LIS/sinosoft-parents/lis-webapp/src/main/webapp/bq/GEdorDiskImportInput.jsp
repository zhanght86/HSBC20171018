<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%
	/*******************************************************************************
	* <p>Title: 保全-团单磁盘导入</p>
	* <p>Description: 团单磁盘导入js文件</p>
	* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
	* <p>Company: 中科软科技股份有限公司</p>
	* <p>WebSite: http://www.sinosoft.com.cn</p>
	*
	* @todo     : 保全-团单磁盘导入
	* @author   : zhangtao
	* @version  : 1.00
	* @date     : 2006-11-24
	* @modify   : 2006-11-25
	******************************************************************************/
	%>
	<head>
		<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<script language="JavaScript" src="../common/javascript/Common.js"></script>
		<script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
		<script language="JavaScript" src="../common/javascript/MulLine.js"></script>
		<script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
		<script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
		<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
		<SCRIPT src="GEdorDiskImport.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="GEdorDiskImportInit.jsp"%>
	</head>
	<body onload="initForm();">
		<form action="./GEdorDiskImportSave.jsp" method=post name=fm id=fm target="fraSubmit" ENCTYPE="multipart/form-data">
			<!---->
			<!--导入控制台-->
			<table>
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divdiv)">
					</td>
					<td class="titleImg">人名清单导入</td>
				</tr>
			</table>
			<div id='divdiv' style="display:''" class=maxbox1>
				<table class=common>
					<TR class=common>
						<TD class=title>上传文件</TD>
						<TD class=common >
							<input name=ImportPath id=ImportPath type="hidden">
							<Input type="file" class="common wid" name=FileName id=FileName size=50>
						</TD>
					</TR>
					<TR class=common>
						<TD class=common colspan=2>
							<INPUT class=cssButton VALUE=" 导  入 " TYPE=button onclick="submitForm();">
							<INPUT class=cssButton VALUE=" 返  回 " TYPE=button onclick="returnParent();">
						</td>
					</TR>
					<input type=hidden name=ImportFile id=ImportFile>
				</table>
			</div>
			<!--历史错误日志查询-->
			<table>
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divHistoryErrorLog)">
					</td>
					<td class="titleImg">导入日志查询</td>
				</tr>
			</table>
			<div id='divHistoryErrorLog' style="display:''" class=maxbox1>
				<table class=common>
					<TR class=common>
						<TD class=title>保全受理号</TD>
						<TD class=input ><Input class="common wid" name="qEdorAcceptNo" id=qEdorAcceptNo></TD>
						<TD class=title>批改类型</TD>
						<TD class=input ><Input type="text"  class="codeno" name="qEdorType" id=qEdorType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('edortype',[this,edorTypeName],[0,1]);" onkeyup="showCodeListKey('edortype',[this,edorTypeName],[0,1]);"><input type="text" class="codename" name="edorTypeName" id=edorTypeName></TD>
						<TD class=title>团体保单号</TD>
						<TD class=input ><Input class="common wid" name="qGrpContNo" id=qGrpContNo></TD>
					</TR>
					<TR class=common>
						<TD class=title>导入批次号</TD>
						<TD class=input ><Input class="common wid" name="qBatchNo" id=qBatchNo></TD>
						<TD class=title>操作员</TD>
						<TD class=input ><Input class="common wid" name="qOperator" id=qOperator></TD>
						<TD class=title>导入时间</TD>
						<TD class=input ><Input class= "coolDatePicker" dateFormat="short" name="errorDate" id=errorDate onClick="laydate({elem: '#errorDate'});" id="errorDate"><span class="icon"><a onClick="laydate({elem: '#errorDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
						</TD>
					</TR>
				</table>
			<INPUT class=cssButton VALUE=" 查  询 " TYPE=button onclick="queryErrorLog();">				
			</div >
			<!--导入结果展示-->
			<table>
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divInquiryTrackInfo)">
					</td>
					<td class="titleImg">保全磁盘导入错误记录</td>
				</tr>
			</table>
			<!--查询结果展现表格 id="spanDataGrid"与init文件中的initDataGrid()保持一致-->
			<div id="divInquiryTrackInfo" style="display:''">
				<table class="common">
					<tr class="common">
						<td>
							<span id="spanDataGrid"></span>
						</td>
					</tr>
				</table>
				<!-- 查询结果翻页 -->
				<div align="center" style="display:''">
					<input type="button" class="cssButton90" value="首  页" onclick="turnPage.firstPage()">
					<input type="button" class="cssButton91" value="上一页" onclick="turnPage.previousPage()">
					<input type="button" class="cssButton92" value="下一页" onclick="turnPage.nextPage()">
					<input type="button" class="cssButton93" value="尾  页" onclick="turnPage.lastPage()">
				</div>
			</div>

			<!--- 隐藏域 ---->
			<input type=hidden id="GrpContNo" name="GrpContNo">
			<input type=hidden id="EdorType" name="EdorType">
			<input type=hidden id="BQFlag" name="BQFlag" value = "Y">
			<input type=hidden id="EdorNo" name="EdorNo">
			<input type=hidden id="EdorValiDate" name="EdorValiDate">
			<input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">

		</form>

		<span id="spanCode" style="display: none; position:absolute; slategray"></span>
		<br/><br/><br/><br/>
	</body>
</html>
