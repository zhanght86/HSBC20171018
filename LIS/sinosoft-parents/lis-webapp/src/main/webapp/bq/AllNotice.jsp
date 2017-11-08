
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
    var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>  
  <SCRIPT src="./AllNotice.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="AllNoticeInit.jsp"%>
  <title>问题件查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--业务员查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
            </td>
            <td class= titleImg>
                问题件查询条件
            </td>            
    	</tr>
    </table>
    <Div  id= "divAllNotice" style= "display: ''">
    <div class="maxbox">
  
  <table  class= common>
      <TR  class= common> 
        <TD class= title5> 保全受理号</TD>
        <TD  class= input5>  <Input class="wid" class=common  name=EdorAcceptNo_all id=EdorAcceptNo_all > </TD>
        <TD class= title5> 个人保单号 </TD>
        <TD  class= input5>  <Input class="wid" class=common  name=ContNo_all id=ContNo_all> </TD>
        </TR>
      <TR  class= common>
        <TD class= title5>客户号</TD>
        <TD class= input5> <Input class="wid" class=common name=InsuredNo_all id=InsuredNo_all></TD>
			 <TD class= title5>经办人</TD>
        <TD  class= input5><Input class="wid" class=common name=CreateOperator_all id=CreateOperator_all></TD> 
			</TR>
  	  <TR class= common>
       <TD class= title5>问题件类型</TD>
        <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right 

center" class=codeno readonly name=AllNoticeType id=AllNoticeType CodeData="0|^0|保全审批问题件^1|人工核保问题件" ondblclick="showCodeListEx('AllNoticeType',[this,AllNoticeTypeName],[0,1]);" onMouseDown="showCodeListEx('AllNoticeType',[this,AllNoticeTypeName],[0,1]);" onkeyup="showCodeListKeyEx('AllNoticeType',[this,AllNoticeTypeName],[0,1]);"><input class=codename name=AllNoticeTypeName id=AllNoticeTypeName readonly=true></TD>
       <TD class=title5>管理机构</TD>
       <TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right 

center"  no-repeat right center" class="codeno" name=ManageCom_all id=ManageCom_all ondblclick="showCodeList('station',[this,ManageComName_all],[0,1])" onMouseDown="showCodeList('station',[this,ManageComName_all],[0,1])" onkeyup="showCodeListKey('station',[this,ManageComName_all],[0,1])" readonly=true><input class=codename name=ManageComName_all id=ManageComName_all readonly></TD>   
      </TR>
  	 <TR class= common>
  	   <TD class= title5>下发日期(起)</TD>          
  	   <TD class=input5><!--<Input class="multiDatePicker" dateFormat="short" name=StartDate_all>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate_all'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate_all id="StartDate_all"><span class="icon"><a onClick="laydate({elem: '#StartDate_all'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
  	   <TD class =title5>下发日期(止)</TD>
  	   <TD class=input5><!--<Input class="multiDatePicker" dateFormat="short" name=EndEate_all>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#EndEate_all'});" verify="有效开始日期|DATE" dateFormat="short" name=EndEate_all id="EndEate_all"><span class="icon"><a onClick="laydate({elem: '#EndEate_all'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
		 </TR>      
    </table> 
		
    </Div>
    </div>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickAll();"> -->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClickAll();">查    询</a>
 <Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNoticeGrid);">
		</td>
		<td class= titleImg>共享工作池</td>
		</tr>
	</Table>      
  	<Div  id= "divAllNoticeGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllNoticeGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
			<center>      <INPUT class=cssButton90 VALUE="首  页" class="cssButton90" TYPE=button onclick="turnPageAll.firstPage();HighlightAllRow();"> 
			      
			      <INPUT class=cssButton91 VALUE="上一页" class="cssButton91" TYPE=button onclick="turnPageAll.previousPage();HighlightAllRow();"> 					
			     			      
			      <INPUT class=cssButton92 VALUE="下一页" class="cssButton92" TYPE=button onclick="turnPageAll.nextPage();HighlightAllRow();"> 
			    		      
			      <INPUT class=cssButton93 VALUE="尾  页" class="cssButton93" TYPE=button onclick="turnPageAll.lastPage();HighlightAllRow();"> 			</center>			
			   
  		 	</div> <!--<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyMission();">-->
            <a href="javascript:void(0);" id="riskbutton" class="button" onClick="ApplyMission();">申    请</a> 
  		 	            				
        <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfNotice);">
            </td>
            <td class= titleImg>
                我的问题件查询条件
            </td>            
    	</tr>
    </table>
    
  <Div  id= "divSelfNotice" style= "display: ''"><div class="maxbox">
  <table  class= common>
      <TR  class= common> 
        <TD class= title5> 保全受理号</TD>
        <TD  class= input5>  <Input class="wid" class=common  name=EdorAcceptNo id=EdorAcceptNo > </TD>
        <TD class= title5> 个人保单号 </TD>
        <TD  class= input5>  <Input class="wid" class=common  name=ContNo id=ContNo> </TD>
        </TR>
      <TR  class= common>
        <TD class= title5>被保人客户号</TD>
        <TD class= input5> <Input class="wid" class=common name=InsuredNo id=InsuredNo></TD>
			 <TD class= title5>经办人</TD>
        <TD  class= input5><Input class="wid" class=common name=CreateOperator id=CreateOperator></TD> 
			</TR>
  	  <TR class= common>
       <TD class= title5>问题件类型</TD>
        <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png)  no-repeat right center" class=codeno readonly name=SelfNoticeType id=SelfNoticeType CodeData="0|^0|保全审批问题件^1|人工核保问题件" ondblclick="showCodeListEx('SelfNoticeType',[this,SelfNoticeTypeName],[0,1]);" onMouseDown="showCodeListEx('SelfNoticeType',[this,SelfNoticeTypeName],[0,1]);" onkeyup="showCodeListKeyEx('SelfNoticeType',[this,SelfNoticeTypeName],[0,1]);"><input class=codename name=SelfNoticeTypeName id=SelfNoticeTypeName readonly=true></TD>
       <TD class=title5>管理机构</TD>
       <TD class=input5><Input style="background:url(../common/images/select--bg_03.png)  no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1])" onMouseDown="showCodeList('station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1])" readonly=true><input class=codename name=ManageComName id=ManageComName readonly></TD>   
      </TR>
  	 <TR class= common>
  	   <TD class= title5>下发日期(起)</TD>          
  	   <TD class=input5><!--<Input class="multiDatePicker" dateFormat="short" name=StartDate>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
  	   <TD class =title5>下发日期(止)</TD>
  	   <TD class=input5><!--<Input class="multiDatePicker" dateFormat="short" name=EndEate>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#EndEate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndEate id="EndEate"><span class="icon"><a onClick="laydate({elem: '#EndEate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
		 </TR>      
    </table> 
		
    </Div>
    </div>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickSelf();">--> 
    <a href="javascript:void(0);" class="button" onClick="easyQueryClickSelf();">查    询</a>
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfNoticeGrid);">
    		</td>
    		<td class= titleImg>
    			 我的问题件列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divSelfNoticeGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfNoticeGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<!--<table>
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPageSelf.firstPage();HighlightSelfRow();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPageSelf.previousPage();HighlightSelfRow();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPageSelf.nextPage();HighlightSelfRow();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPageSelf.lastPage();HighlightSelfRow();"> 						
			    </td>  			
  			</tr>
  		</table>-->
  		 	</div>
  		<!--<INPUT class=cssButton VALUE="问题件处理" TYPE=button onclick="dealNotice();"> -->
        <a href="javascript:void(0);" class="button" onClick="dealNotice();">问题件处理</a>
  		
  		<div id= "divApproveNotice" style= "display: 'none'" >
  		 <table>
	        <TR>	        	
	         	<TD class= titleImg>审批问题内容</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="DispIdeaTrace" cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
	      <table>
	        <TR>	        	
	         	<TD class= titleImg>我的回复</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea name="MyReply" cols="100" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
	     <!--<INPUT class=cssButton VALUE="回 复" TYPE=button onclick="replyApproveAsk();">-->
         <a href="javascript:void(0);" class="button" onClick="replyApproveAsk();">回    复</a>
          
				<input type="hidden" id="PrtSeq" name="PrtSeq">
  	</div>


      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
  <br /><br /><br /><br />
</body>
</html>
