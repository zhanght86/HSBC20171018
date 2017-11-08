<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<% 
//程序名称：BqCheckPrintInput.jsp
//程序功能：保全收据在线打印控制台
//创建日期：2005-08-02 16:20:22
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
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
<SCRIPT src="./BqCheckPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="BqCheckPrintInit.jsp"%>
<title>保全收据打印 </title>   
</head>
<body  onload="initForm();initElementtype();" >
  <form  action="./BqCheckPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 查询部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
                <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" >
            </td>
			<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
	</table>
	<div class=maxbox>
    <table  class= common align=center>
       <TR  class= common>
          <TD  class= title5>  保全受理号   </TD>
          <TD  class= input5>  <Input class="common wid" name=EdorAcceptNo id=EdorAcceptNo> </TD> 
          <TD  class= title5>  保单号   </TD>
          <TD  class= input5>  <Input class="common wid" name=ContNo id=ContNo> </TD> 
       </tr>
       <tr class= common>
          <TD  class= title5>   管理机构  </TD>
          <!--<TD  class= input><Input class= code name=ManageCom ondblclick="return showCodeList('ComCode',[this]);" onkeyup="return showCodeListKey('ComCode',[this]);"></TD>-->
          <TD  class= input5><Input class= "code" style="background:url(../common/images/select--bg_03.png) no-repeat right 

center" name=ManageCom id=ManageCom ondblclick="return showCodeListEx('nothis', [this],[0,1],null,null,null,1,'260');" onkeyup="return showCodeListKeyEx('nothis', [this],[0,1],null,null,null,1,'260');"></TD>
 		  <td class="title5">业务员编码</td>
		  <td class="input5" COLSPAN="1">
		      <input NAME="AgentCode" style="background:url(../common/images/select--bg_03.png) no-repeat right 

center" id=AgentCode VALUE="" MAXLENGTH="10" CLASS="code" verifyorder="1"  ondblclick="return queryAgent();" ></td>	  
       </TR>
       <tr class= common>
          <TD class=title5>收据类型</TD>
          <td class=input5>
			<Input class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right 

center" name=InvoiceType id=InvoiceType ondblclick="showCodeList('bqinvoicetotal',[this,InvoiceName],[0,1])" onkeyup="showCodeListKey('bqinvoicetotal',[this,InvoiceName],[0,1])">
			<input class=codename name=InvoiceName id=InvoiceName readonly>
		  </td>
          <TD class=title5> </TD>
          <TD class=title5> </TD>	       
       </TR>
   </table>
   </div>
		  <a href="javascript:void(0);" class="button" onClick="easyQueryClickSelf();">查    询</a>
  <br><br>
   <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCheckGrid);">
    		</td>
    		<td class= titleImg>
    			 打印收据任务列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCheckGrid" style= "display: ''; text-align:center;">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanNoticeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table  style="text-align:center">
	     <INPUT VALUE="首页" class=cssButton90 class=cssButton TYPE=button onclick="getFirstPage();"> 
         <INPUT VALUE="上一页" class=cssButton91 class=cssButton TYPE=button onclick="getPreviousPage();"> 					
         <INPUT VALUE="下一页" class=cssButton92 class=cssButton TYPE=button onclick="getNextPage();"> 
         <INPUT VALUE="尾页" class=cssButton93 class=cssButton TYPE=button onclick="getLastPage();"> 					
       </table>
   </div>
  	<table class= common>
  		<tr class= common>
  			<td class= title5>收据号</td>
  			<td class= input5>
				<Input class= common name=CheckNo id=CheckNo elementtype=nacessary verify="收据号|notnull">
				<INPUT VALUE="打印收据" class= cssButton TYPE=button onclick="printCheck();">
			</td>
  			<!--
  			<td class= title>收据类型</td>
  			<td class= input><Input class= common name=CertifyCode  elementtype=nacessary verify="收据类型|notnull" readonly = true type=hidden></td>  
  			--->
  			<td class= title5></td>
  			<td class= input5></td>  			
  			<td class= title5></td>
  			<td class= input5></td>
			
  		</tr>
  	</table> 	
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<Input type=hidden id="CertifyCode" name="CertifyCode">
  	<input type=hidden id="SelEdorAcceptNo" name="SelEdorAcceptNo">
  	<input type=hidden id="ChequeType" name="ChequeType">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html> 
