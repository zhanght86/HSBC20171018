<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<%
//程序名称：RReportQuery.jsp
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-24   更新原因/内容：根据新核保要求进行修改
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";

  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<%
  String tFlag = "";
  String tPrtSeq = "";
  String tMissionID = "";
  String tSubMissionID = ""; 
 

  tContNo = request.getParameter("ContNo");
  String tEdorNo = request.getParameter("EdorNo");
  
  tPrtSeq = request.getParameter("PrtSeq");
  tFlag = request.getParameter("Flag");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");  
  String tQueryFlag = request.getParameter("QueryFlag"); 
  
%>            
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var customers = "";                   //生调人选
  var Flag = "<%=tFlag%>";
  var QueryFlag = "<%=tQueryFlag%>";
  
 	var tContNo = "<%=tContNo%>";
    var tEdorNo = "<%=tEdorNo%>";
</script>
<head >
<title>问题件查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="BQRReportQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQRReportQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tEdorNo%>','<%=tPrtSeq%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post id="fm" name=fm target="fraSubmit">
  	
  	<div id = "divMainHealth" style = "display : none">
  	 <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 生存调查报告内容：
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
  </div>
    <div id ="divOperation" class="maxbox1" style = "display : none">
    <table class=common>
       <tr  class= common>
          <td class=title5>生存调查人</td>
		  <td class=input5><Input  class=wid id="Operator" name= 'Operator' readonly></td>
          <td class=title5>发送时间</td>
		  <td class=input5><Input  class=wid id="MakeDate" name= 'MakeDate' readonly></td>
       </tr>
       <tr  class= common>
          <td class=title>回复时间</td>
		  <td class=input5><Input  class=wid id="ReplyDate" name= 'ReplyDate' readonly></td>
		  <td class=title5></td>
		  <td class=input5></td>
       </tr>
    </table>
    </div>
   
      
  <!--table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 报告内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 回复内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table-->
		<Div id= "divUWSpec2" style= "display: none">
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    		<td class= titleImg>	 生调项目</td>                            
    	</tr>	
    </table>
    <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanRReportGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
  </div>
    <Div  id= "divUWSpec1" class="maxbox1" style= "display: ''">

    	<table class = common>
    		<tr class = common>
    			<td class=title>生调费用</td>
    			<td class="input"><Input  class= "code wid" id="RReportFee" name= 'RReportFee' ></td>
    			<td class=title></td>
         		<td class= input></td>
         		<td class=title></td>
         		<td class= input></td>
    		</tr>
      </table>
   </Div>
    <Div  id= "divUWDis" style= "display: none">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanRReportResultGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      </div>
        <table class=common>
         <TR  class= common> 
           <TD  class= common> 生调说明 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Contente" id=Contente cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
       <table class=common>
         <TR  class= common> 
           <TD  class= common> 生调报告 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="ReplyContente" id=ReplyContente cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="EdorNo" name= "EdorNo" value= "">
    <input type= "hidden" id="Type" name= "Type" value="">
    <input type= "hidden" id="PrtSeq" name= "PrtSeq" value="">
    <input type= "hidden" id="MissionID" name= "MissionID" value="">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
    
  </p>
   <div id = "divRReportButton" style = "display:none">
  	<input value="生调结果保存" class=cssButton type=button onclick="saveRReport();" >   	
  </Div>
  <div id = "divRReportButton" style = "display:none">
  	<input value="生调评分表" class=cssButton type=button id="Assess" name=Assess onclick="soreRReport();" >   	
  </Div>
  <input class= cssButton type= "button" value=" 返  回 "  onClick="top.close();">
  <br ><br ><br ><br ><br >
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
