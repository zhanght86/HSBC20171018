<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
   Operator = "<%=tGlobalInput.Operator%>";
   ComCode = "<%=tGlobalInput.ComCode%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="UWUpReportDeal.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWUpReportDealInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  <title>投保单复核 </title> 
</head>


<body  onload="initForm();initElementtype();" >
  <form action="./ProposalApproveSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <div id ="UWUpReportPool"></div>
     <Input type=hidden id="MissionID" name="MissionID" >
     <Input type=hidden id="SubMissionID" name="SubMissionID" >
     <Input type=hidden id="ActivityID" name="ActivityID" >  
     <Input type=hidden id="ManageCom" name="ManageCom" >
     <Input type=hidden id="AgentCode" name="AgentCode" >
     <Input type=hidden id="ProposalNo" name="ProposalNo" >
   <!--   <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title>
            印刷号
          </TD>
          <TD  class= input>
            <Input class= common name=ProposalNo type="hidden">
            <Input class= common name=PrtNo>
          </TD>
<!--
          <TD  class= title>
           被生调人客户号
          </TD>
          <TD  class= input>
            <Input class=common  name=CustomerNo >
          </TD>
-->          
          
        <!--  <TD  class= title8>
            管理机构
          </TD>
          <TD  class= input8>
            <Input class="codeno" name=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </TD>          
        </TR>
        
        <TR  class= common>         
          <TD  class= title>
            业务员编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode ondblclick="return queryAgent();">
          </TD>
          <TD  class= title>
            呈报日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short"  name=MakeDate >
          </TD>

        </TR>

    </table>
    </DIV>
          <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 共享工作池
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common >
       		<tr  class= common align = left>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">		
  	</div>
    
  <br>
   <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();">

   <DIV id=DivLCContInfo STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
    		</td>
    		<td class= titleImg>
    			 待处理保单
    		</td>
    	</tr>  	
    </table>
    </Div>
         <Div  id= "divSelfPolGrid" style= "display: ''" align = center>
          <table  class= common >
       		<tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSelfPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
  		  </table>
         <INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
         <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
         <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
         <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">     
    </div>
	<br>     -->   
       	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
