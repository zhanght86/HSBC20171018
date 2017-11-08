<html> 
<% 
//程序名称：WFEdorAppInput.jsp
//程序功能：保全工作流-保全申请
//创建日期：2005-04-27 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
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
	var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFEdorApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFEdorAppInit.jsp"%>
  
  <title>保全申请</title>

</head>

<body  onload="initForm();" >
  <form action="./WFEdorAppSave.jsp" method=post name=fm id=fm target="fraSubmit">
	<div id="WFEdorInputPool"></div>
   <!--<table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title8> 管理机构 </TD>
	          <TD  class= input8><Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>      	
	          <TD  class= title8> 扫描时间 </TD>
	          <TD  class= input8><Input class="multiDatePicker" name=InputDate ></TD>  
	          <TD  class= title8> </TD>
	          <TD  class= input8> </TD>                
	        </TR>      
	    </table>
    </div> 
    
	<INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClickAll();"> 
	
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
		<INPUT CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage3.firstPage();HighlightAllRow();"> 
      	<INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage3.previousPage();HighlightAllRow();"> 					
      	<INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage3.nextPage();HighlightAllRow();"> 
      	<INPUT CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage3.lastPage();HighlightAllRow();">						
  	</div>
  	
	<br>
		<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyMission();">
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
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();HighlightSelfRow();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();HighlightSelfRow();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();HighlightSelfRow();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();HighlightSelfRow();"> 					
  	</div>  --> 
  	
	<!--<INPUT class= cssButton TYPE=button VALUE=" 保全处理 " onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE=" 影像复制 " onclick="GoToImageCopy();"> 
	<INPUT class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">保全处理</a>
    <a href="javascript:void(0);" class="button" onClick="GoToImageCopy();">影像复制</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a>
	
		<!-- 隐藏域部分 -->
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">  
          	<INPUT  type= "hidden" class= Common name= EdorAcceptNo  value= ""> 
          	        	
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
