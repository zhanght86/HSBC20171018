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
<%@page import = "com.sinosoft.lis.claim.*"%>
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
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLInqConclusion.js"></script>
    <%@include file="LLInqConclusionInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>

    <!--调查结论信息-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusion);"></TD>
            <TD class= titleImg>调查机构结论录入</TD>
        </TR>
    </table>
    <Div id= "divLLInqConclusion" style= "display: ''" class="maxbox1">
        <Table class=common>
            <TR class=common>
                <TD class=title> 立案号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ClmNo id=ClmNo></TD>
                <TD class=title> 结论序号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ConNo id=ConNo></TD>
                <TD class=title> 调查批次 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=BatNo id=BatNo></TD>                
            </TR>
            <TR class=common>
		        <TD class=title> 发起机构 </TD>  <!--##ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);"##-->
		        <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InitDept" id="InitDept" onclick="return showCodeList('stati',[this,InitDeptName],[0,1]);"ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="InitDeptName" id="InitDeptName" readonly=TRue></TD>
		        <TD class=title> 调查机构 </TD>  <!--##ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"##-->
		        <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqDept" onclick="return showCodeList('stati',[this,InqDeptName],[0,1]);" ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"  onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" id="InqDeptName" readonly=TRue></TD>
                <TD class=title> 完成标志 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=FiniFlag id=FiniFlag></TD>
            </TR>
        	<TR  class= common>
                <TD class=title> 本地标志 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=LocFlag id=LocFlag ></TD>
                <TD class=title> 汇总标志 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ColFlag id=ColFlag ></TD>
                <TD class=title> 阳性结论 </TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly value= "0" name=MasFlag id=MasFlag CodeData="0|^0|否^1|是" ondblClick="showCodeListEx('MasFlag',[this,MasFlagName],[0,1]);" onClick="showCodeListEx('MasFlag',[this,MasFlagName],[0,1]);" onkeyup="showCodeListKeyEx('MasFlag',[this,MasFlagName],[0,1]);"><Input class=codename name= "MasFlagName" id= "MasFlagName" value= "否" readonly=true> </TD>
		    </TR>
        </table>
    </Div>  
    <!--<table>
		<TR  class= common>
            <TD><Input class=cssButton name=""  value="申请调查结论" type=button onclick="queryInqApply()"></TD>
            <TD><Input class=cssButton name=""  value="隐       藏" type=button onclick="hideInqApply()"></TD>
			<TD><Input class=cssButton name=""  value="查看调查信息" type=button onclick="queryInqInfo()"></TD>
		</TR>    
    </table>-->
    <a name="" href="javascript:void(0);" class="button" onClick="queryInqApply();">申请调查结论</a>
    <a name="" href="javascript:void(0);" class="button" onClick="hideInqApply();">隐    藏</a>
    <a name="" href="javascript:void(0);" class="button" onClick="queryInqInfo();">查看调查信息</a><br><br>
	<Div  id= "divQueryInqApply" style= "display: none">
	 	   <!--调查申请信息-->
			<Table class= common>
			   <TR class= common>
			        <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
			   </TR>
			</Table>  
			<!--<Table align="center"> 
				<TR class=common>  
					<TD><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></TD>
					<TD><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></TD>
					<TD><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></TD>
					<TD><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></TD>
				</TR> 
			</Table>  -->
            <hr class="line"> 
			<Table class=common> 
				<TR class= common>
				   <TD class= title> 调查原因 </TD>
				   <!--##ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"####-->
				   <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqReason" id="InqReason" ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"><input class=codename name="InqReasonName" id="InqReasonName" readonly=TRue></TD>
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
				  <TD style="padding-left:16px" class= input> <textarea name="InqItem" cols="199" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>
				<TR class= common>
				  <TD class= title> 调查申请结论 </TD>
				</TR> 
				<TR class= common>       
				  <TD style="padding-left:16px" class= input> <textarea name="InqConclusion1" cols="199" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
				</TR>                     
			</Table>  
	</Div>   
    <Div>    
        <Table class= common>
	    	<TR class= common>
	    	    <TD class= title> 请输入机构调查结论<font size=1 color='#ff0000'><b>*</b></font> </TD>
    		</TR>
		    <TR class= common>
	    	    <TD style="padding-left:16px" class= input> <textarea name="InqConclusion" cols="199%" rows="4" wiTDh=25% class="common"></textarea></TD>
		    </TR>            
	    	<TR class= common>
	    	    <TD class= title> 备注 </TD>
    		</TR>
		    <TR class= common>
	    	    <TD style="padding-left:16px" class= input> <textarea name="Remark" cols="199%" rows="4" wiTDh=25% class="common"></textarea></TD>
		    </TR>
        </Table>
        <!--<table>
            <TR>
                <TD><Input class=cssButton name="saveAdd"  value=" 保 存 " type=button onclick="saveClick()"></TD>
                <TD><Input class=cssButton name=""  value=" 返 回 " type=button onclick="goBack()"></TD>
            </TR>
        </table>-->
        <br>
        <a href="javascript:void(0);" name="saveAdd" class="button" onClick="saveClick();">保    存</a>
        <a href="javascript:void(0);" name="" class="button" onClick="goBack();">返    回</a>        
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
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
