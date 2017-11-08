<%
/***************************************************************
 * <p>ProName：LLClaimNoticePrintInput.jsp</p>
 * <p>Title：理赔通知书打印</p>
 * <p>Description：理赔通知书打印</p>
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
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>理赔通知书打印</title>
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
	<script src="./LLClaimNoticePrintInput.js"></script>
	<%@include file="./LLClaimNoticePrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimNoticePrintSave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common><img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></ td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table>
		<div id="divSearch" class=maxbox1 style="display:''">
			<table  class= common>
				<tr class= common>
					<td class= title>批次号</td>
					<td class= input><input class="wid common" name="BatchNo"></td>
					<td class= title>投保人名称</td>
					<td class= input><input class="wid common" name="GrpName"></td>
					<td class= title>受理机构</td>
					<td class= input><input class=codeno  name=MngCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,MngComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,MngComName],[0,1],null,null,null,1);"><input class=codename name=MngComName ></td>
				</tr>
				<tr class= common>
					<td class= title>申请起期</td>
					<td class= input><Input class="coolDatePicker"  dateFormat="short" name=RgtdateStart onClick="laydate({elem: '#RgtdateStart'});" id="RgtdateStart"><span class="icon"><a onClick="laydate({elem: '#RgtdateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class= title>申请止期</td>
					<td class= input><Input class="coolDatePicker"  dateFormat="short" name=RgtdateEnd onClick="laydate({elem: '#RgtdateEnd'});" id="RgtdateEnd"><span class="icon"><a onClick="laydate({elem: '#RgtdateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class= title></td>
					<td class= input></td>
				</tr>
			</table>
   		<input name="AskIn" style="display:''"  class=cssButton type=button value="查  询" onclick="easyQueryClick()">	   
   	</div>	
		<table>
			<tr>
				<td class=common><img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLLMainAskInput1);"></ td>
				<td class=titleImg>理赔通知书列表</td>
			</tr>
		</table>
  
    <div  id= "divLLLLMainAskInput1" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanClaimGrid" ></span>
	        </td>
	      </tr>			
	    </table>
			<center>
				<input class=cssButton90 value="首  页" type=button onclick="turnPage1.firstPage();">
				<input class=cssButton91 value="上一页" type=button onclick="turnPage1.previousPage();">
				<input class=cssButton92 value="下一页" type=button onclick="turnPage1.nextPage();">
				<input class=cssButton93 value="尾  页" type=button onclick="turnPage1.lastPage();">
      </center>
		</div>	  
		
		<input name="GrpInPrint" style="display:''"  class=cssButton type=button value="理赔批次结案通知书打印" onclick="GrpGetPrint()">
		
		</br>
		<table>
			<tr>
				<td class=common><img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch1);"></ td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table>
		<div id="divSearch1" class=maxbox1 style="display:''">
			<table class= common>
				<tr class= common>
					<td  class= title>案件号</td>
					<td  class= input><input class="wid common" name="RgtNo1"></td>
					<td  class= title>投保人名称</td>
					<td  class= input><input class="wid common" name="GrpName1"></td>
					<td  class= title>被保险人姓名</td>
					<td  class= input><input class="wid common" name="CustomerName" ></td>
				</tr>
				<tr  class= common>
					<td class= title>受理机构</td>
					<td class= input><input class=codeno name=MngCom1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,MngComName1],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,MngComName1],[0,1],null,null,null,1);"><input class=codename name=MngComName1 ></td>
					<td class= title>结案起期</td>
					<td class= input><Input class="coolDatePicker"  dateFormat="short" name=EndDateStart onClick="laydate({elem: '#EndDateStart'});" id="EndDateStart"><span class="icon"><a onClick="laydate({elem: '#EndDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class= title>结案止期</td>
					<td class= input><Input class="coolDatePicker"  dateFormat="short" name=EndDateEnd onClick="laydate({elem: '#EndDateEnd'});" id="EndDateEnd"><span class="icon"><a onClick="laydate({elem: '#EndDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
			</table>
   		<input name="AskIn" style="display:''"  class=cssButton type=button value="查  询" onclick="QueryRgtClick()">	   
   	</div>	
	 	<table>
			<tr>
				<td class=common><img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCaseList);"></ td>
				<td class=titleImg>客户信息列表</td>
			</tr>
		</table>
		<div  id= "divCaseList" style= "display: ''">
			<table  class= common>
				<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCaseGrid"></span>
				</td>
			</tr>	
			</table>
			
			<center>
				<input class=cssButton90 value="首  页" type=button onclick="turnPage2.firstPage();">
				<input class=cssButton91 value="上一页" type=button onclick="turnPage2.previousPage();">
				<input class=cssButton92 value="下一页" type=button onclick="turnPage2.nextPage();">
				<input class=cssButton93 value="尾  页" type=button onclick="turnPage2.lastPage();">
      </center> 
    </div>
			<input name="PerInPrint" style="display:''"  class=cssButton type=button value=" 个人结案通知书打印 " onclick="PerGetPrint()">
			<input name="PerInPrint1" style="display:''"  class=cssButton type=button value="个人结案通知书批量打印 " onclick="PerGetPrint1()">
			<input name="PerInPrint" style="display:''"  class=cssButton type=button value=" 个人密封函打印 " onclick="PerSealPrint()">
			<input name="PerInPrint1" style="display:''"  class=cssButton type=button value="个人密封函批量打印 " onclick="AllSealPrint()">
<!--	<input name="ClmBatchPrint2" style="display:''"  class=cssButton type=button value="健康险批次给付批单打印" onclick="BatchPrintTwo()">
			<input name="ClmBatchPrint3" style="display:''"  class=cssButton type=button value="健康险批次密封函打印" onclick="BatchPrintThree()">   -->
			<Input type=hidden  name=Operate >				<!--操作类型-->
			<Input type=hidden  name=GrpRgtNo >
			<Input type=hidden  name=RgtNo >
  	</form>
	<br /><br /><br /><br />
		<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
