<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
	<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tUserCode  = tG.Operator;
%>
<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="BqSubmitApplyDeal.js"></SCRIPT>
<%@include file="BqSubmitApplyDealInit.jsp"%>
  
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

</head>
<script language="javascript">
var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
var SubNo = "<%=request.getParameter("SubNo")%>";
var flag = "<%=request.getParameter("flag")%>";
var manageCom = "<%=tG.ManageCom%>"; //记录登陆机构
var comcode = "<%=tG.ComCode%>"; //记录登陆机构
</script>
<body onload="initForm();">
	<Form action="./BqSubmitApplyDealSave.jsp"  method=post name=fm id=fm target="fraSubmit" ENCTYPE="multipart/form-data">
	    <table>
	        <TR><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>	        	
	         	<TD class= titleImg>呈报的具体信息</TD>
	       	</TR>
	     </table> 
        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
     		<Table class= common>     				
		      	
        		<TR  class= common>
        			<TD  class= title5>呈报号</TD>
					<TD  class= input5><Input class="wid" class= "readonly" name="SubNo" id="SubNo" readonly></TD>  
					<TD  class= title5>保全项目</TD>
					<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name="EdorType"  readonly=true ondblclick="getEdorInfo();return showCodeListEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);" onMouseDown="getEdorInfo();return showCodeListEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);" onkeyup="getEdorInfo();return showCodeListKeyEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);"><input class=codename name="EdorTypeName" id="EdorTypeName" readonly=true></TD> 
				</TR>
  				<tr class=common>
			        <TD  class= title5>团体合同号码</TD>
					<TD  class= input5><Input class="wid" class= "common" name="GrpContNo" id=GrpContNo></TD> 			        
  					<TD  class= title5>承接机构</TD>
					<TD  class= input5><Input class="codeno" name="DispDept" id="DispDept" readonly=true><input class=codename name="DispDeptName" id="DispDeptName" readonly=true></TD>    			
         		</TR>
  				<tr class=common>
         			 
         			<TD  class= title5>承接人员</TD>
					<td class=input5><Input  class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" name="DispPer" id="DispPer" readonly=true ondblclick="getDispPer();return showCodeListEx('DispPer',[this,DispPerName],[0,1],'', '', '', true);" onMouseDown="getDispPer();return showCodeListEx('DispPer',[this,DispPerName],[0,1],'', '', '', true);" onkeyup="getDispPer();return showCodeListKeyEx('DutyGetKind',[this,DispPerName],[0,1],'', '', '', true);"><input class=codename name=DispPerName id=DispPerName readonly=true></td>
         			<TD  class= title5> 呈报人</TD>
					<TD  class= input5><Input class="wid" class= "readonly"  name="SubPer" id="SubPer" readonly value="<%=tUserCode%>"></TD>
         			
		        </tr>
		    </Table>
		</Div>       
		<Div id= "DivApp">  
		    <Table class= common>     
		        <TR  class= common height=24>   	
					<TD  class= title colSpan=4 ><strong> 附件内容</strong></TD>
				</tr>
			    <TR  class= common>
					<TD  class= input colSpan=4>
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="团险保全申请单证;">团险保全申请单证&nbsp;
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="委托授权书;">委托授权书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="身份证复印件;">身份证复印件&nbsp;
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="保险单原件;">保险单原件&nbsp;<br>
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="健康告知书;">健康告知书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="社保证明材料;">社保证明材料&nbsp;&nbsp;
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="交费凭据;">交费凭据<br>
						<INPUT TYPE="checkbox" NAME="AppTextChk" id=AppTextChk value="体检报告;">体检报告&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<INPUT TYPE="checkbox" NAME="AppTextChkOther" id=AppTextChkOther value="其他:">其他（请注明）&nbsp;&nbsp;
						<br/><br/>
						<Input class="wid" class=common  name="AppTextOther" id="AppTextOther">
						<Input type=hidden name="AppText" id=AppText>
					</TD>
			    </tr>
			    <TR  class= common>
			        <TD  class= title5>附件</TD>
			        <TD  class= input5><input class="wid" type="file" name="filePath" id=filePath accept="*" class="common"></td>
                    <TD  class= title5></TD>
                    <TD  class= input5></TD>
		        </TR>
		    </table>
		</div> 
		        
		         <Div id= "DivNewDispPer" style="display: none"> 
		         	 <Table class= common>     
		        			<TR  class= common>
		        				<TD  class= title5>新承接机构</TD>
								<TD  class= input5><Input  class="codeno" name="NewDispDept" id="NewDispDept" readonly=true><input class=codename name="NewDispDeptName" id="NewDispDeptName" readonly=true></TD> 
								<TD  class= title5>新承接人员</TD>
								<td class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name="NewDispPer" id="NewDispPer"  ondblclick="getNewDispPer();return showCodeListEx('NewDispPer',[this,NewDispPerName],[0,1],'', '', '', true);" onMouseDown="getNewDispPer();return showCodeListEx('NewDispPer',[this,NewDispPerName],[0,1],'', '', '', true);" onkeyup="getNewDispPer();return showCodeListKeyEx('NewDispPer',[this,NewDispPerName],[0,1],'', '', '', true);"><input class=codename name=NewDispPerName id=NewDispPerName readonly=true></td>
		        		</TR>
		        	</Table>  
		        </Div>
		 	
		 	<Table class= common>
		        <TR >
	            	<TD class= title> 呈报描述 </TD>
	            </TR>     
	            <TR >  
	            	<TD style="padding-left:16px" class= input> <textarea name="SubDesc" id=SubDesc cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
	           
      		</Table>
      		<div id="UpSubApp" style="display:none">
	            
	            <Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="修改呈报信息" type=button onclick="upSubInfo()">
	           
	           </div>
	    	
	   
	    
	    <hr class="line">
	    <Div id= "Divunload" style="display: none">   
	    <table>
	    <tr></tr>
		</table>
		</Div>   
	    <Div id= "DivSave">          
	    	<TABLE class= common> 	        	
	            <TR>
	                <TD class=common>
	                	<Input class=cssButton  name="Replyconfirm" id=Replyconfirm value=" 呈 报 " type=button onclick="Replyport();"> 
   	                    <Input class=cssButton  value=" 返 回 " type=button onclick="TurnBack()">
   	                </TD> 
	            </TR>
  	    	</TABLE> 
	    </Div> 
	    
	    
	    <Div id= "DivCPSee" style="display: none">          
	    	<TABLE class= common> 	        	
	            <TR>
	                <TD class=common>
	                	<Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="查看扫描件" type=button onclick="HaveApp()">
   	                </TD> 
	            </TR>
  	    	</TABLE> 
	    </Div> 
	    
	    <Div id= "DivDeal" align=center> 
	    	<TABLE class= common>   
	        	<TR class= common>
	        		<TD class= title5> 选择呈报处理类型:</TD> 
					<TD class= input5>
						<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name=DispType id=DispType CodeData="0|^0|继续呈报^1|呈报完成^2|退回^C|确认关闭" ondblclick="showCodeListEx('DispType',[this,DispTypeName],[0,1]);" onMouseDown="showCodeListEx('DispType',[this,DispTypeName],[0,1]);" onkeyup="showCodeListKeyEx('DispType',[this,DispTypeName],[0,1]);">
						<input class=codename name="DispTypeName" id="DispTypeName" readonly=true> 
					</TD>
					<TD class= title5></TD>
					<TD class= input5></TD>

	            </TR>  
				<TR class= common>
					<TD class= title5> 呈报处理意见 </TD>
					<TD class= title5></TD>
					<TD class= title5></TD>
					<TD class= title5></TD>
				</TR>     
	        </TABLE> 
			<TABLE class= common>
				<TR class= common>  
					<TD class= input> <textarea name="DispIdea" id=DispIdea cols="100" rows="4" witdh=25% class="common wid"></textarea></TD>
				</TR>
			</TABLE> 
	                 
	                	<Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="处理确认" type=button onclick="ReplyportConfirm()"> 
	                	<Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="已有处理" type=button onclick="HaveDeal()"> 
	                	<Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="附件明细" type=button onclick="ShowAppFile()">
   	                    <Input class=cssButton  value=" 返 回 " type=button onclick="TurnBack()">
   	           
	      
	    </Div>     
	    <Div id= "DivSee"> 
	    <TABLE class= common> 	        	
	            <TR>
	                <TD class=common>
	                	    
   	                    <Input class=cssButton  value=" 返 回 " type=button onclick="TurnBack()">
   	                    <Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="已有处理" type=button onclick="HaveDeal()"> 
	                	<Input class=cssButton  name="Replyconfirm" id=Replyconfirm value="附件明细" type=button onclick="ShowAppFile()">
   	                </TD> 
	            </TR>
  	    	</TABLE> 
	    </Div>  
	    <Div id="DivAppShow" style="display:  none ">
	    	<input type=hidden id="FilePathHide" 	name="FilePathHide">
	    	<input type=hidden id="DatePathHide" 	name="DatePathHide">
	    	<TABLE class= common height=24>   
	        	<TR class=common>
	        		 <TD  class= title> <strong> 附件内容</strong></TD>
	        	</TR>
	        <TR class=common height=24>
	        		 <TD class= input><textarea class="common wid"  cols="100" rows="4" witdh=25% onpropertychange="this.style.height=this.scrollHeight+'px';"  oninput="this.style.height=this.scrollHeight+'px';" style="overflow:hidden;" readonly=true name="AppTextHide"></textarea></TD>
	        		 </TR>
	        <TR class=common height=24>
	        		 <TD  class= title colSpan=4><DIV id="AppPath"></div></TD>
	        	</TR>
  	    	</TABLE> 
	    </DIV>
	    
	     <Div  id= "DivLPTraceSubmit" style= "display: none">
		    <span id="operateButton1" >
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanLPSubmitApplyTraceGrid"></span> 
                </TD>
              </TR>
            </Table> 
            <DIV align=center> 
	        <table> 
	            <tr>  
	                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
	                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
	                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
	                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
	            </tr> 
           </table>
          </div>
       <table>
	        <TR>	        	
	         	<TD class= titleImg>处理意见</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="DispIdeaTrace" id=DispIdeaTrace cols="170" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>  
        </Div>   
	</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
