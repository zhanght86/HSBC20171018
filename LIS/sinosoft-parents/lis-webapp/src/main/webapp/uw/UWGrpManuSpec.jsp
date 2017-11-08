<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpec.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWGrpManuSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 条件承保 </title>
  <%@include file="UWGrpManuSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPolNo%>');" >

  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWGrpManuSpecChk.jsp">
    <!-- 以往核保记录部分（列表） -->
     <Div  id= "divLCPol" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			投保单信息
    		</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWSpecGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>
    
    
   
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWResultSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	</Div>

    </table>
        <table class = common>
    	<TR  class= common>
          <TD  class= title>
            特约原因
          </TD>
          <tr></tr>          
      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea name="SpecReason" id=SpecReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>


    	
    	<table class = common>
    	<TR  class= common>
          <TD  class= title>
            特别约定
          </TD>
          <tr></tr>
          
      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea id="Remark" name="Remark" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    	
     <table>
      
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
       <INPUT type= "hidden" id="PolNo" name= "PolNo" value= "">
      <INPUT type= "hidden" id="Flag" name= "Flag" value="">
      <input type= "hidden" id="UWIdea" name= "UWIdea" value="">
      <input type= "hidden" id="PrtNo" name= "PrtNo" value="">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
      <INPUT type= "hidden" id="operate" name= "operate" value="">
      <INPUT type= "hidden" id="proposalno" name= "proposalno" value="">
      <INPUT type= "hidden" id="serialno" name= "serialno" value="">
      
      <INPUT type= "button" id="button1" name= "sure" value="添  加" class= cssButton  onclick="submitForm()">			
      <INPUT type= "button" id="button3" name= "sure" value="修  改" class= cssButton  onclick="submitForm()">			
      
      <INPUT type= "button" id="button2" name= "back" value="返  回" class= cssButton  onclick="top.close();">					
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
