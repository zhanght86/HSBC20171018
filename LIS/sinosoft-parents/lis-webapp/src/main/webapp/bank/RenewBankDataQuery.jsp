 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
    GlobalInput tGI = (GlobalInput)session.getValue("GI");
    String Branch = tGI.ComCode;
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./RenewBankDataQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./RenewBankDataQueryInit.jsp"%>
  <title>续保件发盘确认数据查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./RenewBankDataUndoSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
    		<td class= titleImg>续保确认信息查询</td>
    	</tr>
    </table>
    <div id="divInvAssBuildInfo">
    <div class="maxbox1" >
      <table  class= common>
        <TR  class= common>
          <TD class=title5>管理机构 </TD>
          <TD class=input5><Input name=ManageCom id=ManageCom value='<%=Branch%>' class="common wid" verify="管理机构|code:comcode&notnull" 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
          		ondblclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            	onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);"></TD>
        </TR>    	
        <TR  class= common>  	
          <TD class= title5>合同号码</TD>
          <TD class= input5><Input class="common wid" name=ContNo></TD>
          <TD class= title5>印刷号码</TD>
          <TD class= input5><Input class="common wid" name=PrtNo></TD>
        </TR>
        
        <TR  class= common>
          <TD class= title5>投保人姓名</TD>
          <TD class= input5><Input class="common wid" name=AppntName></TD>
          <TD class= title5>代理机构</TD>
          <!--<TD class= input><Input class= 'code' name=AgentCom ondblclick="return showCodeList('agentcom',[this],null,null,null,null,null,250);" 
          		onkeyup="return showCodeListKey('agentcom',[this],null,null,null,null,null,250);"></TD>-->
		  <td class="input5" width="25%"><Input class="common wid" name=AgentCom> <input class=cssButton
				type="button" value="查  询" style="width:60" onClick="queryAgentCom()"></td>          		
        </TR>
                
        <TR  class= common>
          <TD  class= title5>缴至日期（起）</TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
          <TD  class= title5>缴至日期（止）</TD>
          <TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
        </TR>
        </table>
    </Div>
     </Div>
    
		<!--<input class= cssButton type=Button value=" 查 询 " onClick="easyQueryClick()">-->
        <a href="javascript:void(0);" class="button"onClick="easyQueryClick()">查   询</a>
    
	  <br><br>
    <Div style= "display: ''">
	    <table  class= common>
	    	<tr  class= common>
	      	<td text-align: left colSpan=1>
		  			<span id="spanCodeGrid" ></span> 
	  	  	</td>
	  		</tr>
	    </table>
    	<div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="首  页" TYPE=button id="buttona" onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button id="buttonb" onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button id="buttonc" onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button id="buttond" onClick="turnPage.lastPage();">
    	</div>
  	</div>
  	<br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
