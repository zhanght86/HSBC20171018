<%@ page contentType="text/html; charset=gb2312" language="java"
	errorPage=""%>
<%
	 /*******************************************************************************
	 * <p>Title: Lis 6.0</p>
	 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
	 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
	 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
	 * <p>WebSite: http://www.sinosoft.com.cn</p>
	 *
	 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
	 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
	 * @date     : 2005-11-29, 2005-12-03, 2006-02-15, 2006-02-28, 2006-03-23
	 * @direction: �������⸴Ч¼�������
	 ******************************************************************************/
%>
<%@ include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>���⸴Ч</title>
<!-- ����������ʽ -->
<link href="../common/css/Project.css" type="text/css" rel="stylesheet">
<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
<link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
<!-- �������ýű� -->
<script language="JavaScript" src="../common/javascript/Common.js"></script>
<script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
<script language="JavaScript" src="../common/javascript/MulLine.js"></script>
<script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
<script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<!-- ˽�����ýű� -->
<script language="JavaScript" src="LRNSpecialAvailableInput.js"></script>
<%@ include file="LRNSpecialAvailableInit.jsp"%>
</head>

<body topmargin="0" onload="initForm()" ondragstart="return false">
<form name="fm" id="fm" method="post" target="fraSubmit">
<table>
	<tr>
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand" onclick="showPage(this,divSearchLayer)"></td>
		<td class="titleImg">�������ѯ����</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divSearchLayer" style="display:''">
<table class="common">
	<tr class="common">
		<td class="title5">��ͬ����</td>
		<td class="input5"><input type="text" class="common wid"
			name="ContNo_srh" id="ContNo_srh" maxlength="40"></td>
		<td  class="title5"></td>
		<td  class="input5"></td>
	</tr>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="getAvailableInfoGrid();">��  ѯ</a>
<a href="javascript:void(0)" class=button onclick="gotoMultipleQuery();">�ۺϲ�ѯ</a>
<!-- <input type="button" class="cssButton" value=" �� ѯ " onclick="getAvailableInfoGrid()">
<input type="button" class="cssButton" value="�ۺϲ�ѯ" onclick="gotoMultipleQuery()"> -->

<table>
	<tr>
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand" onclick="showPage(this,divAvailableList)"></td>
		<td class="titleImg">ʧЧ��ͬ��Ϣ</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divAvailableList" style="display:''">
<table class="common">
	<tr class="common">
		<td class="title5">��ͬ����</td>
		<td class="input5"><input type="text" class="readonly wid"
			name="ContNo" id="ContNo" readonly></td>
		<td class="title5">���һ�ν��Ѷ�Ӧ��</td>
		<td class="input5"><input type="text" class="readonly wid"
			name="LastPayToDate" id="LastPayToDate" readonly></td>
	</tr>
	<tr class="common">
		<td class="title5">ʧЧ����</td>
		<td class="input5"><input type="text" class="readonly wid"
			name="UnAvailableDate" id="UnAvailableDate" readonly></td>
		<td class="title5">���һ�ν�������</td>
		<td class="input5"><input type="text" class="readonly wid"
			name="LastEnterAccDate" id="LastEnterAccDate" readonly></td>
	</tr>
</table>
</div>
</div>
<br>
<table class="common">
	<tr class="common">
		<td><span id="spanAvailableGrid"></span></td>
	</tr>
</table>
<div align="center" style="display:''">
<input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage.firstPage()">
<input type="button" class="cssButton91" value="��һҳ" onclick="turnPage.previousPage()"> 
<input type="button" class="cssButton92" value="��һҳ" onclick="turnPage.nextPage()"> 
<input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage.lastPage()">
</div>

<hr class="line"> 

<!-- ԭ��ע�۵�չ�� -->
<table>
	<tr>
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand" onclick="showPage(this,divReasonComment)"></td>
		<td class="titleImg">��Ч��ע</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divReasonComment" style="display:''">
<table class="common">
	<tr class="common">
		<td class="title5">��Ч��ע</td>
		<td ><textarea class="common"
			name="Remark" cols="107" rows="4" verify="��Ч��ע��Ϣ|len<400"></textarea></td>
	</tr>
</table>
</div>
</div>
<input type="hidden" name="ManageCom" id="ManageCom"> 
<input type="hidden" name="InvalidReason" id="InvalidReason">
<a href="javascript:void(0)" class=button onclick="saveRevalidateCont();">��  ��</a> 
<!-- <input type="button" class="cssButton" value=" �� �� " onclick="saveRevalidateCont()"> -->
</form>
<br>
<br>
<br>
<br>
<!-- ͨ��������Ϣ�б� -->
<span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
