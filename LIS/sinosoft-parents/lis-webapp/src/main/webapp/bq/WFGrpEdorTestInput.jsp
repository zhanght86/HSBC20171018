<html> 

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
 
<%
	//添加页面控件的初始化。
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");  
%>   
<script>	
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFGrpEdorTest.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFGrpEdorTestInit.jsp"%>
  
  <title>团单保全试算申请</title>

</head>

<body  onload="initForm();" >
  <form action="./WFGrpEdorTestSave.jsp" method=post name=fm target="fraSubmit">
  
<Div  id= "divSearchAll" style= "display: none">  
    <table class= common border=0 width=100%>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
    		</td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
	    <table  class= common >   
	      	<TR  class= common>
		        <td class=title> 保全受理号 </td>
		        <td class= input><Input class="wid" class="common" name=EdorAcceptNo_ser id=EdorAcceptNo_ser></td>
	          	<TD class= title> 客户/团体保单号 </TD>
	          	<td class= input><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
	          	<TD class= title> 号码类型 </TD>
	          	<td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName id=OtherNoName readonly=true ></td>            	                          
	        </TR>
	      	<TR  class= common>
                <td class=title> 申请人 </td>
                <td class=input><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>             
                <td class=title> 申请方式 </td>
                <td class= input ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly=true></td>                          
				<TD class=title> 管理机构 </TD>
	          	<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom_ser id=ManageCom_ser ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true></TD>      	               
	        </TR> 
	      	<TR  class= common>
	            <TD class= title> 录入日期 </TD>
	            <TD class= input><!--<Input class= "multiDatePicker" dateFormat="short" name=MakeDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD> 
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	        </TR> 	        
	    </table>
    </div> 
    
    </div> 
    </Div>
		<INPUT VALUE="查  询" class = cssButton TYPE=hidden onclick="easyQueryClickSelf();">
		<!--<INPUT class=cssButton id="riskbutton" VALUE="团单申请试算" TYPE=button onClick="applyMission();">-->
        <a href="javascript:void(0);" id="riskbutton" class="button" onClick="applyMission();">团单申请试算</a>
	
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
    		</td>
    		<td class= titleImg>
    			 试算任务
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<!--<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"> 	-->				
  	</div>
  	
	<!--<INPUT class= cssButton TYPE=button VALUE="团单保全试算" onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE="团单试算完毕" onclick="EdorTestFinish();">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">团单保全试算</a>
    <a href="javascript:void(0);" class="button" onClick="EdorTestFinish();">团单试算完毕</a>
	
		<!-- 隐藏域部分 -->
			<INPUT  type= "hidden" class= Common name= EdorAcceptNo  value= "">
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">  
          	<INPUT  type= "hidden" class= Common name= Transact  value= "">  
          	        	
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>