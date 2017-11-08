<html> 
<% 
//程序名称：WFEdorConfirmInput.jsp
//程序功能：保全工作流-保全确认
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
 
<%
	//添加页面控件的初始化。
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");  
%>   
<script>	
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFEdorConfirm.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFEdorConfirmInit.jsp"%>
  
  <title>复核修改</title>

</head>

<body  onload="initForm();" >
  <form  method=post name=fm id=fm target="fraSubmit">
  <div id="ConfirmInputPool"></div>
  <!--<table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
		        <td class=title> 保全受理号 </td>
		        <td class= input><Input class="common" name=EdorAcceptNoSearch></td>
	          	<TD class= title> 号码类型 </TD>
	          	<td class= input><Input class="codeno" name=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);"onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName readonly=true ></td>            	                          
	          	<TD class= title> 客户/保单号 </TD>
	          	<td class= input><Input class="common" name=OtherNo></td>
	        </TR>
	      	<TR  class= common>
                <td class=title> 申请人 </td>
                <td class=input><Input type="input" class="common" name=EdorAppName></td>             
                <td class=title> 申请方式 </td>
                <td class= input ><Input class="codeno" name=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);"onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName readonly=true></td>                          
	            <TD class= title> 录入日期 </TD>
	            <TD class= input><Input class= "multiDatePicker" dateFormat="short" name=MakeDate ></TD> 
	        </TR> 
	      	<TR  class= common>
				<TD class=title> 管理机构 </TD>
	          	<TD class=input><Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>      	               
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	        </TR> 	        
	    </table>
    </div> 
    
	<INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClickAll();"> 
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllGrid);">
    		</td>
    		<td class= titleImg>
    			 共享工作池
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAllGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();HighlightAllRow();"> 
      	<INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();HighlightAllRow();"> 					
      	<INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();HighlightAllRow();"> 
      	<INPUT CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();HighlightAllRow();">						
  	</div>
  	
	<br>
		<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyMission();">
	<br>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
    		</td>
    		<td class= titleImg>
    			 我的任务
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightSelfRow();"> 
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightSelfRow();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightSelfRow();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightSelfRow();"> 					
  	</div>  -->  
  	<Div  id= "divAppUWLable" style= "display: ''">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppUWInfo);">
                    </td>
                    <td class= titleImg> 保全核保结论 </td>
                </tr>
               </table>
     </DIV>
    <Div  id= "divAppUWInfo" style= "display: ''" class="maxbox1">
             <table  class= common>
                <tr class=common>
                    <td class=title> 核保结论 </td>
                    <td class=input><Input class="codeno" name=AppUWState id=AppUWState readonly  ><input class=codename name=AppUWStateName id=AppUWStateName readonly=true></td>                    <td class=title></td>
                    <td class=input></td>
                    <td class=title></td>
                    <td class=input></td>
                </tr></table>
                <table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > 核保意见 </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px" colspan=6 ><textarea name="AppUWIdea" cols="166%" rows="4" witdh=100% class="common" readonly ></textarea></TD>
                </tr>
              </table>
    </DIV>
    
    <!--  add by jiaqiangli 2009-04-03 增加强制人工核保原因录入 -->
   <Div  id= "divMandatoryUWLable" style= "display: ''">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMandatoryUWInfo);">
                    </td>
                    <td class= titleImg> 强制人工核保原因 </td>
                </tr>
               </table>
     </DIV>
    <Div  id= "divMandatoryUWInfo" style= "display: ''" class="maxbox1">
             <table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > 强制人工核保原因录入 </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px" colspan=6 ><textarea name="MMUWReason" cols="166%" rows="4" witdh=100% class="common" ></textarea></TD>
                </tr>
              </table>
    </DIV> 
    <!--  add by jiaqiangli 2009-04-03 增加强制人工核保原因录入 -->
    
  	<br>
	<!--<INPUT class= cssButton TYPE=button VALUE=" 保全确认 " onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE=" 强制终止 " onclick="doOverDue();">
	<INPUT class= cssButton TYPE=button VALUE=" 拒 保 " onclick="doJB();">
	<INPUT class= cssButton TYPE=button VALUE=" 强制提交核保 " onclick="submitUW();">
	<INPUT class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">保全确认</a><!--
    <a href="javascript:void(0);" class="button" onClick="doOverDue();">强制终止</a>
    <a href="javascript:void(0);" class="button" onClick="doJB();">拒    保</a>
    --><a href="javascript:void(0);" class="button" onClick="submitUW();">强制提交核保</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a><br>
    
	 <Div  id= "divGetNotice" style= "display: none">
	 	  	<br>
     <!--<INPUT class= cssButton TYPE=button VALUE="付费通知书" onclick="GetNotice();">-->
     <a href="javascript:void(0);" class="button" onClick="GetNotice();">付费通知书</a><br>
  </Div>

	<Div  id= "divPayNotice" style= "display: none">
		  	<br>
      <!--<INPUT VALUE=" 补费通知书 " class=cssButton TYPE=button onclick="PayNotice();">-->
      <a href="javascript:void(0);" class="button" onClick="PayNotice();">补费通知书</a><br>
  </Div>

<Div  id= "divCashValue" style= "display: none">

		  	<br>
      <!--<INPUT VALUE=" 打印新现价表 " class=cssButton TYPE=button onclick="PrintCashValueTable();">-->
      <a href="javascript:void(0);" class="button" onClick="PrintCashValueTable();">打印新现价表</a>
  </Div>
		<!-- 隐藏域部分 -->
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">
          	<INPUT  type= "hidden" class= Common name= EdorAcceptNo  value= "">
          	<INPUT  type= "hidden" class= Common name= fmtransact  value= "">
          	<INPUT  type= "hidden" class= Common name= PrtSeq  value= "">
			
			<INPUT  type= "hidden" class= Common name= OtherNo1  value= "">
			<INPUT  type= "hidden" class= Common name= OtherNoType1  value= ""> 
			<INPUT  type= "hidden" class= Common name= EdorAppName1  value= "">
			<INPUT  type= "hidden" class= Common name= Apptype1  value= "">
			<INPUT  type= "hidden" class= Common name= ManageCom1  value= "">
			<INPUT  type= "hidden" class= Common name= AppntName1  value= "">
			<INPUT  type= "hidden" class= Common name= PaytoDate1  value= "">
		<!--	<INPUT  type= "hidden" class= Common name= CustomerNo1  value= "">
			<INPUT  type= "hidden" class= Common name= InsuredName1  value= "">
			<INPUT  type= "hidden" class= Common name= InsuredName2  value= "">
			<INPUT  type= "hidden" class= Common name= InsuredName3  value= "">
			<INPUT  type= "hidden" class= Common name= EdorType1  value= "">
			<INPUT  type= "hidden" class= Common name= theCurrentDate1  value= "">
			<INPUT  type= "hidden" class= Common name= BackDate1  value= "">
			<INPUT  type= "hidden" class= Common name= VIP1  value= "">
			<INPUT  type= "hidden" class= Common name= StarAgent1  value= "">-->
			<INPUT type="hidden" class= Common name=uWstate1 value="">
			<INPUT type="hidden" class= Common name=CustomerIdea value="">
			
			<INPUT  type= "hidden" class= Common name= ContNo  value= "">
			<!--保全确认操作人 --->
			<INPUT  type= "hidden" class= Common name= Operator  value= "">
			<!--保全申请操作人 --->
			<INPUT  type= "hidden" class= Common name= ApplyOperator  value= "">
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
