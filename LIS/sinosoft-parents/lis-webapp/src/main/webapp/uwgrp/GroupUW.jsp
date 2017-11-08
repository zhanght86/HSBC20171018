<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：GroupUW.jsp
//程序功能：团体保单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tContNo = "";
	String tPrtNo = "";
	String tGrpContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";  
	String tActivityID = "";
	String tNoType = "";
		
	tPrtNo = request.getParameter("PrtNo");
	tGrpContNo = request.getParameter("GrpContNo");
	tMissionID = request.getParameter("MissionID");
	tSubMissionID  = request.getParameter("SubMissionID");	
	tActivityID = request.getParameter("ActivityID");	
	tNoType = request.getParameter("NoType");
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	loggerDebug("GroupUW",tGrpContNo+"asdf"+tPrtNo);
%>
<script>
	var PrtNo = "<%=tPrtNo%>";
	var GrpContNo = "<%=request.getParameter("GrpContNo")%>";
	var MissionID = "<%=tMissionID%>";
	var SubMissionID = "<%=tSubMissionID%>";
	var ActivityID = "<%=tActivityID%>";
	var NoType = "<%=tNoType%>";
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupUW.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupUWInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWCho.jsp" method=post name=fmQuery id="fmQuery" target="fraSubmit">
  	<div id = "yaory" style = "display: none">
      <table>
        <tr>
          <TD  class= title5>
            所属机构
          </TD>
          <TD  class= input5>
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=AgentCom id="AgentCom" readonly=true><input class=codename name=AgentComName id="AgentComName" readonly=true>
          </TD>
          <TD  class= title5>
            VIP标记
          </TD>
          <TD  class= input5>
          	<input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=VIPValue id="VIPValue" readonly=true><input class=codename name=VIPValueName id="VIPValueName" readonly=true>
            <INPUT type= "hidden" name= "Operator" id="Operator" value= "">
            <INPUT type= "hidden" name= "temriskcode" id="temriskcode" value= "">
          </TD>
        </tr>
      </table>
    </div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpCont);">
    		</td>
    		<td class= titleImg>
    			团体投保单信息
    		</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divGrpCont" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            投保单号码
          </TD>
 		      <TD  class= input>
            <Input class="readonly wid" readonly name=GrpContNo id="GrpContNo">
      <!--    <TD  class= title8>
            印刷号码
          </TD>-->
            <Input type="hidden"  class="readonly" readonly name=PrtNo id="PrtNo">
          </TD>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ManageCom id="ManageCom" readonly=true><input class=codename name=ManageComName id="ManageComName" readonly=true>
          </TD>
          <TD  class= title>
            销售渠道
          </TD> 
          <TD  class= input>
          	<input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=SaleChnl id="SaleChnl" readonly=true><input class=codename name=SaleChnlName id="SaleChnlName" readonly=true>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            业务员
          </TD>
          <TD  class= input>
      				<Input class="readonly wid" readonly name=AgentCode id="AgentCode">
         </TD>
         <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AgentGroup id="AgentGroup">
          </TD>  
          <TD  class= title>
            投保单位客户号码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntNo id="AppntNo">
          </TD>        
        </TR>
        <TR  class= common>
          <TD  class= title>
            投保单位名称
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntName id="AppntName">
          </TD>  
        </TR>
      </table>
        <div id="DivBlack" STYLE="display:none">
        <table class=common>
        <TR  class= common>          
          <TD  CLASS="title5">
            黑名单标记
          </TD>
          <TD CLASS="input5">
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=BlacklistFlag id="BlacklistFlag" readonly=true><input class=codename name=BlacklistFlagName id="BlacklistFlagName" readonly=true>
          </TD>
          <td CLASS="title5">核保结论 </td>
				  <td CLASS="input5" COLSPAN="1">
					  <input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=SendUWFlag id="SendUWFlag" readonly=true><input class=codename name=SendUWFlagName id="SendUWFlagName" readonly=true>
	    		</td>
        </TR>
        </table>
        </div>
        <div id="DivLCSendTrance" STYLE="display:none">
			  <table class=common>
				  <tr CLASS="common">
			      <td CLASS="title5">上报标志 
	    		  </td>
				    <td CLASS="input5" COLSPAN="1">
				      <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=SendFlag id="SendFlag" readonly=true><input class=codename name=SendFlagName  id="SendFlagName" readonly=true>
	    	    </td>
    				<td CLASS="title5">核保意见</td>
    				<td CLASS="input5" COLSPAN="1">
				      <input NAME="SendUWIdea" id="SendUWIdea" CLASS="readonly wid" readonly>
	    	    </td>
			    </tr>
		    </table>		
	      </div>
    </Div>
    </div>
    <!-- 团体查询条件 -->
    <!--TR  class= common>
      <TD  class= titles>
        团体投保单号码
      </TD>
      <TD  class= input>
        <Input class= common name=GrpProposalNo >
        <Input type= "hidden" class= common name=GrpMainProposalNo >
        <INPUT type= "hidden" name= "Operator" value= "">
      </TD>
    </TR-->       
       
    <!-- 查询未过团体保单（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			团体投保单查询结果
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </Div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolFee);">
    		</td>
    		<td class= titleImg>
    			险种保费信息
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPolFee" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpPolFeeGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </Div>    
   
	<hr class="line">
	<div>
    <INPUT VALUE ="扫描件查询" Class="cssButton" name="scan" id="scan" TYPE=button onclick="ScanQuery2()"> 
    <INPUT VALUE ="团体核保信息" Class="cssButton" name="autoCheck" id="autoCheck" TYPE=button onclick= "showGNewUWSub();"> 
    <a href="javascript:void(0)" class=button name="grpDetails" id="grpDetails" onclick="showGrpCont();">团体保单明细</a> 
    <input value="该团单管理费" class=cssButton name="grpManageFee" id="grpManageFee" type=button onclick="showRealFee();">
    <INPUT VALUE ="团体问题件查询" Class="cssButton" name="grpPolReason" id="grpPolReason" TYPE=button onclick="GrpQuestQuery();"> 
    <a href="javascript:void(0)" class=button name="perAutoCheck" id="perAutoCheck" onclick="GrpContQuery('<%=tGrpContNo%>');">个人自动核保信息</a> 
  </div>
	<!-- <INPUT VALUE ="扫描件查询" Class="cssButton" name="scan" TYPE=button onclick="ScanQuery2()"> 
	<INPUT VALUE ="团体核保信息" Class="cssButton" name="autoCheck" TYPE=button onclick= "showGNewUWSub();">
	<INPUT VALUE ="团体保单明细" Class="cssButton" name="grpDetails" TYPE=button onclick= "showGrpCont();"> -->
	<!--INPUT VALUE =" 团体既往询价信息 " Class="cssButton" TYPE=button onclick= "showAskApp();"-->
	<!--INPUT VALUE =" 团体既往保障信息 " Class="cssButton" TYPE=button onclick= "showHistoryCont();"-->
	<!-- <input value="该团单管理费" class=cssButton name="grpManageFee" type=button onclick="showRealFee();">
	<INPUT VALUE ="团体问题件查询" Class="cssButton" name="grpPolReason" TYPE=button onclick="GrpQuestQuery();">
	<INPUT VALUE =" 个人自动核保信息 " Class="cssButton" name="perAutoCheck" TYPE=button onclick="GrpContQuery('<%=tGrpContNo%>');"> -->
	<br>
	<!--INPUT VALUE ="团体已承保保单查询" class="cssButton"TYPE=button onclick="queryProposal();"-->
  <!--INPUT VALUE ="团体未承保保单查询" class="cssButton"TYPE=button onclick="queryNotProposal();"-->
	<!--INPUT VALUE="团体保单承保特约录入" Class=Common TYPE=button onclick="showGSpec();"-->
	
	
	<!--INPUT VALUE = "团体保单问题件录入" Class="cssButton" TYPE=button onclick="GrpQuestInput();"-->
  <div>
    <a href="javascript:void(0)" class=button name="grpQuestionInput" id="grpQuestionInput" onclick="GrpQuestInput();">团体问题件录入</a>
    <a href="javascript:void(0)" class=button name="questInputConfirm" id="questInputConfirm" onclick="GrpQuestInputConfirm();">问题件录入完毕</a>
    <a href="javascript:void(0)" class=button style="display:none" name="PE" id="PE" onclick="showPE();">团体体检结论查询</a>
    <a href="javascript:void(0)" class=button name="RealFee" id="RealFee" onclick="showContPlan();">保障计划查询</a>
  </div>
	<!-- <INPUT VALUE="团体问题件录入"    class=cssButton name="grpQuestionInput"  TYPE=button onclick="GrpQuestInput();">
	<INPUT VALUE="问题件录入完毕"    class=cssButton name="questInputConfirm"  TYPE=button onclick="GrpQuestInputConfirm();"> -->
	<!--INPUT VALUE = "发团体体检通知书" Class="cssButton" name="perAutoInfo" TYPE=button onclick="checkBody();"-->
	<!-- <input value="团体体检结论查询" style="display:'none'" class=cssButton name="PE" disabled=true type=button onclick="showPE();">
	<input value="保障计划查询" class=cssButton name="RealFee" type=button onclick="showContPlan();"> -->
	
	<!--INPUT VALUE = "发团体生调通知书" Class="cssButton" TYPE=button onclick="RReport();"-->
	<!--INPUT VALUE = "发核保结论通知书" Class="cssButton" TYPE=button onclick="GrpSendNotice();"-->
	
	<!--input value="发核保通知书" class=cssButton type=button onclick="SendNotice();"--> 
	
		<!--input value="  承保计划变更  " class=cssButton type=button onclick="showChangePlan();"-->
	<br>
	<!--INPUT VALUE="分保资料录入"    class=cssButton name="distriContract"  TYPE=button onclick="tt('<%=tGrpContNo%>');"-->
	<!--INPUT VALUE="手续费录入"    class=cssButton name="FeeContract" TYPE=button onclick="pp('<%=tGrpContNo%>');"-->
	<!--input value="个别分红录入" class=cssButton name="perFH"  type=button onclick="showBonus();"-->
	<!--input value="特约录入" class=cssButton name="SpecInfo" type=button onclick="showSpecInfo();"-->
  <div>
    <input value="管理费定义" class=cssButton name="manageFeeQuery" id="manageFeeQuery" type=button onclick="showManaFee();">
    <a href="javascript:void(0)" class=button name="perAutoRea" id="perAutoRea" onclick="GrpContReasonQuery('<%=tGrpContNo%>');">自核未通过清单</a>
    <a href="javascript:void(0)" class=button name="Tbandpay" id="Tbandpay" onclick="UWPastInfo('<%=tGrpContNo%>');">既往投保及赔付情况查询</a>
  </div>
  <br>
	<!-- <input value="管理费定义" class=cssButton name="manageFeeQuery"  type=button onclick="showManaFee();">
	<INPUT VALUE ="自核未通过清单" Class="cssButton" name="perAutoRea" TYPE=button onclick="GrpContReasonQuery('<%=tGrpContNo%>');"> -->
	<!--INPUT VALUE ="强制再保险" Class="cssButton" name="perAutoRea1" TYPE=button onclick="ReLife('<%=tGrpContNo%>');"-->
	<!-- <INPUT VALUE ="既往投保及赔付情况查询" Class="cssButton" name="Tbandpay" TYPE=button onclick="UWPastInfo('<%=tGrpContNo%>');"> -->
	<!--input class=cssButton VALUE="管理费通用查询" TYPE=button onclick="ManageFeeCal();"-->
		<!--input value="修改事项索要材料录入" class=cssButton type=button onclick="showChangeResultView();"-->
		<!--input value="  团体生调结论查询  " class=cssButton type=button onclick="showReport();"-->
		
		<!--input value = "团单加费承保录入" class=cssButton type=button onclick="grpAddFee()"-->
		<!--input value = "风险要素费率调整" class=cssButton type=button onclick="grpRiskElement()">
		<input value = "浮动费率调整" class = cssButton type = button onclick = "grpFloatRate()"-->
		<!--INPUT VALUE="问题件录入" class=cssButton TYPE=button onclick="QuestInput();"-->
	<!--INPUT VALUE = "发送问题件" Class="cssButton" TYPE=button onclick="SendQuest();"-->
	<!--INPUT VALUE = "发送加费核保通知书" Class="cssButton" TYPE=button onclick="SendNotice();"-->
  <hr class="line">    
    <!-- 集体单核保结论 -->
    <div id="div1" style="display:none">
      <table class= common border=0 width=100%>
    	  <tr>
		      <td class= titleImg align= center>团体险种单核保结论：</td>
	      </tr>
      </table>
      <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
            团体险种单核保结论
          </TD>
          <TD class=input5>
            <!--Input class=codeno name=GUWState ondblclick="return showCodeList('tdhbconlusion',[this,GUWStateName],[0,1]);" onkeyup="return showCodeListKey('tdhbconlusion',[this,GUWStateName],[0,1]);"><Input class=codename name=GUWStateName readonly elementtype=nacessary--> 
            <!--Input class="code" name=GUWState CodeData="0|^1|拒保^4|通融承保^9|标准承保" ondblclick="showCodeListEx('Grpcondition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);"-->                   
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name=GUWState class='codeno' id="GUWState" ondblclick="getcodedata();return showCodeListEx('GUWState',[this,GUWStateName],[0,1],'', '', '', true);" onkeyup="getcodedata();return showCodeListEx('GUWState',[this,GUWStateName],[0,1]);"><input name=GUWStateName id="GUWStateName" class=codename readonly=true>
          </TD>
          <td class=title5></td>
          <td class=input5></td>
        </TR>
        <tr>
          <TD  class= title5>
            团体险种单核保意见
          </TD>
          <TD  class= input5> <textarea name="GUWIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></TD>
          <td class=title5></td>
          <td class=input5></td>
        </tr>
      </table>
      <div id="divUWSave" style="display:''">		
        <a href="javascript:void(0)" class=button name="ok" id="ok" onclick="grpPolCommit();">确  认</a>
    	  <!-- <input value = "确  认" Class="cssButton" name="ok" TYPE=button onclick="grpPolCommit();"> -->
      </div>
      <br>
    	<div id = "divUWAgree" style = "display: 'none'">
        <a href="javascript:void(0)" class=button name="isOk" id="isOk" onclick="gmanuchk(1);">同  意</a>
        <a href="javascript:void(0)" class=button name="isNotOk" id="isNotOk" onclick="gmanuchk(2);">不同意</a>
      	<!-- <INPUT VALUE="同  意" class=cssButton name="isOk" TYPE=button onclick="gmanuchk(1);">
      	<INPUT VALUE="不同意" class=cssButton name="isNotOk" TYPE=button onclick="gmanuchk(2);"> -->
      </div>
  
          <input type="hidden" name= "WorkFlowFlag" id="WorkFlowFlag" value= "">
          <input type="hidden" name= "MissionID" id="MissionID" value= "">
          <input type="hidden" name= "SubMissionID" id="SubMissionID" value= "">
          <input type="hidden" name= "PrtNoHide" id="PrtNoHide" value= "">
          <input type="hidden" name= "GrpProposalContNo" id="GrpProposalContNo" value= "">
          <input type="hidden" name= "GrpPolNo" id="GrpPolNo" value="">
          <INPUT  type= "hidden" class= Common name= "YesOrNo" id="YesOrNo" value= ""> 
      <br>   
      <div id = "divChangeResult" style = "display: 'none'">
        <table  class= common>
          <tr class=common>
          	<TD  class= title5>
            		承保计划变更结论录入:
          	</TD>
            <TD  class= input5><textarea name="ChangeIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></TD>
            <td class= title5></td>
            <td class= input5></td>
          </tr>
      	</table>
        <div>
          <a href="javascript:void(0)" class=button onclick="showChangeResult();">确  定</a>
          <a href="javascript:void(0)" class=button onclick="HideChangeResult();">取  消</a>
        </div>
      	<!-- <INPUT VALUE="确  定" class=cssButton TYPE=button onclick="showChangeResult();">
      	<INPUT VALUE="取  消" class=cssButton TYPE=button onclick="HideChangeResult();"> -->
      </div>
    </div>
    <div id = "divUWResult" style = "display: ''">
        <!-- 核保结论 -->
        <table class=common >
            <tr>
              <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
              </td>
              <td class= titleImg align= center>团单核保流向：</td>
            </tr>
        </table>
        <div class="maxbox1">
        <div  id= "divFCDay" style= "display: ''">
        <table class=common>
            <tr class = common>
            <!--td class=title8
                    合 作 方-->
                <!--Input class="code" name=cooperate CodeData="0|^001|美国友邦^002|中科软" ondblclick="showCodeListEx('condition2',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition2',[this,''],[0,1]);"--> 
                <!--Input name=cooperate class='codeno' id="cooperate" ondblclick="getcodedata1();return showCodeListEx('cooperate',[this,cooperateName],[0,1],'', '', '', true);" onkeyup="getcodedata1();return showCodeListEx('cooperate',[this,cooperateName],[0,1]);"><input name=cooperateName class=codename readonly=true-->                 
                
                <td class=title5>
                    风险级别
                </td>
                <!--Input class="code" name=riskgrade CodeData="0|^01|一般^02|中^03|高" ondblclick="showCodeListEx('condition3',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition3',[this,''],[0,1]);"--> 
                <td class=input5>
                <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name=riskgrade class='codeno' id="riskgrade" onclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" ondblclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" onkeyup="getcodedata2();return showCodeListEx('riskgrade',[this,cooperateName],[0,1]);"><input name=riskgradeName id="riskgradeName" class=codename readonly=true><font color=red>*</font>                                  
                </td>
                <td class=title5></td>
                <td class=input5></td>
                
                <!--
                <td class=title8>
                    附加协议
                <Input class="code" name=appcontract CodeData="0|^0|无^1|有" ondblclick="showCodeListEx('condition4',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition4',[this,''],[0,1]);"> 
                 <Input name=appcontract class='codeno' id="appcontract" ondblclick="getcodedata3();return showCodeListEx('appcontract',[this,appcontractName],[0,1],'', '', '', true);" onkeyup="getcodedata3();return showCodeListEx('appcontract',[this,cooperateName],[0,1]);"><input name=appcontractName class=codename readonly=true><font color=red>*</font>                                                   
                </td>
                -->
            </tr>
            <tr class = common>
                <td class=title5>
                    核保流向
                </td>
                    <!--Input class="codeno" name=uwUpReport ondblclick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onkeyup="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary-->
                    <!--Input class="code" name=uwUpReport CodeData="0|^0|处理完毕^2|超权限上报" ondblclick="showCodeListEx('condition1',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition1',[this,''],[0,1]);"-->     
                <td class=input5>              
                   <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" name=uwUpReport class='codeno' id="uwUpReport" onclick="getcodedata4();return showCodeListEx('uwUpReport',[this,uwUpReportName],[0,1],'', '', '', true);" ondblclick="getcodedata4();return showCodeListEx('uwUpReport',[this,uwUpReportName],[0,1],'', '', '', true);" onkeyup="getcodedata4();return showCodeListEx('uwUpReport',[this,uwUpReportName],[0,1]);"><input name=uwUpReportName id="uwUpReportName" class=codename readonly=true><font color=red>*</font>
                </td>
                <td class=title5></td>
                <td class=input5></td>
            </tr>
        </table>
        <table class=common>
  		    <tr class=common>
  		      	<td class= title5 style="width:14.5%;">
              		核保意见
            	</td>
              <td  class= input5 > <textarea name="UWIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></td>
              <td class=title5></td>
              <td class=input5></td>
          </tr>
  		    <tr>
        		<td>
        			<input name=uwPopedom id="uwPopedom" type=hidden>
            </td>
        	</tr>
	      </table>
      </div>
      </div>
      <a href="javascript:void(0)" class=button name="grpPolUtility" id="grpPolUtility" onclick="gmanuchk(0);">团体投保单整单确认</a>
      <a href="javascript:void(0)" class=button onclick="GoBack();">返  回</a>
	    <!-- <INPUT VALUE="团体投保单整单确认" Class="cssButton" name="grpPolUtility" TYPE=button onclick="gmanuchk(0);"> -->
	     <!--INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->  
	    <!-- <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="GoBack();">  	 -->
</div>
    
    
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
