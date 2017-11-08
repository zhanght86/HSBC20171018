<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  	//个人下个人
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script type="text/javascript">
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
  <script src="../common/javascript/Common.js" type="text/javascript"></script>
  <script src="../common/javascript/MulLine.js" type="text/javascript"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js" type="text/javascript"></script>
  <script src="../common/easyQueryVer3/EasyQueryCache.js" type="text/javascript"></script>
  <script src="../common/cvar/CCodeOperate.js" type=""></script>
  <script type="text/javascript" src="Receipt.js"></script>
  <LINK href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <script src="../common/laydate/laydate.js"></script>
  <%@include file="./ReceiptInit.jsp"%>
  <title>保单状态查询 </title>
</head>
<body  onload="initForm();" >
  <form method="post" name="fm" id="fm" target="fraSubmit" action="">
  <input type="hidden" id="fmAction" name="fmAction">
    <table class="common" border="0" width="100%">
    	<tr>
        <td class="common">
          <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,divFCDay);"/>
        </td>
			  <td class="titleImg" align="center">请输入查询条件：</td>
		  </tr>
	  </table>
    <div class="maxbox1">
    <div  id="divFCDay" style= "display: ''"> 
    <table  class="common" align="center">
      	<tr  class="common">
          <td  class="title5">印刷号</td>
          <td  class="input5">
            <Input class="common wid" name="ProposalContNo" id="ProposalContNo"/>
          </td>
		      <td class="title5">签单日期</td>
		      <td class="input5"><input name="ValidStartDate" id="ValidStartDate" class="common wid" onclick="laydate({elem: '#ValidStartDate'});" verify="有效开始日期|DATE" dateFormat="short" /><span class="icon"><a onclick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
          <input type= "hidden" class="common" name="PrtNo" id="PrtNo" />
        </tr>
    </table>
  </div>
  </div>
  <hr class="line"/>
   <input value="查  询" class="cssButton" type="button" onclick="easyQueryClick();"/>
    <table>
    	<tr>
        	<td class="common">
			    <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick= "showPage(this,divLCPol1);"/>
    		</td>
    		<td class="titleImg">
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<div  id= "divLCPol1" style= "display: ''" align="center">
      	<table  class="common">
       		<tr  class="common">
      	  		<td text-align: left colspan="1">
  					<span id="spanPolGrid"></span>
  			  	</td>
  			</tr>
    	</table>
      <input value="首  页"  	class="cssButton90" type="button" onclick="getFirstPage();"/>
      <input value="上一页" 	class="cssButton91" type="button" onclick="getPreviousPage();"/>
      <input value="下一页" 	class="cssButton92" type="button" onclick="getNextPage();"/>
      <input value="尾  页"  	class="cssButton93" type="button" onclick="getLastPage();"/>
  	</div>
  	<table class="common" border="0" width="100%">
    	<tr>
        <td class="common">
          <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick= "showPage(this,divFCDay1);"/>
        </td>
			  <td class="titleImg" align="center">回销日期更新：</td>
		  </tr>
	  </table>
    <div class="maxbox1">
    <div  id= "divFCDay1" style= "display: ''"> 
    <table  class="common" align="center">
      	<tr>
		      <td>回销日期</td>
		      <td><input name="ValidStartDate1" id="ValidStartDate1" length="20" class="common wid" style=" float:left;width:200px; height:26px;" onClick="laydate({elem: '#ValidStartDate1'});" verify="有效开始日期|DATE" dateFormat="short" ><span class="icon"><a onClick="laydate({elem: '#ValidStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
          <input type= "hidden" class="common" name="PrtNo" id="PrtNo" />
        </tr>
        <tr>
		      <td ></td>
		      <td ></td>
          <input type= "hidden" class="common" name="PrtNo" id="PrtNo" />
        </tr>
    </table>
  </div>
  </div>
  <hr class="line"/>
  	<div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class="common">
       		<tr  class="common">
      	  		<td text-align: left colspan="1">
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
  	
       <!-- <INPUT VALUE="保单状态明细" class= cssButton TYPE=button onclick= "getStatus();"> -->
       	<%--更新数据 --%>
       <input value="更  新" style="float:left;" class="cssButton" type="button" onclick= "submitForm();">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/><br/><br/><br/><br/>
</body>
</html>
