<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  



<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
		
		<script src="../common/javascript/jquery.js"></script>
		<!--˽��JS�ű�-->
		<SCRIPT src="LDScanPage.js"></SCRIPT>
		<%@include file="LDScanPageInit.jsp"%>

		<title>Ӱ������Ͳ�ѯ</title>
	</head>

	<body  onload="initForm();">
		<form action="LDScanDefSave.jsp" method=post name=fm id=fm target="fraSubmit">

			<table class= common border=0 width=100%>
				<tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
					<td class= titleImg>
						������Ӱ������Ͳ�ѯ������
					</td>
				</tr>
			</table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
			<table  class= common>
				<TR  class= common>

					<td class=title5 >Ӱ�������</td>
					<td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="SubType" id="SubType" ondblclick="showCodeList('invoicetype',[this,SubTypeName],[0,1])" onclick="showCodeList('invoicetype',[this,SubTypeName],[0,1])" onkeyup="showCodeListKey('invoicetype',[this,SubTypeName],[0,1])"><input class=codename name=SubTypeName id=SubTypeName></td>	
                    <td class=title5 ></td>
                    <td class= input5></td>			
				</TR>

			</table>
</Div>
<!--<INPUT VALUE=" �� ѯ " class=cssButton TYPE=button onclick="SubTypeQuery();">-->
<a href="javascript:void(0);" class="button" onClick="SubTypeQuery();">��    ѯ</a>
			<table>
				<tr>
					<td class=common>
						<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divScan);">
					</td>
					<td class= titleImg>
						Ӱ������Ͳ�ѯ�����
					</td>
				</tr>
			</table>
			<Div  id= "divScan" style= "display: ''">
				<table  class= common>
					<tr  class= common>
						<td style=" text-align: left" colSpan=1>
							<span id="spanScanGrid" >
							</span>
						</td>
					</tr>
				</table>
				
				<input type=hidden name=oper >
			</div>
			
			<!--<INPUT class=cssButton TYPE=button VALUE="�����涯����ҳ��" onclick="defScanPosition()">-->
            <a href="javascript:void(0);" class="button" onClick="defScanPosition();">�����涯����ҳ��</a>
			<!-- ��ȡ���ݵ������� -->
		</form>
		<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
	</body>
</html>
