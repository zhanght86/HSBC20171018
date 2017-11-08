
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tDisplay = "";
	try
	{
		tDisplay = request.getParameter("display");
	}
	catch( Exception e )
	{
		tDisplay = "";
	}
%> 

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
   loggerDebug("PolModifyQuery","管理机构-----"+tG.ComCode);
%>   

<script>
  var comCode = <%=tG.ComCode%>
  var RiskSql = "1 and subriskFlag =#M# ";
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="PolModifyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="PolModifyQueryInit.jsp"%>
  
  <title>保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>请输入个人保单查询条件：</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title>保单号码 </TD>
          <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo > </TD>
          <TD  class= title> 集体保单号码</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= "code" name=GrpContNo id=GrpContNo CodeData="0|^00000000000000000000|非集体单下的个人单" ondblclick="showCodeListEx('GrpPolNo',[this],[0]);" onclick="showCodeListEx('GrpPolNo',[this],[0]);" onkeyup="showCodeListKeyEx('GrpPolNo',[this],[0]);"> </TD><TD  class= title> 印刷号码</TD>
          <TD  class= input> <Input class="wid" class= common name=PrtNo id=PrtNo ></TD></TR>
          <TR  class= common>
          
          <TD  class= title> 管理机构 </TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=ManageCom id=ManageCom ondblclick="return showCodeList('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');" onclick="return showCodeList('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');" onkeyup="return showCodeListKey('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');"> </TD>
           <td  class= title> 险种编码 </td>
          <td  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onkeyup="return showCodeListKey('RiskCode',[this],null,null,RiskSql,'1',1);"></td>
          <TD  class= title> 代理人编码</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=AgentCode id=AgentCode verify="代理人编码|notnull&code:AgentCode" ondblclick="return queryAgent(comCode);" onclick="return queryAgent(comCode);" onkeyup="return queryAgent(comCode);"></TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title> 投保人姓名 </TD>
          <TD  class= input> <Input class="wid" class= common name=AppntName id=AppntName > </TD>
          <TD  class= title> 被保人客户号 </TD>
          <TD  class= input><Input class="wid" class= common name=InsuredNo id=InsuredNo ></TD><TD  class= title> 被保人姓名 </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredName id=InsuredName > </TD></TR>
         
        <!-- <TR  class= common>
          <TD  class= title> 被保人出生日期 </TD>
          <TD  class= input><Input class= common name=InsuredBirthday ></TD>
          <TD  class= title> 保单生效日:起始 </TD>
          <TD  class= input> <Input class="coolDatePicker" dateFormat="short" name=StartCValiDate > </TD>
          <TD  class= title> 保单生效日:终止</TD>
          <TD  class= input> <Input class="coolDatePicker" dateFormat="short" name=EndCValiDate > </TD>
        </TR> -->
    </table></Div>
          <INPUT VALUE=" 查  询 " class = cssButton TYPE=button onclick="easyQueryClick();">
          <INPUT VALUE=" 进入保单修改 " class = cssButton TYPE=button onclick="PolClick();">
          <INPUT VALUE=" 返  回 " name=Return class = cssButton TYPE=button STYLE="display:none" onclick="returnParent();">
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
<center>
      <INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
