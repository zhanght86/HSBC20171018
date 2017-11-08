<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：NotepadQuery.jsp
//程序功能：记事本查询
//创建日期：2005-9-29 16:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<%                                                                                	                    
    String tPrtNo = request.getParameter("PrtNo");     
%>                                                                                
                                                                                  
 
  
  <SCRIPT src="NotepadQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NotepadQueryInit.jsp"%>
  <title>记事本查询</title>
  
</head>


<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <div id= "divNotePad" style= "display: ''" >
	  <table>
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotepadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 记事本信息 
		    	</td>
	    	</tr>
	    </table>        
		<div id= "divNotepadInfo" style= "display: ''"  >

		    <Div  id= "divQuestGrid" style= "display: ''" align = center>
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  				<span id="spanQuestGrid">
		  				</span> 
		  		  		</td>
		  			</tr>
		    	</table>
			         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
			         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
			         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
			         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> 	      
		    </div>
			
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> 记事内容（400字以内,回车符占一个汉字）</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea value readonly name="Content" id=Content verify="记事内容|len<800" verifyorder="1" cols="130" rows="5" class="common" ></textarea></TD>
			    </TR>
			  </table>
			  
			  <table class= common>
			  <TR  class= common>			
			  <td class= input >
	         <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="GoBack();"> 
        </td>		
			  </TR>	
			  </table>	
			  	  
			  <INPUT CLASS=cssButton VALUE="新  增" TYPE=hidden onclick="addNote();">
			  <INPUT CLASS=cssButton VALUE="清  空" TYPE=hidden onclick="fm.Content.value=''">
			  <INPUT class=cssButton VALUE="返  回" TYPE=hidden onclick="top.close();">	
	  </div>	   
   </div> 	 
   
      	 <Input type=hidden name="PrtNo" id="PrtNo" ><!--印刷号-->
    	
    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
