<% 
//清空缓存
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html> 
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
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
  <SCRIPT src="GrpSysCertTakeBackInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpSysCertTakeBackInit.jsp"%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="ProposalAutoMove3.js"></script>
</head>
<body  onload="initForm()" >
  <form action="./GrpSysCertTakeBackSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
    
  
    <!-- 单证类型 -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">单证编码</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="CertifyCode" id="CertifyCode" value ="9994" readonly =true ondblclick="return showCodeList('SysCertCode1', [this])"onkeyup="return showCodeListKey('SysCertCode1', [this])" ></td>
        <!--td class="input"><input class="cssButton" type="button" value="扫描件签名查询" onclick="queryScanSignature()" ></td-->
        <td></td>
        </tr>
    <!-- 回收的信息 -->
    <div style="width:120">
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divTakeBackInfo);"></td>
          <td class="titleImg">回收信息</td></tr></table></div>

    <div id="divTakeBackInfo">
      <!-- 单证号码和经办日期 -->
      <div class="maxbox">
      <table class="common"> 
        <tr class="common">
          <td class="title5" width="25%">单证号码</td>
          <td class="input5" width="25%">
          	<input class="wid" class="common" name="CertifyNo" id="CertifyNo" onblur="checkScan();queryagent();">
          	<!--<input class="cssButton" type="button" value="查询单证" style="width:90" onclick="query()">-->
            <a href="javascript:void(0);" class="button" onClick="query();">查询单证</a></td>

          <td class="title5" width="25%">有效日期</td>
          <td class="input5" width="25%"><!--<input class="readonly" readonly name="ValidDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#ValidDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ValidDate id="ValidDate"><span class="icon"><a onClick="laydate({elem: '#ValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">发放者</td>
          <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom" verify="发放者|NOTNULL"></td>

          <td class="title5">接收者</td>
          <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom" verify="接收者|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">经办人</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="Handler" id="Handler"></td>

          <td class="title5">经办日期</td>
          <td class="input5"><!--<input class="readonly" readonly name="HandleDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="有效开始日期|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
        
        <tr class="common">
          <td class="title5">操作员</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="Operator" id="Operator"></td>
        
          <td class="title5">入机日期</td>
          <td class="input5"><!--<input class="readonly" readonly name="MakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
          
        
    
        <tr class="common">
          <td class="title5">回收操作员</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackOperator"></td>
        
          <td class="title5">回收日期</td>
          <td class="input5"><!--<input class="common"  name="TakeBackDate" verify="回收日期|DATE" onblur="confirmSecondInput(this,'onblur');">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackDate'});" verify="回收日期|DATE" onblur="confirmSecondInput(this,'onblur');" dateFormat="short" name=TakeBackDate id="TakeBackDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">发放批次号</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="SendNo" id="SendNo"></td>
        
          <td class="title5">回收批次号</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackNo" id="TakeBackNo"></td></tr>
        
        <tr class="common">
          <td class="title5">回收操作日期</td>
          <td class="input5"><!--<input class="readonly" readonly name="TakeBackMakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackMakeDate'});" verify="回收日期|DATE" onblur="confirmSecondInput(this,'onblur');" dateFormat="short" name=TakeBackMakeDate id="TakeBackMakeDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackMakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
          
          <td class="title5">回收操作时间</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackMakeTime" id="TakeBackMakeTime"></td></tr>
        
      </table>
    </div>

	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
    <input type="hidden" name="hideOperation">
    <input type="hidden" name="hideTakeBackDate">
    <input type="hidden" name="sql_where">
    
    <input type=hidden id="PolNo" name="PolNo">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="SignatureFlag" name="SignatureFlag" value="false">
  	<input type=hidden id="LastCertifyNo" name="LastCertifyNo" value="">
  	<input type=hidden id="ScanFlag" name="ScanFlag" value="true">
  	<input type="hidden" name="QUERY_FLAG">
    </div>
<!--    <table class="common">
    	<tr class="common">
    		<td class="input"><input class="cssButton" type="button" value="单 证 回 收" onclick="submitForm()" ></td>
    		<td class="input"><input class="cssButton" type="button" value="单 证 查 询" onclick="queryClick()" ></td>
    		<td></td>
    		</tr>
    
    </table>--><br>
    <a href="javascript:void(0);" class="button" onClick="submitForm();">单证回收</a>
    <a href="javascript:void(0);" class="button" onClick="queryClick();">单证查询</a>
  </form>
</body>
</html>
