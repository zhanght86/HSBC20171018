<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RecordQuery.jsp
//程序功能：履历查询
//创建日期：2005-06-22 11:10:36
//创建人  ：CCVIP
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //投保单号
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tContType=request.getParameter("ContType"); //传过来的保单类型：1--个单；2--团单---hp
  //GlobalInput tGI = new GlobalInput();
	//tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("RecordQuery","tContType:"+tContType);
%>
<script language="JavaScript">
  var tContNo = "<%=tContNo%>"; //投保单号
  var tPrtNo = "<%=tPrtNo%>"; //投保单号
  var tContType="<%=tContType%>"; //保单类型;
 // fm.all.('ContNo').value = tContNo;
</script>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>履历查询</title>


  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <script src="RecordQuery.js"></SCRIPT>
  
  
  <%@include file="RecordQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRecord);">
	    	</td>
	    	<td class= titleImg>
	    	 操作履历信息 
	    	</td>
	    </tr>
	  </table>    
	  
	  <div id= "divRecord" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td>
  				  <span id="spanRecordGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
       <!-- <div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
          <input class=cssButton VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
          <input class=cssButton VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
          <input class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">
        </div>-->
    </div>

    <div id="QueryButton" style="display:''">
    
    <input type="hidden" name="ContNo" value="">
    
 <!-- <table class= common align=center>
  	  
     <tr class= common>
     <td class= input align=lift> -->
   <INPUT class=cssButton VALUE="体检结果查询"   TYPE=button name="Button1" onclick="HealthQuery();">
   <INPUT class=cssButton VALUE="生调结果查询"   TYPE=button name="Button2" onclick="MeetQuery();">
   <INPUT class=cssButton VALUE="   核保查询   " TYPE=button name="Button3" onclick="UWQuery();"> 	
   <INPUT class=cssButton VALUE=" 再保回复查询 " TYPE=button name="Button4" onclick="UpReportQuery();">
   <!--INPUT class=cssButton VALUE="  特约查询  " TYPE=button onclick="ShowSpecialQuery();"-->
   <!--INPUT class=cssButton VALUE="客户合并通知书查询" TYPE=button name="Button5" onclick="KHHBNoticQuery();"-->
   <!--INPUT class=cssButton VALUE="  保额累计  " TYPE=button onclick="ShowAddAmntAccumulate();">   
   <INPUT class=cssButton VALUE="核保加费查询" TYPE=button onclick="ShowUWAddAmntQuery();"-->
   <!--INPUT class=cssButton VALUE="健康告知查询" TYPE=button onclick="ImpartQuery();"-->
   <!--INPUT class=cssButton VALUE="被保人体检资料查询" TYPE=button onclick="InsuredHealthQuery();"-->
   <!--INPUT class=cssButton VALUE="被保人健康告知查询" TYPE=button onclick="InsuredImpartQuery();"-->
   <!--INPUT class=cssButton VALUE="投保人保额累计查询" TYPE=button onclick="AmntAccumulateQuery();"-->
   <!--INPUT class=cssButton VALUE="被保人保额累计查询" TYPE=button onclick="InsuredAmntAccumulateQuery();"--> 
     
      <!-- </td>
  	  </tr> 
     <tr class= common>
     <td class= input align=lift> -->
   <!--INPUT class=cssButton VALUE="影像资料查询" TYPE=button onclick="ImageQuery();"-->
   <INPUT class=cssButton VALUE="自核提示查询"   TYPE=button name="Button6" onclick="UWErrQuery();"> 
	 <INPUT class=cssButton VALUE=" 问题件查询 "   TYPE=button name="Button7" onclick="ShowQuest();">
   <!--INPUT class=cssButton VALUE="核保通知书查询" TYPE=button name="Button8" onclick="UWNoticQuery();"-->
   <INPUT class=cssButton VALUE="  记事本查询  " TYPE=button name="Button9" onclick="ShowNotepadQuery();">
   <!-- </td>
    </tr>
	   <td class= input>-->
	 <!--<INPUT class=cssButton VALUE="返  回" TYPE=button onclick="GoBack();"> -->
    <!--   </td>
  	  </tr>   
  	</table>	-->
    </div>
    <br>
<a href="javascript:void(0);" class="button" onClick="GoBack();">返    回</a>
		<!--隐藏域-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>

</html>
