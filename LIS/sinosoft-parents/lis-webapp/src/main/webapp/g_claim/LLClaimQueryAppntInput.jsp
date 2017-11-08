<%
/***************************************************************
 * <p>ProName：LLClaimCustomerInput.jsp</p>
 * <p>Title：系统团体投保人查询</p>
 * <p>Description：系统团体投保人查询/p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : withxiaoqi
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.utility.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	StrTool tStrTool = new StrTool();
	String mAppntName = tStrTool.unicodeToGBK(request.getParameter("AppntName"));
	String mContType = tStrTool.unicodeToGBK(request.getParameter("ContType"));
	String mAcceptCom = tStrTool.unicodeToGBK(request.getParameter("AcceptCom"));
	
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mAppntName = "<%=mAppntName%>";
	var mContType = "<%=mContType%>";
	var mAcceptCom = "<%=mAcceptCom%>";
</script>
<html>
<head>
	<title>系统团体投保人查询</title>
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
	<script src="./LLClaimQueryAppntInput.js"></script>
	<%@include file="./LLClaimQueryAppntInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="" target=fraSubmit>

	<table>
		<tr>
    	<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpAppntList);">
			</td>
			<td class=titleImg>团体投保人信息列表</td>
		</tr>
	</table> 
	<div id="divGrpAppntList" style= "display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGrpAppntListGrid"></span> 
				</td>
			</tr>
		</table>
		<center>
			<input class=cssbutton90 value="首  页" type=button onclick="turnPage1.firstPage();">
			<input class=cssbutton91 value="上一页" type=button onclick="turnPage1.previousPage();">
			<input class=cssbutton92 value="下一页" type=button onclick="turnPage1.nextPage();">
			<input class=cssbutton93 value="尾  页" type=button onclick="turnPage1.lastPage();">
		</center>
	</div>
	<table>
		<tr>
    	<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpAppntDetailList);">
			</td>
			<td class=titleImg>下辖投保单位</td>
		</tr>
	</table> 
	<div id="divGrpAppntDetailList" style= "display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGrpAppntDetailGrid"></span> 
				</td>
			</tr>
		</table>
	</div>	
	
	<input class=cssbutton value="选  择" type=button onclick="returnSelect();">
	<input class=cssbutton value="关  闭" type=button onclick="top.close();"> 
	<input type=hidden name=Operate id=Operate> 	 	 <!--操作类型-->
 </form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
