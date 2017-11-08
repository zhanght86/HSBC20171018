<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  



<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<!-- 公共引用样式 -->
		<link href="../common/css/Project.css" type="text/css" rel="stylesheet">
        <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
		<link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
		<!-- 公共引用脚本 -->
		<script language="JavaScript" src="../common/javascript/Common.js"></script>
		<script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
		<script language="JavaScript" src="../common/javascript/MulLine.js"></script>
		<script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
		<script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
		
		<script src="../common/javascript/jquery.js"></script>
		<!--私有JS脚本-->
		<SCRIPT src="LDScanPage.js"></SCRIPT>
		<%@include file="LDScanPageInit.jsp"%>

		<title>影像件类型查询</title>
	</head>

	<body  onload="initForm();">
		<form action="LDScanDefSave.jsp" method=post name=fm id=fm target="fraSubmit">

			<table class= common border=0 width=100%>
				<tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
					<td class= titleImg>
						请输入影像件类型查询条件：
					</td>
				</tr>
			</table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
			<table  class= common>
				<TR  class= common>

					<td class=title5 >影像件类型</td>
					<td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="SubType" id="SubType" ondblclick="showCodeList('invoicetype',[this,SubTypeName],[0,1])" onclick="showCodeList('invoicetype',[this,SubTypeName],[0,1])" onkeyup="showCodeListKey('invoicetype',[this,SubTypeName],[0,1])"><input class=codename name=SubTypeName id=SubTypeName></td>	
                    <td class=title5 ></td>
                    <td class= input5></td>			
				</TR>

			</table>
</Div>
<!--<INPUT VALUE=" 查 询 " class=cssButton TYPE=button onclick="SubTypeQuery();">-->
<a href="javascript:void(0);" class="button" onClick="SubTypeQuery();">查    询</a>
			<table>
				<tr>
					<td class=common>
						<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divScan);">
					</td>
					<td class= titleImg>
						影像件类型查询结果：
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
			
			<!--<INPUT class=cssButton TYPE=button VALUE="进入随动定制页面" onclick="defScanPosition()">-->
            <a href="javascript:void(0);" class="button" onClick="defScanPosition();">进入随动定制页面</a>
			<!-- 获取数据的隐藏域 -->
		</form>
		<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
	</body>
</html>
