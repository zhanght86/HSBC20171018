<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuGrpSpecInput.jsp
//程序功能：人工核保条件承保
//创建日期：
//创建人  ：
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
  <SCRIPT src="UWManuGrpSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 条件承保 </title>
  <%@include file="UWManuGrpSpecInit.jsp"%>
</head>
<body  onload="initForm();" >

  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWManuGrpSpecSave.jsp">
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
      <TD  class= input> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="SpecReason" id=SpecReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>


    	
    	<table class = common>
    	<TR  class= common>
          <TD  class= title>
            特别内容
          </TD>
          <tr></tr>
          
      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <textarea name="Remark" id=Remark cols="100%" rows="5" witdh=100% class="common" ></textarea></TD>
        </TR>
    	
     <table>
      
      <INPUT type= "hidden" id="GrpContNo" name= "GrpContNo" value= "">
      <INPUT type= "hidden" id="Operate" name= "Operate" value= "">
      <INPUT type= "button" id="button1" name= "sure" value="确  定" class= cssButton  onclick="submitForm();">			
      <INPUT type= "button" id="button1" name= "sure" value="删  除" class= cssButton  onclick="deleteSpec();">
      <INPUT type= "button" id="button2" name= "back" value="返  回" class= cssButton  onclick="top.close();">					
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
