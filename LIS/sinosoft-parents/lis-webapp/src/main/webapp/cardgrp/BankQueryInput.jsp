<%
//程序名称：AgentQueryInput.jsp
//程序功能：
//创建日期：2003-04-8
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BankQueryInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./BankQueryInit.jsp"%>
  <title>银行查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <!--业务员查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            </td>
            <td class= titleImg>
                银行查询条件
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
     <table  class= common>
      <TR  class= common> 
        <TD  class= title> 银行编码 </TD>
        <TD  class= input>   <Input name=BankCode class= common >  </TD>        
        <TD  class= title> 银行名称  </TD>
        <TD  class= input>   <Input name=BankName class= common >  </TD>
      </TR>   
     </table>
                   
	   <table> 
		    <tr>
		    <td><INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> </td>
		    <td><INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();"> 	</td>
        </tr> 
	   </table> 
  </Div>      
    <hr>      				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBankGrid);">
    		</td>
    		<td class= titleImg>
    			 银行信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divBankGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
    		<tr>
    			<td>
			      <INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
