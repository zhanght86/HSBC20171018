<%
//**************************************************************************************************
//页面名称: LWMissionReassignInput.jsp
//页面功能：任务重新分配
//建立人: yuejw    建立日期：2005-7-14   
//修改日期：  修改原因/内容：
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//###############接收登陆公共信息####################
	String CurrentDate = PubFun.getCurrentDate();
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LWMissionReassign.js"></SCRIPT>
   <%@include file="LWMissionReassignInit.jsp"%>
</head>
<body onload="initForm();" >
<form  method=post name=fm target="fraSubmit">
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchCondition);"></TD>
            <TD class= titleImg>赔案查询</TD>
            <TD> <input value="查   询" class= cssButton type=button onclick="LWMissionQueryClick();"></TD>
        </TR>
    </table>
    <Div  id= "divSearchCondition" style= "display: ''">
    	<table  class= common>
       	    <TR  class= common>
			    <TD  class= title> 赔案阶段 </TD>
          	    <!--<TD  class= input> <Input class= common name=ActivityIDQ ></TD>-->
				<TD class= input><Input class=codeno name="ActivityIDQ" ondblclick="return showCodeList('activityid',[this,ActivityIDQName],[0,1]);" onkeyup="return showCodeListKey('activityid',[this,ActivityIDQName],[0,1]);"><input class=codename name="ActivityIDQName" readonly=true></TD>           	    
         	    <TD  class= title> 赔案号 </TD>
          	    <TD  class= input> <Input class= common name=ClmNOQ></TD>
          	    <TD  class= title> 操作员 </TD>
          	    <TD  class= input> <Input class= common name=OperatorQ></TD>
            </TR>
        </table>    
	</Div>	
	
	<table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchResult);"></TD>
            <TD class= titleImg>赔案查询列表</TD>
        </TR>
    </table>
    <Div  id= "divSearchResult" style= "display: ''">
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLWMissionGrid" ></span></td>
	        </tr>
	    </table>  
	    <input class= button value=" 首  页 " type=button onclick="getFirstPage();"> 
	    <input class= button value=" 上一页 " type=button onclick="getPreviousPage();">                   
	    <input class= button value=" 下一页 " type=button onclick="getNextPage();"> 
	    <input class= button value=" 尾  页 " type=button onclick="getLastPage();">  
	</Div>	
	<hr>
    <Div  id= "" style= "display: ''">
    	<table class= common>
	        <tr class= common>
				<TD  class= title> 原操作员</TD>
				<TD  class= input> <input class="readonly" readonly name=DefaultOperator></TD>
		        <TD  class= title></TD>      
		        <TD  class= input></TD>   		
		        <TD  class= title></TD>      
		        <TD  class= input></TD>   
		    </TR>    
		    <tr class= common>
				<TD  class= title> 指定操作员</TD>
          	    <TD  class= input> <Input class= common name=DesignateOperator readonly ></TD>
            	<TD> <input value="查询理赔用户" class= cssButton type=button onclick="LLClaimUserQueryClick();"></TD>
		    </TR>    
		</Table>	
		<hr>   
		<Table>
			<tr>
				<TD> <input value="指定确认" class= cssButton type=button onclick="DesignateConfirmClick();"></TD>
 			</tr>
 		</table>	
    </Div>		
    <!--隐藏区,保存信息用-->
    <input type=hidden id="Operate" name=Operate>
    <input type=hidden id="ComCode" name=ComCode>    
    
	<input type=hidden id="MissionID"   name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">  
	  
    <input type=hidden id="fmtransact" name="fmtransact">
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>