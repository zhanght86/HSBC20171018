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
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <SCRIPT src="LWMissionReassign.js"></SCRIPT>
   <%@include file="LWMissionReassignInit.jsp"%>
</head>
<body onload="initForm();" >
<form  method=post name=fm id=fm target="fraSubmit">
    <table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchCondition);"></TD>
            <TD class= titleImg>赔案查询</TD>
        </TR>
    </table>
    <Div  id= "divSearchCondition" style= "display: ''" class="maxbox1">
    	<table  class= common>
       	    <TR  class= common>
			    <TD  class= title5> 赔案阶段 </TD>
				<TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name="ActivityIDQ" id="ActivityIDQ" ondblclick="return showCodeList('lpactivityid',[this,ActivityIDQName],[0,1]);" onclick="return showCodeList('lpactivityid',[this,ActivityIDQName],[0,1]);" onkeyup="return showCodeListKey('lpactivityid',[this,ActivityIDQName],[0,1]);"><input class=codename name="ActivityIDQName" id="ActivityIDQName" readonly=true></TD>           	    
         	    <TD  class= title5> 报案号 </TD>
          	    <TD  class= input5> <Input class="wid" class= common name=ClmNOQ id=ClmNOQ></TD>
			</TR>
			<TR class= common>
				<TD  class= title5> 立案号 </TD>
          	    <TD  class= input5> <Input class="wid" class= common name=ClmNOQ1 id=ClmNOQ1></TD>
          	    <TD  class= title5> 操作员代码 </TD>
          	    <TD  class= input5> <Input class="wid" class= common name=OperatorQ id=OperatorQ></TD>
            </TR>
        </table>    
	</Div>	
    <!--<input value="查   询" class= cssButton type=button onclick="LWMissionQueryClick();">-->
    <a href="javascript:void(0);" class="button" onClick="LWMissionQueryClick();">查    询</a>
	<table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchResult);"></TD>
            <TD class= titleImg>赔案列表</TD>
        </TR>
    </table>
    <Div  id= "divSearchResult" style= "display: ''">
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLWMissionGrid" ></span></td>
	        </tr>
	    </table>
       
	</Div>	

    <Div  id= "" style= "display: ''" class="maxbox1">
    	<table class= common>
	        <tr class= common>
				<TD  class= title5> 原操作员</TD>
				<TD  class= input5> <input class="readonly wid" readonly name=DefaultOperator id=DefaultOperator></TD>
		   <TD  class= title5> 指定操作员</TD>
          	    <TD  class= input5> <Input class="wid" class= common name=DesignateOperator id=DesignateOperator readonly ></TD>  
		    </TR>    
		     
		</Table> </Div>		
		<!--<input value="查询理赔用户" class= cssButton type=button onclick="LLClaimUserQueryClick();"> 
        <input value="指定确认" class= cssButton type=button onclick="DesignateConfirmClick();">  --><br>
        <a href="javascript:void(0);" class="button" onClick="LLClaimUserQueryClick();">查询理赔用户</a>
        <a href="javascript:void(0);" class="button" onClick="DesignateConfirmClick();">指定确认</a>
		
   	
    <!--隐藏区,保存信息用-->
    <input type=hidden id="Operator" name=Operator>
    <input type=hidden id="ComCode" name=ComCode>    
    <input type=hidden id="OComCode" name=OComCode>
    
	<input type=hidden id="MissionID"   name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">  
	  
    <input type=hidden id="fmtransact" name="fmtransact">
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
