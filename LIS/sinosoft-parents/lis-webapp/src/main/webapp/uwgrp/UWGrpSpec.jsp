<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWGrpSpec.jsp
//程序功能：团体人工核保特约录入
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
  <SCRIPT src="UWGrpSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 条件承保 </title>
  <%@include file="UWGrpSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tFlag%>','<%=tUWIdea%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWGrpSpecChk.jsp">
   <!-- 以往核保记录部分（列表） -->
    <table>
    	<TR  class= common>
          <TD  class= title>
            特别约定
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="Remark" cols="100%" rows="10" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>
    <table>
    	<TR  class= common>
          <TD  class= title>
            特约原因
          </TD>
          <tr></tr>          
      <TD  class= input> <textarea name="SpecReason" cols="100%" rows="10" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>   
   
   <Div  id= "divUWSpec" style= "display: 'none'">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 保单查询结果
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      </div>
      <table>
    	<TR  class= common>
          <TD  class= title>
            加费原因
          </TD>
          <tr></tr>
          
          <TD  class= input> <textarea name="Reason" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
     </Div>
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "Flag" value="">
      <input type= "hidden" name= "UWIdea" value="">
      <INPUT type= "button" name= "sure" value="确认" onclick="submitForm()">			
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
