<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：TempFeeWithdrawInput.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	//登陆信息
	GlobalInput tGI = new GlobalInput();
	if (session.getValue("GI")==null)
	{
		loggerDebug("TempFeeWithdrawInput","null");
	}
	else
	{
		tGI = (GlobalInput)session.getValue("GI");
	}
	
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" type="text/javascript" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="TempFeeWithdrawInput.js"></SCRIPT>
  <%@include file="TempFeeWithdrawInit.jsp"%>
  
  <title>暂交费信息 </title>
</head>

<body  onload="initForm();" >
  <form action="./TempFeeWithdrawSave.jsp" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 暂交费信息部分 fraSubmit-->
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
    <table  class= common>
    <TR  class= common>
      <TD  class= title5>收费号码</TD>
      <TD  class= input5><Input class= "common wid" name=TempFeeNo id="TempFeeNo"></TD>
      <TD  class= title5>印刷号/合同号</TD>
      <TD  class= input5><Input class= "common wid" name=PrtNo id="PrtNo"></TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>管理机构</TD>
      <TD  class= input5>
      	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="管理机构|code:station" onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input class=codename name=ManageComName id="ManageComName" readonly=true>
      </TD>      	
      <TD  class= title5>交费日期</TD>
      <TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="交费日期|date" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>到帐日期</TD>
      <TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#EnterAccDate'});" verify="到帐日期|date" dateFormat="short" name=EnterAccDate id="EnterAccDate"><span class="icon"><a onClick="laydate({elem: '#EnterAccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
      <TD  class= title5>交费金额</TD>
      <TD  class= input5>
        <Input class= "common wid" name=PayMoney id="PayMoney">
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>操作员</TD>
      <TD  class= input5>
        <Input class= "common wid" name=Operator id="Operator">
      </TD>
      <TD  class= title5>险种编码</TD>
      <TD  class= input5>
        <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this]);" ondblclick="return showCodeList('RiskCode',[this]);" >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>代理人编码</TD>
      <TD  class= input5>
        <Input class= "common wid" name=AgentCode id="AgentCode">
      </TD>
    	<TD  class= title5>登陆机构</TD> 
      <TD  class= input5>
        <Input class= "common wid" name=mComcode id="mComcode" value="<%=tGI.ManageCom%>" readonly >
       </TD> 
    </TR>
  </table>
  </Div>
  </div>
  <Input class= common type=hidden name=AgentGroup id="AgentGroup">
  <a href="javascript:void(0)" class=button onclick="tempFeeNoQuery();">查  询</a>
  <a href="javascript:void(0)" class=button  name=PrintFee id = "PrintFee" onclick="PrintInform();">打印暂交费退费实付凭证</a>
  <!-- <INPUT VALUE="查  询" class= cssButton TYPE=button onclick="tempFeeNoQuery();">
  <INPUT VALUE="打印暂交费退费实付凭证" class= cssButton TYPE=button onclick="PrintInform();" name=PrintFee> -->
    <!-- 暂交费信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJTempFee1);">
    		</td>
    		<td class= titleImg>
    			 暂交费信息 
    		</td>
    	</tr>
    </table>
	<Div  id= "divLJTempFee1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanFeeGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
	
  <Div id= "divPage" align=center style= "display: 'none' ">
  <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
  <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
  <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
  <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
  </Div>
  <div>
    <a href="javascript:void(0)" class=button name=TFConfirm id="TFConfirm" onclick="submitForm();">退费确认</a>
  </div>
  <br>
  <!-- <INPUT VALUE="退费确认" class= cssButton TYPE=button onclick="submitForm();" name=TFConfirm>    -->
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
  <form action="./TempFeeWithdrawPrintMain.jsp" method=post name=fm2 id="fm2" target="_blank">
    <Input type=hidden name=PrtData id="PrtData">
  </form>
  <form action="../f1print/TempFeeWithdrawPrintSave.jsp" method=post name=fm3 id="fm3" target="_blank">
    <Input type=hidden name=PrtData1 id="PrtData1">
  </form>
  <br>
  <br>
  <br>
  <br>
</body>
</html>
<script>
	var codeSql=" 1 and comcode like #"+<%=tGI.ComCode%>+"%#";
</script>
