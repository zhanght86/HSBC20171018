<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<html>
<%
  //个人下个人  
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
%>
<script>  
  var operator = "<%=tGI.Operator%>";   //记录操作员
  var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
  var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="BQUWConfirmPool.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQUWConfirmPoolInit.jsp"%>
  <title>保全人工核保确认</title>
</head>
<body  onload="initForm();" >
  <form action="./ScanContInputSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <div id="BQUWConfirmPool"></div>
    <!-- 保单信息部分 -->
  <!-- <table class= common border=0 width=100%>
      <tr>
      <td class= titleImg align= center>请输入查询条件：</td>
    </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
      <table  class= common >   
          <TR  class= common>
            <TD  class= title>管理机构</TD>
            <TD  class= input>
              <Input class="codeno" name=ManageCom verify="管理机构|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
            </TD>        
            <TD  class= title>保单号</TD>
            <TD  class= input><Input class= common name=ContNo ></TD>
                             
          </TR>  
          <TR  class= common>
            <TD  class= title>批单号</TD>
            <TD  class= input><Input class="common" name=EdorNo > </TD>   
            <TD  class= title>业务类别</TD>
            <td class="input"><Input class= "codeno" name=EdorType verify="批改类型|notnull&code:EdorCode" ondblclick="return showCodeList('EdorCode', [this,EdorTypeName], [0,1], null,0, 0);" onkeyup="return showCodeListKey('EdorCode', [this,EdorTypeName], [0,1], null, 0, 0);"><input class=codename name=EdorTypeName readonly=true></TD>            
          </TR>      
      </table>

    </div> 
    <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClick();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>共享工作池</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID  value= "">        
          <INPUT  type= "hidden" class= Common name= InputTime  value= "">        
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" ></span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();HighlightAllRow()"> 
      <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();HighlightAllRow()"> 					
      <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();HighlightAllRow()"> 
      <INPUT CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();HighlightAllRow()">						
  	</div>
  	
  <br>
   <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();">

   <DIV id=DivLCContInfo STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>个人工作池</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         <INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightSelfRow();"> 
         <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightSelfRow();"> 					
         <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightSelfRow();"> 
         <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightSelfRow();"> 					
  	</div> -->  
 	      <INPUT  type= "hidden" class= "Common wid" name= MissionID id=MissionID  value= "">
          <INPUT  type= "hidden" class= "Common wid" name= SubMissionID id=SubMissionID  value= "">
          <INPUT  type= "hidden" class= "Common wid" name= ActivityID id=ActivityID value= "">   
          <INPUT  type= "hidden" class= "Common wid" name= InputTime id=InputTime value= "">  
  	<p>
  	</p>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
