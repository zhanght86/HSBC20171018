<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    String tPNo = "";
    try
    {
        tPNo = request.getParameter("PNo");
    }
    catch( Exception e )
    {
        tPNo = "";
    }
//only use for bq
//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体下个人投保单明细查询
// 5 -- 复核
// 6 -- 查询
// 7 -- 保全新保加人
// 8 -- 保全新增附加险
// 9 -- 无名单补名单
// 10-- 浮动费率
// 99-- 随动定制

    String tLoadFlag = "";
    try
    {
        tLoadFlag = request.getParameter( "LoadFlag" );
        //默认情况下为个人保单直接录入
        if( tLoadFlag == null || tLoadFlag.equals( "" ))
            tLoadFlag = "1";
    }
    catch( Exception e1 )
    {
        tLoadFlag = "1";
    }
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
  loggerDebug("NSProposalInput","LoadFlag:" + tLoadFlag);
//  loggerDebug("NSProposalInput","############tGI.ManageCom===="+tGI.ManageCom);
//  String APYriskcode=request.getParameter("riskcode");
//  loggerDebug("NSProposalInput","附加险的主险编码===="+APYriskcode);
//  String queryCode="";
//  //////判断是不是主险，如果是就保存到SESSION中保证可以连续的添加附加险
//  if(APYriskcode!=null){
//  ExeSQL exeSql = new ExeSQL();
//  SSRS MRSSRS = new SSRS();
//  MRSSRS = exeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+APYriskcode+"'");
//  loggerDebug("NSProposalInput","如果是主险就存放到session====="+MRSSRS.GetText(1,1));
//
//    if(MRSSRS.GetText(1,1).equals("M"))
//    {
//      session.putValue("MainRiskNo",APYriskcode);
//
//    }
//  }
//   queryCode="***-"+(String)session.getValue("MainRiskNo");
//loggerDebug("NSProposalInput","投保单号====="+request.getParameter("tBpno"));
//loggerDebug("NSProposalInput","标记======"+tLoadFlag);
String queryRiskarray="**-"+request.getParameter("tBpno");
%>


<html>
<head>
<script language="javascript">

    var LoadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
    var BQFlag = "<%=request.getParameter("BQFlag")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
    var prtNo = "<%=request.getParameter("prtNo")%>";
        //alert(prtNo);
    var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var checktype = "<%=request.getParameter("checktype")%>";
    var scantype = "<%=request.getParameter("scantype")%>";
    var mainnoh = "";
    var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
    if (ContType == "null")
      ContType=1;
    var EdorType = "<%=request.getParameter("EdorType")%>";
    var param="";
    var tMissionID = "<%=request.getParameter("MissionID")%>";
    var tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
    var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
    var PolSaveFlag="<%=request.getParameter("PolSaveFlag")%>";
    //alert(PolSaveFlag);
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>

    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

    <SCRIPT src="NSProposalInput.js"></SCRIPT>
    <SCRIPT src="SpecDealByRisk.js"></SCRIPT>

    <%@include file="NSProposalInit.jsp"%>
<%
  if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
  {
%>
    <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
        <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
<%
  }
%>
</head>

<body  onload="initForm()">
  <form action="./NSProposalSave.jsp" method=post name=fm id=fn target="fraSubmit">



    <div id= "divRiskCode0" class=maxbox1 style="display: ">
      <table class=common>
         <tr class=common>
            <TD  class= title5>险种编码</TD>
            <%if(tLoadFlag.equals("18")) {%>
            <TD  class= input5>
              <Input class="codeno" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('RiskCode',[this,'RiskCodeName'],[0,1]);" onkeyup="showCodeListKeyEx('RiskCode',[this,'RiskCodeName'],[0,1]);"><input class="codename" name="RiskCodeName" id=RiskCodeName readonly="readonly">
            </TD>
            <%}else if(!tLoadFlag.equals("2") && !tLoadFlag.equals("8")) {%>
            <TD  class= input5>
              <Input class="codeno" name=RiskCode  id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('RiskCode',[this,'RiskCodeName'],[0,1],null,null,null,null,'400');" onkeyup="showCodeListKey('RiskCode',[this,'RiskCodeName'],[0,1],null,null,null,null,'400');"><input class="codename" id=RiskCodeName name="RiskCodeName" readonly="readonly">
            </TD>
            <%}else{ %>
            <TD  class= input5>
               <Input class=codeno name=RiskCode  id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData=""  ondblclick="showCodeListEx('RiskCode',[this,'RiskCodeName'],[0,1]);" onkeyup="return showCodeListKeyEx('RiskCode',[this,'RiskCodeName'],[0,1]);"><input class=codename name=RiskCodeName id=RiskCodeName readonly="readonly" elementtype=nacessary>
            </TD>
            <%} %>
			<td class=title5></td>
			<td class=input5></td>
         </tr>
      </table>
    </div>

    <!-- 隐藏信息 -->
    <div  id= "divALL0" style= "display: none">
    <!-- 连带被保人信息部分（列表） -->
        <div  id= "divLCInsured0" style= "display: none">
        <table>
          <tr>
            <td class=common>
                  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
              </td>
              <td class= titleImg>
                  连带被保人信息
              </td>
          </tr>
        </table>
          <div  id= "divLCInsured2" style= "display:  ">
          <table  class= common>
            <tr  class= common>
              <td><span id="spanSubInsuredGrid"></span></td>
                </tr>
              </table>
          </div>
        </div>
      <!-- 受益人信息部分（列表） -->
      <table>
        <tr>
          <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
            </td>
          <td class= titleImg>
              受益人信息
            </td>
        </tr>
      </table>
        <div id= "divLCBnf1" style= "display:  " >
        <table class= common>
          <tr class= common>
            <td><span id="spanBnfGrid"></span></td>
              </tr>
            </table>
        </div> 
      <!-- 告知信息部分（列表） -->
      <table>
        <tr>
          <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
            </td>
            <td class= titleImg>
              告知信息
            </td>
        </tr>
      </table>
        <div  id= "divLCImpart1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td><span id="spanImpartGrid" ></span></td>
              </tr>
            </table>
        </div>
      <!-- 特约信息部分（列表） -->
    <div  id= "divLCSpec" style= "display: none">
      <table>
        <tr>
          <td class=common>
                <IMG src= "../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCSpec1);">
            </td>
            <td class= titleImg>
              特约信息
            </td>
        </tr>
      </table>
        <div  id= "divLCSpec1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td><span id="spanSpecGrid"></span></td>
              </tr>
            </table>
            <div id= "divHideButton" style= "display:  ">
          <input type="button" class=cssButton name= "back" value= "上一步" onclick="returnparent();">
        </div>
       <!--     <div id= "divHideButton" style= "display:  ">
            <input type="button" name= "quest" value= "问题件录入">
        </div>-->
        </div>
    </div>
      <!--可以选择的责任部分，该部分始终隐藏-->
        <div id="divDutyGrid" style= "display:  ">
        <table class= common>
          <tr class= common>
                  <td><span id="spanDutyGrid"></span></td>
              </tr>
            </table>
            <!--确定是否需要责任信息-->
        </div>


        <div id= "divPremGrid1" style= "display:  ">
            <table class= common>
                <tr class= common>
                <td><span id="spanPremGrid"></span></td>
                </tr>
            </table>
        </div>
    </div>
    <div  id= "divButton" style= "display:  ">
     <%@include file="../common/jsp/ProposalOperateButton.jsp"%>
    </div> 
    <!--add by yaory-->
     <input type=hidden name="tLoadFlag" value="<%=tLoadFlag%>">
    <!--end add-->
    <input type=hidden name="inpNeedDutyGrid" value="0">
      <input type=hidden name="inpNeedPremGrid" value="0">
      <input type=hidden name="fmAction">
      <input type="hidden" name="SelPolNo">
      <input type=hidden name="ContType">
      <input type=hidden name="ContNo">  
     <input type=hidden name="MainPolNo">                                                  
    <input type=hidden name=BQFlag>
    <input type='hidden' name="ProposalContNo">
    <input type='hidden' name="WorkFlowFlag">
    <input type='hidden' name="MissionID">
    <input type='hidden' name="SubMissionID">
    <input type=hidden name="BQContNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>
