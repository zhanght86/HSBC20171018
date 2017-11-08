<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<% 
//程序名称：DayreportFormPrintInput.jsp
//程序功能：保全报表批量打印控制台
//创建日期：2006-09-25 09:10:00
//创建人  ：wangyan
//更新记录：  更新人    更新日期      更新原因/内容
%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>";//记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>   
<SCRIPT src="./DayreportFormPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="DayreportFormPrintInit.jsp"%>
<title>投连报表批量打印 </title>   
</head>
<body  onload="initForm();" >
  <form  action="./DayreportFormPrintSave.jsp" method=post id="fm" name=fm target="fraSubmit">
    <!-- 查询部分 -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
    </table>
   <div id="divInvAssBuildInfo">
       <div class="maxbox1" > 
    <table  class= common style="center" >
       <TR  class= common>
 		        
          <TD  class= title5>报表类型<font color=red> *</font></TD>
          <TD  class= input5> 
            <!--
            <Input class="codeno" name=BillType ondblclick="initBillType(this, BillTypeName)" onkeyup="onKeyUpBillType(this, BillTypeName);"><input class=codename name=BillTypeName readonly=true>
            -->
            <input type="text" class="codeno" name="BillType" maxlength="10"  id="BillType"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onMouseDown="showCodeList('tlreport',[this,BillTypeName],[0,1])" 
            onDblClick="showCodeList('tlreport',[this,BillTypeName],[0,1])" 
            onKeyUp="showCodeListKey('tlreport',[this,BillTypeName],[0,1])"><input type="text" class="codename" name="BillTypeName" readonly>
          </TD>
          <TD  class= title5></TD>
					<TD  class= input5></TD>
       </TR>
       <TR> 
          <TD  class= title5>统计起期<font color=red> *</font></TD>
          <TD  class= input5><input class="multiDatePicker laydate-icon" dateFormat="short" name="StartDate" id="StartDate" onClick="laydate({elem:'#StartDate'});" ><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>	</TD>
          <TD  class= title5>统计止期<font color=red> *</font></TD>
          <TD  class= input5><input class="multiDatePicker laydate-icon" dateFormat="short" name="EndDate" id="EndDate" onClick="laydate({elem:'#EndDate'});" ><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>	</TD>
       </TR>
       <TR>             
    </table>
    </div>
     </div>
    
          <!--<INPUT VALUE=" 查 询 " class= cssButton TYPE=button onClick="queryNotice();"> 
          <INPUT VALUE=" 打 印 " class= cssButton TYPE=button onClick="printNotice();"> -->
          <a href="javascript:void(0);" class="button"onClick="queryNotice();">查  询</a>
          <a href="javascript:void(0);" class="button"onClick="printNotice();">打  印</a>
  <br>
   <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNoticeGrid);">
    		</td>
    		<td class= titleImg>
    			 报表任务列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divNoticeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanNoticeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <div align="center">
    	<INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">
        </div>
   </div>
  	
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="ChnlType" name="ChnlType">
  	<input type=hidden id="ChnlSel" name="ChnlSel">
  	<input type=hidden id="Flag" name="Flag">
  	<input type=hidden id="RiskFlag" name="RiskFlag">
  	<input type=hidden id="CTFlag" name="CTFlag">
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
