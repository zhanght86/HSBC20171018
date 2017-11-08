
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<script>
	var branchtype="<%=request.getParameter("branchtype")%>";
	//alert(branchtype);
</script>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./ComCommonQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ComCommonQueryInit.jsp"%>
  <title>业务员查询 </title>
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
                机构查询条件
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
  <table  class= common>
      <TR  class= common> 
        <TD class= title>   机构编码  </TD>
        <TD  class= input>  <Input class=common  name=AgentCode > </TD>
        <TD class= title>   机构名称  </TD>
        <TD class= input> <Input class=common name=AgentGroup onblur=" trimname();">  </TD>
        <TD class= title>  管理机构 </TD>
        <TD class= input> <Input name=ManageCom class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true>   </TD>
      </TR>
      
   
    </table>
          
          <!--INPUT class=common VALUE="查询" TYPE=button onclick="easyQueryClick();"--> 
	   <table> 
		    <tr>
		    <td><INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> </td>
		    <td><INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();"> 	</td>

			<td>  
			 <!--INPUT class=common VALUE="业务员信息" TYPE=button onclick="returnParent();"-->  </td>
		   <td><!--INPUT class=common VALUE="投保单信息" TYPE=button onclick="ProposalClick();"-->  </td>
			<td> <!--INPUT class=common VALUE="保单信息" TYPE=button onclick="PolClick();"-->  </td>
			<td>  
			<!--INPUT class=common VALUE="销户保单信息" TYPE=button onclick="DesPolClick();"-->  </td>
		    </tr> 
	   </table> 
    </Div>      
          				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 查询结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
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
