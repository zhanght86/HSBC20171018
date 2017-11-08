<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<head>
<%@page import="com.sinosoft.print.func.*" %>
<%@page import="com.sinosoft.productdef.*" %>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="./PDTRiskTestInput.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>

<%
String tRiskCode = request.getParameter("RiskCode");
System.out.println(tRiskCode);
//RiskTestService tTest = new RiskTestService(request); 
RiskTestService tTest = new RiskTestService(tRiskCode);
String tAppntString = tTest.initAppntScript();
String tInsuredString = tTest.initInsuredScript();
String tRiskString = tTest.initRiskScript();
String tPayString = tTest.initPayScript();
String tFuncString = tTest.initFuncScript();

%>


<script type="text/javascript">
	var turnPage = new turnPageClass();
	<%=tFuncString%>
</script>


<body onload="initForm();">

<form action="./PDTRiskTestSave.jsp" method=post name=fm
	target="fraSubmit">
<table class=common>
         <tr class=common>
            <TD  class= title>ÏÕÖÖ±àÂë</TD>
            <TD  class= input>
				<Input class=readonly readonly="readonly" name=RiskCode value=<%=tRiskCode%>></Input>
			</TD>
			 <TD  class= title></TD>
            <TD nowrap class= input>
				<Input class=readonly readonly="readonly"></Input>
			</TD>
			 <TD  class= title></TD>
            <TD  class= input>
				<Input class=readonly readonly="readonly" ></Input>
			</TD>
		</tr>
</table>	

<br/>

<hr/>	
<%=tAppntString%>
<hr>
<%=tInsuredString%>
<hr>
<%=tRiskString%>
<hr>

<%=tPayString%>
<hr>
<table class=common>
       <tr  class=common>
          <td class=title5>
            <input value="²âÊÔ"  type=button class=cssbutton   onclick="return testRisk();">
          </td>
          
       </tr>	
       <tr  class=common>
       <td class=title5>
            <input value="·µ»Ø"  type=button class=cssbutton   onclick="returnRisk();">
          </td>
         </tr>	  
 </table>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
