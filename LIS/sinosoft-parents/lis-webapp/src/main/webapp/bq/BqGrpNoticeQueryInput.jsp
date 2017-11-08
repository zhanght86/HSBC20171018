
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<html>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BqGrpNoticeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="BqGrpNoticeQueryInit.jsp"%>
  <title>问题件查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--业务员查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
            </td>
            <td class= titleImg>
                问题件查询条件
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
  <div class="maxbox1">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> 问题件流水号</TD>
        <TD  class= input>  <Input class="wid" class=common  name=PrtSeq11 id=PrtSeq11> </TD>
        <TD class= title> 保全受理号 </TD>
        <TD  class= input>  <Input class="wid" class=common  name=EdorAcceptNo id=EdorAcceptNo> </TD>
        <TD class= title> 团体合同号</TD>
        <TD class= input> <Input class="wid" class=common name=GrpContNo id=GrpContNo>  </TD>
		
      </TR>
       <TR  class= common> 

        	<TD  class= title>问题件类型</TD>
			<TD  class= input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 	no-repeat right center" class="codeno" name=AskType id=AskType CodeData="0|^1|人工核保问题件^2|保全审批问题件" ondblclick="showCodeListEx('AskType',[this,AskTypeName],[0,1]);" onMouseDown="showCodeListEx('AskType',[this,AskTypeName],[0,1]);" onkeyup="showCodeListKeyEx('AskType',[this,AskTypeName],[0,1]);"><input class=codename name="AskTypeName" id="AskTypeName" readonly=true> </TD>   
			<TD  class= title>问题人</TD>
			<TD  class= input>
				<input class="wid" class=common name=Asker id=Asker>     	        	 
			</TD>
			<TD  class= title> 承接人 </TD>
			<TD  class= input> 
				<input class="wid" class=readonly readonly   name=DispPer id=DispPer value="<%=tGI.Operator%>">     	
			</TD>
          
        
      </TR>
      
    </table>
      </div>   </Div>             

		<!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> -->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>

        
          				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 需要我回复的问题件
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ''" >
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	 <table align="center">
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
        
  		 <table>
	        <TR>	        	
	         	<TD class= titleImg>问题内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea style="margin-left:16px" readonly=true name="DispIdeaTrace" id="DispIdeaTrace" cols="199" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
         
	      <table>
          
	        <TR>	        	
	         	<TD class= titleImg>我的回复</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea style="margin-left:16px" name="MyReply" id="MyReply" cols="199" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>

  	</div>

	     <!--<INPUT class=cssButton VALUE="回 复" TYPE=button onclick="replyAsk();">-->
         <a href="javascript:void(0);" class="button" onClick="replyAsk();">回    复</a> 
				<input type="hidden" id="PrtSeq" name="PrtSeq">
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
     
  </form>
  <br><br><br><br>
</body>
</html>
