
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：AbilityCount.jsp
//程序功能：性能查询
//创建日期：2008-9-17
//创建人  ：
//更新记录：  
%>

<%
	String Business = "";
	try
	{
		Business = request.getParameter("Business");
	}
	catch( Exception e )
	{
		Business = "";
	}
	
		String ManageCom = "";
	try
	{
		ManageCom = request.getParameter("ManageCom");
	}
	catch( Exception e )
	{
		ManageCom = "";
	}
	
	String RuleStartDate = "";
	try
	{
		RuleStartDate = request.getParameter("RuleStartDate");
	}
	catch( Exception e )
	{
		Business = "";
	}
	
		String RuleEndDate = "";
	try
	{
		RuleEndDate = request.getParameter("RuleEndDate");
	}
	catch( Exception e )
	{
		RuleEndDate = "";
	}
%>
<head >
<script> 
	var Business = "<%=Business%>";
	var ManageCom = "<%=ManageCom%>";
	var RuleStartDate = "<%=RuleStartDate%>";
	var RuleEndDate = "<%=RuleEndDate%>";
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="CountQuery.js"></SCRIPT>
  <%@include file="CountQueryInit.jsp"%>

<%
//==================================================================BEGIN
	String flag= request.getParameter("flag");

//===================================================================END
%>
</head>
<body  onload="initElementtype();InitAbilityDetail();initAbilityDetailGrid();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>性能查询</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox1">
<table class=common align=center>
	<TR class=common>
		 <td class=title5>机构</td>
          <td class=input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=ManageCom id=ManageCom verify="管理机构|code:comcode&NOTNULL"  
                   ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);"   onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" 
                   onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input name=ManageComName id=ManageComName class='codename' readonly=true >
           </td>  
			
       <TD class=title5>业务模块</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onchange="return Clean();" readonly=true>
	    </TD> 
	</TR>
	<TR class=common>
	<TD class=title5>
                          起始日期：
        </TD>	  
      
         <TD  class= input5>
          <!--<Input name=RuleStartDate class='coolDatePicker'  verify="开始日期|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="开始日期|Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
		<TD class=title5>
                         结束日期：
        </TD>	  
         <TD  class= input5>
          <!--<Input name=RuleEndDate class='coolDatePicker'  verify="结束日期|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="开始日期|Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
        
	</TR>
<TR>
</Table>
</Div>
  <p>
  <Div id= DivSelect style="display: ''">
     <INPUT VALUE="查  询" TYPE=button class="cssButton" onclick="AbilityDetail()">
    
  </Div>
<!--<a href="javascript:void(0);" class="button" onClick="AbilityDetail();">查    询</a>-->
  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	性能列表
      </td></tr>
    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	        <span id="spanAbilityDetailGrid" ></span>
		</td>
		<TD class=input style="display: none">						
		</TD>
	</tr>

     </table><center>
        <INPUT VALUE="首  页" TYPE=button  class="cssButton90" onclick="turnPage.firstPage()">
        <INPUT VALUE="上一页" TYPE=button  class="cssButton91" onclick="turnPage.previousPage()">
        <INPUT VALUE="下一页" TYPE=button  class="cssButton92" onclick="turnPage.nextPage()">
        <INPUT VALUE="尾  页" TYPE=button  class="cssButton93" onclick="turnPage.lastPage()"></center></Div>
<br>
<br>	    
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
