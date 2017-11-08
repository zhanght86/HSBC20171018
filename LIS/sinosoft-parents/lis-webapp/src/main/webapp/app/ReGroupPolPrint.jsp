<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%

GlobalInput tGI = new GlobalInput();
tGI= (GlobalInput)session.getValue("GI");

	String tContNo = "";
	try
	{
		tContNo = request.getParameter( "ContNo" );
		
		//默认情况下为集体投保单
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "00000000000000000000";
	}
	catch( Exception e1 )
	{
		tContNo = "00000000000000000000";
	}
%>
<script>
	var contNo = "<%=tContNo%>";  //个人单的查询条件.
	ComCode="<%=tGI.ComCode%>";   //记录登陆机构 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ReGroupPolPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ReGroupPolPrintInit.jsp"%>
  <title>重打团单 </title>
</head>
<body  onload="initForm();" >
  <form action="./ReGroupPolPrintSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：</td>
		  </tr>
	</table>
  <div class="maxbox">
  <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>集体保单号码</TD>
          <TD  class= input5><Input class= "common wid" name=GrpContNo id="GrpContNo"></TD>
          <TD  class= title5>印刷号码</TD>
          <TD  class= input5><Input class= "common wid" name=PrtNo id="PrtNo"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>主险险种编码</TD>
          <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" type="text" class="codeno" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);"><input class="codename" name="RiskCodeName" id="RiskCodeName"> </TD>
          <TD  class= title5>险种版本</TD>
          <td class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  type="text" class= "code" CodeData="" name=RiskVersion id="RiskVersion" onclick="showCodeListEx('RiskVer', [this]);" ondblclick="showCodeListEx('RiskVer', [this]);" onkeyup="showCodeListKeyEx('RiskVer', [this]);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>代理人组别</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" type="text" class="codeno"  name=AgentGroup id="AgentGroup" onclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" ondblclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" onkeyup="return showCodeListKey('AgentGroup',[this,AgentGroupName],[0,1]);"><input class="codename" name="AgentGroupName" id="AgentGroupName"> 
          </TD>
          <TD  class= title5>代理人编码</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" MAXLENGTH=10 name=AgentCode id="AgentCode" onclick="return queryAgent(ComCode);" ondblclick="return queryAgent(ComCode);" onkeyup="return queryAgent2();">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>管理机构</TD>
          <TD class= input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" type="text" class="codeno" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName"> </TD>
          <TD  class= title5>投保人姓名 </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpName id="GrpName">
          </TD>
        </TR>
    </table>
  </Div>
  </div>
  <div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查询保单</a>
  </div>
  <br>
  <!-- <INPUT VALUE=" 查询保单 " class="cssButton" TYPE=button  onclick="easyQueryClick();"> -->
</form>
<form action="./ReGroupPolPrintSave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit">
	<!-- <input value=" 提交申请 " class="cssButton" TYPE=button  onclick="printGroupPol();"> -->
	<!-- <input VALUE="yes" name = "" type="checkbox" onclick="dataConfirm(this);">      		
      		重新生成打印数据  --> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 合同信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<input type=hidden id="fmtransact" name="fmtransact">
    <div>
    <a href="javascript:void(0)" class=button onclick="printGroupPol();">提交申请</a>
    <input VALUE="yes" name = "" type="checkbox" onclick="dataConfirm(this);">          
          重新生成打印数据  
  </div>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var codeRiskSql = "1  and RiskProp in (#G#,#A#,#B#,#D#)";
</script>
