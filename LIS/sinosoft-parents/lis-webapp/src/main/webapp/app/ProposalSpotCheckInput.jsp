<html>
<%
//name :ProposalSpotCheckInput.jsp
//Creator :张征
//date :2007-07-26
//保存复核抽检规则
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.certify.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");
    String Branch =tGlobalInput.ComCode;
    loggerDebug("ProposalSpotCheckInput","从页面获得登陆机构是"+Branch);
%>
	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src = "ProposalSpotCheckInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="ProposalSpotCheckInit.jsp"%>
	</head>
	
	<body  onload="initForm();" >
    <form action="./ProposalSpotCheckSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  <table class=common>
	  <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
      </td>
  		<td class=titleImg>复核抽查规则(抽查比率只能录入0至100之间的整数)</td>
  	</tr>
  </table>
  <div class="maxbox">
  <Div  id= "divFCDay" style= "display: ''">
   	<Table class=common>  
   	   <TR class=common>
   			<TD class= title5>外包方编码</TD>
   			<TD class= input5><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name="BussNo" id="BussNo"  readonly
            onclick="return showCodeList('queryBPOID',[this],null,null,'1 and bussnotype=#TB#','1');" ondblclick="return showCodeList('queryBPOID',[this],null,null,'1 and bussnotype=#TB#','1');" 
            onkeyup="return showCodeListKey('queryBPOID',[this],null,null,'1 and bussnotype=#TB#','1');"></TD>
        <TD  class= title5>险种编码</TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name=RiskCode id="RiskCode" onclick="return showCodeList('riskcode',[this]);" ondblclick="return showCodeList('riskcode',[this]);" onkeyup="return showCodeListKey('riskcode',[this]);">
        </TD>
   	   </TR>   		
       <TR class= common>
		    <TD class= title5>抽查比率</TD>
        <TD class= input5><Input class= "common wid" name=checkRate id="checkRate"></TD>
		    <td class= title5>抽查上限</td>
        <td class= input5><input class= "common wid" name=checkMax id="checkMax"></td>
       </TR>        
       <tr class= common>   
        <TD  class= title5 >管理机构</TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= code  name=ManageCom id="ManageCom" onclick="return showCodeList('comcode',[this]);" ondblclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);">
        </TD>
       </tr>
    </table>
    <table>
      <TR class= common>
        <TD class= title5 style="width:4.2%;">备注</TD>
        <TD class= input5><textarea name="Remark" cols="145%" rows="4" witdh=100% class="common"></textarea></TD>
      </TR>
 		</table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="submitForm();">保  存</a>
  <!-- <INPUT  VALUE="保存" TYPE=button onclick="submitForm();"> -->
  <Input class= common type= hidden name=OperateType id="OperateType">
   </form>
   <br>
   <br>
   <br>
   <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
