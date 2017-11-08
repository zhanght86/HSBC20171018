<html>
<%
//程序名称：EdorApproveInput.jsp
//程序功能：保全复核
//创建日期：2005-05-08 15:20:22
//创建人  ：sinosoft
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<head >
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

//=====从工作流保全撤销页面传递过来的参数=====BGN===================================
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //保全受理号
    String tMissionID = request.getParameter("MissionID");          //任务ID
    String tSubMissionID = request.getParameter("SubMissionID");    //子任务ID
    String tEdorPopedom = request.getParameter("EdorPopedom");       //最低权限
    String tActivityID      = request.getParameter("ActivityID"); 
    tActivityID=tActivityID.replace(" ", ""); 
//=====从工作流保全撤销页面传递过来的参数=====END===================================

%>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="./EdorApprove.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="EdorApproveInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./EdorApproveSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg>保全申请信息</td>
        </tr>
    </table>
    <Div  id= "divEdorAppInfo" style= "display: ''" class=" maxbox">
        <TABLE class=common>
        <!-- 显示保全受理的基本信息,包括变更金额 以供参考 -->
            <tr class=common>
                <td class=title> 保全受理号 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                <td class=title> 申请号码 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=OtherNo id=OtherNo></td>
                <td class=title> 号码类型 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
            </tr>
            <tr class=common>
                <td class=title> 申请人 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=EdorAppName id=EdorAppName></td>
                <td class=title> 申请方式 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=ApptypeName id=ApptypeName></td>
                <td class=title> 管理机构 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=ManageComName id=ManageComName></td>
            </tr>
            <tr class=common>
                <td class=title> 补/退费金额合计 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=GetMoney id=GetMoney></td>
                <td class=title> 补/退费利息 </td>
                <td class= input><input type="input" class="readonly wid" readonly name=GetInterest id=GetInterest></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </TABLE>
   </Div>

    <Div  id= "divEdorMainInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG src="../common/images/butExpand.gif" style="cursor:hand;" onClick="showPage(this,divEdorMainGrid);">
                </td>
                <td class= titleImg> 保全申请批单信息 </td>
            </tr>
        </table>
        <div id="divEdorMainGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorMainGrid"></span></td>
                </tr>
            </table>
           <!-- <div id="divTurnPageEdorMainGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="首  页" onclick="turnPage1.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPage1.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPage1.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPage1.lastPage()">
            </div>-->
        </div>
    </DIV>

    <Div  id= "divEdorItemInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> 保全申请项目信息 </td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
            <!--<div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="首  页" onclick="turnPage2.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPage2.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPage2.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPage2.lastPage()">
            </div>-->
        </div>
    </DIV>

    <br>
    <INPUT VALUE=" 扫描件明细 " class=cssButton TYPE=button onclick="ScanDetail();">
        <INPUT VALUE="  批单明细  " class=cssButton TYPE=button onclick="EndorseDetail();">
        <INPUT VALUE="  初核情况查询  " class=cssButton TYPE=hidden onclick="UWQuery();">
        <INPUT VALUE="保全明细查询" class=cssButton TYPE=button onclick="EdorDetailQuery();">
        <INPUT VALUE="保全操作轨迹" class=cssButton TYPE=button onclick="MissionQuery();">
  <!--      <Div  id= "divGetNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" 付费通知书 " class=cssButton TYPE=button onclick="GetNotice();">
        </Div>
   -->     
        <Div  id= "divPayNotice" style= "display: none">
            <br>
            <INPUT VALUE=" 打印拒绝通知书 " class=cssButton TYPE=button onclick="InvaliNotice();">
        </Div>
   <!--         
        <Div  id= "divPayPassNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" 打印复核通知书 " class=cssButton TYPE=button onclick="InvaliPassNotice();">
        </Div>  
   -->   
    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg>保全复核</td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>复核结论</td>
                    <td class=input><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ApproveFlag id=ApproveFlag verify="复核结论|NotNull&Code:EdorApproveIdea" ondblclick="return showCodeList('edorapproveidea',[this,edorapproveideaName],[0,1]);" onclick="return showCodeList('edorapproveidea',[this,edorapproveideaName],[0,1]);" onkeyup="return showCodeListKey('edorapproveidea',[this,edorapproveideaName],[0,1]);"><input class=codename name=edorapproveideaName id=edorapproveideaName readonly></td>
                    <!-- XinYQ added on 2005-11-28 : 复核修改原因 : BGN -->
                    <td class="title"><Div id="divApproveMofiyReasonTitle" style="display:none"> 修改原因 </Div></td>
                    <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" name="ModifyReason" verify="修改原因|Code:EdorApproveReason" ondblclick="return showCodeList('EdorApproveReason',[this,ModifyReasonName],[0,1])" onkeyup="return showCodeListKey('EdorApproveReason',[this,ModifyReasonName],[0,1])"><input class="CodeName" name="ModifyReasonName" readonly></Div></td>
                    <td class="title"><Div id="divApproveErrorChk" style="display:none"><input name="ErrorChk" type="checkbox" onclick="setErrorFlag()" >是否为差错件</Div></td>
                    <td class="input">&nbsp;</td>
                    <!-- XinYQ added on 2005-11-28 : 复核修改原因 : END -->
                    <td class=title></td><td class=input></td> <td class=title></td><td class=input></td> 
                </tr></table><table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > 复核意见 </TD>
                </tr>
                <tr class=common>
                    <TD  colspan="6" style="padding-left:16px" ><textarea name="ApproveContent" id="ApproveContent" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>

            <input type="button" class="cssButton" value=" 确 定 " onClick="ApproveSubmit()">
            <input type="button" class="cssButton" value=" 清 空 " onClick="ApproveCancel()">
            <input type="button" class="cssButton" value=" 下发问题件 " onclick="showPage(this,divAskModelInfo)">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
     <Div id= "divAskModelInfo" style= "display: none" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>问题件类型</td>
                    <td class=input><Input class="codeno" name=AskFlag readonly value="3"><input class=codename name=AskFlagName readonly value="保全复核问题件"></td>
                    <td class="title">发送对象：系统操作人</td>
                    <td class="input"><input class=codeno name=SysOperator readonly><input class=codename name=SysOperatorName readonly></td>
 <td class=title></td><td class=input></td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 问题件内容 </TD>
                </tr>
                <tr class=common>
                    <TD colspan="6" style="padding-left:16px" ><textarea name="AskContent" id="AskContent" cols="226%" rows="4" witdh=100% class="common"></textarea></TD>
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
    <Div  id= "divAgentGrid" style= "display: none" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
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
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="AskInfo" cols="226" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	      </table>
	      <table>
	        <TR>	        	
	         	<TD class= title>回复内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="ReplyInfo" cols="226" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
	    </div>
    </DIV>
    <!-- 隐藏域-->
    <input type="hidden" name="MissionID" id=MissionID>
    <input type="hidden" name="SubMissionID" id=SubMissionID>
    <input type="hidden" name="EdorState" id=>
    <input type="hidden" name="EdorMainState" id=EdorState>
    <input type="hidden" name="EdorItemState" id=EdorItemState>
    <input type="hidden" name="CancelType" id=CancelType>
    <input type="hidden" name="EdorNo" id=EdorNo>
    <input type="hidden" name="EdorType" id=EdorType>
    <input type="hidden" name="ContNo" id=ContNo>
    <input type="hidden" name="InsuredNo" id=InsuredNo>
    <input type="hidden" name="PolNo" id=PolNo>
    <input type="hidden" name="ActivityID" id=ActivityID>
    <input type="hidden" name="MakeDate" id=MakeDate>
    <input type="hidden" name="MakeTime" id=MakeTime>
    <input type="hidden" name="ActionFlag" id=ActionFlag>
    <input type="hidden" name="OtherNoType" id=OtherNoType>
    <input type="hidden" name="Apptype" id=Apptype>
    <input type="hidden" name="ManageCom" id=ManageCom>
    <input type="hidden" name="AppntName" id=AppntName>
    <input type="hidden" name="PaytoDate" id=PaytoDate>
    <input type="hidden" name="PrtSeq" id=PrtSeq>
    <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
    <input type="hidden" name="EdorAppDate" id=EdorAppDate>
    <input type="hidden" name="EdorValiDate" id=EdorValiDate>
    <input type="hidden" name="AppObj" value="I" id=AppObj>
    <input type="hidden" name="ErrorChkFlag" value="N" id=ErrorChkFlag>
    <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
	<input type="hidden" name="operator" value="" id=operator>
	<input type="hidden" name="EdorPopedom" id=EdorPopedom>
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>
</html>
