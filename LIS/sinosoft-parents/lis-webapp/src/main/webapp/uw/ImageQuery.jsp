<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ImageQuery.jsp
//程序功能：影像资料查询
//创建日期：2005-07-07 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%
	String tContNo = "";
	tContNo = request.getParameter("ContNo"); 
	session.putValue("ContNo",tContNo);
%>
<html>
<%
  //个人下个人
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";
	var ttContNo = "<%=request.getParameter("ContNo")%>";          
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
  <SCRIPT src="ImageQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ImageQueryInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm();" >
  <form action="../common/EasyScanQuery/EasyScanQuery.jsp" method=post name=fm id=fm target="fraPic">
  	 
	<div id="DivLCCont"  class="maxbox1">
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>印刷号 
	    	</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id="ContNo" VALUE CLASS="readonly wid" readonly= TABINDEX="-1" MAXLENGTH="14">
	    	</td>
	    	 
         <td class=title>
           影像类别 
         </td>
	    	<td class=input>
         <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=subtype id=subtype ondblclick="return showCodeList('imagetype',[this,subtypname],[0,1]);" onclick="return showCodeList('imagetype',[this,subtypname],[0,1]);" onkeyup="return showCodeListKey('Imagetype',[this,subtypname],[0,1]);" ><Input class="codename" name=subtypname id=subtypname readonly elementtype=nacessary>
         </td> 		
         <td class=title>
           扫描批次号
         </td>
	    	<td class=input>
         <Input class="common wid" name="ScanNo" id="ScanNo">
         </td> 		
			</tr>	  
		</table>
		
		 </DIV> 
	    <INPUT class=cssButton VALUE="查   询" TYPE=button onclick="QueryImage();"> 
	 
<!--影像信息-->   
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 影像资料信息:
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divLCPol2" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanImageGrid">
  					</span> 
  			  	</td>
  			</tr> 
    	</table> 
         	<div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
        </div>				
    </Div>
    
    
     <div  id= "HiddenValue" style="display:none;float: right">
			<input type="hidden" id="DocID" name="DocID" value= ""> 
			<input type="hidden" id="QueryType" name="QueryType"  value="0">   
    </div>         
 
           
  </form> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
  
