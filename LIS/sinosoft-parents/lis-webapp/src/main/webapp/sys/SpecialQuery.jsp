<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：SpecialQuery.jsp
//程序功能：特约查询
//创建日期：2005-9-29 11:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%
	String tContNo = request.getParameter("ContNo"); 
%>

<head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="SpecialQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SpecialQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit">
  	
<table>
    	 <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpecial1);">
    		  </td>
    		  <td class= titleImg>
    			 特约信息 
    		  </td>
    	</tr>
</table>


<Div  id= "divLCSpecial1" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanSpecGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onclick="turnPage.lastPage();"> 
</div>

	  <p>
        <INPUT VALUE="返  回" class= cssButton TYPE=button onclick="parent.close();"> 					
    </P>
    <!--读取信息-->
    
    <Input type=hidden id="ContNo" name="ContNo" id=ContNo><!--合同号-->
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>




















