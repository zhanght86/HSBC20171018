<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-12-24 11:10:36
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tContNo = "";
	String tPolNo = "";
	String tIsCancelPolFlag = "";
	String tPrtNo = "";
	try
	{
		//tContNo = request.getParameter("ContNo");
		//tPolNo = request.getParameter("PolNo");
		//tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
		tPrtNo = request.getParameter("prtNo");
	}
	catch( Exception e )
	{
		tContNo = "";
		tPolNo = "";
		tIsCancelPolFlag = "";
		tPrtNo = "";
	}
%>
<head>
<script> 
	var tPrtNo = "<%=tPrtNo%>";
	//alert(tPrtNo);
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="PolRiskQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolRiskQueryInit.jsp"%>
	
	<title>保单险种查询 </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id=fm >
	<Div  id= "divLCPol1" style= "display: none">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            保单号码
          </TD>
          <TD  class= input>
            <Input class= "common wid" readonly name=ContNo id=ContNo >
          </TD>
           <TD  class= title>
            险种号码
          </TD>
          <TD  class= input>
          	<Input class= "common wid" readonly name=PolNo id=PolNo >
          </TD>
          <TD  class= title>
          </TD>
          <TD  class= input> 
          </TD>
          
		</TR>
     </table>
  </Div>
  
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPrem1);">
    		</td>
    		<td class= titleImg>
    			 险种信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPrem1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>			
  	</div>
  	
  	<Div id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
      <table class= common align=center>
    	<tr class= common>
    	  <td class= input align=lift>
    		<INPUT class=cssButton VALUE=" 保费项信息 " TYPE=button onclick="PremQuery();">
    		<INPUT class=cssButton VALUE=" 返回 " TYPE=button onclick="returnParent();">
    	  </td>
    	</tr>
      </table>
    </Div>
  	
  </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


