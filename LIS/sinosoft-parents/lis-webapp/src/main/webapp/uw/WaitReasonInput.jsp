<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：QuestInput.jsp
//程序功能：核保等待原因录入
//创建日期：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tMissionID = "";
	String tSubMissionID = "";	
	String tContNo = "";
	String tType = "";
	String tOtherFlag = "";
	
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
	tContNo =  request.getParameter("ContNo");
	tType =  request.getParameter("Type");
	tOtherFlag =  request.getParameter("OtherFlag");
	
	GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="WaitReason.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>新单核保等待</title>
  <%@include file="WaitReasonInit.jsp"%>
<script language="javascript">
  var tMissionID = "<%=tMissionID%>";
  var tSubMissionID = "<%=tSubMissionID%>";
  var tContNo ="<%=tContNo%>";
  var tType ="<%=tType%>";
  var tOtherFlag ="<%=tOtherFlag%>";
  
  	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>  
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./WaitReasonInputChk.jsp">
  <div class="maxbox1">
  <table  class=common border=0 align=center>
      <tr>
       <td class=title5>印刷号</td>
       <TD  class=input5>
         <Input class= "readonly wid" readonly name=PrtNo >            
       </TD> 	    
       <TD class = title5> 并单号 </TD>
       <TD  class=input5><Input class="readonly wid" readonly name=UniteNo ></TD>      
     </tr>    
  </table>
  </div>
  <Div  id= "divQuery" style= "display: none" >
	  <table  class=common border=0 align=center>
	      <tr>
	      <td class=title>管理机构</td>
	       <TD  class=input>
	         <Input class= "readonly" readonly name=ManageCom >            
	       </TD>	    
	       <TD class = title> 业务员工号 </TD>
	       <TD  class=input><Input class="readonly" readonly name=AgentCode ></TD>     
	     </tr>    
	  </table>
  </Div>
  <hr class="line">
  <table>
      <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 被保人信息
    		</td>
      </tr>
  </table>
  <Div  id= "divLCPol2" style= "display: ''" >
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span>
  			  	</td>
  			</tr>
      </table>
  </Div> 
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divWaitCont);">
			</td>
			<td class= titleImg>同一被保险人未承保投保单</td>
		</tr>
	</table>
 <Div  id= "divWaitCont" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanWaitContGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	<div align=center>
    	<input CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    </div>
    <table>
	  <tr>
	    <td>
	    <INPUT VALUE="操作履历查询" class=cssButton TYPE=button name="uwButton6" onclick="QueryRecord()">	    
	    </td>
	  </tr>
	</table>
  </Div>	
  <hr class="line">   
   <table class = common border=0>
    <TR class=common >
       <TD  class= title5>
    	  核保等待原因
    	</TD>
        <TD class= input5>
            <Input class=codeno name=WaitReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeList('WaitReason',[this,Reason],[0,1]);" onkeyup="showCodeListKey('WaitReason',[this,Reason],[0,1]);"><Input class="codename wid" name=Reason readonly="readonly">
        </TD>
        <td></td>
       </TR>
    </table>    
  <table class = common align = center width="121%" height="17%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 核保员说明 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "ContNoH" value= "">
    <input type= "hidden" name= "MissionIDH" value= "">
    <input type= "hidden" name= "SubMissionIDH" value= "">
    <input type= "hidden" name= "ActivityIDH" value= "">     
    <input type= "hidden" name= "InsuredNoH" value= "">  
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" name= "sure" class= cssButton  value=" 确  认 " onClick="submitForm()">
<!-- modified by liuyuxiao 2011-05-24 隐去返回，在tab中无用 -->
<input class= cssButton type= "button" value=" 返  回 " class= Common onClick="top.close();" style= "display: none">
</body>
</html>
