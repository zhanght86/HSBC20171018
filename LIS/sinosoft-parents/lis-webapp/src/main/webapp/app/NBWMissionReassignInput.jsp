<%
//程序名称：NBWMissionReassingInput.jsp
//程序功能：新契约任务分配
//创建日期：2006-2-20 14:26
//创建人  ：chenrong
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="NBWMissionReassign.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="NBWMissionReassignInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit" action="UWMissionAssignSave.jsp"> 
  
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>请输入查询条件：</td>
		</tr>
	</table>
	<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
        <table  class= common>
          	<TR  class= common>
                <TD  class= title> 新契约状态 </TD>
				<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name="ActivityID1" id="ActivityID1" CodeData="0|5  ^0000001090| 异常件处理 ^0000001091|复核抽检 ^0000001001|新单复核 ^0000001094|特殊投保单录入 ^0000001020|管理中心 ^0000001099|新契约扫描录入 ^0000001002|新契约问题件修改" ondblclick="return showCodeListEx('ActivityID',[this,ActivityIDName],[0,1]);" onclick="return showCodeListEx('ActivityID',[this,ActivityIDName],[0,1]);" onkeyup="return showCodeListKeyEx('ActivityID',[this,ActivityIDName],[0,1]);"><input class=codename name="ActivityIDName" id="ActivityIDName" readonly=true></TD>      	    
                <TD  class= title>印刷号</TD>
                <TD  class= input><Input class="wid" class= common name=PrtNo id=PrtNo value=""></TD>
                <TD  class= title> 操作员工号 </TD>
          	    <TD  class= input><Input class="wid" class= common name=OperatorQ id=OperatorQ></TD></TR>
                <TR  class= common>
                
                <TD  class= title> 扫描开始日期 </TD>
          	    <TD  class= input>
                <Input class="coolDatePicker" onClick="laydate({elem: '#ScanStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ScanStartDate id="ScanStartDate"><span class="icon"><a onClick="laydate({elem: '#ScanStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
          	 <TD  class= title> 扫描结束日期 </TD>
          	    <TD  class= input>
                <Input class="coolDatePicker" onClick="laydate({elem: '#ScanEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ScanEndDate id="ScanEndDate"><span class="icon"><a onClick="laydate({elem: '#ScanEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD></TR>
          	
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQuery();">
    <!--<a href="javascript:void(0);" class="button" onClick="easyQuery();">查    询</a> -->    
      
    <div id=DivLCContInfo STYLE="display:''"> 
        <table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllPolGrid);"></td>
        		<td class=titleImg>共享工作池</td>
        	</tr>  	
        </table>
    </div>
	<div  id= "divAllPolGrid" style= "display: ''" align = center>
    	<table  class= common >
    		<tr  class=common>
    			<td text-align: left colSpan=1 ><span id="spanAllPolGrid" ></span></td>
    		</tr>
    	</table>
	 
	<div  id= "divPage" align=center style= "display: 'none' ">
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">	    
	</div></div> 
    <div class="maxbox1">
    <table class= common>
        <tr class= common style= "display: none ">
            <td  class= title>&nbsp;原操作员&nbsp;</td>
            <td  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=DefaultOperator id=DefaultOperator><input class="codename" readonly name=DefaultOperatorName id=DefaultOperatorName></td>
            </td> </tr> <tr class= common>
            <td class= title>指定操作员</td>
            <td  class= Input> <Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=DesignateOperator id=DesignateOperator  onclick="DuserQueryClick();" onchange="DuserNameQueryClick();"><input class=codename name=DesignateOperatorName id=DesignateOperatorName readonly=true></td>
            <td class= title></td>
            <td  class= Input></td> <td class= title></td>
            <td  class= Input></td>
        </tr>   

    </table>

<!--    <input value="查询操作员" class= cssButton type=button onclick="userQueryClick();">
    <INPUT class=cssButton id="riskbutton" VALUE=" 确  认 " TYPE=button onClick="reassignNBW();">--><br><br>
    <a href="javascript:void(0);" class="button" onClick="userQueryClick();">查询操作员</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="reassignNBW();">确    认</a>
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    
    <Input type=hidden name="MissionID" >
    <Input type=hidden name="SubMissionID" >
    <Input type=hidden name="ActivityID" >
    <input type=hidden id="fmtransact" name="fmtransact">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
