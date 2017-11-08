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
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupUWInit.jsp"%>
</head>
<body  onload="initForm();checkNotePad(PrtNo);" >
  <form action="./GroupUWCho.jsp" method=post id="fm" name=fmQuery target="fraSubmit">
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
    <Div  id= "divGrpCont" style= "display: ''">
    <div class="maxbox">
      <table  class= common>
        <TR  class= common>
          <TD  class= title8>
            团体投保单号码
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="GrpContNo" name=GrpContNo >
          </TD>
      <!--    <TD  class= title8>
            印刷号码
          </TD>-->
 
            <Input type="hidden"  class="readonly" readonly id="PrtNo" name=PrtNo>
        
          <TD  class= title8>
            管理机构
          </TD>
          <TD  class= input8>
            <Input class=codeno id="ManageCom" name=ManageCom readonly=true><input class=codename id="ManageComName" name=ManageComName readonly=true>
          </TD>
           <TD  class= title8>
            销售渠道
          </TD> 
          <TD  class= input8>
          	<input class=codeno id="SaleChnl" name=SaleChnl readonly=true><input class=codename id="SaleChnlName" name=SaleChnlName readonly=true>
          </TD>
        </TR>
        <TR  class= common8>
          
         
          <TD  class= title8>
            所属机构
          </TD>
          <TD  class= input8>
            <input class=codeno id="AgentCom" name=AgentCom readonly=true><input class=codename id="AgentComName" name=AgentComName readonly=true>
          </TD>
          <TD  class= title8>
            业务员
          </TD>
          <TD  class= input8>
      				<input class=codeno id="AgentCode" name=AgentCode readonly=true><input class=codename id="AgentCodeName" name=AgentCodeName readonly=true>
         </TD>
         <TD  class= title8>
            代理人组别
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="AgentGroup" name=AgentGroup>
          </TD>   
        </TR>
        <TR  class= common>
          
          
            <TD  class= title8>
            投保单位客户号码
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="AppntNo" name=AppntNo >
          </TD>       
          <TD  class= title8>
            投保单位名称
          </TD>
          <TD  class= input8>
            <Input class="readonly wid" readonly id="AppntName" name=AppntName >
          </TD>  
          <TD  class= title8>
            VIP标记
          </TD>
          <TD  class= input8>
          	<input class=codeno id="VIPValue" name=VIPValue readonly=true><input class=codename id="VIPValueName" name=VIPValueName readonly=true>
            <INPUT type= "hidden" id="Operator" name= "Operator" value= "">
          </TD>
        </TR>
        <TR  class= common>          
           <TD  class= title8>
            黑名单标记
          </TD>
          <TD  class= input8>
            <input class=codeno id="BlacklistFlag" name=BlacklistFlag readonly=true><input class=codename id="BlacklistFlagName" name=BlacklistFlagName readonly=true>
          </TD>
          </TR>
         
        </table>
       </div>   	
    <div id=DivLCSendTrance STYLE="display:none">
			<table class=common>
				<tr CLASS="common">
			    <td CLASS="title">上报标志 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class=codeno id="SendFlag" name=SendFlag readonly=true><input class=codename id="SendFlagName" name=SendFlagName readonly=true>
	    		</td>
				<td CLASS="title">核保结论 
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input class=codeno id="SendUWFlag" name=SendUWFlag readonly=true><input class=codename id="SendUWFlagName" name=SendUWFlagName readonly=true>
	    		</td>
				<td CLASS="title">核保意见
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input id="SendUWIdea" NAME="SendUWIdea" CLASS="readonly" readonly>
	    		</td>
			</tr>
		</table>		
	</div>


    </Div>
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
  <Div id ='divLCGrpPol' style="display:none">
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
    </div>
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
    </div>
     
 </div>   
   
   
 <!--产品组合队列-->  
   <div id="divGrpContPlan" style="display:none">
   
    <table>
    	<tr>
         <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPlan);">
    		</td>
    		<td class= titleImg>
    			产品组合查询结果
    		</td>
    	</tr>
    </table>
    
    <Div  id= "divLDPlan" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPlanGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </div>
    <table>
     <tr>
    	<td class=common>
    			<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlnaRiskDetail);">
    		</td>
    		<td class=titleImg>险种详细信息</td>
    	</tr>
    </table>
    <div id="divPlnaRiskDetail">
      <Table class=common>
        <tr>
         <td text-align: left colSpan=1>
           <span id="spanPlanRiskGrid"></span>
         </td>
        </tr>
        <tr>
         <TD  class= title>
            产品组合特约信息
          </TD>
          <tr></tr>
          <TD  class= input> <textarea name="PlanSpecContent" id=PlanSpecContent readOnly = "True" cols="100%" rows="3" witdh=100% class="common"></textarea></TD>
        </tr>
      </Table>
     </div>
    
   </div>
	<br>    
	<hr class="line"/>
	<INPUT VALUE ="  团体扫描件查询  " Class="cssButton" id="uwButton1" name="uwButton1" TYPE=button onclick="ScanQuery2();"> 
	<INPUT VALUE =" 团体自动核保信息 " Class="cssButton" id="uwButton2" name="uwButton2" TYPE=button onclick= "showGNewUWSub();">
	<INPUT VALUE ="   团体保单明细   " Class="cssButton" id="uwButton3" name="uwButton3" TYPE=button onclick= "showGrpCont();">
	<!--INPUT VALUE =" 团体既往询价信息 " Class="cssButton" TYPE=button onclick= "showAskApp();"-->
	<!--INPUT VALUE =" 团体既往保障信息 " Class="cssButton" TYPE=button onclick= "showHistoryCont();"-->
	<INPUT VALUE ="   个人核保信息   " Class="cssButton" id="uwButton4" name="uwButton4" TYPE=button onclick="GrpContQuery('<%=tGrpContNo%>');">
	<INPUT VALUE ="团体已承保保单查询" class="cssButton" id="uwButton5" name="uwButton5" TYPE=button onclick="queryProposal();">
  <INPUT VALUE ="团体未承保保单查询" class="cssButton" id="uwButton6" name="uwButton6" TYPE=button onclick="queryNotProposal();">
	<!--INPUT VALUE="团体保单承保特约录入" Class=Common TYPE=button onclick="showGSpec();"-->
	<INPUT VALUE ="团体保单问题件查询" Class="cssButton" id="uwButton7" name="uwButton7"  TYPE=button onclick="GrpQuestQuery();">
	<INPUT VALUE ="  团体既往保全查询" Class="cssButton" id="uwButton14" name="uwButton14" TYPE=button onclick="queryEdor();">
	<INPUT VALUE = "  团体既往理赔查询" Class="cssButton" id="uwButton15" name="uwButton15" TYPE=button onclick="queryClaim();">
	<INPUT VALUE = "    通知书查询    " Class="cssButton" id="uwButton16" name="uwButton16" TYPE=button onclick="queryNotice();">
<br></br>
	
	<!--<INPUT VALUE = "发团体体检通知书" Class="cssButton" TYPE=button onclick="checkBody();">
	<INPUT VALUE = "发团体生调通知书" Class="cssButton" TYPE=button onclick="RReport();">-->
	<INPUT VALUE = "  核保结论通知书  " Class="cssButton" id="uwButton8" name="uwButton8" TYPE=button onclick="GrpSendNotice();">
	
	<!--input value="发核保通知书" class=cssButton type=button onclick="SendNotice();"--> 
	
	<!--	<input value="  承保计划变更  " class=cssButton type=button onclick="showChangePlan();">-->
		<input value="  核保要求通知书  " class=cssButton id="uwButton9" name="uwButton9" type=button onclick="showChangeResultView();">
		<input value=" 团体生调结论查询 " class=cssButton id="uwButton10" name="uwButton10" type=button onclick="showReport();">
		<input value=" 团体体检结论查询 " class=cssButton id="uwButton11" name="uwButton11" type=button onclick="showPE();">
		<!--<input value = "团单加费承保录入" class=cssButton type=button onclick="grpAddFee()">-->
		
		<input value = "   浮动费率调整   " class = cssButton id="uwButton13" name="uwButton13" type = button onclick = "grpFloatRate()">
		<input value = "   风险要素评估   " class=cssButton id="uwButton12" name="uwButton12" type=button onclick="grpRiskElement()">
		<input value = "    暂交费查询    " class=cssButton id="uwButton17" name="uwButton17" type=button onclick="showTempFee()">
	  <input value = " 团单特别约定录入 " class=cssButton id="uwButton18" name="uwButton18" type=button onclick="grpSpecInput()">
		<!--INPUT VALUE="问题件录入" class=cssButton TYPE=button onclick="QuestInput();"-->
	<!--INPUT VALUE = "发送问题件" Class="cssButton" TYPE=button onclick="SendQuest();"-->
	<!--INPUT VALUE = "发送加费核保通知书" Class="cssButton" TYPE=button onclick="SendNotice();"-->
  <hr class="line"/>     
    <!-- 集体单核保结论 -->
<div id="divGrpPolUWResult" style="display:none">    
    <table class= common border=0 width=100%>
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
		<td class= titleImg align= center>团体险种单核保结论：</td>
	</tr>
    </table>
    <div class="maxbox1">
    <table  class= common border=0 width=100%>
      	<TR  class= common>
          <TD  height="29" class= title>
            团体险种单核保结论  
            <!--Input class=codeno name=GUWState ondblclick="return showCodeList('tdhbconlusion',[this,GUWStateName],[0,1]);" onkeyup="return showCodeListKey('tdhbconlusion',[this,GUWStateName],[0,1]);"><Input class=codename name=GUWStateName readonly elementtype=nacessary--> 
            <Input class="code" id="GUWState" name=GUWState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData="0|^1|拒保^s|上浮费率承保^x|下浮费率承保^r|特约承保－责任调整^m|特约承保－免赔额及赔付比例调整^q|特约承保－其它调整^9|标准承保" ondblclick="showCodeListEx('Grpcondition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">                      
          </TD>
        </TR>
          <tr></tr>
          </div>
          <TD  class= title>
            团体险种单核保意见
          </TD>
          <tr></tr>
          <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea name="GUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    </table>
    
    	  <div id=divUWSave style="display:''">		
    	  	<input value = "确  认" Class="cssButton" TYPE=button onclick="grpPolCommit();">         
        </div>
</div>    

<!--产品组合核保结论-->
<div id="divGrpPlanUWResult" style="display:none">    
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>团体产品组合核保结论：</td>
	</tr>
    </table>
    <table  class= common border=0 width=100%>
      	<TR  class= common>
          <TD  height="29" class= title>
            团体产品组合核保结论 
            <!--Input class=codeno name=GUWState ondblclick="return showCodeList('tdhbconlusion',[this,GUWStateName],[0,1]);" onkeyup="return showCodeListKey('tdhbconlusion',[this,GUWStateName],[0,1]);"><Input class=codename name=GUWStateName readonly elementtype=nacessary--> 
            <Input class="code" id="GPlanUWState" name=GPlanUWState readonly='true' CodeData="0|^1|拒保^s|上浮费率承保^x|下浮费率承保^r|特约承保－责任调整^m|特约承保－免赔额及赔付比例调整^q|特约承保－其它调整^9|标准承保" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('Grpcondition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);">                      
          </TD>
        </TR>
          <tr></tr>
          <TD  class= title>
            团体产品组合核保意见
          </TD>
          <tr></tr>
          <TD  class= input> <textarea name="GPlanUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    </table>
    
    	  <div id=divUWSave style="display:''">		
    	  	<input value = "确  认" Class="cssButton" TYPE=button onclick="grpPlanCommit();">         
        </div>
</div>  
        
  	<div id = "divUWAgree" style = "display: none">
    		<INPUT VALUE="同  意" class=cssButton TYPE=button onclick="gmanuchk(1);">
    		<INPUT VALUE="不同意" class=cssButton TYPE=button onclick="gmanuchk(2);">
    		
  	</div>
  
          <input type="hidden" id="WorkFlowFlag" name= "WorkFlowFlag" value= "">
          <input type="hidden" id="MissionID" name= "MissionID" value= "">
          <input type="hidden" id="SubMissionID" name= "SubMissionID" value= "">
          <input type="hidden" id="PrtNoHide" name= "PrtNoHide" value= "">
          <input type="hidden" id="GrpProposalContNo" name= "GrpProposalContNo" value= "">
          <input type="hidden" id="GrpPolNo" name= "GrpPolNo" value="">
          <input type="hidden" id="ContPlanCode" name= "ContPlanCode" value="">
          <input type="hidden" id="PlanType" name="PlanType" value="6">
          <INPUT  type= "hidden" class= Common id="YesOrNo" name= "YesOrNo"  value= "">
           </div>    
    
    <div id = "divUWResult" style = "display: ''">
        <!-- 核保结论 -->
        <table class=common >
            <tr>
                
            <td class= titleImg align= center><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,heId);">&nbsp;团单核保流向：</td></tr>
         
			
            <tr class = common>
            
                <td class=title8>
                <div id="heId" class="maxbox1">
                   &nbsp;&nbsp;&nbsp;&nbsp; 核保流向
              
                    <!--Input class="codeno" name=uwUpReport ondblclick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onkeyup="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary-->
                    <Input class="code" id="uwUpReport" name=uwUpReport style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData="0|^0|处理完毕^2|超权限上报" ondblclick="showCodeListEx('condition1',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition1',[this,''],[0,1]);">                   
                </td>
</div>
            </TR>
            
        </table>
    <table class=common>
		<tr>
		      	<td height="24"  class= title>
            		核保意见
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<td  class= input>&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      		<td>
      			<input id="uwPopedom" name=uwPopedom type=hidden>

      			</td>
      		</tr>
	  </table>
	    <INPUT VALUE="团体投保单整单确认" Class="cssButton" TYPE=button onclick="gmanuchk(0);">
	     <INPUT class=cssButton VALUE="记事本查看"  id="NotePadButton" TYPE=button onclick="showNotePad();">  
	     <INPUT VALUE="返回复核" class=cssButton TYPE=button onclick="returnApprove();"><br/>
	    <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="GoBack();">
	      	
</div>
    
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
