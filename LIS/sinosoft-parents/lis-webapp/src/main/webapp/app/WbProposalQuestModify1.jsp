<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="WbProposalQuestModify1.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WbProposalQuestModifyInit1.jsp"%>
  <title></title>
 </head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  String tLoadFlag = request.getParameter("LoadFlag");
  String tPoolTitle = "待处理异常件";
  if(tLoadFlag == null || "".equals(tLoadFlag))
  {
   tLoadFlag = "3";  //异常件处理为3，抽检件处理为4
  }
  
  if("4".equals(tLoadFlag))
  {
   tPoolTitle = "待处理抽检件";
  }
%>

<script>
  var Operator = "<%=tGlobalInput.Operator%>";
  var ComCode = "<%=tGlobalInput.ComCode%>";
  var manageCom = "<%=tGlobalInput.ManageCom%>"; //记录登陆机构
  var RiskSql = "1 and subriskFlag =#M# ";
  var tLoadFlag = "<%=tLoadFlag%>";
  var tCreatePos="异常件处理";
  var tPolState="1006";
  var mActivityID = "0000001090";  //异常件处理
  
  if(tLoadFlag == "4")
  {
    tCreatePos="复核抽查" ;
    tPolState="1005";
    mActivityID = "0000001091";  //异常件处理
  }   
</script>

<body  onload="initForm();" >
<form action="" method=post name=fm id="fm" target="fraSubmit">
	<!-- ########################保单信息部分 输入查询条件########################  -->
	<Table class= common>
		<tr>
      <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
        </td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</Table>
  <div class="maxbox">
	<Div  id= "divSearch" style= "display: ''">
    <table class=common>
      <tr class=common>
    	  <TD  class= title5 >印刷号</TD>
        <TD  class= input5>
          <Input class= "common  wid" name=PrtNo id="PrtNo">
        </TD>
        <TD  class= title5 >外包公司</TD>
        <TD  class= input5>
          <Input class=  code  style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  name=BPOID id="BPOID" onclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype=#TB#','1');"  ondblclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype=#TB#','1');" onkeyup="return showCodeListKey('querybpoid',[this],null,null,'1 and bussnotype=#TB#','1');">
        </TD>
      </tr>
      <tr class=common>
        <TD  class= title5 >管理机构</TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= code  name=ManageCom id="ManageCom" onclick="return showCodeList('comcode',[this]);" ondblclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);">
        </TD>   
    	  <TD  class= title5>险种编码</TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" ondblclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onkeyup="return showCodeListKey('RiskCode',[this],null,null,RiskSql,'1',1);">
        </TD>
      </tr>
      <tr class=common>
        <TD class=title5>扫描日期</TD>
	      <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#ScanDate'});" verify="扫描日期|DATE" dateFormat="short" name=ScanDate id="ScanDate"><span class="icon"><a onClick="laydate({elem: '#ScanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
    	  <TD class=title5>保单性质</TD>
	      <TD  class= input5>
	        <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= "code" name=PolProp1 id="PolProp1" CodeData="0|^11|个险^15|银代|^16|简易|^21|中介|^25|银代邮储|^35|银代"  onclick="showCodeListEx('PolProp1',[this],[0,1]);" ondblClick="showCodeListEx('PolProp1',[this],[0,1]);" onkeyup="showCodeListKeyEx('PolProp1',[this],[0,1]);">
        </TD>
      </tr>
      <tr class=common>
        <TD class=title5>优先机构</TD>
		    <TD class=input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=PreCom id="PreCom"
			CodeData="0|^a|百团机构投保单^b|非百团机构投保单"
      onclick="showCodeListEx('PreCom',[this,PreComName],[0,1]);"
			ondblClick="showCodeListEx('PreCom',[this,PreComName],[0,1]);"
			onkeyup="showCodeListKeyEx('PreCom',[this,PreComName],[0,1]);"><input
			class=codename name=PreComName id="PreComName" value='' readonly=true></TD>
	    </tr>
    </table>
	</Div>
	</div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  <a href="javascript:void(0)" id="riskbutton" class=button onclick="ApplyUW();">申  请</a>
	<!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> 
	<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();"> -->
	<!-- ########################保单信息部分 共享工作池########################  -->
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
		</td>
		<td class= titleImg>共享工作池</td>
		</tr>
	</Table>
	
	<Div  id= "divLCPol1" style= "display: ''" align = center>
		<Table  class= common >
			<tr  class= common align = left>
				<td text-align: left colSpan=1>
					<span id="spanPolGrid" ></span> 
			  	</td>
			</tr>
		</Table>
    <div style= "display: none">
		<INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    </div>		
	</Div>
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
		</td>
		<td class= titleImg><%=tPoolTitle%></td>
		</tr>  	
	</Table>
  <div class="maxbox1">
<Div  id= "divSearch1" style= "display: ''">
 <table class=common>
    <tr class=common>
    	<TD  class= title5 >印刷号</TD>
      <TD  class= input5>
        <Input class= "common wid"  name=PrtNo1 id="PrtNo1"></TD>
      <TD  class= title5 >外包公司</TD>
      <TD  class= input5>
        <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= code  name=BPOID1 id="BPOID1" onclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype=#TB#','1');" ondblclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype=#TB#','1');" onkeyup="return showCodeListKey('querybpoid',[this],null,null,'1 and bussnotype=#TB#','1');">
      </TD>
    </tr>
    <tr class=common>
      <TD  class= title5 >管理机构</TD>
      <TD  class= input5>
        <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= code  name=ManageCom1 id="ManageCom1" onclick="return showCodeList('comcode',[this]);" ondblclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);">
      </TD>
    	<TD  class= title5>险种编码</TD>
      <TD  class= input5>
        <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name=RiskCode1 id="RiskCode1" onclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" ondblclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onkeyup="return showCodeListKey('RiskCode',[this],null,null,RiskSql,'1',1);">
      </TD>
    </tr>
    <tr class=common>
      <TD class=title5>扫描日期</TD>
	    <TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#ScanDate1'});" verify="扫描日期|DATE" dateFormat="short" name=ScanDate1 id="ScanDate1"><span class="icon"><a onClick="laydate({elem: '#ScanDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
    	<TD class=title5>保单性质</TD>
	    <TD  class= input5>
	     <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= "code" name=PolProp11 id="PolProp11" CodeData="0|^11|个险^15|银代|^16|简易|^21|中介|^25|银代邮储|^35|银代" onclick="showCodeListEx('PolProp1',[this],[0,1]);"  ondblClick="showCodeListEx('PolProp1',[this],[0,1]);" onkeyup="showCodeListKeyEx('PolProp1',[this],[0,1]);">
      </TD>
    </tr>
  </table>
</DIV>
</div>	
<div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">查  询</a>
</div>
<br>
	<!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickSelf();">  -->
	<Div  id= "divSelfPolGrid" style= "display: ''" align = center>
		<Table  class= common >
		<tr  class=common>
			<td text-align: left colSpan=1 >
				<span id="spanSelfPolGrid" ></span> 
		  	</td>
		</tr>
		</Table>
    <div style= "display: none">
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">  
    </div>   
	</Div>
	<br>

	<!--#########################  隐藏表单区域   ##############################-->
	<Input type=hidden name="MissionID" id="MissionID">
	<Input type=hidden name="SubMissionID" id="SubMissionID">
	<Input type=hidden name="ActivityID" id="ActivityID">   	 
</form>
<br>
<br>
<br>
<br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
