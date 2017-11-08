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
    String tActivityID      = request.getParameter("ActivityID"); 
    tActivityID=tActivityID.replace(" ", ""); 
//=====从工作流保全撤销页面传递过来的参数=====END===================================

%>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./EdorCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorCancelInit.jsp"%>
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
        <Div  id= "divEdorAppInfo" style= "display: ''" class="maxbox">
            <TABLE class=common>
            <!- 显示保全受理的基本信息,包括变更金额 以供参考 ->
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
                    <td class= input><Input type="input" class="readonly wid" readonly name=Apptype id=Apptype></td>
                    <td class=title> 管理机构 </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=ManageCom id=ManageCom></td>
                </tr>
                <tr class=common>
                    <td class=title> 补/退费金额 </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=GetMoney id=GetMoney></td>
                    <td class=title> 补/退费利息 </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=GetInterest id=GetInterest></td>
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
                    <td><span id="spanAppItemGrid"></span></td>
                </tr>
            </table>
            <!-- 批单项目结果翻页 -->
            <div align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPage.lastPage()">
            </div>
        </div>
        
        <INPUT VALUE="保全明细查询" class=cssButton TYPE=button onclick="EdorDetailQuery();">
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
                    <td class=title><font color=red><b>撤销原因:</b></font></td> 
                    <td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=MCanclReason id=MCanclReason verify="撤销原因|NotNull&Code:edorcancelmreason" ondblclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onkeyup="return showCodeListKey('edorcancelmreason',[this,SCanclReasonName],[0,1]);"><input class=codename name=SCanclReasonName id=SCanclReasonName readonly></td>
                    <td class="title"><Div id="divCancelMainReason" style="display:none"> 具体原因 </Div></td>
                    <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" CodeData=""  name="SCanclReason" verify="修改原因|Code:edorcancelsreason" ondblclick="return showCodeListEx('edorcancelsreason',[this,MCanclReasonName],[0,1])" onkeyup="return showCodeListKeyEx('edorcancelsreason',[this,MCanclReasonName],[0,1])"><input class="CodeName" name="MCanclReasonName" readonly></Div></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
     </DIV>
         <Div  id= "divAppCancelA" style= "display: 'none'">            
            <table  class= common>
                <tr class=common>
                    <td class=title>A．	客户原因</td>
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="A01">客户反悔
               <input class=title name=SCanclReason TYPE=radio  value="A02" >家庭矛盾
               <input class=title name=SCanclReason TYPE=radio  value="A03" >客户申请
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelB" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>B．	代办问题</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="B01">越权办理
               <input class=title name=SCanclReason TYPE=radio  value="B02" >代办存在误解
               <input class=title name=SCanclReason TYPE=radio  value="B03" >存在代签字问题
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelC" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>C．	业务员原因</td>
                 
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="C01">越权代办
               <input class=title name=SCanclReason TYPE=radio  value="C02" >存在误导
               <input class=title name=SCanclReason TYPE=radio  value="C03" >存在代签字问题
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelD" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>D．	系统问题</td>
                  
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="D01">系统报错
               <input class=title name=SCanclReason TYPE=radio  value="D02" >结果数据错误
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelE" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>E．	公司原因</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="E01">操作错误
               <input class=title name=SCanclReason TYPE=radio  value="E02" >沟通存在误解
               <input class=title name=SCanclReason TYPE=radio  value="E03">其他部门转办
               <input class=title name=SCanclReason TYPE=radio  value="E04" >不符合公司相关办理规定
               <input class=title name=SCanclReason TYPE=radio  value="E05">超期未确认
               <input class=title name=SCanclReason TYPE=radio  value="E06" >有相同项目未结保全案件
               <input class=title name=SCanclReason TYPE=radio  value="E07" >有其他未结保全案件
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelF" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>F．	司法配合</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="F01">司法配合
              </TD>
             </tr>
           </table>
        </DIV>
        <Div  id= "divAppCancelG" style= "display: 'none'">     
        <table  class= common>
                <tr class=common>
                    <td class=title>G．	其他</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="G01">其他
              </TD>
             </tr>
           </table>
           <table  class= common>
           <tr class=common>
                    <TD class=title colspan=6 > 详细情况 </TD>
                </tr>
                <tr class=common>
                    <TD colspan="6" style="padding-left:16px" ><textarea name="CancelReasonContent" id="CancelReasonContent" cols="226%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>       
        </DIV>                                               

    </DIV>

       
        <INPUT VALUE=" 撤 销 " class=cssButton TYPE=button onclick="cSubmit();">
        <INPUT VALUE=" 清 空 " class=cssButton TYPE=button onclick="cCancel();" style= "display: 'none'">

       
        <INPUT VALUE=" 返 回 " class=cssButton TYPE=button onclick="returnParent();">

    <!-- 隐藏域-->
    <input type="hidden" name="MissionID">
    <input type="hidden" name="SubMissionID">
    <input type="hidden" name="EdorState">
    <input type="hidden" name="EdorMainState">
    <input type="hidden" name="EdorItemState">
    <input type="hidden" name="CancelType">
    <input type="hidden" name="DelFlag">
    <input type="hidden" name="EdorNo">
    <input type="hidden" name="EdorType">
    <input type="hidden" name="ContNo">
    <input type="hidden" name="InsuredNo">
    <input type="hidden" name="PolNo">
    <input type="hidden" name="MakeDate">
    <input type="hidden" name="MakeTime">
    <input type="hidden" name="ActionFlag">
    <input type="hidden" name="OtherNoType">
    <input type="hidden" name="EdorAppDate">
    <input type="hidden" name="EdorValiDate">
    <input type="hidden" name="ActivityID">
    <input type="hidden" name="AppObj" value="I">
    

    <input type="hidden" name="OtherNoType">
    <input type="hidden" name="Apptype">
    <input type="hidden" name="ManageCom">
    <input type="hidden" name="AppntName">
    <input type="hidden" name="PaytoDate">
    <input type="hidden" name="PrtSeq">
    <input type="hidden" name="EdorItemAppDate">
    <input type="hidden" name="GrpContNo">
    <input type="hidden" name="ButtonFlag" value="1">
    <input type="hidden" name="CancelReasonCode">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
