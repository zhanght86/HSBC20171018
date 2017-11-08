<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：RReportQuery.jsp
//程序功能：生存调查报告查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<%
  String tFlag = "";
  String tPrtSeq = "";
  String tGrpContNo = "";
  String tSubMissionID = ""; 
  String tMissionID = ""; 
  String tContNo="";
  String tGrpPolNo="";
  tGrpContNo = request.getParameter("GrpContNo");
  
 


  
%>            
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var customers = "";                   //生调人选
  var Flag = "<%=tFlag%>";
 
</script>
<head >
<title>问题件查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <script src="../common/laydate/laydate.js"></script>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpPEResult.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpPEResultInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tGrpContNo%>','<%=tPrtSeq%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
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
      <table>
   
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    		<td class= titleImg> 已体检个人保单</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanContGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    <hr class="line">
    <div class="maxbox1">
    <table class=common>
        <TR  class= title>
         
          <TD  class= title5>  体检医院  </TD>
          <TD  class= input5> <Input class="common wid" id="PEAddress" name='PEAddress' > </TD>
          <TD  class= title5>  体检医师  </TD>
          <TD  class= input5> <Input class="common wid" id="PEDoctor" name='PEDoctor' > </TD>
        </TR>
        <TR  class= title>
          <TD  class= title5>  体检时间  </TD>
          <!--<TD  class= input> <Input class="common" name='PEDate' > </TD> -->
          <TD  class= input5>
            <%-- <Input class="common"  dateFormat="short" id="PEDate" name='PEDate' verify="体检时间|date" >--%>
            <Input id="PEDate" name='PEDate' class="common wid"  onClick="laydate({elem: '#PEDate'});" verify="体检时间|date" dateFormat="short" ><span class="icon"><a onClick="laydate({elem: '#PEDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>  复查时间  </TD>
          <!--<TD  class= input> <Input class="common" name='REPEDate' > </TD>-->
          <TD  class= input5>
           <%-- <Input class="common"  dateFormat="short" id="REPEDate" name='REPEDate' verify="复查时间|date" >--%>
            <Input id="REPEDate" name='REPEDate' class="common wid"  onClick="laydate({elem: '#REPEDate'});" verify="复查时间|date" dateFormat="short" ><span class="icon"><a onClick="laydate({elem: '#REPEDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>

    </table>
    </div>
    <table>
    
    
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    		<td class= titleImg>	 体检项目</td>                            
    	</tr>	
    </table>
    </div>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanHealthGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
   </div>
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
     <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   疾病结果</td>
    	</tr>
    </table>
      <Div  id= "divUWDis" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanDisDesbGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
      </div>
        
        <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   体检结果</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <table>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Note" id=Note cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
      </div>
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="Type" name= "Type" value="">
    <input type= "hidden" id="PrtSeq" name= "PrtSeq" value="">
    <input type= "hidden" id="MissionID" name= "MissionID" value="">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
    
  </p>
   <div id = "divRReportButton" style = "display: none">
  	<input value="生调结果保存" class=cssButton type=button onclick="saveRReport();" > 
  </Div>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br/><br/><br/><br/>
</body>
</html>
