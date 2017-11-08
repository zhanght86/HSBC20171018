<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//程序名称：InsuAccYearBillPrintInput.jsp
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
<SCRIPT src="./InsuAccYearBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="InsuAccYearBillPrintInit.jsp"%>
<title>万能险年结报告清单 </title>
</head>
<body  onload="initForm();" >
  <form  action="./InsuAccYearBillPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 查询部分 -->
<table >
  <tr>
    <td class="common">
      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
   </td>
    <td class= titleImg>
    万能险年结报告清单
    </td> 
   </tr> 
 </table> 
    <table class= common border=0 width=100%>
        <tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
            <td class= titleImg align= center>请输入查询条件：</td>
    </tr>
    </table>
   <div id="divInvAssBuildInfo">
       <div class="maxbox1" > 
    <table  class= common align=center>
       <TR  class= common>
          <td class="title5">渠道</td>
          <td class="input5">
              <Input class="codeno" name=SaleChnl id=SaleChnl CodeData="0|^02|个人营销^03|银行代理^05|专业代理^07|联办代理^10|收展业务" 
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
              onDblClick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
              onKeyUp="showCodeListKeyEx('SaleChnl', [this,SaleChnlName],[0,1])" ><input class="codename" name=SaleChnlName readonly=true>
          </td>
          <td class="title5"></td>
          <td class="input5"></td>
 
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
       <TR>
          <TD  class= title5>统计起期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
          <TD  class= title5>统计止期<font color=red> *</font></TD>
          <TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate

({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
   </TD>
       </TR>
 </table>
</div>
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
      <INPUT VALUE=" 生成清单 " class= cssButton TYPE=button onClick="printBill();">
      
      
      
      
      <INPUT VALUE=" 下载清单 " class= cssButton TYPE=button onClick="GetPrint();">
    </p>-->
     <a href="javascript:void(0);" class="button"onClick="printBill();">生成清单</a>
      <a href="javascript:void(0);" class="button"onClick="GetPrint();">下载清单</a>
    <input type="hidden" name="fmtransact">
    <input type="hidden" id="CurDate" name="CurDate">
    <input type="hidden" id="CurTime" name="CurTime">    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
</script>
