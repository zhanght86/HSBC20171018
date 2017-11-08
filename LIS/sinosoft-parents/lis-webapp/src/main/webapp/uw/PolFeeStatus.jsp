<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：PolFeeStatus.jsp
//程序功能：投保单交费状态查询
//创建日期：2003-07-7 11:10:36
//创建人  ：SXY
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="PolFeeStatus.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PolFeeStatusInit.jsp"%>
  <title>投保单交费状态查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./PolFeeStatusChk.jsp">
    <!-- 投保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            印刷号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" id="PrtNo" name=PrtNo >
          </TD>
          
          <TD  class= title5>
            投保单号码
          </TD>
          <TD  class= input5>
            <!--Input type=hidden class= common name=ProposalNo -->
            <Input class= "common wid" id="ProposalNo" name=ProposalNo >
          </TD>
          
        </TR>
    </table>
    </div>
    </div>
    <br>

    <INPUT VALUE="查    询" class= cssButton TYPE=button onClick="easyQueryClick();">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align =center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolFeeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
  	<p>

    <INPUT VALUE="投保单交费状态明细" class= cssButton TYPE=button onclick= "getStatus();"> 
  	</p>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 投保单交费明细
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  				<span id="spanPolFeeStatuGrid" >
  				</span> 
  			</td>
  		</tr>
    	</table>
        </Div>
        <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
