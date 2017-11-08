<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：BonusQueryInit.jsp
//程序功能：
//创建日期：2005-08-10
//创建人  ：Howie
//更新记录：  更新人   更新日期     更新原因/内容
%>
<%
	String tContNo = "";
	String tPolNo = "";
	String tIsCancelPolFlag = "";
	try
	{
		tContNo = request.getParameter("ContNo");
		tPolNo = request.getParameter("PolNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
	
		tContNo = "";
		tPolNo = "";
		tIsCancelPolFlag = "";
	}
%>
<head>
<script> 
	var tContNo = "<%=tContNo%>";
	var tPolNo = "<%=tPolNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <!--deleted by liurx :turnPage object will be abated if the file below was loaded.-->
  <!--<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>-->
  <SCRIPT src="BonusQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./BonusQueryInit.jsp"%>
	<title>红利查询 </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id="fm" >
  <table>
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				保单信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            保单号码
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ContNo id="ContNo">
          </TD>
          <TD  class= title>险种号码
          </TD>
          <TD  class= input>
            <input class= "readonly wid" readonly name=PolNo id="PolNo">
          </TD>
          <TD  class= title>
          </TD>
          <TD  class= input>
          </TD>
		</TR>
     </table>
  </Div>
     <!--INPUT VALUE=" 查 询 " class=cssButton TYPE=button OnClick="QueryClick();" --> 
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGet1);">
    		</td>
    		<td class= titleImg>
    			 分红信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCGet1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    
    <Div  id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onClick="turnPage.lastPage();">
    </Div>	
  	</div>
  	<INPUT class=cssButton VALUE=" 返回 " TYPE=button onClick="returnParent();">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


