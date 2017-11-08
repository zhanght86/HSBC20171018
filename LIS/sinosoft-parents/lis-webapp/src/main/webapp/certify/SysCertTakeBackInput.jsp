<% 
//清空缓存
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.utility.*"%>
<html> 
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
	String tContNo = request.getParameter("ContNo"); 
	String tprtNo = request.getParameter("prtNo"); 
	String tAgentName = StrTool.unicodeToGBK(request.getParameter("AgentName"));
	loggerDebug("SysCertTakeBackInput","input中   "+tAgentName);
%>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-07
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language = "javascript">
  var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var sysdate = "<%=tCurrentDate%>"; //记录登陆机构
  var prtNo = "<%=request.getParameter("prtNo")%>";
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
     <script src="../common/javascript/EasyQuery.js"></script>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="SysCertTakeBackInput.js"></SCRIPT>
  <script src="../common/javascript/jquery.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>

	<script src="../common/javascript/Signature.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <%@include file="SysCertTakeBackInit.jsp"%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="ProposalAutoMove3.js"></script>
</head>
<body  onload="initForm()" >
  <form action="./SysCertTakeBackSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
    
    <!-- 单证类型 -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title">单证编码</td>
        <td class="input"><input class="code" name="CertifyCode" value ="9995" readonly =true style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SysCertCode1', [this])"onkeyup="return showCodeListKey('SysCertCode1', [this])" ></td>
       <td class="title"></td><td class="input"></td>
       <td class="title"></td><td class="input"></td>
        </tr></table>
        <!--<input id="sqButton5" class=cssButton VALUE="签名查询"  TYPE=button onclick="fm.SignatureFlag.value='true';signatureQuery(this);" cancut="0" codetype="PrtNo">-->
        <a id="sqButton5"  cancut="0" codetype="PrtNo" href="javascript:void(0);" class="button" onClick="fm.SignatureFlag.value='true';signatureQuery(this);">签名查询</a>
    <!-- 回收的信息 -->
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divTakeBackInfo);"></td>
          <td class="titleImg">回收信息</td></tr></table>

    <Div  id= "divTakeBackInfo" style= "display: ''" class="maxbox">
      <!-- 单证号码和经办日期 -->
      <table class="common"> 
        <tr class="common">
          <td class="title">单证号码</td>
          <td class="input">
          	<!-- input class="common" name="CertifyNo" onblur="checkScan();queryagent();" -->
          	<input class="common" name="CertifyNo" onblur="checkScan();queryagent();">
          	<!--<input class="cssButton" type="button" value="待回收单证查询" style="width:60" onclick="query()">-->
            <a href="javascript:void(0);" class="button"  onClick="query();">待回收单查询</a>
            </td>

          <td class="title">有效日期</td>
          <td class="input"><input class="readonly wid" readonly name="ValidDate" id="ValidDate"></td>
          <td class="title">发放者</td>
          <td class="input"><input class="common wid" name="SendOutCom" id="SendOutCom" verify="发放者|NOTNULL"></td>
          </tr>

        <tr class="common">
          

          <td class="title">业务员姓名</td>
          <td class="input"><input class="common wid" name="AgentName" id="AgentName" ></td>

          <td class="title">接收者</td>
          <td class="input"><input class="common wid" name="ReceiveCom" id="ReceiveCom" verify="接收者|NOTNULL"></td>
          <td class="title">经办人</td>
          <td class="input"><input class="readonly wid" readonly name="Handler" id="Handler"></td>
          </tr>

        <tr class="common">
          

          <td class="title">经办日期</td>
          <td class="input"><input class="readonly wid" readonly name="HandleDate" id="HandleDate"></td>
           <td class="title">操作员</td>
          <td class="input"><input class="readonly wid" readonly name="Operator" id="Operator"></td>
        
          <td class="title">入机日期</td>
          <td class="input"><input class="readonly wid" readonly name="MakeDate" id="MakeDate"></td>
          </tr>
        <tr class="common">
          <td class="title">回收操作员</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackOperator" id="TakeBackOperator"></td>
        
          <td class="title">回收日期</td>
          <td class="input"><input class="common wid"  name="TakeBackDate"  id="TakeBackDate" verify="回收日期|DATE" onblur="confirmSecondInput(this,'onblur');" onFocus="setTakeBackDateFocus();"></td>
		
		  <TD  class= title>延迟送达原因</TD>
		  <TD  class= input>
				     <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=reasoncode id=reasoncode ondblClick="showCodeList('delaybackreason',[this,reasondesc],[0,1]);" onClick="showCodeList('delaybackreason',[this,reasondesc],[0,1]);" onkeyup="showCodeListKey('delaybackreason',[this,reasondesc],[0,1]);"><input class="codename" name="reasondesc" id="reasondesc" value ="" >
		  </TD>
		</tr>
        <tr class="common">
          <td class="title">发放批次号</td>
          <td class="input"><input class="readonly wid" readonly name="SendNo" id="SendNo"></td>
        
          <td class="title">回收批次号</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackNo" id="TakeBackNo"></td>
          <td class="title">回收操作日期</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackMakeDate" id="TakeBackMakeDate"></td>
          </tr>
        
        <tr class="common">
          
          
          <td class="title">回收操作时间</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackMakeTime" id="TakeBackMakeTime"></td></tr>
        
      </table>
    </div>
<!--<table class="common">
    	<tr class="common">
    		<td class="input"><input class="cssButton" type="button" value="单 证 回 收" onclick="submitForm()" ></td>
    		<td class="input"><input class="cssButton" type="button" value="已回收单证查询" onclick="queryClick()" ></td>
    		<td class="input"><input class="cssButton" type="button" value="删除扫描件" onclick="DELPic()" ></td>
    		<td></td>
    		</tr>
    
   
    </table>-->
    
    <a href="javascript:void(0);" class="button" onClick="submitForm();">单证回收</a>
    <a href="javascript:void(0);" class="button" onClick="queryClick();">已回收单证查询</a>
    <a href="javascript:void(0);" class="button" onClick="DELPic();">删除扫描件</a>
  
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
    <input type="hidden" name="hideOperation">
    <input type="hidden" name="hideTakeBackDate">
    <input type="hidden" name="sql_where">
    <input type=hidden id="PrtNo" name="PrtNo" value='<%=tprtNo%>'>
    <input type=hidden id="PolNo" name="PolNo">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="SignatureFlag" name="SignatureFlag" value="false">
  	<input type=hidden id="LastCertifyNo" name="LastCertifyNo" value="">
  	<input type=hidden id="ScanFlag" name="ScanFlag" value="true">
  	<input type="hidden" name="QUERY_FLAG">
  	<input type="hidden" name="ManageCom">
  </form><br><br><br><br>
</body>
</html>
