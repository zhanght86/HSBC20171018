
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<html> 
<%
  
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var ComCode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="CountPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CountPrintInit.jsp"%>
  <title>打印保单 </title>
</head>
<body  onload="initForm();" >
  <form  action = './CountPrintSave.jsp' method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入保单查询条件：</td>
		  </tr>
	</table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>    销售渠道  </TD>
          <TD  class= input5> 	<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=SaleChnl verify="销售渠道|code:SaleChnl" onclick="return showCodeList('SaleChnl',[this]);" ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);"> </TD>
          <TD  class= title5>  合同号   </TD>
          <TD  class= input5>  <Input class= "common wid" name=ContNo > </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>  印刷号码 </TD>
          <TD  class= input5>  <Input class= "common wid" name=PrtNo >  </TD>
          <TD  class= title5>   险种编码     </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=RiskCode onClick="return showCodeList('RiskCode',[this]);" onDblClick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">  </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5> 管理机构  </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"><font size=1 color="#ff0000"><b>*</b></font>  </TD>
          <TD  class= title5> 代理人编码  </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=AgentCode onclick="return queryAgent(ComCode);" ondblclick="return queryAgent(ComCode);" onkeyup="return queryAgent(ComCode);"></TD> 
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查询保单</a>
  <a href="javascript:void(0)" class=button onclick="printPol();">打印保单</a>
          <!-- <INPUT VALUE="查询保单" class= cssButton TYPE=button onclick="easyQueryClick();"> 
      	  <INPUT VALUE="打印保单" class= cssButton TYPE=button onclick="printPol();"> --> 
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
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾页" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
	<input type=hidden name="EasyPrintFlag" value="1">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
