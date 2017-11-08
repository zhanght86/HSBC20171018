<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：GrpQuestQuery.jsp
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
  <form method=post name=fm id="fm" target="fraSubmit" action="./QuestQueryChk.jsp">
    <table>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
        </td>
				<td class= titleImg>扫描单证信息</td>
      </tr>
    </table>
    <div class="maxbox1" id="maxbox">
    <table class=common >
       <TR  class= common>
          <TD  class= title>
            合同投保单号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name=GrpContNo id="GrpContNo">
          </TD>
          <TD  class= title>
            返回对象
          </TD>
          <TD  class= input>
            <Input class=code name=BackObj id="BackObj" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="showCodeListEx('BackObj',[this,''],[0,1],'','','',true);" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1],'','','',true);" onKeyUp="showCodeListKeyEx('BackObj',[this,''],[0,1],'','','',true);" >
          </TD>
          <TD  class= title>
            操作位置
          </TD>
          <TD  class= input>
            <Input class=code name=OperatePos id="OperatePos" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="showCodeListEx('OperatePos',[this,''],[0,1],'','','',true);" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1],'','','',true);" onKeyUp="showCodeListKeyEx('OperatePos',[this,''],[0,1],'','','',true);" >
          </TD>
        </TR>

    	<TR  class= common>
          <TD  class= title>  管理机构  </TD>
          <TD  class= input>  <Input class=code name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="return showCodeList('station',[this]);" onDblClick="return showCodeList('station',[this]);" onKeyUp="return showCodeListKey('station',[this]);">  </TD>
          <TD  class= title>   问题件选择  </TD>
          <TD  class= input>  <Input class=code name=Quest id="Quest" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="showCodeListEx('GrpQuest',[this,''],[0,1],null,null,null,null,1500);" ondblclick= "showCodeListEx('GrpQuest',[this,''],[0,1],null,null,null,null,1500);" onKeyUp="showCodeListKeyEx('GrpQuest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <TD  class= title>  是否回复 </TD>
          <TD  class= input> <Input class=code name=IfReply id="IfReply" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" value="" onClick="return showCodeList('YesNo',[this]);" onDblClick="return showCodeList('YesNo',[this]);" onKeyUp="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <TD  class= title>
            问题件所在管理机构
          </TD>
          <TD  class= inpu >
            <Input class=code name=OManageCom id="OManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="return showCodeList('station',[this]);" onDblClick="return showCodeList('station',[this]);" onKeyUp="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= title >
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class= Common name = SerialNo >
          </TD>
        
      </TR>
      <TR>
       <TD  class= title>
             <input type="button" class=cssButton name= "Query" class= Common value="查  询" onClick= "query()"> 
        </TD> 
      
      </TR>
    </table>
    <!--Input type=hidden class="readonly" name=IfReply-->
</div>    
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
      
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 问题件内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 问题件回复 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ProposalNoHide" value= "">
    <input type= "hidden" name= "Type" value="">
    <input type= "hidden" name= "HideOperatePos" value="">
    <input type= "hidden" name= "HideQuest" value="">
    <input type= "hidden" name= "GrpContNoHide" value="">
    <input type= "hidden" name= "HideSerialNo" value="">
    
  </p>
</form>
<div id = "divButton">
	<input type= "button" name= "sure" value="问题回复" onClick="reply()">
</div>
<div id = "divModiButton">
	<input type= "button" name= "sure" value="问题件打印标记设置" onClick="IfPrint()">
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br><br>
</body>
</html>
