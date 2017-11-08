<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：QuestInput.jsp
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tMissionID = "";
	String tSubMissionID = "";
	String tEdorNo = "";
	String tEdorType = "";
	
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
	tEdorNo =  request.getParameter("EdorNo");
	tEdorType =  request.getParameter("EdorType");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="SendAllNotice.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>核保问题件录入</title>
  <%@include file="SendAllNoticeInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tEdorNo%>','<%=tEdorType%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./SendAllNoticeChk.jsp">
  	
  		<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>问题件信息</td>
		</tr>
	</table>
	<div class="maxbox1">
   <table  class= common align=center>
    	<TR  class= common>
    		  <TD  class= title5>
            通知书类型
          </TD>
          <TD class="input5">
          <Input class= "codeno" id="NoticeType" name = NoticeType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('uwnoticetype',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('noticetype',[this,NoticeTypeName],[0,1]);"><Input class = codename id="NoticeTypeName" name=NoticeTypeName readonly = true>
          </TD> 
          <td></td><td></td>
    
  </table>
    </div>
    <div id= "divnoticecontent" style= "display: none" >	
  <table class= common align=center width="121%" height="37%">
    <TR  class= common > 
      <TD width="100%" height="13%"  class= title> 通知书内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
</div>

    <Div  id= "divUWSpec1" style= "display: none">
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec);"></td>
    		<td class= titleImg>	 问卷类型选择</td>                            
    	</tr>	
    </table>
  </div>
    <Div  id= "divUWSpec" style= "display: none">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  							<span id="spanQuestionTypeGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
    </Div>	
    
     <Div  id= "divLCPol" style= "display: none">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			已保存特约信息
    		</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWSpecGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>    
<p>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" id="sure" name= "sure" class= cssButton  value="发送通知书" onClick="submitForm()">
</P>

 <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 已发通知书
	    	</td>
	    </tr>
	    </table>    
	<div id= "divPolGrid" style= "display: ''" >
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<div align=center>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();">
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();">
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();">
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">
  </div>
    <div id= "divresult" style= "display: none" >	
  <table class= common align=center width="121%" height="37%">
    <TR  class= common > 
      <TD width="100%" height="13%"  class= title> 查询结果 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="result" id=result cols="135" rows="10" class="common" readonly = true></textarea></TD>
    </TR>
  </table>
</div>
  
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="MissionID" name= "MissionID" value= "">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type= "hidden" id="EdorNo" name= "EdorNo" value= "">
    <input type= "hidden" id="EdorType" name= "EdorType" value= "">
  </p>
</form>
  

</body>
</html>
