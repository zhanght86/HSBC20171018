<%
/***************************************************************
 * <p>ProName��FinExtractInput.jsp</p>
 * <p>Title����Ʒ�¼��ȡ</p>
 * <p>Description����Ʒ�¼��ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>��Ʒ�¼��ȡ</title>
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
	<script src="./FinExtractInput.js"></script>
	<%@include file="./FinExtractInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinExtractSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinExtract);">
			</td>
			<td class=titleImg>������ҵ������</td>
		</tr>
	</table>
	
	<div id="divFinExtract" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��ʼ����</td>
				<td class=input><input class=coolDatePicker dateFormat="short" name=StartDate verify="��ʼ����|notnull&DATE&LEN=10" elementtype=nacessary onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��ֹ����</td>
				<td class=input><input class=coolDatePicker dateFormat="short" name=EndDate verify="��ֹ����|notnull&DATE&LEN=10" elementtype=nacessary onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td><font color="#FF0000">ע����Ʒ�¼������һ�ο�����ȡ��������</font></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="��  ��" onclick="extractData();">
		<input class=cssButton type=button value="ɾ  ��" onclick="deleteData();">
		<input class=cssButton type=button value="���ز���ӿ��ļ�" onclick="downloadData();">&nbsp;&nbsp;&nbsp;
		<input class=cssButton type=button value="��  ѯ" onclick="queryData();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divFinExtractInfo);">
			</td>
			<td class=titleImg>��Ʒ�¼��Ϣ</td>
		</tr>
	</table>
	<div id="divDataSourceInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanFinExtractGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="��������" onclick="exportData();">
		</center>
	</div>
	
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
