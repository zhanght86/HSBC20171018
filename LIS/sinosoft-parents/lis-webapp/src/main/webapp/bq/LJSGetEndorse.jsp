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
  <title>������ϸ��ѯ </title>
</head>
<body  onload="initForm('');" >

  <form action="./LJSGetEndorse.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
	
    <Table>
    	<tr align=right>
        <td class=button width="10%">
          <INPUT class= cssButton VALUE="  ����������  "     TYPE=button onclick="initForm('PolNo');return false;"> 
          <INPUT class= cssButton VALUE="��������Ŀ����" TYPE=button onclick="initForm('PayPlanCode');return false;"> 
          <INPUT class= cssButton VALUE="��������������" TYPE=button onclick="initForm('FeeFinaType');return false;"> 
          <INPUT class= cssButton VALUE="�����ý������" TYPE=button onclick="initForm('GetMoney');return false;">
       </td>
     </tr> 
        </Table>
    
				
    <table>
    	<tr>
        	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJSGetEndose);">
    		</td>
    		<td class= titleImg>
    			 ������ϸ��Ϣ
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
		      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
		      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
		      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
		      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> 
        	
		    </table>			
  	</div>
  </form>
  <INPUT class = cssButton VALUE="��   ��" TYPE=button onclick="returnParent();"> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
