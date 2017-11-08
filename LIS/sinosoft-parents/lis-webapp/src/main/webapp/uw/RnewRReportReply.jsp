<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：RReportReply.jsp
//程序功能：生存调查报告回复
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-23   更新原因/内容：根据新核保要求进行修改
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head >
<title>问题件查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="RnewRReportReply.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewRReportReplyInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="">
    <!-- 保单查询条件 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<td class= titleImg>请输入查询条件：</td>
	</tr>
    </table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
    	<tr class= common>
      	<TD  class= title5>  保单号 </TD>
        <TD  class= input5> <Input class="wid" class= common name=QPrtNo id=QPrtNo > </TD>
        <TD  class= title5>  被保人 </TD>
        <TD  class= input5> <Input class="wid" class= common name=QInsuredName id=QInsuredName > </TD></tr>
        <tr class= common>
        <TD  class= title5> 管理机构 </TD>
        <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code wid" name=QManagecom id=QManagecom ondblclick="return showCodeList('station',[this]);" onclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"> </TD>
        <TD  class= title5></TD>
        <TD  class= input5></TD>
        </tr>
    </table></Div>
    <!--<input type= "button" class= cssButton name= "Reply" value="查  询" onClick= "easyQueryClick()">-->
    <a href="javascript:void(0);" name= "Reply" class="button" onClick="easyQueryClick();">查    询</a>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 生存调查报告内容：
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
        <center>
      <INPUT VALUE="首  页" class = cssButton90  TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91  TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton92  TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class = cssButton93  TYPE=button onclick="turnPage.lastPage();">	</center>
    </div>
    <br> </br>
    
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
