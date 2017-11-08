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
  <SCRIPT src="GrpQuestQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpQuestQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tGrpContNo%>','<%=tFlag%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./GrpQuestQueryChk.jsp">
    <div class="maxbox1">
    <table class=common >
    	<TR  class= common>
          <TD  class= title>
            合同投保单号码
          </TD>
          <TD  class= input>
            <Input class="common wid"  name=GrpContNo id="GrpContNo">
          </TD>
          <TD  class= title>
            返回对象
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=BackObj id="BackObj" onclick= "showCodeListEx('BackObj',[this,''],[0,1],'','','',true);" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1],'','','',true);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1],'','','',true);" >
          </TD>
          <TD  class= title>
            操作位置
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" type= "hidden" class=code name=OperatePos id="OperatePos" onclick= "showCodeListEx('OperatePos',[this,''],[0,1],'','','',true);" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1],'','','',true);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1],'','','',true);" >
          </TD>
        </TR>

    	  <TR  class= common>
          <TD  class= title>  管理机构  </TD>
          <TD  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <TD  class= title>   问题件选择  </TD>
          <TD  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=Quest id="Quest" onclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,500);" ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,500);" onkeyup="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,500);" >  </TD>
          <TD  class= title>  是否回复 </TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=IfReply id="IfReply" value="N" onclick="return showCodeList('YesNo',[this]);" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <TD  class= title >
            问题件所在管理机构
          </TD>
          <TD  class= input >
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=OManageCom id="OManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class="common wid" name = SerialNo id="SerialNo">
          </TD>
        
        </TR>
      </table>
    </div>
    <a href="javascript:void(0)" class=button name= "Query" id="Query" onclick="query();">查  询</a>
    
     <!-- <input type="button" class=cssButton name= "Query" class= Common value="查  询" onClick= "query()">                   
     <input type="button" class=cssButton name= "Back" class= Common value="返  回" onClick= "returnParent()">  -->          
    <Input type=hidden class="readonly" name=IfReply id="IfReply">
    
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
    </Div>
      
  <table  class= common>
    <TR  class= common> 
      <TD  class= title> 问题件内容 </TD>
      <TD><textarea name="Content" cols="142" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <table class= common>
    <TR  class= common> 
      <TD  class= title> 问题件回复 </TD>
      <TD ><textarea name="ReplyResult" cols="142" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "Flag"  id="Flag"value="">
    <input type= "hidden" name= "GrpContNoHide" id="GrpContNoHide" value= "">
    <input type= "hidden" name= "Type" id="Type" value="">
    <input type= "hidden" name= "HideOperatePos" id="HideOperatePos" value="">
    <input type= "hidden" name= "HideSerialNo" id="HideSerialNo" value="">
    <input type= "hidden" name= "HideQuest" id="HideQuest" value="">
    <input type= "hidden" name= "MissionID" id="MissionID" value="">
    <input type= "hidden" name= "SubMissionID" id="SubMissionID" value="">
  </p>
</form>
<div id = "divButton">
  <div>
  <a href="javascript:void(0)" class=button name= "sure" id="sure" onclick="reply();">回复问题</a>
	<!-- <input class= cssButton type= "button" name= "sure" value="回复问题" class= Common onClick="reply()"> -->
  </div>
  <br>
</div>
<div id = "divModiButton">
  <div>
  <a href="javascript:void(0)" class=button name= "sure" id="sure" onclick="IfPrint();">修改问题件打印标记</a>
  </div>
  <br>
	<!-- <input type= "button" name= "sure" value="修改问题件打印标记" class= cssButton onClick="IfPrint()"> -->
</div>
<a href="javascript:void(0)" class=button name= "Back" id="Back" onclick="returnParent();">返  回</a>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br>
<br>
<br>
<br>
</body>
</html>
