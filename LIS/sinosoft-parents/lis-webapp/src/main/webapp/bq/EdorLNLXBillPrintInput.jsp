<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//程序名称：EdorLNLXBillPrintInput.jsp
//程序功能：保全清单在线打印控制台
//创建日期：2005-08-08 16:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容
%>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<script>
    var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="./EdorLNLXBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorLNLXBillPrintInit.jsp"%>
<title>借款利息数据统计 </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorLNLXBillPrintSave.jsp" method=post name=fm id=fm  target="fraSubmit">
    <!-- 查询部分 -->

    <table class= common border=0 width=100%>
        <tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divDate);">
            </TD>
            <td class= titleImg align= center>请输入查询条件：</td>
    </tr>
    </table>
<div id = "divDate" style= "display: ''">
 <div class="maxbox1" >
 <table  class= common align=center>
       <TR>
          <TD  class= title5>统计起期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>统计止期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>   </TD>
       </TR>
 </table>

 <table  class= common align=center>
       <TR>
          <TD  class= title5>管理机构<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class="common wid" name=ManageCom  id=ManageCom
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this])" 
            onDblClick="showCodeList('Station',[this])"
             onKeyUp="showCodeListKey('Station',[this])"></TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
  
       </TR>
</table>
</div></div>
<br>
   <!-- <p>
      <INPUT VALUE=" 生成报表 " class= cssButton TYPE=button onClick="printBill();">
      
      
      
      
      <INPUT VALUE=" 下载报表 " class= cssButton TYPE=button onClick="GetPrint();">
    </p>-->
    <a href="javascript:void(0);" class="button"onClick="printBill();">生成报表</a>
    <a href="javascript:void(0);" class="button"onClick="GetPrint();">下载报表</a>
    <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype# and othersign=#1#)";
</script>
