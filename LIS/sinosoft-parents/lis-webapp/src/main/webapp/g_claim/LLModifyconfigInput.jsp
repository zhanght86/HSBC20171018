<%
/***************************************************************
 * <p>LLModifyconfigInput.jsp</p>
 * <p>Title�������޸Ĺ������ý���</p>
 * <p>Description�������޸Ĺ�������ȷ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��־��
 * @version  : 8.0
 * @date     : 2015-03-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<head >
	<title>�����޸Ĺ�������ȷ��</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="LLModifyconfigInput.js"></script>
	<%@include file="LLModifyconfigInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLModifyconfigSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLLModifyconfigInfo);">
			</td>
		 	<td class=titleImg>��������޸�����</td>
		</tr>
	</table>
	
	<div id="divLLModifyconfigInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�����޸�ԭ��</td>
				<td class=input><input class=codeno name=ReasonNo verify="�����޸�ԭ��|NOTNULL"  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick=" showCodeList('clmreasontype',[this,ReasonName],[0,1],null,null,null,1);" 
					onkeyup="return showCodeListKey('clmreasontype',[this,ReasonName],[0,1],null,null,null,1);"><input class=codename name=ReasonName elementtype=nacessary readonly=true></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick()">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRuleTypeGrid);">
			</td>
			<td class=titleImg>���������б�</td>
		</tr>
	</table>
	<div id="divRuleTypeGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanRuleTypeGrid" ></span> </td>
			</tr>
		</table>
	</div>
	
	 
	<div id= "divRuleTypeGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</table>
		</div>
	
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=RuleType
					ondblclick=" showCodeList('clmruletype',[this,RuleTypeName],[0,1],null,null,null,1);"  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					onkeyup="return showCodeListKey('clmruletype',[this,RuleTypeName],[0,1],null,null,null,1);"><input class=codename name=RuleTypeName elementtype=nacessary readonly=true></td>
			    <td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			
		</table>
	
		<input class=cssButton name=addButton type=button value="��  ��" onclick="addClick(1);">
		<input class=cssButton name=DeleteButton type=button value="ɾ  ��" onclick="deleteClick(1);">
		
		 
		<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPolTypeGrid);">
			</td>
			<td class=titleImg>�����б�</td>
		</tr>
	</table>
	<div id="divPolTypeGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanPolTypeGrid" ></span> </td>
			</tr>
		</table>
	</div>
	<div id= "divPolTypeGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
			</table>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title>���ֱ���</td>
				<td class=input><input class=codeno name=PolType
					ondblclick=" showCodeList('quotrisk',[this,PolTypeName],[0,1],null,null,null,1);" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					onkeyup="return showCodeListKey('quotrisk',[this,PolTypeName],[0,1],null,null,null,1);"><input class=codename name=PolTypeName elementtype=nacessary readonly=true></td>
			    <td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	
	  
	  <input class=cssButton name=addButton type=button value="��  ��" onclick="addClick(2)">  
	  <input class=cssButton name=DeleteButton type=button value="ɾ  ��" onclick="deleteClick(2)">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divTiaoZhengGrid);">
			</td>
			<td class=titleImg>���������б�</td>
		</tr>
	</table>
	<div id="divTiaoZhengGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanTiaoZhengGrid" ></span> </td>
			</tr>
		</table>
	</div>
	<div id= "divTiaoZhengGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage3.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage3.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage3.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage3.lastPage();">
			</table>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=TiaoZhengType style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick=" showCodeList('clmadjust',[this,TiaoZhengName],[0,1],null,null,null,1);" 
					onkeyup="return showCodeListKey('clmadjust',[this,TiaoZhengName],[0,1],null,null,null,1);"><input class=codename name=TiaoZhengName elementtype=nacessary readonly=true></td>
			    <td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	
	 
	  <input class=cssButton name=addButton type=button value="��  ��" onclick="addClick(3)">  
	  <input class=cssButton name=DeleteButton type=button value="ɾ  ��" onclick="deleteClick(3)">
	  
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=HiddenModify>
	<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
