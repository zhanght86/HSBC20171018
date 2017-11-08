<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//程序名称：ContCheckInput.jsp
//程序功能：新单复核页面
//创建日期：2006-2-13 10:33
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script language="javascript">
	
	//从服务器端取得数据:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var NoType = "<%=request.getParameter("NoType")%>";
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var type = "<%=request.getParameter("type")%>";
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	if (ScanFlag == "null") ScanFlag = "0";
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	var LoadFlag ="<%=request.getParameter("LoadFlag")%>"; //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	var ContType = 1;
	var Auditing = "<%=request.getParameter("Auditing")%>";   
	if(Auditing=="null") Auditing=0;   
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";
</script>


<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="ProposalAutoMoveNew.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <script src="ContCheckInput.js"></script>
  <script src="TabAutoScroll.js"></script>
  <script src="../common/javascript/MultiCom.js"></script>
  <%@include file="ContCheckInit.jsp"%>
  <script src="../common/javascript/jquery-1.7.2.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>

	<script src="../common/javascript/Signature.js"></script>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="../common/javascript/ScanPicView.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
}
%>
</head>

  
<body  onload="initForm();initElementtype();window.document.ondblclick=AutoTab;" >
<form action="" method=post name=fm id="fm" target="fraSubmit">
	<!--VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		引入合同信息录入控件   jsp:include page="ContPage.jsp" 
	VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV-->
    <jsp:include page="ContPage.jsp" />

	<!--VVVVVVVVVVVVVVVVVVVV多业务员MultiLine  VVVVVVVVVVVVVVVVVVVVVVVVVVVVV -->
	<div id="DivAgentImpart" style="display:none">
	    <table>
	        <tr>
	            <td class=common>
	                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgentGrid);">
	            </td>
	            <td class= titleImg> 其他业务员信息 </td>
	        </tr>
	    </table>
	    <div id="divMultiAgentGrid" style="display:''">
	        <table class="common">
	            <tr class="common">
	                <td style="text-align:left" colSpan="1"><span id="spanMultiAgentGrid"></span></td>
	            </tr>
	        </table>
	    </div>
	</div>  
	  
	<!--VVVVVVVVVVVVVVVVVVVVVVVVVV业务员告知 VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV -->
	<div id="DivAgentImpart" style="display:''">
		<table>
		    <tr>
		        <td class=common>
		            <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divAgentImpartGrid);">
		        </td>
		        <td class= titleImg>业务员告知</td>
		    </tr>
		</table>
		<div id="divAgentImpartGrid" style="display:''">
		  <table class="common">
		    <tr class="common">
		      <td class="common"><span id='spanAgentImpartGrid'> </span></td>
		    </tr>
		  </table>
		</div>
	</div>  
	<hr class="line">
	<!-- VVVVVVVVVVVVVVVVVVVVVVVVVV引入投保人录入控件界面VVVVVVVVVVVVVVVVVVVVVVVVVVV-->
	<jsp:include page="ComplexAppnt.jsp"/>
	<hr class="line">
    <!-- VVVVVVVVVVVVVVVVVVVVVVVVVV投保人告知信息部分（列表）VVVVVVVVVVVVVVVVVVVVVVVVVV -->
 	<div id="DivLCImpart" style="display:''">
 		<table>
		  <tr>
		      <td class=common>
		          <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divImpartGrid);">
		      </td>
		      <td class= titleImg>投保人告知信息</td>
		  </tr>
		</table>
		<div id="DivLCImpartq" style="display:none">
		<div class="maxbox1">
 		<table class="common">  
			<tr class="common">
				<td class="title">您每年固定收入</td>
				<td class=input>
				<input class='common wid'  name=Income0 id="Income0" verify="投保人收入|NUM&LEN<=8" verifyorder='1' >万元
				</td>
				<td  class="title">主要收入来源：</td>             
				<td class="input">           
				<input class="codeno" name="IncomeWay0" id="IncomeWay0" verify="主要收入来源|LEN<4"  verifyorder="1" onclick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" ondblclick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName0" id="IncomeWayName0" readonly="true">
				</td>   
				<td class=title></td>           
				<td class=input></td>           
			</tr>
			<tr class = "common">
				<td class="title">投保人身高</td><td>
				<input class='common wid' name=AppntStature id="AppntStature" verify="投保人身高|NUM" verifyorder='1' >厘米
				</td>          
				<td class="title">投保人体重</td>
				<td>
				<input class='common wid' name=AppntAvoirdupois id="AppntAvoirdupois" verify="投保人体重|NUM" verifyorder='1' >公斤
				</td>
			</tr>	
 		</table>
 		</div>
 		<input class="cssButton" name=appnthealimpart id="appnthealimpart" type="button" value="投保人健康告知" >
 		<input class="cssButton" name=appntfinaimpart id="appntfinaimpart" type="button" value="投保人财务告知" >
 		</div>
 		<div id="divImpartGrid" style="display:''">
		  <table class="common">
		      <tr class="common">
		          <td style="text-align:left" colSpan="1"><span id="spanImpartGrid"></span> </td>
		      </tr>
		  </table>
		</div>	
		<!--
		<table class=common>
			<tr  class= common> 
				<td  class= common>其它声明（200个汉字以内）</TD>
			</tr>
			<tr  class= common>
				<td  class= common>
					<textarea name="Remark" verify="其他声明|len<800" verifyorder="1" cols="88" rows="2" class="common" ></textarea>
				</td>
			</tr>
		</table>-->
	</div>	
 	<hr class="line">
	<!--VVVVVVVVVVVVVVVVVVVVVVVVVVVVV被保人列表MultiLineVVVVVVVVVVVVVVVVVVVVVVVVVVVVV-->
    
    <div  id= "divInsuredGrid" style= "display:''">
    	<table class=common>
    	  <tr>
    		  <td text-align: left colSpan=1><span id="spanInsuredGrid" ></span></td>
    		</tr>
    	</table>
    </div>
    
    <!--VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV 引入被保人录入控件VVVVVVVVVVVVVVVVVVVVVVVVVVV-->
	<jsp:include page="ComplexInsured2.jsp"/>
	<hr class="line">
	<!--VVVVVVVVVVVVVVVVVVVVVVVV 被保人告知信息部分（列表） VVVVVVVVVVVVVVVVVVVVVVVV-->
	<div id="DivLCImpartInsured" STYLE="display:''">
		<table>
			<tr>
				<td class=common>
				<img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divImpartInsuredGrid);">
				</td>
				<td class= titleImg>被保险人告知信息</td>
			</tr>
		</table>
		<div id="DivLCImpartq" STYLE="display:none">
		<div class="maxbox1">
		<table class="common">  
			<tr class="common">
				<td class="title">您每年固定收入</td>
				<td class = "input">
					<input class="common wid"  name=Income id="Income" verify="被保险人收入|NUM&LEN<=8" verifyorder='2' >万元
				</td>
				<td  class="title">主要收入来源：</td>             
				<td class="input">           
					<input class="codeno" name="IncomeWay" id="IncomeWay" verify="被保人主要收入来源|LEN<4"  verifyorder="2" onclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" id="IncomeWayName" readonly="true">
				</td>
				<td class="title"></td>    
				<td class="input"></td>    
			</tr>
			<tr class="common">
				<td class="title">被保人身高</td>
				<td class = "input">
					<input class="common wid" name=Stature id="Stature"  verify="被保人身高|NOTNULL&NUM" verifyorder='2' elementtype=nacessary>厘米
				</td>          
				<td class="title">被保人体重</td>
				<td class = "input">
					<input class="common wid" name=Avoirdupois id="Avoirdupois" verify="被保人体重|NOTNULL&NUM" verifyorder='2' elementtype=nacessary>公斤
				</td>
			</tr>	       
		</table>
		</div>
		<input class="cssButton" name=insufinaimpart id="insufinaimpart" type="button" value="被保人财务告知" >
		<input class="cssButton" name=insuhealimpart id="insuhealimpart" type="button" value="被保人健康告知" >
		</div>
		<div id="divImpartInsuredGrid" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanImpartInsuredGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	
	<hr class="line">    
    <!-- VVVVVVVVVVVVVVVVVVVVVVVV被保人险种信息部分VVVVVVVVVVVVVVVVVVVVVVVV -->
	<div id="DivLCPol" STYLE="display:''">
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolGrid);">
				</td>
				<td class= titleImg>被保险人险种信息</td>
				<td>
				<input class="cssButton" name=RiskButton id="RiskButton" type="button" value="被保险人险种信息">
				</td>
			</tr>
		</table>
		<div  id= "divLCPolGrid" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td style="text-align:left" colSpan=1>
						<span id="spanPolGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
    
    
    <!--VVVVVVVVVVVVVVVVVVVVVVVV 受益人信息部分（列表） VVVVVVVVVVVVVVVVVVVVVVVV-->
    <div id="DivLCBnf" STYLE="display:''">
		<table>
			<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnfGrid);">
			</td>
			<td class= titleImg>受益人信息</td>
			<td>
				<input class="cssButton" name=BnfButton id="BnfButton" type="button" value="受益人信息" >
			</td>
			</tr>
		</table>
		<div id= "divLCBnfGrid" style= "display: ''" >
			<table class= common>
			<tr class= common>
				<td style="text-align: left" colSpan="1"><span id="spanLCBnfGrid" ></span></td>
			</tr>
			</table>
		</div>
    </div>
    <hr>
    <br>
 <!-- 保单投资计划 -->
	 <Div  id= "divInvestPlanRate" style= "display: 'none'" >
       <table>
        <tr class=common>	
       <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanInvestPlanRate);">
          </td>
    	 	<td class= titleImg>
    			 保单投资计划
    		</td>
    	  </tr>
    	    </table> 
        <table class=common> 
    	   <TR  class= common>
       			<td class="title">保单帐户生效日类型</td> 
       			<td class="input"><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=UintLinkValiFlag id="UintLinkValiFlag" CodeData='0|^2|签单日生效^4|过犹豫期后生效' value=2 onclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" ondblclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);"><input class=codename name=UintLinkValiFlagName id="UintLinkValiFlagName" readonly=true ></td>
       			<td class="title"></td>
       			<td class="input"></td>
       			<td class="title"></td>
       			<td class="input"></td>
        	</TR> 
        </table>
    	<table  class= common>
    		<tr  class= common>
    	  		<td text-align: left colSpan=1>
					  <span id="spanInvestPlanRate" >
					  </span>
				</td>
			</tr>
		</table>
  	</Div>
	<!--
	  ###############################################################################
	                              复核按钮 
	  ###############################################################################
	-->
	<div id = "divApproveContButton" style = "display:none">
		<table>
			<tr><td>
				<input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
				<input id="demo1" name="AppntChkButton" id="AppntChkButton" class=cssButton VALUE=投保人校验 TYPE=hidden onclick='AppntChk();'>
				<input id="demo2" name="InsuredChkButton" id="InsuredChkButton" class=cssButton VALUE="被保人校验" TYPE=hidden onclick='InsuredChk();'>
				<input class=cssButton id="riskbutton5" VALUE="强制进入人工核保" TYPE=button onclick="forceUW();">			
				<input class=cssButton id="intoriskbutton" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
			</td></tr>
			<tr><td>
				<input class=cssButton VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();">	
			    <input class=cssButton VALUE="问题件查询" name="ApproveQuestQuery" id="ApproveQuestQuery" TYPE=button onclick="QuestQuery();">
				<input class=cssButton name="NotePadButton5" id="NotePadButton5" VALUE="记事本查看" TYPE=button onclick="showNotePad();">
				<input class=cssButton id="Donextbutton4" VALUE="复核完毕" TYPE=button onclick="inputConfirm(2);">
			</td></tr>
		</table>
	</div>
	<div id = "divCustomerUnionButton" style = "display:''">
		<table>
			<tr><td>
				<!--input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();"-->
				<!--input class=cssButton id="intoriskbutton" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();"-->

				<input id="demo1" name="AppntChkButton1" id="AppntChkButton1" class=cssButton VALUE="投保人校验" TYPE=button onclick='GoToAppnt();'>
				<input id="demo2" name="InsuredChkButton1" id="InsuredChkButton1" class=cssButton VALUE="被保人校验" TYPE=button  onclick='GoToInsured();'>
			</td></tr>
			<tr><td>
				<!--input class=cssButton VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();"-->	
			    <!--input class=cssButton VALUE="问题件查询" name="ApproveQuestQuery" TYPE=button onclick="QuestQuery();"-->
				<!-- <input class=cssButton name="NotePadButtona" VALUE="记事本查看" TYPE=button onclick="showNotePad();"> -->
				<a href="javascript:void(0)" class=button name="NotePadButtona" id="NotePadButtona" onclick="showNotePad();">记事本查看</a>
				<input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=hidden onClick="QuestInput();">
				<!-- <input class=cssButton id="Donextbutton4" VALUE=" 客户号手工合并完毕 " TYPE=button onclick="UnionConfirm();"> -->
				<a href="javascript:void(0)" class=button id="Donextbutton4" onclick="UnionConfirm();">客户号手工合并完毕</a>
			</td></tr>
		</table>
	</div>
	
<!--
  ###############################################################################
                              随动定制按钮 
  ###############################################################################
-->
    
    <div id="autoMoveButton" style="display:none">
		<input type="button" name="autoMoveInput" id="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('11');" class="cssButton">
		<input type="button" name="Next" id="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
		<input class=cssButton id="intoriskbutton5" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
		<input value="返  回" class="cssButton" type="button" onclick="top.close();">
		<input type=hidden name="autoMoveFlag" id="autoMoveFlag">
		<input type=hidden name="autoMoveValue" id="autoMoveValue">
		<input type=hidden name="pagename" id="pagename" value="11">
    </div>
	
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               隐藏控件位置                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <div  id= "HiddenValue" style="display:none;float: right">
		<input type="hidden" id="fmAction" name="fmAction">
		<!-- 工作流任务编码 -->
		<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
		<input type="hidden" id="MissionID" name="MissionID" value= "">
		<input type="hidden" id="SubMissionID" name="SubMissionID" value= "">
		<input type="hidden" id="ActivityID" name="ActivityID" value= "">
		<input type="hidden" id="NoType" name="NoType" value= "">
		<input type="hidden" id="ContNo" name="ContNo" value="" >
		<input type="hidden" id="ProposalGrpContNo" name="ProposalGrpContNo" value="">
		<input type="hidden" id="SelPolNo" name="SelPolNo" value="">
		<input type="hidden" id="BQFlag" name="BQFlag">  
		<input type='hidden' id="MakeDate" name="MakeDate">
		<input type='hidden' id="MakeTime" name="MakeTime">
		<input type='hidden' id="ContType" name="ContType">
		<input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
        <input type="hidden" id="RollSpeedSelf" name="RollSpeedSelf">
	    <input type="hidden" id="RollSpeedBase" name="RollSpeedBase">
	    <input type="hidden" id="RollSpeedOperator" name="RollSpeedOperator">   
	    <input type="hidden" id="Operator" name="Operator"> 
    </div>          
</form>
<br>
<br>
<br>
<br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
