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
      
      loggerDebug("LLInqCourseInpute",);
  %>   
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLInqCourse.js"></SCRIPT>
   <%@include file="LLInqCourseInit.jsp"%>
 </head>

 <body  onload="initForm();" >
   <form method=post name=fm target="fraSubmit"> 
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyInfo);"></TD>
            <TD class= titleImg>调查申请详细信息</TD>
       </TR>
    </table>
    <Div id= "divLLInqApplyInfo" style= "display: ''">
        <TABLE class=common>
            <tr class=common>
                <td class=title> 立案号 </td>
                <td class= input><Input class="readonly" readonly  name=ClmNo></td>
                <td class=title> 调查序号 </td>
                <td class= input><Input class="readonly" readonly  name=InqNo></td>
                <td class=title> 调查批次 </td>
                <td class= input><Input class="readonly" readonly  name=BatNo></td>                
            </tr>
            <tr class=common>
                <td class=title> 出险人客户号 </td>
                <td class= input><Input class="readonly" readonly  name="CustomerNo"></td>
                <td class=title> 出险人姓名 </td>
                <td class= input><Input class="readonly" readonly  name="CustomerName"></td>
                <td class=title> VIP客户标志 </td>
                <td class= input><Input class="readonly" readonly  name="VIPFlag"></td>                
            </tr>
            <tr class=common>
                <td class=title> 申请人 </td>
                <td class= input><Input class="readonly" readonly  name="ApplyPer"></td>
                <td class=title> 申请日期 </td>
                <td class= input><Input class="readonly" readonly  name="ApplyDate"></td>
                <td class=title> 提起阶段 </td>
                <!--<td class= input><Input class="readonly" readonly  name="InitDept"></td>  ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" -->
                <td class= input><Input class=codeno readonly name="InitDept" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ><input class=codename name="InitDeptName" readonly=true></TD>      
            </tr>
            <TR class=common>
				<TD  class= title>调查机构</TD>
				<!--<TD  class= input><input class="readonly" readonly name="InqDept"></TD>  ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"-->  
				<TD class= input><Input class=codeno readonly  name="InqDept"  onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" readonly=true></TD> 	              
      			<TD class=title> 发起机构 </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InitDept"></TD>  ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" -->
              <TD class= input><Input class=codeno readonly name="InitDept" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ><input class=codename name="InitDeptName" readonly=true></TD> 	   
            	<TD class=title> 本地标志 </TD>
            	<TD class= input><Input class="readonly" readonly name="LocFlag"></TD> 
            	<!--<TD class= input><input class=codeno readonly name="LocFlag" CodeData="0|3^0|本地^1|异地"  onkeyup="return showCodeListKeyEx('LocFlag', [this,LocFlagName],[0,1]);"><input class=codename name="LocFlagName" readonly=true></TD>--> 	   	 
            </TR>    
            <TR class=common>
			    <TD class=title> 调查原因 </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InqRCode"></TD>  ondblclick="return showCodeList('llinqreason',[this,InqRCodeName],[0,1]);"-->
                <TD class= input><Input class=codeno readonly  name="InqRCode"  onkeyup="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);"><input class=codename name="InqRCodeName" readonly=true></TD>               
                <TD class=title> 调查项目 </TD>
                <TD class= input><Input class="readonly" readonly  name="InqItem"></TD>   
                <TD class=title> </TD>
                <TD class= input></TD>           
            </TR>         
    </TABLE> 
	<Table class=common>
			<TR  class= common> <TD class= common> 调查描述</TD></TR>
			<TR  class= common>
				<TD  class= common><textarea name="InqDesc" readonly class="hcommon" ></textarea></TD>
			</TR>   
	</Table> 
	<Table class= common> 
		    <TR><Input class=cssButton  name=""  value="查询其他调查信息" type=button onclick="queryOtherInqClick();"></TR>
	</Table> 
  </DIV>               

    <hr><!--分割线-->  
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqCourseInfo);"></TD>
            <TD class= titleImg>调查过程录入</TD>
       </TR>
    </table>
    <Div  id= "divInqCourseInfo" align=center style= "display: ''">
    <table class = common>
      <TR  class= common>
        <TD  class= title>调查方式</TD>
        <!--<TD  class= input><input class= common name="InqMode"><font size=1 color='#ff0000'><b>*</b></font></TD>-->
              <td class= input><Input class=codeno  name="InqMode" ondblclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onkeyup="return showCodeListKey('llinqmode',[this,InqModeName],[0,1]);"><input class=codename name="InqModeName" readonly=true></TD>        
        <TD  class= title>调查地点</TD>
        <TD  class= input><input class= common name="InqSite"></TD>
        <TD  class= title>调查日期</TD>
       <TD  class= input><input class= 'coolDatePicker' dateFormat="Short" name="InqDate"></TD> 
      </TR>
      <TR class = common> 
        <TD  class= title>第一调查人</TD>
        <TD  class= input><input class= common name="InqPer1"></TD>
        <TD  class= title>第二调查人</TD>
        <TD  class= input><input class= common name="InqPer2" ></TD>
        <TD  class= title>被调查人</TD>
        <TD  class= input><input class= common name="InqByPer" ></TD>
      </TR>
    </table>  
    <Table  class= common>
      <TR  class= common>
        <TD text-align: left colSpan=1><span id="spanLLInqCertificateGrid" ></span> </TD>
      </TR>
    </Table>
     <Div id= "DivInqCertificate" style= "display: 'none'">
       <hr>
          <table class= common>
              <tr class= common>
                  <TD  class= title>单证名称</TD>                      
                  <TD  class= input><Input class=codeno name=AffixCode ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1],'','','','1','500');"><input class=codename name=AffixName readonly=true></TD>
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                     
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                                                                     
              </tr>   
  
              <tr class= common>
                  <TD  class= title><input type="checkbox" value="" name="checkbox" onclick="checkboxClick()">选择其他</input></TD>                       
                  <TD  class= input> <Input class=common name="OtherName" disabled=true></TD>
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                     
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                                                                         
              </tr>      

          </table>
          <table class= common>
            <tr class= common>    
                  <input class=cssButton type=button value=" 确 认 " onclick="addInqCertificate();">
                  <input class=cssButton type=button value=" 取 消 " onclick="cancelInqCerficate();">
              </tr>
          </table> 
         <hr>  
     </Div>    
    <Table class=common>
			<TR  class= common> <TD  class= common> 调查过程录入 </TD></TR>
			<TR  class= common><TD class= common> <textarea name="InqCourse"  class="hcommon" ></textarea> </TD></TR>
			<TR  class= common> <TD  class= common> 调查过程备注 </TD></TR>
			<TR  class= common><TD  class= common><textarea name="InqCourseRemark" class="hcommon" ></textarea></TD></TR>
	</Table>
      <Table class= common> 
        <tr>
            <td>
              <Input class=cssButton name=""  value="调查单证" type=button onclick="showInqCerficate();">
              <Input class=cssButton name=""  value=" 保  存 " type=button onclick="AddInqCourseClick();">
                  <Input class=cssButton name=""  value="查询过程" type=button onclick="InqCourseQueryClick();">
              </td>
        </tr>
     </TABLE>         
    </Div>  
    <hr><!--分割线-->
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqFeeInfo);"></TD>
            <TD class= titleImg>查勘费用录入</TD>
       </TR>
    </table>
    <Div  id= "divInqFeeInfo" align=center style= "display: ''">
        <Table class= common> 
          <TR  class= common>
                <td class=title> 费用类型 </td>
            	<td class= input><Input class=codeno readonly=true name="FeeType" ondblclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llinqfeetype',[this,FeeTypeName],[0,1],null,null,null,null,'400');"><input class=codename name="FeeTypeName" readonly=true></TD>
                <TD  class= title>费用金额</TD>
                <TD  class= input><input class= common name="FeeSum"></TD>
                <TD  class= title>发生时间</TD>
                <TD  class= input><input class= 'coolDatePicker' dateFormat="Short" name="FeeDate"></TD>
           </TR>
            <TR  class= common>
                <TD  class= title>领款人</TD>
                <TD  class= input><input class= common name="Payee"></TD>
                <TD  class= title>领款方式</TD>
                <td class= input><Input class=codeno  name="PayeeType" ondblclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,PayeeTypeName],[0,1]);"><input class=codename name="PayeeTypeName" readonly=true></TD>
           </TR>
        </TABLE>
        <Table class= common>         
	    	<TR class= common><TD class= title> 备注 </TD></TR>
		    <TR class= common><TD class= input> <textarea name="Remark" class="hcommon"></textarea></TD></TR>
        </Table>  
        
      <Table class= common> 
        <tr>
            <td>
              <Input class=cssButton name=""  value=" 保  存 " type=button onclick="AddInqFeeClick();">
                  <Input class=cssButton name=""  value="查询费用" type=button onclick="QueryInqFeeClick();">
              </td>
        </tr>
     </TABLE>       
    </Div>  
    <hr>
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqConclusionInfoAffix);"></TD>
            <TD class= titleImg>添加与浏览附件</TD>
        </TR>           
    </table>
    <Div  id= "divInqConclusionInfoAffix" align=center style= "display: ''">
    <Table class= common> 
        <tr>
            <td>
              <Input class=cssButton name="AddAffix"  value="添加附件" type=button onclick="AddAffixClick();">
              <Input class=cssButton name="LoadAffix" value="浏览附件" type=button onclick="LoadAffixClick();">
              </td>
        </tr>
     </TABLE>     
    </Div>
    <hr><!--分割线-->    
   <Table>
        <TR>
        	<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqConclusionInfo);"></TD>
            <TD class= titleImg>调查结论录入</TD>
        </TR>   
        
    </Table>
    <Div  id= "divInqConclusionInfo" align=center style= "display: ''">
		<Table class=common>	
			<TR  class= common>
				<TD ><input type="checkbox" value="1" name="MoreInq" onclick="MoreInqClick();">调查完成标志</input></TD>
            </TR>		
			<TR  class= common> <TD  class= common> 调查结论</TD></TR>
			<TR  class= common>
				<TD  class= common><textarea name="InqConclusion" disabled class="hcommon" ></textarea></TD>
			</TR>
		</Table>
		<Table class= common> 
		    <TR>
		        <TD>
		        	<Input class=cssButton  disabled name="InqConfirm"  value=" 确 认 " type=button onclick="InqConfirmClick();">
	                <Input class=cssButton name=""  value=" 返 回 " type=button onclick="turnback();">
	            </TD>
		    </TR>
	   </Table>  
      
    </Div>          
    	<!-- 隐藏区域-->
      	<input type=hidden id="InqState" name="InqState"><!--调查完成标示  “02--完成”-->  
      	<!--<td class=title> 提起阶段 </td>-->
     	<Input type=hidden class="readonly" readonly  name="InitPhase">
     
     	<input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
      	<input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->  
      
      	<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag"><!---->   
        
      	<input type=hidden id="MissionID" name="MissionID" ><!--任务ID-->  
      	<input type=hidden id="SubMissionID" name="SubMissionID" ><!--子任务ID-->    
      	<input type=hidden id="Activityid" name="Activityid" ><!--子任务ID-->    
      
        <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
        
        <Input type=hidden id="ConNo" name="ConNo"><!--调查结论序号-->

  	</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
