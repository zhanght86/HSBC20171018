<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构	
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GroupPolApprove.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupPolApproveInit.jsp"%>
  <title>集体投保单复核 </title>
</head>
<body onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 集体信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch1);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：</td>
		  </tr>
	</table>
  <div class="maxbox1">
	<DIV id= "divSearch1" style= "display: ''">
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>结算批次号</TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpProposalNo id="GrpProposalNo">
          </TD>
          <TD  class= title5>管理机构</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name=ManageComName id="ManageComName" readonly=true>
          </TD>
        </TR>
      </table>
     <Input type="hidden"  class= "common wid" name=PrtNo id="PrtNo">
    </DIV>
    </div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
    <a href="javascript:void(0)" class=button id="riskbutton" onclick="ApplyUW();">申  请</a>
    <!-- <INPUT VALUE="查  询" class=cssButton  TYPE=button onclick="easyQueryClick();">
    <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();"> -->
          
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 共享工作池
    		</td>
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS="cssButton90" VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT  CLASS="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 		
      <INPUT  CLASS="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT  CLASS="cssButton93" VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">						
  	</div>
  	
  <br>
   <div class="maxbox1">
   	<DIV id= "divSearch" style= "display: ''">
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>结算批次号</TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpProposalNoSelf id="GrpProposalNoSelf">
          </TD>
          <TD  class= title5>管理机构</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=ManageComSelf id="ManageComSelf"  onclick="return showCodeList('station',[this,ManageComSelfName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComSelfName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name=ManageComSelfName id="ManageComSelfName" readonly=true>
          </TD>
        </TR>   
    </table>
    </DIV>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">查  询</a>
  <!-- <INPUT VALUE="查  询" class=cssButton  TYPE=button onclick="easyQueryClickSelf();"> -->
            	
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
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
         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"> 				
  	</div>  	
  	<p>
  	
  	<!--INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->
      <!--INPUT VALUE="查询集体下个人投保单" Class=Common  TYPE=button onclick="showPol();"--> 
      <!--INPUT VALUE="集体投保单复核" Class=Common TYPE=button onclick="approveGrpPol();"--> 
  	</p>
  	
          <INPUT  type= "hidden" class= Common name= MissionID    id= "MissionID"     value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID id= "SubMissionID"  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID   id= "ActivityID"   value= "">      	
  </form>
  <br>
  <br>
  <br>
  <br>
         
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
