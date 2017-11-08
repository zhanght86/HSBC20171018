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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./BankQueryInit.jsp"%>
  <title>银行查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id="fm" target="fraSubmit">
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
    <div class="maxbox1">
  <Div  id= "divLAAgent" style= "display: ''">
     <table  class= common>
      <TR  class= common> 
        <TD  class= title> 银行编码 </TD>
        <TD  class= input>   <Input name=BankCode id="BankCode" class="common wid">  </TD>        
        <TD  class= title> 银行名称  </TD>
        <TD  class= input>   <Input name=BankName id="BankName" class="common wid">  </TD>
        <td class= title></td>
        <td class= input></td>
      </TR>   
     </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  
		    <!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">
		    <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">  -->     				
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
      <div style= "display: none">
      <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 			      
      <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 
      </div>						
  	</div>
    <a href="javascript:void(0)" class=button onclick="returnParent();">返  回</a>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
  <br>
  <br>
  <br>
  <br>
</body>
</html>
