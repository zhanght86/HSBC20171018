<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%
	/*******************************************************************************
	* <p>Title: ��ȫ-�ŵ����̵���</p>
	* <p>Description: �ŵ����̵���js�ļ�</p>
	* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
	* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
	* <p>WebSite: http://www.sinosoft.com.cn</p>
	*
	* @todo     : ��ȫ-�ŵ����̵���
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
			<!--�������̨-->
			<table>
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divdiv)">
					</td>
					<td class="titleImg">�����嵥����</td>
				</tr>
			</table>
			<div id='divdiv' style="display:''" class=maxbox1>
				<table class=common>
					<TR class=common>
						<TD class=title>�ϴ��ļ�</TD>
						<TD class=common >
							<input name=ImportPath id=ImportPath type="hidden">
							<Input type="file" class="common wid" name=FileName id=FileName size=50>
						</TD>
					</TR>
					<TR class=common>
						<TD class=common colspan=2>
							<INPUT class=cssButton VALUE=" ��  �� " TYPE=button onclick="submitForm();">
							<INPUT class=cssButton VALUE=" ��  �� " TYPE=button onclick="returnParent();">
						</td>
					</TR>
					<input type=hidden name=ImportFile id=ImportFile>
				</table>
			</div>
			<!--��ʷ������־��ѯ-->
			<table>
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divHistoryErrorLog)">
					</td>
					<td class="titleImg">������־��ѯ</td>
				</tr>
			</table>
			<div id='divHistoryErrorLog' style="display:''" class=maxbox1>
				<table class=common>
					<TR class=common>
						<TD class=title>��ȫ�����</TD>
						<TD class=input ><Input class="common wid" name="qEdorAcceptNo" id=qEdorAcceptNo></TD>
						<TD class=title>��������</TD>
						<TD class=input ><Input type="text"  class="codeno" name="qEdorType" id=qEdorType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('edortype',[this,edorTypeName],[0,1]);" onkeyup="showCodeListKey('edortype',[this,edorTypeName],[0,1]);"><input type="text" class="codename" name="edorTypeName" id=edorTypeName></TD>
						<TD class=title>���屣����</TD>
						<TD class=input ><Input class="common wid" name="qGrpContNo" id=qGrpContNo></TD>
					</TR>
					<TR class=common>
						<TD class=title>�������κ�</TD>
						<TD class=input ><Input class="common wid" name="qBatchNo" id=qBatchNo></TD>
						<TD class=title>����Ա</TD>
						<TD class=input ><Input class="common wid" name="qOperator" id=qOperator></TD>
						<TD class=title>����ʱ��</TD>
						<TD class=input ><Input class= "coolDatePicker" dateFormat="short" name="errorDate" id=errorDate onClick="laydate({elem: '#errorDate'});" id="errorDate"><span class="icon"><a onClick="laydate({elem: '#errorDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
						</TD>
					</TR>
				</table>
			<INPUT class=cssButton VALUE=" ��  ѯ " TYPE=button onclick="queryErrorLog();">				
			</div >
			<!--������չʾ-->
			<table>
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divInquiryTrackInfo)">
					</td>
					<td class="titleImg">��ȫ���̵�������¼</td>
				</tr>
			</table>
			<!--��ѯ���չ�ֱ�� id="spanDataGrid"��init�ļ��е�initDataGrid()����һ��-->
			<div id="divInquiryTrackInfo" style="display:''">
				<table class="common">
					<tr class="common">
						<td>
							<span id="spanDataGrid"></span>
						</td>
					</tr>
				</table>
				<!-- ��ѯ�����ҳ -->
				<div align="center" style="display:''">
					<input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage.firstPage()">
					<input type="button" class="cssButton91" value="��һҳ" onclick="turnPage.previousPage()">
					<input type="button" class="cssButton92" value="��һҳ" onclick="turnPage.nextPage()">
					<input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage.lastPage()">
				</div>
			</div>

			<!--- ������ ---->
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
