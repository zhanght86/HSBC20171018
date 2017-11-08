<%
//程序名称：LLSubmitApplyDealInput.jsp
//程序功能：呈报信息处理
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //赔案号
	      String tSubNo=request.getParameter("SubNo"); //呈报序号      
	      String tSubCount=request.getParameter("SubCount"); //呈报次数
		  String tMissionID =request.getParameter("MissionID");  //工作流任务ID
		  String tSubMissionID =request.getParameter("SubMissionID");  //工作流子任务ID	 		  
	%>
	<title>呈报信息</title>  
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> 
    <SCRIPT src="LLSubmitApplyDeal.js"></SCRIPT>
    <%@include file="LLSubmitApplyDealInit.jsp"%>
</head>
<body  onload="initForm();">
	<Form action="./LLSubmitApplyDealSave.jsp"  method=post name=fm target="fraSubmit">
	    <table>
	        <TR>
	        	<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLSubmit);"></TD>
	         	<TD class= titleImg>选中呈报的具体信息</TD>
	       	</TR>
	     </table> 
        <Div  id= "DivLLSubmit" style= "display: ''">
     		<Table class= common>     				
		      	<TR  class= common>
			      	<TD  class= title> 赔案号</TD>
			      	<TD  class= input><Input class= "readonly" readonly id="ClmNo" name="ClmNo"></TD>
			        <TD  class= title>呈报序号</TD>
			        <TD  class= input><Input class= "readonly" readonly id="SubNO" name="SubNO"></TD>
			        <TD  class= title>呈报次数</TD>
			        <TD  class= input><Input class= "readonly" readonly id="SubCount"  name="SubCount"></TD>
		        </TR>
        		<TR  class= common>   	
	      			<TD  class= title> 出险人客户号</TD>
			      	<TD  class= input><Input class= "readonly" readonly id="CustomerNo" name="CustomerNo"></TD>
			        <TD  class= title> 出险人名称</TD>
			        <TD  class= input><Input class= "readonly" readonly id="CustomerName" name="CustomerName"></TD>
			        <TD  class= title> VIP客户</TD>
	        		<TD  class= input><Input class= "readonly" readonly id="VIPFlag"  name="VIPFlag"></TD>
		        </TR>
		        <TR  class= common>   	
		        	<TD  class= title> 提起阶段</TD>
                    <TD  class= input> <Input class=codeno type=hidden disabled name="InitPhase" ondblclick="return showCodeList('llinitphase',[this,InitPhaseName],[0,1]);" onkeyup="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);"><input class= "readonly" name="InitPhaseName" readonly=true></TD>
			        <TD  class= title> 呈报类型</TD>
                    <TD  class= input> <Input class=codeno type=hidden  disabled name="SubType" ondblclick="return showCodeList('llsubtype',[this,SubTypeName],[0,1]);" onkeyup="return showCodeListKey('llinitphase',[this,SubTypeName],[0,1]);"><input class= "readonly" name="SubTypeName" readonly=true></TD>
			        <TD  class= title> 呈报原因</TD>
			        <TD  class= input><Input class= "readonly" readonly id="SubRCode"  name="SubRCode"></TD>
		            
		        </TR>
		        <TR  class= common>   	
			        <TD  class= title> 呈报人</TD>
			        <TD  class= input><Input class= "readonly" readonly id="SubPer" name="SubPer"></TD>
			        <TD  class= title> 呈报机构</TD>
			      	<!--<TD  class= input><Input class= "readonly" readonly id="SubDept" name="SubDept"></TD>-->
                    <TD  class= input> <Input class=codeno disabled type=hidden name="SubDept" ondblclick="return showCodeList('station',[this,SubDeptName],[0,1]);" onkeyup="return showCodeListKey('station',[this,SubDeptName],[0,1]);"><input class= "readonly"  name="SubDeptName" readonly=true></TD>
			        <TD  class= title> 呈报日期</TD>
			        <TD  class= input><Input class= "readonly" readonly id="SubDate"  name="SubDate"></TD>
		        </TR>
		        <TR  class= common>   	
			        <TD  class= title> 呈报状态</TD>
			        <!--<TD  class= input><Input class= "readonly" readonly id="SubState" name="SubState"></TD>-->
                    <TD  class= input> <Input class=codeno type=hidden  disabled name="SubState" ondblclick="return showCodeList('llsubstate',[this,SubStateName],[0,1]);" onkeyup="return showCodeListKey('llsubstate',[this,SubStateName],[0,1]);"><input class= "readonly" name="SubStateName" readonly=true></TD>		        
			        <TD  class= title> 承接人员编号</TD>
			      	<TD  class= input><Input class= "readonly" readonly id="DispPer" name="DispPer"></TD>
			        <TD  class= title> 承接机构代码</TD>
			        <TD  class= input><Input class= "readonly" readonly id="DispDept"  name="DispDept"></TD>
		        </TR>
		 	</Table>
		 	<Table class= common>
		        <TR >
	            	<TD class= title> 呈报描述 </TD>
	            </TR>     
	            <TR >  
	            	<TD class= input> <textarea name="SubDesc" readonly cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
      		</Table>
	    </Div>	
	    <!--#######总公司处理意见，用于“ 反馈呈报队列”处理时查看---2005-08-14添加 #############-->
	    <Div  id= "DivHeadIdea" style= "display: ''">  
	        <!-- 对报案的回复意见   -->        
	    	<TABLE class= common> 
	        	<TR class= common>
	            	<TD class= title> 总公司处理意见 </TD>
	            </TR>     
	            <TR class= common>  
	            	<TD class= input> <textarea name="HeadIdea" readonly cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
  	    	</TABLE> 
	    </Div> 
	    <hr>
	    <Div> 
	    	<TABLE class= common>   
	        	<TR >
	        		<TD class= title> 选择呈报处理类型</TD> 
                    <TD  class= input> <Input class=codeno readonly name="DispType" ondblclick="return showCodeList('lldisptype',[this,DispTypeName],[0,1]);" onkeyup="return showCodeListKey('lldisptype',[this,DispTypeName],[0,1]);"><input class=codename name="DispTypeName" readonly=true></TD>
	            </TR>  
	        </TABLE> 
	    </Div>     
	    <Div  id= "DivDispType" style= "display: 'none'">  
	        <!-- 对报案的回复意见   -->        
	    	<TABLE class= common> 
	        	<TR class= common>
	            	<TD class= title> 呈报处理意见 </TD>
	            </TR>     
	            <TR class= common>  
	            	<TD class= input> <textarea name="DispIdea" cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
  	    	</TABLE> 
	    </Div> 
		<Div  id= "DivReportheadSubDesc" style= "display: 'none'">  
	    	<Table class= common>
		        <TR >
	            	<TD class= title> 继续呈报原因描述 </TD>
	            </TR>     
	            <TR >  
	            	<TD class= input> <textarea name="ReportheadSubDesc"  cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
      		</Table>
	    </Div>	
	    <Div>          
	    	<TABLE class= common> 	        	
	            <TR>
	                <TD class=common>
	                	<Input class=cssButton  name="Replyconfirm" value="呈报确认" type=button onclick="Replyport()"> 
   	                    <Input class=cssButton  value=" 返 回 " type=button onclick="TurnBack()">
   	                </TD> 
	            </TR>
  	    	</TABLE> 
	    </Div> 
	    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->	    
	    <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
	    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->	
	    <input type=hidden id="MissionID" name="MissionID"><!--任务ID-->	
	    <input type=hidden id="SubMissionID" name="SubMissionID"><!--子任务ID-->	
	    
	</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>