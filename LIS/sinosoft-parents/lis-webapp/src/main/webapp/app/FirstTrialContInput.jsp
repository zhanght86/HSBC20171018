<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%  
	String prtNo = "";
	String ManageCom  ="";
	String MissionID  ="";
	String SubMissionID  ="";
	try
	{
		prtNo = request.getParameter("prtNo");
    ManageCom = request.getParameter("ManageCom");
    MissionID = request.getParameter("MissionID");
    SubMissionID = request.getParameter("SubMissionID");
	}
	catch( Exception e )
	{ 
	}
 
//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体下个人投保单明细查询
// 5 -- 复核
// 6 -- 查询
// 7 -- 保全新保加人
// 8 -- 保全新增附加险
// 9 -- 无名单补名单
// 10-- 浮动费率
// 99-- 随动定制

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
        loggerDebug("FirstTrialContInput","LoadFlag:" + tLoadFlag);
%>
<script>
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>"; 
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";	
	var type = "<%=request.getParameter("type")%>";
	//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	if (ScanFlag == "null") ScanFlag = "0";
	//保全调用会传险种过来
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//添加其它模块调用处理
	var LoadFlag ="<%=tLoadFlag%>"; //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="FirstTrialContInputInit.jsp"%>
  <SCRIPT src="FirstTrialContInput.js"></SCRIPT>
  <SCRIPT src="ProposalAutoMove.js"></SCRIPT>
    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

  <%if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){%>
  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
  <%}%> 

 
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./FirstTrialContSave.jsp" method=post name=fm id=fm target="fraSubmit">


    <!-- 合同信息部分 ContPage.jsp-->
     
<DIV id=DivLCContButton STYLE="display: ">
<table id="table1">
		<tr>
			<td class=common>
			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
			</td>
			<td class="titleImg">合同信息
			</td>
		</tr>
</table>
</DIV>
<div id="DivLCCont" class=maxbox1 STYLE="display: ">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title">投保单号 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<Input class="common wid" name=PrtNo id=PrtNo elementtype=nacessary readonly MAXLENGTH="11" verify="印刷号码|notnull&len=11">
    		</td>
			<td CLASS="title">管理机构 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="ManageCom" id=ManageCom VALUE MAXLENGTH="10" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" elementtype=nacessary CLASS="code" ondblclick="return showCodeList('comcode',[this],null,null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull">
    		</td>
		</tr>
		<tr CLASS="common">
			<td CLASS="title">销售渠道 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="SaleChnl" id=SaleChnl VALUE MAXLENGTH="2" CLASS="code" elementtype=nacessary style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentType',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this],null,null,null,null,1);">
    		</td>
			<td CLASS="title">代理人编码 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="AgentCode" id=AgentCode VALUE MAXLENGTH="10" CLASS="code" elementtype=nacessary style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryAgent();" onkeyup="return queryAgent2();">
    		</td>
			<td CLASS="title">代理人组别 
    		</td>
			<td CLASS="input" COLSPAN="1">
			<input NAME="AgentGroup" id=AgentGroup VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="代理人组别|notnull">
    		</td>
		</tr>


	</table>
	<table class=common>
         <TR  class=common> 
           <TD  class=title>  备    注 </TD>
         </TR>
         <TR  class=common>
           <TD  class=title>
             <textarea name="Remark"  id=Remark cols="110" rows="2" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
</div>
<DIV id=DivLCAppntIndButton STYLE="display: ">
<!-- 投保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class=titleImg>
投保人信息(客户号)：<Input class= common  name=AppntNo id=AppntNo >
<INPUT id="butBack" VALUE="查  询" TYPE=button class= cssButton onclick="queryAppntNo();">(首次投保客户无需填写客户号）
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd class=maxbox1 STYLE="display: ">
 <table  class=common>
        <TR  class=common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="common wid" name=AppntName id=AppntName elementtype=nacessary verify="投保人姓名|notnull&len<=20" >
          </TD>
                    <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" id=AppntIDType verify="投保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntIDNo" id=AppntIDNo verify="投保人证件号码|len<=20" onblur="checkidtype();getBirthdaySexByIDNo(this.value);" >
          </TD> 
        </TR>
        <TR  class= common>
       <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" elementtype=nacessary name="AppntBirthday" verify="投保人出生日期|notnull&date" onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex elementtype=nacessary verify="投保人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
      
        </TR>
     


      </table>   
</DIV>
   <Div  id= "divButton" style= "display:  ">
<span id="operateButton" >
	<table  class=common align=center>
		<tr align=left>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton VALUE="增  加"  TYPE=button onclick="submitForm();">
			</td>
			<!--
			<td class=button width="10%" align=right>
				<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();">
			</td>			
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">
			</td>			
			<td class=button width="10%" align=right>
				<INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return deleteClick();">
			</td>
			-->
		</tr>
	</table>
</span>
    <%@include file="../common/jsp/InputButton.jsp"%>
  </DIV>
<br>
 <DIV id=DivLCImpart STYLE="display: ">
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
            </td>
            <td class=titleImg>
                被保人信息列表
            </td>
        </tr>
    </table>
    
	<Div  id= "divLCGrp1" style= "display:  " align=center>
      	<table  class=common>
       		<tr  class=common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>					
</div>
<br>
<DIV id=DivLCAppntIndButton STYLE="display: ">
<!-- 投保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class=titleImg>
被保人信息(客户号)：<Input class= common  name=InsuredNo id=InsuredNo>
<INPUT id="butBack" VALUE="查  询" TYPE=button class=cssButton onclick="queryInsuredNo();">(首次投保客户无需填写客户号）
<Div  id= "divSamePerson" style="display: ">
<font color=red>
如投保人为被保险人本人，可免填本栏，请选择
<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>
<DIV id=DivLCInsuredInd class=maxbox1 STYLE="display: ">
 <table  class=common>
        <TR  class=common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="common wid" name=InsuredName id=InsuredName elementtype=nacessary verify="投保人姓名|notnull&len<=20" >
          </TD>
                    <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name="InsuredIDType" id=InsuredIDType verify="投保人证件类型|code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name="InsuredIDNo" id=InsuredIDNo verify="投保人证件号码|len<=20" onblur="checkidtype1();getBirthdaySexByIDNo1(this.value);">
          </TD>  

        </TR>
        
        <TR  class= common>
         <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" elementtype=nacessary name="InsuredBirthday" verify="投保人出生日期|notnull&date" onClick="laydate({elem: '#InsuredBirthday'});" id="InsuredBirthday"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>   
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=InsuredSex id=InsuredSex elementtype=nacessary verify="投保人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
  
        </TR>
       

        </TR>        
      </table>   
</DIV>
   <Div  id= "divInputContButton" style= "display:  " style="float: left">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="添加被保人" TYPE=button onClick="AddInsured();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="删除被保人" TYPE=button onClick="DelInsured();">
    </DIV>
    <br>
    <br>

    
 </DIV>     
    <br>
    <Div  id= "divInputContButton" style= "display:  " style="float: right">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="体检录入" TYPE=button onClick="showHealth();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="体检打印" TYPE=button onClick="PrintHealth();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="生调录入" TYPE=button onClick="showReport();">
    	<INPUT class=cssButton id="Donextbutton5" VALUE="生调打印" TYPE=button onClick="PrintReport();">
      <INPUT class=cssButton id="Donextbutton1" VALUE="初审完毕" TYPE=button onclick="inputConfirm();">
    </DIV>

  <div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('11');" class=cssButton>
	<input type="button" name="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class=cssButton>	
      <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="top.close();">
        <input type=hidden id="" name="autoMoveValue">   
        <input type=hidden id="" name="pagename" value="11">                         
      </div>    
    
    <Div  id= "HiddenValue" style= "display:none" style="float: right"> 
    	<input type=hidden id="fmAction" name="fmAction">	
    	<input type=hidden id="stype" name="stype">	
			<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
			<INPUT  type= "hidden" class=Common name=ContNo id=ContNo value= "">
			<input type=hidden id="MissionID" name="MissionID">
			<input type=hidden id="SubMissionID" name="SubMissionID">
			<input type=hidden id="PrtSeq" name="PrtSeq">
    </DIV>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>
