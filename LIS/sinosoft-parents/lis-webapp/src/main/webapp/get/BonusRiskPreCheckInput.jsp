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
            �������������
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalYear id=BonusCalYear verify="�������������|notnull&INT&len==4"><font color=red>*</font>
           </TD> 
          <TD  class= title5>
            ������������
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalRisk id=BonusCalRisk >
          </TD>   
      </TR>     
    </Table>  
</Div>
<INPUT VALUE="��  ѯ" name = "query" CLASS=cssButton TYPE=button onclick="queryData();">
<!--<a href="javascript:void(0);" name = "query" class="button" onClick="queryData();">��    ѯ</a>-->
<table>
   <tr>
      <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);"></td>
      <td class= titleImg>
        ����ֺ�������Ϣ
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
			      <INPUT  VALUE="��  ҳ" TYPE=button CLASS=cssButton90 onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button CLASS=cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button CLASS=cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button CLASS=cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
</div>


<INPUT VALUE="����ȷ��" name = "compute" CLASS=cssButton TYPE=button onclick="dealData();">
<!--<a href="javascript:void(0);" name = "compute" class="button" onClick="dealData();">����ȷ��</a>-->
    <input type="hidden" name="transact">      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
