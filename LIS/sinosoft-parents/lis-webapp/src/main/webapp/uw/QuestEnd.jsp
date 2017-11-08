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
  <SCRIPT src="QuestEnd.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QuestEndInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action="./QuestQueryChk.jsp">
    <div class=maxbox>
    <table class=common >
    	<TR  class= common>
          <TD  class= title>
            合同投保单号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name=ContNo id=ContNo>
          </TD>
          <TD  class= title>
            返回对象
          </TD>
          <TD  class= input>
            <Input class=code name=BackObj id=BackObj style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
          <TD  class= title>
            操作位置
          </TD>
          <TD  class= input>
            <Input class=code name=OperatePos id=OperatePos style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1]);" >
          </TD>
        </TR>

    	<TR  class= common>
          <TD  class= title>  管理机构  </TD>
          <TD  class= input>  <Input class=code name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <TD  class= title>   问题件选择  </TD>
          <TD  class= input>  <Input class=code name=Quest id=Quest style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <TD  class= title>  是否回复 </TD>
          <TD  class= input> <Input class=code name=IfReply id=IfReply style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" value="N" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <TD  class= title >
            问题件所在管理机构
          </TD>
          <TD  class= input >
            <Input class=code name=OManageCom id=OManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
		  <TD  class= title ></td>
          <TD  class= input>
          <Input  type= "hidden" class="common wid" name = SerialNo id=SerialNo >
          </TD>
		  <TD  class= title ></td>
          <TD  class= input> </td>
          <TR>

      </TR>
    </table>
    <Input type=hidden class="readonly" name=IfReply id=IfReply>
    
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
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    </div>
      
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 问题件内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="8" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 问题件回复 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" id=ReplyResult cols="135" rows="8" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ContNoHide" value= "">
    <input type= "hidden" name= "QuesFlag" value="">
    <input type= "hidden" name= "Type" value="">
    <input type= "hidden" name= "HideOperatePos" value="">
    <input type= "hidden" name= "HideSerialNo" value="">
    <input type= "hidden" name= "HideQuest" value="">
    <input type= "hidden" name= "MissionID" value="">
    <input type= "hidden" name= "SubMissionID" value="">
  </p>
</form>
<div id = "divButton">
	<input class= cssButton type= "button" name= "sure" value="回复问题" class= Common onClick="reply()">
  <input class= cssButton type= "button" name= "sure" value="回复完毕" class= Common onClick="replySave()">
</div>
<div id = "divModiButton">
	<input type= "button" name= "sure" value="问题件回复" class= cssButton onClick="IfPrint()">
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br /><br /><br /><br />
</body>
</html>
