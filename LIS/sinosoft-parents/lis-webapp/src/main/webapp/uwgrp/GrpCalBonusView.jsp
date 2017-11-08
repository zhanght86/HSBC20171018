<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：CutBonus.jsp
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./GrpCalBonusView.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./GrpCalBonusViewInit.jsp"%>
  <title>团体分红查询</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" >
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,pair);"></td>
				<td class= titleImg>请输入查询条件：</td>
			</tr>
		</table>
        <Div  id= "pair" style= "display: ''" class="maxbox">
    <table  class= common>
      <TR  class= common>
				<TD  class= title5>会计年度</TD> 
	      <TD  class= input5><Input class="wid" class=common name=FiscalYear id=FiscalYear></TD>
      	<TD  class= title5>团单号码</TD>
        <TD  class= input5><Input class="wid" class= common name=GrpContNo id=GrpContNo></TD></TR>
        <TR  class= common>
				<TD  class= title5>险种编码</TD>
        <TD  class= input5><Input class="wid" class=common name=RiskCode id=RiskCode></TD> 
        <TD  class= title5>生效起期</TD>
	        <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="有效开始日期|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>  
      </TR>
      <TR  class= common>
          
		  		<TD  class= title5>生效止期</TD>
	      	<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD  class= title5>分红状态</TD>
	        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=State id=State CodeData="0|^0|已设置^1|团单红利计算完毕^2|团单红利分配完毕^3|部分个单已计算^4|部分个单已分配" 
	        		ondblClick="showCodeListEx('State',[this],[0,1]);" onclick="showCodeListEx('State',[this],[0,1]);" onkeyup="showCodeListKeyEx('State',[this],[0,1]);"></TD>     		
			</TR>
    </table>
    </Div>
    <!--<INPUT VALUE="查     询" class=cssButton TYPE=button onclick="easyQueryClick();"> 
    <INPUT VALUE="打     印" class=cssButton TYPE=button onclick="easyPrint();"> -->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
    <a href="javascript:void(0);" class="button" onClick="easyPrint();">打    印</a>
    <br>
    <table>
    	<tr>
      	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
    		<td class= titleImg>团单分红信息</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
    	<table  class= common>
     		<tr  class= common>
    	  	<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
				</tr>
  		</table>
        <center>
      <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();"> </center>					
  	</div>
  	<p>
    <!--<INPUT VALUE="查询团单下个单" class=cssButton TYPE=button onclick="queryGrpDetail();"> -->
    <a href="javascript:void(0);" class="button" onClick="queryGrpDetail();">查询团单下个单</a>
  	<!--
    <table class= common align=center>
      <TR class= common>
        <TD  class= title>团体合计分红金额 </TD>
        <TD  class= input><Input class= common name=sumBonus></TD>
        <TD  class= input><INPUT VALUE="计算" class=common TYPE=button onclick="calSum();"> </TD>
 			</TR> 
  	</table>
  	-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
