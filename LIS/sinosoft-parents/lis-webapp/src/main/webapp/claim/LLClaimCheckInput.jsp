<html>
<%@page contentType="text/html;charset=GBK"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%

%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<style type="text/css">
a {background:#86DFF7;padding:5px 10px 3px 10px;border:1px solid #86DFF7;}
a:link {color: #000000;background:#86DFF7;}
a:visited {color: #000000;background:#86DFF7;}
a:hover {color: #000000;background:#86DFF7;}
a:active {color: #000000;background:#86DFF7;border:1px solid #888888;}
</style>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="LLClaimCheck.js"></SCRIPT>
<SCRIPT src="LLClaimPubFun.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="LLClaimCheckInit.jsp"%>
</head>
<body onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit"><br>
<span id="spanCode" style="display: none; position:absolute;z-index:9999; slategray"></span>
<div><b>���������⸶�ܽ��:</b><INPUT class=common VALUE="" name=ClaimMoney id=ClaimMoney disabled></div>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,InsureAccount);">
		</td>
		<td class=titleImg>�������⸶��������</td>
	</tr>
</table>
<Div id="InsureAccount" style="display:''" align="center">
<Table class=common>
	<tr>
		<td colSpan=1><span id="spanThisDutyGrid"></span></td>
	</tr>
</Table>
<INPUT class=cssButton90 VALUE="�ס�ҳ" TYPE=button onclick="turnPage1.firstPage();">
<INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();">
<INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();">
<INPUT class=cssButton93 VALUE="β��ҳ" TYPE=button onclick="turnPage1.lastPage();">
</Div>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,ClaimAccount);">
		</td>
		<td class=titleImg>�������⸶�˵�����</td>
	</tr>
</table>
<Div id="ClaimAccount" style="display:''" align="center">
<Table class=common>
	<tr>
		<td colSpan=1><span id="spanThisFeeGrid"></span></td>
	</tr>
</Table>
<INPUT class=cssButton90 VALUE="�ס�ҳ" TYPE=button onclick="turnPage2.firstPage();">
<INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();">
<INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();">
<INPUT class=cssButton93 VALUE="β��ҳ" TYPE=button onclick="turnPage2.lastPage();">
</Div>
<hr>
<a href="#" onclick="top.close();">������</a>
<!--���ر���,������Ϣ��--> 
<input type=hidden name=RgtNo id=RgtNo value=''> 
<input type=hidden name=CustNo id=CustNo value=''>
<input type=hidden name=ContNo id=ContNo value=''>
<input type=hidden id=fmtransact name=fmtransact>
<input type=hidden id=AccountType name=AccountType>
<input type=hidden id=ActivityID name=ActivityID>
</form>
</body>
</html>
