<%
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <SCRIPT src="CustomerHB.js"></SCRIPT>
  <%@include file="CustomerHBInit.jsp"%>
  <title>客户合并</title>
</head>

<body  onload="initForm(); initElementtype();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
  <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
	        <TD  class= title5>客户账户号码</TD>
	        <TD  class= input5>
				    <Input class= "common wid" name=InsuAccNo id="InsuAccNo">
	        </TD>
	        <TD  class= title5> 客户号码</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name=CustomerNo id="CustomerNo">
	        </TD>	        
        </TR>
        <TR  class= common>
	        <TD  class= title5> 客户姓名</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name=CustomerName id="CustomerName">
	        </TD>	        
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  <!-- <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();"> -->
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 客户账户信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCustomerGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none">
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onclick="getLastPage();"> 	
      </div>
    </Div> 				
  	</div>
  	<p>
  	
  	</p>
  	<table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
        </td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
  <div class="maxbox1">
    <Div  id= "divFCDay1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
	        <TD  class= title5>客户账户号码</TD>
	        <TD  class= input5>
				<Input class= "common wid" name="InsuAccNo1" id="InsuAccNo1">
	        </TD>
	        <TD  class= title5> 客户号码</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name="CustomerNo1" id="CustomerNo1" >
	        </TD>	        
        </TR>
        
  <TR  class= common>
	        <TD  class= title5> 客户姓名</TD>
	        <TD  class= input5>
	          <Input class= "common wid" name="CustomerName1" id="CustomerName1" >
	        </TD>	        
        </TR>
        
       
    </table>
  </Div>
  </div>
    <br>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick1();">查  询</a>
  <!-- <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick1();"> -->
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
        </td>
        <td class= titleImg>
           并入客户账户信息
        </td>
      </tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCustomer1Grid" >
  					</span> 
  			  	</td>
  			  </tr>
    	  </table>
        <div style= "display: none">
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onclick="getLastPage();"> 	
        </div>
    </Div>
  	<p> 
    <a href="javascript:void(0)" class=button name="signbutton" id="signbutton" onclick="autochk();">合  并</a> 
		<!-- <INPUT VALUE="合  并" class=cssButton TYPE=button name="signbutton" id="signbutton" onclick = "autochk();">  -->
  	
  	<Input class=common name=currentDate id="currentDate" type=hidden>	 
  	<Input class=common name=PolicyCom id="PolicyCom" type=hidden>	 
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
