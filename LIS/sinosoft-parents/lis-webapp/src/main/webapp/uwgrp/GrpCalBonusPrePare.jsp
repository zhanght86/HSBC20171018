<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
	
	int curYear = Integer.parseInt(PubFun.getCurrentDate().substring(0,4)) - 1;
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpCalBonusPrePare.js"></SCRIPT>
  <%@include file="GrpCalBonusPrePareInit.jsp"%>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>分红处理</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action= "./GrpCalBonusPrePareChk.jsp">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,moreq);"></td>
        <td class= titleImg>请输入查询条件：</td></tr>
		</table>
        <Div  id= "moreq" style= "display: ''" class="maxbox1">
    <table  class= common>
				<TR  class= common>
          <TD  class= title5>会计年度</TD> 
	      	<TD  class= input5><Input class="wid" class=common name=FiscalYear id=FiscalYear value=<%=curYear%>  verify="会计年度|NOTNULL"></TD>
					<TD  class= title5>团单号</TD>
	        <TD  class= input5><Input class="wid" class=common name=GrpContNo id=GrpContNo ></TD>
            </TR>
           <TR  class= common> 
          <TD  class= title5>险种编码</TD>
          <TD  class= input5><Input class="wid" class=common name=RiskCode id=RiskCode value='212401' verify="险种编码|NOTNULL"></TD>
          <TD  class= title5>生效起期</TD>
	        <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="有效开始日期|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
				</TR> 	 		  
        <TR  class= common>
            
		  		<TD  class= title5>生效止期</TD>
	      	<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD  class= title5>分红状态</TD>
	        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=State id=State CodeData="0|^0|参数已设置^3|团单部分红利计算^4|团单部分红利分配" 
	        		ondblClick="showCodeListEx('State',[this],[0,1]);" onclick="showCodeListEx('State',[this],[0,1]);" onkeyup="showCodeListKeyEx('State',[this],[0,1]);"></TD>     		
        </TR>
    </table>
		</Div>
    <!--<INPUT VALUE="查    询" type =button class=cssButton onclick="easyQueryClick();"> 	
		<INPUT VALUE="保存修改" type =button class=cssButton onclick="submitForm();" name='update'> --><br>
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
        <a href="javascript:void(0);" class="button" onclick="submitForm();" name='update'>保存修改</a><br><br><br>
		<p>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
    	<table  class= common>
     		<tr  class= common>
    	  	<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
				</tr>
  		</table>
      <INPUT VALUE="首页" type =button class=cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" type =button class=cssButton91 onclick="getPreviousPage();">
      <INPUT VALUE="下一页" type =button class=cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="尾页" type =button class=cssButton93 onclick="getLastPage();"> 					
  	</div>      
		<p>
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,kop);"></td>
        <td class= titleImg>请输入计算参数：</td></tr>
		</table>
        <Div  id= "kop" style= "display: ''" class="maxbox">
    <table  class= common>
				<TR  class= common>
		  		<TD  class= title5>实际投资收益率</TD>
	      	<TD  class= input5><Input class="wid" class=common name=ActuRate id=ActuRate value="0.035" ></TD>
					<TD  class= title5>保证年收益率</TD>
	        <TD  class= input5><Input class="wid" class=common name=EnsuRateDefault id=EnsuRateDefault value="0.025"></TD></TR>
                <TR  class= common> 		
					<TD  class= title5>分红比例</TD>
	        <TD  class= input5><Input class="wid" class=common name=AssignRate id=EnsuRateDefault value="0.7"></TD>     		
				</TR>
    </table>
    </Div>
			<input type=hidden id="fmtransact" name="fmtransact">
			<!--<INPUT VALUE="全部保存" type =button class=cssButton onclick="saveAll();" name='insAll'> 
			<INPUT VALUE="全部修改" type =button class=cssButton onclick="updateAll();" name='updAll'>--><br>
            <a href="javascript:void(0);" class="button" onclick="saveAll();" name='insAll'>全部保存</a>
            <a href="javascript:void(0);" class="button" onclick="updateAll();" name='updAll'>全部修改</a> 
<!--	    <INPUT VALUE="删除参数" class=common TYPE=button  onclick = "deleteParm();">  -->

    <table>
      <tr>
					<TD  class= titleImg>注意：分红状态为空进行查询时，是对未进行参数设置的保单进行筛选！</TD>
      </tr>
    </table> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
