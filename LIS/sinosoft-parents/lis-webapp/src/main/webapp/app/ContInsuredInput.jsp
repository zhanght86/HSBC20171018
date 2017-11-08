<%
//程序名称：TempFeeInput.jsp
//程序功能：财务收费的输入
//创建日期：2002-07-12
//创建人  ：
//更新记录：  更新人     更新日期     更新原因/内容
%>
<!--%@include file="../common/jsp/UsrCheck.jsp"%-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
  <head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <script language="javascript">
  
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>                            
<% 
String grpContNo = request.getParameter("GrpContNo");
String CValidate = BqNameFun.getAnother("LCGrpCont","GrpContNo",grpContNo,"CValidate");
//loggerDebug("ContInsuredInput","CValidate = "+CValidate);
%>
  	var EdorAcceptNo = "<%=request.getParameter("EdorAcceptNo")%>";
  	var EdorValiDate = "<%=request.getParameter("EdorValiDate")%>";
  	var CValiDate = "<%=CValidate%>";
  	var GrpContNo = "<%=request.getParameter("ProposalGrpContNo")%>";
  	var ContNo = "<%=request.getParameter("ContNo")%>";
  	var prtNo = "<%=request.getParameter("prtNo")%>";
    var display = "<%=request.getParameter("display")%>"; 
  	if (prtNo == null)
  	    prtNo="";
  	var checktype = "<%=request.getParameter("checktype")%>";
  	var scantype = "<%=request.getParameter("scantype")%>";
  	var LoadFlag = "<%=request.getParameter("LoadFlag")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
  	if (LoadFlag == null)
  	    LoadFlag=1
    var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
  	if (ContType == "null")
  	    ContType=1
  	var Auditing = "<%=request.getParameter("Auditing")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
  	if (Auditing == "null") 
  	    Auditing=0;
  	var MissionID = "<%=request.getParameter("MissionID")%>";
  	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
  	var AppntNo = "<%=request.getParameter("AppntNo")%>";
  	var AppntName = "<%=request.getParameter("AppntName")%>";
  	var BQFlag = "<%=request.getParameter("BQFlag")%>";
  	var EdorType = "<%=request.getParameter("EdorType")%>";
  	var NameType = "<%=request.getParameter("tNameFlag")%>";     
  	    //alert(EdorAcceptNo);
  	    //alert(BQFlag);
  	var param="";
  	<%
  	   loggerDebug("ContInsuredInput",request.getParameter("Auditing"));
  	%>
  </script>
  	<script src="../common/javascript/Common.js" ></SCRIPT>
  	<script src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  	<script src="../common/javascript/MulLine.js"></SCRIPT>
  	<script src="../common/javascript/VerifyInput.js"></SCRIPT>
    <script src="../common/javascript/EasyQuery.js"></SCRIPT>
  	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<script src="ProposalAutoMove.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
    <script src="ContInsuredInput.js"></SCRIPT>
    <%@include file="ContInsuredInit.jsp"%>
    <%if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){%>
    <script src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
    <script>window.document.onkeydown = document_onkeydown;</SCRIPT>
    <%}%>
  </head>
<body  onload="initForm();initElementtype();" >
<Form action="./GrpContInsuredSave.jsp" method=post name=fm id=fm target="fraSubmit">
<input type=hidden id="fmAction" name="fmAction">
<input type=hidden id="ContType" name="ContType">
<input type=hidden id="GrpContNo" name="GrpContNo">
  <!--add by yaory-->
  <input type=hidden id="BPNo" name="BPNo" value="<%=request.getParameter("ProposalGrpContNo")%>">

    <Div  id= "divFamilyType" style="display: ">
    <table class=common>
       <tr class=common>
          <TD  class= title>
            个人合同类型
          </TD>
          <TD  class= input>
            <Input class="code" name=FamilyType id=FamilyType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();">
          </TD>
       </tr>
    </table>
    </Div>
    <!--table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			 合同信息
    		</td>
    	</tr>
    </table-->


    <Input type= "hidden" class="common" readonly name=ContNo id=ContNo>


    <DIV id="divPrtNo" style="display:none">
    	<table class=common>
    		<TR>
    			<TD  class= title5>
    			  投保单号码
    			</TD>
    			<TD  class= input5>
    			  <Input class="common wid" readonly name=ProposalContNo id=ProposalContNo>
    			</TD>
    			<TD  class= title5>
    			  印刷号码
    			</TD>
    			<TD  class= input5>
    			  <!--<Input class="common" readonly name=PrtNo verify="印刷号码|notnull" >-->
                          <!--edit by yaory-->
                          <Input class="common wid" readonly name=PrtNo id=PrtNo value="<%=request.getParameter("ProposalGrpContNo")%>">
    			</TD>
    		</TR>
    	</table>
    </DIV>
    <Div  id= "divTempFeeInput" style= "display:  ">
    	<Table  class= common>
    		<tr>
    			<td style="text-align: left" colSpan=1>
	  				<span id="spanInsuredGrid" >
	  				</span>
	  			</td>
    		</tr>
    	</Table>
    </div>
   <% 
        if (request.getParameter("ContType").equals("2"))
        {
        %>
            <jsp:include page="GrpComplexInsured.jsp"/>
        <%
        }
        else
        {
        %>
            <jsp:include page="ComplexInsured.jsp"/>
        <%
        }
   %>

  <!--table class=common>
   <TR  class= common>
      <TD  class= title> 特别约定及备注 </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
      <textarea name="GrpSpec" cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table-->

<DIV id=DivLCImpart STYLE="display: ">
<!-- 告知信息部分（列表） -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
</td>
<td class= titleImg>
被保险人告知信息
</td>
</tr>
</table>

<div  id= "divLCImpart1" style= "display:  ">
<table  class= common>
<tr  class= common>
<td style="text-align: left" colSpan=1>
<span id="spanImpartGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
<!-- 告知信息部分（列表） -->
<!--DIV id=DivLCImpart STYLE="display: ">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart2);">
</td>
<td class= titleImg>
被保险人告知明细信息
</td>
</tr>
</table>

<div  id= "divLCImpart2" style= "display:  ">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanImpartDetailGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV-->
<Div  id= "divAddDelButton" style= "display:  " align=right>
    <input type =button class=cssButton value="添加被保险人" onclick="addRecord();"> </TD>
    <input type =button class=cssButton value="删除被保险人" onclick="deleteRecord();"> </TD>
    <input type =button class=cssButton value="修改被保险人" onclick="modifyRecord();"></TD>
</DIV>
<hr/>
<!-- 被保人险种信息部分 -->
<DIV id=DivLCPol STYLE="display: ">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
</td>
<td class= titleImg>
被保险人险种信息
</td>
</tr>
</table>

<div  id= "divLCPol1" style= "display:  ">
<table  class= common>
<tr  class= common>
<td style="text-align: left" colSpan=1>
<span id="spanPolGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
 <hr>
    <Div  id= "divGrpInputContButton" style= "display: none" style="float: left">
      <INPUT class=cssButton id="riskbutton4" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
      
      <INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();">
      <INPUT class=cssButton id="riskbutton3" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
      <INPUT class=cssButton id="riskbutton33" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();">
   <HR align="LEFT" size="2" width="100%" >
    </DIV>
     <Div  id= "divGrpInputoverButton" style= "display: none" style="float: right">
      <INPUT class=cssButton id="riskbutton2" VALUE="录入完毕"  TYPE=button onclick="GrpInputConfirm(1);">
      </Div>

    <Div  id= "divInputContButton" style= "display:  " style="float: left">
    <INPUT class=cssButton id="riskbutton4" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
      
      <INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();">
      <!--INPUT class=cssButton id="riskbutton2" VALUE="录入完毕"  TYPE=button onclick="inputConfirm(1);"-->
      <INPUT class=cssButton id="riskbutton3" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
      <INPUT class=cssButton id="riskbutton33" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();">
    </DIV>
    <DIV id = "divApproveContButton" style = "display:none" style="float: left">
      <INPUT class=cssButton id="riskbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
      <input id="demo2" name="InsuredChkButton2" style = "display: " class=cssButton VALUE=被保人校验 TYPE=button onclick='InsuredChk();'>
      <INPUT class=cssButton id="riskbutton5" VALUE="上一步" TYPE=button onclick="returnparent();">
     <DIV id = "divCheckInsuredButton" style = "display: " style="float: left">
      <INPUT id="demo" name="InsuredChkButton" style = "display: " class=cssButton VALUE=被保人校验 TYPE=button onclick='InsuredChk();'>

     </DIV>
    	<!--INPUT class=cssButton id="riskbutton6" VALUE="复核完毕" TYPE=button onclick="inputConfirm(2);"-->
    	<INPUT class=cssButton id="riskbutton7" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
   <!-- 	<INPUT class=cssButton id="riskbutton33" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();">-->
    </DIV>
    <DIV id = "divApproveModifyContButton" style = "display:none" style="float: left">
    <!--  <INPUT class=cssButton id="riskbutton12" VALUE="问题件录入" TYPE=button onClick="QuestInput();">-->
      <INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
      
      <INPUT class=cssButton id="riskbutton9" VALUE="上一步" TYPE=button onclick="returnparent();">
    	<!--INPUT class=cssButton id="riskbutton10" VALUE="复核修改完毕" TYPE=button onclick="inputConfirm(3);"-->
    	<INPUT class=cssButton id="riskbutton11" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
    </DIV>
    <DIV id = "divQueryButton" style = "display:none" style="float: left">
      <INPUT class=cssButton id="riskbutton9" VALUE="上一步" TYPE=button onclick="returnparent();">
      <INPUT class=cssButton id="riskbutton11" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
    </DIV>
      <div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove( +param+ );" class=cssButton>
        <INPUT class=cssButton id="riskbutton9" VALUE="定制投保人页面" TYPE=button onclick="returnparent();">
	<input type="button" name="AutoMovePerson" value="定制第二被保险人" onclick="AutoMoveForNext();" class=cssButton>
	<!--input type="button" name="Next" value="定制险种页面" onclick="location.href='ProposalInput.jsp?LoadFlag='+LoadFlag+'&prtNo='+prtNo+'&scantype='+scantype+'&checktype='+checktype" class=cssButton-->
	<input type="button" name="Next" value="定制险种页面" onclick="intoInterface();" class=cssButton>
        <input type=hidden id="" name="autoMoveFlag">
        <input type=hidden id="" name="autoMoveValue">
        <input type=hidden id="" name="pagename" value="">

      </div>
	<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<INPUT  type= "hidden" class= Common name= MissionID id=MissionID value= ""><!-- 工作流任务编码 -->
  <INPUT  type= "hidden" class= Common name= SubMissionID id=SubMissionID value= "">
  <INPUT  type= "hidden" class= Common name= ProposalGrpContNo id=ProposalGrpContNo value= "">
  <INPUT  type= "hidden" class= Common name= AppntNo id=AppntNo value= "">
  <INPUT  type= "hidden" class= Common name= AppntName id=AppntName value= "">
  <INPUT  type= "hidden" class= Common name= SelPolNo id=SelPolNo value= "">
  <INPUT  type= "hidden" class= Common name= SaleChnl id=SaleChnl value= "">
  <INPUT  type= "hidden" class= Common name= ManageCom id=ManageCom value= "">
  <INPUT  type= "hidden" class= Common name= AgentCode id=AgentCode value= "">
  <INPUT  type= "hidden" class= Common name= AgentGroup id=AgentGroup value= "">
  <INPUT  type= "hidden" class= Common name= GrpName id=GrpName value= "">
  <INPUT  type= "hidden" class= Common name= CValiDate id=CValiDate value= "">
  <INPUT  type= "hidden" class= Common name= LoadFlag id=LoadFlag value= "">
  <input type=hidden name=BQFlag id=BQFlag value="<%=request.getParameter("BQFlag")%>">
  <input type=hidden name=EdorType id=EdorType value="<%=request.getParameter("EdorType")%>">
  <input type=hidden name=EdorValiDate id=EdorValiDate value="<%=request.getParameter("EdorValiDate")%>">
  <INPUT  type= "hidden" class= Common name= OccupationTypeReal id=OccupationTypeReal value= "">
</Form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
