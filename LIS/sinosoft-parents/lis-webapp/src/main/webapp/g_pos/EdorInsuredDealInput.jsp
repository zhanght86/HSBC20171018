<%
/***************************************************************
 * <p>ProName��EdorInsuredDealInput.jsp</p>
 * <p>Title����ȫ��Ա�嵥����</p>
 * <p>Description����ȫ��Ա�嵥����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
%>
<script>
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head >
	<title>��Ա�嵥����</title>
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
	<script src="./EdorInsuredDealInput.js"></script>
	<%@include file="./EdorInsuredDealInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
		 	<td class=titleImg>��Ա�嵥��ѯ����</td>
		</tr>
	</table>
	
	<div id="divQueryInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
					<td class=title>��ȫ��Ŀ</td>
					<td class=input><input class=codeno name=EdorType id=EdorType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
						ondblclick="return showCodeList('edorcode',[this, EdorTypeName],[0, 1],null,'1 and b.appobj in(#G#,#C#) and b.edorlevel=#1#','1','1',180);" 
						onkeyup="return showCodeListKey('edorcode',[this, EdorTypeName],[0, 1],null,'1 and b.appobj in(#G#,#C#) and b.edorlevel=#1#','1','1',180);"><input class=codename name=EdorTypeName id=EdorTypeName readonly></td>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=InsuredIDNo id=InsuredIDNo></td>
			</tr>
			<tr class=common>
				<td class=title>У��״̬</td>
				<td class=input><input class=codeno name=State id=State readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('insuliststate',[this, StateName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('insuliststate',[this, StateName],[0, 1],null,null,null,'1',180);"><input class=codename name=StateName id=StateName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInsuredList);">
			</td>
			<td class=titleImg>��Ա�嵥�б�</td>
		</tr>
	</table>
	<div id="divInsuredList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanInsuredListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		
		<input class=cssButton type=button value="ѡ��ɾ��" onclick="selDelete();">
		<input class=cssButton type=button value="����ɾ��" onclick="conDelete();">
		<input class=cssButton type=button value="ȫ��ɾ��" onclick="allDelete();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input class=cssButton type=button value="��Ա�嵥У��" onclick="insuredListCheck();">
	</div>
	
	<input type=hidden name=Operate>
	<input type=hidden name=MissionID value="<%=tMissionID%>">
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>">
	<input type=hidden name=ActivityID value="<%=tActivityID%>">
	<input type=hidden name=EdorAppNo value="<%=tEdorAppNo%>">
	
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetSql>
	<input type=hidden name=SheetSql>
</form>
<form name=fmupload method=post action="./LDAttachmentSave.jsp" ENCTYPE="multipart/form-data" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBatch);">
			</td>
			<td class=titleImg>���������б�</td>
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
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	</div>
	
	<div id="divImport" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�����ļ�·��</td>
				<td class=input colspan=5><input class=common type=file name=ImportPath id=ImportPath style="width:400px">&nbsp;&nbsp;<a id="temp1" style="display:'none' " href="../template/EdorInsuredList1.xlsx"><font color="#FF0000">������ģ�����ء�</font></a><a id="temp2" style="display:'none'" href="../template/EdorInsuredList2.xlsx"><font color="#FF0000">������ģ�����ء�</font></a><a id="temp3" style="display:'none'" href="../template/EdorInsuredList3.xlsx"><font color="#FF0000">������ģ�����ء�</font></a></td>
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
		
		<input class=cssButton type=button name=ImportButton value="��  ��" onclick="importClick();">
		<input class=cssButton type=button name="DownloadButton" value="����������Ϣ����"  onclick="downloadClick();">
	</div>
	<Br /><Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
