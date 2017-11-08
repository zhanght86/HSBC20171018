<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：EdorUWQueryInput.jsp
//程序功能：保全核保查询
//创建日期：2005-7-12 19:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%
	String tContNo = "";
	String tEdorNo = "";
	tContNo = request.getParameter("ContNo"); 
	tEdorNo = request.getParameter("EdorNo"); 

%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";          //个人单的查询条件.
	var EdorNo = "<%=tEdorNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="EdorUWQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EdorUWQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  	
    <!--合同信息-->
	<DIV id=DivLPContButton >
	<table id="table1">
			<tr>
				<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLPCont);">
				</td>
				<td class="titleImg">合同信息
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLPCont"  class=maxbox1>
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>保单号码 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id=ContNo VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>管理机构 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id=ManageCom  MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title" nowrap>销售渠道 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" id=SaleChnl CLASS="readonly wid" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">业务员编码 
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="AgentCode" id=AgentCode MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				
				<td CLASS="title">其它声明 
	    		</td>
				<td CLASS="input" COLSPAN="3">
					<input NAME="Remark" id=Remark CLASS="readonly wid" readonly MAXLENGTH="255">
	    		</td>
				<td></td>
				<td></td>
			</tr>
		</table>
</div>
		
		 </DIV>
          <INPUT class=cssButton VALUE="合同核保轨迹查询" TYPE=button onclick="QueryContTrace();">     
      
<DIV id=DivLPContInfo STYLE="display:''"> 
<hr>
<!--被保人信息-->   
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
    		</td>
    		<td class= titleImg>
    			 被保人信息:
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divLPInsured  " >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>
  
<!--险种信息-->  
<div id = "DivPolInfo"	style = "display:'none'">
  	<hr>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPPol);">
    		</td>
    		<td class= titleImg>
    			 险种信息:
    		</td>
    	    </tr>
        </table>	
        
        <Div  id= "DivLPPol" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>	
</div>
<hr>
<INPUT VALUE=" 返 回 " class=cssButton TYPE=button onclick="returnParent();"> 	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
