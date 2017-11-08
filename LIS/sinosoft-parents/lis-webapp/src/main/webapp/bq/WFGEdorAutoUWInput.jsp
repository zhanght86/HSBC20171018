<html> 
<% 

//程序功能：保全工作流-团体自动核保
//
%>

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
  <SCRIPT src="WFGEdorAutoUW.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFGEdorAutoUWInit.jsp"%>
  
  <title>团体自动核保</title>

</head>

<body  onload="initForm();" >
  <form action="./WFEdorAutoUWSave.jsp" method=post name=fm target="fraSubmit">

    <table class= common border=0 width=100%>
    	<tr>
        	<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSearch)"></td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
	    <table  class= common >   
	      	<TR  class= common>
		        <td class=title5> 保全受理号 </td>
		        <td class= input5><Input class="wid" class="common" name=EdorAcceptNo id=EdorAcceptNo></td>
	          	<TD class= title5> 号码类型 </TD>
	          	<td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName id=OtherNoName readonly=true ></td>  </TR>
                 <TR  class= common>         	                          
	          	<TD class= title5> 客户/保单号 </TD>
	          	<td class= input5><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
                <td class=title5> 申请人 </td>
                <td class=input5><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td> 
	        </TR>
	      	<TR  class= common>
                            
                <td class=title5> 申请方式 </td>
                <td class= input5 ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly=true></td>                          
	            <TD class= title5> 录入日期 </TD>
	            <TD class= input5>
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD> 
	        </TR> 
	      	<TR  class= common>
				<TD class=title5> 管理机构 </TD>
	          	<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true></TD>      	               
	            <TD class= title5>  </TD>
	            <TD class= title5>  </TD>
	        </TR> 	        
	    </table>
    </div> 
    </Div>
	<!--<INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClickAll();">-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClickAll();">查    询</a> 
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllGrid);">
    		</td>
    		<td class= titleImg>
    			 共享工作池
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAllGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      	<INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      	<INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      	<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">						
  	</div>
  	
	<br>
		<!--<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyMission();">-->
        <a href="javascript:void(0);" id="riskbutton" class="button" onClick="applyMission();">申    请</a>
	<br>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
    		</td>
    		<td class= titleImg>
    			 我的任务
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
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"> 					
  	</div>
  	
	<!--<INPUT class= cssButton TYPE=button VALUE=" 自动核保 " onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">自动核保</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a>
	
		<!-- 隐藏域部分 -->
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">
          	<INPUT  type= "hidden" class= Common name= fmtransact  value= "">
          	 
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 <br><br><br><br> 
</body>
</html>
