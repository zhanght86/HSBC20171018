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
<title>问题件查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="RnewQuestQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewQuestQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./RnewQuestQueryChk.jsp">
    
    <table class=common >
    	<TR  class= common>
          <!--TD  class= title>
            合同投保单号码
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=common name=ContNo id="ContNo">
          </TD>
          <!--TD  class= title>
            返回对象
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code name=BackObj id="BackObj" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
          <!--TD  class= title>
            操作位置
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code name=OperatePos id="OperatePos" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1]);" >
          </TD>
        </TR>

    	<TR  class= common>
          <!--TD  class= title>  管理机构  </TD-->
          <TD  class= input>  <Input type="hidden" class=code name=ManageCom id="ManageCom" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <!--TD  class= title>   问题件选择  </TD-->
          <TD  class= input>  <Input type="hidden" class=code name=Quest id="Quest" ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <!--TD  class= title>  是否回复 </TD-->
          <TD  class= input> <Input type="hidden" class=code name=IfReply id="IfReply" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <!--TD  class= title >
            问题件所在管理机构
          </TD-->
          <TD  class= input >
            <Input type="hidden" class=code name=OManageCom id="OManageCom" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class= Common name = SerialNo id="SerialNo">
          </TD>
          <TR>
          <!--TD  class= title>
            <input type="button" class=cssButton name= "Query" class= Common value="查  询" onClick= "query()">
          </TD>
          </TD-->
      </TR>
    </table>
    
  
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 问题件列表：
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
    <div id ="divOperation" style = "display : none">
      <div class="maxbox1">
      <table class=common>
       <tr  class= common>
          <td class=title>发送人</td>
          <td class=common><Input  class= "Common wid " name= 'Operator' id="Operator" readonly></td>
          <td class=title>发送时间</td> 
          <td class=common><Input  class= "Common wid " name= 'MakeDate' id="MakeDate" readonly></td>
          <td class=title>回复时间</td><td class=common><Input  class= "Common wid " name= 'ReplyDate' id="ReplyDate" readonly></td>
       </tr>
      </table>
      </div>
    </div>
    <table class= common>
      <TR  class= common> 
        <TD width="14%" class= title> 问题件内容 </TD>
      </TR>
      <tr>
      	 <TD >
      	 &nbsp;&nbsp;&nbsp;&nbsp;<textarea name="Content" id="Content" cols="135" rows="4" class="common" readonly></textarea></TD>
      </tr>
    </table>
  
  <table class= common>
    <TR  class= common> 
      <TD width="14%" class= title> 问题件回复 </TD>
    </TR>
    <tr>
    	 <TD >
    	 &nbsp;&nbsp;&nbsp;&nbsp;<textarea name="ReplyResult" id="ReplyResult" cols="135" rows="4" class="common" readOnly></textarea></TD>
    </tr>
  </table>
  
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "Flag" id="Flag" value="">
    <input type= "hidden" name= "ContNoHide" id="ContNoHide" value= "">
    <input type= "hidden" name= "QuesFlag" id="QuesFlag" value="">
    <input type= "hidden" name= "Type" id="Type" value="">
    <input type= "hidden" name= "HideOperatePos" id="HideOperatePos" value="">
    <input type= "hidden" name= "HideSerialNo" id="HideSerialNo" value="">
    <input type= "hidden" name= "HideQuest" id="HideQuest" value="">
    <input type= "hidden" name= "MissionID" id="MissionID" value="">
    <input type= "hidden" name= "SubMissionID" id="SubMissionID" value="">
    <input type= "hidden" name= "hiddenOperate" id="hiddenOperate" value="">
  </p>
</form>
<div id = "divButton">
  	<input class=cssButton VALUE=问题件影像查询 TYPE=button onclick="QuestPicQuery();">
	<input class= cssButton type= "button" name= "sure" value="保存" class= Common onClick="reply()">
	<input class= cssButton type= "button" value="返回" class= Common onClick="top.close();"> 
</div>
<br>
<div id = "divButtonCenter" style= "display: 'none'">
	<input class=cssButton VALUE=问题件回复完毕 TYPE=button onclick="finishMission();">
</div>
<br>
<div id = "divModiButton">
</div>
<div id = "divUWButton">
	<input class=cssButton VALUE=问题件影像查询 TYPE=button onclick="QuestPicQuery();">
	<input class= cssButton type= "button" value="返回"  onClick="top.close();"> 
</div>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
