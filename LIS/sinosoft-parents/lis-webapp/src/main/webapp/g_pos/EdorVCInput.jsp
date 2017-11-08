<%
/***************************************************************
 * <p>ProName：EdorVCInput.jsp</p>
 * <p>Title：归属规则维护</p>
 * <p>Description：归属规则维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	
	String tOperator = tGI.Operator;
	String tCurrentDate= PubFun.getCurrentDate();
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo"); 
	String tEdorType = request.getParameter("EdorType");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorNo = request.getParameter("EdorNo");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head>
	<title>归属规则维护</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./EdorVCInput.js"></script>
	<%@include file="EdorVCInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
 <form method=post name=fm id=fm target="fraSubmit" action="">
 <div style="margin-left:10px">
	<table class=common>
		<tr class=common>
			<td class=common>
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divPositionGrid);">
			</td>
			<td class=titleImg>被保人职级信息</td>
		</tr>
	</table>
	<div id="divPositionGrid" style="display: ''"> 
		<table  class= common>
			<tr class= common>
				<td text-align: left colSpan=1>
					<span id="spanPositionGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
		<br>
		<table class=common>	
			<tr class=common>
				<td class=title>职级代码</td>
				<td class=input><input class="wid common" id=PositionCode name=PositionCode elementtype=nacessary verify="职级代码|notnull&len<=4" ></td>
				<td class=title>职级名称</td>
				<td class=input><input class="wid common" id=PositionCodeName name=PositionCodeName elementtype=nacessary verify="职级名称|notnull"></td>
			 	<td class=title>归属年度计算方式</td>
				<td class=input><input class=codeno name=CountType id=CountType style="background:url(../common/images/select--bg_03.png) no-repeat right center" readonly verify="归属年度计算方式|notnull" ondblclick="showCodeList('counttype',[this,CountTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('counttype',[this,CountTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=CountTypeName elementtype=nacessary ></td>
			</tr>	
		</table>
		<div id= "divAccascriptionSave" style= "display: none" >
			<Input VALUE="保  存" class=cssButton type=button onclick="addPosition();">
			<Input VALUE="修  改" class=cssButton type=button onclick="updatePosition();">
			<Input VALUE="删  除" class=cssButton type=button onclick="deletePosition();">
		</div>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick= "showPage(this,divAscriptionGrid);">
			</td>
			<td class=titleImg>普通归属规则定义</td>
		</tr>
	</table>
	<div id="divAscriptionGrid" style="display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanAscriptionGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
		<br>
		<table class= common>
			<tr class=common>
				<td class=title>职级代码</td>
				<td class=input><input class=code name=AscriptionCode id=AscriptionCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('positionpos',[this,AscriptionCodeName],[0,1],null,'<%=tEdorNo%>','edorno',1,null);" onkeyup="return showCodeListKey('positionpos',[this,AscriptionCodeName],[0,1],null,'<%=tEdorNo%>','edorno',1,null);" elementtype=nacessary> </td>
				<td class=title>职级名称</td>
				<td class=input><input class="wid readonly" name=AscriptionCodeName id=AscriptionCodeName ></td>
				<td class=title></td>
				<td class=input></td>	
			</tr>
			<tr class=common>
				<td class=title>年度起始值（>）</td>
				<td class=input><input class="wid common" name=StartYears id=StartYears elementtype=nacessary></td>
				<td class=title>年度终止值（≤）</td>
				<td class=input><input class="wid common" name=EndYears id=EndYears  elementtype=nacessary></td>	
				<td class=title>归属比例</td>
				<td class=input><input class="wid common" name=Rate id=Rate elementtype=nacessary></td>				  
			</tr>
		 </table>
	
		<div  id= "divRiskSave" style= "display: none"> 
			<input type=button class="cssButton" value="保   存" onclick="saveAscription();">
			<input type=button class="cssButton" value="修   改" style= "display: ''" onclick="updateAscription();">
			<input type=button class="cssButton" value="删   除" style= "display: ''" onclick="deleteAscription();">
		</div>
	</div> 
	<div id="divMultiAgent2" style="display:''">
	    <table class="common">
		<tr> 
			<td class="title">特殊归属规则定义请勾选，请勾选&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="multiageflag" id=multiageflag value="true" onclick="sepicalTpye();"></td>
		</tr>
	    </table>
	</div>		 
	<div id="DivshowSepical" style="display:none">
		<table>
			<tr>
				<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divSepicalType);"></ td>
				<td class=titleImg>特殊归属规则定义</ td>
			</tr>
		</table> 
		<div id="divSepicalType" name=divSepicalType style="display: ''">
			<table class= common>
				<tr class= common>
					<td text-align: left colSpan=1> 
						<span id="spanSepicalAscriptionGrid" ></span>  
					</td>
				</tr>
			</table> 
		<br>
		 	<table class= common>
		 		<tr  class= common> 
		 			<td class=title>归属类型</td>
						<td class=input><input class=codeno name=SepicalType id=SepicalType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" return showCodeList('spepositioncode',[this,SepicalTypeName],[0,1],null,null,null,'1',null);" onkeyup=" return showCodeListKey('spepositioncode',[this,SepicalTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=SepicalTypeName  elementtype=nacessary></td>
						<td class=title>归属比例</td>
						<td class=input><input class="wid common" id=Rate1 name=Rate1 ></td>
						<td class=title></td>
						<td class=input></td>
		 		</tr>
		 	</table>	 	
		 	<div  id= "divSepicalSave" style= "display: none" align= left> 
	 			<input type=button class="cssButton" value="保   存" onclick="saveSpeAscription();">
		  		<input type=button class="cssButton" value="修   改" style= "display: ''" onclick="updateSpeAscription();">
		  		<input type=button class="cssButton" value="删   除" style= "display: ''" onclick="deleteSpeAscription();">
		 	</div>
		</div>
	</div>
		<input type=button class="cssButton" value="关  闭" style= "display: ''" onclick="top.close();">
		<!--隐藏区-->
		<input type=hidden name=Operate>
		<input type=hidden name=GrpPropNo>
		<input type=hidden name=CurrentDate>
		<input type=hidden name=EdorAppNo>
		<input type=hidden name=MissionID> <!-- 工作任务ID -->
		<input type=hidden name=SubMissionID> <!-- 子工作任务ID -->
		<input type=hidden name=ActivityID> <!-- 工作节点ID -->
		<input type=hidden name=mOperate>
		<input type=hidden name=tOperate>
		<input type=hidden name=RiskCode>
</div>
<Br /><Br /><Br /><Br />
</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
