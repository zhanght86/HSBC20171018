<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
    loggerDebug("OtoFReverse","Branch:"+Branch);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  
  <SCRIPT src="OtoFReverse.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="OtoFReverseInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./OtoFReverseSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table class=common >
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,qwert);"></td>
        <td class= titleImg>请输入查询条件：</td></tr>
		</table>
        <Div  id= "qwert" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>凭证类型</TD>
          <TD  class= input5>
			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=VoucherType id=VoucherType  CodeData="0|^1|暂收保费^2|预收保费^3|保费收入^4|赔款支出^5|保全应付^6|领取类给付^7|其他应付^8|实付^9|保全收费^10|业务员佣金^11|续收员佣金^12|续收督导津贴|M" ondblClick="showCodeListEx('VoucherType',[this,VoucherTypeName],[0,1]);" onclick="showCodeListEx('VoucherType',[this,VoucherTypeName],[0,1]);" onkeyup="showCodeListKeyEx('VoucherType',[this,VoucherTypeName],[0,1]);"><Input class=codename name=VoucherTypeName id=VoucherTypeName readonly>
		  </TD>       
          <TD  class= title5>核算机构</TD>
          <TD  class= input5>
			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=ManageCom id=ManageCom ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql1,'1',1,250);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql1,'1',1,250);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,codeSql1,'1',1,250);"><Input class=codename name=ManageComName id=ManageComName readonly>
		  </TD></TR>
          <TR  class= common>
          <TD  class= title5>业务发生日期</TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#TransDate'});" verify="有效开始日期|DATE" dateFormat="short" name=TransDate id="TransDate"><span class="icon"><a onClick="laydate({elem: '#TransDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>凭证号</TD>
	<TD  class= input5><Input class="common wid"  name=VoucherID id=VoucherID></TD>
        </TR>
       <TR  class= common>
	
	<TD  class= title5>保单号</TD>
	<TD  class= input5><Input class="common wid"  name=PolNo id=PolNo></TD>
	<TD  class= title5>收据号</TD>
	<TD  class= input5><Input class="common wid"  name=BussNo id=BussNo></TD>
       </TR>        
    </table>
		</Div>
		<!--<INPUT VALUE="财务凭证查询" class= cssButton TYPE=button onclick="easyQuery();">-->
        <a href="javascript:void(0);" class="button" onClick="easyQuery();">财务凭证查询</a>					
    <table >
    	<tr>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);"></td>
      	<td class= titleImg align= center>财务凭证信息</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
	  					<span id="spanCodeGrid" ></span> 
				  	</td>
	  			</tr>
	    	</table>
		<center>
      <INPUT CLASS=cssButton90 VALUE="首    页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾    页" TYPE=button onclick="turnPage.lastPage();"></center>
      </Div>
        <table>
    	<tr>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
      	<td class= titleImg>请录入冲销日期</td>
    	</tr>
    	</table>
        <Div  id= "peer" style= "display: ''" class="maxbox1">
    	<table class= common>
    	<tr class= common>
    	<td class= title5>冲销日期</td>
    <TD class=input5> <Input class="coolDatePicker wid" dateFormat="short" name=AccountingDate id=AccountingDate></TD>  
    	<td class= title5></td>
        <TD class=input5></TD>
    	</tr></table>   
        <table class= common> 	    	    	
				<TR class= common>
					<TD class= title5>凭证冲销原因</TD>
				</TR>
				<TR  class= common>
					<TD style="padding-left:16px" colspan="4"><textarea name="Reason" cols="168%" rows="4" witdh=25% class="common"></textarea></TD>
				</TR>
	 		</table>
  	</div>
<!--    <INPUT VALUE="财务凭证冲销" class= cssButton TYPE=button onclick="OtoFReverse();">
    	 <INPUT VALUE="按查询条件冲销" class= cssButton TYPE=button onclick="OtoFReverseAll();">--><br>
         <a href="javascript:void(0);" class="button" onClick="OtoFReverse();">财务凭证冲销</a>
         <a href="javascript:void(0);" class="button" onClick="OtoFReverseAll();">按查询条件冲销</a>
   <INPUT type= "hidden" name= "fmAction" value= ""> 
   <INPUT type= "hidden" name= "ComCode" value= ""> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql1 = "1  and comcode like #"+<%=Branch%>+"%# and char_length(trim(comcode))<=6"  ;
	var codeSql = "1  and code like #"+<%=Branch%>+"%# and char_length(trim(code))<=6"  ;
	fm.all('ComCode').value="<%=Branch%>";
</script>
