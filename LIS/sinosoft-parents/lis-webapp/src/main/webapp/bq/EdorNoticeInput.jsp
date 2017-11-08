<html>
<%
//程序名称：EdorNoticeInput.jsp
//程序功能：保全问题件处理
//创建日期：2008-12-08 15:20:22
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
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //保全受理号
    String tMissionID = request.getParameter("MissionID");          //任务ID
    loggerDebug("EdorNoticeInput","任务号为"+tMissionID);
    String tSubMissionID = request.getParameter("SubMissionID");    //子任务ID
%>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="./EdorNotice.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="EdorNoticeInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./EdorNoticeSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr >
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
            </td>
            <td class= titleImg>保全申请信息</td>
        </tr>
    </table>
    <Div  id= "divEdorAppInfo" style= "display: ''" class=maxbox1>
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
        </TABLE>
   </Div>


    <Div  id= "divEdorItemInfo" style= "display: ''">
        <table>
            <tr>
                <td class=common>
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
            <div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPage2.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPage2.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPage2.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPage2.lastPage()">
            </div>
        </div>
    </DIV>
        <Div  id= "divPayNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" 打印拒绝通知书 "  class=cssButton TYPE=button onclick="InvaliNotice();">
        </Div>
        <Div  id= "divPayPassNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" 打印审核通知书 " class=cssButton TYPE=button onclick="InvaliPassNotice();">
        </Div>  

    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg>下发函件</td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class=maxbox>
            <table  class= common>
                <tr class=common>
                    <td class=title>函件类型</td>
                    <td class=input><Input class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" name=ApproveFlag id=ApproveFlag verify="函件类型|NotNull&Code:EdorNoticeIdea" ondblclick="return showCodeList('edornoticeidea',[this,'edornoticeideaName'],[0,1]);" onkeyup="return showCodeListKey('edornoticeidea',[this,'edornoticeideaName'],[0,1]);"><input class=codename name=edornoticeideaName id=edornoticeideaName readonly></td>
                    <td class="title">下发对象</td>
                    <td class="input"><Input class="codeno" name=SendTo id=SendTo value="1" readonly><input class=codename name=SendToName id=SendToName readonly value="客户"></td>
 
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 函件内容 </TD>
                </tr>
                <tr class=common>
                    <TD  class=input colspan=6 ><textarea name="ApproveContent" id=ApproveContent cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>

            <input type="button" class="cssButton" value=" 确 定 " onClick="ApproveSubmit()">
            <input type="button" class="cssButton" value=" 清 空 " onClick="ApproveCancel()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">

    <!-- 隐藏域-->
    <input type="hidden" id=MissionID name="MissionID">
    <input type="hidden" id=SubMissionID name="SubMissionID">
    <input type="hidden" id=EdorState name="EdorState">
    <input type="hidden" id=EdorMainState name="EdorMainState">
    <input type="hidden" id=EdorItemState name="EdorItemState">
    <input type="hidden" id=CancelType name="CancelType">
    <input type="hidden" id=EdorNo name="EdorNo">
    <input type="hidden" id=EdorType name="EdorType">
    <input type="hidden" id=ContNo name="ContNo">
    <input type="hidden" id=InsuredNo name="InsuredNo">
    <input type="hidden" id=PolNo name="PolNo">
    <input type="hidden" id=MakeDate name="MakeDate">
    <input type="hidden" id=MakeTime name="MakeTime">
    <input type="hidden" id=ActionFlag name="ActionFlag">
    <input type="hidden" id=OtherNoType name="OtherNoType">
    <input type="hidden" id=Apptype name="Apptype">
    <input type="hidden" id=ManageCom name="ManageCom">
    <input type="hidden" id=AppntName name="AppntName">
    <input type="hidden" id=PaytoDate name="PaytoDate">
    <input type="hidden" id=PrtSeq name="PrtSeq">
    <input type="hidden" id=EdorItemAppDate name="EdorItemAppDate">
    <input type="hidden" id=EdorAppDate name="EdorAppDate">
    <input type="hidden" id=EdorValiDate name="EdorValiDate">
    <input type="hidden" id=AppObj name="AppObj" value="I">
    <input type="hidden" id=ButtonFlag name="ButtonFlag" value="1">
	<input type="hidden" id=operator name="operator" value="">
	<input type="hidden" id=EdorPopedom name="EdorPopedom">
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>

</body>
</html>
