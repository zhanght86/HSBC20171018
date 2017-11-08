<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：QuestQuery.jsp
//程序功能：问题件查询
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head >
<title>客户合并查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="CustCombintQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CustCombintQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action="./QuestQueryChk.jsp">
    
	<table class=common >
    	<TR  class= common>
         
          <TD  class= input>
            <Input type= "hidden" class="common wid" name=ContNo id=ContNo>
          </TD>
          
      </TR>
    </table>

    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 客户合并通知书列表：
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	<input CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    </div>
      <div id ="divOperation" style = "display : ''" class=maxbox1>
    <table class=common>
       <tr  class= common>
          <td class=title>发送人</td><td class=common><Input  class= "common wid" name= 'Operator' id=Operator readonly></td>
          <td class=title>发送时间</td><td class=common><Input  class= "common wid" name= 'MakeDate' id=MakeDate readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>回复时间</td><td class=common><Input  class= "common wid" name= 'ReplyDate' id=ReplyDate readonly></td>
       </tr>
    </table>
    </div>
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 客户合并通知书内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="8" class="common wid" readonly></textarea></TD>
    </TR>
  </table>
  
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 客户合并通知书回复 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" id=ReplyResult cols="135" rows="8" class="common wid" readonly></textarea></TD>
    </TR>
  </table>
  
  <p> 
    <!--读取信息-->
    <input type= "hidden" id=Flag name= "Flag" value="">
    <input type= "hidden" id=ContNoHide name= "ContNoHide" value= "">
    <input type= "hidden" id=QuesFlag name= "QuesFlag" value="">
    <input type= "hidden" id=Type name= "Type" value="">
    <input type= "hidden" id=HideOperatePos name= "HideOperatePos" value="">
    <input type= "hidden" id=HideSerialNo name= "HideSerialNo" value="">
    <input type= "hidden" id=HideQuest name= "HideQuest" value="">
    <input type= "hidden" id=MissionID name= "MissionID" value="">
    <input type= "hidden" id=SubMissionID name= "SubMissionID" value="">
  </p>
</form>
<div id = "divButton">
	<input class= cssButton type= "button" name= "sure" value="回复问题" class= Common onClick="reply()">
	<input class= cssButton type= "button" value="返回" class= Common onClick="top.close();">
  <input class=cssButton VALUE=问题件影像查询 TYPE=button onclick="QuestPicQuery();">
  <!--input class= cssButton type= "button" name= "sure" value="回复完毕" class= Common onClick="replySave()"-->
</div>
<div id = "divModiButton">
	<!--input type= "button" name= "sure" value="修改问题件打印标记" class= cssButton onClick="IfPrint()"-->
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br/><br/><br/><br/>
</body>
</html>
