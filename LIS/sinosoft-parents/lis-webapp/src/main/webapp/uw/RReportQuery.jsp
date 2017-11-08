<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
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
  <SCRIPT src="RReportQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RReportQueryInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtSeq%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id="fm" target="fraSubmit">
  	
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
    <div id ="divOperation" style = "display : none">
    <table class=common>
       <tr  class= common>
          <td class=title>生存调查人</td><td class=input><Input  class= "common wid" name= 'Operator' id="Operator" readonly></td>
          <td class=title>发送时间</td><td class=input><Input  class= coolDatePicker name= 'MakeDate' readonly id="MakeDate" onClick="laydate({elem:'#MakeDate'});"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
          <td class=title>回复时间</td><td class=input><Input  class= coolDatePicker name= 'ReplyDate' readonly id="ReplyDate" onClick="laydate({elem:'#ReplyDate'});"><span class="icon"><a onClick="laydate({elem: '#ReplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
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
    		<td class= titleImg> 生调项目</td>                            
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
    			<td class=title>生调费用</td><td class=input><Input  class="common wid" name= 'RReportFee' id="RReportFee" ></td>
                <td class=title></td><td class=input></td>
                <td class=title></td><td class=input></td>
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
           <TD  class= title> 生调说明 </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="Contente" id="Contente" cols="120" rows="3" class= common >
             </textarea>
           </TD>
         </TR>
      </table>
       <table class=common>
         <TR  class= common> 
           <TD  class= title> 生调报告 </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="ReplyContente" id="ReplyContente" cols="120" rows="3" class= common >
             </textarea>
           </TD>
         </TR>
      </table>
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="Type" name= "Type" value="">
    <input type= "hidden" id="PrtSeq" name= "PrtSeq" value="">
    <input type= "hidden" id="MissionID" name= "MissionID" value="">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
    
  </p>
   <div id = "divRReportButton" style = "display:''">
  	<input value="生调结果保存" class=cssButton type=button id="saveresult" name=saveresult onClick="saveRReport();" > 
  </Div>
  <div id = "divRReportButton1" style = "display:''">
  	<input value="生调评分表" class=cssButton type=button id="Assess" name=Assess onClick="soreRReport();" > 
  	<!-- modified by liuyuxiao 2011-05-24 隐去返回，在tab中无用 -->
  	<input class= cssButton type= "button" value=" 返  回 " class= Common onClick="top.close();" style= "display: none">
  </Div>
</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
