<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<script>
var managecom ="<%=tGI.ManageCom%>"; //记录管理机构
var comcode ="<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="LLUWImpartQuestCheck.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LLUWImpartQuestCheckInit.jsp"%>
<title>打印理补充告知问卷</title>
</head>
<body onLoad="initForm();">
	<form action="./BodyCheckPrintQuery.jsp" method=post name=fm  id="fm" target="fraSubmit">
		<!-- 投保单信息部分 -->
<table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入投保单查询条件：</td>
		  </tr>
	  </table>
      <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
      	
          <TD  class= title5>立案号 </TD>
          <TD class = input5><Input class="common wid" name = ClmNo>
          </TD> 
          
                  
          </TD> 
          <TD  class= title5>  保单号码   </TD>
          <TD  class= input5>  <Input class="common wid" name=ContNo > </TD>
          <Input  type = "hidden" class= common name=PrtNo >
          </TR>
          	<TR  class= common>
          <TD  class= title5>   管理机构  </TD>
          <TD  class= input5>  <Input class="common wid" name=ManageCom id=ManageCom
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this]);" 
 
          onDblClick="return showCodeList('station',[this]);" 
          onKeyUp="return showCodeListKey('station',[this]);">  </TD>
        
        
         
         </TR>
         <TR  class= common style = "display:none">
         	  <TD  class= title5> 营业员编码 </TD>
          <TD  class= input5>  <Input class="code" name=AgentCode 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentCode',[this]);"
          onDblClick="return showCodeList('AgentCode',[this]);"
           onKeyUp="return showCodeListKey('AgentCode',[this]);">   </TD>
         <TD  class= title5> 营业部、营业组 </TD>
          <TD  class= input5>  <Input class="code" name=AgentGroup
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentGroup',[this]);"

           onDblClick="return showCodeList('AgentGroup',[this]);"
            onKeyUp="return showCodeListKey('AgentGroup',[this]);">   </TD>
         
		
          </TD>
        </TR>
    </table>
    </div>
    </div>
          <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();">

  </form>
	<form action="./LLUWImpartQuestCheckSave.jsp" method=post name=fmSave target="fraSubmit">
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCCont1);">
				</td>
				<td class=titleImg>待打印信息</td>
			</tr>
		</table>
		<Div id="divLCCont1" style="display: ''" align =center>
			<table class=common>
				<TR class=common>
					<td text-align: left colSpan=1>
						<span id="spanPolGrid"></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="getFirstPage();">
			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="getPreviousPage();">
			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="getNextPage();">
			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="getLastPage();">
		</div>
		<p>
			<INPUT VALUE="打印补充告知问卷" class=cssButton TYPE=button onClick="printPol();">
		</p>
		<input type=hidden id="fmtransact" name="fmtransact">
		<input type=hidden id="ContNo" name="ContNo">
		<input type=hidden id="PrtSeq" name="PrtSeq">
		<input type=hidden id="MissionID" name="MissionID">
		<input type=hidden id="SubMissionID" name="SubMissionID">
		<input type=hidden id="PrtNo" name="PrtNo">
		<input type=hidden id="ClmNo" name="ClmNo">
		<input type=hidden id="BatNo" name="BatNo">
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
