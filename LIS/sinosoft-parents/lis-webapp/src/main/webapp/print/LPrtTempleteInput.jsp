<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="./LPrtTempleteInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<%@include file="./LPrtTempleteInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./LPrtTempleteSave.jsp" method=post name=fm id="fm" target="fraSubmit" ENCTYPE="multipart/form-data">
		<input type=hidden id="fmtransact" name="fmtransact">
		<table class=common>
			<tr>
				<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divTemplete1);"></td>
				<td class=titleImg>ģ�嶨�壺</td>
			</tr>
		</table>
		<div class="maxbox">
		<div id="divTemplete1" name=divTemplete1 style="display: '' ">
			<table class=common>
				<tr class=common>
					<td class=title5>ģ�����</td>
					<td class=input5><input class="common wid" name=TempleteID id="TempleteID" readOnly=true >
					</td>
					<td class=title5>ģ������</td>
					<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintID id="PrintID" verify="ģ������|notnull"
						onclick="return showCodeList('printid',[this,TempleteName],[0,1],null,null,null,1);"  ondblclick="return showCodeList('printid',[this,TempleteName],[0,1],null,null,null,1);"
						onkeyup="return showCodeLisKey('printid',[this,TempleteName],[0,1],null,null,null,1);"><input class=codename readOnly=true name=TempleteName id="TempleteName" elementtype=nacessary>
					</td>
				</tr>
				<tr class=common>
					<td class=title5>����</td>
					<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=Language id="Language" verify="�����|notnull"
						onclick="return showCodeList('language',[this,LanguageType],[0,1]);" ondblclick="return showCodeList('language',[this,LanguageType],[0,1]);"
						onkeyup="return showCodeListKey('language',[this,LanguageType],[0,1]);"><input class=codename name=LanguageType id="LanguageType" readOnly=true elementtype=nacessary>
					</td>
					<td class=title5>ģ������</td>
					<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=TempleteType id="TempleteType" verify="ģ������|notnull"
						onclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);"  ondblclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);"
						onkeyup="return showCodeListKey('templetetype',[this,TempleteTypeName],[0,1]);">
						<input class=codename name=TempleteTypeName id="TempleteTypeName" readOnly=true elementtype=nacessary>
					</td>
				</tr>
				<tr class=common>
					<td class=title5>�����ʽ</td>
					<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=Output id="Output" verify="�����ʽ|notnull"
						onclick="return showCodeList('output',[this,OutputName],[0,1]);" ondblclick="return showCodeList('output',[this,OutputName],[0,1]);"
						onkeyup="return showCodeListKey('output',[this,OutputName],[0,1]);"><input class=codename name=OutputName id="OutputName" readOnly=true elementtype=nacessary>
					</td>
					<td class=title5>�������</td>
					<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=OutputType id="OutputType" verify="�������|notnull"
						onclick="return showCodeList('outputtype',[this,OutputTypeName],[0,1]);"  ondblclick="return showCodeList('outputtype',[this,OutputTypeName],[0,1]);"
						onkeyup="return showCodeListKey('outputtype',[this,OutputTypeName],[0,1]);"><input class=codename name=OutputTypeName id="OutputTypeName" readOnly=true elementtype=nacessary>
					</td>
				</tr>
				<tr class=common>
					<td class= title5>�Ƿ�ΪĬ��ģ��</td>
					<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=DefaultType id="DefaultType" verify="Ĭ������|notnull"
						onclick="return showCodeList('yesno',[this,DefaultFlag],[0,1]);" ondblclick="return showCodeList('yesno',[this,DefaultFlag],[0,1]);"
						onkeyup="return showCodeListKey('yesno',[this,DefaultFlag],[0,1]);"><input class=codename name=DefaultFlag id="DefaultFlag" readOnly=true elementtype=nacessary>
					</td>
				</tr>
				<tr class=common>
					<td class= title5>�ļ�</td>
					<td class=input5>
						<input class="common wid"  name= FilePath1 id="FilePath1" style ="display :none" >
						<input class="common wid" type="file"  name=FilePath  id="FilePath" size=14 style ="display :''" elementtype=nacessary>
					</td>
				</tr>
			</table>
		</div>
		</div>
		<a href="javascript:void(0)" class=button name=fileQueryClick style ="display :none" onclick="fileUpdateClick();">��  ��</a>
		<a href="javascript:void(0)" class=button onclick="return addClick();">��  ��</a>
		<a href="javascript:void(0)" class=button onclick="return deleteClick();">ɾ  ��</a>
		<a href="javascript:void(0)" class=button onclick="return updateClick();">��  ��</a>
		<a href="javascript:void(0)" class=button onclick="return queryClick();">��  ѯ</a>
		<a href="javascript:void(0)" class=button onclick="return resetForm();">��  ��</a>
		<!-- <input class=cssButton type=button value="��  ��" onclick="return addClick();">
		<input class=cssButton type=button value="ɾ  ��" onclick="return deleteClick();">
		<input class=cssButton type=button value="��  ��" onclick="return updateClick();">
		<input class=cssButton type=button value="��  ѯ" onclick="return queryClick();">
		<input class=cssButton type=button value="��  ��" onclick="return resetForm();"> -->
		<!-- <input class=cssButton type=button name=fileQueryClick style ="display :'none'" value="��  ��" onclick ="fileUpdateClick();" > -->
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
