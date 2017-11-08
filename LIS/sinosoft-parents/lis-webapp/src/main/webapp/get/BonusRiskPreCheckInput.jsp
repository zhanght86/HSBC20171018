<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
   
    String CurrentDate = PubFun.getCurrentDate();
    loggerDebug("BonusRiskPreCheckInput",CurrentDate);      
%>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="BonusRiskPreCheck.js"></SCRIPT> 
  <%@include file="BonusRiskPreCheckInit.jsp"%> 
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./BonusRiskPreCheckSave.jsp" method=post name=fm id=fm target="fraSubmit">

  <Div class="maxbox1">      
    <table  class= common>
      <TR class= common>
         <TD  class= title5>
            红利分配会计年度
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalYear id=BonusCalYear verify="红利分配会计年度|notnull&INT&len==4"><font color=red>*</font>
           </TD> 
          <TD  class= title5>
            红利分配险种
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalRisk id=BonusCalRisk >
          </TD>   
      </TR>     
    </Table>  
</Div>
<INPUT VALUE="查  询" name = "query" CLASS=cssButton TYPE=button onclick="queryData();">
<!--<a href="javascript:void(0);" name = "query" class="button" onClick="queryData();">查    询</a>-->
<table>
   <tr>
      <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);"></td>
      <td class= titleImg>
        参与分红险种信息
      </td>
   </tr>
</table>   

  
       
<Div  id= "divAgentGrid" style= "display: ''" >
      	<table  width="100%" >
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanNoBonusRiskGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="首  页" TYPE=button CLASS=cssButton90 onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="上一页" TYPE=button CLASS=cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="下一页" TYPE=button CLASS=cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="尾  页" TYPE=button CLASS=cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
</div>


<INPUT VALUE="上线确认" name = "compute" CLASS=cssButton TYPE=button onclick="dealData();">
<!--<a href="javascript:void(0);" name = "compute" class="button" onClick="dealData();">上线确认</a>-->
    <input type="hidden" name="transact">      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
