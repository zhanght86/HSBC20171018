<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//程序名称：UWApp.jsp
//程序功能：既往投保信息查询
//创建日期：2002-06-19 11:10:36
//创建人  ： WHN
//更新记录：  更新人  ln  更新日期  2009-01-04   更新原因/内容
%>
<%
  String tContNo = "";
  String tCustomerNo = "";
  String tType = "";
  tContNo = request.getParameter("ContNo");
  tCustomerNo = request.getParameter("CustomerNo");  
  tType = request.getParameter("type");
  loggerDebug("UWApp","ContNo:"+tContNo);
  loggerDebug("UWApp","CustomerNo:"+tCustomerNo);
  loggerDebug("UWApp","type:"+tType);
%> 
<script>
	var ContNo = "<%=tContNo%>";
	
	var tCustomerNo = "<%=tCustomerNo%>";
	var tType = <%=tType%>;	
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAppInit.jsp"%>
  <title>既往投保资料查询 </title>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tCustomerNo%>','<%=tType%>');" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
     <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
	    	</td>
	    	<td class= titleImg>
	    	 请输入相应信息 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "maxbox" class="maxbox1" >
     <table class="common">
      <tr class="common">
        <td class="title5">
          客户号
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerNo" id="CustomerNo" readonly>
        </td>
        <td class="title5">
          客户姓名
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerName" id="CustomerName" readonly>
        </td>
      </tr>
    </table>
    <!-- 既往投保信息部分（列表） -->
    <!-- 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCont);">
    		</td>
    		<td class= titleImg>
    			 既往合同信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCCont" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanContGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton TYPE=button onclick="getLastPage();">		
	</div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 既往险种保单信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPol1" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanPolGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton TYPE=button onclick="getLastPage();">
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "ProposalNoHide2" value= "">      
      <hr>					
      <INPUT VALUE="保单明细信息" class= cssButton TYPE=button onclick="showPolDetail();"> 
      <INPUT VALUE="以往核保记录" class= cssButton TYPE=button onclick="showOldUWSub();">
      <INPUT VALUE="最终核保信息" class= cssButton TYPE=button onclick="showNewUWSub();">
      <INPUT VALUE="体检资料信息" class= cssButton TYPE=button onclick="showHealthQ();">
      <INPUT VALUE="核保分析报告" class= cssButton TYPE=button onclick="showReport();">					
	</div>
	 -->
	 <!--已承保险种信息-->
	 <!-- 
	 <table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
				</td>
				<td class= titleImg>个险信息</td>
			</tr>
	</table> 
	--> 
	<div id= "divCont" style= "display: " >
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
				</td>
				<td class= titleImg>已承保保单险种信息</td>
			</tr>
		</table>    
		<div id= "divPol" style= "display: " >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display: none ">
				<input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage1.firstPage();"> 
				<input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage1.previousPage();"> 					
				<input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage1.nextPage();"> 
				<input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage1.lastPage();">
			</div>
		</div>	
		<!--已承保险种信息-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotPol);">
				</td>
				<td class= titleImg>未承保保单险种信息</td>
			</tr>
		</table>    
		<div id= "divNotPol" style= "display: ''" >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanNotPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display: ">
				<input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage2.firstPage();"> 
				<input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage2.previousPage();"> 					
				<input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage2.nextPage();"> 
				<input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage2.lastPage();">
			</div>
		</div>	
	</div>
	<!-- --------------------------------------------------- -->
		<!--团险信息-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpPol);">
				</td>
				<td class= titleImg>团险信息</td>
			</tr>
		</table>    
		<div id= "divGrpPol" style= "display: " >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanGrpPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display:  ">
				<input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage3.firstPage();"> 
				<input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage3.previousPage();"> 					
				<input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage3.nextPage();"> 
				<input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage3.lastPage();">
			</div>
		</div>	
		<hr class="line">
		<div id="Button" style="display:">
		<table  class= common>
				<tr  class= common>
					<td>
					<input class="cssButton" id="button1" name="button1" value="  保单详细信息查询  " type="button" onClick="getContDetailInfo();">
					<input class="cssButton" id="button2" name="button2" value="  影像资料查询  " type="button" onClick="showImage();">    
					<input class="cssButton" id="button3" name="button3" value="  核保分析报告查询  " type="button" onClick="showUWReport();">
					<input class="cssButton" id="button4" name="button4" value="  操作履历查询  " type="button"  onclick="QueryRecord();">
					<input class="cssButton" id="button5" name="button5" value="  核保等待查询  " type="button"  onclick="queryReason();">
					</td>
				</tr>
				<tr  class= common>
					<td>
					<input class="cssButton" id="button6" name="button6" value="  既往体检资料查询  " type="button" onClick="queryHealthReportResult();"> 
					<input class="cssButton" id="button7" name="button7" value="  既往生调资料查询  " type="button" onClick="queryRReportResult();"> 
					<input class="cssButton" id="button8" name="button8" value="  既往问题件查询  " type="button" onClick="QuestQuery();"> 
					<input class="cssButton" id="button9" name="button9" value="  再保查询  " type="button" onClick="showUpReportReply();">
					<input class="cssButton" id="button13" name="button13" value="  保单交费查询  " type="button" onClick="showTempFee();">
					<input class="cssButton" id="button10" name="button10" value="  承保结论变更查询  " disabled=true type="hidden" onClick="UWQuery();"> 	
					</td>
				</tr>
				<tr  class= common>
					<td>
				  	<input class="cssButton" id="button11" name="button11" value="  既往理赔资料查询  " type="button" onClick="queryClaim();"> 
					<input class="cssButton" id="button12" name="button12" value="  既往保全资料查询  " type="button" onClick="queryEdor();"> 
					<input class="cssButton" id="button14" name="button14" value="  客户既往理赔资料查询  " type="button" onClick="queryClaimCus();"> 
					<input class="cssButton" id="button15" name="button15" value="  客户既往保全资料查询  " type="button" onClick="queryEdorCus();">
					</td>
				</tr>
			</table>      			              	                      
	    </div>
	<p>
	    <!-- modified by liuyuxiao  2011-05-27-->
        <INPUT VALUE="返  回" class= cssButton TYPE=button onClick="parent.close();" style="display: none"> 	
        <INPUT type= "hidden" id= "InsureNoHide" value= "">
	    <INPUT type= "hidden" id= "AppntNoHide" name= "AppntNoHide" value= "">
	    <INPUT type= "hidden" id= "ContNoHide" name= "ContNoHide" value= "">	
	    <INPUT type= "hidden" id= "PrtNoHide" name= "PrtNoHide" value= "">	
	    <INPUT type= "hidden" id= "GrpContNoHide" name= "GrpContNoHide" value= "">	
	    <INPUT type= "hidden" id= "GridNoHide" name= "GridNoHide" value= "">	
	    <INPUT type= "hidden" id= "OldContNo" name= "OldContNo" value= "">	
	    <INPUT type= "hidden" id= "OldCustomerNo" name= "OldCustomerNo" value= "">	
	    <INPUT type= "hidden" id= "type" name= "type" value= "">	
    </P>
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
