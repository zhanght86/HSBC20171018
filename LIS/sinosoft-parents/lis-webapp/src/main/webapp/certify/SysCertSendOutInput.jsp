<% 
//清空缓存
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-08-07
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="SysCertSendOutInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SysCertSendOutInit.jsp"%>
</head>
<body  onload="initForm()">
  <form action="./SysCertSendOutSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--  <table class="common">
    	<tr class="common">
    		<td class="input"></td></tr>
    
    </table>-->

    <!-- 单证编码 -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">单证编码</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="CertifyCode" id="CertifyCode" ondblclick="return showCodeList('SysCertCode', [this]);" onMouseDown="return showCodeList('SysCertCode', [this]);" onkeyup="return showCodeList('SysCertCode', [this]);" verify="单证编码|NOTNULL"></td>
    	<td class="title5"></td>
        <td class="input5"></td></tr></table>
        </div>
    <!-- 发放的信息 -->    
    <div style="width:120"><!-- this div is used to change output effect. zhouping 2002-08-07 -->
    
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
          <td class="titleImg">发放信息</td></tr></table></div>

    <div id="divSendOutInfo">
      <!-- 单证号码和经办日期 -->
      <div class="maxbox1">
      <table class="common"> 
        <tr class="common">
          <td class="title5">发放机构</td>
          <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom" verify="发放机构|NOTNULL"></td>

          <td class="title5">接收机构</td>
          <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom" verify="接收机构|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">经办人</td>
          <td class="input5"><input class="wid" class="common" name="Handler" id="Handler"></td>

          <td class="title5">经办日期</td>
          <td class="input5"><!--<input class="coolDatePicker" dateFormat="short" name="HandleDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="有效开始日期|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
        
      </table>
      </div>
    </div>

		<input type=hidden name="hideOperation">    

    <!-- 单证列表 -->
    <table>
   	  <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCertifyList);"></td>
    		<td class= titleImg>单证列表</td></tr>
    </table>

		<div id="divCertifyList">
      <table class="common">
        <tr class="common">
          <td text-align: left colSpan=1><span id="spanCertifyList"></span></td></tr>
	  	</table>
		</div>

	  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

    <!-- 
      发放操作人员、发放操作日期、以及领用日期都是自动生成的，不需要用户输入 
    --><br>
    <!--<input class=cssButton type="button" value="发放单证" onclick="submitForm()" >-->
    <a href="javascript:void(0);" class="button" onClick="submitForm();">发放单证</a>
  </form>
</body>
</html>
