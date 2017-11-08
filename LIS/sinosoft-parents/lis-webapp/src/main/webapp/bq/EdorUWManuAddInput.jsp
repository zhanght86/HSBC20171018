<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuAddInput.jsp
//程序功能：保全人工核保加费
//创建日期：2005-07-16 11:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="EdorUWManuAdd.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 加费 </title>
  <%@include file="EdorUWManuAddInit.jsp"%>
 
</head>
<body  onload="initForm('<%=tEdorNo%>', '','<%=tContNo%>', '<%=tMissionID%>', '<%=tSubMissionID%>','<%=tInsuredNo%>','<%=tEdorAcceptNo%>');" >
	
	
  <form method=post name=fm id=fm target="fraSubmit" >
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	    </tr>
        </table>	
        <Div  id= "divLCPol" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align = center>
    	  <tr class=common ><td >
        	<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
        	<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
        	<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="getNextPage();"> 
        	<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();"> 
        	</td></tr>	
    	</table>
    				
      </Div>
      <div id = "divAddInfo" style = "display:'none'">
        <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 加费信息
    		</td>
    	</tr>
		
       </table>
      	<Div  id= "divUWSpec1" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	</div>
    
    	
     <table class="common">
    	<TR  class= common>
          <TD  class= titleImg> 加费原因 </TD>
        </tr>
        <TR  class= common>
          	<TD  class= input> <textarea name="AddReason" id=AddReason cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
        </TR>
     </table>
     
    </div> 
      <INPUT type="hidden" name="PolNo2" id=PolNo2 value= ""> <!--保全加费所针对的保单-->
      <INPUT type= "hidden" name="PolNo" id=PolNo value= ""> <!--保全项目所针对的保单,由前一页面传来-->
      <INPUT type= "hidden" name="ContNo" id=ContNo value= "">
      <INPUT type= "hidden" name="MissionID" id=MissionID value= "">
      <INPUT type= "hidden" name="SubMissionID" id=SubMissionID value= "">
      <INPUT type= "hidden" name="InsuredNo" id=InsuredNo value="">
      <INPUT type= "hidden" name="EdorNo" id=EdorNo value= "">
      <INPUT type= "hidden" name="EdorType" id=EdorType value= "">
      <INPUT type= "hidden" name="OtherNoType" id=OtherNoType value= "">
      <INPUT type= "hidden" name="RiskCode" id=RiskCode value= "">
      <INPUT type = "hidden" name = "DutyCode" id=DutyCode value = "">
      <INPUT type= "hidden" name="AddFeeObject" id=AddFeeObject value= "">
      <INPUT type= "hidden" name="AddFeeType" id=AddFeeType value="">
      <INPUT type= "hidden" name="SuppRiskScore" id=SuppRiskScore value= "">
      <INPUT type= "hidden" name="SecondScore" id=SecondScore value= "">
      <INPUT type= "hidden" name="PayStartDate" id=PayStartDate value= "">
      <INPUT type= "hidden" name="PayToDate" id=PayToDate value= "">
      <INPUT type= "hidden" name="PayEndDate" id=PayEndDate value= "">
      <INPUT type= "hidden" name="GridRow" id=GridRow value= "">
      <INPUT type= "hidden" name="NoAddPrem" id=NoAddPrem value= "">
      <INPUT type= "hidden" name="EdorAcceptNo" id=EdorAcceptNo value= "">

   <table> 
   <tr>  
     <td>
       <Div  id= "divSubmit" style= "display: ''">
               <INPUT type= "button"  name="sure" id=sure value="确  认" class= cssButton onclick="submitForm()">			

       </div>
     </td>
     <td>
       <Div  id= "divReturn" style= "display: ''">
      		<INPUT type= "button" name= "return" id=return value="返  回" class= cssButton  onclick="parent.close();">	
	   </div>
	 </td>
   </tr>
   </table>   	  
 	
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
