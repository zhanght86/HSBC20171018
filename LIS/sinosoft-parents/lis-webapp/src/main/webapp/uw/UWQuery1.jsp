<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWQuery.jsp
//程序功能：核保查询
//创建日期：2005-6-23 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%
	String tProposalContNo = "";
	String tContType = "";
	tProposalContNo = request.getParameter("ProposalContNo"); 
	tContType = request.getParameter("ContType"); 
	session.putValue("ProposalContNo",tProposalContNo);
%>
<html>
<%
  //个人下个人
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ProposalContNo = "<%=tProposalContNo%>";          //个人单的查询条件.
	var ContType = "<%=tContType%>";          //保单类型:"1"个单；"2"集体单
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
  <SCRIPT src="UWQuery1.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWQueryInit1.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
  	
    <!--合同信息-->
	<DIV id=DivLCContButton >
	<table id="table1">
			<tr>
				<td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
				</td>
				<td class="titleImg">合同信息
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLCCont" class="maxbox1" >
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>投保单号码 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id="ContNo" VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>管理机构 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id="ManageCom"  MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title" nowrap>销售渠道 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" id="SaleChnl" CLASS="readonly wid" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">业务员编码 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="AgentCode" id="AgentCode" MAXLENGTH="10" CLASS="readonly wid" id="readonly" readonly>
	    		</td>
				<td CLASS="title">其它声明 
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="Remark" id="Remark" CLASS="readonly wid" readonly MAXLENGTH="255">
	    		</td>	    		
			</tr>
		</table>
</div>
		
		 </DIV>
          <INPUT class=cssButton VALUE="核保轨迹查询" TYPE=button onClick="QueryContTrace();">     
      
     <DIV id=DivLCContInfo STYLE="display:''"> 
	
	

<!--被保人信息-->   
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 被保人信息:
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divLCPol2" >
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

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
    		</td>
    		<td class= titleImg>
    			 险种信息:
    		</td>
    	    </tr>
        </table>	
        
        <Div  id= "DivLCPol" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>	
  	 
          
 </form>
 <br><br><br><br><br>
   <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
        
</body>
</html>
