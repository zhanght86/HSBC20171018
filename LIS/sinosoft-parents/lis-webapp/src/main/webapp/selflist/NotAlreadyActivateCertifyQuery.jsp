<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：NotAlreadyActivateCertifyQuery.jsp
//程序功能：未激活卡单查询
//创建日期：2008-03-17
//创建人  ：zz
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="NotAlreadyActivateCertifyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NotAlreadyActivateCertifyQueryInit.jsp"%>
  <title>已激活卡单查询</title>
</head>
<body  onload="initForm();" >
  <form action="" method=post name=fm id="fm" target="fraSubmit">
    <!-- 卡单信息部分 -->
    <table class="common" border=0 width=100%>
      <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
        <td class="titleImg" align="center">请输入查询条件:</td>
      </tr>
	  </table>
    <div class="maxbox1">
      <Div  id= "divFCDay" style= "display: ''">
    <table class="common" align="center" id="tbInfo" name="tbInfo">
      <tr class="common">
        <td class="title5">卡号</td>
        <td class="input5"><input class="common wid" name="CertifyNo" id="CertifyNo"></td>
        
        <TD  class= title5>是否产品组合</TD>
					<TD  class= input5>
							  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=IsPlan id="IsPlan" readonly=readonly value="1" verify="是否产品组合|NOTNULL" CodeData="0|^0|否^1|是" onclick="showCodeListEx('IsPlan',[this],[0,1]);" ondblclick="showCodeListEx('IsPlan',[this],[0,1]);" onkeyup="showCodeListKeyEx('IsPlan',[this],[0,1]);">

      <tr class="common">
        <td class="title5">缴费日期（开始）</td>
        <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="入机日期（开始）|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
        
        <td class="title5">缴费日期（结束）</td>
        <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="入机日期（结束）|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
     </tr>
     
      <TR><TD colSpan="4"  class= title5><font color=red>(注意：缴费起止期间请控制在十天内，防止数据量过大)</font> </TD></TR>
    </table>
  </Div>
    </div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
    <!-- <input value="查  询" type="button" class="cssButton" onclick="easyQueryClick();"> 	 -->
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCertifyInfo);"></td>
    	<td class= titleImg>卡单信息</td></tr>
    </table>
    
  	<div id="divCardInfo" style="display: ''" align=center>      
      <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanCardInfo"></span></td></tr>
      </table>
      
      <input VALUE=" 首页 " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="上一页" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="下一页" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE=" 尾页 " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
    <br>
    <br>
    <br>
    <br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
