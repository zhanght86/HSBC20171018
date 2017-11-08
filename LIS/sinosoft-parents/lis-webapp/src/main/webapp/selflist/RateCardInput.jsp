<html>
<%
//name :RateCardInput.jsp
//Creator :zz
//date :2008-06-19
//卡单费率表定义菜单
//
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
    loggerDebug("RateCardInput","从页面获得登陆机构是"+Branch);
%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src = "RateCardInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="RateCardInit.jsp"%>
	</head>

<body  onload="initForm();" >
  <form action="./RateCardSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <Table class= common>
	     <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	         <td class=titleImg>卡单费率定义(一个险种的一种保费对应一种产品形态)</td>
	     </tr>
	  </Table>
    <div class="maxbox1"> 
      <Div  id= "divFCDay" style= "display: ''">
   	    <Table class= common>
   		    <TR class= common>
   			    <TD class= "title5">险种编码</TD>
   			    <td class= "input5">
   				    <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=Riskcode id="Riskcode" readonly CodeData="0|^141814|MS交通工具意外伤害保险|^141815|MS短期综合意外伤害保险|" onClick="return showCodeListEx('Riskcode',[this]);" onDblClick="return showCodeListEx('Riskcode',[this]);" onKeyUp="return showCodeListKeyEx('Riskcode',[this]);" >
				    </td>
            <td class= "title5">产品计划</td>
            <td class= "input5">
        	    <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ProductPlan id="ProductPlan" readonly CodeData="0|^1|1期|^2|2期|" onClick="return showCodeListEx('ProductPlan',[this]);" onDblClick="return showCodeListEx('ProductPlan',[this]);" onKeyUp="return showCodeListKeyEx('ProductPlan',[this]);">
            </td>
   		    </TR>
          <TR class= common>
				    <TD class= "title5">保险年期</TD>
            <TD class= "input5"><Input class= "common wid" name=InsuYear id="InsuYear" ></TD>	
	    	    <td class= "title5">保险年期单位</td>
            <td class= "input5">
        	    <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=InsuYearFlag id="InsuYearFlag" readonly CodeData="0|^Y|年|^M|月|^D|日|^A|岁|" onClick="return showCodeListEx('RelationToLCInsured',[this]);" onDblClick="return showCodeListEx('RelationToLCInsured',[this]);" onKeyUp="return showCodeListKeyEx('RelationToLCInsured',[this]);">
            </td>        
          </TR>     
          <TR class= common>      	
		        <TD class= "title5">保费</TD>
            <TD class= "input5"><Input class= "common wid" name= Prem  id="Prem"></TD>
           
            <TD class= "title5">份数</TD>
            <TD class= "input5"><Input class= "common wid" name= Mult id="Mult" value="1"></TD>
          </TR>     
        </Table>
      </Div>
    </div>
</form>
<%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
