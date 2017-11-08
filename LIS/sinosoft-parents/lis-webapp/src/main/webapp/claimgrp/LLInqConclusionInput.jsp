<%
//程序名称：LLInqConclusionInput.jsp
//程序功能：调查结论信息
//创建日期：2005-05-10
//创建人  ：zhoulei
//更新记录：yuejw
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
		<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //赔案号
	      String tConNo=request.getParameter("ConNo"); //结论序号      
	      String tActivityid=request.getParameter("Activityid"); //活动ID
		  String tMissionID =request.getParameter("MissionID");  //工作流任务ID
		  String tSubMissionID =request.getParameter("SubMissionID");  //工作流子任务ID	
 		  
	%> 	
    <title>调查结论信息</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLInqConclusion.js"></script>
    <%@include file="LLInqConclusionInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>

    <!--调查结论信息-->
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusion);"></TD>
            <TD class= titleImg>调查结论信息</TD>
        </TR>
    </table>
    <Div id= "divLLInqConclusion" style= "display: ''">
        <Table class=common>
            <TR class=common>
                <TD class=title> 赔案号 </TD>
                <TD class= input><Input class="readonly" readonly  name=ClmNo></TD>
                <TD class=title> 结论序号 </TD>
                <TD class= input><Input class="readonly" readonly  name=ConNo></TD>
                <TD class=title> 调查批次 </TD>
                <TD class= input><Input class="readonly" readonly  name=BatNo></TD>                
            </TR>
            <TR class=common>
		        <TD class=title> 发起机构 </TD>  <!--##ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);"##-->
		        <TD class= input><Input class=codeno readonly  name="InitDept"  onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="InitDeptName" readonly=TRue></TD>
		        <TD class=title> 调查机构 </TD>  <!--##ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"##-->
		        <TD class= input><Input class=codeno readonly name="InqDept"  onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" readonly=TRue></TD>
                <TD class=title> 完成标志 </TD>
                <TD class= input><Input class="readonly" readonly  name=FiniFlag></TD>
            </TR>
        	<TR  class= common>
                <TD class=title> 本地标志 </TD>
                <TD class= input><Input class="readonly" readonly  name=LocFlag ></TD>
                <TD class=title> 汇总标志 </TD>
                <TD class= input><Input class="readonly" readonly  name=ColFlag ></TD>
                <TD class=title> 阳性结论 </TD>
                <TD class= input><Input class="readonly" readonly  name=MasFlag ></TD>
		    </TR>
        </table>
    </Div>  
    <table>
		<TR  class= common>
            <TD><Input class=cssButton name=""  value="调查申请结论" type=button onclick="queryInqApply()"></TD>
            <TD><Input class=cssButton name=""  value="隐       藏" type=button onclick="hideInqApply()"></TD>
			      <TD><Input class=cssButton name=""  value="查看调查信息" type=button onclick="queryInqInfo()"></TD>
			      <TD><Input class=cssButton name="LoadAffix" value="浏 览  附 件" type=button onclick="LoadAffixClick();"></TD>
		</TR>    
    </table>
    <hr> 
	<Div  id= "divQueryInqApply" style= "display: 'none'">
	 	   <!--调查申请信息-->
			<Table class= common>
			   <TR class= common>
			        <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
			   </TR>
			</Table>  
			<Table class=common> 
				<TR class= common>
				   <TD class= title> 调查原因 </TD>
				   <!--##ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"####-->
				   <TD class= input><Input class=codeno readonly name="InqReason" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"><input class=codename name="InqReasonName" readonly=TRue></TD>
				   <TD class= title></TD>	
				   <TD class= input></TD>		
			       <TD class= title></TD>	
				   <TD class= input></TD>		
				</TR> 	
			</Table>  
			<Table class=common> 		
				<TR class= common>
				   <TD class= title> 调查申请项目 </TD>
				</TR> 
				<TR class= common>       
				  <TD class= input> <textarea name="InqItem" cols="100" rows="1" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>
				<TR class= common>
				  <TD class= title> 调查申请结论 </TD>
				</TR> 
				<TR class= common>       
				  <TD class= input> <textarea name="InqConclusion1" cols="100" rows="2" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>                     
			</Table>
   		<hr>  
	</Div>   
    <Div>    
        <Table class= common>
	    	<TR class= common>
	    	    <TD class= title> 请输入机构调查结论<font size=1 color='#ff0000'><b>*</b></font> </TD>
    		</TR>
		    <TR class= common>
	    	    <TD class= input> <textarea name="InqConclusion" cols="100%" rows="3" wiTDh=25% class="common"></textarea></TD>
		    </TR>            
	    	<TR class= common>
	    	    <TD class= title> 备注 </TD>
    		</TR>
		    <TR class= common>
	    	    <TD class= input> <textarea name="Remark" cols="100%" rows="3" wiTDh=25% class="common"></textarea></TD>
		    </TR>
        </Table>
        <table>
            <TR>
                <TD><Input class=cssButton name="saveAdd"  value=" 保 存 " type=button onclick="saveClick()"></TD>
                <TD><Input class=cssButton name=""  value=" 返 回 " type=button onclick="goBack()"></TD>
            </TR>
        </table>        
    </Div>

    <%
    //******************
    //保存数据的隐藏表单
    //******************
    %>
    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
	<input type=hidden id="MissionID" name="MissionID"><!--任务ID-->	
	<input type=hidden id="SubMissionID" name="SubMissionID"><!--子任务ID-->	  
    <input type=hidden id="Activityid" name="Activityid"><!--子任务ID-->	  
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag"><!---->	 

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
