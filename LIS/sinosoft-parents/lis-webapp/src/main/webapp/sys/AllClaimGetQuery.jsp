<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2003-4-2
//创建人  ：lh
//修改人：刘岩松
//修改时间:2004-2-17
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tInsuredNo = "";
	try
	{
		tInsuredNo = request.getParameter("InsuredNo");
	}
	catch( Exception e )
	{
		tInsuredNo = "";
	}
%>
<head>
<script>
	var tInsuredNo = "<%=tInsuredNo%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="AllClaimGetQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllClaimGetQueryInit.jsp"%>

	<title>理赔查询 </title>
</head>

<body  onload="initForm();" >
  <form action="./AllClaimGetQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				被保人信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            被保人编码
          </TD>
          <TD  class= input>
            <Input class= "common wid" name=InsuredNo id=InsuredNo >
          </TD>
		   <TD  ></TD>
		   <TD  ></TD>
		</TR>
     </table>
  </Div>


    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCClaim1);">
    		</td>
    		<td class= titleImg>
    			 理赔信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCClaim1" style= "display: ''; text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class = cssButton90 TYPE=button onclick="turnPage.firstPage();">
      <INPUT VALUE="上一页" class = cssButton90 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="下一页" class = cssButton90 TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="尾页" class = cssButton90 TYPE=button onclick="turnPage.lastPage();">
  	</div>
  	  <TD class= common>
      <input class=cssButton type=button value="查询" onclick="ShowDetailInfo()">
      </TD>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


