<html>
<%
//程序名称：EdorCancelInput.jsp
//程序功能：保全撤销
//创建日期：2005-05-08 09:20:22
//创建人  ：zhangtao
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
//=====从工作流保全撤销页面传递过来的参数=====END===================================

%>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./GEdorCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="GEdorCancelInit.jsp"%>

</head>
<body  onload="initForm();" >
    <form action="./PEdorAppCancelSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
                </td>
                <td class= titleImg> 保全申请信息 </td>
            </tr>
        </table>
        <Div  id= "divEdorAppInfo" style= "display: ''" class="maxbox1">
            <TABLE class=common>
            <!- 显示保全受理的基本信息,包括变更金额 以供参考 ->
                <tr class=common>
                    <td class=title> 保全受理号 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                    <td class=title> 申请号码 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=OtherNo id=OtherNo></td>
                    <td class=title> 号码类型 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
                </tr>
                <tr class=common>
                    <td class=title> 申请人 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=EdorAppName id=EdorAppName></td>
                    <td class=title> 申请方式 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=Apptype id=Apptype></td>
                    <td class=title> 管理机构 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=ManageCom id=ManageCom></td>
                </tr>
                <tr class=common>
                    <td class=title> 补/退费金额 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=GetMoney id=GetMoney></td>
                    <td class=title> 补/退费利息 </td>
                    <td class= input><Input class="wid" type="input" class="readonly" readonly name=GetInterest id=GetInterest></td>
                    <td class= title> </td>
                    <td class= input> </td>
                </tr>
            </TABLE>
        </Div>
        
        <!-- XinYQ commented on 2006-02-09 : 初始化显示保单批单及项目信息 : BGN -->
        <!--
        <Div  id= "divEdorMainInfo" style= "display: 'none'">
            <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                    </td>
                    <td class= titleImg> 保全申请批单信息 </td>
                </tr>
            </table>
            <Div  id= "divEdorMainGrid" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1>
                            <span id="spanEdorMainGrid" >
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </DIV>
        <Div  id= "divEdorItemInfo" style= "display: 'none'">
            <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                    </td>
                    <td class= titleImg> 保全申请项目信息 </td>
                </tr>
            </table>
            <Div  id= "divEdorItemGrid" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1>
                            <span id="spanEdorItemGrid" >
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </DIV>
        -->
        <!-- XinYQ commented on 2006-02-09 : 初始化显示保单批单及项目信息 : BGN -->

        <!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : BGN -->
        <!-- 批单项目折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divAppItemList)"></td>
                <td class="titleImg">批单项目信息</td>
           </tr>
        </table>
        <!-- 批单项目结果展现 -->
        <div id="divAppItemList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
            <!-- 批单项目结果翻页 -->
            <!--<div align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPage.lastPage()">
            </div>-->
        </div>
        <!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : END -->

    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg> 保全申请撤销 </td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>撤销原因</td>
                    <td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CancelReasonCode" id="CancelReasonCode" verify="保全申请撤销原因|NotNull&Code:CancelEdorReason" ondblclick="return showCodeList('CancelEdorReason',[this,CancelReasonName],[0,1]);" onMouseDown="return showCodeList('CancelEdorReason',[this,CancelReasonName],[0,1]);" onkeyup="clearEmptyCode(this,CancelReasonName); return showCodeListKey('CancelEdorReason',[this,CancelReasonName],[0,1]);"><input class=codename name=CancelReasonName id=CancelReasonName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 详细情况 </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px"  class=input colspan=6 ><textarea name="CancelReasonContent" id=CancelReasonContent cols="222%" rows="4" witdh=100% class="common wid"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>

        
        <INPUT VALUE="撤  销" class=cssButton TYPE=button onclick="cSubmit();">
        <INPUT VALUE="清  空" class=cssButton TYPE=button onclick="cCancel();" style= "display: 'none'">
        <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="returnParent();">

    <!-- 隐藏域-->
    <input type="hidden" name="MissionID" id=MissionID>
    <input type="hidden" name="SubMissionID" id=SubMissionID>
    <input type="hidden" name="EdorState" id=EdorState>
    <input type="hidden" name="EdorMainState" id=EdorMainState>
    <input type="hidden" name="EdorItemState" id=EdorItemState>
    <input type="hidden" name="DelFlag" id=DelFlag>
    <input type="hidden" name="EdorNo" id=EdorNo>
    <input type="hidden" name="EdorType" id=EdorType>
    <input type="hidden" name="ContNo" id=ContNo>
    <input type="hidden" name="InsuredNo" id=InsuredNo>
    <input type="hidden" name="PolNo" id=PolNo>
    <input type="hidden" name="MakeDate" id=MakeDate>
    <input type="hidden" name="MakeTime" id=MakeTime>
    <input type="hidden" name="ActionFlag" id=ActionFlag>
    <input type="hidden" name="OtherNoType" id=OtherNoType>
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>

</body>
</html>
