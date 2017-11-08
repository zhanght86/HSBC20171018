<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWGErr.jsp
//程序功能：集体核保未过原因查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<%
  String tGrpContNo = "";
  tGrpContNo = request.getParameter("GrpContNo");
//  loggerDebug("UWGErr","!!!tGrpContNo:" + tGrpContNo);
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="UWGErr.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWGErrInit.jsp"%>
  <title>以往核保记录 </title>
</head>
<body  onload="initForm('<%=tGrpContNo%>');" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 以往核保记录部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 自动核保信息 
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCUWSub1" style= "display: ''" align=center>
    	<table  class= common >
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWErrGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾  页" class="cssButton93" TYPE=button onClick="getLastPage();"> 
	</div>
	<p>
        <INPUT VALUE="返  回"class="cssButton"  TYPE=button onClick="parent.close();"> 					
    </P>
    <INPUT type= "hidden" name= GrpContNo value= "">				
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
