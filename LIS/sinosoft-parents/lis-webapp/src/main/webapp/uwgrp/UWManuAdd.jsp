<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpec.jsp
//程序功能：保全人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
 
   GlobalInput tGlobalInput = new GlobalInput();
   tGlobalInput=(GlobalInput)session.getValue("GI");	
   String tOperator = tGlobalInput.Operator;
   loggerDebug("UWManuAdd"," tOperator:"+ tOperator);
 %>  
 <script language="JavaScript">
	var tOperator = "<%=tOperator%>";
 </script>                          
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWManuAdd.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 加费承保 </title>
  <%@include file="UWManuAddInit.jsp"%>
 
</head>
<body  onload="initForm('', '<%=tContNo%>', '<%=tMissionID%>', '<%=tSubMissionID%>','<%=tInsuredNo%>');" >
	
	
  <form method=post name=fm target="fraSubmit" >
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	    </tr>
        </table>	
        <Div  id= "divLCPol2" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<tr class=common align=center>
        	<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="getFirstPage();"> 
        	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="getPreviousPage();"> 					
        	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="getNextPage();"> 
        	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="getLastPage();"> 
    	</tr>				
      </Div>
   
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
      	<Div  id= "divUWSpec1" style= "display: ''">
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
          <TD  class= title>
            加费原因
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="AddReason" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
      <INPUT type="hidden" name="PolNo2" value= ""> <!--保全加费所针对的保单-->
      <INPUT type= "hidden" name="PolNo" value= ""> <!--保全项目所针对的保单,由前一页面传来-->
      <INPUT type= "hidden" name="ContNo" value= "">
      <INPUT type= "hidden" name="MissionID" value= "">
      <INPUT type= "hidden" name="SubMissionID" value= "">
      <INPUT type= "hidden" name="InsuredNo" value="">
      
      <INPUT type= "hidden" name="RiskCode" value= "">
      <INPUT type = "hidden" name = "DutyCode" value = "">
      <INPUT type= "hidden" name="AddFeeObject" value= "">
      <INPUT type= "hidden" name="AddFeeType" value="">
      <INPUT type= "hidden" name="SuppRiskScore" value= "">
      <INPUT type= "hidden" name="SecondScore" value= "">
   
     
      
      <INPUT type= "button"  name="sure" value="确  认" class= cssButton onclick="submitForm()">			
	  
 	
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
