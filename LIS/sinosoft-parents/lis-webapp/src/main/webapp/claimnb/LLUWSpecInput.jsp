<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLUWSpecInput.jsp
//程序功能：二核特约承保
//创建日期：2005-11-04 
//创建人  ：万泽辉
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
   String tQueryFlag = request.getParameter("QueryFlag");

%>
 <script language="JavaScript">
	var tQueryFlag = "<%=tQueryFlag%>";
 </script>                          

<html> 
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLUWSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 特约承保 </title>
  <%@include file="LLUWSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tClmNo%>','<%=tProposalNo%>','<%=tBatNo%>');" >

  <form method=post name=fm id=fm target="fraSubmit" >
    <!-- 以往核保记录部分（列表） -->
     <Div  id= "divLCPol" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
    		</td>
    		   <td class= titleImg>保单信息</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanLLUWSpecGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>
   
   <Div  id= "divLCPol2" style= "display: ''">  	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		  <td class= titleImg>特约信息</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanLLUWSpecContGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

    </table>
  </Div>
  
        <table class = common>
    	<TR  class= common>
          <TD  class= title>特约原因 </TD>
          <tr></tr>          
          <TD colspan="6" style="padding-left:16px"> <textarea name="SpecReason" id="SpecReason" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
   	
	 <table class = common>
	  <TR  class= common>
       <TD  class= title>特别约定 </TD>
       <tr></tr>
       <TD colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
     	
     <table class=common  style="display:none">
     	<tr class=common>
     		<td class=common>
		      <INPUT type= "button" id="button1"  name= "sure"  value="确  认" class= cssButton  onclick="saveClick()">			
		      <INPUT type= "button" id="button3"  name="delete" value="删  除" class= cssButton  onclick="deleteClick();">      
		      <INPUT type= "button" id="button2"  name= "back"  value="返  回" class= cssButton  onclick="top.close();">					
		    </td>
		</tr>
     </table>
     <br>
     <a href="javascript:void(0);" id="button1"  name="sure" class="button" onClick="saveClick();">确    认</a>
     <a href="javascript:void(0);" id="button3"  name="delete" class="button" onClick="deleteClick();">删    除</a>
     <a href="javascript:void(0);" id="button2"  name="back" class="button" onClick="top.close();">返    回</a>
      <INPUT type= "hidden" name= "ClmNo" value= "">
      <INPUT type= "hidden" name= "ContNo" value= "">
      <INPUT type= "hidden" name= "PolNo" value= "">
      <INPUT type= "hidden" name= "ProposalNo" value= "">
      <INPUT type= "hidden" name= "Flag" value="">
      <input type= "hidden" name= "UWIdea" value="">
      <input type= "hidden" name= "PrtNo" value="">
      <input type= "hidden" name= "BatNo" value="">
      <INPUT type= "hidden" name= "MissionID" value= "">
      <INPUT type= "hidden" name= "SubMissionID" value="">
      <input type= "hidden" name= hideOperate value=''> 
      <input type= "hidden" name= SerialNo value=''> 
      <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
