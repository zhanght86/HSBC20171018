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
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="ContInsuredQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ContInsuredQueryInit.jsp"%>
	
	<title>被保人查询 </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id="fm" >
	<Div  id= "divLCPol1" style= "display: none;margin-top:10px">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            保单号码
          </TD>
          <TD  class= input>
            <Input class="common wid" readonly name=ContNo id="ContNo" >
          </TD>
           <TD  class= title>
            险种号码
          </TD>
          <TD  class= input>
          	<Input class= "common wid" readonly name=PolNo id="PolNo" >
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
    			 被保人信息
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
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage.previousPage();">
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onClick="turnPage.lastPage();">
<Br>
      <table class= common >
      <tr>
        <td>
          <INPUT VALUE="被保人已承保保单查询" class=cssButton TYPE=button name="Button1" onClick="queryProposal();">
          <INPUT VALUE="被保人未承保保单查询" class=cssButton TYPE=button name="Button2" onClick="queryNotProposal();">
          <INPUT VALUE=" 被保人既往保全查询 " class=cssButton TYPE=button name="Button3" onClick="queryEdor()">
          <INPUT VALUE=" 被保人既往理赔查询 " class=cssButton TYPE=button name="Button4" onClick="queryClaim();">
          <INPUT VALUE="   被保人保额累计   " class=cssButton TYPE=hidden name="Button5" onClick="amntAccumulate();">
              
        </td>
      </tr>
    	<tr class= common>
    	  <td class= input align=lift>
        <INPUT class=cssButton VALUE=" 被保人健康告知查询 " TYPE=button name="Button6" onClick="queryHealthImpart()">
    		<INPUT class=cssButton VALUE="      加费信息      " TYPE=button name="Button7" onClick="AddPremQuery();">
    		<INPUT class=cssButton VALUE="      特约信息      " TYPE=button name="Button8" onClick="SpecQuery();">
    	  </td>
    	</tr>
    	<tr class= common>
    	  <td class= input align=lift>
    		<INPUT class=cssButton VALUE="        返回        " TYPE=button onClick="returnParent();">
    	  </td>
    	</tr>
      </table>
    </Div>
  	
  </form>
  <br><br><br><br><br>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


