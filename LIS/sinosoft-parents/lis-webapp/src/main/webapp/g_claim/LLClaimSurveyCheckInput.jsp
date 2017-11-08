<%
/***************************************************************
 * <p>ProName：LLClaimSurveyCheckInput.jsp</p>
 * <p>Title：调查复核申请</p>
 * <p>Description：调查复核申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom =tGI.ManageCom;
	String tOperator = tGI.Operator;
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //记录登陆机构
	var tOperator = "<%=tOperator%>";  //记录操作人
</script>
<html>
<head>
	<title>调查复核申请</title>
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
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimSurveyCheckInput.js"></script>
	<%@include file="./LLClaimSurveyCheckInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimSurveyCheckSave.jsp" target=fraSubmit>
	<!--	<table>
			<tr>
				<td class=commontop>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues);">
				</td>
				<td class=titleImgtop>查询条件</td>
			</tr>
		</table>
		<div id="divLLRgt" style="display:''">
			<table class=common>
				<tr class=common>
					<td class=title>报案号</td>
					<td class=input><input class=common name=RptNo ></td>
					<td class=title>案件号</td>
					<td class=input><input class=common name=AcceptNo ></td>
					<td class=title>投保人名称</td>
					<td class=input><input class=common name=ClmNo ></td>
				</tr>
	      <tr>
					<td class=title>被保险人姓名</td>
	        <td class=input><input class=common name=GrpcontNo></td>
	        <td class=title>证件号码</td>
	        <td class=input><input class=common name=GrpName></td>
	        <td class=title>调查类型</td>
					<td class=input><Input class=codeno readonly name=SurveyType ondblclick="return showCodeList('surveytype',[this,SurveyTypeName],[0,1]);" onkeyup="return showCodeListKey('surveytype',[this,SurveyTypeName],[0,1]);" ><input class=codename name="SurveyTypeName" readonly></td>        
	      </tr>
				<tr>
					<td class=title>调查发起起期</td>
					<td class=input><input class='coolDatePicker' dateFormat="Short" name=ApplyStartdate></td> 
					<td class=title>调查发起止期</td>
					<td class=input><input class='coolDatePicker' dateFormat="Short" name=ApplyEndDate></td>
					<td class=title>是否异地调查</td>
					<td class=input><Input class=codeno name=Initdept ondblclick="return showCodeList('stati',[this,InitdeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitdeptName],[0,1]);"><input class=codename name="InitdeptName" readonly=true></td>
				</tr>
				<tr>
					<td class=title>调查发起机构</td>
					<td class=input><Input class=codeno name=Initdept ondblclick="return showCodeList('stati',[this,InitdeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitdeptName],[0,1]);"><input class=codename name="InitdeptName" readonly=true></td>
				  <td class=title>调查机构</td>
					<td class=input><Input class=codeno name=Initdept ondblclick="return showCodeList('stati',[this,InitdeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitdeptName],[0,1]);"><input class=codename name="InitdeptName" readonly=true></td>
				  <td class=title></td>
					<td class=input></td> 
				</tr>
      </table>  
			<input class=cssButton type=button name=query value="查  询" onclick="queryPubPoolInfo();">
		</div>    
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPublic);">
				</td>
				<td class=titleImg>公共池</td>
			</tr>
		</table>
		<div id="divPublic" style="display: ''">   
			<table class=common>
  			<tr class=common><td text-align: left colSpan=1><span id="spanPublicPoolGrid" ></span></td></tr>
			</table>
			<center>
				<input value="首  页" class=cssButton type=button onclick="turnPage1.firstPage();">
				<input value="上一页" class=cssButton type=button onclick="turnPage1.previousPage();">
				<input value="下一页" class=cssButton type=button onclick="turnPage1.nextPage();">
				<input value="尾  页" class=cssButton type=button onclick="turnPage1.lastPage();">
				<input value="导出数据" class=cssButton type=button onclick="turnPage1.makeExcel();">
			</center> 
		</div>
		<input class=cssButton type=button name=apply value="申  请" onclick="applyCase();">	-->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPrivate);">
				</td>
				<td class=titleImg>待调查复核的任务列表</td>
			</tr>
		</table>
		<div id="divPrivate" style="display: ''">   
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPrivatePoolGrid" ></span></td>
				</tr>
			</table>
			<center>
			<Input class=cssButton90 value="首  页" type=button onclick="turnPage1.firstPage();">
			<Input class=cssButton91 value="上一页" type=button onclick="turnPage1.previousPage();">
			<Input class=cssButton92 value="下一页" type=button onclick="turnPage1.nextPage();">
			<Input class=cssButton93 value="尾  页" type=button onclick="turnPage1.lastPage();">
			</center> 
		</div>
 <%
  //保存数据用隐藏表单区
  %>   
		<Input type=hidden name=ComCode >
		<Input type=hidden name=Operate> 	 	 <!--操作类型-->
		<Input type=hidden name=InqNo> 	 	
		<Input type=hidden name=RgtNo> 	 	
	</form>	
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
