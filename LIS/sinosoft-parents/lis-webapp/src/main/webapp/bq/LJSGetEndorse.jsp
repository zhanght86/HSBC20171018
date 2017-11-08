<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>\
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="./LJSGetEndorse.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="LJSGetEndorseInit.jsp"%>
  <title>费用明细查询 </title>
</head>
<body  onload="initForm('');" >

  <form action="./LJSGetEndorse.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 个人信息部分 -->
	
    <Table>
    	<tr align=right>
        <td class=button width="10%">
          <INPUT class= cssButton VALUE="  按保单排序  "     TYPE=button onclick="initForm('PolNo');return false;"> 
          <INPUT class= cssButton VALUE="按交费项目排序" TYPE=button onclick="initForm('PayPlanCode');return false;"> 
          <INPUT class= cssButton VALUE="按费用类型排序" TYPE=button onclick="initForm('FeeFinaType');return false;"> 
          <INPUT class= cssButton VALUE="按费用金额排序" TYPE=button onclick="initForm('GetMoney');return false;">
       </td>
     </tr> 
        </Table>
    
				
    <table>
    	<tr>
        	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJSGetEndose);">
    		</td>
    		<td class= titleImg>
    			 费用明细信息
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLJSGetEndorse" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLJSGetEndorseGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <table align=lift>
		      <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
		      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
		      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="getNextPage();"> 
		      <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();"> 
        	
		    </table>			
  	</div>
  </form>
  <INPUT class = cssButton VALUE="返   回" TYPE=button onclick="returnParent();"> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
