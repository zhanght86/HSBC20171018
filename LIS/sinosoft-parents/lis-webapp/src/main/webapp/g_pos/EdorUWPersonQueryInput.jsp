<%
/***************************************************************
 * <p>ProName��EdorUWPersonQueryInput.jsp</p>
 * <p>Title����Ա�ֲ���ѯ</p>
 * <p>Description����Ա�ֲ���ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorNo =  request.getParameter("EdorNo");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tGrpContNo = "<%=tGrpPropNo%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tEdorNo =  "<%=tEdorNo%>";
	
</script>
<html>
<head >
	<title>��Ա�ֲ�</title>
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
	<script src="./EdorUWPersonQueryInput.js"></script>
	<%@include file="./EdorUWPersonQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	 <table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divYearDistribution);">
			</td>
			<td class=titleImg>��Ա��Ϣ�ֲ�</td>
		</tr>
	</table>
	<div  id= "divr1" class=maxbox1 style= "display: ''">
	<table class=common>
		<tr class=common>
			<td class=title>�ֲ����</td>
			<td class=input><input class=codeno name="branchSub" id=branchSub value='0' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('distritype',[this,branchSubName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('distritype',[this,branchSubName],[0,1],null,null,null,1,null);" ><input class=codename name=branchSubName value='������Ϣ�ֲ�' readonly=true></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	</div>
	<div id= "divr2" style= "display: none">
		<table class=common>
			<tr class=common>
				<td class=title>���շ���</td> 
				<td class=input><Input class="codeno" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);"><input class=codename name=ContPlanCodeName elementtype=nacessary><input type=hidden name=sysPlanCode ></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
	</table>
	</div>
	<div id= "divr3" style= "display: none">
	<table class=common>
		<tr class=common>
			<td class=title>����</td>
			<td class=input><Input class="codeno" name="RiskCode" id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showRiskCode(this,RiskCodeName);" onkeyup="showRiskCodeName(this,RiskCodeName);"><input class=codename name=RiskCodeName elementtype=nacessary></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	</div>
	<div id= "divYearDistribution" style= "display: ''">
		<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanYearDistributionGrid" ></span>
					</td>
				</tr>
		</table>
	<center style="display:none">
		<input value="��  ҳ" class =cssButton90 TYPE=button onclick="turnPage1.firstPage();">
		<input value="��һҳ" class =cssButton91 TYPE=button onclick="turnPage1.previousPage();">
		<input value="��һҳ" class =cssButton92 TYPE=button onclick="turnPage1.nextPage();">
		<input value="β  ҳ" class =cssButton93 TYPE=button onclick="turnPage1.lastPage();">
	</center>
	 </Div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpPropNo value="<%=tGrpPropNo%>">
	<Br/><Br/><Br/><Br/>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
