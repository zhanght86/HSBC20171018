<%
//程序名称：在LLSecondManuUWInput.jsp.
//程序功能：理赔个人人工核保-----合同或保单核保
//创建日期：2005-12-29 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人 yuejw    更新日期     更新原因/内容
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
    	String tCaseNo = request.getParameter("CaseNo"); //赔案号		
		String tBatNo = request.getParameter("BatNo");   //批次号			
		String tInsuredNo = request.getParameter("InsuredNo"); //出险人客户号 
		String tClaimRelFlag = request.getParameter("ClaimRelFlag"); //赔案相关标志 	
		
		String tMissionid = request.getParameter("Missionid");   //任务ID 
		String tSubmissionid = request.getParameter("Submissionid"); //子任务ID 
		String tActivityid = request.getParameter("Activityid");  //节点号 	
		
	%>
<head >
	
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="./LLSecondManuUW.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet Type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet Type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet Type=text/css>
	<%@include file="./LLSecondManuUWInit.jsp"%>
</head>
<body  onload="initForm();" >
 <form method=post name=fm id="fm" target="fraSubmit" >
 	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></TD>
			<TD class= titleImg>申请任务列表信息</TD>
		</TR>
	</Table>	
	<Div  id= "DivLLCUWBatchGrid" align= center style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1 ><span id="spanLLCUWBatchGrid"></span> </TD>
			</TR>
		</Table>
	</Div> 
	<Div  id= "divReasonList" style= "display: ''">
    	<table class=common>
                <tr class=common>
                    <TD class=title colspan=6 > 理赔核保原因说明栏(500字以内) </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="InFo1" cols="100%" rows="3" witdh=100% readonly class="common"></textarea></TD>
                </tr>
         </table>
    </Div>
	<!--合同信息-->
	<Div id="DivLCCont3" STYLE="display:none">
	<Table >
		<TR>
			<TD class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></TD>
			<TD class="titleImg">合同详细信息</TD>
		</TR>
	</Table>
	</Div>
	<Div id="DivLCCont" class="maxbox1" STYLE="display:none">
		<Table class=common >
			<TR class=common>
				<TD class="title">保单号 </TD>
				<TD><Input name="ContNo" id="ContNo" Value class="readonly wid" readonly ></TD>
				<TD class="title">管理机构 </TD>
				<TD><Input name="MngCom" id="MngCom"  MAXLENGTH="10" class="readonly wid" readonly></TD>
                <TD class="title" >销售渠道 </TD>
				<TD  class= input><Input class="readonly wid" type=hidden name="SaleChnl" id="SaleChnl" onKeyUp="return showCodeList('salechnl',[this,SaleChnlName],[0,1]);"><input class="readonly wid" name=SaleChnlName id="SaleChnlName" readonly=true></TD>  
				
			</TR>
			<TR class=common>
				<TD class="title">代理人编码 </TD>
				<TD><Input name="AgentCode" class="readonly wid" readonly></TD>
				<TD class="title">代理人姓名 </TD>
				<TD><Input name="AgentName" class="readonly wid" readonly></TD>
				<TD class="title">代理机构</TD>
				<TD ><Input name="AgentCom" class="readonly wid" type=hidden><input class="readonly wid" name=AgentComName readonly=true></TD>  
				<TD ><Input name="PrtNo" class="readonly wid" readonly type="hidden"></TD>						    		
			</TR>
		</Table>
		<Table class=common>
			<TR>
				<TD class= title>备注</TD>
			</TR>
			<TR>
				<TD  class= title> <textarea name="Remark" readonly cols=100% rows="2" witdh=100% class=common></textarea></TD>
			</TR>
	<hr class="line">
		</Table>
	</Div>	
	<!----投保书健康告知栏询问号,体检健康告知栏询问号,对应未告知情况,出险人健康状况告知内容----->
	<Div id="DivLCCont2" STYLE="display:none">
		<Table class=common>
			<TR><TD class= title>投保书健康告知栏询问号：</TD></TR>
			<TR>
				<TD  class= title><textarea name="HealthImpartNo1" id="HealthImpartNo1" readonly cols="100%" rows="1" wiTDh=100% class="common wid"></textarea></TD>
			</TR>
			<TR><TD class= title>体检健康告知栏询问号：</TD></TR>
			<TR>
				<TD  class= title> <textarea name="HealthImpartNo2" id="HealthImpartNo2" readonly cols="100%" rows="1" wiTDh=100% class=common></textarea></TD>
			</TR>
				
			<TR><TD class= title>对应未告知情况：</TD></TR>
			<TR>
				<TD  class= title> <textarea name="NoImpartDesc" id="NoImpartDesc" readonly cols="100%" rows="3" wiTDh=100% class=common></textarea></TD>
			</TR>
			<TR><TD class= title>出险人健康状况告知内容：</TD></TR>
			<TR>
				<TD  class= title> <textarea name="Remark1" id="Remark1" readonly cols="100%" rows="3" wiTDh=100% class=common></textarea></TD>
			</TR>					
		</Table>
	</Div>
	<!-- 投保人信息部分 (为支持团险，增加隐藏域) -->
	<hr class="line">
	<div id = DivForAppnt style = "display:">
    	<Table>
    		<TR>
    			<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);"></TD>
    			<TD class= titleImg>投保人信息</TD>
    		</TR>
    	</Table>
    	<DIV id=DivLCAppntInd class="maxbox1" STYLE="display:''">
    		<Table  class= common>
    			<TR  class= common>        
    				<TD  class= title>客户号</TD>
    				<TD  class= input><Input class="readonly wid" readonly name=AppntNo id="AppntNo"></TD>				
    				<TD  class= title>姓名</TD>
    				<TD  class= input><Input class="readonly wid" readonly name=AppntName id="AppntName"></TD>
    				<TD class= title>性别</TD>
                 <TD class= input>
                    <input class="codeno" name="AppntSex" id="AppntSex" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " type="hidden" onClick="style="background: url(../common/images/select--bg_03.png) no-repeat center right; "" ondblClick="showCodeList('AppntSex',[this,AppntSexName],[0,1]);" onKeyUp="showCodeListKey('AppntSex',[this,AppntSexName],[0,1]);" >
                    <input class="readonly wid" name="AppntSexName" id="AppntSexName" readonly>
                </TD>
    
    			</TR>
    			<TR  class= common>
    				<TD  class= title> 年龄</TD>
    				<TD  class= input><Input class="readonly wid" readonly name="AppntBirthday"  id="AppntBirthday"></TD>				
    				<TD  class= title>职业名称 </TD>
                <TD  class= input>
                    <Input class="readonly wid" name="OccupationCode" id="OccupationCode" readonly >
                </TD>
                <TD  class= title> 职业类别 </TD>
                <TD  class= input>
                    <input class="codeno" name="OccupationType" id="OccupationType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " type="hidden" onClick="showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" ondblClick="showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" onKeyUp="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);" >
                    <input class="readonly wid" name="OccupationTypeName" id="OccupationTypeName" readonly> 
                </TD>        
    		  		
    			</TR>
    			<TR>
    			    <TD CLASS=title>国籍 </TD>
                    <TD CLASS=input COLSPAN=1>
                       <input class="codeno" name="NativePlace" id="NativePlace" type="hidden" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" ondblClick="showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onKeyUp="showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);" >
                       <input class="readonly wid" name="NativePlaceName" id="NativePlaceName" readonly> 
                    </TD>
    
    				<TD  class= title>VIP标记</TD>
    				<TD  class= input><Input class="readonly wid" readonly name=VIPValue id="VIPValue" ></TD>
    				<TD  class=title>黑名单标记</TD>
    		 		<TD  class= input><Input class="readonly wid" readonly name=BlacklistFlag id="BlacklistFlag" ></TD>			 		
    			</TR>
    		</Table>   
    	</DIV>
	</DIV>
	<div id = DivForGrpAppnt style = "display:none">
        <table>
        	<tr>
            	<td class=common>
    			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol2);">
        		</td>
        		<td class= titleImg>
        			 投保单位资料
        		</td>
        	</tr>
        </table>
        <Div  id= "divGroupPol2" class="maxbox1" style= "display: none">
          <table  class= common>
           <TR>
              <TD  class= title5>
                单位名称
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid" name=GrpName id="GrpName" elementtype=nacessary onchange=checkuseronly(this.value) verify="单位名称|notnull&len<=60">
              </TD>
              <TD  class= title5>
                单位地址
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid" name=GrpAddress id="GrpAddress" elementtype=nacessary  verify="单位地址|notnull&len<=60">
              </TD>
                       </TR>
            <TR  class= common>
              <TD  class= title5>
                邮政编码
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid" name=GrpZipCode id="GrpZipCode"  elementtype=nacessary  verify="邮政编码|notnull&zipcode">
              </TD>       
              <TD  class= title5>
                单位电话
              </TD>
              <TD  class= input5>
                <Input class= "readonly wid"  name=Phone id="Phone" elementtype=nacessary verify="单位电话|notnull&len<=30">
              </TD>
            </TR>
          </table>
          
        </Div>
	</DIV>
	
	<hr class="line">

<table>
  <tr>
    <td nowrap>
    <!--INPUT VALUE=" 投保人既往投保信息 " class=cssButton TYPE=button onclick="showApp(1);"-->
    <INPUT VALUE=" 投保人健康告知查询 " class=cssButton TYPE=button name="uwButton9" onClick="queryHealthImpart()">
    <INPUT VALUE=" 投保人既往投保资料查询 " class=cssButton TYPE=button name="uwButton30" onClick="showApp(1)">
 	<Input Value="投保人既往理赔查询" class=cssButton Type=button onClick="AppntQuery();">	
 <!--   <INPUT VALUE=" 投保人体检资料查询 " class=cssButton TYPE=button name="uwButton10" onclick="showBeforeHealthQ()">
    <INPUT VALUE=" 投保人生调资料查询(待定) " class=cssButton TYPE=button name="uwButton10" onclick="showRiseQ()">
    <INPUT VALUE=" 投保人保额累计信息 " class=cssButton TYPE=button name="uwButton11" onclick="amntAccumulate();">
    <INPUT VALUE=" 投保人影像资料查询 " class=cssButton TYPE=bu name="uwButton8"tton onclick=""> -->
    </td>
  </tr>   
  <tr>
    <td nowrap>
    <!-- 
    <INPUT VALUE="投保人已承保保单查询" class=cssButton TYPE=button name="uwButton12" onclick="queryProposal();">
    <INPUT VALUE="投保人未承保保单查询" class=cssButton TYPE=button name="uwButton13" onclick="queryNotProposal();">
    <INPUT VALUE=" 投保人既往保全查询 " class=cssButton TYPE=button name="uwButton14" onclick="queryEdor()">
    <INPUT VALUE=" 投保人既往理赔查询 " class=cssButton TYPE=button name="uwButton15" onclick="queryClaim();">
     -->
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>被保人信息</td>
    	    </tr>
      </table>
    <Div  id= "divLCPol2" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanInsuredGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
    </Div>
    <jsp:include page="../uw/LLManuUWInsuredShow.jsp"/>
	<hr class="line">
	<Table  class=common>
		<TR>
			<Input Value="体检通知书录入" class=cssButton Type=button onClick="showHealth();"> 
			<input class=cssButton id="Donextbutton5" VALUE="  问题件录入  " TYPE=button onClick="QuestInput();">
			<input VALUE="核保分析报告"  class=cssButton name="ReportButton" TYPE=button onClick="showUWReport();">
			<INPUT VALUE="  记事本  " class=cssButton id="NotePadButton" TYPE=button onClick="showNotePad();">
		</TR>
		
		<TR>
			<Input Value=" 体检结果查询 " class=cssButton Type=button onClick="showHealthResult();"> 
			<input value="  查询问题件  " class=cssButton type=button onClick="QuestQuery();">
			<Input Value="  赔 案 查 询 " class=cssButton Type=button onClick="queryClaim();">	
			<Input Value=" 保单信息查询 " class=cssButton Type=button onClick="showContQuery();"> 
			<Input Value=" 影像资料查询 " class=cssButton Type=button onClick="colQueryImage();"> 			
            
			<Input Value="被保人既往理赔查询" class=cssButton Type=button onClick="InsuredQuery();">
			<!-- <input value=" 发核保通知书 " class="cssButton" type="button" name="uwButton23" onclick="SendAllNotice()"> -->
			<!--<Input Value=" 索要问卷录入 " class=cssButton Type=hidden onclick="showUWNotice();"> -->
			<!-- <Input Value="核保通知书录入" class=cssButton Type=button onclick="SendAllNotice();">  -->
       		<input value="体检医院品质管理"   class=cssButton type=button name="uwButtonHospital" onClick="HospitalQuality();" >
		    <input value="业务员品质管理"   class=cssButton type=button name="uwButton26" onClick="AgentQuality();" >
		  	<input value=" 客户品质管理 "   class=cssButton type=button name="uwButton25" onClick="CustomerQuality();" >

		</TR>
		
	</Table>	
	<hr class="line">   
	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWSub);"></TD>
			<TD class= titleImg>查看选中合同的核保轨迹信息</TD>
		</TR>
	</Table>
	<Div id=DivLLCUWSub align= center style="display:''">
		<Table  class= common>
			<TR  class= common>
				<td text-align: left colSpan=1><span id="spanLLCUWSubGrid" ></span></td>
			</TR>
		</Table>
	</Div>
	<Div id = "DivUWResult" style = "display: ''">
		<!-- 核保结论 -->   	
		<Table  class= common>
			<TR class= common>
				<TD class= title>合同核保结论</TD>
				<TD class=input><Input class=codeno readonly name=uwState id="uwState" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('lluwsign',[this,uwStatename],[0,1]);" onDblClick="return showCodeList('lluwsign',[this,uwStatename],[0,1]);" onKeyUp="return showCodeListKey('lluwsign',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename id="uwStatename" readonly ></TD>
				<TD class=title></TD>
				<TD class=input></TD>	
				<TD class=title></TD>
				<TD class=input></TD>	
			</TR>               
		</Table>            
		<Table class=common>
			<TR>
				<TD class= title>核保意见</TD>
			</TR>
			<TR>
				<TD  class= title> <textarea name="UWIdea" cols="100%" rows="5" witdh =100% class=common></textarea></TD>
			</TR>
		</Table>
		<Table class=common>		
			<TR>
				<Input Value="确    定" class=cssButton Type=button onClick="uwSaveClick();">
	    		<Input Value="重    置" class=cssButton Type=button onClick="uwCancelClick();">
        	</TR>		
		</Table>
	</Div>
	<hr class="line">
	<Table  class=common>
	<tr>	
		<TD class= title>返回理赔</TD>
		<TD class=input><Input class=codeno readonly name=lluwflag id="lluwflag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('lluwflag',[this,lluwflagName],[0,1],null,'1 and code = #0#','1');" onDblClick="return showCodeList('lluwflag',[this,lluwflagName],[0,1],null,'1 and code = #0#','1');" onKeyUp="return showCodeListKey('lluwflag',[this,lluwflagName],[0,1],null,'1 and code = #0#','1');"><Input class=codename name=lluwflagName id="lluwflagName" readonly ></TD>
				<TD class=title></TD>
				<TD class=input></TD>	
				<TD class=title></TD>
				<TD class=input></TD>	
	</tr>
	</table>
	<Table class=common>
			<TR>
				<TD class= title>返回原因</TD>
			</TR>
			<TR>
				<TD  class= title> <textarea name="UWIdea2" cols="100%" rows="5" witdh =100% class=common></textarea></TD>
			</TR>
		</Table>
	<!-- <table class=common>
	<TR>
				<TD class= title>核保意见</TD>
	</TR>
	<TR>
		<TD  class= input> <textarea name="UWIdea2" cols="100%" rows="5" witdh =100% class=common></textarea></TD>
	</TR>
	</Table> -->
	<Table  class=common>
		<TR>
			<Input Value ="核保完成" class = cssButton Type=button onClick="llSecondUWFinish();">
			<Input Value ="返    回" class=cssButton Type=button onClick="turnBack();"> 
		</TR>
	</Table>

	
	<!--隐藏区域-->
	<Input Type=hidden id="Operator" name="Operator" ><!--//-->
	<Input Type=hidden id="ComCode" name="ComCode" ><!--//-->
	<Input Type=hidden id="ManageCom" name="ManageCom" ><!--//-->			
	
	<Input Type=hidden id="tCaseNo" name="tCaseNo" ><!--//赔案号<上页传入>-->
	<Input Type=hidden id="tBatNo" name="tBatNo" ><!--//批次号<上页传入>-->
	<Input Type=hidden id="tInsuredNo" name="tInsuredNo" ><!--//出险人客户号<上页传入> -->
	<Input Type=hidden id="tClaimRelFlag" name="tClaimRelFlag" ><!--//赔案相关标志<上页传入> -->	
	<Input Type=hidden id="tMissionid" name="tMissionid"><!--  //任务ID -->
	<Input Type=hidden id="tSubmissionid" name="tSubmissionid"><!--//子任务ID -->
	<Input Type=hidden id="tActivityid" name="tActivityid"><!--//节点号 	-->	
		  
	<Input Type=hidden id="tContNo" name="tContNo" ><!--//合同号码 -->	
	
    <Input Type=hidden id="fmtransact" name="fmtransact" ><!--隐常的操作符号-->
</form>
<br><br><br><br><br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
