<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
		GlobalInput tGI = (GlobalInput)session.getValue("GI");
		String comcode = tGI.ComCode;
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  

  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 

  <SCRIPT src="LFGetNotice.js"></SCRIPT>
  <%@include file="LFGetNoticeInit.jsp"%>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>打印领款清单 </title> 
</head>
<body  onload="initForm();" >
  <form  method=post name=fm id="fm" target="f1print">
		<!-- 投保单信息部分 -->
		<table class= common border=0 width=100%>
			<tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,inshow);"></td>
				<td class= titleImg>请输入查询条件：</td>
			</tr>
		</table>
        <Div  id= "inshow" style= "display: ''" class="maxbox1" >
      <table  class= common>
    <TR  class= common>
    
      <TD  class= title5>
        团体保单号
      </TD>
      <TD  class= input5>
        <Input class=wid name=GrpContNo id=GrpContNo >
      </TD>
     <TD  class= title5></TD>
	 <TD  class= input5></TD>
    </TR>
  </table> 
  </div>
    <!--<INPUT class="cssButton" VALUE=" 查   询 " TYPE=button onclick="easyQueryClick();">-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>

  	<input type=hidden id="PrtSeq" name="PrtSeq" id="PrtSeq">
    
    <table>
			<tr>
					<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
					<td class= titleImg>领取信息</td>
			</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" >
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
							<span id="spanPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<INPUT VALUE=" 首 页 " class= cssButton90 TYPE=button onclick="getFirstPage();"> 
			<INPUT VALUE="上一页 " class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
			<INPUT VALUE="下一页 " class= cssButton92 TYPE=button onclick="getNextPage();"> 
			<INPUT VALUE=" 尾 页 " class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
  	
      <!--<INPUT VALUE="打印领款清单" class= cssButton TYPE=button onclick="printPol();">-->
      <a href="javascript:void(0);" class="button" onClick="printPol();">打印领款清单</a> 
  	</DIV>
  	</p> 
  	
<div id="divGetDrawInfo" style= "display:none">
	     <table>
         
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGetDrawButton);">
        </td>
        <td class= titleImg>
          领取记录明细
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLFGetGrid" > </span> 
        </td>
      </tr>	
    </table>
      <Div id= "divLCGetDrawButton" style= "display: ''" align="center">
    	
        <INPUT VALUE="首  页" class="cssButton90" type="button" onclick="turnPage1.firstPage();"> 
				<INPUT VALUE="上一页" class="cssButton91" type="button" onclick="turnPage1.previousPage();"> 					
				<INPUT VALUE="下一页" class="cssButton92" type="button" onclick="turnPage1.nextPage();"> 
				<INPUT VALUE="尾  页" class="cssButton93" type="button" onclick="turnPage1.lastPage();">  			

  </div> 
</div> 	
  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=comcode%>+"%#";
</script>
