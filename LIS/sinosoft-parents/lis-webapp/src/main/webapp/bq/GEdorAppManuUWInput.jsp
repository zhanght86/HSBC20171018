<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * @direction: 团单保全人工核保整单层主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>团体保全核保</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本  -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="GEdorAppManuUW.js"></script>
    <SCRIPT language="JavaScript" src="./PEdor.js"></SCRIPT>
    <%@ include file="GEdorAppManuUWInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- 受理信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divEdorAppInfo)"></td>
                <td class="titleImg">保全受理信息</td>
            </tr>
        </table>
        <!-- 受理信息结果展现 -->
        <div id="divEdorAppInfo" style="display:''" class="maxbox">
            <table class="common">
                <tr class="common">
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id="EdorAcceptNo" readonly></td>
                    <td class="title">申请号码</td>
                    <td class="input"><input type="text" class="readonly wid" name="OtherNo" id="OtherNo" readonly></td>
                    <td class="title">号码类型</td>
                    <td class="input"><input type="text" class="codeno" name="OtherNoType" id="OtherNoType" readonly><input type="text" class="codename" name="OtherNoTypeName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">申请人</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAppName" id="EdorAppName" readonly></td>
                    <td class="title">申请方式</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" id="AppType" readonly><input type="text" class="codename" name="AppTypeName" readonly></td>
                    <td class="title">管理机构</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom" id="ManageCom" readonly><input class="codename" name="ManageComName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">补退费金额</td>
                    <td class="input"><input type="text" class="readonly wid" name="GetMoney" id="GetMoney" readonly></td>
                    <td class="title">补退费利息</td>
                    <td class="input"><input type="text" class="readonly wid" name="GetInterest" id="GetInterest" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </div>
        <!-- 团单信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divGrpContInfo)"></td>
                <td class="titleImg">团体保单信息</td>
           </tr>
        </table>
        <!-- 团单信息结果展现 -->
        <div id="divGrpContInfo" style="display:''" class="maxbox">
            <table class="common">
                <tr class="common">
                    <td class="title">团体保单号</td>
                    <td class="input"><input type="text" class="readonly wid" name="GrpContNo" id="GrpContNo" readonly></td>
                    <td class="title">团体客户号</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntNo" id="AppntNo" readonly></td>
                    <td class="title">投保单位</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" id="AppntName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">投保人数</td>
                    <td class="input"><input type="text" class="readonly wid" name="Peoples2" id="Peoples2" readonly></td>
                    <td class="title">管理机构</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom" id="ManageCom" readonly><input type="text" class="codename" name="ManageComName" readonly></td>
                    <td class="title">销售渠道</td>
                    <td class="input"><input type="text" class="codeno" name="SaleChnl" id="SaleChnl" readonly><input type="text" class="codename" name="SaleChnlName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">代理人编码</td>
                    <td class="input"><input type="text" class="readonly wid" name="AgentCode" id="AgentCode" readonly></td>
                    <td class="title">代理人姓名</td>
                    <td class="input"><input type="text" class="readonly wid" name="AgentName" id="AgentName" readonly></td>
                    <td class="title">代理人组别</td>
                    <td class="input"><input type="text" class="codeno" name="AgentGroup" id="AgentGroup" readonly><input type="text" class="codename" name="AgentGroupName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">VIP 标记</td>
                    <td class="input"><input type="text" class="codeno" name="VIPValue" id="VIPValue" readonly><input type="text" class="codename" name="VIPValueName" readonly></td>
                    <td class="title">黑名单标记</td>
                    <td class="input"><input type="text" class="codeno" name="BlacklistFlag" id="BlacklistFlag" readonly><input type="text" class="codename" name="BlacklistFlagName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </div>
        
    <Div  id= "divEdorItemInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class=titleImg>保全申请项目信息</td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
        </div>
    </DIV>
        <!-- 险种信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divContInfo)"></td>
                <td class="titleImg">保单信息</td>
           </tr>
        </table> 
        <div id="divContInfo" style="display:''" class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="input">                       
                        <input type="button" class="cssButton" value="保单明细信息" onclick="queryContDetail()">
                        <input type="button" class="cssButton" value="投保单影像查询" onclick="scanQuery()">
                        <input type="button" class="cssButton" value="投保单位已承保保单" onclick="queryProposal()">
                        <input type="button" class="cssButton" value="投保单位未承保保单" onclick="queryNotProposal()">
                        <input type="button" class="cssButton" value="投保单位既往保全" onclick="queryAgoEdor()">
                        <input type="button" class="cssButton" value="投保单位既往理赔" onclick="queryAgoClaim()">
                        <br><br>
                        
                        <!--<input type="button" class="cssButton" value="   投保单位保额累计   " onclick=""> -->
                    </td>
                </tr>
            </table>
        </div>               
        <!-- 险种信息折叠展开 -->
        <!--table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divGrpPolList)"></td>
                <td class="titleImg">团体保单险种信息</td>
           </tr>
        </table-->
        <!-- 险种信息结果展现 -->
        <!--div id="divGrpPolList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanGrpPolGrid"></span></td>
                </tr>
            </table>
            <!-- 险种信息结果翻页 -->
            <!--div id="divGrpPolTP" align="center" style= "display:''">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageGrpPolGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageGrpPolGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageGrpPolGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageGrpPolGrid.lastPage()">
            </div>
        </div-->
        <!-- 核保操作折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divEdorOperations)"></td>
                <td class="titleImg">保全信息</td>
           </tr>
        </table>
        <!-- 核保操作按钮集合 -->
        <div id="divEdorOperations" style="display:''" class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="input">  
                        <input type="button" class="cssButton" value="保全影像查询" onclick="scanDetail()">
                        <!-- <input type="button" class="cssButton" value="   保全核保影像查询   " onclick="UWScanQuery()"> -->
                        <input type="button" class="cssButton" value="进入核保原因" onclick="queryGrpAutoUWTrack()">
                        <!--input type="button" class="cssButton" value="保全明细查询" onclick="edorDetailQuery()"-->
                        <!--input type="button" class="cssButton" value="     个人核保信息     " onclick="gotoContUW()"-->
                        <input value="批单查询" class="cssButton" type="button" onclick="EndorseDetail()">
                        <input value="人名清单查询" class="cssButton" type="button" onclick="NamesBill()">
                        <input type="button" class="cssButton" value="保全操作轨迹" onclick="missionQuery()">
                        <input value="核保查询  " class="cssButton" type="button" onclick="UWQuery()">
                        <br><br>
                        <!--input type="button" class="cssButton" value="   发核保结论通知书   " onclick="noticeEdorUWResult()">
                        <input type="button" class="cssButton" value="   发核保要求通知书   " onclick="sendUWRequest()"-->
                        <!-- 
                        <input type="button" class="cssButton" value="  发团体体检通知书  " onclick="noticeHealthInspect()">
                        <input type="button" class="cssButton" value="  发团体生调通知书  " onclick="noticeLiveInquiry()">
												<br><br> -->
												
                        <!--input type="button" class="cssButton" value="     浮动费率调整     " onclick=""-->                      
                        <!--input type="button" class="cssButton" value=" 已发送核保通知书查询 " onclick="queryAgoNotice()">
                        <input type="button" class="cssButton" value="   投保单位基本情况   " onclick="grpRiskElementView()"-->
                    </td>
                </tr>
            </table>
        </div>
        <!-- 团单核保折叠展开 -->
        <!--table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divGrpContUWInput)"></td>
                <td class="titleImg">团体险种核保结论</td>
            </tr>
        </table-->
        <!-- 团单核保结论录入 -->
        <!--div id="divGrpContUWInput" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">核保结论</td>
                    <td class="input"><input type="text" class="codeno" name="GrpUWState" verify="团体保单核保结论|Code:gedorgrppoluw" ondblClick="showCodeList('gedorgrppoluw',[this,GrpUWStateName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('gedorgrppoluw',[this,GrpUWStateName],[0,1],null,null,null,0,207)"><input type="text" class="codename" name="GrpUWStateName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="title">核保意见</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="input" colspan="6"><textarea class="common" name="GrpUWIdea" cols="108" rows="3" verify="团体保单核保意见|Len<255"></textarea></td>
                </tr>
            </table>
            <!-- 提交数据操作按钮 -->
            <!--input type="button" class="cssButton" value=" 确 定 " onclick="saveGrpContUW()">
            <input type="button" class="cssButton" value=" 重 置 " onclick="resetGrpContUW()">
            <br><br>
        </div-->
        <!-- 申请核保折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAppUWInput)"></td>
                <td class="titleImg">保全申请核保结论</td>
            </tr>
        </table>
        <!-- 申请核保结论录入 -->
        <div id="divEdorAppUWInput" style="display:''" class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="title">核保结论</td>
                    <td class="input">
                    	<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="AppUWState" id="AppUWState" verify="保全申请核保结论|Code:edorgrpuwstate" 
                    	ondblclick="initEdorType(this,AppUWStateName);" onclick="initEdorType(this,AppUWStateName);" onkeyup="actionKeyUp(this,AppUWStateName);"
                    	>
                    	<input type="text" class="codename" name="AppUWStateName" id="AppUWStateName" readonly></td>
                    <td class="title"></td>
                    <td class="input"></td>
                    <td class="title"></td>
                    <td class="input"></td>
                </tr>
                <tr class="common">
                    <td class="title">核保意见</td>
                    
                </tr>
                <tr class="common">
                    <td colspan="6" style="padding-left:16px"><textarea class="common" name="AppUWIdea" cols="224" rows="4" verify="保全申请核保意见|Len<255"></textarea></td>
                </tr>
            </table>
            <!-- 提交数据操作按钮 -->
            <input type="button" class="cssButton" value=" 确 定 " onclick="saveEdorAppUW()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <input type="button" class="cssButton" value=" 下发问题件 " onclick="showPage(this,divAppCancelInfo)">
            <input type="button" class="cssButton" value=" 记事本查看 " onclick="showNotePad()">
            <br><br><br>
        </div>
        
         <Div  id= "divAppCancelInfo" style= "display: none" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>问题件类型</td>
                    <td class=input><Input class="codeno" name=AskFlag id=AskFlag readonly value="1"><input class=codename name=AskFlagName id=AskFlagName readonly value="人工核保问题件"></td>
                   <td class="title"></td>
                    <td class="input"></td>
                    <td class="title"></td>
                    <td class="input"></td>
 
                </tr>
                <tr class=common>
                    <TD class=title > 问题件内容 </TD>
                </tr>
                <tr class=common>
                    <TD colspan="6" style="padding-left:16px" ><textarea name="AskContent" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>
            	 <input type="button" class="cssButton" value=" 下发 " onclick="sendAskMsg()">
             <input type="button" class="cssButton" value=" 问题件查询 " onclick="queryAskMsg()"> 
             
   <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 问题件列表
    		</td>
    	</tr>
    </table>
    <Div  id= "divAgentGrid" style= "display: 'none'">
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align =center>
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  		 <table>
	        <TR>	        	
	         	<TD class= title>问题内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="AskInfo" cols="224" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	      </table>
	      <table>
	        <TR>	        	
	         	<TD class= title>回复内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="ReplyInfo" cols="224" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
	    </div>
    </DIV>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="MissionID" id=MissionID>
        <input type="hidden" name="SubMissionID" id=SubMissionID>
        <input type="hidden" name="ActivityID" id=ActivityID>
        <input type="hidden" name="ActivityStatus" id=ActivityStatus>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="EdorState" id=EdorState>
        <input type="hidden" name="PrtNo" id=PrtNo>
        <input type="hidden" name="GrpPolNo" id=GrpPolNo>
        <input type="hidden" name="ProposalContNo" id=ProposalContNo>
        <input type="hidden" name="PayToDate" id=PayToDate>
        <input type="hidden" name="UWType" id=UWType>
        <input type="hidden" name="ActionFlag" id=ActionFlag>
        <input type="hidden" name="LoginOperator" id=LoginOperator>
        <input type="hidden" name="LoginManageCom" id=LoginManageCom>
        <input type="hidden" name="ContType" value="2" id=ContType>
        <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
        <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
        <input type="hidden" name="EdorValiDate" id=EdorValiDate>
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
