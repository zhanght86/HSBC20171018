<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：EdorApproveInput.jsp
//程序功能：保全复核
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>

<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

//=====从工作流保全撤销页面传递过来的参数=====BGN===================================
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //保全受理号
    String tMissionID = request.getParameter("MissionID");          //任务ID
    String tSubMissionID = request.getParameter("SubMissionID");    //子任务ID
//=====从工作流保全撤销页面传递过来的参数=====END===================================
%>


<html>
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保全复核</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
	<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT language="JavaScript" src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdor.js"></script>
    <script language="JavaScript" src="GEdorApprove.js"></script>
    <%@ include file="GEdorApproveInit.jsp" %>
</head>
<body  onload="initForm();" >
  <form name=fm id=fm method=post target="fraSubmit">
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg> 保全申请信息 </td>
        </tr>
    </table>
    <Div  id= "divEdorAppInfo" style= "display: ''" class=maxbox>
        <TABLE class=common>
        <!-- 显示保全受理的基本信息,包括变更金额 以供参考 -->
            <tr class=common>
                <td class=title> 保全受理号 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                <td class=title> 申请号码 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=OtherNo id=OtherNo></td>
                <td class=title> 号码类型 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
            </tr>
            <tr class=common>
                <td class=title> 申请人 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=EdorAppName id=EdorAppName></td>
                <td class=title> 申请方式 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=ApptypeName id=ApptypeName></td>
                <td class=title> 管理机构 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=ManageComName id=ManageComName></td>
            </tr>
            <tr class=common>
                <td class=title> 补/退费金额合计 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=GetMoney id=GetMoney></td>
                <td class=title> 补/退费利息 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=GetInterest id=GetInterest></td>
                <td class= title> </td>
                <td class= input> </td>
            </tr>
        </TABLE>
   </Div>

    <Div  id= "divEdorMainInfo" style= "display: 'none'">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                </td>
                <td class=titleImg>保全申请批单信息</td>
            </tr>
        </table>
        <Div  id= "divEdorMainGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorMainGrid"></span></td>
                </tr>
            </table>
        </div>
    </DIV>

    <Div  id= "divEdorItemInfo" style= "display: 'none'">
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

     <br>
        <input value=" 扫描件明细 " class="cssButton" type="button" onclick="ScanDetail()">
        <input value="  批单查询  " class="cssButton" type="button" onclick="EndorseDetail()">
        <input value="人名清单查询" class="cssButton" type="button" onclick="NamesBill()">
        <input value="  核保查询  " class="cssButton" type="button" onclick="UWQuery()">
        <input value="保单明细查询" class="cssButton" type="button" onclick="GrpContInfoQuery()">
        <input value="保全操作轨迹" class="cssButton" type="button" onclick="MissionQuery()">
        <Div  id= "divGetNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" 付费通知书 " class=cssButton TYPE=button onclick="GetNotice();">
        </Div>
        <Div  id= "divPayNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" 补费通知书 " class=cssButton TYPE=button onclick="PayNotice();">
        </Div>

    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg> 保全复核 </td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class=maxbox>
            <table  class= common>
                <tr class=common>
                    <td class=title> 审批结论 </td>
                    <td class=input><Input class="codeno" name=ApproveFlag id=ApproveFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('edorapproveidea',[this,edorapproveideaName],[0,1])" onkeyup="showCodeListKey('edorapproveidea',[this,edorapproveideaName],[0,1])"><input class=codename name=edorapproveideaName id=edorapproveideaName readonly></td>
                    <!-- XinYQ added on 2005-11-28 : 复核修改原因 : BGN -->
                    <td class="title"><div id="divApproveMofiyReasonTitle" style="display:none"> 修改原因 </div></td>
                    <td class="input"><div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" name="ModifyReason" id=ModifyReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="修改原因|Code:EdorApproveReason" ondblclick="showCodeList('EdorApproveReason',[this,ModifyReasonName],[0,1])" onkeyup="showCodeListKey('EdorApproveReason',[this,ModifyReasonName],[0,1])"><input class="CodeName" name="ModifyReasonName" id=ModifyReasonName readonly></div></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <!-- XinYQ added on 2005-11-28 : 复核修改原因 : END -->
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 审批意见 </TD>
                </tr>
                <tr class=common>
                    <TD  class=input colspan=6 ><textarea name="ApproveContent" id=ApproveContent cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>
    <br>
    <input value=" 确 定 " type="button" class="cssButton" onclick="ApproveSubmit()">
    <input value=" 清 空 " type="button" class="cssButton" onclick="ApproveCancel()">
    <input type="button" class="cssButton" value=" 下发问题件 " onclick="showPage(this,divAskModelInfo)">
    <input value=" 返 回 " type="button" class="cssButton" onclick="returnParent()">
    <!-- 隐藏域-->
    
     <Div id= "divAskModelInfo" style= "display: 'none'">
            <table  class= common>
                <tr class=common>
                    <td class=title>问题件类型</td>
                    <td class=input><Input class="codeno" name=AskFlag id=AskFlag readonly value="2"><input class=codename name=AskFlagName id=AskFlagName readonly value="保全审批问题件"></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
 
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 问题件内容 </TD>
                </tr>
                <tr class=common>
                    <TD  class=input colspan=6 ><textarea name="AskContent" id=AskContent cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
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
    <Div  id= "divAgentGrid" style= "display: 'none'" align =center>
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
	         	<TD class= titleImg>问题内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="AskInfo" id=AskInfo cols="100" rows="4" witdh=25% class="common wid"></textarea></TD>
	       	</TR>
	      </table>
	      <table>
	        <TR>	        	
	         	<TD class= titleImg>回复内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="ReplyInfo" id=ReplyInfo cols="100" rows="4" witdh=25% class="common wid"></textarea></TD>
	       	</TR>
	     </table>
	    </div>
    </DIV>
    <input type="hidden" name="MissionID" id=MissionID>
    <input type="hidden" name="SubMissionID" id=SubMissionID>
    <input type="hidden" name="EdorState" id=EdorState>
    <input type="hidden" name="EdorMainState" id=EdorMainState>
    <input type="hidden" name="EdorItemState" id=EdorItemState>
    <input type="hidden" name="CancelType" id=CancelType>
    <input type="hidden" name="EdorNo" id=EdorNo>
    <input type="hidden" name="EdorType" id=EdorType>
    <input type="hidden" name="ContNo" id=ContNo>
    <input type="hidden" name="InsuredNo" id=InsuredNo>
    <input type="hidden" name="PolNo" id=PolNo>
    <input type="hidden" name="ContType" value="2" id=ContType>
    <input type="hidden" name="MakeDate" id=MakeDate>
    <input type="hidden" name="MakeTime" id=MakeTime>
    <input type="hidden" name="ActionFlag" id=ActionFlag>
    <input type="hidden" name="OtherNoType" id=OtherNoType>
    <input type="hidden" name="Apptype" id=Apptype>
    <input type="hidden" name="ManageCom" id=ManageCom>
    <input type="hidden" name="AppntName" id=AppntName>
    <input type="hidden" name="PaytoDate" id=PaytoDate>
    <input type="hidden" name="PrtSeq" id=PrtSeq>
    <input type="hidden" name="GrpContNo" id=GrpContNo>
    <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
    <input type="hidden" name="EdorAppDate" id=EdorAppDate> 
    <input type="hidden" name="EdorValiDate" id=EdorValiDate>
    <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
    <input type="hidden" name="EdorTypeCal"  id=EdorTypeCal>
    <input type="hidden" name="ContNoApp"  id=ContNoApp>
    <input type="hidden" name="EdorTypeName" id=EdorTypeName>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
