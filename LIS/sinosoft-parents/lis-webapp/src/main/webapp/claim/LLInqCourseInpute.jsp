<html>
<%
//Name：LLInqCourseInpute.jsp
//Function：调查过程界面(调查过程录入、调查费用录入、及调查完成确认)
//Date：2005-6-22
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>

 <head >
	<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		
		  String tCurrentDate = PubFun.getCurrentDate();  //提取服务器当前日期
	      String tClmNo=request.getParameter("ClmNo"); //赔案号
	      String tInqNo=request.getParameter("InqNo"); //调查序号      
	      String tActivityid=request.getParameter("Activityid"); //活动ID
		  String tMissionID =request.getParameter("MissionID");  //工作流任务ID
		  String tSubMissionID =request.getParameter("SubMissionID");  //工作流子任务ID	
 		  
	%> 	
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
	<SCRIPT src="LLInqCourse.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<%@include file="LLInqCourseInit.jsp"%>
 </head>

 <body  onload="initForm();" >
   <form method=post name=fm id=fm target="fraSubmit"> 
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyInfo);"></TD>
            <TD class= titleImg>调查申请详细信息</TD>
       </TR>
    </Table>
    <Div id= "divLLInqApplyInfo" style= "display: ''" class="maxbox">
        <Table class=common>
            <TR class=common>
                <TD class=title> 立案号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ClmNo id=ClmNo></TD>
                <TD class=title> 调查序号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=InqNo id=InqNo></TD>
                <TD class=title> 调查批次 </TD>
                <TD class= input><Input class="readonly wid" readonly  name=BatNo id=BatNo></TD>                
            </TR>
            <TR class=common>
                <TD class=title> 出险人客户号 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="CustomerNo" id="CustomerNo"></TD>
                <TD class=title> 出险人姓名 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="CustomerName" id="CustomerName"></TD>
                <TD class=title> VIP客户标志 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="VIPFlag" id="VIPFlag"></TD>                
            </TR>
            <TR class=common>
                <TD class=title> 申请人 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="ApplyPer" id="ApplyPer"></TD>
                <TD class=title> 申请日期 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="ApplyDate" id="ApplyDate"></TD>
                <TD class=title> 提起阶段 </TD>
                <!--##ondblclick="return showCodeList('llinitphase',[this,InitPhaseName],[0,1]);"##-->
	            <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InitPhase" id="InitPhase" onclick="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);" ondblclick="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);" onkeyup="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);"><input class=codename name="InitPhaseName" id="InitPhaseName" readonly=true></TD>          
            </TR>
            <TR class=common>
				<TD  class= title>调查机构</TD>
				<!--<TD  class= input><input class="readonly" readonly name="InqDept"></TD>  ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"-->  
				<TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InqDept" id="InqDept"  onclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" id="InqDeptName" readonly=true></TD> 	              
      			<TD class=title> 发起机构 </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InitDept"></TD>  ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" -->
              <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InitDept" id="InitDept" onclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ><input class=codename name="InitDeptName" id="InitDeptName" readonly=true></TD> 	   
            	<TD class=title> 本地标志 </TD>
            	<TD class= input><Input class="readonly wid" readonly name="LocFlag" id="LocFlag"></TD> 
            	<!--<TD class= input><input class=codeno readonly name="LocFlag" CodeData="0|3^0|本地^1|异地"  onkeyup="return showCodeListKeyEx('LocFlag', [this,LocFlagName],[0,1]);"><input class=codename name="LocFlagName" readonly=true></TD>--> 	   	 
            </TR>    
            <TR class=common>
			    <TD class=title> 调查原因 </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InqRCode"></TD>  ondblclick="return showCodeList('llinqreason',[this,InqRCodeName],[0,1]);"-->
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InqRCode" id="InqRCode"  onclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" ondblclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);"><input class=codename name="InqRCodeName" id="InqRCodeName" readonly=true></TD>               
                <TD class=title> 调查项目 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqItem" id="InqItem"></TD>   
                <TD class=title> </TD>
                <TD class= input></TD>           
            </TR>         
		</Table>   
		<Table class=common>
			<TR  class= common> <TD class= title> 调查描述</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="199" rows="4" name="InqDesc" readonly class="common" ></textarea></TD>
			</TR>   
		</Table> </DIV>
		<!--<Table class= common> 
		    <TR><Input class=cssButton  name=""  value="查询其他调查信息" type=button onclick="queryOtherInqClick();"></TR>
	    </Table> -->
			<a name="" href="javascript:void(0);" class="button" onClick="queryOtherInqClick();">查询其他调查信息</a>           
 
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqCourseInfo);"></TD>
            <TD class= titleImg>调查过程录入</TD>
       </TR>
    </Table>
    <Div  id= "divInqCourseInfo" align=center style= "display: ''" class="maxbox1">
		<Table class = common>
			<TR  class= common>
				<TD  class= title>调查方式</TD>
				<!--<TD  class= input><input class= common name="InqMode"><font size=1 color='#ff0000'><b>*</b></font></TD>-->
	            <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name="InqMode" id="InqMode" ondblclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onkeyup="return showCodeListKey('llinqmode',[this,InqModeName],[0,1]);"><input class=codename name="InqModeName" id="InqModeName" readonly=true></TD>				
				<TD  class= title>调查地点</TD>
				<TD  class= input><input class="wid" class= common name="InqSite" id="InqSite"></TD>
				<TD  class= title>调查日期</TD>
            <!--     <TD  class= input><input class="wid" class= 'multiDatePicker' dateFormat="Short" name="InqDate" id="InqDate"></TD>   --> 
			<td>	<Input class="coolDatePicker" onClick="laydate({elem: '#InqDate'});" verify="有效开始日期|DATE" dateFormat="short" name=InqDate id="InqDate"><span class="icon"><a onClick="laydate({elem: '#InqDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </td> <!-- modify wyc -->
				
			</TR>
			<TR class = common> 
				<TD  class= title>第一调查人</TD>
				<TD  class= input><input class="wid" class= common name="InqPer1" id="InqPer1"></TD>
				<TD  class= title>第二调查人</TD>
				<TD  class= input><input class="wid" class= common name="InqPer2" id="InqPer2" ></TD>
				<TD  class= title>被调查人</TD>
				<TD  class= input><input class="wid" class= common name="InqByPer" id="InqByPer" ></TD>
			</TR>
		</Table><br>	
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLInqCertificateGrid" ></span> </TD>
			</TR>
		</Table>
		 <Div id= "DivInqCertificate" style= "display: 'none'">
	        <Table class= common>
	            <TR class= common>
	                <TD  class= title>单证名称</TD>                      
	                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AffixCode id=AffixCode ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1],'','','','1','500');"><input class=codename name=AffixName id=AffixName readonly=true></TD>
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                     
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                                                                     
	            </TR>   
  
	            <TR class= common>
	                <TD  class= title><input style="vertical-align:middle" type="checkbox" value="" name="checkbox" onclick="checkboxClick()"><span style="vertical-align:middle">选择其他</span></input></TD>                       
	                <TD  class= input> <Input class="wid" class=common name="OtherName" id="OtherName" disabled=true></TD>
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                     
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                                                                         
            	</TR>      

	        </Table>
	       <Table class= common style="display:none">
	    	    <TR class= common>    
	                <input class=cssButton type=button value=" 确 认 " onclick="addInqCertificate();">
	                <input class=cssButton type=button value=" 取 消 " onclick="cancelInqCerficate();">
	            </TR>
	        </Table>
 
	   </Div>  
       <!--<a href="javascript:void(0);" class="button" onClick="addInqCertificate();">确    认</a>
       <a href="javascript:void(0);" class="button" onClick="cancelInqCerficate();">取    消</a>  -->
		<Table class=common>
			<TR  class= common> <TD  class= title> 调查过程录入 </TD></TR>
			<TR  class= common><TD style="padding-left:16px" class= common> <textarea cols="199" rows="4" name="InqCourse"  class="common" ></textarea> </TD></TR>
			<TR  class= common> <TD  class= title> 调查过程备注 </TD></TR>
			<TR  class= common><TD style="padding-left:16px"  class= common><textarea name="InqCourseRemark" cols="199" rows="4" class="common" ></textarea></TD></TR>
		</Table></Div>
	   <!-- <Table class= common> 
		    <TR>
		        <TD>
		        	<Input class=cssButton name=""  value="调查单证" type=button onclick="showInqCerficate();">
		        	<Input class=cssButton name=""  value=" 保  存 " type=button onclick="AddInqCourseClick();">
	                <Input class=cssButton name=""  value="查询过程" type=button onclick="InqCourseQueryClick();">
	            </TD>
		    </TR>
	   </Table> -->	
       <br>	    
    	<a href="javascript:void(0);" name="" class="button" onClick="showInqCerficate();">调查单证</a>
        <a href="javascript:void(0);" name="" class="button" onClick="AddInqCourseClick();">保    存</a>
        <a href="javascript:void(0);" name="" class="button" onClick="InqCourseQueryClick();">查询过程</a>
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqFeeInfo);"></TD>
            <TD class= titleImg>查勘费用录入</TD>
       </TR>
    </Table>
    <Div  id= "divInqFeeInfo" align=center style= "display: ''" class="maxbox1">
        <Table class= common> 
        	<TR  class= common>
                <TD class=title> 费用类型 </TD>
		        <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name="FeeType"  id="FeeType" ondblclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);"onclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llinqfeetype',[this,FeeTypeName],[0,1]);"><input class=codename name="FeeTypeName" id="FeeTypeName" readonly=true></TD>
                <TD  class= title>费用金额</TD>
                <TD  class= input><input class="wid" class= common name="FeeSum" id="FeeSum"></TD>
                <TD  class= title>发生时间</TD>
                <TD  class= input><!--<input class= 'multiDatePicker' dateFormat="Short" name="FeeDate">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#FeeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=FeeDate id="FeeDate"><span class="icon"><a onClick="laydate({elem: '#FeeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
           </TR>
            <TR  class= common>
                <TD  class= title>领款人</TD>
                <TD  class= input><input class="wid" class= common name="Payee" id="Payee"></TD>
                <TD  class= title>领款方式</TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name="PayeeType" id="PayeeType" ondblclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,PayeeTypeName],[0,1]);"><input class=codename name="PayeeTypeName" id="PayeeTypeName" readonly=true></TD>
           </TR>
        </Table>
        <Table class= common>         
	    	<TR class= common><TD class= title> 备注 </TD></TR>
		    <TR class= common><TD style="padding-left:16px" class= input> <textarea name="Remark" id="Remark" cols="199" rows="4" class="common"></textarea></TD></TR>
        </Table>	</Div>
	<!--	    
	    <Table class= common> 
		    <TR>
		        <TD>
		        	<Input class=cssButton name=""  value=" 保  存 " type=button onclick="AddInqFeeClick();">
	                <Input class=cssButton name=""  value="查询费用" type=button onclick="QueryInqFeeClick();">
	            </TD>
		    </TR>
	   </Table> -->      
<a href="javascript:void(0);" class="button" name="" onClick="AddInqFeeClick();">保    存</a>
<a href="javascript:void(0);" class="button" name="" onClick="QueryInqFeeClick();">查询费用</a>    
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqConclusionInfo);"></TD>
            <TD class= titleImg>调查结论录入</TD>
        </TR>   
        
    </Table>
    <Div  id= "divInqConclusionInfo" align=center style= "display: ''" class="maxbox1">
		<Table class=common>	
			<TR  class= common>
				<TD class="input" ><input type="checkbox" value="1" name="MoreInq" onclick="MoreInqClick();">调查完成标志</input></TD>
            </TR>		
			<TR  class= common> <TD  class=title> 调查结论</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="199" rows="4" name="InqConclusion" id="InqConclusion" disabled class="common" ></textarea></TD>
			</TR>
		</Table></Div>
		<!--<Table class= common> 
		    <TR>
		        <TD>
		        	
	                <Input class=cssButton name=""  value=" 返 回 " type=button onclick="turnback();">
	            </TD>
		    </TR>
	   </Table> -->		<br> 
			<Input class=cssButton  disabled name="InqConfirm"  value=" 确 认 " type=button onclick="InqConfirmClick();">
            <a href="javascript:void(0);" name="" class="button" onClick="turnback();">返    回</a>
    	        
		<!-- 隐藏区域-->
	    <input type=hidden id="InqState" name="InqState"><!--调查完成标示  “02--完成”-->	
	    <!--<TD class=title> 提起阶段 </TD>-->
	 	<!--<Input type=hidden class="readonly" readonly  name="InitPhase">-->
	 	
	 	<input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
	    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->	
	    
	    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag"><!---->	 
	      
	    <input type=hidden id="MissionID" name="MissionID"><!--任务ID-->	
	    <input type=hidden id="SubMissionID" name="SubMissionID"><!--子任务ID-->	  
    	<input type=hidden id="Activityid" name="Activityid"><!--子任务ID-->	  
    	
        <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
        
        <Input type=hidden id="ConNo" name="ConNo"><!--调查结论序号-->

	</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
